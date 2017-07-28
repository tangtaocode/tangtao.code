<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>新建库表</title>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',title:'新建库表',border:true">
			<div id="toolbar" class="toolbar" >
				<table style="width:auto;">
					<tr>
						<td>
							<a id="btn_add" href="javascript:void(0);"
							class="easyui-linkbutton"
							data-options="iconCls:'icon-add',plain:true"
							onclick="addrow();">新增字段</a>
							<a href="javascript:void(0);" 
							class="easyui-linkbutton"
							data-options="iconCls:'icon-save',plain:true"
							onclick="save();">保存修改</a>
							<!-- <a href="javascript:void(0);" 
							class="easyui-linkbutton"
							data-options="iconCls:'icon-save',plain:true"
							onclick="saveall();">保存所有</a> -->
							<a href="javascript:void(0);" 
							class="easyui-linkbutton"
							data-options="iconCls:'icon-cancel',plain:true"
							onclick="cancelall();">取消所有</a>
						</td>
					</tr>
				</table>
			</div>
			<table id="grid_addTable">
			</table>
		</div>
	</div>
</body>
<script>
	var grid = $('#grid_addTable');
	var editcount = 0;
	var deleteColumns = [];
	
	function editrow(index){
		grid.datagrid('beginEdit', index);
       }
	function deleterow(index, column_name_old){
           $.messager.confirm('确认','是否真的删除?',function(r){
               if (r){
               	if(column_name_old){
                       deleteColumns.push(column_name_old);
                   } 
                   grid.datagrid('deleteRow', index);
               }
           });
       }
       function saverow(index){
           grid.datagrid('endEdit', index);
       }
       function cancelrow(index){
           grid.datagrid('cancelEdit', index);
       }
       function addrow(){
           if (editcount > 0){
               $.messager.alert('警告','当前还有'+editcount+'记录正在编辑，不能增加记录。');
               return;
           }
           var index = grid.datagrid('appendRow',{}).datagrid('getRows').length-1;
           editrow(index);
       }
       function saveall(){
           grid.datagrid('acceptChanges');
       }
       function cancelall(){
           grid.datagrid('rejectChanges');
       }
       
       function save() {
       	saveall();
           var data = grid.datagrid('getRows');
           if(data.length==0){
               alert('表字段不能为空！');
           }else{            
           	var retVal = prompt("请输入表名称：");
               if(!retVal){
                   alert('表名称不能为空！');
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
               tableName = retVal;
               top.$.messager.confirm('确认', '您是否确认保存？', function(r) {
   				if (r) {
   					$.ajax({
   						type : 'POST',
   						dataType : 'json',
   						data : {
   							tableName: tableName,
                            	addColumns: $.toJSON(data),
   						},
   						url : "${ctx}/db/saveTable",
   						success : function(res) {
   							top.$.messager.show({
   								title : '提示',
   								msg : res.msg
   							});
   							if(res.success) {
   								parent.refresh();
   								parent.showTableDetail(tableName);
   							}
   						}
   					});
   				}
   			});
           }
       }
       
	$(function() {
		grid.datagrid({
			toolbar:'#toolbar',
			fit: true,
			singleSelect:true,
			fitColumns: true,
			striped: true,
			rownumbers: true,
			pagination: false,
			border: false,
			nowrap: false,  
			columns: [[
				{	field: 'column_name_old', title: '', hidden: true},
				{	field: 'column_name', title: '<b>英文名称</b>', width: 120, 
					editor:{
                           type:'validatebox',
                           options:{
                               required:'true'
                           }
                       }
				},
				{	field: 'comment', title: '<b>中文名称</b>', width: 120, editor:'validatebox'},
				{	field: 'data_type', title: '<b>JDBC类型</b>', width: 70, hidden: true},
				{	field: 'type_name', title: '<b>字段类型</b>', width: 70, 
					editor: {
		                type: 'combobox',
		                options: {
		                	data: $.parseJSON('${sqlTypes}'),
		                	valueField: 'id',  
		                	textField: 'text',
		                	required: true
		                }
		            }
				},
				{	field: 'data_length', title: '<b>字段长度</b>', width: 40,
					editor: {
		                type: 'numberbox',
		                options: {
		                    min: 0,
		                    precision: 0,
		                    required: true
		                }
		            }
				},
				{	field: 'data_precision', title: '<b>数字位数</b>', width: 40, hidden: true},
				{	field: 'data_scale', title: '<b>小数位数</b>', width: 40
					,editor:'numberbox'
				},
				{	field: 'isPrimaryKey', title: '<b>是否主键</b>', width: 40, 
					editor: {
						type: 'checkbox',
						options: {
							on: 1,
							off: 0
						}
					},
					formatter:function(value, row, index) {
						var checkbox;
						if(value==1) {
							checkbox = '<input type="checkbox" disabled="disabled" checked="checked"/>';
						}else {
							checkbox = '<input type="checkbox" disabled="disabled"/>';
						}
						return checkbox;
					}
				},
				{	field: 'isNull', title: '<b>是否可空</b>', width: 40, 
					editor: {
						type: 'checkbox',
						options: {
							on: 1,
							off: 0
						}
					},
					formatter:function(value, row, index) {
						var checkbox;
						if(value==1) {
							checkbox = '<input type="checkbox" disabled="disabled" checked="checked"/>';
						}else {
							checkbox = '<input type="checkbox" disabled="disabled"/>';
						}
						return checkbox;
					}
				},
				{field: 'primaryKey', hidden: true},
				{field: 'nullable', hidden: true},
				{	field: 'action', title: '<b>操作</b>', width: 70,
					formatter : function(value, row, index) {
						if (row.editing){
                               var s = '<a href="#" onclick="saverow('+index+')">保存</a> ';
                               var c = '<a href="#" onclick="cancelrow('+index+')">取消</a>';
                               return s+c;
                           } else {
                               var e = '<a href="#" onclick="editrow('+index+')">编辑</a> ';
                               var d = '<a href="#" onclick="deleterow('+index+', \''+row.column_name_old+'\')">删除</a>';
                               return e+d;
                           }
					}	
				}
	        ]],
	        onBeforeEdit:function(index,row){
                   row.editing = true;
                   grid.datagrid('refreshRow', index);
                   editcount++;
               },
               onAfterEdit:function(index,row){
                   row.editing = false;
                   grid.datagrid('refreshRow', index);
                   editcount--;
                   //针对easyui相关显示属性的处理
                   if(row.isPrimaryKey == 1) {
                   	row.primaryKey = true;
                   }else {
                   	row.primaryKey = false;
                   }
                   if(row.isNull == 1) {
                   	row.nullable = true;
                   }else {
                   	row.nullable = false;
                   }
               },
               onCancelEdit:function(index,row){
                   row.editing = false;
                   grid.datagrid('refreshRow', index);
                   editcount--;
               },
	        onDblClickRow :function(rowIndex, row){
	        	//grid.datagrid('beginEdit', rowIndex);
        	},
	        //隐藏grid的title右键菜单
			onHeaderContextMenu: function(e, field){
				var cmenu = $('<div/>').appendTo('body');
				cmenu.menu('hide');
			}
           });
	});
	
</script>
</html>
