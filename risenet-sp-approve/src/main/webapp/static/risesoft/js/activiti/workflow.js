//jquery ui最新版本不支持在标题栏中显示html,故修改之。
/*$.widget("ui.dialog", $.extend({}, $.ui.dialog.prototype, {
	_title : function(title) {
		if (!this.options.title) {
			title.html("&#160;");
		} else {
			title.html(this.options.title);
		}
	}
}));*/

function graphTrace(processInstanceId,processDefinitionId) {
	// 处理使用js跟踪当前节点坐标错乱问题
	$(document).on('click', '#changeImg', function() {
		$('#workflowTraceDialog').dialog('close');
		if ($('#imgDialog').length > 0) {
			$('#imgDialog').remove();
		}
		$('<div/>', {
			'id' : 'imgDialog',
			title : '此对话框显示的图片是由引擎自动生成的，并用红色标记当前的节点',
			html : "<img src='" + ctx + "/workflow/resource/process-instance?pid=" + processInstanceId+"&processDefinitionId="+processDefinitionId+"&type=image" + "' />"
		}).appendTo('body').dialog({
			modal : true,
			resizable : true,
			dragable : true,
			width : $(window).width() * 0.9,
			height : $(window).height() * 0.9
		});
	});
	
	// 获取图片资源
	var imageUrl = ctx + "/workflow/resource/process-instance?pid=" + processInstanceId+'&processDefinitionId='+processDefinitionId + "&type=image";
	
	//获取流程各个节点信息
	$.getJSON(ctx + '/workflow/process/trace?pid=' + processInstanceId+'&processDefinitionId='+processDefinitionId, function(infos) {
		var positionHtml = "";
		
		// 生成图片
		var varsArray = new Array();
		$.each(infos, function(i, v) {
			// 节点边框
			var $border = $('<div/>', {
				'class' : 'activity-attr-border'
			}).css({
				position : 'absolute',
				left : (v.x + 9),//设置流程图中红边框距离左边的位置
				top : (v.y + 39),//设置流程图中红边框距离顶部的位置v.currentActiviti
				width : (v.width - 4),
				height : (v.height - 3),
				zIndex : $.fn.qtip.zindex - 2
			});

			if (v.currentActiviti) {
				$border.addClass('ui-corner-all-12').css({
					border : '3px solid red'
				});
			}else{
				$border.addClass('ui-corner-all-12').css({
					border : '3px solid blue'
				});
			}

			positionHtml +=$border.outerHTML();
			varsArray[varsArray.length] = v.vars;
		});

		if ($('#workflowTraceDialog').length == 0) {
			$('<div/>', {
				id : 'workflowTraceDialog',
				title : '查看流程（按ESC键可以关闭）<button id="changeImg">如果坐标错乱请点击这里</button>',
				html : "<div><img src='" + imageUrl + "' style='position:absolute; left:10px; top:40px;' />" + "<div id='processImageBorder'>" + positionHtml + "</div>" + "</div>"
			}).appendTo('body');//left:10px; top:40px;用来设置生成的流程图距离dialog的边框的距离
		} else {
			$('#workflowTraceDialog img').attr('src', imageUrl);
			$('#workflowTraceDialog #processImageBorder').html(positionHtml);
		}

		// 设置每个节点的data
		$('#workflowTraceDialog .activity-attr').each(function(i, v) {
			$(this).data('vars', varsArray[i]);
		});
		
		// 打开对话框
		$('#workflowTraceDialog').dialog({
			modal : true,
			resizable : true,
			width : $(window).width()*0.9,
			height : $(window).height()*0.8,
			dragable : true,
			open : function() {
				//$('#workflowTraceDialog').css('padding', '0.2em');
				//$('#workflowTraceDialog .ui-accordion-content').css('padding', '0.2em').height($('#workflowTraceDialog').height() - 75);

				// 此处用于显示每个节点的信息，如果不需要可以删除
				$('.activity-attr').qtip({
					content : function() {
						var vars = $(this).data('vars');
						var tipContent = "<table class='need-border'>";
						$.each(vars, function(varKey, varValue) {
							if (varValue) {
								tipContent += "<tr><td class='label'>" + varKey + "</td><td>" + varValue + "<td/></tr>";
							}
						});
						tipContent += "</table>";
						return tipContent;
					},
					position : {
						at : 'bottom left',
						adjust : {
							x : 3
						}
					}
				});
				// end qtip
			},
			close : function() {
				$('#workflowTraceDialog').remove();
			}
		});

	});
}
