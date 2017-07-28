package net.risesoft.approve.service.impl.supervise;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.service.supervise.IGetNewSblshUtil;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class GetNewSblshUtil  implements IGetNewSblshUtil {	

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	} 
	private static org.apache.log4j.Logger log = Logger.getLogger(GetNewSblshUtil.class);
	
	/**
	 * 正式访问地址
	 */
	//因为正式的取号程序部署在10.161.109.72上，所以在该服务器上访问的地址是127.0.0.1，其余的访问地址是10.161.109.72:8087
//	private static String SBLSH_CREATE_URL = "http://10.161.109.72:8087/c/api.businessIndex/createSerialNum";
//	private static String SBLSH_DISCARD_URL = "http://10.161.109.72:8087/c/api.businessIndex/discard";

	/**
	 * 本地部署的访问地址
	 */
//	private static String SBLSH_DISCARD_URL = "http://127.0.0.1:8087/c/api.businessIndex/discard";
//	private static String SBLSH_CREATE_URL = "http://127.0.0.1:8087/c/api.businessIndex/createSerialNum";
	
	/**
	 * 测试访问地址
	 */
	private static String SBLSH_CREATE_URL = "http://10.169.3.5:8087/c/api.businessIndex/createSerialNum";
	private static String SBLSH_DISCARD_URL = "http://10.169.3.5:8087/c/api.businessIndex/discard";
	
