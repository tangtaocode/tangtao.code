<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link rel="stylesheet" href="/css/jquery-ui.css" />  
<link href="/css/emp.css" type="text/css" rel="stylesheet"/>
<link href="/css/qyInvestIndex.css" type="text/css" rel="stylesheet" />
<link href="/css/login-box.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery-1.7.1.min.js"></script>  
<script src="/js/Scripts/jquery-ui.min.js"></script>  
<script src="/js/businessJS/shareWebService.js"></script>

<style type="text/css">
a:hover {color: red; text-decoration:none;}
#tipTitle{
	font-size:15px;
	text-align:left;
	width:400px;
	position:absolute;
	border:1px solid #333;
	background:#f7f5d1;
	padding:2px 5px;
	color:#333;
	display:none;
	padding:5px;
    border-radius:5px;
    -webkit-border-radius:5px;
    -moz-border-radius:5px;
}	
a{
	cursor:pointer;
}
</style>
<script type="text/javascript">
/**
this.tipTitle = function(){
		xOffset = 10;
		yOffset = 20;	
	$(".tipTitle").hover(function(e){
		if(this.title!="0"){
			this.t = this.title;
			this.title = "";									  
			$("body").append("<p id='tipTitle'>材料说明："+ this.t +"</p>");
			$("#tipTitle")
				.css("top",(e.pageY - xOffset) + "px")
				.css("left",(e.pageX + yOffset) + "px")
				.fadeIn("fast");
		}else{
			this.title = "";
			this.t="0"
		}										  
    },
	function(){
		this.title = this.t;		
		$("#tipTitle").remove();
    });	
	$(".tipTitle").mousemove(function(e){
		$("#tipTitle")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};
$(document).ready(function(){
	tipTitle();
});**/
$(function() {
	$( "#tabs" ).tabs();
	
	$(".material_del").click(function(){
		var delGuid = $(this).attr("id");
		var delType = $(this).attr("delType");
		var crentent = $(this);
		$.post("/onlineService/sick.html",{'appInstanceGuid':$("#appInstanceGuid").val()},
		function(data){
		if(data.message == "1"){
		 alert("当前业务已经传窗口收件了，不能修改材料！");
		 }else{
		  	if(delType==null||""==delType){
				deleteDoc(delGuid);
			}else{
				deleteFile(delGuid,delType,crentent);
			}
		  }
		});
	});
});
</script>

</head>
<body>

