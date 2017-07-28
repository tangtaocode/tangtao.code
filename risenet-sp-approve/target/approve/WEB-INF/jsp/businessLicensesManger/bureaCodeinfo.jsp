<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META content="IE=11.0000" http-equiv="X-UA-Compatible">
<title>材料定义</title>
<!-- 树组件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/tree/ztree/ztree.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/static/QUI/libs/js/tree/ztree/ztree.css"></link>
<!-- 树组件end -->
<!-- 树形下拉框start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->

<script type="text/javascript">
var g_pageNo = 1;

var g_pageSize = 20;

//数据表格使用

  var g;

  var gridData;
  var jyData={"list":[{"value":"","key":""},{"value":"1","key":"是"},{"value":"0","key":"否"},]};
  var clData={"list":[{"value":"","key":""},{"value":"1","key":"有效"},{"value":"0","key":"无效"},]};
	
function initComplete(){
	g = $("#dataBasic").quiGrid({

	    columns:[
	        { display: '主键', name: 'BUREAGUID',     align: 'left', width: "",hide:true},

	        { display: '单位名称', name: 'BUREAUNAME',     align: 'left', width: "15%"},

	        { display: '组织机构代码', name: 'BUREACODE',    align: 'left', width: "16%",editor: { type: 'text',maxlength:40,tip:'不能超过40个字' }},

	        { display: '绑定R8部门', name: 'RC8_DEPARTMENT_ID',     align: 'center',  width:"16%",type:"text",
	        render: function (rowdata, rowindex, value, column){
    		      var st = "";
    		      var stuff = "";
                  if (rowdata.RC8_DEPARTMENT_NAME!=null&&rowdata.RC8_DEPARTMENT_NAME!="")
                  {
                      
                  	stuff= rowdata.RC8_DEPARTMENT_NAME;
                  }
                  st = "<input type='text' value='"+ stuff +"' size='15' maxlength='20' name='stuffdefine' id='"+ rowdata.BUREAGUID +"'/>"; 
                  st += "&nbsp;<a onclick='ondepttree(" + rowindex + ")'><span class='icon_add'>&nbsp;</span></a> ";
                  return st;
		  }
	        } ,
	        { display: '操作', align: 'center', width: "16%",showTitle:true,
            	render: function (rowdata, rowindex, value, column){
            		 var view = "";
	                    if (!rowdata._editing)
	                    {
	                    	//view += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> "; 
	                    	view += "<a onclick='beginEdit(" + rowindex + ")'><span class='icon_edit'>修改</span></a> ";
	                       
	                    }
	                    else
	                    {
	                    	//view += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> ";
	                    	view += "<a onclick='endEdit(" + rowindex + ")'><span class='icon_ok'>提交</span></a> ";
	                    	view += "<a onclick='cancelEdit(" + rowindex + ")'><span class='icon_no'>取消</span></a> "; 
	                       
	                    }
	                    return view;
            	}  	 
            }
	       ],

	        url:'${ctx}/bureaCode/bureaCodeList',sortName:'STUFFDEFINEID',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: true,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
                g.beginEdit(rowindex);
              }, onBeforeSubmitEdit: onBeforeSubmitEdit,onAfterSubmitEdit: onAfterSubmitEdit,
            /* toolbar:{
		       	 items:[
		       		  {text: '新增一行', click: addUnit,    iconClass: 'icon_add'},
		       		  { line : true },
		       		  {text: '整体提交', click: endAllEdit, iconClass: 'icon_save'},
		    		  { line : true },
		    		   ]
		        	} */
	});
}
//编辑
function beginEdit(rowid) { 
       g.beginEdit(rowid);
   }
//取消编
function cancelEdit(rowid) { 
    g.cancelEdit(rowid);
}
//结束编辑
function endEdit(rowid)
{
    g.endEdit(rowid);
}
//全部确认修改
function endAllEdit()
{
	 g.endEdit();
}