//	private static String SBLSH_CREATE_PARAM = "access_token=b8GoVX6lS98sm7uddJ3cMraXV41SK/8j&sxbm=";
	private static String SBLSH_CREATE_PARAM = "access_token=I7/Sv0Gmo/1oCKxDvA3ZN8aCvL0LFoov&sxbm=";
	private static String SBLSH_DISCARD_PARAM = "access_token=I7/Sv0Gmo/1oCKxDvA3ZN8aCvL0LFoov&sblsh=&status=2&reason=&remark=&backup=";
	
	private static GetNewSblshUtil sblshUtil = null;
	
	public synchronized static GetNewSblshUtil getInstance(){
		if(null==sblshUtil){
			sblshUtil = new GetNewSblshUtil();
		}
		return sblshUtil;
	}
	
	public synchronized String getResult(String url, String param) {
		String content = "";
		HttpURLConnection connection = null;
		try {
			URL restURL = null;
			try {
				restURL = new URL(url);
			} catch (Exception me) {
				me.printStackTrace();
			}
			connection = (HttpURLConnection) restURL.openConnection();
			connection.setConnectTimeout(1000 * 100);// 100秒
			connection.setReadTimeout(1000 * 150);

			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			if ((param != null) && (param.length() > 1)) {
				connection.setRequestMethod("POST");
				connection.setDoOutput(true);
				connection.setUseCaches(false);// Post 请求不能使用缓存

				OutputStreamWriter outer = new OutputStreamWriter(connection
						.getOutputStream(), "UTF-8");
				outer.write(param);
				outer.flush();
				outer.close();

			}
			InputStream ips = connection.getInputStream();
			content = inputStreamToString(ips, "UTF-8");
			ips.close();
//			System.out.println("HTTP 响应消息状态码:" + connection.getResponseCode());
//			System.out.println("HTTP 响应消息:" + connection.getResponseMessage());
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("访问本地应用，获取流水号出错",e);
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return content;
	}

	public String inputStreamToString(InputStream is, String charSet)
			throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(is,
				charSet));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
			buffer.append("\r\n");
		}
		in.close();
		return buffer.toString();
	}
	
	/**
	 * 
	  * @MethodName: getNativeSblsh
	  * @Description: TODO 调用市接口获取流水号失败，则生成本地流水号
	  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
	  * @param： (参数)
	  * @return String    返回类型
	  * @throws
	  * 
	  * @Author cbn
	  * @date Jun 19, 2015  11:52:37 AM
	 */
	public String getNativeSblsh(String sxbm) {
		if(sxbm == null || sxbm.equals("")) {
			return "";
		}
		String sblsh = sxbm.substring(0,19);
		String sql = "select to_char(sysdate, 'yymmdd') from dual";
		String date = "";
		int result=0;
		try {
			date = jdbcTemplate.queryForObject(sql, String.class);
			sblsh +=date;
			sblsh +="Le";
			sql = "select t.serialnumber from spi_serialnumbers t where t.sxcode=? and t.ywrq=? ";
			int se = jdbcTemplate.queryForObject(sql,Integer.class, new Object[] {sxbm, date});
			if(se>0 && se<10) {
				sblsh =sblsh+"0"+se;
				sql = "update spi_serialnumbers t set t.serialnumber=? where t.sxcode=? and t.ywrq=? ";
				result = jdbcTemplate.update(sql, new Object[] {se+1,sxbm, date});
			}else if(se>=10 && se<100) {
				sblsh += se;
				sql = "update spi_serialnumbers t set t.serialnumber=? where t.sxcode=? and t.ywrq=? ";
				result = jdbcTemplate.update(sql, new Object[] {se+1,sxbm, date});
				System.out.println("");
			}else {
				sblsh +="01";
				sql = "insert into spi_serialnumbers values(?,?,01)";
				result = jdbcTemplate.update(sql, new Object[] {sxbm, date});
			}
			
			
		}catch(Exception e) {
			log.error("获取本地流水号日期出错", e);
			return "";
		}
		return sblsh;
	}
	
	/**
	 * 将返回结果转成 json ，然后根据情况判断返回信息
	 * @param result
	 * @return
	 */
	public String getSblsh(String result){
    	String data = "";
    	try {
    		JSONObject json=JSONObject.fromObject(result);
        	String state=json.getString("state");
    		if("1".equals(state)){//成功，取流水号
        		data=String.valueOf(json.getString("data"));
        		if(data.indexOf("{")==0){
        			JSONObject lshData = JSONObject.fromObject(data);
        			data=lshData.getString("sblsh");
        		}
        	}else{
        		data="error:"+String.valueOf(json.getString("message"));
        	}
		} catch (Exception e) {
			data = "error:获取申办流水号出错，请联系管理员";
			e.printStackTrace();
			log.error("获取申办流水号出错:",e);
		}
    	return data;
	}
	
	public String getResult(String result){
    	String data = "";
    	try {
    		System.out.println(result);
    		JSONObject json=JSONObject.fromObject(result);
        	String state=json.getString("state");
    		if("1".equals(state)){//作废成功
        		data=json.getString("message");
        	}else{
        		data="error:"+String.valueOf(json.getString("message"));
        	}
		} catch (Exception e) {
			data = "error:作废申办流水号异常，请联系管理员";
			e.printStackTrace();
			log.error("作废申办流水号出错:",e);
		}
    	return data;
	}
	
	public synchronized String getSblshByApproveItemGuid(String itemGuid){
		String sxbmStr = "";
		String sql = "select t.sxbm code,t.approveitemguid value from spm_approveitem t where t.approveitemguid=?";
		try {
			Map<String,Object> code = jdbcTemplate.queryForMap(sql, new String[] { itemGuid});
			String sxbm = "";
			if (code != null){//提取26位事项编码
				sxbm = code.get("code").toString();
			}
			if(sxbm!=null&&sxbm.length()==26){//确保事项编码正确
				sxbmStr=sxbm;
			}else{
				log.error("事项guid有问题,请检查");
				return "";
			}
			String sblsh = getSblshBySxbm(sxbmStr);
			return sblsh;
		}catch(Exception e) {
			log.error("根据事项guid:"+itemGuid+" 来获取申办流水号时出错:",e);
			e.printStackTrace();
			return "";
		}
	}
	
	
	public synchronized String getSblshBySxbm(String sxbm){
		//验证事项编码的正确性
		if(null==sxbm||"".equals(sxbm)){
			log.error("事项编码有问题，请检查");
			return "";//返回空值
		}
		String result = getSblsh(getResult(GetNewSblshUtil.SBLSH_CREATE_URL, GetNewSblshUtil.SBLSH_CREATE_PARAM+sxbm));
		if(result.indexOf("error")==-1){//获取流水号成功
			log.info("事项编码为 "+sxbm+" 的事项，成功获取申办流水号："+result);
		}else {//获取失败
			log.error("事项编码为 "+sxbm+" 的事项，获取申办流水号失败："+result);
			//调用本地代码生成规则的统一流水号
			result = getNativeSblsh(sxbm);
//			result = "";//返回空值
		}
		return result;
	}
	
	/**
	 * 作废 指定的申办流水号
	 * @param sblsh
	 * @return
	 */
	public synchronized String DiscardSblsh(String sblsh,String reason){
		if(null==sblsh||"".equals(sblsh)){
			return "申办流水号有问题，请检查";
		}
		if(null==reason||"".equals(reason)){
			reason = "测试数据";
		}
		String result = getResult(getResult(GetNewSblshUtil.SBLSH_DISCARD_URL, 
				GetNewSblshUtil.SBLSH_DISCARD_PARAM.replace("sblsh=", "sblsh="+sblsh).replace("reason=", "reason="+reason)));
		if(result.indexOf("error")==-1){
			log.info("申办流水号为 "+sblsh+" 执行作废操作："+result);
		}else {
			log.error("申办流水号为 "+sblsh+" 执行作废操作失败："+result);
		}
		return result;
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 1; i++) {
			System.out.println(GetNewSblshUtil.getInstance().getSblshBySxbm("20015500069395380714440310"));
//			System.out.println(GetNewSblsh.getInstance().DiscardSblsh("2001550006939538071150521P001", ""));;
		}
		
	}

}