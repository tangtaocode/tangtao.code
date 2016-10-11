$(function(){
	//申请类型
	if(needCharge==1){
		$("input:radio[name=shenqingType]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=shenqingType]").eq(1).attr("checked",true);
	}
	//是否下放
	if(isdelegate==1){
		$("input:radio[name=isdelegate]").eq(0).attr("checked",true);
		showDiv('isdelegateDiv');
		if(delegatetype==1){//下放类型
			$("input:radio[name=delegatetype]").eq(0).attr("checked",true);
		}
		if(delegatetype==2){//下放类型
			$("input:radio[name=delegatetype]").eq(1).attr("checked",true);
		}
	}else{
		$("input:radio[name=isdelegate]").eq(1).attr("checked",true);
	}
	//选中服务对象
	if(approveobject.charAt(0)==1){//个人
		$("input:checkbox[name=approveObject]").eq(0).attr("checked",true);
	}
	if(approveobject.charAt(1)=='1'){//企业
		$("input:checkbox[name=approveObject]").eq(1).attr("checked",true);
	}
	if(approveobject.charAt(2)=='1'){//事业
		$("input:checkbox[name=approveObject]").eq(2).attr("checked",true);
	}
	if(approveobject.charAt(3)=='1'){//机关
		$("input:checkbox[name=approveObject]").eq(3).attr("checked",true);
	}
	if(approveobject.charAt(4)=='1'){//社团
		$("input:checkbox[name=approveObject]").eq(4).attr("checked",true);
	}
	if(approveobject.charAt(5)=='1'){//其他
		$("input:checkbox[name=approveObject]").eq(5).attr("checked",true);
	}
	//是否收费
	if(needCharge==1){
		$("input:radio[name=needCharge]").eq(0).attr("checked",true);
		showDiv('needChargeDiv');
	}else{
		$("input:radio[name=needCharge]").eq(1).attr("checked",true);
	}
	//是否年检
	if(needAnnual==1){
		$("input:radio[name=needAnnual]").eq(0).attr("checked",true);
		showDiv('needAnnualDiv');
	}else{
		$("input:radio[name=needAnnual]").eq(1).attr("checked",true);
	}
	//审批系统g
	if(approveplace==1){
		$("input:radio[name=approveplace]").eq(0).attr("checked",true);
		if(abutment==1){
			$("input:radio[name=abutment]").eq(0).attr("checked",true);
		}else{
			$("input:radio[name=abutment]").eq(1).attr("checked",true);
		}
		showDiv('approveplaceDiv');
	}
	if(approveplace==0){
		$("input:radio[name=approveplace]").eq(1).attr("checked",true);
	}
	if(approveplace==2){
		$("input:radio[name=approveplace]").eq(2).attr("checked",true);
	}
	//特别程序
	if(isSpecial==1){
		$("input:radio[name=specialProgramFlag]").eq(0).attr("checked",true);
		showDiv('hasprogramsDiv');
	}else{
		$("input:radio[name=specialProgramFlag]").eq(1).attr("checked",true);
	}
	//是否涉外
	if(foreignservice==1){
		$("input:radio[name=foreignservice]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=foreignservice]").eq(1).attr("checked",true);
	}
	//是否进驻
	if(isprovince==1){
		$("input:radio[name=isprovince]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=isprovince]").eq(1).attr("checked",true);
	}
	//是否纳入监察
	if(supervisionFlag==1){
		$("input:radio[name=supervisionFlag]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=supervisionFlag]").eq(1).attr("checked",true);
	}
	
	//是否适合网上办理
	if(suitOnline==1){
		$("input:radio[name=suitOnline]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=suitOnline]").eq(1).attr("checked",true);
	}
	//网上缴费
	if(payfeesonlineservice==1){
		$("input:radio[name=payfeesonlineservice]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=payfeesonlineservice]").eq(1).attr("checked",true);
	}
	//全程网上办结
	if(onlinewholecomplete==1){
		$("input:radio[name=onlinewholecomplete]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=onlinewholecomplete]").eq(1).attr("checked",true);
	}
	//身份验证
	if(authenticationservice==1){
		$("input:radio[name=authenticationservice]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=authenticationservice]").eq(1).attr("checked",true);
	}
	//ca验证
	if(caauthenticationservice==1){
		$("input:radio[name=caauthenticationservice]").eq(0).attr("checked",true);
	}else{
		$("input:radio[name=caauthenticationservice]").eq(1).attr("checked",true);
	}
})
function validateRadio(){
	//是否下放
    if($("input:radio[name=isdelegate]").filter("[checked]").val()==1){
    	if($("#delegatetype").val()==""){
    		alert("下放类型不能为空！");
    		return;
    	}
    	if($("#superiorserviceorgid").val()==''){
    		alert("上级主管部门不能为空！");
    		return;
    	}
    }
    //检查承诺时限是否大于法定时限
	var ftime=$("#timelimit").val();
	var ptime=$("#promiselimittime").val();
	
    //是否收费
    if($("input:radio[name=needCharge]").filter("[checked]").val()==1){
    	if($("#chargeName").val()==''){
    		alert("收费名称不能为空！");
    		return;
    	}
    	if($("#chargeStandard").val()==''){
    		alert("收费标准不能为空！");
    		return;
    	}
    	if($("#chargeBasis").val()==''){
    		alert("收费依据不能为空！");
    		return;
    	}
    }
    //是否年检
    if($("input:radio[name=needAnnual]").filter("[checked]").val()==1){
    	if($("#annualDate").val()==''){
    		alert("年检时间不能为空！");
    		return;
    	}
    	if($("#annualType").val()==''){
    		alert("年检方式不能为空！");
    		return;
    	}
    	if($("#annual").val()==''){
    		alert("年检依据不能为空！");
    		return;
    	}
    }
    //检查承诺时限是否大于法定时限
	var ftime=$("#timelimit").val();
	var ptime=$("#promiselimittime").val();
	
    //审批系统
    if($("input:radio[name=approveplace]").filter("[checked]").val()==1){
    	if($("#systemLevel").val()==''){
    		alert("系统级别不能为空！");
    		return;
    	}
    	if($("#systemName").val()==''){
    		alert("系统名称不能为空！");
    		return;
    	}
    	if($("#abutment").val()==''){
    		alert("是否对接不能为空！");
    		return;
    	}
    	//是否提供在线申办
        if($("#onlineapplyingservice").val()==1){
        	if($("#onlineapplyingwebsite").val()==''){
        		alert("在线申办网址不能为空！");
        		return;
        	}
        }
        //是否提供网上咨询
        if($("#businessconsultingservice").val()==1){
        	if($("#businessconsultingwebsite").val()==''){
        		alert("业务咨询网址不能为空！");
        		return;
        	}
        }
        //进度查询
        if($("#progressinquiryingservice").val()==1){
        	if($("#progressinquiryingwebsite").val()==''){
        		alert("进度查询网址不能为空！");
        		return;
        	}
        }
        //结果查询
        if($("#resultinquiryingservice").val()==1){
        	if($("#resultinquiryingwebsite").val()==''){
        		alert("结果查询网址不能为空！");
        		return;
        	}
        }
    //是否适合网上办理
    if($("input:radio[name=suitOnline]").filter("[checked]").val()==0){
    	if($("#onlineReason").val()==''){
    		alert("不适合网上办理原因不能为空！");
    		return;
    	}
    }
    }
    return true;
}

