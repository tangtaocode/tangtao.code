<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/util.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>

<title>窗口人员信息</title>
<!--选取角色开始 -->
<script type="text/javascript" src="${ctx}/static/js/jquery.orgStructure.js"></script>
<!-- 选取角色结束 -->
<!-- 弹出人员选择框开始 -->
<script type="text/javascript" src="${ctx}/static/js/choiceperson.js"></script>
<!-- 弹出人员选择框结束-->
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/qtip2/jquery.qtip.min.css" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/risesoft/css/fileflow.css" />
<script type="text/javascript" src="${ctx}/static/jquery/qtip2/jquery.qtip.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.outerhtml.js"></script>
<script type="text/javascript" src="${ctx}/static/risesoft/js/activiti/workflow.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>

</head>
<body>
 	<div class="easyui-layout">
		<div>			
				<table align="center" class="tableStyle" formMode="line">

					<tr>
						<td>窗口人员姓名</td>				
 							<td><input id="name" readonly/><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="person" >选取</a></td>
 					</tr>
					<tr>
						<td>联系方式</td>
						<td><input id="phonenumber"/></td>
					</tr>
					 <tr>
						<td>人员所在大厅的工号</td>
						<td><input id="enrollnumber" type="text"/><span class="star">*</span></td>
					</tr>					
					<tr>
						<td>窗口所在大厅名称</td>
						<td>
							<%-- <select id="sel"  url="${ctx}/hallmanager/gethallname" prompt="请选择" onchange="onchangeHandler();"></select>	 --%>
							<input value="${windowhallname}" readonly="true" style="width:200"/>						
						</td>
					</tr>
					<tr>						
						<td>对应窗口名称</td>
						<td>							
 							<select id="entitywindowname" url='${ctx}/hallmanager/findwindowname' prompt="请选择" selWidth="200" onchange="findddevice();"></select>
							<span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>对应局和科室</td>
						<td>
							<input id="dept" style="width:200"/><span class="star">*</span>
						</td>
					</tr>
					<tr>
						<td>是否为窗口负责人:</td>
						<td><input type="radio" name="windowhead" value="1"/>是<input type="radio" name="windowhead" checked value="0"/>否</td>				
					</tr>
					<tr>
						<td>岗位:</td>
						<td><input /></td>				
					</tr>
					<tr>
						<td>职责:</td>
						<td><input /></td>				
					</tr>
					<tr>
						<td>所负责的业务:</td>
						<td><input /></td>				
					</tr>
					<tr>
						<td>窗口的工作时间</td>
						<td>上午<input type="text" class="date" name="time01" dateFmt="HH:mm:ss"/>至<input type="text" class="date" name="time02" dateFmt="HH:mm:ss"/><br/>
							下午<input type="text" class="date" name="time03" dateFmt="HH:mm:ss"/>至<input type="text" class="date" name="time04" dateFmt="HH:mm:ss"/>
						</td>																
					</tr>
					<tr style="height:100px">
						<th colspan="2" align="left"><input type="button" onclick="save();" value="保存"/></th>				
					</tr>
					<input type="hidden" id="personid"/>
				</table>				
		</div>
	</div>
	
</body>
<script type="text/javascript">	
	var ctx='${ctx}';
	/* function findguid(){
		//var windowhallname=$('#sel option:checked').text();
		var entitywindowname=$('#entitywindowname option:checked').text();
 		$.ajax({
			type:'POST',
			dataType:'json',
			data:{windowhallname:"${windowhallname}",entitywindowname:entitywindowname},
			url:'${ctx}/hallmanager/findguid',
			success:function(data){
				$('#windowguid').val(data.windowguid);
			}
		}) 
	}  */
function save(){
	//窗口人员姓名
 	 var name=$('#name').val();
	//人员所在大厅的工号
	var enrollnumber=$.trim($('#enrollnumber').val()); 
	//实体窗口名称
	var entitywindowname=$('#entitywindowname').attr("relText");
	//实体窗口guid
	var windowguid=$('#entitywindowname').attr("value");
    if(name==''||enrollnumber==''||entitywindowname==''){
    	Dialog.alert('请填写完整信息');
    }else{	
    	var employeeid=$("#personid").val();
    	var username=$('#username').val();
		//手机号码
		var phonenumber=$('#phonenumber').val();
		//窗口人员所在大厅和科室
		var dept=$('#dept').val();
		var radio=$('input:radio:checked').val(); 				
		//此处没有用form提交、用form提交的话、则人员选择框会消失
  		 $.ajax({
				type:'POST',
				dataType:'json',
				url:'${ctx}/hallmanager/personadd',
			    data:{employeeid:employeeid,name:name,phonenumber:phonenumber,enrollnumber:enrollnumber,windowhallname:"${windowhallname}",
			    	entitywindowname:entitywindowname,dept:dept,radio:radio,username:"${username}",usernameid:"${usernameid}",windowguid:windowguid,devicenumber:"${devicenumber}"},
			     success:function(data) {
						Dialog.alert("保存成功",function(){
							parent.location.reload();
							parent.Dialog.close();
							});
				}  
			}); 
    }  

}
</script>
</html>
