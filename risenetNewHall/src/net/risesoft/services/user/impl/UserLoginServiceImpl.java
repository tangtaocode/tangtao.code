package net.risesoft.services.user.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.risesoft.beans.system.MessageConfig;
import net.risesoft.beans.user.CompanyUser;
import net.risesoft.beans.user.PersonUser;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.daos.base.impl.BaseDaoImpl;
import net.risesoft.services.user.ICompanyUserService;
import net.risesoft.services.user.IPersonUserService;
import net.risesoft.services.user.IUserLoginService;
import net.risesoft.utils.base.GUID;
import net.risesoft.utils.base.ICodeMapUtil;
/**
 * @登录用户服务
 * 
 * @author 张坤
 * @Date 2013-1-24 上午10:50:24
 */
@Service
public class UserLoginServiceImpl extends BaseDaoImpl<UserInfo> implements IUserLoginService{
	@Resource
	private ICompanyUserService companyUserService;
	@Resource
	private IPersonUserService personUserService;
	@Resource
	private ICodeMapUtil codeMapUtil;
	public UserInfo getUserByLoginName(String loginName) {
		List<UserInfo> list =  findByProperty("loginname", loginName);
		if(list.size()==0){
			try {
				List<CompanyUser> clist=companyUserService.findByProperty("orgcode", loginName);//组织机构代码
				if(clist.size()>0){
					list=findByProperty("guid", clist.get(0).getLogonguid());
				}else{
					List<PersonUser> plist =  personUserService.findByProperty("idcard_code", loginName);//身份证号码
					if(plist.size()>0){
						list=findByProperty("guid", plist.get(0).getLogonguid());
					}
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		/**
		 * 先注释掉获取统一登录验证
		 * update by Jon
		 */
		/*Map<String, String> dataSource = codeMapUtil.getMapByType("dataSource.properties");
		String url=dataSource.get("djww.database.url");
		String username=dataSource.get("djww.database.user");
		String pass=dataSource.get("djww.database.password");
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			Connection conn = DriverManager.getConnection(url,username,pass);
			String sql="select * from sys_users t where t.usercode=?";
			ps=conn.prepareStatement(sql);
			ps.setString(1, loginName);
			rs=ps.executeQuery();
			if(rs.next()){
				UserInfo user=new UserInfo();
				user.setPassword(rs.getString("password"));
				user.setCardtype("1");
				user.setUsertype("2");
				list.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	public CompanyUser getCompanyUser(String loginGUID)throws Exception {
		List<CompanyUser> list =  companyUserService.findByProperty("logonguid", loginGUID);
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	public CompanyUser getOrgInfo(String OrgId)throws Exception{
		
			
		CompanyUser orgUser = new CompanyUser();
			Map<String, String> dataSource = codeMapUtil
					.getMapByType("dataSource.properties");
			String sql = "select t.orgid,t.orgName from dbo.test_org t where t.orgid=?";
			Connection conn = null;
			PreparedStatement pst = null;
			try {
				Class.forName(dataSource.get("sqlserver.database.driver"))
						.newInstance();				
				conn = DriverManager.getConnection(dataSource
						.get("sqlserver.database.url"), dataSource
						.get("sqlserver.database.user"), dataSource
						.get("sqlserver.database.password"));
				pst = conn.prepareStatement(sql);
				pst.setString(1, OrgId);			
				
				ResultSet rs = (ResultSet)pst.executeQuery();
				while (rs.next())
				 {
				String str1=rs.getString(1);
				String str=rs.getString("orgName").toString();
				String zch=rs.getString("ZCH").toString();
				orgUser.setName(str);
				orgUser.setRegcode(zch);
				//orgUser.set
				 }
				return orgUser;
				
			} catch (Exception e) {
				e.printStackTrace();
				return orgUser;
			}finally{
				try {
					if(pst!=null)pst.close();
					if(conn!=null)conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		
		
	}
	public PersonUser getPersonUser(String loginGUID)throws Exception {
		List<PersonUser> list =  personUserService.findByProperty("logonguid", loginGUID);
		if(list.size()==0){
			return null;
		}else{
			return list.get(0);
		}
	}
	@Override
	public String getLoginNameById(String guid) throws Exception {
		List<UserInfo> list = findByProperty("guid", guid);
		if(null != list && list.size()>0){
			return list.get(0).getLoginname();
		}
		return null;
	}
	@Override
	public String getPwdById(String guid) throws Exception {
		List<UserInfo> list = findByProperty("guid", guid);
		if(null != list && list.size()>0){
			return list.get(0).getPassword();
		}
		return null;
	}
}
