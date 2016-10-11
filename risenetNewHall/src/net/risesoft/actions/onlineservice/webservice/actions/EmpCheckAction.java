package net.risesoft.actions.onlineservice.webservice.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.onlineservice.CorpGasEmployee;
import net.risesoft.daos.base.ISimpleJdbcDao;


@Controller
@ParentPackage("default")
// 继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })
public class EmpCheckAction extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Resource
	private ISimpleJdbcDao<EmpCheckAction> iSimpleJdbcDao;
	
	@Resource
	private ISimpleJdbcDao<CorpGasEmployee> corpGasEmployeeJdbcDao;
	
	private String idno;
	
	private String sbguid;
	
	private String empType;
	
	private String [] zyIdno;
	
	//任职人员信息
	@Action(value="/onlineService/doEmpCheck")
	public void doEmpCheck(){
//		select distinct i.name,i.ORGANIZATION_NO from corp_gas_employee t, corp_gas_info i
//	     where t.sb_guid = i.guid
//	       and t.id_no = ?
//	       and i.organization_no <> ( select c.organization_no from corp_gas_info c where c.guid=?) 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		try {
			conn = iSimpleJdbcDao.getNativeConn();
			String sql ="select i.name qyname from corp_gas_employee e,corp_gas_info i " +
					"where e.sb_guid=i.guid and e.id_no=? " +
					"and e.sb_guid<>? " +
					"and e.employee_type=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, idno);	
			pstmt.setString(2, sbguid);
			pstmt.setString(3, empType);
			rs = pstmt.executeQuery();
			while(rs.next()){
				String qyName= rs.getString("qyname") == null ? "" : rs.getString("qyname");
				if(qyName!=null&&!qyName.equals("")){
					System.out.println(qyName);
					outJson("{'qyname':'"+qyName+"'}",null);
				}
			}
			pstmt.close();
			rs.close();
		} catch (DataAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	

		
	}
	/**
	 * 专业技术人员
	 */
//	@Action(value="/onlineService/doZyjsEmpCheck")
//	public void doZyjsEmpCheck(){
//
////		Connection conn = null;
////		PreparedStatement pstmt = null;
////		ResultSet rs = null; 
////			try {
//				//conn = iSimpleJdbcDao.getNativeConn();
//				StringBuffer sb=new StringBuffer();
//				String sql="select distinct t.name as position,t.id_no,i.name from corp_gas_employee t,corp_gas_info i " +
//						"where t.sb_guid=i.guid and t.id_no in ("+this.zyIdno+") and t.employee_type=? and t.sb_guid<>?";
//				System.out.println("this.idNo========="+this.zyIdno);
//				List<CorpGasEmployee> cge=corpGasEmployeeJdbcDao.queryForRow(sql, new String []{this.empType,this.sbguid,"-1","-1"}, CorpGasEmployee.class);
//				int count=cge.size();
//				for(int i=0;i<count;i++){
//					sb.append("姓名为："+cge.get(i).getPosition()+"的人员已经在"+cge.get(i).getName()+"任职<br>");
//				}
//				if(count>0){
//					outJson("{'flag':'1','message':'"+sb.toString()+"'}",null);
//				}else{
//					outJson("{'flag':'0'}", null);
//				}
////				for(int i =0;i<zyIdno.length;i++){
////				String sql ="select i.name qyname from corp_gas_employee e,corp_gas_info i " +
////						"where e.sb_guid=i.guid and e.id_no=? " +
////						"and e.sb_guid<>? " +
////						"and e.employee_type=?";
////				pstmt = conn.prepareStatement(sql);
////				System.out.println("=========:"+zyIdno[i]);
////				pstmt.setString(1, zyIdno[i]);	
////				pstmt.setString(2, sbguid);
////				pstmt.setString(3, empType);
////				rs = pstmt.executeQuery();
////				//while(rs.next()){
////					//String qyName= rs.getString("qyname") == null ? "" : rs.getString("qyname");
////				String qyName="zhuanye";
////					if(qyName!=null&&!qyName.equals("")){
////						System.out.println(qyName);
////						outJson("{'qyname':'"+qyName+"'}",null);
////					}
////			//}
////				}
////			} catch (DataAccessException e) {
////				e.printStackTrace();
////			} 	
//	}
	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getSbguid() {
		return sbguid;
	}

	public void setSbguid(String sbguid) {
		this.sbguid = sbguid;
	}

	public String getEmpType() {
		return empType;
	}

	public void setEmpType(String empType) {
		this.empType = empType;
	}
	public String[] getZyIdno() {
		return zyIdno;
	}
	public void setZyIdno(String[] zyIdno) {
		this.zyIdno = zyIdno;
	}

}
