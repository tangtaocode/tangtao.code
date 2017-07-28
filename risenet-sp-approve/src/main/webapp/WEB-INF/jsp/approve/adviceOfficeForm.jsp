﻿﻿﻿﻿﻿﻿﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<title></title>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body topmargin=0 leftmargin=0 rightmargin=0 bottommargin=0
	marginwidth=0 marginheight=0 >

	<table cellspacing="0" cellpadding="0" bordercolor="#D4D0C8" border="0"
		width="100%" bgcolor="#D4D0C8" style="border-bottom-style: groove"
		bordercolorlight="#EEEEEE" bordercolordark="#B1AEA9">
		<input type="hidden" name="instanceId" id="instanceId" value="${instanceId }"></input>
		<input type="hidden" name="xbqids" id="xbqids" value="${xbqids }"></input>
		<input type="hidden" name="xbzids" id="xbzids" value="${xbzids }"></input>
		<tbody>
			<tr>
				<td align="left" style="font: Menu;">&nbsp;
					<button onclick="saveDocument();" class="op1" id="saveDocument">
						<font color="red">保存world</font>
					</button>
					<!-- <button onclick="openTaoHong();" class="op1" id="openTaoHong">选择套红</button>
					<button onclick="undoTaoHong(2);" class="op1" id="undoTaoHong">撤销红头</button>
					<button onclick="saveEFileAndTopdf(2);" class="op1" id="saveEFileAndTopdf">转PDF并上传</button>
					<button onclick="showRevisions(this);" class="op1" id="showRevisions">隐藏修订</button> -->
					<button onclick="showToolbars(this);" class="op1" id="showToolbars">显示工具栏</button>
				</td>
				<td align="right" style="font: Menu;">&nbsp;</td>
				<td align="right" style="font: Menu;">&nbsp;</td>
			</tr>
		</tbody>
	</table>
	</td>
	</tr>
	</tbody>
	</table>

	<object id="NTKO"
		classid="clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404"
		codeBase="${ctx}/static/tags/ocx/OfficeControl.cab#version=5,0,2,4"
		width="100%" height="100%">
		<param name="BorderStyle" value="0">
		<param name="BorderColor" value="14402205">
		<param name="TitlebarColor" value="53668">
		<param name="TitlebarTextColor" value="0">
		<param name="MenubarColor" value="13160660">
		<param name="Caption" value="欢迎使用！">
		<param name="Titlebar" value="0">
		<param name="MaxUploadSize" value="10000000">
		<param name="CustomMenuCaption" value="辅助选项">
		<!-- <param name="ProductCaption" value="深圳市罗湖区信息中心">
		<param name="ProductKey" value="CF5FA619AD4331233437B6AE9E9137C09D5347BF"> -->
		<param name="ProductCaption" value="深圳市水务局">
<param name="ProductKey" value="B033D5F2FEE8CF084E41AA2CB582DBC60F5F0BDB">
		<SPAN STYLE="color: #3366cc">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
	</object>
	<script type="text/javascript">
		var jsonObj= '${map}';
		jsonObj = JSON.parse(jsonObj);
		var NTKO_OBJ;
		window.onload = function(){
			NTKO_OBJ = document.all.NTKO;
			var templateurl = "${ctx}/static/template/gaozhi.docx";
			NTKO_OBJ.OpenFromURL(templateurl);
			NTKO.Toolbars = false;
		}
	</script>
	<script language="javascript" for="NTKO" event="OnDocumentOpened(fileName, doc)">		
		var props = "";
		for(var p in jsonObj){
			if(typeof(jsonObj[p])!="function"){     
				if(jsonObj[p].toString().length>0){
					NTKO_OBJ.SetBookmarkValue(p,jsonObj[p]);
				}
			}     
		}
	</script>
	<script type="text/javascript">
	//保存word
	function saveDocument() {
		var info="";
		var instanceId = $("#instanceId").val();
		var xbqids = $("#xbqids").val();
		var xbzids = $("#xbzids").val();
		try {
			info = NTKO_OBJ.SaveToURL(
						"${ctx}/bjgz/saveDocument?instanceId="+instanceId+"&xbqids="+xbqids+"&xbzids="+xbzids,
						"currentDoc", "", "huifu/FileName", 0);
			if (info == "true") {
				alert("保存文件成功！");
				NTKO_OBJ.ActiveDocument.Saved = true;
				window.parent.parent.refreshMenu();

			} else {
				alert("保存文件错误，请联系管理员！");
				return;
			}
		} catch (e) {
			alert('无法保存文件！');
		}
	}
	
	function showToolbars(da) {
		var s = da.innerHTML;
		if (s == '显示工具栏') {
			NTKO.Toolbars = true;
			da.innerHTML = '隐藏工具栏';
		} else {
			NTKO.Toolbars = false;
			da.innerHTML = '显示工具栏';
		}
	}
	
	
	</script>
	</body>