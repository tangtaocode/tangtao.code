<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<html>
<head>
<title>深圳市龙岗区住房与建设局产业转型升级专项资金网上申报</title>
<link href="/css/appGuide.css" rel="stylesheet" type="text/css"/>
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="/dwr/interface/BizApplyJs.js"></script>
<script type="text/javascript" src="/js/technology.js"></script>
<script type="text/javascript" src="/js/calendar.js"></script>
<script type="text/javascript" src="/js/validation.js"></script>
 
<style type="text/css">
body{ margin: 1;
	  padding: 1; 
	  }

</style>
<script type="text/javascript">

</script>
</head>
<body>
<form action="/bizApply/savebaseInfoBean.html" method="post" id="submitForm">
<table  border="0" align="center" cellpadding="0" cellspacing="0" class="table_line">
<tr><td class="top"> </td> </tr>
<tr><th class="title" style="height:30px;">单位基本情况</th></tr>
<tr><td>
<div style="overflow: scroll;height:485px;width:1195px;scrollbar-face-color:#E0F2FF;">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop">
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">单位名称：</TD>
			<TD width="80%" class="td_left"  colspan="5">
			<s:hidden name="unitBaseInfoBean.guid"></s:hidden>
			<s:hidden name="unitBaseInfoBean.appguid"></s:hidden>
			<s:textfield  name="unitBaseInfoBean.departmentname" id="departmentname" size="20" verify="单位名称|NotNull" cssClass="zc_input02"  maxlength="300" ></s:textfield><span style="color:red">*</span>
			</TD>
		    <TD width="20%" class="title_center" nowrap="nowrap">法人代表：</TD>
			<TD width="80%" class="td_left" colspan="5">
			<s:textfield  name="unitBaseInfoBean.lawper" id="lawper" size="20" cssClass="zc_input02" verify="法人代表|NotNull"  maxlength="30" ></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">法人代码：</TD>
			<TD width="80%" class="td_left"  colspan="2">
			<s:textfield  name="unitBaseInfoBean.corporationcode" id="corporationcode" size="20" verify="法人代码|NotNull" cssClass="zc_input02"  maxlength="30" ></s:textfield><span style="color:red">*</span>
			</TD>
		    <TD width="20%" class="title_center" nowrap="nowrap">营业执照注册号：</TD>
			<TD width="80%" class="td_left" colspan="2">
			<s:textfield  name="unitBaseInfoBean.regcode" id="regcode" size="20" cssClass="zc_input02" verify="注册号|NotNull"  maxlength="20" ></s:textfield><span style="color:red">*</span>
			</TD>
			<TD width="20%" class="title_center" nowrap="nowrap">国税登记证号：</TD>
			<TD width="80%" class="td_left" colspan="5">
			<s:textfield  name="unitBaseInfoBean.nationaltax" id="nationaltax" size="20" verify="国税号|NotNull" cssClass="zc_input02"  maxlength="20" ></s:textfield><span style="color:red">*</span>
		 </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">单位地址：</TD>
			<TD width="80%" class="td_left"  colspan="5">
			<s:textfield  name="unitBaseInfoBean.departmentadd" id="departmentadd" size="20"  verify="单位地址|NotNull" cssClass="zc_input02"  maxlength="500" ></s:textfield><span style="color:red">*</span>
		 </TD>
		    <TD width="20%" class="title_center" nowrap="nowrap">地税登记证号：</TD>
			<TD width="80%" class="td_left" colspan="5">
			<s:textfield  name="unitBaseInfoBean.localtax" id="localtax" size="20" cssClass="zc_input02" verify="地税号|NotNull"  maxlength="20" ></s:textfield><span style="color:red">*</span>
			 </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">基本户开户银行：</TD>
			<TD width="80%" class="td_left"  colspan="5">
			<s:textfield  name="unitBaseInfoBean.bankname" id="bankname" size="20" cssClass="zc_input02" verify="开户银行|NotNull"  maxlength="300" ></s:textfield><span style="color:red">*</span>
			</TD>
		    <TD width="20%" class="title_center" nowrap="nowrap">帐号：</TD>
			<TD width="80%" class="td_left" colspan="5">
			<s:textfield  name="unitBaseInfoBean.bankcode" id="bankcode" size="20" cssClass="zc_input02" onkeyup="restrictNumber(this,'1',25)" verify="帐号|NotNull"  maxlength="25" ></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">单位成立时间：</TD>
			<TD width="80%" class="td_left"  colspan="5">
			<s:textfield  name="unitBaseInfoBean.founddate" id="founddate" size="20" cssClass="zc_input02"  verify="成立时间|NotNull" onclick="calendar.show(this);" readonly="readonly" ></s:textfield><span style="color:red">*</span>
			 </TD>
		    <TD width="20%" class="title_center" nowrap="nowrap">注册资本：</TD>
			<TD width="80%" class="td_left" colspan="5">
			<s:textfield  name="unitBaseInfoBean.regmoney" id="regmoney" size="20" cssClass="zc_input02" verify="注册资本|NotNull" onkeyup="restrictNumber(this,'2',2)" maxlength="20" ></s:textfield><span style="color:red">*</span>
			 </TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">经营范围：</TD>
			<TD width="80%" class="td_left"  colspan="11">
			<s:textfield  name="unitBaseInfoBean.businesssphere" id="businesssphere" size="20" verify="经营范围|NotNull" cssClass="zc_input02"  maxlength="2000" ></s:textfield><span style="color:red">*</span>
			</TD>
		</TR>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">企业资质：</TD>
			<TD width="80%" class="td_left"  colspan="11">
			<s:radio name="unitBaseInfoBean.qualification" list="%{#{'1':'国家级高新技术企业','2':'市级高新技术企业','3':'市级软件企业','4':'互联网企业','5':'新一代信息技术企业'}}"  ></s:radio> 
			 &nbsp;&nbsp;<span style="color:red">* </span> </TD>
		</TR>
		<tr>
		<td colspan="12">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="initDateTableId" class="table_leftop">
		<tr>
		<s:iterator value="shareholderList" status="sh">
		<td><input type="checkbox" id="<s:property value="guid"/>checkBox" onclick="selectShareholder(this)" value="<s:property value="guid"/>"><s:property value="stockholdername"/> </td>
		<s:if test="(#sh.index+1)%8==0"><tr></s:if>
		</s:iterator>
		</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop" id="shareholderTableId">
		<tr><th colspan="5" style="text-align:center;font-size: 20px;font-family:宋体">单位主要股东信息</th></tr>
		<tr id="shareholderTrId">
			<td class="table_td_center" width="20%">主要股东名称</td>
			<td class="table_td_center" width="20%">股东出资额（万元）</td>
			<td class="table_td_center" width="20%">出资形式</td>
			<td class="table_td_center" width="20%">所占股份比例（%）</td>
			<td class="table_td_center" width="5%">删除</td>
		</tr>
		<s:iterator value="subShareholderList" status="ush">
			<tr id="<s:property value="guid"/>" >
			<td class="td_left">
			<input type="hidden" name="shareholderBean.guid" id="guid" value="<s:property value="guid"/>">
								<input type="text" verify="股东名称|NotNull" id="stockholdername" name="shareholderBean.stockholdername" value="<s:property value="stockholdername"/>" maxlength="100" class="zc_input02" size="30"></td>
			<td class="td_left"><input type="text" verify="出资额|NotNull" id="stockholdermoney" name="shareholderBean.stockholdermoney" value="<s:property value="stockholdermoney"/>" onkeyup="restrictNumber(this,'2',2)" maxlength="10" class="zc_input02" size="30"></td>
			<td class="td_left"><input type="text" verify="出资形式|NotNull" id="financialtype" name="shareholderBean.financialtype" value="<s:property value="financialtype"/>" maxlength="100" class="zc_input02" size="30"></td>
			<td class="td_left"><input type="text" verify="所占比例|NotNull" id="shareproportion" name="shareholderBean.shareproportion" value="<s:property value="shareproportion"/>" onkeyup="restrictNumber(this,'2',2)" maxlength="10" class="zc_input02" size="30"></td>
			<td class="td_left" align="center"><a href="javaScript:return false;" onclick="removeById('shareholderTableId','<s:property value="guid"/>')"><img src="/images/page/cancel.png" style="border:0" ></a> </td>
		</s:iterator>
		<tr><td colspan="5" class="addnew" width="100%" ><a href="javaScript:return false;" onclick="addShareholder('')" title="点击选择"><img style="border:0" src="/images/page/add.png">新增股东信息</a>&nbsp;&nbsp;</td> </tr>
		</table>
		
		</td>
		</tr>
		<TR>
			<TD width="20%" class="title_center" nowrap="nowrap">员（职）工总数：</TD>
			<TD width="80%" class="td_left"  colspan="3">
			<s:textfield verify="员工总数|NotNull" name="unitBaseInfoBean.totalpeop" onkeyup="restrictNumber(this,'1',7)" id="totalpeop" size="15" cssClass="zc_input02"  maxlength="1000" ></s:textfield><span style="color:red">* </span> 
			</TD>
		    <TD width="20%" class="title_center" colspan="2" nowrap="nowrap">大专以上人员人数及占数百分比：</TD>
			<TD width="80%" class="td_left" colspan="2">
			<s:textfield verify="大专比例|NotNull" name="unitBaseInfoBean.dztotalpeop" id="dztotalpeop" size="15" onkeyup="restrictNumber(this,'1',7)" cssClass="zc_input02"  maxlength="1000" ></s:textfield><span style="color:red">* </span>
			<TD width="20%" class="title_center" colspan="2" nowrap="nowrap">研究开发人员人数及占总数百分比：</TD>
			<TD width="80%" class="td_left" colspan="2">
			<s:textfield verify="研发人员比例|NotNull" name="unitBaseInfoBean.researchpeop" id="researchpeop" size="15" onkeyup="restrictNumber(this,'1',7)" cssClass="zc_input02"  maxlength="1000" ></s:textfield><span style="color:red">* </span>
		</TR>
		<tr>
  <td rowspan=3 class="title_center">单位拥有知识产权状况（件）</td>
  <td rowspan=2 class="table_td_center">专利申请总数</td>
  <td colspan=3 class="table_td_center">其中 </td>
  <td rowspan=2 class="table_td_center">专利授权总数</td>
  <td colspan=3 class="table_td_center">其中</td>
  <td rowspan=2 class="table_td_center">软件著作权</td>
  <td rowspan=2 class="table_td_center">（IC）布图设计专有权</td>
  <td rowspan=2 class=table_td_center>植物新品种权</td>
 </tr>
 <tr>
  <td class="table_td_center">发明</td>
  <td class="table_td_center">实用新型</td>
  <td class="table_td_center">外观设计</td>
  <td class="table_td_center">发明</td>
  <td class="table_td_center">实用新型</td>
  <td class="table_td_center">外观设计</td>
 </tr>
 <tr>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.patentnum" verify="专利申请总数|NotNull" onkeyup="restrictNumber(this,'1',7)" id="patentnum" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield> </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.patentinvent" verify="发明数|NotNull" onkeyup="restrictNumber(this,'1',7)" id="patentinvent" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
  </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.patentpractical" verify="实用新型|NotNull" onkeyup="restrictNumber(this,'1',7)" id="patentpractical" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
 </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.patentsurface" verify="外观设计|NotNull" onkeyup="restrictNumber(this,'1',7)" id="patentsurface" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
 </td>
 <td class="td_left">
