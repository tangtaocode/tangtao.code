<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>材料定义</title>
<link href="${ctx}/static/risesoft/css/style.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript">
var g_pageNo = 1;

var g_pageSize = 20;

//数据表格使用

  var g;

  var gridData;
  var jyData={"list":[{"value":"1","key":"是"},{"value":"0","key":"否"}]};
  var clData={"list":[{"value":"1","key":"有效"},{"value":"0","key":"无效"}]};
	
function initComplete(){
	g = $("#dataBasic").quiGrid({

	    columns:[
	        { display: '主键', name: 'STUFFDEFINEID',     align: 'left', width: "",hide:true},

	        { display: '材料定义编码', name: 'CODE',     align: 'left', width: "15%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字符' }},

	        { display: '材料定义名称', name: 'STUFFDEFINENAME',    align: 'left', width: "16%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},

	        { display: '是否检验有效期', name: 'ISCHECKVALID', align: 'left', width: "9%",
		        editor: {
					type: 'select',data: jyData, selWidth: 100, openDirection: 'bottom', render: function(item) {
						for(var i=0; i<jyData["list"].lenght; i++ ) {
							if(jyData["list"][i]['value'] == item.ISCHECKVALID) {
								return jyData["list"][i]['key']
							}
						}
						return item.ISCHECKVALID;
					}
				},
            	render: function (rowdata, rowindex, value, column){
        		    if(rowdata.ISCHECKVALID!=null&&rowdata.ISCHECKVALID=='1'){
        		    return '是';
                		 	
        		    }else{
            		return '否';
        		    }
        		 }
	        },

	        { display: '排 序 号', name: 'TABINDEX',     align: 'center',  width:"14%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }} ,
	        { display: '材料状态', name: 'STATE',     align: 'center',  width:"14%",
	        	editor: {
					type: 'select',data: clData, selWidth: 100, openDirection: 'bottom', render: function(item) {
						for(var i=0; i<clData["list"].lenght; i++ ) {
							if(clData["list"][i]['value'] == item.STATE) {
								return clData["list"][i]['key'];
							}
						}
						return item.STATE;
					}
				},
            	render: function (rowdata, rowindex, value, column){
        		    if(rowdata.STATE!=null&&rowdata.STATE=='1'){
        		    return '有效'
                		 	
        		    }else{
            		return '无效'
        		    }
        		 }	
	        } ,
	        { display: '新增时间', name: 'DEFINETIME',     align: 'center',  width:"16%",type:"date",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}
	        } ,
	        { display: '操作', align: 'center', width: "16%",showTitle:true,
            	render: function (rowdata, rowindex, value, column){
            		 var view = "";
	                    if (!rowdata._editing)
	                    {
	                    	view += "<a onclick='onDelete(" + rowindex + ")' title='删除'><span class='icon_delete'>删除</span></a> "; 
	                    	view += "<a onclick='beginEdit(" + rowindex + ")' title='修改'><span class='icon_edit'>修改</span></a> ";
	                       
	                    }
	                    else
	                    {
	                    	view += "<a onclick='onDelete(" + rowindex + ")' title='删除'><span class='icon_delete'>删除</span></a> ";
	                    	view += "<a onclick='endEdit(" + rowindex + ")' title='提交'><span class='icon_ok'>提交</span></a> ";
	                    	view += "<a onclick='cancelEdit(" + rowindex + ")' title='取消'><span class='icon_no'>取消</span></a> "; 
	                       
	                    }
	                    return view;
            	}  	 
            }
	       ],

	        url:'${ctx}/materialshare/materialList',sortName:'STUFFDEFINEID',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: true,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
                g.beginEdit(rowindex);
              }, onBeforeSubmitEdit: onBeforeSubmitEdit,onAfterSubmitEdit: onAfterSubmitEdit
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


	//删除	
function onDelete(rowidx){
	Dialog.confirm("确定要删除该记录吗？",function(){
	  	//删除记录
	  	var row = g.getRow(rowidx);
	  	$.post("${ctx}/materialshare/delete",{"STUFFDEFINEID":row.STUFFDEFINEID},function(result){
	  		if (result.success=true) {
				Dialog.alert("删除成功！");
				
			} else {
				Dialog.alert("删除失败！");
				
			} 
	  		g.loadData();
		});
				
	});
}
	
//新增材料
function materialadd() {		
	window.location.href = "${ctx}/materialshare/materialadd"; 
}
/* function addUnit() {		
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
    var params = '&STUFFDEFINEID='+row.STUFFDEFINEID+'&CODE='+row.CODE+'&STUFFDEFINENAME='+row.STUFFDEFINENAME+'&ISCHECKVALID='+row.ISCHECKVALID+'&TABINDEX='+row.TABINDEX+'&STATE='+row.STATE+'&DEFINETIME='+row.DEFINETIME;
    return params;
} 


 //编辑提交前事件 
function onBeforeSubmitEdit(e){
	if(e.newdata.CODE==""){
            Dialog.alert("材料定义编码不能为空！",null,null,null,20);
            return false;
    }else if (!validateInput(e.newdata.code, "^[A-Za-z0-9]+$")){
            Dialog.alert("材料定义编码为数字或字母！",null,null,null,20);
            return false;
    }	
	if(e.newdata.STUFFDEFINENAME==""){      
         Dialog.alert("材料定义名称不能为空！",null,null,null,20);
         return false;
    }else if (!validateInput(e.newdata.STUFFDEFINENAME, "^[\u4e00-\u9fa5]+$")){
         Dialog.alert("材料定义名称需要是中文！",null,null,null,20);
         return false
    }
 }
//编辑后事件 
function onAfterSubmitEdit(e) {
	 //在这里一律作修改处理
	
    var rowData = e.newdata;
	rowData.STUFFDEFINEID = e.record.STUFFDEFINEID;
    $.post('${ctx}/materialshare/materialupdata',rowToBO(rowData),function(result){         
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
		<form action="${ctx}/materialshare/materialList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>材料编号：</td>
				<td><input type="text" name="CODE"   style="width:140px;"/></td>
				<td>材料名称：</td>
				<td><input type="text" name="STUFFDEFINENAME"   style="width:140px;"/></td>
				
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="materialadd()"><span class="icon_add">新增</span></button></td>
			</tr>
		</table>
		</form>
		
	</div>
		<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>

</body>
</html>