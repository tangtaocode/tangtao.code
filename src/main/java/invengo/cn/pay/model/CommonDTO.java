package invengo.cn.pay.model;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @ClassName:  CommonDTO   
 * @Description:TODO(公共传输实体)   
 * @author: tt1498
 * @date:   2017年9月12日 上午8:26:24
 */
@SuppressWarnings("serial")
public class CommonDTO implements IPayDTO,Serializable
{
  private List<String> epcs;//扫到epc集合
  
  private String mac;//Android端设备地址
  
  private String type;//WX AL//选择支付下单传输支付单类型
  
  private Long userId; //当前门店价格查询系统id
  
  private String memberId;//如有会员传输会员id
  
  public List<String> getEpcs()
  {
    return epcs;
  }
  public void setEpcs(List<String> epcs)
  {
    this.epcs = epcs;
  }
  public String getMac()
  {
    return mac;
  }
  public void setMac(String mac)
  {
    this.mac = mac;
  }
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public Long getUserId()
  {
    return userId;
  }
  public void setUserId(Long userId)
  {
    this.userId = userId;
  }
  public String getMemberId()
  {
    return memberId;
  }
  public void setMemberId(String memberId)
  {
    this.memberId = memberId;
  }
}
