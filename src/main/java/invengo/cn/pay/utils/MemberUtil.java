package invengo.cn.pay.utils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import invengo.cn.pay.model.access.MemberAccess;

/**
 * 
 * @ClassName:  MemberUtil   
 * @Description:TODO(会员工具类)   
 * @author: tt1498
 * @date:   2017年9月16日 下午1:11:23
 */
public class MemberUtil
{
  public static ConcurrentMap<String, MemberAccess> concurrentMapTask = new ConcurrentHashMap<String, MemberAccess>();
  
  public static void add(MemberAccess member){
      if(member!=null&&member.getId()!=null){
        concurrentMapTask.put(member.getMerchantId(),member);
      }
  }
  public static void remove(MemberAccess member){
    if(member!=null&&member.getId()!=null){
      concurrentMapTask.remove(member.getMerchantId());
    }
  }
  public static MemberAccess get(String key){
      return concurrentMapTask.get(key);
  }
}
