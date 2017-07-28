/**
 * selecter - jQuery EasyUI Extensions
 * author chenli
 * since 2014/7/21
 * 
 * 选择组件
 * 类似combo的选择组件，不同点是选择面板为弹出窗口
 */
(function($) {
	
	/**
	 * 初始化组件
	 */
	function create(target) {
		
		var state = $.data(target, 'selecter');
		var opts = state.options;
		
//		var span = $('<span class="selecter"></span>').insertAfter(target);
//		var input = $('<input type="text" class="selecter-text">').appendTo(span);
//		$('<span><span class="selecter-btn"></span></span>').appendTo(span);
//		$('<input type="hidden" class="selecter-value">').appendTo(span);
		
		if (!state.panel) {
			var dialog = $('<div class="selecter-dialog"></div>').appendTo('body');
			dialog.dialog({
				modal : true,
				closed : true,
				title : opts.title,
				width : opts.dialogWidth,
				height : opts.dialogHeight,
				buttons : [{
					text : '确定',
					handler : function() {
						closeDialog(target);
						opts.onChoose.call(target);
					}
				}, {
					text : '取消',
					handler : function() {
						closeDialog(target);
					}
				}],
				onClose : function() {
					opts.onCloseDialog.call(target);
				}
			});
			state.dialog = dialog;
		}
		
		$(target).addClass('selecter-f').textbox($.extend({}, state.options, {
			icons : [{
				iconCls : 'selecter-btn',
				handler : function(e) {
					state.dialog.dialog('open');
				}
			}]
		}));
		
		$(target).next().addClass('selecter');
	};
	
	/**
	 * 打开窗口
	 */
	function openDialog(target) {
		var state = $.data(target, 'selecter');
		
		state.dialog.dialog.dialog('open');
		
		state.options.onOpenDialog.call(target);
	};
	
	/**
	 * 关闭窗口
	 */
	function closeDialog(target) {
		var state = $.data(target, 'selecter');
		
		state.dialog.dialog('close');
		
		state.options.onCloseDialog.call(target);
	};
	
	/**
	 * 初始化组件
	 */
	$.fn.selecter = function(options, param){
		if (typeof options == 'string') {
			var method = $.fn.selecter.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.textbox(options, param);
			}
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
			create(this);
//			reset(this);
//			$('input.selecter-text', state.selecter).attr('readonly', !state.options.editable);
//			setDisabled(this, state.options.disabled);
//			setSize(this);
//			bindEvents(this);
//			validate(this);
		});
	};
	
	/**
	 * 组件方法集
	 */
	$.fn.selecter.methods = {
		options : function(jq) {
			return $.data(jq[0], 'selecter').options;
		},
		dialog : function(jq) {
			return $.data(jq[0], 'selecter').dialog;
		},
	};
	
	/**
	 * 属性解析
	 * 从标签数据中解析属性
	 */
	$.fn.selecter.parseOptions = function(target){
		return $.extend({}, $.fn.validatebox.parseOptions(target),
				$.parser.parseOptions(target,['width', 'height', 'editable', 'dialogWidth', 'dialogHeight', 'title', 
				                              'multiple', 'separator']));
	};
	
	/**
	 * 组件属性
	 */
	$.fn.selecter.defaults = $.extend({}, $.fn.textbox.defaults, {
		width : 'auto',
//		height : 22,
		editable : false,
		disabled : false,
		multiple : false,
		title : '选择',
		separator: ',',
		dialogWidth : 400,
		dialogHeight : 300,
		onOpenDialog : function() { },
		onCloseDialog : function() { },
		onChoose : function() { }
	});
	
	/**
	 * 添加到组件列表
	 */
	$.parser.plugins.push("selecter");
	
})(jQuery);