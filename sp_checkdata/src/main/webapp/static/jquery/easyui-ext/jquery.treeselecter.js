/**
 * treeselecter - jQuery EasyUI Extensions
 * author chenli
 * since 2014/7/21
 * 
 * 树选择组件
 */
(function($) {
	
	function create(target) {
		var opts = $.data(target, 'treeselecter').options;
//		console.log(opts);
		var tree = $.data(target, 'treeselecter').tree;
		var boxlist = $.data(target, 'treeselecter').boxlist;
		$(target).addClass('treeselecter-f');
		
		$(target).selecter($.extend({}, opts, {
			onChoose : function() {
				retrieveValues(target);
				opts.onChoose.call(target);
			}
		}));
		var dialog = $(target).selecter('dialog');
		if (!tree) {
			var treeDiv = $('<div></div>').css({ 'overflow-x' : 'auto', 'overflow-y' : 'scroll', height : (dialog._outerHeight() - 52) }).appendTo(dialog);
			tree = $('<ul></ul>').appendTo(treeDiv);
			var boxlist = $('<div></div>').insertAfter(treeDiv).css({ overflow : 'auto', height : 50 });
			
			$.data(target, 'treeselecter').tree = tree;
			$.data(target, 'treeselecter').boxlist = boxlist;
		}
		
//		console.log(opts);
		//init boxlist
		boxlist.boxlist({ 
			separator : opts.separator,
			onBeforeClose : function(box) {
				if (opts.multiple) {
					tree.tree('uncheck', tree.tree('find', box.value).target);
				}
			}
		});
		//init tree
		tree.tree($.extend({}, opts, {
			checkbox : opts.multiple,
			cascadeCheck : false,
			onSelect : function(node) {
				if (opts.multiple) {
					
					if ($(node.target).find('.tree-checkbox').hasClass('tree-checkbox1')) {
						//checked
						tree.tree('uncheck', node.target);
					} else {
						//unchecked
						tree.tree('check', node.target);
					}
					
					return ;
				}
				boxlist.boxlist('setBoxs', {
					values : node.id,
					texts : node.text
				});
			},
			onCheck : function(node, checked) {
//				console.log(checked);
				if (checked) {
					boxlist.boxlist('addBoxs', {
						values : node.id,
						texts : node.text
					});
				} else {
					boxlist.boxlist('deleteBoxs',{
						values : node.id
					});
				}
			}
		}));
		
	};
	
	function retrieveValues(target) {
		var opts = $.data(target, 'treeselecter').options;
		var boxlist = $.data(target, 'treeselecter').boxlist;
		
		var boxs = boxlist.boxlist('getBoxs');
//		console.log(boxs);
		$(target).selecter('setValue', boxs.values);
		$(target).selecter('setText', boxs.texts);
	};
	
	/**
	 * 初始化组件
	 */
	$.fn.treeselecter = function(options, param){
		if (typeof options == 'string') {
			method = $.fn.treeselecter.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.selecter(options, param);
			}
		}
		
		options = options || {};
		
		return this.each(function() {
			var state = $.data(this, 'treeselecter');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'treeselecter', {
					options : $.extend({}, $.fn.treeselecter.defaults, $.fn.treeselecter.parseOptions(this), options)
				});
			}
			create(this);
		});
	};
	
	/**
	 * 组件方法集
	 */
	$.fn.treeselecter.methods = {
		options : function(jq) {
			return $.data(jq[0], 'treeselecter').options;
		}
	};
	
	/**
	 * 属性解析
	 * 从标签数据中解析属性
	 */
	$.fn.treeselecter.parseOptions = function(target){
		return $.extend({}, $.fn.selecter.parseOptions(target), $.parser.parseOptions(target,['url']));
	};
	
	/**
	 * 组件属性
	 */
	$.fn.treeselecter.defaults = $.extend({}, $.fn.selecter.defaults, {
		onOpenDialog : function() { },
		onCloseDialog : function() { }
	});
	
	/**
	 * 添加到组件列表
	 */
	$.parser.plugins.push("treeselecter");
	
})(jQuery);