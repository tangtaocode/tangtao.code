package net.risesoft.utils.base;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.risesoft.beans.base.CodeMap;
import net.risesoft.common.Common;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;

/**
 * <p>
 * Title: 通过类型取 key-value 对
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: 有生
 * </p>
 * 
 * @author ZK
 * @version 1.0
 */
@Service
public class CodeMapUtil extends BaseDaoImpl<CodeMap> implements ICodeMapUtil {
	@Autowired
	private ISimpleJdbcDao<CodeMap> simpleJdbcDao;
	private final Logger log = Logger.getLogger(CodeMapUtil.class);
	private static List<String> offDayList = new ArrayList<String>();
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static boolean flag = true;

	/**
	 * 初始化 code-value 的 Map
	 * 
	 * @throws Exception
	 */
	public void init() throws Exception {
		TreeMap<String, TreeMap<String, String>> tmCodeMapTemp = new TreeMap<String, TreeMap<String, String>>();
		TreeMap<String, List<CodeMap>> tmCodeListTemp = new TreeMap<String, List<CodeMap>>();
		TreeMap<String, String> tmCode = null;
		ArrayList<CodeMap> liCode = null;
		String sql = "select t.type,t.code, t.value from xzql_codemap t order by t.type,t.orderno";
		List<CodeMap> list = simpleJdbcDao.queryForRow(sql, CodeMap.class);
		for (CodeMap code : list) {
			if (tmCodeMapTemp.get(code.getType()) == null) {
				tmCode = new TreeMap<String, String>();
				liCode = new ArrayList<CodeMap>();
				tmCodeMapTemp.put(code.getType(), tmCode);
				tmCodeListTemp.put(code.getType(), liCode);
			}
			tmCodeMapTemp.get(code.getType()).put(code.getCode(),
					code.getValue());
			tmCodeListTemp.get(code.getType()).add(code);
		}

		// 系统职能局和街道GUID字典
		sql = "select distinct t.bureauguid as code,t.bureaucnfullname as value,"
				+ "t.bureautabindex from spm_bureau t, xzql_xzsp_base o,xzql_xzsp_extend e "
				+ "where t.flag='1' and e.itemid=o.itemid and e.approveitemstatus='运行' "
				+ "and o.adminorgid = t.bureauguid  order by t.bureautabindex";
		setOtherCodeMap("罗湖区审批职能局和街道", sql, tmCodeMapTemp, tmCodeListTemp);

		// 系统职能局GUID字典
		sql = "select distinct t.bureauguid as code,substr(t.bureaucnfullname,0,18)  as value,t.bureautabindex "
				+ "from spm_bureau t, xzql_xzsp_base o,xzql_xzsp_extend e where t.isstreet = '0'  "
				+ "and e.itemid=o.itemid and e.approveitemstatus='运行' and e.isprovince = '1' and o.adminorgid = t.bureauguid "
				+ "and t.flag='1' order by t.bureautabindex";
		setOtherCodeMap("罗湖区审批职能局", sql, tmCodeMapTemp, tmCodeListTemp);

		// 罗湖区审批部门GUID字典
		sql = "select t.department_guid as code,t.department_name as value from risenet_department t order by t.tabindex";
		setOtherCodeMap("罗湖区审批部门", sql, tmCodeMapTemp, tmCodeListTemp);
		
		// 罗湖区审批部门GUID字典
		sql = "select t.bureauguid as code,decode(t.bureaucnfullname,null,t.bureauname,t.bureaucnfullname)as value from spm_bureau t where t.flag='1' order by t.bureautabindex";
		setOtherCodeMap("罗湖区职能局和街道全称或简称", sql, tmCodeMapTemp, tmCodeListTemp);

		// 系统职能局和街道机构代码字典
		sql = "select t.bureacode as code,b.bureaucnfullname as value from t_bdex_bureacode t,spm_bureau b "
				+ "where t.bureaguid=b.bureauguid and flag='1'  order by b.bureautabindex";
		setOtherCodeMap("罗湖区审批职能局和街道机构代码", sql, tmCodeMapTemp, tmCodeListTemp);

		// 系统职能局和街道机构代码字典
		sql = "select distinct t.bureacode as code,substr(b.bureaucnfullname,0,18) as value,b.bureautabindex from t_bdex_bureacode t,spm_bureau b ,"
				+ " xzql_xzsp_base o,xzql_xzsp_extend e where t.bureaguid=b.bureauguid"
				+ " and o.adminorgid=b.bureauguid   and e.itemid(+)=o.itemid   and e.approveitemstatus='运行'   and e.isprovince='1'"
				+ " and b.isstreet = '0' and b.flag='1'  order by b.bureautabindex";
		setOtherCodeMap("罗湖区审批职能局机构代码", sql, tmCodeMapTemp, tmCodeListTemp);

		// 加载文件上传和下载资源文件信息
		getMapFromProperties("downFileTypeData.properties", tmCodeMapTemp);

		getMapFromProperties("dataSource.properties", tmCodeMapTemp);

		Common.tmKeyCodeMap = tmCodeMapTemp;
		Common.tmKeyCodeList = tmCodeListTemp;
		if (log.isDebugEnabled())
			log.debug("系统数据字典加载完毕！");

	}

