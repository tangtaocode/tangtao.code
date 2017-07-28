<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>

<style type="text/css" media=print>
.noprint{display : none }
</style>
<script type="text/javascript">
 var id='${guid}';
 $(function(){
	$.post("${ctx}/scanninghistory/read",{gid:id},function(result){
		/* console.info(result.info);
		console.info(result.names); */
		var arr=result.info;
		var map=result.names;
		var Con = document.getElementById("iden");
		for(var i=0;i<arr.length;i++){
			//console.info(map[c]+c);
			var tr = document.createElement("tr");
			Con.appendChild(tr);
			var td = document.createElement("td");
			tr.appendChild(td);

			td.innerHTML = "<span  onclick=getBigImage('${ctx}/scanninghistory/readeveryone?geshu="+i+"&gid=${guid}')><br/><img src='${ctx}/scanninghistory/readeveryone?geshu="+i+"&gid=${guid}' style='width:90px;height:120px'/><br/>"+map[arr[i]]+"<br/></span>";
			}
 		 },"json");  
})

function getBigImage(param){
	//console.info(param);
	$("#img").attr("src",param);
}
 
</script>
	
<title>证照扫描</title>
</head>
<body>
   <div  class="box1" panelWidth="100%" panelHeight="700"  overflow="false"  position="center">
    <div >
		<form action="" id="queryForm" >
			<input  type="hidden" name="gid" id="gid" value="${id }"/>
			<input type="hidden" name="processDefinitionId" id="processDefinitionId" value="${id }"/>
            <table  class="tableStyle" formMode="line">
		        <tr><th colspan="2">影像查看</th></tr>
		
		        <tr><td height="25">业务名称：</td><td>${approvname}</td></tr>
		
		        <tr><td height="25">申请人姓名：</td><td>${name}</td></tr>
		
		        <tr><td height="25">经办部门：</td><td>${tel}</td></tr>
		        
		        <tr><td height="25">办结时间：</td><td>${CHENGNUORIQI}</td></tr>
		        
		        <tr><td height="25">证照有效期：</td><td>${VALIDITYPERIOD}</td></tr>
		         <tr><td colspan="2" align="center" valign="top">
		<table  style="width:100%;height:1000px;border:1px solid #fff;" >
			<tr>
				<td width="30%" valign=top>
				<table  id="iden" align=center valign=top border="1" cellspacing="0" bordercolor="#fff" width="100%" style="text-align:center;vertical-align:middle">
							  
							  <%--动态添加图片的id RISENET_FILE储存图片的表--%>
				</table>
				</td>
				<td>	
				<div id="view" style="text-align:center;margin:0px auto">
									<%--<img src="file:///E|/photos/01.jpg" width="400" height="100"/> 
									<img src="/file/01.jpg" style="margin:0px auto;padding-top: 10%;"/>--%>
									<img id="img" src="${ctx}/scanninghistory/readeveryone?geshu=0&gid=${guid}" style="padding-top:10;width:90%;height:auto;"/>
							</div></td>
			</tr>
		
	</table><br><br>
		        </td></tr>
		    </table>

			</form>
			</div>
	  </div>
	</body>
</html>