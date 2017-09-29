package invengo.cn.pay.service;

import java.util.Map;

/**
 * @ClassName:  IOrderService   
 * @Description:TODO(订单处理)   
 * @author: tt1498
 * @date:   2017年9月8日 下午6:33:06
 */
public interface IOrderService
{
  /**
   * 
   * @Title: add   
   * @Description: TODO(订单数据添加)   
   * @param: @param map      
   * @return: void      
   * @throws
   */
  public void add(Map<String,Object> map);
  /**
   * 
   * @Title: add   
   * @Description: TODO(订单数据更新)   
   * @param: @param map      
   * @return: void      
   * @throws
   */
  public void update(Map<String,Object> map);
  
}
