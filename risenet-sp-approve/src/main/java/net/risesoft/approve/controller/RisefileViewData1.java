package net.risesoft.approve.controller;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.RiseFileManagerProxy;
import net.risesoft.approve.risefile.exception.RiseFileException;

@Controller
@RequestMapping("/risefileViewData1")
public class RisefileViewData1 extends HttpServlet {
	

	@RequestMapping(value="/view",method = RequestMethod.GET)
	@ResponseBody
	public void view(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try{			
			long time1 = 0, time2 = 0;
			time1 = System.currentTimeMillis();
			String appname = request.getParameter("appname");
			String fileGUID = request.getParameter("fileGUID");
			//String fileName = request.getParameter("fileName");//delete by  liujun fileName可能包含特殊字符比如%在tomcat下面没有问题，会返回null webphere 中会抛出异常
//			if(GUIDUtil.inValidate(fileGUID)){
//				throw new IllegalArgumentException("没有传入正确的fileGUID");
//			}
			RiseFile riseFile = RiseFileManagerProxy.get(appname,fileGUID);
			InputStream in = null; 
			try { 
				in = new FileInputStream(riseFile.getRealFullPath());
			}catch (FileNotFoundException e) { 
				throw new Exception("文件没找到。");
			} 
			 response.reset();
			 if(riseFile.getFileNameExt().equals("pdf")){
			    response.setContentType("application/pdf");
			 }else{
                response.setContentType("application/octet-stream");
			 }
			//response.setHeader("Content-Disposition","filename=\"" + StringI18.u2a(fileName) + "\"");
			
			ServletOutputStream sos = response.getOutputStream();
			byte buffer[] = new byte[80 * 1024];
			int b=0;
			while ((b = in.read(buffer)) != -1) {
				sos.write(buffer, 0, b);
			}
			sos.flush();
			in.close();
			sos.close();
			time2 = System.currentTimeMillis();
			System.out.println("文件搜索1耗时： " + (time2 - time1) + "ms");
			
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
}
