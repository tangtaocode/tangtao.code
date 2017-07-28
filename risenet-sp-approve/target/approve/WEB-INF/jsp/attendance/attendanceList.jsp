<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>

<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />

<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/msg.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div id="dialog-find" class="easyui-layout" data-options="fit:true,border:true">	
		<table id="simpleDgId" style="height:700px"></table>
		<div id="toolbar" class="toolbar" >
				<table border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#CCEEFF;">
					<tr>
				<td>日期:</td>				 
				<td><input type="text" id="startTime" name="startTime" class="easyui-datebox"></input>至<input type="text" id="endTime" name="endTime" class="easyui-datebox"></input></td>				
				<td>&nbsp;&nbsp;&nbsp;部门:</td>
				<td>
						 <c:if test="${ismainhall==0}">	
		  					<select  id="sel" selWidth="200" name="department">						 
								  	<option value="罗湖区行政服务大厅">罗湖区行政服务大厅</option>
									<option value="罗湖区民政局婚姻登记大厅">罗湖区民政局婚姻登记大厅</option>
									<option value="罗湖区财政局专业服务大厅">罗湖区财政局专业服务大厅</option>
									<option value="罗湖区人力资源局专业服务大厅">罗湖区人力资源局专业服务大厅</option>
									<option value="罗湖区卫生监督所专业服务大厅">罗湖区卫生监督所专业服务大厅</option>												
									<option value="桂园街道">桂园街道</option>
									<option value="黄贝街道">黄贝街道</option>
									<option value="东门街道">东门街道</option>						
									<option value="翠竹街道">翠竹街道</option>
									<option value="东晓街道">东晓街道</option>
									<option value="南湖街道">南湖街道</option>						
									<option value="笋岗街道">笋岗街道</option>
									<option value="东湖街道">东湖街道</option>
									<option value="莲塘街道">莲塘街道</option>						
									<option value="清水河街道">清水河街道</option>
							</select>	
						 </c:if>	
						<c:if test="${ismainhall==1}">
									<span>${departmentname}</span>	
						</c:if>	
						<!-- <select id="sel"></select> -->
					 	<input type="hidden"  id="hidedepart" value="${departmentname}"/>						
				</td>
				<td><button type="button" onclick="testconn('search');"><span class="icon_find">查询</span></button></td>
				<!-- <td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="exportPageData()"><span class="icon_export">导出当前页</span></button></td> -->
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="exportTotalData()"><span class="icon_export">导出所有页</span></button></td>		
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="add()"><span class="icon_add">新增考勤记录</span></button></td>									
			</tr>	
				</table>
			</div>
		
		
	</div>
