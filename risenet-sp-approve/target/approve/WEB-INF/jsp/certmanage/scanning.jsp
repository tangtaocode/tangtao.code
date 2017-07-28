<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>证照上传</title>


<script type="text/javascript">
//数据表格使用

  var g;

  var gridData;
 
function initComplete(){
	g = $("#dataBasic").quiGrid({

	    columns:[
	        { display: '主键', name: 'ID',     align: 'left', width: "",hide:true},

	        { display: '业 务 编 号', name: 'DECLARESN',     align: 'left', width: "15%"},

	        { display: '事 项 名 称', name: 'APPROVEITEMNAME',    align: 'left', width: "20%"},

	        { display: '申 请 人姓名', name: 'DECLARERPERSON', align: 'left', width: "14%"},

	        { display: '办 结 时 间', name: 'CHENGNUORIQI',     align: 'center',  width:"12%",type:"date"} ,
	        
	        { display: '证照有效期', name: 'VALIDITYPERIOD',     align: 'center',  width:"12%",type:"date"} ,
	        
	        { display: '预 警 时 间', name: 'WARN',     align: 'center',  width:"12%",type:"date",render: renderAbilityBar
	        } ,

	        { display: '操 作', isAllowHide: false, align: 'center', width:"13%",
	        	render: function (rowdata, rowindex, value, column){
        		    	ret= '<a href="javascript:void(0);" title="证 照 上 传" onclick="up(\'' + rowdata.ID + '\',\'' + rowdata.DECLARESN + '\',\'' + rowdata.APPROVEITEMNAME + '\',\'' + rowdata.DECLARERPERSON + '\',\'' + rowdata.CHENGNUORIQI + '\',\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.VALIDITYPERIOD + '\');">证 照 上 传</a>' ;
        		    return ret;
        			}

	            }

	      ],

	      url: '${ctx}/scanning/getData',  sortName: 'WORKFLOWINSTANCE_GUID',usePager: true,

	      rownumbers:true,checkbox:false,

	      height: '100%', width:"100%",pageSize:20,percentWidthMode:true,
	});
}

//预警时间
function renderAbilityBar(rowdata, rowindex, value, column){
	 var returnData;
	 //console.info(value);
	if(value==undefined || value==null || value=='null'){
		value="";
	}else{
		if(parseInt(value)<=0){
			var sb="已超过:"+value.substr(1,value.length)+"天         ";
		  returnData=sb+"<img src='${ctx}/static/images/light/redcard.jpg'/>";
		}else if(parseInt(value)<=5){
			var sb="剩余:"+value+"天         ";
		  returnData=sb+"<img src='${ctx}/static/images/light/yellowcard.jpg'/>";
		}else{
			var sb="剩余:"+value+"天         ";
			returnData=sb+"<img src='${ctx}/static/images/light/greencard.jpg'/>";
		}
	}
	   return returnData;

}

//保存后刷新
function refresh(){
	window.location.reload();
}

//查询
function searchHandler(){
	//得到查询参数
	var query = $("#queryForm").formToArray(); 
	//将查询参数传给grid表格
	g.setOptions({ params: query});
	//页号重置为1
	g.setNewPage(1);
	//重新加载数据
	g.loadData();
}
function up(id,code,name,unit,time,instanceguid,VALIDITYPERIOD){
	window.location.href = "${ctx}/scanning/upData?id="+id+"&code="+code+"&name="+name+"&unit="+unit+"&time="+time+"&instanceguid="+instanceguid+"&VALIDITYPERIOD="+VALIDITYPERIOD;
	 //var rows = grid.getSelectedRows();
	 //alert(VALIDITYPERIOD);
}
//重置
function resetHandler(){
   //表单常规元素还原
   $("#queryForm")[0].reset();
   //下拉框还原
   $("#queryForm").find("select").render();
   //重新查询
   searchHandler();
}



//删除
function onDelete(rowid,rowidx){
top.Dialog.confirm("确定要删除该记录吗？",function(){
  	top.Dialog.alert("向后台发送ajax请求来删除。见JAVA版或.NET版演示。");
});
}

//刷新表格 表单提交的回调
function afterFormSubmit(){
g.loadData();
}

//证照资料录入接口，事项办结出证需采集时需要提供instanceGuid，doctypeguid 这里带的测试数据
function scanFun(){
	Dialog.open({URL:"${ctx}/scanning/goscan?instanceGuid={09A16D3C-FFFF-FFFF-B15C-9E680000023D}&doctypeguid={0A150095-FFFF-FFFF-E362-43CE00000008}",Title:"证照资料录入",Width:900,Height:680});

}
</script>
</head>
<body>	
<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/scanning/getData" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>事项名称：</td>
				<td><input type="text" name="approveName"   style="width:140px;"/></td>
				<td>业务编号：</td>
				<td><input type="text" name="code"  style="width:140px;"/></td>
				<td>申请人姓名：</td>
				<td><input type="text" name="applyName"  style="width:140px;"/></td>
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				 <!-- <td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="scanFun();"><span class="icon_find">证照资料录入</span></button></td> -->
			</tr>
		</table>
		</form>
		
	</div>
		<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>
	<%--<div id="pageContent" style="height:35px;"></div>  --%>
</body>
</html>