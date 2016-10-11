/**
 * 实现一些常用的操作
 */
var bodyWidth = top.document.documentElement.clientWidth;
var bodyHeight = top.document.documentElement.clientHeight;

var utils = {};
//判断对象是否是字符串（String）类型值。
utils.isString=function(obj){
	 return $.type(obj) == "string";
};
//判断字符串是否为空或者空字符串
utils.isNullOrEmpty=function(str){
	return str === undefined || str === null || str === ""; 
};
//判断字符串是否为空或者空格字符串
utils.isNullOrWhiteSpace=function(str){
	return utils.isNullOrEmpty(str) || $.trim(String(str)) === ""; 
};
//检测一个对象是否为一个数组对象或者类似于数组对（具有数组的访问方式：具有 length 属性、且具有属性名为数字的索引访问器）
//注意：此方法传入 字符串 时执行，也会返回 true，因为 字符串 是一个字符数组。
utils.likeArray = function (obj) {
    if (obj == null || obj == undefined || $.isWindow(obj)) {
        return false;
    }
    if (obj.nodeType === 1 && obj.length) {
        return true;
    }
    var type = $.type(obj);
    return type === "array" || type !== "function" && $.isNumeric(obj.length) && obj.length >= 0;
};

//  检测一个对象是否为一个数组对象或者类似于数组对（具有数组的访问方式：具有 length 属性、且具有属性名为数字的索引访问器）且不是字符串
utils.likeArrayNotString = function (obj) {
    return utils.likeArray(obj) && !utils.isString(obj);
};
//去除字符串左边空格
utils.ltrim=function(str){
	str = utils.isNullOrEmpty(str) ? "" : String(str);
    return str.replace(/(^\s*)/g, "");
};
//去除字符串右边空格
utils.rtrim=function(str){
	str = utils.isNullOrEmpty(str) ? "" : String(str);
    return str.replace(/(\s*$)/g, "");
};
//替换字符串
utils.replaceAll=function(str, substr, replacement, ignoreCase){
	if (!substr || substr == replacement) { return str; }
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    var length = str.length, i = 0;
    while (str.indexOf(substr) > -1 && i++ < length) { str = str.replace(substr, replacement); }
    return str;
};
//截取当前字符串左边的指定长度内容。
utils.left = function (str, len) {
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    if (!$.isNumeric(len)) { len = parseInt(len, 10); }
    if (len < 0 || len > str.length) { len = str.length; }
    return str.substr(0, len);
};
//截取当前字符串右边的指定长度内容。
utils.right = function (str, len) {
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    if (!$.isNumeric(len)) { len = parseInt(len, 10); }
    if (len < 0 || len > str.length) { len = str.length; }
    return str.substring(str.length - len, str.length);
};
//判断当前 String 对象是否以指定的字符串开头。
utils.startsWith = function (str, val) {
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    return str.substr(0, val.length) == val;
};
//判断当前 String 对象是否以指定的字符串结尾。
utils.endsWith = function (str, val) {
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    return str.substr(str.length - val.length) == val;
};
//返回一个新字符串，该字符串通过在此实例中的字符左侧填充空格或指定字符来来达到指定的总长度，从而使这些字符右对齐。
utils.padLeft = function (str, len, paddingChar) {
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    paddingChar = utils.isNullOrEmpty(paddingChar) || !paddingChar.length ? " " : paddingChar;
    len = $.isNumeric(len) ? len : str.length;
    if (str.length < len) { for (; str.length < len; str = paddingChar + str) { } }
    return str;
};
//返回一个新字符串，该字符串通过在此字符串中的字符右侧填充空格或指定字符来达到指定的总长度，从而使这些字符左对齐
utils.padRight = function (str, len, paddingChar) {
    str = $.isNullOrEmpty(str) ? "" : String(str);
    paddingChar = $.isNullOrEmpty(paddingChar) || !paddingChar.length ? " " : paddingChar;
    len = $.isNumeric(len) ? len : str.length;
    if (str.length < len) { for (; str.length < len; str += paddingChar) { } }
    return str;
};
/**
 * 生成guid字符串
 * @param format 
 * "N":返回值的格式 32 位(xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx)
 * "D":返回值的格式 由连字符分隔的 32 位数字(xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx)
 * "B":返回值的格式 括在大括号中、由连字符分隔的 32 位数字({xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx})
 * "P":返回值的格式 括在圆括号中、由连字符分隔的 32 位数字((xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx))
 * @param length 表示返回字符串的长度；如果不定义该参数，则全部返回。
*/
utils.guid=function (format, length) {
    format = utils.isString(format) ? format.toLowerCase() : "d";
    length = (length == null || length == undefined || !$.isNumeric(length)) ? 32 : length;
    if (length > 32 || length == 0) { length = 32; }
    if (length < -32) { length = -32; }
    var ret = "";
    for (var i = 1; i <= 32; i++) {
        ret += Math.floor(Math.random() * 16.0).toString(16);
        if ((i == 8) || (i == 12) || (i == 16) || (i == 20)) { ret += "-"; }
    }
    switch (format) {
        case "n": ret = utils.replaceAll(ret, "-", ""); break;
        case "b": ret = "{" + ret + "}"; break;
        case "p": ret = "(" + ret + ")"; break;
        case "d": default: break;
    }
    return length >= 0 ? utils.left(ret, length) : utils.right(ret, Math.abs(length));
};
//将字符串对象转换成 对象(Object)值
utils.toObject=function(str){
	return eval("(" + $.trim(str + "") + ")");
};
//将字符串对象转换成 函数(function) 值
utils.toFunction = function (str) {
    if ($.isFunction(str)) { return str; }
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    str = $.trim(str);
    if (!str.startsWith("function")) { str = "function(){" + str + "}"; }
    str = "{ \"func\": " + str + " }";
    return utils.toObject(str).func;
};
/**
 * 以 try...catch... 方式执行指定的函数代码块
 * @returns 如果 code 代码块执行成功，则返回该代码块的执行返回值；否则判断 error 和 final 代码块是否具有返回值；
 * 			如果这两个代码块都有返回值，则取 final 的执行结果返回；
 * 			如果 error 和 final 两个代码块只有其中一个具有返回值，则返回那个具有返回值的代码块的执行结果。
*/
utils.tryExec=function(code, error, final) {
    var defaults = {
        code: null, error: null, final: null, tryError: false, tryFinal: false
    };
    var opts = $.extend(defaults, typeof code == "object" ? code : { code: code, error: error, final: final }), ret;
    if (typeof opts.code == "string") { opts.code = toFunction(opts.code); }
    if (typeof opts.error == "string") { opts.error = toFunction(opts.error); }
    if (typeof opts.final == "string") { opts.final = toFunction(opts.final); }
    try {
        if ($.isFunction(opts.code)) {
            ret = opts.code();
        }
    } catch (e) {
        if ($.isFunction(opts.error)) {
            var a = opts.tryError ? tryExec(opts.error) : opts.error(e);
            if (a != undefined) { ret = a; }
        }
    } finally {
        if ($.isFunction(opts.final)) {
            var b = opts.tryFinal ? tryExec(opts.final) : opts.final();
            if (b != undefined) { ret = b; }
        }
    }
    return ret;
};
//将字符串对象转换成 对象(Object)值
utils.toJSONString = function (str) {
    str = utils.isNullOrEmpty(str) ? "" : String(str);
    str = $.trim(str);
    return str.charAt(0) === "<" && str.charAt(str.length - 1) === ">" && str.length >= 3 ? $(str).text() : str;
};
utils.parseJSON = function (data) {
    var val = null;
    var isString = utils.isString(data);
    if ($.isPlainObject(data) || (utils.likeArrayNotString(data))) {
        val = $.isPlainObject(data.d) ? utils.parseJSON(data.d) : data;
    } else {
        val = $.parseJSON(isString ? utils.toJSONString(data) : $(data).text());
    }
    return val;
};
/**
 * 确认两个 javascript 对象是否等值，该函数定义如下参数:
 * @param item1 待比较的对象1；
 * @param item2 待比较的对象2，用于和对象1进行比较；
 * @param compare: 用于比较运算的函数，该函数用于比较 item1 是否与 item2 等值；该函数返回一个 bool 值；这是一个可选参数。如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
 * @returns 如果 item1 与 item2 等值，则返回 true，否则返回 false。
*/
utils.equals = function (item1, item2, compare) {
    var isFunc = $.isFunction(compare);
    if (!isFunc) { compare = utils.equalsCompare; }
    return compare.call(this, item1, item2) == true;
};
//定义默认的对象比较函数，该函数返回一个 bool 值表示传入的两个对象是否等值。
utils.equalsCompare = function (item1, item2) { 
	return item1 == item2; 
};
/**
 * 确认数组中是否包含指定的元素
 * @param array 被检测的数组；
 * @param item 被检测的元素，判断该元素是否存在于数组 array 中；
 * @param compare 用于比较运算的函数，该函数被循环调用，用于比较 array 中的每一项是否与 item 等值；该函数返回一个 bool 值；如果不定义参数 compare，则使用默认的比较运算符 "==" 进行值的匹配；
*/
utils.contains = function (array, item, compare) {
    return coreArray.some(array, function (val) { return utils.equals(val, item, compare); });
};
/**
 * 显示消息提示
 * @auth heyj
 * @param type 消息类型:info/error/warning/question/confirm
 * @param message 消息内容
 * @param callback 回调方法
*/
showMessage = function (type, message, callback) {
    var messager = top.$.messager;
    if (messager != null) {
        switch (type) {
            case "info":
                messager.alert("系统提示", message, "info",callback);
                break;
            case "error":
                messager.alert("系统提示", message, "error",callback);
                break;
            case "warning":
                messager.alert("系统提示", message, "warning",callback);
                break;
            case "question":
                messager.alert("系统提示", message, "question",callback);
                break;
            case "confirm":
                return messager.confirm('确认', message, callback);
        }
    }
};

