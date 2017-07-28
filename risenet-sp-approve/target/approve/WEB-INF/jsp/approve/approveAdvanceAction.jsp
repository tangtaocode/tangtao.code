<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>审批事项预受理</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<style type="text/css">


body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
body,td,th {
	font-family: 宋体;
	font-size: 12px;
	color: #000000;
}
.table_right_line {
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #85AAD6;
}
.font_top {
	font-family: "宋体";
	font-size: 14px;
	color: #FFFFFF;
	letter-spacing: 2px;
}
.font_left:link {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	letter-spacing: 1px;
	background-image: url(img/left_coi.gif);
	background-repeat: no-repeat;
	background-position: 9px center;
	padding-left: 17px;
	line-height: 24px;
	padding-top: 3px;
}
.font_left:visited {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	letter-spacing: 1px;
	background-image: url(img/left_coi.gif);
	background-repeat: no-repeat;
	background-position: 9px center;
	padding-left: 17px;
	line-height: 24px;
	padding-top: 3px;
}
.font_left:hover {
	font-family: "宋体";
	font-size: 12px;
	color: #003C58;
	text-decoration: none;
	letter-spacing: 1px;
	background-image: url(img/left_coi.gif);
	background-repeat: no-repeat;
	background-position: 9px center;
	padding-left: 17px;
	line-height: 24px;
	padding-top: 3px;
	background-color: #A1D4F3;
}
.font_left:active {
	font-family: "宋体";
	font-size: 12px;
	color: #003C58;
	text-decoration: none;
	letter-spacing: 1px;
	background-image: url(img/left_coi.gif);
	background-repeat: no-repeat;
	background-position: 9px center;
	padding-left: 17px;
	line-height: 24px;
	padding-top: 3px;
	background-color: #A1D4F3;
}
.font_left2 {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	background-image: url(img/left_coi.gif);
	background-repeat: no-repeat;
	background-position: 9px 7px;
	padding-left: 17px;
	line-height: 24px;
	padding-right: 3px;
}
.font_leftb {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	background-image: url(img/left_coib.gif);
	background-repeat: no-repeat;
	background-position: 9px 7px;
	padding-left: 17px;
	line-height: 24px;
	padding-right: 3px;
}
.font_left3 {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	background-repeat: no-repeat;
	background-position: 9px 3px;
	line-height: 24px;
	letter-spacing: 1px;
	padding: 3px 3px 0px 0px;
}
.font_left3:link {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	background-repeat: no-repeat;
	background-position: 9px 3px;
	line-height: 24px;
	letter-spacing: 1px;
}
.font_left3:active {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	background-repeat: no-repeat;
	background-position: 9px 3px;
	line-height: 24px;
	letter-spacing: 1px;
	}
.font_left3:hover {
	font-family: "宋体";
	font-size: 12px;
	color: #000000;
	text-decoration: none;
	background-repeat: no-repeat;
	background-position: 9px 3px;
	line-height: 24px;
	background-color: #A1D4F3;
	letter-spacing: 1px;
}

</style></style>
<!--框架必需end-->
<title>材料清单</title>
</head>
<body>
	<div class="box2" panelTitle="审批事项-预受理" id="searchPanel">
	<div class="box_tool_min padding_top2 padding_bottom2 padding_right5">
    <div class="center">
    <div class="left">
    <div class="right">
        <div class="padding_top5 padding_left10">
        <a href="javascript:void(0);" title="材料初审" onclick="onbszn('${instanceId}');"><span class="icon_edit">材料初审</span></a>
        <div class="box_tool_line"></div>
        <a href="javascript:void(0);" title="政策法规"><span class="icon_view">政策法规</span></a>
        <div class="box_tool_line"></div>
        </div>
    </div>      
    </div>  
    </div>
    </div>
	</div>

	<div class="box1" panelWidth="99%" >

<form>

    <table class="tableStyle" formMode="line" align="center" width="600">

        <tr><th colspan="2">${formname}</th></tr>

        <tr><td>业务编号：</td><td><input type="text"/></td></tr>

        <tr><td>审批事项名称：</td><td><input type="text"/></td></tr>    

        <tr><td>项目名称：</td><td><input type="text"/></td></tr>

        <tr><td>申请单位/人：</td><td><input type="text"/></td></tr>
        
        <tr><td>单位地址/住址：</td><td><input type="text"/></td></tr>
        
        <tr><td>证件代码:：</td><td><input type="text"/></td></tr>
        <tr><td></td><td>注：申请单位填组织机构代码，申请人填身份证号码</td></tr>
        
        <tr><td>申请类型：</td><td><select><option value="1">个人</option><option value="2">企业</option><option value="3">其他</option></select></td></tr>
        
        <tr><td>联系人：</td><td><input type="text"/></td></tr>
        
        <tr><td>联系人证件类型：</td><td><select><option value="1">个人</option><option value="2">企业</option><option value="3">其他</option></select></td></tr>
        
        <tr><td>联系人证件号码：</td><td><input type="text"/></td></tr>
        
        <tr><td>联系电话：</td><td><input type="text"/></td></tr>
        
        <tr><td>手机：</td><td><input type="text"/></td></tr>
        
        <tr><td>传真：</td><td><input type="text"/></td></tr>
        
        <tr><td>提交方式：</td><td><select><option value="1">网上</option><option value="2">信函</option><option value="3">电报</option></select></td></tr>
                
        <tr><td>申办具体网点：</td><td><input type="text"/></td></tr>
        
        <tr><td>经 办 人：</td><td><input type="text"/></td></tr>
        
        <tr><td>申请日期：</td><td><input type="text"/></td></tr>
        <tr><td colspan="2"><input type="submit" value="提交"/>&nbsp;<input type="reset" value="重置"/></td></tr>

    </table>

</form>

</div>
	
</body>
</html>