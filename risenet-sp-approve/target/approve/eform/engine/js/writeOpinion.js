var frameID = newGuid();
var category;
var opinionType;
var guids;
//根据意见框是否只读来默认签写意见
$(document).ready(function() {
	var table = document.getElementsByTagName("table");
	for(var i = 0;i<table.length;i++){
		var tableId = table[i].id;
		$("#"+tableId).each(function(){
			if($(this).attr("read_only")=="NO"){
				category = $(this).attr("category");
				opinionType = $(this).attr("opiniontype");
				if(!($(this).attr("guids")=="undefined"||$(this).attr("guids")==undefined)){
					guids = $(this).attr("guids");
				}
			}
		})
	}
	
});

//填写默认意见
function writeOpinion(){
	var processInstanceId = parent.document.getElementById("processInstanceId").value;
	var taskId = parent.document.getElementById("taskId").value;
	var processSerialNumber = parent.document.getElementById("processSerialNumber").value;
	 $.ajax({
	        type: "POST",
			async:false,
			cache:false,
	        url: ctx+"/opinion4New/writeOpinion",
	        data: {taskId:taskId,processInstanceId:processInstanceId,processSerialNumber:processSerialNumber,content:"已阅。",category:category,opinionType:opinionType,guids:guids},
	        dataType: "json",
	        success: function(data){}
	    });
}
