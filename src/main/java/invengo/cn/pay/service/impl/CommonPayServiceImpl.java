package invengo.cn.pay.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import invengo.cn.pay.mapper.OrderMapper;
import invengo.cn.pay.model.AliPayDTO;
import invengo.cn.pay.model.CommonDTO;
import invengo.cn.pay.model.DreamResponse;
import invengo.cn.pay.model.DreamStatus;
import invengo.cn.pay.model.GoodVO;
import invengo.cn.pay.model.IPayDTO;
import invengo.cn.pay.model.StockVO;
import invengo.cn.pay.model.WxPayDTO;
import invengo.cn.pay.model.entity.Order;
import invengo.cn.pay.model.entity.StockEx;
import invengo.cn.pay.service.IAlipayService;
import invengo.cn.pay.service.ICommonPayService;
import invengo.cn.pay.service.IPriceSearchService;
import invengo.cn.pay.service.IWxpayService;
import invengo.cn.pay.utils.StockUtil;
@Service
@Transactional
public class CommonPayServiceImpl implements ICommonPayService {
  @Autowired
  private IAlipayService alipayService;
  @Autowired
  private IWxpayService wxpayService;
  @Autowired
  private IPriceSearchService priceSearchService;
  @Autowired
  private OrderMapper orderMapper;
  
  @Override
  public DreamResponse multUnifiedOrder(IPayDTO dto) {
    DreamResponse revalue =  new DreamResponse();
    DreamResponse alresponse = alipayService.unifiedOrder(dto);
    DreamResponse wxresponse = wxpayService.unifiedOrder(dto);
    Map<String,Object> exmap = new HashMap<String,Object>();
    exmap.put("wx_url", wxresponse.getExtData().get("code_url"));
    exmap.put("wx_code", wxresponse.getExtData().get("out_trade_no"));
    exmap.put("wx_msg", wxresponse.getMsg());
    exmap.put("al_url", alresponse.getExtData().get("qrCode"));
    exmap.put("al_code", alresponse.getExtData().get("outTradeNo"));
    exmap.put("al_msg", alresponse.getMsg());
    exmap.put("totalAmt", wxresponse.getExtData().get("totalAmt"));//总金额
    exmap.put("totalDisAmt", wxresponse.getExtData().get("totalDisAmt"));//优惠金额
    revalue.setExtData(exmap);
    revalue.setData(wxresponse.getData());
    revalue.setStatus(wxresponse.getStatus());
    return revalue;
  }
  
  @Override
  public DreamResponse getGoods(IPayDTO dto) {
    CommonDTO real = (CommonDTO)dto;
    DreamResponse revalue = new DreamResponse();
    if(real.getEpcs()==null) {
      revalue.setMsg("标签列表不能为空！");
      revalue.setStatus(DreamStatus.FAIL);
      return revalue;
    }
    if(real.getMac()==null) {
      revalue.setMsg("设备地址不能为空！");
      revalue.setStatus(DreamStatus.FAIL);
      return revalue;
    }
    StockVO stockVo = priceSearchService.getSKUPrice(real);
    List<StockEx> listStock = stockVo.getAllListStock();
    List<GoodVO> listGood = StockUtil.filterItem(stockVo.getListStock());
    Map<String,Object> exmap = new HashMap<String,Object>();
    Map<String,Object> existEpc = new HashMap<String,Object>();
    List<String> erroListStock = new ArrayList<String>();
    for(StockEx stock:listStock){
        existEpc.put(stock.getEpc(), stock);
    }
    if(null==listStock||listStock.isEmpty()){
      erroListStock = real.getEpcs();
    }else{
      for(String epc:real.getEpcs()){
          if(existEpc.get(epc)==null){
            erroListStock.add(epc);
          }
      }
    }
    exmap.put("exepcs", erroListStock);
    exmap.put("totalAmt", stockVo.getTotalAmt()+"");
    exmap.put("totalDisAmt", stockVo.getTotalDisAmt()+"");
    revalue.setData(listGood);
    revalue.setTotal(listGood.size());
    revalue.setExtData(exmap);
    revalue.setStatus(DreamStatus.SUCCESS);
    return revalue;
  }
  
  @SuppressWarnings("rawtypes")
  @Override
  public DreamResponse orderQuery(String out_trade_no) {
    
    //构建查询条件,通过订单号获取支付方式进行对应的查询
    Order queryParam = new Order();
    queryParam.setOrderNo(out_trade_no);
    Order order = orderMapper.selectByParams(queryParam);
    DreamResponse res = new DreamResponse();
    res.setStatus(DreamStatus.FAIL);
    if(order == null) {
      res.setMsg("此交易订单号不存在!");
      return res;
    }
    
    String payType = order.getPaymentType();
    if (DreamStatus.AL.equals(payType)) { //支付宝订单状态查询
      AliPayDTO dto = new AliPayDTO();
      dto.setOut_trade_no(out_trade_no);
      return alipayService.orderQuery(dto);
    }
    if (DreamStatus.WX.equals(payType)) { //微信状态查询
      WxPayDTO dto = new WxPayDTO();
      dto.setOut_trade_no(out_trade_no);
      return wxpayService.orderQuery(dto);
    }
    res.setMsg("支付类型错误!");
    return res;
  }

}
