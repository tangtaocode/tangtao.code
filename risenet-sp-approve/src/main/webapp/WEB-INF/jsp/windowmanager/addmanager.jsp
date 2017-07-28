<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>添加大厅管理员</title>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.orgStructure.js"></script>
<!-- 弹出人员选择框开始 -->
<script type="text/javascript" src="${ctx}/static/js/choiceperson.js"></script>
<!-- 弹出人员选择框结束-->
<!-- 表单验证start -->
<script src="${ctx}/static/QUI/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="${ctx}/static/QUI/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->
</head>
<body>
	<div data-options="region:'center',title:'',border:true" >	
		<table align="center">
			<tr>
				<td width="45%" align="right">管理员姓名:</td>				
					 <td><input id="name" readonly/><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" id="person">选取</a></td>
					
			</tr>
			<tr>
				<td align="right">所属部门和科室</td>
				<td><input id="dept" class="validate[required]"/></td>
			</tr>
			<tr>
				<td align="right">请选择相应大厅</td>
				<td>
					<select id="sel" selWidth="200">
						 <option>--请选择--</option>
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
					</select><span class="star">*</span>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="2"><button onclick="commit();"><span class="icon_ok">保存</span></button></td>				
			</tr>
			<input type="hidden" id="personid"/>
		</table>
		</div>
	 <div class="padding_right5">
		<div id="maingrid"></div>
    </div> 

 <script type="text/javascript">
 var g;
	function initComplete(){
		g=$('#maingrid').quiGrid({
			columns:[
			         {display:'管理员姓名',name:'name',align:'center',width:'20%',showTitle:true},
			         {display:'个人guid',name:'employeeid',align:'center',width:'20%',showTitle:true,hide:true},
			         {display:'所属部门和科室',name:'departmentdesk',align:'center',width:'30%',showTitle:true},
			         {display:'大厅名称',name:'windowhallname',align:'center',width:'30%',showTitle:true},
			         {display:'操作',
			        	 	render:function(rowdata){
			        	 		ret='<a href="javascript:void(0);" class="img_delete hand" onclick="onDelete(\'' + rowdata.name + '\',\'' + rowdata.employeeid + '\');"></a>'
			        	 		return ret;
			        	 	}	
			         }
			         ],
			url:"${ctx}/hallmanager/managerindex",usePager:true,rownumbers:true,percentWidthMode:true,checkbox:true,
			 pageSize:20,
		     params:[{pageNo:"2"},{pageSize:"20"}],
		     height: '100%', width:"100%"
		})
	} 
</script>
<script type="text/javascript">	
var ctx="${ctx}";
	function commit(){
		var employeeid=$('#personid').val();
		var proposer=$('#name').val();
		var departmentdesk=$('#dept').val();
		var windowhallname=$('#sel').attr("relText");
		 if(proposer==''||departmentdesk==''||windowhallname==''){
			 Dialog.alert("请填写完整信息");
		 }else{
				$.ajax({
					type:'POST',
					dataType:'json',
					data:{proposer:proposer,departmentdesk:departmentdesk,windowhallname:windowhallname,employeeid:employeeid},
					url:'${ctx}/hallmanager/savemanager',
					success:function(date){
						Dialog.alert(date.message,function(){window.location.reload()});
							}
						})
		 }			
		} 
	
	//删除	
	function onDelete(name,employeeid){
		var row=g.getSelectedRows();
		var len=row.length;
		if(len<1){
			Dialog.alert("至少选择一条记录！|提示",null);
			return;
		}else if(len>1){
			Dialog.alert("只能选择一条记录!|提示",null);
			return;
		}else if(len==1){
			Dialog.confirm("确定要删除该记录吗？",function(){
				$.ajax({
					type:'POST',
					dataType:'json',
					url:'${ctx}/hallmanager/managerdelete',
					data:{name:name,employeeid:employeeid},
					success:function(data){
						Dialog.alert(data.message,window.location.reload());
					}
				})		
				});
		}			
	}
</script>
<script type="text/javascript">

</script>	 
</body>
</html>
