package net.risesoft.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;






import org.apache.log4j.Logger;



/**
 * @ClassName: FilterSqlInjection.java
 * @Description: TODO
 *
 * @author liuhua
 * @date 2015年4月27日 上午10:55:26
 * @version 
 * @since JDK 1.6
 */
public class FilterSqlInjection implements Filter{	
	//private static org.apache.log4j.Logger log = net.risesoft.commons.log.LogFactory.getLog(FilterSqlInjection.class);
	private static final Logger logger = Logger.getLogger(FilterSqlInjection.class); 
	String nonFilterUrl[];
	//private static final 
	public void init(FilterConfig config)throws ServletException{
		//获取不过滤地址参数
		String strSkipUrl = config.getInitParameter("nonFilterUrl");
	    if (strSkipUrl != null) {
	    	nonFilterUrl = strSkipUrl.split(",");	      
	    }
		
		//nonFilterUrl=config.getInitParameter("nonFilterUrl").split(",");
	}
	public void doFilter(ServletRequest args0, ServletResponse args1,  
			            FilterChain chain) throws IOException, ServletException {
			        HttpServletRequest req=(HttpServletRequest)args0;  
			        HttpServletResponse res=(HttpServletResponse)args1;  
			        res.setHeader("Set-Cookie", "name=value; HttpOnly"); 
			        boolean isFilterUrl=true;			        
			        isFilterUrl=isFilterUrl(req);
			         //获得所有请求参数名  
			        Enumeration params = req.getParameterNames();  
			        String sql = "";  
			        List<String> paramList = new ArrayList<String>();
			        while (params.hasMoreElements()) {  
			            //得到参数名  
			            String name = params.nextElement().toString();  
			            //System.out.println("name===========================" + name + "--");  
			            //得到参数对应值  
			            String[] value = req.getParameterValues(name);  
			            for (int i = 0; i < value.length; i++) {  
			                //sql = sql + value[i]; 
			                paramList.add(value[i]);
			            }  
			        }  
			       
			        //System.out.println("============================SQL"+sql);
			        //有sql关键字，跳转到error.html  
			        if (isFilterUrl==true&&sqlValidate(paramList,req.getRequestURI())) {  
			        	String strIp = getIpAddr(req);
			        	System.out.println("=============非法ip: "+strIp);
			        	Throwable ex = null;
			        	if(logger.isDebugEnabled())
			        	{
			        		logger.debug("=============非法ip: ");
			        	}
			        	//logger.error("=============非法ip: ", ex);
			        	logger.error("非法ip："+strIp);
			        	logger.error("非法SQL:"+sql);
			        	res.sendRedirect("/");
			        	/*res.setContentType("text/html");
			        	res.setCharacterEncoding("utf-8");
			        	PrintWriter out = res.getWriter();
			        	out.print("<script language='javascript'>alert(\"您("+strIp+")提交的相关表单数据字符含有非法字符。\")"
			        			+ ";location.href('/');</script>");
			        	out.flush();
			        	out.close();*/
			        	
			            //throw new IOException("您发送请求中的参数中含有非法字符"); 
			        } else {  
			            chain.doFilter(args0,args1);  
			        }  
			    }  
	
			/**
			  * @MethodName: getIpAddr
			  * @Description: TODO (获取非法字符输入者ip)
			  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
			  * @param： (参数)
			  * @return String    返回类型
			  * @throws
			  * 
			  * @Author liuhua
			  * @date 2015-4-28  上午09:38:39
			  */
			public String getIpAddr(HttpServletRequest request) {  
				String ip = request.getHeader("x-forwarded-for");  
			    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			        ip = request.getRemoteAddr()+"Proxy:"+request.getHeader("Proxy-Client-IP"); 
			    }  
			    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			        ip = request.getRemoteAddr()+"WL:"+request.getHeader("WL-Proxy-Client-IP");  
			    }  
			    if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
			        ip = request.getRemoteAddr();  
			    }  
			      
			    return ip;  
			}   
			      
		      
		    /**
		      * @MethodName: sqlValidate
		      * @Description: TODO (//效验特殊字符)
		      * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		      * @param： (参数)
		      * @return boolean    返回类型
		      * @throws
		      * 
		      * @Author liuhua
		      * @date 2015-4-28  上午09:37:46
		      */
		    protected static boolean sqlValidate(List<String> list,String reqUri) {  
		        
		    	List<String> lowList = new ArrayList<String>();
		    	for(String s:list){
		    		lowList.add(s==null?null:s.toLowerCase());
		    	}
		        String badStr = "'|window|location|onclick|alert|http|href|src|script|and|exec|execute|insert|select|delete|update|count|drop|chr|mid|master|truncate|" +  
		                "char|declare|sitename|net user|xp_cmdshell|;|or|like'|and|exec|execute|insert|create|drop|" +  
		                "table|from|grant|use|group_concat|column_name|" +  
		                "information_schema.columns|table_schema|union|where|order|by|count|like";//过滤掉的sql关键字，可以手动添加  
		        String[] badStrs = badStr.split("\\|");  
		        for (int i = 0; i < badStrs.length; i++) {  
		            if (lowList.contains(badStrs[i])) {  
		            	logger.error("请求地址："+reqUri+" --非法关键字："+badStrs[i]);
		            	System.out.println("请求地址："+reqUri+" --非法关键字："+badStrs[i]);
		                return true;  
		            }  
		        }  
		        return false;  
		    }
		    
		    
		    /**
		      * @MethodName: isFilterUrl
		      * @Description: TODO (是否过滤的地址)
		      * @warning：TODO (部分含特殊字符的参数不进行过滤，但采用preareStatment方法过滤)
		      * @param： (参数)
		      * @return boolean    返回类型
		      * @throws
		      * 
		      * @Author liuhua
		      * @date 2015年4月27日  上午10:55:32
		      */
		    protected boolean isFilterUrl(HttpServletRequest req){	
		    	if(nonFilterUrl!=null)
		    	{
			    	for(int i=0 ;i<nonFilterUrl.length;i++)
			    	{
			    		//if (req.getRequestURI().indexOf("/common/down.do")>=0)
			    	//	System.out.println(req.getRequestURI());
			    	//	System.out.println(nonFilterUrl[i].toString());
			    		if (req.getRequestURI().indexOf(nonFilterUrl[i].toString())>=0)
				    	{
				    		return false;
				    	}			    	
			    	}
		    	}
		    	return true;
		    		
		    }
			public void destroy() {
				// TODO Auto-generated method stub
				this.nonFilterUrl = null;  
			}
		    
			
		 


}
