[#macro riseComment category  OpinionType  guids="" readOnly="" mainAndSub=""]
	<table id="opinionCotent-${category}" category=${category}  style="border: 0px; width: 100%">
	</table>
	<script type="text/javascript">
		var frameID = newGuid();
		$(document).ready(function() {
			buildComment_${category}();
		});
		
		function buildComment_${category}(){
			var opinionType="${OpinionType}";
			if(opinionType==1){
				$.ajax({
					   async:false, 
					   cache:false,
				       type:'get',
				       dataType:'JSON',         
				       url:'${ctx}/opinion/personCommentList',    
				       data:{ 
							processSerialNumber: "${processSerialNumber}",
							processInstanceId: $("#processInstanceId").val(),
							taskId: $("#taskId").val(),
							activitiUser: $("#activitiUser").val(),
							category: "${category}",
							itembox: "${itembox}",
							mainAndSub: "${mainAndSub}",
							readOnly: "${readOnly}"
						},    
				       	success:function(data){
				       		var str="";
				       		var addableStr="";
				       		$.each(data,function(i,item){
				       			if(i!=(data.length-1)){
					       			if(item.editable==true){
				       					if(item.isAgent==0){
				       						//alert("编辑个人意见");
				       						str+="<tr><td colspan='2' category=${category} id='"+item.id+"'>"+item.content+"<img title='编辑个人意见' onclick='openCommentForEditPersion(this);' style='cursor: pointer;' src='${ctx}/static/images/editconfig.gif'></td></tr><tr align='right'><td>"+item.userName+"&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}else{
				       						//alert("编辑代录意见");
				       						str+="<tr><td colspan='2' category=${category} id='"+item.id+"' agentUserId='"+item.agentUserId+"'   agentUserDeptId='"+item.agentUserDeptId+"'   agentUserName='"+item.agentUserName+"' opinionContent='"+item.content+"' >"+item.content+"<img title='编辑代录意见' onclick='openCommentForEditAgent(this);' style='cursor: pointer;' src='${ctx}/static/images/editconfig.gif'></td></tr><tr align='right'><td>"+item.agentUserName+"&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}
				       				}else{
				       					if(item.isAgent==0){
				       						//alert("只读个人意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.content+"</td></tr><tr align='right'><td>"+item.userName+"&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}else{
				       						//alert("只读个人意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.content+"</td></tr><tr align='right'><td>"+item.agentUserName+"&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}
				       				}
				       			}else{
				       				if(item.addable==true){
				       					//alert("新增个人意见、代录意见");
					       				addableStr="<tr><td colspan='2' align='left' category=${category} ><img title='新建个人意见、代录意见' onclick='addOnlyPO(this);' style='cursor: pointer;' src='${ctx}/static/images/writeComment0.gif'></td></tr>";
					       			}else{
					       				//alert("只能代录意见");
					       				if(item.addAgent==true){
					       					addableStr="<tr><td colspan='2' align='left' category=${category} ><img title='只新建代录意见' onclick='addOnlyAgent(this);' style='cursor: pointer;' src='${ctx}/static/images/writeComment0.gif'></td></tr>";
					       				}
					       			}
				       			}
				       		});
				       		$("#opinionCotent-${category}").html(str+addableStr);
				       }    
				});
			}else if(opinionType==2){
				$.ajax({
					   async:false, 
					   cache:false,
				       type:'get',
				       dataType:'JSON',         
				       url:'${ctx}/opinion/deptCommentList',    
				       data:{ 
							processSerialNumber: "${processSerialNumber}",
							processInstanceId: $("#processInstanceId").val(),
							taskId: $("#taskId").val(),
							activitiUser: $("#activitiUser").val(),
							category: "${category}",
							itembox: "${itembox}",
							mainAndSub: "${mainAndSub}",
							guids: "${guids}",
							readOnly: "${readOnly}"
						},
				       success:function(data){
				       		var str="";
				       		var addableStr="";
				       		$.each(data,function(i,item){
				       			//alert("i="+i);
				       			//alert("data.length="+data.length);
				       			if(i!=(data.length-1)){
					       			if(item.editable==true){
				       					if(item.isDepartmentOpinion==1){
				       						//alert("编辑科室意见");
				       						str+="<tr><td colspan='2' guids=${guids}  category=${category} id='"+item.id+"' opinionContent='"+item.content+"' >"+item.content+"<img title='编辑科室意见'   onclick='openCommentForEditDept(this);' style='cursor: pointer;' src='${ctx}/static/images/editconfig.gif'></td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.departmentName+"（"+item.userName+"）&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}else if(item.isDepartmentOpinion==2){
				       						//alert("编辑委办局意见");
				       						str+="<tr><td colspan='2'  guids=${guids} category=${category} id='"+item.id+"' opinionContent='"+item.content+"' >"+item.content+"<img title='编辑委办局意见' onclick='openCommentForEditDept(this);' style='cursor: pointer;' src='${ctx}/static/images/editconfig.gif'></td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.departmentName+"（"+item.userName+"）&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}
				       				}else{
				       					if(item.isDepartmentOpinion==1){
				       						//alert("只读科室意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.content+"</td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.departmentName+"（"+item.userName+"）&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}else if(item.isDepartmentOpinion==2){
				       						//alert("只读委办局意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.content+"</td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.departmentName+"（"+item.userName+"）&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       					}
				       				}
				       			}else{
				       				if(item.addable==true){
				       					//alert("新增部门意见");
					       				addableStr="<tr><td colspan='2' align='left' category=${category} guids=${guids}><img title='新建科室意见' onclick='addOnlyDO(this);' style='cursor: pointer;' src='${ctx}/static/images/writeComment0.gif'></td></tr>";
					       			}
				       			}
				       		});
				       		$("#opinionCotent-${category}").html(str+addableStr);
				       }    
				});
				
			}else if(opinionType==3){
				$.ajax({
					   async:false, 
					   cache:false, 
				       type:'get',
				       dataType:'JSON',        
				       url:'${ctx}/opinion/leaderCommentList',    
				       data:{ 
							processSerialNumber: "${processSerialNumber}",
							processInstanceId: $("#processInstanceId").val(),
							taskId: $("#taskId").val(),
							activitiUser: $("#activitiUser").val(),
							category: "${category}",
							itembox: "${itembox}",
							mainAndSub: "${mainAndSub}",
							opinionType: 3,
							readOnly: "${readOnly}"
						},    
				       success:function(data){
				       		var str="";
				       		var addableStr="";
				       		$.each(data,function(i,item){
				       			//alert("i="+i);
				       			//alert("data.length="+data.length);
				       			if(i!=(data.length-1)){
					       			if(item.editable==true){
					       				//alert("编辑领导意见");
				       					str+="<tr><td colspan='2' category=${category} id='"+item.id+"' opinionContent='"+item.content+"' >"+item.content+"<img title='编辑领导意见' onclick='openCommentForEditLeader(this);' style='cursor: pointer;' src='${ctx}/static/images/editconfig.gif'></td></tr><tr align='right'><td>"+item.userName+"&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       				}else{
				       					//alert("只读领导意见");
				       					str+="<tr><td colspan='2' >"+item.content+"</td></tr><tr align='right'><td>"+item.userName+"&nbsp;&nbsp;"+item.opinionDate+"</td></tr>";
				       				}
				       			}else{
				       			//alert("item.addable="+(item.addable==true));
				       				if(item.addable==true){
				       					//alert("新增领导意见意见");
					       				addableStr="<tr><td colspan='2' align='left' category=${category} ><img title='只能新建领导意见' onclick='addOnlyLeader(this);' style='cursor: pointer;' src='${ctx}/static/images/writeComment0.gif'></td></tr>";
					       			}
				       			}
				       		});
				       		$("#opinionCotent-${category}").html(str+addableStr);
				       }    
				});
			}
		}
		
		//只能新建个人意见
		function addOnlyPO(element){
			var processInstanceId=$('#processInstanceId').val();
			var taskId=$('#taskId').val();
			var obj=element.parentNode;
			var category=$(obj).attr('category');
			var opinionContent="";
			var AddEditPcDept="AddOnlyPO";
			openCurWindow({
				id : frameID,
		        src : '${ctx}/opinion/add?AddEditPcDept='+AddEditPcDept+'&opinionContent='+opinionContent+'&category='+category+'&taskId='+taskId+'&activitiUser=${activitiUser}&processSerialNumber=${processSerialNumber}'+'&processInstanceId='+processInstanceId+'&opinionType='+1,
				destroy : true,
				title : '新建个人意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
		
		//只能新建代录意见
		function addOnlyAgent(element){
			var processInstanceId=$('#processInstanceId').val();
			var taskId=$('#taskId').val();
			var obj=element.parentNode;
			var category=$(obj).attr('category');
			var opinionContent="";
			var AddEditPcDept="AddOnlyAgent";
			openCurWindow({
				id : frameID,
		        src : '${ctx}/opinion/add?AddEditPcDept='+AddEditPcDept+'&opinionContent='+opinionContent+'&category='+category+'&taskId='+taskId+'&activitiUser=${activitiUser}&processSerialNumber=${processSerialNumber}'+'&processInstanceId='+processInstanceId+'&opinionType='+1,
				destroy : true,
				title : '新建代录意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
		
		//只能新建领导意见
		function addOnlyLeader(element){
			var processInstanceId=$('#processInstanceId').val();
			var taskId=$('#taskId').val();
			var obj=element.parentNode;
			var category=$(obj).attr('category');
			var opinionContent="";
			var AddEditPcDept="addOnlyLeader";
			openCurWindow({
				id : frameID,
		        src : '${ctx}/opinion/add?AddEditPcDept='+AddEditPcDept+'&opinionContent='+opinionContent+'&category='+category+'&taskId='+taskId+'&activitiUser=${activitiUser}&processSerialNumber=${processSerialNumber}'+'&processInstanceId='+processInstanceId+'&opinionType='+3,
				destroy : true,
				title : '新建领导意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
		
		//新建科室、委办局意见
		function addOnlyDO(element){
			var processInstanceId=$('#processInstanceId').val();
			var obj=element.parentNode;
			var category=$(obj).attr('category');
			var guids=$(obj).attr('guids');
			var taskId=$('#taskId').val();
			var opinionContent="";
			var AddEditPcDept="AddOnlyDO";
			openCurWindow({
				id : frameID,
				src : '${ctx}/opinion/add?AddEditPcDept='+AddEditPcDept+'&opinionContent='+opinionContent+'&category='+category+'&taskId='+taskId+'&activitiUser=${activitiUser}&processSerialNumber=${processSerialNumber}'+'&processInstanceId='+processInstanceId+'&guids='+guids+'&opinionType='+2,
				destroy : true,
				title : '新建部门意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
		
		//只编辑个人意见
		function openCommentForEditPersion(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var AddEditPcDept="EditPersion";
			openCurWindow({
				id : frameID,
		        src : '${ctx}/opinion/edit?id='+id+'&category='+category+'&AddEditPcDept='+AddEditPcDept+'&activitiUser=${activitiUser}&taskId=${taskId}',
				destroy : true,
				title : '编辑个人意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
		
		//只编辑代录意见
		function openCommentForEditAgent(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var AddEditPcDept="EditAgent";
			openCurWindow({
				id : frameID,
		        src : '${ctx}/opinion/edit?id='+id+'&AddEditPcDept='+AddEditPcDept+'&category='+category+'&taskId=${taskId}&activitiUser=${activitiUser}',
				destroy : true,
				title : '编辑代录意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
		
		//只编辑领导意见
		function openCommentForEditLeader(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var AddEditPcDept="editLeader";
			openCurWindow({
				id : frameID,
		        src : '${ctx}/opinion/edit?id='+id+'&AddEditPcDept='+AddEditPcDept+'&category='+category+'&activitiUser=${activitiUser}&taskId=${taskId}',
				destroy : true,
				title : '编辑领导意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
		
		//编辑科室、委办局意见
		function openCommentForEditDept(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var AddEditPcDept="EditDept";
			openCurWindow({
				id : frameID,
				src : '${ctx}/opinion/edit?id='+id+'&AddEditPcDept='+AddEditPcDept+'&category='+category+'&activitiUser=${activitiUser}&taskId=${taskId}',
				destroy : true,
				title : '编辑部门意见',
				width : 800,
				height : 500,
				modal : true
	    	});
		}
	</script>
[/#macro]