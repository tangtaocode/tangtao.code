package net.risesoft.services.interaction.impl;

import java.util.List;

import javax.annotation.Resource;

import net.risesoft.beans.onlineservice.BusinessInfo;
import net.risesoft.beans.onlineservice.ProjectInfo;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.interaction.IDynamicStateService;

import org.springframework.stereotype.Service;
/**
 * @办事动态服务类
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Service
public class DynamicStateServiceImpl implements IDynamicStateService {
	@Resource
	private ISimpleJdbcDao<BusinessInfo> simpleJdbcDao;
	@Resource
	private ISimpleJdbcDao<ProjectInfo> simpleJdbcDao1;
	
	public List<BusinessInfo> getMoreDynamic(String sql,Object[] param) {
		List<BusinessInfo> list = simpleJdbcDao.queryForRow(sql, param,BusinessInfo.class);
		return list;
	}
	@SuppressWarnings("unchecked")
	public List<BusinessInfo> getHomeDynamic(int rowNum) {
		String sql = "select distinct t.yxtywlsh,  t.sljgmc, t.spsxmc, "
			+ " t.sqdwhsqrxm, to_char(t.slsj, 'yyyy-MM-dd') slsj, "
			+ " decode(b.bjjg,'0', '出证办结', '1',  '退回办结','2', '作废办结', '3', " 
			+ " '删除办结',  '4',  '转报办结', '5', "
			+ " '补交不来办结',  null,  '办理中',  '办结') blzt "
			+ " from (select *  from t_shouli s  where not exists (select 1 "
			+ " from t_shouli l where s.sjbbh < l.sjbbh "
			+ " and s.ywlsh = l.ywlsh)) t, office_spi_declareinfo d, "
			+ " T_BANJIE b  where d.declaresn(+) = t.yxtywlsh  and b.ywlsh(+) = t.ywlsh "
			+ " and t.yxtywlsh not in (select s.declaresn from spi_supervise_back s) " 
			+ " order by slsj desc";
		List<BusinessInfo> list = simpleJdbcDao.queryForRow(sql, new String[]{"1",rowNum+""},BusinessInfo.class);
		return list;
	}
	
	/**
	 * 查询办结结果
	 * @param rowNum
	 * @return 办结结果
	 * @author hjl
	 * @serialData 2013-09-16
	 */
	public List<BusinessInfo> getResultPublicity(String sql,Object[] params) {
//		String sql = "select t.yxtywlsh,  t.sljgmc, t.spsxmc, "
//			+ " t.sqdwhsqrxm, to_char(t.slsj, 'yyyy-MM-dd') slsj, "
//			+ " decode(b.bjjg,'0', '出证办结', '1',  '退回办结','2', '作废办结', '3', " 
//			+ " '删除办结',  '4',  '转报办结', '5', "
//			+ " '补交不来办结',  null,  '办理中',  '办结') blzt "
//			+ " from (select *  from t_shouli s  where not exists (select 1 "
//			+ " from t_shouli l where s.sjbbh < l.sjbbh "
//			+ " and s.ywlsh = l.ywlsh)) t, office_spi_declareinfo d, "
//			+ " T_BANJIE b  where d.declaresn(+) = t.yxtywlsh  and b.ywlsh(+) = t.ywlsh "
//			+ " and t.yxtywlsh not in (select s.declaresn from spi_supervise_back s) " 
//			+ " and b.bjjg is not null "
//			+ " order by t.slsj desc";
		List<BusinessInfo> list = simpleJdbcDao.queryForRow(sql,params,BusinessInfo.class);
		return list;
	}
	
	/**
	 * 查询项目信息
	 * @param rowNum
	 * @return 项目
	 * @author hjl
	 * @serialData 2013-09-16
	 */
	public List<ProjectInfo> getResultProject(String sql,Object[] params) {
//		String sql = "select t.yxtywlsh,  t.sljgmc, t.spsxmc, "
//			+ " t.sqdwhsqrxm, to_char(t.slsj, 'yyyy-MM-dd') slsj, "
//			+ " decode(b.bjjg,'0', '出证办结', '1',  '退回办结','2', '作废办结', '3', " 
//			+ " '删除办结',  '4',  '转报办结', '5', "
//			+ " '补交不来办结',  null,  '办理中',  '办结') blzt "
//			+ " from (select *  from t_shouli s  where not exists (select 1 "
//			+ " from t_shouli l where s.sjbbh < l.sjbbh "
//			+ " and s.ywlsh = l.ywlsh)) t, office_spi_declareinfo d, "
//			+ " T_BANJIE b  where d.declaresn(+) = t.yxtywlsh  and b.ywlsh(+) = t.ywlsh "
//			+ " and t.yxtywlsh not in (select s.declaresn from spi_supervise_back s) " 
//			+ " and b.bjjg is not null "
//			+ " order by t.slsj desc";
		List<ProjectInfo> list = simpleJdbcDao1.queryForRow(sql,params,ProjectInfo.class);
		return list;
	}
	
	public int getDataRows(String sql, Object[] params) {
		return simpleJdbcDao.countRows(sql,params);
	}
	public BusinessInfo getBusinessInfo(String sql, Object[] params) {
		return simpleJdbcDao.getBean(sql, params, BusinessInfo.class);
	}

}
