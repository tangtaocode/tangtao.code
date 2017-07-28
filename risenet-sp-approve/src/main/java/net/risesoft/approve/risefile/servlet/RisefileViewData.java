package net.risesoft.approve.risefile.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.risefile.RiseFileManagerProxy;
import net.risesoft.approve.risefile.commons.StringUtil;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.handler.RiseFileInputStream;

public class RisefileViewData extends HttpServlet{
	
	public RisefileViewData() {
		super();
	}

	public void destroy() {
		super.destroy(); 
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RiseFileConfig config = null;
		try{
			long time1 = 0, time2 = 0;
			time1 = System.currentTimeMillis();
			String fileGUID=request.getParameter("fileGUID");
			String fileName=request.getParameter("fileName");
//			if(GUIDUtil.inValidate("")){
//				throw new IllegalArgumentException("没有传入正确的fileGUID");
//			}
			config = makeConfig(request,config);
			RiseFileInputStream fileis=RiseFileManagerProxy.getContent(config,fileGUID);

			response.setContentType("application/octet-stream");
			response.setHeader("Content-Disposition",
							"attachment;filename=\"" +fileName
							+ "\"");
			response.setContentLength((int) fileis.size);
			
			ServletOutputStream sos = response.getOutputStream();
			byte buffer[] = new byte[80 * 1024];
			int b=0;
			while ((b = fileis.inputStream.read(buffer)) != -1) {
				sos.write(buffer, 0, b);
			}
			sos.flush();
			fileis.inputStream.close();
			if(fileis.sourceInputStream!=null){
				fileis.sourceInputStream.close();
			}
			sos.close();
			time2 = System.currentTimeMillis();
			System.out.println("文件搜索耗时 " + (time2 - time1) + "ms");
			
		}catch (RiseFileException e) {
			System.out.println("文件系统出现异常:"+e);
		}catch(IllegalArgumentException e){
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} catch(Exception e){
			System.out.println(e);
		}
	}
	
	private RiseFileConfig makeConfig(HttpServletRequest request,RiseFileConfig riseFileConfig){
		
		String appname=request.getParameter("appname");
		if(appname==null){
			throw new IllegalArgumentException("没有传入正确的APPNAME");
		}
		RiseFileConfig config=RiseFileConfigManager.getExtendedFileConfig(appname);
		config.setFileboxName(StringUtil.parseNull(request.getParameter("fileboxname")));
		config.setSaveMode(StringUtil.parseNull(request.getParameter("savemode")));
		config.setFileRoot(StringUtil.parseNull(request.getParameter("fileroot")));
		if(!request.getParameter("majorversion").equals("")){
			config.setMajorVersion(request.getParameter("majorversion"));
		}
		config.setAppInstGUID(request.getParameter("appInstGUID"));
		config.setMajorVersionName(StringUtil.parseNull(request.getParameter("majorversionname")));
		config.setKeepMinimalVersion(Boolean.valueOf(request.getParameter("keepminimalversion")).booleanValue());
		
		config.setHandleExtends(Boolean.valueOf(request.getParameter("ishandleextends")).booleanValue());
		if(!request.getParameter("streamhandles").equals("")){
			config.setStreamHandles(Arrays.asList(request.getParameter("streamhandles").split(";")));
		}
		riseFileConfig=config;
		return riseFileConfig;
	}

	public void init() throws ServletException {
	}
}
