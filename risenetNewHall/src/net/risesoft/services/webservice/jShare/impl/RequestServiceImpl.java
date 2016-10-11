package net.risesoft.services.webservice.jShare.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ParameterMode;

import net.risesoft.actions.onlineservice.webservice.beans.WebServiceBean;
import net.risesoft.beans.base.KeyValue;
import net.risesoft.services.webservice.jShare.RequestService;
import net.risesoft.util.ValidatorUtil;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.codehaus.xfire.client.Client;
import org.jdom.input.SAXBuilder;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 住建局WebService接口访问
 * @author HJL
 *
 */
@Controller
public class RequestServiceImpl implements RequestService
{
	@Resource
	private ICodeMapUtil codeMapUtil;
	
	/**
	 * @description 保函信息webServices接口访问接口
	 * @param gcbh 工程编号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	@Override
	public List<KeyValue> backletterWebService(String gcbh){
		ByteArrayInputStream inputStream = null;
		List<KeyValue> dataList = new ArrayList<KeyValue>();
		KeyValue kv = null;
		try {
//			String url = "http://www.szjsjy.com.cn:8003/WebService.asmx?wsdl";
		    String url = "http://61.144.226.5:8003/WebService.asmx?wsdl";
			Object[] params = new Object[]{gcbh};
			
			/*通过访问WebService得到XML文件*/
			Client client = new Client(new URL(url));
			Object[] xml = client.invoke("GetGuarLetter",params);
			/*对XML文档进行解析，并封装放到bean里面*/
   			//把String类型字符串转为输入流
   			inputStream = new ByteArrayInputStream(xml[0].toString().getBytes("UTF-8"));
   			//对XML文档进行解析，转换为Document对象
   			org.jdom.Document doc= new SAXBuilder().build(inputStream);
   			org.jdom.Element root=doc.getRootElement();//获得根元素element
   			org.jdom.Element child=root.getChild("Item");//获得指定名称的第一个子元素

