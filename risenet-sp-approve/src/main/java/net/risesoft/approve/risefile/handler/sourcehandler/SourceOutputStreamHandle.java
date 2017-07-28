package net.risesoft.approve.risefile.handler.sourcehandler;

import java.io.IOException;

import net.risesoft.approve.risefile.commons.TabIndex;
import net.risesoft.approve.risefile.handler.RiseFileOutputStream;

public abstract class SourceOutputStreamHandle implements TabIndex{
	//源文件，总是最大
	public int getTabIndex(){
		return 0;
	}
	
	public abstract RiseFileOutputStream getSourceOutputStream(String url)throws IOException;
}
