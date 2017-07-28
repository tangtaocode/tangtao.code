<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
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


<script type="text/javascript" src="${ctx}/static/js/xcConfirm.js"></script>
<link href="${ctx}/static/js/xcConfirm.css" rel="stylesheet" type="text/css"/>


<script type="text/javascript">
	    //定义远程数据
        var g;
	    var ret;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
		                 { display: '业务名称', name: 'APPROVNAME', align: 'center', width: "16%"},
			                { display: '受理日期', name: 'DECLDATE',type:"date", align: 'center', width: "10%"},
			                { display: '扫描日期', name: 'ADDDATE',type:"date", align: 'center', width: "10%", isSort:false},
			                { display: '申请人姓名', name: 'NAME',align: 'center', width: "10%"},
			                { display: '申请人证件号码', name: 'CODE',align: 'center', width: "11%"},
			                { display: '申请人联系电话', name: 'TEL',   align: 'center', width: "11%" },
			                { display: '证照有效期', name: 'ZZYXQ',   align: 'center', width: "10%"},
		                	{ display: '操作',  align: 'center', width:"10%",							 
			                	render: function (rowdata, rowindex, value, column){
			                		    ret= '<a href="javascript:void(0);" onclick="onMaterialInfo(\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.APPROVNAME + '\',\'' + rowdata.NAME + '\',\'' + rowdata.ADDDATE + '\',\'' + rowdata.TEL + '\');">[查看影像]</a>';
			                		    return ret;
			                	}
                         },
                         { display: '证照状态', isAllowHide: false, align: 'center', width: "12%" 
                         	,render: function (rowdata, rowindex, value, column){
		                		    var d = new Date();
	                		    	var str = d.getFullYear()+"/"+(d.getMonth()+1)+"/"+d.getDate();
	                		    	
	                		    	var time1 = Date.parse(str); 
	                	            var time2 = Date.parse(rowdata.ZZYXQ);
	                	            var dayCount = Math.round((Math.abs(time2 - time1))/1000/60/60/24); 
	                	            if(time2>time1&&dayCount>30){
	                	              return "有效期内"	;
	                	            }if(time2>time1&&dayCount<=30){
	                	              return dayCount+"天失效"+' <a href=\"javascript:void(0);\" onclick=\"messageYuJing(\'' + rowdata.INSTANCEGUID + '\',\'' + rowdata.TEL + '\',\'' + dayCount + '\',\'' + rowdata.NAME + '\',\'' + rowdata.CODE + '\');\">[发送短信]</a>';	
	                	            }else{
	                	              return "已失效"+dayCount+"天";
	                	            }
		                		} 
                         },
		         ], 
		        url:"${ctx}/scanninghistory/selectData",rownumbers:true,pageSize:20,percentWidthMode:true,
		        height: '100%', width:"100%"
		      <%--  toolbar:{
		            items:[
						 {text: '清空', click: clearWhere, iconClass: 'icon_delete'},
						 { line : true },
		                 {text: '导出当前页', click: exportPageData, iconClass: 'icon_export'},
		                 { line : true },
		                 {text: '导出全部',click: exportTotalData, iconClass: 'icon_export'},
		               ]

		           },--%>
         	});
			
		}
		

		
		//清空查询条件
		function clearWhere(){
			$("#queryForm")[0].reset();
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

	        var url = "${ctx}/scanninghistory/exportData";

	        if(isPage){
	            url += "?pager.pageSize=" + pagerSize;
	            url += "&pager.pageNo=" + pageNo;
	            url += "&sort=" + sort;
	            url += "&direction=" + direction;
	            url += "&isPage=1";
	        }else{
	            url += "?isPage=0";
	        }
	        $.post(url,{},function(result){
	    		Dialog.alert(result.info,function(){
	    		});
	     	 },"json"); 

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
		function onMaterialInfo(GUID,APPROVNAME,NAME,ADDDATE,TEL) {
			window.location.href = "${ctx}/scanninghistory/getView?gid="+GUID+"&APPROVNAME="+APPROVNAME+"&NAME="+NAME+"&ADDDATE="+ADDDATE+"&TEL="+TEL;
		}
		
		//短信预警
		function messageYuJing(GUID,TEL,dayCount,NAME,CODE){
			$.post('${ctx}/scanninghistory/messageYuJing',{"gid":GUID,"TEL":TEL,"dayCount":dayCount,"name":NAME,"code":CODE},function(result){
				if (result) {
					alert("发送成功!"+result);
				}
		    },"json"); 	
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
				<td>业务名称：</td>
				<td><input type="text" name="approveName"  /></td>
				<td>申请人姓名：</td>
				<td><input type="text" name="applyName"  /></td>
				<td>证照有效期：</td>
				<td><select name="yujing" id='sect'>
						  <option value=''>请选择证照有效期</option>
						  <option value='1'>未过期</option>
						  <option value='2'>已过期</option>
					</select></td>
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
