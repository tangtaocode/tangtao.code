<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<script type="text/javascript">
 $(document).ready(function(){
 	<s:if test="twoLevel!=null&&twoLevel!=''">
		changeClassify('<s:property value="twoLevel"/>','3','images');
	</s:if>
	<s:else>
		$("#towMenuA0").click();
		$("#towMenuA0").attr("class","BS_c");
	</s:else>
	
 });
</script>
</head>
<body>
	<ul id="towLevelA">
		<s:iterator value="classifyList" status="item">
          <li>
			  <a id="towMenuA<s:property value="#item.index"/>" 
			  	 href="javascript:changeClass('towMenuA',<s:property value="#item.index"/>,<s:property value="classifyList.size"/>,'BS_c','');" 
			  	 onclick="changeClassify('<s:property value="guid"/>','3','images')"
			  	 <s:if test="twoLevel==guid">class="BS_c"</s:if> > 
			  	 <s:property value="name"/>
			  </a> | 
		 </li>
        </s:iterator>
</ul>
</body>
</html>
