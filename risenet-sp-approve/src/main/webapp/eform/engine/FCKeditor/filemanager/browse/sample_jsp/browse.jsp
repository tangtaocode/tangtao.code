<%@ page contentType="text/html; charset=GBK" %>
<%@ page import = "java.io.*"%>
<%
String imagesDir="/FCKeditor/userimages/";
String docsDir="/FCKeditor/_docs/";
String fileType=(String)request.getParameter("type");
String selectedDir="";
String functionName="";
if (fileType.equals("img")) {
	selectedDir=imagesDir;
	functionName="getImage";
	}
else if (fileType.equals("doc")) {
	selectedDir=docsDir;
	functionName="getDoc";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<HTML>
	<HEAD>
		<TITLE>FCKeditor - Image Browser</TITLE>
		<META name="vs_targetSchema" content="http://schemas.microsoft.com/intellisense/ie5">
		<STYLE type="text/css">
			BODY, TD, INPUT { FONT-SIZE: 12px; FONT-FAMILY: Arial, Helvetica, sans-serif }
		</STYLE>
		<link href="/riseway/riseway.css" rel="stylesheet" type="text/css">
		<SCRIPT language="javascript">
		
var sImagesPath  = "<%=request.getContextPath()+selectedDir%>" ;
var sActiveImage = "" ;

function getImage(imageName)
{
	sActiveImage = sImagesPath + imageName ;
	imgPreview.src = sActiveImage ;
}

function ok()
{	
	window.setImage(sActiveImage) ;
	window.close() ;
}

function getDoc(fileName)
{
	window.setImage( sImagesPath + fileName ) ;
	window.close() ;
}
		</SCRIPT>
	</HEAD>
	<BODY>
		<TABLE height="100%" cellspacing="0" cellpadding="0" width="100%" border="0">
			<TR>
				<TD height="100%">
					<TABLE height="100%" cellspacing="5" cellpadding="0" width="100%" class="simple" >
						<TR>
							<TD align="middle" width="45%">
								选择图片
								<BR>
<%
String imagesFolder=application.getRealPath(selectedDir);
File folder=new File(imagesFolder);
String[] filesImmagini=folder.list();
for (int i=0; i<filesImmagini.length;i++) {
	out.println("<A href=\"javascript:"+functionName+"('"+filesImmagini[i]+"');\">"+filesImmagini[i]+"</A><BR>");
} 
%>								
</TD>
<% if (fileType.equals("img")) {%>
							<TD align="middle" width="55%">
								<IMG src="<%=request.getContextPath()+selectedDir%><%= filesImmagini[0]%>" id="imgPreview" width="200" height="250" border="0">
							</TD>
<% } %>
						</TR>
					</TABLE>
				</TD>
			</TR>
			<TR>
				<TD valign="bottom" align="middle">
					<INPUT style="WIDTH: 80px" type="button" value="确定"     onclick="ok();"> &nbsp;&nbsp;&nbsp;&nbsp;
					<INPUT style="WIDTH: 80px" type="button" value="关闭窗口" onclick="window.close();"><BR>
				</TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>
