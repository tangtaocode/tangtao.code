package net.risesoft.services.webservice.jShare.jyxt;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import javax.activation.DataHandler;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.soap.Constants;
import org.apache.soap.Envelope;
import org.apache.soap.SOAPException;
import org.apache.soap.messaging.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 
 * 交易系统接口相关处理
 * @author hy
 *
 */

public class JyxtDeal {
	
	private static String m_url = "http://192.168.53.37/conmis_new_webservice_test/service.asmx";
	private static String m_soapUri = "";
	private static Message m_strMsg_ = new Message ();
	private static Envelope m_envelope = new Envelope ();
	private static DataHandler m_strReturnMsg = null;
			 
	
	/**
	 * 检查项目经理或者总监是否能够任职
	 * @param empID 人员身份证号
	 * @param xmbh 项目编号
	 * @param isBig 是否重大项目
	 * @param gcbh 工程编号
	 * @return
	 */
	public static boolean checkIsCanRZ(String empID,String xmbh,String isBig,String gcbh) throws SOAPException{
		boolean b = false;
		ByteArrayInputStream inputStream = null;		
		if (m_url == null) 
	    {
	      throw new SOAPException (Constants.FAULT_CODE_CLIENT,"A URL not specified");
	    }
		try{
			m_soapUri = "http://222.35.143.130/CheckIsCanRZ";
		    String msgSink = "<CheckIsCanRZ xmlns=\"http://222.35.143.130/\">"+
			"<empID>"+empID+"</empID>"+
			"<xmbh>"+xmbh+"</xmbh>"+
			"<isBig>"+isBig+"</isBig>"+
			"<gcbh>"+gcbh+"</gcbh>"+
			"</CheckIsCanRZ>"; 
		    MsgBody ourBody = new MsgBody (msgSink);
		    m_envelope.setBody (ourBody);   
		    m_strMsg_.send (new URL (m_url), m_soapUri, m_envelope);
		    m_strReturnMsg = m_strMsg_.receive();
		    String strReturn=m_strReturnMsg.getContent().toString();
		    /*对XML文档进行解析，并封装放到bean里面*/
			//建立一个解析器工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//获得一个具体的解析器对象
			DocumentBuilder db = factory.newDocumentBuilder();			
			//把String类型字符串转为输入流
			inputStream = new ByteArrayInputStream(strReturn.getBytes("UTF-8"));
			//对XML文档进行解析，获得Document对象
			Document doc = db.parse(inputStream);
			doc.normalize();
	
			//获取所有的nodes元素列表
			NodeList nodes = doc.getElementsByTagName("CheckIsCanRZResult");
			if(nodes!=null && nodes.getLength()>0){
				Element node = (Element) nodes.item(0);
				//返回：String		--非空消息：不允许任职；空消息：允许任职
				if(node.getFirstChild()!=null && node.getFirstChild().getNodeValue()!=null){
					b=false;
					System.out.println("返回结果==>"+node.getFirstChild().getNodeValue());
				}else
					b=true;
			}

		    System.out.println(strReturn);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{			
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	
	/**
	 * 获取工程人员任职情况 人员任职情况XML字符串，为空表示无任职情
	 * @param condition 条件组合(GCBH = '{0}' AND EMP_ID = '{1}')
	 * @return
	 * @throws SOAPException
	 */
	public static boolean getRYRZQK_XML(String gcbh,String emp_id) throws SOAPException{
		boolean b = false;
		ByteArrayInputStream inputStream = null;		
		if (m_url == null) 
	    {
	      throw new SOAPException (Constants.FAULT_CODE_CLIENT,"A URL not specified");
	    }
		try{
			m_soapUri = "http://222.35.143.130/GetRYRZQK_XML";
		    String msgSink = "<GetRYRZQK_XML xmlns=\"http://222.35.143.130/\">"+
			"<condition>(GCBH = '"+gcbh+"' AND EMP_ID = '"+emp_id+"')</condition>"+
			"</GetRYRZQK_XML>";
		    MsgBody ourBody = new MsgBody (msgSink);
		    m_envelope.setBody (ourBody);   
		    m_strMsg_.send (new URL (m_url), m_soapUri, m_envelope);
		    m_strReturnMsg = m_strMsg_.receive();
		    String strReturn=m_strReturnMsg.getContent().toString();		    			
		    
		    /*对XML文档进行解析，并封装放到bean里面*/
			//建立一个解析器工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//获得一个具体的解析器对象
			DocumentBuilder db = factory.newDocumentBuilder();			
			//把String类型字符串转为输入流
			inputStream = new ByteArrayInputStream(strReturn.getBytes("UTF-8"));
			//对XML文档进行解析，获得Document对象
			Document doc = db.parse(inputStream);
			doc.normalize();
	
			//获取所有的nodes元素列表
			NodeList nodes = doc.getElementsByTagName("GetRYRZQK_XMLResult");
			if(nodes!=null && nodes.getLength()>0){
				Element node = (Element) nodes.item(0);
				//返回：String		--非空消息：不允许任职；空消息：允许任职
				if(node.getFirstChild()!=null && node.getFirstChild().getNodeValue()!=null){
					b=false;
					System.out.println("返回结果==>"+node.getFirstChild().getNodeValue());
				}else
					b=true;
			}
		    
		    System.out.println(strReturn);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{			
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return b;
	}
	
	/**
	 * 编辑人员任职情况
	 * @param xml 任职详细信息
	 */
	public static void editRYRZQK_XML(String xml) throws SOAPException{
		ByteArrayInputStream inputStream = null;		
		if (m_url == null) 
	    {
	      throw new SOAPException (Constants.FAULT_CODE_CLIENT,"A URL not specified");
	    }
		try{			
			m_soapUri = "http://222.35.143.130/EditRYRZQK_XML";
		    String msgSink = "<EditRYRZQK_XML xmlns=\"http://222.35.143.130/\">" +
			"<dataSetXMLString>" +
			xml+
			"</dataSetXMLString>"+
			"</EditRYRZQK_XML>";
		    MsgBody ourBody = new MsgBody (msgSink);
		    m_envelope.setBody (ourBody);   
		    m_strMsg_.send (new URL (m_url), m_soapUri, m_envelope);
		    m_strReturnMsg = m_strMsg_.receive();
		    String strReturn=m_strReturnMsg.getContent().toString();
		    System.out.println(strReturn);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{			
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 编辑锁定信息
	 * @param xml 锁定详细信息
	 * @return
	 */
	public static void editSD_XML(String xml) throws SOAPException{
		ByteArrayInputStream inputStream = null;		
		if (m_url == null) 
	    {
	      throw new SOAPException (Constants.FAULT_CODE_CLIENT,"A URL not specified");
	    }
		try{			
			m_soapUri = "http://222.35.143.130/EditSD_XML";
		    String msgSink = "<EditSD_XML xmlns=\"http://222.35.143.130/\">" +
			"<dataSetXMLString>" +
			xml+
			"</dataSetXMLString>"+
			"</EditSD_XML>";
		    MsgBody ourBody = new MsgBody (msgSink);
		    m_envelope.setBody (ourBody);   
		    m_strMsg_.send (new URL (m_url), m_soapUri, m_envelope);
		    m_strReturnMsg = m_strMsg_.receive();
		    String strReturn=m_strReturnMsg.getContent().toString();
		    System.out.println(strReturn);
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{			
			try {
				if(inputStream!=null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 编辑人员任职情况(施工许可（提前开工复函）申办)
	 * @return
	 */
	public static String editSgxkRYRZQK(Connection conn,String instanceGUID){		
		String str = "";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		RyrzqkBean obj = new RyrzqkBean();		
		try{			
			
			obj.setModifytime(new Date());
			obj.setModifygovernno("深圳市住房和建设局");
			
			//从登记表中获取信息
			String sql = "select * from office_spi_declareinfo t where t.workflowinstance_guid=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, instanceGUID);
			rs = pstmt.executeQuery();
			if(rs.next()){
				obj.setCorp_name(rs.getString("DECLARERPERSON"));
				obj.setOrg_code(rs.getString("ZHENGJIANDAIMA"));
				obj.setGcbh(rs.getString("GONGCHENGBIANHAO"));
				obj.setGcmc(rs.getString("GONGCHENGMINGCHENG"));
			}
			rs.close();
			pstmt.close();
			
			//项目经理和项目信息从表单内读取
			sql = "select * from zjsb_za_fwptsqb where guid = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, instanceGUID);
			rs = pstmt.executeQuery();
			if(rs.next()){				
				obj.setXmbh(rs.getString("xmbh"));
				obj.setXmmc(rs.getString("xmmc"));
				obj.setIsbig("0");				
				obj.setStart_date(rs.getString("htkgrq"));
				obj.setEnd_date(rs.getString("htjgrq"));
				obj.setIs_complete("0");	
				
				//项目经理任职记录
				if(!StringUtils.isBlank(rs.getString("jzsmc"))){					
					obj.setEmp_name(rs.getString("jzsmc"));
					obj.setEmp_id("jzssfzhm");
					obj.setEmp_cert_no("jzszch");
					obj.setEmp_type("1");
					//校验是否可任职
					if(getRYRZQK_XML(obj.getGcbh(),obj.getEmp_id())){		
						//checkIsCanRZ(obj.getEmp_id(),obj.getXmbh(),obj.getIsbig(),obj.getGcbh()) 			
						//拼接成xml字符串
						String strxml = "<![CDATA[<DataSet>"+
							"<DataTable>"+
							"<ORG_CODE>"+obj.getOrg_code()+"</ORG_CODE>"+
							"<CORP_NAME>"+obj.getCorp_name()+"</CORP_NAME>"+
							"<XMBH>"+obj.getXmbh()+"</XMBH>"+
							"<XMMC>"+obj.getXmmc()+"</XMMC>"+
							"<IsBig>"+obj.getIsbig()+"</IsBig>"+
							"<GCBH>"+obj.getGcbh()+"</GCBH>"+
							"<GCMC>"+obj.getGcmc()+"</GCMC>"+
							"<EMP_ID>"+obj.getEmp_id()+"</EMP_ID>"+
							"<EMP_NAME>"+obj.getEmp_name()+"</EMP_NAME>"+
							"<EMP_TYPE>"+obj.getEmp_type()+"</EMP_TYPE>"+
							"<EMP_CERT_NO>"+obj.getEmp_cert_no()+"</EMP_CERT_NO>"+
							"<START_DATE>"+obj.getStart_date()+"</START_DATE>"+
							"<END_DATE>"+obj.getEnd_date()+"</END_DATE>"+
							"<IS_Complete>"+obj.getIs_complete()+"</IS_Complete>"+
							"<REMARK>"+obj.getRemark()+"</REMARK>"+
							"<Modifier>"+obj.getModifier()+"</Modifier>"+
							"<ModifyGovernNO>"+obj.getModifygovernno()+"</ModifyGovernNO>"+
							"<ModifyTime>"+obj.getModifytime()+"</ModifyTime>"+
						  "</DataTable>"+
						"</DataSet>]]>";
						
						//调用接口
						editRYRZQK_XML(strxml);
					}
				}
						
				//项目总监任职记录
				if(!StringUtils.isBlank(rs.getString("jzsmc"))){					
					obj.setEmp_name(rs.getString("xmzj"));
					obj.setEmp_id("xmzjsfzhm");
					obj.setEmp_cert_no("zmzjzch");
					obj.setEmp_type("2");
					//校验是否可任职
					if(getRYRZQK_XML(obj.getGcbh(),obj.getEmp_id())){
						//checkIsCanRZ(obj.getEmp_id(),obj.getXmbh(),obj.getIsbig(),obj.getGcbh())
						//拼接成xml字符串
						String strxml = "<![CDATA[<DataSet>"+
							"<DataTable>"+
							"<ORG_CODE>"+obj.getOrg_code()+"</ORG_CODE>"+
							"<CORP_NAME>"+obj.getCorp_name()+"</CORP_NAME>"+
							"<XMBH>"+obj.getXmbh()+"</XMBH>"+
							"<XMMC>"+obj.getXmmc()+"</XMMC>"+
							"<IsBig>"+obj.getIsbig()+"</IsBig>"+
							"<GCBH>"+obj.getGcbh()+"</GCBH>"+
							"<GCMC>"+obj.getGcmc()+"</GCMC>"+
							"<EMP_ID>"+obj.getEmp_id()+"</EMP_ID>"+
							"<EMP_NAME>"+obj.getEmp_name()+"</EMP_NAME>"+
							"<EMP_TYPE>"+obj.getEmp_type()+"</EMP_TYPE>"+
							"<EMP_CERT_NO>"+obj.getEmp_cert_no()+"</EMP_CERT_NO>"+
							"<START_DATE>"+obj.getStart_date()+"</START_DATE>"+
							"<END_DATE>"+obj.getEnd_date()+"</END_DATE>"+
							"<IS_Complete>"+obj.getIs_complete()+"</IS_Complete>"+
							"<REMARK>"+obj.getRemark()+"</REMARK>"+
							"<Modifier>"+obj.getModifier()+"</Modifier>"+
							"<ModifyGovernNO>"+obj.getModifygovernno()+"</ModifyGovernNO>"+
							"<ModifyTime>"+obj.getModifytime()+"</ModifyTime>"+
						  "</DataTable>"+
						"</DataSet>]]>";
						
						//调用接口
						editRYRZQK_XML(strxml);
					}
				}
			}							
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try{
				if(rs!=null)
					rs.close();
				if(pstmt!=null)
					pstmt.close();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return str;
	}
}
