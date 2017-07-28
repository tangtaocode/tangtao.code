package net.risesoft.util;

import java.util.ArrayList;
import java.util.List;




import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang3.StringUtils;

public class TmessageAction {
	//测试用WSDL服务器，正式使用需要替换
	public final String wsdl = "http://183.56.159.138:8090/services/Sms?wsdl";
	public final String userName = "AjkDbl09UjI=";// 用户名
	public final String password = "AkUDNl10UndXFFBnUz5XZFw7";// 密码
	
	
	
	/**
	 * 发送短信
	 * 
	 * @MethodName: sendSms
	 * @Description: TODO (这里用一句话描述这个方法的作用)
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数) mobileList号码集合,content短信内容
	 * @return String 返回类型
	 * @throws
	 * 
	 * @Author joe
	 */
	public String sendSms(List<String> mobileList, String content)
			throws Exception {

		String result = "SUCCESS";
		// if(isOpen()){
		List<String> mobiles = filterValidateMobile(mobileList);// 有效的手机号码
		if (mobiles.size() == 0) {
			result = "ERROR";
			return result;
		}
		int a = 0;
		for (String s : mobiles) {
			a++;
		}
		try {
			Call call = null;
			Service service = new Service();
			call = (Call) service.createCall();
			call.setOperationName("InsertDownSms");
			call.setTargetEndpointAddress(new java.net.URL(wsdl));
			List<String> params = addParams();
			String sendBody = getSendBody(mobiles, content);
			params.add(sendBody);
			String resultXml = (String) call.invoke(params.toArray());
		} catch (Exception e) {
			e.printStackTrace();
			result = "ERROR";
		}
		// }
		return result;
	}
	
	public List<String> filterValidateMobile(List<String> mobileList) {
		List<String> mobiles = new ArrayList<String>();
		// Pattern p = Pattern
		// .compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
		for (String mobile : mobileList) {
			// 针对一个人多个手机号
			if (StringUtils.isBlank(mobile))
				continue;
			if (mobile.length() > 11) {
				String[] sms = mobile.split(",");
				for (String s : sms) {
					// Matcher m = p.matcher(s);
					if (StringUtils.isNotBlank(s) && s.length() == 11) {// 有效的手机号
						if (!mobiles.contains(s)) {
							mobiles.add(s);// 剔除重复的号码
						}
					}
				}

			} else {
				// Matcher m = p.matcher(mobile);
				if (StringUtils.isNotBlank(mobile) && mobile.length() == 11) {// 有效的手机号
					if (!mobiles.contains(mobile)) {
						mobiles.add(mobile);// 剔除重复的号码
					}
				}
			}
		}
		return mobiles;
	}
	
	/**
	 * 接口传递参数
	 * 
	 * @MethodName: addParams
	 * @Description: TODO 接口传递参数
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数)
	 * @return List<String> 返回类型
	 * @throws
	 * 
	 * @Author joe
	 */
	public List<String> addParams() {
		List<String> params = new ArrayList<String>();
		params.add(userName);// 加入用户名
		params.add(password);// 加入密码
		String batch = " ";
		params.add(batch);

		return params;
	}
	
	/**
	 * 接口发送的内容
	 * 
	 * @MethodName: getSendBody
	 * @Description: 接口发送的内容
	 * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	 * @param： (参数) mobile 手机号，content 发送的内容
	 * @return String 返回类型
	 * @throws
	 * 
	 * @Author joe
	 */
	public String getSendBody(List<String> mobileList, String content) {
		StringBuffer mobiles = new StringBuffer();
		for (String mobile : mobileList) {
			mobiles.append(mobile).append(",");
		}
		if (mobiles.length() > 0) {
			mobiles.deleteCharAt(mobiles.length() - 1);
		}
		StringBuffer sendbody = new StringBuffer();// 发送主体
		sendbody.append("<sendbody>");
		sendbody.append("<message>");
		sendbody.append("<orgaddr></orgaddr>");
		sendbody.append("<mobile>" + mobiles + "</mobile>");
		sendbody.append("<content>" + content + "</content>");
		sendbody.append("<sendtime></sendtime>");
		sendbody.append("<needreport>1</needreport>");// 需要状态报告
		sendbody.append("</message>");
		sendbody.append("<publicContent></publicContent>");
		sendbody.append("</sendbody>");
		return sendbody.toString();
	}
	
	public static void main(String args[]){
		TmessageAction ta = new TmessageAction();
		List<String> list = new ArrayList<String>();
		try {
			ta.sendSms(list,"你好啊！");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
