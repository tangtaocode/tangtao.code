<%@ page contentType="text/html; charset=GBK" %>
<%@ page  import="com.jspsmart.upload.*"%> 
<%@ page  import="java.text.SimpleDateFormat"%>
<%@ page  import="java.util.Date"%>

<HTML>
	<HEAD>
		<TITLE>FCKeditor - File Upload</TITLE>
	</HEAD>
	<BODY>
		<TABLE height="100%" width="100%">
			<TR>
				<TD align=center valign=middle>
					Upload in progress...
				</TD>
			</TR>
		</TABLE>
	</BODY>
</HTML>
<%
    SmartUpload smartupload = new SmartUpload();
    String trance = null;
    String urlpath=null;
  
    String fileType=(String)request.getParameter("type");
    
    try{
        smartupload.initialize(pageContext);
        smartupload.upload();
        
        for(int i=0;i<smartupload.getFiles().getCount();i++){
            com.jspsmart.upload.File file = smartupload.getFiles().getFile(i);
            if((file!=null)||(!file.isMissing())){
                String filename =file.getFileName();
                String filesuffix =filename.substring(0,filename.lastIndexOf("."));
                String ext = smartupload.getFiles().getFile(0).getFileExt();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssS");
                String newName=sdf.format(new Date());
                int filesize = file.getSize();
                String path = "/FCKeditor/userimages/";
                if (fileType.equals("img")) {
//                path = request.getRealPath("/FCKeditor")+"\\userimages\\";      
                  path = "/FCKeditor/userimages/";
                  urlpath=request.getContextPath()+path+newName+"."+ext;                  
//                  urlpath=request.getContextPath()+path+filename;
                }
                if (fileType.equals("doc")) {
//                path = request.getRealPath("/FCKeditor")+"\\userimages\\";      
                  path = "/FCKeditor/_docs/";
                  urlpath=request.getContextPath()+path+newName+"."+ext;
//                  urlpath=request.getContextPath()+path+filename;
                }
//                trance = path+filename;
                trance = path+newName+"."+ext; 
                file.saveAs(trance);
            }
       %>
           <SCRIPT language=javascript>
              window.opener.setImage('<%=urlpath %>');
              window.close() ;
           </SCRIPT>     
<%     }
    }catch(Exception e){
        e.printStackTrace();
        out.print(e.getMessage());
    }
%>