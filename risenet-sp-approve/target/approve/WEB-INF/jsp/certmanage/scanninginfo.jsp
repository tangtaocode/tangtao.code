<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>证照资料查询</title>


<script type="text/javascript">
	    //定义远程数据
        var g;
	    var ret;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
							{ display: '主键', name: 'GID', align: 'center', width: "10%",hide:true},
			                { display: '业务编号', name: 'SERVICECODE', align: 'center', width: "15%"},
			                { display: '业务类型', name: 'APPROVNAME', align: 'center', width: "18%"},
			               <%-- { display: '证件类型', name: 'DECLDATE',type:"date", align: 'center', width: "10%"},--%>
			                { display: '申请人姓名', name: 'NAME',align: 'center', width: "10%"},
			                { display: '证件号码', name: 'CODE',align: 'center', width: "10%"},
			                { display: '办结时间', name: 'CHENGNUORIQI',type:"date", align: 'center', width: "10%", isSort:true},
			                { display: '经办部门', name: 'DEPT',   align: 'center', width: "12%"},
			                { display: '证照有效期', name: 'VALIDITYPERIOD',type:"date", align: 'center', width: "10%", isSort:true},
		                	{ display: '操作', isAllowHide: false, align: 'center', width:"15%",							 
			                	render: function (rowdata, rowindex, value, column){
			                		    ret= '<a href="javascript:void(0);" onclick="onMaterialInfo(\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.APPROVNAME + '\',\'' + rowdata.NAME + '\',\'' + rowdata.PERSON + '\',\'' + rowdata.CHENGNUORIQI + '\',\'' + rowdata.DEPT + '\',\'' + rowdata.VALIDITYPERIOD + '\');">[查看影像]</a>' + '&nbsp;&nbsp;<a href="javascript:void(0);" 		  onclick="upscanning(\'' + rowdata.ID + '\',\'' + rowdata.SERVICECODE + '\',\'' + rowdata.APPROVNAME + '\',\'' + rowdata.NAME + '\',\'' + rowdata.CHENGNUORIQI + '\',\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.VALIDITYPERIOD + '\');">[补充扫描]</a> ';
			                		    return ret;
			                	}
                            },
		         ], 
		        url:"${ctx}/scanninghistory/select",rownumbers:true,pageSize:20,percentWidthMode:true,
		        height: '100%', width:"100%"
		        <%--toolbar:{
		            items:[
		                 {text: '导出当前页', click: exportPageData, iconClass: 'icon_export'},
		               ]

		           },--%>
         	});
			
		}
		//导出当前数据
		function exportPageData(){
			 exportData(true);
		}
		//导出全部数据
		function exportTotalData(){
			 exportData(false);
		}
		 //导出处理
	    function exportData(isPage){

	        var pageNo = g.options.page;

	        var pagerSize = g.options.pageSize;;

	        var sort = g.options.sortName;

	        var direction = g.options.sortOrder;

	        var url = "${ctx}/history/exportData";

	        if(isPage){
	            url += "&pager.pageSize=" + pagerSize;
	            url += "&pager.pageNo=" + pageNo;
	            url += "&sort=" + sort;
	            url += "&direction=" + direction;
	            url += "&isPage=1";
	        }else{
	            url += "&isPage=0";
	        }
	        window.location = url;

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
		
		//查看影像
		function onMaterialInfo(GUID,APPROVNAME,NAME,PERSON,CHENGNUORIQI,TEL,VALIDITYPERIOD) {
		
			window.location.href = "${ctx}/scanninghistory/getView?gid="+GUID+"&APPROVNAME="+APPROVNAME+"&NAME="+NAME+"&PERSON="+PERSON+"&CHENGNUORIQI="+CHENGNUORIQI+"&TEL="+TEL+"&VALIDITYPERIOD="+VALIDITYPERIOD;
		}
		
		//补充扫描
		function upscanning(id,code,name,unit,time,instanceguid,VALIDITYPERIOD){
			window.location.href = "${ctx}/scanning/upData?id="+id+"&code="+code+"&name="+name+"&unit="+unit+"&time="+time+"&instanceguid="+instanceguid+"&VALIDITYPERIOD="+VALIDITYPERIOD;
			 //var rows = grid.getSelectedRows();
			//console.info(a);
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
 
</script>	
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>业务编号：</td>
				<td><input type="text" name="ServiceCode"   style="width:140px;"/></td>
				<td>业务类型：</td>
				<td><input type="text" name="approveName"   style="width:140px;"/></td>
				<td>申请人姓名：</td>
				<td><input type="text" name="applyName"   style="width:140px;"/></td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>


</body>
</html>
