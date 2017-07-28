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
		var SPinstanceId = parent.SPinstanceId;//审批业务数据主键Id
		var ctx=parent.ctx;
		var msg;//用来判定当前事项是否显示签名验证
		//var readOnly="";
		//var guids="";
		//var category="";
		//var mainAndSub="";
		//var opinionType="";
		$(document).ready(function() {
			$("table[name='risePersonalComment']").each(function(){
				var tableId=$(this).attr("id");
				var category=$(this).attr("category");
				var opinionType=$(this).attr("opiniontype");
				//var guids=$(this).attr("guids");
				var readOnly=$(this).attr("read_only");
				var mainAndSub=$(this).attr("mainandsub");
				buildPersonalComment(tableId,category,opinionType,readOnly,mainAndSub);
				//showSeal();//显示印章
			});
		});
		
		//获取事项，无纸化意见签名目前只针对部分事项，需要获取事项控制
		function getSpmApproveitem(){
			$.ajax({
				async:false,
				type: "POST",
				cache: false,
				dataType:'JSON',
				url:ctx+"/sp/opinion4New/getSpmApproveitem?SPinstanceId="+SPinstanceId,
				success:function (data){
					if(data.msg){
						msg = true;
					}else{
						msg = false;
					}
				}
			});
		}
		
		function buildPersonalComment(tableId,category,opinionType,readOnly,mainAndSub){//初始化意见table
				$.ajax({
					   async:false, 
					   cache:false,
				       type:'get',
				       dataType:'JSON',         
				       url:ctx+'/sp/opinion4New/personCommentList',    
				       data:{ 
							SPinstanceId:SPinstanceId,
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
							getSpmApproveitem();//判定当前事项是否显示签名验证
				       		var str="";
				       		var addableStr="";
				       		$.each(data,function(i,item){
				       			var sign = "";
				       			if(msg){//只有意见签名的事项才显示验证信息
					       			var opinionCheck = item.opinionCheck;//意见验证结果，true或false或noSign
					       			if(opinionCheck=="true"){
					       				sign = "——已签名";
					       			}else if(opinionCheck=="false"){
					       				sign = "——验证失败";
					       			}else{
					       				sign = "——未签名";
					       			}
				       			}
				       			
				       			if(i!=(data.length-1)){
					       			if(item.editable==true){
				       					if(item.opinion.isAgent==0){
				       						//alert("编辑个人意见");
				       						str+="<tr><td colspan='2' id='"+item.opinion.id+"' tableId='"+tableId+"' category='"+category+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'>"+item.opinion.content+"<img title='编辑个人意见' onclick='modifyPersonal(this);' style='cursor: pointer;' src='"+ctx+"/static/images/editconfig.gif'><font color=\"red\">"+sign+"</font></td></tr><tr align='right'><td><font color=\"red\">"+item.opinion.userName+"&nbsp;&nbsp;"+item.date+"</font></td></tr>";
				       					}else{//暂时不需要代录意见
				       						//alert("编辑代录意见");
				       						str+="<tr><td colspan='2' id='"+item.opinion.id+"' tableId='"+tableId+"' category='"+category+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'>"+item.opinion.content+"<img title='编辑代录意见' onclick='modifyAgent(this);' style='cursor: pointer;' src='"+ctx+"/static/images/editconfig.gif'><font color=\"red\">"+sign+"</font></td></tr><tr align='right'><td><font color=\"red\">"+item.opinion.agentUserName+"&nbsp;&nbsp;"+item.date+"</font></td></tr>";
				       					}
				       				}else{
				       					if(item.opinion.isAgent==0){
				       						//alert("只读个人意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.opinion.content+"<font color=\"red\">"+sign+"</font></td></tr><tr align='right'><td><font color=\"red\">"+item.opinion.userName+"&nbsp;&nbsp;"+item.date+"</font></td></tr>";
				       					}else{
				       						//alert("只读个人意见"+(item.editable==true));
				       						str+="<tr><td colspan='2' >"+item.opinion.content+"<font color=\"red\">"+sign+"</td></font></tr><tr align='right'><td><font color=\"red\">"+item.opinion.agentUserName+"&nbsp;&nbsp;"+item.date+"</font></td></tr>";
				       					}
				       				}
				       			}else{
				       				if(item.addable==true||item.addAgent==true){
				       					//alert("新增个人意见、代录意见");
					       				addableStr="<tr><td colspan='2' align='left' tableId='"+tableId+"' category='"+category+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'><img title='新建个人意见、代录意见' onclick='addPersonal(this);' style='cursor: pointer;' src='"+ctx+"/static/images/writeComment0.gif'></td></tr>";
					       			//}else{暂时不需要代录意见
					       				//alert("只能代录意见");
					       			//	if(item.addAgent==true){
					       			//		addableStr="<tr><td colspan='2' align='left' tableId='"+tableId+"' category='"+category+"' read_only='"+readOnly+"' mainAndSub='"+mainAndSub+"'><img title='只新建代录意见' onclick='addAgent(this);' style='cursor: pointer;' src='"+ctx+"/static/images/writeComment0.gif'></td></tr>";
					       			//	}
					       			}
				       			}
				       		});
				       		$("#"+tableId).html(str+addableStr);
				       }    
				});
		}
		
		//新建个人意见、代录意见
		function addPersonal(element){
			/*在未启动流程时，processInstanceId和taskId都为空，启动流程时才有值，当保存后再填意见，
			上面一开始就加载获取的processInstanceId和taskId仍为空，需要再次获取*/
			var processInstanceId = parent.document.getElementById("processInstanceId").value;
			var taskId = parent.document.getElementById("taskId").value;
			var obj = element.parentNode;
			var category=$(obj).attr('category');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/sp/opinion4New/newOrModify/personalComment?SPinstanceId='+SPinstanceId+'&category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id=',window,"dialogWidth=770px;dialogHeight=530px;scroll=no;status=no"); 
			buildPersonalComment(tableId,category,1,readOnly,mainAndSub);
		}
		
		//编辑个人意见
		function modifyPersonal(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/sp/opinion4New/newOrModify/personalComment?SPinstanceId='+SPinstanceId+'&category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id='+id,window, "dialogWidth=770px;dialogHeight=530px;scroll=no;status=no"); 
			buildPersonalComment(tableId,category,1,readOnly,mainAndSub);
		}
		
		//只新建代录意见
		function addAgent(element){
			var obj=element.parentNode;
			var category=$(obj).attr('category');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/opinion4New/newOrModify/personalComment?category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id=',window, "dialogWidth=770px;dialogHeight=520px;scroll=no;status=no"); 
			buildPersonalComment(tableId,category,1,readOnly,mainAndSub);
		}
		
		//只编辑代录意见
		function modifyAgent(element){
			var obj=element.parentNode;
			var id=$(obj).attr('id');
			var category=$(obj).attr('category');
			var readOnly=$(obj).attr('read_only');
			var mainAndSub=$(obj).attr('mainAndSub');
			var tableId=$(obj).attr('tableId');
			var returnvalue = window.showModalDialog(ctx+'/opinion4New/newOrModify/personalComment?category='+category+'&processInstanceId='+processInstanceId+'&taskId='+taskId+'&processSerialNumber='+processSerialNumber+'&id='+id,window, "dialogWidth=770px;dialogHeight=520px;scroll=no;status=no"); 
			buildPersonalComment(tableId,category,1,readOnly,mainAndSub);
		}
		
/*	</script>
[/#macro]*/