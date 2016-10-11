<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head><title>
	政务公开 - 广东省网上办事大厅深圳
</title>
<meta name="keywords" content="内容模型,表单自定义"></meta>
<meta name="description" content="ds"></meta>
<style type="text/css">
    .orange
    {
        color: #FF5500;
    }
    .simple-autocomplete-container
    {
        border: 1px solid #ddd;
        box-shadow: 1px 1px 3px rgba(200,200,200,0.8);
        background: #fff;
    }
    .simple-autocomplete-select
    {
        background: #f1f1f1;
    }
    .simple-autocomplete-container li
    {
        padding: 3px 10px;
    }
</style>
<link href="/css/supervise.css" type="text/css" rel="stylesheet" />
<link href="/css/base.css" type="text/css" rel="stylesheet" />
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
 <div class="monitorTb" >
                  <table cellspacing="0" cellpadding="4" class="monitor_table">
                    <tbody>
                      <tr>
                        <th>单位名称</th>
                        <th class="color1">接件总数</th>
                        <th class="color2">办结总数</th>
                        <th class="color3">在办总数</th>
                        <th class="color4">提前办结率</th>
                      </tr>
                      <s:set var ="total1" value="0"/>
	                        <s:set var ="total2" value="0"/>
	                        <s:set var ="total3" value="0"/>
                      <s:iterator value="statList" status="item">
                      	<tr>
	                        <td><s:property value="column1"/> </td>
	                        <td><s:property value="int1"/></td>
	                        <td><s:property value="int2"/></td>
	                        <td><s:property value="int3"/></td>
	                        <td><s:property value="column3"/></td>
	                         <s:set var ="total1" value="cint1+#total1"/>
	                        <s:set var ="total2" value="cint2+#total2"/>
	                        <s:set var ="total3" value="cint3+#total3"/>
		               </tr>
                      </s:iterator>
                       <tr>
	                        <td>合计 </td>
	                        <td><s:property value="#total1"/></td>
	                        <td><s:property value="#total2"/></td>
	                        <td><s:property value="#total3"/></td>
	                        <td>&nbsp;</td>
		               </tr>
                    </tbody>
                  </table>
            	</div>
</body>
</html>