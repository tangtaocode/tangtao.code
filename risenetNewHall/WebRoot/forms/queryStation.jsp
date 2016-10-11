<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,net.risesoft.beans.onlineservice.*"%>
<%@page import="net.risesoft.util.ValidatorUtil"%>
<%--<%@ page import="java.util.*,net.risesoft.webapprove.forms.bean.*"%>--%>
<HTML>
	<META>
	<head>
		<LINK href="../css/main.css" type=text/css rel=stylesheet>

	</head>

	<body>
		<form method="post" id="czform">
			<%
				  try{
				  String insGuid = request.getParameter("insGuid");
				  List<CorpGasStation> list = (List<CorpGasStation>)request.getAttribute("CorpStationList");
 			%>
			<input type="hidden" name="insGuid" value="<%=insGuid %>" />
			<div align=center>
				<div class=onlinewdiv>
					<table id="cz" cellSpacing=0 cellPadding=0 width=730 border=0>
						<%
				    		if(list!=null&&list.size()>0){
				    	%>
						<script type="text/javascript">
    					size=<%=list.size() %>;
    					</script>
						<%	
		    	 for(int i=0;i<list.size();i++){
		    	%>
						<input type="hidden" name="czsize" value="<%=i %>" />
						<br />
						<br />

						<table class=onlineform_table cellSpacing=0 cellPadding=0
							width=730 border=0>
							<tr>
								<th style="border-right: #9DC3DA solid 1px; font-size: 18px"
									colSpan=4>
									<strong>场站信息（<%=ValidatorUtil.filter(list.get(i).getCzname() )%>）</strong>
								</th>
							</tr>
							<tr>
								<td style="text-align: right;border-left: 1px solid #9DC3DA" colSpan=4>
									编号:<%=list.get(i).getForm_no() %></td>
							</tr>
							<tr>
								<th >
									场站名称
									<font color=#ff0000>*</font>
								</th>
								<td colSpan=3><%=list.get(i).getCzname()%>&nbsp; </td>
							</tr>
							<tr>
								<th>
									场站地址
									<font color=#ff0000>*</font>
								</th>
								<TD style="BORDER-BOTTOM: #9dc3da 1px solid" colSpan=3><%=list.get(i).getAddress() %>&nbsp; 
								</TD>
							</TR>
							<TR>
								<Th>
									坐标
									<FONT color=#ff0000>*</FONT>
								</th>
								<TD colSpan=3>
									1、 X:<%=ValidatorUtil.filter(list.get(i).getCoordinate1x()) %>
									— Y:<%=ValidatorUtil.filter(list.get(i).getCoordinate1y()) %>&nbsp;&nbsp; 2、X:<%=ValidatorUtil.filter(list.get(i).getCoordinate2x()) %>
									— Y:<%=ValidatorUtil.filter(list.get(i).getCoordinate2y()) %><BR>
									3、&nbsp;X:<%=ValidatorUtil.filter(list.get(i).getCoordinate3x()) %>
									— Y:<%=ValidatorUtil.filter(list.get(i).getCoordinate3y()) %>
									&nbsp; 4、X:<%=ValidatorUtil.filter(list.get(i).getCoordinate4x())%>
									— Y<%=ValidatorUtil.filter(list.get(i).getCoordinate4y()) %>
								</TD>
							</TR>
							<tr>
								<th>
									场站类型
								</th>
								<TD colSpan=3>
									<%=list.get(i).getStation_type()==null||list.get(i).getStation_type().equals("null")?"":list.get(i).getStation_type()%>&nbsp;
								</TD>
							</TR>

							<tr>
								<th>
									用地种型
								</Th>
								<TD colSpan=3>
									<span <%if(list.get(i).getNo_type()!=null &&!list.get(i).getNo_type().equals("自有") ){ %>style="display:none"<%} %>>
									 房屋产权号: <%=ValidatorUtil.filter(list.get(i).getPropertyno()) %></span>
									<span  <%if(list.get(i).getNo_type()!=null &&!list.get(i).getNo_type().equals("租用") ){ %>style="display:none"<%} %>>
									租凭合同号:<%=ValidatorUtil.filter(list.get(i).getRentalno()) %></span>
								
									</TD>
							</TR>
							<tr>
								<th>
									开工日期
								</th>
								<TD ><%=ValidatorUtil.filter(list.get(i).getStartdate()) %>&nbsp; </TD>
								<th>
									竣工日期
								</th>
								<TD >
									<%=ValidatorUtil.filter(list.get(i).getEnddate()) %>&nbsp; </TD>
							</TR>
							
							<tr>
								<th>
									符合防火要求<br>消防验收意见书编号
								</th>
								<td><%=ValidatorUtil.filter(list.get(i).getAcceptanceno()) %>&nbsp; </td>
								<th >
									供气能力
								</th>
								<td>
									<%=ValidatorUtil.filter(list.get(i).getSupply()) %>&nbsp; </TD>
							</tr>
							<tr>
								<th>
									设计单位
									</TD>
									<TD><%=ValidatorUtil.filter(list.get(i).getDesigncorp())%>&nbsp; 
									</TD>
									<th>
										施工单位
									</th>
									<TD><%=ValidatorUtil.filter(list.get(i).getConstructioncorp()) %>&nbsp; 
									</TD>
							</TR>
							<tr>
								<Th>
									燃气来源
								</Th>
								<TD colSpan=3><%=ValidatorUtil.filter(list.get(i).getGassource()) %>&nbsp; 
								</TD>
							</TR>
							<TR>
								<th>
									安评机构
								</th>
								<TD colSpan=3><%=ValidatorUtil.filter(list.get(i).getSecorg()) %>&nbsp; 
								</TD>
							</TR>
							<tr>
								<Th rowSpan=2>
									储罐资料
								</th>
								<TD>
									总容积
								</TD>
								<TD colSpan=2><%=ValidatorUtil.filter(list.get(i).getVolume())%>&nbsp; 
								</TD>
							</TR>
							<TR>
								<TD>
									安装形式
								</TD>
								<TD colSpan=2>
									<%=list.get(i).getInstall_type()==null||list.get(i).getInstall_type().equals("null")?"":list.get(i).getInstall_type() %>&nbsp; 
								</TD>
							</TR>
							<tr>
								<th colSpan=4 class=onlineform_table_bottom>
									<p align=center style="margin: 5px;">
										场站简介
										<FONT color=#ff0000>*</FONT>
										<br><%=ValidatorUtil.filter(list.get(i).getBrief())%></p>
								</Th>
							</TR>
							<tr>
								<th colspan="4" class=onlineform_table_bottom>
									<br>
									<h4>
										场站设备
									</h4>
									<table id="czsb<%=i %>" class=onlineform_table cellSpacing=0
										cellPadding=0 border=0>
										<tr >
											<th>序号</th>
											<th>设备名称<FONT color=#ff0000>*</FONT>
											</th>
<%--											<th>产地</th>--%>
											<th>规格型号</th>
											<th>数量</th>
											<th colspan="2">检查有效期</th>
										</tr>
										<% if(list.get(i).getEqmList()!=null && list.get(i).getEqmList().size()>0){
											for(int j=0;j<list.get(i).getEqmList().size();j++){   
										%>
										<tr>
											<td><%=j+1 %>&nbsp; </td>
											<td><%=ValidatorUtil.filter(list.get(i).getEqmList().get(j).getName()) %>&nbsp; </td>
<%--											<td><%=ValidatorUtil.filter(list.get(i).getEqmList().get(j).getPlc_of_product()) %>&nbsp;</td>--%>
											<td><%=ValidatorUtil.filter(list.get(i).getEqmList().get(j).getProduct_model()) %>&nbsp; </td>
											<td><%=ValidatorUtil.filter(list.get(i).getEqmList().get(j).getTotal_product()) %>&nbsp; </td>
											<td colspan="2">
											<%=ValidatorUtil.filter(list.get(i).getEqmList().get(j).getVal_date_of_test()) %>&nbsp; 
											</td>
											
										</tr>
										<%}} %>
									</table>
									</div>
									</div>
						</table>

						<%}}else{%>
						<div align="center">
							未查询出相关信息！
						</div>
						<%} }catch(Exception ex){ex.printStackTrace();}%>
					</table>

				</div>

			</div>
		</form>
	</body>
</html>
