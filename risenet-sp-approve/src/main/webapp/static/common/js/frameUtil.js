var bodyWidth = top.document.documentElement.clientWidth;
var bodyHeight = top.document.documentElement.clientHeight;

/**
 * 此工具类依赖jquey easyUI 的window组件，messager组件
 */

//随机生成一个GUID
var NewGUID = (function () { var a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split(""); return function (b, f) { var h = a, e = [], d = Math.random; f = f || h.length; if (b) { for (var c = 0; c < b; c++) { e[c] = h[0 | d() * f]; } } else { var g; e[8] = e[13] = e[18] = e[23] = "-"; e[14] = "4"; for (var c = 0; c < 36; c++) { if (!e[c]) { g = 0 | d() * 16; e[c] = h[(c == 19) ? (g & 3) | 8 : g & 15]; } } } return e.join("").toLowerCase(); }; })();

/**
 * 创建iframe,返回iframe的html片段
*/
function createFrame(url) {
    var src = ""
    if (url != null && url != "") {
        src = '<iframe onload="javascript:frmLoad(this)" scrolling="auto" frameborder="0"  src="' + url + '" style="border: 0;width:100%;height:100%;"></iframe>';
    }
    return src;
}

/**
 * 加载IFrame页面时，显示进度条
*/
function frmLoad(obj) {
    if (!obj.readyState || obj.readyState == "complete") {
        window.top.$.messager.progress('close');
    }
}

/**
 * 顶层窗口中打开IFrame
 */
function openInTopWindow(options) {
    $(top.document.body).append('<div id="' + options.id + '" style=" overflow:hidden;"><iframe scrolling="auto" id="' + options.id + 'Frame" name="' + options.id + 'Frame" frameborder="0"  src="' + options.src + '" style="width:100%;height:100%;"></iframe></div>');
    iframeLoaded(top.document.getElementById(options.id + 'Frame'), options.onLoad);
    if (options.destroy) {
        options.onClose = function () {
            top.$(this).window('destroy');
        };
    }
    top.$('#' + options.id).window($.extend({
    	minimizable : false,
    	maximizable : false,
    	collapsible : false,
    	modal : true
    }, options, {
    	
    }));
    top.$('#' + options.id).window('open');
};

//关闭从顶层页面打开的IFrame
function closeInTopWindow(winID,type){
	if(!type)type='close';
	top.$('#' + winID).window(type);
};


//在当前自定义的父页面打开iframe
function openParentWindow(curParent , options) {
	$(curParent.document.body).append('<div id="' + options.id + '" style=" overflow:hidden;"><iframe scrolling="auto" id="' + options.id + 'Frame" name="' + options.id + 'Frame" frameborder="0"  src="' + options.src + '" style="width:100%;height:100%;"></iframe></div>');
	iframeLoaded(curParent.document.getElementById(options.id + 'Frame'), options.onLoad);
	if (options.destroy) {
		options.onClose = function () {
			$(this).window('destroy');
		};
	}
	curParent.$('#' + options.id).window($.extend({
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true
	}, options, {
		
	}));
	curParent.$('#' + options.id).window('open');
};

//关闭从顶层页面打开的IFrame
function closeParentWindow(curParent,winID,type){
	if(!type)type='close';
	curParent.$('#' + winID).window(type);
};



//在当前页面打开iframe
function openCurWindow(options) {
	$(document.body).append('<div id="' + options.id + '" style=" overflow:hidden;"><iframe scrolling="auto" id="' + options.id + 'Frame" name="' + options.id + 'Frame" frameborder="0"  src="' + options.src + '" style="width:100%;height:100%;"></iframe></div>');
	iframeLoaded(document.getElementById(options.id + 'Frame'), options.onLoad);
	if (options.destroy) {
		options.onClose = function () {
			$(this).window('destroy');
		};
	}
	$('#' + options.id).window($.extend({
		minimizable : false,
		maximizable : false,
		collapsible : false,
		modal : true
	}, options, {
		
	}));
	$('#' + options.id).window('open');
};

//在弹出的页面中，关闭自己
function closeCurWindow(winID,type){
	if(!type)type='close';
	parent.$('#' + winID).window(type);
};

//iframe加载完之后回调。 
function iframeLoaded(iframeEl, callback) {      
    //处理不同浏览器打开的onload事件
	$(iframeEl).on('load', function() {
		var fwin = iframeEl.contentWindow;
		fwin['windowFrom']=window; 
        window[iframeEl.id]=fwin; 
           
        if (callback && typeof (callback) == "function") { 
            callback.call(fwin); 
            iFrameHeight(iframeEl.id); 
        }
	});
};

//调整iframe的高度
function iFrameHeight(id) {
    var ifm = top.document.getElementById(id);
    var subWeb = document.frames ? top.document.frames[id].document : ifm.contentDocument;
    if (ifm != null && subWeb != null) {
        ifm.height = subWeb.body.scrollHeight;
		id=id.substring(0,id.length-5);
        if (subWeb.body.scrollHeight > top.document.getElementById(id).offsetHeight) {
            top.document.getElementById(id).style.overflowY = 'auto';
        }
    }
};