	String.prototype.endWith = function(s) {
		if (s == null || s == "" || this.length == 0 || s.length > this.length)
			return false;
		if (this.substring(this.length - s.length) == s)
			return true;
		else
			return false;
		return true;
	}
	
	String.prototype.startWith = function(s) {
		if (s == null || s == "" || this.length == 0 || s.length > this.length)
			return false;
		if (this.substr(0, s.length) == s)
			return true;
		else
			return false;
		return true;
	}

	var curMenu = null, zTree_Menu = null;
	var setting = {
		view: {
			showLine: false,
			showIcon: false,
			selectedMulti: false,
			dblClickExpand: false,
			addDiyDom: addDiyDom
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "id",
				pIdKey: "parentID"
			},
			key :{
				url:"ztreeURL"
			}
		},
		callback: {
			beforeClick: beforeClick,
			onClick:zTreeOnClick
		}
	};
	
	function zTreeOnClick(event, treeId, treeNode) {
		if(treeNode.url.startWith("http://")) {
			addTab({id:treeId,title:treeNode.name,url:treeNode.url ,closable:true}) ;
		}else{
			addAndRefreshTab(treeId , treeNode.name , treeNode.url , true) ;
			//addTab({id:treeId,title:treeNode.name,url:context + treeNode.url ,closable:true}) ;
		}
			
	    return false ;
	};

	function addDiyDom(treeId, treeNode) {
		var spaceWidth = 5;
		var switchObj = $("#" + treeNode.tId + "_switch"),
		icoObj = $("#" + treeNode.tId + "_ico");
		switchObj.remove();
		icoObj.before(switchObj);

		if (treeNode.level > 1) {
			var spaceStr = "<span style='display: inline-block;width:" + (spaceWidth * treeNode.level)+ "px'></span>";
			switchObj.before(spaceStr);
		}
	}

	function beforeClick(treeId, treeNode) {
		if (treeNode.level == 0 ) {
			var zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.expandNode(treeNode);
			return false;
		}
		return true;
	}


$(function(){

	$('#accordionMenu').accordion({
		border : false,
		fit : true
	});
	initAccdionMenu(context + "/app/getMenu/none" , true) ;
	
	var $menu = $("#navMenu") ;
	var $box = $("#west");
	$menu.find("li>a").click(
			function() {
				var $a = $(this);
				if($a.attr("href") == context +"/" || $a.attr("href").startWith("http://")) {
					return true ;
				}else {
					initAccdionMenu($a.attr("href") , false) ;
					return false;
				}
					
			});
	
}) ;

function initAccdionMenu(url , isIndex) {
	var length = $('#accordionMenu').accordion('panels').length ;
	for(var i = length - 1 ; i >= 0  ; i --){
		 $('#accordionMenu').accordion('remove' , 0) ;
	}
	$.ajax({
		url : url,
		type : "post",
		cache:false,
		dataType : "json",
		success : function(data) {
			$.each(data, function(index, value) {
				$('#accordionMenu').accordion('add', {
					title : value.name,
					iconCls: value.icon ? value.icon : 'icon-accordion',
					animate:true,
					id : "accordion" + value.id,
					content: '<div class="accordionContent"><ul class="ztree" id="accordionMenu'+value.id+'"></ul></div>' ,
					selected : index == 0 ? true : false
				});
				
				$.ajax({
					url : context + "/app/getSubMenu/" + value.id + "/" + isIndex,
					type : "post",
					dataType : "json",
					cache:false,
					success : function(data) {
						var treeObj = $("#accordionMenu" + value.id);
						$.fn.zTree.init(treeObj, setting, data);
						zTree_Menu = $.fn.zTree.getZTreeObj("accordionMenu" + value.id);
						curMenu = zTree_Menu.getNodes()[0] ;
						zTree_Menu.expandNode(curMenu);

						treeObj.hover(function () {
							if (!treeObj.hasClass("showIcon")) {
								treeObj.addClass("showIcon");
							}
						}, function() {
							treeObj.removeClass("showIcon");
						});
					}
				}) ;
				
			});
			
		}
	});
	
}

/**
 * 打开新的标签页，若该标签已存在，则让该页显示
*/
function addTab(opt){
	if(!$('#tabs').tabs('exists', opt.title)){
		$('#tabs').tabs('add',{
			id:opt.id,
		    title:opt.title,
		    content:'<iframe width="100%" height="100%" scrolling="auto" id="' + opt.id + 'Frame" name="' + opt.id + 'Frame" frameborder="0" src="' + opt.url + '"></iframe>',
		    closable:opt.closable,
		    tabWidth:120
		});
	}else{
		$('#tabs').tabs('select', opt.title) ;
	}
}

/**
 * 刷新当前标签页
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
 * 刷新当前标签页
*/
function addAndRefreshTab(frameID , title , href , closeable) {
    var index_tabs = $('#tabs');
    if(index_tabs.tabs("exists" , title)){
    	index_tabs.tabs("select",title) ;
    	removeTab() ;
    }
    addTab({id:frameID,title:title,url:context + href ,closable:closeable}) ;
}

/**
 * 移除当前标签页
*/
function removeTab() {
    var index_tabs = $('#tabs');
    var index = index_tabs.tabs('getTabIndex', index_tabs.tabs('getSelected'));
    var tab = index_tabs.tabs('getTab', index);
    //debugger;
    if (tab.panel('options').closable) {
        index_tabs.tabs('close', index);
    } else {
        $.messager.alert('警告','[' + tab.panel('options').title + ']不可以被关闭！','error');
    }
}

/**
 * 缩放当前标签页，用于最大化
*/
function resizeTab(obj) {
    if ($(obj).linkbutton("options").iconCls.toLowerCase() == 'icon-arrow-out') {
        $('#layout').layout('hidden', 'all');
        $('#layout').layout("panel", "center").panel("maximize");
        $(obj).linkbutton({
            iconCls: 'icon-arrow-in'
        });
    }
    else {
        $('#layout').layout('show', 'all');
        $('#layout').layout("panel", "center").panel("restore");
        $(obj).linkbutton({
            iconCls: 'icon-arrow-out'
        });
    }
}

/**
 * 显示个人信息
 */
function getUserMsg(){
	addAndRefreshTab('','个人信息' , '/app/getUserMsg'  , true) ;
}