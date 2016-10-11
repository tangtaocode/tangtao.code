<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
<body>
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
			<tr>
				<td align="left" style="padding: 20px;">
				<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
					<tr>
						<td style="font-size: 18pt; color: #858585">欢迎您来<span style="color:#ffc125">深圳市住房与建设局</span>投资</td>
						
						<td align="right">&nbsp;</td>
					</tr>
				</table>
			</td>
			</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
		 <tr>
			<td style="text-align:center;">

	<table border="0" cellpadding="0" cellspacing="0" style="width: 100%;">
		<tr>
			<td style="text-align:center;" width="90%">
				<table id="investTabTable" border="0" cellpadding="0" cellspacing="0" style="width: 100%">
					<tr>
						<td align="center">
							<div class="investTabSelected"  onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep1');" >
								<div style="padding-top:12px">第一阶段：项目立项</div>
							</div>
						</td>
						<td align="center">
							<div class="investTab" onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep2');" >
								<div style="padding-top:12px">第二阶段：环评</div>
							</div>
						</td>
						<td align="center">
							<div class="investTab" onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep3');">
								<div style="padding-top:12px">第三阶段：工程设计</div>
							</div>
						</td>
					
						<td align="center">
							<div class="investTab" onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep4');">
								<div style="padding-top:12px">第四阶段：概算审批</div>
							</div>
						</td>
						<td align="center">
							<div class="investTab" onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep5');">
								<div style="padding-top:12px">第五阶段：施工许可</div>
							</div>
						</td>
						<td align="center">
							<div class="investTab" onclick="tabSelected(this,'investTabTable','investTab','investTabSelected');qyInvestStepChange('qyInvestStep6');">
								<div style="padding-top:12px">第六阶段：验收决算</div>
							</div>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table></td>
			</tr>
			<tr>
				<td width="100%" valign="top" align="center">
				<div id="qyInvestStep">
				<!--阶段一-->
				<div id="qyInvestStep1">
	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr>
			<td align="left" style="padding-left:70px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" />
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
			<img src="/images/investment/flow/zftz1.png" border="0" usemap="#zftz1" />
			<map id="zftz1" name="zftz1">
				<area shape="rect" coords="34,31,248,131" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A08300000009}')" title="项目立项" />
				<area shape="rect" coords="33,260,250,363" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0850000000A}')" title="临时建筑审批" />
			</map>
			</td>
		</tr>
	</table></div>
	<!--阶段二-->
				<div id="qyInvestStep2" style="display: none">

	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr> 
			<td align="left" style="padding-left:225px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" /> 
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
			<img src="/images/investment/flow/zftz2.png" border="0" usemap="#zftz2" /> 
			<map id="zftz2" name="zftz2">
				<area shape="rect" coords="198,30,416,132" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0860000000B}')" title="环境影响评价批复" />
				<area shape="rect" coords="198,174,415,274" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0880000000C}')" title="勘察设计招标" />
				<area shape="rect" coords="198,313,416,414" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A08A0000000D}')" title="重要项目可研审批" />
				<area shape="rect" coords="197,459,415,560" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A08C0000000E}')" title="节能审批" />
				</map>
			</td>
		</tr>
	</table></div>
	<!--阶段三-->
				<div id="qyInvestStep3" style="display: none">

	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr>
			<td align="left" style="padding-left:380px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" />
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
			<img src="/images/investment/flow/zftz3.png" border="0" usemap="#zftz3" />
			<map id="zftz3" name="zftz3" onclick="">
				<area shape="rect" coords="122,31,341,132" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A08D0000000F}')" title="人防报建审核" />
				<area shape="rect" coords="122,170,340,270" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09000000010}')" title="消防设计审核" onclick=""/>
				<area shape="rect" coords="121,306,338,406" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09200000011}')" title="水土保持方案审批" onclick=""/>
				</map>
			</td>
		</tr>
	</table></div>
	<!--阶段四-->
				<div id="qyInvestStep4" style="display: none">
	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr>
			<td align="left" style="padding-left:540px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" />
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
			<img  border="0" usemap="#zftz4" src="/images/investment/flow/zftz4.png" /> 
			<map id="zftz4" name="zftz4">
				<area shape="rect" coords="120,30,337,132" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09400000012}')" title="概算审批" />
				<area shape="rect" coords="121,168,336,270" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09500000013}')" title="临时建筑设计方案审批" />
				<area shape="rect" coords="119,308,336,410" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09700000014}')" title="临时建筑工程规划许可" />
				</map>
			</td>
		</tr>
	</table></div>
	<!--阶段五-->
	<div id="qyInvestStep5" style="display: none">
	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr>
			<td align="left" style="padding-left:700px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" />
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
			<img src="/images/investment/flow/zftz5.png" border="0" usemap="#zftz5" />
			<map id="zftz5" name="zftz5">
				<area shape="rect" coords="109,29,302,131" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09900000015}')" title="概算设计" />
				<area shape="rect" coords="108,180,301,282" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09B00000016}')" title="招投标等备案" />
				<area shape="rect" coords="109,326,301,426" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09C00000017}')" title="征用占用林地许可" />
				<area shape="rect" coords="373,179,552,283" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A09F00000018}')" title="招投投标" />
				<area shape="rect" coords="374,322,553,428" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0A100000019}')" title="施工许可" />
			</map>
			
			</td>
		</tr>
	</table></div>
	<!--阶段六-->
	<div id="qyInvestStep6" style="display: none">
	<table border="0" cellpadding="0" cellspacing="0px" style="width: 100%;">
		<tr>
			<td align="left" style="padding-left:860px;padding-top:20px">
				<img src="/images/investment/tab_invest_arrow.png" width="23px" height="10px" border="0" />
			</td>
		</tr>
		<tr>
			<td>
				<img src="/images/investment/line_invest_split.png" width="956px" height="22px" border="0" />
			</td>
		</tr>
		<tr>
			<td style="text-align:center;">
			<img src="/images/investment/flow/zftz6.png" border="0" usemap="#zftz6" />
			<map id="zftz6" name="zftz6">
				<area shape="rect" coords="40,120,184,201" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0A20000001A}')" title="工程拨款" />
				<area shape="rect" coords="200,122,343,203" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0A40000001B}')" title="设备采购" />
				<area shape="rect" coords="42,302,184,384" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0A60000001C}')" title="工程报账" />
				<area shape="rect" coords="197,301,343,384" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0A80000001D}')" title="环保验收" />
				<area shape="rect" coords="357,302,500,384" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0A90000001E}')" title="消防验收" />
				<area shape="rect" coords="161,490,317,575" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0AB0000001F}')" title="工程结算审计" />
				<area shape="rect" coords="336,489,492,571" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0AE00000020}')" title="竣工资料归档" />
				<area shape="rect" coords="336,605,493,686" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0AF00000021}')" title="竣工验收备案" />
				<area shape="rect" coords="324,714,501,800" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0B100000022}')" title="竣工财务决算审计" />
				<area shape="rect" coords="328,831,501,911" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0B300000023}')" title="竣工财务决算批复" />
				<area shape="rect" coords="328,942,501,1026" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0B500000024}')" title="产权登记" />
				<area shape="rect" coords="328,1057,501,1138" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0B600000025}')" title="资产移交" />
				<area shape="rect" coords="563,121,735,201" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0B800000026}')" title="设计变更确认" />
				<area shape="rect" coords="563,302,737,384" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0BA00000027}')" title="设计变更审批" />
				<area shape="rect" coords="555,491,746,573" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-A0BD00000028}')" title="设计变更审计" />
				<area shape="rect" coords="563,644,736,724" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-9FBF00000029}')" title="下达变更投资计划" />
				<area shape="rect" coords="746,391,917,472" href="javascript:queryApprove('{7F000001-FFFF-FFFF-F346-9FC10000002A}')" title="设计变更上报审批" />
			</map>
			</td>
		</tr>
	</table></div>
				</div>
				</td>
			</tr>
		</table>
</body>
</html>