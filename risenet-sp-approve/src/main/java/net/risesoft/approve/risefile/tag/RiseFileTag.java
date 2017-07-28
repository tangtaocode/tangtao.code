package net.risesoft.approve.risefile.tag;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

//import net.risesoft.commons.util.GUIDUtil;










import net.risesoft.approve.risefile.FileBox;
import net.risesoft.approve.risefile.FileBoxFactory;
import net.risesoft.approve.risefile.RiseFileConst;
//import net.risesoft.components.risefile.commons.LogFactory;
import net.risesoft.approve.risefile.config.RiseFileConfig;
import net.risesoft.approve.risefile.config.RiseFileConfigManager;
import net.risesoft.approve.risefile.exception.RiseFileException;
import net.risesoft.approve.risefile.exception.RiseFileSystemException;
import net.risesoft.approve.risefile.filter.RiseFileContext;
import net.risesoft.approve.service.RisenetFileService;
import net.risesoft.common.util.ContextUtil;
import net.risesoft.model.OrgUnit;
import net.risesoft.model.Person;
import net.risesoft.tenant.pojo.ThreadLocalHolder;
import net.risesoft.util.RisesoftUtil;

/**
 * 显示文件框的Tag
 */
public class RiseFileTag extends TagSupport {

//	private static org.apache.log4j.Logger log = LogFactory.getLog(RiseFileTag.class);

	private String fileboxname = "";

	private String appname = "";
	
	private String subappname="";

	private String fileroot = "";

	private String width = "200";

	private String height = "200";

	private String maxuploadsize = 1000 * 1024 * 1024 + "";

	private String viewtype = "2";
	
	private String savemode = "";

	private String keepminimalversion = "false";

	private String filters = "";

	private String isfilterextends = "false";

	private String streamhandles = "";

	private String ishandleextends = "false";

	private String appinstguid = "";

	private String majorversion = "";
	
	private String template="";

	String rsID = null;

	String divID = null;

	String objectName = null;

	javax.servlet.http.HttpServletRequest request = null;

	RiseFileContext context = null;
	
	FileBox filebox=null;
	
	RiseFileConfig config=null;
	
	private RisenetFileService risenetFileService;

	public int doStartTag() {
		JspWriter writer = pageContext.getOut();
		request = (HttpServletRequest) pageContext.getRequest();

		rsID = fileboxname + "XML";
		divID = fileboxname + "DIV";
		objectName = fileboxname + "Object";
		if(fileroot.equals("")){
			fileroot=subappname;
		}
		StringBuffer sb = new StringBuffer();
		try {
			try {
				init();
				sb.append(getFileBoxXML());
				sb.append(getTableHTML());
				sb.append(getJS());
			} catch (Exception e) {
//				log.info(e, e);
				// 显示错误标签
				sb = new StringBuffer();
				sb.append(getTableHTML());
				sb.append(getErrorJS(e.getMessage()));
			}
			writer.write(sb.toString());
		} catch (Exception e) {
//			log.info(e, e);
		}
		return SKIP_BODY;
	}

