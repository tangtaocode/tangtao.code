<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>文件夹树形</title>
<link rel="stylesheet" href="<%=path%>/css/ztree/demo.css"/>
<link rel="stylesheet" href="<%=path%>/css/ztree/zTreeStyle.css"/>
<script type="text/javascript" src="<%=path%>/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=path%>/js/ztree/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=path%>/js/ztree/jquery.ztree.excheck-3.5.js"></script>
</head>
<SCRIPT language="javascript"> 
		var setting = {
			check: {
				enable: true,
				chkStyle: "checkbox",
				radioType: "all"
				
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};							
		
		//加载文件夹树
		function backCatalogsNodes(data){				
			var zNodes = new Array();
			var folderguid = '';
			var rows=$("#bigdiv li").length;
			for(var r=1;r<rows;r++){
				folderguid = folderguid +$("#bigdiv li").eq(r).attr("id")+",";
			}
			for(var i=0;i<data.length;i++){		
				if(folderguid!='' && folderguid.indexOf(data[i].folder_guid)>-1){//已选项 勾上		
						zNodes[i]={id:data[i].folder_guid,pId:data[i].superior_guid,name:data[i].folder_name,deedbox_guid:data[i].deedbox_guid,chkDisabled:true,checked:true,open:true};
					}else{
						zNodes[i]={id:data[i].folder_guid,pId:data[i].superior_guid,name:data[i].folder_name,deedbox_guid:data[i].deedbox_guid,open:false};
				}
			}
			$.fn.zTree.init($("#forlderTrees"), setting,zNodes);
			$('#treecontain').show();
		}
		//加载文档自动获取当前人有权限的栏目
		$(document).ready(function(){
			$.post(
			'/employeeSet/getFolderTree',
			function success(data){
			 backCatalogsNodes(data);
			},'json');
		});	
			
		//文件夹 确定
		function comfirForlder(){
			var zTree = $.fn.zTree.getZTreeObj("forlderTrees"),
			nodes =  zTree.getCheckedNodes(true);
			var icon_guid = $("#inputfenping0").val();
			var num = $('#yuansunum0').val();
			var picnum=0;
			var checkids = "";
			for(var i=0;i<nodes.length;i++){
				num++;
				checkid = nodes[i].id;
				checkids = checkids+checkid+",";
				checkname = nodes[i].name;
				if(num>12){
					picnum = num-12;
				}else{
					picnum = num;
				}
				if(num>24){
					alert("添加应用最多只能24个！");
					return "";
				}else{
					//符合条件加一行
					docreateRow(checkid,checkname,icon_guid,picnum);
					continue;
					}
				
			}
			checkids = checkids.substring(0, checkids.length-1);
			addsubscreen(checkids);
		}					
</SCRIPT>			
<body>
<ul id="forlderTrees" class="ztree"></ul>
</body>
</html>