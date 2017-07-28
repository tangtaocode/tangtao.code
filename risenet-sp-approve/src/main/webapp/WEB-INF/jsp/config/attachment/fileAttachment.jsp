<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--树形表格start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--树形表格end-->
<!--弹窗组件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
<!--弹出式提示框start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/messager.js"></script>
<!--弹出式提示框end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!--箭头分页start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/pageArrow.js"></script>
<!--箭头分页end-->
<!--箭头分页start-->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--箭头分页end-->
<script type="text/javascript" src="${ctx}/static/risesoft/js/risenetDialog.js"></script>
</head>
<body>
<div id="maingrid" style="width:98%;"></div>
			<input type="hidden" name="ytjids" id="ytjids" value="" /> 
			<input	type="hidden" name="xbqids" id="xbqids" value="" /> 
			<input	type="hidden" name="xbzids" id="xbzids" value="" />

	
</body>

<script type="text/javascript">
//定义远程数据
var g;
var a1="ytj";
var a2="xbq";
var a3="xbz";
var ytjids="";
var xbqids="";
var xbzids="";
var ret="";
var SPinstanceId = "${SPinstanceId}";
var itembox = "${itembox}";
function initComplete(){
	 g = $("#maingrid").quiGrid({
       columns: [ 
                 	{ display: '材料Id',  name:'SEQ',hide:true},
                 	{ display: '事项材料编号',  name:'STUFF_SEQ',hide:true},
                    { display: '材料名称', name: 'CLMC', align: 'center', width: "20%"},
	                { display: '文件类型', name: 'WJLX', align: 'center', width: "10%",
                    	render: function (rowdata, rowindex, value, column){
	                		if(rowdata.WJLX=='0') {
	                			return '纸质';
	                		}else{
	                			return '电子版';
	                		}
	                	}	
	                },
	                { display: '材料类型', name: 'CLLX', align: 'center', width: "10%",
	                	render: function (rowdata, rowindex, value, column){
	                		if(rowdata.CLLX=='0') {
	                			return '原件';
	                		}else if(rowdata.CLLX=='1') {
	                			return '复印件';
	                		}else{
	                			return '电子件';
	                		}
	                	}	
	                },
	                { display: '材料数量', name: 'CLSL', align: 'center', width: "10%"},
	                { display: '附件', name: 'PUTONG', align: 'center', width: "20%",
	                	render: function (rowdata, rowindex, value, column){
                		    //if(rowdata.PUTONG=='checked'||itembox=='done'||itembox=='doing'){
                		    //	return '<a href="javascript: void(0);" onclick="lookFile(\''+rowdata.DECLAREANNEXGUID+'\')"  class="aColor" >查看附件</a>';
                		    //}else{
							//	return '<a href="javascript: void(0);" onclick="lookFile(\''+ rowdata.DECLAREANNEXGUID+ '\')"  class="aColor" >上传附件</a>';
                		   // }
                		    var s='';
                		    for(i=0;i<rowdata.attachList.length;i++){
                		    	s+='<a href="'+rowdata.attachList[i].ATTACH_PATH+'" class="aColor" >' + rowdata.attachList[i].ATTACH_NAME + '</a><br/>';
                		    }
	                		return s;
                		 }	
	                },
	                /* { display: '证照', name: 'ZHENGZHAO', align: 'center', width: "9%", 
	                	render: function (rowdata, rowindex, value, column){
	                		if(rowdata.ZHENGZHAO=='checked') {
	                			return '<a onclick="viewZhengzhao(\''+rowdata.DECLAREANNEXGUID+'\')" class="aColor">查看证照</a>';
	                		}
	                	}
	                }, */
	                /* { display: '共享材料', name: 'STUFFDATA', align: 'center', width: "9%", 
	                	render: function (rowdata, rowindex, value, column){
	                		if(rowdata.STUFFDATA=='checked') {
	                			return '<a onclick="lookStuffdata(\''+rowdata.DECLAREANNEXGUID+'\')" class="aColor">共享材料</a>';
	                		}
	                	}
	                }, */
	                /* { display: '表单', name: 'EFORM', align: 'center', width: "9%", 
	                	render: function (rowdata, rowindex, value, column){
	                		if(rowdata.EFORM=='checked') {
	                			return '<a onclick="showEform(\''+ rowdata.DECLAREANNEXGUID+ '\',\''+ rowdata.VIEWID+ '\',\''+ rowdata.WORKFLOWINSTANCE_GUID+'\')" class="aColor">电子表单</a>';
	                		}
	                	}
	                }, */
	                { display: '已提交', name: 'SUBMISSION', align: 'center', width: "10%",
	                	render: function (rowdata, rowindex, value, column){
	                		  return '<input type="checkbox"  class="checkbox" id="ytj" name="checkbox'+rowindex+'" onclick="checkedThis(a1,this)"   ' + rowdata.SUBMISSION + '   value="'+rowdata.SEQ+'">'
                       }	
	                },
	                { display: '需补齐', name: 'NOTSUBMISSION',align: 'center',width: "10%",
	                	render: function (rowdata, rowindex, value, column){
                                return '<input type="checkbox" class="checkbox" id="xbq" name="checkbox'+rowindex+'" onclick="checkedThis(a2,this)"  ' + rowdata.NOTSUBMISSION + '   value="'+rowdata.SEQ+'">'
                       }	
	                },
	                { display: '需补正', name: 'REJIGGER',align: 'center',width: "10%",
	                	 render: function (rowdata, rowindex, value, column){
                                return '<input type="checkbox" class="checkbox" id="xbz" name="checkbox'+rowindex+'" onclick="checkedThis(a3,this)"  ' + rowdata.REJIGGER + '   value="'+rowdata.SEQ+'">'
                          }			
	                }
	                
         ], 
        url:'${ctx}/wssbcl/materialList?SPinstanceId=${SPinstanceId}',usePager:false,
        //groupColumnName: "DECLAREANNEXTYPENAME", groupColumnDisplay: "材料类别",percentWidthMode:true,
        height: '99%', width:"100%",fixedCellHeight:false,rownumbers:true ,frozen:false,columnWidth:0,
 	});
}

	
	function getCheckedFile() {
		var ytjids = "";
		var xbqids = "";
		var xbzids = "";
		var options = g.getData();
		for (var i = 0; i < options.length; i++) {
			var t = document.getElementsByName("checkbox" + i);
			for (var j = 0; j <= t.length - 1; j++) {
				if (t[j].checked && t[j].id == "ytj") {
					if(ytjids==''){
						ytjids = t[j].value;
					}else{
						ytjids += ","+t[j].value;
					}
				} else if (t[j].checked && t[j].id == "xbq") {
					xbqids += t[j].value + ",";
				} else if (t[j].checked && t[j].id == "xbz") {
					xbzids += t[j].value + ",";
				}
			}
		}
		$("#ytjids").val(ytjids);
		$("#xbqids").val(xbqids);
		$("#xbzids").val(xbzids);
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

	function lookFile(DECLAREANNEXGUID) {
		var url = '${ctx}/wssbcl/lookFile?DECLAREANNEXGUID=' + DECLAREANNEXGUID
					+ '&instanceId=' + SPinstanceId + '&itembox=' + itembox;
		url='${ctx}/wssbcl/lookFile?stuffSeq=1&sblsh=cs2017011210001&&version=1';
		Dialog.open({URL:url,Title:"附件列表",Width:650,Height:370});
	}

	function viewZhengzhao(DECLAREANNEXGUID) {
		var url = '${ctx}/scanninghistory/getViewByInstanceid?declareannexguid='
					+ DECLAREANNEXGUID + '&instanceId=' + SPinstanceId;
		Dialog.open({URL:url,Title:"证照",Width:800,Height:600});
	}

	function lookStuffdata(DECLAREANNEXGUID) {
		var url =  '${ctx}/stuff/lookStuffdata?DECLAREANNEXGUID='
					+ DECLAREANNEXGUID + '&instanceId=' + SPinstanceId
					+ '&type=wind';
		Dialog.open({URL:url,Title:"共享材料列表",Width:1000,Height:600});
	}

	function checkedThis(name, obj) {
		var boxArray = document.getElementsByName(obj.name);
		for (var i = 0; i <= boxArray.length - 1; i++) {
			if (boxArray[i] == obj && obj.checked) {
				boxArray[i].checked = true;
			} else {
				boxArray[i].checked = false;
			}
		}
	}

	function showEform(listid, viewid, flowinstance_guid) {		
		$.post("${ctx}/eform/show",function(data){
	  		var url = data.showEformUrl+"?temp_Id="+viewid+"&edittype=1&insGuid="+flowinstance_guid+"&listguid="+listid;
	  		Dialog.open({URL:url,Title:"显示表单",Width:900,Height:500});
        });	
	}
</script>
</html>