<s:hidden id="approveItemGUID" name="approveItemGUID"></s:hidden>
<s:hidden id="guids" name="guids"></s:hidden>
<%--
<s:if test="zbwjFlag==1">

	<table  cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
		class="BS_list" style="font-family:微软雅黑;display:">
			<tr>
				<th>工程信息</th>
			</tr>
			<tr>
				<td>
					<s:label>工程名称</s:label>
				</td>
				<td width="74%">
					<s:textfield cssClass="input1_query" name="GONGCHENGMINGCHENG" id="GONGCHENGMINGCHENG" readonly="true"/>
					<span style="color:red" id="span_gcmc">*</span>
					<s:submit cssClass="buttonClass_4" type="button" onclick="selectGcInfo();">选择</s:submit>
				</td>
			</tr>
			<tr>
				<td><s:label >工程编号</s:label></td>
				<td>
					<s:textfield cssClass="input1_query" name="GONGCHENGBIANHAO" id="GONGCHENGBIANHAO" readonly="true"/>
					<s:hidden name="zbwjFlag" id="zbwjFlag"/>
					<!-- <span style="color:red" id="span_gcbh">*</span> -->
				</td>
			</tr>
			<tr>
				<td><s:label>项目名称</s:label></td>
				<td colspn="2">
					<s:textfield cssClass="input1_query" name="XIANGMUMINGCHENG" id="XIANGMUMINGCHENG" readonly="true"/>
					<!-- <span style="color:red" id="span_xmmc">*</span> -->
				</td>
			</tr>
		
		<tr>
				<th>工程信息</th>
			</tr>
			<s:if test="approveItem.itemid==\"{7F000001-FFFF-FFFF-A669-F75700000079}\"||approveItem.itemid==\"{0A009FA8-FFFF-FFFF-8836-09C6000004FF}\"">
			
			<tr>
				<td>
					<s:label>项目名称</s:label>
				</td>
				
				<td width="74%">
					<s:textfield cssClass="input1_query" name="SGGK_xmmc" id="SGGK_xmmc" readonly="true"/>
					<span style="color:red" id="span_xmmc">*</span>
					<s:submit cssClass="buttonClass_4" type="button" onclick="selectGcInfo();">选择</s:submit>
				</td>
			</tr>
			<tr>
				<td><s:label >项目编号</s:label></td>
				<td>
					<s:textfield cssClass="input1_query" name="SGGK_xmbh" id="SGGK_xmbh" readonly="true"/>
					<s:hidden name="zbwjFlag" id="zbwjFlag"/>
					<span style="color:red" id="span_xmbh">*</span> 
				</td>
			</tr>
			<tr>
				<td><s:label>建设单位</s:label></td>
				<td colspn="2">
					<s:textfield cssClass="input1_query" name="SGGK_zbr" id="SGGK_zbr" readonly="true"/>
					<span style="color:red" id="span_jsdw">*</span> 
				</td>
			</tr>
			</s:if>
			<s:if test="approveItem.itemid==\"{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}\"||approveItem.itemid==\"{0A009FA8-0000-0000-68C9-3CDB0000004C}\"">
					<tr>
				<td>
					<s:label>项目名称</s:label>
				</td>
				
				<td width="74%">
					<s:textfield cssClass="input1_query" name="SGGK_xmmc" id="SGGK_xmmc" />
					<span style="color:red" id="span_xmmc">*</span>					
				</td>
			</tr>
			<tr>
				<td><s:label >项目编号</s:label></td>
				<td>
					<s:textfield cssClass="input1_query" name="SGGK_xmbh" id="SGGK_xmbh"/>
					<s:hidden name="zbwjFlag" id="zbwjFlag"/>
					<!-- <span style="color:red" id="span_gcbh">*</span> -->
				</td>
			</tr>
			<tr>
				<td><s:label>建设单位</s:label></td>
				<td colspn="2">
					<s:textfield cssClass="input1_query" name="SGGK_zbr" id="SGGK_zbr" />
					<span style="color:red" id="span_jsdw">*</span> 
				</td>
			</tr>
			</s:if>
			
			<tr>
				<td><s:label>工程名称</s:label></td>
				<td colspn="2">
					<s:textfield cssClass="input1_query" name="GONGCHENGMINGCHENG" id="GONGCHENGMINGCHENG" />
					<span style="color:red" id="span_gcmc">*</span>
				</td>
			</tr>
			<tr>
				<td><s:label>工程编号</s:label></td>
				<td colspn="2">
					<s:textfield cssClass="input1_query" name="GONGCHENGBIANHAO" id="GONGCHENGBIANHAO" />
					<!-- <span style="color:red" id="span_xmmc">*</span> -->
				</td>
			</tr>
		</table>
</s:if>

