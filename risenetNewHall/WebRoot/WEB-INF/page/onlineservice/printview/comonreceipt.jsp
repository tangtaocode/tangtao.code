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
						<TABLE id=maintable align=center >
				<TBODY>
					<TR >
						<TD align="center" colspan="2" width="698">
							<SPAN style='FONT-FAMILY: 宋体; FONT-SIZE: 36px'>深圳市龙岗区住房和建设局业务办理中心</SPAN>
						</TD>
					</TR>
					<TR>
						<TD colspan="2" align="center" width="698">
							<SPAN style="FONT-SIZE: 16px;" lang=EN-US>The Business Service Center of Housing & Construction Bureau of  Longgang   Shenzhen Municipality
							</SPAN>
						</TD>
					
					<TR>
						<TD colspan="2" style="height:0px;" width="698">
      						<HR style='BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; HEIGHT: 1px; BORDER-TOP: #000000 10px groove; BORDER-RIGHT: medium none; width: 99%'>
							<HR style='BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; HEIGHT: 3px; BORDER-TOP: #000000 10px groove; BORDER-RIGHT: medium none; width: 100%;position: relative;top: -10px;'>
						</TD>
					</TR>
					<TR>
							<TH height=35 width=698 align=middle>
								<FONT size=5 face=宋体>
								网上申报预备案回执
								</FONT>
							</TH>
						</TR>
				</TBODY>
			</TABLE>
			</DIV>
			<DIV id=divbody style="height:800px;">
				<TABLE border=0 cellSpacing=0 cellPadding=5 width=650
					align=center style="line-height:50px;font-size:20px;font-family: 仿宋_GB2312;">
					<TBODY>
						<TR>
							<TD>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您本次网上报建的申报编号为：<SPAN style="TEXT-DECORATION: underline">${app.declaresn }</SPAN>
								工程名称：<SPAN style="TEXT-DECORATION: underline">${app.gcmc }</SPAN>，办理事项：<span style="TEXT-DECORATION: underline">${app.formname }</span>
								，请在5个工作日内，带齐相关资料到业务窗口办理相关手续。
							</TD>
						</TR>
						<tr><TD>&nbsp;</TD></tr>
						<tr>
						<TD align="center">深圳市龙岗区住房和建设局业务办理中心</TD>
						</tr>
						<tr>
						<TD style="text-align:center;letter-spacing: 2px;">&nbsp;&nbsp;&nbsp;&nbsp;${app. ytjids}  年  ${app.xbqids }    月  ${app.xbzids }日</TD>
						</tr>
						<tr><TD>&nbsp;</TD></tr>
						<tr><TD>&nbsp;</TD></tr>
						<tr><TD>&nbsp;</TD></tr>
				</TABLE>
		</DIV>
		<div id="footer" width=698>
		<TABLE id=maintable align=center style="FONT-SIZE: 13px;">
  <TBODY>
  <tr>
  <td width=698 align="left">
  <SPAN ><strong>注：</strong>本回执一式二份，申请人和深圳市龙岗区住房和建设局各一份。</SPAN>
  </td>
  </tr>
  <TR>
    <TD width=698 align=middle>
      <HR 
      style="BORDER-BOTTOM: medium none; BORDER-LEFT: medium none; HEIGHT: 2px; BORDER-TOP: #000000 2px groove; BORDER-RIGHT: medium none">
    </TD></TR>
  <TR>
    <TD width=698 align=middle>
      <TABLE border=0 cellPadding=0 width=698 style="FONT-SIZE: 13px;">
        <TBODY>
        <TR>
          <TD style="TEXT-ALIGN: left">龙岗区住房和建设局网址：www.cb.gov.cn</TD>
          <TD style="TEXT-ALIGN: right">咨询电话：28589822</TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE>
		</div>
						
		</FORM>
	</BODY>
</HTML>
