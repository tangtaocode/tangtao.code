/**
 * 
 * 树+列表选择组件
 * 
 * author chenli
 * since 2014/7/24
 * 
 */
(function($) {
	
	function create(target) {
		var state = $.data(target, 'treelistselecter');
		var opts = state.options, tree = state.tree, grid = state.grid, boxlist = state.boxlist;
		
		$(target).selecter($.extend({}, opts, {
			onChoose : function() {
				retrieveValues(target);
				opts.onChoose.call(target);
			}
		}));
		
		var dialog = $(target).selecter('dialog');
		dialog.css({
			overflow : 'hidden'
		});
		if (!tree) {
			var layout = $('<table></table>').appendTo(dialog).append('<tr></tr>').attr({ border : 0, cellPadding : 0, cellSpacing : 0 });
			var treetd = $('<td></td>').css({ width : opts.treeWidth, height : dialog._outerHeight() - 2}).appendTo(layout.find('tr:first'));
			var gridtd = $('<td></td>').css({ width : (dialog._outerWidth() - opts.treeWidth), height : dialog._outerHeight() - 2}).insertAfter(treetd);
			
			var treeDiv = $('<div></div>').css({ 'overflow-x' : 'auto', 'overflow-y' : 'scroll', height : dialog._outerHeight() }).appendTo(treetd);
			tree = $('<ul></ul>').appendTo(treeDiv);
			
			var gridDiv = $('<div></div>').css({ height : dialog._outerHeight() }).appendTo(gridtd);
			grid = $('<div></div>').appendTo(gridDiv);
			boxlist = $('<div></div>').css({ overflow : 'auto', height : 50 }).appendTo(gridDiv);
			
			state.tree = tree;
			state.grid = grid;
			state.boxlist = boxlist;
		}
		
		var queryParams = {};
		
		tree.tree($.extend({}, opts, {
			url : opts.treeUrl,
			onSelect : function(node) {
				queryParams[opts.paramName] = node.id;
				grid.datagrid('load', queryParams);
			}
		}));
		
		boxlist.boxlist({
			separator : opts.separator,
			onBeforeClose : function(box) {
				if (!opts.multiple) {
					/*
					 * 单选
					 * 1、情况当前页面所有选择项(有一项或没有)
					 * 2、清空getSelections中的数据
					 */
					grid.datagrid('unselectAll');
					grid.datagrid('getSelections').splice(0, 1);
//					var rows = grid.datagrid('getRows');
//					$.data(grid[0], 'datagrid').selectedRows = [];
//					var selectedRows = $.data(grid[0], 'datagrid').selectedRows;
//					for (var i = 0; i < selectedRows.length; i ++) {
//						if (selectedRows[i][opts.valueField] == box.value) {
//							delete selectedRows[i];
//							return;
//						}
//					}
				} else {
					/*
					 * 多选
					 * 1、获取当前页面选择的行，如果有则取消该行
					 * 2、匹配getSelections中的数据，有则删除
					 */
					var rows = grid.datagrid('getRows');
					var selections = grid.datagrid('getSelections');
					for (var i = 0; i < rows.length; i ++) {
						if (rows[i][opts.valueField] == box.value) {
							grid.datagrid('unselectRow', grid.datagrid('getRowIndex', rows[i]));
							break ;
						}
					}
					for (var i = 0; i < selections.length; i ++) {
						if (selections[i][opts.valueField] == box.value) {
							selections.splice(i, 1);
							break ;
						}
					}
				}
			}
		});
		
		queryParams[opts.paramName] = opts.paramValue;
		
		grid.datagrid($.extend({}, opts, {
			title : null,
			url : opts.gridUrl,
			columns : opts.gridColumns,
			height : dialog._outerHeight() - 52,
			border : false,
			fitColumns : true,
			queryParams : queryParams,
			singleSelect : !opts.multiple,
			idField : opts.valueField,
			onSelect : function(rowIndex, rowData) {
				if (!opts.multiple) {
					//单选时 - 直接替换选择值
					boxlist.boxlist('setBoxs', {
						texts : rowData[opts.textField],
						values : rowData[opts.valueField]
					});
				} else {
					//多选时 - 直接添加选择值
					boxlist.boxlist('addBoxs', {
						texts : rowData[opts.textField],
						values : rowData[opts.valueField]
					});
				}
			},
			onUnselect : function(rowIndex, rowData) {
				boxlist.boxlist('deleteBoxs', {
					values : rowData[opts.valueField]
				});
			}
		}));
	};
	
	function retrieveValues(target) {
		var opts = $.data(target, 'treelistselecter').options;
		var boxlist = $.data(target, 'treelistselecter').boxlist;
		
		var boxs = boxlist.boxlist('getBoxs');
//		console.log(boxs);
		$(target).selecter('setValue', boxs.values);
		$(target).selecter('setText', boxs.texts);
	};
	
	/**
	 * 初始化组件
	 */
	$.fn.treelistselecter = function(options, param){
		if (typeof options == 'string') {
			var method = $.fn.treelistselecter.methods[options];
			if (method) {
				return method(this, param);
			} else {
				return this.selecter(options, param);
			}
		}
		
		options = options || {};
		
		return this.each(function() {
			var state = $.data(this, 'treelistselecter');
			if (state) {
				$.extend(state.options, options);
			} else {
				state = $.data(this, 'treelistselecter', {
					options : $.extend({}, $.fn.treelistselecter.defaults, $.fn.treelistselecter.parseOptions(this), options)
				});
			}
			create(this);
		});
	};
	
	/**
	 * 组件方法集
	 */
	$.fn.treelistselecter.methods = {
		
	};
	
	/**
	 * 属性解析
	 * 从标签数据中解析属性
	 */
	$.fn.treelistselecter.parseOptions = function(target){
		return $.extend({}, $.fn.selecter.parseOptions(target), 
				$.parser.parseOptions(target,['treeUrl', 'treeWidth', 'gridUrl', 'paramName', 'paramValue', 'textField', 'valueField']));
	};
	
	/**
	 * 组件属性
	 */
	$.fn.treelistselecter.defaults = $.extend({}, $.fn.selecter.defaults, {
		dialogWidth : 500,
		dialogHeight : 400,
		treeUrl : '',
		treeWidth : 265,
		gridUrl : '',
		gridColumns : null,
		paramName : 'parent_id',
		paramValue : '-1',
		textField : '',
		valueField : ''
	});
	
	/**
	 * 添加到组件列表
	 */
	$.parser.plugins.push("treelistselecter");
	
})(jQuery);