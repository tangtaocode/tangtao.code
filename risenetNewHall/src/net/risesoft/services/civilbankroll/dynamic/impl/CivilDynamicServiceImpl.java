package net.risesoft.services.civilbankroll.dynamic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.Statistical;
import net.risesoft.beans.civilbankroll.CivilApplicationBean;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.civilbankroll.dynamic.ICivilDynamicService;
 
@Service 
public class CivilDynamicServiceImpl extends BaseDaoImpl<CivilApplicationBean>
		implements ICivilDynamicService {
	@Resource
	ISimpleJdbcDao<Statistical> simpleJdbcDao;
	@Resource
	ISimpleJdbcDao<CivilApplicationBean> civilApplicationDao;
	public List<CivilApplicationBean> homeDynamicList(int rowNum) {
		String sql = "  select a.SLBH,o.name xmlxname,a.xmname,q.USERNAME sbztid,a.fzr, "+
				" to_char(a.SSFBSJ, 'yyyy-MM-dd') createtimeStr,a.SSZT "+
				" from msxm_application a,(select t.name, p.id, t.id as projectId "+
				" from msxm_type t, msxm_type_project p where t.id = p.typeguid) o, view_user q "+
				" where q.guid = a.sbztid and a.SSSTATE = '1' and o.id = a.xmlxguid order by a.createtime desc";
		return civilApplicationDao.queryForRow(sql, new String[]{"0",rowNum+""}, CivilApplicationBean.class);
	}

	public Statistical getStatistical() {
		String finishSql = "select count(t.slbh) from msxm_application t  where ISFINISHED='1'";
		String receiveSql = "select count(t.slbh) from msxm_application t where 1=1 ";
		String todaySql = " and to_char(t.CREATETIME, 'yyyy-MM-dd') = to_char(sysdate, 'yyyy-MM-dd')";
		String monthSql = " and to_char(t.CREATETIME, 'yyyy-MM') = to_char(sysdate, 'yyyy-MM')";
		String sql = "select todayReceive,todayFinish,monthReceive,monthFinish,countReceive,to_char(countReceive-finishCount) as transactCount,finishCount "
				+ " from (select to_char(("
				+ receiveSql
				+ todaySql
				+ ")) as todayReceive,to_char(("
				+ finishSql
				+ todaySql
				+ ")) as todayFinish,"
				+ "to_char(("
				+ receiveSql
				+ monthSql
				+ ")) as monthReceive,to_char(("
				+ finishSql
				+ monthSql
				+ ")) as monthFinish,"
				+ "to_char(("
				+ receiveSql
				+ ")) as countReceive,to_char(("
				+ finishSql
				+ ")) as finishCount from dual)";
		Statistical stat = simpleJdbcDao.getBean(sql, Statistical.class);
		return stat;
	}

	public int getDataRows(String sql, Object[] obj) {
		return civilApplicationDao.countRows(sql, obj);
	}

	public List<CivilApplicationBean> moreDynamic(String sql, Object[] obj) {
		return civilApplicationDao.queryForRow(sql, obj, CivilApplicationBean.class);
	}
	
}
