<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审核批准角色绑定</title>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
<script type="text/javascript" src="${ctx}/static/auditRole/orgBureau.js"></script>

<!-- 表单验证start -->
<script src="${ctx}/static/QUI/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="${ctx}/static/QUI/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->
<!--布局控件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/layout.js"></script>
<!--布局控件end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--树形表格start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/treeTable.js"></script>
<!--树形表格end-->
<style>
.l-grid-hd-cell-inner span {
    font-family: SimSun;
    font-size: 12px;
    font-weight: bolder;
}

</style>

<script type="text/javascript">
	    //定义远程数据
        var g;
	    var ret;
	    var winWidth;
		var winHeight;
		function initComplete(){
			size();
			 g = $("#maingrid").quiGrid({
		       columns: [ 
			                { display: '事项ID', name: 'APPROVEITEMGUID',hide:true, align: 'center'},
			                { display: '事项名称', name: 'APPROVEITEMNAME', align: 'center', width: "25%"},
			                { display: '时限', name: 'TIMELIMIT', align: 'center', width: "25%"},
			                { display: '类别', name: 'APPROVEITEMTYPE', align: 'center', width: "25%"},
			                { display: '状态', name: 'APPROVEITEMSTATUS', align: 'center', width: "22%"}
		                	
		         ], 
		         url : '${ctx}/auditRole/getApproveItem',rownumbers:true,checkbox:true,pageSize:30,percentWidthMode:true,usePager:true,
		         pageSizeOptions:[10, 20, 30, 40, 50],height: '100%', width:"100%", frozen:false,columnWidth:1,
		         detail: { onShowDetail:showRole, height: 200,onExtend:refreshChild},
		         toolbar:{
		             items:[
		                   {text: '绑定人员',click:addRole,iconClass: 'icon_config'}
		                 ]
		             }
         	});
			
			 $("#searchPanel").bind("stateChange",function(e,state){
				g.resetHeight();
			}); 
			
		}
		
		function size() {
			winWidth = $(window).width();
			winHeight = $(window).height() - 40;
		}
		
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
</script>	
</head>
<body>
	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>
    <script type="text/javascript">
    	var ctx="${ctx}";
    	var type;
    	var APPROVEITEMGUID = "";
		var g1;//子表对象
		var gridArray=[];//子表对象数组
		
		//显示所选事项下的批准角色
	    function showRole(row, detailPanel,callback){
	    	APPROVEITEMGUID = row.APPROVEITEMGUID;
		    $.post('${ctx}/auditRole/getRoleList?approveItemGuid='+row.APPROVEITEMGUID,
			{parentId:row.APPROVEITEMGUID},
			function(result){
	             var childGrid = document.createElement('div'); 
	                $(detailPanel).append(childGrid);
	                g1 = $(childGrid).css('margin',10).quiGrid({
	                	  columns: [ 
	    		               		{ display: '人员id', name: 'PERSONGUID', hide:true,align: 'center'},
	    			                { display: '姓名', name: 'PERSONNAME', align: 'center', width: "10%"},
	    			                { display: '所属部门', name: 'BUREAUNAME', align: 'center',width: "10%"},
	    			                { display: '授权人', name: 'AUTHORNAME', align: 'center',width: "10%"},
	    			                { display: '委办局id', name: 'BUREAUGUID',hide:true, align: 'center'},
	    			                { display: '事项ID', name: 'APPROVEITEMGUID',hide:true, align: 'center'},
	    			                { display: '事项名称', name: 'APPROVEITEMNAME',hide:true,  align: 'center', width: "80%"},
	    			                { display: '时间', name: 'CREATETIME',hide:true, align: 'center'},
	    			                { display: '操作', name: 'taskAssignee',   align: 'center', width: "10%" ,
	    			                	render: function (rowdata, rowindex, value, column){
	    				                	var s = '';
	    									s += '<a href="javascript:void(0);" class="aColor" onclick="deleteRole(\'' + rowdata.PERSONGUID + '\',\'' + rowdata.APPROVEITEMGUID + '\');"><span class=\'icon_delete\'>删除</span></a>';
	    									return s;
	    								}
	    			                }
	    		                	
	    		         ], 
	    		         data: result, sortName: 'CREATETIME',
	    		         url:'${ctx}/auditRole/getRoleList?approveItemGuid='+row.APPROVEITEMGUID,
	                     rownumbers:true,checkbox:true,pageSize:30,percentWidthMode:true,usePager:true,
	    		         pageSizeOptions:[10, 20, 30, 40, 50],height: 210, width:"99%",columnWidth:1,
	    		         toolbar:{
	    		             items:[
	    		                   {text: '新增',click:function(){addPerson(row.APPROVEITEMGUID)},iconClass: 'icon_add'},
	    		                   { line : true },
	    		                   {text: '批量删除',click:function(){deletePerson(row.APPROVEITEMGUID)},iconClass: 'icon_delete'}
	    		                 ]
	    		             }
	                }); 
	                //将子表放入gridArray中
	                var obj={};
                    obj.id=row.APPROVEITEMGUID;
                    obj.grid=g1;
                    gridArray.push(obj);
		    })
	    }
	    
	    
	  	//获取当前子表
	    function getChild(gridId){
	  		var childGrid;
		    $.each(gridArray,function(idx,item){
	            if(item.id==gridId){
	            	childGrid = item.grid
	            }
	        })
	        return childGrid;
	    }
	  	
	  	//展开子表时刷新
	    function refreshChild(row, detailPanel,callback){
	    	var childGrid = getChild(row.APPROVEITEMGUID);
	    	childGrid.loadData(); 
	    }

	 	var personTreeOptions = {
					treeType:'tree_type_person',
					title:'选取人员',
					orgCheckSetting: {
						orgNoCheck:true,
						deptNoCheck:true,
						groupNoCheck:true,
						positionNoCheck:true,
						personNoCheck:false
				    },
				    selectedMulti:true,
					callback: {
						afterConfirm:function(checkedNodes,selectedNodes){
							if(checkedNodes.length>0){
								if(checkedNodes[0].orgType=="Person"){
									var personGuids = "";
									var personNames = "";
									for(var i = 0;i<checkedNodes.length;i++){
										if(i == 0){
											personGuids = checkedNodes[i].ID;
											personNames = checkedNodes[i].name;
										}else{
											personGuids = personGuids + "," + checkedNodes[i].ID;
											personNames = personNames + "," + checkedNodes[i].name;
										}
									}
									var approveItemGuids = "";
									if(type=="Role"){//单个或多个事项绑定人员
										 var obj = g.getCheckedRows();
										 for(var i = 0;i<obj.length;i++){
											 if(i==0){
												 approveItemGuids = obj[i].APPROVEITEMGUID; 
											 }else{
												 approveItemGuids = approveItemGuids + "," + obj[i].APPROVEITEMGUID;
											 }
										 }
									}else{
										approveItemGuids = APPROVEITEMGUID;
									}
									personNames = encodeURIComponent(personNames);//转码,IE下传到后台会乱码。
									 $.ajax({
									       async : false,  
									       cache : false,  
									       type: 'POST',
									       dataType:"json",
									       url: '${ctx}'+'/auditRole/saveRole?personGuids='+personGuids+'&personNames='+personNames+'&approveItemGuids='+approveItemGuids,
									       success:function(data){
									    	   if(data.success){
									    		   alert('保存成功！'); 
									    		   var childGrid = getChild(APPROVEITEMGUID);//获取当前子表
									    		   if(childGrid!=undefined&&childGrid!="childGrid"){
									    			   childGrid.loadData();  
									    		   }
									    	   }else{
									    		   alert('保存失败！');   
									    	   }
									       },
									       error: function () {
									           alert('保存失败！');  
									       }
									});
								}else{
									$.messager.alert('提示', "只能点选人员","info");
									return false;
								}
							}else{
								$.messager.alert('提示', "请勾选人员","info");
								return false;
							}
						}
					}
			};
	 	
		 	//点击弹出人员树，单个或多个事项绑定人员
			function addRole(){
		 		type = "Role";
				 var obj = g.getCheckedRows();
				 var personGuids = "";
				 if(obj.length==0){
					 alert("请勾选事项！")
				 }else{
					$.fn.extcloudTreeStructure(personTreeOptions);
				 }
			} 
	 	
			//点击弹出人员树，单个事项内新增人员
			function addPerson(approveItemGuid){
				type = "Person";
				APPROVEITEMGUID = approveItemGuid;
				$.fn.extcloudTreeStructure(personTreeOptions);
			} 
			
			 //单个删除人员
			function deleteRole(personGuid,approveItemGuid){
			 	 $.ajax({
				       async : false,  
				       cache : false,  
				       type: 'POST',
				       dataType:"json",
				       url: '${ctx}'+'/auditRole/deleteRole?personGuids='+personGuid+'&approveItemGuid='+approveItemGuid,
				       success:function(data){
				    	   if(data.success){
				    		   alert('删除成功！');  
				    		   var childGrid = getChild(approveItemGuid);//获取当前子表
				    		   childGrid.loadData(); 
				    	   }else{
				    		   alert('删除失败！');
				    	   }
				       },
				       error: function () {
				           alert('删除失败！');  
				       }
				}); 
			}
			 
			 //批量删除人员
			function deletePerson(approveItemGuid){
				 var childGrid = getChild(approveItemGuid);//获取当前子表
				 var obj = childGrid.getCheckedRows();
				 var personGuids = "";
				 var approveItemGuid = "";
				 if(obj.length==0){
					 alert("请选择一条记录！")
				 }else{
					 for(var i = 0;i<obj.length;i++){
						 approveItemGuid = obj[i].APPROVEITEMGUID; 
						 if(i==0){
							 personGuids = obj[i].PERSONGUID; 
						 }else{
							 personGuids = personGuids + "," + obj[i].PERSONGUID;
						 }
					 }
				 	 $.ajax({
					       async : false,  
					       cache : false,  
					       type: 'POST',
					       dataType:"json",
					       url: '${ctx}'+'/auditRole/deleteRole?personGuids='+personGuids+'&approveItemGuid='+approveItemGuid,
					       success:function(data){
					    	   if(data.success){
					    		   alert('删除成功！');  
					    		   childGrid.loadData();
					    	   }else{
					    		   alert('删除失败！');
					    	   }
					       },
					       error: function () {
					           alert('删除失败！');  
					       }
					}); 
				 }
			} 

			
	</script>

</body>
</html>