//检查到现场次数
function checkscenen(){
	//办事深度
	var servicedept=document.forms[0].onlineServiceDepth.value;
	var scenenumber = $("#scenenumber").val();
	if(servicedept=='2'){//不超过2次
		if(scenenumber=='23'||scenenumber=='33'||scenenumber=='99'){
			alert('当前网上办事深度下的到现场次数不能超过两次');
			$("#scenenumber").val("");
			return false;
		}
	}else if(servicedept=='3'){//不超过1次
		if(scenenumber=='12'||scenenumber=='22'||scenenumber=='23'||scenenumber=='33'||scenenumber=='99'){
			alert('当前网上办事深度下的到现场次数不能超过一次');
			$("#scenenumber").val("");
			return false;
		}
	}
}
function addProgram(){
	var $tbox=$("<div class='t_box' id='box"+mynum+"'></div>");
	$tbox.append("是否是必经程序<span style='color:red; '>*</span> ");
	$tbox.append("<select name='isNeedProgram'><option value=''>--请选择--</option><option value='1'>是</option><option value='0'>否</option></select>;<a href='javascript:void(0)' onclick=delProgram('box"+mynum+"') style='float:right;' alt='删除'><img alt='删除' src='../static/risesoft/images/delete.png' /></a><br><br>");
	var sel = "<select name='programType' id='programType"+mynum+"' prompt='' url='"+ctx+"/resource/specialSelect'></select><br><br>";
	$tbox.append("特别程序类型<span style='color:red;'>*</span>");
	$tbox.append(sel);
	$tbox.append("实施主体<span style='color:red;'>*</span>");
	$tbox.append("<textarea name='impleUnit' class='style_text_yj' rows='3' cols='80'></textarea><br><br>");
	
	$tbox.append("实施依据<span style='color:red; '>*</span>");
	$tbox.append("<textarea name='impleBase' class='style_text_yj' rows='3' cols='80'></textarea><br><br>");
	
	$tbox.append("实施期限<span style='color:red; '>*</span>");
	$tbox.append("<textarea name='implelimit' class='style_text_yj' rows='3' cols='80'></textarea><br><br>");
	
	$tbox.append("实施方式<span style='color:red; '>*</span>");
	$tbox.append("<textarea name='impleway' class='style_text_yj' rows='3' cols='80'></textarea><br><br>");
	
	$("#speprogram").append($tbox);
	$("#programType"+mynum).render();
	mynum++;
}

function delProgram(objid){
  var obj=$("#"+objid);
  if(!confirm("确认删除该特别程序吗？")) return false;  
  obj.remove();
}

function hideDiv(obj){
		$("#"+obj).hide();
	}
function showDiv(obj){
	$("#"+obj).show();
}