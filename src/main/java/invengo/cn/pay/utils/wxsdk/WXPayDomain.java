package invengo.cn.pay.utils.wxsdk;

import invengo.cn.pay.config.WXconfig;

public class WXPayDomain implements IWXPayDomain {

  @Override
  public void report(String domain, long elapsedTimeMillis, Exception ex) {
    // TODO Auto-generated method stub
  }

  @Override
  public DomainInfo getDomain(WXPayConfig config) {
    WXconfig wxConfig = (WXconfig)config;
    return new DomainInfo(wxConfig.getDomain(), wxConfig.isPrimaryDomain());
  }

}
