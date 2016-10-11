package net.risesoft.actions.user;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.user.SubUserInfo;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.risesoft.services.user.ISubUserLoginService;
import net.risesoft.services.user.IUserCommoneyService;
import net.risesoft.services.user.IUserLoginService;
import net.risesoft.util.CryptogramUtils;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;
/**
 * @登录
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Controller
@Results( { @Result(name = "message", location = "/WEB-INF/page/share/message.jsp")})
@ParentPackage("json-default")  //继承json包，用于返回json
public class UserLoginAction extends BaseActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1213627255363082450L;
	private String userName;
	private String password;
	private String checkCode;
	private String message;
	private String urlAddress;
	private String errorCode;
	@Resource
	private IUserLoginService userLoginService;
	@Resource 
	private ISubUserLoginService subUserLoginService;
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource
	private IUserCommoneyService userCommoneyService;
	@JSON
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JSON
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	@JSON
	public String getUrlAddress() {
		return urlAddress;
	}

	public void setUrlAddress(String urlAddress) {
		this.urlAddress = urlAddress;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCheckCode() {
		return checkCode;
	}
	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
	@Action(value = "/user/loginAction", results = { @Result(name = "success", location = "/WEB-INF/page/login/login.jsp") })
	public String goToLogin(){
		return SUCCESS;
	}
	@Action(value = "/user/doLogin" ,results={@Result(name="info",type="json")},params={"pageType","text/html"})
	public String loginAction() {
		try {
			String code = (String) getSession().getAttribute(Common.sessionCheckCode);
			if(!checkCode.toLowerCase().equals(code.toLowerCase())){
			//if(false){
				setMessage("验证码错误");
				setErrorCode("codeMes");
				return "info";
			}else{
				/**
				 * 新增子帐号登录--update by Jon
				 */
				SubUserInfo subUserInfo = subUserLoginService.getUserByLoginName(userName);
				
				if(null != subUserInfo){
					if(!CryptogramUtils.getInstance(CryptogramUtils.Algorithm_DESede).encrypt(password).equals(subUserInfo.getPassword())/*&&!getSHAStr(password).equals(user.getPassword())*/){
						setMessage("密码错误");
						setErrorCode("passwdMes");
						return "info";
					}else if(subUserInfo.getState()==1){
						setMessage("该用户已禁用");
						setErrorCode("stateMes");
						return "info";
					}else{
						getSession().setAttribute(Common.userMobile, subUserInfo.getMobile());
						getSession().setAttribute(Common.lxrName, subUserInfo.getTruename());
						getSession().setAttribute(Common.subFlag, "1:"+subUserInfo.getGuid());
						userName = userLoginService.getLoginNameById(subUserInfo.getUserId());
						UserInfo user = userLoginService.getUserByLoginName(userName);
						if(null == user){
							setMessage("用户名不存在");
							setErrorCode("nameMes");
							return "info";
						}else{
							user.setCompanyUser(userLoginService.getCompanyUser(user.getGuid()));
							user.setPersonUser(userLoginService.getPersonUser(user.getGuid()));
							if("2".equals(user.getUsertype())&&user.getCompanyUser()!=null){
								user.getCompanyUser().setUserCommoney(userCommoneyService.find(user.getCompanyUser().getCommoneyguid()));
							} 
							user.setTypeValue(codeMapUtil.getMapByType("大厅网-用户类型").get(user.getUsertype()));
							user.setCardValue(codeMapUtil.getMapByType("大厅网-证件类型").get(user.getCardtype()));
							getSession().setAttribute(Common.sessionLoginUserID, user);
							setMessage("success");
							String url = (String)getSession().getAttribute(Common.loginSuUrl);
							if(url!=null){
								setUrlAddress(url);
							}else{
								/*setUrlAddress("/businessfollow/initBusinessfollow.html");*/
								setUrlAddress("/onlineService/initOnlineService.html");
							}
							
							return "info";
						}
					}
				}else{
					UserInfo user = userLoginService.getUserByLoginName(userName);
					if(user==null){
						setMessage("用户名不存在");
						setErrorCode("nameMes");
						return "info";
					}else if(!CryptogramUtils.getInstance(CryptogramUtils.Algorithm_DESede).encrypt(password).equals(user.getPassword())/*&&!getSHAStr(password).equals(user.getPassword())*/){
						setMessage("密码错误");
						setErrorCode("passwdMes");
						return "info";
					}else if(user.getState()==1){
						setMessage("该用户已禁用");
						setErrorCode("stateMes");
						return "info";
					}else{
						getSession().setAttribute(Common.userMobile, user.getMobile());
						getSession().setAttribute(Common.subFlag, "0:"+user.getGuid());
						user.setCompanyUser(userLoginService.getCompanyUser(user.getGuid()));
						user.setPersonUser(userLoginService.getPersonUser(user.getGuid()));
						if("2".equals(user.getUsertype())&&user.getCompanyUser()!=null){
							user.getCompanyUser().setUserCommoney(userCommoneyService.find(user.getCompanyUser().getCommoneyguid()));
						} 
						user.setTypeValue(codeMapUtil.getMapByType("大厅网-用户类型").get(user.getUsertype()));
						user.setCardValue(codeMapUtil.getMapByType("大厅网-证件类型").get(user.getCardtype()));
						getSession().setAttribute(Common.sessionLoginUserID, user);
						setMessage("success");
						String url = (String)getSession().getAttribute(Common.loginSuUrl);
						if(url!=null){
							setUrlAddress(url);
						}else{
							/*setUrlAddress("/businessfollow/initBusinessfollow.html");*/
							setUrlAddress("/onlineService/initOnlineService.html");
						}
						
						return "info";
					}
					
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			setMessage("程序内部错误");
			setErrorCode("errorMes");
			return "info";
		}
		
	}
	@Action(value = "/user/logoutAction", results = {@Result(name = "success",location = "/onlineService/initOnlineService.html")})
	public String logoutAction(){
		super.clearSession();
		return SUCCESS;
	}
	@Action(value = "/user/checkIsLogin", results = { @Result(name = "success",type="json") })
	public String checkIsLogin(){
		if(getSession().getAttribute(Common.sessionLoginUserID)!=null){
			setMessage("1");
		}else{
			setMessage("0");
		}
		return SUCCESS;
	}
	
	private static String getSHAStr(String password) throws NoSuchAlgorithmException {
    	MessageDigest alga = MessageDigest.getInstance("SHA-1");
    	 alga.update(password.getBytes());
    	 byte[] digesta = alga.digest();
    	  StringBuffer hexValue = new StringBuffer();
    	         for (int i = 0; i < digesta.length; i++) {
    	             int val = ((int) digesta[i]) & 0xff;
    	             if (val < 16)
    	                 hexValue.append("0");
    	             hexValue.append(Integer.toHexString(val));
    	         }
    	         System.out.println(hexValue.toString());
    	return hexValue.toString().toUpperCase();
    }
}
