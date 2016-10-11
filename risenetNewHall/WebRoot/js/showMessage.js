document.write("<script src='/js/Scripts/jquery-1.7.1.min.js'></script>");
document.write("<script src='/js/zDialog/zDialog.js'></script>");

var diag = null;
var checkingID = null;
var loading = '<br><img src="/images/share/loading.gif"/><br><br>';

//加载中
function showLoading(title, data) {
	closeDialog();
	diag = new Dialog();
	diag.Width = 600;
	diag.Title = title;
	diag.InnerHtml = data;
	diag.CancelEvent = function () {
		closeDialog();
		checkingID = null;
	};
	diag.show();
}
	
//关闭加载中窗口
function closeDialog() {
	if (diag != null) {
		diag.close();
		diag = null;
	}
}
	
//弹出确认信息框
function showMessage(title, data, p_width, p_height, addImg_id) {
	closeDialog();
	diag = new Dialog();
	diag.Width = p_width;
	diag.Height = p_height;
	diag.Title = title;
	diag.InnerHtml = data;
	diag.OKEvent = function () {
		closeDialog();
		if(addImg_id!=undefined) addIcon(addImg_id, 1);
	};
	diag.CancelEvent = function () {
		closeDialog();
		if(addImg_id!=undefined) addIcon(addImg_id, 0);
	};
	diag.show();
}

/**
 *@description 添加标志图标
 *@param addImg_id 需添加标志图标的元素id
 *@param isSuccess 图标类型 1:成功图标，0：失败图标
 */
function addIcon(addImg_id, isSuccess) {
	if (1 == isSuccess) {
		$("#" + addImg_id + "_img").remove();
		$("#" + addImg_id).parent().append("<img src='/xzql/images/button/checked.gif' id='" + addImg_id + "_img' width='16' height='16'/>");
	} else {
		$("#" + addImg_id + "_img").remove();
		$("#" + addImg_id).parent().append("<img src='/xzql/images/button/cancel.png' id='" + addImg_id + "_img' width='16' height='16'/>");
	}
}