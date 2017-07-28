<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>服务大厅</title>
<script type="text/javascript">	
	//点击"编辑"按钮
	function searchHandler(department,ip,port){			
			Dialog.open({URL:"${ctx}/zkAttendance/deleteon?department="+department+"&ip="+ip+"&port="+port,Width:500,Height:330}); 	
			Dialog.close();			
			Dialog.open({URL:"${ctx}/zkAttendance/popup",Width:500,Height:330}); 				
	}
	
  	function deleteon(department,ip,port){
		Dialog.confirm("确定要删除？",
				function(){		
					Dialog.open({URL:"${ctx}/zkAttendance/deleteon?department="+department+"&ip="+ip+"&port="+port,Width:500,Height:330}); 	
					Dialog.close();
				},				
				function(){
					Dialog.close();
				});
	}  
  	
  	//点击"新增"按钮、弹出"IP和端口设置页面"
  	function newadd(){
  		Dialog.open({URL:"${ctx}/zkAttendance/popup",Width:500,Height:200})
  	}
	
	
/* 	 	function deleteon(department,ip,port){
			Dialog.confirm("确定要删除？",			
			function(){
				alert(1);
				$("#delete").ajax({
					type:'POST',
					dateType:'json',
					url:'${ctx}/zkAttendance/deleteon?department="+department+"&ip="+ip+"&port="+port,Width:500,Height:330',
					
				})
			}
			
			function(){
				Dialog.close();
			});
}  */
</script>
</head>
<body>
	<div class="box2" panelTitle="考勤机管理" id="searchPanel">
		<button type="button" onclick="newadd()"><span class="icon_add">新增</span></button>
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>

<script type="text/javascript">		
	    
        var g;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 						

						{ display: '大厅', name: 'department', align: 'center', width: "20%"},
						{ display: 'IP地址', name: 'ip', align: 'center', width: "20%"},
						{ display: '端口号', name: 'port',align: 'center', width: "10%" },
						{ display: '设备编号', name: 'devicenumber',align: 'center', width: "10%" },
						{ display: '是否为主厅', name: 'ismainhall',align: 'center', width: "20%" ,
							render:function(rowdata){
								if(rowdata.ismainhall==0){
									return "是";
								}else{
									return "否";
								}
							}	
						},
						 { display: '设备', isAllowHide: false, align: 'center', width:"20%",							 
		                	render: function (rowdata, rowindex, value, column){
	                		    	ret= '<a href="javascript:void(0);"  onclick="searchHandler(\'' + rowdata.department + '\',\'' + rowdata.ip + '\',\'' +rowdata.port+ '\');">编辑</a>' +
	                		    	' <a  id="delete" href="javascript:void(0);"  onclick="deleteon(\'' + rowdata.department + '\',\'' + rowdata.ip + '\',\'' +rowdata.port+ '\');">删除</a>' ;
		                		    return ret;
		                	}
                        } 
		         ], 
		        url:"${ctx}/zkAttendance/findMachines",rownumbers:true,percentWidthMode:true,checkbox:false,
		        height: '100%', width:"100%",
         	});
		}
</script>
</body>
</html>