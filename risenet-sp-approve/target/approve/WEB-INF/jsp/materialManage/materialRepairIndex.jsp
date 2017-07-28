<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资产维修</title>
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/datePicker/WdatePicker.js"></script>
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
	        { display: '主键', name: 'GUID',     align: 'center', width: "",hide:true},

	        { display: '物料编码', name: 'MATERIAL_CODE',     align: 'center', width: "10%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字符' }},

	        { display: '物料名称', name: 'MATERIAL_NAME',    align: 'center', width: "10%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
	        
	        { display: '维修数量', name: 'REPAIR_NUM',    align: 'center', width: "5%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
	        
			{ display: '维修费用', name: 'REPAIR_FUNDS',    align: 'center', width: "5%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
	        
	        { display: '维修方名称', name: 'REPAIR_PERSON',    align: 'center', width: "5%",editor: { type: 'text',maxlength:20}},
			
	        { display: '故障描述', name: 'REPAIR_REASON',    align: 'center', width: "20%",editor: { type: 'text',maxlength:20}},
			
	        { display: '维修开始时间', name: 'REPAIR_TIME_START',     align: 'center',  width:"10%",type:"date",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}} ,
	        
	        { display: '维修完成时间', name: 'REPAIR_TIME_DONE',     align: 'center',  width:"10%",type:"date",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}} ,
	        
	        { display: '负责人', name: 'CHARGE_PERSON',    align: 'center', width: "5%",editor: { type: 'text',maxlength:20}},
			
	        { display: '操作人', name: 'CREATE_PERSON',     align: 'center',  width:"5%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }} ,
	        
	        { display: '操作', align: 'center', width: "15%",showTitle:true,
            	render: function (rowdata, rowindex, value, column){
            		 var view = "";
	                    if (!rowdata._editing)
	                    {
	                    	view += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> "; 
	                    	view += "<a onclick='beginEdit(" + rowindex + ")'><span class='icon_edit'>修改</span></a> ";
	                    }
	                    else
	                    {
	                    	view += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> ";
	                    	view += "<a onclick='endEdit(" + rowindex + ")'><span class='icon_ok'>提交</span></a> ";
	                    	view += "<a onclick='cancelEdit(" + rowindex + ")'><span class='icon_no'>取消</span></a> "; 
	                    }
	                    return view;
            	}  	 
            }
	       ],

	        url:'${ctx}/materialManage/materialRepairList',sortName:'USING_TIME',rownumbers:true,percentWidthMode:true,checkbox:false,
	        height: '100%', width:"100%",pageSize:20,enabledEdit: true,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
                //g.beginEdit(rowindex);
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


	//删除	
function onDelete(rowidx){
	Dialog.confirm("确定要删除该记录吗？",function(){
	  	//删除记录
	  	var row = g.getRow(rowidx)
	  	$.post("${ctx}/materialManage/materialRepairDelete",{"guid":row.GUID},function(result){
	  		if (result.success=true) {
				Dialog.alert("删除成功！");
				
			} else {
				Dialog.alert("删除失败！");
				
			} 
	  		g.loadData();
		});
				
	});
}

//新增维修
function materialRepairAdd() {
	window.location.href = "${ctx}/materialManage/materialRepairAdd"; 
	//Dialog.open({ URL:"${ctx}/materialManage/materialRepairAdd",Title:"添加",Width:800,Height:350});
	/* var diag = new top.Dialog();alert(22);
    diag.Title = "手动关闭窗口并刷新页面";
    diag.URL = "${ctx}/materialManage/materialRepairAdd";
    diag.ShowButtonRow=true;
    diag.OkButtonText=" 确 定 ";
    diag.OKEvent = function(){
        top.Dialog.alert("成功提交",function(){window.location.reload()})
        diag.close();
    };
    diag.show(); */

}

function fanhui(){
	history.go(-1);
}
 //将row JSON对象转化为bo对象
 function rowToBO(row) {
    var params = '&guid='+row.GUID+'&repair_num='+row.REPAIR_NUM+'&repair_funds='+row.REPAIR_FUNDS
    +'&repair_person='+row.REPAIR_PERSON+'&repair_reason='+row.REPAIR_REASON
    +'&repair_time_start='+row.REPAIR_TIME_START+'&repair_time_done='+row.REPAIR_TIME_DONE
    +'&charge_person='+row.CHARGE_PERSON;
    return params;
} 


 //编辑提交前事件 
function onBeforeSubmitEdit(e){
	if(e.newdata.MATERIAL_CODE==""){
            Dialog.alert("物料编码不能为空！",null,null,null,20);
            return false;
    }else if (!validateInput(e.newdata.MATERIAL_CODE, "^[A-Za-z0-9]+$")){
            Dialog.alert("物料编码为数字或字母！",null,null,null,20);
            return false;
    }	
	if(e.newdata.MATERIAL_NAME==""){      
         Dialog.alert("物料名称不能为空！",null,null,null,20);
         return false;
    }else if (!validateInput(e.newdata.MATERIAL_NAME, "^[\u4e00-\u9fa5]+$")){
         Dialog.alert("物料名称需要是中文！",null,null,null,20);
         return false
    }
	if(e.newdata.REPAIR_NUM==""){      
        Dialog.alert("领用数量不能为空！",null,null,null,20);
        return false;
   }else if (!validateInput(e.newdata.REPAIR_NUM, "^[0-9]+$")){
        Dialog.alert("领用数量必须是数字！",null,null,null,20);
        return false
   }
	if(e.newdata.REPAIR_FUNDS==""){      
        Dialog.alert("维修费用不能为空！",null,null,null,20);
        return false;
    }else if (!validateInput(e.newdata.REPAIR_FUNDS, "^[0-9]+$")){
        Dialog.alert("维修费用必须是数字！",null,null,null,20);
        return false
    }
    if(e.newdata.REPAIR_PERSON==""){      
        Dialog.alert("故障描述不能为空！",null,null,null,20);
        return false;
    }
	if(e.newdata.CHARGE_PERSON==""){      
        Dialog.alert("负责人不能为空！",null,null,null,20);
        return false;
    }
 }
//编辑后事件 
function onAfterSubmitEdit(e) {
	 //在这里一律作修改处理
	
    var rowData = e.newdata;
	rowData.GUID = e.record.GUID;
    $.post('${ctx}/materialManage/materialRepairUpdata',rowToBO(rowData),function(result){         
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
//查询物料维护记录
function manageHistory(){
	Dialog.alert("物料维护记录");
}
</script>
</head>
<body>
<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="${ctx}/materialManage/materialList" id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>物料编号：</td>
				<td><input type="text" name="material_code"   style="width:140px;"/></td>
				<td>物料名称：</td>
				<td><input type="text" name="material_name"   style="width:140px;"/></td>
				
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="materialRepairAdd()"><span class="icon_add">添加维修记录</span></button></td>
			</tr>
		</table>
		</form>
		
	</div>
		<div class="padding_right5">
		<div id="dataBasic"></div>
    </div>

</body>
</html>