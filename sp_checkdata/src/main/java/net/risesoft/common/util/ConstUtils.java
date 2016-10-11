/**
 * @ProjectName:CAManagerPro
 * @FileName: ConstUtils.java
 * @PackageName: net.risesoft.common.util
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2014,RiseSoft  All Rights Reserved.
 * @date Dec 4, 2014 10:35:19 AM
 */
package net.risesoft.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: ConstUtils.java
 * @Description: TODO
 *
 * @author kun
 * @date Dec 4, 2014 10:35:19 AM
 * @version 
 * @since JDK 1.6
 */
public class ConstUtils {
	/**
	 * 菜单权限 浏览
	 */
	public static final String OPERATION_BROWSE = "browse";
	/**
	 * 全局session User标识
	 */
	public static final String SESSION_USER = "riseUser";
	
	/**
	 * RC7单点登录用户ID
	 */
	public static final String LOGINRCUID = "loginrcuid";
	
	/**
	 * 当前登录优用户ID
	 */
	public static final String CURRENTUSER_GUID = "currentUser_GUID";
	
	/**
	 * session随机数，防跨站点请求伪造
	 */
    public static final String RANDOM = "randomId";
    /**
     * 网上收件栏目guid
     */
    public static final String ONLINEFOLDER = "{0A150095-0000-0000-61FF-026A00000066}";
    /**
     * 维修金中心部门guid
     */
    public static final String wxjguid="{AC100A59-FFFF-FFFF-B035-27BA00000007}";
    /**
     * 物管科部门guid
     */
    public static final String wgkguid="{BFA7FFFC-0000-0000-6C18-58D600000018}";
	/**
	 * 系统管理员ID
	 */
	public static String ADMIN_USERGUID;
	public static String rootResourceUID;
	public static String ADMIN_DEPARTMENTGUID;
	public static String ADMIN_DEPARTMENTNAME;
	public static String ZZJORGCODE;
	public static String XZSPIP;
	public static String XZSPURL;
	public static String SMS_OVERTIME ;
	public static String SMS_RULE;
	static{
		Properties ppt = new Properties();
		try {
			ppt.load(ConstUtils.class.getResourceAsStream("/properties/systemIp.properties"));
			ADMIN_USERGUID=ppt.getProperty("ADMIN_USERGUID");
			rootResourceUID=ppt.getProperty("rootResourceUID");
			ADMIN_DEPARTMENTGUID=ppt.getProperty("ADMIN_DEPARTMENTGUID");
			ADMIN_DEPARTMENTNAME=ppt.getProperty("ADMIN_DEPARTMENTNAME");
			ZZJORGCODE=ppt.getProperty("ZZJORGCODE");
			XZSPIP=ppt.getProperty("XZSP.ip");
			SMS_OVERTIME = ppt.getProperty("SMS_OVERTIME");
			SMS_RULE = ppt.getProperty("SMS_RULE");
			String port = ppt.getProperty("XZSP.port");
			XZSPURL="http://"+ppt.getProperty("XZSP.ip")+(StringUtils.isBlank(port)?"":":"+port); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final String todoSql = "select sysdate-s.begindate orderIndex,s.* "+
								" from (SELECT distinct s.begindate + 1000 ordercol, "+
								" 'SP01' Type_guid, "+
								"  s.approveitemname, "+
								" s.statedescription, "+
								"  m.gongchengmingcheng, "+
								"  s.BEGINDATE, "+
								"  s.estimateenddate, "+
								"  s.WorkflowInstance_GUID , "+
								" s.DECLARESN, "+
								"  e.employee_guid, "+
								"  s.STATE "+
								"  from SPI_Supervise                 s, "+
								" OFFICE_WorkflowInstanceActors a, "+
								" OFFICE_WorkflowInstance       i, "+
								" OFFICE_SPI_DECLAREINFO        m, "+
								" spm_bureau                    sb, "+
								" risenet_employee              e, "+
								" risenet_department            d, "+
								" spm_approveitem               sa "+
								" where s.WORKFLOWINSTANCE_GUID = a.WorkflowInstance_GUID "+
								" AND s.WORKFLOWINSTANCE_GUID = m.WorkflowInstance_GUID "+
								" AND i.WorkflowInstance_GUID = a.WorkflowInstance_GUID "+
								" AND i.InstanceDeleted = 0 "+
								" AND s.State in (20, 21, 22, 23, 24, 25, 26, 11, 41, 42, 43,501) "+
								" and s.bureauguid = sb.bureauguid "+
								" AND a.Person_Guid = e.employee_guid "+
								" AND e.department_guid = d.department_guid "+
								" AND (i.InstanceStatus = 2 OR i.InstanceStatus = 3) "+
								" AND a.Actors_Classify = 1 "+
								" AND a.handel_status in (1, 2, 3) "+
								" and s.isFINISHED = 0 "+
								" and ((trunc(sysdate) >= trunc(s.estimateenddate) - 1) and "+
								" s.estimateenddate is not null) "+
								" and sa.isproject = 0 "+
								" and m.approveitemguid = sa.approveitemguid "+
								" union "+
								" SELECT distinct s.begindate ordercol, "+
								" 'SP01' Type_guid, "+
								" s.approveitemname, "+
								" s.statedescription, "+
								" m.gongchengmingcheng, "+
								" s.BEGINDATE, "+
								" s.estimateenddate, "+
								" s.WorkflowInstance_GUID, "+
								" s.DECLARESN, "+
								" e.employee_guid, "+
								" s.STATE "+
								" from SPI_Supervise                 s, "+
								" OFFICE_WorkflowInstanceActors a, "+
								"  OFFICE_WorkflowInstance       i, "+
								" OFFICE_SPI_DECLAREINFO        m, "+
								" spm_bureau                    sb, "+
								"  risenet_employee              e, "+
								" risenet_department            d, "+
								" spm_approveitem               sa "+
								" where s.WORKFLOWINSTANCE_GUID = a.WorkflowInstance_GUID "+
								" AND s.WORKFLOWINSTANCE_GUID = m.WorkflowInstance_GUID "+
								" AND i.WorkflowInstance_GUID = a.WorkflowInstance_GUID "+
								" AND i.InstanceDeleted = 0 "+
								" AND s.State in (20, 21, 22, 23, 24, 25, 26, 11, 41, 42, 43,501) "+
								" and s.bureauguid = sb.bureauguid "+
								"  AND a.Person_Guid = e.employee_guid "+
								"  AND e.department_guid = d.department_guid "+
								" AND (i.InstanceStatus = 2 OR i.InstanceStatus = 3) "+
								" AND a.Actors_Classify = 1 "+
								" AND a.handel_status in (1, 2, 3) "+
								"  and s.isFINISHED = 0 "+
								"  and ((trunc(sysdate) < trunc(s.estimateenddate) - 1) or "+
								" s.estimateenddate is null) "+
								" and sa.isproject = 0 "+
								"  and m.approveitemguid = sa.approveitemguid) s "+
								" ORDER BY estimateenddate, ordercol desc";
	
	public static final String doingSql = "select sysdate-s.begindate orderIndex,s.* "+
			" from (SELECT distinct s.begindate + 1000 ordercol, "+
			" 'SP01' Type_guid, "+
			"  s.approveitemname, "+
			" s.statedescription, "+
			"  m.gongchengmingcheng, "+
			"  s.BEGINDATE, "+
			"  s.estimateenddate, "+
			"  s.WorkflowInstance_GUID , "+
			" s.DECLARESN, "+
			"  e.employee_guid, "+
			"  s.STATE "+
			"  from SPI_Supervise                 s, "+
			" OFFICE_WorkflowInstanceActors a, "+
			" OFFICE_WorkflowInstance       i, "+
			" OFFICE_SPI_DECLAREINFO        m, "+
			" spm_bureau                    sb, "+
			" risenet_employee              e, "+
			" risenet_department            d, "+
			" spm_approveitem               sa "+
			" where s.WORKFLOWINSTANCE_GUID = a.WorkflowInstance_GUID "+
			" AND s.WORKFLOWINSTANCE_GUID = m.WorkflowInstance_GUID "+
			" AND i.WorkflowInstance_GUID = a.WorkflowInstance_GUID "+
			" AND i.InstanceDeleted = 0 "+
			" AND s.State in (20, 21, 22, 23, 24, 25, 26, 11, 41, 42, 43) "+
			" and s.bureauguid = sb.bureauguid "+
			" AND a.Person_Guid = e.employee_guid "+
			" AND e.department_guid = d.department_guid "+
			" AND (i.InstanceStatus = 2 OR i.InstanceStatus = 3) "+
			" AND a.Actors_Classify = 5 "+
			" AND a.handel_status = 4 "+
			" and s.isFINISHED = 0 "+
			" and ((trunc(sysdate) >= trunc(s.estimateenddate) - 1) and "+
			" s.estimateenddate is not null) "+
			" and sa.isproject = 0 "+
			" and m.approveitemguid = sa.approveitemguid "+
			" union "+
			" SELECT distinct s.begindate ordercol, "+
			" 'SP01' Type_guid, "+
			" s.approveitemname, "+
			" s.statedescription, "+
			" m.gongchengmingcheng, "+
			" s.BEGINDATE, "+
			" s.estimateenddate, "+
			" s.WorkflowInstance_GUID, "+
			" s.DECLARESN, "+
			" e.employee_guid, "+
			" s.STATE "+
			" from SPI_Supervise                 s, "+
			" OFFICE_WorkflowInstanceActors a, "+
			"  OFFICE_WorkflowInstance       i, "+
			" OFFICE_SPI_DECLAREINFO        m, "+
			" spm_bureau                    sb, "+
			"  risenet_employee              e, "+
			" risenet_department            d, "+
			" spm_approveitem               sa "+
			" where s.WORKFLOWINSTANCE_GUID = a.WorkflowInstance_GUID "+
			" AND s.WORKFLOWINSTANCE_GUID = m.WorkflowInstance_GUID "+
			" AND i.WorkflowInstance_GUID = a.WorkflowInstance_GUID "+
			" AND i.InstanceDeleted = 0 "+
			" AND s.State in (20, 21, 22, 23, 24, 25, 26, 11, 41, 42, 43) "+
			" and s.bureauguid = sb.bureauguid "+
			"  AND a.Person_Guid = e.employee_guid "+
			"  AND e.department_guid = d.department_guid "+
			" AND (i.InstanceStatus = 2 OR i.InstanceStatus = 3) "+
			" AND a.Actors_Classify = 5 "+
			" AND a.handel_status = 4 "+
			"  and s.isFINISHED = 0 "+
			"  and ((trunc(sysdate) < trunc(s.estimateenddate) - 1) or "+
			" s.estimateenddate is null) "+
			" and sa.isproject = 0 "+
			"  and m.approveitemguid = sa.approveitemguid) s "+
			" ORDER BY estimateenddate, ordercol desc";
	//在办件添加特别程序数据
	public static final String tbcxanddoing="union (select sysdate-k.begindate orderIndex,k.* from ("+
			" SELECT distinct s.begindate + 1000 ordercol, "+
			" 'SP01' Type_guid, "+
			"  s.approveitemname, "+
			" s.statedescription, "+
			"  m.gongchengmingcheng, "+
			"  s.BEGINDATE, "+
			"  s.estimateenddate, "+
			"  s.WorkflowInstance_GUID , "+
			" s.DECLARESN, "+
			"  e.employee_guid, "+
			"  s.STATE "+
			"  from SPI_Supervise                 s, "+
			" OFFICE_WorkflowInstanceActors a, "+
			" OFFICE_WorkflowInstance       i, "+
			" OFFICE_SPI_DECLAREINFO        m, "+
			" spm_bureau                    sb, "+
			" risenet_employee              e, "+
			" risenet_department            d, "+
			" spm_approveitem               sa "+
			" where s.WORKFLOWINSTANCE_GUID = a.WorkflowInstance_GUID "+
			" AND s.WORKFLOWINSTANCE_GUID = m.WorkflowInstance_GUID "+
			" AND i.WorkflowInstance_GUID = a.WorkflowInstance_GUID "+
			" AND i.InstanceDeleted = 0 "+
			" AND s.State ='401' "+
			" AND e.employee_guid =? "+
			" AND s.bureauguid = sb.bureauguid "+
			" AND a.Person_Guid = e.employee_guid "+
			" AND e.department_guid = d.department_guid "+
			" AND (i.InstanceStatus = 2 OR i.InstanceStatus = 3) "+
			" AND a.Actors_Classify = 1 "+
			" AND a.handel_status in (1, 2, 3) "+
			" and s.isFINISHED = 0 "+
			" and sa.isproject = 0 "+
			" and m.approveitemguid = sa.approveitemguid "
		      + ") k )";
	//待办件添加特别程序数据
		public static final String tbcxandtodo="union (select sysdate-k.begindate orderIndex,k.* from ("+
				" SELECT distinct s.begindate + 1000 ordercol, "+
				" 'SP01' Type_guid, "+
				"  s.approveitemname, "+
				" s.statedescription, "+
				"  m.gongchengmingcheng, "+
				"  s.BEGINDATE, "+
				"  s.estimateenddate, "+
				"  s.WorkflowInstance_GUID , "+
				" s.DECLARESN, "+
				"  e.employee_guid, "+
				"  s.STATE "+
				"  from SPI_Supervise                 s, "+
				" OFFICE_WorkflowInstanceActors a, "+
				" OFFICE_WorkflowInstance       i, "+
				" OFFICE_SPI_DECLAREINFO        m, "+
				" spm_bureau                    sb, "+
				" risenet_employee              e, "+
				" risenet_department            d, "+
				" spm_approveitem               sa,spm_tbscxqb      t "+
				" where s.WORKFLOWINSTANCE_GUID = a.WorkflowInstance_GUID "+
				" AND s.WORKFLOWINSTANCE_GUID = m.WorkflowInstance_GUID "+
				" AND i.WorkflowInstance_GUID = a.WorkflowInstance_GUID "+
				" AND i.InstanceDeleted = 0 "+
			     "   AND s.State='401'"+
				" and s.bureauguid = sb.bureauguid "+
				" AND a.Person_Guid = e.employee_guid "+
				" AND e.department_guid = d.department_guid "+
				" AND (i.InstanceStatus = 2 OR i.InstanceStatus = 3) "+
				" and m.approveitemguid = sa.approveitemguid "+
			      "  AND T.WORKFLOWINSTANCEGUID=I.WORKFLOWINSTANCE_GUID"+
			      "  AND instr(T.SENDTOPSGUIDS,?)>0 "+
			      "  AND s.isFINISHED = 0 "+
			      "AND A.ACTORS_CLASSIFY=1"
			      + ") k )"   ;
}

