package net.risesoft.form.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.onlineservice.CorpGasStaeqm;
import net.risesoft.beans.onlineservice.CorpGasStation;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.form.dao.CorpStationDao;
//import net.risesoft.utils.base.ICodeMapUtil;
@Controller
public class CorpStationDaoImpl implements CorpStationDao {
	
	@Resource
	private ISimpleJdbcDao<CorpGasStation> corpGasStationJdbcDao;
	@Resource
	private ISimpleJdbcDao<CorpGasStaeqm> corpGasStaeqmJdbcDao;
	
	@Override
	public List<CorpGasStation> queryInfo(String insGuid,Connection conn) {
		
		List<CorpGasStation> list = new ArrayList<CorpGasStation>();

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		PreparedStatement pstmt1 = null;
		ResultSet rs1 = null;
		try {


			String sql = "SELECT t.*,to_char(t.START_DATE,'yyyy-MM-dd') START_DATE1,to_char(END_DATE,'yyyy-MM-dd') END_DATE1 from CORP_GAS_STATION t where t.SB_GUID=? ";
			//List<CorpGasStation> corpGasStationList=corpGasStationJdbcDao.queryForRow(sql, new String[]{insGuid}, CorpGasStation.class);
			//conn = getNativeConn();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, insGuid);
			rs = pstmt.executeQuery();
			while(rs.next()){
				// 场站信息
				CorpGasStation sta = new CorpGasStation();
//				sta.setCzname(sta.getCzname());
//				sta.setAddress(sta.getAcceptanceno());
//				sta.setStation_type(sta.getStation_type());
//				sta.setPropertyno(sta.getPropertyno());
//				sta.setRentalno(sta.getRentalno());
//				sta.setStartdate(sta.getStartdate());
//				sta.setEnddate(sta.getEnddate());
//				sta.setAcceptanceno(sta.getAcceptanceno());
//				sta.setSupply(sta.getSupply());
//				sta.setDesigncorp(sta.getDesigncorp());
//				sta.setConstructioncorp(sta.getConstructioncorp());
//				sta.setBrief(sta.getBrief());
//				sta.setVolume(sta.getVolume());
//				sta.setGassource(sta.getGassource());
//				sta.setSecorg(sta.getSecorg());
//				sta.setInstall_type(sta.getInstall_type());
//				sta.setCoordinate1x(sta.getCoordinate1x());
//				sta.setCoordinate1y(sta.getCoordinate1y());
//				sta.setCoordinate2x(sta.getCoordinate2x());
//				sta.setCoordinate2y(sta.getCoordinate2y());
//				sta.setCoordinate3x(sta.getCoordinate3x());
//				sta.setCoordinate3y(sta.getCoordinate3y());
//				sta.setCoordinate4x(sta.getCoordinate4x());
//				sta.setCoordinate4y(sta.getCoordinate4y());
//				sta.setForm_no(sta.getForm_no());
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
				// 场站设备
				// sql =
				// "select t.*,to_char(t.val_date_of_test,'yyyy-MM-dd') val_date_of_test1 from corp_gas_sta_eqm t where t.STATION_GUID=?";
				sql = "select * from corp_gas_sta_eqm t where t.STATION_GUID=? ";
				//List<CorpGasStaeqm> corpGasStaeqmList =corpGasStaeqmJdbcDao.queryForRow(sql, new String[]{sta.getGuid()}, CorpGasStaeqm.class);
				pstmt1 = conn.prepareStatement(sql);
				pstmt1.setString(1, rs.getString("guid"));
				rs1 = pstmt1.executeQuery();
				ArrayList eqmList = new ArrayList();
//				while (rs1.next()) {
				while(rs1.next()){
					CorpGasStaeqm obj = new CorpGasStaeqm();
					//obj.setName(obj.getName());// 设备名称
					//obj.setPlc_of_product(obj.getPlc_of_product());// 产地
					//obj.setProduct_model(obj.getProduct_model());// 规格型号
					//obj.setTotal_product(obj.getTotal_product());// 数量
					//obj.setVal_date_of_test(obj.getVal_date_of_test());// 检查有效期
					obj.setName(rs1.getString("NAME"));// 设备名称
//					obj.setPlc_of_product(rs1.getString("plc_of_product"));// 产地
					obj.setProduct_model(rs1.getString("product_model"));// 规格型号
					obj.setTotal_product(rs1.getString("total_product"));// 数量
					obj.setVal_date_of_test(rs1.getString("val_date_of_test"));// 检查有效期
					eqmList.add(obj);
				}
				sta.setEqmList(eqmList);
				list.add(sta);
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return list;
	}

}
