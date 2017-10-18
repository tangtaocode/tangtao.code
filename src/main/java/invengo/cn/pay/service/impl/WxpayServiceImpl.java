package invengo.cn.pay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.alipay.api.internal.util.StringUtils;
import com.google.gson.Gson;

import invengo.cn.pay.component.BaseComponent;
import invengo.cn.pay.component.PayMsgSender;
import invengo.cn.pay.config.WXconfig;
import invengo.cn.pay.mapper.OrderItemExMapper;
import invengo.cn.pay.model.CacheObject;
import invengo.cn.pay.model.CommonDTO;
import invengo.cn.pay.model.DreamResponse;
import invengo.cn.pay.model.DreamStatus;
import invengo.cn.pay.model.IPayDTO;
import invengo.cn.pay.model.MessageType;
import invengo.cn.pay.model.StockVO;
import invengo.cn.pay.model.WxPayDTO;
import invengo.cn.pay.model.entity.OrderItem;
import invengo.cn.pay.model.wx.WXrequestParamsBean;
import invengo.cn.pay.service.IOrderService;
import invengo.cn.pay.service.IPriceSearchService;
import invengo.cn.pay.service.IWxpayService;
import invengo.cn.pay.utils.DateUtils;
import invengo.cn.pay.utils.MemberUtil;
import invengo.cn.pay.utils.OrderUtil;
import invengo.cn.pay.utils.RedisCacheServiceImpl;
import invengo.cn.pay.utils.RequestUtil;
import invengo.cn.pay.utils.StockUtil;
import invengo.cn.pay.utils.wxsdk.WXPay;
import invengo.cn.pay.utils.wxsdk.WXPayConstants;
import invengo.cn.pay.utils.wxsdk.WXPayUtil;

@SuppressWarnings("rawtypes")
@Service
@Transactional
public class WxpayServiceImpl extends BaseComponent implements IWxpayService {
	private static Logger logger = Logger.getLogger(WxpayServiceImpl.class);
	@Autowired
	private WXconfig wxconfig;
	@Autowired
	private WXrequestParamsBean wxbean;
	@Autowired
	private IOrderService orderService;
	@Autowired
	private RedisCacheServiceImpl redis;
	@Autowired
	private PayMsgSender payMsgSender;
	@Autowired
	private IPriceSearchService priceSearchService;
	@Autowired
	private OrderItemExMapper orderItemExMapper;