<s:textfield  name="unitBaseInfoBean.empowernum" verify="专利授权总数|NotNull" onkeyup="restrictNumber(this,'1',7)" id="empowernum" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield> 
 </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.empowerinvent" verify="发明|NotNull" onkeyup="restrictNumber(this,'1',7)" id="empowerinvent" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield> 
 </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.empowerpractical" verify="实用新型|NotNull" onkeyup="restrictNumber(this,'1',7)" id="empowerpractical" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
  </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.empowersurface" verify="外观设计|NotNull" onkeyup="restrictNumber(this,'1',7)" id="empowersurface" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
 </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.softwarepower" verify="软件著作权|NotNull" onkeyup="restrictNumber(this,'1',7)" id="softwarepower" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
 </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.icdevisepower" verify="专有权|NotNull" onkeyup="restrictNumber(this,'1',7)" id="icdevisepower" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
 </td>
 <td class="td_left">
 <s:textfield  name="unitBaseInfoBean.speciespower" verify="新品种权|NotNull" onkeyup="restrictNumber(this,'1',7)" id="speciespower" size="10" cssClass="zc_input02"  maxlength="7"></s:textfield>
</td>
 </tr>
 <tr>
		<td colspan="12">
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" id="initDateTableIdsu" class="table_leftop">
		<tr>
		<s:iterator value="historySubsidizeList" status="hs">
		<td><input type="checkbox" onclick="selectHistorySubsidize(this)" id="<s:property value="guid"/>checkBox" value="<s:property value="guid"/>"><s:property value="projectname"/> </td>
		<s:if test="(#hs.index+1)%5==0"><tr></s:if>
		</s:iterator>
		</tr>
		</table>
		<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="table_leftop" id="subsidizeTableId">
		<tr><th colspan="10" style="text-align:center;font-size: 20px;font-family:宋体">近三年曾获深圳市、区政府资助情况（单位：万元）</th></tr>
		<tr id="subsidizeTrId">
		<td class="table_td_center" width="10%">项目名称</td>
		<td class="table_td_center" width="10%">资助部门</td>
		<td class="table_td_center" width="10%">资助时间</td>
		<td class="table_td_center" width="10%">资助金额</td>
		<td class="table_td_center" width="10%">项目验收情况</td>
		<td class="table_td_center" width="10%">验收时间</td>
		<td class="table_td_center" width="10%">资助形式</td>
		<td class="table_td_center" width="10%">借款偿还情况</td>
		<td class="table_td_center" width="10%">还款时间</td>
		<td class="table_td_center" width="10%">删除</td>
		</tr>
		<s:iterator value="subHistorySubsidizeList" status="uhs">
		<tr id="<s:property value="guid"/>"><td class="td_left"><input type="hidden" name="historySubsidize.guid"  id="guid" value="<s:property value="guid"/>">
							<input type="text" verify="项目名称|NotNull" id="projectname" maxlength="500"  name="historySubsidize.projectname"   value="<s:property value="projectname"/>" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text" verify="资助部门|NotNull" id="subsidizename" maxlength="300" name="historySubsidize.subsidizename" value="<s:property value="subsidizename"/>" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text" verify="资助时间|NotNull" id="subsidizedate" name="historySubsidize.subsidizedate" value="<s:property value="subsidizedate"/>" readonly="readonly" onclick="calendar.show(this);" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text" verify="资助金额|NotNull" id="subsidizemoney" maxlength="10" onkeyup="restrictNumber(this,'2',2)" name="historySubsidize.subsidizemoney" value="<s:property value="subsidizemoney"/>" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text" verify="验收情况|NotNull" id="acceptance" maxlength="1000"   name="historySubsidize.acceptance"    value="<s:property value="acceptance"/>" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text"  id="acceptancedate" name="historySubsidize.acceptancedate" value="<s:property value="acceptancedate"/>" readonly="readonly" onclick="calendar.show(this);" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text" verify="资助形式|NotNull" id="subsidizetype" maxlength="100" name="historySubsidize.subsidizetype" value="<s:property value="subsidizetype"/>" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text" verify="偿还情况|NotNull" id="repayment" maxlength="1000"	  name="historySubsidize.repayment"    value="<s:property value="repayment"/>" class="zc_input02" size="10"> </td>
		<td class="td_left"><input type="text"  id="repaymentdate" name="historySubsidize.repaymentdate"  value="<s:property value="repaymentdate"/>" readonly="readonly" onclick="calendar.show(this);" class="zc_input02" size="10"> </td>
		<td class="td_left" align="center"><a href="javaScript:return false;" onclick="removeById('subsidizeTableId','<s:property value="guid"/>');"><img  src="/images/page/cancel.png" style="border:0" ></a></td></tr>		
		</s:iterator>
		<tr><td colspan="10" class="addnew" width="100%" ><a href="javaScript:return false;" onclick="addHistorySubsidize('')" title="点击选择"><img style="border:0" src="/images/page/add.png">新增资助信息</a>&nbsp;&nbsp;</td> </tr>
		</table>
		
		</td>
		</tr>
		</table>
		</div>
	</td></tr>
	<tr><td align="center"><input type="button" onclick="doSubmit()" value="保 存" class="buttonClass"> &nbsp;&nbsp;
		<input type="button" onclick="doSubmit()" value="暂 存" class="buttonClass"></td></tr>
	</table>
	</form>
</body>
</html>
