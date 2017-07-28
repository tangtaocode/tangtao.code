package net.risesoft.approve.service.impl;

import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.service.HistoryServiceI;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.util.ServiceUtil;
import net.risesoft.utilx.StringX;
import net.risesoft.util.ServiceUtil;

@Service
public class HistoryServiceImpl implements HistoryServiceI {
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	public List<Map<String,Object>> getZhengzhaoByInstanceid(String instanceId, String declareannexguid) {
		//可以用作该材料的证明文件的证照类型
		String doctypeSql = "select to_char(d.doctypeguid) doctypeguid from spm_declareannex d where d.declareannexguid=?";
		List<String> params = new ArrayList<String>();
		params.add(declareannexguid);
		String doctypes = (String)jdbcTemplate.queryForList(doctypeSql, params.toArray()).get(0).get("doctypeguid");
		
		//申请人证件号码
		String cardidSql = "select t.zhengjiandaima from office_spi_declareinfo t where t.workflowinstance_guid=? ";
		params.clear();
		params.add(instanceId);
		String cardid = (String)jdbcTemplate.queryForList(cardidSql, params.toArray()).get(0).get("zhengjiandaima");
		
		String sql = new String();
		sql = "select o.workflowinstance_guid, d.docname, d.docno, o.approveitemname approvename, o.declarerperson name, b.bureauname tel, "
				+ "substr(d.producedate,0 ,10) chengnuoriqi, d.validityperiod validityperiod "
				+ "from office_spi_declareinfo o, spm_bureau b, t_bdex_docinfo d "
				+ "where 1=1 and o.zhengjiandaima= ? and trim(o.bureauguid)=trim(b.bureauguid) and o.declaresn=d.businessid "
				+ "and instr(?, d.doctypeguid)>0 ";
		params.clear();
		params.add(cardid);
		params.add(doctypes);
		List<Map<String,Object>> dataList=jdbcTemplate.queryForList(sql,params.toArray());
		return dataList;
	}


