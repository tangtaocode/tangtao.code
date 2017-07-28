package net.risesoft.approve.risefile;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;




//import net.risesoft.common.util.DateUtility;
//import net.risesoft.commons.RiseUser;
//import net.risesoft.commons.datetime.DateUtil;





import net.risesoft.approve.risefile.commons.util.StringUtil;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.exception.RiseFileSystemException;
import net.risesoft.approve.risefile.filter.FileFilter;
import net.risesoft.approve.risefile.filter.RiseFileContext;
import net.risesoft.approve.service.RisenetFileService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

/**
 * 获得文件框API
 */
public class FileBoxFactory {
	
	/**
	 * @return net.risesoft.components.risefile.FileBox
	 * @throws RiseFileSystemException 
	 * @roseuid 450126730177
	 */
	public static FileBox getFileBox(RiseFileConfig config, RiseFileContext context) throws RiseFileException {
		List filters=config.getFilters();
		FileBox fileBox=new FileBox();
		fileBox.setAppInstanceGUID(context.getAppInstanceGUID());
		fileBox.setAppName(config.getAppName());
		fileBox.setFileBoxName(config.getFileboxName());
		List list = RiseFileManagerProxy.getRiseFiles(config,context);
		fileBox.setFileBeams(list);
		//依次处理filter,
		for (Iterator iter = filters.iterator(); iter.hasNext();) {
			FileFilter filter = (FileFilter) iter.next();
			fileBox=filter.excute(fileBox,context);
		}
		return fileBox;
	}
	
	/**
	 * @param config
	 * @param context
	 * @param isAllVersion
	 * @return
	 * @throws RiseFileException
	 */
	public static String getFileBoxXML(RiseFileConfig config, RiseFileContext context,boolean isAllVersion,String deptname)throws RiseFileException{
		FileBox filebox=getFileBox(config,context);
		return getFileBoxXML(filebox,isAllVersion,deptname);
	}

