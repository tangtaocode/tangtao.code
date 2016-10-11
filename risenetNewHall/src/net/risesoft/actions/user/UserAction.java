package net.risesoft.actions.user;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.user.CompanyUser;
import net.risesoft.beans.user.CompanyUserLog;
import net.risesoft.beans.user.PersonUser;
import net.risesoft.beans.user.PersonUserLog;
import net.risesoft.beans.user.SubUserInfo;
import net.risesoft.beans.user.UserCommoney;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.risesoft.services.user.ICompanyUserLogService;
import net.risesoft.services.user.ICompanyUserService;
import net.risesoft.services.user.IPersonUserLogService;
import net.risesoft.services.user.IPersonUserService;
import net.risesoft.services.user.ISubUserLoginService;
import net.risesoft.services.user.IUserCommoneyService;
import net.risesoft.services.user.IUserLoginService;
import net.risesoft.services.webservice.sShare.Request;
import net.risesoft.util.CryptogramUtils;
import net.risesoft.util.ValidatorUtil;
import net.risesoft.utils.annotation.LoginRequired;
import net.risesoft.utils.base.GUID;

@Controller
@ParentPackage("default")
//继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })
public class UserAction extends BaseActionSupport {

	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = -7248952503707824401L;
	private String loginname;
	private String phone;
	private String phoneCheckNum;
	private String message;
	private String url;
	private String method;
	private String guid;
	private String isEdit;
	private String newPassword;
	private UserInfo userInfo;
	private CompanyUser companyUser;
	private PersonUser personUser;
	private String appModel;
	@Resource
	private IUserLoginService userLoginService;
	@Resource 
	private ISubUserLoginService subUserLoginService;
	@Resource
	private ICompanyUserService companyUserService;
	@Resource
	private IPersonUserService personUserService;
	@Resource
	private IPersonUserLogService personUserLogService;
	@Resource
	private ICompanyUserLogService companyUserLogService;
	@Resource
	private IUserCommoneyService userCommoneyService;

	public String getAppModel() {
		return appModel;
	}

