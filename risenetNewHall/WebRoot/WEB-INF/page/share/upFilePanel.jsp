<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/css/jquery-ui.css"  />
<link rel="stylesheet" type="text/css" href="/js/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css"></link> 
<script src="/js/Scripts/jquery.min.js"></script>
<script type="text/javascript" src="/js/Scripts/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/plupload/plupload.full.js"></script>
<script type="text/javascript" src="/js/plupload/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="/js/plupload/plupload.flash.js"></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script>   
<script type="text/javascript" src="/js/plupload/i18n/zh-cn.js"></script>  
<script type="text/javascript">
var succ = 0;
$(function() {
	$("#uploader").plupload({
		runtimes : 'flash',
		url : '/riseFile/uploadFileAction.html',// 服务端上传路径
		max_file_size : '100mb', // 文件上传最大限制。
		max_file_count: 10,
		chunk_size : '1mb', // 上传分块每块的大小，这个值小于服务器最大上传限制的值即可。（文件总大小/chunk_size = 分块数）。
		rename: true,
		unique_names : true,  // 上传的文件名是否唯一，默认为false，选择为true上传时会自动生成uuid作为文件名
		//resize : {width : 320, height : 240, quality : 90},// 是否生成缩略图（仅对图片文件有效）。
		//sortable: true,
		multipart_params:{'modeType':'<s:property value="modeType"/>',
						  'correctionGUID':'<s:property value="correctionGUID"/>',
						  'projectFK':'<s:property value="projectFK"/>'},
		filters : [
			{title : "图片文件", extensions : "jpg,gif,png"},
			{title : "压缩文件", extensions : "zip,rar"},
			{title : "OFFICE文件", extensions : "doc,docx,ppt,pptx,xls,xlsx,txt"},
			{title : "PDF文件", extensions : "pdf"}
		],  //  这个数组是选择器，就是上传文件时限制的上传文件类型
		flash_swf_url : '/js/plupload/plupload.flash.swf',
		init : {
			BeforeUpload : function(upl, file){
			 var uploader = $('#uploader').plupload('getUploader');
			  	uploader.settings.multipart_params.trueName = file.name;
			},
			FileUploaded : function (upl, file, resp){
				succ++;
			},
			UploadComplete: function (upl, files){
				showInfo("<font color='green'>文件上传结果：<br/>&nbsp;&nbsp;成功"+succ+"个文件<font>");
			}
		}
	});
});
</script>
</head>
<body>
	<div id="uploader">
		<p>对不起，您的浏览器未安装Flash控件。</p>
	</div>
</body>
</html>
