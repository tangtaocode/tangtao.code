package net.risesoft.approve.risefile.commons;

import java.io.File;
import java.net.URL;




/**
 * TODO:根据所在工程，获得log源
 * <p>debug < info < warn < error < fatal</p>
 * @author wangdong
 * @version $Revision$
 */

public class LogFactory {


	static{
		try{
			String basePath =null;
			URL url=LogFactory.class.getClassLoader().getResource("/");
			if(url==null){
				url=LogFactory.class.getClassLoader().getResource(".");
			}
			//TODO:RiseNetLog.properties
			basePath = url.getPath();
			int i = basePath.indexOf("WEB-INF");
			if (i != -1) {
				basePath = basePath.substring(0, i+7);
			}
			
			
			if(System.getProperty("net.risesoft.approve.risefile.commons.RiseNet.logFile")==null){
				System.setProperty("net.risesoft.approve.risefile.commons.RiseNet.logFile", basePath+File.separator+"logs"
																				+File.separator+"RiseNet.log");
			}
			//拼装log
			String propertyPath=basePath+File.separator+"classes"+File.separator+"net"
									+File.separator+"risesoft"+File.separator+"approve"
									+File.separator+"risefile"+File.separator+"config"
									+File.separator+"risefilelog.properties";
			org.apache.log4j.PropertyConfigurator.configure(propertyPath);
		}catch(Throwable e){
			e.printStackTrace();
		}
	}


  private LogFactory() {
  }

  public static org.apache.log4j.Logger getLog(Class c) {
    return org.apache.log4j.Logger.getLogger(c);
  }

  public static org.apache.log4j.Logger getLog(String s) {
    return org.apache.log4j.Logger.getLogger(s);
  }
  
  public static void main(String[] args){
	  LogFactory.getLog("dd");
  }

 
}