	public void setAppModel(String appModel) {
		this.appModel = appModel;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneCheckNum() {
		return phoneCheckNum;
	}

	public void setPhoneCheckNum(String phoneCheckNum) {
		this.phoneCheckNum = phoneCheckNum;
	}

	@JSON
	public String getMessage() {
		return message;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public CompanyUser getCompanyUser() {
		return companyUser;
	}

	public void setCompanyUser(CompanyUser companyUser) {
		this.companyUser = companyUser;
	}

	public PersonUser getPersonUser() {
		return personUser;
	}

	public void setPersonUser(PersonUser personUser) {
		this.personUser = personUser;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	/**
	 * @Title: findPasswordByUserName
	 * @Description: 找回密码
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/userService/findPassword", results = {
			@Result(name = "success", location = "/WEB-INF/page/share/message.jsp"),
			@Result(name = "input", location = "/WEB-INF/page/user/forgetPassword.jsp") })
	public String findPasswordByUserName() {
		try {
			if (StringUtils.isNotBlank(getMethod())) {
				if (!phoneCheckNum.trim().equals(Common.mobileCheckCode)) {
					setMessage("手机验证码错误，请重新获取!");
					setUrl("/userService/forgetPassword.html");
				} else {
					setMessage("您的密码是："
							+ CryptogramUtils.getInstance(
									CryptogramUtils.Algorithm_DESede).decrypt(
									userLoginService.findByProperty(
											"loginname", loginname).get(0)
											.getPassword()));
					getSession().removeAttribute(Common.mobileCheckCode);
				}
			} else {
				return INPUT;
			}
		} catch (Exception e) {
			setMessage("找回密码失败，错误：" + e.getMessage());
			setUrl("/userService/forgetPassword.html");
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * @Title: userRegister
	 * @Description: 用户注册，基本信息注册
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/userService/userRegister", results = { @Result(name = "success", location = "/WEB-INF/page/user/userRegister.jsp") })
	public String userRegister() {
		if (StringUtils.isBlank(getMethod())) {
			return SUCCESS;
		} else {
			try {
				UserInfo user = getUserInfo();
				user.setGuid(GUID.getGUID());
				user.setCreattime(new Date(System.currentTimeMillis()));
				user.setState(0);
				user.setPassword(CryptogramUtils.getInstance(
						CryptogramUtils.Algorithm_DESede).encrypt(
						user.getPassword()));
				
				
				
				/**
				 * 未获取到市级信息
				 * 用户注册先冻结
				 * update by Jon
				 * */
				//user.setState(1);
				userLoginService.save(user);
				try{//企业基本信息初始化，捕获了异常以防止初始化失败影响正常注册
					//Map map=new Request().GetYYXKZDataById(user.getCardid());
					//if(map.get("state").toString().equals("1")){
					if("1".equals(user.getUsertype())){
						PersonUser puser = new PersonUser();
						puser.setRegister_date(new Date());
						puser.setUser_guid(GUID.getGUID());
						puser.setLogonguid(user.getGuid());
						puser.setMobile(user.getMobile());
						puser.setIdcard_code(user.getCardid());
						puser.setIdcard_type(user.getCardtype());
						puser.setTrue_name(user.getComuser());
						personUserService.save(puser);
						
					}else{
						CompanyUser companyUser=new CompanyUser();
						SimpleDateFormat sdf=new SimpleDateFormat("yyy-MM-dd"); 
						companyUser.setGuid(GUID.getGUID());
						companyUser.setLogonguid(user.getGuid());
						companyUser.setName(user.getLoginname());
						companyUser.setRegtype(user.getUsertype());
						companyUser.setEname(user.getComuser());//单位名称
						companyUser.setRegcode(user.getCardid());
						companyUser.setOrgcode(user.getCardid());
						//System.out.println("-----------------------------------"+user.getComuser());
						
//						companyUser.setKind(ValidatorUtil.filter(map.get("EntTypeCode").toString()));//企业性质
//						companyUser.setOrgcode(ValidatorUtil.filter(map.get("EntOrgCode").toString()));//组织机构代码
//						companyUser.setRegcode(ValidatorUtil.filter(map.get("EntRegNO").toString()));//登记证号/注册号
//						companyUser.setRegdate(sdf.parse(ValidatorUtil.filter(map.get("EstDate").toString())));//成立时间
//						companyUser.setRegcode(ValidatorUtil.filter(map.get("EntRegNO").toString()));//登记证号/注册号
//						companyUser.setLawperson(ValidatorUtil.filter(map.get("LeRepName").toString()));//法定代表人 (负责人）
//						companyUser.setAddress(ValidatorUtil.filter(map.get("Addr").toString()));//注册地址
//						companyUser.setAdministrative(ValidatorUtil.filter(map.get("DistCode").toString()));//所在行政区
//						companyUser.setOpenadd(ValidatorUtil.filter(map.get("BizAddr").toString()));//营业地址
//						companyUser.setLimit(ValidatorUtil.filter(map.get("CBuItem").toString())); //经营范围
						companyUserService.save(companyUser);
					}
						
						//t_out_companyuser
					//}//
				}catch(Exception e){
					e.printStackTrace();
				}
				getSession().removeAttribute(Common.mobileCheckCode);
				outJson("{'message':'1','url':'/user/loginAction.html'}", null);
				return null;
			} catch (Exception e) {
				outJson("{'message':'" + e.getMessage()
						+ "','url':'/userService/userRegister.html'}", null);
				e.printStackTrace();
				return null;
			}
		}

	}