	private void init() throws RiseFileException {
		
		//针对R6的写法,统一把参数封装在RiseForm中
//		RiseForm riseform=(RiseForm)request.getAttribute("riseForm");
//		if(riseform!=null){
//			context = (RiseFileContext)riseform.getValueObject(RiseFileContext.RiseFileContextConst);
//		}else{
//			context = (RiseFileContext) request.getAttribute("RiseFileContext");
//		}
		//针对其他应用的写法
		context = (RiseFileContext) request.getAttribute("RiseFileContext");

		
		// 优先使用contex中的参数
		if (context != null) {
			appinstguid = context.getAppInstanceGUID();
			majorversion = "" + context.getMajorVersion();
		} else {
		// 如果没有context，使用request中的参数。并生成context
			if(fileboxname.equals("TANGER_OCX")){
//				appinstguid = (pageContext.getRequest()).getParameter(WorkflowConst.InstanceGUID);
//			    if(GUIDUtil.inValidate(appinstguid)) {
//			    	appinstguid = (String)pageContext.getRequest().getAttribute(WorkflowConst.InstanceGUID);
//			      if(GUIDUtil.inValidate(appinstguid)) {
//			    	  throw new RiseFileSystemException("没有找到WorkflowInstanceGUID!");
//			      }
//			    }
			} else{
				if (appinstguid.equals("")) {
					throw new RiseFileSystemException("没有在request中传入context或在tag中定义appInstGUID");
				}
			}
			if (majorversion.equals("")) {
				throw new RiseFileSystemException("没有在request中传入context或在tag中定义majorVersion");
			}
			context = new RiseFileContext(appinstguid,new Integer(majorversion).intValue());
		}
		context.setFileboxName(fileboxname);
		
		// 合成config
		config = RiseFileConfigManager.getExtendedFileConfig(appname);
		config.setFileboxName(fileboxname);
		config.setHandleExtends(Boolean.valueOf(ishandleextends).booleanValue());
		config.setFilterExtends(Boolean.valueOf(isfilterextends).booleanValue());
		if (!filters.equals("")) {
			config.setFilters(Arrays.asList(filters.split(";")));
		}
		// 初始化filebox
		filebox=FileBoxFactory.getFileBox(config,context);
	}

	private StringBuffer getTableHTML() {
		StringBuffer sb = new StringBuffer();
		// 全局变量所以还是写在HTML中
		sb.append("<SCRIPT LANGUAGE='JavaScript'>").append("\n");
		sb.append("var webContextPath='" + request.getContextPath() + "'").append("\n");
		sb.append("</SCRIPT>").append("\n");
		
		//引入css
		sb.append("<link type='text/css' rel='stylesheet' href='"+request.getContextPath()+"/static/js/ntko/css/filebox.css'>").append("\n");
		// 引入js
		sb.append("<script language='javascript' src='"+ request.getContextPath()+ "/static/js/ntko/js/fileBox.js'></script>").append("\n");
		if(fileboxname.equals("TANGER_OCX")){
			sb.append("<script language='javascript' src='"+ request.getContextPath()+ "/static/js/ntko/js/fileBoxVar.js'></script>").append("\n");
		}
		sb.append(
				"<table   class='filebox' border='0' width='" + width + "' id='table1' height='"+ height + "' cellspacing='0' cellpadding='0'>").append("\n");
		sb.append("<tr>").append("\n");
		sb.append("<td style='height:" + height + "'>").append("\n");
		sb.append("<div id='" + divID + "'></div>").append("\n");
		sb.append("</td>").append("\n");
		sb.append("	</tr>").append("\n");
		sb.append("	<tr>").append("\n");
		/*if(template.equals("")){
			sb.append("		<td align='center' valign='top' height='22px'>").append("\n");
			sb.append("			<input type='button' class='filebox' value=' 保存文件修改 ' name='"+fileboxname+"Save' onclick='updateRiseFilesByID(\""+ fileboxname + "\")'>").append("\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;").append("\n");
			sb.append("			<input type='button' class='filebox' value='显示版本' name='"+fileboxname+"Version' onclick='showVersion(\""+ fileboxname + "\")'>").append("\n");
			sb.append("		</td>").append("\n");
		}else if(template.endsWith("officeStyle")){
			sb.append("		<td align='center' valign='top' height='22px'>").append("\n");
			sb.append("			<input type='button' class='filebox' value=' 添加 ' name='"+fileboxname+"Add' onclick='addFileCmd(\""+ fileboxname + "\")'>").append("\n");
			sb.append("&nbsp;&nbsp;&nbsp;&nbsp;").append("\n");
			sb.append("			<input type='button' class='filebox' value=' 删除 ' name='"+fileboxname+"Del' onclick='showVersion(\""+ fileboxname + "\")'>").append("\n");
			sb.append("		</td>").append("\n");
			
		}*/
		
		sb.append("</tr>").append("\n");
		sb.append("</table>").append("\n");
		
		return sb;
	}

