<%@ page contentType="text/html; charset=GBK" %>
<%@ page import="net.risesoft.approve.util.ContextHolder"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="net.risesoft.commons.*,java.sql.*,net.risesoft.commons.database.Conn,java.io.*,
org.apache.axis.client.*,java.net.URL,org.apache.axis.encoding.XMLType,javax.xml.rpc.ParameterMode,
org.apache.commons.io.IOUtils"%>
<%

			String bwbh="�ס��[2013]65��";
			//���ز�������
			Service service = new Service();
			 String wsdlUrl = "http://localhost/services/WebServiceForSearch?wsdl";//��������URL 
	            URL url = new URL(wsdlUrl);//ͨ��URL��Ĺ��췽������wsdlUrl��ַ����URL���� 
	// 2.�������񷽷��ĵ����߶���call������call��������� 
	            Call call = (Call) service.createCall(); 
	            call.setTargetEndpointAddress(url);//��call�������������URL���� 
	            //String serviceName = "getFile"; 
	            String serviceName = "getWord"; 
	            call.setOperationName(serviceName);//��call�������õ��÷��������� 
	            call.addParameter("bwbh", XMLType.XSD_STRING, ParameterMode.IN);// ��call�������÷����Ĳ��������������͡�����ģʽ 
	            call.setReturnType(XMLType.XSD_STRING);// ���õ��÷����ķ���ֵ���� 
			//ִ�нӿڷ���
	            //String i = (String)call.invoke(new Object[] {bwbh}) ;
	            String i = (String)call.invoke(new Object[] {bwbh}) ;  
	            if(i!=null){
		            byte[] b = new sun.misc.BASE64Decoder().decodeBuffer(i);
		            response.setHeader("Content-Type", "application/x-download");
			String name="����.doc";
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