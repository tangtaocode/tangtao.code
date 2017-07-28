<!--
	/**
 * @ FileName: ordersOnline.jsp
 * @Description: 管理人员“预约配置”弹窗
 * @autdor chenbingni
 * @date 2015-12-24
 */
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预约配置</title>

<!-- 表单异步提交start -->

<script src="${ctx}/static/QUI/libs/js/form/form.js"
	type="text/javascript"></script>

<!-- 表单异步提交end -->

<script type="text/javascript">

var g;
var ret;
function initComplete(){
	 g = $("#maingrid").quiGrid({
       columns: [
                 { display: '预约时间段', name: 'TYPE', align: 'center', width: "50%",
                	 render: function (rowdata, rowindex, value, column){
              		    if(rowdata.TYPE!=null&&'00'==rowdata.TYPE){
              		    	ret= '每小时' ;
              		    }else if('1'==rowdata.TYPE){
              		    	ret= '09:00~09:59'; 	                		
              		    } else if('2'==rowdata.TYPE){
            		    	ret= '10:00~10:59'; 	                		
            		    } else if('3'==rowdata.TYPE){
          		    	ret= '11:00~12:00'; 	                		
          		    } else if('4'==rowdata.TYPE){
          		    	ret= '14:00~14:59'; 	                		
          		    } else if('5'==rowdata.TYPE){
          		    	ret= '15:00~15:59'; 	                		
          		    } else if('6'==rowdata.TYPE){
          		    	ret= '16:00~16:59'; 	                		
          		    } else if('7'==rowdata.TYPE){
          		    	ret= '17:00~17:40'; 	                		
          		    }
              		    return ret;
              		    }
                 },
                 { display: '限定人数', name: 'LIMIT', align: 'center', width: "50%"}
                 ],
                 url:"${ctx}/orderOnline/searchManage?itemGuid=${itemGuid }",rownumbers:true,percentWidthMode:true,usePager:false,
                 height: '100%', width:"100%"
          	});	 
}

function addLimit() {
	var firstRow = g.getRow(0);

    var rowData={
    		itemGuid : '${itemGuid }',
    		type : $("#newType").val(),
    		limit : $("#newLimit").val()
    };
    
    g.addRow(rowData, firstRow, true);
    $.post("${ctx}/orderOnline/saveManage",rowData,function(result){

        if(result.id ==0 || result.id == ''){

            top.Dialog.alert(result.message);

      }

      g.getRow(0).userId = result.id;

      g.loadData();

      },"json");

}
	 
	 
</script>

</head>
<body>
	<form name="formManage" id="formManage">
		<input type="hidden" name="guid" id="guid" value="${itemGuid }" />
		<table class="tableStyle" formMode="line">
			<tr>
				<th colspan="2" class="ali03">${itemName }</th>
			</tr>
			<tr>
				<td colspan="2" class="ali03">
					<span class="staticTip" width="100%" style="font-size: 11px; ">温馨提示：如果开通了预约，没有填写可预约人数，则每小时默认可以预约<span style="color: red; font-weight: 900;">10</span>人！</span>
				</td>
			</tr>
			<tr>
				<td  class="ali03">
					<select name="newType" id="newType" prompt="请选择" boxWidth="200" boxHeight="200" selWidth="180">
						<option value="00">每小时</option>
						<option value="1">09:00~09:59</option>
						<option value="2">10:00~10:59</option>
						<option value="3">11:00~12:00</option>
						<option value="4">14:00~14:59</option>
						<option value="5">15:00~15:59</option>
						<option value="6">16:00~16:59</option>
						<option value="7">17:00~17:40</option>
					</select>
				</td>
				<td  class="ali03">
					可预约：<input type="text" name="newLimit" id="newLimit" inputMode="numberOnly"  watermark="限制输入正整数"></input>
					<button type="button" onclick="addLimit();"><span class="icon_add">点击新增</span></button>
				</td>
			</tr>
		</table>
	</form>
	<div class="padding_right5">
		<div id="maingrid"></div>
	</div>
</body>
</html>