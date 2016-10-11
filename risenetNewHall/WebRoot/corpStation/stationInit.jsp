<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,net.risesoft.beans.onlineservice.*" %>
<HTML>
<META >
<head>
<LINK href="../css/main.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="../js/My97DatePicker/WdatePicker.js"></script>
     <script type="text/javascript">
     //场站类型复选框选择
     function setStationTypeValue(obj){
         var station_type_check =document.getElementsByName('station_type_check'+obj);
         var stationTypevalue='';
         for(var i=0;i<station_type_check.length;i++){
             if(station_type_check[i].checked){
	             if(stationTypevalue=='')
	            	 stationTypevalue = station_type_check[i].value;
	             else
	            	 stationTypevalue += ','+ station_type_check[i].value;
             }
         }
        // alert(stationTypevalue);
         document.getElementById('station_type'+obj).value=stationTypevalue;
     }
   //安装形式复选框选择
     function setInstallTypeValue(obj){
         var station_type_check =document.getElementsByName('install_type_check'+obj);
         var stationTypevalue='';
         for(var i=0;i<station_type_check.length;i++){
             if(station_type_check[i].checked){
	             if(stationTypevalue=='')
	            	 stationTypevalue = station_type_check[i].value;
	             else
	            	 stationTypevalue += ','+ station_type_check[i].value;
             }
         }
         document.getElementById('install_type'+obj).value=stationTypevalue;
     }

     function radioInfo(){

         }
     var size=1;
     function addCzbase(){
     	var tableStr='<input type="hidden" name="czsize" value="'+size+'"/><br/><br/><input type="button" value="添加场站" class="box_table" onclick="addCzbase()"/><input type="button" value="删除场站" class="box_table" onclick="delCzRow(this)"/><TABLE class=onlineform_table cellSpacing=0 cellPadding=0 width=780 border=0>'+
 '<tr>'+
   '<th style="border-right:#9DC3DA solid 1px;font-size:18px" colSpan=4><strong>场站信息表</strong></th>'+
	'</tr>'+
	'<tr>'+
	   '<td style="text-align:right;border-left:1px solid #9DC3DA;" colSpan=4>信息确认号:<INPUT  style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" dataSrc=#bind_508  mIndex="63" readOnly> </td>'+
	'</tr>'+
	    '<tr>'+
	    '<th style="width:135px">场站名称'+
		'<font color=#ff0000>*</font></th>'+
	    '<td colSpan=3><input type="text" name="czname"></td></tr>'+
	  '<TR>'+
	    '<Th>场站地址'+
	     '<FONT color=#ff0000>*</FONT></th>'+
	    '<TD style="BORDER-BOTTOM: #9dc3da 1px solid" colSpan=3><input type="text" name="address"> </TD></TR>'+
	 ' <TR>'+
	   ' <Th>坐标'+
		'<FONT color=#ff0000>*</FONT></th>'+
	  '<TD colSpan=3> '+
	'1、 X<FONT color=#ff0000>*</FONT> &nbsp;<input type="text" size="6" name="coordinate1x">— Y<FONT color=#ff0000>*</FONT>&nbsp;<input type="text"  size="6" name="coordinate1y"> &nbsp;&nbsp;2、X&nbsp;<input type="text"  size="6" name="coordinate2x">— Y&nbsp;<input type="text"  size="6" name="coordinate2y"><BR> 3、&nbsp;&nbsp;X &nbsp;<input type="text"  size="6" name="coordinate3x">— Y&nbsp;<input type="text"  size="6" name="coordinate3y"> &nbsp;&nbsp;4、X&nbsp;<input type="text"  size="6" name="coordinate4x">— Y&nbsp;<input type="text"  size="6" name="coordinate4y"></TD></TR>'+
	'<TR>'+
	 '<Th>场站类型</th>'+
	    '<TD  colSpan=3><input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="接收站">接收站'+
	    '<input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="存储站">存储站'+
	    '<input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="储配站">储配站'+
	    '<input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="充装站">充装站'+
	    '<input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="汽车加气站">汽车加气站'+
	    '<input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="LNG汽车加气站">LNG汽车加气站'+
	    '<input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="CNG汽车加气站">CNG汽车加气站'+
	    '<input type="checkbox" name="station_type_check'+size+'" onclick="setStationTypeValue(\''+size+'\')" value="其他">其他'+
	    '<input type="hidden" name="station_type" id="station_type'+size+'"/> </TD></TR>'+

'<TR>'+
	    '<Th>用地种型</Th>'+
	    '<TD  colSpan=3> 自有<input type="radio" name="ydzl'+size+'" onclick="checkYdzl(\''+size+'\',\'zy\')" value="自有" >'+ 
	     ' 租用<input type="radio" name="ydzl'+size+'" onclick="checkYdzl(\''+size+'\',\'zl\')" value="租用" >'+
	     '&nbsp;&nbsp;&nbsp;&nbsp;<span id="fwcqhSpan'+size+'" style="display:none">房屋产权号:<input type="text" name="propertyno" id="fwcqh'+size+'"></span>'+
	     '<span id="zlhthSpan'+size+'" style="display:none">租凭合同号:<input type="text" name="rentalno" id="zlhth'+size+'"></span></TD></TR>'+
	  '<TR>'+
	   '<th>开工日期</th>'+
	 '<TD > <input class="Wdate"  type="text" onClick="WdatePicker({readOnly:true})" name="startdate"></TD>'+
	 '<th>竣工日期</th>'+
	    '<TD > <input class="Wdate"  type="text" onClick="WdatePicker({readOnly:true})" name="enddate" ></TD>'+
	 '</TR>'+
<%--	  '<TR>'+--%>
<%--	   --%>
<%--	    '</TR>'+--%>
	  '<TR>'+
	  ' <th>符合防火要求的<br>消防验收意见书编号</th>'+
	   ' <TD ><input type="text" name="acceptanceno"> </TD>'+
	   ' <Th >供气能力</Th>'+
	   ' <TD > <input type="text" name="supply">&nbsp;&nbsp;吨/月</TD></TR>'+
	  '<TR>'+
	   ' <th>设计单位</Th>'+
	   ' <TD><input type="text" name="designcorp"> </TD>'+
	    '<Th >施工单位</Th>'+
	    '<TD><input type="text" name="constructioncorp"> </TD></TR>'+
	  '<TR>'+
	  '  <Th>燃气来源</Th>'+
	 '<TD colSpan=3><input type="text" size="50" name="gassource"> </TD></TR>'+
	 ' <TR>'+
	  '  <th>安评机构</th>'+
	  '  <TD colSpan=3><input type="text" size="50" name="secorg"> </TD></TR>'+
	  '<TR>'+
	  '  <Th rowSpan=2>储罐资料</th>'+
	  '  <TD colSpan=3>总容积&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" size="4" name="volume"> </TD>'+
	  '</TR>'+
	 ' <TR>'+
	   ' <TD>安装形式</TD>'+
	   ' <TD colSpan=2> '+
	  ' <input type="checkbox" name="install_type_check'+size+'" value="地上罐" onclick="setInstallTypeValue(\''+size+'\')">地上罐 '+
	   '<input type="checkbox" name="install_type_check'+size+'" value="半地下罐" onclick="setInstallTypeValue(\''+size+'\')">半地下罐'+
	   ' <input type="checkbox" name="install_type_check'+size+'" value="地下罐" onclick="setInstallTypeValue(\''+size+'\')">地下罐'+
	   '<input type="hidden" name="install_type" id="install_type'+size+'"/> </TD></TR>'+
	  '<TR>'+
	    '<Th colSpan=4  class=onlineform_table_bottom>'+
	          ' <p align=center  style="margin:5px;">场站简介'+
	          ' <FONT color=#ff0000>*</FONT><br><textarea rows=15 cols=80 name="brief" ></textarea></p>'+ 
	      '</Th></TR>'+
	      '<tr>'+
	      '<th colspan="4" class=onlineform_table_bottom>'+
	      '<br>'+
	      '<h4>场站设备</h4>'+
			   '<table id="czsb'+size+'" class=onlineform_table cellSpacing=0 cellPadding=0  border=0>'+
			   '<tr>'+
			   '<th>序号</th>'+
			   '<th>设备名称<FONT color=#ff0000>*</FONT></th>'+
<%--			   '<th>产地</th>'+--%>
			   '<th>规格型号 </th>'+
			   '<th>数量 </th>'+
			   '<th>检测有效期</th><th><input type="button" value="添加" class="box_table" onclick="addCzsbRow(\''+size+'\',\'czsb'+size+'\')"/></th>'+
			   '</tr>'+
			   '<tr>'+
					'<td>1</td>'+
					'<td><input type="text"   size="16"  name="name'+size+'" /></td>'+
<%--					'<td><input  size="10" name="plc_of_product'+size+'"  /></td>'+--%>
					'<td><input  maxlength="6" size="10"  name="product_model'+size+'" /></td>'+
					'<td><input  maxlength="6" size="10"   name="total_product'+size+'"/></td>'+
					'<td><input  size="10" maxlength="10" class="Wdate"  type="text"  onClick="WdatePicker({readOnly:true})"   name="val_date_of_test'+size+'"/></td>'+
					'<td><input type="button" value="删除" class="box_table"  onclick="delCzsbRow(this,\'czsb'+size+'\')"></td>'+
			   '</tr>'+
			   '</table>'+
			   '</div>'+
			   '</div>'+
			  
			   '</TABLE>'
	     	
		var sqclTable = document.getElementById('cz');		
	  	var newRow = sqclTable.insertRow(sqclTable.rows.length);  		   
		newCell = newRow.insertCell();//td1
	  	newCell.innerHTML = tableStr;			   
		size++;	   
     }

     function checkYdzl(size,type){
         if(type=="zy"){
             document.getElementById('fwcqhSpan'+size).style.display="";
             document.getElementById('zlhthSpan'+size).style.display="none";
             document.getElementById('zlhth'+size).value="";
         }
         if(type=="zl"){
        	 document.getElementById('zlhthSpan'+size).style.display="";
             document.getElementById('fwcqhSpan'+size).style.display="none";
             document.getElementById('fwcqh'+size).value="";
         }
             
     }
     function addCzsbRow(czsbSize,tableid){
	     // js代码：插入行（两个单元格）

		var sqclTable = document.getElementById(tableid);
		var rowsSize =sqclTable.rows.length;		
		rowsSize+1;
	  	var newRow = sqclTable.insertRow(rowsSize);	
  		newCell = newRow.insertCell();//td1
	  	newCell.innerHTML =''+rowsSize;		
	  	newCell = newRow.insertCell();//td1
	  	newCell.innerHTML ='<input type="hidden" name="station_guid'+czsbSize+'"><input type="text"   size="16"  name="name'+czsbSize+'" />';				  
<%--	  	newCell = newRow.insertCell();//td1--%>
<%--	  	newCell.innerHTML ='<input  size="10" name="plc_of_product'+czsbSize+'"  />';--%>
	  	newCell = newRow.insertCell();//td2
	  	newCell.innerHTML = '<input  maxlength="6" size="10"  name="product_model'+czsbSize+'" />';
	  	newCell = newRow.insertCell();//td2                         
	  	newCell.innerHTML = '<input  maxlength="6" size="10"   name="total_product'+czsbSize+'"/>';
	  	newCell = newRow.insertCell();//td2
	  	newCell.innerHTML = '<input  size="10" maxlength="10" class="Wdate"  type="text"  onClick="WdatePicker({readOnly:true})"   name="val_date_of_test'+czsbSize+'"/>';	
	  	newCell = newRow.insertCell();//td2
	  	newCell.innerHTML = '<input type="button" value="删除" class="box_table" onclick="delCzsbRow(this,\'czsb'+czsbSize+'\')">';	

  	} 
  	
  	//js代码： 删除行
	function delCzsbRow(obj,tableid){
	  var sqclTable = document.getElementById(tableid);
	  if(confirm("确认删除？")){ 	  	
		//删除该材料
	  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);
	  	sqclTable = document.getElementById(tableid);
	  	var rowsSize = sqclTable.rows.length;
	  	for(var i=1;i<rowsSize;i++){
	  		sqclTable.rows[i].cells[0].innerHTML=''+i;
	  	}	  	
	  }else{
	  	return false;
	  }
	 }  
	 //js代码： 删除行
	function delCzRow(obj){
	  var sqclTable = document.getElementById('cz');
	  if(confirm("确认删除？")){ 	  	
		//删除该材料
		//alert(obj.parentNode.parentNode.rowIndex);
	  	sqclTable.deleteRow(obj.parentNode.parentNode.rowIndex);	  	
	  }else{
	  	return false;
	  }
	 }
	 function doSubmit(){
		 //alert(1);
		 document.getElementById('czform').action='/onlineService/doCorpStationAction.html';
		 document.getElementById('czform').submit();
	 }
     </script>
     
  </head>
  
  <body> 
  
  <form action="/onlineService/doCorpStationAction.html" method="post" id="czform"> 
  <%
  try{
	String insGuid = request.getParameter("insGuid");
  List<CorpGasStation> list = (List<CorpGasStation>)request.getAttribute("StationList");
  
  %>
  <input type="hidden" name="method" value="save"/>
  <input type="hidden" name="insGuid" value="<%=insGuid %>"/>   
  
<div align=center>
<div class=onlinewdiv  >
<input type="button" value="添加场站" onclick="addCzbase()" class="box_table"/>
 	<table id="cz"  cellSpacing=0 cellPadding=0 width=730 border=0> 
    	<%
    	if(list!=null&&list.size()>0){
    	%>
    	<script type="text/javascript">
    	size=<%=list.size() %>;
    	</script>
    	<%	
    	 for(int i=0;i<list.size();i++){
    	%>
    	<tr><td>
    	<input type="hidden" name="czsize" value="<%=i %>"/><br/><br/>
    	<input type="button" value="添加场站" onclick="addCzbase()" class="box_table"/>
    	<input type="button" value="删除场站" onclick="delCzRow(this)" class="box_table"/>
    	<TABLE class=onlineform_table cellSpacing=0 cellPadding=0 width=780 border=0>
		 <tr>
		   <th style="border-right:#9DC3DA solid 1px;font-size:18px" colSpan=4><strong>场站信息表</strong></th>
		</tr>
		<tr>
		   <td style="text-align:right;border-left:1px solid #9DC3DA;" colSpan=4>信息确认号:
		   <INPUT  id=form_no style="BORDER-RIGHT: 0px; BORDER-TOP: 0px; BORDER-LEFT: 0px; BORDER-BOTTOM: 0px" dataSrc=#bind_508 name="form_no" mIndex="63" readOnly value="<%=list.get(i).getForm_no()%>"/>
		   </td>
		</tr>
			    <tr>
			    <th style="width:135px">场站名称
				<FONT color=#ff0000>*</FONT></th>
			    <TD colSpan=3><input type="text" name="czname" value="<%=list.get(i).getCzname()==null?"":list.get(i).getCzname() %>"></TD></TR>
			  <tr>
			    <th>场站地址
			     <FONT color=#ff0000>*</FONT></th>
			    <TD style="BORDER-BOTTOM: #9dc3da 1px solid" colSpan=3><input type="text" name="address" value="<%=list.get(i).getAddress() %>"> </TD></TR>
			 <TR>
			   <Th>坐标
					<FONT color=#ff0000>*</FONT>
			   </th>
					  <TD colSpan=3> 
							1、 X<FONT color=#ff0000>*</FONT>&nbsp;<input type="text" size="6" name="coordinate1x" value="<%=list.get(i).getCoordinate1x()==null||list.get(i).getCoordinate1x().equals("")?"":list.get(i).getCoordinate1x() %>">
							— Y<FONT color=#ff0000>*</FONT>&nbsp;<input type="text"  size="6" name="coordinate1y" value="<%=list.get(i).getCoordinate1y()==null||list.get(i).getCoordinate1y().equals("")?"":list.get(i).getCoordinate1y()%>"> &nbsp;&nbsp;
							2、X&nbsp;<input type="text"  size="6" name="coordinate2x" value="<%=list.get(i).getCoordinate2x()==null||list.get(i).getCoordinate2x().equals("")?"":list.get(i).getCoordinate2x() %>">
							— Y&nbsp;<input type="text"  size="6" name="coordinate2y" value="<%=list.get(i).getCoordinate2y()==null||list.get(i).getCoordinate2y().equals("")?"":list.get(i).getCoordinate2y() %>"><BR> 
							3、X&nbsp;<input type="text"  size="6" name="coordinate3x" value="<%=list.get(i).getCoordinate3x()==null||list.get(i).getCoordinate3x().equals("")?"":list.get(i).getCoordinate3x()%>">
							— Y&nbsp;<input type="text"  size="6" name="coordinate3y" value="<%=list.get(i).getCoordinate3y()==null||list.get(i).getCoordinate3y().equals("")?"":list.get(i).getCoordinate3y() %>"> &nbsp;&nbsp;
							4、X&nbsp;<input type="text"  size="6" name="coordinate4x" value="<%=list.get(i).getCoordinate4x()==null||list.get(i).getCoordinate4x().equals("")?"":list.get(i).getCoordinate4x()%>">
							— Y&nbsp;<input type="text"  size="6" name="coordinate4y" value="<%=list.get(i).getCoordinate4y()==null||list.get(i).getCoordinate4y().equals("")?"":list.get(i).getCoordinate4y() %>">
					  </TD>
			</TR>
			<tr>
			 <th>场站类型</th>
			    <TD  colSpan=3>
			    <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="接收站" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("接收站")>-1 ){ %>checked<%} %>>接收站
			    <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="存储站" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("存储站")>-1 ){ %>checked<%} %>>存储站
			    <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="储配站" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("储配站")>-1 ){ %>checked<%} %>>储配站
			    <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="充装站" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("充装站")>-1 ){ %>checked<%} %>>充装站
			    <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="汽车加气站" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("汽车加气站")>-1 ){ %>checked<%} %>>汽车加气站
			   <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="LNG汽车加气站" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("LNG汽车加气站")>-1 ){ %>checked<%} %>>LNG汽车加气站
			   <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="CNG汽车加气站" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("CNG汽车加气站")>-1 ){ %>checked<%} %>>CNG汽车加气站
			   <input type="checkbox" name="station_type_check<%=i %>" onclick="setStationTypeValue('<%=i %>')" value="其他" <%if(list.get(i).getStation_type()!=null &&list.get(i).getStation_type().indexOf("其他")>-1 ){ %>checked<%} %>>其他
			    <input type="hidden" name="station_type" id="station_type<%=i %>" value="<%=list.get(i).getStation_type() %>"/> 
			    </TD>
			</TR>
		
		<tr>
	    <th>用地种型</Th>
	    <TD  colSpan=3>
	    自有<input type="radio" name="ydzl<%=i %>" onclick="checkYdzl('<%=i %>','zy')" value="自有" <%if(list.get(i).getNo_type()!=null &&list.get(i).getNo_type().equals("自有") ){ %>checked<%} %>> 
	     租用<input type="radio" name="ydzl<%=i %>" onclick="checkYdzl('<%=i %>','zl')" value="租用" <%if(list.get(i).getNo_type()!=null &&list.get(i).getNo_type().equals("租用") ){ %>checked<%} %>>&nbsp;&nbsp;&nbsp;&nbsp;   	  
	  <span id="fwcqhSpan<%=i %>" <%if(list.get(i).getNo_type()!=null &&!list.get(i).getNo_type().equals("自有") ){ %>style="display:none"<%} %>>房屋产权号:
	  <input type="text" name="propertyno" id="fwcqh<%=i %>" value="<%=list.get(i).getPropertyno()==null||list.get(i).getPropertyno().equals("")?"":list.get(i).getPropertyno() %>">
	  </span> 
	  <span id="zlhthSpan<%=i %>" <%if(list.get(i).getNo_type()!=null &&!list.get(i).getNo_type().equals("租用") ){ %>style="display:none"<%} %>>租凭合同号:
	  <input type="text" name="rentalno" id="zlhth<%=i %>" value="<%=list.get(i).getRentalno()==null||list.get(i).getRentalno().equals("")?"":list.get(i).getRentalno() %>">
	  </span>
	      </TD>
	    </TR>
	  <tr>
	   <th>开工日期</th>
	 <TD colSpan=3> <input class="Wdate"  type="text"  onClick="WdatePicker({readOnly:true})" name="startdate" value="<%=list.get(i).getStartdate()==null||list.get(i).getStartdate().equals("")?"":list.get(i).getStartdate() %>"></TD></TR>
	  <tr>
	    <th>竣工日期</th>
	    <TD colSpan=3> <input class="Wdate"  type="text"  onClick="WdatePicker({readOnly:true})" name="enddate" value="<%=list.get(i).getEnddate()==null||list.get(i).getEnddate().equals("")?"":list.get(i).getEnddate() %>"></TD></TR>
	  <tr>
	  <th>符合防火要求的<br>消防验收意见书编号</th>
	   <TD ><input type="text" name="acceptanceno" value="<%=list.get(i).getAcceptanceno()==null||list.get(i).getAcceptanceno().equals("")?"":list.get(i).getAcceptanceno() %>"> </TD>
	   <TD style="width:100px">供气能力</TD>
	   <TD > <input type="text" name="supply" value="<%=list.get(i).getSupply()==null||list.get(i).getSupply().equals("")?"":list.get(i).getSupply() %>">&nbsp;&nbsp;吨/月</TD></TR>
	  <tr>
	   <th>设计单位</TD>
	   <TD><input type="text" name="designcorp" value="<%=list.get(i).getDesigncorp()==null||list.get(i).getDesigncorp().equals("")?"":list.get(i).getDesigncorp()%>"> </TD>
	    <TD >施工单位</TD>
	    <TD><input type="text" name="constructioncorp" value="<%=list.get(i).getConstructioncorp()==null||list.get(i).getConstructioncorp().equals("")?"":list.get(i).getConstructioncorp() %>"> </TD></TR>
	  <tr>
	  <Th>燃气来源</Th>
	 <TD colSpan=3><input type="text" size="50" name="gassource" value="<%=list.get(i).getGassource()==null||list.get(i).getGassource().equals("")?"":list.get(i).getGassource() %>"> </TD></TR>
	 <TR>
	  <th>安评机构</th>
	  <TD colSpan=3><input type="text" size="50" name="secorg" size="30" value="<%=list.get(i).getSecorg()==null||list.get(i).getSecorg().equals("")?"":list.get(i).getSecorg() %>"> </TD></TR>
	  <tr>
	  <Th rowSpan=2>储罐资料</th>
	  <TD colSpan=3>总容积&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" name="volume" value="<%=list.get(i).getVolume()==null||list.get(i).getVolume().equals("")?"":list.get(i).getVolume()%>"> </TD>
	  </TR>
	 <TR>
	   <TD>安装形式</TD>
	   <TD colSpan=2> 
		  <input type="checkbox" name="install_type_check<%=i %>" value="地上罐" onclick="setInstallTypeValue('<%=i %>')" <%if(list.get(i).getInstall_type()!=null &&list.get(i).getInstall_type().indexOf("地上罐")>-1 ){ %>checked<%} %>>地上罐 
		   <input type="checkbox" name="install_type_check<%=i %>" value="半地下罐" onclick="setInstallTypeValue('<%=i %>')" <%if(list.get(i).getInstall_type()!=null &&list.get(i).getInstall_type().indexOf("半地下罐")>-1 ){ %>checked<%} %>>半地下罐
		   <input type="checkbox" name="install_type_check<%=i %>" value="地下罐" onclick="setInstallTypeValue('<%=i %>')" <%if(list.get(i).getInstall_type()!=null &&list.get(i).getInstall_type().indexOf("地下罐")>-1 ){ %>checked<%} %>>地下罐
	   <input type="hidden" name="install_type" id="install_type<%=i %>" value="<%=list.get(i).getInstall_type() %>"/> </TD></TR>
	  <tr>
	    <th colSpan=4  class=onlineform_table_bottom>
	          <p align=center  style="margin:5px;">场站简介
	          <FONT color=#ff0000>*</FONT><br><textarea rows=15 cols=80 name="brief" value="<%=list.get(i).getBrief()%>"><%=list.get(i).getBrief()==null||list.get(i).getBrief().equals("")?"":list.get(i).getBrief()%></textarea></p> 
	      </Th></TR>
	      <tr>
	      <th colspan="4" class=onlineform_table_bottom>
	      <br>
	      <h4>场站设备</h4>
			   <table id="czsb<%=i %>" class=onlineform_table cellSpacing=0 cellPadding=0  border=0>
			   <tr>
			   <th>序号</th>
			   <th>设备名称<FONT color=#ff0000>*</FONT></th>
