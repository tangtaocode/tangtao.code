<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>表单模型编辑</title>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.loadJSON.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.min.js"></script>

</head>
<body>
	<div>
		<div>
			<form id="dataFormBind" action="" method="post">
				<table>
					<tr>
						<td>表单字段英文名：</td>
						<td><input type="text" name="fieldAlias" id="fieldAlias" value="${fieldAlias}"/></td>
					</tr>
					<tr>
						<td>表单字段中文名：</td>
						<td><input type="text" name="fieldName" id="fieldName" value="${fieldName}"/></td>
					</tr>
					
					<tr>
						<td>对应表名称：</td>
						<td><select id='tableName_select' name='tableName' style="width:150px"></select></td>
					</tr>
					
					<tr>
						<td>对应表字段名称：</td>
						<td><div id="columnNameId" style="width:150px"></div></td>
					</tr>
					
					<tr>
						<td>对应表字段类型：</td>
						<td><div id="columnTypeId" class='columnTypeId' style="width:146px"></div></td>
					</tr>
					
					<tr>
						<td>有无子表：</td>
						<td><select name="isSubTable" id="isSubTableId" onChange='isSubTable_function("");'>
									<option value="false">无</option>
									<option value="true">有</option>
							 </select>
						</td>
					</tr>
					
					<tr id="trTableAliasId">
						<td>
								子表对应对象名称：
						</td>
						
						<td>	<input type="text" name="tableAlias" id="tableAliasId" onblur="inspect();" value="${tableAlias}"/>
								<input type="hidden" name="formId" id="formId" value="${formId}"/>
								<input type="hidden" name="id" id="id" value="${id}"/>
						</td>
					</tr>
				
				</table>
					<div align="right"><button id="save">保存</button></div>
			</form>
		</div>
	</div>

</body>
<script type="text/javascript">
var id="${id}";
var fieldAliasTemp="${fieldAlias}";
var tableNameTemp="${tableName}";
var columnTypeTemp="${columnType}";
var columnNameTemp="${columnName}";
var isSubTableaValTemp="${isSubTable}";
$(document).ready(function() {
	if(id!=null&&id!=""){
		isSubTable_function(isSubTableaValTemp);//初始化是否显示对应子表名称字段 
		tableNameList(tableNameTemp,columnNameTemp);//加载表下拉菜单
	}else{
		isSubTable_function("");//初始化是否显示对应子表名称字段 
		tableNameList("","");//加载表下拉菜单 
	}
	
	$("#tableName_select").change(function(){ 
		var tableName = $("#tableName_select").val();  
		showColumn(tableName,""); 
	});
	
	$("#columnName_select").change(function(){ 
		alert("sss"); 
	});
	
	$('button').click(function(event) {
		switch (this.id) {
		case 'save':
			$("#dataFormBind").ajaxSubmit({
				type : 'POST',
				dataType : 'json',
				url : '${ctx}/form/dBTableBind/saveDateFormBind',
				success : function(responseText, statusText, xhr, $form) {
					alert(responseText.msg);
					//var index = parent.layer.getFrameIndex(window.name);
					//parent.layer.close(index);
					parent.grid.datagrid('reload');
					closeCurWindow(parent.frameID,'close');
				}
			});
			break;
		}
		return false;//避免提交两次。
	});
});

function tableNameList(tableNameTemp,columnNameTemp){
	$.getJSON('${ctx}/form/dBTableBind/listAllTableName?'+new Date(),  
			{},  
			function(data){  
				$("#tableName_select").append("<option selected>请选择</option>");  
				$.each(data,function(i,k){
						$("#tableName_select").append("<option value='"+k.tableName+"' style=\"width:200px\">"+k.tableName+"</option>");  
				});
				if(tableNameTemp!=""){
					$("#tableName_select").val(tableNameTemp);
					showColumn(tableNameTemp,columnNameTemp);
				}else{
					showColumn("","");
				}
				
					
	});  
}
function showColumn(tableNameTemp,columnNameTemp){
	if(tableNameTemp!=""){
		$.getJSON('${ctx}/form/dBTableBind/listAllColumn',  
	            {tableName:tableNameTemp},  
	            function(data){  
	                $("#columnNameId").html("<select style=\"width:115px\" id='columnName_select' name='columnName' onChange='columnName_select_function();'></select>");  
	                $("#columnName_select").html("<option  selected>请选择</option>");  
	                $.each(data,function(i,k){
	                    	if(columnNameTemp==k.columnName){
	                    		 $("#columnName_select").append("<option value='"+k.columnName+"'>"+k.columnName+"</option>");
	                    		 $("#columnName_select").val(columnNameTemp);
	                    		 showColumnType(tableNameTemp,columnNameTemp);
	                    	}else{
	                    		 $("#columnName_select").append("<option value='"+k.columnName+"'>"+k.columnName+"</option>");  
	                    	}
	                });
	                
	    });  
	}else{
		$("#columnNameId").html("<select style=\"width:115px\" id='columnName_select' name='columnName' onChange='columnName_select_function();'></select>");  
        $("#columnName_select").html("<option  selected>请选择</option>");
	}
}  
function showColumnType(tableNameTemp,columnNameTemp){
	
		$.getJSON('${ctx}/form/dBTableBind/listAllColumnType',
				{tableName:tableNameTemp,columnName:columnNameTemp}, 
				function(data){  
					if (data) {  
						$("#columnTypeId").html("");  
					}
					if(data.columnName==columnNameTemp){
						$("#columnTypeId").append("<input type=\"text\" name=\"columnType\" readOnly style=\"width:200px\" id=\"columnTypeId_input\" value=\""+data.columnType+"\"/>"); 
					}
		 });
	
}

function columnName_select_function() {
	var tableName = $("#tableName_select").find("option:selected").text();
	var columnName = $("#columnName_select").find("option:selected").text();
	showColumnType(tableName,columnName);  
}

//isSubTableaVal=重新编辑的时候选中的值	  isSubTableaValTemp=编辑的时候传进来的值
function isSubTable_function(isSubTableaValTemp){
	var isSubTableaVal=$('#isSubTableId option:selected').val();
	if(isSubTableaVal=="true"||isSubTableaValTemp=="true"){
		$("#isSubTableId").val("true");
		$('#trTableAliasId').show();
	}else{
		$("#isSubTableId").val("false");
		$("#tableAliasId").val("");//选择无的时候，把对应的子对象名称的值置空
		$('#trTableAliasId').hide();
	}
}

function inspect(){
	var tableAliasValue=$("#tableAliasId").val();
	var reg=/^[A-Za-z0-9]+$/;
	if(!reg.test(tableAliasValue)){
		alert("字段名称只能包含英语字母和阿拉伯数字");
	}
}

</script>
</html>