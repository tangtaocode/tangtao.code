<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>文档编辑-用户选取</title>
<style>
body, td, th {
    color: #000000;
    font-family: 宋体;
    font-size: 12px;
	//font-weight: bold;
}
/*.tabs-header {
	 border-style: solid;
	border-width: 0px 0px 0;
	overflow: hidden;
	padding: 2px 0 0;
	position: relative;
}*/
/*.tabs-header, .tabs-tool {
    background-color: #FCFCFE;
}*/
/*.tabs-header, .tabs-scroller-left, .tabs-scroller-right, .tabs-tool, .tabs, .tabs-panels, .tabs li a.tabs-inner, .tabs li.tabs-selected a.tabs-inner, .tabs-header-bottom .tabs li.tabs-selected a.tabs-inner, .tabs-header-left .tabs li.tabs-selected a.tabs-inner, .tabs-header-right .tabs li.tabs-selected a.tabs-inner {
    border-color: #919B9C;
}
*/

#container { width:100%; padding:0; margin:0 auto; height:95%;}
#leftDiv { width:40%;float:left; text-align:center; display:block;/* background-color:#ccc; */margin-left:3%; color:#f00;}
#rightDiv { width:40%;float:right; text-align:center; display:block; /* background-color:#ccc; */ margin-right:3%;color:#fff;}
#centerDiv{margin:0 auto;width:10%;height:85%;}
.tabs-panels{height:70%;}
#tabdiv{width:100%;}
#rightTab{width:100%;}
#receiveObj{font-size:14px; color: #000000;font-family: 宋体;font-weight: bold;width:90%;}
#shoujianren{width:100%;height:370px;}
#banwenshuoming{width:100%;}
#duanxintixing{width:100%;}

</style>
</head>
<body>
	<div id="container">
		<div  align="center">
			<input type="hidden" id="processDefinitionId" value="${processDefinitionId}"></input>
			<input type="hidden" id="existPerson" value="${existPerson}"></input>
			<input type="hidden" id="existDepartment" value="${existDepartment}"></input>
			<input type="hidden" id="existGroup" value="${existGroup}"></input>
			<input type="hidden" id="existPosition" value="${existPosition}"></input>
			
		</div>
		<div align="left">
			<c:if test="${options!=null}">
				<table style="background-color: #EFEFEF; border: 1px dashed ThreedShadow; font: Menu; width:100%;">
					<tr>
						<td align="center">
							<input type="button" id="submit1" name="submit1" value=" 确 定 "
							style="border: 1px solid #669999; background-color: #FFFFFF; font: Menu" />
							&nbsp;&nbsp; 
							<input type="button" name="closeDlg" id="closeDlg" value=" 关 闭 " style="border: 1px solid #669999; background-color: #FFFFFF; font: Menu" />
						</td>
					</tr>
					<tr>
						<td align="left">
							${options}
						</td>
					</tr>
				</table>
			</c:if>
		</div>
		<br />
		<div id="leftDiv">
			<div id="tabdiv" class="easyui-tabs"></div>
		</div>

		<div id="rightDiv">
			<div id="rightTab" class="easyui-tabs">
				<div title="收件人" id="shoujianren" style="overflow-y:scroll;">
					<table id="receiveObj" class="aaa"></table>
				</div>
				<div title="短信提醒" id="duanxintixing" >
					 <fieldset style="margin-left:10px;width:90%;height:90%">
		                <legend>短信提醒</legend>
		                <table width="100%" border="0" style="font:Menu">
		                  <tr>
		                    <td></td>
		                    <td height="20"align="left"><input id="awoke" type="checkbox" name="awoke" value="true" checked/>
		                      <label for="awoke">短信提醒</label>
		                      <input id="awokeShuMing" type="checkbox" name="awokeShuMing" checked  onClick="ifAddShuMing()"/>
		                      <label for="awokeShuMing">是否添加署名</label>
		                    </td>
		                  </tr>
		                  <tr>
			                  <td></td>
			                  <td height="20"align="left">
			                  	<input readonly name="prefixSmsContext" id="prefixSmsContext" value="行政审批系统">
			                  </td>
			              </tr>
		                  <tr>
		                    <td>
		                    </td>
		                    <td height="20"align="left">
		                      <textarea id="awokeText" name="awokeText" rows="5" cols="30" >《${documentTitle}》请及时办理。</textarea>
		                      <textArea align="left" readonly id="lastfixSmsContext" name="lastfixSmsContext"  rows="2" cols="30">--${userName}</textarea>
		                    </td>
		                  </tr>
		                </table>
		               </fieldset>
				</div>
			</div>
		</div>
		<div id="centerDiv">
			<table style="width: 100%;height: 85%; ">
				<tr>
					<td style="height: 40%;width: 50%;" align="left" valign="bottom"><img
						id="addOperation" name="addOperation"
						src="${ctx}/static/images/arrow_right.gif" width="20px"
						 border="0px" alt="添加" /></td>
						 <td style="height: 40%;width: 50%;" align="right" valign="bottom"><img
							id="topOperation" name="topOperation"
							src="${ctx}/static/images/arrow_up.png" width="20px"
							 alt="向上" /></td>
				</tr>
				<tr>
					<td height="10px;"></td>
				</tr>
				<tr>
					<td style="height: 40%;width: 50%;" align="left" valign="top"><img
						id="removeOperation" name="removeOperation"
						src="${ctx}/static/images/arrow_left.gif" width="20px"
						border="0px" alt="移除" /></td>
						 <td  style="height: 40%;width: 50%;" align="right" valign="top"><img
							id="downOperation" name="downOperation"
							src="${ctx}/static/images/arrow_down.png" width="20px"
							 alt="向下" /></td>
				</tr>
			</table>
		</div>
	</div>
