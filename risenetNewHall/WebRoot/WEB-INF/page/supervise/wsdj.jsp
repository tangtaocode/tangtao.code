<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<body>
		<table width="924px" cellspacing="0" cellpadding="4" style="border: #c0c0c0 1px solid; border-collapse: collapse">
                    <tbody>
                      <tr>
                        <th class="colColor1">部门名称</th>
                        <th class="colColor2" width="13%">办事指南</th>
                        <th class="colColor3" width="13%">表格下载</th>
                        <th class="colColor4" width="13%">网上咨询</th>
                        <th class="colColor5" width="13%">网上申报</th>
                        <th class="colColor6" width="13%">结果反馈</th>
                      </tr>
                       		<s:set var ="total1" value="0"/>
	                        <s:set var ="total2" value="0"/>
	                        <s:set var ="total3" value="0"/>
	                        <s:set var ="total4" value="0"/>
	                        <s:set var ="total5" value="0"/>
                      <s:iterator value="statList" status="item">
                      	<tr>
	                        <td class="titleCol"><s:property value="column1"/> </td>
	                        <td class="numCol"><s:property value="int1"/></td>
	                        <td class="numCol"><s:property value="int2"/></td>
	                        <td class="numCol"><s:property value="int3"/></td>
	                        <td class="numCol"><s:property value="int4"/></td>
	                        <td class="numCol"><s:property value="int5"/></td>
	                        <s:set var ="total1" value="cint1+#total1"/>
	                        <s:set var ="total2" value="cint2+#total2"/>
	                        <s:set var ="total3" value="cint3+#total3"/>
	                        <s:set var ="total4" value="cint4+#total4"/>
	                        <s:set var ="total5" value="cint5+#total5"/>
		               </tr>
                      </s:iterator>
                      <tr>
	                        <td>合计 </td>
	                        <td class="numCol"><s:property value="#total1"/></td>
	                        <td class="numCol"><s:property value="#total2"/></td>
	                        <td class="numCol"><s:property value="#total3"/></td>
	                        <td class="numCol"><s:property value="#total4"/></td>
	                        <td class="numCol"><s:property value="#total5"/></td>
		               </tr>
                    </tbody>
                  </table>
</body>
</html>