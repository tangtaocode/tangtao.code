package net.risesoft.lhsms.webservice;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.risesoft.common.util.Guid;
import net.risesoft.service.ISmsService;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Component;
/**
 * 
 * @author liujun
 * 短信发送工具类
 */
@Component
public class SmsSenderUtil {
	@Resource
	private ISmsService iSmsService;
	private static Logger log = Logger.getLogger(SmsSenderUtil.class);
	//public final String wsdl = "http://192.168.1.3/services/Sms?wsdl";
	//61.142.175.86:10087短信公司 测试IP
	//public final String wsdl = "http://61.142.175.86:10087/services/Sms?wsdl";
	public final String wsdl = "http://192.168.1.3/services/Sms?wsdl";
	//public final String userName = "Ajo=";// 用户名   接口老地址：http://203.91.37.24:8080
	public final String userName = "AjsDbg==";// 默认抬头深圳水务局
	public final String password = "AmYDH11MUjRXZFBkUztXFVwuB3IFKVIp";// 密码
	private String userGuid="{00000000-0000-0000-0000-000000000000}";
	public  final String batch = "0514";
	private static SmsSenderUtil smsSenderUtil;
	
	public static SmsSenderUtil getInstance(){
		if(null==smsSenderUtil){
			smsSenderUtil= new SmsSenderUtil();
		}
		return smsSenderUtil;
	}
	
