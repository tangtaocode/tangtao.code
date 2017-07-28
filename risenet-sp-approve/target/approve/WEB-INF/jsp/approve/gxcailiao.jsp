<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>

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


<script type="text/javascript">
var grid;

function initComplete(){

grid = $("#maingrid").quiGrid({

        columns: [
			{ display: 'instanceguid', name: 'INSTANCEGUID' ,hide:true},

            { display: '共享证照名称', name: 'DOCNAME', align: 'left',width:"30%",editor: { type: 'text',tip:'请输入共享名称'} },

            //{ display: '是否永久有效', name: 'orgName' ,width:"10%"},

            { display: '有效期开始日期', name: 'PRODUCEDATE' ,align: 'center',width:"15%",type:"date",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}},

            { display: '有效期结束日期', name: 'ZZYXQ' ,align: 'center',width:"15%",type:"date",editor: { type: 'date',dateFmt:'yyyy-MM-dd'}},

            //{ display: '状态', name: 'orgPhone' ,width:"10%"},

            //{ display: '认证者', name: 'parentId' ,width:"10%"},
            //{ display: '认证时间', name: 'parentId' ,width:"10%"},
            { display: '操作', name: 'parentId' ,width:"15%",
            	 render: function (rowdata, rowindex, value, column){
            		 var view = "";
            		 if (!rowdata._editing)
	                    {
	                    	view += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> "; 
	                    	view += "<a onclick='beginEdit(" + rowindex + ")'><span class='icon_edit'>修改</span></a> ";
	                       
	                    }
	                    else
	                    {
	                    	view += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> ";
	                    	view += "<a onclick='endEdit(" + rowindex + ")'><span class='icon_ok'>提交</span></a> ";
	                    	view += "<a onclick='cancelEdit(" + rowindex + ")'><span class='icon_no'>取消</span></a> "; 
	                       
	                    }
	                    return view;
            	}
            
            }

        ],   

       
        //isScroll:false表示不出现滚动条，高度自适应。 frozen:false表示列宽不固定，可通过拖拽进行调整。

        isScroll: false, frozen:false,url: '${ctx}/onlineApprove/gxcailiaoList?cardId=${cardId}&declareannexguid=${declareannexguid}',width:'100%', columnWidth:300,
        enabledEdit: true,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
            g.beginEdit(rowindex);
          }, onBeforeSubmitEdit: onBeforeSubmitEdit,onAfterSubmitEdit: onAfterSubmitEdit,
        detail: { onShowDetail: showEmployees, height: 'auto' },

        onError: function (a, b){

 
            //错误事件

        }

    });

};

//编辑
function beginEdit(rowid) { 
       grid.beginEdit(rowid);
   }
//取消编
function cancelEdit(rowid) { 
    grid.cancelEdit(rowid);
}
//结束编辑
function endEdit(rowid)
{
    grid.endEdit(rowid);

    var row = grid.getRow(rowid)
  	$.post("${ctx}/onlineApprove/updateGXZZ",{"instanceguid":row.INSTANCEGUID,"docname":row.DOCNAME,"producedate":row.PRODUCEDATE,"zzyxq":row.ZZYXQ},function(result){
			handleResult1(result);
			//刷新表格
			grid.loadData();
		},"json"); 
    

		/* //刷新表格
		grid.loadData(); */
		

}
//全部确认修改
function endAllEdit()
{
	 grid.endEdit();
}




//编辑提交前事件 
function onBeforeSubmitEdit(e){

	if(e.newdata.DOCNAME==""){      
        Dialog.alert("共享证照名称不能为空！",null,null,null,20);
        return false;
   }else if (!validateInput(e.newdata.DOCNAME, "^[\u4e00-\u9fa5]+$")){
        Dialog.alert("共享证照名称需要是中文！",null,null,null,20);
        return false
   }
}
//编辑提交前事件 
function onAfterSubmitEdit(e){

}









//显示附件列表

function showEmployees(row, detailPanel,callback){
$.post('${ctx}/onlineApprove/fujianList?instanceguid='+row.INSTANCEGUID,

{parentId:row.orgId},function(result){

     if(result.rows.length > 0){

         var childGrid = document.createElement('div'); 

            $(detailPanel).append(childGrid);

            $(childGrid).css('margin',10).quiGrid({

                 columns: [

				 { display: 'fileguid', name: 'FILEGUID',hide:true},


                 { display: '附件名称', name: 'FILENAME',     align: 'left',width:"40%",
                	 render: function(rowdata, rowindex, value, column){
      					ret = '<a href="${ctx}/onlineApprove/download?id=' + rowdata.FILEGUID + '">' + rowdata.FILENAME + '</a>';
      					//ret = '<a href="javascript:void(0);" onclick="showFile(\''+rowdata.GUID+'\');">' + rowdata.FILENAME + '</a>';
      					return ret;
      				}
                 
                 },


                // { display: '文件类型', name: 'FILETYPE',  align: 'left'},


                 { display: '上传时间', name: 'CREATEDATE', align: 'left',width:"15%"},

                 { display: '上传时间', name: 'CREATEDATE', align: 'center',width:"20%"},


                // { display: '排序号', name: 'userEmployTime',    align: 'left'} ,


                 { display: '操作', name: 'userEducation',     align: 'left',width:"15%"},

                 { display: '操作', name: 'userEducation',     align: 'center',width:"20%",

                	
                	 render: function (rowdata, rowindex, value, column){
                		 var view = "";


    	                  view += '<span class="icon_delete" title="删除" onclick="onDeleteFJ(\'' + rowdata.FILEGUID + '\')">删除</span>';
    	                    	
    	                 return view;
                	}
                 
                 } 

                 ], 

                 isScroll: false,width: '95%', columnWidth: 120,

                 data: result,

                 //onAfterShowData可以自定义回调

                 onAfterShowData: callback, pageSize: 5

            });  

     }

})

}

//删除共享证照
function onDelete(rowidx){
	

	  	
	  	Dialog.confirm("确定要删除该记录吗？",function(){
		  	//删除记录
		  	var row = grid.getRow(rowidx)
		  	$.post("${ctx}/onlineApprove/deleteGXZZ",{"instanceguid":row.INSTANCEGUID},function(result){
		  			handleResult1(result);
					},"json");
					//刷新表格
					grid.loadData();
			});
		
}

//删除附件
function onDeleteFJ(FILEGUID){
	Dialog.confirm("确定要删除该记录吗？",function(){

	  	$.post("${ctx}/onlineApprove/onDeleteFJ",{"fileguid":FILEGUID},function(result){

	  			handleResult1(result);
				},"json");
				//刷新表格
				grid.loadData();
		});
}



</script>
	


<title>共享材料列表</title>
</head>
<body>
<div class="padding_right5">

    <div id="maingrid"></div>

</div>

</body>
</html>