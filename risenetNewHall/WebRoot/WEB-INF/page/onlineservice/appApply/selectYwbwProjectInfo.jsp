<%@ page language="java"  pageEncoding="UTF-8"%>
<%@ page import="net.risesoft.beans.onlineservice.YwbwProjectInfo"%>
<%@page import="net.risesoft.utils.base.WebUtil"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>选择工程信息</title>  
    <link href="/css/main.css" type="text/css" rel="stylesheet" />
    <script src='<%= request.getContextPath() %>/dwr/engine.js'></script>
	<script src='<%= request.getContextPath() %>/dwr/util.js'></script>
	<script src="<%= request.getContextPath() %>/js/zDialog/zDialog.js"></script>
    <base target="_self">
    <script type="text/javascript">    	
    	
    	function selectProValue(obj){
    		if(obj==0){
    			alert('未找到工程信息');
    			return false;
    		}else{
	    		var ret = new Object();
	 			ret.prj_id = document.forms[0].prj_id.value;
	 			ret.prj_name = document.forms[0].prj_name.value; 
	 			ret.item_name = document.forms[0].item_name.value;
	 			ret.item_id = document.forms[0].item_id.value;
	 			ret.const_org = document.forms[0].const_org.value;
	 			ret.ins_dept = document.forms[0].ins_dept.value;
	 			ret.auct_resp = document.forms[0].auct_resp.value;
	 			ret.auct_handler = document.forms[0].auct_handler.value;
	 			//ret.prj_id = document.getElementById('proid'+obj).innerHTML;
		 		//ret.prj_name = document.getElementById('proname'+obj).innerHTML; 
		 		//ret.item_name = document.getElementById('ITEM_NAME'+obj).innerHTML; 	
		 		ret.auct_resp = document.getElementById('auct_resp').value;
		 		ret.auct_mobile = document.getElementById('auct_mobile').value;
		 		ret.sup_name = document.getElementById('sup_name').value;
		 		ret.design_name = document.getElementById('design_name').value;
		 		ret.prj_location = document.getElementById('prj_location').value;
		 		ret.fb_const_price = document.getElementById('fb_const_price').value;
		 		ret.exp_start_date = document.getElementById('exp_start_date').value.split(" ")[0];
		 		ret.exp_end_date = document.getElementById('exp_end_date').value.split(" ")[0];
		 		ret.fund_gov = document.getElementById('fund_gov').value;
		 		ret.fund_nat = document.getElementById('fund_nat').value;
		 		ret.fund_comm = document.getElementById('fund_comm').value;
		 		ret.funn_priv = document.getElementById('funn_priv').value;
		 		ret.fund_for = document.getElementById('fund_for').value;
		 		ret.fund_oth = document.getElementById('fund_oth').value;
		 		ret.prj_classlev = document.getElementById('prj_classlev').value;
		 		ret.bldg_up_type = document.getElementById('bldg_up_type').value;
		 		ret.bldg_base_type = document.getElementById('bldg_base_type').value;
		 		ret.mgr_name = document.getElementById('mgr_name').value;
		 		ret.mgr_reg_cert_no = document.getElementById('mgr_reg_cert_no').value;
		 		ret.mgr_card = document.getElementById('mgr_card').value;
		 		ret.mgr_mobile = document.getElementById('mgr_mobile').value;
	 			window.returnValue = ret;
	 			window.close();
 			}
    	}
    	
    	function doSumbit(){
    		document.forms[0].submit();
    	}
    	
    	//工程报建
    	function showGcbj(){    		
	    	var diag = new Dialog();		
			diag.Width = 450;
			diag.Height = 150;
			diag.Title = "温馨提示";
			var gcbjStr = '<br/><div style=\"align:center;font-size:16px;\">&nbsp;&nbsp;&nbsp;&nbsp;如果检索工程名称关键字或工程编号，本系统未能查找到工程相关信息的，可能是由于建设单位尚未进行项目信息确认和网上报建，故应先在我局交易系统内填报工程信息；同时将施工许可相关电子材料上传至本系统，该环节无需填写施工许可申请表。</div><br/>'+
				'<div><div style="font-size:12px;"><span style=\"color:red;\">【工程报建】</span>按钮，进行工程报建。<span style=\"color:red;\">【取消】</span>按钮，关闭提示窗口。<span style=\"color:red;\"></span></div>';				
			diag.InnerHtml = gcbjStr;
			diag.OkButtonText="工程报建";
			diag.OKEvent = function(){
				diag.close();	
				window.open('http://www.szjsjy.com.cn/CALogin.aspx');	
				window.close();	
			};		
			diag.CancelEvent = function(){
				diag.close();							
			}
			diag.show();    	
    	}
    </script> 
  </head> 
  <body>  	
  <form action="/onlineService/selectYwbwProjectInfo.html" accept-charset=utf-8 onsubmit="document.charset='utf-8'" method="post">
  <input type="hidden" name="method" value="search">
  <table width="100%" class="BS_list">
  	<tr>
  	<td>工程名称：<input type="text" name="projectname" value="<%=request.getAttribute("projectname")==null?"":request.getAttribute("projectname") %>"/>
  	&nbsp;&nbsp;工程编号：<input type="text" name="projectid" value="<%=request.getAttribute("projectid")==null?"":request.getAttribute("projectid") %>"/>    
    &nbsp;&nbsp;<input type="button" value="查询" onclick="doSumbit()"/>&nbsp;&nbsp;<input type="button" value="确定" onclick="selectProValue('<%if(request.getAttribute("YwbwProjectInfo")!=null){ %>1<%}else{ %>0<%} %>')"/></td>
    </tr>
  </table>
  <br>
  	<table border="1" cellpadding="0" cellspacing="0" width="100%" class="BS_list"> 
    <tr><th colspan="4">工程信息</th></tr>
    <% if(request.getAttribute("YwbwProjectInfo")!=null){
  		YwbwProjectInfo pro = (YwbwProjectInfo)request.getAttribute("YwbwProjectInfo");
  	%>
    <tr>  	   
    <td width="15%" nowrap align="ceneter">项目名称</td>
    <td><%=WebUtil.changeNull(pro.getItem_name()) %><input type="hidden" name="item_name" id="item_name" value="<%=WebUtil.changeNull(pro.getItem_name()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">项目编号</td>
    <td><%=WebUtil.changeNull(pro.getItem_id()) %><input type="hidden" name="item_id" id="item_id" value="<%=WebUtil.changeNull(pro.getItem_id()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">计划立项文号</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getDoc_id()) %><input type="hidden" name="doc_id" id="doc_id" value="<%=WebUtil.changeNull(pro.getDoc_id()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">规划许可证号</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getPrj_lic_id()) %><input type="hidden" name="prj_lic_id" id="prj_lic_id" value="<%=WebUtil.changeNull(pro.getPrj_lic_id()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">项目编号</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getPrj_id()) %><input type="hidden" name="prj_id" id="prj_id" value="<%=WebUtil.changeNull(pro.getPrj_id()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">工程名称</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getPrj_name()) %><input type="hidden" name="prj_name" id="prj_name" value="<%=WebUtil.changeNull(pro.getPrj_name()) %>"/>&nbsp;</td>   
    </tr>
     <tr>
    <td width="15%" nowrap align="ceneter">建设单位</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getConst_org()) %><input type="hidden" name="const_org" id="const_org" value="<%=WebUtil.changeNull(pro.getConst_org()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">主管部门</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getIns_dept()) %><input type="hidden" name="ins_dept" id="ins_dept" value="<%=WebUtil.changeNull(pro.getIns_dept()) %>"/>&nbsp;</td>   
    </tr>
     <tr>
    <td width="15%" nowrap align="ceneter">项目经理名称</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getMgr_name()) %><input type="hidden" name="mgr_name" id="mgr_name" value="<%=WebUtil.changeNull(pro.getMgr_name()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">身份证号</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getMgr_card()) %><input type="hidden" name="mgr_card" id="mgr_card" value="<%=WebUtil.changeNull(pro.getMgr_card()) %>"/>&nbsp;</td>   
    </tr>
     <tr>
    <td width="15%" nowrap align="ceneter">职业注册号</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getMgr_reg_cert_no()) %><input type="hidden" name="mgr_reg_cert_no" id="mgr_reg_cert_no" value="<%=WebUtil.changeNull(pro.getMgr_reg_cert_no()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">项目经理手机</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getMgr_mobile()) %><input type="hidden" name="mgr_mobile" id="mgr_mobile" value="<%=WebUtil.changeNull(pro.getMgr_mobile()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">招标人_负责人</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAuct_resp()) %><input type="hidden" name="auct_resp" id="auct_resp" value="<%=WebUtil.changeNull(pro.getAuct_resp()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">招标人_经办人</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAuct_handler()) %><input type="hidden" name="auct_handler" id="auct_handler" value="<%=WebUtil.changeNull(pro.getAuct_handler()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">招标人_办公电话</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAuct_tel()) %><input type="hidden" name="auct_tel" id="auct_tel" value="<%=WebUtil.changeNull(pro.getAuct_tel()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">招标人_移动电话</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAuct_mobile()) %><input type="hidden" name="auct_mobile" id="auct_mobile" value="<%=WebUtil.changeNull(pro.getAuct_mobile()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">招标代理机构</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAgent_name()) %><input type="hidden" name="agent_name" id="agent_name" value="<%=WebUtil.changeNull(pro.getAgent_name()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">代理机构_资质等级</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAgent_qual()) %><input type="hidden" name="agent_qual" id="agent_qual" value="<%=WebUtil.changeNull(pro.getAgent_qual()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">代理机构_资质证书号</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAgent_cert()) %><input type="hidden" name="agent_cert" id="agent_cert" value="<%=WebUtil.changeNull(pro.getAgent_cert()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">代理机构_负责人</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAgent_resp()) %><input type="hidden" name="agent_resp" id="agent_resp" value="<%=WebUtil.changeNull(pro.getAgent_resp()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">代理机构_经办人</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAgent_handler()) %><input type="hidden" name="agent_handler" id="agent_handler" value="<%=WebUtil.changeNull(pro.getAgent_handler()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">代理机构_办公电话</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAgent_tel()) %><input type="hidden" name="agent_tel" id="agent_tel" value="<%=WebUtil.changeNull(pro.getAgent_tel()) %>"/>&nbsp;</td>   
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">代理机构_移动电话</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getAgent_moblie()) %><input type="hidden" name="agent_moblie" id="agent_moblie" value="<%=WebUtil.changeNull(pro.getAgent_moblie()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">招标部分估价</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getPart_val()) %><input type="hidden" name="part_val" id="part_val" value="<%=WebUtil.changeNull(pro.getPart_val()) %>"/>（万元）</td>   
    </tr>  
    <tr>
    <td width="15%" nowrap align="ceneter">勘察单位名称</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getProsp_name()) %><input type="hidden" name="prosp_name" id="prosp_name" value="<%=WebUtil.changeNull(pro.getProsp_name()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">设计单位名称</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getDesign_name()) %><input type="hidden" name="design_name" id="design_name" value="<%=WebUtil.changeNull(pro.getDesign_name()) %>"/></td>   
    </tr> 
    <tr>
    <td width="15%" nowrap align="ceneter">监理单位编号</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getSup_id()) %><input type="hidden" name="sup_id" id="sup_id" value="<%=WebUtil.changeNull(pro.getSup_id()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">监理单位名称</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getSup_name()) %><input type="hidden" name="sup_name" id="sup_name" value="<%=WebUtil.changeNull(pro.getSup_name()) %>"/></td>   
    </tr>
    <tr>
    	<td>工程地址</td>
    	<td colspan="3"><%=WebUtil.changeNull(pro.getPrj_location()) %><input type="hidden" name="prj_location" id="prj_location" value="<%=WebUtil.changeNull(pro.getPrj_location()) %>"> </td>
    </tr>
    <tr>
    <td width="15%" nowrap align="ceneter">合同开始日期</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getExp_start_date()) %><input type="hidden" name="exp_start_date" id="exp_start_date" value="<%=WebUtil.changeNull(pro.getExp_start_date()) %>"/>&nbsp;</td>
    <td width="15%" nowrap align="ceneter">合同结束日期</td>
    <td width="35%"><%=WebUtil.changeNull(pro.getExp_end_date()) %>&nbsp;<input type="hidden" name="exp_end_date" id="exp_end_date" value="<%=WebUtil.changeNull(pro.getExp_end_date()) %>"/></td>   
    </tr>
    <tr>
    	<td>合同造价</td>
    	<td colspan="3">
    		<%=WebUtil.changeNull(pro.getFb_const_price()) %><input type="hidden" name="fb_const_price" id="fb_const_price" value="<%=WebUtil.changeNull(pro.getFb_const_price()) %>"> 
		    
		    <input type="hidden" name="fund_gov" id="fund_gov" value="<%=pro.getFund_gov()%>">
		    <input type="hidden" name="fund_nat" id="fund_nat" value="<%=pro.getFund_nat()%>">
		    <input type="hidden" name="fund_comm" id="fund_comm" value="<%=pro.getFund_comm()%>">
		    <input type="hidden" name="funn_priv" id="funn_priv" value="<%=pro.getFunn_priv()%>">
		    <input type="hidden" name="fund_for" id="fund_for" value="<%=pro.getFund_for()%>">
		    <input type="hidden" name="fund_oth" id="fund_oth" value="<%=pro.getFund_oth()%>">
		    <input type="hidden" name="prj_classlev" id="prj_classlev" value="<%=WebUtil.changeNull(pro.getPrj_classlev())%>">
		    <input type="hidden" name="bldg_up_type" id="bldg_up_type" value="<%=WebUtil.changeNull(pro.getBldg_up_type())%>">
		    <input type="hidden" name="bldg_base_type" id="bldg_base_type" value="<%=WebUtil.changeNull(pro.getBldg_base_type())%>">
    	</td>
    </tr>  
    <%}else{ %>
    <tr><td colspan="4" align="center">根据工程编号或工程名称，交易系统内未找到相关工程信息，如未在交易系统内报建工程信息，请先报建工程信息。【<a href="http://www.szjsjy.com.cn/CALogin.aspx">工程报建</a>】</td></tr>   
    <%} %>
    </table>
    </form>
  </body>
</html>
<%if(request.getAttribute("YwbwProjectInfo")==null && (request.getAttribute("projectname")!=null || request.getAttribute("projectid")!=null)){ %>
	<script type="text/javascript">    	
    	showGcbj();   
    </script>
<%} %>