<%--			   <th>产地</th>--%>
			   <th>规格型号 </th>
			   <th>数量 </th>
			   <th>检测有效期</th><th><input type="button" value="添加" class="box_table" onclick="addCzsbRow('<%=i %>','czsb<%=i %>')"/></th>
			   </tr>
			   <% if(list.get(i).getEqmList()!=null && list.get(i).getEqmList().size()>0){
					for(int j=0;j<list.get(i).getEqmList().size();j++){   
				%>
			   <tr>
					<td><%=j+1 %></td>
					<td><input type="text"   size="16"  name="name<%=i %>" value="<%=list.get(i).getEqmList().get(j).getName()==null||list.get(i).getEqmList().get(j).getName().equals("")?"":list.get(i).getEqmList().get(j).getName() %>"/></td>
<%--					<td><input  size="10" name="plc_of_product<%=i %>"  value="<%=list.get(i).getEqmList().get(j).getPlc_of_product()==null||list.get(i).getEqmList().get(j).getPlc_of_product().equals("")?"":list.get(i).getEqmList().get(j).getPlc_of_product() %>"/></td>--%>
					<td><input  maxlength="6" size="10"  name="product_model<%=i %>" value="<%=list.get(i).getEqmList().get(j).getProduct_model()==null||list.get(i).getEqmList().get(j).getProduct_model().equals("")?"":list.get(i).getEqmList().get(j).getProduct_model() %>"/></td>
					<td><input  maxlength="6" size="10"   name="total_product<%=i %>" value="<%=list.get(i).getEqmList().get(j).getTotal_product()==null||list.get(i).getEqmList().get(j).getTotal_product().equals("")?"":list.get(i).getEqmList().get(j).getTotal_product() %>"/></td>
					<td><input  size="10" maxlength="10" class="Wdate"  type="text"  onClick="WdatePicker({readOnly:true})"   name="val_date_of_test<%=i %>" value="<%=list.get(i).getEqmList().get(j).getVal_date_of_test()==null||list.get(i).getEqmList().get(j).getVal_date_of_test().equals("")?"":list.get(i).getEqmList().get(j).getVal_date_of_test() %>"/></td>
					<td><input type="button" value="删除" class="box_table" onclick="delCzsbRow(this,'czsb<%=i %>')"></td>
			   </tr>
			   <%}} %>
			   </table>
			   </div>
			   </div>
			  
			   </TABLE>
    		</td>
    		</tr>
    	<%}} }catch(Exception ex){ex.printStackTrace();}%>
    </table>
    <br/>
<%--   <table class=onlineform_table cellSpacing=0 cellPadding=0 width=730 border=0>--%>
<%--   <tr>--%>
<%--    <th class=onlineform_table_bottom colSpan=3>--%>
      <P align=center>&nbsp;<INPUT class=backButton type="button" onclick="doSubmit()" value=保存 > 
      </P>
<%--     </th>--%>
<%--     </tr> --%>
<%--   </table>--%>
   </div>
   
   </div>
   </form>
  </body>
</html>
