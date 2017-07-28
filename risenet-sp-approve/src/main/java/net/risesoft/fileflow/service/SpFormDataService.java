package net.risesoft.fileflow.service;

import java.util.List;
import java.util.Map;

public interface SpFormDataService {

	/**
	 * 保存表单传过来的数据
	 * @param processInstanceId
	 * @param formId
	 * @param formJsonData
	 */
	void doSaveFormData(String processInstanceId, String formId,String formJsonData);
	
	/**
	 * 获取表单数据
	 * @param formIds
	 * @param processInstanceId
	 * @param tableNameList
	 * @return
	 */
	List<Map<String, Object>> getFormDatas(String formIds, String processInstanceId, List<String> tableNameList);
	
}
