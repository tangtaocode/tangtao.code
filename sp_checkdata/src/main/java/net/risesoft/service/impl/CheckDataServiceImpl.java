/**   
* @Title: CheckDataServiceImpl.java 
* @Package net.risesoft.service.impl 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016年9月22日 上午9:29:41 
* @version V1.0   
*/
package net.risesoft.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import net.risesoft.common.base.Pager;
import net.risesoft.service.CheckDataService;

/** 
* @ClassName: CheckDataServiceImpl 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author tangtao
* @date 2016年9月22日 上午9:29:41 
*  
*/
@Service
public class CheckDataServiceImpl implements CheckDataService {
	@Resource 
	private JdbcTemplate jdbcTemplate;
	/* (非 Javadoc) 
	* <p>Title: checkHourData</p> 
	* <p>Description: </p> 
	* @return 
	* @see net.risesoft.service.CheckDataService#checkHourData() 
	*/
	@Override
	public Pager checkHourData(Pager pager,String sblsh,String sxmc) {
		String sql = "select a.guid,a.sblsh,a.miaosu,a.hjmc,to_char(a.datatime,'yyyy-mm-dd hh24:mi:ss') as datatime,b.sxmc from T_12HourData a, ex_gdbs_sb b where a.sblsh=b.sblsh ";
		List<Object> params = new ArrayList<Object>();
		
		if(!StringUtils.isBlank(sblsh)){//类型
			sql += " and a.sblsh like ? ";
			params.add("%"+sblsh+"%");
		}
		if(!StringUtils.isBlank(sxmc)){
			sql += " and b.sxmc like ?";
			params.add("%"+sxmc+"%");
		}
		sql+=" order by a.datatime asc ";
		pager.setTotalRows(jdbcTemplate.queryForInt("select count(guid) from ("+sql+")",params.toArray()));
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pager.getPageNo(), pager.getPageSize()),params.toArray()));
		return pager;
	}

	/* (非 Javadoc) 
	* <p>Title: checkJianChaData</p> 
	* <p>Description: </p> 
	* @return 
	* @see net.risesoft.service.CheckDataService#checkJianChaData() 
	*/
	@Override
	public Pager checkJianChaData(Pager pager,String sblsh,String sxmc) {
		String sql = "select guid,spsxbh,spsxmc,sblsh,to_char(check_time,'yyyy-mm-dd hh24:mi:ss') as check_time from t_jiancha where CHECK_STATE='1'";
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isBlank(sblsh)){//类型
			sql += " and  sblsh like ? ";
			params.add("%"+sblsh+"%");
		}
		if(!StringUtils.isBlank(sxmc)){
			sql += " and spsxmc like ?";
			params.add("%"+sxmc+"%");
		}
		pager.setTotalRows(jdbcTemplate.queryForInt("select count(guid) from ("+sql+")",params.toArray()));
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pager.getPageNo(), pager.getPageSize()),params.toArray()));
		return pager;
	}

	/* (非 Javadoc) 
	* <p>Title: getProblemList</p> 
	* <p>Description: </p> 
	* @param pager
	* @param sblsh
	* @return 
	* @see net.risesoft.service.CheckDataService#getProblemList(net.risesoft.common.base.Pager, java.lang.String) 
	*/
	@Override
	public Pager getProblemList(Pager pager, String sblsh) {
		String sql = "select * from t_badinfo where sblsh=? order by datatime desc ";
		pager.setTotalRows(jdbcTemplate.queryForInt("select count(guid) from ("+sql+")",new Object[]{sblsh}));
		pager.setPageList(jdbcTemplate.queryForList(pager.setPageSql(sql, pager.getPageNo(), pager.getPageSize()),new Object[]{sblsh}));
		return pager;
	}

	/* (非 Javadoc) 
	* <p>Title: getHourCounts</p> 
	* <p>Description: </p> 
	* @return 
	* @see net.risesoft.service.CheckDataService#getHourCounts() 
	*/
	@Override
	public String getHourCounts() {
		String sql = "select count(guid) from T_12HourData";
		int temp = jdbcTemplate.queryForInt(sql);
		return temp+"";
	}

	/* (非 Javadoc) 
	* <p>Title: getJianChaCounts</p> 
	* <p>Description: </p> 
	* @return 
	* @see net.risesoft.service.CheckDataService#getJianChaCounts() 
	*/
	@Override
	public String getJianChaCounts() {
		String sql = "select count(guid) from t_jiancha where check_state='1' ";
		int temp = jdbcTemplate.queryForInt(sql);
		return temp+"";
	}

}
