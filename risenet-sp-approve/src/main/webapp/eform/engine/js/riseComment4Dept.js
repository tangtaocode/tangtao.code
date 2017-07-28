/*[#macro riseComment category  OpinionType  guids="" readOnly="" mainAndSub=""]
	<table id="opinionCotent-${category}" category=${category}  style="border: 0px; width: 100%">
	</table>
	<script type="text/javascript">*/
		var frameID = newGuid();
		var processInstanceId = parent.document.getElementById("processInstanceId").value;
		var taskId = parent.document.getElementById("taskId").value;
		var activitiUser = parent.document.getElementById("activitiUser").value;
		var processSerialNumber = parent.document.getElementById("processSerialNumber").value;
		var itembox = parent.document.getElementById("itembox").value;
		var ctx=parent.ctx;
		$(document).ready(function() {
			$("table[name='riseDeptComment']").each(function(){
				var tableId=$(this).attr("id");
				var category=$(this).attr("category");
				var opinionType=$(this).attr("opiniontype");
				var guids=$(this).attr("guids");
				var readOnly=$(this).attr("read_only");
				var mainAndSub=$(this).attr("mainandsub");
				buildDeptComment(tableId,category,opinionType,guids,readOnly,mainAndSub);
			})
		});
		
		function buildDeptComment(tableId,category,opinionType,guids,readOnly,mainAndSub){//初始化意见table
				$.ajax({
					   async:false, 
					   cache:false,
				       type:'get',
				       dataType:'JSON',         
				       url:ctx+'/opinion4New/deptCommentList',    
				       data:{ 
							processSerialNumber: processSerialNumber,
							processInstanceId: processInstanceId,
							taskId: taskId,
							activitiUser: activitiUser,
							category: category,
							itembox: itembox,
							mainAndSub: mainAndSub,
							readOnly: readOnly,
							guids:guids
						},
				       	success:function(data){
				       		var str="";
				       		var addableStr="";
				       		$.each(data,function(i,item){
				       			if(i!=(data.length-1)){
					       			if(item.editable==true){
				       					if(guids.indexOf("Bureau")<0){
				       						//alert("编辑科室意见");
				       						str+="<tr><td colspan='2' id='"+item.opinion.id+"' tableId='"+tableId+"' category='"+item.opinion.category+"' guids='"+item.opinion.guids+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'>"+item.opinion.content+"<img title='编辑科室意见'   onclick='modifyDept(this);' style='cursor: pointer;' src='"+ctx+"/static/images/editconfig.gif'></td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.opinion.departmentName+"（"+item.opinion.signatureName+"）&nbsp;&nbsp;"+item.opinion.modifyDate+"</td></tr>";
				       					}else if(item.opinion.isDepartmentOpinion==2){
				       						//alert("编辑委办局意见");
				       						str+="<tr><td colspan='2' id='"+item.opinion.id+"' tableId='"+tableId+"' category='"+item.opinion.category+"' guids='"+item.opinion.guids+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'>"+item.opinion.content+"<img title='编辑委办局意见' onclick='modifyDept(this);' style='cursor: pointer;' src='"+ctx+"/static/images/editconfig.gif'></td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.opinion.departmentName+"（"+item.opinion.signatureName+"）&nbsp;&nbsp;"+item.opinion.modifyDate+"</td></tr>";
				       					}
				       				}else{
				       					if(guids.indexOf("Bureau")<0){
				       						//alert("只读科室意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.opinion.content+"</td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.opinion.departmentName+"（"+item.opinion.signatureName+"）&nbsp;&nbsp;"+item.opinion.modifyDate+"</td></tr>";
				       					}else if(item.opinion.isDepartmentOpinion==2){
				       						//alert("只读委办局意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.opinion.content+"</td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.opinion.departmentName+"（"+item.opinion.signatureName+"）&nbsp;&nbsp;"+item.opinion.opinionDate+"</td></tr>";
				       					}
				       				}
				       			}else{
				       				if(item.addable==true){
				       					//alert("新增部门意见");
					       				addableStr="<tr><td colspan='2' align='left' tableId='"+tableId+"' category='"+category+"' guids='"+guids+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'><img title='新建科室意见' onclick='addDept(this);' style='cursor: pointer;' src='"+ctx+"/static/images/writeComment0.gif'></td></tr>";
					       			}
				       			}
				       		});
				       		$("#"+tableId).html(str+addableStr);
				       }    
				});
		}
		
		function addDept(element){
			var obj=element.parentNode;
			var category=$(obj).attr('category');
			var guids=$(obj).attr('guids');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/opinion4New/newOrModify/deptComment?category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id=&guids='+guids,'新建科室意见', "dialogWidth=770px;dialogHeight=520px;scroll=no;status=no"); 
			buildDeptComment(tableId,category,2,guids,readOnly,mainAndSub);
		}
		function modifyDept(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var guids=$(obj).attr('guids');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/opinion4New/newOrModify/deptComment?category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id='+id+'&guids='+guids,'编辑科室意见', "dialogWidth=770px;dialogHeight=520px;scroll=no;status=no"); 
			buildDeptComment(tableId,category,2,guids,readOnly,mainAndSub);
		}
/*	</script>
[/#macro]*/