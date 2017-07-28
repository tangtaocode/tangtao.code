package net.risesoft.approve.risefile.handler;

import java.io.OutputStream;
/**
 * @author wangdong
 * 把源输入流，经过Handle流过率后，得到的输入流，
 * 并把这个最终的流和源流的路径，封装成RiseFileInputStream
 */
public class RiseFileOutputStream {
	
	public OutputStream outupStream;
	
	public String fileFullName;
}