	/**
	 * 
	 * @Title: addPersonUserInfo
	 * @Description: 用户注册，个人基础信息增加
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/userService/editUserInfo", results = {
			@Result(name = "peditApp", location = "/WEB-INF/page/user/approveitm/editPersonal.jsp"),
			@Result(name = "ceditApp", location = "/WEB-INF/page/user/approveitm/editCompanyInfo.jsp"),
			@Result(name = "oeditApp", location = "/WEB-INF/page/user/approveitm/editOrganizational.jsp"),
			@Result(name = "pviewApp", location = "/WEB-INF/page/user/approveitm/viewPersonal.jsp"),
			@Result(name = "cviewApp", location = "/WEB-INF/page/user/approveitm/viewCompanyInfo.jsp"),
			@Result(name = "oviewApp", location = "/WEB-INF/page/user/approveitm/viewOrganizational.jsp"),
			@Result(name = "peditBiz", location = "/WEB-INF/page/user/bizbankroll/editPersonal.jsp"),
			@Result(name = "ceditBiz", location = "/WEB-INF/page/user/bizbankroll/editCompanyInfo.jsp"),
			@Result(name = "oeditBiz", location = "/WEB-INF/page/user/bizbankroll/editOrganizational.jsp"),
			@Result(name = "pviewBiz", location = "/WEB-INF/page/user/bizbankroll/viewPersonal.jsp"),
			@Result(name = "cviewBiz", location = "/WEB-INF/page/user/bizbankroll/viewCompanyInfo.jsp"),
			@Result(name = "oviewBiz", location = "/WEB-INF/page/user/bizbankroll/viewOrganizational.jsp"),
			@Result(name = "editCivi", location = "/WEB-INF/page/user/civilbankroll/editOrganizational.jsp"),
			@Result(name = "viewCivi", location = "/WEB-INF/page/user/civilbankroll/viewOrganizational.jsp") })
	public String addPersonUserInfo() {
		try {
			UserInfo base = getUser();
			if (StringUtils.isBlank(getMethod())) {
				if ("1".equals(base.getUsertype())) {
					PersonUser puser = personUserService.findBeanBypro(
							"logonguid", getUserGUID());
					if (puser == null) {
						puser = new PersonUser();
						puser.setLogonguid(base.getGuid());
						puser.setMobile(base.getMobile());
						puser.setIdcard_code(base.getCardid());
						puser.setIdcard_type(base.getCardtype());
					}
					setPersonUser(puser);
				} else {
					CompanyUser cUser = companyUserService.findBeanBypro(
							"logonguid", getUserGUID());
					CompanyUser cu=userLoginService.getOrgInfo("000");
					if (cUser == null) {
						cUser = new CompanyUser();
						cUser.setLogonguid(base.getGuid());
						cUser.setContactmobile(base.getMobile());
						cUser.setOrgcode(base.getCardid());
						cUser.setRegtype(base.getUsertype());
						cUser.setName(base.getLoginname());
						//cUser.setEname(cu.getName());
					} else {
						if ("bizbankroll".equals(getAppModel())
								&& "2".equals(getUserType())) {
							cUser.setUserCommoney(userCommoneyService
									.find(cUser.getCommoneyguid()));
						}
					}

					setCompanyUser(cUser);
				}
			} else {
				if ("1".equals(base.getUsertype())) {
					PersonUser user = getPersonUser();
					if (StringUtils.isBlank(user.getUser_guid())) {
						user.setRegister_date(new Date());
						user.setUser_guid(GUID.getGUID());
					}
					PersonUserLog userLog = new PersonUserLog();
					BeanUtils.copyProperties(userLog, user);
					userLog.setLogguid(GUID.getGUID());
					userLog.setLogdate(new Date());
					personUserLogService.save(userLog);
					personUserService.saveOrUpdate(user);
					base.setMobile(user.getMobile());
					base.setPersonUser(user);
				} else {
					CompanyUser user = getCompanyUser();
					if (StringUtils.isBlank(user.getGuid())) {
						user.setGuid(GUID.getGUID());
						user.setRegister_date(new Date());
						if ("bizbankroll".equals(getAppModel())
								&& "2".equals(getUserType())) {
							UserCommoney commoney = user.getUserCommoney();
							commoney.setGuid(GUID.getGUID());
							commoney.setCreatedate(new Date());
							commoney.setIsold(1);
							userCommoneyService.save(commoney);
							user.setCommoneyguid(commoney.getGuid());
						}
					} else if ("bizbankroll".equals(getAppModel())
							&& "2".equals(getUserType())) {
						UserCommoney commoney1 = user.getUserCommoney();
						UserCommoney commoney2 = userCommoneyService.find(user
								.getCommoneyguid());
						int sjbbh=commoney1.getIsold()==null?0:commoney1.getIsold();
						if (!commoney1.equals(commoney2)) {
							UserCommoney commoney3 = new UserCommoney();
							commoney3.setGuid(GUID.getGUID());
							commoney3.setCreatedate(new Date());
							commoney3.setCyrs(commoney1.getCyrs());
							commoney3.setIsold(sjbbh+1);
							commoney3.setNsze(commoney1.getNsze());
							commoney3.setYysr(commoney1.getYysr());
							userCommoneyService.save(commoney3);
							user.setCommoneyguid(commoney3.getGuid());
						}
					}

					CompanyUserLog userLog = new CompanyUserLog();
					BeanUtils.copyProperties(userLog, user);
					userLog.setLogguid(GUID.getGUID());
					userLog.setLogdate(new Date());
					companyUserLogService.save(userLog);
					companyUserService.saveOrUpdate(user);
					base.setMobile(user.getContactmobile());
					base.setCompanyUser(user);
				}
				userLoginService.update(base);
				getSession().removeAttribute(Common.sessionLoginUserID);
				getSession().setAttribute(Common.sessionLoginUserID, base);
			}

			if ("approveItem".equals(getAppModel())) {
				if (StringUtils.isBlank(getMethod()))
					if ("1".equals(base.getUsertype()))
						return "peditApp";
					else if ("2".equals(base.getUsertype()) || "7".equals(base.getUsertype()))
						return "ceditApp";
					else
						return "oeditApp";
				else if ("1".equals(base.getUsertype()))
					return "pviewApp";
				else if ("2".equals(base.getUsertype()) || "7".equals(base.getUsertype()))
					return "cviewApp";
				else
					return "oviewApp";
			} else if ("bizbankroll".equals(getAppModel())) {
				if (StringUtils.isBlank(getMethod()))
					if ("1".equals(base.getUsertype()))
						return "peditBiz";
					else if ("2".equals(base.getUsertype()) || "7".equals(base.getUsertype()))
						return "ceditBiz";
					else
						return "oeditBiz";
				else if ("1".equals(base.getUsertype()))
					return "pviewBiz";
				else if ("2".equals(base.getUsertype()) || "7".equals(base.getUsertype()))
					return "cviewBiz";
				else
					return "oviewBiz";
			} else if ("civilbankroll".equals(getAppModel())) {
				if (StringUtils.isBlank(getMethod()))
					return "editCivi";
				else
					return "viewCivi";
			} else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			outJson("{'message':'0'}", null);
			return null;
		}
	}

	/**
	 * 
	 * @Title: editPassword
	 * @Description: 修改密码
	 * @param
	 * @return 设定文件
	 * @return String 返回类型
	 * @throws
	 */
	@Action(value = "/userService/editPassword", results = {
			@Result(name = "userRegister", location = "/WEB-INF/page/user/editPassword.jsp"),
			@Result(name = "success", type = "json") })
	@LoginRequired(module = "approveItem")
	public String editPassword() {
		try {
			if (StringUtils.isNotBlank(getMethod())) {
				/**
				 * 修改用户登录密码
				 * update by Jon
				 */
				String subFlag = (String)getSession().getAttribute(Common.subFlag);
				if(StringUtils.isNotBlank(subFlag)){
					String[] params = subFlag.split(":");
					if(StringUtils.equals(params[0],"1")){
						SubUserInfo subUserInfo = subUserLoginService.find(params[1]);
						subUserInfo.setPassword(CryptogramUtils.getInstance(
								CryptogramUtils.Algorithm_DESede).encrypt(
								getNewPassword()));
						subUserLoginService.update(subUserInfo);
					}else if(StringUtils.equals(params[0],"0")){
						UserInfo user = userLoginService.find(getGuid());
						user.setPassword(CryptogramUtils.getInstance(
								CryptogramUtils.Algorithm_DESede).encrypt(
								getNewPassword()));
						userLoginService.update(user);
					}
				}
				setMessage("修改成功！");
				return SUCCESS;
			} else {
				setGuid(getUserGUID());
				return "userRegister";
			}
		} catch (Exception e) {
			e.printStackTrace();
			setMessage("修改失败！");
			setUrl("/userService/editPassword/guid/" + getGuid() + ".html");
			return SUCCESS;
		}
	}

