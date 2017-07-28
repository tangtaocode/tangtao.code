package net.risesoft.approve.risefile.handler.sourcehandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import net.risesoft.approve.risefile.handler.RiseFileInputStream;

/**
 * @author wangdong
 * 文件源流
 *
 */
public class FileSourceInputStreamHandle extends SourceInputStreamHandle{

	public RiseFileInputStream getSourceInputStream(String url) throws IOException {
		//计算size
		File file=new File(url);
		long size=file.length();
		//获得流
		RiseFileInputStream risefis=new RiseFileInputStream();
		risefis.inputStream=new FileInputStream(url);
		risefis.fileFullName=url;
		risefis.size=size;
		risefis.sourceInputStream=this;
		return risefis;
	}

	public void close() {
		
	}

}
