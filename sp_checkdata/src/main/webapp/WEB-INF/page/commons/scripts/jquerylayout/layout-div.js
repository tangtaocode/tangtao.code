function loadIframePage(pane, $Pane) {
		if (!$Pane)
			$Pane = $('.ui-layout-' + pane);
		var $Iframe = $Pane.prop('tagName') == 'IFRAME' ? $Pane : $Pane.find('IFRAME:first');
		if (!$Iframe.length)
			return; // no iframe
		var src = $Iframe.attr('src'), page = $Iframe.attr('longdesc');
		if (page && src != page)
			$Iframe.attr('src', page);
	}

	var myLayout;

	$(document).ready(function() {
		myLayout = $("body").layout({
			minSize : 25,
			north__size : 85,
			north__closable:false,
			north__resizable:false,
			west__size : 162,
			center__minHeight : 200,
			center__minWidth : '100%',
			spacing_closed : 5,
			initClosed : false,
			maskContents : true,
			onopen : loadIframePage
		});
	});