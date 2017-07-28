<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>区级部门统计详细信息</title>
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
var personidPage="${personid}districtDptDetail";	
</script>
</head>
<body>
	
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="" name="queryForm" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td nowrap="nowrap">评价日期：</td>
				<td nowrap="nowrap"><input type="text" name="senatedatetime_start" id="senatedatetime_start" class="date"/>
				--
				<input type="text" name="senatedatetime_end" id="senatedatetime_end" class="date"/>
				</td>
				<td nowrap="nowrap">受理编号：</td>
				<td>
				<input type="text" name="declaresn" id="declaresn"/>
				</td>
				<td nowrap="nowrap">窗口人员：</td>
				<td>
				<input type="text" name="employee_name" id="employee_name"/>
				</td>
				<td nowrap="nowrap">是否评价：</td>
				<td>
				<select name="issenate" id="issenate" prompt="请选择">
					<option value="">全部</option>
					<option value="1">是</option>
					<option value="0">否</option>
				</select>
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap">受理日期：</td>
				<td nowrap="nowrap"><input type="text" name="declaredatetime_start" id="declaredatetime_start" class="date"/>
				--
				<input type="text" name="declaredatetime_end" id="declaredatetime_end" class="date"/>
				</td>
				<td nowrap="nowrap">事项名称：</td>
				<td>
				<input type="text" name="approveitemname" id="approveitemname"/>
				</td>
				<td nowrap="nowrap">评价方式：</td>
				<td>
				<select name="senate_type" id="senate_type" prompt="请选择">
					<option value="">全部</option>
					<option value="pjq">评价器</option>
					<option value="3">网上大厅</option>
					<option value="4">手机短信</option>
				</select>
				</td>
				<td>&nbsp;</td>
				<td nowrap="nowrap">
				<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button>
				<button type="button" onclick="resetForm()"><span class="icon_find">重置</span></button>
				</td>
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
						{ display: '受理单位', name: 'BUREAUNAME', align: 'center', width: "10%"},
						{ display: '受理号', name: 'DECLARESN', align: 'center', width: "10%"},
						{ display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "20%"},
						{ display: '服务人员', name: 'EMPLOYEEDEPTNAME', align: 'center', width: "10%"},
						{ display: '受理时间', name: 'DECLAREDATETIME', align: 'center', width: "10%"},
						{ display: '受理评价情况', name: 'SATISFACTION_SL', align: 'center', width: "5%",
							render: function(rowdata, rowindex, value, column){
								var view ='';
								if(rowdata.SATISFACTION_SL=='1'){
									view='满意';
								}
								if(rowdata.SATISFACTION_SL=='2'){
									view='一般';
								}
								if(rowdata.SATISFACTION_SL=='3'){
									view='不满意';
								}
								return view;
			                }
						},
						{ display: '办结评价情况', name: 'SATISFACTION', align: 'center', width: "5%",
							render: function(rowdata, rowindex, value, column){
								var view ='';
								if(rowdata.SATISFACTION=='1'){
									view='满意';
								}
								if(rowdata.SATISFACTION=='2'){
									view='一般';
								}
								if(rowdata.SATISFACTION=='3'){
									view='不满意';
								}
								return view;
			                }
						},
						{ display: '受理评价时间', name: 'SENATEDATETIME_SL', align: 'center', width: "5%"},
						{ display: '办结评价时间', name: 'SENATEDATETIME', align: 'center', width: "5%"},
						{ display: '受理评价方式', name: 'TYPE_SL', align: 'center', width: "5%",
							render: function(rowdata, rowindex, value, column){
								var view ='评价器';
								if(rowdata.SATISFACTION_SL=='no'||rowdata.SATISFACTION_SL==''){
									view='';
								}
								if(rowdata.TYPE_SL=='3'){
									view='网上大厅';
								}
								if(rowdata.TYPE_SL=='4'){
									view='手机短信';
								}
								return view;
			                }
						},
						{ display: '办结评价方式', name: 'TYPE', align: 'center', width: "5%",
							render: function(rowdata, rowindex, value, column){
								var view ='评价器';
								if(rowdata.SATISFACTION=='no'||rowdata.SATISFACTION==''){
									view='';
								}
								if(rowdata.TYPE=='3'){
									view='网上大厅';
								}
								if(rowdata.TYPE=='4'){
									view='手机短信';
								}
								return view;
			                }
						},
						{ display: '业务来源', name: 'TYPE', align: 'center', width: "10%",
							render: function(rowdata, rowindex, value, column){
								var view ='无业务来源';
								if(rowdata.TYPE=='1'||rowdata.TYPE_SL=='1'){
									view='审批与服务平台';
								}
								if(rowdata.TYPE=='2'||rowdata.TYPE_SL=='2'){
									view='窗口咨询';
								}
								if(rowdata.TYPE=='5'||rowdata.TYPE_SL=='5'){
									view='无业务来源';
								}
								return view;
			                }}
		         ],
		        url:"${ctx}/senateManage/streetSubDetailList?bureauGuid=${param.bureauGuid}&type=${param.type}&resultType=${personid}districtDptDetail",rownumbers:true,percentWidthMode:true,checkbox:false,
		        height: '100%', width:"100%",pageSize:20,
		        toolbar:{
		            items:[
		                 {text: '导出为Excel', click: exportPageData, iconClass: 'icon_export'},
		                 { line : true },
		               ]
		           }
         	});
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
    
    function resetForm(){
    	document.queryForm.reset(); 
    }
  //导出为Excel
	function exportPageData(){
	  	window.location.href="${ctx}/senateManage/exportData?filename=区级部门评价详细统计表&resultType="+personidPage;
		out.clear();
		out = pageContext.pushBody();
	}
 
</script>	
</body>
</html>
