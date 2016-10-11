package net.risesoft.dwr;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.risesoft.beans.user.SubUserInfo;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.SimpleJdbcDaoImpl;
import net.risesoft.services.system.ISendMessageService;
import net.risesoft.services.user.ISubUserLoginService;
import net.risesoft.services.user.IUserLoginService;
import net.risesoft.util.CryptogramUtils;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
/**
 * 
  * @ClassName: UserDwrJs
  * @Description: 用户注册使用DWR，采用Annotation (注解)
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:11:03 PM 
  *
 */
@RemoteProxy(creator = SpringCreator.class, name = "UserDwrJs")
public class UserDwrJs {
	private final Logger log = Logger.getLogger(SimpleJdbcDaoImpl.class);
	@Resource 
	private IUserLoginService userLoginService;
	@Resource
	private ISubUserLoginService subUserLoginService;
	@Resource
	private ISendMessageService sendMessageService;
	@RemoteMethod
	public boolean sendmobileMessage(String mobile,HttpServletRequest request){
		try {
			String randNum = RandomStringUtils.random(6, false, true);
			request.getSession().setAttribute(Common.mobileCheckCode, randNum);
			return sendMessageService.sendMessage(mobile, "REGISTERCODE", new String[]{randNum});
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	@RemoteMethod
	public String sendWsdlMobileMessage(String mobile,HttpServletRequest request){
		try{
			String randNum = RandomStringUtils.random(6, false, true);
			request.getSession().setAttribute(Common.mobileCheckCode, randNum);
			 return sendMessageService.handlerSms(mobile,"REGISTERCODE",new String[]{randNum});
		}catch(Exception e){
			e.printStackTrace();
			return "调用接口出错!";
		}
	}
	@RemoteMethod
	public boolean findUserByLoginName(String userName){
		try {
			int count =0;
			List<UserInfo> user = userLoginService.findByProperty("loginname", userName);
			List<SubUserInfo> subUser = subUserLoginService.findByProperty("username", userName);
			if(user!=null&&user.size()>0){
				count++;
			}
			if(null != subUser && subUser.size()>0){
				count++;
			}
			
			if(count>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(log.isDebugEnabled())log.error(e.getMessage());
			return false;
		}
	}
	@RemoteMethod
	public boolean checkMobileCheckNum(String checkNum,HttpServletRequest request){
		if(request.getSession().getAttribute(Common.mobileCheckCode).equals(checkNum)){
			return true;
		}else{
			return false;
		}
	}
	@RemoteMethod
	public boolean checkOldPassword(String paaword,String guid){
		try {
			if(paaword.equals(CryptogramUtils.getInstance(
									CryptogramUtils.Algorithm_DESede).decrypt(userLoginService.find(guid).getPassword()))){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(log.isDebugEnabled())log.error(e.getMessage());
			return false;
		}
	}
	@RemoteMethod
	public boolean checkCardNum(String cardid,String cardType){
		try {
			if(!"3".equals(cardType)){
				List<UserInfo> user = userLoginService.findByProperty("cardid", cardid);
				if(user!=null&&user.size()>0){
					return true;
				}else{
					return false;
				}
			}else{
				//组织机构代码已"-"分割
				/*String[] cards = cardid.split("-");
				//如果等于1 则住址机构代码没有带-
				if(cards.length==1){
					//没有带-的组织机构代码查找
					List<UserInfo> user = userLoginService.findByProperty("cardid", cardid);
					//大于0则存在
					if(user!=null&&user.size()>0){
						return true;
					}else{
						//不存在时在末尾加上-查找，
						user = userLoginService.findByProperty("cardid", cardid.subSequence(0, cardid.length()-1)+"-"+cardid.subSequence(cardid.length()-1,cardid.length()));
						//大于0存在
						if(user!=null&&user.size()>0){
							return true;
						}else{
							//不存在
							return false;
						}
					}
				}else{
					//组织机构代码带有-查找
					List<UserInfo> user = userLoginService.findByProperty("cardid", cardid);
					//大于0则存在
					if(user!=null&&user.size()>0){
						return true;
					}else{
						//去掉-查找，
						user = userLoginService.findByProperty("cardid", cardid.replace("-", ""));
						//大于0存在
						if(user!=null&&user.size()>0){
							return true;
						}else{
							//不存在
							return false;
						}
					}
				}*/
				//modify 2015-05-29,这里不对组织机构进行重复判断
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(log.isDebugEnabled())log.error(e.getMessage());
			return false;
		}
	}
}
