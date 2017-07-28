/**
 * 使用easyui，这里实现初始化的操作，包括初始化框架、左侧菜单、时钟等
 */
$(function() {
	initMenu();//初始化左侧菜单
	getDefault();
	//showDate();
	$(".colour div").removeClass("selected");
	$(".colour").find("li[rel=" + $.getThemes() + "]").children().first().addClass("selected");
	$(".colour li").click(function() {
		$(".colour div").removeClass("selected");
		$(this).children().first().addClass("selected");
		changeTheme($(this).attr("rel"));
	});
	//绑定tabs的右键菜单
	$("#tabs").tabs({
		onContextMenu : function(e, title) {
			e.preventDefault();
			$('#tabsMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data("tabTitle", title);
		},
		onSelect : function(title, index) {
			//updateHash(index);//@author 暂时不显示hash 2014.07.29
		}
	});

	//实例化menu的onClick事件
	$("#tabsMenu").menu({
		onClick : function(item) {
			var curTabTitle = $(this).data("tabTitle");
			if (item.id == "mm-tabupdate") {
				var currTab = $('#tabs').tabs('getSelected');
				var url = $(currTab.panel('options').content).attr('src');
				$('#tabs').tabs('update', {
					tab : currTab,
					options : {
						content : createFrame(url)
					}
				});
			} else if (item.id == "mm-tabclose") {
				var tab = $('#tabs').tabs("getTab", curTabTitle);
				if (tab.panel('options').closable) {
					$('#tabs').tabs('close', curTabTitle);
				}
			} else if (item.id == 'mm-tabcloseall') {
				var len = $('#tabs').tabs("tabs").length;
				for (var i = len - 1; i > -1; i--) {
					var tab = $('#tabs').tabs('getTab', i);
					if (tab.panel('options').closable) {
						$('#tabs').tabs('close', i);
					}
				}
			} else if (item.id == 'mm-tabcloseother') {
				var currTab = $('#tabs').tabs('getSelected');
				var tabs = $.grep($('#tabs').tabs('tabs'), function(tab, i) {
					return tab != currTab && tab.panel('options').closable;
				});
				$.each(tabs, function(i, tab) {
					$('#tabs').tabs('close', $(this).panel("options").title);
				});
			} else if (item.id == 'mm-tabcloseright') {
				var currTab = $('#tabs').tabs('getSelected');
				var len = $('#tabs').tabs("tabs").length;
				var index = $('#tabs').tabs('getTabIndex', currTab);
				for (var i = len - 1; i > index; i--) {
					var tab = $('#tabs').tabs('getTab', i);
					if (tab.panel('options').closable) {
						$('#tabs').tabs('close', i);
					}
				}
			} else if (item.id == 'mm-tabcloseleft') {
				var currTab = $('#tabs').tabs('getSelected');
				var index = $('#tabs').tabs('getTabIndex', currTab);
				for (var i = index - 1; i > -1; i--) {
					var tab = $('#tabs').tabs('getTab', i);
					if (tab.panel('options').closable) {
						$('#tabs').tabs('close', i);
					}
				}
			}
		}
	});
	//loadHash();//加载hash //@author 暂时不显示hash 2014.07.29
});
/**
 * 获得默认主题
 */
function getDefault() {
	return;
	$.ajax({
		url : "platSettingService/getPersonTheme.run",
		type : "post",
		dataType : "text",
		success : function(data) {
			$(".colour div").removeClass("selected");
			$(".colour").find("li[rel=" + data + "]").children().first().addClass("selected");
			changeTheme(data);
		}
	});
}

/**
 * 改变主题
 * @param themeName 主题名称
 * @author heyj
*/
function changeTheme(themeName) {
	var themes = $('#themes');
	var href = themes.attr('href');
	href = href.substring(0, href.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
	themes.attr('href', href);

	var $iframe = $('iframe');
	if ($iframe.length > 0) {
		for (var i = 0; i < $iframe.length; i++) {
			var ifr = $iframe[i];
			$(ifr).contents().find('#themes').attr('href', href);
		}
	}
	//提交后台
	//    $.post("/changeTheme.run", { theme: themeName }, function (data) {
	//        if (data.Success) {
	//            //showMessage("info", "主题应用成功！");
	//        }
	//    });
}

/**
 * 显示时间
 * @author heyj
 * @since 2014.06.03
*/
/*function showDate() {
	$("#bgclock").html(getTime());
	setInterval(function() {
		$("#bgclock").html(getTime());
	}, 200);
}
*/
updateHash = function(index) {
	var panel = $('#tabs').tabs('getSelected').panel('panel');
	var frame = panel.find('iframe');
	try {
		if (frame.length > 0) {
			for (var i = 0; i < frame.length; i++) {
				if (frame[i].src != null && frame[i].src != "") {
					window.location.hash = frame[i].src.replace($.getRootPath(), "");
				}
			}
			if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
				try {
					CollectGarbage();
				} catch (e) {
				}
			}
		}
	} catch (e) {
	}
};

loadHash = function() {
	var hash = new String(document.location.hash);
	if (hash != null && hash != "") {
		openTab("新标签页", $.getContextPath() + hash.slice(1));
	}
};

/**
 * 框架页面初始化
*/
function frmLoad(obj) {
	if (!obj.readyState || obj.readyState == "complete") {
		window.top.$.messager.progress('close');
	}
}


/**
 * 初始化左侧菜单
 * @author heyj
 * @since 2014.05.28
*/
function initMenu() {
	$("#menu").html('<ul id="top"></ul>');
	var ids = [];
	var treeMenu = null;
	$.ajax({
		url : ctx + "/main/getPermMenus",
		type : "post",
		dataType : "json",
		success : function(data) {
		    var todo = false;
			$.each(data,function(i,item){
				var isParent=item.isParent;
				var id=item.id;
				var name=item.text;
				var url=item.url;
				if(url==null || url=="")
				{
					url="";
				}
				if(url.indexOf("sp/worklist/todo")!=-1){//判断是否存在待办件菜单
					todo = true;
				}
				if(isParent==false)
				{
					$("#top").append('<li id=\''+id+'\'><a onclick="opentap(\'' + name + '\',\'' + url + '\')">'+name+'</a></li>');
				}else{
					$("#top").append('<li id=\''+id+'\'><a class="inactive" onclick="bindMenu(this,\'' + name + '\',\'' + id + '\')">'+name+'</a></li>');
				}
			});
			//$(".menu ul li").menu();
			if(todo){//当存在待办件时菜单时，才默认打开待办件。
				openTab("待办件", $.getContextPath() + "/sp/worklist/todo");
			}
		}
	});
}

//刷新菜单数据
function refreshMenu(type){//type用于判定是否打开待办件，保存办件时，需要停留在打开的件，不需要打开待办件。
	$.ajax({
		url : ctx + "/main/refreshMenu",
		type : "post",
		dataType : "json",
		success : function(data) {
			var todo = false;
			for(var i = 0;i<$('#top li a').length;i++){//第一级菜单
				var aObj = $('#top li a')[i];
				if(aObj.innerHTML.indexOf("待办件(")!=-1){
					todo = true;
					aObj.innerHTML = "待办件("+data.todoCount+")";
				}
				if(aObj.innerHTML.indexOf("在办件(")!=-1){
					aObj.innerHTML = "在办件("+data.doingCount+")";
				}
				if(aObj.innerHTML.indexOf("补齐补正件(")!=-1){
					aObj.innerHTML = "补齐补正件("+data.bqbzCount+")";
				}
				if(aObj.innerHTML.indexOf("特别程序审核(")!=-1){
					aObj.innerHTML = "特别程序审核("+data.teBieChengXuCount+")";
				}
				if(aObj.innerHTML.indexOf("办结件(")!=-1){
					aObj.innerHTML = "办结件("+data.doneCount+")";
				}
				if(aObj.innerHTML.indexOf("网上收件(")!=-1){
					aObj.innerHTML = "网上收件("+data.yslCount+")";
				}
				if(aObj.innerHTML.indexOf("暂停件(")!=-1){
					aObj.innerHTML = "暂停件("+data.pauseCount+")";
				}
				if(aObj.innerHTML.indexOf("所有件(")!=-1){
					aObj.innerHTML = "所有件("+data.allCount+")";
				}
			}
			if(todo&&!(type=="save")){//当存在待办件时菜单时，才默认打开待办件。
				openTab("待办件", $.getContextPath() + "/sp/worklist/todo");
			}
		}
	});
}


function opentap(text,url){
	if(text.indexOf("待办件")!=-1){
		openTab("待办件", url);
	}else if(text.indexOf("在办件")!=-1){
		openTab("在办件", url);
	}else if(text.indexOf("补齐补正件")!=-1){
		openTab("补齐补正件", url);
	}else if(text.indexOf("特别程序审核")!=-1){
		openTab("特别程序审核", url);
	}else if(text.indexOf("办结件")!=-1){
		openTab("办结件", url);
	}else if(text.indexOf("暂停件")!=-1){
		openTab("暂停件", url);
	}else if(text.indexOf("所有件")!=-1){
		openTab("所有件", url);
	}else if(text.indexOf("网上收件")!=-1){
		openTab("网上收件", url);
	}else{
		openTab(text, url);
	}
}


/**
 * 绑定菜单
 * @param obj 当前对象
 * @param title 菜单名称
 * @param id 菜单id
 * @author heyj
 * @since 2014.05.28
 * 
*/
function bindMenu(obj,title, pId) {
	if($(obj).attr("class")=="inactive"){
		$(obj).removeClass('inactive');
		$(obj).addClass('inactives');//展开图标
		var aObj0 = $('#'+pId).parent('ul').parent('li').children('a')[0];//父节点的a
		for(var i = 0;i<$('#top li a').length;i++){//处理同一级只有一个展开图标
			var aObj = $('#top li a')[i];
			if($(aObj).attr("class")=="inactives"&&aObj!=obj&&aObj0!=aObj){
				$(aObj).removeClass('inactives');
				$(aObj).addClass('inactive');
			}
		}
	}else{
		$(obj).removeClass('inactives');
		$(obj).addClass('inactive');//折叠图标
	}
	$.ajax({
		url : ctx + "/main/getPermMenus",
		data : {
			text : title,
			id : pId
		},
		type : "post",
		dataType : "json",
		success : function(data) {
			if($('#'+pId+' ul').length>0){
				var ulObj = $('#'+pId+' ul')[0];
				if($(ulObj).attr("class")=="ul"){
					$(ulObj).removeClass("ul");
					$(ulObj).addClass("uls");//展开
					for(var i = 0;i<$('#top li ul').length;i++){//处理同一级只有一个展开
						var ulObj1 = $('#top li ul')[i];
						var ulObj0 = $('#'+pId).parent('ul')[0];//父节点的ul
						if($(ulObj1).attr("class")=="uls"&&ulObj1!=ulObj&&ulObj0!=ulObj1){
							$(ulObj1).removeClass('uls');
							$(ulObj1).addClass('ul');
						}
					}
				}else{
					$(ulObj).removeClass("uls");
					$(ulObj).addClass("ul");//折叠
				}
			}else{
				for(var i = 0;i<$('#top li ul').length;i++){//处理同一级只有一个展开
					var ulObj1 = $('#top li ul')[i];
					var ulObj0 = $('#'+pId).parent('ul')[0];//父节点的ul
					if($(ulObj1).attr("class")=="uls"&&ulObj0!=ulObj1){
						$(ulObj1).removeClass('uls');
						$(ulObj1).addClass('ul');
					}
				}
				$("#"+pId).append('<ul class="uls"></ul>');
				$.each(data,function(i,item){
					var isParent=item.isParent;
					var id=item.id;
					var name=item.text;
					var url=item.url;
					if(isParent==false){
						$("#"+pId+' ul').append('<li id=\''+id+'\'><a onclick="opentap(\'' + name + '\',\'' + url + '\')">'+name+'</a></li>');
					}else{
						$("#"+pId+' ul').append('<li id=\''+id+'\'><a class="inactive" onclick="bindMenu(this,\'' + name + '\',\'' + id + '\')">'+name+'</a></li>');
					}
				});
			}
		}
	});
}

/**
 * 刷新当前标签页
 * @author heyj
 * @since 2014.05.28
*/
function reloadTab() {
	var index_tabs = $('#tabs');
	var href = index_tabs.tabs('getSelected').panel('options').href;
	if (href) {/*说明tab是以href方式引入的目标页面*/
		var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
		index_tabs.tabs('getTab', index).panel('refresh');
	} else {/*说明tab是以content方式引入的目标页面*/
		var panel = index_tabs.tabs('getSelected').panel('panel');
		var frame = panel.find('iframe');
		try {
			if (frame.length > 0) {
				for (var i = 0; i < frame.length; i++) {
					frame[i].contentWindow.document.write('');
					frame[i].contentWindow.close();
					frame[i].src = frame[i].src;
				}
				if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
					try {
						CollectGarbage();
					} catch (e) {
					}
				}
			}
		} catch (e) {
		}
	}
}
/**
 * 移除当前标签页
 * @author heyj
 * @since 2014.05.28
*/
function removeTab() {
	var index_tabs = $('#tabs');
	var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
	var tab = index_tabs.tabs('getTab', index);
	debugger;
	if (tab.panel('options').closable) {
		index_tabs.tabs('close', index);
	} else {
		showMessage('error', '[' + tab.panel('options').title + ']不可以被关闭！');
	}
}
/**
 * 缩放当前标签页，用于最大化
 * @author heyj
 * @obj 当前对象
 * @since 2014.05.28
*/
function resizeTab(obj) {
	if ($(obj).linkbutton("options").iconCls.toLowerCase() == 'icon-arrow-out') {
		$('#wrap').layout('hidden', 'all');
		$('#wrap').layout("panel", "center").panel("maximize");
		$(obj).linkbutton({
			iconCls : 'icon-arrow-in'
		});
	} else {
		$('#wrap').layout('show', 'all');
		$('#wrap').layout("panel", "center").panel("restore");
		$(obj).linkbutton({
			iconCls : 'icon-arrow-out'
		});
	}
}

/**
 * 获取本地时钟
 * @returns 返回日期时间
 * @author heyj
 * @since 2014.06.03
*/
function getTime() {
	var now = new Date();
	var year = now.getFullYear(); // getFullYear getYear
	var month = now.getMonth();
	var date = now.getDate();
	var day = now.getDay();
	var hour = now.getHours();
	var minu = now.getMinutes();
	var sec = now.getSeconds();
	var week;
	month = month + 1;
	if (month < 10)
		month = "0" + month;
	if (date < 10)
		date = "0" + date;
	if (hour < 10)
		hour = "0" + hour;
	if (minu < 10)
		minu = "0" + minu;
	if (sec < 10)
		sec = "0" + sec;
	var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	week = arr_week[day];
	var time = "";
	//time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;
	time = year + "年" + month + "月" + date + "日" + " " + week;
	return time;
}
