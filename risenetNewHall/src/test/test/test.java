/*
 * Created on 2005-7-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test.test;

import java.net.URL;

import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class test {
	
	public static void main(String[] args) {
		try {
			String bwbh="深建住保[2013]65号";
			//返回参数类型
			Service service = new Service();
			 // String wsdlUrl = "http://localhost/services/WebServiceForSearch?wsdl";//请求服务的URL 
//			 String wsdlUrl = "http://192.168.0.72/services/WebServiceForSearch?wsdl";
			 String wsdlUrl = "http://192.168.53.89:7001/services/WebServiceForSearch?wsdl";
	            URL url = new URL(wsdlUrl);//通过URL类的构造方法传入wsdlUrl地址创建URL对象 
	// 2.创建服务方法的调用者对象call，设置call对象的属性 
	            Call call = (Call) service.createCall(); 
	            call.setTargetEndpointAddress(url);//给call对象设置请求的URL属性 
	            //String serviceName = "getFile"; // 文件标题、申请单位、办结时间（|分割，注意顺序），用null判断是否存在
				
	            String serviceName = "getWord";  // 只有内网用
	            call.setOperationName(serviceName);//给call对象设置调用方法名属性 
				// 办文编号 参数
	            call.addParameter("bwbh", XMLType.XSD_STRING, ParameterMode.IN);// 给call对象设置方法的参数名、参数类型、参数模式 
	            call.setReturnType(XMLType.XSD_STRING);// 设置调用方法的返回值类型 
			//执行接口方法
	            //String i = (String)call.invoke(new Object[] {bwbh}) ;
	            String i = (String)call.invoke(new Object[] {bwbh}) ;  
	            System.out.println("test.main()-->" + i);
	            if(i!=null){
		            byte[] b = new sun.misc.BASE64Decoder().decodeBuffer(i);
	            }

	            

		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}

