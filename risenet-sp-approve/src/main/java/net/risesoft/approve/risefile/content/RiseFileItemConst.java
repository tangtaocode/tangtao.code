package net.risesoft.approve.risefile.content;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import net.risesoft.approve.risefile.config.RiseFileConfigManager;

public class RiseFileItemConst {
	private static RiseFileItemConst fileConst= new RiseFileItemConst();
	
	/**
	 * riseRepository为临时存储目录
	 */
	private File riseRepository = null;// 
	
	public static int riseSizeThreshold = RiseFileConfigManager.getThreadMemorySize();// riseSizeThreshold为同时支持文件处理的进程数
		
	
	private RiseFileItemConst()
	{
	}
	
	public static RiseFileItemConst getInstance(HttpServletRequest request)
	{
		if(fileConst.riseRepository==null)
		{
			synchronized(fileConst)
			{
				String filePath=request.getRealPath("/");
				if(!filePath.endsWith(File.separator)) 
					filePath=filePath+File.separator;
				filePath=filePath+"WEB-INF";			
				fileConst.riseRepository = new File(filePath);	
			} 
		}

		return fileConst;
	}
		
	public File getFile()
	{
		return this.riseRepository;
	}
}
