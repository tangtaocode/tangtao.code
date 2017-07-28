//加select信息 oJquery:加载到哪里去 selectId:加载条件
function loadSelectInfo(getUrl,oJquery,type){
	$.ajax({
		  type: "post",
		  dataType: "",
		  error : function(){
				submitState = 0;
				alert("失败,请稍后再试或与我们系统管理员联系！");
		  },
		  url: getUrl,
		  success: function(resultText,resultStatus){
			    addDefaultInfo(oJquery);
			  	if(resultText == null || resultText == ""){
					 return;
				 }else{
				     resultText = $.trim(resultText);
					 oJquery.append(jsonResult(resultText));
					 type();
				 }
		  }
	});
}

function jsonResult(resultText){
	var jsonList = eval('(' + resultText + ')');
	var sHtml = "";
	  for(var i=0;i<jsonList.objectList.length;i++){
		  var oT = jsonList.objectList[i];
		  sHtml += "<option value='"+$.trim(oT.id)+"'>"+$.trim(oT.name)+"</option>";
	  }
	  return sHtml;
}

function addDefaultInfo(oJquery){
	var title = oJquery.children().html();
	var soption = '<option value="">'+title+'</option>';
	oJquery.children().remove();
	oJquery.append(soption);
}
