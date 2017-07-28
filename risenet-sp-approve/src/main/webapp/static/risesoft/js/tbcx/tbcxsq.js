function loadTbcx(){
	if(tbcxzl!=''){
		//document.getElementById("tbcxzl").value=tbcxzl;
		if(tbcxzl=="A"){
			document.getElementById("tbcxzl").value=tbcxzl;
			$('#tbcxzl').combobox('setValues', ['A类延长审批']);
		}else if(tbcxzl=="B"){
			document.getElementById("tbcxzl").value=tbcxzl;
			$('#tbcxzl').combobox('setValues', ['B类除延长审批之外']);
		}
		checktype(tbcxzl);
	}
}

function checktype(t){
	if(tbcxzl=='B'&& t=='A'){
		alert("你已申请B类除延长审批之外，不能再申请A类");
		document.getElementById("tbcxzl").value=tbcxzl;
		$('#tbcxzl').combobox('setValues', ['B类除延长审批之外']);
		return;
	}else{
		$('#P_TBCXZLMC').combobox('clear');
		var url = ctx+'/sp/teBieChengXuShenQing/getType?type='+t;
		$('#P_TBCXZLMC').combobox('reload', url);
	}
}

function checkData(){
    var eguids="";
    for (var i=0;i<document.all.lstSelected.options.length;i++)    
    {    
        var e=document.all.lstSelected.options[i].value;    
        eguids=eguids+e+",";   
    } 
    if(eguids.length>38){
    	eguids=eguids.substring(0,eguids.length-1);
   }
   document.getElementById("eeguids").value=eguids;
	return true;
}  
/*function checktype(t){
	if(tbcxzl=='B'&& t=='A'){
		alert("你已申请B类除延长审批之外，不能再申请A类");
		document.getElementById("tbcxzl").value='B';
		return;
	}
	document.getElementById("tbcxsx").readOnly=true;
	if(t=="A"){
			document.getElementById("tbcxsx").value=10;
			data.push({ "text": "延长审批", "id": '延长审批' });
			$("#P_TBCXZLMC").combobox("loadData", data);
	}else if(t=="B"){
		document.getElementById("tbcxsx").value=tbcxsx;
		data.push({"text": "专家鉴定", "id": '专家鉴定'},
				{"text": "听证", "id": '听证'},{"text": "招标", "id": '招标'},
				{"text": "拍卖", "id": '拍卖'},{"text": "检验", "id": '检验'},
				{"text": "检测", "id": '检测'},{"text": "检疫", "id": '检疫'},
				{"text": "公示", "id": '公示'},{"text": "现场验收", "id": '现场验收'},
				{"text": "其它", "id": '其它'});
		$("#P_TBCXZLMC").combobox("loadData", data);
	}
}*/


