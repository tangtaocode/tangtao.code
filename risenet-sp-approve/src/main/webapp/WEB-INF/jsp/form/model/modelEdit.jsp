<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<title>表单模型编辑</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/layout/layout-default-latest.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/css/myLayout.css" />
<link rel="stylesheet" href="${ctx}/static/jquery/tree/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">

<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layout/jquery.layout-latest.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tree/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.loadJSON.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.min.js"></script>

<script type="text/javascript">
    var formModel = null;
    var watchable = null;
	var zTreeObj = null;
	var clickedNode;
	
	var formModelEmpty = {
	    id:"",
	    pid:"",
	    name:"",
	    title:"",
	    elementWidth:40,
	    tableName:"",
	    fieldName:"",
	    fieldType:1,
	    formId:"${formId}",
	    //function:"",
	    sectionLabel:"",
	    sectionType:0,
	    jsonType:"object",
	    labelAlign:0,
	    labelWidth:40,
	    newLine:true,
	    spaceWidth:20,
	    tabIndex:1
	};
	
	var setting = {
		async : {
			enable : true,
			url : "${ctx}/form/model/getTreeNodes",
			autoParam : [ "id" ],
			otherParam : {
				"formId" : "${formId}"
			}
		},
		view : {
			selectedMulti : false,
			//fontCss : getFont,
			//nameIsHTML : true,
			showIcon : true
		},
		data : {
			key : {
				name : "elementName",
				title : "elementLabel"
			},
			simpleData : {
				enable : true,
				idKey : "id",
				pIdKey : "pid"
			}
		},
		edit : {
			enable : false
		},
		callback : {
			onClick : onClick
		}
	};

	function getFont(treeId, node) {
		return node.font ? node.font : {};
	}

	function onClick(e, treeId, treeNode) {
		clickedNode = treeNode;
		formModel = treeNode;
		
		//document.getElementById("nodeForm").reset();
		//$('#nodeForm').not(':button, :submit, :reset, :hidden').val('').removeAttr('checked').removeAttr('selected');
		$('form').loadJSON(formModel);
		//alert($.toJSON(formModel));
		$('#elementName').focus();
	}

	$(document).ready(function() {
		var treeObj = $("#tree");
		zTreeObj = $.fn.zTree.init(treeObj, setting);

		var myLayout = $("body").layout({
			west__size : 300,
			center__minHeight : 200,
			center__minWidth : '100%',
			//spacing_closed : 5,
			togglerTip_open : '关闭',
			togglerTip_closed : '打开',
			resizerTip : '拖动',
			initClosed : false,
			maskContents : true
		});
		
		$('#elementName').focus();
		
		$('button').click(function(event) {
			switch (this.id) {
			case 'addChild':
				var name = prompt('请输入英文名称：');
				if(name){
					$.post('${ctx}/form/model/save', 
						{ 
						    pid: clickedNode?clickedNode.id:'rootGuid',
						    formId: '${formId}',
						    elementName: name,
						    elementLabel: name,
						    sectionType:0,
						    jsonType:'object',
						    labelAlign:0,
						    labelWidth:40,
						    newLine:true,
						    spaceWidth:20,
						    tabIndex:1
						},
				        function(data){
							zTreeObj.reAsyncChildNodes( clickedNode, "refresh");
				            alert(data.success);
				        }, 
				        "json"
				    );
				}
				break;
			case 'addSiblings':
				var name = prompt('请输入英文名称：');
				if(name){
					$.post('${ctx}/form/model/save',
						{
						    pid: clickedNode?clickedNode.pid:'rootGuid',
						    formId: '${formId}',
						    elementName: name,
						    elementLabel: name,
						    sectionType:0,
						    jsonType:'object',
						    labelAlign:0,
						    labelWidth:40,
						    newLine:true,
						    spaceWidth:20,
						    tabIndex:1
						},
				        function(data){
							zTreeObj.reAsyncChildNodes( clickedNode?clickedNode.getParentNode():null, "refresh");
				            alert(data.success);
				        }, 
				        "json"
				    );
				}
				break;
			case 'delete':
				if(!clickedNode || clickedNode.isParent){
					alert('请选择一个叶子节点。');
					return;
				}
				$.getJSON('${ctx}/form/model/delete', { id: clickedNode.id}, function(json){
					zTreeObj.removeNode(clickedNode);
					alert(json.msg);
				});
				break;
			case 'save':
				$("#nodeForm").ajaxSubmit({
					type : 'POST',
					dataType : 'json',
					url : '${ctx}/form/model/save',
					success : function(responseText, statusText, xhr, $form) {
						alert(statusText);
					}
				});
				break;
			}
			return false;//避免提交两次。
		});
	});
</script>
</head>
<body>
	<div class="ui-layout-west">
		<div class="ui-layout-content">
			<ul id="tree" class="ztree"></ul>
		</div>
	</div>

	<div class="ui-layout-center">
		<div class="header">
			<button id="addChild">增加下级节点</button><button id="addSiblings">增加同级节点</button><button id="delete">删除节点</button><button id="buildForm">生成表单</button>
		</div>
		<div class="ui-layout-content">
			<form id="nodeForm" action="" method="post">
				<input type="hidden" name="id" id="id""/>
				<input type="hidden" name="pid" id="pid"/>
				<input type="hidden" name="formId" id="formId" value="${formId}" />
				<input type="hidden" name="function" id="function"/><br/>
				元素名称：<input type="text" name="elementName" id="elementName"/><br/>
				元素标题：<input type="text" name="elementLabel" id="elementLabel"/><br/>
				JSON类型：<select name="jsonType" id="jsonType">
                                <option value="">--</option>
								<option value="array">array</option>
								<option value="object">object</option>
								<option value="string">string</option>
								<option value="number">number</option>
								<option value="boolean">boolean</option>
								<option value="function">function</option>
						</select><br/><br/><br/>
				标题宽度：<input type="text" name="labelWidth" id="labelWidth"/><br/>
				标题类型：<select name="labelAlign" id="labelAlign" data-key="labelAlign">
								<option value="0">显示在元素左侧</option>
								<option value="1">显示在元素顶部</option>
						</select><br/>
				元素宽度: <input type="text" name="elementWidth" id="elementWidth"/><br/>
				间隔宽度：<input type="text" name="spaceWidth" id="spaceWidth"/><br/>
				是否换行：<input type="text" name="newLine" id="newLine"/><br/>
				
				段落标题：<input type="text" name="sectionLabel" id="sectionLabel"/><br/>
				段落类型：<select name="sectionType" id="sectionType">
						 		<option value="0">单行</option>
								<option value="1">多行</option>
						 </select><br/>
				数据库表：<input type="text" name="tableName" id="tableName"/><br/>
				表字段： <input type="text" name="fieldName" id="fieldName"/><br/>
				<input type="hidden" name="fieldType" id="fieldType""/>
				排列序号：<input type="text" name="tabIndex" id="tabIndex"/><br/><br/>
				
				<button id="save">保存</button>
			</form>
		</div>
	</div>

</body>
</html>