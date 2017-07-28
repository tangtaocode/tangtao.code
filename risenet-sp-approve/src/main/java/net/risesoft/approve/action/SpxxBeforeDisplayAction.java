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
import net.risesoft.common.util.ContextUtil;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;
import net.risesoft.utilx.FTSuperviseUtil;

public class SpxxBeforeDisplayAction implements I_TemplateAction {

	private OfficeSpiDeclareinfoService officeSpiDeclareinfoService = ContextUtil.getBean("officeSpiDeclareinfoService");;


	private SpmApproveItemService spmApproveItemService = ContextUtil.getBean("spmApproveItemService");
	private SpmBureauService spmBureauService =ContextUtil.getBean("spmBureauService");

	public static JdbcTemplate jdbcTemplate = ContextUtil.getBean("routerJdbcTemplate");

	@Override
	public int execute(TemplateContext context) throws Exception {
		//真实的事项guid
		String processInstanceId = context.getRequest().getParameter("guid");
		String SPinstanceId = context.getRequest().getParameter("SPinstanceId");
		Person person = ThreadLocalHolder.getPerson();
		OrgUnit dept = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		//建立数据库连接，取得结果
		Connection conn = jdbcTemplate.getDataSource().getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement spxxPstmt = null;
		ResultSet spxxRs = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String doctypeName = FTSuperviseUtil.findDoctypeName(SPinstanceId);//证照名，用于办结单
			context.put("certifyName", doctypeName);//证照名
			String spxxSql = "select t.DECLARESN,t.APPROVEITEMNAME,t.XIANGMUMINGCHENG,t.DECLARERPERSON,"
					+ "t.DECLARERLXR,t.DECLAREDATETIME,t.BUREAUNAME,t.APPROVEITEMGUID,sp.FILETYPE,"
					+ "sp.BUREAU_NW,sp.AUDIT_INFO,sp.PERSON_NG,sp.LWZH,sp.TITLE_HF,sp.WENHAO,sp.GUID,sp.PROCESSINSTANCEID"
					+ " from OFFICE_SPI_DECLAREINFO t,EFORM_SB_LCBD_SPXX sp where"
					+ " sp.guid=t.WORKFLOWINSTANCE_GUID and t.WORKFLOWINSTANCE_GUID=?";
			String sql = "select t.DECLARESN,t.APPROVEITEMNAME,t.XIANGMUMINGCHENG,t.DECLARERPERSON,"
					+ "t.DECLARERLXR,t.DECLAREDATETIME,t.BUREAUNAME,t.APPROVEITEMGUID"
					+ " from OFFICE_SPI_DECLAREINFO t where"
					+ " t.WORKFLOWINSTANCE_GUID=?";
			spxxPstmt = conn.prepareStatement(spxxSql);//从库中获取数据
			spxxPstmt.setString(1,SPinstanceId);
			spxxRs = spxxPstmt.executeQuery();
			pstmt = conn.prepareStatement(sql);//从库中获取数据
			pstmt.setString(1,SPinstanceId);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String DECLARESN = rs.getString(1);//业务编号
				String APPROVEITEMNAME = rs.getString(2);//审批事项名称
				String XIANGMUMINGCHENG= rs.getString(3);//项目名称
				String DECLARERPERSON = rs.getString(4);//申请单位/人
				String DECLARERLXR = rs.getString(5);//联系人
				String DECLAREDATETIME = rs.getString(6);//登记（受理）时间
				String BUREAUNAME = rs.getString(7);//(业务单位)委办局名称
				String APPROVEITEMGUID = rs.getString(8);//事项guid

				context.put("spxx_guid", SPinstanceId);//业务编号
				context.put("spxx_processInstanceId", processInstanceId);//业务编号
				context.put("declaresn", DECLARESN);//业务编号
				context.put("approveitemname", APPROVEITEMNAME);//审批事项名称
				context.put("xiangmumingcheng", XIANGMUMINGCHENG);//项目名称
				context.put("declarerperson", DECLARERPERSON);//申请单位/人
				context.put("declarerlxr", DECLARERLXR);//联系人
				context.put("declaredatetime", DECLAREDATETIME==null?sdf.format(new Date()):DECLAREDATETIME);//登记（受理）时间

				SpmApproveitem spmApproveitem = spmApproveItemService.findByApproveitemguid(APPROVEITEMGUID);//根据事项id查询事项
				String bname="";
				bname=(BUREAUNAME==null?dept.getName():BUREAUNAME);
				if(spmApproveitem!=null){
					bname=spmBureauService.findDepartMentById(spmApproveitem.getBureauguid()).getBureauname();
				}
				context.put("bureauname", bname);//委办局名称
			}
			while(spxxRs.next()){
				String FILETYPE = spxxRs.getString(9);//文件类型
				String BUREAU_NW = spxxRs.getString(10);//拟文单位
				String AUDIT_INFO = spxxRs.getString(11);//处室审核
				String PERSON_NG = spxxRs.getString(12);//拟稿人
				String LWZH = spxxRs.getString(13);//来文字号
				String TITLE_HF = spxxRs.getString(14);//回复标题
				String WENHAO = spxxRs.getString(15);//文号
				String GUID = spxxRs.getString(16);
				String PROCESSINSTANCEID = spxxRs.getString(17);

				context.put("spxx_guid", GUID);//业务编号
				context.put("spxx_processInstanceId", PROCESSINSTANCEID);//业务编号
				context.put("filetype", FILETYPE);
				context.put("bureau_NW", BUREAU_NW);
				context.put("audit_info", AUDIT_INFO);
				context.put("person_NG", PERSON_NG);
				context.put("lwzh", LWZH);
				context.put("title_HF", TITLE_HF);
				context.put("wenhao", WENHAO);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(spxxRs != null){
					spxxRs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(spxxPstmt != null){
					spxxPstmt.close();
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

	@Override
	public String getErrorMessage() {
		return null;
	}

}
