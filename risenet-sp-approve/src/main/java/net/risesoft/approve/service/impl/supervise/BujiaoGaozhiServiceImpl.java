package net.risesoft.approve.service.impl.supervise;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;

import net.risesoft.api.SendDataManager;
import net.risesoft.approve.entity.jpa.FtSupervise;
import net.risesoft.approve.entity.jpa.SpmWordFile;
import net.risesoft.approve.entity.jpa.TBujiaogaozhi;
import net.risesoft.approve.entity.jpa.TBujiaogaozhiUnits;
import net.risesoft.approve.entity.jpa.TBujiaoshouli;
import net.risesoft.approve.entity.jpa.TBujiaoshouliUnits;
import net.risesoft.approve.entity.jpa.gdbs.BuZhengGaoZhiProcess;
import net.risesoft.approve.entity.jpa.gdbs.BuZhengGaoZhiUnits;
import net.risesoft.approve.entity.jpa.gdbs.ShouliProcess;
import net.risesoft.approve.repository.jpa.SpmWordFileRepository;
import net.risesoft.approve.repository.jpa.supervise.BuZhengGaoZhiRepository;
import net.risesoft.approve.repository.jpa.supervise.BuqibuzhengRepository;
import net.risesoft.approve.repository.jpa.supervise.BuqibuzhengshouliRepository;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.supervise.BujiaoGaozhiService;
import net.risesoft.common.util.WorkdayUtils;
import net.risesoft.fileflow.service.ReminderDefineService;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.utilx.FTSuperviseUtil;
import net.risesoft.utilx.StringX;
import net.risesoft.utilx.database.Conn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 审批环节数据接口实现类
 * 
 * @author Administrator
 *
 */
@Service(value="bujiaoGaozhiService")
public class BujiaoGaozhiServiceImpl implements BujiaoGaozhiService {


	@Autowired
	private BuqibuzhengRepository buqibuzhengRepository;// 补齐补正repository

	@Autowired
	private BuqibuzhengshouliRepository buqibuzhengshouliRepository;// 补齐补正受理repository
	
	@Autowired
	private BuZhengGaoZhiRepository buZhengGaoZhiRepository;// 补齐补正repository
	
	@Resource(name="officeSpiDeclareinfoService")
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService;

	@Resource(name = "reminderDefineService")
	private ReminderDefineService reminderDefineService;
	
	@Resource(name = "routerDataSource")
	private DataSource routerDataSource;
	
	@Resource(name="sendDataManager")
	private SendDataManager sendDataManager;

	private JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void afterIoc() {
		jdbcTemplate = new JdbcTemplate(this.routerDataSource);
	}

	@Autowired
	private SpmWordFileRepository spmWordFileRepository;