	@Action(value = "/userService/viewUserByType", results = {
			@Result(name = "pviewApp", location = "/WEB-INF/page/user/approveitm/viewPersonal.jsp"),
			@Result(name = "cviewApp", location = "/WEB-INF/page/user/approveitm/viewCompanyInfo.jsp"),
			@Result(name = "oviewApp", location = "/WEB-INF/page/user/approveitm/viewOrganizational.jsp"),
			@Result(name = "pviewBiz", location = "/WEB-INF/page/user/bizbankroll/viewPersonal.jsp"),
			@Result(name = "cviewBiz", location = "/WEB-INF/page/user/bizbankroll/viewCompanyInfo.jsp"),
			@Result(name = "oviewBiz", location = "/WEB-INF/page/user/bizbankroll/viewOrganizational.jsp"),
			@Result(name = "viewCivi", location = "/WEB-INF/page/user/civilbankroll/viewOrganizational.jsp") })
	public String viewUserByType() {
		if ("approveItem".equals(getAppModel())) {
			if ("1".equals(getUserType()))
				return "pviewApp";
			else if ("2".equals(getUserType()))
				return "cviewApp";
			else
				return "oviewApp";
		} else if ("bizbankroll".equals(getAppModel())) {
			if ("1".equals(getUserType()))
				return "pviewBiz";
			else if ("2".equals(getUserType()))
				return "cviewBiz";
			else
				return "oviewBiz";
		} else if ("civilbankroll".equals(getAppModel())) {
			return "viewCivi";
		} else
			return null;
	}
}
