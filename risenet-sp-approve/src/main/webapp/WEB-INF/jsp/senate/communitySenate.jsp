<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>社区评价统计</title>
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
<!--根据父id获取子列表-->
<script type="text/javascript" src="${ctx}/static/risesoft/js/selectInfo.js"></script>
<style>
a{color:blue;}
</style>
<script type="text/javascript">
var personidPage="${personid}communitySenate";
var ctx ="${ctx}";
</script>
</head>
<body>
	
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>街道：</td> 
				<td>
				<select name="streetguid" id="streetguid" onchange="streetChange()" prompt="请选择" url="${ctx}/senateManage/getStreetList"></select>
				</td>
				<td>社区：</td>
				<td>
				<select name="bureauguid" id="bureauguid" prompt="请选择" url="${ctx}/senateManage/getCommunityList"></select>
				</td>
				<td>评价日期：</td>
				<td><input type="text" name="senatedatetime_start" id="senatedatetime_start" class="date"/>
				--
				<input type="text" name="senatedatetime_end" id="senatedatetime_end" class="date"/>
				</td>
				<td>受理日期：</td>
				<td><input type="text" name="declaredatetime_start" id="declaredatetime_start" class="date"/>
				--
				<input type="text" name="declaredatetime_end" id="declaredatetime_end" class="date"/>
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
						{ display: '', name: 'BUREAUGUID', align: 'center', hide:true},
						{ display: '街道名称', name: 'BUREAUNAME', align: 'center', hide:true},
						{ display: '社区名称', name: 'BUREAUNAME', align: 'center', width: "20%"},
						{ display: '业务总量', name: 'TOTAL', align: 'center', width: "10%",
							render: function(rowdata, rowindex, value, column){
								var view ='0';
								if(rowdata.TOTAL>0){
									view='<a href="javascript:void(0);" onclick="viewDetail(\'' + rowdata.BUREAUGUID + '\',\'ywzl\')">'+rowdata.TOTAL+'</a> ';
								}
								return view;
			                }
						},
						{ display: '评价总量', name: 'PJL',align: 'center', width: "10%",
							render: function(rowdata, rowindex, value, column){
								var view ='0';
								if(rowdata.PJL>0){
									view='<a href="javascript:void(0);" onclick="viewDetail(\'' + rowdata.BUREAUGUID + '\',\'pjzl\')">'+rowdata.PJL+'</a> ';
								}
								return view;
			                }
					    },
						{ display: '满意', name: 'BESTNUM',align: 'center', width: "10%",
							render: function(rowdata, rowindex, value, column){
								var view ='0';
								if(rowdata.BESTNUM>0){
									view='<a href="javascript:void(0);" onclick="viewDetail(\'' + rowdata.BUREAUGUID + '\',\'my\')">'+rowdata.BESTNUM+'</a> ';
								}
								return view;
			                }
						},
						{ display: '一般', name: 'GOODNUM',   align: 'center', width: "10%",
							render: function(rowdata, rowindex, value, column){
								var view ='0';
								if(rowdata.GOODNUM>0){
									view='<a href="javascript:void(0);" onclick="viewDetail(\'' + rowdata.BUREAUGUID + '\',\'yb\')">'+rowdata.GOODNUM+'</a> ';
								}
								return view;
			                }
						},
						{ display: '不满意', name: 'BADNUM',   align: 'center', width: "10%",
							render: function(rowdata, rowindex, value, column){
								var view ='0';
								if(rowdata.BADNUM>0){
									view='<a href="javascript:void(0);" onclick="viewDetail(\'' + rowdata.BUREAUGUID + '\',\'bmy\')">'+rowdata.BADNUM+'</a> ';
								}
								return view;
			                }
						},
						{ display: '评价率', name: 'PJLV',align: 'center', width: "10%"},
						{ display: '满意率', name: 'MYLV',   align: 'center', width: "10%"},
						{ display: '不满意率', name: 'BMYLV',   align: 'center', width: "10%" }
		         ], 
		        url:"${ctx}/senateManage/senateList?classify=community&resultType="+personidPage,rownumbers:true,percentWidthMode:true,checkbox:false,
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
 
    function viewDetail(guid,type){
    	Dialog.open({URL:"${ctx}/senateManage/districtDptDetail?bureauGuid="+guid+"&type="+type,Title:"评价详细信息列表",Width:1200,Height:800});
    }
    
  //导出为Excel
	function exportPageData(){
	  	window.location.href="${ctx}/senateManage/exportData?filename=社区评价统计表&resultType="+personidPage;
		//$.ajax({ url: "${ctx}/senateManage/exportData?filename=区级部门评价统计表&resultType="+personidPage, context: document.body, success: function(){
	      //}});
		out.clear();
		out = pageContext.pushBody();
	}
  
    function streetChange(){
    	var getUrl = ctx+"/senateManage/getCommunityList?parentId="+$("#streetguid").val();
	    loadSelectInfo(getUrl,$("#bureauguid"),function(){
	    	//回调函数
	    });
	}
</script>	
</body>
</html>
