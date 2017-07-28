package net.risesoft.fileflow.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.common.util.ContextUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class ServerStatusServlet extends HttpServlet {
	private static final long serialVersionUID = -4784858452673048698L;

	private static Logger logger = LoggerFactory.getLogger(ServerStatusServlet.class);
	
	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			int i = jdbcTemplate.queryForObject("SELECT 1 FROM DUAL", Integer.class);
			if (i == 1) {
				out.println("success");
			}else{
				out.println("error");
			}			
		} catch (Exception e) {
			logger.error(e.getMessage());
			out.println("error");
		} finally {
			out.flush();
			out.close();
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	@Override
	public void init() throws ServletException {	
		dataSource=ContextUtil.getBean("defaultDataSource");
		jdbcTemplate = new JdbcTemplate(dataSource);
		logger.debug("ServerStatusServlet init...");
	}

}
