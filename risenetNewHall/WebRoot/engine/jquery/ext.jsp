<%@ page language="java" contentType="text/html; charset=GBK"%>
<script type="text/javascript" src="ext-base.js"></script>
<script type="text/javascript" src="ext-all.js"></script>
<link rel="stylesheet" type="text/css" href="../resources/css/ext-all.css"/>
<script>
Ext.FilesPanel=Ext.extend(Ext.Panel,{
	title:'test',
	width:100,
	height:100	
});
Ext.onReady(function(){
	var fp=new Ext.FilesPanel({
		renderTo:'fp',
		title:'sssss'
	});
});
</script>
<div id="fp"></div>