	private StringBuffer getJS() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<SCRIPT LANGUAGE='JavaScript'>").append("\n");
		// 填充数据
		sb.append("var " + objectName + "=new Object();").append("\n");
		sb.append("  " + objectName + ".FILEBOXNAME='" + fileboxname + "';").append("\n");
		sb.append("  " + objectName + ".APPNAME='" + appname + "';").append("\n");
		sb.append("  " + objectName + ".FILEROOT='" + fileroot + "';").append("\n");
		sb.append("  " + objectName + ".SAVEMODE='" + savemode + "';").append("\n");
		sb.append("  " + objectName + ".KEEPMINIMALVERSION='"+ keepminimalversion + "';").append("\n");
		sb.append("  " + objectName + ".FILTERS='" + filters + "';").append("\n");
		sb.append("  " + objectName + ".ISFILTEREXTENDS='" + isfilterextends+ "';").append("\n");
		sb.append("  " + objectName + ".STREAMHANDLES='" + streamhandles+ "';").append("\n");
		sb.append("  " + objectName + ".ISHANDLEEXTENDS='" + ishandleextends+ "';").append("\n");
		sb.append("  " + objectName + ".APPINSTGUID='"+appinstguid+"';").append("\n");
		sb.append("  " + objectName + ".MAJORVERSION='"+majorversion+"';").append("\n");
		sb.append("  " + objectName + ".MAJORVERSIONNAME='"+majorversion+"';").append("\n");
		sb.append("  " + objectName + ".CONTEXT='" + context.toString() + "';").append("\n");
		sb.append("  " + objectName + ".WIDTH=" + width + ";").append("\n");
		sb.append("  " + objectName + ".HEIGHT=" + height + ";").append("\n");
		sb.append("  " + objectName + ".MAXUPLOADSIZE=" + maxuploadsize + ";").append("\n");
		sb.append("  " + objectName + ".VIEWTYPE=" + viewtype + ";").append("\n");
		sb.append("  " + objectName + ".ALLVERSION=false;").append("\n");
		sb.append("  " + objectName + ".SELECTCOUNT=0;").append("\n");
		if(filebox.getFileBoxPerm()==RiseFileConst.PERMISSION_READWRITE){
			sb.append("  " + objectName + ".ISPERMITADDDELFILES=-1;").append("\n");
		}else if(filebox.getFileBoxPerm()==RiseFileConst.PERMISSION_READ){
			sb.append("  " + objectName + ".ISPERMITADDDELFILES=0;").append("\n");
		}
		
	
		sb.append("initFileBox(" + objectName + ");").append("\n");
		sb.append("</SCRIPT>").append("\n");
		// 增加事件
		sb.append("<script language='JScript' for='" + fileboxname+ "' event='OnLocalFileAdded(path,name ,AttachFile)'>").append("\n");
		sb.append(" 	addRiseFile(this);").append("\n");
		sb.append("</script>").append("\n");
		sb.append("<script language='JScript' for='"+ fileboxname+ "' event='BeforeFileCommand(cmdType,IsServerFile,FilePathOrURL ,AttachFile)'>").append("\n");
		sb.append("		doFileCommand(cmdType,IsServerFile,FilePathOrURL ,AttachFile,this);").append("\n");
		sb.append("</script>").append("\n");
		sb.append("<script language='JScript' for='" + fileboxname+ "' event='AfterSaveToURL(retStr,ErrCode)'>").append("\n");
		sb.append("		afterCommitFileBox(retStr,ErrCode);").append("\n");
		sb.append("</script>").append("\n");
//		sb.append("<script language='JScript' for='" + fileboxname+ "' event='OnSelectChange(SelCount)'>").append("\n");
//		sb.append("		fileboxSelectChange(this,SelCount)").append("\n");
//		sb.append("</script>").append("\n");
		
