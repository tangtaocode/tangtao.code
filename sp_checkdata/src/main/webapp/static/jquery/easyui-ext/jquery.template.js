/**
 * easyui 扩展模版
 */
(function($) {
	
	/**
	 * 初始化组件
	 */
	$.fn.template = function(options, param){
		if (typeof options == 'string') {
			return $.fn.selecter.methods[options](this, param);
		}
		
		options = options || {};
		
		return this.each(function() {
			var state = $.data(this, 'selecter');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'selecter', {
					options : $.extend({}, $.fn.selecter.defaults, $.fn.selecter.parseOptions(this), options)
				});
			}
		});
	};
	
	/**
	 * 组件方法集
	 */
	$.fn.template.methods = {
		
	};
	
	/**
	 * 属性解析
	 * 从标签数据中解析属性
	 */
	$.fn.template.parseOptions = function(target){
		return $.extend({}, $.parser.parseOptions(target,[]));
	};
	
	/**
	 * 组件属性
	 */
	$.fn.template.defaults = {
	};
	
	/**
	 * 添加到组件列表
	 */
	$.parser.plugins.push("template");
	
})(jQuery);