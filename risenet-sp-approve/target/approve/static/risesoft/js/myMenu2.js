/**
 * 使用easyui，这里实现初始化的操作，包括初始化框架、左侧菜单、时钟等
 */
$(function () {
	initMenu(processDefinitionKey);//初始化左侧菜单
	getDefault();
	showDate();
	$(".colour div").removeClass("selected");
    $(".colour").find("li[rel="+$.getThemes()+"]").children().first().addClass("selected");
    $(".colour li").click(function () {
        $(".colour div").removeClass("selected");
        $(this).children().first().addClass("selected");
        changeTheme($(this).attr("rel"));
    });
	//绑定tabs的右键菜单
	$("#tabs").tabs({
		onContextMenu : function (e, title) {
			e.preventDefault();
			$('#tabsMenu').menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data("tabTitle", title);
		},
		onSelect:function(title, index){
			//updateHash(index);//@author 暂时不显示hash 2014.07.29
		}
	});
	
	//实例化menu的onClick事件
	$("#tabsMenu").menu({
		onClick : function (item) {
			var curTabTitle = $(this).data("tabTitle");
			if(item.id=="mm-tabupdate"){
				var currTab = $('#tabs').tabs('getSelected');
		        var url = $(currTab.panel('options').content).attr('src');
		        $('#tabs').tabs('update', {
		            tab: currTab,
		            options: {
		                content: createFrame(url)
		            }
		        });
			}else if(item.id=="mm-tabclose"){
				var tab=$('#tabs').tabs("getTab",curTabTitle);
				if(tab.panel('options').closable){
					$('#tabs').tabs('close', curTabTitle);
				}
			}else if(item.id=='mm-tabcloseall'){
				var len=$('#tabs').tabs("tabs").length;
				for(var i=len-1;i>-1;i--){
					var tab= $('#tabs').tabs('getTab',i);
					if(tab.panel('options').closable){
						$('#tabs').tabs('close', i);
					}
				}
			}else if(item.id=='mm-tabcloseother'){
				var currTab= $('#tabs').tabs('getSelected');
				var tabs=$.grep($('#tabs').tabs('tabs'), function(tab,i){
					  	return tab!= currTab&&tab.panel('options').closable;
					});
				$.each(tabs,function (i, tab) {
					$('#tabs').tabs('close', $(this).panel("options").title);
				});
			}else if(item.id=='mm-tabcloseright'){
				var currTab= $('#tabs').tabs('getSelected');
				var len=$('#tabs').tabs("tabs").length;
				var index= $('#tabs').tabs('getTabIndex',currTab);
				for(var i=len-1;i>index;i--){
					var tab= $('#tabs').tabs('getTab',i);
					if(tab.panel('options').closable){
						$('#tabs').tabs('close', i);
					}
				}
			}else if(item.id=='mm-tabcloseleft'){
				var currTab= $('#tabs').tabs('getSelected');
				var index= $('#tabs').tabs('getTabIndex',currTab);
				for(var i=index-1;i>-1;i--){
					var tab= $('#tabs').tabs('getTab',i);
					if(tab.panel('options').closable){
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
function getDefault(){
	return;
	$.ajax({
		url:"platSettingService/getPersonTheme.run",
		type:"post",
		dataType:"text",
		success:function(data){
			$(".colour div").removeClass("selected");
		    $(".colour").find("li[rel="+data+"]").children().first().addClass("selected");
		        changeTheme(data);
		}
	});
}

/**
 * 改变主题
 * @param themeName 主题名称
 * @author heyj
*/
function changeTheme(themeName){
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
function showDate(){
	$("#bgclock").html(getTime());
	setInterval(function () { $("#bgclock").html(getTime()); }, 200);
}

updateHash = function (index) {
	var panel = $('#tabs').tabs('getSelected').panel('panel');
    var frame = panel.find('iframe');
    try {
        if (frame.length > 0) {
            for (var i = 0; i < frame.length; i++) {
            	if(frame[i].src!=null&&frame[i].src!=""){
            		window.location.hash =frame[i].src.replace($.getRootPath(),"");
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

loadHash = function()
{
	var hash=new String(document.location.hash);
	if(hash!=null&&hash!=""){
		openTab("新标签页",$.getContextPath()+hash.slice(1));
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
function initMenu(processDefinitionKey) {
	var ids = [];
	var treeMenu=null;
	$("#leftMenu").html('<div id="topMenu" class="easyui-accordion" ></div>');
	$.parser.parse($("#leftMenu"));
	$('#topMenu').accordion({
		border : false,
		fit : true,
		onSelect : function(title, index) {
			var p = $('#topMenu').accordion('getSelected');
			//var url=p.attr("url");
			var depts=p.attr("depts");
			//alert(url);
			//if(null != url && "" != url)
			//{
			//	openTab(title,url);
			//}else
			//{
				treeMenu = $("<ul></ul>");
				treeMenu.css("overflow-y", "auto");
				bindMenu(treeMenu, title, ids[index],depts);
				treeMenu.appendTo(p.html(""));
			//}
		}
	});
	
	$.ajax({
		url: ctx + "/menu/getIntellPM?processDefinitionKey="+processDefinitionKey,
		type:"post",
		dataType:"json",
		success:function(data){
			$.each(data, function(index, value) {
				ids[index] = value.id;
				$('#topMenu').accordion('add', {
					title : value.text,
					id : value.id,
					selected : index == 0 ? true : false
				});
				if(null != value.url && "" != value.url)
				{
					$('#'+value.id).attr("url",value.url);
					$('#'+value.id).attr("depts",value.depts);
				}else
				{
					$('#'+value.id).attr("url","");
					$('#'+value.id).attr("depts",value.depts);
				}
			});
		}
	});
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
function bindMenu(obj, title, id,depts) {
	$.ajax({
		url:ctx + "/menu/getPM?processDefinitionKey="+processDefinitionKey,
		type:"post",
		dataType:"json",data : {text:title,id:id},
		success:function(data){
			obj.tree({
				animate : true,
				checkbox : false,
				data : data,
				dataType : "json",
				onClick : function(node) {//这里的node是后台传过来的数据
					if (node.attributes != "") {
						var nodeUrl = node.url;//当url中包含&、?时，说明是新建办文，需要在url后面添加菜单的id用于判断用户所在的有权限的部门
						if(nodeUrl.indexOf("&") > 0||nodeUrl.indexOf("?") > 0){
							nodeUrl=nodeUrl+"&id="+node.id;
						}
						if (null != nodeUrl && "" != nodeUrl) {
							nodeUrl = $.getContextPath() + "/" + nodeUrl;
							openTab(node.text, nodeUrl);
						}
					}
				}
			});
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
            iconCls: 'icon-arrow-in'
        });
    }
    else {
        $('#wrap').layout('show', 'all');
        $('#wrap').layout("panel", "center").panel("restore");
        $(obj).linkbutton({
            iconCls: 'icon-arrow-out'
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