		//var a=SelCount;
		sb.append("<script language='JScript'>").append("\n");
		sb.append("		window.prompt = (function(prompt) {").append("\n");
		sb.append("			if (window.navigator.appName.toLowerCase().indexOf(\"microsoft\")<0)\n");
	    sb.append("				return prompt;\n");	    
	    sb.append("		    return function(msg) {\n");
	    sb.append("		    	window.vbs_var = null;\n");
	    sb.append("				execScript('window.vbs_var = InputBox(unEscape(\"'+escape(msg)+'\"), \"用户提示\")', \"VBScript\");");
	    sb.append("				return window.vbs_var;").append("\n");
	    sb.append("		    }\n");
	    sb.append("		})(window.prompt);").append("\n");
		sb.append("</script>").append("\n");
		sb.append("<script language='JScript' for='" + fileboxname+ "' event='OnScannerSetFileName()'>").append("\n");
		sb.append("		var userInputFile = \"\";").append("\n");
		sb.append("		userInputFile = window.prompt(\"请输入扫描文件名称:\",\"\");\n");
	    sb.append("		if((!userInputFile) || (userInputFile.length == 0)){\n");	    
	    sb.append("		    CancelLastCommand = true;\n");
	    sb.append("		    return;\n");
	    sb.append("		}\n");
	    sb.append("		ScannerPicFileName = userInputFile;").append("\n");
		sb.append("</script>").append("\n");
		return sb;
	}

	private StringBuffer getErrorJS(String msg) {
		StringBuffer sb = new StringBuffer();
		sb.append("<SCRIPT LANGUAGE='JavaScript'>").append("\n");
		sb.append("showError('" + fileboxname + "','"+msg+"')");
		sb.append("</SCRIPT>").append("\n");
		return sb;
	}

	private StringBuffer getFileBoxXML() throws RiseFileException {
		Person person = ThreadLocalHolder.getPerson();
//		this.risenetFileService = ContextUtil.getBean(RisenetFileService.class);
//		String deptname = risenetFileService.returnDeptName(person.getID());
		OrgUnit org = RisesoftUtil.getPersonManager().getBureau(ThreadLocalHolder.getTenantId(), person.getID());
		String deptname = org.getName();		
		StringBuffer sb = new StringBuffer();

		sb.append("<XML ID='" + rsID + "'>").append("\n");
		sb.append(FileBoxFactory.getFileBoxXML(filebox, false,deptname)).append("\n");
		sb.append("</XML>").append("\n");

		return sb;
	}

	public String getAppname() {
		return appname;
	}

	public void setAppname(String appname) {
		this.appname = appname;
	}

	public String getFileboxname() {
		return fileboxname;
	}

	public void setFileboxname(String fileboxname) {
		this.fileboxname = fileboxname;
	}

	public String getFileroot() {
		return fileroot;
	}

	public void setFileroot(String fileroot) {
		this.fileroot = fileroot;
	}

	public String getFilters() {
		return filters;
	}

	public void setFilters(String filters) {
		this.filters = filters;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getIsfilterextends() {
		return isfilterextends;
	}

	public void setIsfilterextends(String isfilterextends) {
		this.isfilterextends = isfilterextends;
	}

	public String getIshandleextends() {
		return ishandleextends;
	}

	public void setIshandleextends(String ishandleextends) {
		this.ishandleextends = ishandleextends;
	}

	public String getKeepminimalversion() {
		return keepminimalversion;
	}

	public void setKeepminimalversion(String keepminimalversion) {
		this.keepminimalversion = keepminimalversion;
	}

	public String getMaxuploadsize() {
		return maxuploadsize;
	}

	public void setMaxuploadsize(String maxuploadsize) {
		this.maxuploadsize = maxuploadsize;
	}

	public String getSavemode() {
		return savemode;
	}

	public void setSavemode(String savemode) {
		this.savemode = savemode;
	}

	public String getStreamhandles() {
		return streamhandles;
	}

	public void setStreamhandles(String streamHandles) {
		this.streamhandles = streamHandles;
	}

	public String getViewtype() {
		return viewtype;
	}

	public void setViewtype(String viewtype) {
		this.viewtype = viewtype;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getAppinstguid() {
		return appinstguid;
	}

	public void setAppinstguid(String appInstGUID) {
		this.appinstguid = appInstGUID;
	}

	public String getMajorversion() {
		return majorversion;
	}

	public void setMajorversion(String majorVersion) {
		this.majorversion = majorVersion;
	}

	public String getSubappname() { 
		return subappname;
	}

	public void setSubappname(String subappname) {
		this.subappname = subappname;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}



	

}

