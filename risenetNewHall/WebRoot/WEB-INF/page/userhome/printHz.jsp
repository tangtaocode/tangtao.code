<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="/css/login-box.css" rel="stylesheet" type="text/css" />
<link href="/css/UxStyle.css" rel="stylesheet" type="text/css" />
<link href="/css/main.css" type="text/css" rel="stylesheet" />
<link href="/css/initialize.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/zDialog/risenetDialog.js" type="text/javascript"></script>
<script src="/js/businessJS/onlineApply.js" type="text/javascript"></script>
<script src="/js/userService.js" type="text/javascript"></script>
<script type="text/javascript" src="/js/validation.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
</head>
<body>
<script language="javascript" src="/commons/LodopFuncs.js" charset="GBK"></script>
<object id="LODOP_OB" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=0 height=0> 
	<embed id="LODOP_EM" type="application/x-print-lodop" width=0 height=0 pluginspage="/commons/install_lodop.exe"></embed>
</object>
<table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list">
<tr> 
	<th colspan="4" style="text-align:center">网上预受理回执<div class="box_table" style="  float: right; margin-top:-10px; font-size:12px;cursor:pointer;" onclick="PreviewMytable()" >打印预览</div></th>
	
</tr>
			<tr>
  				<td width="15%" style="text-align:right">受理编号：</td>
      			<td width="35%">
      			<s:property value="approveinstance.declaresn"/>
      		</td>
      		<td width="15%" style="text-align:right">市统一编号：</td>
      			<td width="35%">&nbsp;
      			<s:property value="approveinstance.sjtybh"/>
      		</td>
           <tr>
  				<td width="18%" style="text-align:right">事项名称：</td>
      			<td colspan="3" width="82%">
      			<s:property value="approveinstance.formname"/>
      		</td>
  			</tr>
  			<tr>
  				<td width="18%" style="text-align:right">申请人/单位：</td>
      			<td colspan="3" width="82%">
      			<s:property value="#session.loginUser.companyUser.ename"/>
      			<s:property value="#session.loginUser.personUser.true_name"/>
      		</td>
  			</tr>
  			<tr>
      			<td style="text-align:right">联系电话：</td>
      			<td>
      			<s:property value="#session.loginUser.mobile"/>
      			</td>
      			<td style="text-align:right">申请时间：</td>
      			<td>
      			<s:date name="approveinstance.submittime" format="yyyy-MM-dd HH:mm:ss"/>
      			</td>
  			</tr>
  			
  			<s:set name="count_y" value="0"/>
      		<s:iterator value="approveinstance.fileList" status="ysc">
      			<s:if test="type==1">
      				<tr>
      					<s:if test="yscclcount/2==0">
      						<td style="border-bottom: 0px;text-align:right">已提交材料清单：</td>
      					</s:if>
      					<s:elseif test="#count_y==yscclcount/2">
	      					<td style="border-bottom: 0px;border-top: 0px;text-align:right">已提交材料清单：</td>
	      				</s:elseif>
		      			<s:elseif test="#count_y==0">
	      					<td style="border-bottom: 0px; ">&nbsp;</td>
	      				</s:elseif>
	      				<s:else>
	      					<td style="border-bottom: 0px;border-top: 0px; ">&nbsp;</td>
	      				</s:else>
	      				<td colspan="3">
	      					<ul>
	      						<s:set name="count_y" value="#count_y+1"/>
      							<li>（<s:property value="#ysc.index+1"/>）<s:property value="materialname"/> </li>
	      					</ul>
	      				</td>
      				</tr>
      			</s:if>
      		</s:iterator>
  			
  			
  			
  			<s:set name="count_w" value="0"/>
      		<s:iterator value="approveinstance.fileList" status="wsc">
      			<s:if test="type==0">
      				<tr>
      					<s:if test="wscclcount/2==0">
      						<td style="border-bottom: 0px;text-align:right">已提交材料清单：</td>
      					</s:if>
      					<s:elseif test="#count_w==wscclcount/2">
	      					<td style="border-bottom: 0px;border-top: 0px;text-align:right">未提交材料清单：</td>
	      				</s:elseif>
	      				<s:elseif test="#count_w==0">
	      					<td style="border-bottom: 0px; ">&nbsp;</td>
	      				</s:elseif>
	      				<s:else>
	      					<td style="border-bottom: 0px;border-top: 0px; ">&nbsp;</td>
	      				</s:else>
	      				<td colspan="3">
	      					<ul>
	      						<s:set name="count_w" value="#count_w+1"/>
	      						<li>（<s:property value="#wsc.index+1"/>）<s:property value="materialname"/> </li>
	      					</ul>
	      				</td>
      				</tr>
      			</s:if>
      		</s:iterator>
      		<s:if test="#count_w==0">
      			<li></li>
      		</s:if>		
		  </table>
<script language="javascript" type="text/javascript">
//document.get
var LODOP; //声明为全局变量
	function PreviewMytable(){//alert(document.frames["showform"].document.body.table.innerHTML);
	    var styles='<LINK href="/css/main.css" type=text/css rel=stylesheet><style type="text/css"> .box_table{display:none;} </style>';
		LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  		
		LODOP.PRINT_INIT("网上预受理回执单");
		LODOP.ADD_PRINT_TABLE(80,"6%","90%",450,styles+"<body>"+document.body.innerHTML+"</body>");
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		LODOP.ADD_PRINT_TEXT(1060,660,80,30,"第#页/共&页");	
		LODOP.SET_PRINT_STYLEA(0,"ItemType",2);
		LODOP.SET_PRINT_STYLEA(0,"Horient",1);
		LODOP.NewPageA();		
		LODOP.PREVIEW();
		
	}
</script>
</body>
</html>
