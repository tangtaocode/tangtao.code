/**
 * ���񻥶�
 */
/**
 * ҵ�����ۡ���������ź�֤����Ų�ѯҵ����Ϣ��������Ϣ
 */
function searchAppraise(){
	if(!validations("userInfoTab"))return false;
	$.post("/interaction/interactionQuery.html",
	{'declaresn':$("#declaresn").val(),
	 'cardId':$("#cardId").val(),
	 'method':'search'},
	 function(data){
		$("#pageCountTd").html(data);
	});
}
/**
 * ��ѯҵ�������Ϣ
 * @param {Object} isCheck �Ƿ�����֤��
 * @param {Object} pageNum
 */
function searchBusi(isCheck,pageNum){
	var data = $("#searchForm").serializeArray(); 
	var obj = new Object();
	obj.name="page";
	obj.value = pageNum;
	data.push(obj);
	$.post("/interaction/queryResult.html",data,function(rs){
		$("#pageCountTd").html(rs);
	});
}

/**
 * 办事查询，我要查询(业务状态)
 * @param {Object} isCheck  是否验证验证码
 * @param {Object} pageNum  页数
 */
function searchBusi_stus(isCheck,pageNum){
	var ys = $("#yxtywlsh").val();
	var cc = $("#checkCode").val();		
	var sqsj_S=$("#sqsj_S").val();	
	var sqsj_E=$("#sqsj_E").val();	
	var personName=$("#personName").val();
	var cardId=$("#cardId").val();
	var organizationCode=$("#organizationCode").val();	
	if(isCheck=="true"){
		if(cc==""||cc==null){
			alert("请输入验证码");
			$("#checkCode").focus();
			return false;
		}
	}
	$("#checkCode").attr("value","");
	
	$.post('/interaction/queryResultStus.html',
		{'yxtywlsh':ys,
		 'checkCode':cc,
		 'isCheck':isCheck,
		 'sqsj_S':sqsj_S,
		 'sqsj_E':sqsj_E,
		 'personName':personName,
		 'cardId':cardId,
		 'organizationCode':organizationCode,
		 'page':pageNum,
		 'method':'query'},
		function(data){			 
			if(data.message=="0"){				
				showInfo("验证码输入错误");
				$("#bjcxRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());
			}else{
				$("#pageCountTd").html(data);
				if(isCheck=="true"){$("#bjcxRand").attr("src","/servlet/CheckCodeServlet?"+Math.random());}
				searchAtran();
			}
		});
}
/**
 * ��ѯͶ����Ϣ
 * @param {Object} pageNum ҳ��
 */
function searchComplain(pageNum){
	var data = $("#searchForm").serializeArray(); 
	var obj = new Object();
	obj.name="page";
	obj.value = pageNum;
	data.push(obj);
	$.post("/interaction/queryComplain.html",data,function(rs){
		$("#pageCountTd").html(rs);
	});
}
/**
 * ���id����Ͷ����ϸ��Ϣ
 * @param {Object} id Ͷ��GUID
 * @param {Object} pageNum  ҳ��
 */
function findComplain(id,pageNum){
	$.post("/interaction/findComplain.html",{guid:id,page:pageNum},function(data){
		$("#pageCountTd").html(data);
	});
}
/**
 * ����������ѯ���
 * @param {Object} pageNum ҳ��
 */
function searchConsult(pageNum){
	var data = $("#searchForm").serializeArray(); 
	var obj = new Object();
	obj.name="page";
	obj.value = pageNum;
	data.push(obj);
	$.post("/interaction/queryConsult.html",data,function(rs){
		$("#pageCountTd").html(rs);
	});
}
/**
 * �����ѯid������ѯ��ϸ��Ϣ
 * @param {Object} id ��ѯGUID
 * @param {Object} pageNum  ҳ��
 */
function findConsult(id,pageNum){
	$.post("/interaction/findConsult.html",{guid:id,page:pageNum},function(data){
		$("#pageCountTd").html(data);
	});
}
