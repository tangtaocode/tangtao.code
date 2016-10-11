/*******瀵煎叆澶栭儴js*******/
document.write('<script src="/js/zDialog/zDialog.js"></script>');
document.write('<script src="/js/zDialog/zDrag.js"></script>');

function showPage(title,url,width,height){
	var diag = new Dialog();
	diag.Width = width;
	diag.Height = height;
	diag.Title = title;
	diag.URL = url;
	diag.show();
}

function showUpFilePanel(title,url,modeType){
	var diag = new Dialog();
	diag.Width = 790;
	diag.Height = 360;
	diag.Title = title;
	diag.CancelEvent=function(){
		$(window.frames["_DialogFrame_0"].document).find("object:first").remove();
		if(modeType=="correction"){
			searchBusi();
		}else if(modeType=="bizApply"||modeType=="civilApply"){
			alert(modeType);
			refashFile();
		}else if(modeType=="civilOrgApply"){
			loadUpFilePage();
		}else if(modeType=="approveItem"){
			findFile();
		}
		diag.close();
	};
	diag.URL = url;
	diag.show();
}

function showInfo(mess){
	Dialog.alert(mess);
}

function showConfirm(mess,fun){
	Dialog.confirm(mess,fun);
}

function showMaxConfirm(mess,fun){
	Dialog.maxConfirm(mess,fun);
}

function showConfirmtip(diag,mess,fun,fun1){
	Dialog.confirmtip(diag,mess,fun,fun1);
}

function closeWin(){
	ownerDialog.close();
}
