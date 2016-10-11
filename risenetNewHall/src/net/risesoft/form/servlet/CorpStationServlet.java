package net.risesoft.form.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import net.risesoft.beans.onlineservice.CorpGasStation;
import net.risesoft.form.dao.CorpStationDao;
import net.risesoft.form.dao.impl.CorpStationDaoImpl;


public class CorpStationServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static DataSource source;
	// @Resource
	// private ISimpleJdbcDao<CorpStationAction> iSimpleJdbcDao;
	//@Resource
	CorpStationDao corpStationDao = new CorpStationDaoImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
        String insGuid = request.getParameter("insGuid");
        
		Connection conn=null;
		List<CorpGasStation> list=null;
		try{
			conn=source.getConnection();
			list = corpStationDao.queryInfo(insGuid,conn);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(null!=conn){
					conn.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
			request.setAttribute("CorpStationList", list);
			request.getRequestDispatcher("/forms/queryStation.jsp").forward(request, response);
			//request.getRequestDispatcher.forward("/forms/queryStation.jsp");

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();  
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
		source = (DataSource) ctx.getBean("hibernateDataSource"); 
	}
}
