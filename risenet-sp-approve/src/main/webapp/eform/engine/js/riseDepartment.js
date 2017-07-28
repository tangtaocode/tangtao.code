var width;
var height;
$(document).ready(function() {
	width = $(window).width();
	height = $(window).height();
	$('input[name=laiwenzihao]').css('color','red');
	$.ajax({
			   async:false, 
			   cache:false,
		       type:'get',
		       dataType:'JSON',         
		       url:'characterValues4Select',    
		       success:function(data){
		       		$("#character").append(data.cv4select);
		       }    
		});
});

function genSequence(){
	 var character = encodeURIComponent($("#character").val());//转码
	 var returnvalue = window.showModalDialog('docSequence?character='+character+'&labelName=sequence1','编号', "dialogWidth=240px;dialogHeight=120px;dialogLeft="+width/2+"px;dialogTop="+height/2+"px;scroll=no;status=no") 
	 if(returnvalue!=null){
		 document.getElementById("laiwenzihao").value=returnvalue;
	 }
}


function  getDept(){
    var returnvalue = window.showModalDialog('userChoicePage?urd=2&chkStyle=radio','单位选取', "dialogWidth=500px;dialogHeight=420px;dialogLeft="+width/2+"px;dialogTop="+height/2+"px;scroll=no;status=no") 
	if(returnvalue!=null){	
		var userids=returnvalue[0];
		var names=returnvalue[1];
		var deptIds=returnvalue[2];
		if(userids.indexOf(";")>0){
		}else{
			document.getElementById("laiwendanwei").value=names;
		}
	}
}
