package net.risesoft.approve.risefile.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.risesoft.approve.risefile.RiseFile;
import net.risesoft.approve.risefile.commons.ComparatorTabIndex;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;
import net.risesoft.approve.risefile.exception.RiseFileSystemException;
import net.risesoft.approve.risefile.handler.filterhandler.InputStreamHandle;
import net.risesoft.approve.risefile.handler.filterhandler.OutputStreamHandle;
import net.risesoft.approve.risefile.handler.sourcehandler.FileSourceInputStreamHandle;
import net.risesoft.approve.risefile.handler.sourcehandler.FileSourceOutputStreamHandle;
import net.risesoft.approve.risefile.handler.sourcehandler.SourceInputStreamHandle;
import net.risesoft.approve.risefile.handler.sourcehandler.SourceOutputStreamHandle;

public class HandleManager {
	
	private static org.apache.log4j.Logger log = net.risesoft.approve.risefile.commons.LogFactory
	.getLog(HandleManager.class);
	/**
	 * 保存文件
	 * @param config 文件框配置信息
	 * @param fileGUID 文件的guid，如果新文件为null.
	 * @param is输入流
	 */
	public static RiseFileOutputStream getRiseFileOutputStream(RiseFileConfig config,String fileFullName) throws RiseFileSystemException{

		RiseFileOutputStream fos=null;
		OutputStream os = null;

		try {
			List l = config.getPlusList("streamHandles","isHandleExtends");
			l=RiseFileConfigManager.getMapObjectBatch(l,RiseFileConfigManager.OUTPUTSTREAMHANDLE);
			Collections.sort(l, new ComparatorTabIndex());
			
			//初始化 流文件源
			//如果设定的源输出流，那么就采用指定的源输出流
			//否则就用普通文件源输出流
			if(l.size()>0&&l.get(0) instanceof SourceOutputStreamHandle){
					fos=((SourceOutputStreamHandle)l.get(0)).getSourceOutputStream(fileFullName);
					l.remove(0);
			}else{
				fos=(new FileSourceOutputStreamHandle()).getSourceOutputStream(fileFullName);
			}
			os=fos.outupStream;
			//建立流的连接

			for (Iterator iter = l.iterator(); iter.hasNext();) {
				OutputStreamHandle outeros = (OutputStreamHandle) iter.next();
				outeros.setOutputStream(os);
				os=outeros;
			}
			fos.outupStream=os;
		} catch (FileNotFoundException e) {
			log.error(e,e);
			throw new RiseFileSystemException(e.getMessage());
		}catch (IOException e) {
			log.error(e,e);
			throw new RiseFileSystemException(e.getMessage());
		}
		return fos;
	}
	
	public static RiseFileInputStream getRiseFileInputStream(RiseFile riseFile)throws RiseFileSystemException{
//		RiseFileConfig config=RiseFileConfigManager.getAppFileConfig(riseFile.getAppName());
//		String fileFullName=config.getFileRoot()+File.separator+riseFile.getRealFullPath();
		String fileFullName=riseFile.getRealFullPath();
		//获得文件流
		RiseFileInputStream fileis=getRiseFileInputStreamImp(riseFile,fileFullName);
		//计算size
		long size=0;
		
		//做一下性能优化，如果没有handle就可以直接读出文件大小，不必单独读一遍文件只为计算文件大小。
				//故大文件不建议使用handle,会至少降低一半性能。
		if(!riseFile.getHandles().equals("")){
			try {
				byte buffer[] = new byte[8 * 1024];
				int b=0;
				//获得一个新的流文件，因为要获得最终的文件大小，所以必须读一遍文件。
				RiseFileInputStream tempfileis=getRiseFileInputStreamImp(riseFile,fileFullName);
				InputStream inputstream=tempfileis.inputStream;
				while ((b = inputstream.read(buffer)) != -1) {
					size=size+b;
				}
				inputstream.close();
				tempfileis.sourceInputStream.close();
			} catch (IOException e) {
				log.info(e,e);
				throw new RiseFileSystemException(e.getMessage());
			}catch (Exception e) {
				e.printStackTrace();
			}
			fileis.size=size;
		}
		return fileis;
	}
	
	private static RiseFileInputStream getRiseFileInputStreamImp(RiseFile riseFile,String fileFullName)throws RiseFileSystemException{
		RiseFileInputStream fis=new RiseFileInputStream();
		InputStream is=null;
		
		try {
			List l=null;
			//获得Handles
//			if(riseFile.getHandles().equals("")){
				l=new ArrayList();
//			}else{
//				l = Arrays.asList(riseFile.getHandles().split(";"));
//			}
//			l=RiseFileConfigManager.getMapObjectBatch(l,RiseFileConfigManager.INPUTSTREAMHANDLE);
			Collections.sort(l, new ComparatorTabIndex());
			
			//初始化 流文件源
			//如果设定的源输出流，那么就采用指定的源输出流
			//否则就用普通文件源输出流
//			if(l.size()>0&&l.get(0) instanceof SourceInputStreamHandle){
//				fis=((SourceInputStreamHandle)l.get(0)).getSourceInputStream(fileFullName);
//				l.remove(0);
//			}else{
				fis=(new FileSourceInputStreamHandle()).getSourceInputStream(fileFullName);
//			}
			is=fis.inputStream;
			//建立流的连接

			for (Iterator iter = l.iterator(); iter.hasNext();) {
				InputStreamHandle innor = (InputStreamHandle) iter.next();
				innor.setInputStream(is);
				is=innor;
			}
			fis.inputStream=is;
		} catch (FileNotFoundException e) {
			log.error(e,e);
			throw new RiseFileSystemException(e.getMessage());
		}catch (IOException e) {
			log.error(e,e);
			throw new RiseFileSystemException(e.getMessage());
		} 
		return fis;
	}
}
