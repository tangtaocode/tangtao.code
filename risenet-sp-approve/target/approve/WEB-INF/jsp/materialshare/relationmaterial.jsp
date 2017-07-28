<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=Edge">
<title>关联材料共享</title>
<!-- 布局 -->
<script type="text/javascript"	src="${ctx}/static/QUI/libs/js/nav/layout.js"></script>
<!--树组件start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/tree/ztree/ztree.js"></script>
<link href="${ctx}/static/QUI/libs/js/tree/ztree/ztree.css" rel="stylesheet" type="text/css" />
<!-- 树形下拉框start -->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/selectTree.js"></script>
<!-- 树形下拉框end -->
<!--父子表start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--父子表end-->
<!--树组件end -->
<!-- 选项卡-->
<script type="text/javascript"	src="${ctx}/static/QUI/libs/js/nav/basicTab.js"></script>
<!-- 选项卡end-->
<script type="text/javascript">

    //初始化得到菜单树
	function initComplete() {
		var layout = $("#layout1").layout({
			leftWidth : 200,
			rightWidth : 150
		});
		layout.setRightCollapse(true);
		//远程得到树
		$.post("${ctx}/certmanage/businessTree", {}, function(result) {
			//console.info(result.treeNodes);
			$.fn.zTree.init($("#tree-1"), setting1, result.treeNodes);
		}, "json");
	}
	var setting1 = {
		callback : {
			onClick : clickNode
		}
	};
	
	function customHeightSet(contentHeight){

	    $(".layout_content").height(contentHeight-5)

	}
	
	function clickNode(event, treeId, treeNode) {
		var zTree = $.fn.zTree.getZTreeObj("tree-1");
		//Dialog.alert(treeNode.id);
		itemid = treeNode.id;
		grid(treeNode.id);
		typeManager(itemid);
	}
	
	
	//材料分类类别
	 var grid1;
     var gridArray=[];
	 function typeManager(itemid){
	 	 grid1 = $("#grid1").quiGrid({
            columns: [
                { display: 'typeguid', name: 'TYPEGUID' ,hide:true},
                { display: '版本号', name: 'BANBEN',hide:true},
                { display: '序号', name: 'ROWNUM' ,hide:true},
                { display: '材料类别', name: 'TYPENAME' , width: 600 ,editor: { type: 'textarea' } },
                { display: '排序号', name: 'ORDERNO' , width: 300 ,editor: { type: 'text' }},
                { display: '操作', name: '' , width: 200,
                	render: function (rowdata, rowindex, value, column){
                  		 var hi = "";
       	                    if (!rowdata._editing)
       	                    {
       	                        hi += "<a onclick='onDelete1(" + rowindex + ")'><span class='icon_delete'>删除</span></a> "; 
       	                        hi += "<a onclick='beginEdit1(" + rowindex + ")'><span class='icon_edit'>修改</span></a> ";
    	                    }
       	                    else
       	                    {
       	                        hi += "<a onclick='onDelete1(" + rowindex + ")'><span class='icon_delete'>删除</span></a> ";
       	                        hi += "<a onclick='endEdit1(" + rowindex + ")'><span class='icon_ok'>提交</span></a> ";
       	                        hi += "<a onclick='cancelEdit1(" + rowindex + ")'><span class='icon_no'>取消</span></a> "; 
                       }
       	                    return hi;
                  }
                }
            ],   
            //isScroll:false表示不出现滚动条，高度自适应。 frozen:false表示列宽不固定，可通过拖拽进行调整。
            isScroll:true,frozen:false,url: "${ctx}/relationmaterial/table",params:[{name:"itemid",value:itemid}],width:'100%',height:'100%', usePager:false,detailCloseOther:false,
            enabledEdit: true,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
            	//alert(rowdata.BANBEN);
            	grid1.beginEdit(rowindex);

            },onBeforeSubmitEdit: onBeforeSubmitEdit1,onAfterSubmitEdit: onAfterSubmitEdit1,
            detail: { onShowDetail: showEmployees, height: 'auto' },
            onError: function (a, b){
                //错误事件
            },
            toolbar:{
	        	 items:[
                      {text: '新增', click: addUnit1,    iconClass: 'icon_add'},
	                   { line : true },
	        		  {text: '保存',  click: materialcheck ,  iconClass: 'icon_import'},
	        		]
	         	}
        });
	}

    //显示该类别下材料明细
    function showEmployees(row, detailPanel,callback){
    	
    	var childGrid = document.createElement('div'); 
        $(detailPanel).append(childGrid);
        var childGrid=$(childGrid).css('margin',10).quiGrid({
             columns: [
                { display: '主键', name: 'ID',  hide:true}, 
                   
				{ display: '序号', name: 'ORDERNO',  align: 'center', width: '5%'},
				{display: '',name: 'CHECK',align: 'center', width: '5%',
					render: function (rowdata, rowindex, value, column){
	      		  		return '<input type="checkbox"  class="checkbox" id="mgx" name="MCHECK"  value="'+rowdata.ID+"_"+row.TYPEGUID+'" '+rowdata.CHECK+'>'
	            	}	
				},
				{ display: '材料名称', name: 'MATERIALNAME', 	 align: 'left', width: '90%'}		
             ], 
             fixedCellHeight:'flase',width: '1000px',rowHeight:'aotu',checkbox:false,
             url: '${ctx}/relationmaterial/materialtable?itemid='+row.ITEMID+'&typeguid='+row.TYPEGUID,
             usePager:false           
        });  
        var obj={};
        obj.id=row.ITEMID;
        obj.grid=childGrid;
        gridArray.push(obj);
      }
   
    
	//保存材料类别勾选状态
	function materialcheck(rowindex) {	
		var row=grid1.getRow(0);
		var griddata =JSON.stringify(row);
		var griddata1 =JSON.parse(griddata);
        var verdata = griddata1.BANBEN;
        var svaedata ="";
         //获取勾选信息
		  var t = document.getElementsByName("MCHECK");
		  var checkid="";
			 for(var j=0;j<=t.length-1;j++){
				 if(t[j].checked){ 
					checkid += t[j].value+",";					
				 }
			 }

	     svaedata = "checkid="+checkid +"&itemid="+itemid + "&version="+verdata;
		 //在这里做保存处理
		$.post("${ctx}/relationmaterial/savecheck?"+svaedata,function(result){
			
			Dialog.alert(result.msg);
	    	
		    	childGrid.loadData();
          },"json"); 

		  

	  }

  	
  //材料明细管理
	var typeData={"list":[{"value":"0","key":"扫描件"},{"value":"1","key":"申请表格"},{"value":"2","key":"其它材料"}]};
	/* var departNode = {"treeNodes":[
	                            { id:1, parentId:0, name:"界面设计"},
	                            { id:2, parentId:0, name:"程序开发"},
	                            { id:3, parentId:0, name:"市场调研"},
	                            { id:4, parentId:0, name:"质量监督"},
	                            { id:5, parentId:0, name:"产品测试"}
	                        ]};  */
	function grid(itemid) {
		g = $("#maingrid").quiGrid({
			columns : [
		{display : '材料名称',name : 'MATERIALNAME',align : 'left',width : "20%",editor: { type: 'textarea',tip:'请输入材料名称'} }, 
		{display : '材料描述',name : 'DESCRIBE',align : 'left',width : "20%",editor: { type: 'textarea',tip:'请输入材料描述' }},
			{display : '材料类型',name : 'MATERIALLX',align : 'center',width : "8%",editor: { type: 'select',data:typeData },
				render:function (item){
	                for (var i = 0; i < typeData["list"].length; i++)
	                { 
	                    if (typeData["list"][i]['value'] == item.MATERIALLX)
	                        return typeData["list"][i]['key']
	                }
	                return item.MATERIALLX;
            	}
			},
			/* {display : '主键',name : 'ID',align : 'center',width : '',hide:true},  */
			/* {display : '版本号',name : 'VERSION',align : 'center',width : '',hide:true}, */
			{display : '关联共享材料',name : 'STUFFDEFINENAME' ,align : 'center',width : "20%", minWidth: '200',
			render: function (rowdata, rowindex, value, column){
          		      var st = "";
          		      var stuff = "";
	                    if (rowdata.STUFFDEFINENAME!=null&&rowdata.STUFFDEFINENAME!="")
	                    {
	                        
	                    	stuff= rowdata.STUFFDEFINENAME;
	                    }

	                    st = "<input type='text' value='"+ stuff +"' size='15'  name='stuffdefine' id='STUFFDEFINENAME"+ rowdata.ID +"'/>"; 
                        st += "&nbsp;<a onclick='onstufftree(" + rowindex + ")'><span class='icon_add'>&nbsp;</span></a> ";

	                    return st;
			  }
			},
			{display : '关联证照',name : 'DOCTYPENAME' ,align : 'center',width : "20%", minWidth: '200',
				render: function (rowdata, rowindex, value, column){
        		      var dt = "";
        		      var docname = "";
        		      var docid = "";
	                    if (rowdata.DOCTYPENAME!=""&&rowdata.DOCTYPENAME!=null&&rowdata.DOCTYPENAME!="undefined")
	                    {
	                    	docid= rowdata.DOCTYPEGUID;
	                    	docname= rowdata.DOCTYPENAME;
	                    }
	                    dt = "<input type='text' value='"+ docname +"' size='15'  name='doctype' id='doctype"+ rowdata.ID +"'/>"; 
                        dt += "&nbsp;<a onclick='ondoctree(" + rowindex + ")'><span class='icon_add'>&nbsp;</span></a> ";

	                    return dt;
			  }
			}
			/*,
			
			 {display : '排序号',name : 'ORDERNO',align : 'center',width : '', hide: true,editor: { type: 'text',maxlength:4,tip:'请输入数字' }}, */
			/* {display : '操作',align : 'center',width : '', hide:true,
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
	            } */
			],
			//pageSize默认为10，可以不配置
			url:"${ctx}/relationmaterial/materialinfo",params:[{name:"itemid",value:itemid}],sortName:'ORDERNO',sortOrder:'desc',rownumbers:true,percentWidthMode:true,checkbox:false,
			isScroll:false,frozen:true,usePager:false,pageSize:20,enabledEdit: true,clickToEdit: false,onDblClickRow:function(rowdata, rowindex){
                g.beginEdit(rowindex);
            }, onBeforeSubmitEdit: onBeforeSubmitEdit,onAfterSubmitEdit: onAfterSubmitEdit,
	        height: '100%', width:"99%"
	       /*  ,
	        toolbar:{
		       	 items:[
		       		  {text: '新增', click: addUnit,    iconClass: 'icon_add'},
		       		  { line : true }
		        		]
		        	} */
		});
	}
	                        
	                        
	  //关联材料共享树 
	function onstufftree(rowid){
		
		var gdata =JSON.stringify(g.getRow(rowid));
		var gdata1 =JSON.parse(gdata);
		  
		var diag = new Dialog();
		diag.Title = "请选择关联材料";
		diag.URL = "${ctx}/relationmaterial/stufftree?ID="+gdata1.ID+"&CLTYPEID="+itemid;
		diag.show();

	  }                    
	   
	//关联证照树 
	function ondoctree(rowid){
		var gdata =JSON.stringify(g.getRow(rowid));
		var gdata1 =JSON.parse(gdata);
		var diag1 = new Dialog();

	    diag1.URL = "${ctx}/relationmaterial/doctypetree?ID="+gdata1.ID;
	    diag1.show();
	
	} 


	  
	//编辑
    function beginEdit(rowid) { 
           g.beginEdit(rowid);
       }

  //取消编辑
    function cancelEdit(rowid) { 
        g.cancelEdit(rowid);
    }
  //结束编辑
    function endEdit(rowid)
    {
    	//alert("结束编辑!");
        g.endEdit(rowid);
    }
  
	//材料分类编辑1
    function beginEdit1(rowid) {
    	grid1.beginEdit(rowid);
       }
    //取消材料分类编辑1
    function cancelEdit1(rowid) { 
    	grid1.cancelEdit(rowid);
    }
  //结束材料分类编辑1
    function endEdit1(rowid)
    {
	  grid1.endEdit(rowid);
    }
	//添加
	function addUnit() {		
		var firstRow = g.getRow(0);
		var gdata =JSON.stringify(firstRow);
		var gdata1 =JSON.parse(gdata);
		var rowData={
			ITEMID:itemid,
			VERSION:gdata1.VERSION,
			TYPENAME:"",
			ORDERNO:""
		}
		 g.addEditRow(rowData, firstRow, true);
		 //g.addRow(rowData, firstRow, true);
		 //在这里做新增处理
		$.post("${ctx}/relationmaterial/rmsave",rowData,function(result){
			  
		    	g.getRow(0).ID = result.id;
          },"json");
	}
	
	//将row JSON对象转化为bo对象
    function rowToBO(row) {
       var params = '&ID='+row.ID+'&STUFFDEFINENAME='+row.STUFFDEFINENAME+'&MATERIALLX='+row.MATERIALLX+'&MATERIALNAME='+row.MATERIALNAME+'&DESCRIBE='+row.DESCRIBE+'&ORDERNO='+row.ORDERNO+'&DOCTYPENAME='+row.DOCTYPENAME+'&DOCTYPEGUID='+row.DOCTYPEGUID;
       return params;
   } 
	 //编辑提交前事件 
    function onBeforeSubmitEdit(e){
    	if(e.newdata.MATERIALNAME==""){
                Dialog.alert("材料信息不能为空！",null,null,null,20);
                return false;
        }
    	
      /* if(e.newdata.DESCRIBE==""){      
             Dialog.alert("材料描述不能为空！",null,null,null,20);
             return false;
        }else if (!validateInput(e.newdata.DESCRIBE, "^[\u4e00-\u9fa5]+$")){
             Dialog.alert("材料描述需要是中文！",null,null,null,20);
             return false
        } */
    	if(e.newdata.ORDERNO==""){      
            Dialog.alert("排序号不能为空！",null,null,null,20);
            return false;
       }else if (!validateInput(e.newdata.ORDERNO, "^[0-9]*$")){
            Dialog.alert("排序号必须是数字！",null,null,null,20);
            return false
       }

	 }
  //编辑后事件 
    function onAfterSubmitEdit(e) {
    	 //在这里一律作修改处理
        var rowData = e.newdata;
    	rowData.ID = e.record.ID;
        $.post("${ctx}/relationmaterial/rmupdate",rowToBO(rowData),function(result){         
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
	//删除	
	function onDelete(rowidx){
		Dialog.confirm("确定要删除该记录吗？",function(){
		  	//删除记录
		  	var row = g.getRow(rowidx)
		  	$.post("${ctx}/relationmaterial/delete",{"id":row.ID},function(result){
		  			handleResult(result);
					},"json");
					//刷新表格
					g.loadData();
			});
	}
	
	
	 //材料分类编辑提交前事件 
    function onBeforeSubmitEdit1(e){
    	if(e.newdata.TYPENAME==""){
                Dialog.alert("材料信息不能为空！",null,null,null,50);
                return false;
        }
    	if(e.newdata.ORDERNO==""){      
            Dialog.alert("排序号不能为空！",null,null,null,20);
            return false;
       }else if (!validateInput(e.newdata.ORDERNO, "^[0-9]*$")){
            Dialog.alert("排序号必须是数字！",null,null,null,20);
            return false
       }

	 }
	 
	//材料分类编辑将row JSON对象转化为bo对象
    function rowToBO1(row) {
       var params = '&TYPEGUID='+row.TYPEGUID+'&BANBEN='+row.BANBEN+'&TYPENAME='+row.TYPENAME+'&ORDERNO='+row.ORDERNO;
       return params;
   } 
	 //材料分类编辑后事件 
    function onAfterSubmitEdit1(e) {
    	 //在这里一律作修改处理
        var rowData = e.record;
    	rowData.TYPEGUID = e.record.TYPEGUID;
        $.post("${ctx}/relationmaterial/materialupdate",rowToBO1(rowData),function(result){         
              Dialog.alert(result.msg);
        },"json"); 
        grid1.loadData();
    }
	 
	//材料分类删除后的提示

	function handleResult1(result){	   
	     Dialog.alert("删除成功！");
	     grid1.loadData();
	}		
    //刷新表格 表单提交的回调
    function afterFormSubmit1(){
        grid1.loadData();
    }
	//材料分类删除	
	function onDelete1(rowidx){
		Dialog.confirm("确定要删除该项吗？",function(){
		  	//删除记录
		  	var row = grid1.getRow(rowidx)
		  	$.post("${ctx}/relationmaterial/materialdelete",{"TYPEGUID":row.TYPEGUID,"BANBEN":row.BANBEN},function(result){
		  			handleResult1(result);
					},"json");
					//刷新表格
					grid1.loadData();
			});
	}
	
	
	//材料分类新增
	function addUnit1() {	
		var row=grid1.getRow(0);
		var griddata =JSON.stringify(row);
		var griddata1 =JSON.parse(griddata);
        //复制第一行数据到新增行
		var rowData={
			ITEMID:itemid,
			TYPENAME:griddata1.TYPENAME,
			ORDERNO:griddata1.ORDERNO,
			VERSION:griddata1.BANBEN
		}
		 grid1.addEditRow(rowData, row, row);
		 //g.addRow(rowData, firstRow, true);
		 //在这里做新增处理
		$.post("${ctx}/relationmaterial/materialsave",rowData,function(result){
			  	if(result.id ==0 || result.id == ''){
			  		Dialog.alert(result.msg);
		    	}
		    	grid1.getRow(0).TYPEGUID = result.typeguid;
		    	grid1.getRow(0).BANBEN = result.banben;
          },"json");
	}
	
	
	 //只刷新当前子表
    function refreshChild(isUpdate,gridId){
    var childGrid;
    $.each(gridArray,function(idx,item){
            if(item.id==gridId){
                childGrid=item.grid;
            }
        })
        if(childGrid){
            if(!isUpdate){
             //重置排序
            childGrid.options.sortName='userId';
            childGrid.options.sortOrder="desc";
            //页号重置为1
             childGrid.setNewPage(1);
        }
        childGrid.loadData();
        }
    }

</script>
</head>
<body>
	<div id="layout1">

		<div position="left" style="" panelTitle="业务类别">
			<div class="layout_content">
				<div>
					<ul id="tree-1" class="ztree"></ul>
				</div>
			</div>
		</div>

		<div position="center" >
		
				<!-- <div class="basicTab"> -->
					<div name="材料明细管理" style="width: 100%; height: 100%;" id="maingrid" style="overflow-x:hidden;">
						
					</div>
					<!-- <div  name="材料分类管理" style="width: 100%; height: 100%;" id="grid1"> -->
					    
						<!-- <input style="float: right;" type="button" class="input_button" value="添加材料类型" onclick="insRowType()" />
						<div id="typeManager" >
						
						</div> -->
						
					</div>
					
				</div>
		</div>
	</body>
</html>