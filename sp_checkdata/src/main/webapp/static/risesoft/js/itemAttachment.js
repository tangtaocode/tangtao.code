var i=0;
function addFile() {
i++;
currRow=conditionTable.insertRow();
cellc=currRow.insertCell();
cellcContext= '<input type="file" size="75" class="style_input_left" NAME="sqFile">&nbsp;&nbsp;<a href="####" onClick="removeFile(this)">'+
  		'<img src="../static/risesoft/images/delete.png" alt="删除申请表格"/></a>';
cellc.innerHTML=cellcContext;

}

function removeFile(obj){
	conditionTable.deleteRow(obj.parentNode.parentNode.rowIndex);		
}	
//js代码： 删除行
function delSqbg(obj,filename){
  var sqclTable = document.getElementById("sqbg_old_table");
  if(!confirm("确认删除《"+filename+"》附件？")) return false;  
  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);
 }

var i=0;
function addYbFile() {
i++;
ybcurrRow=ybconditionTable.insertRow();
ybcellc=ybcurrRow.insertCell();
ybcellcContext= '<input type="file" size="75" class="style_input_left" NAME="ybfile">&nbsp;&nbsp;<a href="####" onClick="return removeYbFile(this)">'+
  		'<img src="../static/risesoft/images/delete.png" alt="删除样表"/></a>';
ybcellc.innerHTML=ybcellcContext;

}

function removeYbFile(obj){
	ybconditionTable.deleteRow(obj.parentNode.parentNode.rowIndex);		
}	

//js代码： 删除行
function delSqyb(obj,filename){
  var sqclTable = document.getElementById("sqyb_old_table");
  if(!confirm("确认删除《"+filename+"》附件？")) return false;  
  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);
 }
//窗口流程图
function delWindowWorkFlowFile(obj,filename){
	  var sqclTable = document.getElementById("ckbllc_old_table");
	  if(!confirm("确认删除《"+filename+"》附件？")) return false;  
	  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);
	  	document.getElementById("windowWorkFlowDel").value="0";
	  	document.getElementById("windowGuid").value="";
	 }
//全力运行流程图
function delPowerWorkFlowFile(obj,filename){
	  var sqclTable = document.getElementById("qlyxlc_old_table");
	  if(!confirm("确认删除《"+filename+"》附件？")) return false;  
	  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);
	  	document.getElementById("powerWorkFlowDel").value="0";
	  	document.getElementById("powerGuid").value="";
	 }
//网上办理流程图
function delLct(obj,filename){
	  var sqclTable = document.getElementById("lct_old_table");
	  if(!confirm("确认删除《"+filename+"》附件？")) return false;  
	  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);
	  	document.getElementById("flowchartsDel").value="";
	 }