package invengo.cn.pay.service;

import invengo.cn.pay.model.CommonDTO;
import invengo.cn.pay.model.StockVO;

public interface IPriceSearchService
{
  /**
   * 
   * @Title: getTotalAmount   
   * @Description: TODO(根据epc获取总金额)   
   * @param: @param epcs
   * @param: @return      
   * @return: Double      
   * @throws
   */
  public Double getTotalAmount(CommonDTO dto);
  /**
   * 
   * @Title: getSKUPrice   
   * @Description: TODO(获取每款价格数据量)   
   * @param: @param epcs
   * @param: @return      
   * @return: List<StockEx>      
   * @throws
   */
  public StockVO getSKUPrice(CommonDTO dto);
}
