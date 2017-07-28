package net.risesoft.fileflow.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.common.util.DateConverter;
import net.risesoft.common.util.RiseBpmUtil;
import net.risesoft.util.SysVariables;
import net.risesoft.fileflow.entity.jpa.FormDBTableBind;
import net.risesoft.fileflow.service.FormDBTableBindService;
import net.risesoft.fileflow.service.SpFormDataService;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Service;

@Service(value = "spFormDataService")
public class SpFormDataServiceImpl implements SpFormDataService {

	@Resource(name = "formDBTableBindService")
	private FormDBTableBindService formDBTableBindService;

	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	@Resource(name = "officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void doSaveFormData(String processInstanceId, String formId, String formJsonData) {
		List<FormDBTableBind> formDataKeyBindList = formDBTableBindService.findAllByFormId(formId);
		Map<String, Object> tempMap = formDBTableBindService.analyzeFormDataKeyBindList(formDataKeyBindList);
		Map<String, List<FormDBTableBind>> formDBTableBindMap = (Map<String, List<FormDBTableBind>>) tempMap.get("formDBTableBindMap");// 保存主表的数据绑定信息
		try {
			saveNormalDatas(processInstanceId, formDBTableBindMap, formJsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getFormDatas(String formIds, String processInstanceId, List<String> tableNameList) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(formIds)) {
			List<String> formIdList = RiseBpmUtil.stringToList(formIds, SysVariables.COMMA);
			for (String formId : formIdList) {
				List<FormDBTableBind> formDBTableBindList = formDBTableBindService.findAllByFormId(formId);
				Map<String, Object> tempMap = formDBTableBindService.analyzeFormDataKeyBindList(formDBTableBindList);
				Map<String, List<FormDBTableBind>> formDBTableBindMap = (Map<String, List<FormDBTableBind>>) tempMap.get("formDBTableBindMap");// 保存主表的数据绑定信息
				map.putAll(getNormalDatas(processInstanceId, formDBTableBindMap));
			}
		}
		listMap.add(map);
		return listMap;
	}

	public void saveNormalDatas(String processInstanceId, Map<String, List<FormDBTableBind>> formDataKeyBindMap, String formJsonData) throws Exception {
		JSONObject jsonObject = JSONObject.fromObject(formJsonData);
		for (String tableName : formDataKeyBindMap.keySet()) {
			List<FormDBTableBind> tempList = formDataKeyBindMap.get(tableName);
			List<List<Object>> columns = new ArrayList<List<Object>>();

			for (FormDBTableBind entity : tempList) {
				List<Object> tempList2 = new ArrayList<Object>();
				String columnName = entity.getColumnName();
				String fieldAlias = entity.getFieldAlias();
				Object o = jsonObject.get(fieldAlias);
				if (o != null && !"".equals(o)) {
					tempList2.add(columnName);
					tempList2.add(entity.getColumnType());
					tempList2.add(o);
					columns.add(tempList2);
				}
			}
			int count = getCount(tableName, processInstanceId);
			if (count == 0) {// count为0，说明之前没有数据存在则执行插入
				columns = genFixedColumns(columns, processInstanceId, 1);
				insertData(tableName, columns);
			} else if (count == 1) {
				updateData(processInstanceId, 1, tableName, columns);
			} else {
				throw new Exception("当前数据表中存在多条记录");
			}
		}
	}

	/**
	 * 获取指定数据表中指定processInstanceId对应的记录是否存在
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param processInstanceId
	 *            流程实例Id
	 * @return
	 */
	public int getCount(String tableName, String processInstanceId) {
		int count = 0;
		count = jdbcTemplate.queryForObject("select count(*) from " + tableName + " t where t.processInstanceId=?", Integer.class, processInstanceId);
		return count;
	}

	/**
	 * 生成常用的数据字段
	 * 
	 * @param columns
	 * @param processSerialNumber
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @return
	 */
	private List<List<Object>> genFixedColumns(List<List<Object>> columns, String processInstanceId, Integer tabIndex) {
		/*
		 * Date date = new Date(); SimpleDateFormat sdf = new
		 * SimpleDateFormat(SysVariables.DATETIME_PATTERN); String strDate =
		 * sdf.format(date); columns.add(addGernalData("createTime",
		 * SysVariables.TIMESTAMP, strDate));
		 */
		columns.add(addGernalData("processInstanceId", SysVariables.VARCHAR, processInstanceId));
		String WORKFLOWINSTANCE_GUID = officeSpiDeclareinfoService.getGuidByProcessInstanceId(processInstanceId);
		columns.add(addGernalData("WORKFLOWINSTANCE_GUID", SysVariables.VARCHAR, WORKFLOWINSTANCE_GUID));
		//columns.add(addGernalData("WORKFLOWINSTANCE_GUID", SysVariables.VARCHAR, GuidUtil.genGuid()));

		return columns;
	}

	private List<Object> addGernalData(String columnName, String columnType, Object columnValue) {
		List<Object> list = new ArrayList<Object>();
		list.add(columnName);
		list.add(columnType);
		list.add(columnValue);
		return list;
	}

	/**
	 * 插入数据到指定数据库表中
	 * 
	 * @param tableName
	 *            数据表名称
	 * @param columns
	 *            key是列的名称，value是列的值
	 */
	public void insertData(String tableName, List<List<Object>> columns) {
		String columnName = "";
		String columnValue = "";
		final List<List<Object>> columns2 = columns;
		for (List<Object> l : columns) {
			columnName = RiseBpmUtil.genCustomStr(columnName, (String) l.get(0));
			columnValue = RiseBpmUtil.genCustomStr(columnValue, "?");
		}
		String sql = "insert into " + tableName + "(" + columnName + ") values (" + columnValue + ")";
		jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				for (int i = 1; i <= columns2.size(); i++) {
					List<Object> l = columns2.get(i - 1);
					if (SysVariables.TIMESTAMP.equalsIgnoreCase((String) l.get(1)) || SysVariables.DATE.equalsIgnoreCase((String) l.get(1)) || SysVariables.TIME.equalsIgnoreCase((String) l.get(1)) || SysVariables.DATETIME.equalsIgnoreCase((String) l.get(1))) {
						String strDate = (String) l.get(2);
						DateConverter dateConverter = new DateConverter();
						Date date = (Date) dateConverter.convert(Date.class, strDate);
						ps.setDate(i, new java.sql.Date(date.getTime()));
						continue;
					}
					if (SysVariables.INTEGER.equalsIgnoreCase((String) l.get(1)) || SysVariables.DOUBLE.equalsIgnoreCase((String) l.get(1))) {
						Integer d = (Integer) l.get(2);
						ps.setInt(i, d);
						continue;
					}
					ps.setString(i, (String) l.get(2));
				}
			}
		});
	}

	/**
	 * 更新查询到的数据
	 * 
	 * @param processInstanceId
	 *            流程实例Id
	 * @param tabIndex
	 *            索引
	 * @param tableName
	 *            数据表名称
	 * @param columns
	 *            key是列的名称，value是列的值
	 */
	public void updateData(String processInstanceId, Integer tabIndex, String tableName, List<List<Object>> columns) {
		String str = "";
		final List<List<Object>> columns2 = columns;
		for (List<Object> l : columns) {
			str = RiseBpmUtil.genCustomStr(str, (String) l.get(0));
			str = RiseBpmUtil.genCustomStr(str, "?", SysVariables.EQUAL_SIGN);
		}
		if (StringUtils.isNotBlank(str) && StringUtils.isNotBlank(tableName)) {
			String sql = "update " + tableName + " set " + str + " where processInstanceId='" + processInstanceId+"'";
			jdbcTemplate.update(sql, new PreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps) throws SQLException {
					for (int i = 1; i <= columns2.size(); i++) {
						List<Object> l = columns2.get(i - 1);
						if (SysVariables.TIMESTAMP.equalsIgnoreCase((String) l.get(1)) || SysVariables.DATE.equalsIgnoreCase((String) l.get(1)) || SysVariables.TIME.equalsIgnoreCase((String) l.get(1)) || SysVariables.DATETIME.equalsIgnoreCase((String) l.get(1))) {
							String strDate = (String) l.get(2);
							DateConverter dateConverter = new DateConverter();
							Date date = (Date) dateConverter.convert(Date.class, strDate);
							ps.setDate(i, new java.sql.Date(date.getTime()));
							continue;
						}
						if (SysVariables.INTEGER.equalsIgnoreCase((String) l.get(1)) || SysVariables.DOUBLE.equalsIgnoreCase((String) l.get(1))) {
							Integer d = (Integer) l.get(2);
							ps.setInt(i, d);
							continue;
						}
						ps.setString(i, (String) l.get(2));
					}
				}
			});
		}

	}

	/**
	 * 保存表单字段与数据库表及其字段直接绑定的数据
	 * 
	 * @param processSerialNumber
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param formDataKeyBindMap
	 * @param formJsonData
	 * @throws Exception
	 */
	public Map<String, Object> getNormalDatas(String processInstanceId, Map<String, List<FormDBTableBind>> formDataKeyBindMap) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (String tableName : formDataKeyBindMap.keySet()) {
			List<FormDBTableBind> tempList = formDataKeyBindMap.get(tableName);
			list = getFormatDatas(processInstanceId, tableName, tempList);
			if (list != null && list.size() > 0) {
				result.putAll(list.get(0));
			}
		}
		return result;
	}

	/**
	 * 对数据进行查询并进行格式化
	 * 
	 * @param processSerialNumber
	 * @param tableName
	 * @param tempList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getFormatDatas(String processInstanceId, String tableName, List<FormDBTableBind> tempList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		List<Object> objectList = formDBTableBindService.getColumns(tempList);
		String columns = (String) objectList.get(0);
		Map<String, Object> formatMap = (Map<String, Object>) objectList.get(1);
		list = getDatas(processInstanceId, tableName, columns);

		List<Map<String, Object>> tempList2 = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			Map<String, Object> tempMap2 = new HashMap<String, Object>();
			for (FormDBTableBind entity : tempList) {
				String columnName = entity.getColumnName();
				String fieldAlias = entity.getFieldAlias();
				Object o = map.get(columnName);
				if (o != null && !"".equals(o)) {
					tempMap2.put(fieldAlias, o);
				} else {
					tempMap2.put(fieldAlias, "");
				}
			}
			tempList2.add(tempMap2);
		}

		list = formatDatas(formatMap, tempList2);
		return list;
	}

	/**
	 * 从数据库中查询出数据
	 * 
	 * @param processSerialNumber
	 * @param processDefinitionId
	 * @param processInstanceId
	 * @param tableName
	 * @param columns
	 * @return
	 */
	public List<Map<String, Object>> getDatas(String processInstanceId, String tableName, String columns) {
		String sql = "select " + columns + " from " + tableName + " where processInstanceId='" + processInstanceId + "'";
		List<Map<String, Object>> result = jdbcTemplate.queryForList(sql);
		return result;
	}

	/**
	 * 格式化查询出的数据，如日期型数据
	 * 
	 * @param formatMap
	 * @param list
	 * @return
	 */
	public List<Map<String, Object>> formatDatas(Map<String, Object> formatMap, List<Map<String, Object>> list) {
		List<Map<String, Object>> tempList=new ArrayList<Map<String,Object>>();
		if (formatMap.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> tempMap=list.get(i);
				Map<String, Object> tempMap1 = changeMapKey(tempMap);
				for (String key : formatMap.keySet()) {
					String format = (String) changeMapKey(formatMap).get(key.toLowerCase());
					if (SysVariables.TIMESTAMP.equalsIgnoreCase(format) || SysVariables.DATE.equalsIgnoreCase(format) || SysVariables.DATETIME.equalsIgnoreCase(format)) {
						Date date;
						if (tempMap1.get(key.toLowerCase()).equals("")) {
							continue;
						} else {
							date = (Date) tempMap1.get(key.toLowerCase());
						}
						DateConverter dateConverter = new DateConverter();
						String patten = "";
						switch (format) {
						case SysVariables.TIMESTAMP:
							patten = SysVariables.DATETIME_PATTERN;
							break;
						case SysVariables.DATE:
							patten = SysVariables.DATE_PATTERN;
							break;
						case SysVariables.DATETIME:
							patten = SysVariables.DATETIME_PATTERN;
							break;
						default:
							patten = SysVariables.DATETIME_PATTERN;
							break;
						}
						String strDate = (String) dateConverter.doConvertToString(date, patten);
						tempMap.put(key, strDate);
					}
				}
				tempList.add(tempMap);
			}
		}else{
			tempList=list;
		}
		return tempList;
	}
	
	private Map<String,Object> changeMapKey(Map<String,Object> map){
		Map<String,Object> tempMap=new HashMap<String, Object>();
		for(String key:map.keySet()){
			tempMap.put(key.toLowerCase(), map.get(key));
		}
		return tempMap;
	}

}
