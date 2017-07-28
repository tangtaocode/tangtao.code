<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>表单列表</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true,border:true">
		<div data-options="region:'center',title:'',border:true">
			<div id="toolbar" class="toolbar" align="left" style="width:100%; background-color:#CCEEFF;">
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#CCEEFF;">
					<tr>
						<td>
							<a href="javascript:void(0);" class="easyui-linkbutton"	data-options="iconCls:'icon-add',plain:true" id="addFormId">增加表单</a>
						</td>
						<td>
							&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</table>
			</div>
			<table id="list1"></table>
		</div>
	</div>
</body>
<script type="text/javascript">
	var grid = $("#list1");
	$(document).ready(function() {
		grid.datagrid({
			url : '${ctx}/form/def/getList',
			toolbar : '#toolbar',
			fit : true,
			fitColumns : true,
			singleSelect:true,
			striped : true,
			border : true,
			nowrap : false,
			columns : [[ {
				title : 'id',
				field : 'id',
				width : 90,
				align : "center",
				hidden : true
			}, {
				title : '表单代码',
				field : 'formCode',
				width : 90,
				align : "center"
			}, {
				title : '表单名称',
				field : 'formName',
				width : 60,
				align : "center"
			},{
				title : '版本',
				field : 'version',
				width : 30,
				align : "center",
				hidden : false
			}, {
				title : '更新时间',
				field : 'updateTime',
				width : 40,
				align : "center",
				hidden : false
			},  {
				title : '是否启用',
				field : 'enabled',
				width : 40,
				align : "center"
			},{
				title : '操作',
				field : 'opt',
				width : 300,
				fixed : true,
				align : "center",
				formatter : function(cellvalue, rowObject, rowIndex) {
	                var s = '<a href="javascript:void(0);" onclick="modelEdit(\'' + rowObject.id + '\');">表单字段绑定</a>';
	                s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="opinionFrame(\'' + rowObject.id + '\');">表单意见框管理</a>';
	                s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="formEdit(\'' + rowObject.id + '\');">修改</a>';
	                s += '&nbsp;|&nbsp;<a href="javascript:void(0);" onclick="formDelete(\'' + rowObject.id + '\');">删除</a>';
	                return s;
	            }
			} ]]
		});
	});
	
	//增加表单
	$('#addFormId').click(function() {
		formEdit('');
	});
	
    function formDelete(id) {
        $.getJSON('${ctx}/form/def/delete?id=' + id, function(data, textStatus, jqXHR) {
        	grid.datagrid('reload');
            alert(data.msg);
        });
    }

    var frameID = newGuid();
    function formEdit(id) {
    	openCurWindow({
			id : frameID,
			src : '${ctx}/form/def/edit?id=' + id,
			destroy : true,
			title : '表单基本信息',
			width : 650,
			height : 500,
			modal : true
    	});
    }
    
    function modelEdit(id) {
    	window.location.href = "${ctx}/form/dBTableBind/modelEdit?id=" + id;
    }
    
    function modelEditOld(id) {
        $.layer({
            type : 2,
            title : '表单数据模型Old',
            //shade : false,
            offset : [ '0px', '0px' ],
            closeBtn : [ 0, true ],
            bgcolor : '#fff',
            area : [ ($(window).width() - 10) + 'px', ($(window).height() - 10) + 'px' ],
            border : [ 5, 0.5, '#EF8016', true ],
            iframe : {
                src : '${ctx}/form/model/edit?id=' + id
            },
            end : function() {
                grid.datagrid('reload');
            }
        });
    }
    
    function opinionFrame(id){
    	window.location.href = "${ctx}/opinionFrame/forwarding?id=" + id;
    }
</script>
</html>
