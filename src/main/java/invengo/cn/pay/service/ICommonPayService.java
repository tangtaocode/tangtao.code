package invengo.cn.pay.service;

import invengo.cn.pay.model.DreamResponse;
import invengo.cn.pay.model.IPayDTO;

public interface ICommonPayService
{
  /**
   * 
   * @Title: multUnifiedOrder   
   * @Description: TODO(多种方式同时下单)   
   * @param: @param dto
   * @param: @return      
   * @return: DreamResponse      
   * @throws
   */
   public DreamResponse multUnifiedOrder(IPayDTO dto);
   /**
    * 
    * @Title: getGoods   
    * @Description: TODO(获取每款商品信息)   
    * @param: @param dto
    * @param: @return      
    * @return: DreamResponse      
    * @throws
    */
   public DreamResponse getGoods(IPayDTO dto);
   
   /**   
   * @Title: orderQuery   
   * @Description: TODO(支付订单状态查询)   
   * @param: @param out_trade_no
   * @param: @return      
   * @return: DreamResponse      
   * @throws   
   */ 
  DreamResponse orderQuery(String out_trade_no);
}
