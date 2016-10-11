<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

<body>
 <table width="924px" cellspacing="0" cellpadding="4" style="border: #c0c0c0 1px solid; border-collapse: collapse">
                    <tbody>
                      <tr>
                        <th class="colColor1">部门名称</th>
                        <th class="colColor2" width="13%">审批事项总数</th>
                        <th class="colColor3" width="13%">可在线办理数</th>
                        <th class="colColor4" width="13%">社会事务服务事项总数</th>
                        <th class="colColor5" width="13%">可在线办理数</th>
                        <th class="colColor6" width="13%">事项可在线办理率</th>
                        <th class="colColor7" width="13%">级别</th>
                      </tr>
                       		<s:set var ="total1" value="0"/>
	                        <s:set var ="total2" value="0"/>
	                        <s:set var ="total3" value="0"/>
	                        <s:set var ="total4" value="0"/>
                      <s:iterator value="statList" status="item">
                      	<tr>
	                        <td class="titleCol"><s:property value="column1"/> </td>
	                        <td class="numCol"><s:property value="int1"/></td>
	                        <td class="numCol"><s:property value="int2"/></td>
	                        <td class="numCol"><s:property value="int3"/></td>
	                        <td class="numCol"><s:property value="int4"/></td>
	                        <td class="numCol"><s:property value="column2"/></td>
	                        <td class="numCol">
	                        <s:iterator value="star" status="indx">
	                        	<s:if test="star[#indx.index]==1">
	                        	<img src="/images/share/star_1.png" alt="" width="17" height="16" vspace="4" />
	                        	</s:if>
	                        	<s:else>
	                        	<img src="/images/share/star_2.png" alt="" width="17" height="16" vspace="4" />
	                        	</s:else>
	                        </s:iterator>
	                        </td>
	                        <s:set var ="total1" value="cint1+#total1"/>
	                        <s:set var ="total2" value="cint2+#total2"/>
	                        <s:set var ="total3" value="cint3+#total3"/>
	                        <s:set var ="total4" value="cint4+#total4"/>
		               </tr>
                      </s:iterator>
                      <tr>
	                        <td>合计 </td>
	                        <td class="numCol"><s:property value="#total1"/></td>
	                        <td class="numCol"><s:property value="#total2"/></td>
	                        <td class="numCol"><s:property value="#total3"/></td>
	                        <td class="numCol"><s:property value="#total4"/></td>
	                        <td class="numCol">&nbsp;</td>
	                        <td class="numCol">&nbsp;</td>
		               </tr>
                    </tbody>
                  </table>
                   <div style="margin-left: 5px;" id="pjinfo">
	                    <ul>
		                    <li><span>说明：<font color="#ff8000" size="4">★</font>级别根据可在线办理率而定 </span> </li>
		                    <li><span class="effect_font">无级别</span>&nbsp;&nbsp;&nbsp;&nbsp;&lt;20%</li>
		                    <li>
		                    	<span class="effect_font"><font color="#ff8000" size="4">★</font></span>&nbsp;&nbsp;&nbsp;&nbsp;20%-40%
		                    </li>
		                    <li>
		                    	<span class="effect_font"><font color="#ff8000" size="4">★★</font> </span>&nbsp;&nbsp;&nbsp;&nbsp;40%-60%
		                    </li>
		                    <li>
		                    	<span class="effect_font"><font color="#ff8000" size="4">★★★</font></span>&nbsp;&nbsp;&nbsp;&nbsp;60%-80%
		                    </li>
		                     <li>
		                     	<span class="effect_font"><font color="#ff8000" size="4">★★★★</font></span>&nbsp;&nbsp;&nbsp;&nbsp; ≥80%
		                    </li>
		                     <li><span>没有应办事项的单位不显示</span> </li>
	                    </ul>
                    </div>
</body>
</html>