//鼠标移动到一个tr时
function editRowOver(num){
	  var count = parseInt($("#recordCount").val());
	  for(var i = 0;i<=count;i++){
		var saveEditButtonRead = $("#saveEditButton"+i);
		var returnEditButtonRead = $("#returnEditButton"+i);
	   	var updateEditButtonRead = $("#updateEditButton"+i);
	   	var deleteEditButtonRead = $("#deleteEditButton"+i);
	   	var returnDisplayShow = returnEditButtonRead.css("display");
	   	var editRow = $("#editRow"+i);
	   	//编辑状态是否还原
		if(returnDisplayShow==""){
			saveEditButtonRead.show();
			returnEditButtonRead.show();
			updateEditButtonRead.hide();
			deleteEditButtonRead.hide();
		}else if(returnDisplayShow=="none"){
			if(num==i){
				saveEditButtonRead.hide();
				returnEditButtonRead.hide();
	    		updateEditButtonRead.show(); 
	    		deleteEditButtonRead.show();
			}else{
				saveEditButtonRead.hide();
				returnEditButtonRead.hide();
	    		updateEditButtonRead.hide(); 
	    		deleteEditButtonRead.hide();
			}
		}
	   	//底色控制
		if(num==i){
	   		editRow.addClass("backgroundSetting");  
	   	}else{
	   		editRow.removeClass("backgroundSetting");
		}
	  } 
   }


//点击编辑时
function updateEdit(index){
	  var count = parseInt(document.getElementById("recordCount").value);
	  //1.先判断之前是否有按编辑，是则还原
	  for(var i = 0;i<count;i++){
	   	  var returnEditButtonRead = $("#returnEditButton"+i);
	  	  var returnDisplayShow = returnEditButtonRead.css("display");
	  	  //火狐和IE的值不一样，火狐：inline，IE：inline-block。
	  	  if(returnDisplayShow=="inline"||returnDisplayShow=="inline-block"){//有取消按钮
	  	  	var updateEditButtonRead = $("#updateEditButton"+i);
	  		var deleteEditButton = $("#deleteEditButton"+i);
	  		
	  		var saveEditButtonRead = $("#saveEditButton"+i);
	  		//还原内容
	  		var editComment = $("#editComment"+i);
	  	 	editComment.text($("#recordComment").val());
	  		
	  	 	//还原样式
	  		updateEditButtonRead.show();
	  		deleteEditButton.show();
	  		
	  		saveEditButtonRead.hide();
	   		returnEditButtonRead.hide();
	  	  }
	  }
	  
	  //2.内容转换为input
	   var editComment = $("#editComment"+index);
	   var editCommentValue = editComment.text().replace(/(^\s*)/g, "");
	   var inputComment = "<textarea type='text' id='inputComment"+index+"' cols='50%' >"+editCommentValue+"</textarea>";
	   editComment.html(inputComment);
	   //3.隐藏修改按钮
	   var updateEditButton = $("#updateEditButton"+index);
	   var returnEditButton = $("#returnEditButton"+index);
	   var saveEditButton = $("#saveEditButton"+index);
	   var deleteEditButton = $("#deleteEditButton"+index);
	   updateEditButton.hide();
	   deleteEditButton.hide();
	   
	   returnEditButton.show();
	   saveEditButton.show();
	   //4.记录上次编辑常用意见,用于还原内容
	   $("#recordComment").val(editCommentValue);
}


//删除单个常用意见
function deleteEdit(tabIndex,index){
		var count = parseInt($("#recordCount").val());
		var str = false;
		for (var i = 0; i < count; i++) {
			var editComment = $("#editComment"+i);
			var flag = editComment.html().indexOf("<textarea");
			if(flag == 0){//其他为编辑状态时
				str = true;
			}
	   }
		if(str){//其他为编辑状态时
			alert("请您先完成常用语编辑！")
			return;
		}else{
			var r=confirm("您确定要删除该常用语吗？")
			if(!r){
				return;
			}
			removeCommonSentences(tabIndex);
	  	}
}

 
 //点击取消时
 function returnEdit(index){
	//1.还原内容
 	var editComment = $("#editComment"+index);
 	editComment.text( $("#recordComment").val());
 	//2.还原样式
 	var saveEditButton = $("#saveEditButton"+index);
    var returnEditButton = $("#returnEditButton"+index);
    
    var updateEditButton = $("#updateEditButton"+index);
    var deleteEditButton = $("#deleteEditButton"+index);
   
    updateEditButton.show();
    deleteEditButton.show();
    saveEditButton.hide();
    returnEditButton.hide();
 }