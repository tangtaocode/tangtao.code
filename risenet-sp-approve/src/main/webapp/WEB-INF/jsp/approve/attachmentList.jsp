<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>材料清单</title>
<script type="text/javascript">
	var instanceId='${instanceId}';
	var declareGuid='${DECLAREANNEXGUID}';
	var msg = '${msg}';
	if(msg!=null && msg!=''){
		$("#errorMsg").html(msg);
	}
function initComplete(){
	 g = $("#maingrid").quiGrid({
      columns: [ 
                   { display: '附件名称', name: 'FILENAME', align: 'center', width: "60%",
                   		render: function (rowdata, rowindex, value, column){
	                		    var	ret= '<a href="${ctx}/wssbcl/download?id=' + rowdata.ID + '&instanceId='+instanceId+'&declareGuid='+declareGuid+'" class="aColor">' + rowdata.FILENAME + '</a>' ;
	                		    return ret;
	                		   }
					},
	               { display: '上传时间', name: 'UPDATETIME', align: 'center', width: "35%"}  
        ], 
       url:'${ctx}/onlineApprove/openFileList?instanceId='+instanceId+'&declareGuid='+declareGuid+'',usePager:false,
       groupColumnName: "MATERIALNAME", groupColumnDisplay: "材料名称",
       height: 'auto', width:"99%",fixedCellHeight:false,rownumbers:true
	});
}
</script>
</head>
<body>

    <div id="maingrid" style="margin-top:20px"></div>
	<span id="errorMsg" style="color:red">${msg}</span>


</body>
</html>