	/**
	 * @param filebox
	 * @param isAllVersion
	 * @return
	 * @throws RiseFileException
	 */
	public static String getFileBoxXML(FileBox filebox,boolean isAllVersion,String deptname) throws RiseFileException{
		StringBuffer sb=new StringBuffer();
		
		String permission="";
		
		sb.append("<root>").append("\r\n");
		
		
		for (Iterator iter = filebox.getFileBeams().iterator(); iter.hasNext();) {
			RiseFileBeam fileBeam = (RiseFileBeam) iter.next();
			RiseFile file=fileBeam.getFile();
			String s = "";
			//根据guid获得人员名称
			if(isAllVersion){
				for (Iterator iterator = fileBeam.getRiseFiles().iterator(); iterator
						.hasNext();) {
					RiseFile riseFile = (RiseFile) iterator.next();
					sb.append("	<row>").append("\r\n");
					sb.append("		<guid>"+StringUtil.escapeXMLTag(riseFile.getFileGUID())+"</guid>").append("\r\n");
					if(riseFile.getFileNameExt().equals(RiseFileConst.RISEFILEEXTNAME_RISECALLBACK)){
						sb.append("		<fileName>"+StringUtil.escapeXMLTag(riseFile.getTitile()+"("
								+"删除)."+riseFile.getFileNameExt())+"</fileName>").append("\r\n");
					}else{
						sb.append("		<fileName>"+StringUtil.escapeXMLTag(riseFile.getTitile()+"("
								+riseFile.getMajorVersion()+"."+riseFile.minVersion+")."+riseFile.getFileNameExt())+"</fileName>").append("\r\n");
					}
					sb.append("		<fileExtName>"+StringUtil.escapeXMLTag(riseFile.getFileNameExt())+"</fileExtName>").append("\r\n");
					//sb.append("		<fileSize>"+StringUtil.escapeXMLTag("0")+"</fileSize>").append("\r\n");

					BigDecimal bd = new BigDecimal(file.getFileSize());
			        bd = bd.setScale(0,BigDecimal.ROUND_HALF_EVEN);
					long i = (long)bd.doubleValue();
					if(i >= 1)
						s = (i*1024*1024)+"";
					else{
						s = (file.getFileSize()*1024*1024)+"";
					}
					
					sb.append("		<fileSize>"+StringUtil.escapeXMLTag(s)+"</fileSize>").append("\r\n");
					sb.append("		<fileModifiedTimeStr>"+StringUtil.escapeXMLTag("")+"</fileModifiedTimeStr>").append("\r\n");
					//权限叠加，只有都是可读写，此文件才是可读写
					sb.append("		<isAllowEdit>"+StringUtil.escapeXMLTag("false")+"</isAllowEdit>").append("\r\n");
					sb.append("		<fileVersion>"+StringUtil.escapeXMLTag(riseFile.getMajorVersion()+"."+riseFile.minVersion)+"</fileVersion>").append("\r\n");
					sb.append("		<fileCreatedTimeStr>"+StringUtil.escapeXMLTag(file.getCreateDate())+"</fileCreatedTimeStr>").append("\r\n");
					sb.append("		<fileCreator>"+StringUtil.escapeXMLTag(ThreadLocalHolder.getPerson().getLoginName())+"</fileCreator>").append("\r\n");
					sb.append("		<fileCreateDept>"+StringUtil.escapeXMLTag(deptname)+"</fileCreateDept>").append("\r\n");
					sb.append("  </row>").append("\r\n");
				}
			}else{
							
				if(file.getFileNameExt()!=null && file.getFileNameExt().equals(RiseFileConst.RISEFILEEXTNAME_RISECALLBACK)){//modify by liujun 加上不为null的判断
					//放入系统回收站的文件不显示
					continue;
				}
				sb.append("	<row>").append("\r\n");
				sb.append("		<guid>"+StringUtil.escapeXMLTag(file.getFileGUID())+"</guid>").append("\r\n");
				sb.append("		<fileName>"+StringUtil.escapeXMLTag(file.getFilename())+"</fileName>").append("\r\n");
				sb.append("		<fileExtName>"+StringUtil.escapeXMLTag(file.getFileNameExt())+"</fileExtName>").append("\r\n");
				//sb.append("		<fileSize>"+StringUtil.escapeXMLTag("0")+"</fileSize>").append("\r\n");
				
				BigDecimal bd = new BigDecimal(file.getFileSize());
		        bd = bd.setScale(0,BigDecimal.ROUND_HALF_EVEN);
				long i = (long)bd.doubleValue();
				if(i >= 1)
					s = (i*1024*1024)+"";
				else{
					s = (file.getFileSize()*1024*1024)+"";
				}
				
				sb.append("		<fileSize>"+StringUtil.escapeXMLTag(s)+"</fileSize>").append("\r\n");
				//sb.append("		<fileModifiedTimeStr>"+StringUtil.escapeXMLTag(file.getLastModified().toLocaleString())+"</fileModifiedTimeStr>").append("\r\n");
				sb.append("		<fileModifiedTimeStr>"+StringUtil.escapeXMLTag("")+"</fileModifiedTimeStr>").append("\r\n");
				
				//权限叠加，只有都是可读写，此文件才是可读写
				if(filebox.getFileBoxPerm()==RiseFileConst.PERMISSION_READWRITE
						&&fileBeam.getPermission()==RiseFileConst.PERMISSION_READWRITE){
					permission="true";
				}else{
					permission="false";
				}
				sb.append("		<isAllowEdit>"+StringUtil.escapeXMLTag(permission)+"</isAllowEdit>").append("\r\n");
				sb.append("		<fileVersion>"+StringUtil.escapeXMLTag(file.getMajorVersion()+"."+file.minVersion)+"</fileVersion>").append("\r\n");
				sb.append("		<fileCreator>"+StringUtil.escapeXMLTag(ThreadLocalHolder.getPerson().getLoginName())+"</fileCreator>").append("\r\n");
				sb.append("		<fileCreateDept>"+StringUtil.escapeXMLTag(deptname)+"</fileCreateDept>").append("\r\n");
				sb.append("		<fileCreatedTimeStr>"+StringUtil.escapeXMLTag(file.getCreateDate().toString())+"</fileCreatedTimeStr>").append("\r\n");
				sb.append("	</row>").append("\r\n");
			}
			
		}
		
		sb.append("</root>").append("\r\n");
		return sb.toString();
	
	}
	
	
}
