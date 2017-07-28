/**

 * @Project Name:risenet-sp-approve
 * @File Name: SharestuffServiceImpl.java
 * @Package Name: net.risesoft.approve.service.impl
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年3月11日 下午2:40:39
 */
package net.risesoft.approve.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import net.risesoft.approve.entity.jpa.SPMwssbcl;
import net.risesoft.approve.entity.jpa.Stuffdata;
import net.risesoft.approve.entity.jpa.StuffdataDel;
import net.risesoft.approve.entity.jpa.StuffdataLog;
import net.risesoft.approve.entity.jpa.StuffdataXt;
import net.risesoft.approve.entity.jpa.StuffdataXtDel;
import net.risesoft.approve.entity.jpa.StuffdataXtLog;
import net.risesoft.approve.repository.jpa.SPMwssbclRepository;
import net.risesoft.approve.repository.jpa.StuffdataDelRepository;
import net.risesoft.approve.repository.jpa.StuffdataLogRepository;
import net.risesoft.approve.repository.jpa.StuffdataRepository;
import net.risesoft.approve.repository.jpa.StuffdataXtDelRepository;
import net.risesoft.approve.repository.jpa.StuffdataXtLogRepository;
import net.risesoft.approve.repository.jpa.StuffdataXtRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SharestuffService;
import net.risesoft.commons.util.GUID;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.FileUtil;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.StringX;

/**
 * @ClassName: SharestuffServiceImpl.java
 *
 * @author chenbingni
 * @date 2016年3月11日 下午2:40:39
 * @version 
 * @since JDK 1.7
 */
