<%@ page contentType="text/html; charset=UTF-8" %>
<html>
  <head>    
    <title>选择业务办文系统工程2</title>  
    <base target="_self">
    <script type="text/javascript">
    	function selectProValue(obj){    		    		
    		var ret = new Object();
	 		ret.prj_id = document.getElementById('proid'+obj).innerHTML;
	 		ret.prj_name = document.getElementById('proname'+obj).innerHTML; 
	 		ret.item_name = document.getElementById('ITEM_NAME'+obj).innerHTML;
	 		//ret.item_id = document.getElementById('item_id'+obj).value; 
	 		ret.auct_resp = document.getElementById('auct_resp'+obj).innerHTML;
	 		//ret.auct_mobile = document.getElementById('auct_mobile'+obj).value;
	 		//ret.sup_name = document.getElementById('sup_name'+obj).value;
	 		//ret.design_name = document.getElementById('design_name'+obj).value;
	 		//ret.prj_location = document.getElementById('prj_location'+obj).value;
	 		//ret.fb_const_price = document.getElementById('fb_const_price'+obj).value;
	 		//ret.exp_start_date = document.getElementById('exp_start_date'+obj).value.split(" ")[0];
	 		//ret.exp_end_date = document.getElementById('exp_end_date'+obj).value.split(" ")[0];
	 		//ret.term=DateDiff(ret.exp_start_date,ret.exp_end_date);
	 		parent.returnValue = ret;
	 		window.close();    		
    	}
    	
    	//计算天数差的函数，通用  
    	   function  DateDiff(sDate1,  sDate2){    //sDate1和sDate2是2006-12-18格式  
    	       var  aDate,  oDate1,  oDate2,  iDays ; 
    	       aDate  =  sDate1.split("-");  
    	       oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);    //转换为12-18-2006格式  
    	       aDate  =  sDate2.split("-");  
    	       oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
    	       iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
    	       return  iDays;  
    	   }    
    	
    	function doSumbit(){
    		document.forms[0].submit();
    	}
    </script> 
    <link href="../riseway/blue/riseway.css" rel="stylesheet" type="text/css" />
