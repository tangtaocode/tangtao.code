package invengo.cn.pay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import invengo.cn.pay.model.CommonDTO;
import invengo.cn.pay.model.DreamResponse;
import invengo.cn.pay.model.DreamStatus;
import invengo.cn.pay.model.WxPayDTO;
import invengo.cn.pay.model.access.MemberAccess;
import invengo.cn.pay.model.ali.NotifyResponse;
import invengo.cn.pay.service.IAccessMemberService;
import invengo.cn.pay.service.IAlipayService;
import invengo.cn.pay.service.ICommonPayService;
import invengo.cn.pay.service.IWxpayService;

/**
 * 
 * @ClassName:  PayController   
 * @Description:TODO(支付控制类)   
 * @author: tt1498
 * @date:   2017年9月8日 下午2:33:39
 */
@Controller
@RequestMapping("/pay")
@SuppressWarnings("rawtypes")
public class PayController
{
  @Autowired
  private IWxpayService wxpayService;
  @Autowired
  private IAlipayService alipayService;
  @Autowired
  private ICommonPayService commonPayService;
  @Autowired
  private IAccessMemberService  accessMemberService;
  /**
   * 
   * @Title: unifiedOrder   
   * @Description: TODO(分类下单接口)   
   * @param: @param dto
   * @return: DreamResponse      
   * @throws
   */
  @RequestMapping("/unifiedOrder")
  @ResponseBody
  public DreamResponse unifiedOrder(CommonDTO dto){
    if(DreamStatus.AL.equals(dto.getType())){
      return alipayService.unifiedOrder(dto);
    }else{
      return wxpayService.unifiedOrder(dto);
    }
  }
  /**
   * 
   * @Title: unifiedOrder   
   * @Description: TODO(分类下单接口)   
   * @param: @param dto
   * @return: DreamResponse      
   * @throws
   */
  @RequestMapping("/multUnifiedOrder")
  @ResponseBody
  public DreamResponse multUnifiedOrder(CommonDTO dto){
    return commonPayService.multUnifiedOrder(dto);
  }
  /**
   * 
   * @Title: wxNotify   
   * @Description: TODO(微信回调地址)   
   * @param: @param dto
   * @return: String      
   * @throws
   */
  @RequestMapping(value="/wxNotify")
  @ResponseBody
  public String wxNotify(WxPayDTO wxdto){
    return wxpayService.notify(wxdto);
  }
  /**
   * 
   * @Title: alNotify   
   * @Description: TODO(支付宝回调地址)   
   * @param: @param dto
   * @return: String      
   * @throws
   */
  @RequestMapping("/alNotify")
  @ResponseBody
  public String alNotify(NotifyResponse dto){
    return alipayService.notify(dto);
  }
  /**
   * 
   * @Title: gateway   
   * @Description: TODO(支付宝网关)   
   * @param: @param dto
   * @return: DreamResponse      
   * @throws
   */
  @RequestMapping("/gateway")
  @ResponseBody
  public void gateway(){
     alipayService.getWay();
  }
  /**
   * 
   * @Title: getGoods   
   * @Description: TODO(获取商品相关信息)   
   * @param: @param dto
   * @return: DreamResponse      
   * @throws
   */
  @RequestMapping("/getGoods")
  @ResponseBody
  public DreamResponse getGoods(CommonDTO dto){
    return commonPayService.getGoods(dto);
  }
  /**
   * 
   * @Title: accessMember   
   * @Description: TODO(扫码支付导入会员信息)   
   * @param: @param dto
   * @return: DreamResponse      
   * @throws
   */
  @RequestMapping("/accessMember")
  @ResponseBody
  public DreamResponse accessMember(@RequestBody MemberAccess dto){
    return accessMemberService.setMember(dto);
  }
 
  /**   
   * @Title: orderQuery   
   * @Description: TODO(支付订单状态查询)   
   * @param: @param out_trade_no
   * @param: @return      
   * @return: DreamResponse      
   * @throws   
   */ 
  @RequestMapping("/orderQuery")
  @ResponseBody
  public DreamResponse orderQuery(String out_trade_no) {
    return commonPayService.orderQuery(out_trade_no);
  }
}