</body>
<script type="text/javascript">
	var sponsor="主办";
	var coSponsor="协办";
	var multiInstance="";
	var grid = $('#receiveObj');
	var tenantId='${tenantId}';
	var zTreeObj=null;
	var processDefinitionId = $("#processDefinitionId").val();
	var taskDefKey = $("input[name='routeToTasks']:checked").val();
	  
	$(function(){
		mydata= [];
		addTab();
		initDatagrid();
		getChecked();
	})
	
	//初始化选中的路由
	function getChecked(){
		multiInstance=$("input[name='routeToTasks']:checked").attr("owner");
		if(multiInstance == 'parallel'){
			$("#receiveObj").datagrid('hideColumn',"order");
			$("#receiveObj").datagrid('showColumn',"isSponsorStatus");
			$("#receiveObj").datagrid('autoSizeColumn','isSponsorStatus')
		}else{
			$("#receiveObj").datagrid('hideColumn',"isSponsorStatus");
			$("#receiveObj").datagrid('showColumn',"order");
			$("#receiveObj").datagrid('autoSizeColumn','order')
		}
	}
	var isdb;
	function initDatagrid(){
		$('#receiveObj').datagrid({
			singleSelect:true,
			fitColumns:true,
			data : mydata,
			onDblClickRow:function(rowIndex, rowData){
				isdb=true;
				deleteReceiveObj(rowData.principalGuid,rowIndex)
			},
			onSelect:function(rowIndex, rowData){  
				isdb=false;
	            setTimeout("onSelect();" , 300);  
			} ,
			columns : [ [{
					title : '类型',
					field : 'principalType',
					width : 80,
					align : "center",
					hidden : true
				},{
					title : '类型',
					field : 'principalTypeName',
					width : 120,
					align : "center",
					hidden : true,
					formatter : function(cellvalue, rowObject, rowIndex) {
						if (cellvalue == 1) {
							return "角色";
						}
						if (cellvalue == 2) {
							return "部门";
						}
						if (cellvalue == 3) {
							return "人员";
						}
						if (cellvalue == 4) {
							return "动态角色";
						}
						if (cellvalue == 5) {
							return "用户组";
						}
						if (cellvalue == 6){
							return "岗位";
						}
						return "未定义";
					}
				},{
					title : '主体Guid',
					field : 'principalGuid',
					width : 40,
					align : "center",
					hidden : true
				},{
					title : 'tId',
					field : 'tId',
					width : 40,
					align : "center",
					hidden : true
				}, {
					title : '名称',
					field : 'principalName',
					width : 120,
					align : "left",
					hidden : false
				},{
					title : '父节点Guid',
					field : 'parentGuid',
					width : 40,
					align : "center",
					hidden : true
				}, {
					title : '部门Guid',
					field : 'deptGuid',
					width : 40,
					align : "center",
					hidden : true
				}, {
					title : '办理顺序',
					field : 'order',
					width : 120,
					align : "center",
					hidden : true,
					formatter : function(cellvalue, rowObject, rowIndex) {
						var s=rowIndex + 1;
						return s;
					}
				},{
					title : '主协办',
					field : 'isSponsorStatus',
					width : 120,
					align : "center",
					hidden : true,				
					formatter : function(cellvalue, rowObject, rowIndex) {
						if(rowIndex == 0){
							var s=sponsor;
						}else{
							var s=coSponsor;
						}
						return cellvalue;
					}
				},{
					title : '操作',
					field : 'taskOpt',
					width : 30,
					hidden : true,	
					align : "center",
					formatter : function(cellvalue, rowObject, rowIndex) {
						var s = '<a href="javascript:void(0);" onclick="deleteReceiveObj(\'' + rowObject.principalGuid + '\',\''+rowIndex+'\');">删除</a>';
						return s;
					}
				}
			] ]
		});  
	}
	
	//选中设为主办
	 function onSelect() {  
	    if(isdb!=false)return;
		var rows = grid.datagrid('getSelected');
		var index = grid.datagrid('getRowIndex',rows);
		var data=grid.datagrid('getRows');
		for(var i = 0;i<data.length;i++){
			if(data[i].isSponsorStatus=="主办"){
				var index1 = grid.datagrid('getRowIndex',data[i]);
				grid.datagrid('updateRow',{index:index1,row:{isSponsorStatus:'协办'}});
			}
		}
		grid.datagrid('updateRow',{index:index,row:{isSponsorStatus:'主办'}});
	} 

	//添加页签
	function addTab(){
		var existPerson = $("#existPerson").val();
		var existDepartment = $("#existDepartment").val();
		var existGroup = $("#existGroup").val();
		var existPosition = $("#existPosition").val();
		if(existPerson=="true"){$('#tabdiv').tabs('add',{title:'人员'});};
		if(existDepartment=="true"){$('#tabdiv').tabs('add',{title:'部门'});};
		if(existGroup=="true"){$('#tabdiv').tabs('add',{title:'用户组'});};
		if(existPosition=="true"){$('#tabdiv').tabs('add',{title:'岗位'});};
		$('#tabdiv').tabs('select',0);
	}
	
	//删除所有页签
	function deleteAllTab(){
		var tab = $('#tabdiv').tabs('tabs');  // 获取选择的面板
		var tabLength=tab.length;
		for(var i=0;i<tabLength;i++){
			$('#tabdiv').tabs('close',0);
		}
	}
	
	//获取是否存在人员，部门，用户组，岗位，用来添加页签
	function getTabMap(processDefinitionId,taskDefKey){
		alert(processDefinitionId+taskDefKey)
		$.ajax({
			async:false, 
			cache:false,
            type: "GET",
            dataType: "json",
            url: ctx+"/objPerm/getTabMap",
            data: {processDefinitionId:processDefinitionId, taskDefKey:taskDefKey},
            success: function(data){
        		$("#existPerson").val(data.existPerson);
        		$("#existDepartment").val(data.existDepartment);
        		$("#existGroup").val(data.existGroup);
        		$("#existPosition").val(data.existPosition);
            }
		});
	}
	
	//初始化页面内容，并默认选中第一个
	$('#tabdiv').tabs({    
	    onSelect:function(){
	    	var tab = $('#tabdiv').tabs('getSelected');  // 获取选择的面板
	    	var title = tab.panel('options').title;//获取面板标题 
	    	var tabType='';
	    	if(title=="人员"){principalType=3;}
	    	if(title=="部门"){principalType=2;}
	    	if(title=="用户组"){principalType=5;}
	    	if(title=="岗位"){principalType=6;}
	    	$('#tabdiv').tabs('update', {
	    		tab: tab,
	    		options: {
	    			content: '<iframe id="iframeId'+principalType+'" name="iframeName'+principalType+'" scrolling="auto" frameborder="0"  src="${ctx}/document4Eform/choiceTab?principalType='+principalType+'" style="width:100%;height:380px;"></iframe>'  // 新内容的URL
	    		}
	    	});
	    }    
	});
	
	//路由改变时，获取对应路由的页面
	$("input[name='routeToTasks']").click(function(){
		var isSponsorStatusBoolean=false;
		taskDefKey = $("input[name='routeToTasks']:checked").val();
		multiInstance=$("input[name='routeToTasks']:checked").attr("owner");
		var rows=$("#receiveObj").datagrid('getRows');
		var index=rows.length;
		for(var i=0;i<index;i++){
			deleteReceiveObj(rows[0].principalGuid,0);
		}
		if(multiInstance == 'parallel'){
			$("#receiveObj").datagrid('hideColumn',"order");
			$("#receiveObj").datagrid('showColumn',"isSponsorStatus");
			$("#receiveObj").datagrid('autoSizeColumn','isSponsorStatus')
		}else{
			$("#receiveObj").datagrid('hideColumn',"isSponsorStatus");
			$("#receiveObj").datagrid('showColumn',"order");
			$("#receiveObj").datagrid('autoSizeColumn','order')
		}
	})
	
		//中间按钮向上移动
	$('#topOperation').click(function(){
		var selectedRow = $('#receiveObj').datagrid('getSelected');
		if(!selectedRow){
			return ;
		}
		var currentIndex = $('#receiveObj').datagrid('getRowIndex',selectedRow);
		if(currentIndex==0){
			return;
		}
		var allRows = $('#receiveObj').datagrid('getRows');
		var targetRow = allRows[currentIndex-1];
		//先交换序号tabIndex
		var tempIndex = selectedRow.tabIndex;
		selectedRow.tabIndex = targetRow.tabIndex;
		targetRow.tabIndex = tempIndex ;
		
		$('#receiveObj').datagrid('deleteRow',currentIndex);
		$('#receiveObj').datagrid('insertRow',{
												index:currentIndex-1,
												row:selectedRow
												}
								 );
		$('#receiveObj').datagrid('selectRow',currentIndex-1);
		$('#receiveObj').datagrid('refreshRow',currentIndex);
	});
	
	//中间按钮向下移动
	$('#downOperation').click(function(){
		var selectedRow = $('#receiveObj').datagrid('getSelected');
		if(!selectedRow){
			return ;
		}
		var currentIndex = $('#receiveObj').datagrid('getRowIndex',selectedRow);
		var allRows = $('#receiveObj').datagrid('getRows');
		if(currentIndex==(allRows.length-1)){
			return;
		}
		var targetRow = allRows[currentIndex+1];
		//先交换序号tabIndex
		var tempIndex = selectedRow.tabIndex;
		selectedRow.tabIndex = targetRow.tabIndex;
		targetRow.tabIndex = tempIndex ;
		
		$('#receiveObj').datagrid('deleteRow',currentIndex);
		$('#receiveObj').datagrid('insertRow',{
												index:currentIndex+1,
												row:selectedRow
												}
								 );
		$('#receiveObj').datagrid('selectRow',currentIndex+1);
		$('#receiveObj').datagrid('refreshRow',currentIndex);
	});
	
	
	//关闭人员选取界面
	$('#closeDlg').click(function(){
		closeCurWindow(parent.frameID,'close');
	});
	
	//中间按钮添加
	$('#addOperation').click(function(){
		addSelectedNodes();
	});
	
	//获取树上选取的节点
	function addSelectedNodes(){
		var tab = $('#tabdiv').tabs('getSelected');
		var title = tab.panel('options').title;//获取面板标题
		var child=null;
		if(title=="人员"){zTreeObj=window.frames["iframeName3"].zTreeObj;child=iframeName3;}
		if(title=="部门"){zTreeObj=window.frames["iframeName2"].zTreeObj;child=iframeName2;}
		if(title=="用户组"){zTreeObj=window.frames["iframeName5"].zTreeObj;child=iframeName5;}
		if(title=="岗位"){zTreeObj=window.frames["iframeName6"].zTreeObj;child=iframeName6;}
		var nodes = zTreeObj.getSelectedNodes();
		if(nodes.length>0){
			//for (var i=0; i<nodes.length; i++) {
				if((nodes[0].orgType=="Person")){//只选择人员
					child.window.addRow(nodes[i]);
				}
			//}
		}else{
			alert("未选择节点");
		}
	}
	
	//中间按钮的移除
	$('#removeOperation').click(function(){
		var rowObject=$("#receiveObj").datagrid('getSelected');
		var index=$("#receiveObj").datagrid('getRowIndex',rowObject);
		deleteReceiveObj(rowObject.principalGuid,index);
	});
	
	//删除jqgrid中选中的数据行
	function deleteReceiveObj(principalGuid,rowIndex){
		/*for(var i = 0;i<rows.length;i++){//删除主办之后，默认第一个为主办
			var index=$("#receiveObj").datagrid('getRowIndex',rows[i]);
			if(index==rowIndex&&rows[i].isSponsorStatus == "主办"){
				grid.datagrid('updateRow',{index:0,row:{isSponsorStatus:'主办'}});
				break;
			}
		}*/
		$("#receiveObj").datagrid('deleteRow',rowIndex);
		var rows = $('#receiveObj').datagrid("getRows");
		$('#receiveObj').datagrid("loadData", rows);

		var tab = $('#tabdiv').tabs('getSelected');
		var title = tab.panel('options').title;//获取面板标题
		if(title=="人员"){zTreeObj=window.frames["iframeName3"].zTreeObj;}
		if(title=="部门"){zTreeObj=window.frames["iframeName2"].zTreeObj;}
		if(title=="用户组"){zTreeObj=window.frames["iframeName5"].zTreeObj;}
		if(title=="岗位"){zTreeObj=window.frames["iframeName6"].zTreeObj;}
		var ztreeNode =zTreeObj.getNodes();
		var node = zTreeObj.getNodeByParam("id", principalGuid, null);
		if(node!=null){
			/*由于部门下的人员与部门下用户组、岗位下的人员可能相同，
			所以删除其中一个下面的人员，另一个有相同人员的也要改变颜色*/
			if((ztreeNode[0].orgType=="Organization"||ztreeNode[0].orgType=="Department")&&node.orgType=="Person"){
				for(var i=0;i<ztreeNode.length;i++){
					DeleteChildren(ztreeNode[i],principalGuid);
				}
			}
			var ParentNode = node.getParentNode();
			if(ParentNode!=null&&ParentNode.color=="blue"){
			}else{
				ChangeDeleteColor(node);					
			}							
		}
		if(rows.length>0){
			grid.datagrid('selectRow',0);//删除之后，默认选中第一个并设为主办
		}
		
		//主协办
	/*	if(multiInstance == 'parallel'){
			var ids=$("#list1").jqGrid('getDataIDs');
			if(ids.length>0){
				var currentRowData=$("#list1").jqGrid("getRowData",ids[0]);
				var principalGuid=currentRowData.principalGuid;
				$('#list1').setRowData( principalGuid, {isSponsorStatus:0 });
			}
		}*/
	}
	
	/*由于部门下的人员与部门下用户组、岗位下的人员可能相同，
	所以删除其中一个下面的人员，另一个有相同人员的也要改变颜色*/
	function DeleteChildren(ztreeNode,principalGuid){
		var children = ztreeNode.children ;
		if(children!=null && children!=undefined && children.length>0){
			for(var i=0;i<children.length;i++){
				if(children[i].orgType=="Person"&&children[i].id==principalGuid&&ztreeNode.color!="blue"){
					children[i].color="#000000";
					zTreeObj.updateNode(children[i]);
				}else{
					DeleteChildren(children[i],principalGuid);
				}
			}
		}
	}
	
	//删除后改变字体颜色
	function ChangeDeleteColor(treeNode){
		treeNode.color = "#000000" ;
		if(zTreeObj!=null){
			zTreeObj.updateNode(treeNode);	
		}
		var children = treeNode.children ;
		if(children!=null && children!=undefined && children.length>0){
			for(var i=0;i<children.length;i++){		
				var data=grid.datagrid('getRows');
				var str=false;
				for(var j=0;j<data.length;j++){
					if(children[i].id==data[j].principalGuid){
						str=true;
					}					
				}
				if(str==true){
				}else{
					children[i].color = "#000000" ;
					if(zTreeObj!=null){
						zTreeObj.updateNode(children[i]);
					}
					ChangeDeleteColor(children[i]);
				}					
			}
		}
	}
	
	$('#submit1').click(function() {
		var result1 = genUserChoice();
		var sponsorGuid = "";
		if (result1 == "") {
			alert("请选择用户");
			return;
		}
		var data = grid.datagrid('getRows');
		for(var i=0;i<data.length;i++){
			if(data[i].isSponsorStatus == "主办"){
				sponsorGuid = data[i].principalGuid;
			}
		}
		parent.end(result1,taskDefKey,sponsorGuid);
		closeCurWindow(parent.frameID,'close');
	});
	
	function genUserChoice(){
		var result="";
		var data = grid.datagrid('getRows');
		for(var i=0;i<data.length;i++){
			if(data[i].principalType==3){//人员
				if(result==""){
					//类型：ID：父ID
					result=data[i].principalType+":"+data[i].principalGuid+":"+data[i].parentGuid;
				}else{
					result=result+";"+data[i].principalType+":"+data[i].principalGuid+":"+data[i].parentGuid;
				}
			}else if(data[i].principalType==2){//部门
				if(result==""){
					result=data[i].principalType+":"+data[i].principalGuid;
				}else{
					result=result+";"+data[i].principalType+":"+data[i].principalGuid;
				}
			}else{//角色/用户组/动态用户组
				var temp=data[i].principalType;
				if(data[i].parentGuid=="rootGuid"){//如果父节点是最高节点，说明选取的是角色/用户组/动态用户组
					temp=temp+":"+data[i].principalGuid;
				}else{//如果不是角色/用户组/动态用户组，那么选取的可能是部门，也可能是人员
					if(data[i].deptGuid==""){//没有值，说明选取的是部门
						temp=temp+":"+data[i].parentGuid+":"+data[i].principalGuid;
					}else{
						temp=temp+":"+data[i].parentGuid+":"+data[i].principalGuid+":"+data[i].deptGuid;
					}
				}
				if(result==""){
					result=temp;
				}else{
					result=result+";"+temp;
				}
			}
		}
		return result;
	}
	
	  function ifAddShuMing(){
          var eleCheckBox = document.getElementById("awokeShuMing")
          var eleInput = document.getElementById("lastfixSmsContext")
          if(eleCheckBox.checked){
             eleInput.value="--${userName}";
             
          }else{
             eleInput.value="";
          }
       }
	
</script>
</html>