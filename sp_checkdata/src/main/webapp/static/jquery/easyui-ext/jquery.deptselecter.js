/**
 * 
 * 部门选择组件
 * 
 * author chenli
 * since 2014/7/21
 */
(function($) {
	
	function create(target) {
		var opts = $.data(target, 'deptselecter').options;
		
		var url = opts.url;
		if (opts.rootDept) {
			url += '?rootDept=' + opts.rootDept;
		}
		
		$(target).treeselecter($.extend({}, opts, {
			url : url
		}));
	};
	
	/**
	 * 初始化组件
	 */
	$.fn.deptselecter = function(options, param){
		if (typeof options == 'string') {
			var method = $.fn.selecter.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.treeselecter(options, param);
			}
		}
		
		options = options || {};
		
		return this.each(function() {
			var state = $.data(this, 'deptselecter');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'deptselecter', {
					options : $.extend({}, $.fn.deptselecter.defaults, $.fn.deptselecter.parseOptions(this), options)
				});
			}
			create(this);
		});
	};
	
	/**
	 * 组件方法集
	 */
	$.fn.deptselecter.methods = {
		
	};
	
	/**
	 * 属性解析
	 * 从标签数据中解析属性
	 */
	$.fn.deptselecter.parseOptions = function(target){
		return $.extend({}, $.fn.treeselecter.parseOptions(target), $.parser.parseOptions(target,['rootDept']));
	};
	
	/**
	 * 组件属性
	 */
	$.fn.deptselecter.defaults = $.extend({}, $.fn.treeselecter.defaults, {
		title : '部门选择',
//		url : 'organizationService/getDepartmentTree.run',
		url : 'http://localhost:8080/construct-platform/mvc/service/organizationService/getDepartmentTree.json',
		dialogWidth : 300,
		dialogHeight : 400,
		rootDept : null
	});
	
	/**
	 * 添加到组件列表
	 */
	$.parser.plugins.push("deptselecter");
	
})(jQuery);