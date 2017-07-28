package net.risesoft.approve.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.approve.entity.base.Pager;
import net.risesoft.approve.entity.jpa.Score;
import net.risesoft.approve.repository.jpa.ScoreRepository;
import net.risesoft.approve.service.DailyManagementService;
import net.risesoft.utilx.DateX;
import net.risesoft.utilx.StringX;

import org.apache.ibatis.io.ResolverUtil.IsA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service(value = "dailymanagementservice")
public class DailyManagementServiceImpl implements DailyManagementService {

	@Autowired
	private ScoreRepository scoreRepository;

	@Resource(name = "routerJdbcTemplate")
	private JdbcTemplate jdbctemplate;

	/**
	 *查询窗口人员
	 *@param userName 人员
	 */
	@Override
	public Pager findWindowPerson(Pager pager,String userName,String department,String sel,String enrollnumber) {
		// TODO Auto-generated method stub
		try {
			int pageNum = pager.getPageNo();
			int pageSize = pager.getPageSize();
			String sql="";
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			List<Object> params = new ArrayList<Object>();
			sql = "  select t.guid,t.enrollnumber,t.departmentdesk department,t.windowname  name from dt_ckgl_windowpersonadd t where t.devicenumber=( select d.devicenumber from kq_rollmachine d where d.department =?)";
			params.add(sel);
			if(!StringX.isBlank(userName)){
				sql += "and t.windowname like ?";
				params.add("%"+userName+"%");
			}
			if(!StringX.isBlank(department)){
				sql += "and t.departmentdesk like ?";
				params.add("%"+department+"%");
			}
			if(!StringX.isBlank(enrollnumber)){
				sql += "and t.enrollnumber like ?";
				params.add("%"+enrollnumber+"%");
			}
			listmap= jdbctemplate.queryForList(sql,params.toArray());
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbctemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize), params.toArray()));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *按日查询窗口人员
	 *@param userName 人员
	 */
	public Pager findDatePerson(Pager pager,String date,String userName,String department,String enrollnumber,String sel) {
		// TODO Auto-generated method stub
		try {
 			int pageNum = pager.getPageNo();
 			Date d = new Date();    
 			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");  
 			String dateNowStr = sdf.format(d);  

			int pageSize = pager.getPageSize();
			String sql="";
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			List<Object> params = new ArrayList<Object>();
			sql = " select t.guid,t.enrollnumber, t.departmentdesk department, t.windowname name,substr(max(d.time),1,7) dated, avg(d.rewards + d.punishment + d.complaints + d.praise + d.cdzt + d.lmwmyy + d.mdgp + d.nwzjqk + d.pyq + d.yxltksj + d.zztfx) ydpf from dt_ckgl_windowpersonadd t, rc_kpsj d "
					+ "where d.userguid(+) = t.guid and t.devicenumber=( select d.devicenumber from kq_rollmachine d where d.department =?) ";
			params.add(sel);
			if(!StringX.isBlank(date)){
				sql += "and d.time like ?";
				params.add("%"+date+"%");
			}else{
				sql += "and d.time like ?";
				params.add("%"+dateNowStr+"%");
			}
			if(!StringX.isBlank(userName)){
				sql += "and t.windowname like ?";
				params.add("%"+userName+"%");
			}
			if(!StringX.isBlank(department)){
				sql += "and t.departmentdesk like ?";
				params.add("%"+department+"%");
			}
			if(!StringX.isBlank(enrollnumber)){
				sql += "and t.enrollnumber like ?";
				params.add("%"+enrollnumber+"%");
			}
			sql +="group by t.guid, t.enrollnumber, t.departmentdesk, t.windowname";
			listmap= jdbctemplate.queryForList(sql,params.toArray());
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbctemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize), params.toArray()));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 *查询考评记录
	 *@param date 考评日期
	 */
	@Override
	public Pager findRecord(Pager pager,String date,String userId) {
		try {
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			int pageNum = pager.getPageNo();
			int pageSize = pager.getPageSize();
			String sql="";
			List<Object> params = new ArrayList<Object>();

			sql = " select (t.rewards + t.punishment + t.complaints + t.praise + t.cdzt + t.lmwmyy + t.mdgp + t.nwzjqk + t.pyq + t.yxltksj + t.zztfx) heji,t.remarks,t.userguid,d.windowname name,t.guid scoreguid,nvl(t.cdzt,0) cdzt,nvl(t.lmwmyy,0) lmwmyy,nvl(t.mdgp,0)mdgp,nvl(t.nwzjqk,0) nwzjqk,nvl(t.pyq,0) pyq,nvl(t.yxltksj,0) yxltksj,nvl(t.zztfx,0) zztfx,"
					+ " nvl(t.time,to_char(sysdate,'yyyy-mm-dd')) time from rc_kpsj t, dt_ckgl_windowpersonadd d where t.userguid(+) = d.guid and d.guid=? order by time Desc";
			params.add(userId);
			if(!StringX.isBlank(date)){
				sql += "t.time= ?";
				params.add(DateX.getStandardDateText(new Date()));
			}
			listmap= jdbctemplate.queryForList(sql,params.toArray());
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbctemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize), params.toArray()));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public Pager findMonthRecord(Pager pager,String date,String userId) {
		try {
			List<Map<String, Object>> listmap = new ArrayList<Map<String, Object>>();
			int pageNum = pager.getPageNo();
			int pageSize = pager.getPageSize();
			String sql="";
			List<Object> params = new ArrayList<Object>();

			sql = " select (t.rewards + t.punishment + t.complaints + t.praise + t.cdzt + t.lmwmyy + t.mdgp + t.nwzjqk + t.pyq + t.yxltksj + t.zztfx) heji,t.remarks,t.userguid,d.windowname name,t.guid scoreguid,nvl(t.cdzt,0) cdzt,nvl(t.lmwmyy,0) lmwmyy,nvl(t.mdgp,0)mdgp,nvl(t.nwzjqk,0) nwzjqk,nvl(t.pyq,0) pyq,nvl(t.yxltksj,0) yxltksj,nvl(t.zztfx,0) zztfx,"
					+ " nvl(t.time,to_char(sysdate,'yyyy-mm-dd')) time from rc_kpsj t, dt_ckgl_windowpersonadd d where t.userguid(+) = d.guid and d.guid=? and t.time like ? order by time Desc";
			params.add(userId);
			params.add("%"+date+"%");
			listmap= jdbctemplate.queryForList(sql,params.toArray());
			pager.setTotalRows(listmap.size());
			pager.setPageList(jdbctemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize), params.toArray()));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	// 保存大厅人员评分
	@Override
	public void saveScore(Score initScore,String scoreguid) {
		
		Score score = scoreRepository.findOne(scoreguid);
		try {
			if(score!=null){
				score.setCdzt(initScore.getCdzt());
				score.setLmwmyy(initScore.getLmwmyy());
				score.setMdgp(initScore.getMdgp());
				score.setNwzjqk(initScore.getNwzjqk());
				score.setYxltksj(initScore.getYxltksj());
				score.setZztfx(initScore.getZztfx());
				score.setPyq(initScore.getPyq());
				score.setRemarks(initScore.getRemarks());
				
			}
		
			scoreRepository.save(initScore);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	//查询人员
	@Override
	public Score find(String reviewid) {
		// TODO Auto-generated method stub
		try {
			String sql = "select a.rewards,a.punishment,a.complaints,a.praise,a.userguid,d.windowname,a.name,a.enrollnumber,a.guid scoreguid,a.remarks,a.time, a.nwzjqk,a.yxltksj,a.cdzt, a.mdgp,a.zztfx,"
					+ " a.pyq,a.lmwmyy from RC_KPSJ a, dt_ckgl_windowpersonadd d where a.guid=? and a.userguid(+) = d.guid";
			Score score = jdbctemplate.queryForObject(sql,
					new String[]{reviewid},
					new BeanPropertyRowMapper<Score>(Score.class));
			/* List<Score> list=jdbctemplate.queryForList(sql,params.toArray()); */

			return score;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new Score();
	}
	//展示奖惩记录
	@Override
	public Pager showJcWindowPerson(Pager pager,String date) {
		// TODO Auto-generated method stub
		try {
			int pageNum = pager.getPageNo();
			int pageSize = pager.getPageSize();
			String sql = " select a.remarks,a.time,a.guid scoreguid,b.guid,b.name, b.enrollnumber,a.rewards,a.punishment, a.complaints, a.praise      from"
					+ " KQ_USERINFO b,(select * from rc_kpsj s where s.time = ?) a where b.guid = a.userguid(+)";

			List<Object> params = new ArrayList<Object>();
			params.add(date+" ");
			pager.setPageList(jdbctemplate.queryForList(
					pager.setPageSql(sql, pageNum, pageSize), params.toArray()));
			return pager;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//月考评表查找
	@Override
	public Score findMonth(Score score, String mounthguid) {
		// TODO Auto-generated method stub
		
		return null;
	}
	//删除记录
	@Override
	public void delectScore(String ScoreGuid) {
		String sql="delete rc_kpsj t where t.guid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(ScoreGuid);
		jdbctemplate.update(sql, params.toArray());
		// TODO Auto-generated method stub
		
	}
	//查询个人信息
	@Override
	public int findperson(String mouthguid,String time) {
		// TODO Auto-generated method stub

		String sql="select avg(a.cdzt+a.lmwmyy+a.mdgp+a.nwzjqk+a.pyq+a.yxltksj+a.zztfx) from rc_kpsj a where a.userguid=? ";
		List<Object> params = new ArrayList<Object>();
		params.add(mouthguid);
		if(!StringX.isBlank(time)){
			sql+=" AND a.time like ?";
			params.add("%"+time+"%");
		}
		int monthavg=jdbctemplate.queryForObject(sql,params.toArray(), Integer.class);
		return monthavg;
	}

	@Override
	public void addjcjl(String scoreGuid,int rewards,int punishment,int complaints,int praise) {
		// TODO Auto-generated method stub
		String sql="update rc_kpsj a set a.rewards=?,a.punishment=?,a.complaints=?,a.praise=? where a.guid=?";
		List<Object> params = new ArrayList<Object>();
		params.add(rewards);
		params.add(punishment);
		params.add(complaints);
		params.add(praise);
		params.add(scoreGuid);
		jdbctemplate.update(sql, params.toArray());
	}

	@Override
	public boolean findis(String time,String userGuid) {
		// TODO Auto-generated method stub
		String sql="select count(*) from rc_kpsj where time=? and userguid=?";
		try {
			int score = jdbctemplate.queryForObject(sql,
					new String[]{time,userGuid},Integer.class);
			if(score>0){
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
