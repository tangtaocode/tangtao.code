<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv=X-UA-Compatible content="IE=10">
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
<script type="text/javascript">
	$(function(){
		var msg = $("#message").val();
		if(msg!=''){
			alert(msg);
		}
	})
</script>
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
    	
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
                            { display: '材料名称', name: 'DECLAREANNEXNAME', align: 'center', width: "19%"},
			                { display: '材料标准', name: 'DECLAREANNEXDESC', align: 'center', width: "15%"},
			                { display: '附件', name: 'PUTONG', align: 'center', width: "9%",
			                	render: function (rowdata, rowindex, value, column){
		                		    if(rowdata.PUTONG=='checked'){
		                		    	return '<a href="javascript: void(0);" onclick="openFile(\''+rowdata.DECLAREANNEXGUID+'\')"  class="aColor" >查看附件</a>';
				                		 	
		                		    }
		                		 }	
			                },
			                { display: '证照', name: 'ZHENGZHAO', align: 'center', width: "9%", 
			                	render: function (rowdata, rowindex, value, column){
			                		if(rowdata.ZHENGZHAO=='checked') {
			                			return '<a onclick="viewZhengzhao(\''+rowdata.DECLAREANNEXGUID+'\')" class="aColor">查看证照</a>';
			                		}
			                	}
			                },
			                { display: '共享材料', name: 'STUFFDATA', align: 'center', width: "9%", 
			                	render: function (rowdata, rowindex, value, column){
			                		if(rowdata.STUFFDATA=='checked') {
			                			return '<a onclick="lookStuffdata(\''+rowdata.DECLAREANNEXGUID+'\')" class="aColor">共享材料</a>';
			                		}
			                	}
			                },
			                { display: '表单', name: 'EFORM', align: 'center', width: "9%", 
			                	render: function (rowdata, rowindex, value, column){
			                		if(rowdata.EFORM=='checked') {
			                			return '<a onclick="showEform(\''+ rowdata.DECLAREANNEXGUID+ '\',\''+ rowdata.VIEWID+ '\',\''+ rowdata.WORKFLOWINSTANCE_GUID+'\')" class="aColor">电子表单</a>';
			                		}
			                	}
			                },
			                { display: '已提交', name: 'SUBMISSION', align: 'center', width: "8%",
			                	render: function (rowdata, rowindex, value, column){
			                		  return '<input type="checkbox"  class="checkbox" id="ytj" name="checkbox'+rowindex+'" onclick="checkedThis(a1,this)"   ' + rowdata.SUBMISSION + '   value="'+rowdata.DECLAREANNEXGUID+'">'
                               }	
			                },
			                { display: '需补齐', name: 'NOTSUBMISSION',align: 'center',width: "10%",
			                	render: function (rowdata, rowindex, value, column){
		                                return '<input type="checkbox" class="checkbox" id="xbq" name="checkbox'+rowindex+'" onclick="checkedThis(a2,this)"  ' + rowdata.NOTSUBMISSION + '   value="'+rowdata.DECLAREANNEXGUID+'">'
		                       }	
			                },
			                { display: '需补正', name: 'REJIGGER',align: 'center',width: "10%",
			                	 render: function (rowdata, rowindex, value, column){
		                                return '<input type="checkbox" class="checkbox" id="xbz" name="checkbox'+rowindex+'" onclick="checkedThis(a3,this)"  ' + rowdata.REJIGGER + '   value="'+rowdata.DECLAREANNEXGUID+'">'
		                          }			
			                }
			                
		         ], 
		        url:'${ctx}/onlineApprove/approveMaterialList?instanceId=${instanceId}&formname=${formname}',usePager:false,
		        groupColumnName: "DECLAREANNEXTYPENAME", groupColumnDisplay: "材料类别",percentWidthMode:true,
		        height: '76%', width:"100%",fixedCellHeight:false,rownumbers:true ,frozen:false,columnWidth:0,
         	});
		}
	function openFile(declareguid){
    	var instanceId = $("#instanceId").val();
    	//弹出办事指南
    	Dialog.open({URL:"${ctx}/onlineApprove/openFile?instanceId="+instanceId+"&declareGuid="+declareguid,Title:"附件",Width:700,Height:500});
    }
	
	function showEform(listid,viewid,flowinstance_guid){
		$.post("${ctx}/eform/show",function(data){				
	        	Dialog.open({URL:data.showEformUrl+"?temp_Id="+viewid+"&edittype=1&insGuid="+flowinstance_guid+"&listguid="+listid,Title:"电子表单",Width:1000,Height:600});	        	
	        });		
	}
	
	function lookStuffdata(DECLAREANNEXGUID){
		var instanceId = $("#instanceId").val();
		Dialog.open({URL:"${ctx}/stuff/lookStuffdata?DECLAREANNEXGUID="+DECLAREANNEXGUID+"&instanceId="+instanceId+"&type=online",Title:"共享材料列表",Width:1000,Height:600});
	}
	
	function viewZhengzhao(DECLAREANNEXGUID) {
		var instanceId = $("#instanceId").val();
		Dialog.open({URL:"${ctx}/scanninghistory/getViewByInstanceid?declareannexguid="+DECLAREANNEXGUID+"&instanceId="+instanceId,Title:"证照列表",Width:800,Height:600});
	}
	 function moren(){
	    	var listdata = g.getData();
			for(var i=0;i<=listdata.length-1;i++){
				var t = document.getElementsByName("checkbox"+i);
				 for(var j=0;j<=t.length-1;j++){
					 if(t[j].checked && t[j].id=="ytj"){ 
						 ytjids += t[j].value+",";
					 }else if(t[j].checked && t[j].id=="xbq"){
						 xbqids += t[j].value+",";
					 }else if(t[j].checked && t[j].id=="xbz"){
						 xbzids += t[j].value+",";
					 }
				 }
			}
	    }	
		
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
     	function gxcailiao(){
    	Dialog.open({URL:"${ctx}/onlineApprove/gxcailiao?userid=${userid}",Title:"共享证照",Width:1000,Height:500});
    	
    }
    
    function onbszn(id){
    	//弹出办事指南
    	Dialog.open({URL:"${ctx}/onlineApprove/approveGuide?instanceId="+id,Title:"办事指南",Width:900,Height:600});
 
    }
    
    function onYSL(id,status){
    	var isbqbzsl = "";
    	//判断补齐补正是否受理
    	var isBqbz = $("#isBqbz").val();
    	if(isBqbz>0){
    		$.post("${ctx}/onlineApprove/isBqbzsl",{'instanceId':id},function(data){
        		if(data=='0'){//未受理
        			$("#isbqbzsl").val(data);
        		}
        	})
    	}
    	//预受理
    	moren();
    	qing_repeat();
    	$("#instanceId").val(id);
    	$("#status").val(status);
    	$("#ytjs").val(ytjids);
    	$("#xbqs").val(xbqids);
    	$("#xbzs").val(xbzids);
    	$("#subForm").submit();
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
    
    
    function reply(id) {
    	var isBqbz = $("#isBqbz").val();
    	if(isBqbz > 0){
    		alert("此业务已经发起过补齐补正！");
    		return;
    	}
    	var text = document.getElementById("fillArea").value;
    	if(text==null || text.length==0){
    		alert("请填写回复意见！");
    		return false;
    	}
    	
    	moren();
      	qing_repeat();
      	if(xbqids==''&&xbzids==''){
    		alert("请勾选需补齐补正的材料！");
    		return false;
    	}
      	document.forms[0].action = "${ctx}/onlineApprove/replyAndapproveMaterial?feedback="+text+"&ytjids="+ytjids+"&xbqids="+xbqids+"&xbzids="+xbzids+"&instanceId=${instanceId}";
    	//id="{0A150177-FFFF-FFFF-9A8C-FDA700000002}";
      	var url = "${ctx}/bjgz/adviceForm?feedback="+text+"&method=''&instanceId="+id+"&ytjids="+ytjids+"&xbqids="+xbqids+"&xbzids="+xbzids;
      	Dialog.open({URL:url,Title:"一次性补交告知单",Width:"800",Height:"500"});

	  	//openNewDialog("${ctx}/bjgz/adviceForm?feedback="+text+"&method=''&instanceId="+id+"&ytjids="+ytjids+"&xbqids="+xbqids+"&xbzids="+xbzids,"一次性补交告知单","800","500");
    }
    
    function bjgzRemark(itemid){
    	var url = "${ctx}/bjgz/adviceRemark?approveitemguid="+itemid;
      	Dialog.open({URL:url,Title:"一次性补交告知",Width:"800",Height:"500"});
      	//openNewDialog(url,"一次性补交告知说明","800","500");
    }
  //一次性补交告知通知单
	/* function openInform(id){
	  id="{0A150177-FFFF-FFFF-9A8C-FDA700000002}";
	  //openNewDialog("${ctx}/onlineApprove/adviceForm?instanceId="+id,"一次性补交告知单","800","500");
	  window.open("${ctx}/onlineApprove/adviceForm?instanceId="+id,"一次性补交告知单","800","500");
	} */	
	

    function qing_repeat(){
    	var ytjstr = ytjids.split(",");
		ytjstr.sort();//排序  
        var ytjresult=new Array();//创建出一个结果数组  
        var tempStr="";  
        for(var i in ytjstr)  
        {  
             if(ytjstr[i] != tempStr)  
             {  
            	 ytjresult.push(ytjstr[i]);  
                  tempStr=ytjstr[i];  
             }  
             else  
             {  
                  continue;  
             }  
        }
       	 ytjids = ytjresult.join(",");
       	 
       	var xbqstr = xbqids.split(",");
       	xbqstr.sort();//排序  
        var xbqresult=new Array();//创建出一个结果数组  
        var tempStr1="";  
        for(var i in xbqstr)  
        {  
             if(xbqstr[i] != tempStr1)  
             {  
            	 xbqresult.push(xbqstr[i]);  
                  tempStr1=xbqstr[i];  
             }  
             else  
             {  
                  continue;  
             }  
        }
       	 xbqids = xbqresult.join(",");
       	 
       	 
       	var xbzstr = xbzids.split(",");
       	xbzstr.sort();//排序  
        var xbzresult=new Array();//创建出一个结果数组  
        var tempStr2="";  
        for(var i in xbzstr)  
        {  
             if(xbzstr[i] != tempStr2)  
             {  
            	 xbzresult.push(xbzstr[i]);  
                  tempStr2=xbzstr[i];  
             }  
             else  
             {  
                  continue;  
             }  
        }
       	 xbzids = xbzresult.join(",");
    }
	
   
    
    var fixedObj=80;
    function customHeightSet(contentHeight){
        $("#scrollContent").height(contentHeight-fixedObj);
    }
</script>
	


<title>材料清单</title>
</head>
<body>
  <div class="box2" panelTitle="罗湖区行政审批与服务综合平台" id="searchPanel">
	<div class="box_tool_min padding_top2 padding_bottom2 padding_right5">
	    <div class="center">
		    <div class="left">
			    <div class="right">
			        <div class="padding_top5 padding_left10">
				        <%-- <a href="javascript:void(0);" title="查看办事指南" onclick="onbszn('${instanceId}');"><span class="icon_view">办事指南</span></a> --%>
				        <a href="javascript:void(0);" title="补交告知说明" onclick="bjgzRemark('${approveitemguid}');"><span class="icon_view">一次性补交告知</span></a>
				        <div class="box_tool_line"></div>
			    	</div>      
			    </div>  
		    </div>
	    </div>
	</div>

	<div class="padding_right5" id="scrollContent">
		<form action="${ctx}/sbApprove/approveMaterialList" id="queryForm" method="post" align="center">
			<div id="maingrid"></div>
			
			<input type="hidden" name="isBqbz" id="isBqbz" value="${isBqbz }"/>
			<input type="hidden" name="approveitemguid" id="approveitemguid" value="${approveitemguid }"/>
			<input type="hidden" name="processDefinitionId" id="processDefinitionId" value="${workflowguid }"/>
			<input type="hidden" name="message" id="message" value="${message }"/>
			<table style="width: 100%;" class="tableStyle" formMode="line">
				<c:if test="${feedback!='' || feedback!=null }">
				     <tr>
				         <td width="21.5%;">回复意见
				         </td>
				         <td >
				         	<textarea id='fillArea' style="width:70%;height:100px;">${feedback}</textarea>
				         </td>
				      </tr>
			     </c:if>
			     <c:if test="${result==0 }">
				      <tr align="center">
				      	 <td colspan="2">
				      	 	<input type="button" value="回复"  onclick="reply('${instanceId}')"/>&nbsp;
				      	 	<input type="button" onclick="onYSL('${instanceId}','预受理')" value="预受理"/>
				   	 	</td>
				      </tr>
			    </c:if>
			 </table>
		</form>
		<form action="${ctx}/onlineApprove/approveAdvanceAction" id="subForm" method="POST" align="center">
			<input type="hidden" name="ytjs" id="ytjs" value=""/>
			<input type="hidden" name="xbqs" id="xbqs" value=""/>
			<input type="hidden" name="xbzs" id="xbzs" value=""/>
			<input type="hidden" name="isbqbzsl" id="isbqbzsl" value=""/>
			<input type="hidden" name="processDefinitionKey" value="${workflowguid}"/>
			<input type="hidden" name="instanceId" id="instanceId" value="${instanceId}"/>
			<input type="hidden" name="status" id="status" value=""/>
		</form>
	</div>
</body>

</html>