	/**
	 * 过滤有效并剔除重复的手机号
	 * @param mobileList
	 * @return
	 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 联通：130、131、132、152、155、156、185、186
     * 电信：133、153、180、189、（1349卫通）
	 */
	public  List<String> filterValidateMobile(List<String> mobileList){
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
	
	

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List<String> mobileList = new ArrayList<String>();
		//mobileList.add("15848195025");
		mobileList.add("13632678082");
		try {
			SmsSenderUtil.getInstance().sendSms(mobileList,"龙岗区住房和建设局业务中心提醒您：");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("{12323-4354-435345-435},".substring(0,"{12323-4354-435345-435},".length()-1));
	}
	
	private  List<String> strToList(String mobiles){
		String[] mobileArray = mobiles.split(",");
		List<String> mobileList = new ArrayList<String>();
		for(String s:mobileArray){
			mobileList.add(s);
		}
		return mobileList;
	}
	
	
	public  String sendSms(String mobiles,String content) throws Exception{
		//mobile="15813875837";
		//mobiles="";
		if(StringUtils.isBlank(mobiles)){
			return "";
		}
		return sendSms(strToList(mobiles) ,content);
	}
	public  String sendSms(List<String> mobileList ,String content){
		String result ="";
		List<String> mobiles =  filterValidateMobile(mobileList);//有效的手机号码
		if(mobiles.size()==0){
			result  = "短信发送失败,缺少短信接收对象";
			return result;
		}
		String receiveGuids = "";
		for(String s:mobiles){
			receiveGuids+=s+",";
		}
		receiveGuids = receiveGuids.substring(0,receiveGuids.length()-1);
		//Connection conn = null;
		try{
			
			Call call = null;
			Service service = new Service();
			call = (Call) service.createCall();
			call.setOperationName("InsertDownSms");
			call.setTargetEndpointAddress(new java.net.URL(wsdl));
			List<String> params = addParams();
			String sendBody = getSendBody(mobiles,content);
			params.add(sendBody);
			String resultXml = (String)call.invoke(params.toArray());
			InsertDownSmsResult insertDownSmsResult = insertDownSmsXML2Bean(resultXml);
			String sendId = Guid.genGuid();
			if("0".equals(insertDownSmsResult.getCode()) && insertDownSmsResult.getMsgid().size()>0){//调用短信下发接口成功
				SmsSender smsSender = new SmsSender();
				smsSender.setGuid(sendId);
				smsSender.setContent(content);
				smsSender.setSendbody(sendBody);
				smsSender.setSendTime(new Date());
				smsSender.setSendState(0);
				String msgids = insertDownSmsResult.getMsgids();
				smsSender.setMsgid(msgids);
				smsSender.setResultXml(resultXml);
				smsSender.setSendUserId("00001");
			//	smsSender.setSendUserId(sender.getUserGUID());
				smsSender.setReceiveGuid(receiveGuids);
				smsSender.setBatch(batch);
				//iSmsService.createInsertSms("SMS_SENDER", PropertyUtils.describe(smsSender));
				for(String msg : insertDownSmsResult.getMsgid()){
					SmsReceive smsReceive = new SmsReceive();
					smsReceive.setGuid(Guid.genGuid());
					smsReceive.setMsgid(msg);
					smsReceive.setReceiveTime(new Date());
					smsReceive.setReceiveState(0);
					//iSmsService.createInsertSms("SMS_RECEIVE", PropertyUtils.describe(smsReceive));				
				}
				result = "您发送了" + insertDownSmsResult.getMsgid().size() + "条短信，全部发送成功！\n发送号码："+mobiles;
				log.info(result);
				
			}else{//短信发送失败
				SmsSender smsSender = new SmsSender();
				smsSender.setGuid(sendId);
				smsSender.setContent(content);
				smsSender.setSendbody(sendBody);
				smsSender.setSendTime(new Date());
				smsSender.setSendState(-1);
				String msgids = insertDownSmsResult.getMsgids();
				smsSender.setMsgid(msgids);
				smsSender.setResultXml(resultXml);
				smsSender.setSendUserId("00001");
				//smsSender.setSendUserId(sender.getUserGUID());
				smsSender.setReceiveGuid(receiveGuids);
				smsSender.setBatch(batch);
				//iSmsService.createInsertSms("SMS_SENDER", PropertyUtils.describe(smsSender));
				result  = "短信发送失败";
				log.info(result);
			}
		}catch (Exception e) {
			e.printStackTrace();
			log.info(e.getMessage());
			result  = "短信发送失败"+e.getMessage();
		}finally{
			//conn.close();
		}
		return result;
	}
	/*public static void updateSmsStatus(){
		try {
			
			String msgid = getMsgid(insertDownSmsResult.getMsgid());
			call = (Call) service.createCall();
			call.setOperationName("getSpecialDownSmsResult");
			call.setTargetEndpointAddress(new java.net.URL(wsdl));
			String result = (String)call.invoke(new Object[]{userName,password,"",msgid});
			SpecialDownSmsResult specialDownSmsResult = insertSpecialDownSmsResult2Bean(result);
			for(Smsresult smsresult : specialDownSmsResult.getSmsresults()){
				SmsReceive sms = new SmsReceive();
				sms.setId("");
			}
			
		} catch (Exception e) {
		}
	}*/
	public String getMsgid(List<String> msgid){
		StringBuffer msgids = new StringBuffer();
		for(String m : msgid){
			msgids.append(m.split(",")[1]).append(",");
		}
		if(msgids.length()>0){
			msgids.deleteCharAt(msgids.length()-1);
		}
		return msgids.toString();
	}
	
	@SuppressWarnings("unchecked")
	public  InsertDownSmsResult insertDownSmsXML2Bean(String xml){
		if(StringUtils.isBlank(xml)){
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
	
	public  SpecialDownSmsResult insertSpecialDownSmsResult2Bean(String xml){
		if(StringUtils.isBlank(xml)){
			return null;
		}
		SpecialDownSmsResult specialDownSmsResult = new SpecialDownSmsResult();
		Document doc = null;
		SAXBuilder builder = new SAXBuilder();
		try{						
			doc = builder.build(new StringReader(xml.toString()));
			Element root = doc.getRootElement();	
			Element head = root.getChild("head");
			Element code =  head.getChild("code");
			Element message =  head.getChild("message");
			specialDownSmsResult.setCode(code.getTextTrim());
			specialDownSmsResult.setMessage(message.getTextTrim());
			Element body =  root.getChild("body");
			List<Element> it = body.getChildren("smsresult");
			Smsresult smsresult = null;
			for(Element e : it){
				smsresult = new Smsresult();
				Element msgid = e.getChild("msgid");
				Element status = e.getChild("status");
				Element msgstatus = e.getChild("msgstatus");
				Element resultmsg = e.getChild("resultmsg");
				Element senttime = e.getChild("senttime");
				Element reserve = e.getChild("reserve");
				smsresult.setMsgid(msgid.getTextTrim());
				smsresult.setStatus(status.getTextTrim());
				smsresult.setMsgstatus(msgstatus.getTextTrim());
				smsresult.setResultmsg(resultmsg.getTextTrim());
				smsresult.setSenttime(senttime.getTextTrim());
				smsresult.setReserve(reserve.getTextTrim());
				specialDownSmsResult.getSmsresults().add(smsresult);				
			}
		}catch (Exception e) {
			e.printStackTrace();
		}finally{
			
		}
		
		return specialDownSmsResult;
	}
	
	public  List<String> addParams(){
		List<String> params = new ArrayList<String>();
		params.add(userName);//加入用户名
		params.add(password);//加入密码
		params.add(batch);
		
		return params;
	}
	public  String getSendBody(List<String> mobileList  ,String content){
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
	
	
}