</body>
<script type="text/javascript">
var grid = $('#simpleDgId');
var sortFlag = false;
var g;
$(function(){
	/* $.ajax({
		url:'${ctx}/zkAttendance/showindex',
		type:'POST',
		dataType:'json',
 		success:function(data){
 			for(var i=0;i<data.length;i++){
 				alert(data);
 				var value=data[i]; 				
 				 $("#sel").append("<option value="+value+">"+value+"</option>");				 
 			}; 			
		} 
	}); */
	size();
	testconn();
})
	function size(){
		winWidth = $(window).width();
		winHeight = $(window).height() - 40;
	}
	
	//测试连接
	function testconn(obj){
		var depart=$('#hidedepart').val();
     	if(depart==""){
		var department=$('#sel').val();
		};
		var startTime=$('#startTime').datebox('getValue');
		var endTime=$('#endTime').datebox('getValue');
		var department=$('#sel').val();
		if(endTime<startTime & endTime!=""){
			Dialog.alert("时间选择有误，请重新选择!");
			return;
		}
		$.ajax({
		url:'${ctx}/zkAttendance/testconn',
		data:{'department':department},
		type:'POST',
		dataType:'json',
 		success:function(data){
			if(data.message==""){
				g=grid.datagrid({  
				    url:"${ctx}/zkAttendance/find",
					queryParams:{
						  'department':department,
						  'val1':startTime,
						  'val2':endTime
					  },
					fit : true,
					toolbar : '#toolbar', 
					fitColumns : true,
					striped : true,
					remoteSort:false,
					rownumbers : true,
					pagination : true,
					singleSelect:true,
					checkbox:true,
					pageSize :20, 
					border : true,
					nowrap : false, 
				    columns:[[  
							  {field:"ORDERT",title:"日期",width:25,align:'center',sortable:true},
				              {field:"ENTITYWINDOWNAME",title:"窗口编号",width:45,align:'center'}, 
				              {field:"TYPE",title:"类型",width:25,align:'center',sortable:true},
				              {field:"WINDOWNAME",title:"姓名",width:25,align:'center',sortable:true},  
				              {field:"DEPARTMENTDESK",title:"所属部门和科室",width:25,align:'center'},
				              {field:"ENROLLNUMBER",title:"工号",width:25,align:'center',sortable:true},	              	               
				              {field:"TIMEAA",title:"打卡时间",width:25,align:'center',sortable:true}, 
				              {field:"ISLATE",title:"状态",width:25,align:'center',sortable:true,
				            	   formatter:function(cellvalue, rowObject, rowIndex){
				            		  if(cellvalue == "1"){
				            			  return "迟到";
				            		  }else{
				            			  return "";
				            		  }
				            	  } 
				              },
				              {field:"STATUSTYPE",title:"新增记录",width:25,align:'center',sortable:true}
				    ]],  
				    onLoadSuccess: function(){					    	
			  	    	$(this).datagrid("autoMergeCells",['ORDERT']); 
			  	    	disLoad();
				    }
				}); 				
			}else{
				disLoad();
				Dialog.alert(data.message);
			};
		},
		beforeSend:function(){
			if(obj=='search'){
				load();	
			}
			
		}
	})
}
	//生成加载层
	function load() {  
	      $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
	      $("<div class=\"datagrid-mask-msg\"></div>").html("正在加载，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
	  }  
	 //取消加载层  
	  function disLoad() {  
	      $(".datagrid-mask").remove();  
	      $(".datagrid-mask-msg").remove();  
	  } 
</script>
<script type="text/javascript">
$.extend($.fn.datagrid.methods,{  
    autoMergeCells : function (jq, fields){ 
        return jq.each(function (){  
            var target = $(this);  
            if (!fields){  
                fields = target.datagrid("getColumnFields");  
            }  
            var rows = target.datagrid("getRows");  
            var i = 0,  
            j = 0,  
            temp = {};  
            for (i; i < rows.length; i++){  
                var row = rows[i];  
                j = 0;  
                for (j;j<fields.length;j++){  
                    var field = fields[j];  
                    var tf = temp[field];  
                    if (!tf) {  
                        tf = temp[field] = {};  
                        tf[row[field]] = [i];  
                    } else {  
                        var tfv = tf[row[field]];  
                        if (tfv) {  
                            tfv.push(i);  
                        } else {  
                            tfv = tf[row[field]] = [i];  
                        }  
                    }  
                }  
            }  
            $.each(temp, function (field, colunm){  
                $.each(colunm, function (){  
                    var group = this;                        
                    if (group.length > 1){  
                        var before,  
                        after,  
                        megerIndex = group[0];  
                        for (var i = 0;i < group.length; i++){  
                            before = group[i];  
                            after = group[i + 1];  
                            if (after && (after - before) == 1){  
                                continue;  
                            }  
                            var rowspan = before - megerIndex + 1;  
                            if (rowspan > 1){  
                                target.datagrid('mergeCells',{  
                                    index : megerIndex,  
                                    field : field,  
                                    rowspan : rowspan  
                                });  
                            }  
                            if (after && (after - before) != 1){  
                                megerIndex = after;  
                            }  
                        }  
                    }  
                });  
            });  
        });  
    }  
});  

	//管理员可以修改员工的打卡记录(只能做添加记录)
	function add(){
		Dialog.open({URL:"${ctx}/zkAttendance/add",Width:400,Height:130})				
	} 
	
	//导出本页
	function exportPageData(){
		exportData(true);
	}
	//导出所有页
	function exportTotalData(){
		exportData(false);
	}
	//导出处理
	/* function exportData(isPage){
		var windowhallname=$('#sel option:selected').text();
		var starttime=$('#startTime').datebox('getValue');
		var endtime=$('#endTime').datebox('getValue');
        var url = "${ctx}/zkAttendance/excel?isPage="+isPage+"&windowhallname="+windowhallname+"&starttime="+starttime+"&endtime="+endtime;
        window.location = url;
	} */
	//测试
	function exportData(isPage){
		var windowhallname=$('#sel option:selected').text();
		var starttime=$('#startTime').datebox('getValue');
		var endtime=$('#endTime').datebox('getValue');
        var url = "${ctx}/zkAttendance/excel?isPage="+isPage+"&windowhallname="+windowhallname+"&starttime="+starttime+"&endtime="+endtime;
        window.location = url;
	}
</script>
</html>