	@Override
	public List<Map<String, Object>> findAll(String name, String yxtywlsh,
			String declareperson) {
		Person person  =  ThreadLocalHolder.getPerson();
		//查询到当前用户在rc7中的id,
		String userID = person.getID();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
			
			List<Map<String, Object>> listmap=new ArrayList<Map<String, Object>>();
			//查询窗口收件列表
			String sql="select s.workflowguid,o.workflowinstance_guid guid,o.declarerperson,t.yxtywlsh,t.bjgzfcr,t.bjgzfcrGUID,to_char(t.bjgzsj,'yyyy-mm-dd hh:mi:ss') bjgzsj,s.approveitemname from t_bujiaogaozhi t,spm_approveitem s,office_spi_declareinfo o "
					+ "where o.approveitemguid=s.approveitemguid and t.ywlsh not in(select ywlsh from t_bujiaoshouli) and o.declaresn=t.yxtywlsh and t.bjgzfcrguid=? ";
			List<Object> params=new ArrayList<Object>();
			params.add(userID);
			if(!StringX.isBlank(name)){	//事项名称			
				sql += " and s.approveitemname like ?";
				params.add("%"+name+"%");			
			}	
			if(!StringX.isBlank(yxtywlsh)){
				sql += " and t.yxtywlsh = ?";
				params.add(yxtywlsh);	
			}
			if(!StringX.isBlank(declareperson)){
				sql += " and o.declarerperson like ?";
				params.add("%"+declareperson+"%");	
			}	
			sql += "ORDER BY bjgzsj DESC";
			listmap =jdbcTemplate.queryForList(sql,params.toArray());			
			list.addAll(listmap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}
	
	@Override
	public long bqbzCount() {
		Person person  =  ThreadLocalHolder.getPerson();
		//查询到当前用户在rc7中的id,
		String userID = person.getID();
		long bqbzCount = 0;
		try{
			List<Map<String, Object>> listmap=new ArrayList<Map<String, Object>>();
			//查询窗口收件列表
			String sql="select s.workflowguid,o.workflowinstance_guid guid,o.declarerperson,t.yxtywlsh,t.bjgzfcr,t.bjgzfcrGUID,to_char(t.bjgzsj,'yyyy-mm-dd hh:mi:ss') bjgzsj,s.approveitemname from t_bujiaogaozhi t,spm_approveitem s,office_spi_declareinfo o "
					+ "where o.approveitemguid=s.approveitemguid and t.ywlsh not in(select ywlsh from t_bujiaoshouli) and o.declaresn=t.yxtywlsh and t.bjgzfcrguid=?";
			List<Object> params=new ArrayList<Object>();
			params.add(userID);
			
			listmap =jdbcTemplate.queryForList(sql,params.toArray());	
			bqbzCount = listmap.size();
		}catch(Exception e){
			e.printStackTrace();
		}
		return bqbzCount;
	}
	
	@Override
	public void saveBuqibuzheng(String instanceId,String reason,String xbqids,String xbzids,int step) {
		Person person = ThreadLocalHolder.getPerson();
		FtSupervise ftSupervise=null;
		try {
			String listIds = xbqids+","+xbzids;
			String clqd="";
			if(!StringX.isBlank(listIds) && !",".equals(listIds)){
				//查询相关材料
				String sql = "select wm_concat(rownum ||'、'|| t.declareannexname||chr(13)) from spm_declareannex t where instr(?,t.declareannexguid)>0";
				clqd = jdbcTemplate.queryForObject(sql, new String[]{listIds}, String.class);
			}
			// 获取公共信息
			ftSupervise = FTSuperviseUtil.getFTCode(jdbcTemplate, instanceId);
			TBujiaogaozhiUnits id = new TBujiaogaozhiUnits();
			id.setYwlsh(ftSupervise.getYwlsh());//业务流水号
			id.setSjbbh((long)1);//数据版本号

			TBujiaogaozhi bean = new TBujiaogaozhi();
			bean.setId(id);
			bean.setSpsxbh(ftSupervise.getSpsxbh());//审批事项编号
			bean.setSpsxzxbh(ftSupervise.getSpsxzxbh());//审批事项子项编号
			bean.setYxtywlsh(ftSupervise.getYxtywlsh());//原系统业务流水号
			bean.setBjgzsj(new Date());//补交告知时间
			if(StringX.isBlank(reason)){
				reason = "无";
			}
			//补齐补正状态
			bean.setStep((long)step);
			bean.setBjgzyy(reason);//补交告知原因
			bean.setBjgzfcrguid(person.getID());
			bean.setBjgzfcr(person.getName());//补交告知发起人
			bean.setBjclgzqd(clqd.replace(",",""));//材料清单
			bean.setDatasource("XZSP");
			bean.setIsget("0");
			bean.setIsexchangebsdt("0");
			bean.setSerialnum(ftSupervise.getSerialnum());
			buqibuzhengRepository.save(bean);
		}catch(EmptyResultDataAccessException ex){
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	@Override
	public void saveBuZhengGaoZhi(String processInstanceId,String reason,String xbqids,String xbzids,String bz) {
		Person person = ThreadLocalHolder.getPerson();
		String sblsh = officeSpiDeclareinfoService.getDeclaresnByProcessInstanceId(processInstanceId);
		try {
			//获取公共信息
			ShouliProcess shouli = FTSuperviseUtil.findBySblsh(sblsh);
			String listIds = xbqids+","+xbzids;
			String clqd="";
			if(!StringX.isBlank(listIds) && !",".equals(listIds)){
				//查询相关材料
				String sql = "select wm_concat(rownum ||'、'|| t.CLMC||chr(13)) from ex_gdbs_sbcl t where instr(?,t.seq)>0";
				clqd = jdbcTemplate.queryForObject(sql, new String[]{listIds}, String.class);
			}
			BuZhengGaoZhiUnits id = new BuZhengGaoZhiUnits();
			//版本号 申办流水号 联合主键
			id.setSblsh_short(shouli.getSblshShort());
			id.setVersion("1");
			BuZhengGaoZhiProcess bean = new BuZhengGaoZhiProcess();
			bean.setId(id);
			bean.setSblsh(shouli.getSblsh());
			bean.setSxbm(shouli.getSxbm());
			bean.setSxbm_short(shouli.getSxbmShort());
			bean.setSxqxbm(shouli.getSxqxbm());
			bean.setXzqhdm(shouli.getXzqhdm());
			bean.setBzgzfcrxm(person.getName());
			bean.setBzgzsj(new Date());//补交告知时间
			if(StringX.isBlank(reason)){
				reason = "无";
			}
			bean.setBzgzyy(reason);//补交告知原因
			if(StringX.isBlank(clqd)){
				clqd="材料清单";
			}
			bean.setBzclqd(clqd.replace(",",""));//材料清单
			listIds = "f9623e0bef9b4d29a2be38d0f37285cf";
			bean.setBjgzclbm(listIds);//材料清单id
			bean.setF_xzqhdm("440300");
			bean.setBz(bz);//备注
			bean.setF_xzqhdm(shouli.getF_xzqhdw());
			bean.setProjectCode(shouli.getProjectCode());
			bean.setD_zzjgdm(shouli.getSlbmzzjgdm());
			bean.setInserttime(new Date());
			buZhengGaoZhiRepository.save(bean);
			
			//向市统一申办受理平台推送补交告知数据
			sendDataManager.sendBujiaogaozhi(sblsh);
			
		}catch(EmptyResultDataAccessException ex){
			ex.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void saveBuqibuzhengshouli(String instanceId) throws Exception{
		
		try {
			Person person  = ThreadLocalHolder.getPerson();
			TBujiaoshouli bean = new TBujiaoshouli();
			String sql = "select t.* from t_bujiaogaozhi t,office_spi_declareinfo s where s.declaresn=t.yxtywlsh and s.workflowinstance_guid=?";
			TBujiaogaozhi bujiaoGaozhi = jdbcTemplate.queryForObject(sql, new String[]{instanceId}, new BeanPropertyRowMapper<TBujiaogaozhi>(TBujiaogaozhi.class));
			bean.setId(new TBujiaoshouliUnits(bujiaoGaozhi.getYwlsh(),(long)1));
			bean.setSpsxbh(bujiaoGaozhi.getSpsxbh());//审批事项编号
			bean.setSpsxzxbh(bujiaoGaozhi.getSpsxzxbh());//子编号
			bean.setYxtywlsh(bujiaoGaozhi.getYxtywlsh());//原系统业务流水号
			bean.setSldwcbrxm(person.getName());//受理人姓名
			bean.setBjsljtdd("罗湖区行政服务大厅");//受理具体地点
			bean.setBjsj(new Date());//办结时间
			bean.setBjclqd(bujiaoGaozhi.getBjclgzqd());//补交材料清单
			bean.setIsget("1");//
			bean.setIsexchangebsdt("0");
			bean.setDatasource("XZSP");
			bean.setDepartid("");
			bean.setSerialnum(bujiaoGaozhi.getSerialnum());//申办流水号
			buqibuzhengshouliRepository.save(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int isBuqibuzheng(String instanceId) {
		// TODO Auto-generated method stub
		try{
			String sql = "select count(1) from t_bujiaogaozhi t,office_spi_declareinfo s where t.yxtywlsh=s.declaresn and s.workflowinstance_guid=?";
			return jdbcTemplate.queryForObject(sql, new String[]{instanceId}, Integer.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
	
	@Override
	public String isBuqibuzhengShouli(String instanceId) {
		try{
			String sql = "select count(1) from ex_gdbs_bzsl t,office_spi_declareinfo s where t.sblsh_short=s.declaresn and s.workflowinstance_guid=?";
			return jdbcTemplate.queryForObject(sql, new String[]{instanceId}, String.class);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return "0";
	}


	@Override
	public void saveBuqibuzheng(TBujiaogaozhi bujiaogaozhi) {
		// TODO Auto-generated method stub
		buqibuzhengRepository.save(bujiaogaozhi);
	}
	
	@Override
	public void saveBuqibuzhengshouli(TBujiaoshouli bean) {
		buqibuzhengshouliRepository.save(bean);
	}
	
	@Override
	public TBujiaogaozhi findById(TBujiaogaozhiUnits id) {
		return buqibuzhengRepository.findById(id);
	}

	@Override
	public Map<String, Object> getBuqibuzhengStatus(TBujiaogaozhiUnits id) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			TBujiaogaozhi tBujiaogaozhi = findById(id);
			if (null != tBujiaogaozhi) {
				// 补齐补正开始时间
				Date bjgzsj = tBujiaogaozhi.getBjgzsj();
				map.put("bjgzsj", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(bjgzsj));
				//获取补齐补正已用天数
				int bjgzsjDaycount = new WorkdayUtils().getWorkdayCount(bjgzsj, new Date());
				map.put("bjgzsjDaycount", bjgzsjDaycount);
				// 补齐补正到期时间
				Date bjgzsjDueDate = reminderDefineService.getAdvanceDate(bjgzsj, 1, 15);
				map.put("bjgzsjDueDate", bjgzsjDueDate);
				return map;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Map<String, Object> getBuqibuzhengLists(String ytjids,
			String xbqids, String xbzids) {
		Map<String,Object> map = new HashMap<String, Object>();
		String sql = "select wm_concat(rownum ||'、'|| t.declareannexname||'\\\\r') from spm_declareannex t where instr(?,t.declareannexguid)>0";
		String clqd="";
		if(!StringX.isBlank(ytjids)){
			//查询相关材料
			clqd = jdbcTemplate.queryForObject(sql, new String[]{ytjids}, String.class);
			map.put("YTJIDS", clqd);
		}
		if(!StringX.isBlank(xbqids)){
			//查询相关材料
			clqd = jdbcTemplate.queryForObject(sql, new String[]{xbqids}, String.class);
			map.put("XBQIDS", clqd);
		}
		if(!StringX.isBlank(xbzids)){
			//查询相关材料
			clqd = jdbcTemplate.queryForObject(sql, new String[]{xbzids}, String.class);
			map.put("XBZIDS", clqd);
		}
		return map;
	}

	@Override
	public SpmWordFile saveWordFile(SpmWordFile wordFile) {
		SpmWordFile word = spmWordFileRepository.save(wordFile);
	
		return word;
	}

	@Override
	public Map<String, Object> getAdviceRemark(String approveitemguid) {
		String sql = "select t.approveitemguid,t.approveitemname,t.timelimit,t.approveitemtype,t.legaltimelimit,b.bureauname  from spm_approveitem t,spm_bureau b "
				+ "where b.bureauguid=t.adminbureauguid and t.approveitemguid=?";
		Map<String,Object> map = jdbcTemplate.queryForMap(sql, new Object[]{approveitemguid});
		return map;
	}

	@Override
	public List<Map<String, Object>> getLists(String approveitemguid) {
		// TODO Auto-generated method stub
		String sql = "select distinct d.declareannexguid,d.declareannexname,t.declareannextypeguid typeguid from spm_declareannex d,spm_declareannexrelation t where  d.declareannexguid=t.declareannexguid and t.approveitemguid=?";
		return jdbcTemplate.queryForList(sql, new Object[]{approveitemguid});
	}

	@Override
	public List<Map<String, Object>> getListType(String approveitemguid) {
		// TODO Auto-generated method stub
		String sql = "select distinct d.declareannextypeguid,d.declareannextypename from spm_declareannextype d,spm_declareannexrelation t where d.declareannextypeguid=t.declareannextypeguid and t.approveitemguid=?";
		return jdbcTemplate.queryForList(sql, new Object[]{approveitemguid});
	}

	@Override
	public Map<String, Object> getSmsMessage(String instanceid,String xbqids,String xbzids) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		String mobile = jdbcTemplate.queryForObject("select t.declarermobile from office_spi_declareinfo t where t.workflowinstance_guid=?", String.class, new Object[]{instanceid});
		map.put("mobile", mobile);
		String declaresn = jdbcTemplate.queryForObject("select t.declaresn from office_spi_declareinfo t where t.workflowinstance_guid=?", String.class, new Object[]{instanceid});
		String clqd = "您有一笔编号为"+declaresn+"的业务材料需要完善，清单如下";
		String xbq = "";
		if(!StringX.isBlank(xbqids)){
			//查询相关材料
			String sql = "select wm_concat(rownum ||'、'|| t.declareannexname||chr(13)) from spm_declareannex t where instr(?,t.declareannexguid)>0";
			xbq = jdbcTemplate.queryForObject(sql, new String[]{xbqids}, String.class);
			clqd += "需补齐："+xbq;
		}
		String xbz = "";
		if(!StringX.isBlank(xbzids)){
			//查询相关材料
			String sql = "select wm_concat(rownum ||'、'|| t.declareannexname||chr(13)) from spm_declareannex t where instr(?,t.declareannexguid)>0";
			xbz = jdbcTemplate.queryForObject(sql, new String[]{xbzids}, String.class);
			clqd += "需补正："+xbz;
		}
		map.put("content", clqd);
		return map;
	}

	

	@Override
	public String findBjgzTime(String sblsh_short) {
		// TODO Auto-generated method stub
		String sql="select t.bzgzsj from ex_gdbs_bzgz t where t.sblsh_short=?";
		return jdbcTemplate.queryForObject(sql,new Object[]{sblsh_short}, String.class);
	}

}
