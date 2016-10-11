/**
 * 用于查询出用户列表设置其是否接受超期业务短信提醒
 */
function smsPage(curentpage,pagesize){
	$("#listsmstitle").remove();//清除原有的table
	$("#listcontent").remove();//清除原有的table
	var employeename = $("#employeename").val();
	var department = $("#department").val();
	$("#curentpage").attr("value",curentpage);
	$.ajax({
		url:"/Sms/getEmployeeList",
		type:"post",
		cache:false,
		data:{"pageSize":pagesize,"curentpage":curentpage,"emplyeename":employeename,"department":department},
		dataType:"json",
		success:function(data){
			if(null!=data&&data.employeelist.length>0&&data.employeelist!=null){
				totableRow(data,pagesize*(curentpage-1),pagesize);
				//pagesearch(data.counts,pagesize);
			}else{
				$("#smstable").append("<table><tbody><tr><th>没有人员！</th></tr></tbody></table>");
				//pagesearch(data.counts,pagesize);
			}
		}
		,error:function(XMLHttpRequest,textStatus,errorThrown){
			alert(textStatus);
		}
	});
}

/**
 * 获取数据形成table
 * */
function totableRow(data,rowIndex,pagesize){
	var table = "<table width='100%' border='0' cellpadding='0' cellspacing='0' class='dxsz_title' id='listsmstitle'>" ;
	table+="<tr><td width='20%'>姓名</td><td width='33%'>所属部门</td><td width='33%'>手机号码</td><td width='14%'>短信提醒</td></tr></table>";
	table+=" <table width='100%' border='0' cellpadding='0' cellspacing='0' class='dxsz_text' id='listcontent'>";
	var employeelist = data.employeelist;
	for(var i=0;i<employeelist.length;i++){
		//table+="<tr><td>"+(rowIndex+i+1)+"</td>";//序号
		table+="<tr><td>"+employeelist[i].employee_name+"</td>";//姓名
		table+="<td>"+employeelist[i].bureauName+"</td>";//部门
		table+="<td>"+employeelist[i].employee_mobile+"</td>";//电话号码
		if(employeelist[i].employee_issms=='1'){
			table+="<td><input type='checkbox' name='employeeguid' value='"+employeelist[i].employee_guid+"'/></td></tr>";
		}else{
			table+="<td><input type='checkbox' name='employeeguid' checked='checked'  value='"+employeelist[i].employee_guid+"'/></td></tr>";
		}
	}
	var rs = pagesearch(data.counts,pagesize);
	table=table+rs+"</table>";
	$("#smstable").append(table);
}
/**
 * 获取数据产生分页
 */
function pagesearch(pagecount,pagesize){
	var currentPage = parseInt($("#curentpage").val());
	var rs = "<tr><td class=\"list_page\" colspan='4'><table width='100%' border='0' cellpadding='0' cellspacing='0'><tr>";
	rs+="<td>共"+pagecount+"页  &nbsp;当前第"+currentPage+"页</td>";
	rs+="<td colSpan='4'><div><a href=\"javascript:smsPage(1,7)\">首页</a>";
	if(currentPage>1)
		rs+="<a href='javascript:smsPage("+(currentPage-1)+","+pagesize+")'>上一页</a>";
	else 
		rs+="<a href='javascript:void(0);'>上一页</a>";;
	
	/*for(var i=1;i<=pagecount;i++){
		if(i==currentPage)rs+=i+"&nbsp;";
		else rs+="<a href='javascript:smsPage("+i+","+pagesize+")'>"+i+"</a>&nbsp;";
	}*/
	
	if(currentPage<pagecount)
		rs +="<a href='javascript:smsPage("+(currentPage+1)+","+pagesize+")'>下一页</a>";
	else
		rs+="<a href='javascript:void(0);'>下一页</a>";
	rs+="</div></td></tr></table></td></tr>";
	return rs;
}
/**
 * 设置
 * @param set
 * @param flag
 */
function setsms(){
	var okemployeeguids="";
	var cancelemployeeguids="";
 $("#listcontent input[name='employeeguid']").each(function (){
	 if($(this).attr("checked")){
		 okemployeeguids+=$(this).val()+",";
	 }else{
		 cancelemployeeguids+=$(this).val()+",";
	 }
	 
 });
 okemployeeguids = okemployeeguids.substring(0, okemployeeguids.length-1);
 cancelemployeeguids = cancelemployeeguids.substring(0, cancelemployeeguids.length-1);
	$.ajax({
		url:"/Sms/setSms",
		type:"post",
		cache:false,
		data:{"okemployeeguids":okemployeeguids,"cancelemployeeguids":cancelemployeeguids},
		dataType:"json",
		success:function(data){
			if(data.flag=="1"){
				alert("设置成功！");
			}else{
				alert("设置失败！");
			}
		}
	});
}
/**
 * 表单提交搜索
 */
function search(){
	var curentpage=$("#curentpage").val();
	smsPage(curentpage,7);
}