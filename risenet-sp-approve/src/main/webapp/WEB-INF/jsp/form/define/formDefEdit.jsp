<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>form define</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/layout/layout-default-latest.css" />

<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/mstyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/listyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/form.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/style.css" />

<style>
.table_v {
	border: 2px solid #9DC3DA;
	width: 90%;
	margin-top: 10px;
	margin-bottom: 10px;
	height: 90%;
}

.table_v tr {
	height: 30px;
}

.table_v td {
	border-right: 1px #9DC3DA solid;
	padding: 7px;
	border-bottom: 1px #9DC3DA solid;
}

.table_v input select {
	height: 18px;
}
</style>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>

</head>
<body>
	<form id="myForm" method="post" action="${ctx}/form/def/save">
		<table cellspacing="0" cellpadding="0" align="center" border="0" class="table_v">
			<!-- <tr>
				<td align="center" colspan="4" style="font-size: 26px; font-weight: bold; padding: 20px; border-right: 0px;">表单基本信息</td>
			</tr> -->
			<tr>
				<td align="center">代码</td>
				<td><input type="text" style="width: 90%; height: 17px;" name="formCode" id="formCode" data-key="formCode" /></td>
				<td width="15%" align="center">名称</td>
				<td><input type="text" style="width: 90%; height: 17px;" name="formName" id="formName" data-key="formName" /></td>
			</tr>
			<tr>
				<td width="15%" align="center">版本</td>
				<td><input type="text" style="width: 90%; height: 17px;" name="version" id="version" data-key="version" /></td>
				<td width="15%" align="center">表名</td>
				<td><input type="text" style="width: 90%; height: 17px;" name="tableName" id="tableName" data-key="tableName" /></td>
			</tr>
			<tr>
				<td width="15%" align="center">序号</td>
				<td><input type="text" style="width: 90%; height: 17px;" name="tabindex" id="tabindex" data-key="tabindex" /></td>
				<td width="15%" align="center">启用</td>
				<td><select style="width: 91%; height: 23px;" name="enabled" id="enabled" data-key="enabled">
						<option value=true>启用</option>
						<option value=false>停用</option>
				</select></td>
			</tr>
			<tr>
				<td width="15%" align="center">源代码</td>
				<td colspan="3"><textarea rows="11" style="width: 96%;" name="htmlContent" id="htmlContent" data-key="htmlContent"></textarea></td>
			</tr>
			<tr>
				<td colspan="4" align="center"><button id="button01">保存</button>
					<button id="button02">取消</button></td>
			</tr>
		</table>
		<input type="hidden" name="id" id="id" value="${id}" />
	</form>
</body>
<script type="text/javascript">
    var dirtyElements = [];
    var formDefModel = null;

    function changeHandler(ev) {
        if ($.inArray(this.id, dirtyElements) == -1) {
            dirtyElements.push(this.id);
        }
    }

    /*var frameID = newGuid();
    function formDesign(id) {
    	openInTopWindow({
			id : frameID,
			src : '${ctx}/form/def/design?id=' + id,
			destroy : true,
			title : '设计表单',
			width : 1100,
			height : 600,
			modal : true,
    	});
    }*/
    
    //删除表单中的一个或多个form元素
    function delForm(){
    	var reg1 = /<form.*?>/g;
    	var reg2 = /<\/form.*?>/g;
    	var s=$('#htmlContent').val();
    	s=s.replace(reg1,"");
    	s=s.replace(reg2,"");
    	$('#htmlContent').val(s);
    }

    $(document).ready(function() {
       // var frameIndex = parent.layer.getFrameIndex(window.name);
        $.ajax({
		       async : false,  
		       cache : false,  
		       type: 'GET',
		       dataType:'JSON',
		       url:  "${ctx}/form/def/getFormDef?id=${id}",
		       error: function () {
		       },
		       success:function(data){
		    	   formDefModel = data;
		            var watchable = DataBind.bind($('#myForm'), formDefModel);
		            watchable.watch(changeHandler);
		       }
		});
       
        $('button').click(function(event) {
            switch (this.id) {
            case 'button01':
            	delForm();
            	$("#myForm").ajaxSubmit({
                    dataType : 'json',
                    /* error: function(xhr, status, error){
                        alert(status);
                    }, */
                    success : function(data) {
                        if (data.success == true) {
                        	parent.grid.datagrid('reload');
                        	closeCurWindow(parent.frameID,'close');
                           // parent.layer.close(frameIndex);
                        } else {
                            alert(data.msg);
                        }
                    }
                });
                break;
            case 'button02':
            	closeCurWindow(parent.frameID,'close');
               // parent.layer.close(frameIndex);
                //parent.layer.closeAll();
                break;
            }
            return false;//避免提交两次。
        });
    });
</script>
</html>