@Service(value = "sharestuffService")
public class SharestuffServiceImpl implements SharestuffService {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	} 
	
	@Autowired
	private StuffdataRepository stuffdataRepository;
	
	@Autowired
	private StuffdataXtRepository stuffdataXtRepository;
	
	@Autowired
	private StuffdataLogRepository stuffdataLogRepository;
	
	@Autowired
	private StuffdataXtLogRepository stuffdataXtLogRepository;
	
	@Autowired
	private StuffdataDelRepository stuffdataDelRepository;
	
	@Autowired
	private StuffdataXtDelRepository stuffdataXtDelRepository;
	
	@Autowired
	private SPMwssbclRepository spMwssbclRepository;
	
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;
	
	@Override
	public List<Map<String, Object>> findValidatasByInstanceguidAndDeclareannexguid(
			String instanceId, String declareannexguid) {
		List<Map<String, Object>> stuffdataList = new ArrayList<Map<String, Object>>();
		String sql = "select dat.guid, dat.stuffdataname, dat.state, to_char(dat.limitbegin, 'yyyy-mm-dd') limitbegin, "
				+ "to_char(dat.limitend, 'yyyy-mm-dd') limitend, dat.limitforever, dat.certifyperson, "
				+ "to_char(dat.certifytime, 'yyyy-mm-dd HH24:mi:SS') certifytime, dat.remark "
				+ "from office_spi_declareinfo o, ss_stuffdata dat, ss_stuffshareitemdef re "
				+ "where o.workflowinstance_guid=? and dat.ownerguid=o.zhengjiandaima "
				+ "and re.stuffdefineid=dat.stuffdefineid and re.listid=? and dat.state='2' "
				+ "order by dat.tabindex ";
		List<Object> params = new ArrayList<Object>();
		params.add(instanceId);
		params.add(declareannexguid);
		
		try {
			
			stuffdataList = jdbcTemplate.queryForList(sql, params.toArray());			
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("窗口获取有效共享材料异常", e);
		}
		
		return stuffdataList;
	}

	@Override
	public List<Map<String, Object>> findByInstanceguidAndDeclareannexguid(
			String instanceId, String declareannexguid) {
		List<Map<String, Object>> stuffdataList = new ArrayList<Map<String, Object>>();
		String sql = "select dat.guid, dat.stuffdataname, dat.state, to_char(dat.limitbegin, 'yyyy-mm-dd') limitbegin, "
				+ "to_char(dat.limitend, 'yyyy-mm-dd') limitend, dat.limitforever, dat.certifyperson, "
				+ "to_char(dat.certifytime, 'yyyy-mm-dd HH24:mi:SS') certifytime, dat.remark "
				+ "from office_spi_declareinfo o, ss_stuffdata dat, ss_stuffshareitemdef re "
				+ "where o.workflowinstance_guid=? and dat.ownerguid=o.zhengjiandaima "
				+ "and re.stuffdefineid=dat.stuffdefineid and re.listid=? "
				+ "order by dat.tabindex ";
		List<Object> params = new ArrayList<Object>();
		params.add(instanceId);
		params.add(declareannexguid);
		
		try {
			
			stuffdataList = jdbcTemplate.queryForList(sql, params.toArray());			
			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("窗口获取共享材料异常", e);
		}
		
		return stuffdataList;
	}


	@Override
	public List<Map<String, Object>> findXtByStuffguid(String stuffdataguid) {
		
		String sql = "select t.guid, t.stuffdataguid, t.filename, t.rootpath, "
				+ "t.wssbclguid, t.tableindex, t.linuxpath, t.uper, t.upertype, "
				+ "to_char(t.uptime, 'yyyy-mm-dd hh24:mi:ss') uptime from ss_stuffdataxt t "
				+ "where t.stuffdataguid=? "
				+ "order by t.tableindex";
		List<Object> params = new ArrayList<Object>();
		params.add(stuffdataguid);
		
		List<Map<String, Object>> listmap = jdbcTemplate.queryForList(sql, params.toArray());
		
		return listmap;
	}


	@Override
	public int updateStuffdataByColumnName(String guid, String columnName,
			String columnValue) {
		int result = 0;
		try {
			if(columnName.equalsIgnoreCase("stuffdataname")) {
				result = stuffdataRepository.updateStuffdataByName(guid, columnValue, getEmployeeInfo());
			}else if(columnName.equalsIgnoreCase("limitforever")) {
				result = stuffdataRepository.updateStuffdataByForever(guid, columnValue, getEmployeeInfo());
			}else if(columnName.equalsIgnoreCase("limitbegin")) {
				DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dfm.parse(columnValue);
				result = stuffdataRepository.updateStuffdataByBegin(guid, date, getEmployeeInfo());
			}else if(columnName.equalsIgnoreCase("limitend")) {
				DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dfm.parse(columnValue);
				result = stuffdataRepository.updateStuffdataByEnd(guid, date, getEmployeeInfo());
			}
			else if(columnName.equalsIgnoreCase("state")) {
				result = stuffdataRepository.updateStuffdataByState(guid, getEmployeeInfo());
			}
			if(result > 0) {
				writeStuffdataLog(guid);
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("保存共享材料信息失败", e);
		}
		return result;
	}
	
	public void writeStuffdataLog(String stuffdataguid) {
		try {
			Stuffdata stuffdata = stuffdataRepository.findByGuid(stuffdataguid);
			List<StuffdataLog> stufflogs = stuffdataLogRepository.fintVersion(stuffdataguid);
			int version = 1;
			if(stufflogs != null && stufflogs.size() > 1) {
				version  = stufflogs.get(0).getLognum() + 1;
			}
			
			StuffdataLog log = new StuffdataLog(stuffdata);
			log.setLognum( version);
			log.setCertifyperson(getEmployeeInfo());
			log.setGxtime(new Date());
		
		
			stuffdataLogRepository.save(log);
		} catch(Exception e) {
			e.printStackTrace();
			logger.debug("保存stuffdatalog表失败", e);
		}
		
	}


	@Override
	public int saveWuxiao(String stuffdataguid, String remark) {
		int result = 0;
		try {			
			result = stuffdataRepository.updateStuffdataByState(stuffdataguid, remark, getEmployeeInfo());
			if(result > 0) {
				writeStuffdataLog(stuffdataguid);
			}
		} catch(Exception e) {
			e.printStackTrace();
			logger.debug("更改stuffdata为无效失败", e);
		}
		return result;
	}

	public static String getEmployeeInfo() {
		//获取当前登录用户信息，拼接为验证者信息
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), ThreadLocalHolder.getPerson().getID());
		String bureau = org.getName();		
		String depart = person.getOrganization().getName();
		String certifyPerson = bureau + " " + depart + " " + person.getName();
		
		return certifyPerson;
	}


	@Override
	public List<Map<String, Object>> findBySbguidAndDeclareannexguid(
			String instanceguid, String declareannexguid) {
		List<Map<String, Object>> stuffdataList = new ArrayList<Map<String, Object>>();
		String sql = "select dat.guid, dat.stuffdataname, dat.state, to_char(dat.limitbegin, 'yyyy-mm-dd') limitbegin, "
				+ "to_char(dat.limitend, 'yyyy-mm-dd') limitend, dat.limitforever, dat.certifyperson, "
				+ "to_char(dat.certifytime, 'yyyy-mm-dd HH24:mi:SS') certifytime, dat.remark "
				+ "from office_spi_declareinfo o, ss_stuffdata dat, ss_stuffshareitemdef re "
				+ "where o.workflowinstance_guid=? and o.zhengjiandaima = dat.ownerguid "
				+ "and re.stuffdefineid=dat.stuffdefineid and re.listid=? order by dat.tabindex";
		List<Object> params = new ArrayList<Object>();
		params.add(instanceguid);
		params.add(declareannexguid);
		
		try {
			stuffdataList = jdbcTemplate.queryForList(sql, params.toArray());			
		}catch(Exception e) {
			e.printStackTrace();
			logger.error("网上收件获取共享材料异常", e);
		}
		
		return stuffdataList;
	}


	@Override
	@Transactional(readOnly = false)
	public void upload(MultipartFile file, String stuffdataguid) {
		if (file != null) {
			StuffdataXt xt = new StuffdataXt();
			xt.setFilename(file.getOriginalFilename());
			String rootpath = FileUtil.WINDOWSPREFIX + FileUtil.FILEPATH_STUFF + stuffdataguid + "\\";
			xt.setRootpath(rootpath);
			String linuxpath = FileUtil.LINUXPREFIX+FileUtil.FILEPATH_STUFF.replace("\\", "/") + stuffdataguid + "/";
			xt.setLinuxpath(linuxpath);			
			xt.setStuffdataguid(stuffdataguid);			
			xt.setUper(getEmployeeInfo());
			xt.setUpertype("2");
			xt.setUptime(new Date());			

			File targetFile = new File(rootpath + "\\" + file.getOriginalFilename()); 
			if (!targetFile.exists()) { 
				 targetFile.mkdirs();        
			}
			try {
				file.transferTo(targetFile);
				stuffdataXtRepository.save(xt);
				writeStuffdataLog(stuffdataguid);
				writeXtLog(xt.getStuffdataguid());
			}catch (IOException e) {
				e.printStackTrace();
				logger.debug("新增共享材料附件失败", e);
			}
		}
		
	}
	
	/**
	 * 
	  * @MethodName: writeXtLog
	  * @Description: 因只能对stuffdataxt进行插入和删除操作，所以xtlog表每次都要将正在使用的xt保存一次，
	  * 即每个stuffdataLog的最高版本=stuffdata，每个stuffdataxtLog的最高版本=stuffdataxt
	  * @param： (参数)
	  * @return void    返回类型
	  * @throws
	  * 
	  * @Author chenbingni
	  * @date 2016年3月28日  下午4:23:03
	 */
	public void writeXtLog(String stuffdataguid) {
		List<StuffdataLog> stuffdatalogList = stuffdataLogRepository.fintVersion(stuffdataguid);
		if(stuffdatalogList != null && stuffdatalogList.size() > 0) {
			StuffdataLog stuffdatalog = stuffdatalogList.get(0);
			//查找StuffdataXtLog同一stuffdatalogguid的最高版本号
//			List<StuffdataXtLog> xtLogList = stuffdataXtLogRepository.fintVersion(stuffdatalog.getGuid());
			Integer version = stuffdatalog.getLognum();
//			if(xtLogList != null && xtLogList.size()>0) {
//				version = xtLogList.get(0).getVersion()+1;
//			}
			List<StuffdataXt> xtList = stuffdataXtRepository.findXtByStuffguid(stuffdatalog.getZbguid());
			for(int i=0; i<xtList.size(); i++) {
				
				StuffdataXtLog xtLog = new StuffdataXtLog(xtList.get(i),stuffdatalog.getGuid(), version);
				//操作人和操作时间
				xtLog.setOperator(getEmployeeInfo());
				stuffdataXtLogRepository.save(xtLog);
			}
		}
	}


	@Override
	public boolean delXt(String xtguid) {
		//将Xt信息保存到XtDel表里
		StuffdataXt xt = stuffdataXtRepository.findByGuid(xtguid);
		if(xt == null) {
			return false;
		}
		String person = getEmployeeInfo();
		StuffdataXtDel xtDel = new StuffdataXtDel(xt, person, new Date());
		try {
			stuffdataXtDelRepository.save(xtDel);
		
			//删除stuffdataXt的该条记录
			stuffdataXtRepository.delete(xt);
			
			//生成一个新版本的stuffdatalog和stuffdataXtlog记录
			writeStuffdataLog(xt.getStuffdataguid());
			writeXtLog(xt.getStuffdataguid());
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("delXt异常", e);
			return false;
		}
		return true;
	}


	@Override
	public boolean delStuffdata(String stuffdataguid) {
		//将stuffdata信息保存到stuffdataDel表里
		Stuffdata stuffdata = stuffdataRepository.findByGuid(stuffdataguid);
		if(stuffdata == null) {
			return false;
		}
		StuffdataDel dataDel = new StuffdataDel(stuffdata, getEmployeeInfo(), new Date());
		try { 
			stuffdataDelRepository.save(dataDel);
		
			//删除其下的Xt
			List<StuffdataXt> xtList = stuffdataXtRepository.findXtByStuffguid(stuffdataguid);
			for(int i=0; i<xtList.size(); i++) {
				delXt(xtList.get(i).getGuid());
			}
			//删除stuffdata的该条记录
			stuffdataRepository.delete(stuffdata);
		} catch(Exception e) {
			e.printStackTrace();
			logger.debug("delStuffdata异常", e);
			return false;
		}
		return true;
	}
	
	@Override
	public void copyFromStuffToWssbcl(String instanceId,
			String declareannexguid) {
		try {
			//通过processInstanceId和DECLAREANNEXGUID获取认证有效的材料列表
			List<Map<String, Object>> stuffdataList = findValidatasByInstanceguidAndDeclareannexguid(instanceId,declareannexguid);
			for(int i=0; i<stuffdataList.size(); i++) {
				List<Map<String, Object>> dataXtList = findXtByStuffguid((String)stuffdataList.get(i).get("GUID"));
				//封装共享材料列表为合格的wssbcl数据
				for(int j=0; j<dataXtList.size(); j++) {
					SPMwssbcl wssbcl = new SPMwssbcl();
					wssbcl.setGuid(new GUID().toString());
					wssbcl.setWorkflowinstanceguid(instanceId);
					wssbcl.setApproveitemgguid(officeSpiDeclareinfoService.findByGuid(instanceId).getApproveitemguid());
					wssbcl.setDeclareannexguid(declareannexguid);
					wssbcl.setFilename((String)dataXtList.get(j).get("FILENAME"));
					wssbcl.setRootpath((String)dataXtList.get(j).get("ROOTPATH"));
					wssbcl.setLinuxpath((String)dataXtList.get(j).get("LINUXPATH"));
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");//小写的mm表示的是分钟  
					String dstr=(String)dataXtList.get(j).get("UPTIME");  
					java.util.Date date=sdf.parse(dstr);  
					wssbcl.setUpdatetime(date);
					wssbcl.setType("2");
					spMwssbclRepository.save(wssbcl);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("从共享材料复制到网上申报材料表失败", e);
		}
		
	}

	@Override
	public void download(String id, HttpServletResponse response,
			HttpServletRequest request){
		try {
			String filename="";
			File file = null;
			StuffdataXt xt = stuffdataXtRepository.findByGuid(id);
			String rootpath = xt.getRootpath()+xt.getFilename();
			String linuxpath = xt.getLinuxpath()+xt.getFilename();
			if(rootpath!=null && rootpath.length()>0){
				file = new File(rootpath);
			}else{
				file = new File(linuxpath);
			}
			InputStream input = new FileInputStream(file);  
		    byte[] data = IOUtils.toByteArray(input); 
		    if(request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
		    	filename = URLEncoder.encode(xt.getFilename(), "UTF-8");//IE浏览器
		    }else{//request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0
		    	filename = new String(xt.getFilename().getBytes("UTF-8"), "ISO8859-1");//火狐浏览器
		    }
		    response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=\""+filename+"\"" );
			response.setHeader("Content-Length", String.valueOf(file.length()));
			IOUtils.write(data, response.getOutputStream());
			response.flushBuffer();		
		} catch(Exception e) {
			e.printStackTrace();
			logger.debug("下载共享材料附件失败", e);
		}
		
	}

	@Override
	public int updateStuffdata(HttpServletRequest request) {
		try {
			
			Stuffdata data = stuffdataRepository.findByGuid(request.getParameter("guid"));
			data.setStuffdataname(request.getParameter("stuffdataname"));
			data.setLimitforever(request.getParameter("limitforever"));
			DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
			if( !StringX.isBlank(request.getParameter("limitbegin"))) {
				Date date = dfm.parse(request.getParameter("limitbegin"));
				data.setLimitbegin(date);
			}
			if(!StringX.isBlank(request.getParameter("limitend"))) {
				Date date = dfm.parse(request.getParameter("limitend"));
				data.setLimitend(date);
			}
			
			data.setState(request.getParameter("state"));
			data.setRemark(request.getParameter("remark"));
			data.setCertifyperson(getEmployeeInfo());
			data.setCertifytime(new Date());
			data.setTabindex(0);
			data.setSync("0");
			stuffdataRepository.save(data);
			
			//写日志
			writeStuffdataLog(data.getGuid());
			//升Xt的版本号
			writeXtLog(data.getGuid());
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("修改共享材料失败", e);
			return 0;
		}
		return 1;
	}

	@Override
	public void tongbu(String procedureName, String guid) {
		String callSql = null;
		if(guid == null || guid.equals("")) {
			callSql = "{call PACK_CLGX." + procedureName + "}";
		} else {
			callSql = "{call PACK_CLGX." + procedureName + "('"+guid + "')}";
		}
		try {
			jdbcTemplate.execute(callSql);
		}catch(Exception e) {
			e.printStackTrace();
			logger.debug("同步共享材料到外网失败：" + procedureName);
		}
		
	}
	
}