	/*
	 * 查询历史档案
	 */
	public Pager getPage(String applyName, String approveName,Pager pager,String yujing){
		int pageNum = pager.getPageNo();
		int pageSize = pager.getPageSize();
		StringBuilder sql = new StringBuilder();
		List<String> params = new ArrayList<String>();
		String strsql = "select distinct t.workflowinstance_guid as gid,t.DECLAREDATETIME as declDate,f.adddate as addDate,t.DECLARERPERSON as name, t.DECLARERTEL as tel,t.DECLARERTYPE as type,t.ZHENGJIANDAIMA as code,t.APPROVEITEMNAME as approvName,Validityperiod as zzyxq from OFFICE_SPI_DECLAREINFO t , T_BDEX_CERTIFIATEINFO f where t.WORKFLOWINSTANCE_GUID=f.instanceguid   ";
		sql.append(strsql);

		/*}else if("1".equals(yujing)){
			sql.append("select distinct t.workflowinstance_guid as gid,t.DECLAREDATETIME as declDate,f.adddate as addDate,t.DECLARERPERSON as name, t.DECLARERTEL as tel,t.DECLARERTYPE as type,t.ZHENGJIANDAIMA as code,t.APPROVEITEMNAME as approvName from OFFICE_SPI_DECLAREINFO t left join T_BDEX_CERTIFIATEINFO f on t.WORKFLOWINSTANCE_GUID=f.instanceguid where  (f.ZZYXQ - sysdate)< 30 and (f.ZZYXQ - sysdate)> 0 ");
		}else if("2".equals(yujing)){
			sql.append("select distinct t.workflowinstance_guid as gid,t.DECLAREDATETIME as declDate,f.adddate as addDate,t.DECLARERPERSON as name, t.DECLARERTEL as tel,t.DECLARERTYPE as type,t.ZHENGJIANDAIMA as code,t.APPROVEITEMNAME as approvName from OFFICE_SPI_DECLAREINFO t left join T_BDEX_CERTIFIATEINFO f on t.WORKFLOWINSTANCE_GUID=f.instanceguid where  (f.ZZYXQ - sysdate)> 30 ");
		}else if("3".equals(yujing)){
			sql.append("select distinct t.workflowinstance_guid as gid,t.DECLAREDATETIME as declDate,f.adddate as addDate,t.DECLARERPERSON as name, t.DECLARERTEL as tel,t.DECLARERTYPE as type,t.ZHENGJIANDAIMA as code,t.APPROVEITEMNAME as approvName from OFFICE_SPI_DECLAREINFO t left join T_BDEX_CERTIFIATEINFO f on t.WORKFLOWINSTANCE_GUID=f.instanceguid where  (f.ZZYXQ - sysdate)< 0 ");
		}*/
		// 添加搜索条件
		if (approveName != null && !"".equals(approveName)) {
			sql.append(" and t.APPROVEITEMNAME like ?");
			params.add("%"+approveName+"%");
		}
		if (applyName != null && !"".equals(applyName)) {
			sql.append(" and t.DECLARERPERSON like ?");
			params.add("%"+applyName+"%");
		}if(yujing != null || !"".equals(yujing)){
			if("1".equals(yujing)){
				sql.append(" and to_date(f.Validityperiod, 'yyyy-mm-dd')  >= sysdate ");
			}if("2".equals(yujing)){
				sql.append(" and to_date(f.Validityperiod, 'yyyy-mm-dd')  < sysdate ");
			}
		}

	List<Map<String,Object>> dataList=jdbcTemplate.queryForList(sql.toString(),params.toArray());
	pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(), pageNum, pageSize),params.toArray()));
	pager.setTotalRows(dataList.size());
		return pager;
	}

	/*
	 * 证照资料查询
	 */
	public Pager selectPage(String applyName, String approveName, Pager pager,
			String serviceCode) {
		int pageNum = pager.getPageNo();
		int pageSize = pager.getPageSize();
		List<String> params = new ArrayList<String>();
		Person person = ThreadLocalHolder.getPerson();
		//RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,person.getID());
				String userid = person.getID();
				OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), userid);
		// 查询属于该科室或者部门的事项ID