//关联rc8 DEPTID表 
function ondepttree(rowid){
	
	var gdata =JSON.stringify(g.getRow(rowid));
	var gdata1 =JSON.parse(gdata);
	var diag = new Dialog();
	diag.Title = "请选择需要关联的部门";
	diag.URL = "${ctx}/bureaCode/depttree?ID="+gdata1.BUREAGUID;
	diag.show();

  } 

	//删除	
function onDelete(rowidx){
	Dialog.confirm("确定要删除该记录吗？",function(){
	  	//删除记录
	  	var row = g.getRow(rowidx)
	  	$.post("${ctx}/bureaCode/delete",{"BUREAGUID":row.BUREAGUID},function(result){
	  		if (result.success==true) {
				Dialog.alert("删除成功！");
				
			} else {
				Dialog.alert("删除失败！");
				
			} 
	  		g.loadData();
		});
				
	});
}
/*	
//新增材料
function materialadd() {		
	window.location.href = "${ctx}/bureaCode/materialadd"; 
}
 function addUnit() {		
	var firstRow = g.getRow(0);
	var rowData={
			CODE:"",
			STUFFDEFINENAME:"",
			ISCHECKVALID:"1",
			TABINDEX:"",
			STATE:"1",
	}
	
	 g.addEditRow(rowData, firstRow, true);
	 //g.addRow(rowData, firstRow, true);
	 //在这里做新增处理
	$.post("${ctx}/materialshare/materialsave",rowToBO(rowData),function(result){
		  	if(result.STUFFDEFINEID ==0 || result.STUFFDEFINEID == ''){
		  		Dialog.alert(result.msg);
	    	}
	    	g.getRow(0).STUFFDEFINEID = result.STUFFDEFINEID;
	    	alert(result.STUFFDEFINEID);
      },"json"); 
} */
function fanhui(){
	history.go(-1);
}
 //将row JSON对象转化为bo对象
 function rowToBO(row) {
    var params = '&BUREACODE='+row.BUREACODE+'&RC8_DEPARTMENT_ID='+row.RC8_DEPARTMENT_ID+'&BUREAGUID='+row.BUREAGUID;
    return params;
} 


 //编辑提交前事件 
function onBeforeSubmitEdit(e){
	if(e.newdata.BUREACODE==""){
            Dialog.alert("组织机构代码不能为空！",null,null,null,40);
            return false;
    }else if (!validateInput(e.newdata.BUREACODE, "^[A-Za-z0-9]+$")){
            Dialog.alert("组织机构代码为数字或字母！",null,null,null,40);
            return false;
    }	
	/* if(e.newdata.STUFFDEFINENAME==""){      
         Dialog.alert("材料定义名称不能为空！",null,null,null,20);
         return false;
    }else if (!validateInput(e.newdata.STUFFDEFINENAME, "^[\u4e00-\u9fa5]+$")){
         Dialog.alert("材料定义名称需要是中文！",null,null,null,20);
         return false
    } */
 }
//编辑后事件 
function onAfterSubmitEdit(e) {
	 //在这里一律作修改处理
	
    var rowData = e.newdata;
	rowData.BUREAGUID = e.record.BUREAGUID;
    $.post('${ctx}/bureaCode/bureacodeupdata',rowToBO(rowData),function(result){         
          Dialog.alert(result.msg);
    },"json");
}
//删除后的提示

function handleResult(result){	   
     Dialog.alert("删除成功！");
     g.loadData();
}		
//刷新表格 表单提交的回调
function afterFormSubmit(){
    g.loadData();
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
</script>
</head>
<body>
<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/bureaCode/bureaCodeList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>单位名称：</td>
				<td><input type="text" name="BUREANAME"   style="width:140px;"/></td>
				<td>&nbsp;组织机构代码：</td>
				<td><input type="text" name="BUREACODE"   style="width:140px;"/></td>
				
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<!-- <td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="materialadd()"><span class="icon_add">新增</span></button></td> -->
			</tr>
		</table>
		</form>
		
	</div>
		<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>

</body>
</html>