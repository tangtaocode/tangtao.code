//package net.risesoft.approve.service.impl;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import net.risesoft.approve.entity.base.Pager;
//import net.risesoft.approve.entity.jpa.KqGeneralLogData;
//import net.risesoft.approve.entity.jpa.ZkRollMachine;
//import net.risesoft.approve.entity.jpa.ZkUserInfo;
//import net.risesoft.approve.repository.jpa.ZkAttendanceRepository;
//import net.risesoft.approve.repository.jpa.ZkRollMachineRepository;
//import net.risesoft.approve.repository.jpa.ZklogdataRepository;
//import net.risesoft.approve.service.ZkAttendanceService;
//import net.risesoft.util.GUID;
//import net.risesoft.utilx.StringX;
//import net.risesoft.utilx.ZkkqSDK;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Service;
//
//import com.jacob.activeX.ActiveXComponent;
//
//@Service(value="zkAttendanceService")
//public class ZkAttendanceServiceImpl implements ZkAttendanceService {
//	private static ActiveXComponent zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
//	
//	@Autowired
//	private ZkAttendanceRepository zkAttendanceRepository;
//	
//	private int devicenumber=0;
//			
//	@Autowired
//	private ZklogdataRepository zklogdataRepository;
//	
//	@Autowired
//	private ZkRollMachineRepository zkRollMachineRepository;
//	
//	@Resource(name = "routerDataSource")
//	private DataSource routerDataSource;
//	
//	private JdbcTemplate jdbctemplate;
//	
//	@PostConstruct
//	private void afterIoc() {
//		jdbctemplate = new JdbcTemplate(this.routerDataSource);
//	} 
//	
//	@Override
//		public void findAllUserInfo(String ip) throws Exception {
//		List<ZkUserInfo> resultList=new ArrayList<ZkUserInfo>();
//		ZkkqSDK sdk = new ZkkqSDK();
//				//先获取到这个ip的设备编号
//				String sql="select t.devicenumber from KQ_ROLLMACHINE t where t.ip=?";
//				int devicenumber=jdbctemplate.queryForObject(sql,new String[]{ip},Integer.class);
//				//获取用户信息,并将信息插入到数据库
//			
//				  sdk.getUserInfo(jdbctemplate,zkAttendanceRepository,ip,devicenumber);
//				//读取考勤记录到PC的内部缓冲区
//				boolean result=sdk.readGeneralLogData();				
//				if(result){
//						sdk.getGeneralLogData(devicenumber,jdbctemplate,zklogdataRepository);
//				}									
//			sdk.disConnect();				
//	}
//
//	//根据用户输入的时间去查询最新的考勤数据
//	@Override
//	public Pager findData(String date1, String date2,Pager pager,int devicenumber) {
//		int pageNum = pager.getPageNo();
//		int pageSize= pager.getPageSize();
//		String sql="SELECT M .ENROLLNUMBER,M.ISLATE,TO_CHAR (M.TIME,'yyyy-mm-dd') orderT,TO_CHAR (M.TIME,'yyyy-mm-dd HH24:mi:ss') TIMEAA,M.TYPE,M.STATUSTYPE,N.WINDOWNAME,N.ENTITYWINDOWNAME,N.DEPARTMENTDESK FROM(SELECT A.STATUSTYPE,A .ENROLLNUMBER,A.TIME,A.TYPE,A.ISLATE FROM KQ_GENERALLOGDATA A WHERE A .DEVICENUMBER =?) M LEFT JOIN (SELECT B.ENROLLNUMBER,B.WINDOWNAME,B.ENTITYWINDOWNAME,B.DEPARTMENTDESK FROM DT_CKGL_WINDOWPERSONADD B WHERE B.DEVICENUMBER =?) N ON M.ENROLLNUMBER= N.ENROLLNUMBER";
//		
//		List<Object> params=new ArrayList<Object>();
//		params.add(devicenumber);
//		params.add(devicenumber);
//		try{
//			if(!StringX.isBlank(date1)){
//				sql += " WHERE TO_CHAR(M.TIME,'yyyy-mm-dd')>=?";
//				params.add(date1);
//			}
//			if(!StringX.isBlank(date2)){
//				sql +=" AND TO_CHAR(M.TIME,'yyyy-mm-dd')<=?";
//				params.add(date2);
//			}
////			sql+=" ORDER BY M.TIME";
//			sql+=" ORDER BY orderT DESC,N.ENTITYWINDOWNAME,TIMEAA";
//			List<Map<String,Object>> list=jdbctemplate.queryForList(sql, params.toArray());
//			pager.setTotalRows(list.size());
//			pager.setPageList(jdbctemplate.queryForList(pager.setPageSql(sql, pageNum, pageSize),params.toArray()));
//		}catch(Exception e){
//			e.printStackTrace();
//		}	
//		return pager;
//	}
//
//	@Override
//	public void save(String ip,int port,String department,String hall) {
//		ZkRollMachine zkRollMachine=new ZkRollMachine();	
//		String guid=new GUID().toString();
//		zkRollMachine.setGuid(guid);
//		zkRollMachine.setIp(ip);
//		zkRollMachine.setPort(port);
//		zkRollMachine.setDepartment(department);
//		zkRollMachine.setDevicenumber(devicenumber+1);
//		if("0".equals(hall)){
//			//"0"代表主厅，"1"代表分厅
//			zkRollMachine.setIsmainhall(0);
//		}else{
//			zkRollMachine.setIsmainhall(1);
//		}
//		devicenumber=devicenumber+1;
//		zkRollMachineRepository.save(zkRollMachine);			
//	}
//	
//	//通过用户输入的大厅名称去查询相应的ip和端口
//	@Override
//	public ZkRollMachine findBydepartment(String department) {		
//		return zkRollMachineRepository.findByDepartment(department);
//	}
//	
//	/**
//	 * 查询考勤机列表
//	 */
//	@Override
//	public List<ZkRollMachine> findMachines() {
//		return zkRollMachineRepository.findAll();		
//	}	
//	
//	/**
//	 * 删除设备
//	 */
//	@Override
//	public void delete(String department, String ip, String port) {
//		ZkRollMachine zkRollMachine=new ZkRollMachine();
//		zkRollMachine=zkRollMachineRepository.findByDepartment(department);
//			try {
//				zkRollMachineRepository.delete(zkRollMachine);
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//	}
//
//	
//	/**
//	 * 通过大厅名称去找到相应的设备编号
//	 */
//	@Override
//	public int finddevice(String windowhallname) {
//		String sql="select t.devicenumber from KQ_ROLLMACHINE t where t.department=?";
//		int devicenumber=jdbctemplate.queryForObject(sql,new String[]{windowhallname},Integer.class);
//		return devicenumber;		
//	}
//	
//	/**
//	 * 管理员手动添加记录并保存到数据库
//	 */
//	@Override
//	public String saveedit(KqGeneralLogData kqGeneralLogData){
//		String message;
//		try {
//			zklogdataRepository.save(kqGeneralLogData);
//			 message="添加成功";
//		} catch(Exception e){
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			message="添加失败";
//		}
//		return message;
//	}
//
//	
//	/**
//	 * 通过大厅名称去找到相应的考勤设备编号
//	 */
//	@Override
//	public int findnumber(String windowhallname) {
//		String sql="select t.devicenumber from KQ_ROLLMACHINE t where t.department=?";
//		int devicenumber=jdbctemplate.queryForObject(sql,new String[]{windowhallname},Integer.class);
//		return devicenumber;
//	}
//
//	
//	@Override
//	public List<String> findallname() {
//		return zkRollMachineRepository.findallname();
//	}
//
//	@Override
//	public List<String> findonehall(String department,int ismainhall) {
//		return zkRollMachineRepository.findonehall(department,ismainhall);
//	}
//
//		
//}
