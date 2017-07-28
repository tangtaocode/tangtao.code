//设置表单是否只读、不可编辑
$(document).ready(function() {
	var itembox = parent.document.getElementById("itembox").value;
	var taskDefKey = parent.taskDefKey;
	//在办件，办结件，补齐补正件，历史办件，暂停件为只读
	var input = document.getElementsByTagName("input");
	var select = document.getElementsByTagName("select");
	var textarea = document.getElementsByTagName("textarea");
	if (itembox == 'doing' || itembox == 'done'||itembox == 'bqbz'||itembox == 'pause'||itembox == 'history'||!(taskDefKey == "dengji")) {
		for(var i = 0;i<input.length;i++){
			input[i].disabled="disabled";
		}
		for(var i = 0;i<select.length;i++){
			select[i].disabled="disabled";
		}
		for(var i = 0;i<textarea.length;i++){
			textarea[i].disabled="disabled";
		}
	}
	for(var i = 0;i<input.length;i++){
		input[i].title = input[i].value;
	}

});
