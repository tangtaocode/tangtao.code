<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ include file="../share/taglib.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

</head>
<body>
<s:form  method="post" id="queryFormId">
<table border="0" align="center" cellpadding="0" cellspacing="0" class="BS_list" style="width:100%;">
              <tr>
                <td style="text-align:right;width:15%">ҵ���ţ�</td>
                <td><s:property value="businessInfo.yxtywlsh"/> &nbsp;
                </td>
                <td style="text-align:right;width:15%">֤����ţ�</td>
                <td><s:property value="businessInfo.cardId"/> &nbsp;
                  </td>
              </tr>
              <tr>
                <td style="text-align:right;width:15%">�������ƣ�</td>
                <td><s:property value="businessInfo.spsxmc"/> &nbsp;</td>
              <td style="text-align:right;width:15%">���뵥λ/�ˣ�</td>
                <td><s:property value="businessInfo.sqdwhsqrxm"/>&nbsp;</td>
              </tr>
              <tr>
               
                <td style="text-align:right;width:15%">�а쵥λ��</td>
                <td><s:property value="businessInfo.sljgmc"/> &nbsp;</td>
                  <td style="text-align:right;width:15%">��&nbsp;��&nbsp;�ˣ�</td>
                <td><s:property value="businessInfo.employeedeptname"/>&nbsp;</td> 
              </tr>
              <tr>
               <td style="text-align:right;width:15%">����ʱ�䣺</td>
                <td><s:property value="businessInfo.slsj"/> &nbsp;</td>
              <td style="text-align:right;width:15%">����״̬��</td>
                <td><s:property value="businessInfo.blzt"/>&nbsp; 
               </td>
              </tr>
          </table>
<s:if test="upFileList!=null&&upFileList.size>0">
<div style="padding-top:10px;">
<jsp:include page="/WEB-INF/page/correction/upFilePage.jsp"></jsp:include>
</div>
</s:if>
     	
			</s:form>
			</body>
</html>

			