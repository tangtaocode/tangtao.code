//设置表单是否只读、不可编辑
$(document).ready(function() {
	var itembox = parent.document.getElementById("itembox").value;
	var taskDefKey = parent.taskDefKey;
	//在办件，办结件，暂停件为只读
	if (itembox == 'doing' || itembox == 'done'||itembox == 'pause'||itembox == 'history') {
		var input = document.getElementsByTagName("input");
		var select = document.getElementsByTagName("select");
		var textarea = document.getElementsByTagName("textarea");
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
	
});
