/**
 * 
 * box组件
 * 
 * author chenli
 * since 2014/7/21
 */
(function($){
	
	function create(target){
		var opts = $.data(target, 'boxlist').options;
		var html = '';
		if(opts.values == null){
			html += '<div class="boxlist"><ul></ul></div>';
		}else{
			var values = opts.values.split(opts.separator);
			var texts = opts.texts.split(opts.separator);
			
			html += '<div class="boxlist"><ul>';
			if(values.length){
				for(var i=0;i<values.length;i++){
					if(texts[i] != ''){
						if(opts.attr == 'line'){
							html += '<li>';
						}else{
							html += '<li class="li-column">';
						}
						html += '<div class="boxlist-item" vid='+values[i]+' >';
						html += texts[i]+'<span class="boxlist-close"></span>';
						html += '</div>';
						html += '</li>';
					}
				}
			}
			html += '</ul></div>';
		}
		
		$(target).html(html);
		
		bindEvents(target);
	};
	
	function bindEvents(target){
		var opts = $.data(target, 'boxlist').options;
		$(target).find('.boxlist-item').each(function(index,item){
			$(item).unbind().hover(function(){
				$(item).addClass('boxlist-item-hover');
			}, function(){
				$(item).removeClass('boxlist-item-hover');
			}).bind('click',function(){
				$(target).find('.boxlist-item').removeClass('boxlist-item-selected');
				$(item).addClass('boxlist-item-selected');
			});
		});
		
		$(target).find('.boxlist-close').each(function(index,value){
			$(value).unbind().hover(function(){
				$(value).addClass('boxlist-close-hover');
			}, function(){
				$(value).removeClass('boxlist-close-hover');
			}).bind('click',function(){
				var box = { text : $(value).parent().text(), value : $(value).parent().attr('vid') };
				if (opts.onBeforeClose.call(target,box) == false) { return; }
				$(value).parents('li').remove();
			});
		});
	};
	
	/**
	 * 清除所有box
	 */
	function clear(target) {
		$(target).find('ul').empty();
	};
	
	/**
	 * 设置值
	 */
	function setBoxs(target, box){
		clear(target);
		addBoxs(target, box);
	};
	
	/**
	 * 添加值
	 */
	function addBoxs(target, box){
		var opts = $.data(target, 'boxlist').options;
		var values = box.values.toString().split(opts.separator);
		var texts = box.texts.toString().split(opts.separator);
		
		var html = '';
		if (values.length<1) { return ; }
		for (var i=0; i<values.length; i++) {
			if(opts.attr == 'line'){
				html += '<li>';
			}else{
				html += '<li class="li-column">';
			}
			html += '<div class="boxlist-item" vid='+values[i]+'>';
			html += texts[i]+'<span class="boxlist-close"></span>';
			html += '</div>';
			html += '</li>';
		}
		$(target).find('ul').append(html);
		$(target).find('ul li:last div')[0].scrollIntoView();
		bindEvents(target);
	};
	
	function getBoxs(target){
		var opts = $.data(target, 'boxlist').options;
		var texts = [], values = [];
		$(target).find('.boxlist-item').each(function(idx, item) {
			values.push($(item).attr('vid'));
			texts.push($(item).text());
		});
		return { values : values.join(opts.separator), texts : texts.join(opts.separator) };
	};
	
	function deleteBoxs(target, box){
		var opts = $.data(target, 'boxlist').options;
		var values = box.values.toString().split(opts.separator);
		
		$.each(values, function(idx, item) {
			$(target).find('li div[vid="' + item + '"]').parent().remove();
		});
		
	};
	
	$.fn.boxlist = function(options, param){
		if (typeof options == 'string'){
			return $.fn.boxlist.methods[options](this, param);
		}
		
		options = options || {};
		return this.each(function(){
			var state = $.data(this, 'boxlist');
			if (state){
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'boxlist', {
					options: $.extend({}, $.fn.boxlist.defaults, $.fn.boxlist.parseOptions(this), options)
				});
			}
			create(this);
		});
	};
	
	$.fn.boxlist.methods = {
		options : function(jq){
			return $.data(jq[0], 'boxlist').options;
		},
		clear : function(jq) {
			return jq.each(function(){
				clear(this);
			});
		},
		addBoxs : function(jq, box) {
			return jq.each(function(){
				addBoxs(this, box);
			});
		},
		setBoxs: function(jq,box){
			return jq.each(function(){
				setBoxs(this, box);
			});
		},
		getBoxs: function(jq){
			return getBoxs(jq[0]);
		},
		deleteBoxs: function(jq, box){
			return jq.each(function(){
				deleteBoxs(this, box);
			});
		}
	};
	
	$.fn.boxlist.parseOptions = function(target){
		return $.extend({}, $.parser.parseOptions(target, ['attr','values','texts', 'separator']));
	};
	
	$.fn.boxlist.defaults = {
		attr : 'line',
		values : '',
		texts : '',
		separator : ',',
		onBeforeClose : function(box) { }
	};
	
	$.parser.plugins.push("boxlist");
	
})(jQuery);