--%>
<s:if test="guidenoFileList.size<1">
<table cellpadding="0" cellspacing="0" border="0" width="100%" id="cardTab1"
			class="BS_list" style="font-family:微软雅黑;display:">
						<tr>
							<th>
								材料名称
							</th>
							<th width="14%" style="text-align:center;">
								上传
							</th>
						</tr>
						<s:iterator value="guideFilTypeeList" status="typeStat">
							<tr>
								<td style="font-weight: bold;color:#E18B46"><s:property value="typename" /></td><td>&nbsp;</td>
							</tr>
							<s:iterator value="guideFileList" status="fileStat">
								<tr><td>
								<s:if test="online_essential==1"><span style="color:red;">*</span></s:if><s:else>&nbsp;</s:else>
								<span class="tipTitle" title="<s:property value="describe"/>">(<s:property value="#fileStat.index+1" />)<s:property value="materialname" /></span>
								<s:if test="describe!=null">
									<span style="color:#BFBFBF;font-size:13px;">材料说明：<s:property value="describe"/></span>
								</s:if>
								
								<table width="98%" cellpadding="0" cellspacing="0" border="0">
									<s:iterator value="fileList" status="file">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<img width="8px" style="padding-top:6px;" height="8px" src="/images/lineservice/fileli.png">&nbsp;
											<a href="/servlet/DownFileServlet/downType/approveItemWeb/fileId/<s:property value="guid"/>.html"><s:property value="filename" /></a></td>
											<!--  
											<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>' delType="approveItem"><img border="0px" width="16px" height="16px" src="/images/lineservice/delete.png"> </a></td>
											-->
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>' delType="approveItem"><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
									<s:iterator value="doctypeList" status="docT">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<s:property value="zzname" />：<s:property value="cardid" /></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>'><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
								</table>
									</td>
									
									<td style="text-align:center;">
									<s:if test="bz==0"><!-- 未被标志位已提交 -->
										<s:if test="doctypeguid==null">
											<img onclick="showFile('<s:property value="id"/>');" src="/images/lineservice/upLine.png" class="imagefile">
										</s:if>
										<s:else>
											<img onclick="showPage('共享证照编号输入','/onlineService/findDocTypeByFileGuid/fileGuid/<s:property value="id"/>/appInstanceGuid/<s:property value="appInstanceGuid"/>/action.html',700,400);" src="/images/lineservice/dataEdit.png" class="imagefile">
										</s:else>
									</s:if><!-- 未被标志位已提交 end-->
									<s:else><!-- 已被标志位已提交 start-->
										&nbsp;
									</s:else><!-- 已被标志位已提交 end-->
									</td>
								</tr>
							</s:iterator>
						</s:iterator>
					<s:iterator value="guideFileList" status="typeStat">
						<tr><td>
						<s:if test="online_essential==1"><span style="color:red;">*</span></s:if><s:else>&nbsp;</s:else>
						<span class="tipTitle" title="<s:property value="describe"/>">(<s:property value="#typeStat.index+1" />)<s:property value="materialname" /></span>
						<s:if test="describe!=null">
							<span style="color:#BFBFBF;font-size:13px;">材料说明：<s:property value="describe"/></span>
						</s:if>
						<table width="98%" cellpadding="0" cellspacing="0" border="0">
									<s:iterator value="fileList" status="file">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<img width="8px" style="padding-top:6px;" height="8px" src="/images/lineservice/fileli.png">&nbsp;
											<a href="/servlet/DownFileServlet/downType/approveItemWeb/fileId/<s:property value="guid"/>.html"><s:property value="filename" /></a></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>' delType="approveItem"><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
									<s:iterator value="doctypeList" status="docT">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<s:property value="zzname" />：<s:property value="cardid" /></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>'><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
								</table>
							</td>
							<td style="text-align:center;">
								<s:if test="bz==0"><!-- 未被标志位已提交 -->
									<s:if test="doctypeguid==null">
										<img onclick="showFile('<s:property value="id"/>');" src="/images/lineservice/upLine.png" class="imagefile"> 
									</s:if>
									<s:else>
										<img onclick="showPage('共享证照编号输入','/onlineService/findDocTypeByFileGuid/fileGuid/<s:property value="id"/>/appInstanceGuid/<s:property value="appInstanceGuid"/>/action.html',700,400);" src="/images/lineservice/dataEdit.png" class="imagefile">
									</s:else>
								</s:if><!-- 未被标志位已提交 end-->
								<s:else><!-- 已被标志位已提交 start-->
										&nbsp;
								</s:else><!-- 已被标志位已提交 end-->
							</td>
						</tr>
					</s:iterator>
				</table>
