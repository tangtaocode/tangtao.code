package net.risesoft.utilx;

import java.util.ArrayList;
import java.util.List;

import net.risesoft.approve.entity.jpa.FtSupervise;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.entity.jpa.gdbs.ShenBanProcess;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.common.util.ContextUtil;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 审批工具类
 * @author Administrator
 *
 */
public class FTSuperviseUtil {
	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");

	
	public static ShouliProcess findBySblsh(String sblsh) {
		String sql = "select * from ex_gdbs_sl t where t.sblsh_short=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{sblsh},new BeanPropertyRowMapper<ShouliProcess>(ShouliProcess.class));
	}
	public static ShenBanProcess findShenBanProcess(String sblsh) {
		// TODO Auto-generated method stub
		String sql = "select * from ex_gdbs_sb t where t.sblsh_short=?";
		return jdbcTemplate.queryForObject(sql, new Object[]{sblsh},new BeanPropertyRowMapper<ShenBanProcess>(ShenBanProcess.class));
	}
	
	
	
	//需要保存的公共字段，包括ywlsh和事项编号的生产
	public static FtSupervise getFTCode(JdbcTemplate jdbcTemplate,String instanceguid)throws Exception{
		String sql="select func_createywlsh('440303' || b.institutioncode || s.ftcode,s.ftsubcode,t.declaresn) ywlsh,"
				+ "'440303'||b.institutioncode||s.ftcode spsxbh,s.ftsubcode spsxzxbh,t.declaresn yxtywlsh, s.approveitemname spsxmc,"
				+ "'XZSP' datasource,'0' isexchangebsdt, t.sblsh serialnum  from office_spi_declareinfo t, spm_approveitem s,spm_bureau b "
				+ "where t.approveitemguid = s.approveitemguid and  b.bureauguid(+) = s.adminbureauguid and t.workflowinstance_guid = ?";
		List<String> param = new ArrayList<String>();
		param.add(instanceguid);
		return jdbcTemplate.queryForObject(sql, param.toArray(),new BeanPropertyRowMapper<FtSupervise>(FtSupervise.class));
	}
	
	//获取spm_approveItem表的数据
	public static SpmApproveitem getSpmFiled(String yxtywlsh){
		//通过原系统业务流水号获取spm_approveitem表中的数据
		String sql = "select * from spm_approveitem s,office_spi_declareinfo t where t.approveitemguid=s.approveitemguid and t.declaresn=?";
		List<String> param = new ArrayList<String>();
		param.add(yxtywlsh);
		return jdbcTemplate.queryForObject(sql, param.toArray(),new BeanPropertyRowMapper<SpmApproveitem>(SpmApproveitem.class));
	}
	//查询证照名称
	public static String findDoctypeName(String workflowInstanceId){
		try {
			String sql = "select distinct t.doctypename from t_bdex_doctype t,spm_approveitem s,"
					+ "office_spi_declareinfo d where instr(s.doctypeguid,t.guid)>0 and s.approveitemguid=d.approveitemguid "
					+ "and d.workflowinstance_guid=?";
			List<String> res =  jdbcTemplate.queryForList(sql, new String[]{workflowInstanceId}, String.class);
			return res.size()>0?res.get(0):"";
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}
	//获取补齐补正材料清单
	public static String getNotDeclareAnnex(){
		String sql = "";
		String annex="";
		return annex;
	}
	
}
