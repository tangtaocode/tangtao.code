package net.risesoft.utils.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.beans.base.DownFile;
import net.risesoft.common.Common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * 
  * @ClassName: DownFileServlet
  * @Description: 文件下载servlet
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:22:39 PM
  *
 */
public class DownFileServlet extends HttpServlet {
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = 3998609311013849676L;
	private Map<String, String> map;
	private final Log log = LogFactory.getLog(getClass());
	private DownFile file;
	private static DataSource source;
	/**
	 * Constructor of the object.
	 */
	public DownFileServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		InputStream inStream = null;
		byte[] buffer = null;
		response.setContentType("application/x-download");
		String downType = request.getParameter("downType");
		String fileId = request.getParameter("fileId");
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pst = null;
		try {
			 conn = source.getConnection();
			 pst = conn.prepareStatement(map.get(downType + Common.sql));
			 System.out.println(downType + Common.sql+"===>"+map.get(downType + Common.sql));
			 pst.setString(1, fileId);
			 rs = pst.executeQuery();
			 System.out.println("systemType===>"+map.get("systemType"));
			 String filepath="";
			if(rs.next()){
				file = new DownFile();				
				file.setFileName(rs.getString(1));
				//根据不同操作系统，获取文件路径不同
				if(map.get("systemType")!=null && map.get("systemType").toLowerCase().equals("windows")){
					filepath = rs.getString(2);
					file.setFilePath(rs.getString(2));//windows系统
				}else if(map.get("systemType")!=null && map.get("systemType").toLowerCase().equals("linux")){
					if(rs.getString(4)!=null && rs.getString(4)!=""){
						filepath = rs.getString(4); 
						file.setFilePath(rs.getString(4));//linux系统
					}
				}else
					return;
				file.setBlobColmon(rs.getBlob(3));
			}else{
				if(log.isErrorEnabled())log.error("根据fileId："+fileId+"，未找到相应的文件");
				return;
			}
			response.addHeader("Content-Disposition", "attachment;filename="
					+ URLEncoder.encode(file.getFileName(), "UTF-8"));
			ServletOutputStream out = response.getOutputStream();
			System.out.println("file.getFilePath()==>"+file.getFilePath());
			if (StringUtils.isBlank(filepath)) {
				inStream = file.getBlobColmon().getBinaryStream();
				buffer = new byte[1024];
			} else {
				inStream = new FileInputStream(new File(file.getFilePath()));
				buffer = new byte[1024 * 1024 * 20];// 缓冲区
			}
			int length = 0;
			// 读写数据
			while ((length = inStream.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(rs!=null)rs.close();
				if(pst!=null)pst.close();
				if(conn!=null)conn.close();
				if(inStream!=null)inStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	public void init() throws ServletException {
		ServletContext servletContext = this.getServletContext();  
		WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
		source = (DataSource) ctx.getBean("hibernateDataSource"); 
		Properties prop = new Properties();
		map = new HashMap<String, String>();
		try {
			prop.load(this.getClass().getResourceAsStream(
					"/downFileTypeData.properties"));
			Set<Entry<Object, Object>> entry = prop.entrySet();
			for (Entry<Object, Object> en : entry) {
				map.put((String) en.getKey(), (String) en.getValue());
			}
		} catch (IOException e) {
			if (log.isDebugEnabled())
				log.debug("加载文件下载资源文件downFileTypeData.properties错误，信息"
						+ e.getMessage());
			e.printStackTrace();
		}
	}

}
