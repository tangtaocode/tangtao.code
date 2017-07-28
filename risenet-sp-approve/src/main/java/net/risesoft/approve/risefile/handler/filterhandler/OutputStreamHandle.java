package net.risesoft.approve.risefile.handler.filterhandler;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.risesoft.approve.risefile.commons.TabIndex;


/**
 * @author wangdong
 * 流过滤器，在其中对stream处理，比如zip,code
 * 下游输出流的注入，有riseflie容器根据配置文件和index注入。
 */
public  abstract class OutputStreamHandle extends FilterOutputStream implements TabIndex{
	
	
	public OutputStreamHandle(){
		super(null);
	}
	
	public void setOutputStream(OutputStream out){
		this.out=out;
	}
		
	public abstract void write(byte[] b, int off, int len) throws IOException;
	
	public abstract void write(int b) throws IOException;
}
