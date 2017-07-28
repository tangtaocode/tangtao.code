<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">

<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<title>工作流中间件-权限设置</title>

<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.10.2.min.js"></script>

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>

<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false" />
<link rel="stylesheet" type="text/css" id="customSkin" />
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->

<!-- 上传控件start -->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/form/upload/fileUpload.js"></script>


<!-- 上传控件end -->


</head>
<body>

	<div class="padding_right5">
		<div id="maingrid"></div>
	</div>	
	
</body>

<script type="text/javascript">
	var ret;
	var grid=$("#list");	
	var DECLAREANNEXGUID='${DECLAREANNEXGUID}';//材料ID
	var instanceId='${instanceId}';//流程实例ID
	var errorMsg = '${msg}';
	var itembox="${itembox}";
	var g;
	
	var forerverData={"list":[{"value":"1", "key":"永久有效"},{"value":"0", "key":"非永久有效"}]};
	
	var stateData={"list":[{"value":"2", "key":"有效"},{"value":"3", "key":"无效"}]};

	
	function initComplete(){
		
		initGrid(); 

	}
	
	function showFileDialog(stuffdataguid) {
		var dia = new Dialog({

			InnerHtml: "<div id=\"dialog-uploadfiles\"><form id=\"fileForm\" enctype=\"multipart/form-data\" action=\"\"><div style=\"height: 20px\"></div><input type=\"file\" name=\"attachmentFile\" id=\"attachmentFile\" style=\"width: 300px; height: 26px\" /></form><br><font color=\"red\">请上传小于20M的附件！</font>	</div>",

		    Title:"上传共享材料附件",
		    ShowButtonRow: true,
		    Width: 400,
		    Height: 200,
		    ShowCancelButton: false,
		    
		    OKEvent: function() {
		    	var fileName = $('#attachmentFile').val();
				if (!fileName || fileName.lastIndexOf('.') == -1) {
					alert('请选择文件，且文件格式为:doc、docx、pdf、xls、wps');
					return;
				} else {
					var fileExtension = fileName.substr(fileName.lastIndexOf('.') + 1);
					
						$('#fileForm').ajaxSubmit({
							type : 'POST',
							dataType : 'json',
							data : {
								'stuffdataguid' :stuffdataguid 
							},
							url : '${ctx}/stuff/uploadXt',
							success : function(responseText, statusText, xhr, $form) {
								if (responseText.success == true) {
									
									Dialog.close();
									alert(responseText.msg);
									g.loadData();
								} else {
									alert(responseText.msg);
									g.loadData();
								}
							},
							error:function(){
								alert('上传失败！');
							}
						});
						
				}
		    }


		});
		dia.show();
	}
	
	function initGrid() {
		g = $("#maingrid").quiGrid({
			columns : [ {
					display : '共享材料名称',
					name : 'STUFFDATANAME',
					width : "10%",
					align : 'center',
					editor: { type: 'text',maxlength:50,tip:'不超过50个字的中文' }
				},{
					display : '是否永久有效',
					name : 'LIMITFOREVER',
					width : "9%",
					align : 'center',
					isTotalSummary : true,
					render: function(rowdata, rowindex, value, column){
						if(rowdata.LIMITFOREVER=='1') {
							ret = '永久有效';
						}else {
							ret = '否';
						}
						return ret;
					},
					editor: {
						type: 'select',data: forerverData, selWidth: 100, render: function(item) {
							for(var i=0; i<forerverData["list"].lenght; i++ ) {
								if(forerverData["list"][i]['value'] == item.LIMITFOREVER) {
									return forerverData["list"][i]['key']
								}
							}
							return item.LIMITFOREVER;
						}
					}
				},{
					display : '有效期开始日期',
					name : 'LIMITBEGIN',
					width : "10%",
					align : 'center',
					editor: {
						type: 'date', dateFmt: 'yyyy-mm-dd', width: 100
					}
				},{
					display : '有效期结束日期',
					name : 'LIMITEND',
					width : "10%",
					align : 'center',
					editor: {
						type: 'date', dateFmt: 'yyyy-mm-dd', width: 100
					}
				},{
					display : '状态',
					width : "9%",
					name : 'STATE',
					align : 'center',
					render: function(rowdata, rowindex, value, column){
						if(rowdata.STATE=='2') {
							ret = '有效';
						} else if(rowdata.STATE=='3') {
							ret = '无效';
						} else {
							ret = '待认证';
						}
						return ret;
					},
					editor: {
						type: 'select',data: stateData, selWidth: 100,
						render: function(item) {
							//使点开下拉框默认值是以前选中的那个
							for(var i=0; i<stateData["list"].lenght; i++ ) {
								if(stateData["list"][i]['value'] == item.STATE) {
									if(item.STATE=='0') {
									}
									return stateData["list"][i]['key']
								}
							}
							return item.STATE;
						}
					}
				},{
					display : '无效原因',
					width : "10%",
					name : 'REMARK',
					align : 'center',
					editor: { type: 'text',maxlength:50,tip:'不超过50个字的中文' }
				},{
					display : '认证者',
					name : 'CERTIFYPERSON',
					width : "15%",
					align : 'center'
				},{
					display : '认证时间',
					name : 'CERTIFYTIME',
					width : "15%",
					align : 'center'
				},{
					display : '操作',
					name : 'Opt',
					width : "10%",
					fixed : true,
					align : 'center',
					render: function (rowdata, rowindex, value, column){
						var view = "";
	                    if (!rowdata._editing)
	                    {
	                    	view = '<div class="padding_top padding_left5">'
	    						+ '<span class="img_add hand" title="新增附件" onclick="showFileDialog(\'' + rowdata.GUID + '\');"></span>'
	    						+ '<span class="img_edit hand" title="修改" onclick="beginEdit(\'' + rowindex + '\');"></span>'
	    		                + '<span class="img_delete hand" title="删除" onclick="onDelete(\'' + rowdata.GUID+ '\')"></span>'
	    		           	 	+ '</div>';
	                       
	                    }
	                    else
	                    {
	                    	view = '<div class="padding_top padding_left5">'
	    						+ '<span class="img_ok hand" title="提交" onclick="endEdit(\'' + rowindex + '\');"></span>'
	    		                + '<span class="img_no hand" title="取消" onclick="cancelEdit(\'' + rowindex+ '\')"></span>'
	    		           	 	+ '</div>';
	                       
	                    }
	                    return view;
	    			}
				}],
			url : '${ctx}/stuff/stuffdataList?DECLAREANNEXGUID=' + DECLAREANNEXGUID+'&instanceId='+instanceId+'&type=${type }',
			rownumbers:true,
			ohterClose:false,
			pageSize:20,
			percentWidthMode:true,
			usePager:false,
	        height: '100%',
			width:"100%",
			frozen:false,
			isScroll:true,
			detail: { onShowDetail: showDataxt, height: 'auto' },
			enabledEdit: true,
//			onAfterEdit: onAfterEdit,
			onBeforeSubmitEdit: onBeforeSubmitEdit,
			onAfterSubmitEdit: onAfterSubmitEdit,
			clickToEdit: false			
			});
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
	    g.endEdit(rowid);
	}
	//编辑提交前事件 
	function onBeforeSubmitEdit(e){
		if(e.newdata.STATE=="3" && e.newdata.REMARK==""){
	            Dialog.alert("认证为无效时，无效原因不能为空！",null,null,null,20);
	            return false;
	    }
		if(e.newdata.LIMITFOREVER!="1" && e.newdata.LIMITEND==""){      
	         Dialog.alert("当材料非永久有效时，有效期结束日期不能为空！",null,null,null,20);
	         return false;
	    }
	 }
	//编辑后事件 
	function onAfterSubmitEdit(e) {
		 //在这里一律作修改处理
		
	    var rowData = e.newdata;
		rowData.GUID = e.record.GUID;
	    $.post('${ctx}/stuff/updateStuffdata',rowToBO(rowData),function(result){         
	          Dialog.alert(result.message);
	          g.loadData();
	    },"json");
	}
	
	//将row JSON对象转化为bo对象
	 function rowToBO(row) {
	    var params = '&guid='+row.GUID+'&stuffdataname='+row.STUFFDATANAME+'&limitforever='+row.LIMITFOREVER+'&limitbegin='+row.LIMITBEGIN+'&limitend='+row.LIMITEND+'&state='+row.STATE+'&remark='+row.REMARK;
	    return params;
	}
	
