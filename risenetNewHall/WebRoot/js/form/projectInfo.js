$(document).ready(function(){
	var itemId = $("#SB_APPROVEGUID").val();
	//如果是建设工程服务（货物）招标文件备案、建设工程施工（监理）招标文件备案,项目信息可选择填写，工程编号非必填
	if(itemId == "{7F000001-FFFF-FFFF-A669-F75700000079}"||itemId == "{0A009FA8-FFFF-FFFF-8836-09C6000004FF}"){
		$("#GONGCHENGBIANHAO").next().hide();
		$("#SGGK_xmbh").attr("readonly",true);
		$("#SGGK_xmmc").attr("readonly",true);
		$("#SGGK_zbr").attr("readonly",true);
	}else if(itemId == "{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}"||itemId == "{0A009FA8-0000-0000-68C9-3CDB0000004C}"){
		//如果是前期物业招标文件备案、业主委员会物业招标文件备案,项目信息和工程信息需要手动填写，项目名称和工程名称必填
		$("#GONGCHENGBIANHAO").next().hide();
		$("#SGGK_xmbh").next().hide();
		$("#selectBtn").hide();
	}
	
});

function selectGcInfo(){
		var approveItemGUID = $("#SB_APPROVEGUID").val();
		var shareId="";
	   //建设工程服务（货物）招标文件备案
	   if(approveItemGUID=="{7F000001-FFFF-FFFF-A669-F75700000079}"){
	   	shareId="{0A0102A1-0000-0000-6169-82E400000038}";
	   }else if(approveItemGUID=="{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}"){
	   	//深圳市前期物业管理招标文件备案（内容与前期物业招标公告和招标组织形式备案一致）
	   	shareId="{0A0100A8-FFFF-FFFF-CC84-507E0000114E}";
	   }else if(approveItemGUID=="{0A009FA8-0000-0000-68C9-3CDB0000004C}"){
	   	 //深圳市物业管理招标文件备案 
	   	shareId="{0A0100A8-FFFF-FFFF-CC8C-6F51000010C0}";
	   }else if(approveItemGUID=="{0A009FA8-FFFF-FFFF-8836-09C6000004FF}"){
	   	 //建设工程施工（监理）招标文件备案
	   	shareId="{0A0102A1-0000-0000-6169-82E400000038}";
	   } 
	   var url = "/approve/shared/sharedSelect.jsp?sharedGuid="+shareId;
	   window.showModalDialog(url,window,"dialogWidth:750px;dialogHeight:450px;center:yes;status:no;help:no;");
}

function other_Valid(){
		var itemId = $("#SB_APPROVEGUID").val();
		var xmbh = $("#SGGK_xmbh").val();
		var xmmc = $("#SGGK_xmmc").val();
		var gcbh = $("#GONGCHENGBIANHAO").val();
		var gcmc = $("#GONGCHENGMINGCHENG").val();
		var jsdw = $("#SGGK_zbr").val();
		if(itemId=='{7F000001-FFFF-FFFF-A669-F75700000079}'||itemId=='{0A009FA8-FFFF-FFFF-8836-09C6000004FF}'){
			if(xmbh==""){parent.showInfo('请填写项目编号！');return false;}
			if(xmmc==""){parent.showInfo('请填写项目名称！');return false;}
			if(gcmc==""){parent.showInfo('请填写工程名称！');return false;}
			if(jsdw==""){parent.showInfo('请填写建设单位！');return false;}
		 }else if(itemId=='{0A009FA8-FFFF-FFFF-8CE7-CD220000006B}'||itemId=='{0A009FA8-0000-0000-68C9-3CDB0000004C}'){
			 if(xmmc==""){parent.showInfo('请填写项目名称！');return false;}
			 if(gcmc==""){parent.showInfo('请填写工程名称！');return false;}
			 if(jsdw==""){parent.showInfo('请填写建设单位！');return false;}
		 }
		 return true;
}