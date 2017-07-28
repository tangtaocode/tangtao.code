<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>编辑收件要点</title>
</head>
<script type="text/javascript">
var g;	
var a1="ytj";
var a2="xbq";
var a3="xbz";

var ret="";;
function initComplete(){
	//parent.window.frames[0].document.getElementById('state').value = "1";//用来记录是否打开过材料页面，基本信息页面要引用材料页面的方法。。。。没打开报错！！
	g = $("#maingrid").quiGrid({
		columns: [ 
					{ display: '序号', name: 'DECLAREANNEXTABINDEX', align: 'center', width: "3%"
						
					},
					{ display: '材料名称', name: 'MATERIALNAME', align: 'left', width: "25%",
						render: function (rowdata, rowindex, value, column){
							var view = '<a href="#" onclick="editPoints(\'' + rowdata.ID +'\',\''+rowdata.MATERIALNAME+'\')">'+rowdata.MATERIALNAME+'</a>';      
		                    return view;
						}
					},
					{ display: '业务情形', name: 'SITUATION', align: 'center', width: "15%"
					},
					{ display: '提交方式', name: 'TJFS', align: 'center', width: "20%"
					},
					
					{ display: '上传附件', name: 'FILENAME', align: 'center', width: "15%",
	                	render: function (rowdata, rowindex, value, column){
                		    if(rowdata.SUBMISSION=='checked'){

                		    	if(rowdata.idid==null){
                		    		return '<a href="javascript: void(0);" onclick="openFile(\''+rowdata.ID+'\')"  class="aColor" >查看附件</a>'+
		                		    '&nbsp;';
                		    	}else{
                		    		ret = '<a href="javascript: void(0);" onclick="openFile(\''+rowdata.ID+'\')"  class="aColor" >查看附件</a>';
                		    		
                		    		return ret;
                		    	}	 	
                		    }else{
	                		return '<a href="javascript: void(0);" onclick="openFile(\''+rowdata.ID+'\')"  class="aColor" >上传附件</a>';
                		    }
                		 }	
	                },
					 { display: '已提交', name: 'SUBMISSION', align: 'center', width: "5%",
	                	render: function (rowdata, rowindex, value, column){
	                		  return '<input type="checkbox"  class="checkbox" id="ytj" name="checkbox'+rowindex+'" onclick="checkedThis(a1,this)"   ' + rowdata.SUBMISSION + '   value="'+rowdata.ID+'">';
                       }	
	                },
	                { display: '需补齐', name: 'NOTSUBMISSION',align: 'center',width: "5%",
	                	render: function (rowdata, rowindex, value, column){
                                return '<input type="checkbox" class="checkbox" id="xbq" name="checkbox'+rowindex+'" onclick="checkedThis(a2,this)"  ' + rowdata.NOTSUBMISSION + '   value="'+rowdata.ID+'">';
                       }	
	                },
	                { display: '需补正', name: 'REJIGGER',align: 'center',width: "5%",
	                	 render: function (rowdata, rowindex, value, column){
                                return '<input type="checkbox" class="checkbox" id="xbz" name="checkbox'+rowindex+'" onclick="checkedThis(a3,this)"  ' + rowdata.REJIGGER + '   value="'+rowdata.ID+'">';
                          }			
	                }
	         ], 
	        url:'${ctx}/togetherWindow/getPoints?itemid=${itemid}&instanceGuid=${instanceGuid}',rownumbers: false,checkbox:false,fixedCellHeight: false,
	        height: '100%', width: '100%', usePager: false,percentWidthMode:true
	});
}

function editPoints(id, materialname) {
	Dialog.open({URL:"${ctx}/togetherWindow/editPointInit?ID="+id+"&MATERIALNAME="+materialname+"&method=end",Title:"查看材料要点",Top:"0%",Width:800,Height:500});
}
function closeThePoint(){
	this.Dialog.close();
	g.loadData();
}

function openFile(declareguid){
	var instanceId = $("#instanceId").val();
	Dialog.open({URL:"${ctx}/wssbcl/lookFile?DECLAREANNEXGUID="+declareguid+"&instanceId="+instanceId+"&itembox=",Title:"附件",Width:700,Height:500});
}

function lookStuffdata(DECLAREANNEXGUID){
	var instanceId = $("#instanceId").val();
	Dialog.open({URL:"${ctx}/stuff/lookStuffdata?DECLAREANNEXGUID="+DECLAREANNEXGUID+"&instanceId="+instanceId+"&type=online",Title:"共享材料列表",Width:800,Height:600});
}
function gxcailiao(){
	Dialog.open({URL:"${ctx}/onlineApprove/gxcailiao?userid=${userid}",Title:"共享证照",Width:1000,Height:500});
	
}

function moren(){
	var ytjids = "";
	var xbqids = "";
	var xbzids = "";
	var listdata = g.getData();
	for(var i=0;i<=listdata.length-1;i++){
		var t = document.getElementsByName("checkbox"+i);
		 for(var j=0;j<=t.length-1;j++){
			 if (t[j].checked && t[j].id == "ytj") {
					if(ytjids==''){
						ytjids = t[j].value;
					}else{
						ytjids += ","+t[j].value;
					}
				} else if (t[j].checked && t[j].id == "xbq") {
					if(xbqids==''){
						xbqids = t[j].value;
					}else{
						xbqids += ","+t[j].value;
					}
				} else if (t[j].checked && t[j].id == "xbz") {
					if(xbzids==''){
						xbzids = t[j].value;
					}else{
						xbzids += ","+t[j].value;
					}
				}
		 }
	}
	$("#ytjs").val(ytjids);
	$("#xbqs").val(xbqids);
	$("#xbzs").val(xbzids);
	
}	

