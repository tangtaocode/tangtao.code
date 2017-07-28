<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/bpmTaglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>审批</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<script type="text/javascript" src="${ctx}/static/risesoft/js/myMenu.js"></script>
<style type="text/css">
*{margin: 0;padding: 0}
body{font-size: 12px;font-family: "宋体","微软雅黑";}
ul,li{list-style: none;}
a:link,a:visited{text-decoration: none;}
.menu{border-bottom:inset 0px;margin:0px auto 0 auto;}
.menu ul li{background:url("${ctx}/static/images/tree.jpg") no-repeat;background-size:100% 90%;border-bottom:outset 0px ;cursor:pointer; }/*一级菜单*/
.menu ul li a{padding-left: 10px; font-size:12px; display: block; font-weight:bold; height:36px;line-height: 36px;position: relative;cursor:pointer;
}
.menu ul li .inactive{ background:url("${ctx}/static/images/off.jpg") no-repeat;background-size:100% 90%;}
.menu ul li .inactives{background:url("${ctx}/static/images/on.jpg") no-repeat;background-size:100% 90%;} 
.menu ul li .ul{display: none;}/*折叠*/
.menu ul li .uls{display: block;}/*展开*/
.menu ul li ul li { border-left:0; border-right:0;  border-bottom:0;border-top:outset 0px ;}/*二级菜单*/
.menu ul li ul li .ul{display: none;}
.menu ul li ul li .uls{display: block;}
.menu ul li ul li a{ padding-left:25px;}
.menu ul li ul li ul li { border-bottom:0;border-top:outset 0px;}/*三级菜单*/
.last{ border-top:inset 0px ; }
.menu ul li ul li ul li a{ padding-left:40px;}
</style>
<script type="text/javascript">
$(document).ready(function() {
	tabPositon();
	//获取当前用户和部门
	$.ajax({
		type : 'POST',
		url : '${ctx}/main/userinfo',
		success : function(data){
			$("#user").text(data.userName);
			$("#org").text(data.orgName);
		}
	});
});

//标签位置
function tabPositon(){
    var tabs = "top";
	$('#tabs').tabs({tabPosition:tabs});
}



</script>
<script type="text/javascript">
	function logout() {
		$.ajax({
			type : 'POST',
			url : ctx+'/main/logout',
			dataType:'JSON',
			success : function(data, status){
				location.href =data.rc7LogoutUrl;
			}
		});
	}
</script>
</head>
<body class="easyui-layout" id="wrap">
		<div id="toolbar" data-options="region:'north'" style=" margin:0px; padding:0px; height:85px; width:100%; overflow:hidden;" align="right">
			<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;background-color:#CCEEFF;" align="right">
				<tr >
					<td  style="width: 71%;">
						<div  style=" margin:0px; padding:0px; height:85px; width:100%; overflow:hidden; background:url(${ctx}/static/risesoft/images/index_01.jpg) left top no-repeat; border-bottom:3px solid #40B8E6;"  >
						</div>
					</td>
					<td  style="width: 47%;">
						<div style="float:right; height:88px;overflow:hidden; width:100%; background:url(${ctx}/static/risesoft/images/flash.jpg) top right no-repeat;"  >							
							<div class="info"  >
								<div class="userinfo" >
									<ul style="line-height: 35px;">
										<li>
											<a href="#" class="sy"><b>首页</b></a>
											
											<a href="javascript:logout();"  class="tc"><b>退出</b></a>
										</li>
									</ul>
								</div>
							</div>
							<div class="uuser"  >
								<div class="uuserinfo" >
									<ul>
										<li>当前用户：<span id="user"></span>  | 所属部门：<span id="org"></span></li>
									</ul>
								</div>
							</div>
						</div>	
					</td>
				</tr>
			</table>
		</div>

	<div id="leftMenu" data-options="region:'west',split:true,title:'<span style=color:#FFFFFF;margin:40px;font-size:14px;>审批系统</span>'" style="width: 210px;  padding: 1px;">
		<div id="menu" class="menu" style="width: 98%; height:94%; padding: 1px; overflow-y:scroll">
			
		</div>
	</div>
	<div data-options="region:'center'" style="overflow: hidden;">
		<div id="tabs" class="easyui-tabs" data-options="fit:true,border:false,tools:'#tab-tools'">
			<!-- <div title="我的工作台" style="overflow: hidden;" data-options="closable:false"></div> -->
		</div>
		<div id="tab-tools">
			<a href="javascript:void(0)" class="easyui-linkbutton easyui-tooltip" title="刷新当前页" data-options="plain:true,iconCls:'icon-reload'" onclick="reloadTab()"></a> 
			<a href="javascript:void(0)" class="easyui-linkbutton easyui-tooltip" title="关闭当前页" data-options="plain:true,iconCls:'icon-cancel'" onclick="removeTab()"></a> 
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton easyui-tooltip" title="缩放窗口" data-options="plain:true,iconCls:'icon-arrow-out'" onclick="resizeTab(this)"></a> -->
		</div>
	</div>
	<div id="tabsMenu" style="width: 150px; display: none;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
	</div>
	<div data-options="region:'south',split:false" style="height: 30px; line-height: 30px; text-align: center; color: #666; background: #efefef; overflow: hidden;">
	</div>
</body>
</html>