//		String psql = "select distinct t.approveitemguid as ID  from SPM_APPROVEITEM t, spm_approveitem_depart d "
//				+ "where t.approveitemguid = d.approveitemguid and RTRIM(d.departmentguid)=? and RTRIM(d.bureauguid)=?";
//		params.add(person.getParentID());
//		params.add(org.getID());
		String psql = "select distinct a.approveitemguid as ID from spm_approveitem        a, xzql_xzsp_windowofitem s, "
				+ "xzql_xzsp_window       w, spm_bureau             d "
				+ "where a.approveitemguid = s.itemid and a.adminbureauguid = d.bureauguid "
				+ "and w.guid = s.windowguid and instr(w.windowusers, ?) > 0";
		params.add(person.getID());
		List<Map<String, Object>> strList = jdbcTemplate.queryForList(psql,
				params.toArray());
		StringBuilder strParam = new StringBuilder();
		strParam.append("( ");
		int i = 0;
		for (Map e : strList) {
			if (i == strList.size() - 1) {
				strParam.append("'" + e.get("ID") + "' )");
			} else {
				strParam.append("'" + e.get("ID") + "',");
				i++;
			}
		}
		// 查询证照
		List<String> params1 = new ArrayList<String>();
		String sql = "SELECT distinct b.GUID as guid,  t.approveitemname  as APPROVNAME,t.DECLARERLXRIDTYPE  as BUREAUNAME, t.DECLARESN  as SERVICECODE,"
				+ " t.DECLARERPERSON as NAME,b.DOCNO as CODE,t.declaredatetime as ADDDATE,t.EMPLOYEEDEPTNAME  as PERSON,b.PRODUCEORGAN as DEPT,"
				+ " b.PRODUCEDATE as CHENGNUORIQI,b.ID  as ID,b.INSTANCEGUID as INSTANCEGUID,b.VALIDITYPERIOD as VALIDITYPERIOD"
				+ " FROM OFFICE_SPI_DECLAREINFO t, T_BDEX_CERTIFIATEINFO b where t.WORKFLOWINSTANCE_GUID = b.instanceguid"
				+ "   and   t.APPROVEITEMGUID in "
				+ strParam.toString();
		// 添加搜索条件
		if (!StringX.isBlank(applyName)) {// 申请人姓名
			sql += " and t.DECLARERPERSON like ?";
			params1.add("%" + applyName + "%");
		}
		if (!StringX.isBlank(approveName)) {// 业务类型
			sql += " and t.approveitemname like ?";
			params1.add("%" + approveName + "%");
		}
		if (!StringX.isBlank(serviceCode)) {// 业务编号
			sql += " and t.DECLARESN like ?";
			params1.add("%" + serviceCode + "%");
		}
		sql += "  order by b.PRODUCEDATE desc ";
		List<Map<String, Object>> dataList = jdbcTemplate.queryForList(
				sql.toString(), params1.toArray());
		pager.setPageList(jdbcTemplate.queryForList(
				pager.setPageSql(sql.toString(), pageNum, pageSize),
				params1.toArray()));
		pager.setTotalRows(dataList.size());
		return pager;
	}

	// //导出
	// public String export(HttpServletRequest request,
	// HttpServletResponse response, String title, String[] hearderTitles,
	// String[] hearderFields, List exportType,
	// ExcelSheets excelSheets){
	// String sql="select distinct t.workflowinstance_guid as gid,"
	// + " t.DECLAREDATETIME       as declDate,"
	// +"  f.adddate               as addDate,"
	// +"  t.DECLARERPERSON        as name,"
	// +"  t.DECLARERTEL           as tel,"
	// +"  t.DECLARERTYPE          as type,"
	// +" t.ZHENGJIANDAIMA        as code,"
	// +" t.APPROVEITEMNAME       as approvName"
	// +" from OFFICE_SPI_DECLAREINFO t"
	// +" left join T_BDEX_CERTIFIATEINFO f"
	// + " on t.WORKFLOWINSTANCE_GUID = f.instanceguid"
	// +" where 1 = 1 and rownum<10";
	// exportType=jdbcTemplate.queryForList(sql.toString());
	// exportExcel(request,
	// response, title, hearderTitles, hearderFields, exportType,
	// excelSheets);
	// return null;
	// }
	// /*
	// * 导出excel
	// * 要求：表头hearderTitles与hearderFields顺序一致，例子{姓名,性别,生日}，{name,sex,birthday}
	// * @see
	// com.gb.sysmodule.service.BaseServiceI#exportExcel(javax.servlet.http.HttpServletRequest,
	// javax.servlet.http.HttpServletResponse, java.lang.String,
	// java.lang.String[], java.lang.String[], java.util.List)
	// */
	// public void exportExcel(HttpServletRequest request,
	// HttpServletResponse response, String title, String[] hearderTitles,
	// String[] hearderFields,
	// ExcelSheets excelSheets) {
	// // 第一步，创建一个webbook，对应一个Excel文件
	// HSSFWorkbook wb = new HSSFWorkbook();
	// // 第二步，在webbook中添加sheet,对应Excel文件中的sheet
	// for (String sheetName : excelSheets.getSheetNames()) {
	// HSSFSheet sheet = wb.createSheet(sheetName);
	// // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	// HSSFRow row = sheet.createRow(0);
	// // 第四步，创建单元格，并设置值表头 设置表头居中
	// HSSFCellStyle style = wb.createCellStyle();
	// style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	// HSSFCell cell;
	// for (int i = 0; i < hearderTitles.length; i++) {
	// cell = row.createCell(i);
	// cell.setCellValue(hearderTitles[i]);//设置单元格的值
	// cell.setCellStyle(style);
	// }
	// // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
	// Map<String, Object> rowMap;
	// for (int i = 0; i < excelSheets.getSheetData(sheetName).size(); i++) {
	// row = sheet.createRow(i + 1);
	// rowMap = (Map<String, Object>)
	// excelSheets.getSheetData(sheetName).get(i);
	// for (int j = 0; j < hearderFields.length; j++) {
	// row.createCell(j).setCellValue(rowMap.get(hearderFields[j]) == null ? ""
	// : rowMap.get(hearderFields[j]).toString());
	// }
	// }
	// }
	//
	// // 第六步文件
	// String sFileName = title + ".xls";
	// try {
	// response.setHeader("Content-Disposition", "attachment;filename="
	// .concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
	// response.setHeader("Connection", "close");
	// response.setHeader("Content-Type", "application/vnd.ms-excel");
	// wb.write(response.getOutputStream());
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /*
	// * 导出excel, 通过制定字段类型自动转换导出
	// * 要求：表头hearderTitles与hearderFields顺序一致，例子{姓名,性别,生日}，{name,sex,birthday}
	// * @see
	// com.gb.sysmodule.service.BaseServiceI#exportExcel(javax.servlet.http.HttpServletRequest,
	// javax.servlet.http.HttpServletResponse, java.lang.String,
	// java.lang.String[], java.lang.String[], java.util.List)
	// */
	// public void exportExcel(HttpServletRequest request,
	// HttpServletResponse response, String title, String[] hearderTitles,
	// String[] hearderFields, List exportType,
	// ExcelSheets excelSheets) {
	// // 第一步，创建一个webbook，对应一个Excel文件
	// HSSFWorkbook wb = new HSSFWorkbook();
	// // 第二步，在webbook中添加sheet,对应Excel文件中的sheet
	// for (String sheetName : excelSheets.getSheetNames()) {
	// HSSFSheet sheet = wb.createSheet(sheetName);
	// // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
	// HSSFRow row = sheet.createRow(0);
	// // 第四步，创建单元格，并设置值表头 设置表头居中
	// HSSFCellStyle style = wb.createCellStyle();
	// style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式
	// HSSFCell cell;
	// for (int i = 0; i < hearderTitles.length; i++) {
	// cell = row.createCell(i);
	// cell.setCellValue(hearderTitles[i]);//设置单元格的值
	// cell.setCellStyle(style);
	// }
	// // 第五步，写入实体数据 实际应用中这些数据从数据库得到，
	// Map<String, Object> rowMap;
	// for (int i = 0; i < excelSheets.getSheetData(sheetName).size(); i++) {
	// row = sheet.createRow(i + 1);
	// rowMap = (Map<String, Object>)
	// excelSheets.getSheetData(sheetName).get(i);
	// for (int j = 0; j < hearderFields.length; j++) {
	// row.createCell(j).setCellValue(rowMap.get(hearderFields[j]) == null ? ""
	// : getValueString(rowMap.get(hearderFields[j]), exportType.get(j)));
	// }
	// }
	// // 设置列宽为自适应，存在中文列宽不足的问题
	// for (int i = 0; i < hearderFields.length; i++) {
	// sheet.autoSizeColumn(i);
	// }
	// }
	//
	// // 第六步文件
	// String sFileName = title + ".xls";
	// try {
	// response.setHeader("Content-Disposition", "attachment;filename="
	// .concat(String.valueOf(URLEncoder.encode(sFileName, "UTF-8"))));
	// response.setHeader("Connection", "close");
	// response.setHeader("Content-Type", "application/vnd.ms-excel");
	// wb.write(response.getOutputStream());
	// } catch (UnsupportedEncodingException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// /**
	// * 根据传入的type类型转换field为对应的字符串
	// * 例：
	// * 传入类型为String，直接返回
	// * 传入类型为Map，field作为key，返回value
	// * 传入类型为Date，转换field为 “xxxx年xx月xx日” 返回
	// * @param field 需要转换的字符串
	// * @param type 转换类型
	// * @return
	// */
	// private String getValueString(Object field, Object type) {
	// if (type instanceof String) {
	// return ((String) field).toString();
	// }
	// if (type instanceof Date) {
	// Date date = new Date();
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	// try {
	// date = sdf.parse((String) field);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// sdf = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
	// return sdf.format(date);
	// }
	// if (type instanceof Map) {
	// return ((Map<String, String>) type).get((String) field);
	// }
	// return "";
	// }
	//
	// public void doGetAllData(){
	// final ExcelSheets excelSheets = new ExcelSheets();
	// // HqlFilter hqlFilter = new HqlFilter(getRequest());
	// // // 因为这里没有设置分页 所以导出全部数据
	// // java.util.List<TTaskInfo> task = service.findByFilter(hqlFilter);
	// // int rownum=task.size();
	//
	//
	// /**
	// * 使用封装的BaseAction中的getJsonByFilter获取json字符串，
	// * 避免Set集合中循环引用导致的无法获取到正确的json问题
	// */
	// // String exportData=getJsonByFilter(task, null, null);
	//
	// //if ((exportData != null)&&(!exportData.equals(""))) {
	// //List<Map<String, Object>>
	// ExpData=ObjMapListUtil.getList(exportData);//要导出的数据
	// String[] hearderTs =
	// {"业务名称","受理日期","扫描日期","申请人姓名","申请人证件号码","申请人证件类型","申请人联系电话"};
	// String[] hearderFs =
	// {"APPROVNAME","DECLDATE","ADDDATE","NAME","CODE","TYPE","TEL"};
	//
	// /**
	// * 定义导出的数据类型，
	// * ！@解决时间输出结果为长整形数值的问题
	// */
	// // List<Object> exportType = new ArrayList<Object>();
	// // exportType.add(new String()); //任务名称
	// // exportType.add(new String()); //任务描述
	// // exportType.add(new HashMap<String, String>(){ // 任务类型
	// // {
	// // put("1", "项目任务");
	// // put("2", "项目研发任务");
	// // put("3", "产品研发任务");
	// // put("4", "运维任务");
	// // put("5", "其他任务");
	// // }
	// // });
	// // exportType.add(new HashMap<String, String>(){ // 任务紧急程度
	// // {
	// // put("1", "普通");
	// // put("2", "加急");
	// // put("3", "特急");
	// // }
	// // });
	// // exportType.add(new HashMap<String, String>(){ // 任务状态
	// // {
	// // put("0", "办结");
	// // put("1", "已接收");
	// // put("2", "正常");
	// // put("3", "预警");
	// // put("4", "超期");
	// // put("7", "申请办结");
	// // put("8", "任务发送");
	// // put("9", "新任务");
	// // }
	// // });
	// // exportType.add(new Date()); // 计划开始时间
	// // exportType.add(new Date()); // 计划完成时间
	// // exportType.add(new String()); // 责任人
	// // exportType.add(new String()); // 任务发起人
	// // exportType.add(new Date()); // 任务发起时间
	// //excelSheets.putSheet("综合查询统计",ExpData);
	// // service.exportExcel(getRequest(), getResponse(),"综合查询统计", hearderTs,
	// hearderFs, exportType, excelSheets);
	// //
	// // service.exportExcel(getRequest(), getResponse(),"综合查询统计", hearderTs,
	// hearderFs, exportType, excelSheets);
	// }
	//
	

	/*
	 * 查找证照扫描信息
	 */
	public List<Map<String, Object>> getImage(String instanceGuid, Person person) {
		String sql = "select t.*, t.rowid from RISENET_FILE t where t.appinstguid =?";
		List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql,
				instanceGuid);
		// List<Map<String,Object>> returnData=new
		// ArrayList<Map<String,Object>>();
		// for(Map e : dataList){
		// Map<String,Object> h=new HashMap<String,Object>();
		// h.put(e.get("FILEGUID").toString(),"【"+e.get("REALFULLPATH").toString()+"】"+e.get("MAJORNAME").toString());
		// returnData.add(h);
		// }
		return dataList;
	}

	
	/*
	 * 查找证照扫描图片信息
	 */
	public String getPhoto(String instanceGuid, HttpServletResponse rep) {
		String sql = "select t.*, t.rowid from RISENET_FILE t where t.FILEGUID =?";
		List<Map<String, Object>> dataList = jdbcTemplate.queryForList(sql,
				instanceGuid);
		if (dataList.isEmpty()) {
			return null;
		} else {
			String a = dataList.get(0).get("REALFULLPATH").toString();
			String rout = a.substring(2, a.length()).replaceAll("/", "//");
			return rout;
		}
	}
	
	
	/**
	 * 查询证照信息统计分析
	 */
	public Pager getPageZzTongJi(Person person,Pager pager,String produceOrgan,String docTypeName,String nianfen){
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		StringBuilder sql=new StringBuilder();
		Calendar a=Calendar.getInstance();
		String sysnianfen = a.get(Calendar.YEAR) + "";
		if(nianfen!=null && !"".equals(nianfen)){
			sysnianfen = nianfen;
		}
		
		List<String> params = new ArrayList<String>();
			
			//params2.add("'"+sysnianfen+"%'");
		    params.add(sysnianfen+"-01");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-02");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-03");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-04");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-05");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-06");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-07");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-08");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-09");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-10");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-11");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-12");
			//params2.add("'"+sysnianfen+"%'");
			//params2.add("to_date('"+sysnianfen+"-01-01','YYYY-MM-DD')");
			//params2.add("to_date('"+sysnianfen+"-03-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
    		/*params2.add("to_date('"+sysnianfen+"-04-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-06-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
			params2.add("to_date('"+sysnianfen+"-07-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-09-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
			params2.add("to_date('"+sysnianfen+"-10-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-12-31','YYYY-MM-DD')");*/
			params.add(sysnianfen);
			//params2.add("'"+sysnianfen+"%'");
			//params2.add("'"+sysnianfen+"%'");
			//params2.add((String) e.get("GUID"));
			
				sql.append("select  distinct s.bureauname as produceorgan,s.bureauguid,s.bureausn,"+sysnianfen+" as nianfen,"
						+ "(select count(*)  from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid)) as yiyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as eryue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as sanyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as siyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as wuyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as liuyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as qiyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as bayue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as jiuyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as shiyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as shiyiyue,"
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as shieryue,"
						//一季度合计
						+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-01-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-03-31','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as yijidu,"
						//二季度合计
						+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-04-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-06-30','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as erjidu,"
						//三季度
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-07-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-09-30','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as sanjidu,"
						//四季度
						+ "(select count(*) from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-10-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-12-31','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as sijidu,"
					 	//年度合计
						+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where to_char(t.adddate,'YYYY') like ? and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as yearTotal,"
						//总合计
						+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where 1=1 and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as totalTotal"
					
						+ " from T_BDEX_DOCTYPE b ,spm_bureau s where   rtrim(b.bguid) = rtrim(s.bureauguid)");
				
				
				if(produceOrgan!=null && !"".equals(produceOrgan)){
					sql.append(" and rtrim(b.bguid) = ?");
					params.add(produceOrgan);
				}
				if(docTypeName!=null && !"".equals(docTypeName)){
					sql.append(" and b.guid = ?");
					params.add(docTypeName);
				}
				sql.append(" order by s.bureausn");
			
		
		List<Map<String,Object>> dataList=jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(), pageNum, pageSize), params.toArray()));
		pager.setTotalRows(dataList.size());
		return pager;
		
	}
	
	/**
	 * 查询证照信息统计分析TWO
	 */
	public Pager getPageZzTongJitwo(Person person,Pager pager,String produceOrgan,String docTypeName,String nianfen){
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		StringBuilder sql=new StringBuilder();
		Calendar a=Calendar.getInstance();
		String sysnianfen = a.get(Calendar.YEAR) + "";
		if(nianfen!=null && !"".equals(nianfen)){
			sysnianfen = nianfen;
		}
		
		List<String> params = new ArrayList<String>();
			
			//params2.add("'"+sysnianfen+"%'");
		    params.add(sysnianfen+"-01");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-02");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-03");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-04");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-05");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-06");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-07");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-08");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-09");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-10");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-11");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-12");
			//params2.add("'"+sysnianfen+"%'");
			//params2.add("to_date('"+sysnianfen+"-01-01','YYYY-MM-DD')");
			//params2.add("to_date('"+sysnianfen+"-03-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
    		/*params2.add("to_date('"+sysnianfen+"-04-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-06-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
			params2.add("to_date('"+sysnianfen+"-07-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-09-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
			params2.add("to_date('"+sysnianfen+"-10-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-12-31','YYYY-MM-DD')");*/
			params.add(sysnianfen);
			//params2.add("'"+sysnianfen+"%'");
			//params2.add("'"+sysnianfen+"%'");
			//params2.add((String) e.get("GUID"));
			sql.append("select  distinct s.bureauname as produceorgan,b.doctypename as docname,s.bureausn,"
					+ "(select count(*)  from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID) as yiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as eryue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as sanyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as siyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as wuyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as liuyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as qiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as bayue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as jiuyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as shiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as shiyiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where to_char(t.adddate,'YYYY-MM') = ? and t.doctypeguid =b.GUID ) as shieryue,"
					//一季度合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t  where t.adddate>= to_date('"+sysnianfen+"-01-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-03-31','YYYY-MM-DD') and  t.doctypeguid =b.GUID ) as yijidu,"
					//二季度合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t  where t.adddate>= to_date('"+sysnianfen+"-04-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-06-30','YYYY-MM-DD') and  t.doctypeguid =b.GUID) as erjidu,"
					//三季度
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where t.adddate>= to_date('"+sysnianfen+"-07-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-09-30','YYYY-MM-DD') and  t.doctypeguid =b.GUID) as sanjidu,"
					//四季度
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  where t.adddate>= to_date('"+sysnianfen+"-10-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-12-31','YYYY-MM-DD') and  t.doctypeguid =b.GUID) as sijidu,"
				 	//年度合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t where to_char(t.adddate,'YYYY') like ? and t.doctypeguid =b.GUID ) as yearTotal,"
					//总合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t where t.doctypeguid =b.GUID) as totalTotal"
					
					+ " from T_BDEX_DOCTYPE b ,spm_bureau s where   rtrim(b.bguid) = rtrim(s.bureauguid)");
				
				
				if(produceOrgan!=null && !"".equals(produceOrgan)){
					sql.append(" and rtrim(b.bguid) = ?");
					params.add(produceOrgan);
				}
				sql.append(" order by s.bureausn");
			
		
		List<Map<String,Object>> dataList=jdbcTemplate.queryForList(sql.toString(),params.toArray());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(), pageNum, pageSize), params.toArray()));
		pager.setTotalRows(dataList.size());
		return pager;
		
	}
	
	/*
	 *查询统计集合
	 */
	public List<Map<String,Object>> getTongJiList(String sysnianfen){

		List<String> params4 = new ArrayList<String>();
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-01%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-02%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-03%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-04%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-05%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-06%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-07%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-08%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-09%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-10%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-11%'");
		params4.add("'"+sysnianfen+"%'");
		params4.add("'"+sysnianfen+"-12%'");
		params4.add("'"+sysnianfen+"%'");
		//统计图
		String sql = "select x.PRODUCEORGAN,sum(x.yiyue) as YIYUE,sum(x.eryue) as ERYUE,sum(x.sanyue) as SANYUE,sum(x.siyue) as SIYUE,sum(x.wuyue) as WUYUE,sum(x.liuyue) as LIUYUE,sum(x.qiyue) as QIYUE,sum(x.bayue) as BAYUE,sum(x.jiuyue) as JIUYUE,sum(x.shiyue) as SHIYUE,sum(x.shiyiyue) as SHIYIYUE,sum(x.shieryue) as SHIERYUE from (select distinct t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as yiyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as eryue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as sanyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as siyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as wuyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as liuyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as qiyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as bayue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as jiuyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as shiyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as shiyiyue,"
				+ "(select count(*) from (select t.PRODUCEORGAN as produceorgan,T.ZZLX as ZZLX,t.DOCNAME as docname from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID where t.adddate like ?) p where t.adddate like ? and t.PRODUCEORGAN=p.produceorgan) as shieryue"

				
				+ " from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b on t.DOCTYPEGUID = b.GUID  where t.adddate like ?)x group by x.PRODUCEORGAN";
		
		return jdbcTemplate.queryForList(sql.toString(),params4.toArray());
	}
	
	
	/*
	 * 
	 * 得到需要导出的List
	 */
	public List<Map<String,Object>> getDaoChuList(String produceOrgan,String docTypeName,String nianfen,Person person){

		StringBuilder sql=new StringBuilder();
		Calendar a=Calendar.getInstance();
		String sysnianfen = a.get(Calendar.YEAR) + "";
		if(nianfen!=null && !"".equals(nianfen)){
			sysnianfen = nianfen;
		}
		
		List<String> params = new ArrayList<String>();
			
			//params2.add("'"+sysnianfen+"%'");
		    params.add(sysnianfen+"-01");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-02");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-03");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-04");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-05");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-06");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-07");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-08");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-09");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-10");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-11");
			//params2.add("'"+sysnianfen+"%'");
			params.add(sysnianfen+"-12");
			//params2.add("'"+sysnianfen+"%'");
			//params2.add("to_date('"+sysnianfen+"-01-01','YYYY-MM-DD')");
			//params2.add("to_date('"+sysnianfen+"-03-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
    		/*params2.add("to_date('"+sysnianfen+"-04-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-06-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
			params2.add("to_date('"+sysnianfen+"-07-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-09-31','YYYY-MM-DD')");
			//params2.add("'"+sysnianfen+"%'");
			params2.add("to_date('"+sysnianfen+"-10-01','YYYY-MM-DD')");
			params2.add("to_date('"+sysnianfen+"-12-31','YYYY-MM-DD')");*/
			params.add(sysnianfen);
			//params2.add("'"+sysnianfen+"%'");
			//params2.add("'"+sysnianfen+"%'");
			//params2.add((String) e.get("GUID"));

			
			sql.append("select  distinct s.bureauname as produceorgan,s.bureauguid,s.bureausn,"+sysnianfen+" as nianfen,"
					+ "(select count(*)  from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid)) as yiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as eryue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as sanyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as siyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as wuyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as liuyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as qiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as bayue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as jiuyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as shiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as shiyiyue,"
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t  left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid  where to_char(t.adddate,'YYYY-MM') = ?  and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as shieryue,"
					//一季度合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-01-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-03-31','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as yijidu,"
					//二季度合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-04-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-06-30','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as erjidu,"
					//三季度
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-07-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-09-30','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as sanjidu,"
					//四季度
					+ "(select count(*) from T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where t.adddate>= to_date('"+sysnianfen+"-10-01','YYYY-MM-DD') and t.adddate<= to_date('"+sysnianfen+"-12-31','YYYY-MM-DD') and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as sijidu,"
				 	//年度合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where to_char(t.adddate,'YYYY') like ? and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as yearTotal,"
					//总合计
					+ "(select count(*) from  T_BDEX_CERTIFIATEINFO t left join T_BDEX_DOCTYPE b1  on t.doctypeguid=b1.guid where 1=1 and rtrim(b1.bguid) = rtrim(s.bureauguid) ) as totalTotal"
					
					+ " from T_BDEX_DOCTYPE b ,spm_bureau s where   rtrim(b.bguid) = rtrim(s.bureauguid)");
				
			
			if(produceOrgan!=null && !"".equals(produceOrgan)){
				sql.append(" and rtrim(b.bguid) = ?");
				params.add(produceOrgan);
			}
			if(docTypeName!=null && !"".equals(docTypeName)){
				sql.append(" and b.guid = ?");
				params.add(docTypeName);
			}
			sql.append(" order by s.bureausn");
		
	
	List<Map<String,Object>> dataList=jdbcTemplate.queryForList(sql.toString(),params.toArray());

	return dataList;

	}
}