//	function onAfterEdit(e) {
//		if(e.column.name=='STATE' && e.value=='3') {
//			Dialog.open({URL:"${ctx}/stuff/wuxiao?stuffdataguid="+e.record.GUID,Title:"无效原因",Width:280,Height:200});
//		}
//		 $.post("${ctx}/stuff/saveStuffData?method=modifyAttributes",
//
//	                {"stuffdataguid":e.record.GUID, 
//	    	 			"columnName": e.column.name,
//	    	 			"columnValue":e.value},
//
//	                function(result){
//							alert(result.message);
//							g.loadData();
//					},"json");
//
//	} 
	
	//删除该共享材料
	function onDelete(stuffdataguid) {
		$.post("${ctx }/stuff/delStuffdata", {
			stuffdataguid: stuffdataguid
		},function(data) {
			alert(data.msg);
			g.loadData();
		});
	}
	
	//删除附件
	function onDeleteXt(xtguid) {
		$.post("${ctx }/stuff/delXt", {
			xtguid: xtguid
		},function(data) {
			alert(data.msg);
			g.loadData();
		});
	}
	
	function showFile(fileid) {
		$.post("${ctx}/stuff/download", {
			id: fileid
		}, function(data) {
			if(data.success=='false') {
				alert(data.msg);
			}
		});
	}
	
	function showDataxt(row, detailPanel,callback) {
		$.post("${ctx}/stuff/stuffdataXtList",{
			parentId : row.GUID
		}, function(result) {
			if(result.rows.length > 0) {
				var childGrid = document.createElement('div'); 

                $(detailPanel).append(childGrid);

                $(childGrid).css('margin',10).quiGrid({

                     columns: [ { 
                    	 display : '附件名称',
                    	 name : 'FILENAME',
                    	 width : "30%",
                    	 align : 'center',
                    	 render: function(rowdata, rowindex, value, column){
         					ret = '<a href="${ctx}/stuff/download?id=' + rowdata.GUID + '">' + rowdata.FILENAME + '</a>';
         					//ret = '<a href="javascript:void(0);" onclick="showFile(\''+rowdata.GUID+'\');">' + rowdata.FILENAME + '</a>';
         					return ret;
         				}
                   	   },{
                 		 display : '上传者类型',
                 		 name : 'UPERTYPE',
                 		 width : "20%",
                 		 align : 'center',
                 		render: function(rowdata, rowindex, value, column){
        					if(rowdata.UPERTYPE=='1') {
        						ret = '分厅注册用户';
        					}else {
        						ret = '审批员';
        					}
        					return ret;
        				}
		               	},{ 
//		               		display : '上传者',
//		          			name : 'UPER',
//		       				width : "20%",
//		       				align : 'center'
//	                  	},{ 
	                  		display : '上传时间',
	             			name : 'UPTIME',
	          				width : "20%",
	          				align : 'center'
                     	},{ //更改为可编辑的
                     		display : '排序号',
                			name : 'TABLEINDEX',
             				width : "10%",
             				align : 'center'
             					
                       	},{
            				display : '操作',
            				name : 'Opt',
            				width : "20%",
            				fixed : true,
            				align : 'center',
            				render: function (rowdata, rowindex, value, column){
            					return '<div class="padding_top padding_left5">'
            	                + '<span class="img_delete hand" title="删除" onclick="onDeleteXt(\'' + rowdata.GUID+ '\')"></span>'
            	           	 	+ '</div>';
                			}
                       	}],
                       	rownumbers:true,
                       	isScroll: false,width: '90%', columnWidth: 120,usePager:false,
                        data: result
                   });
                     

			}
		});
	}
	
	
</script>
</html>