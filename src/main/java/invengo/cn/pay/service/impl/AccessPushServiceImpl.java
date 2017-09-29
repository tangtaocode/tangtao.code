package invengo.cn.pay.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import invengo.cn.pay.config.AccessConfig;
import invengo.cn.pay.mapper.EquipmentMapper;
import invengo.cn.pay.mapper.OrderItemExMapper;
import invengo.cn.pay.mapper.OrderMapper;
import invengo.cn.pay.model.CacheObject;
import invengo.cn.pay.model.CommonDTO;
import invengo.cn.pay.model.DreamStatus;
import invengo.cn.pay.model.StockVO;
import invengo.cn.pay.model.access.GoodDetailAccess;
import invengo.cn.pay.model.access.OrderFlowAccess;
import invengo.cn.pay.model.access.SkuAccess;
import invengo.cn.pay.model.access.WxOrderFlowAccess;
import invengo.cn.pay.model.entity.Equipment;
import invengo.cn.pay.model.entity.Order;
import invengo.cn.pay.model.entity.OrderItem;
import invengo.cn.pay.model.entity.StockEx;
import invengo.cn.pay.service.IAccessPushService;
import invengo.cn.pay.service.IPriceSearchService;
import invengo.cn.pay.utils.BeanUtil;
import invengo.cn.pay.utils.DateUtils;
import invengo.cn.pay.utils.HttpClientUtils;
import invengo.cn.pay.utils.YZTRequestUtils;
import invengo.cn.pay.utils.wxsdk.WXPayUtil;
@Service
@Transactional
public class AccessPushServiceImpl implements IAccessPushService
{
  private static Logger logger = Logger.getLogger(AccessPushServiceImpl.class);
  @Autowired
  public OrderItemExMapper orderItemExMapper;
  @Autowired
  private IPriceSearchService priceSearchService;
  @Autowired
  private AccessConfig accessConfig;
  @Autowired
  private OrderMapper orderMapper;
  @Autowired
  private EquipmentMapper equipmentMapper;
  @Override
  public void pushOrder(CacheObject pushobj)
  {
    StockVO stockvo = new StockVO();
    OrderItem item = new OrderItem();
    item.setOrderNo(pushobj.getOut_trade_no());
    List<OrderItem> listItem = orderItemExMapper.loadByOrderNo(item);
    Order order = new Order();
    order.setOrderNo(pushobj.getOut_trade_no());
    order = orderMapper.selectByParams(order);
    List<String> epclist = new ArrayList<String>();
    for(OrderItem oitem:listItem){
      epclist.add(oitem.getEpc());
    }
    CommonDTO dto = new CommonDTO();
    dto.setEpcs(epclist);
    dto.setMac(pushobj.getDevice_info());
    dto.setUserId(pushobj.getUserId());
    stockvo=priceSearchService.getSKUPrice(dto);
    Equipment equipment = new Equipment();
    equipment.setWarehouseId(stockvo.getListStock().get(0).getWarehouseId()+"");
    equipment = equipmentMapper.selectByParams(equipment);
    //生成订单
    OrderFlowAccess orderFlowAccess = new OrderFlowAccess();
    orderFlowAccess.setFlowNo(pushobj.getOut_trade_no());
    orderFlowAccess.setMerchantId(stockvo.getListStock().get(0).getWarehouseId()+"");
    orderFlowAccess.setSource("2");//RFID
    orderFlowAccess.setUserId(pushobj.getUserId()+"");
    orderFlowAccess.setTerminalId(equipment.getId()+"");
    orderFlowAccess.setThirdPartOrderId(order.getId()+"");
    orderFlowAccess.setUniqueCode(WXPayUtil.generateUUID());
    orderFlowAccess.setPayStatus(DreamStatus.SUCCESS.equals(order.getStatus())?"1":"0");
    String type = DreamStatus.AL.equals(order.getPaymentType())?"3":"2";
    orderFlowAccess.setPayType(type);
    orderFlowAccess.setPayAmount(String.valueOf(order.getAmount().doubleValue()));
    List<SkuAccess> skus = new ArrayList<SkuAccess>();
    SkuAccess sku = null;
    for(StockEx stock:stockvo.getListStock()){
        sku = new SkuAccess();
        sku.setMpBarcode(stock.getBarcode());
        sku.setNum(stock.getQty());
        skus.add(sku);
    }
    orderFlowAccess.setSkus(new Gson().toJson(skus));
    try
    {
      Map<String,String> param = BeanUtil.objectToMapStr(orderFlowAccess);
      param = YZTRequestUtils.createParamsHelperMap(param, accessConfig);
      String paramjson = new Gson().toJson(param);
      logger.info("************推送订单流水入参************"+paramjson);
      String rejson = HttpClientUtils.doBodyPost(accessConfig.getAccessApiOrderFlow(), param, "UTF-8", HttpClientUtils.CONTENT_TYPE_FORM);
      Order record = new Order();
      record.setOrderNo(pushobj.getOut_trade_no());
      record.setAttr01("Y");
      orderMapper.updateByUniqueSelective(record);
      logger.info("************推送订单流水结果************"+rejson);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  @Override
  public void pushWxOrder(CacheObject pushobj) {
      StockVO stockvo = new StockVO();
      OrderItem item = new OrderItem();
      item.setOrderNo(pushobj.getOut_trade_no());
      List<OrderItem> listItem = orderItemExMapper.loadByOrderNo(item);
      List<String> epclist = new ArrayList<String>();
      for(OrderItem oitem:listItem){
        epclist.add(oitem.getEpc());
      }
      CommonDTO dto = new CommonDTO();
      dto.setEpcs(epclist);
      dto.setMac(pushobj.getDevice_info());
      dto.setUserId(pushobj.getUserId());
      stockvo=priceSearchService.getSKUPrice(dto);
      //生成微信小票
      WxOrderFlowAccess wxorderFlowAccess = new WxOrderFlowAccess();
      wxorderFlowAccess.setMerchantId(pushobj.getMember().getMerchantId());
      wxorderFlowAccess.setOrderNo(pushobj.getOut_trade_no());
      wxorderFlowAccess.setPayAmount(stockvo.getTotalAmt()+"");
      wxorderFlowAccess.setQtyTotal(listItem.size()+"");
      wxorderFlowAccess.setMobile(pushobj.getMember().getMobile());//当前会员里面拿
      wxorderFlowAccess.setDiscountAmount(stockvo.getTotalDisAmt()+"");
      wxorderFlowAccess.setTotalAmount(stockvo.getTotalAmt()+"");
      //微信小票机号
      Equipment equipment = new Equipment();
      equipment.setWarehouseId(stockvo.getListStock().get(0).getWarehouseId()+"");
      equipment = equipmentMapper.selectByParams(equipment);
      Long eId = equipment.getId();
      String posNo = eId<10?String.format("0%s", eId):String.valueOf(eId);
      wxorderFlowAccess.setPosNo(posNo);
      
      Order parm = new Order();
      parm.setOrderNo(pushobj.getOut_trade_no());
      parm = orderMapper.selectByParams(parm);
      wxorderFlowAccess.setPayTime(DateUtils.date2String(parm.getCreateDate(), "yyyy-MM-dd HH:mm:ss"));
      String type = DreamStatus.AL.equals(parm.getPaymentType())?"支付宝":"微信";
      wxorderFlowAccess.setPayType(type);
      List<GoodDetailAccess> products = new ArrayList<GoodDetailAccess>();
      GoodDetailAccess good = null;
      for(StockEx stock:stockvo.getListStock()){
          good = new GoodDetailAccess();
          good.setFname(stock.getName());
          good.setPrice(stock.getPrice01().doubleValue());
          good.setQty(stock.getQty());
          good.setTotal(stock.getPrice01().doubleValue()*stock.getQty());
          products.add(good);
      }
      wxorderFlowAccess.setData(products);
      String paramjson = new Gson().toJson(wxorderFlowAccess);
      logger.info("************推送微信小票入参************"+paramjson);
      String rejson = HttpClientUtils.doPost(accessConfig.getAccessApiWeixinBill(), paramjson, "UTF-8");
      logger.info("************推送微信小票结果************"+rejson);
    }

  
}
