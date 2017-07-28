<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />

<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/button.css" />
<script src="${ctx}/static/jquery/jquery-1.10.2.min.js" type="text/javascript"></script>
<script type="text/javascript">
    var ctx = "${ctx}";
    $.ajaxSetup({
        //contentType: "application/x-www-form-urlencoded;charset=utf-8",
        type : "POST",
        complete : function(XMLHttpRequest, textStatus) {
            //通过XMLHttpRequest取得响应头，sessiontimeout，
            var sessiontimeout = XMLHttpRequest.getResponseHeader("sessiontimeout");
            if (sessiontimeout && sessiontimeout == "true") {
                //如果超时就处理 ，指定要跳转的页面
                top.window.location.replace("${ctx}/login");
            }
        }
    });

    function getDom(myid) {
        return document.getElementById(myid);
    }

    function getCmp(myid) {
        return $(document.getElementById(myid));
    }
</script>
<script type="text/javascript">

var SPinstanceId = "${SPinstanceId}";//业务id
var processInstanceId = "${processInstanceId}";//流程实例id
var taskId = "${taskId}";//任务id
var processSerialNumber = "${processSerialNumber}";//流程编号
var declaresn = "${declaresn}";//业务流水号
var operate = "${printType}";//文件类型

function loadobj() {
		var url = "${ctx}/approve/iWebOfficeServlet?SPinstanceId="+SPinstanceId+"&processInstanceId="+ processInstanceId +"&taskId="+taskId+"&fileType="+operate+"&declaresn="+declaresn;
			try{
			    //以下属性必须设置，实始化iWebPDF
			    webform.WebPDF.WebUrl=url;    //WebUrl:系统服务器路径，与服务器文件交互操作，如保存、打开文档 
			    webform.WebPDF.RecordID=SPinstanceId;
			    webform.WebPDF.FileType=operate;
			    webform.WebPDF.Declaresn=declaresn;
		    	
			    webform.WebPDF.ShowMenus = 0;				 //是否显示菜单栏
			    webform.WebPDF.ShowTools = 1;               //工具栏可见（1,可见；0,不可见）
			    webform.WebPDF.SaveRight = 1;               //是否允许保存当前文档（1,允许；0,不允许）
			    webform.WebPDF.PrintRight = 1;              //是否允许打印当前文档（1,允许；0,不允许）
			    webform.WebPDF.AlterUser = false;           //是否允许由控件弹出提示框 true表示允许  false表示不允许
			    webform.WebPDF.ShowBookMark = 1;			//是否显示书签树按钮（1,显示；0,不显示）
		    	webform.WebPDF.ShowSigns = 1;         	    //设置签章工具栏当前是否可见（1,可见；0,不可见）判定权限
		    	
		    	webform.WebPDF.AppendTools("1","保存PDF",2);
		    	webform.WebPDF.AppendTools("2","撤销PDF",34);
			    webform.WebPDF.EnableTools("打开文档;保存文档;关闭文档",2);
			    webform.WebPDF.ShowSides=0;					//是否显示侧边栏
			    webform.WebPDF.SideWidth = 100;             //设置侧边栏的宽度
			    webform.WebPDF.WebOpen();                   //打开该文档    交互OfficeServer的OPTION="LOADFILE"    <参考技术文档>
			    webform.WebPDF.Zoom = 100;                  //缩放比例
			    webform.WebPDF.Rotate = 360;                //当显示页释放角度
			    webform.WebPDF.CurPage = 1;                 //当前显示的页码
			  }catch(e){
			    alert(e.description);                       //显示出错误信息
			  }
	}
	//保存
	function uploadsubmit() {
		var sealcount = webform.WebPDF.SignatureCount();
		var url ="${ctx}/approve/iWebOfficeServlet?SPinstanceId="+SPinstanceId+"&operate="+operate+"&sealcount="+sealcount+"&declaresn="+declaresn;
		webform.WebPDF.WebUrl=url;
		webform.WebPDF.RecordID=SPinstanceId;
		if (!webform.WebPDF.WebSave()){               //交互OfficeServer的OPTION="SAVEFILE"
			 alert("保存失败！");
			 return false;
		}else{
			alert("保存成功！");
			return true;
		}
			
	}
	function checksave() {
		var isChange = webform.WebPDF.DocumentModified;
		if (isChange != 0) {
			if (confirm("文档已修改是否保存？")) {
				uploadsubmit();
			}
		}
		webform.WebPDF.WebClose();
	}
</script>
<script language="javascript" for=WebPDF event="OnToolsClick(vIndex,vCaption)">
if(vIndex==1){
	 uploadsubmit();
}else if(vIndex==2){
	if (confirm("您确定要撤销PDF?")){
		undo("-1");
	}
}
</script>

<script type="text/javascript">

function undo(t) {
	$.ajax({
	    async:false,
	    cache: false,
        type: "GET",
        url: "${ctx}/sp/pdfFile/undoPDF",
        data: {processSerialNumber:processSerialNumber,SPinstanceId:SPinstanceId,printType:printType},
        dataType: "json",
        success: function(data){
             if(data.success){
            	 var url = "";
            	 alert("撤销PDF成功");
            	 if(printType=="banJieDan"){
     		        url = '${ctx}/sp/pdfFile/banJieDanForm?SPinstanceId='+SPinstanceId+'&processInstanceId='+processInstanceId+"&processSerialNumber="+processSerialNumber+"&taskId="+taskId;
            	 }else if(printType=="buQiBuZhengDan"){
	     		   // var url = '${ctx}/sp/pdfFile/banliDanForm?SPinstanceId='+SPinstanceId+'&processInstanceId='+processInstanceId+"&processSerialNumber="+processSerialNumber+"&taskId="+taskId;
            	 }else{
		     		url = '${ctx}/sp/pdfFile/banliDanForm?SPinstanceId='+SPinstanceId+'&processInstanceId='+processInstanceId+"&processSerialNumber="+processSerialNumber+"&taskId="+taskId;
            	 }
            	 window.location.href = url;
             }else{
            	 alert("撤销PDF失败");
             }
       }
	});
}
</script>

</head>
<body onload="loadobj()" onunload="checksave();">
<form name="webform" method="post" >
<object id="WebPDF" width="100%" height="1000px" classid="clsid:39E08D82-C8AC-4934-BE07-F6E816FD47A1" codebase="${ctx }/commons/ocx/iWebPDF.cab#version=8,1,0,856" VIEWASTEXT></object>
			 <iframe
					id="ifrContent" name="ifrContent" src="" width="100%" height="500"
					style="display:none" frameborder="0"></iframe>
	</form>
</body>
</html>
</html>
