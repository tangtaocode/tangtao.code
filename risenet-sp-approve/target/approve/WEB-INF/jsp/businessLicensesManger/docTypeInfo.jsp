<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>证照类别管理</title>
<script type="text/javascript">
		var doguid = $("#guid").attr("value");
        var g;
        var glData={"list":[{"value":"","key":""},{"value":"{0A150095-FFFF-FFFF-F6C5-C17F00000A01}","key":"证照编号"},{"value":"{0A150095-FFFF-FFFF-F6C5-C17F00000A02}","key":"证照有效期"},{"value":"{0A150095-FFFF-FFFF-F6C5-C17F00000A03}","key":"发（出）证日期"},{"value":"{0A150095-FFFF-FFFF-F6C5-C17F00000A04}","key":"发证单位"},]};
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
							{ display: '节点信息英文(比如：证件编号输入zjbh)', name: 'INFOCODE', align: 'center', width:"25%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
			                { display: '', name: 'GUID', align: 'center', width: "0%",hide:true},
			                { display: '', name: 'DOCTYPEGUID', align: 'center', width: "0%",hide:true},
			                { display: '节点信息中文(比如：证件编号)', name: 'INFOMEMO', align: 'center', width:"20%",editor: { type: 'text',maxlength:20,tip:'不能超过20个字' }},
			                { display: '关联必填字段', name: 'GLGUID', align: 'center', width: "30%",editor: { type: 'select',data:glData},
			                	render:function (item){
		                        for (var i = 0; i < glData["list"].length; i++)
		                        {
		                            if (glData["list"][i]['value'] == item.GLGUID)
		                                return glData["list"][i]['key']
		                        }
		                        return item.GLGUID;
		                    }},
			                { display: '操作', align: 'center', width: "20%",showTitle:false,
			                	render: function (rowdata, rowindex, value, column){
			                		 var h = "";
					                    if (!rowdata._editing)
					                    {
					                         h += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> "; 
					                        h += "<a onclick='beginEdit(" + rowindex + ")'><span class='icon_edit'>修改</span></a> ";
					                       
					                    }
					                    else
					                    {
					                         h += "<a onclick='onDelete(" + rowindex + ")'><span class='icon_delete'>删除</span></a> ";
					                        h += "<a onclick='endEdit(" + rowindex + ")'><span class='icon_ok'>提交</span></a> ";
					                        h += "<a onclick='cancelEdit(" + rowindex + ")'><span class='icon_no'>取消</span></a> "; 
					                       
					                    }
					                    return h;
			                	}  	 
			                }
		         ], 
		        url:"${ctx}/doctypeinfo/findAllByGuid?docGuid=${guid}",sortName:'GUID',rownumbers:true,percentWidthMode:true,checkbox:false,
		        height: '100%', width:"100%",enabledEdit: true,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
	                  g.beginEdit(rowindex);
	                }, onBeforeSubmitEdit: onBeforeSubmitEdit,onAfterSubmitEdit: onAfterSubmitEdit,
		        toolbar:{
		       	 items:[
		       		  {text: '新增一行', click: addUnit,    iconClass: 'icon_add'},
		       		  { line : true },
		       		  {text: '整体提交', click: endAllEdit, iconClass: 'icon_save'},
		    		  { line : true },
		    		  {text: '返回', click: fanhui, iconClass: 'icon_back'},
		    		  { line : true }
		        		]
		        	}
         	});
		}
		 //编辑
    function beginEdit(rowid) { 
           g.beginEdit(rowid);
       }
  //取消编
    function cancelEdit(rowid) { 
        g.cancelEdit(rowid);
    }
    //结束编辑
    function endEdit(rowid)
    {
        g.endEdit(rowid);
    }
    //全部确认修改
    function endAllEdit()
    {
    	 g.endEdit();
    }


		//删除	
	function onDelete(rowidx){
		Dialog.confirm("确定要删除该记录吗？",function(){
		  	//删除记录
		  	var row = g.getRow(rowidx)
		  	$.post("${ctx}/doctypeinfo/deleteDoc",{"guid":row.GUID},function(result){
		  			handleResult(result);
					},"json");
					//刷新表格
					g.loadData();
			});
	}
	//添加
	function addUnit() {		
		var firstRow = g.getRow(0);
		var rowData={
			INFOCODE:"",
			INFOMEMO:"",
			GLGUID:""
		}
		 g.addEditRow(rowData, firstRow, true);
		 //g.addRow(rowData, firstRow, true);
		 //在这里做新增处理
		$.post("${ctx}/doctypeinfo/save",rowToBO(rowData),function(result){
			  	if(result.id ==0 || result.id == ''){
			  		Dialog.alert(result.msg);
		    	}
		    	g.getRow(0).GUID = result.id;
          },"json"); 
	}
	function fanhui(){
		history.go(-1);
	}
	 //将row JSON对象转化为bo对象
     function rowToBO(row) {
        var params = '&guid='+row.GUID+'&infocode='+row.INFOCODE+'&infomemo='+row.INFOMEMO+'&glguid='+row.GLGUID+'&doctypeguid=${guid }';
        return params;
    } 


	 //编辑提交前事件 
    function onBeforeSubmitEdit(e){
    	if(e.newdata.INFOCODE==""){
                Dialog.alert("节点信息英文不能为空！",null,null,null,20);
                return false;
        }else if (!validateInput(e.newdata.INFOCODE, "^[A-Za-z0-9]+$")){
                Dialog.alert("材料定义编码为数字或字母！",null,null,null,20);
                return false;
        }	
    	if(e.newdata.INFOMEMO==""){      
             Dialog.alert("节点信息中文不能为空！",null,null,null,20);
             return false;
        }else if (!validateInput(e.newdata.INFOMEMO, "^[\u4e00-\u9fa5]+$")){
             Dialog.alert("节点信息中文需要是中文！",null,null,null,20);
             return false
        }
	 }
  //编辑后事件 
    function onAfterSubmitEdit(e) {
    	 //在这里一律作修改处理
        var rowData = e.newdata;
    	rowData.GUID = e.record.GUID;
        $.post("${ctx}/doctypeinfo/update",rowToBO(rowData),function(result){         
              Dialog.alert(result.msg);
        },"json");
    }
	//删除后的提示

	function handleResult(result){	   
	     Dialog.alert("删除成功！");
	     g.loadData();
	}		
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
 
</script>	
</head>
<body>
		<input type="hidden" name="guid" id="guid" value="${guid }"></input>
		<table>
				<tr align="center">
					<td hight="28" colspan="5" class="title_center" align="center">
						<font style="font-size: 18px; font-weight: 700;">证照类型${docname }信息项管理</font>
					</td>
				</tr>
		</table>
	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>

</body>
</html>
