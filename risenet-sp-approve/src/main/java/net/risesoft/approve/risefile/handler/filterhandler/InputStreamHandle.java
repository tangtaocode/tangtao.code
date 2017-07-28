package net.risesoft.approve.risefile.handler.filterhandler;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import net.risesoft.approve.risefile.commons.TabIndex;


/**
 * @author wangdong
 * ���������������ж�stream���?����zip,code
 * �����������ע�룬��riseflie������������ļ���indexע�롣
 */
public abstract class InputStreamHandle  extends FilterInputStream implements TabIndex{
	
	public InputStreamHandle(){
		super(null);
	}
	
	public void setInputStream(InputStream in){
		this.in=in;
	}
	
	public abstract int read(byte b[], int off, int len) throws IOException ;
	
	public abstract int read() throws IOException ;
}
