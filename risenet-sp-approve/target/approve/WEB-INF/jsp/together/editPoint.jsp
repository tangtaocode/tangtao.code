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
function initComplete(){
	g = $("#dataBasic").quiGrid({
		columns: [ 
					{ display: '材料名称', name: 'MATERIALNAME', align: 'center', width: "10%"},
					{ display: '业务情形', name: 'SITUATION', align: 'center', width: "10%"},
					{ display: '提交方式', name: 'TJFS', align: 'left', width: "10%"
					},
					{ display: '收件要求', name: 'REQIREMENTS', align: 'center', width: "15%"},
					{ display: '收件标准', name: 'STANDARDS', align: 'center', width: "18%"},
					{ display: '操作', name: '',align: 'center', width: "6%",
						render: function (rowdata, rowindex, value, column){
							var view = '<div class="padding_top padding_left5">'
		    						+ '<span class="img_edit hand" title="编辑" onclick="editPoints(\'' + rowdata.ID +'\',\''+rowdata.MATERIALNAME+'\')"></span></div>';      
		                    return view;
		    			}

				    }
	         ], 

	        url:'${ctx}/togetherWindow/getPoints?itemid=${itemid}',rownumbers: false,checkbox:false,fixedCellHeight: false,
	        height: '100%', width: '98%', usePager: false,enabledEdit: true,clickToEdit: false
	});
}

function editPoints(id, materialname) {
	//diag.URL = "${ctx}/togetherWindow/editPointInit?ID="+id+"&MATERIALNAME="+materialname;
	Dialog.open({URL:"${ctx}/togetherWindow/editPointInit?ID="+id+"&MATERIALNAME="+materialname,Title:"编辑材料要点",Width:800,Height:550});
}
function closeThePoint(){
	this.Dialog.close();
	g.loadData();
}
</script>
<body>
	<div class="padding_right5" style="width:98%;">
		<div id="dataBasic"></div>
    </div>

</body>
</html>