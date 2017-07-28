package net.risesoft.approve.risefile.handler.sourcehandler;

import java.io.IOException;

import net.risesoft.approve.risefile.commons.TabIndex;
import net.risesoft.approve.risefile.handler.RiseFileInputStream;

public abstract class SourceInputStreamHandle implements TabIndex{
	//源文件，总是最大
	public int getTabIndex(){
		return 0;
	}
	public abstract RiseFileInputStream getSourceInputStream(String url) throws IOException;
	
	public abstract void close();
}
