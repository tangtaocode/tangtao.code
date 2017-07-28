<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>人员管理首页</title>
<script type="text/javascript">
 	var g;
	function initComplete(){
		g=$('#maingrid').quiGrid({
			columns:[
					 {display:'人员guid',name:'employeeid',align:'center',width:'10%',showTitle:true,hide:true},
					 {display:'姓名',name:'windowname',align:'center',width:'10%',showTitle:true},
					 {display:'所属部门和科室',name:'departmentdesk',align:'center',width:'10%',showTitle:true},				 
			         {display:'联系方式',name:'phonenumber',align:'center',width:'10%',showTitle:true,
						 editor:{type:'text',tip:'只能填数字'}
			         },
			         {display:'窗口名称',name:'entitywindowname',align:'center',width:'10%',showTitle:true},
			         {display:'所在大厅名称',name:'windowhallname',align:'center',width:'10%',showTitle:true},
			         {display:'轮班时间',name:'',align:'center',width:'10%',showTitle:true},
			         {display:'是否为窗口负责人',name:'ISMANAGER',align:'center',width:'10%',showTitle:true,
			        			render:function(rowdata){
			        				if(rowdata.ismanager=='1'){
			        					return '是';
			        				}else{
			        					return '否';
			        				}
			        			}	 
			         }
			         ],
			url:"${ctx}/personmanager/personmanagerpage",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
			 pageSize:20,
			 enabledEdit:true,
 			 onBeforeSubmitEdit:onBeforeSubmitEdit,
			 onAfterEdit:onAfterEdit,
		     params:[{pageNo:"2"},{pageSize:"20"}],
		     height: '100%', width:"100%"
		})
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

	
    
  //编辑提交前事件 

    function onBeforeSubmitEdit(e){
     if(e.column.name=="phonenumber"){
 		if (!validateInput(e.value, "^0?[1][358][0-9]{9}$")){
             Dialog.alert("需要填写11位手机号！",null,null,null,2);
             return false;
         }
     }
    }
	
    //编辑后事件 

    function onAfterEdit(e){
      $.post("${ctx}/personmanager/editinformation",
                {"employeeid":e.record.employeeid,"value":e.value},
                function(result){
                	Dialog.alert(result.message);
                },"json"); 
    }


</script>
</head>
<body>
	<div class="box2">
		<form id="queryForm">
			<table>
				<tr>
					<td>窗口人员姓名</td>
					<td><input name="windowname"/></td>
					<td><button type="button" onclick="searchHandler();"><span class="icon_find">查询</span></button></td>					
				</tr>
				<tr>
					<td colspan="2" class="ali02"><span class="red">提示：点击'联系方式'下方的单元格来添加或修改手机号码。</span></td>					
				</tr>
			</table>
		</form>
	</div>
	<div  class="padding_right5">
		<div id="maingrid">
		</div>
	</div>
</body>
</html>