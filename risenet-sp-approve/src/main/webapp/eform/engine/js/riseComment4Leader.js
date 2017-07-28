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
			$("table[name='riseLeaderComment']").each(function(){
				var tableId=$(this).attr("id");
				var category=$(this).attr("category");
				var opinionType=$(this).attr("opiniontype");
				var readOnly=$(this).attr("read_only");
				var mainAndSub=$(this).attr("mainandsub");
				buildLeaderComment(tableId,category,opinionType,readOnly,mainAndSub);
			})
		});
		
		function buildLeaderComment(tableId,category,opinionType,readOnly,mainAndSub){//初始化意见table
				$.ajax({
					   async:false, 
					   cache:false,
				       type:'get',
				       dataType:'JSON',         
				       url:ctx+'/opinion4New/leaderCommentList',    
				       data:{ 
							processSerialNumber: processSerialNumber,
							processInstanceId: processInstanceId,
							taskId: taskId,
							activitiUser: activitiUser,
							category: category,
							itembox: itembox,
							mainAndSub: mainAndSub,
							readOnly: readOnly
						},
				       	success:function(data){
				       		var str="";
				       		var addableStr="";
				       		$.each(data,function(i,item){
				       			if(i!=(data.length-1)){
					       			if(item.editable==true){
					       				//编辑领导意见
				       					str+="<tr><td colspan='2' id='"+item.opinion.id+"' tableId='"+tableId+"' category='"+item.opinion.category+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'>"+item.opinion.content+"<img title='编辑委办局意见' onclick='modifyLeader(this);' style='cursor: pointer;' src='"+ctx+"/static/images/editconfig.gif'></td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.opinion.userName+"&nbsp;&nbsp;"+item.opinion.realityDate+"</td></tr>";
				       				}else{
				       					//只读领导意见
				       					str+="<tr><td colspan='2' >"+item.opinion.content+"</td></tr><tr align='right'><td colspan='2' height='20px'></td><td>&nbsp;&nbsp;"+item.opinion.userName+"&nbsp;&nbsp;"+item.opinion.realityDate+"</td></tr>";
				       				}
				       			}else{
				       				if(item.addable==true){
				       					//alert("新增领导意见");
					       				addableStr="<tr><td colspan='2' align='left' tableId='"+tableId+"' category='"+category+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'><img title='新建领导意见' onclick='addLeader(this);' style='cursor: pointer;' src='"+ctx+"/static/images/writeComment0.gif'></td></tr>";
					       			}
				       			}
				       		});
				       		$("#"+tableId).html(str+addableStr);
				       }    
				});
		}
		
		function addLeader(element){
			var obj=element.parentNode;
			var category=$(obj).attr('category');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/opinion4New/newOrModify/leaderComment?category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id=','新建科室意见', "dialogWidth=770px;dialogHeight=520px;scroll=no;status=no"); 
			buildLeaderComment(tableId,category,3,readOnly,mainAndSub);
		}
		function modifyLeader(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/opinion4New/newOrModify/leaderComment?category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id='+id,'编辑科室意见', "dialogWidth=770px;dialogHeight=520px;scroll=no;status=no"); 
			buildLeaderComment(tableId,category,3,readOnly,mainAndSub);
		}
/*	</script>
[/#macro]*/