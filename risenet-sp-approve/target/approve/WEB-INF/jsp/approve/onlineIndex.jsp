<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv=X-UA-Compatible content="IE=10">
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--箭头分页start-->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/pageArrow.js"></script>

<!--箭头分页end-->

<script type="text/javascript">
	    //定义远程数据
        var g;
	    var ret;
	    var ctx = "${ctx}";
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
							{ display: '流程id', name: 'WORKFLOWGUID', align: 'center', hide:true},
							{ display: '流程id', name: 'APPROVEITEMGUID', align: 'center', hide:true},
							{ display: '受理编号', name: 'DECLARESN', align: 'center', width: "10%"},
			                { display: '事项名称', name: 'FORMNAME', align: 'center', width: "20%",
								render: function (rowdata, rowindex, value, column){
								if(rowdata.HANDLESTATUS=='预受理'){
									ret= '<a href="javascript:void(0);" class="aColor" title="受理情况" onclick="gotoProcess(\'' + rowdata.GUID + '\',\'' + rowdata.WORKFLOWGUID + '\',\'' + rowdata.HANDLESTATUS + '\');">'+rowdata.FORMNAME+'</a>' ;
								}else{
									ret = '<a href="javascript:void(0);" class="aColor"  onclick="onMaterialInfo(\''+rowdata.APPROVEITEMGUID+'\',\'' + rowdata.GUID + '\',\'' + rowdata.WORKFLOWGUID + '\',\''+rowdata.DECLARESN+'\',\''+rowdata.USERID+'\',\''+rowdata.FORMNAME+'\');">'+rowdata.FORMNAME+'</a>' ;
								}
		                		    return ret;
								}
			                },
			                { display: '单位名称', name: 'WORKNAME', align: 'center', width: "10%"},
			                { display: '申请人', name: 'UNAME', align: 'center', width: "20%", isSort:false},
			                { display: '提交时间', name: 'SUBMITTIME',align: 'center', width: "10%"},
			                /* { display: '回复期限', name: 'REPLYTIME',align: 'center', width: "10%"},
			                { display: '回复状态', name: 'HFSTATE',   align: 'center', width: "9%",type:"hfstate"},//0未回复，1已回复 */
			                { display: '受理状态', name: 'HANDLESTATUS',   align: 'center', width: "15%" },
		                	{ display: '收件处理', isAllowHide: false, align: 'center', width:"15%",							 
			                	render: function (rowdata, rowindex, value, column){
			                			if(rowdata.HANDLESTATUS=='预受理'){
			                				ret= '<a href="javascript:void(0);" class="aColor" title="受理情况" onclick="gotoProcess(\'' + rowdata.GUID + '\',\'' + rowdata.WORKFLOWGUID + '\',\'' + rowdata.HANDLESTATUS + '\');">受理情况</a>' ;
			                			}else{
			                		    	ret= '<a href="javascript:void(0);" class="aColor" title="受理情况" onclick="onMaterialInfo(\''+rowdata.APPROVEITEMGUID+'\',\'' + rowdata.GUID + '\',\'' + rowdata.WORKFLOWGUID + '\',\'' + rowdata.DECLARESN + '\',\''+rowdata.USERID+'\',\''+rowdata.FORMNAME+'\');">受理情况</a>' ;
			                			}
			                		    return ret;
			                	}
                            }
		         ], 
		        url:"${ctx}/onlineApprove/approveList",rownumbers:true,pageSize:20,percentWidthMode:true,usePager:true,
		        height: '100%', width:"100%"
         	});
			
			 $("#searchPanel").bind("stateChange",function(e,state){
				g.resetHeight();
			});
			
		}
		
		 $.quiDefaults.Grid.formatters['hfstate'] = function (value, column) {
			    if(value==0){
			    	return "未回复";
			    }else{
			    	return "已回复";
			    }
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
		
		//材料清单页面
		function onMaterialInfo(approveitemguid,GUID,workflowguid,yxtywlsh,USERID,FORMNAME) {
			var url = ctx+'/onlineApprove/approveMaterial?instanceId='+GUID+'&workflowguid='+workflowguid+'&yxtywlsh='+yxtywlsh+'&userid=111&approveitemguid='+approveitemguid;
			window.parent.openTab(FORMNAME+"("+yxtywlsh+")",url);
		}
		//如果当前状态是预受理，则直接进入流程，避免有件丢失
		function gotoProcess(guid,workflowguid,status){
			window.location.href="${ctx}/onlineApprove/gotoProcess?instanceId="+guid+"&processDefinitionKey="+workflowguid+"&status="+status;
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
		<form action="${ctx}/onlineApprove/approveList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>事项名称：</td>
				<td><input type="text" name="approveName" style="width:200px"/></td>
				<td>回复状态:</td>
				<td>
					<select name="ishf">
						<option value=""></option>
						<option value="0">未回复</option>
						<option value="1">已回复</option>
						</select>
				</td>
				<td>受理状态:</td>
				<td>
					<select name="status">
						<option value=""></option>
						<option value="预受理">预受理</option>
						<option value="未受理">未受理</option>
						<option value="已受理">已受理</option>
						<option value="已办结">已办结</option>
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


</body>
</html>
