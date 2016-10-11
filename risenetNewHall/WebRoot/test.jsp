<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="net.risesoft.approve.util.ContextHolder"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="net.risesoft.commons.*,java.sql.*,net.risesoft.commons.database.Conn,java.io.*,
org.apache.axis.client.*,java.net.URL,org.apache.axis.encoding.XMLType,javax.xml.rpc.ParameterMode,
org.apache.commons.io.IOUtils"%>
<%

			String bwbh="深建住保[2013]65号";
			//返回参数类型
			Service service = new Service();
			 String wsdlUrl = "http://localhost/services/WebServiceForSearch?wsdl";//请求服务的URL 
	            URL url = new URL(wsdlUrl);//通过URL类的构造方法传入wsdlUrl地址创建URL对象 
	// 2.创建服务方法的调用者对象call，设置call对象的属性 
	            Call call = (Call) service.createCall(); 
	            call.setTargetEndpointAddress(url);//给call对象设置请求的URL属性 
	            //String serviceName = "getFile"; 
	            String serviceName = "getWord"; 
	            call.setOperationName(serviceName);//给call对象设置调用方法名属性 
	            call.addParameter("bwbh", XMLType.XSD_STRING, ParameterMode.IN);// 给call对象设置方法的参数名、参数类型、参数模式 
	            call.setReturnType(XMLType.XSD_STRING);// 设置调用方法的返回值类型 
			//执行接口方法
	            //String i = (String)call.invoke(new Object[] {bwbh}) ;
	            String i = (String)call.invoke(new Object[] {bwbh}) ;  
	            if(i!=null){
		            byte[] b = new sun.misc.BASE64Decoder().decodeBuffer(i);
		            response.setHeader("Content-Type", "application/x-download");
			String name="正文.doc";
			String filenamedisplay = java.net.URLEncoder.encode(name,"UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="+filenamedisplay);
		    response.setContentType("application/vnd.ms-word"); 
		    InputStream is= new ByteArrayInputStream(b); 
		    OutputStream os = response.getOutputStream();
			IOUtils.copy(is,os);
			is.close();
			os.flush();
			os.close();
	            }
%>