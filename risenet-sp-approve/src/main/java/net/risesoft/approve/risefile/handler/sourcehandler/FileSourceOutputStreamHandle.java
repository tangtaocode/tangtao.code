package net.risesoft.approve.risefile.handler.sourcehandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.risesoft.approve.risefile.handler.RiseFileOutputStream;

public class FileSourceOutputStreamHandle extends SourceOutputStreamHandle{
	/**
	 * @author wangdong
	 * 文件源流
	 *
	 */
	public RiseFileOutputStream getSourceOutputStream(String url) throws IOException{
		String path=url.substring(0,url.lastIndexOf(File.separator));
		//创建路径
		File file=new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		//创建文件
		file=new File(url);
		if(!file.exists()){
		file.createNewFile();
		}
		//获得流
		RiseFileOutputStream risefis=new RiseFileOutputStream();
		risefis.outupStream=new FileOutputStream(url);
		risefis.fileFullName=url;
		return risefis;
	}

}
