<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>库表管理</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'west',split:true,title:'库表目录'" style="overflow: auto;width:250px;">
			<ul id="tree_table" style="height:97%;"></ul>
		</div>
		<div data-options="region:'center',border:false" style="overflow: hidden;">
			<iframe id="rightframe" name="rightframe" src="" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
		</div>
	</div>
	<div id="pNodeMenu" class="easyui-menu" style="width: 150px;">
    	<div id="p_refresh" onclick="refresh();">刷新</div>
    	<div id="p_line" class="menu-sep"></div>
    	<div id="p_add" onclick="addTablePage();">新建表</div>
	</div>
    <div id="treeMenu" class="easyui-menu" style="width: 150px;">
    	<div id="m_add" onclick="showTableDetail(selectNode.text);">查看表字段</div>
    	<div id="m_edit">查看表数据</div>
    	<div id="m_rename" onclick="tableRename(selectNode.text);">重命名</div>
		<div id="m_line" class="menu-sep"></div>
		<div id="m_del" onclick="tableDelete(selectNode.text);">删除表</div>
	</div>
	<script>
		//树形菜单
		var treeObj = $('#tree_table');
		//右键菜单
		var rMenu = $('#treeMenu');
		var pMenu = $('#pNodeMenu');
		
		var selectNode;
		
		function addTablePage() {
			var src = '${ctx}/db/addTablePage';
			$('#rightframe').attr('src', src);
		}
		
		//展示相应表的字段详情
		function showTableDetail(tableName) {
			var src = '${ctx}/db/tableDetailPage?tableName='+tableName;
			$('#rightframe').attr('src', src);
		}
		
		//刷新库表树
		function refresh() {
			treeObj.tree('reload');
		}
		
		//删除表
		function tableDelete(tableName) {            
            $.ajax({
				type : 'POST',
				dataType : 'json',
				data : {
					tableName: tableName
				},
				url : '${ctx}/db/table/remove',
				success : function(res) {
					top.$.messager.show({
						title : '提示',
						msg : res.msg
					});
					if(res.success) {
						refresh();
					}
				}
			});
        }
		
		//表重命名
		function tableRename(tableName) {            
            var retVal = prompt("请输入表名称：");
            if(!retVal){
                alert('表名称不能为空。');
                return;
            }
            if(retVal.length > 40){
               	alert('表名长度不能大于40！');
               	return;
               }
               var myPattern = new RegExp("^[a-zA-Z]"); // 以英文字母开头
               if(!myPattern.exec(retVal)) {
                   alert("表名必须以英文字母开头!");
                   return;
               }
               $.ajax({
				type : 'POST',
				dataType : 'json',
				data : {
					tableNameOld: tableName,
					tableNameNew: retVal
				},
				url : "${ctx}/db/table/rename",
				success : function(res) {
					top.$.messager.show({
						title : '提示',
						msg : res.msg
					});
					if(res.success) {
						refresh();
						showTableDetail(retVal);
					}
				}
			});
        }
		
		$(function() {
			treeObj.tree({
			    //data: $.parseJSON('${tables}'),
			    url: '${ctx}/db/getTables',
			    onClick: function(node) {
			    	treeObj.tree('select', node.target);
			    	if(node.text != '库表列表') {
			    		showTableDetail(node.text);
			    	}
			    },
			    onContextMenu: function(e, node){
					e.preventDefault();
					selectNode = node;
					treeObj.tree('select', node.target);
					if(node.text == '库表列表') {
						pMenu.menu('show', {
							left: e.pageX,
							top: e.pageY
						});
					}else {
						rMenu.menu('show', {
							left: e.pageX,
							top: e.pageY
						});
					}
				},
			    onLoadSuccess: function() {
			    	return;
			    	var nodes = treeObj.tree('getRoots');
			    	var children = nodes[0].children;
			    	if(children.length > 0) {
			    		treeObj.tree('select', children[0].target);
			    		//showTableDetail(children[0].text);
			    	}
			    }
			});
		});
	</script>
</body>
</html>