/**
 * 顶层窗口中打开Window
 * @param options
 * @since 2014.05.26
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
/**
 * 当前窗口中打开Window
 * @param options
 * @since 2014.05.26
 */
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

function closeInTopWindow(winID,type){
	if(!type)type='close';
	top.$('#' + winID).window(type);
};

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

function getTopWindow(doc) {
    if (!doc) doc = window;
    if (doc != doc.parent) {
        getTopWindow(doc.parent);
    } else {
        window.topWindow = doc;
    }
};

/**
 * 左边菜单单击打开页面方式
 * @param title 菜单标题
 * @param url 链接地址
 * @param icon 菜单图标
*/
function openTab(title,url,icon) {
    title = title||"";
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);
    } else {
    	$("#tabs").tabs({
            onAdd: function () {
                window.top.$.messager.progress({
                    title: '系统提示',
                    text: '正在加载中，请稍候...'
                });
            },
            onUpdate: function () {
                window.top.$.messager.progress({
                    title: '系统提示',
                    text: '正在加载中，请稍候...'
                });
            }
        });
    	if($('#tabs').tabs('exists', 0)){
    		var tab = $('#tabs').tabs('getTab',0);
    		$('#tabs').tabs('update', {
    			tab: tab,
    			options: {
    				title: title,
        			content: createFrame(url),
        			closable: true,
        			icon: icon
    			}
    		});
    	}else{
    		$('#tabs').tabs('add', {
    			title: title,
    			content: createFrame(url),
    			closable: true,
    			icon: icon
    		});
    	}
    }
}