   			if(child!=null){
				kv = new KeyValue();
				kv.setKey("工程名称");
				kv.setValue(child.getAttribute("GCMC").getValue());
				dataList.add(kv);
				
				kv = new KeyValue();
				kv.setKey("业主名称");
				kv.setValue(child.getAttribute("YZMC").getValue());
				dataList.add(kv);
				
				kv = new KeyValue();
				kv.setKey("承包商名称");
				kv.setValue(child.getAttribute("CBSMC").getValue());
				dataList.add(kv);
				
				kv = new KeyValue();
				kv.setKey("支付保函编号");
				kv.setValue(child.getAttribute("ZFBHBH").getValue());
				dataList.add(kv);
				
				kv = new KeyValue();
				kv.setKey("履约保函编号");
				kv.setValue(child.getAttribute("LYBHBH").getValue());
				dataList.add(kv);
				
				kv = new KeyValue();
				kv.setKey("日期");
				kv.setValue(child.getAttribute("RQ").getValue().substring(0,10));
				dataList.add(kv);
			}
			if(dataList.size()<1){
				kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				dataList.add(kv);
			}
//		}catch(ParserConfigurationException e){
//			e.printStackTrace();
//			kv = new KeyValue();
//			kv.setKey("服务端查询出错描述");
//			kv.setValue("<div style='align:center'>查无记录。</div><div>点击“<span style='color:red;'>上传材料</span>”按钮 进行上传附件</div><div>点击“<span style='color:red;'>重新输入</span>”按钮，则重新输入<span style='color:red;'>新编号</span></div>");
//			dataList.add(kv);
//		}catch (SAXException e){
//			e.printStackTrace();
//			kv = new KeyValue();
//			kv.setKey("服务端查询出错描述");
//			kv.setValue("<div style='align:center'>查无记录。</div><div>点击“<span style='color:red;'>上传材料</span>”按钮 进行上传附件</div><div>点击“<span style='color:red;'>重新输入</span>”按钮，则重新输入<span style='color:red;'>新编号</span></div>");
//			dataList.add(kv);
//		}catch (IOException e){
//			e.printStackTrace();
//			kv = new KeyValue();
//			kv.setKey("服务端查询出错描述");
//			kv.setValue("查无记录。请带纸质材料到窗口办理。");
//			dataList.add(kv);
		} catch (Exception e) {
			e.printStackTrace();
			kv = new KeyValue();
			kv.setKey("服务端查询出错描述");
			kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
			dataList.add(kv);
		}finally{
			close(inputStream);
		}
		return dataList;
	}

	
	/**
	 * @description 证照材料共享(深圳市民用建筑施工图设计文件抽查（备案）意见书)webServices接口访问接口
	 * @param zjbh 证照编号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	@Override
	public List<KeyValue> licenseWebService(String zjbh) {
		List<KeyValue> dataList = new ArrayList<KeyValue>();
		KeyValue kv = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
			String url=dataSource.get("ywbw.database.url");
			String username=dataSource.get("ywbw.database.user");
			String pass=dataSource.get("ywbw.database.password");
			conn = DriverManager.getConnection(url,username,pass);
			
			String sql = "select  a.BJSJ, a.ZJBH,a.ZJYXQX, a.ZJMC, a.NID, b.APP_UINT, b.APP_INFO "+
						 "from EXAM_END a, exam_accept b where a.TAG_DEL<>1 and b.nid = a.file_Id and a.ZJBH = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,zjbh);
			rs = pstmt.executeQuery();
			if(rs.next()){
				kv = new KeyValue();
				kv.setKey("证照编号");
				kv.setValue(rs.getString("ZJBH"));
				dataList.add(kv);
				
				kv = new KeyValue();
				kv.setKey("证照名称");
				kv.setValue(rs.getString("ZJMC"));
				dataList.add(kv);
				
				kv = new KeyValue();
				kv.setKey("工程名称");
				kv.setValue(rs.getString("APP_INFO"));
				dataList.add(kv);

				kv = new KeyValue();
				kv.setKey("建设单位");
				kv.setValue(rs.getString("APP_UINT"));
				dataList.add(kv);

				kv = new KeyValue();
				kv.setKey("发证(文)时间");
				kv.setValue(rs.getDate("BJSJ").toString());
				dataList.add(kv);

				kv = new KeyValue();
				kv.setKey("证照有效期限");
				kv.setValue(rs.getString("ZJYXQX"));
				dataList.add(kv);
			}
			if(dataList.size()<1){
				kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				dataList.add(kv);
			}
		}catch(Exception e){
			e.printStackTrace();
			kv = new KeyValue();
			kv.setKey("服务端查询出错描述");
			kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
			dataList.add(kv);
		}finally{
			try{
				if(rs!=null){
					rs.close();
				}
			if(pstmt!=null)
				pstmt.close();
				if(conn!=null){
					conn.close();
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return dataList;
	}

	
	/**
	 * @description 审查合格书webServices接口访问接口
	 * @param qualifiedbookid 审查合格书编号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	@Override
	public List<KeyValue> investigateWebService(String qualifiedbookid) {
		List<KeyValue> dataList = new ArrayList<KeyValue>();
		KeyValue kv = null;
		ByteArrayInputStream inputStream = null;
		try {
			String url = "http://61.144.226.3:8081/kcsjSchgsQueryService/services/dataService?wsdl";
			Object[] params = new Object[]{qualifiedbookid};
			/*通过访问WebService得到XML文件*/
			Client client = new Client(new URL(url));
			Object[] xml = client.invoke("queryQualifiedBook",params);
			
			/*对XML文档进行解析，并封装放到bean里面*/
			//建立一个解析器工厂
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			//获得一个具体的解析器对象
			DocumentBuilder db = factory.newDocumentBuilder();
			//把String类型字符串转为输入流
			inputStream = new ByteArrayInputStream(xml[0].toString().getBytes("UTF-8"));
			//对XML文档进行解析，获得Document对象
			Document doc = db.parse(inputStream);
			doc.normalize();
	
			//获取所有的nodes元素列表
			NodeList nodes = doc.getElementsByTagName("qualifiedbookinfo");
			for (int i = 0; i < nodes.getLength(); i++)
			{
				//获取一个nodes元素
				Element node = (Element) nodes.item(i);
				String queryresult = node.getElementsByTagName("queryresult").item(0).getFirstChild().getNodeValue();
				if(queryresult!=null){
					kv = new KeyValue();
					kv.setKey("查询状态");
					kv.setValue(queryresult);
					dataList.add(kv);
					
					if(queryresult.equals("1")){
						if(node.getElementsByTagName("bacode").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("审查合格书编号");
							kv.setValue(node.getElementsByTagName("bacode").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("prjname").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("工程名称");
							kv.setValue(node.getElementsByTagName("prjname").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("prjadd").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("工程地址");
							kv.setValue(node.getElementsByTagName("prjadd").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("prjtype").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("工程类别");
							kv.setValue(node.getElementsByTagName("prjtype").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("prjlevel").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("工程等级");
							kv.setValue(node.getElementsByTagName("prjlevel").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("prjscale").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("工程规模");
							kv.setValue(node.getElementsByTagName("prjscale").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("jsdw").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("建设单位");
							kv.setValue(node.getElementsByTagName("jsdw").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("kcdw").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("勘察单位");
							kv.setValue(node.getElementsByTagName("kcdw").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("sjdw").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("设计单位");
							kv.setValue(node.getElementsByTagName("sjdw").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
						if(node.getElementsByTagName("scdw").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("审查机构");
							kv.setValue(node.getElementsByTagName("scdw").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
					}else if(queryresult.equals("2")){
						if(node.getElementsByTagName("errorinfo").item(0).getFirstChild()!=null){
							kv = new KeyValue();
							kv.setKey("服务端查询出错描述");
							kv.setValue(node.getElementsByTagName("errorinfo").item(0).getFirstChild().getNodeValue());
							dataList.add(kv);
						}
					}
				}
			}
			if(dataList.size()<1||dataList.get(0).getValue().equals("0")){
				dataList.remove(0);
				kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				dataList.add(kv);
			}
//		}catch(ParserConfigurationException e){
//			e.printStackTrace();
//			kv = new KeyValue();
//			kv.setKey("服务端查询出错描述");
//			kv.setValue("查无记录。请带纸质材料到窗口办理。");
//			dataList.add(kv);
//		}catch (SAXException e){
//			e.printStackTrace();
//			kv = new KeyValue();
//			kv.setKey("服务端查询出错描述");
//			kv.setValue("查无记录。请带纸质材料到窗口办理。");
//			dataList.add(kv);
//		}catch (IOException e){
//			e.printStackTrace();
//			kv = new KeyValue();
//			kv.setKey("服务端查询出错描述");
//			kv.setValue("查无记录。请带纸质材料到窗口办理。");
//			dataList.add(kv);
		} catch (Exception e) {
			e.printStackTrace();
			kv = new KeyValue();
			kv.setKey("服务端查询出错描述");
			kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
			dataList.add(kv);
		}finally{
			close(inputStream);
		}
		return dataList;
	}


	/**
	 * @description 材料共享（直接发包审批决定书文号）WebService访问
	 * @param bwbh 直接发包审批决定书文号
	 * @date 2013-10-14
	 * @author HJL
	 * @return 
	 */
	@Override
	public List<KeyValue> decisionWebService(String bwbh) {
		List<KeyValue> dataList = new ArrayList<KeyValue>();
		try {
//			setBwbh("深建住保[2013]65号");
			//返回参数类型
			Service service = new Service();
			// String wsdlUrl = "http://localhost/services/WebServiceForSearch?wsdl";//请求服务的URL 
//			String wsdlUrl = "http://192.168.0.72/services/WebServiceForSearch?wsdl";
			String wsdlUrl = "http://192.168.53.89:7001/services/WebServiceForSearch?wsdl";
	        URL url = new URL(wsdlUrl);//通过URL类的构造方法传入wsdlUrl地址创建URL对象 
	        // 2.创建服务方法的调用者对象call，设置call对象的属性 
	        Call call = (Call) service.createCall(); 
	        call.setTargetEndpointAddress(url);//给call对象设置请求的URL属性 
	        String serviceName = "getFile"; // 文件标题、申请单位、办结时间（|分割，注意顺序），用null判断是否存在
			
//	        String serviceName = "getWord";  // 只有内网用
	        call.setOperationName(serviceName);//给call对象设置调用方法名属性 
			// 办文编号 参数
	        call.addParameter("bwbh", XMLType.XSD_STRING, ParameterMode.IN);// 给call对象设置方法的参数名、参数类型、参数模式 
	        call.setReturnType(XMLType.XSD_STRING);// 设置调用方法的返回值类型 
			//执行接口方法
	        // 文件标题、申请单位、办结时间（|分割，注意顺序），用null判断是否存在
	        String message = (String)call.invoke(new Object[] {bwbh}) ;
//	        String message = "文件标题test|申请单位test|办结时间test";
	        if(message!=null&&message!=""){
	        	String[] msgs = message.split("\\|");
	        	
	        	KeyValue kv0 = new KeyValue();
	        	KeyValue kv1 = new KeyValue();
	        	KeyValue kv2 = new KeyValue();
	        	kv0.setKey("文件标题");
	        	kv0.setValue(msgs[0]);
	        	kv1.setKey("申请单位");
	        	kv1.setValue(msgs[1]);
	        	kv2.setKey("办结时间");
	        	kv2.setValue(msgs[2]);
	        	
	        	dataList.add(kv0);
	        	dataList.add(kv1);
	        	dataList.add(kv2);
	        }
			if(dataList.size()<1){
				KeyValue kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				dataList.add(kv);
			}
		} catch (Exception e) {
			e.printStackTrace();
			KeyValue kv = new KeyValue();
			kv.setKey("服务端查询出错描述");
			kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
			dataList.add(kv);
		}
		return dataList;
	}
	
	
	/**
	 * @Description 项目总监、建造师信息回填
	 * @Author hjl
	 * @Date 2013-11-05
	 * @return 
	 */
	public List<WebServiceBean> backfill(HttpServletRequest request){
		Connection conn = null; 
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WebServiceBean> list = null;
		try{
			// 中文乱码 
			String sfzhm = URLDecoder.decode(request.getParameter("sfzhm"), "utf-8") == null ? "" : URLDecoder.decode(request.getParameter("sfzhm"), "utf-8");
			String category = request.getParameter("category") == null ? "" : request.getParameter("category");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			if (!sfzhm.equals("")) {
				StringBuffer sql = new StringBuffer();
				sql.append(" select distinct e.name,t.CATEGORY,e.mobile,t.REG_CERT_NO,t.alt_stamp_id,t.Valid_Date,t.ID_NUMBER,e.corp_name,t.change_cause,t.alt_qual_lv ");
				sql.append(" from emp_qual_history t, EMP_PERSINFO e  ");
				sql.append(" where t.id_number = e.id_number and t.id_number = ? and t.CATEGORY = ? and t.tag_del != '1' ");
				try {
					Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
					String url=dataSource.get("ywbw.database.url");
					String username=dataSource.get("ywbw.database.user");
					String pass=dataSource.get("ywbw.database.password");
					conn = DriverManager.getConnection(url,username,pass);
					ps = conn.prepareStatement(sql.toString());
					ps.setString(1, sfzhm);
					ps.setString(2, category);
					rs = ps.executeQuery();
					
					WebServiceBean webServiceBean = null;
					list = new ArrayList<WebServiceBean>();
					while (rs.next()) {
						webServiceBean = new WebServiceBean();
						webServiceBean.setName(rs.getString("name") == null ? "" : rs.getString("name"));
						category = rs.getString("CATEGORY");
						if (category == null) {
							category = "";
						} else if (category.equals("2")) {
							category = "建造工程师（项目经理）";
						} else if (category.equals("3")) {
							category = "监理工程师（项目总监）";
						} else if (category.equals("4")) {
							category = "造价工程师";
						} else {
							category = "";
						}
						webServiceBean.setCategory(category);
						webServiceBean.setCertId(rs.getString("alt_stamp_id"));
						webServiceBean.setValidDate(rs.getString("Valid_Date") == null ? "" : sdf.format(rs.getDate("Valid_Date")));
						webServiceBean.setIdCard(rs.getString("ID_NUMBER"));
						webServiceBean.setMobile(rs.getString("mobile")==null?"":rs.getString("mobile"));
						webServiceBean.setChange_cause(rs.getString("change_cause"));
						webServiceBean.setCorp_name(rs.getString("corp_name"));
						
						int lv = rs.getInt("alt_qual_lv");
						String alt_qual_lv = "";
						if(lv==1){
							alt_qual_lv = "有";
						}else if(lv==2){
							alt_qual_lv = "一级";
						}else if(lv==3){
							alt_qual_lv = "二级";
						}else if(lv==4){
							alt_qual_lv = "三级";
						}else if(lv==5){
							alt_qual_lv = "四级";
						}
						webServiceBean.setAlt_qual_lv(alt_qual_lv);
						
						list.add(webServiceBean);
					}
					rs.close();
					ps.close();
					
//					getResponse().setContentType("text/html;charset=UTF-8");
//					getResponse().setHeader("Cache-Control", "no-cache");
//					JSONObject json = new JSONObject();// JSON父对象
					
//					// 查不到数据
//					if (list.size() == 0) {
////						json.put("error", "该身份证号无对应记录");
//						outJson("{'error':'该身份证号无对应记录'}",null);
//						// 数据不止一个
//					} else if (list.size() > 1) {
////						json.put("error", "系统出错，请联系管理员");
//						outJson("{'error':'系统出错，请联系管理员'}",null);
//					} else {
////						json.put("info", list.get(0));
//						outJson(webServiceBean,null);
//					}
//					
//					System.out.println(json.toString());
//					getResponse().getWriter().write(json.toString());
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (conn != null) {
							conn.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 施工企业资质证书验证
	 *//*
	public List<KeyValue> getSgqyzzzs(String zzzsh){
		List<KeyValue> list=new ArrayList<KeyValue>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select t.cert_no,case when t.qual_lvl=0 then '特级' when t.qual_lvl=1 then '甲级' when t.qual_lvl=2 then '乙级' when t.qual_lvl=3" +
				" then '丙级' when t.qual_lvl=4 then '暂乙' when t.qual_lvl=5 then '暂丙' when t.qual_lvl=6 then '暂定级' when t.qual_lvl=99 then '不分级'" +
				" end as qual_lvl,t.bus_range,t.issue_org,t.issue_date,c.CORP_NAME from corp_corpinfo c,CORP_QUAL_CERTS t where c.corp_id=t.corp_id and" +
				" c.org_code=? and t.tag_del <>1";
		try{
			Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
			String url=dataSource.get("ywbw.database.url");
			String username=dataSource.get("ywbw.database.user");
			String pass=dataSource.get("ywbw.database.password");
			conn = DriverManager.getConnection(url,username,pass);
			ps=conn.prepareStatement(sql);
			ps.setString(1, zzzsh);
			rs=ps.executeQuery();
			while(rs.next()){
				KeyValue keyvalue=new KeyValue();
				keyvalue.setKey("企业名称");
				keyvalue.setValue(rs.getString("CORP_NAME"));
				list.add(keyvalue);
				keyvalue=new KeyValue();
				keyvalue.setKey("资质证书号");
				keyvalue.setValue(rs.getString("cert_no"));
				list.add(keyvalue);
				keyvalue=new KeyValue();
				keyvalue.setKey("资质等级");
				keyvalue.setValue(rs.getString("qual_lvl"));
				list.add(keyvalue);
				KeyValue keyvalue1=new KeyValue();
				keyvalue1.setKey("营业范围");
				keyvalue1.setValue(rs.getString("bus_range"));
				list.add(keyvalue1);
				KeyValue keyvalue2=new KeyValue();
				keyvalue2.setKey("发证机关");
				keyvalue2.setValue(rs.getString("issue_org"));
				list.add(keyvalue2);
				KeyValue keyvalue3=new KeyValue();
				keyvalue3.setKey("发证日期");
				keyvalue3.setValue(sdf.format(rs.getDate("issue_date")));
				list.add(keyvalue3);
			}
			if(list.size()<1){
				KeyValue kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				list.add(kv);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				if(!conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}*/
	
	/**
	 * 施工企业资质证书验证
	 */
	public List<KeyValue> getSgqyzzzs(String zzzsh){
		List<KeyValue> list=new ArrayList<KeyValue>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select t.cert_no,l.name as qual_lvl,Get_BUS_RANGE(t.corp_id, t.cert_type) as bus_range,t.issue_org,t.issue_date,c.CORP_NAME from" +
				" corp_corpinfo c, CORP_QUAL_CERTS t,CORP_DIC_QUAL_LVL2 l where c.corp_id = t.corp_id and l.id=t.qual_lvl and c.org_code = ? and t.tag_del <> 1";
		String sql2="select case when t.is_main=1 then '是' when t.is_main=0 then '否' end as ismain, s.name as zzxl, a.name as zzlb,t.APPR_ORG as pzjg," +
				"l.name as zzdj,t.APPR_DATE as pzsj,case when t.IS_VALID=1 then '是' when t.IS_VALID=0 then '否' end as isyx,x.name as zzxz,t.VALID_PERIOD as yxq,case when " +
				"t.IS_TEMP=1 then '是' when t.IS_TEMP=0 then '否' end as iszd,case when t.IS_STOP=1 then '是' when t.IS_STOP=0 then '否' end as istb from" +
				" corp_cert_quals t,corp_dic_qual_type a,CORP_CORPINFO c,CORP_DIC_QUAL_SERIAL s,CORP_DIC_QUAL_LVL2 l,CORP_DIC_QUAL_LIMIT x where" +
				" t.qual_type = a.id and c.corp_id = t.corp_id and a.QUAL_ID = s.ORDER_ID and t.QUAL_LVL=l.id and t.QUAL_LIMIT=x.id and c.org_code" +
				" = ? and t.tag_del<>1 order by t.is_main desc";
		try{
			Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
			String url=dataSource.get("ywbw.database.url");
			String username=dataSource.get("ywbw.database.user");
			String pass=dataSource.get("ywbw.database.password");
			conn = DriverManager.getConnection(url,username,pass);
			ps=conn.prepareStatement(sql);
			ps.setString(1, zzzsh);
			rs=ps.executeQuery();
			StringBuffer sb=new StringBuffer();
			int count=0;
			while(rs.next()){
				sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\"  width=\"100%\" id=\"table_content\" style=\"font-family:微软雅黑;width:800px;\">");
				sb.append("<tr><th style=\"width:20%\">企业名称</th><td colspan=\"5\"  style=\"width:80%\">&nbsp;"+ValidatorUtil.filter(rs.getString("CORP_NAME"))+"&nbsp;</td></tr>");
				sb.append("<tr><th>资质证书号</th><td colspan=\"2\"  style=\"width:30%\">&nbsp;"+ValidatorUtil.filter(rs.getString("cert_no"))+"&nbsp;</td><th  style=\"width:20%\">资质等级</th><td colspan=\"2\"  style=\"width:30%\">&nbsp;"+ValidatorUtil.filter(rs.getString("qual_lvl"))+"&nbsp;</td></tr>");
				sb.append("<tr><th>发证机关</th><td colspan=\"2\">&nbsp;"+ValidatorUtil.filter(rs.getString("issue_org"))+"&nbsp;</td><th>发证日期</th><td colspan=\"2\">&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("issue_date")))+"&nbsp;</td></tr>");
				sb.append("<tr><th>承接工程范围</th><td colspan=\"5\">&nbsp;"+ValidatorUtil.filter(rs.getString("bus_range"))+"</td></tr>");
				sb.append("</table>");
				count++;
			}
			
			ps=conn.prepareStatement(sql2);
			ps.setString(1, zzzsh);
			rs=ps.executeQuery();
			sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\"  width=\"100%\" id=\"table_content\" style=\"font-family:微软雅黑;width:800px;\">");
			sb.append("<tr><th colspan=\"7\" style=\"font-size:22px;height:40px;text-align: center;\">证书资质信息</th></tr>");
			int number=0;
			sb.append("<tr><th>是否是主项资质</th><th>资质类别</th><th>资质等级</th><th>批准时间</th><th>是否有效</th><th>有效期</th></tr>");
			while(rs.next()){
				/*if(rs.getString("ismain")!=null&&rs.getString("ismain").equals("是")){
					sb.append("<tr><th rowspan=\"4\">主项资质</td><th>资质类别</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzlb"))+"</td><th>资质序列</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("zzxl"))+"</td></tr>");
					sb.append("<tr><th>资质限制</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzxz"))+"</td><th>批准机关</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("pzjg"))+"</td></tr>");
					sb.append("<tr><th>资质等级</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzdj"))+"</td><th>批准时间</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("pzsj")))+"</td><th>是否暂定</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("iszd"))+"</td></tr>");
					sb.append("<tr><th>是否有效</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("isyx"))+"</td><th>有效期</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("yxq")))+"</td><th>是否停标</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("istb"))+"</td></tr>");
				}else{
					number++;
					sb.append("<tr><th rowspan=\"4\">增项"+number+"</td><th>资质类别</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzlb"))+"</td><th>资质序列</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("zzxl"))+"</td></tr>");
					sb.append("<tr><th>资质限制</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzxz"))+"</td><th>批准机关</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("pzjg"))+"</td></tr>");
					sb.append("<tr><th>资质等级</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzdj"))+"</td><th>批准时间</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("pzsj")))+"</td><th>是否暂定</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("iszd"))+"</td></tr>");
					sb.append("<tr><th>是否有效</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("isyx"))+"</td><th>有效期</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("yxq")))+"</td><th>是否停标</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("istb"))+"</td></tr>");
				}*/
				
				sb.append("<tr><td>"+ValidatorUtil.filter(rs.getString("ismain"))+"</td><td>"+ValidatorUtil.filter(rs.getString("zzlb"))+"</td>" +
						"<td>"+ValidatorUtil.filter(rs.getString("zzdj"))+"</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("pzsj")))+"</td>" +
						"<td>&nbsp;"+ValidatorUtil.filter(rs.getString("isyx"))+"</td><td>"+ValidatorUtil.filter(sdf.format(rs.getDate("yxq")))+"</td></tr>");
				
				/*keyvalue=new DataBean();
				keyvalue.setCn_key("资质证书号");
				keyvalue.setValue(rs.getString("cert_no"));
				list.add(keyvalue);
				keyvalue=new DataBean();
				keyvalue.setCn_key("资质等级");
				keyvalue.setValue(rs.getString("qual_lvl"));
				list.add(keyvalue);
				DataBean keyvalue1=new DataBean();
				keyvalue1.setCn_key("营业范围");
				keyvalue1.setValue(rs.getString("bus_range"));
				list.add(keyvalue1);
				DataBean keyvalue2=new DataBean();
				keyvalue2.setCn_key("发证机关");
				keyvalue2.setValue(rs.getString("issue_org"));
				list.add(keyvalue2);
				DataBean keyvalue3=new DataBean();
				keyvalue3.setCn_key("发证日期");
				keyvalue3.setValue(sdf.format(rs.getDate("issue_date")));
				list.add(keyvalue3);*/
			}
			sb.append("</table>");
			KeyValue keyvalue=new KeyValue();
			keyvalue.setKey("施工企业资质证书");
			keyvalue.setValue(sb.toString());
			list.add(keyvalue);
			if(count<1){
				KeyValue kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">确定</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">取消</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				list.add(kv);
			}		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				if(!conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	/**
	 * 监理单位资质证书验证
	 */
	public List<KeyValue> getJldwzzzs(String zzzsh){
		List<KeyValue> list=new ArrayList<KeyValue>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select t.cert_no,l.name as qual_lvl,Get_BUS_RANGE(t.corp_id, t.cert_type) as bus_range,t.issue_org,t.issue_date,c.CORP_NAME from" +
				" corp_corpinfo c, CORP_QUAL_CERTS t,CORP_DIC_QUAL_LVL1 l where c.corp_id = t.corp_id and l.id=t.qual_lvl and c.org_code = ? and t.tag_del <> 1";
		String sql2="select case when t.is_main=1 then '是' when t.is_main=0 then '否' end as ismain, s.name as zzxl, a.name as zzlb,t.APPR_ORG as pzjg," +
				"l.name as zzdj,t.APPR_DATE as pzsj,case when t.IS_VALID=1 then '是' when t.IS_VALID=0 then '否' end as isyx,x.name as zzxz,t.VALID_PERIOD as yxq,case when " +
				"t.IS_TEMP=1 then '是' when t.IS_TEMP=0 then '否' end as iszd,case when t.IS_STOP=1 then '是' when t.IS_STOP=0 then '否' end as istb from" +
				" corp_cert_quals t,corp_dic_qual_type a,CORP_CORPINFO c,CORP_DIC_QUAL_SERIAL s,CORP_DIC_QUAL_LVL2 l,CORP_DIC_QUAL_LIMIT x where" +
				" t.qual_type = a.id and c.corp_id = t.corp_id and a.QUAL_ID = s.ORDER_ID and t.QUAL_LVL=l.id and t.QUAL_LIMIT=x.id and c.org_code" +
				" = ? and t.tag_del<>1 order by t.is_main desc";
		try{
			Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
			String url=dataSource.get("ywbw.database.url");
			String username=dataSource.get("ywbw.database.user");
			String pass=dataSource.get("ywbw.database.password");
			conn = DriverManager.getConnection(url,username,pass);
			ps=conn.prepareStatement(sql);
			ps.setString(1, zzzsh);
			rs=ps.executeQuery();
			StringBuffer sb=new StringBuffer();
			int count=0;
			while(rs.next()){
				sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\"  width=\"100%\" id=\"table_content\" style=\"font-family:微软雅黑;width:800px;\">");
				sb.append("<tr><th style=\"width:20%\">企业名称</th><td colspan=\"5\"  style=\"width:80%\">&nbsp;"+ValidatorUtil.filter(rs.getString("CORP_NAME"))+"&nbsp;</td></tr>");
				sb.append("<tr><th>资质证书号</th><td colspan=\"2\"  style=\"width:30%\">&nbsp;"+ValidatorUtil.filter(rs.getString("cert_no"))+"&nbsp;</td><th  style=\"width:20%\">资质等级</th><td colspan=\"2\"  style=\"width:30%\">&nbsp;"+ValidatorUtil.filter(rs.getString("qual_lvl"))+"&nbsp;</td></tr>");
				sb.append("<tr><th>发证机关</th><td colspan=\"2\">&nbsp;"+ValidatorUtil.filter(rs.getString("issue_org"))+"&nbsp;</td><th>发证日期</th><td colspan=\"2\">&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("issue_date")))+"&nbsp;</td></tr>");
				sb.append("<tr><th>承接工程范围</th><td colspan=\"5\">&nbsp;"+ValidatorUtil.filter(rs.getString("bus_range"))+"</td></tr>");
				sb.append("</table>");
				count++;
			}
			
			ps=conn.prepareStatement(sql2);
			ps.setString(1, zzzsh);
			rs=ps.executeQuery();
			sb.append("<table cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\"  width=\"100%\" id=\"table_content\" style=\"font-family:微软雅黑;width:800px;\">");
			sb.append("<tr><th colspan=\"7\" style=\"font-size:22px;height:40px;text-align: center;\">证书资质信息</th></tr>");
			int number=0;
			sb.append("<tr><th>是否是主项资质</th><th>资质类别</th><th>资质等级</th><th>批准时间</th><th>是否有效</th><th>有效期</th></tr>");
			while(rs.next()){
				/*if(rs.getString("ismain")!=null&&rs.getString("ismain").equals("是")){
					sb.append("<tr><th rowspan=\"4\">主项资质</td><th>资质类别</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzlb"))+"</td><th>资质序列</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("zzxl"))+"</td></tr>");
					sb.append("<tr><th>资质限制</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzxz"))+"</td><th>批准机关</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("pzjg"))+"</td></tr>");
					sb.append("<tr><th>资质等级</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzdj"))+"</td><th>批准时间</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("pzsj")))+"</td><th>是否暂定</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("iszd"))+"</td></tr>");
					sb.append("<tr><th>是否有效</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("isyx"))+"</td><th>有效期</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("yxq")))+"</td><th>是否停标</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("istb"))+"</td></tr>");
				}else{
					number++;
					sb.append("<tr><th rowspan=\"4\">增项"+number+"</td><th>资质类别</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzlb"))+"</td><th>资质序列</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("zzxl"))+"</td></tr>");
					sb.append("<tr><th>资质限制</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzxz"))+"</td><th>批准机关</td><td colspan=\"3\">&nbsp;"+ValidatorUtil.filter(rs.getString("pzjg"))+"</td></tr>");
					sb.append("<tr><th>资质等级</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("zzdj"))+"</td><th>批准时间</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("pzsj")))+"</td><th>是否暂定</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("iszd"))+"</td></tr>");
					sb.append("<tr><th>是否有效</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("isyx"))+"</td><th>有效期</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("yxq")))+"</td><th>是否停标</td><td>&nbsp;"+ValidatorUtil.filter(rs.getString("istb"))+"</td></tr>");
				}*/
				
				sb.append("<tr><td>"+ValidatorUtil.filter(rs.getString("ismain"))+"</td><td>"+ValidatorUtil.filter(rs.getString("zzlb"))+"</td>" +
						"<td>"+ValidatorUtil.filter(rs.getString("zzdj"))+"</td><td>&nbsp;"+ValidatorUtil.filter(sdf.format(rs.getDate("pzsj")))+"</td>" +
						"<td>&nbsp;"+ValidatorUtil.filter(rs.getString("isyx"))+"</td><td>"+ValidatorUtil.filter(sdf.format(rs.getDate("yxq")))+"</td></tr>");
				
				
				/*keyvalue=new DataBean();
				keyvalue.setCn_key("资质证书号");
				keyvalue.setValue(rs.getString("cert_no"));
				list.add(keyvalue);
				keyvalue=new DataBean();
				keyvalue.setCn_key("资质等级");
				keyvalue.setValue(rs.getString("qual_lvl"));
				list.add(keyvalue);
				DataBean keyvalue1=new DataBean();
				keyvalue1.setCn_key("营业范围");
				keyvalue1.setValue(rs.getString("bus_range"));
				list.add(keyvalue1);
				DataBean keyvalue2=new DataBean();
				keyvalue2.setCn_key("发证机关");
				keyvalue2.setValue(rs.getString("issue_org"));
				list.add(keyvalue2);
				DataBean keyvalue3=new DataBean();
				keyvalue3.setCn_key("发证日期");
				keyvalue3.setValue(sdf.format(rs.getDate("issue_date")));
				list.add(keyvalue3);*/
			}
			sb.append("</table>");
			KeyValue keyvalue=new KeyValue();
			keyvalue.setKey("监理单位资质证书信息");
			keyvalue.setValue(sb.toString());
			list.add(keyvalue);
			if(count<1){
				KeyValue kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">确定</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">取消</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				list.add(kv);
			}		
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				if(!conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	/**
	 * 监理单位资质证书验证
	 */
	/*public List<KeyValue> getJldwzzzs(String zzzsh){
		List<KeyValue> list=new ArrayList<KeyValue>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String sql="select t.cert_no,case when t.qual_lvl=0 then '特级' when t.qual_lvl=1 then '一级' when t.qual_lvl=2 then '二级'" +
				" when t.qual_lvl=3 then '三级'  when t.qual_lvl=99 then '不分级' when t.qual_lvl=100 then '在全国范围内承担一、二、三类公路工程、桥'" +
				" end as qual_lvl,t.bus_range,t.issue_org,t.issue_date,c.CORP_NAME from corp_corpinfo c,CORP_QUAL_CERTS t where c.corp_id=t.corp_id and" +
				" c.org_code=? and t.tag_del <>1";
		try{
			Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
			String url=dataSource.get("ywbw.database.url");
			String username=dataSource.get("ywbw.database.user");
			String pass=dataSource.get("ywbw.database.password");
			conn = DriverManager.getConnection(url,username,pass);
			ps=conn.prepareStatement(sql);
			ps.setString(1, zzzsh);
			rs=ps.executeQuery();
			while(rs.next()){
				KeyValue keyvalue=new KeyValue();
				keyvalue.setKey("企业名称");
				keyvalue.setValue(rs.getString("CORP_NAME"));
				list.add(keyvalue);
				keyvalue=new KeyValue();
				keyvalue.setKey("资质证书号");
				keyvalue.setValue(rs.getString("cert_no"));
				list.add(keyvalue);
				keyvalue=new KeyValue();
				keyvalue.setKey("资质等级");
				keyvalue.setValue(rs.getString("qual_lvl"));
				list.add(keyvalue);
				KeyValue keyvalue1=new KeyValue();
				keyvalue1.setKey("营业范围");
				keyvalue1.setValue(rs.getString("bus_range"));
				list.add(keyvalue1);
				KeyValue keyvalue2=new KeyValue();
				keyvalue2.setKey("发证机关");
				keyvalue2.setValue(rs.getString("issue_org"));
				list.add(keyvalue2);
				KeyValue keyvalue3=new KeyValue();
				keyvalue3.setKey("发证日期");
				keyvalue3.setValue(sdf.format(rs.getDate("issue_date")));
				list.add(keyvalue3);
			}
			if(list.size()<1){
				KeyValue kv = new KeyValue();
				kv.setKey("服务端查询出错描述");
				kv.setValue("<br/><br/><div style=\"align:center;font-size:20px;font-weight:bold;\">查无记录。</div><br/><div><div>点击“<span style=\"color:red;\">上传材料</span>”按钮 进行上传附件</div><div>点击“<span style=\"color:red;\">重新输入</span>”按钮，则重新输入<span style=\"color:red;\">新编号</span></div>");
				list.add(kv);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				rs.close();
				ps.close();
				if(!conn.isClosed()){
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return list;
	}*/
	
	/**
	 * 关闭资源
	 * @param input
	 */
	public void close(ByteArrayInputStream input){
		if(input!=null){
			try {
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
