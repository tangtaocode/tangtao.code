package net.risesoft.actions.onlineservice.webservice.actions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.onlineservice.CorpGasStaeqm;
import net.risesoft.beans.onlineservice.CorpGasStation;
import net.risesoft.daos.base.IBaseDao;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.ICodeMapUtil;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

@Controller
@ParentPackage("default")
// 继承json包，用于返回json
@InterceptorRefs( { @InterceptorRef("isLoginStack") })
// 权限拦截
public class CorpStationAction extends BaseActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource
	private ISimpleJdbcDao<CorpStationAction> iSimpleJdbcDao;

	
	
	private String[] czsize;//场站信息数量
	private String[] czname;//场站名称
	private String[] address;//场站地址
	private String[] coordinate1x;//1坐标
	private String[] coordinate1y;//1坐标
	private String[] coordinate2x;//2坐标
	private String[] coordinate2y;//2坐标
	private String[] coordinate3x;//3坐标
	private String[] coordinate3y;//3坐标
	private String[] coordinate4x;//4坐标
	private String[] coordinate4y;//4坐标
	private String[] propertyno;//自有房屋产权号
	private String[] rentalno;//租用租凭合同号
	private String[] startdate;//开工日期
	private String[] enddate;//竣工日期
	private String[] acceptanceno;//消防验收意见书编号
	private String[] supply;//供气能力
	private String[] designcorp;//设计单位
	private String[] constructioncorp;//施工单位
	private String[] gassource;//燃气来源
	private String[] secorg;//安评机构
	private String[] volume;//总容积
	private String[] brief;//场站简介
	private String form_no;
	
	
	private String[] install_type;//安装形式
	private String[] station_type;//场站类型   
	
	private String method;//操作方法
	private String insGuid;//示例guid
	
	private List<CorpGasStation> corpGasStationList;	
	
    /**
     * 保存场站信息
     * @return
     */
	@Action(value="/onlineService/doCorpStationAction" , results = {@Result(name = "success" , location = "/corpStation/stationInit.jsp")})
	public String saveInfo() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;         

		try {
			
			conn = iSimpleJdbcDao.getNativeConn();
			if(method!=null && method.equals("save")){//保存数据
				getRequest().setAttribute("insGuid", insGuid);
				String sql = "delete from corp_gas_station t where SB_GUID=?";
				//删除场站
				if(pstmt!=null)
					pstmt.close();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, insGuid);
				pstmt.executeUpdate();
				//删除场站设备
				sql = "delete from CORP_GAS_STA_EQM t where SB_GUID=?";
				if(pstmt!=null)
					pstmt.close();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, insGuid);
				pstmt.executeUpdate();
				
				//获取场站信息数量
		        for(int i =0;i<czsize.length;i++){
		        	String ydzl = getRequest().getParameter("ydzl"+czsize[i]);

		        	//获取场站类型
		        	sql = "insert into corp_gas_station(guid, name, address, station_type_1, property_no, rental_no,start_date,end_date, acceptance_no, supply, design_corp, construction_corp, " +
		        			"gas_source, sec_org, volume, install_type_1, brief, corp_gas_guid, coordinate_1_x, " +
		        			"coordinate_1_y, coordinate_2_x, coordinate_2_y, coordinate_3_x, coordinate_3_y, coordinate_4_x, " +
		        			"coordinate_4_y, sb_guid,NO_TYPE)" +
//		        			" values(?,?,?,?,?,?,'"+startdate[i]+"','"+enddate[i]+"',?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		        			" values(?,?,?,?,?,?,to_date('"+startdate[i]+"','yyyy-MM-dd'),to_date('"+enddate[i]+"','yyyy-MM-dd'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		        	if(pstmt!=null)
						pstmt.close();
		        	pstmt = conn.prepareStatement(sql);
		        	String czguid = new GUID().toString();
		        	pstmt.setString(1, czguid);
		        	pstmt.setString(2, czname[i]);
		        	pstmt.setString(3, address[i]);
		        	pstmt.setString(4, this.station_type[i]);
		        	pstmt.setString(5, this.propertyno[i]);
		        	pstmt.setString(6, this.rentalno[i]);
		        	pstmt.setString(7,this.acceptanceno[i] );
		        	pstmt.setString(8, this.supply[i]);
		        	pstmt.setString(9, this.designcorp[i]);
		        	pstmt.setString(10, this.constructioncorp[i]);
		        	pstmt.setString(11, this.gassource[i]);
		        	pstmt.setString(12, this.secorg[i]);
		        	pstmt.setString(13, this.volume[i]);
		        	pstmt.setString(14, this.install_type[i]);
		        	pstmt.setString(15, this.brief[i]);
		        	pstmt.setString(16, null);
		        	pstmt.setString(17, this.coordinate1x[i]);
		        	pstmt.setString(18, this.coordinate1y[i]);
		        	pstmt.setString(19, this.coordinate2x[i]);
		        	pstmt.setString(20, this.coordinate2y[i]);
		        	pstmt.setString(21, this.coordinate3x[i]);
		        	pstmt.setString(22, this.coordinate3y[i]);
		        	pstmt.setString(23, this.coordinate4x[i]);
		        	pstmt.setString(24, this.coordinate4y[i]);
		        	pstmt.setString(25, insGuid);	
		        	pstmt.setString(26, ydzl);	
		        	pstmt.executeUpdate();
		        	//场站设备添加
		        	//获取安装形式
		        	String czsbName[]=getRequest().getParameterValues("name"+czsize[i]);//获取场站设备名称
		        	String czsbProduct[]=getRequest().getParameterValues("plc_of_product"+czsize[i]);//获取产地
		        	String czsbModel[]=getRequest().getParameterValues("product_model"+czsize[i]);//获取规格型号 
		        	String czsbTProduct[]=getRequest().getParameterValues("total_product"+czsize[i]);//获取数量 
		        	String[] czsbDate=getRequest().getParameterValues("val_date_of_test"+czsize[i]);//获取检查有效期
		        	for(int j=0;j<czsbName.length;j++){
		        		sql=" insert into CORP_GAS_STA_EQM(guid, name," +
		        				" product_model, total_product, val_date_of_test," +
//		        				" distinction, sb_guid, station_guid) values(?,?,?,?,?,to_date('"+czsbDate[j]+"','yyyy-MM-dd'),?,?,?)";
		        				" distinction, sb_guid, station_guid) values(?,?,?,?,?,?,?,?)";
		        		if(pstmt!=null)
							pstmt.close();
		        		pstmt = conn.prepareStatement(sql);
			        	String czsbguid = new GUID().toString(); 
			        	pstmt.setString(1, czsbguid);
			        	pstmt.setString(2, czsbName[j]);
//			        	pstmt.setString(3,czsbProduct[j]);
			        	pstmt.setString(3,czsbModel[j] );
			        	pstmt.setString(4, czsbTProduct[j]);
			        	pstmt.setString(5,czsbDate[j]);
			        	pstmt.setString(6, "1");
			        	pstmt.setString(7, insGuid);
			        	pstmt.setString(8,czguid);
			        	pstmt.executeUpdate();
		        	}
		        }
	        }
			getRequest().setAttribute("StationList", getStationList(insGuid));
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null)
					pstmt.close();
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}		
		return "success";
	}
	
	/**
	 *  查询
	 * @param insGuid
	 * @return
	 */
	public List<CorpGasStation> getStationList(String insGuid){
		List<CorpGasStation> list = new ArrayList<CorpGasStation>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null; 
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null; 
		try{			
			conn = iSimpleJdbcDao.getNativeConn();
			String sql = "SELECT t.*,to_char(t.START_DATE,'yyyy-MM-dd') START_DATE1,to_char(END_DATE,'yyyy-MM-dd') END_DATE1 from CORP_GAS_STATION t where t.SB_GUID=? ";
//			String sql = "SELECT * from CORP_GAS_STATION t where t.SB_GUID=?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, insGuid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				//场站信息
				CorpGasStation sta = new CorpGasStation();
				sta.setCzname(rs.getString("NAME"));
				sta.setAddress(rs.getString("ADDRESS"));
				sta.setStation_type(rs.getString("STATION_TYPE_1"));
				sta.setPropertyno(rs.getString("PROPERTY_NO"));
				sta.setRentalno(rs.getString("RENTAL_NO"));
				sta.setStartdate(rs.getString("START_DATE1"));
				sta.setEnddate(rs.getString("END_DATE1"));
				sta.setAcceptanceno(rs.getString("ACCEPTANCE_NO"));
				sta.setSupply(rs.getString("SUPPLY"));
				sta.setDesigncorp(rs.getString("DESIGN_CORP"));
				sta.setConstructioncorp(rs.getString("CONSTRUCTION_CORP"));
				sta.setBrief(rs.getString("BRIEF"));
				sta.setVolume(rs.getString("VOLUME"));
				sta.setGassource(rs.getString("GAS_SOURCE"));
				sta.setSecorg(rs.getString("SEC_ORG"));
				sta.setInstall_type(rs.getString("INSTALL_TYPE_1"));
				sta.setCoordinate1x(rs.getString("coordinate_1_x"));
				sta.setCoordinate1y(rs.getString("coordinate_1_y"));
				sta.setCoordinate2x(rs.getString("coordinate_2_x"));
				sta.setCoordinate2y(rs.getString("coordinate_2_y"));
				sta.setCoordinate3x(rs.getString("coordinate_3_x"));
				sta.setCoordinate3y(rs.getString("coordinate_3_y"));
				sta.setCoordinate4x(rs.getString("coordinate_4_x"));
				sta.setCoordinate4y(rs.getString("coordinate_4_y"));
				sta.setForm_no(rs.getString("form_no"));
				sta.setNo_type(rs.getString("no_type"));
				//场站设备
//				sql = "select t.*,to_char(t.val_date_of_test,'yyyy-MM-dd') val_date_of_test1 from corp_gas_sta_eqm t where t.STATION_GUID=?";
				sql = "select * from corp_gas_sta_eqm t where t.STATION_GUID=? ";

				pstmt1 = conn.prepareStatement(sql);
				pstmt1.setString(1, rs.getString("guid"));
				rs1 = pstmt1.executeQuery();
				ArrayList eqmList = new ArrayList();
				while(rs1.next()){
					CorpGasStaeqm obj = new CorpGasStaeqm();
					obj.setName(rs1.getString("name"));//设备名称
//					obj.setPlc_of_product(rs1.getString("plc_of_product"));//产地
					obj.setProduct_model(rs1.getString("product_model"));//规格型号
					obj.setTotal_product(rs1.getString("total_product"));//数量
					obj.setVal_date_of_test(rs1.getString("val_date_of_test"));//检查有效期
					eqmList.add(obj);
				}
				sta.setEqmList(eqmList);
				list.add(sta);
				pstmt1.close();
				rs1.close();
			}
			pstmt.close();
			rs.close();
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return list;
	}
	
	/**
	 * 测试
	 */
	@Action(value="/onlineService/doTestJsp" , results = {@Result(name = "success" , location = "/forms/test.jsp")})
	public String doTestJsp(){
		return "success";	
	}
	
	public String[] getCzname() {
		return czname;
	}
	public void setCzname(String[] czname) {
		this.czname = czname;
	}
	public String[] getStation_type() {
		return station_type;
	}
	public void setStation_type(String[] stationType) {
		station_type = stationType;
	}



	public String[] getCzsize() {
		return czsize;
	}



	public void setCzsize(String[] czsize) {
		this.czsize = czsize;
	}



	public ICodeMapUtil getCodeMapUtil() {
		return codeMapUtil;
	}



	public void setCodeMapUtil(ICodeMapUtil codeMapUtil) {
		this.codeMapUtil = codeMapUtil;
	}



	public String[] getAddress() {
		return address;
	}



	public void setAddress(String[] address) {
		this.address = address;
	}



	public String[] getCoordinate1x() {
		return coordinate1x;
	}



	public void setCoordinate1x(String[] coordinate1x) {
		this.coordinate1x = coordinate1x;
	}



	public String[] getCoordinate1y() {
		return coordinate1y;
	}



	public void setCoordinate1y(String[] coordinate1y) {
		this.coordinate1y = coordinate1y;
	}



	public String[] getCoordinate2x() {
		return coordinate2x;
	}



	public void setCoordinate2x(String[] coordinate2x) {
		this.coordinate2x = coordinate2x;
	}



	public String[] getCoordinate2y() {
		return coordinate2y;
	}



	public void setCoordinate2y(String[] coordinate2y) {
		this.coordinate2y = coordinate2y;
	}



	public String[] getCoordinate3x() {
		return coordinate3x;
	}



	public void setCoordinate3x(String[] coordinate3x) {
		this.coordinate3x = coordinate3x;
	}



	public String[] getCoordinate3y() {
		return coordinate3y;
	}



	public void setCoordinate3y(String[] coordinate3y) {
		this.coordinate3y = coordinate3y;
	}



	public String[] getCoordinate4x() {
		return coordinate4x;
	}



	public void setCoordinate4x(String[] coordinate4x) {
		this.coordinate4x = coordinate4x;
	}



	public String[] getCoordinate4y() {
		return coordinate4y;
	}



	public void setCoordinate4y(String[] coordinate4y) {
		this.coordinate4y = coordinate4y;
	}



	public String[] getPropertyno() {
		return propertyno;
	}



	public void setPropertyno(String[] propertyno) {
		this.propertyno = propertyno;
	}



	public String[] getRentalno() {
		return rentalno;
	}



	public void setRentalno(String[] rentalno) {
		this.rentalno = rentalno;
	}



	public String[] getStartdate() {
		return startdate;
	}



	public void setStartdate(String[] startdate) {
		this.startdate = startdate;
	}



	public String[] getEnddate() {
		return enddate;
	}



	public void setEnddate(String[] enddate) {
		this.enddate = enddate;
	}



	public String[] getAcceptanceno() {
		return acceptanceno;
	}



	public void setAcceptanceno(String[] acceptanceno) {
		this.acceptanceno = acceptanceno;
	}



	public String[] getSupply() {
		return supply;
	}



	public void setSupply(String[] supply) {
		this.supply = supply;
	}



	public String[] getDesigncorp() {
		return designcorp;
	}



	public void setDesigncorp(String[] designcorp) {
		this.designcorp = designcorp;
	}



	public String[] getConstructioncorp() {
		return constructioncorp;
	}



	public void setConstructioncorp(String[] constructioncorp) {
		this.constructioncorp = constructioncorp;
	}



	public String[] getGassource() {
		return gassource;
	}



	public void setGassource(String[] gassource) {
		this.gassource = gassource;
	}



	public String[] getSecorg() {
		return secorg;
	}



	public void setSecorg(String[] secorg) {
		this.secorg = secorg;
	}



	public String[] getVolume() {
		return volume;
	}



	public void setVolume(String[] volume) {
		this.volume = volume;
	}



	public String[] getBrief() {
		return brief;
	}



	public void setBrief(String[] brief) {
		this.brief = brief;
	}


	public String getMethod() {
		return method;
	}



	public void setMethod(String method) {
		this.method = method;
	}



	public String getInsGuid() {
		return insGuid;
	}



	public void setInsGuid(String insGuid) {
		this.insGuid = insGuid;
	}



	public String[] getInstall_type() {
		return install_type;
	}



	public void setInstall_type(String[] installType) {
		install_type = installType;
	}

	public List<CorpGasStation> getCorpGasStationList() {
		return corpGasStationList;
	}

	public void setCorpGasStationList(List<CorpGasStation> corpGasStationList) {
		this.corpGasStationList = corpGasStationList;
	}

	public String getForm_no() {
		return form_no;
	}

	public void setForm_no(String formNo) {
		form_no = formNo;
	}
	

}
