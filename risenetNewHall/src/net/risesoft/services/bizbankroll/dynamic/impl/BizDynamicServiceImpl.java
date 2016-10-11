package net.risesoft.services.bizbankroll.dynamic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.bizbankroll.BizApplicationBean;
import net.risesoft.beans.bizbankroll.Statistical;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicService;
 
@Service 
public class BizDynamicServiceImpl extends BaseDaoImpl<BizApplicationBean>
		implements IBizDynamicService {
	@Resource
	ISimpleJdbcDao<Statistical> simpleJdbcDao;
	@Resource
	ISimpleJdbcDao<BizApplicationBean> bizApplicationDao;
	public List<BizApplicationBean> getHomeDynamicList(int rowNum) {
		String sql = "select  t.SLBH,t.pro_name,t.department_name zgbmid,t.createtime," +
					" t.pressor_state,q.username sbztid from ( "+  
        			" select distinct a.SLBH,a.pro_name,y.department_name,a.createtime,decode(a.pressor_state,'2','办理中','20', "+
        			" '部门初审','30','补齐补正','40','不予受理','50','不予扶持','60','科室评审','70','分管局领导审批','80', "+
        			" '局长审批','90','分管区领导审批','100','批准办结','办理中') as pressor_state,a.sbztid as userguid from "+ 
        			" zjfc_application a,(select case when t1.department_guid='{00000000-0000-0000-0000-000000000000}'  "+
        			" then t2.department_name else t1.department_name end as department_name,t2.department_guid  "+
        			" from RISENET_DEPARTMENT t1,RISENET_DEPARTMENT t2 where t1.department_guid=t2.superior_guid ) y "+ 
        			" where a.zgbmid=y.department_guid and a.pressor_state!='1') t,  "+ 
        			" ( select c.logonguid,c.ename as username from t_out_companyuser c union all "+ 
        			" select p.logonguid ,p.true_name as username from t_out_personuser p) q where q.logonguid=t.userguid order by t.createtime desc";
		return bizApplicationDao.queryForRow(sql, new String[]{"0",rowNum+""}, BizApplicationBean.class);
	}

	public Statistical getStatistical() {
		String finishSql = "select count(t.slbh) from zjfc_application t  where ISFINISHED='1'";
		String receiveSql = "select count(t.slbh) from zjfc_application t where 1=1 ";
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
		return bizApplicationDao.countRows(sql, obj);
	}

	public List<BizApplicationBean> getMoreDynamic(String sql, Object[] obj) {
		return bizApplicationDao.queryForRow(sql, obj, BizApplicationBean.class);
	}
	
}
