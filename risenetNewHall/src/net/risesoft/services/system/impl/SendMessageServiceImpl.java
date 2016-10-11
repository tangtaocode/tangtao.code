package net.risesoft.services.system.impl;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.risesoft.beans.system.InsertDownSmsResult;
import net.risesoft.beans.system.MessageConfig;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.system.ISendMessageService;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.ICodeMapUtil;
import net.risesoft.utils.base.StringX;

import org.apache.axis.client.Call;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Service;


@Service
public class SendMessageServiceImpl extends BaseDaoImpl<MessageConfig>
		implements ISendMessageService {
	private Logger log = Logger.getLogger(SendMessageServiceImpl.class);
	public static final String wsdl = "http://10.1.1.48/services/Sms?wsdl";
	public static final String userName="AjEDZl00Uj4=";//用户名
	public static final String password="Ai0DbF08UjdXYFAHU2hXNg==";//密码
	public static final String batch = "0514";
	
	
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Override
	public boolean sendMessage(String mobile, String messageCode,
			String[] messParams) {
		MessageConfig config = findBeanBypro("smsno", messageCode);
		if (config!=null&&"1".equals(config.getFlag())) {
			Map<String, String> dataSource = codeMapUtil
					.getMapByType("dataSource.properties");
			String sql = "insert into Message_Log(MOBUILE_ID,MESSAGEINFO,USERNAME,ISSUE_DATE,SEND_FLAG,NID) values (?,?,'审批系统',sysdate,'0','')";
			Connection conn = null;
			PreparedStatement pst = null;
			try {
				Class.forName(dataSource.get("ywbw.database.driver"))
						.newInstance();
				String count = getMessage(config.getSmscontent(), messParams);
				conn = DriverManager.getConnection(dataSource
						.get("ywbw.database.url"), dataSource
						.get("ywbw.database.user"), dataSource
						.get("ywbw.database.password"));
				pst = conn.prepareStatement(sql);
				pst.setString(1, mobile);
				
				
				pst.setString(2, count);
				int rows = pst.executeUpdate();
				if (rows > 0) {
					if(log.isDebugEnabled())log.debug("短信发送成功\n 内容："+count+"\n 接收手机号："+mobile+" \n ");
					sql = "insert into sms_detail (EMPLOYEE_GUID,SMS_GUID,MOBILE,SENDTIME,SMS,ORI_TEL) "
							+ "values ('{0A150154-0000-0000-1407-584B00000007}',?,"
							+ "? ,sysdate,? ,?)";
					execute(sql, new String[] { GUID.getGUID(), mobile, count,
							config.getOri_tel() });
					return true;
				} else {
					if(log.isDebugEnabled())log.debug("短信发送失败\n 内容："+count+"\n 接收手机号："+mobile+" \n ");
					sql = "insert into sms_false (EMPLOYEE_GUID,GUID,MOBILE,SENDTIME,SMS,ORI_TEL) "
							+ "values ('{0A150154-0000-0000-1407-584B00000007}',? ,"
							+ " ?,sysdate,?,?)";
					execute(sql, new String[] { GUID.getGUID(), mobile, count,
							config.getOri_tel() });
					return false;
				}
			} catch (Exception e) {
				if(log.isDebugEnabled())log.debug("短信发送程序异常："+e.getMessage());
				e.printStackTrace();
				return false;
			}finally{
				try {
					if(pst!=null)pst.close();
					if(conn!=null)conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}else{
			if(log.isDebugEnabled())log.debug("短信关闭状态，不发送");
			return false;
		}
	}

	/**
	 * 
	 * @Title: getMessage
	 * @Description: 根据短息配置内容和参数获取完整短信内容
	 * @param message
	 *            配置短信模板
	 * @param params
	 *            需要替换的短信内容参数
	 * @return String 返回类型
	 * @throws
	 */
	protected String getMessage(String message, String[] params) {
		int flag = params.length;
		for (int i = flag; i >= 1; i--) {
			message = message.replace(getFlage(i), params[i - 1]);
		}
		return message;
	}

	/**
	 * 
	 * @Title: getFlage
	 * @Description: 根据模板关键替换值长度获取替换标示符
	 * @param
	 * @param length
	 *            标示符长度
	 * @return String 返回类型
	 * @throws
	 */
	protected String getFlage(int length) {
		String param = "";
		for (int i = 0; i < length; i++) {
			param += "*";
		}
		return param;
	}

	@Override
	public String handlerSms(String mobile,String messageCode,
			String[] messParams) throws Exception{
		
		if(StringUtils.isBlank(mobile)){
			return "";
		}
		return sendSms(strToList(mobile),messageCode,messParams);
	}
	
	
	private String sendSms(List<String> mobileList,String messageCode,
			String[] messParams) throws Exception {
		String result ="";
		//验证手机号是否正确
		List<String> mobiles =  filterValidateMobile(mobileList);//有效的手机号码
		if(mobiles.size()==0){
			result  = "短信发送失败,缺少短信接收对象";
			return result;
		}
		String receiveGuids = "";
		for(String s:mobiles){
			receiveGuids+=s+",";
		}
		
		String content = "你的验证码：";
		MessageConfig config = findBeanBypro("smsno", messageCode);
		String count = getMessage(config.getSmscontent(), messParams);
		content +=count;
		
		receiveGuids = receiveGuids.substring(0,receiveGuids.length()-1);
		try{
			Call call = null;
			org.apache.axis.client.Service service = new org.apache.axis.client.Service();
			call = (Call) service.createCall();
			call.setOperationName("InsertDownSms");
			call.setTargetEndpointAddress(new java.net.URL(wsdl));
			List<String> params = addParams();
			String sendBody = getSendBody(mobiles,content);
			params.add(sendBody);
			String resultXml = (String)call.invoke(params.toArray());
			InsertDownSmsResult insertDownSmsResult = insertDownSmsXML2Bean(resultXml);
			if("0".equals(insertDownSmsResult.getCode()) && insertDownSmsResult.getMsgid().size()>0){//调用短信下发接口成功
				result = "短信发送成功！";
				log.info(result);
			}
		}catch(Exception e){
			e.printStackTrace();
			log.info(e.getMessage());
			result  = "短信发送失败"+e.getMessage();
		}
		return result;
	}
	
	
	public static List<String> addParams(){
		List<String> params = new ArrayList<String>();
		params.add(userName);//加入用户名
		params.add(password);//加入密码
		params.add(batch);
		
		return params;
	}
	
	private static List<String> strToList(String mobiles){
		String[] mobileArray = mobiles.split(",");
		List<String> mobileList = new ArrayList<String>();
		for(String s:mobileArray){
			mobileList.add(s);
		}
		
		
		return mobileList;
	}
	
	/**
	 * 过滤有效并剔除重复的手机号
	 * @param mobileList
	 * @return
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
	 */
	public static List<String> filterValidateMobile(List<String> mobileList){
		List<String>  mobiles = new ArrayList<String>();
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); 
		for(String mobile : mobileList ){
			Matcher m = p.matcher(mobile);  
			if(m.find()){//有效的手机号
				if(!mobiles.contains(mobile)){
					mobiles.add(mobile);//剔除重复的号码
				}				
			}
		}
		return mobiles;
	}
	
	/**
	 * wsdl 内容
	 * @param mobileList
	 * @param content
	 * @return
	 */
	public static String getSendBody(List<String> mobileList  ,String content){
		StringBuffer  mobiles = new StringBuffer();
		for(String mobile : mobileList){
			mobiles.append(mobile).append(",");
		}
		if(mobiles.length()>0){
			mobiles.deleteCharAt(mobiles.length()-1);
		}
		StringBuffer sendbody = new StringBuffer();//发送主体
		sendbody.append("<sendbody>");
		sendbody.append("<message>");
		sendbody.append("<orgaddr></orgaddr>");
		sendbody.append("<mobile>"+mobiles+"</mobile>");
		sendbody.append("<content>"+content+"</content>");
		sendbody.append("<sendtime></sendtime>");
		sendbody.append("<needreport>1</needreport>");//需要状态报告
		sendbody.append("</message>");
		sendbody.append("<publicContent></publicContent>");
		sendbody.append("</sendbody>");
		return sendbody.toString();
	}
	
	/**
	 * xml转换为javaBean
	 * @param xml
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static InsertDownSmsResult insertDownSmsXML2Bean(String xml){
		if(StringX.isBlank(xml)){
			return null;
		}
		InsertDownSmsResult insertDownSmsResult = new InsertDownSmsResult();
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try{						
			doc = builder.build(new StringReader(xml.toString()));
			Element root = doc.getRootElement();	
			Element head = root.getChild("head");
			Element code =  head.getChild("code");
			insertDownSmsResult.setCode(code.getTextTrim());
			Element body =  root.getChild("body");
			List<Element> it = body.getChildren("msgid");
			for(Element e : it){
				insertDownSmsResult.getMsgid().add(e.getTextTrim());
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
		return insertDownSmsResult;
	}
}
