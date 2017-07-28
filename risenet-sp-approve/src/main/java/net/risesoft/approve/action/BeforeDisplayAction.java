package net.risesoft.approve.action;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

import net.business.engine.common.I_TemplateAction;
import net.business.engine.common.TemplateContext;
import net.risesoft.approve.entity.jpa.SpmApproveitem;
import net.risesoft.approve.service.OfficeSpiDeclareinfoService;
import net.risesoft.approve.service.SpmApproveItemService;
import net.risesoft.approve.service.SpmBureauService;
import net.risesoft.approve.service.impl.SpmBureauServiceImpl;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.common.util.WorkdayUtils;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.FTSuperviseUtil;

/**
 * 1、模板显示前的校验
 * @author Administrator
 *
 */
public class BeforeDisplayAction implements I_TemplateAction{
	
	
	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;
	
	
	private SpmApproveItemService spmApproveItemService = ContextUtil.getBean("spmApproveItemService");
	private SpmBureauService spmBureauService =ContextUtil.getBean("spmBureauService");
	
	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");
	
	public int execute(TemplateContext context) throws Exception{
		//真实的事项guid
		String processInstanceId = context.getRequest().getParameter("guid");
		String SPinstanceId = context.getRequest().getParameter("SPinstanceId");
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit dept = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		if(processInstanceId!=null&&processInstanceId!=""){
			String approveitemguid=officeSpiDeclareinfoService.findApproveitemguidByProcessInstanceId(processInstanceId);//获取审批事项id
			SpmApproveitem spmApproveitem = spmApproveItemService.findByApproveitemguid(approveitemguid);//根据事项id查询事项
			String doctypeguid = "";
			if(spmApproveitem!=null){
				doctypeguid = spmApproveitem.getDoctypeguid();//获取证照类型guid
			}
			if(doctypeguid!=null&&doctypeguid!=""){
				context.put("certifyWay", "证照");//办结方式
			}else{
				context.put("docWay", "文件");//办结方式
			}
		}
		
		//建立数据库连接，取得结果
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String doctypeName = FTSuperviseUtil.findDoctypeName(SPinstanceId);//证照名，用于办结单
			context.put("certifyName", doctypeName);//证照名
			String sql = "select t.DECLARESN,t.APPROVEITEMNAME,t.XIANGMUMINGCHENG,t.DECLARERPERSON,t.ADDRESS,t.ZHENGJIANDAIMA,t.DECLARERTYPE,"
					+ "t.DECLARERLXR,t.DECLARERLXRIDTYPE,t.DECLARERLXRID,t.DECLARERTEL,t.DECLARERMOBILE,t.DECLARERFAX,"
					+ "t.EMPLOYEEDEPTNAME,t.DECLAREDATETIME,t.BUREAUNAME,t.APPROVETYPE,t.DECLAREANNEXGUIDS,t.NOTDECLAREANNEXGUIDS,t.BHGSBCL,t.EMPLOYEE_TEL,"
					+ "t.LIMITIME,t.DATAFROMTYPE,t.CHENGNUORIQI,t.ZIXUNDIANHUA,t.NSJYSL,t.APPROVEITEMGUID from OFFICE_SPI_DECLAREINFO t where t.WORKFLOWINSTANCE_GUID=?";
			pstmt = conn.prepareStatement(sql);//从库中获取数据
			//pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,SPinstanceId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String DECLARESN = rs.getString(1);//业务编号
				String APPROVEITEMNAME = rs.getString(2);//审批事项名称
				String XIANGMUMINGCHENG= rs.getString(3);//项目名称
				String DECLARERPERSON = rs.getString(4);//申请单位/人
				String ADDRESS = rs.getString(5);//单位地址/住址
				String ZHENGJIANDAIMA = rs.getString(6);//证件代码
				String DECLARERTYPE = rs.getString(7);//申请人类型
				String DECLARERLXR = rs.getString(8);//联系人
				String DECLARERLXRIDTYPE = rs.getString(9);//联系人证件类型
				String DECLARERLXRID = rs.getString(10);//联系人证件号码
				String DECLARERTEL = rs.getString(11);//联系电话
				String DECLARERMOBILE = rs.getString(12);//手机
				String DECLARERFAX = rs.getString(13);//传真
				String EMPLOYEEDEPTNAME = rs.getString(14);//经办人、受理人员
				String DECLAREDATETIME = rs.getString(15);//登记（受理）时间
				String BUREAUNAME = rs.getString(16);//(业务单位)委办局名称
				String APPROVETYPE = rs.getString(17);//事项类型
				String DECLAREANNEXGUIDS = rs.getString(18);//已交材料
				String NOTDECLAREANNEXGUIDS = rs.getString(19);//需补交的材料
				String BHGSBCL = rs.getString(20);//需补正的材料
				String EMPLOYEE_TEL = rs.getString(21);//受理职员电话
				String LIMITIME = rs.getString(22);//承诺时限
				String DATAFROMTYPE = rs.getString(23);//数据来源（申请途径）
				String CHENGNUORIQI = rs.getString(24);//承诺日期
				String ZIXUNDIANHUA = rs.getString(25);//咨询电话
				String NSJYSL = rs.getString(26);//年设计用水量
				String APPROVEITEMGUID = rs.getString(27);//事项guid
				
				context.put("declaresn", DECLARESN);//业务编号
				context.put("approveitemname", APPROVEITEMNAME);//审批事项名称
				//context.put("xiangmumingcheng", XIANGMUMINGCHENG==null?APPROVEITEMNAME:XIANGMUMINGCHENG);//项目名称
				context.put("xiangmumingcheng", XIANGMUMINGCHENG);//项目名称
				context.put("declarerperson", DECLARERPERSON);//申请单位/人
				context.put("address", ADDRESS);//单位地址/住址
				context.put("zhengjiandaima", ZHENGJIANDAIMA);//证件代码
				context.put("declarertype", DECLARERTYPE);//申请人类型
				context.put("declarerlxr", DECLARERLXR);//联系人
				context.put("declarerlxridtype", DECLARERLXRIDTYPE);//联系人证件类型
				context.put("declarerlxrid", DECLARERLXRID);//联系人证件号码
				context.put("declarertel", DECLARERTEL);//联系电话
				context.put("declarermobile", DECLARERMOBILE);//手机
				context.put("declarerfax", DECLARERFAX);//传真
				context.put("employeedeptname", EMPLOYEEDEPTNAME==null?person.getName():EMPLOYEEDEPTNAME);//经办人(受理人)
				context.put("declaredatetime", DECLAREDATETIME==null?sdf.format(new Date()):DECLAREDATETIME);//登记（受理）时间
				context.put("nsjysl", NSJYSL);
				
				context.put("approvetype", APPROVETYPE);//事项类型
				context.put("declareannexguids", DECLAREANNEXGUIDS);//已交材料
				context.put("notdeclareannexguids", NOTDECLAREANNEXGUIDS);//需补交的材料
				context.put("bhgsbcl", BHGSBCL);//需补正的材料
				context.put("employeeTel", EMPLOYEE_TEL);//受理职员电话
				
				SpmApproveitem spmApproveitem = spmApproveItemService.findByApproveitemguid(APPROVEITEMGUID);//根据事项id查询事项
				String limiTime = "";
				String chengnuoriqi = null;
				String bname="";
				bname=(BUREAUNAME==null?dept.getName():BUREAUNAME);
				
				
				//System.out.println(bname);
				if(spmApproveitem!=null){
					limiTime = spmApproveitem.getTimelimit().toString();
					WorkdayUtils workdayUtils = new WorkdayUtils();
					Date date = workdayUtils.getWorkday(new Date(), spmApproveitem.getTimelimit());
					chengnuoriqi = sdf.format(date);
					bname=spmBureauService.findDepartMentById(spmApproveitem.getBureauguid()).getBureauname();
					//spmBureauService.findAll(approveItemType, bureauGuid, approveName, approveStatus, page)
				}
				context.put("bureauname", bname);//委办局名称
				String temp=(LIMITIME==null?limiTime:LIMITIME);
				context.put("limiTime",temp );//承诺时限
				context.put("chengnuoriqi", CHENGNUORIQI==null?chengnuoriqi:CHENGNUORIQI);//承诺日期
				
				context.put("datafromtype", DATAFROMTYPE);//数据来源（申请途径）
				context.put("zixundianhua", ZIXUNDIANHUA);//咨询电话
			}
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
				return 0;
			}
		}
		return SUCCESS;
	}
		

	public String getErrorMessage() {
		return null;
	}
}
