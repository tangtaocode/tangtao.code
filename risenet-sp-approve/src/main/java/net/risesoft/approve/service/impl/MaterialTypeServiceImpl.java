 package net.risesoft.approve.service.impl;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.approve.service.XzqlXzspListTypeService;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service(value="XzqlXzspListTypeService")
public class MaterialTypeServiceImpl  implements XzqlXzspListTypeService{
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;

	private JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}
	@Override
	public List<Map<String, Object>> find(String approveitemguid) {
		String sql="select t.declareannextypename,t.declareannextypeguid from spm_declareannextype t where t.approveitemguid=?";
		List<String> params=new ArrayList<String>();
		params.add(approveitemguid);
		
			List<Map<String, Object>> isresult=jdbcTemplate.queryForList(sql, params.toArray());		
			for(Map<String,Object> s:isresult){
					for(String m:s.keySet()){
						System.out.println("K值为："+m+","+"Value值为："+s.get(m));
					}
				}
			return isresult;
			}
	@Override
	public List<Map<String, Object>> findform(String listguid) {
		String sql="select t.declareannexguid key,t.declareannexname value"
					+ " from spm_declareannex t where t.declareannexguid "
					+ "in(select t.declareannexguid from spm_declareannexrelation t "
					+ "where t.declareannextypeguid=?) and t.declareannextype='1'";
		List<String> params=new ArrayList<String>();
		params.add(listguid);
		List<Map<String, Object>> isresult=null;
		try {
			isresult = jdbcTemplate.queryForList(sql,params.toArray());
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isresult;
	}
			
}
