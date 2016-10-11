package net.risesoft.utils.templateAction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import net.business.engine.common.I_TemplateAction;
import net.business.engine.common.TemplateContext;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.sysmain.common.I_TemplateConstant;

public class QyInfoInit  implements I_TemplateAction
{
    public int execute(TemplateContext context) throws Exception{
    	//1、依据当前用户的身份从数据库中获取信息，代码略
        //context.getServletContext(),context.getRequest()可以获取相应的对象
    	String sql="select * from T_OUT_COMPANYUSER t where t.logonguid=?";
    	String userId=((UserInfo) context.getRequest().getSession().getAttribute(Common.sessionLoginUserID)).getGuid();
    	Connection conn=null;
    	PreparedStatement ps=null;
    	ResultSet rs=null;
    	try{
	    	ServletContext servletContext = context.getRequest().getSession().getServletContext();  
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
			DataSource source = (DataSource) ctx.getBean("hibernateDataSource"); 
			conn=source.getConnection();
			ps=conn.prepareStatement(sql);
			ps.setString(1, userId);
			rs=ps.executeQuery();
			while(rs.next()){
		    	if(context.getTemplatePara().getEditType() == I_TemplateConstant.ACTION_TYPE_ADD) 		{
		        	//2、将字段信息存储到变量中，不同的字段采用不同的变量名称，示例：
		        	context.put("COPR_NAME", rs.getString("ename"));
		        	context.put("COPR_ORGANIZATION_NO", rs.getString("ORGCODE"));
		        	context.put("COPR_ADRESS", rs.getString("ADDRESS"));
		        	context.put("COPR_REGISTER_CAPITAL", rs.getString("REGMONEY"));
		        	context.put("COPR_REGISTER_NO", rs.getString("REGCODE"));
		        	context.put("COPR_CORP_TYPE", rs.getString("KIND"));
		        	context.put("COPR_SETUP_DATE", rs.getString("REGDATE"));
		        	context.put("COPR_LEGAL_REP", rs.getString("LAWPERSON"));
		        }
				else
				{
		    		//其中cname是表单元素名称，最好所有的表单统一，这样使用一个类就可以了
					if(context.get("F_COPR_NAME")==null||context.get("F_COPR_NAME").equals("")){//企业名称
						context.put("F_COPR_NAME", rs.getString("name"));
					}
					if(context.get("F_COPR_ORGANIZATION_NO")==null||context.get("F_COPR_ORGANIZATION_NO").equals("")){//组织机构代码
						context.put("F_COPR_ORGANIZATION_NO", rs.getString("ORGCODE"));
					}
					if(context.get("F_COPR_ADRESS")==null||context.get("F_COPR_ADRESS").equals("")){//企业住所
						context.put("F_COPR_ADRESS", rs.getString("ADDRESS"));
					}
					if(context.get("F_COPR_REGISTER_CAPITAL")==null||context.get("F_COPR_REGISTER_CAPITAL").equals("")){//企业注册资本
						context.put("F_COPR_REGISTER_CAPITAL", rs.getString("REGMONEY"));
					}
					if(context.get("F_COPR_REGISTER_NO")==null||context.get("F_COPR_REGISTER_NO").equals("")){//营业执照
						context.put("F_COPR_REGISTER_NO", rs.getString("REGCODE"));
					}
					if(context.get("F_COPR_CORP_TYPE")==null||context.get("F_COPR_CORP_TYPE").equals("")){//企业类型
						context.put("F_COPR_CORP_TYPE", rs.getString("KIND"));
					}
					if(context.get("F_COPR_SETUP_DATE")==null||context.get("F_COPR_SETUP_DATE").equals("")){//成立时间
						context.put("F_COPR_SETUP_DATE", rs.getString("REGDATE"));
					}
					if(context.get("F_COPR_LEGAL_REP")==null||context.get("F_COPR_LEGAL_REP").equals("")){//法定代表人
						context.put("F_COPR_LEGAL_REP", rs.getString("LAWPERSON"));
					}
			
				}
			}
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    		rs.close();
    		ps.close();
    		conn.close();
    	}

        return SUCCESS;
    }

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}
}

