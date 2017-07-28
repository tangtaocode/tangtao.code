<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
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
						{ display: '事项性质', name: 'APPROVEITEMTYPE', align: 'center', width: "10%"},
						{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "25%"},
						{ display: '收件处理', name: '',align: 'center', width: "25%",
							render: function(rowdata, rowindex, value, column){
			                	var str = '<input type="button" onclick="startOrg(\'' + rowdata.APPROVEITEMGUID + '\');" value="开始处理"/>&nbsp;&nbsp;'+
			                			'<input type="button" onclick="bjgzRemark(\'' + rowdata.APPROVEITEMGUID + '\');" value="一次性告知"/>';
			                	return str;
			                }

					    },
						{ display: '主管部门', name: 'BUREAUNAME',align: 'center', width: "20%"},
						{ display: '事项类型', name: 'BUREAUTYPE',   align: 'center', width: "10%"},
						{ display: '办理时限', name: 'TIMELIMIT', align: 'center', width: "10%"}
		         ], 
		        url:"${ctx}/windowApprove/windowList",rownumbers:true,percentWidthMode:true,checkbox:false,
		        height: '100%', width:"100%"
         	});
		}
		
		
		function startOrg(guid){
			Dialog.open({URL:"${ctx}/windowApprove/code?guid="+guid,Title:"填写信息",Width:500,Height:330});
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
		
		function bjgzRemark(itemid){
	    	var url = "${ctx}/bjgz/adviceRemark?approveitemguid="+itemid;
	      	Dialog.open({URL:url,Title:"一次性补交告知",Width:"800",Height:"500"});
	      	//openNewDialog(url,"一次性补交告知说明","800","500");
	    }
	function showGaozhi(itemGuid,typeGuid,orgCode){
		Dialog.close();
		openNewDialog("${ctx}/windowApprove/declareannexGaozhi?itemGuid='"+itemGuid+"'&orgCode='"+orgCode+"'&typeGuid="+typeGuid,"提交材料告知单","800","500");
	}
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
</script>	
</body>
</html>
