var qWidget = {
    
    jQuery : $,
    
    settings : {
        columns : '.column',
        widgetSelector: '.widget',
        handleSelector: '.widget-head',
        toolSelector: '.widget-tool',
       	editSelector: '.widget-edit',
        contentSelector: '.widget-content',
        widgetDefault : {
            movable: true,
            removable: true,
            collapsible: true,
            editable: true
        }
    },

    init : function () {
        this.addWidgetControls();
        this.makeSortable();
    },
    
    /*
getWidgetSettings : function (id) {
        var $ = this.jQuery,
            settings = this.settings;
        return (id&&settings.widgetIndividual[id]) ? $.extend({},settings.widgetDefault,settings.widgetIndividual[id]) : settings.widgetDefault;
    },
*/
    
    addWidgetControls : function () {
        var qWidget = this,
            $ = this.jQuery,
            settings = this.settings;
            
        $(settings.widgetSelector, $(settings.columns)).each(function () {
           // var thisWidgetSettings = qWidget.getWidgetSettings(this.id);
          var thisWidgetSettings =settings.widgetDefault;
			if($(this).attr("collapsable")!="false") {
                $('<a href="#" class="portal_icon_collapse"></a>').mousedown(function (e) {
                    e.stopPropagation();    
                }).toggle(function () {
                    $(this).addClass("portal_icon_extend")
                        .parents(settings.widgetSelector)
                            .find(settings.contentSelector).hide();
                    return false;
                },function () {
                    $(this).removeClass("portal_icon_extend")
                        .parents(settings.widgetSelector)
                            .find(settings.contentSelector).show();
                    return false;
                }).appendTo($(settings.toolSelector,this));
            }
			
			 if($(this).attr("editable")!="false") {
                $('<a href="#" class="portal_icon_edit"></a>').mousedown(function (e) {
                    e.stopPropagation();    
                }).toggle(function () {
                   $(this).addClass("portal_icon_ok")
                        .parents(settings.widgetSelector)
                            .find('.edit-box').show().find('input').focus();
                    return false;
                },function () {
                    $(this).removeClass("portal_icon_ok")
                        .parents(settings.widgetSelector)
                            .find('.edit-box').hide();
                    return false;
                }).appendTo($(settings.toolSelector,this));
			
		    if($(this).attr("removable")!="false") {
                $('<a href="#" class="portal_icon_close"></a>').mousedown(function (e) {
                    e.stopPropagation();    
                }).click(function () {
                    if(confirm('确定删除吗?')) {
                        $(this).parents(settings.widgetSelector).animate({
                            opacity: 0    
                        },function () {
                            $(this).wrap('<div/>').parent().slideUp(function () {
                                $(this).remove();
                            });
                        });
                    }
                    return false;
                }).appendTo($(settings.toolSelector, this));
            }
            
           
                $('<div class="edit-box" style="display:none;"/>')
                    .append('<ul><li class="item"><label>新标题：</label><input value="' + $('.widget-title',this).text() + '"/></li>')
                    .append('</ul>')
                    .appendTo($(settings.editSelector,this));
            }
              
            
        });
        
        $('.edit-box').each(function () {
            $('input',this).keyup(function () {
                $(this).parents(settings.widgetSelector).find('.widget-title').text( $(this).val().length>20 ? $(this).val().substr(0,20)+'...' : $(this).val() );
            });
        });
        
    },
    
    attachStylesheet : function (href) {
        var $ = this.jQuery;
        return $('<link href="' + href + '" rel="stylesheet" type="text/css" />').appendTo('head');
    },
    
    makeSortable : function () {
        var qWidget = this,
            $ = this.jQuery,
            settings = this.settings,
            $sortableItems = (function () {
			var notSortable = "";
            $(settings.widgetSelector,$(settings.columns)).each(function (i) {
            	 alert(this.id);
                if($(this).attr("movable")=="false"){
					if(!this.id) {
                        this.id = 'widget-no-id-' + i;
                    }
                    notSortable += '#' + this.id + ',';
				}
            });
			if(notSortable==""){
				return $('> li', settings.columns);
			}
			else{
				 return $('> div:not(' + notSortable + ')', settings.columns);
			}
            })();
        
        $sortableItems.find(settings.handleSelector).css({
            cursor: 'move'
        }).mousedown(function (e) {
            $sortableItems.css({width:''});
            $(this).parent().css({
                width: $(this).parent().width() + 'px'
            });
        }).mouseup(function () {
            if(!$(this).parent().hasClass('dragging')) {
                $(this).parent().css({width:''});
            } else {
                $(settings.columns).sortable('disable');
            }
        });

        $(settings.columns).sortable({
            items: $sortableItems,
            connectWith: $(settings.columns),
            handle: settings.handleSelector,
            placeholder: 'widget-placeholder',
            forcePlaceholderSize: true,
            revert: 300,
            delay: 100,
            opacity: 0.8,
            containment: 'document',
            start: function (e,ui) {
                
            },
            stop: function (e,ui) {
                $(ui.item).css({width:''}).removeClass('dragging');
                $(settings.columns).sortable('enable');
                
                //var show = $(".loader");  
                //var orderlist = $(".orderlist"); 
                var listleft = $("div[id = 'column_left']");
                var listcenter = $("div[id = 'column_center']");
                var listright = $("div[id = 'column_right']"); 
                
                var new_order_left = ""; //左栏布局
                var new_order_center = "";//中栏布局
                var new_order_right = "";//右栏布局
                listleft.children(".widget").each(function(i) {
                	new_order_left +="column_left,"+i+","+$(this).find(".widget-title").text()+";";
                    //new_order_left.push(i);
                    //new_order_left.push($(this).find(".widget-title").text());
                 }); 
                listcenter.children(".widget").each(function(i) { 
                	new_order_center +="column_center,"+i+","+$(this).find(".widget-title").text()+";";
                    //new_order_center.push(i);
                    //$(this).find(".widget-title").text();
                 }); 
                listright.children(".widget").each(function(i) { 
                	new_order_right+="column_right,"+i+","+$(this).find(".widget-title").text()+";";
                    //new_order_right.push(i);
                    //$(this).find(".widget-title").text();
                 }); 
                
                $.ajax({
                    type: "POST",
                    url: ctx+"/onlineApprove/test",
                    data: {"paramLeft":new_order_left,"paramCenter":new_order_center,"paramRight":new_order_right},
                    dataType: "JSON",
                    success: function(data){
                     }
                });

            }
        });
    }
  
};
$(function(){
	qWidget.init();
})