/**
 * 打开一个新的tab
 * @param title 菜单标题
 * @param url 链接地址
 * @param icon 菜单图标
*/
function openNewTab(title,url,icon) {
    title = title||"";
    if ($('#tabs').tabs('exists', title)) {
        $('#tabs').tabs('select', title);
    } else {
    	$("#tabs").tabs({
            onAdd: function () {
                window.top.$.messager.progress({
                    title: '系统提示',
                    text: '正在加载中，请稍候...'
                });
            },
            onUpdate: function () {
                window.top.$.messager.progress({
                    title: '系统提示',
                    text: '正在加载中，请稍候...'
                });
            }
        });
    	
		$('#tabs').tabs('add', {
			title: title,
			content: createFrame(url),
			closable: true,
			icon: icon
		});
    	
    }
}
/**
 * 创建iframe框架
 * @author heyj
 * @since 2014.05.28
*/
function createFrame(url) {
    var src = "";
    if (url != null && url != "") {
        src = '<iframe onload="javascript:frmLoad(this)" scrolling="auto" frameborder="0"  src="' + url + '" style="border: 0;width:100%;height:100%;overflow:hidden;"></iframe>';
    }
    return src;
}

function openUpdateWin(id,url,width,height){
	var options={
		id:id, //id一定要是唯一
		src:url,
		destroy:true,//点关闭时是否注销该窗口释放内容
		title:'修改用户信息',
		width:width||800,   
		height:height||500, 
		modal:true ,
		onLoad:function(){
			//这里的this已经指向到了对应的IFRAME
			//this.document.getElementById('NAME').value='名称';
		} 
	};
	openInTopWindow(options);
}
/**
 * 获取URL参数
 * @param name 参数名
 * @param url 请求地址
 * @author heyj
 * @since 2014.05.29
*/
function getUrlParam(name, url) {
    url = url || window.location.href;
    var theRequest = new Object();
    if (url.indexOf("?") != -1) {
        var str = url.split("?")[1];
        strs = str.split("&");
        for (var i = 0; i < strs.length; i++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
    }
    return theRequest[name];
};
/**
 * 限制字符串长度，超过长度用省略号代替
 * @param str 字符串
 * @param length 截取长度
 * @author heyj
 * @since 2014.05.29
*/
function limitStr(str, length) {
    var nstr = str.replace(/[^\x00-\xff]/g, 'xxxx');
    if (length - nstr.length < 0) {
        str = str.substr(0, length - 3) + "...";
    }
    return str;
}

/**
 * 在url后面加入参数
 * @param url 请求地址
 * @param key 键名
 * @param value 键值
 * @author heyj
 * @since 2014.05.29
*/
function addParamToUrl(url, key, value) {
    if (url.lastIndexOf("?") == -1) {
        url = url + "?" + key + "=" + value;
    } else {
        url = url + "&" + key + "=" + value;
    }
    return url;
}
/**
 * 对Date的扩展，将 Date 转化为指定格式的String
 * 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
 * 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
 * 例子：
 * (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
 * (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
*/
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(),    //day
        "h+": this.getHours(),   //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
        "S": this.getMilliseconds() //millisecond
    };
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
    (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1,
      RegExp.$1.length == 1 ? o[k] :
        ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

/**
 * 获取所选记录行(单选)
 * @param gridId DataGrid列表的id
 * @param errorMessage 没有选择或选择了多行的提示信息
 * @return 所选记录行对象，如果返回值为null则没有
 * @author heyj
 * @since 2014.05.29
*/
function getSingleSelectRow(gridId, errorMessage) {
    var rows = $('#' + gridId).datagrid('getChecked');
    var num = rows.length;
    if (num == 1) {
        return rows[0];
    } else {
        showMessage('info', errorMessage);
        return null;
    }
}

/**
 * 在DataGrid中获取所选记录的id,多个id用逗号分隔
 * 注：该方法使用的前提是：DataGrid的idField属性对应到列表Json数据中的字段名必须为id
 * @param gridId DataGrid列表的id
 * @param noOneSelectMessage 没有选择提示信息
 * @return 所选记录的id字符串(多个id用逗号隔开)
 * @author heyj
 * @since 2014.05.29
*/
function getSelectIds(gridId, noOneSelectMessage) {
    var rows = $('#' + gridId).datagrid('getChecked');
    var num = rows.length;
    var ids = null;
    if (num < 1) {
        if (null != noOneSelectMessage) showMessage('info', noOneSelectMessage);
        return null;
    } else {
        var idField = $('#' + gridId).datagrid('options')["idField"] || "Id";
        for (var i = 0; i < num; i++) {
            if (null == ids || i == 0) {
                ids = rows[i][idField];
            } else {
                ids = ids + "," + rows[i][idField];
            }
        }
        return ids;
    }
}

/**
 * 删除所选datagrid记录
 * 注：该方法会自动将所选记录的id(dataGrid的idField属性对应到列表Json数据中的字段名必须为id)
 * 另外，后台代码必须在操作完之后以ajax的形式返回Json格式的提示信息，后台格式：return Json(new { Success = true, Msg = "****" });
 *
 * @param gridId 列表id
 * @param requestURL 与后台服务器进行交互，进行具体删除操作的请求路径,参数名为ids
 * @param confirmMessage 删除确认信息
*/
function deleteSelect(gridId, requestURL, confirmMessage) {
    if (null == confirmMessage || typeof (confirmMessage) == "undefined" || "" == confirmMessage) {
        confirmMessage = "确定删除所选记录?";
    }
    var rows = $('#' + gridId).datagrid('getChecked');
    var num = rows.length;
    if (num < 1) {
        showMessage('info', '请选择你要删除的记录!');
    } else {
        showMessage('confirm', confirmMessage, function (r) {
            if (r) {
                var ids = [];
                $.each(rows, function (index, value) {
                    ids.push(value.Id);
                });
                $.post(requestURL, { "ids": ids.join(",") }, function (data) {
                    var json = typeof (data) == "string" ? $.evalJSON(data) : data;
                    if (json.Success) {
                        showMessage('info', "删除成功！");
                        reloadGrid(gridId);
                    } else {
                        showMessage('warning', '删除失败！');
                    }
                    clearSelect(gridId);
                });
            }
        });
    }
}

/**
 * 验证datagrid是否选中
 * @param gridId 列表id
 * @param singleSelect 是否单选
 * @returns 返回true 表示选择了数据；返回false 表示多选或者没有选择数据
 * @author heyj
 * @since 2014.05.29
*/
function validateSelect(gridId, singleSelect) {
    var selectRows = $('#' + gridId).datagrid("getChecked");
    if (selectRows.length == 0) {
        showMessage("info", "请选择数据！");
        return false;
    } else if (singleSelect == true && selectRows.length > 1) {
        showMessage("info", "只能选择一条数据，你已经选择了<font color='red'>" + selectRows.length + "</font>条");
        return false;
    }
    return true;
}

/**
 * 刷新DataGrid列表
 * @param 列表id
 * @author heyj
 * @since 2014.05.30
*/
function reloadGrid(gridId) {
	var grid;
	if(typeof(openerWindow)!='undefined'){
		grid = openerWindow.$('#' + gridId);//父窗体
		if (grid.hasClass("easyui-datagrid")) {
	        grid.datagrid('reload');
	    }
	    if (grid.hasClass("easyui-treegrid")) {
	        grid.treegrid('reload');
	    }
	}
	grid = $('#' + gridId);
    if (grid.hasClass("easyui-datagrid")) {
        grid.datagrid('reload');
    }
    if (grid.hasClass("easyui-treegrid")) {
        grid.treegrid('reload');
    }
}

/**
 * 取消dataGrid中的行选择
 * @param 列表id
 * @author heyj
 * @since 2014.05.30
*/
function clearSelect(gridId) {
    var grid = $('#' + gridId);
    if (grid != null && grid.length > 0) {
    	if(grid.hasClass("easyui-datagrid")){
    		grid.datagrid('clearSelections').datagrid("uncheckAll").datagrid("clearChecked");
    	}
    	else if (grid.hasClass("easyui-treegrid")) {
    		grid.treegrid('clearSelections').treegrid("uncheckAll").treegrid("clearChecked");
    	}
    }
}

/**
 * 数组转为json对象
 * @param o为传入的对象
 * @returns 返回json字符串
 * @author heyj
 * @since 2014.05.30
*/
function arrayToJson(o) {
    var r = [];
    if (typeof o == "string") return "\"" + o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n").replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
    if (typeof o == "object") {
        if (!o.sort) {
            for (var i in o)
                r.push(i + ":" + arrayToJson(o[i]));
            if (!!document.all && !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/.test(o.toString)) {
                r.push("toString:" + o.toString.toString());
            }
            r = "{" + r.join() + "}";
        } else {
            for (var i = 0; i < o.length; i++) {
                r.push(arrayToJson(o[i]));
            }
            r = "[" + r.join() + "]";
        }
        return r;
    }
    return o.toString();
}

/**
 * 带逗号的字符串转为json字符串
 * @param 带逗号字符串（如var str="1,2"）
 * @returns 返回['1','2']
*/
function strToJson(str) {
    var strTemp = "[";
    var lengI = str.indexOf(","); //逗号结束的地方

    while (lengI > 0) {
        var oneOf = str.substring(0, lengI);
        strTemp += "'" + oneOf + "',";
        str = str.replace(str.substring(0, lengI + 1), "");
        lengI = str.indexOf(",");
    }
    if (str.length > 0) {
        strTemp += "'" + str + "'";
    } else if (strTemp.endWith(',')) {
        strTemp = strTemp.substring(0, strTemp.length - 1);
    }
    strTemp += "]";
    return strTemp;
}

/**
 * 扩展字符串的方法startWith
 * @param 起始字符串
 * @returns 存在返回true，不存在返回false
 * @author heyj
 * @since 2014.05.30
*/
String.prototype.startWith = function (str) {
    var reg = new RegExp("^" + str);
    return reg.test(this);
};

/**
 * 扩展字符串的方法endWith
 * @param 结尾字符串
 * @returns 存在返回true，不存在返回false
 * @author heyj
 * @since 2014.05.30
*/
String.prototype.endWith = function (str) {
    var reg = new RegExp(str + "$");
    return reg.test(this);
};
/**
 * 去除字符串前后空字符串
 * @returns 返回处理结果
 * @author heyj
 * @since 2014.05.30
*/
String.prototype.trim = function () {
    return this.replace(/(^\s*)|(\s*$)/g, "");
};

/**
 * 创建guid
 * @returns 返回guid
 * @author heyj
 * @since 2014.05.30
*/
var newGuid = (function () { var a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split(""); return function (b, f) { var h = a, e = [], d = Math.random; f = f || h.length; if (b) { for (var c = 0; c < b; c++) { e[c] = h[0 | d() * f]; } } else { var g; e[8] = e[13] = e[18] = e[23] = "-"; e[14] = "4"; for (var c = 0; c < 36; c++) { if (!e[c]) { g = 0 | d() * 16; e[c] = h[(c == 19) ? (g & 3) | 8 : g & 15]; } } } return e.join("").toLowerCase(); }; })();
/**
 * 把字节转换为kb
*/
function fileSizeKb(value, row, index) {
    if (value != null) {
        return (value / 1024).toFixed(2).toString() + "KB";
    }
}

formatString = function () {
    for (var i = 1; i < arguments.length; i++) {
        var exp = new RegExp('\\{' + (i - 1) + '\\}', 'gm');
        arguments[0] = arguments[0].replace(exp, arguments[i]);
    }
    return arguments[0];
};

function exporter(opt, fieldJson) {
	debugger;
    var self = this;//{ params: {} };

    var defaultOptions = {
        action: $.getRootPath() + '/platform/exportfile/download.jsp',
        dataGetter: "url",
        dataAction: "",
        dataParams: {},
        fieldJson: fieldJson,
        titles: [[]],
        fileType: 'xls',
        compressType: 'none'
    };

    this.paging = function (page, rows) {
        self.params.dataParams.page = page;
        self.params.dataParams.rows = rows;
        return self;
    };
    this.queryParams = function (fieldName, fieldValue) {
        self.params.dataParams.fieldName = fieldName;
        self.params.dataParams.fieldValue = fieldValue;
        return self;
    };
    this.compress = function () {
        self.params.compressType = 'zip';
        return self;
    };

    this.title = function (filed, title) {
        self.params.titles[0][filed] = title;
        return self;
    };

    this.download = function (suffix) {
        self.params.fileType = suffix || "xls";
        self.params.dataParams = JSON.stringify(self.params.dataParams);
        self.params.titles = JSON.stringify(self.params.titles);
        var downloadHelper;
        if ($("#downloadHelper").length==0)
            downloadHelper = $('<iframe style="display:none;" id="downloadHelper"></iframe>').appendTo(document.body)[0];
        else
            downloadHelper = $("#downloadHelper")[0];
        var doc = downloadHelper.contentWindow.document;
        if (doc) {
            $(doc).html("");
            doc.open();
            doc.write('');//微软为doc.clear()有时会出bug
            doc.writeln(formatString("<html><body><form id='downloadForm' name='downloadForm' method='post' action='{0}'>", self.params.action));
            for (var key in self.params) {
                doc.writeln(formatString("<input type='hidden' name='{0}' value='{1}'>", key, self.params[key]));
            }
            doc.writeln('<\/form><\/body><\/html>');
            doc.close();
            var form = doc.forms[0];
            if (form) {
                form.submit();                
            }
        }
    };

    initFromGrid = function (grid) {
        var options = grid.$element().datagrid('options');
        if (grid.treegrid)
            options.url = options.url || grid.treegrid('options').url;

        var titles = [[]], length = Math.max(options.frozenColumns.length, options.columns.length);
        for (var i = 0; i < length; i++)
            titles[i] = (options.frozenColumns[i] || []).concat(options.columns[i] || []);

        self.params = $.extend(true, {}, defaultOptions, {
            dataAction: options.url,
            dataParams: options.queryParams,
            titles: titles
        });
    };
    if (opt.$element)
        initFromGrid(opt);
    else
        self.params = $.extend(true, {}, defaultOptions, opt);

    return self;
}

/**
 * 对dialog中的元素宽度进行初始化，根据页面宽度及设定的控件宽度百分比，来设定各个控件的实际宽度
 * @param obj jquery对象
 * @author heyj
 * @since 2014.05.29
*/
function autoInputWidth(obj) {
    $.each([
        {
            selector: 'input.input,textarea.input',
            handler: function (jq) {
                jq.each(function () {
                    var width = $(this).parent().width();
                    $(this).width(width - 18);
                });
            }
        },
        {
            selector: 'input[comboname],input.combo-f',
            handler: function (jq) {
                jq.each(function () {
                    var width = $(this).parent().width();
                    $(this).combo('resize', width - 15);
                });
            }
        },
        {
            selector: '.easyui-my97',
            handler: function (jq) {
                jq.each(function () {
                    var width = $(this).parent().parent().width();
                    $(this).my97('setWidth', width - 37);
                });
            }
        },
        {
            selector: '.easyui-datagrid',
            handler: function (jq) {
                jq.each(function () {
                    var width = $(this).parent().width();
                    $(this).datagrid('resize', width - 2);
                });
            }
        },
        {
            selector: '.easyui-combogrid',
            handler: function (jq) {
                jq.each(function () {
                    var width = $(this).parent().width();
                    $(this).combogrid('resize', width - 2);
                });
            }
        }


    ], function () {
        var jq = dialog.find(this.selector);
        if (jq.length > 0) this.handler(jq);
        if (dialog.is(this.selector)) this.handler(dialog);

    });
}

/**
 * JS处理样式高度自适应
 * @param oField
 */
function autoGrow (oField) {
  	if (oField.scrollHeight > oField.clientHeight) {
    	oField.style.height = oField.scrollHeight + "px";
  	}
}
