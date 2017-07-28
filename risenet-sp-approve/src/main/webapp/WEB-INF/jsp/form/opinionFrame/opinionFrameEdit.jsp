<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>表单与流程任务的绑定</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
			<div id="toolbar" class="toolbar"  align="left" style="width:100%; background-color:#CCEEFF;">
					<table>
						<tr>
							<td><a href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'icon-add',plain:true" id="button01" >增加意见框</a>
							</td>
							<td><a href="javascript:void(0);" class="easyui-linkbutton"
								data-options="iconCls:'icon-back',plain:true" id="back01" >返回</a>
							</td>
							<td>&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
			</div>
			<table id="list1"></table>
	</div>
	<div id="dialog-delete">
		<p>确定删除意见框?</p>
	</div>
	<div id="dialog-addAndEdit">
		<table>
			<tr>
				<td>readOnly:</td>
				<td>
					<select id="readOnly">
						<option value="NO">NO:读写</option>
						<option value="YES">YES:只读</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>opinionType:</td>
				<td>
					<select id="opinionType">
						<option value="1">1:个人意见</option>
						<option value="2">2:部门意见</option>
						<option value="3">3:领导意见</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>mainAndSub:</td>
				<td>
					<select id="mainAndSub">
						<option value="0">0:不是父子流程意见</option>
						<option value="1">1：当前意见为主流程意见</option>
						<option value="2">2：当前意见为子流程意见</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>opinionName:</td>
				<td><input id="opinionName"></td>
			</tr>
			<tr>
				<td>category:</td>
				<td><input id="category"></td>
			</tr>
			<tr>
				<td>guids:</td>
				<td><input id="guids" value=""></td>
			</tr>
		</table>
	</div>
</body>
<script type="text/javascript">
	var grid = $("#list1");
	var formId="${formId}";
	$(document).ready(function(){
		grid.datagrid({
			url : '${ctx}/opinionFrame/list?formId='+formId,
			fit : true,
			fitColumns : true,
			striped : true,
			singleSelect:true,
			rownumbers : true,
			pagination : true,//分页控件，要分页时设置为true
			pageSize : 30,
			border : true,
			nowrap : false,
			columns : [[{
				title : 'formId',
				field : 'formId',
				width : 30,
				align : "center",
				editable: true,
				hidden : true
			},{
				title : 'category',
				field : 'category',
				width : 30,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : 'opinionType',
				field : 'opinionType',
				width : 30,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : 'opinionName',
				field : 'opinionName',
				width : 30,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : 'guids',
				field : 'guids',
				width : 30,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : 'readOnly',
				field : 'readOnly',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : 'mainAndSub',
				field : 'mainAndSub',
				width : 40,
				align : "center",
				editable: true,
				hidden : false
			},{
				title : '操作',
				field : 'id',
				width : 240,
				fixed : true,
				align : "center",
				hidden : false,
				formatter : function(cellvalue, rowObject, rowIndex) {
					var s = '';
					s += '<a href="javascript:void(0);" onclick="opinionFrameDelete(\'' + rowObject.id + '\');">删除</a>';
					s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="opinionFrameEdit(\'' + rowObject.id +'\',\''+rowObject.formId+'\',\''+rowObject.category+'\',\''+rowObject.opinionType+'\',\''+rowObject.opinionName+'\',\''+rowObject.guids+'\',\''+rowObject.readOnly+'\',\''+rowObject.mainAndSub+ '\');">修改</a>';
					return s;
				}
			} ]]
		});
		
		$('#button01').click(function() {
			opinionFrameAdd();
		});
		
		$('#back01').click(function() {
			history.back();
		});
	});

	function opinionFrameDelete(id) {
		$( "#dialog-delete" ).dialog({
			title:"确定删除",
			resizable: false,
			width : 200,
			height:130,
			left:400,
			top:150,
			modal: true,
			buttons:[{
				text:'确定',
				handler:function() {
					$.getJSON('${ctx}/opinionFrame/delete?id=' + id, function(data, textStatus, jqXHR) {
						alert(data.msg);
						reloadgrid();
					});
					$("#dialog-delete").dialog( "close" );
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-delete").dialog( "close" );
				}
			}]
		});
	}

	function opinionFrameAdd() {
		clearSel();
		$( "#dialog-addAndEdit" ).dialog({
			title:"意见框属性设置",
			resizable: false,
			height:250,
			width:350,
			modal: true,
			buttons:[{
				text:'保存',
				handler:function() {
					 $.ajax({
			             type: "POST",
			             url:"${ctx}/opinionFrame/save",
			             dataType:'JSON',
			             data: {
			            	 formId:formId,
			            	 category:$("#category").val(),
			            	 opinionType:$("#opinionType").val(),
			            	 opinionName:$("#opinionName").val(),
			            	 guids:$("#guids").val(),
			            	 readOnly:$("#readOnly").val(),
			            	 mainAndSub:$("#mainAndSub").val()
			             },
			             success: function(data){
			            	 if(data.seccess){
			            		 $("#dialog-addAndEdit").dialog( "close" );
			            		 reloadgrid();
			            	 }
			            	 alert(data.msg);
			             }

			         });

					$("#dialog-addAndEdit").dialog( "close" );
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-addAndEdit").dialog( "close" );
				}
			}]
		});
	}

	function opinionFrameEdit(id,formId,category,opinionType,opinionName,guids,readOnly,mainAndSub) {
		 $("#category").val(category),
	   	 $("#guids").val(guids),
	   	 $("#opinionType").val(opinionType),
	   	 $("#opinionName").val(opinionName),
	   	 $("#readOnly").val(readOnly),
	   	 $("#mainAndSub").val(mainAndSub)
		
		$( "#dialog-addAndEdit" ).dialog({
			title:"意见框属性设置",
			resizable: false,
			height:250,
			width:350,
			left:450,
			top:100,
			modal: true,
			buttons:[{
				text:'保存',
				handler:function() {
					 $.ajax({
			             type: "POST",
			             url:"${ctx}/opinionFrame/update",
			             dataType:'JSON',
			             data: {
			            	 id:id,
			            	 formId:formId,
			            	 category:$("#category").val(),
			            	 opinionType:$("#opinionType").val(),
			            	 opinionName:$("#opinionName").val(),
			            	 guids:$("#guids").val(),
			            	 readOnly:$("#readOnly").val(),
			            	 mainAndSub:$("#mainAndSub").val()
			             },
			             success: function(data){
			            	 if(data.seccess){
			            		 $("#dialog-addAndEdit").dialog( "close" );
			            		 reloadgrid();
			            	 }
			            	 alert(data.msg);
			             }

			         });

					$("#dialog-addAndEdit").dialog( "close" );
				}
			},
			{
				text:'取消',
				handler: function() {
					$("#dialog-addAndEdit").dialog( "close" );
				}
			}]
		});
	}
	
	//重新加载jqgrid
	function reloadgrid()
	{
		grid.datagrid({url:'${ctx}/opinionFrame/list',queryParams:{
				'formId':formId
	 		}
		});
	}
	
	function clearSel(){
	   	 $("#category").val(""),
	   	 $("#opinionName").val(""),
	   	 $("#guids").val(""),
	   	 $("#opinionType").val("1"),
	   	 $("#readOnly").val("NO"),
	   	 $("#mainAndSub").val("0")
	}
</script>
</html>