function checkedThis(name,obj){	
    var boxArray = document.getElementsByName(obj.name); 
    for(var i=0;i<=boxArray.length-1;i++){	  
         if(boxArray[i]==obj && obj.checked){ 
            boxArray[i].checked = true;
         }else{ 
            boxArray[i].checked = false;   
         } 
    } 
 }
</script>
<body>
	<div class="padding_right5" style="height:100%;width:100%">
		<div id="maingrid" style="width:98%"></div>
    </div>
    <input type="hidden" name="declareAnnexGuids2" id="ytjs" />
	<input type="hidden" name="notDeclareAnnexGuids2" id="xbqs" />
	<input type="hidden" name="bhgsbcl2" id="xbzs" />
	<input type="hidden" name="instanceId" id="instanceId" value="${instanceGuid}"/>
	<div id="pointDiv" style="display:none">
	</div> 
</body>
<script type="text/javascript">
	function appendPoint(i){
		var str = '<table class="tableStyle" formMode="line" align="left">'
				+'<tr><td width="20%">'
				+'<input type="checkbox" id="yj" name="tjlx'+i+'" value="1"/>'
				+'	<label for="yj" class="hand">原件(正本)</label></td><td>'
				+'	<input type="checkbox" id="yj_hy" name="tjfs'+i+'" value="1"/>'
				+'	<label for="yj_hy" class="hand">核验</label>&nbsp;&nbsp;'
				+'	<input type="checkbox" id="yj_sq" name="tjfs'+i+'" value="2"/>'
				+'	<label for="yj_sq" class="hand">收取</label>&nbsp;&nbsp;'
				+'	<input type="text" name="des'+i+'"/>'
				+'</td></tr>'
				+'<tr><td width="20%">'
				+'	<input type="checkbox" id="fb" name="tjlx'+i+'" value="2"/>'
				+'	<label for="fb" class="hand">原件(副本)</label>'
				+'</td>'
				+'<td>'
				+'	<input type="checkbox" id="fb_hy" name="tjfs'+i+'" value="1"/>'
				+'	<label for="fb_hy" class="hand">核验</label>&nbsp;&nbsp;'
				+'	<input type="checkbox" id="fb_sq" name="tjfs'+i+'" value="2"/>'
				+'	<label for="fb_sq" class="hand">收取</label>&nbsp;&nbsp;'
				+'	<input type="text" name="des'+i+'"/>'
				+'</td></tr>'
				+'<tr><td width="20%">'
				+'	<span style="margin-right:25px">'
				+'	<input type="checkbox" id="dzj" name="tjlx'+i+'" value="3"/>'
				+'	<label for="dzj" class="hand">电子件</label>'
				+'	</span>'
				+'</td>'
				+'<td>'
				+'	<input type="checkbox" id="dzj_hy" name="tjfs'+i+'" value="1"/>'
				+'	<label for="dzj_hy" class="hand">核验</label>&nbsp;&nbsp;'
				+'	<input type="checkbox" id="dzj_sq" name="tjfs'+i+'" value="2"/>'
				+'	<label for="dzj_sq" class="hand">收取</label>&nbsp;&nbsp;'
				+'	<input type="text" name="des'+i+'"/>'
				+'</td>'
				+'</tr>'
				+'<tr>'
				+'<td width="20%">'
				+'	<span style="margin-right:25px">'
				+'	<input type="checkbox" id="fyj" name="tjlx'+i+'" value="4"/>'
				+'	<label for="fyj" class="hand">复印件</label>'
				+'	</span>'
				+'</td>'
				+'<td>'
				+'	<input type="checkbox" id="fyj_hy" name="tjfs'+i+'" value="1"/>'
				+'	<label for="fyj_hy" class="hand">核验</label>&nbsp;&nbsp;'
				+'	<input type="checkbox" id="fyj_sq" name="tjfs'+i+'" value="2"/>'
				+'	<label for="fyj_sq" class="hand">收取</label>&nbsp;&nbsp;'
				+'	<input type="text" name="des'+i+'"/>'
				+'</td>'
				+'</tr>'
				+'<tr>'
				+'<td width="20%">'
				+'	<span style="margin-right:37px">'
				+'		<input type="checkbox" id="qt" name="tjlx'+i+'" value="5"/>'
				+'		<label for="qt" class="hand">其它</label>'
				+'	</span>'
				+'</td>'
				+'<td>'
				+'	<input type="checkbox" id="qt_hy" name="tjfs'+i+'" value="1"/>'
				+'	<label for="qt_hy" class="hand">核验</label>&nbsp;&nbsp;'
				+'	<input type="checkbox" id="qt_sq" name="tjfs'+i+'" value="2"/>'
				+'	<label for="qt_sq" class="hand">收取</label>&nbsp;&nbsp;'
				+'	<input type="text" name="des'+i+'"/>'
				+'</td></tr></table>';
				return str;
	}
</script>
</html>