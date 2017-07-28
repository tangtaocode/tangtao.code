function showMSG2(msg){
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:2000,
		showType:'slide'
	});
}

function showMSG5(msg){
	$.messager.show({
		title:'提示',
		msg:msg,
		timeout:5000,
		showType:'slide'
	});
}

function alertMSGError(msg){
	$.messager.alert("提示", msg,"error");
}

function alertMSGWarning(msg){
	$.messager.alert("提示", msg,"warning");
}