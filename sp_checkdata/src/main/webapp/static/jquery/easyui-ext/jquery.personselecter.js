/**
 * 
 * 人员选择组件
 * 
 * author chenli
 * since 2014/7/24
 * 
 */
(function($) {
	
	function create(target) {
		var opts = $.data(target, 'personselecter').options;
		
		$(target).treelistselecter($.extend({}, opts, {
		}));
	};
	
	/**
	 * 初始化组件
	 */
	$.fn.personselecter = function(options, param){
		if (typeof options == 'string') {
			var method = $.fn.personselecter.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return $(this).treelistselecter(options, param);
			}
		}
		
		options = options || {};
		
		return this.each(function() {
			var state = $.data(this, 'personselecter');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'personselecter', {
					options : $.extend({}, $.fn.personselecter.defaults, $.fn.personselecter.parseOptions(this), options)
				});
			}
			create(this);
		});
	};
	
	/**
	 * 组件方法集
	 */
	$.fn.personselecter.methods = {
		
	};
	
	/**
	 * 属性解析
	 * 从标签数据中解析属性
	 */
	$.fn.personselecter.parseOptions = function(target){
		return $.extend({}, $.fn.treelistselecter.parseOptions(target), $.parser.parseOptions(target,[]));
	};
	
	/**
	 * 组件属性
	 */
	$.fn.personselecter.defaults = $.extend({}, $.fn.treelistselecter.defaults, {
		title : '选择人员',
		treeUrl : 'http://localhost:8080/construct-platform/mvc/service/organizationService/getDepartmentTree.json',
		gridUrl : 'http://localhost:8080/construct-platform/mvc/service/organizationService/getPersons.json',
		gridColumns : [[
    		{ field : 'name', title : '姓名', width : 100},
    		{ field : 'duty', title : '职务', width : 80 }
    	]],
    	paramName : 'parent_id',
    	textField : 'name',
    	valueField : 'id'
	});
	
	/**
	 * 添加到组件列表
	 */
	$.parser.plugins.push("personselecter");
	
})(jQuery);