	protected void setOtherCodeMap(String type, String sql,
			TreeMap<String, TreeMap<String, String>> map,
			TreeMap<String, List<CodeMap>> listMap) {
		List<CodeMap> list = simpleJdbcDao.queryForRow(sql, CodeMap.class);
		TreeMap<String, String> tmCode = new TreeMap<String, String>();
		for (CodeMap code : list) {
			tmCode.put(code.getCode(), code.getValue());
		}
		listMap.put(type, list);
		map.put(type, tmCode);
	}

	public TreeMap<String, String> getKeyValueMap(String sql, Object[] params) {
		List<CodeMap> list = simpleJdbcDao.queryForRow(sql, params,
				CodeMap.class);
		TreeMap<String, String> map = new TreeMap<String, String>();
		for (CodeMap code : list) {
			map.put(code.getCode(), code.getValue());
		}
		return map;

	}

	public List<CodeMap> getCodeMapList(String sql) {
		return simpleJdbcDao.queryForRow(sql, CodeMap.class);
	}

	public List<CodeMap> getCodeMapList(String sql, String[] parem) {
		return simpleJdbcDao.queryForRow(sql, parem, CodeMap.class);
	}

	/**
	 * 通过类型获取 code-value 的 Map
	 * 
	 * @param pType
	 *            String 类型
	 * @return TreeMap
	 * @throws Exception
	 */
	public TreeMap<String, String> getMapByType(String pType) {
		try {
			if (Common.tmKeyCodeMap == null) {
				init();
			}
			if (Common.tmKeyCodeMap.containsKey(pType)) {
				return Common.tmKeyCodeMap.get(pType);
			} else {
				if (log.isDebugEnabled())
					log.error("通过类型 " + pType + " 未能找到对应的 code-value 对");
				throw new Exception("通过类型 " + pType + " 未能找到对应的 code-value 对");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// 加载src根目录资源文件，其他目录不适用
	protected void getMapFromProperties(String propertiesFileName,
			TreeMap<String, TreeMap<String, String>> tmCodeMapTemp) {
		Properties prop = new Properties();
		TreeMap<String, String> map = new TreeMap<String, String>();
		try {
			prop.load(this.getClass().getResourceAsStream(
					"/" + propertiesFileName));
			Set<Entry<Object, Object>> entry = prop.entrySet();
			for (Entry<Object, Object> en : entry) {
				map.put((String) en.getKey(), (String) en.getValue());
			}
			tmCodeMapTemp.put(propertiesFileName, map);
		} catch (IOException e) {
			if (log.isDebugEnabled())
				log.debug("加载文件下载资源文件downFileTypeData.properties错误，信息"
						+ e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public List<CodeMap> getListByType(String type) {
		try {
			if (Common.tmKeyCodeList == null) {
				init();
			}
			if (Common.tmKeyCodeList.containsKey(type)) {
				return Common.tmKeyCodeList.get(type);
			} else {
				if (log.isDebugEnabled())
					log.error("通过类型 " + type + " 未能找到对应的 list 对");
				throw new Exception("通过类型 " + type + " 未能找到对应的 list对");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 受理单编号自动生成规则： 为了考虑从触摸屏输入方便，以及今后利用小键盘进行满意度的评议，受理单编号应该全部采用数字进行编号，规则如下：
	 * 受理单编号总长度为12位，采用定长编码， 1、 1-2位，委办局唯一编码，对应SPM_Bureau
	 * 表BureauSN字段。从可读性的角度来说，应该保留委办局的唯一编码。 2、
	 * 3-8位，年月日标识，如：050713，为了使受理单编号尽可能的短，年为2位，从本系统的使用寿命来说，完全没有千年虫问题。采用年月日标识是为了提高受理单编号的可读性，方便进行查询，所以没有采用8位时间戳。
	 * 3、
	 * 9-12位，当天的事件流水号，从0001开始顺序生成，作废不补，允许中间跳号。按照每个委办局走流水，不是大流水，否则委办局唯一编码的意义就不是很大了。
	 */
	public String getDeclaresnByBureauGuid(String bureauGuid) {
		String bureauStr = "";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String datestr = sdf.format(date).substring(2);
		String sql = "select to_char(t.bureausn) code,t.bureauname value from spm_bureau t where t.bureauguid=?";
		CodeMap code = simpleJdbcDao.getBean(sql, new String[] { bureauGuid,
				"-1", "-1" }, CodeMap.class);
		int bureauSn = 0;
		if (code != null)
			bureauSn = Integer.parseInt(code.getCode());
		if (bureauSn < 10) {
			bureauStr = "0" + bureauSn;
		} else {
			bureauStr = Integer.toString(bureauSn);
		}
		StringBuffer result = new StringBuffer(bureauStr);
		result.append(datestr);
		String serialStr = "";
		int serialnumber = 1;
		sql = "select to_char(SERIALNUMBER) code,'1' value from SPI_BUREAUSERIALNUMBERS where BUREAUSN=? and CURRENTDATE=?";
		code = simpleJdbcDao.getBean(sql, new String[] { bureauSn + "",
				datestr, "-1", "-1" }, CodeMap.class);
		if (code != null) {
			serialnumber = Integer.parseInt(code.getCode()) + 1;
			sql = "update SPI_BUREAUSERIALNUMBERS set SERIALNUMBER="
					+ serialnumber + " where BUREAUSN=" + bureauSn
					+ " and CURRENTDATE='" + datestr + "'";
			simpleJdbcDao.executeSql(sql);
		} else
			simpleJdbcDao
					.executeSql("insert into SPI_BUREAUSERIALNUMBERS (BUREAUSN,CURRENTDATE,SERIALNUMBER) values("
							+ bureauSn
							+ ",'"
							+ datestr
							+ "',"
							+ serialnumber
							+ ")");
		// 此处已经修改：wqb
		if (serialnumber < 10) {
			serialStr = "000" + serialnumber;
		} else if (serialnumber >= 10 && serialnumber < 100) {
			serialStr = "00" + serialnumber;
		} else if (serialnumber >= 100 && serialnumber < 1000) {
			serialStr = "0" + serialnumber;
		} else {
			serialStr = "" + serialnumber;
		}
		result.append(serialStr);
		return new String(result);
	}

	/**
	 * 计算某天加上day个工作日后得到的日期 可以向前或者向后
	 * 
	 * @param beginDate
	 * @param day
	 * @return
	 */
	public Date caculateEndDate(Date beginDate, int day) {
		try {
			if (day == 0)
				return beginDate;
			Date endDate = add(beginDate, day);

			int offDays = 0;
			if (day > 0)
				offDays = caculateOffDayNum(add(beginDate, 1), endDate);
			else
				offDays = caculateOffDayNum(endDate, add(beginDate, -1));

			if (day < 0)
				offDays = -offDays;

			if (offDays == 0)
				return endDate;
			else
				return caculateEndDate(endDate, offDays);
		} catch (Exception e) {
			if(log.isDebugEnabled())log.debug("日期处理错误："+e.getMessage());
			return null;
		}
	}

	/**
	 * 计算两个日期之间的非工作日天数
	 * 
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private int caculateOffDayNum(Date beginDate, Date endDate) throws Exception {
		int offDays = 0;
		if (flag)
			initOffDayList();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Timestamp fromD = truncTimestampToDay(Timestamp.valueOf(format
				.format(beginDate)));
		Timestamp toD = truncTimestampToDay(Timestamp.valueOf(format
				.format(endDate)));
		if ((fromD.equals(toD)) || (fromD.before(toD))) {
			cal.setTime(beginDate);
			while (cal.getTime().getTime() <= endDate.getTime()) {
				String date = sdf.format(cal.getTime());
				if (offDayList.contains(date))
					offDays++;
				cal.add(Calendar.DATE, 1);
			}
		} else {

			cal.setTime(endDate);
			while (cal.getTime().getTime() <= beginDate.getTime()) {
				String date = sdf.format(cal.getTime());
				if (offDayList.contains(date))
					offDays++;
				cal.add(Calendar.DATE, 1);
			}
			offDays = -offDays;
		}

		return offDays;

	}

	private void initOffDayList() throws Exception {
		offDayList.clear();
		String sql = "select t.yearmonthsetting code,t.daysetting value from Office_Risecalendarsetting t "
				+ "where t.risecalendarguid='{AC1D3317-0000-0000-30C5-5F7000000BE0}'";
		List<CodeMap> codeList = getCodeMapList(sql);
		List<String> list1 = getStringList(codeList, 1);
		List<String> list2 = getStringList(codeList, 2);
		for (int i = 0; i < list1.size(); i++) {
			String[] ym = ((String) list1.get(i)).split("-");
			String ymd = "";
			if (ym[1].length() == 1)
				ymd = ym[0] + "-0" + ym[1];
			else
				ymd = ym[0] + "-" + ym[1];
			for (int j = 0; j < list2.size(); j++) {
				String[] d = ((String) list2.get(i)).split(",");
				for (int k = 0; k < d.length; k++) {
					if (d[k].length() == 1)
						offDayList.add(ymd + "-0" + d[k]);
					else
						offDayList.add(ymd + "-" + d[k]);
				}
			}
		}
		flag = false;

	}

	private List<String> getStringList(List<CodeMap> codeList, int i)
			{
		List<String> list = new ArrayList<String>();
		for (CodeMap c : codeList) {
			if (i == 1) {
				list.add(c.getCode());
			} else {
				list.add(c.getValue());
			}
		}
		return list;
	}

	/**
	 * 日期后跳day天
	 * 
	 * @param beginDate
	 * @param day
	 * @return
	 */
	private Date add(Date beginDate, int day) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		cal.add(Calendar.DATE, day);
		return cal.getTime();
	}

	private Timestamp truncTimestampToDay(Timestamp timestamp)
			throws Exception {
		String tmp = convertTimestampToString(timestamp, "yyyyMMdd");
		Timestamp ret = convertStringToTimestamp(tmp, "yyyyMMdd");
		return ret;
	}

	private String convertTimestampToString(Timestamp pTimestamp,
			String pFormat) throws Exception {
		if (pTimestamp == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(pFormat);
		return format.format(pTimestamp);
	}

	private Timestamp convertStringToTimestamp(String strDate,
			String sFormat) throws Exception {
		if ((strDate == null) || (strDate.equals(""))) {
			return null;
		}
		Timestamp t = null;
		SimpleDateFormat format = new SimpleDateFormat(sFormat);
		t = new Timestamp(format.parse(strDate).getTime());
		return t;
	}

	@Override
	public String findSlbh(String typeGuid) {
		String sql = "select FUN_GETZJFCYWLSH(?) valCount from dual";
		return (String) findObject(sql, new String[]{typeGuid}, Hibernate.STRING);
	}
}
