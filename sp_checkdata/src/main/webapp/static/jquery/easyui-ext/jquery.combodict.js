/**
 * 
 * 字典选择组件
 * 
 * author chenli
 * since 2014/7/29
 */
(function($) {
	
	function create(target) {
		var opts = $.data(target, 'combodict').options;
		
		$(target).combotree($.extend({}, opts, {
			url : opts.url + '?dictCode=' + opts.dictCode,
			onClick : function(node) {
				retrieveValues(target);
				$(target).combo('hidePanel');
				opts.onClick.call(this, node);
			},
			onCheck : function(node, checked) {
				retrieveValues(target);
				opts.onCheck.call(this, node, checked);
			}
		}));
	};
	
	function retrieveValues(target){
		var opts = $.data(target, 'combodict').options;
		var tree = $.data(target, 'combotree').tree;
		var vv = [], ss = [];
		if (opts.multiple){
			var nodes = tree.tree('getChecked');
			for(var i=0; i<nodes.length; i++){
				vv.push(nodes[i].attributes.value);
				ss.push(nodes[i].text);
			}
		} else {
			var node = tree.tree('getSelected');
			if (node){
				vv.push(node.attributes.value);
				ss.push(node.text);
			}
		}
		$(target).combo('setValues', vv).combo('setText', ss.join(opts.separator));
	}
	
	/**
	 * 初始化组件
	 */
	$.fn.combodict = function(options, param){
		if (typeof options == 'string') {
			var method = $.fn.selecter.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return $(this).combotree(options, param);
			}
		}
		
		options = options || {};
		
		return this.each(function() {
			var state = $.data(this, 'combodict');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'combodict', {
					options : $.extend({}, $.fn.combodict.defaults, $.fn.combodict.parseOptions(this), options)
				});
			}
			create(this);
		});
	};
	
	/**
	 * 组件方法集
	 */
	$.fn.combodict.methods = {
		
	};
	
	/**
	 * 属性解析
	 * 从标签数据中解析属性
	 */
	$.fn.combodict.parseOptions = function(target){
		return $.extend({}, $.fn.combotree.parseOptions(target), $.parser.parseOptions(target,['dictCode']));
	};
	
	/**
	 * 组件属性
	 */
	$.fn.combodict.defaults = $.extend({}, $.fn.combotree.defaults, {
		dictCode : null,
		//url : 'dictDetailService/getDictDetailTree.run'
		//url : 'http://127.0.0.1:8080/construct-platform/platform/test/dictDetailService/getDictDetailTree.run'
		url : 'http://localhost:8080/construct-platform/mvc/service/dictDetailService/getDictDetails.json'
	});
	
	/**
	 * 添加到组件列表
	 */
	$.parser.plugins.push("combodict");
	
})(jQuery);