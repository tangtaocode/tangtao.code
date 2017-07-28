<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>统一收件</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<script type="text/javascript" src="${ctx}/static/risesoft/js/risenetDialog.js"></script>
 

<script type="text/javascript">
	
</script>
</head>
<body>
	
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/windowApprove/windowList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>事项名称：</td>
				<td><input type="text" name="approveItemName" style="width:200px"/></td>
				<td>状态:</td>
				<td>
					<select name="status">
						<option value=""></option>
						<option value="待处理">待处理</option>
						<option value="已分发">已分发</option>
						</select>
				</td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>

<script type="text/javascript">
	    //定义远程数据
	   
        var g;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
						{ display: '', name: 'APPROVEITEMGUID', align: 'center', width: "15%",hide:true},
						{ display: '申办流水号', name: 'SBLSH_SHORT', align: 'center', width: "10%"},
						{ display: '事项名称', name: 'SXMC', align: 'center', width: "25%",
							render: function (rowdata, rowindex, value, column){
								ret = '<a href="javascript:void(0);" class="aColor"  onclick="gotoProcess(\''+rowdata.SBLSH_SHORT+'\',\''+rowdata.SXMC+'\');">'+rowdata.SXMC+'</a>' ;
	                		    return ret;
							}	
						},
						{ display: '申请人名称', name: 'SQRMC',align: 'center', width: "20%"},
						{ display: '申办时间', name: 'SBSJ',align: 'center', width: "20%"},
						{ display: '受理时间', name: 'SLSJ',   align: 'center', width: "10%"},
						{ display: '操作', name: '', align: 'center', width: "10%",
							render: function (rowdata, rowindex, value, column){
								ret = '<a href="javascript:void(0);" class="aColor"  onclick="gotoProcess(\''+rowdata.SBLSH_SHORT+'\',\''+rowdata.SXMC+'\');">开始处理</a>' ;
	                		    return ret;
							}
						}
		         ], 
		        url:"${ctx}//windowTodo/windowToDoList",rownumbers:true,percentWidthMode:true,checkbox:false,
		        height: '100%', width:"100%"
         	});
		}
		
		
		//进入流程操作
		function gotoProcess(sblsh,name){
			window.location.href="${ctx}/windowTodo/newInstanceAction?sblsh="+sblsh+"&name="+name;
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
		
		//重置
		function resetHandler(){
		   //表单常规元素还原
		   $("#queryForm")[0].reset();
		   //下拉框还原
		   $("#queryForm").find("select").render();
		   //重新查询
		   searchHandler();
		}
		
	
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
</script>	
</body>
</html>
