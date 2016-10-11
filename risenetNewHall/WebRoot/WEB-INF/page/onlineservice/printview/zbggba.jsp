<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
	<HEAD>
	<style type="text/css">
	body{
		width:700px;
		margin:auto;
		background-color:#fff;
	}
	#divhead{
		padding-top:50px;
	}
	</style>		
	</HEAD>
	<BODY>
		<FORM>
			<DIV id=divhead>
				<TABLE id=maintable align=center style="line-height:40px;">
					<TBODY>
						<TR>
							<TD align=middle>
								<SPAN style="WIDTH: 650px; FONT-FAMILY: 宋体; FONT-SIZE: 35px">深圳市龙岗区住房和建设局业务办理中心</SPAN>
							</TD>
						</TR>
						<TR>
							<TD width=698 align=middle>
								<SPAN style="WIDTH: 650px; WHITE-SPACE: nowrap; FONT-SIZE: 16px">The
									Business Service Center of Housing and Construction Bureau of
									Longgang Shenzhen Municipality</SPAN>
							</TD>
						</TR>
						<TR>
							<TD>
								<!--  <HR
									style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; HEIGHT: 8px; BORDER-TOP: #000000 9px dashed; BORDER-RIGHT: medium none">
							</TD>-->
							<HR
								style='BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; HEIGHT: 10px; BORDER-TOP: #000000 10px groove; BORDER-RIGHT: medium none; width: 100%'>
						</TD>
						</TR>
						<TR>
							<TH height=35 width=698 align=middle>
								<FONT size=5 face=宋体>
								建设工程招标公告备案网上申报预备案回执
								</FONT>
							</TH>
						</TR>
					</TBODY>
				</TABLE>
			</DIV>
			<DIV id=divbody style="height:750px;">
				<TABLE border=0 cellSpacing=0 cellPadding=5 width=650
					align=center style="line-height:50px;font-size:20px;">
					<TBODY>
						<TR>
							<TD>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您本次网上报建的申报编号为：<SPAN style="TEXT-DECORATION: underline">${app.declaresn }</SPAN>
								工程名称：<SPAN style="TEXT-DECORATION: underline">${app.gcmc }</SPAN>
								，请在5个工作日内，带齐相关资料到招标备案窗口办理相关手续。
							</TD>
						</TR>
						<tr><TD>&nbsp;</TD></tr>
						<tr>
						<TD style="text-align:right">日期：${app. ytjids}年${app.xbqids }月${app.xbzids }日</TD>
						</tr>
						<tr><TD>&nbsp;</TD></tr>
						<tr><TD>&nbsp;</TD></tr>
						<tr><TD>&nbsp;</TD></tr>
				</TABLE>
		</DIV>
		<div id="footer" width=698>
			<TABLE id=maintable align=center width=698>
			  <TBODY>
			  <TR>
			    <TD align=middle>
			      <HR 
			      style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; HEIGHT: 10px; BORDER-TOP: #000000 10px groove; BORDER-RIGHT: medium none">
			    </TD>
			  </TR>
			  <TR>
			    <TD  align=middle>
			      <TABLE border=0 cellPadding=0 width=698>
			        <TBODY>
			        <TR>
			          <TD style="TEXT-ALIGN: left">
			           <span style="FONT-SIZE: 14px">龙岗建设网网址：www.cb.gov.cn</span></TD>
			          <TD style="TEXT-ALIGN: right">
			            <span style="FONT-SIZE: 14px">咨询电话：28589110</span></TD>
			          </TR>
			         </TBODY>
			       </TABLE>
			      </TD>
			    </TR>
			  </TBODY>
			 </TABLE>
		</div>
						
		</FORM>
	</BODY>
</HTML>
