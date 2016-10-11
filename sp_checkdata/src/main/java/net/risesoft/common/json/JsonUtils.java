package net.risesoft.common.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Json输出工具
 * @author HJL
 *
 */
public class JsonUtils
{
	/**或许日志实例**/
	public Log getLog() {
		return LogFactory.getLog(this.getClass());
	}
	
	/**
	 * 输出json字符串
	 * @param str 目标字符串
	 * @param respose
	 */
	public void outJsonString(String str,HttpServletResponse respose) {
		outString(str,respose);
	}
	
	/**
	 * json配置
	 * @param pattern
	 * @return
	 */
	private JsonConfig jsoncfg(String pattern){//yyyy-MM-dd HH:mm:ss
		JsonConfig cfg = new JsonConfig();
		cfg.setIgnoreDefaultExcludes(false);
		cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		cfg.registerJsonValueProcessor(Date.class,new DateJsonValueProcessor(pattern)); //date processor register
		return cfg;
	}
	
	/**
	 * 输出json对象
	 * @param obj 目标对象
	 * @param pattern
	 * @param respose HttpServletResponse
	 */
	public void outJson(Object obj,String pattern,HttpServletResponse respose) {
		getLog().info(JSONObject.fromObject(obj,jsoncfg(pattern)).toString());
		outJsonString(JSONObject.fromObject(obj,jsoncfg(pattern)).toString(),respose);
	}
	
	/**
	 * 输出json数组
	 * @param array 目标数组
	 * @param pattern
	 * @param respose HttpServletResponse
	 */
	public void outJsonArray(Object array,String pattern,HttpServletResponse respose) {
		getLog().info(JSONArray.fromObject(array,jsoncfg(pattern)).toString());
		outString(JSONArray.fromObject(array,jsoncfg(pattern)).toString(),respose);
	}
	
	/**
	 * 输出json字符串
	 * @param str 目标字符串
	 * @param respose
	 */
	public void outString(String str,HttpServletResponse respose) {
		try {
			respose.setContentType("application/json;charset=UTF-8");
			PrintWriter out = respose.getWriter();
			getLog().info(str);
			out.write(str);
			// 清空缓存
			out.flush();
			// 关闭流
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * list转化成json
	 * @param str
	 */
	public static String jsonString(Map map){
		return JSONObject.fromObject(map).toString();
	}

}
