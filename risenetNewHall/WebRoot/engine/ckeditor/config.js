/*
Copyright (c) 2003-2012, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	config.skin = 'office2003'; //界面: v2,kama,office2003   
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
/**
    config.height = '300px'; // 高度   
	config.uiColor = '#F7F8F9'
    config.scayt_autoStartup = false
    config.language = 'zh-cn'; //中文
    config.filebrowserBrowseUrl = 'ckfinder/ckfinder.html';
    config.filebrowserImageBrowseUrl = 'ckfinder/ckfinder.html?Type=Images';
    config.filebrowserFlashBrowseUrl = 'ckfinder/ckfinder.html?Type=Flash';
    config.filebrowserUploadUrl = 'ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Files';
    
    config.filebrowserFlashUploadUrl = 'ckfinder/core/connector/aspx/connector.aspx?command=QuickUpload&type=Flash';
*/
config.entities = false;   //不进行字符的转义
config.fullPage = false;   //不显示完整的html，只是body部分的
config.enterMode = CKEDITOR.ENTER_BR;
config.font_names = '宋体;新宋体;仿宋_GB2312;仿宋;黑体;楷体_GB2312;楷体;隶书;幼圆;Arial;Comic Sans MS;Courier New;Fixedsys;Georgia;Tahoma;Times New Roman;Verdana;';
config.fontSize_sizes = '8pt/8pt;9pt/9pt;10pt/10pt;11pt/11pt;12pt/12pt;13pt/13pt;14pt/14pt;16pt/16pt;18pt/18pt;24pt/24pt;36pt/36pt;48/48pt;'
config.toolbar =
     [   
        ['Source','-','Preview'],   
        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Scayt'],   
        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],   
        ['Image','Flash','Table','Smiley','SpecialChar'], ['Link','Unlink'], 
        '/',   
        ['Styles','Format','Font','FontSize'],
        ['TextColor','BGColor'],
        ['Bold','Italic','Strike'],   
        ['NumberedList','BulletedList','-','Outdent','Indent']
    ]; 
config.filebrowserImageUploadUrl = "/engine/ckeditor/uploadfile.jsp?type=image";
config.filebrowserUploadUrl = "/engine/ckeditor/uploadfile.jsp?type=file";
config.filebrowserFlashUploadUrl = "/engine/ckeditor/uploadfile.jsp?type=flash";

//config.removePlugins = 'elementspath';   //去除状态栏
config.resize_enabled = false;
};
