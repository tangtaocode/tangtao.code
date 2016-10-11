//js代码： 删除行
function delRow(obj){
  var sqclTable = document.getElementById("table_content_fr");
  if(!confirm("确认删除？")) return false;  
  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);
 }     

function insClRow(){
	var guid="";
    // js代码：插入行（两个单元格）
	var sqclTable = document.getElementById("table_content_fr");		
 	var newRow = sqclTable.insertRow(sqclTable.rows.length);
 	var length = sqclTable.rows.length;
 	//var materialTypeTexts = document.getElementsByName("materialTypeText");  
 	var meterialLx='<select name="materialLx" id="materialLx'+length+'" prompt="" url="'+ctx+'/resource/materialSelect"></select>';	  			
	newCell = newRow.insertCell();//td1
 	newCell.innerHTML = meterialLx;
 	
 	newCell = newRow.insertCell();//td1
 	newCell.innerHTML = '<input type="hidden" name="materialGuid"   value="'+guid+'">'+
 	'<textarea class="style_text_yj" keepDefaultStyle=true style="height:30px;width:100%" name="materialName" id="materialName"></textarea>';
 	newCell = newRow.insertCell();//td2
 	newCell.innerHTML = '<textarea class="style_text_yj" keepDefaultStyle=true style="height:30px;width:100%" name="materialDescribe" id="materialDescribe"></textarea>';
 	newCell = newRow.insertCell();//td3
 	newCell.innerHTML = '<input type="text" style="width:50px" class="style_input_left" size="3" maxlength="3" name="materialOrderno" id="materialOrderno"/>';
 	newCell = newRow.insertCell();//td4
 	newCell.innerHTML ='<a href="####" onClick="return delRow(this)">'+
 		'<img src="../static/risesoft/images/delete.png" alt="删除"/></a>';
 	$("#materialLx"+length).render();
	}  	

//隐藏材料选择框
	function hiddenCheck(check_td,obj){
		document.getElementById(check_td).style.display="none";
		obj.parentNode.innerHTML = '<a href="####" onClick="return showCheck(\''+check_td+'\',this)"><img src="../static/risesoft/images/expand.png" alt="展开"/></a>';
	}
	//显示材料选择框
  	function showCheck(check_td,obj){
  		document.getElementById(check_td).style.display="block";
  		obj.parentNode.innerHTML = '<a href="####" onClick="return hiddenCheck(\''+check_td+'\',this)"><img src="../static/risesoft/images/collapse.png" alt="收起"/></a>';
  	}
  //材料分类行插入
  var cltyleSize = $("#typelen").val();
 function insRowType(){
     // js代码：插入行（两个单元格）
	var sqclTable = document.getElementById("type");			
	cltyleSize++;
  	var newRow = sqclTable.insertRow(sqclTable.rows.length);
  	newCell = newRow.insertCell();//td0	  	
  		
    var htmlStr = '<table name="table_content_typegl" id="table_content_typegl" width="100%">'+
    '<tr><th width="5%"><a href="####" onClick="return hiddenCheck(\'check_td_'+cltyleSize+'\',this)"><img src="../static/risesoft/images/collapse.png" alt="收起"/></a></th>'+
    '<input type="hidden" name="groupname" id="groupname" value="'+cltyleSize+'"><th>分类名称：<input type="text" size="60"  class="validate[required]" name="materialTypeText'+cltyleSize+'" id="materialTypeText'+cltyleSize+'"/><font color="red">*</font><input type="hidden" size="60" class="style_input_left" name="materialTypeGuid'+cltyleSize+'"/></th>'+
    '<th>排序号：<input type="text" class="validate[required]" class="style_input_left" size="10" maxlength="6" id="materialTypeOrderno'+cltyleSize+'" name="materialTypeOrderno'+cltyleSize+'"/><font color="red">*</font></th>'+
    '<th><a href="####" onClick="return delRowType(this)"><img src="../static/risesoft/images/delete.png" alt="删除"/></a></th></tr>';
    var materialNames = document.getElementsByName("materialName");  
    var materialGuids = document.getElementsByName("materialGuid");  
    htmlStr +=	'<tr><td id="check_td_'+cltyleSize+'" name="check_td_'+cltyleSize+'" text-align="left" colspan="4">';
    if(eval(materialGuids) && materialGuids.length>0){		    	
  		for (var i=0; i<materialGuids.length; i++) {
  			htmlStr += '<input name="checkbox'+cltyleSize+'" id="checkbox'+cltyleSize+'" type="checkbox" value="'+materialGuids[i].value+'">'+materialNames[i].value+'<br>';
  		}	  		
	}
    htmlStr +=	'</td></tr>';
    htmlStr +='</table>';		
    newCell.innerHTML=htmlStr;
} 	
//js代码： 删除行
function delRowType(obj){
  var sqclTable = document.getElementById("type");
  if(!confirm("确认删除？")) return false;  
  	sqclTable.deleteRow(obj.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.rowIndex);
 } 