	@Override
	public DreamResponse unifiedOrder(IPayDTO dto) {
		DreamResponse res = new DreamResponse();
		CommonDTO real = (CommonDTO) dto;
		if (real.getEpcs() == null || real.getEpcs().size() == 0) {
			res.setMsg("标签列表不能为空！");
			res.setStatus(DreamStatus.FAIL);
			return res;
		}
		if (StringUtils.isEmpty(real.getMac())) {
			res.setMsg("设备地址不能为空！");
			res.setStatus(DreamStatus.FAIL);
			return res;
		}
		Map<String, String> resmap = new HashMap<String, String>();
		try {
			WXPay wxpay = new WXPay(wxconfig);
			Map<String, String> data = new HashMap<String, String>();
			StockVO stockVo = priceSearchService.getSKUPrice(real);
			data.put("body", stockVo.getListStock().get(0).getWarehouseName());
			data.put("out_trade_no", OrderUtil.generateOrderNo());
			data.put("device_info", real.getMac());
			data.put("fee_type", "CNY");
			// 去除小数点,total_fee不支持小数点
			Double totalAmt = stockVo.getTotalAmt() * 100;// 订单总金额，单位为分
			String temp = totalAmt.toString();
			temp = temp.substring(0, temp.indexOf('.'));
			data.put("total_fee", temp);
			data.put("spbill_create_ip", wxbean.getSpbill_create_ip());
			data.put("notify_url", wxbean.getNotify_url());
			data.put("trade_type", "NATIVE"); // 此处指定为扫码支付
			// data.put("attach",wxbean.getOpenid());
			data.put("product_id", OrderUtil.generateOrderNo("", 9));// 生成一个id
			data.put("time_start", DateUtils.date2String(new Date(), "yyyyMMddHHmmss"));
			data.put("limit_pay", "no_credit");
			//data.put(WXPayConstants.FIELD_SIGN_TYPE, WXPayConstants.HMACSHA256);
			Map<String, Object> mapdto = new HashMap<String, Object>();
			mapdto.put("epclist", real.getEpcs());
			mapdto.put("out_trade_no", data.get("out_trade_no"));
			mapdto.put("device_info", real.getMac());
			mapdto.put("payment_type", DreamStatus.WX);
			mapdto.put("stockVo", stockVo);
			// 记录订单流水
			orderService.add(mapdto);
			logger.info("****微信下订单入参：" + new Gson().toJson(data));
			resmap = wxpay.unifiedOrder(data);
			resmap.put("out_trade_no", data.get("out_trade_no"));
			logger.info("****微信下订单返回结果：" + new Gson().toJson(resmap));
			if (DreamStatus.SUCCESS.equals(resmap.get("return_code"))
					&& DreamStatus.SUCCESS.equals(resmap.get("result_code"))) {
				res.setMsg("微信支付下订单成功");
				res.setStatus(DreamStatus.SUCCESS);
				// 下单成功存入redies缓存
				CacheObject cache = new CacheObject();
				cache.setDevice_info(data.get("device_info"));
				cache.setOut_trade_no(data.get("out_trade_no"));
				cache.setUserId(real.getUserId());
				cache.setMember(MemberUtil.get(stockVo.getListStock().get(0).getWarehouseId() + ""));
				cache.setIspay(false);
				redis.set(data.get("out_trade_no"), cache, 4 * 60 * 60L);
				resmap.put("totalAmt", stockVo.getTotalAmt() + "");
				resmap.put("totalDisAmt", stockVo.getTotalDisAmt() + "");
				res.setExtData(resmap);
				res.setData(StockUtil.filterItem(stockVo.getListStock()));
			} else {
				res.setExtData(resmap);
				res.setMsg("微信支付下订单失败");
				res.setStatus(DreamStatus.FAIL);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("微信支付下订单失败", e);
			res.setExtData(resmap);
			res.setMsg("下单失败");
			res.setStatus(DreamStatus.FAIL);
		}
		return res;
	}

	@SuppressWarnings("unchecked")
	@Override
	public DreamResponse orderQuery(IPayDTO dto) {
		WxPayDTO real = (WxPayDTO) dto;
		DreamResponse res = new DreamResponse();
		res.setStatus(DreamStatus.FAIL);

		String outTradeNo = real.getOut_trade_no();// 商户订单号,两者不能全为空
		// String transactionId = real.getTrade_no();//微信订单号,微信建议优先使用

		// 微信订单查询
		try {
			WXPay wxpay = new WXPay(wxconfig);
			HashMap<String, String> data = new HashMap<String, String>();
			data.put("out_trade_no", outTradeNo);
			// data.put("transaction_id", transactionId);
			Map<String, String> r = wxpay.orderQuery(data);

			if (DreamStatus.FAIL.equals(r.get("return_code"))) {
				res.setMsg("微信订单查询失败!");
				return res;
			}

			if (DreamStatus.FAIL.equals(r.get("result_code"))) {
				// 获取业务错误码
				String errCode = r.get("err_code");
				if (DreamStatus.WXPayStatus.ORDER_NOT_EXIST.equals(errCode)) {
					res.setMsg("此交易订单号不存在!");
				}
				if (DreamStatus.WXPayStatus.SYSTEM_ERROR.equals(errCode)) {
					res.setMsg("系统异常，请重新查询!");
				}
				return res;
			}

			// 根据trade_state判断交易状态,当且仅当return_code
			// 、result_code、trade_state都为SUCCESS时返回订单的所有数据
			// 否则只返回out_trade_no和attach
			// outTradeNo = r.get("out_trade_no");
			// 查询该订单号的商品详情
			OrderItem record = new OrderItem();// 构建查询条件
			CommonDTO queryParam = new CommonDTO();
			record.setOrderNo(outTradeNo);
			List<OrderItem> loadByOrderNo = orderItemExMapper.loadByOrderNo(record);
			ArrayList<String> itemEpcs = new ArrayList<>();
			for (OrderItem orderItem : loadByOrderNo) {
				itemEpcs.add(orderItem.getEpc());
			}
			queryParam.setEpcs(itemEpcs);
			StockVO stockVo = priceSearchService.getSKUPrice(queryParam);

			String tradeStatus = r.get("trade_state");
			res.setStatus(DreamStatus.SUCCESS);
			r.put("totalAmt", stockVo.getTotalAmt() + "");
			r.put("totalDisAmt", stockVo.getTotalDisAmt() + "");
			res.setExtData(r);

			if (DreamStatus.WXPayStatus.SUCCESS.equals(tradeStatus)) {
				res.setMsg("交易支付成功！");
			} else if (DreamStatus.WXPayStatus.REFUND.equals(tradeStatus)) {
				res.setMsg("交易已转入退款!");
			} else if (DreamStatus.WXPayStatus.NOTPAY.equals(tradeStatus)) {
				res.setMsg("交易尚未支付 !");
			} else if (DreamStatus.WXPayStatus.CLOSED.equals(tradeStatus)) {
				res.setMsg("交易结束!");
			} else if (DreamStatus.WXPayStatus.REVOKED.equals(tradeStatus)) {
				res.setMsg("交易已撤销（刷卡支付）!");
			} else if (DreamStatus.WXPayStatus.USERPAYING.equals(tradeStatus)) {
				res.setMsg("用户支付中!");
			} else if (DreamStatus.WXPayStatus.PAYERROR.equals(tradeStatus)) {
				res.setMsg("交易支付失败,请重新支付!");
			} else {
				res.setMsg("未知状态!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setMsg("支付宝订单查询失败！");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		return res;
	}

	@Override
	public DreamResponse reverse(IPayDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String notify(IPayDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, String> res = new HashMap<String, String>();
		String xml = "";
		String notifyXml = "";
		// 微信支付结果参数
		Map<String, String> paramMap = null;
		WxPayDTO real = null;

		try {
			// 获取微信支付成功后回调POST反馈的信息
			logger.info("微信支付回调获取数据开始...");
			notifyXml = RequestUtil.getRequestBodyByReader(request);
			logger.debug("微信支付回调参数xml格式:" + notifyXml);
			if (StringUtils.isEmpty(notifyXml)) {
				logger.error("微信支付回调参数xml为空");
			}
			paramMap = WXPayUtil.xmlToMap(notifyXml);
			String paramJson = new Gson().toJson(paramMap);
			logger.info("微信支付回调参数json格式" + paramJson);
			real = new Gson().fromJson(paramJson, WxPayDTO.class);

			WXPay wxpay = new WXPay(wxconfig);
			logger.info("微信通知成功：" + paramJson);

			if (isPay(real.getOut_trade_no()) || null == real) {// 已通知过,无参数无需通知
				res.put("return_code", DreamStatus.SUCCESS);
				res.put("return_msg", "OK");
				xml = WXPayUtil.mapToXml(res);
				return xml;
			}
			//指定验签方式与下单时的提交到微信服务端的sign_type一致,为HMACSHA256
			paramMap.put(WXPayConstants.FIELD_SIGN_TYPE, WXPayConstants.HMACSHA256);
			if (wxpay.isPayResultNotifySignatureValid(paramMap)) {
				// 签名正确
				logger.info("微信验签成功：" + new Gson().toJson(paramMap));
				if (DreamStatus.SUCCESS.equals(real.getResult_code())
						&& DreamStatus.SUCCESS.equals(real.getReturn_code())) {
					map.put("out_trade_no", real.getOut_trade_no());
					map.put("status", DreamStatus.SUCCESS);
					map.put("type", MessageType.PAY_OVER);
					orderService.update(map);
					// 刷新缓存状态
					CacheObject cache = new CacheObject();
					cache = (CacheObject) redis.get(real.getOut_trade_no());
					cache.setIspay(true);
					redis.remove(real.getOut_trade_no());
					redis.set(real.getOut_trade_no(), cache, 4 * 60 * 60L);
					// 存在会员删除会员
					MemberUtil.remove(cache.getMember());
					// 发息送消通知Android端
					String mjson = new Gson().toJson(map);
					payMsgSender.sendAndroid(cache.getDevice_info(), mjson);
					// 发送推送小票流水消息
					map.put("type", MessageType.PUSH_MESS);
					mjson = new Gson().toJson(cache);
					payMsgSender.sendServer(mjson);
					res.put("return_code", DreamStatus.SUCCESS);
					res.put("return_msg", "OK");
				} else {
					res.put("return_code", DreamStatus.FAIL);
					res.put("return_msg", real.getErr_code_des());
				}
			} else {
				// 签名失败
				logger.info("微信验签失败：" + new Gson().toJson(paramMap));
				map.put("out_trade_no", real.getOut_trade_no());
				map.put("status", DreamStatus.FAIL);
				orderService.update(map);
				res.put("return_code", DreamStatus.FAIL);
				res.put("return_msg", "fail");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.put("return_code", DreamStatus.FAIL);
			res.put("return_msg", "fail");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("微信消息通知异常", e);
		}
		try {
			xml = WXPayUtil.mapToXml(res);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml;
	}

	private boolean isPay(String out_trade_no) {
		CacheObject cache = (CacheObject) redis.get(out_trade_no);
		return cache.isIspay();
	}
}
