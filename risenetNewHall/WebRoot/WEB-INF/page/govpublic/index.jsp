<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/page/share/taglib.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>广东省网上办事大厅深圳市龙岗区住房与建设局-政务公开</title>
<meta name="keywords" content="深圳市住房与建设局网上办事大厅-政务公开,深圳市住房与建设局政务公开,政务公开,深圳市住房与建设局"></meta>
<meta name="description" content="广东省网上办事大厅深圳市住房与建设局窗口-政务公开首页"></meta>
<meta name="robots" content="all" />
<link href="/css/portal.css" type="text/css" rel="stylesheet" />
<link href="/css/appPub.css" type="text/css" rel="stylesheet" />
<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
<script src="/js/businessJS/govpublic.js" type="text/javascript"></script>
</head>
<body style="background-color:#fff;background-image:url('/images/govpublic/background.jpg');background-position:center top;background-repeat:repeat-y; background-attachment:fixed;">
<table width="1000" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="1000" height="170" align="center" valign="top" background="images/topbanner2.jpg">
		<jsp:include page="/WEB-INF/page/public/topMenu.jsp">
    <jsp:param name="menu" value="1"/>
    </jsp:include>
	</td>
  </tr>

  <!--页面版区开始 -->
  <tr>
    <td align="center" valign="top" height="8"><img src="/images/govpublic/xshadow_A.png" width="1000" height="8" /></td>
  </tr>
    <tr>
    <td align="center" valign="top" background="/images/govpublic/xshadow_C.png">
    <div id="Portal_Content">
      <table width="980" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="left" valign="top">&nbsp;</td>
          <td align="center" valign="middle" >&nbsp;</td>
        </tr>
        <tr>
          <td width="320" align="left" valign="top"><table width="306" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td align="center" valign="top" height="20"></td>
            </tr>
            <tr>
              <td align="center" valign="top"><a target="_blank" href="http://www.szjs.gov.cn/xxgk/detail.aspx?type=gkzn"><img src="/images/govpublic/leftbutten1_1.jpg" alt="" width="306" height="52" border="0" /></a></td>
            </tr>
            <tr>
              <td align="center" valign="top" height="20"></td>
            </tr>
            <tr>
              <td align="center" valign="top"><a target="_blank" href="http://www.szjs.gov.cn/xxgk/xxgkml.aspx"><img src="/images/govpublic/leftbutten1_2.jpg" alt="" width="306" height="52" border="0" /></a></td>
            </tr>
            <tr>
              <td align="center" valign="top" height="20"></td>
            </tr>
            <tr>
              <td align="center" valign="top"><a target="_blank" href="http://www.szjs.gov.cn/xxgk/xxgk_list.aspx?nav1=其他&nav2=政府信息公开年度工作报告&node=ChildMenu8&id=ndxxgkbg&pagesize=15"><img src="/images/govpublic/leftbutten1_3.jpg" alt="" width="306" height="52" border="0" /></a></td>
            </tr>
            <tr>
              <td align="center" valign="top" height="20"></td>
            </tr>
            <tr>
              <td align="center" valign="top"><a target="_blank" href="http://203.91.46.18/gk/LoginServlet?type=ysqgk"><img src="/images/govpublic/leftbutten1_4.jpg" alt="" width="306" height="52" border="0" /></a></td>
            </tr>
            <tr>
              <td align="center" valign="top" height="20"></td>
            </tr>
          </table>
          </td>
          <td width="689" height="300" align="right" valign="middle" style="background-position:left; background-repeat:no-repeat;" background="/images/govpublic/grayline2.png">
			<div id="deptList" style="width: 650px;">
				<table width="630px" cellpadding="0px" cellspacing="0px" border="0px">
					<tr>
						<td background="/images/govpublic/new_index620.jpg" height="44px" style="background-repeat: no-repeat; font-size: 16px; padding-left: 12px">部门信息公开 </td>
					</tr>
					<tr>
						<td style="border: 1px solid #CCC; border-top: 0px solid #CCC">
						<table width="98%" cellpadding="0px" cellspacing="0px" border="0px" style="line-height:38px;">
					<tr>
					<tr>
					<s:iterator value="bureauList" status="item">
					<s:if test="status==0">
						<td valign="top" width="320px">
							<div style="white-space:normal;word-break:break-all;width:310px;padding-left:10px">
								<a href="javascript:searchAppList('<s:property value="code"/>','','<s:property value="value"/>');">●&nbsp;<s:property value="value"/></a>
							</div>
						</td>
						<s:if test="(#item.index+1)%2==0"></tr>
							<s:if test="(#item.index+1)%4==2"><tr style="background:#F1F6FE"></s:if>
						<s:else><tr></s:else>
						</s:if>
					</s:if>
					</s:iterator>					
					</tr>
					</table>
					</td>
					</tr>
				</table>
			</div>
          </td>
        </tr>

        <tr>
          <td align="left" valign="top">&nbsp;</td>
          <td align="center" valign="middle" >&nbsp;</td>
        </tr>
      </table>
      </div>
      </td>
  </tr>
  <tr>
    <td align="center" valign="top" height="20"><img src="/images/govpublic/xshadow_B.png" width="1000" height="8" /></td>
  </tr>
  <!--页面版区结束 -->

  <!--页尾区开始 -->
  <tr>
    <td align="center" valign="top" >
      <jsp:include page="/WEB-INF/page/public/bottom.jsp"></jsp:include> </td>
  </tr>

  <!--页尾区结束 -->
</table>
</body>
</html>
