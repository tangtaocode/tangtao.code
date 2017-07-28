package net.risesoft.approve.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.approve.entity.jpa.RisenetEmployeeUnits;
import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.service.RisenetFileService;
import net.risesoft.util.ServiceUtil;

@Service(value="risenetFileService")
public class RisenetFileServiceImpl implements RisenetFileService{
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	
	@Override
	public void save(RiseFile riseFile) {
		//List list = new ArrayList();
		String sql ="insert into RISENET_FILE(fileguid,filename,filenameext,titile,majorname,majorversion,minversion"
				+ ",appname,fileboxname,appinstguid,creatorguid,createdate,lastmodified,realfullpath"
				+ ",savetype,handles,filesize) values('"+riseFile.fileGUID+"','"+riseFile.filename+"','"+riseFile.fileNameExt+"',"
				+ "'"+riseFile.titile+"','"+riseFile.majorName+"',"+riseFile.majorVersion+","+riseFile.minVersion+","
				+ "'"+riseFile.appName+"','"+riseFile.fileboxName+"','"+riseFile.appInstGUID+"','"+riseFile.creatorGUID+"',to_date('"+riseFile.createDate+"','yyyy-mm-dd hh24:mi:ss'),"
				+ "to_date('"+riseFile.lastModified+"','yyyy-mm-dd hh24:mi:ss'),'"+riseFile.realFullPath+"','"+riseFile.saveType+"','"+riseFile.handles+"',"
				+ ""+riseFile.fileSize+")";
/*		list.add(riseFile.fileGUID);
		list.add(riseFile.filename);
		list.add(riseFile.fileNameExt);
		list.add(riseFile.titile);
		list.add(riseFile.majorName);
		list.add(riseFile.majorVersion);
		list.add(riseFile.minVersion);
		list.add(riseFile.appName);
		list.add(riseFile.fileboxName);
		list.add(riseFile.appInstGUID);
		list.add(riseFile.creatorGUID);
		list.add("to_date('"+riseFile.createDate+"','yyyy-mm-dd')");
		list.add("to_date('"+riseFile.lastModified+"','yyyy-mm-dd')");
		list.add(riseFile.realFullPath);
		list.add(riseFile.saveType);
		list.add(riseFile.handles);
		list.add(riseFile.fileSize);*/
		jdbcTemplate.update(sql);
	}

	@Override
	public List<Map<String, Object>> findByAppguidAndcheckBoxName(String checkBoxName, String appguid) {
		List list = new ArrayList();
		String sql = "select * from RISENET_FILE where fileboxname=? and appinstguid = ?";
		list.add(checkBoxName);
		list.add(appguid);
		return jdbcTemplate.queryForList(sql,list.toArray());
	}

	@Override
	public int delete(String fileguid) {
		List list = new ArrayList();
		String sql = "delete from RISENET_FILE where fileguid=?";
		list.add(fileguid);
		return jdbcTemplate.update(sql,list.toArray());
	}

	@Override
	public List<Map<String, Object>> findToDelete(String fileguid) {
		List list = new ArrayList();
		String sql = "select * from RISENET_FILE where fileguid=?";
		list.add(fileguid);
		return jdbcTemplate.queryForList(sql,list.toArray());
	}

	@Override
	public List<Map<String, Object>> find(String filename, String majorVersion,
			String appInstGUID) {
		List list = new ArrayList();
		String sql = "select * from RISENET_FILE where filename=? and majorVersion=? and appInstGUID = ?";
		list.add(filename);
		list.add(majorVersion);
		list.add(appInstGUID);
		return jdbcTemplate.queryForList(sql,list.toArray());
	}

	@Override
	public String returnDeptid(String userid) {
		RisenetEmployeeUnits risenetInfo = ServiceUtil.riseInfo(jdbcTemplate,userid);
		return risenetInfo.getDepartmentGuid();
	}

	@Override
	public String returnDeptName(String userid) {
		List list = new ArrayList();
		String deptid = returnDeptid(userid);
		String sql = "select distinct t.department_name from risenet_employee e,risenet_department t where e.department_guid=t.department_guid and t.department_guid=?";
		list.add(deptid);
		return jdbcTemplate.queryForObject(sql,list.toArray(),String.class);
	}

}
