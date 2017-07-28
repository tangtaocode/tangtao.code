package net.risesoft.approve.service.impl.chart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.service.chart.ChartService;
import net.risesoft.utilx.StringX;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 
 * 统计service类
 * @author Administrator
 *
 */
@Service(value="chartService")
public class ChartServiceImpl implements ChartService{
	
/*	@Autowired
	@Qualifier("defaultDataSource")
	private DataSource defaultDataSource;
*/	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Override
	public Pager bureauChartList(String bureauGuid,String year,String quart,String month,Pager pager) {
		List<Map<String,Object>> bureauList = new ArrayList<Map<String,Object>>();
		int pageNum = pager.getPageNo();
		int pageSize= pager.getPageSize();
		List<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT b.bureauName BUREAUNAME,b.bureauGUID field2,nvl(total,0) int1,nvl(s.bestnum,0) int2,nvl(s.goodnum,0) int3,nvl(s.badnum,0) int4,nvl(pjl,0) int5,(case  when total=0 or total is null then '0.00%' else to_char(round(nvl(pjl,0)/total*100,2),'fm9999990.00') || '%' end)  field3,(case  when pjl=0 or pjl is null then '0.00%' else to_char(round(bestnum/pjl*100,2),'fm9999990.00') || '%' end)  field4,(case when pjl = 0 or pjl is null then '0.00%' else to_char(round(badnum/pjl*100,2),'fm9999990.00') || '%' end)  field5,(case  when total=0 or total is null then 0 else round(nvl(pjl,0)/total*100,2) end) int6,(case  when pjl=0 or pjl is null then 0 else round(bestnum/pjl*100,2) end) int7,(case when pjl = 0 or pjl is null then 0 else round(badnum/pjl*100,2) end) int8 FROM ");
		sql.append("(select * from (select bureauguid,bureauname,BUREAUTABINDEX from spm_bureau b where b.flag='1' and b.isstreet=0 union select 'ALL','合计',999 from dual) where 1=1 ) b ");
		sql.append("LEFT OUTER JOIN (SELECT nvl(trim(bureauGUID),'ALL') bureauGUID, COUNT(distinct(t1.declaresn)) total,COUNT(CASE issenate WHEN '1' THEN 1 END) pjl, ");
		sql.append("COUNT(CASE satisfaction WHEN '1' THEN 1 END)AS bestnum,COUNT(CASE satisfaction WHEN '2' THEN 1 END)AS goodnum,COUNT(CASE satisfaction WHEN '3' THEN 1 END)AS badnum ");
		sql.append("FROM spm_senate t1 where t1.type in ('1','2','3','4','5') ");
		if(!StringX.isBlank(year)){
			sql.append(" and to_char(t1.declaredatetime,'YYYY')=?");
			param.add(year);
		}
		if(!StringX.isBlank(month)){
			sql.append(" and to_char(t1.declaredatetime,'MM')=?");
			param.add(month);
		}
		if(!StringX.isBlank(quart)){
			sql.append(" and to_char(t1.declaredatetime,'Q')=?");
			param.add(quart);
		}
		sql.append(" and trim(t1.bureauguid) in (select bureauguid from spm_bureau b where b.flag = '1' and b.isstreet = 0 )  ");
		sql.append(" GROUP BY rollup(bureauGUID)) s ON  b.bureauGUID=s.bureauGUID order by decode(s.bureauGUID,'ALL',-1,int6) desc");
		bureauList = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		pager.setTotalRows(bureauList.size());
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql.toString(), pageNum, pageSize),param.toArray()));
		//pager = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		return pager;
	}
	//获取部门业务统计柱状图数据
	@Override
	public List<Map<String, Object>> bureauChartGrahpic(String year,String quart,String month) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> bureauList = new ArrayList<Map<String,Object>>();
		StringBuffer sql = new StringBuffer();
		List<Object> param = new ArrayList<Object>();
		
        sql.append("SELECT b.bureauName bureauname,sum(nvl(total,0)) ywl FROM ");
        sql.append("(select * from (select bureauguid,bureauname,BUREAUTABINDEX from spm_bureau b where b.flag='1' and b.isstreet=0) where 1=1 ) b ");
        sql.append("LEFT OUTER JOIN (SELECT to_char(t1.DECLAREDATETIME,'YYYY') year,nvl(trim(bureauGUID),'ALL') bureauGUID, COUNT(distinct(t1.declaresn)) total,COUNT(CASE issenate WHEN '1' THEN 1 END) pjl, ");
        sql.append("COUNT(CASE satisfaction WHEN '1' THEN 1 END)AS bestnum,COUNT(CASE satisfaction WHEN '2' THEN 1 END)AS goodnum,COUNT(CASE satisfaction WHEN '3' THEN 1 END)AS badnum ");
        sql.append("FROM spm_senate t1 where t1.type in ('1','2','3','4','5')");
		if(!StringX.isBlank(year)){
			sql.append(" and to_char(t1.DECLAREDATETIME,'YYYY')=? ");
			param.add(year);
		}
		if(!StringX.isBlank(month)){
			sql.append(" and to_char(t1.declaredatetime,'MM')=?");
			param.add(month);
		}
		if(!StringX.isBlank(quart)){
			sql.append(" and to_char(t1.DECLAREDATETIME,'Q')=? ");
			param.add(StringX.getNumQuart(quart));
		}
        sql.append("and trim(t1.bureauguid) in (select bureauguid from spm_bureau b where b.flag = '1' and b.isstreet = 0 ) ");
        sql.append("GROUP BY rollup(bureauGUID),to_char(t1.DECLAREDATETIME,'YYYY')) s ON  b.bureauGUID=s.bureauGUID group by b.bureauName");
        try{
        	bureauList = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		}catch(Exception e){
			e.printStackTrace();
		}
		return  bureauList;
	}
	
	//获取季度业务统计趋势图数据
	@Override
	public List<Map<String, Object>> quarterlyData(String dataSource,String year) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> querterList = new ArrayList<Map<String,Object>>();
		List<Object> param = new ArrayList<Object>();
		if(StringX.isBlank(year)){
			//获取当前年份
			Date date = new Date();
			year = (Integer.parseInt(sdf.format(date))-1)+"";
		}
		param.add(year);
		StringBuffer sql = new StringBuffer();
		sql.append("select year, ");
		sql.append("SUM(CASE when annual.TIME = '1' then annual.COUNT else 0 end) spring, ");
		sql.append("SUM(CASE when annual.TIME = '2' then annual.COUNT else 0 end) summer, ");
		sql.append("SUM(CASE when annual.TIME = '3' then annual.COUNT else  0 end) autumn,");
		sql.append("SUM(CASE when annual.TIME = '4' then annual.COUNT else 0 end) winter ");
		sql.append("FROM (SELECT TO_CHAR(T.Slsj, 'YYYY') YEAR, TO_CHAR(T.Slsj, 'Q') TIME,COUNT(*) COUNT ");
		sql.append("FROM t_shouli T,spm_bureau b where   t.sljgzzjgdm=b.institutioncode(+) and TO_CHAR(T.Slsj, 'YYYY')=? ");
		if(!StringX.isBlank(dataSource)){
			sql.append("  and t.datasource=? ");
			param.add(dataSource);
		}
		sql.append("GROUP BY TO_CHAR(T.Slsj, 'Q'), TO_CHAR(T.Slsj, 'YYYY')) annual  group by year");
		try{
			querterList = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		}catch(Exception e){
			e.printStackTrace();
		}
		return querterList;
	}
	
	//获取网上办理率统计数据
	@Override
	public List<Map<String, Object>> doOnlineData(String dataSource,String year) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List<Map<String,Object>> querterList = new ArrayList<Map<String,Object>>();
		List<Object> param = new ArrayList<Object>();
		if(StringX.isBlank(year)){
			//获取当前年份
			Date date = new Date();
			year = (Integer.parseInt(sdf.format(date))-1)+"";
		}
		param.add(year);
		StringBuffer sql = new StringBuffer();
		sql.append("select year,to_char(decode(zong1,0,0,round(wang1 / zong1 * 100, 2))) spring,to_char(decode(zong2,0,0,round(wang2 / zong2 * 100, 2))) summer,to_char(decode(zong3,0,0,round(wang3 / zong3 * 100, 2))) autumn,to_char(decode(zong4,0,0,round(wang4 / zong4 * 100, 2))) winter ");
		sql.append("from (select year, ");
		sql.append("SUM(CASE when annual.TIME = '1' then annual.COUNT else 0 end) zong1,  ");
		sql.append("SUM(CASE when annual.TIME = '1' and annual.tjfs='1' then annual.COUNT else 0 end) wang1,");
		sql.append("SUM(CASE when annual.TIME = '2' then annual.COUNT else 0 end) zong2, ");
		sql.append("SUM(CASE when annual.TIME = '2' and annual.tjfs='1' then annual.COUNT else 0 end) wang2, ");
		sql.append("SUM(CASE when annual.TIME = '3' then annual.COUNT else  0 end) zong3, ");
		sql.append("SUM(CASE when annual.TIME = '3' and annual.tjfs='1' then annual.COUNT else 0 end) wang3,");
		sql.append("SUM(CASE when annual.TIME = '4' then annual.COUNT else 0 end) zong4 ,");
		sql.append("SUM(CASE when annual.TIME = '4' and annual.tjfs='1' then annual.COUNT else 0 end) wang4 ");
		sql.append("FROM (SELECT TO_CHAR(T.Slsj, 'YYYY') YEAR, TO_CHAR(T.Slsj, 'Q') TIME,COUNT(*) COUNT ,t.tjfs ");
		sql.append("FROM t_shouli T,spm_bureau b where   t.sljgzzjgdm=b.institutioncode(+) and TO_CHAR(T.Slsj, 'YYYY')=? ");
		if(!StringX.isBlank(dataSource)){
			sql.append("  and t.datasource=? ");
			param.add(dataSource);
		}
		sql.append("GROUP BY TO_CHAR(T.Slsj, 'Q'), TO_CHAR(T.Slsj, 'YYYY'),t.tjfs) annual group by year)");
		try{
			querterList = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		}catch(Exception e){
			e.printStackTrace();
		}
		return querterList;
	}

	@Override
	public List<Map<String, Object>> todoAndBanjieList(String bureau,String year,String quart,String manth) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append("select b.bureauname,sum((case when t.ywlsh in(select ywlsh from t_banjie) then 1 else 0 end)) done, ");
		sql.append("sum((case when t.ywlsh not in(select ywlsh from t_banjie) then 1 else 0 end)) todo ");
		sql.append("from t_shouli t,spm_bureau b where t.departid(+)=b.institutioncode ");
		
		if(!StringX.isBlank(year)){
			sql.append(" and TO_CHAR(T.Slsj, 'YYYY')=? ");
			param.add(year);
		}
		sql.append("group by b.bureauname ");
		try{
			list = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> senatePieList(String bureau, String year,
			String quart, String month) {
		List<Map<String,Object>> bureauList = new ArrayList<Map<String,Object>>();
		List<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT sum(CASE issenate WHEN '1' THEN 1 END) pjl, ");
		sql.append("sum(CASE satisfaction WHEN '1' THEN 1 END)AS bestnum,COUNT(CASE satisfaction WHEN '2' THEN 1 END)AS goodnum,sum(CASE satisfaction WHEN '3' THEN 1 END)AS badnum ");
		sql.append("FROM spm_senate t1 where t1.type in ('1','2','3','4','5') ");
		if(!StringX.isBlank(year)){
			sql.append(" and to_char(t1.declaredatetime,'YYYY')=?");
			param.add(year);
		}
		if(!StringX.isBlank(month)){
			sql.append(" and to_char(t1.declaredatetime,'MM')=?");
			param.add(month);
		}
		if(!StringX.isBlank(quart)){
			sql.append(" and to_char(t1.declaredatetime,'Q')=?");
			param.add(quart);
		}
		sql.append(" and trim(t1.bureauguid) in (select bureauguid from spm_bureau b where b.flag = '1' and b.isstreet = 0 )  ");
		bureauList = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		return bureauList;
	}

	@Override
	public List<Map<String, Object>> banjieLvData(String bureau, String year,
			String quart, String month) {
		List<Map<String,Object>> banjieList = new ArrayList<Map<String,Object>>();
		List<Object> param = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.bjs/s.sls from (select count(1) sls from t_shouli where 1=1");
		sql.append(") s,(select count(1) bjs from t_banjie b where 1=1");
		sql.append(")t ");
		banjieList = jdbcTemplate.queryForList(sql.toString(), param.toArray());
		return banjieList;
	}

	
	
}
