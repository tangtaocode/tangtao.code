package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import net.risesoft.approve.risefile.RiseFile;

public interface RisenetFileService {

	public void save(RiseFile riseFile);
	public List<Map<String, Object>> findByAppguidAndcheckBoxName(String checkBoxName,String appguid);
	public List<Map<String, Object>> findToDelete(String fileguid);
	public List<Map<String, Object>> find(String filename,String majorVersion,String appInstGUID);
	public int delete(String fileguid);
	public String returnDeptid(String userid);
	public String returnDeptName(String userid);
}