</s:if>
<s:if test="guidenoFileList.size>0">
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td>
						<div id="tabs">
				  <ul>
				      <li><a href="#tabs-1"><span style="font-weight:bold;">上传材料</span></a></li>
				      <li><a href="#tabs-2"><span style="font-weight:bold;">共享材料</span></a></li>
				   </ul>
				   <div id="tabs-1">
				   <table cellpadding="0" cellspacing="0" border="0" id="cardTab1" class="BS_list" style="width:900px;font-family:微软雅黑;display:">
				       <tr>
							<th>
								材料名称
							</th>
							<th width="14%" style="text-align:center;">
								上传
							</th>
						</tr>
						<s:iterator value="guideFilTypeeList" status="typeStat"><!-- 材料类型 -->
							<tr>
								<td style="font-weight: bold;color:#E18B46"><s:property value="typename" /></td><td>&nbsp;</td>
							</tr>
							<s:iterator value="guideFileList" status="fileStat"><!-- 存放没有材料类型的材料 -->
								<tr><td>
								<s:if test="online_essential==1"><span style="color:red;">*</span></s:if>
								<s:else>&nbsp;</s:else>
								<span class="tipTitle" title="<s:property value="describe"/>">(<s:property value="#fileStat.index+1" />)<s:property value="materialname" /></span>
								<s:if test="describe!=null">
									<span style="color:#BFBFBF;font-size:13px;">材料说明：<s:property value="describe"/></span>
								</s:if>
								
								<table width="98%" cellpadding="0" cellspacing="0" border="0">
									<s:iterator value="fileList" status="file">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<img width="8px" style="padding-top:6px;" height="8px" src="/images/lineservice/fileli.png">&nbsp;
											<a href="/servlet/DownFileServlet/downType/approveItemWeb/fileId/<s:property value="guid"/>.html"><s:property value="filename" /></a></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>' delType="approveItem"><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
									<s:iterator value="doctypeList" status="docT">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<s:property value="zzname" />：<s:property value="cardid" /></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 -->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>'><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
								</table>
									</td>
									
									<td style="text-align:center;">
										<s:if test="bz==0"><!-- 未被标志位已提交 -->
											<s:if test="doctypeguid==null">
												<img onclick="showFile('<s:property value="id"/>');" src="/images/lineservice/upLine.png" class="imagefile">
											</s:if>
											<s:else>
												<img onclick="showPage('共享证照编号输入','/onlineService/findDocTypeByFileGuid/fileGuid/<s:property value="id"/>/appInstanceGuid/<s:property value="appInstanceGuid"/>/action.html',700,400);" src="/images/lineservice/dataEdit.png" class="imagefile">
											</s:else>
										</s:if><!-- 未被标志位已提交 end-->
										<s:else><!-- 已被标志位已提交 start-->
										&nbsp;
										</s:else><!-- 已被标志位已提交 end-->
									</td>
								</tr>
							</s:iterator>
						</s:iterator>
					<s:iterator value="guideFileList" status="typeStat">
						<tr><td>
						<s:if test="online_essential==1"><span style="color:red;">*</span></s:if>
						<s:else>&nbsp;</s:else>
						<span class="tipTitle" title="<s:property value="describe"/>">(<s:property value="#typeStat.index+1" />)<s:property value="materialname" /></span>
						<s:if test="describe!=null">
									<span style="color:#BFBFBF;font-size:13px;">材料说明：<s:property value="describe"/></span>
								</s:if>
						<table width="98%" cellpadding="0" cellspacing="0" border="0">
									<s:iterator value="fileList" status="file">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<img width="8px" style="padding-top:6px;" height="8px" src="/images/lineservice/fileli.png">&nbsp;
											<a href="/servlet/DownFileServlet/downType/approveItemWeb/fileId/<s:property value="guid"/>.html"><s:property value="filename" /></a></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 -->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>' delType="approveItem"><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if>
										</tr>
									</s:iterator>
									<s:iterator value="doctypeList" status="docT">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<s:property value="zzname" />：<s:property value="cardid" /></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a class="material_del" id='<s:property value="guid"/>'><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
								</table>
							</td>
							<td style="text-align:center;">
							<s:if test="bz==0"><!-- 未被标志位已提交 start-->
								<s:if test="doctypeguid==null">
									<img onclick="showFile('<s:property value="id"/>');" src="/images/lineservice/upLine.png" class="imagefile"> 
								</s:if>
								<s:else>
									<img onclick="showPage('共享证照编号输入','/onlineService/findDocTypeByFileGuid/fileGuid/<s:property value="id"/>/appInstanceGuid/<s:property value="appInstanceGuid"/>/action.html',700,400);" src="/images/lineservice/dataEdit.png" class="imagefile">
								</s:else>
							</s:if><!-- 未被标志位已提交 end-->
							<s:else><!-- 已被标志位已提交 start-->
										&nbsp;
							</s:else><!-- 已被标志位已提交 end-->
							</td>
						</tr>
					</s:iterator>
					</table>
				   </div>  
				   <div id="tabs-2">  
				     <table cellpadding="0" cellspacing="0" border="0" id="cardTab1" class="BS_list" style="width:900px;font-family:微软雅黑;display:">
				       <tr>
							<th>
								材料名称
							</th>
							<th width="14%" style="text-align:center;" colspan='2'>
								填写
							</th>
						</tr>
						<s:iterator value="guidenoFileList" status="typeStat">
						<tr>
							<td>
								<s:if test="online_essential==1"><span style="color:red;">*</span></s:if>
								<s:else>&nbsp;</s:else>
								<span class="tipTitle" title="<s:property value="describe"/>">(<s:property value="#typeStat.index+1" />)<s:property value="materialname" /></span>
								<!-- 
								<s:if test="describe!=null">
									<span style="color:#BFBFBF;font-size:13px;">材料说明：<s:property value="describe"/></span>
								</s:if>
								-->
									
								<%-- 迭代附件 --%>
								<table width="98%" cellpadding="0" cellspacing="0" border="0">
									<s:iterator value="fileList" status="file">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<img width="8px" style="padding-top:6px;" height="8px" src="/images/lineservice/fileli.png">&nbsp;
											<a href="/servlet/DownFileServlet/downType/approveItemWeb/fileId/<s:property value="guid"/>.html"><s:property value="filename" /></a></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a del_insID='<s:property value="id"/>' class="material_del" id='<s:property value="guid"/>' delType="approveItem"><span  title="删除材料" style="color:red;">删除</span></a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
									</s:iterator>
									<s:iterator value="doctypeList" status="docT">
										<s:if test="cardid==null">
										<tr>
											<td width="85%" style="border:0px;color:#6688CC;">
											<s:property value="zzname" />：<s:property value="cardid" /></td>
											<s:if test="bz==0"><!-- 未被标志位已提交 start-->
												<td style="border:0px;"><a del_insID='<s:property value="id"/>' class="material_del" id='<s:property value="guid"/>'><span  title="删除材料" style="color:red;">删除</span> </a></td>
											</s:if><!-- 未被标志位已提交 end-->
										</tr>
										</s:if>
									</s:iterator>
								</table>
							</td>
							
							<!-- 已实现接口 -->
							<s:if test="isok!=null&&isok==1">
								<td style="text-align:left;border-right:0px;padding-right:0px;padding-left:5px;" width="10%">
									<span style="color:#BFBFBF;font-size:13px;"><s:property value="share_code"/>：</span>
									<span style="color:#BFBFBF;font-size:13px;">(例如:<s:property value="code_model"/>)</span>
								</td>
								<td style="text-align:left;padding-right:0px;" width="24%">
								<s:if test="bz==0"><!-- 未被标志位已提交 -->
									<s:if test="doctypeguid==null">
										<img onclick="showFile('<s:property value="id"/>');" src="/images/lineservice/upLine.png" class="imagefile"> 
									</s:if>
									<s:else>
										<!--  
										<img onclick="showPage('共享证照编号输入','/onlineService/findDocTypeByFileGuid/fileGuid/<s:property value="id"/>/appInstanceGuid/<s:property value="appInstanceGuid"/>/action.html',700,400);" src="/images/lineservice/dataEdit.png" class="imagefile">
										-->
										<input uploadID='<s:property value="id"/>' type="text" name="<s:property value='code'/>" id="<s:property value='code'/>" size="18" fileGuid='<s:property value="id"/>' appInstanceGuid='<s:property value="appInstanceGuid"/>' guid='<s:property value="guid"/>' value='<s:property value="cardid"/>'/>
										<s:if test="cardid!=null">
											<img src='/engine/jquery/themes/icons/checked.gif' id='<s:property value="id"/>' width='16' height='16'/>
										</s:if>
									</s:else>
								</s:if><!-- 未被标志位已提交 end-->
								<s:else><!-- 已被标志位已提交 -->
									<s:if test="doctypeguid==null">
									</s:if>
									<s:else>
										<s:property value="cardid"/>
										<s:if test="cardid!=null">
											<img src='/engine/jquery/themes/icons/checked.gif' id='WS_XMZJZCZS0' width='16' height='16'/>
										</s:if>
									</s:else>
								</s:else><!-- 已被标志位已提交 end-->
								</td>
								<!--  
								<td style="text-align:left;border-left:0px;padding-left:0px;padding-left:0px;padding-right:0px;" width="20%">
									<span style="color:#BFBFBF;font-size:13px;">(例如:<s:property value="code_model"/>)</span>
								</td>
								-->
							</s:if>
							
							<!-- 未实现接口 -->
							<s:else>
								<td style="text-align:center;" colspan="2">
									<s:if test="bz==0"><!-- 未被标志位已提交 -->
										<img onclick="showFile('<s:property value="id"/>');" src="/images/lineservice/upLine.png" class="imagefile">
									</s:if><!-- 未被标志位已提交 end-->
									<s:else><!-- 已被标志位已提交 start-->
										&nbsp;
									</s:else><!-- 已被标志位已提交 end-->
								</td>
							</s:else>
						</tr>
					</s:iterator>
					</table>
				   </div>  
				</div>
					
					</td>
				</tr>
	</table>
</s:if>	
</body>
</html>