<style type="text/css">
 .BS_list {
	font-family:"微软雅黑";
    line-height: 150%;
    border-top-color: rgb(204, 204, 204);
    border-bottom-color: rgb(204, 204, 204);
    border-left-color: rgb(204, 204, 204);
    border-top-width: 1px;
    border-bottom-width: 1px;
    border-left-width: 1px;
    border-top-style: solid;
    border-bottom-style: solid;
    border-left-style: solid;
	width:98%;
	margin-left:auto;
	margin-right:auto;
} .BS_list th {
    background: url("/images/lineservice/bg_tabletitle.jpg") repeat-x 0px 0px;
    border-width: 1px;
    border-style: solid;
    border-color: rgb(255, 255, 255) rgb(206, 206, 206) rgb(255, 255, 255) rgb(255, 255, 255);
    padding: 0px 15px;
    height: 42px;
    line-height:20px;
    font-size: 16px;
    font-weight: bold;
} .BS_list td {
    border-width: 1px;
    border-style: solid;
    border-color: rgb(206, 206, 206) rgb(206, 206, 206) rgb(255, 255, 255) rgb(255, 255, 255);
    padding: 5px 15px;
    line-height:18px;
    height: 42px;
    font-size: 16px;
}
</style>
  </head>
  
  
  <body>  	
  <form action="/approve/selectYwbwProject.jsp" >
  <table class="BS_list">
  	<tr><th>工程编号</th><td><input type="text" name="projectid" value="<%=request.getParameter("projectid")==null?"":request.getParameter("projectid") %>"/></td>
  	<th>工程名称</th><td><input type="text" name="projectname" value="<%=request.getParameter("projectname")==null?"":request.getParameter("projectname") %>"/></td></tr>
    <tr><th>负责人</th><td><input type="text" name=auct_resp value="<%=request.getParameter("auct_resp")==null?"":request.getParameter("auct_resp") %>"/></td>
    <td colspan="2"><input type="button" value="查询" onclick="doSumbit()"/></td></tr>
  </table>
  <br>
    <table border="1" cellpadding="0" cellspacing="0" class="BS_list">             
    	<tr>
    		<th nowrap width="2%" align="ceneter">序号</th>
    		<th width="20%" nowrap align="ceneter">工程编号</th>
    		<th width="28%" nowrap align="ceneter">工程名称</th>
    		<th width="20%" nowrap align="ceneter">项目名称</th>
    		<th width="25%" nowrap align="ceneter">负责人</th>
    		<th width="5%" nowrap align="ceneter">操作</th>
    	</tr>
    	<%-- 
    <%
    while(wbp.loop() ){%>
    	<tr>
    		<td nowrap align="ceneter"><%=wbp.row() %></td>
    		<td id="proid<%=wbp.row() %>" nowrap align="ceneter"><%=wbp.text("PRJ_ID")  %></td>
    		<td id="proname<%=wbp.row() %>" align="ceneter"><%=wbp.text("PRJ_NAME")%></td>
    		<td id="ITEM_NAME<%=wbp.row() %>" align="ceneter"><%=wbp.text("ITEM_NAME")%></td>
    		<td id="auct_resp<%=wbp.row() %>" align="ceneter"><%=Util.filterNull(wbp.text("AUCT_RESP"))%></td>
    		<input type="hidden" id="item_id<%=wbp.row() %>" value="<%=wbp.text("item_id")%>">
    		<input type="hidden" id="auct_mobile<%=wbp.row() %>" value="<%=wbp.text("auct_mobile")%>">
    			<input type="hidden" id="sup_name<%=wbp.row() %>" value="<%=wbp.text("sup_name")%>">
    			<input type="hidden" id="design_name<%=wbp.row() %>" value="<%=wbp.text("design_name")%>">
    			<input type="hidden" id="prj_location<%=wbp.row() %>" value="<%=wbp.text("prj_location")%>">
    			<input type="hidden" id="fb_const_price<%=wbp.row() %>" value="<%=wbp.text("fb_const_price")%>">
    			<input type="hidden" id="exp_start_date<%=wbp.row() %>" value="<%=wbp.text("exp_start_date")%>">
    			<input type="hidden" id="exp_end_date<%=wbp.row() %>" value="<%=wbp.text("exp_end_date")%>">
    		 <td nowrap align="ceneter"><a href="#" onclick='selectProValue(<%=wbp.row() %>)' >选择</a></td>
    	</tr>
    <%}
    %>--%>
    <tr>
     <td nowrap align="ceneter">1</td>
    		<td id="proid0" nowrap align="ceneter">44030720140402901</td>
    		<td id="proname0" align="ceneter">鹤湖新居客家围屋保护修缮工程(结算编制)</td>
    		<td id="ITEM_NAME0" align="ceneter">鹤湖新居客家围屋保护修缮工程</td>
    		<td id="auct_resp0" align="ceneter">林鲁生</td>
    		 <td nowrap align="ceneter"><a href="#" onclick='selectProValue(0)' >选择</a></td>
    </tr>
    <tr>
      <td nowrap align="ceneter">2</td>
    		<td id="proid1" nowrap align="ceneter">44030720130100704</td>
    		<td id="proname1" align="ceneter">布吉街道消防水池及泵站工程（监理）</td>
    		<td id="ITEM_NAME1" align="ceneter">布吉街道消防水池及泵站工程</td>
    		<td id="auct_resp1" align="ceneter">郑美璇</td>
    		 <td nowrap align="ceneter"><a href="#" onclick='selectProValue(1)' >选择</a></td>
    </tr>
    <tr>
     <td nowrap align="ceneter">3</td>
    		<td id="proid2" nowrap align="ceneter">44030720140304602</td>
    		<td id="proname2" align="ceneter">金域揽峰花园监理</td>
    		<td id="ITEM_NAME2" align="ceneter">金域揽峰花园</td>
    		<td id="auct_resp2" align="ceneter">周彤</td>
    		 <td nowrap align="ceneter"><a href="#" onclick='selectProValue(2)' >选择</a></td>
    </tr>
    <tr>
      <td nowrap align="ceneter">4</td>
    		<td id="proid3" nowrap align="ceneter">44030720140303203</td>
    		<td id="proname3" align="ceneter">深圳市龙岗区“三馆”项目监理</td>
    		<td id="ITEM_NAME3" align="ceneter">深圳市龙岗区“三馆”项目</td>
    		<td id="auct_resp3" align="ceneter">周彤</td>
    		 <td nowrap align="ceneter"><a href="#" onclick='selectProValue(3)' >选择</a></td>
    </tr>
    <tr>
      <td nowrap align="ceneter">5</td>
    		<td id="proid4" nowrap align="ceneter">44030720140303501</td>
    		<td id="proname4" align="ceneter">公园里花园三期（监理）</td>
    		<td id="ITEM_NAME4" align="ceneter">公园里花园三期</td>
    		<td id="auct_resp4" align="ceneter">王玉清</td>
    		 <td nowrap align="ceneter"><a href="#" onclick='selectProValue(4)' >选择</a></td>
    </tr>
    </table>
  
    </form>
  </body>
</html>
