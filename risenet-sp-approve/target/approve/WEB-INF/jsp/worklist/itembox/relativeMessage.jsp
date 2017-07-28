<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tld/risefile-html.tld" prefix="risefilehtml" %>
<%@ include file="/static/common/taglib.jsp"%>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/jquery.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/language/cn.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<link href="${ctx}/static/QUI/libs/css/import_basic.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" type="text/css" id="skin" prePath="${ctx}/static/QUI/" scrollerY="false"/>
<link rel="stylesheet" type="text/css" id="customSkin"/>
<!--框架必需end-->
<!--数据表格start-->
<script src="${ctx}/static/QUI/libs/js/table/quiGrid.js" type="text/javascript"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 日期控件start -->
<script type="text/javascript" src="${ctx}/static/js/My97DatePicker/WdatePicker.js"></script>
<!-- 日期控件end -->
<!--树形表格start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--树形表格end-->
<!--弹窗组件start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/drag.js"></script>
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/dialog.js"></script>
<!--弹窗组件end-->
<!--弹出式提示框start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/popup/messager.js"></script>
<!--弹出式提示框end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!--箭头分页start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/nav/pageArrow.js"></script>
<!--箭头分页end-->
<!--箭头分页start-->

<script type="text/javascript" src="${ctx}/static/QUI/libs/js/table/detailTable.js"></script>
<!--箭头分页end-->

<title>相关信息</title>
</head>
<body>
<fieldset>
<legend>相关信息</legend>
<table class="detailTable" style="width:100%" trClick="true">
    <tr>
        <th width="10%">操作</th>
        <th width="90%" style="text-align:left" >数据来源</th>
    </tr>
    <tr>
        <td style="text-align:center"><span class="img_add2 hand" title="点击展开"></span></td><td >织网工程</td>
    </tr>
    <tr >
    	<td></td>
   		 <td>
    		<table class="tableStyle" useHover="true" useClick="true">
           		<tr>
                  <td style="width:10%" >姓名：</td>
                  <c:if test="${type=='1' }">
                 		<td style="width:25%" >${zwgcMap.CARD_NAME}</td>
                 	</c:if>
                 	<c:if test="${type=='2' }">
                 		<td style="width:25%" >${zwgcMap.LEGAL_PERSON}</td>
                 	</c:if>
                 	<c:if test="${type==null }">
                 		<td style="width:25%" >${zwgcMap.LEGAL_PERSON==null?zwgcMap.CARD_NAME:zwgcMap.LEGAL_PERSON}</td>
                 	</c:if>
                  <td style="width:10%" >性别：</td>
                   <td style="width:30%" >${zwgcMap.GENDER_NAME }</td>
                  <td style="width:10%" >民族：</td>
                   <td style="width:15%" >${zwgcMap.NATIONALITY_NAME }</td>
              </tr>
              <tr>
                  <td>出生日期：</td>
                  <td>${zwgcMap.BIRTH_DATE }</td>
                  <td>证件号码：</td>
                  <td>${zwgcMap.CARD_NO }</td>
                  <td>证件类型：</td>
                  <td>${zwgcMap.CARD_TYPE_NAME }</td>
              </tr>
              <tr>
                  <td>出生地：</td>
                   <td>${zwgcMap.NATIVE_PLACE }</td>
                  <td>现居住地：</td>
                   <td>${zwgcMap.NATIVE_ADDR }</td>
                  <td>户口类型：</td>
                   <td>${zwgcMap.HUKOU_TYPE_NAME }</td>
              </tr>
              <tr>
              	<td>公司名称：</td>
              	<c:if test="${type=='1' }">
              		 <td>${zwgcMap.EMPLOY_ORGAN }</td>
              	</c:if>
              	<c:if test="${type=='2' }">
              		 <td>${zwgcMap.ORG_NAME_CN }</td>
              	</c:if>
              	<c:if test="${type==null }">
                 		<td style="width:26%" >${zwgcMap.ORG_NAME_CN==null?zwgcMap.EMPLOY_ORGAN:zwgcMap.ORG_NAME_CN}</td>
                 	</c:if>
                  <td>注册地址：</td>
                   <td>${zwgcMap.REGI_ADDR }</td>
                  <td>创建日期：</td>
                   <td>${zwgcMap.FOUND_DATE }</td>
              </tr>
              <tr>
              	<td>住宅电话：</td>
              	<td>${zwgcMap.HOME_TEL }</td>
                  <td>联系电话：</td>
                  <td>${zwgcMap.TEL }</td>
                  <td>联系手机：</td>
                  <td>${zwgcMap.MOBILE }</td>
              </tr>
             </table>
     	</td>
    </tr>
    
     <tr>
        <td style="text-align:center"><span class="img_add2 hand" title="点击展开"></span></td><td>证照库</td>
    </tr>
  
    <tr>
        <td></td>
        <td>
            <table class="tableStyle" useHover="false" useClick="false">
                 <tr>
                    <td style="width:10%" >证照主体：</td>
                    <td style="width:20%" >${zzkMap.DOCOWNER }</td>
                    <td style="width:10%" >证照编号：</td>
                     <td style="width:30%" >${zzkMap.DOCNO }</td>
                    <td style="width:10%" >证照类型：</td>
                     <td style="width:20%" >
                     	<c:if test="${zzkMap.ZZLX=='1' }">许可证</c:if>
                    	<c:if test="${zzkMap.ZZLX=='2' }">营业证</c:if>
                    	<c:if test="${zzkMap.ZZLX=='3' }">营业执照</c:if>
                    	<c:if test="${zzkMap.ZZLX=='4' }">税务登记证</c:if>
                    	<c:if test="${zzkMap.ZZLX=='5' }">资格证</c:if>
                    	<c:if test="${zzkMap.ZZLX=='6' }">批准文件</c:if>
                     </td>
                </tr>
                <tr>
                    <td>证件号码：</td>
                    <td>${zzkMap.ORGANCODE }</td>
                    <td>证照名称：</td>
                    <td>${zzkMap.DOCNAME }</td>
                    <td>有效期：</td>
                    <td>${zzkMap.VALIDITYPERIOD }</td>
                </tr>
                <tr>
                 	<td>发证日期：</td>
                    <td>${zzkMap.PRODUCEDATE }</td>
                    <td>注册地址：</td>
                    <td>${zzkMap.ZCADDR }</td>
                    <td>经营地址：</td>
                    <td>${zzkMap.JYADDR }</td>
                </tr>
            </table>
        </td>
    </tr> 
    
     <tr>
        <td style="text-align:center"><span class="img_add2 hand" title="点击展开"></span></td><td>资料库</td>
    </tr>
  
    <tr>
        <td></td>
        <td>
            <table class="tableStyle" useHover="false" useClick="false" style="width:100%">
            	<tr>
                    <td style="width:9%" >主体类型</td>
                    <td style="width:10%" >证件号码</td>
                    <td style="width:22%" >材料名称</td>
                    <td style="width:9%" >材料状态</td>
                    <td style="width:13%" >有效期开始日期</td>
                    <td style="width:13%" >有效期结束日期</td>
                 	<td style="width:15%" >认证人</td>
                    <td style="width:9%" >认证时间</td>
                </tr>
	            <c:forEach  items="${zlkMap}" var ="zlkMap">
	            	<tr>
	                    <td >
		                    <c:if test="${zlkMap.OWNERTYPE=='1' }">个人</c:if>
	                    	<c:if test="${zlkMap.OWNERTYPE=='2' }">企业</c:if>
	                    	<c:if test="${zlkMap.OWNERTYPE=='3' }">机关事业单位</c:if>
                    	</td>
	                    <td >${zlkMap.OWNERGUID }</td>
	                    <td >${ zlkMap.STUFFDATANAME}</td>
	                    <td >                    	
		                    <c:if test="${zlkMap.STATE=='1' }">待认证</c:if>
	                    	<c:if test="${zlkMap.STATE=='2' }">认证有效</c:if>
	                    	<c:if test="${zlkMap.STATE=='3' }">无效</c:if>
                    	</td>
	                    <td >${zlkMap.LIMITBEGIN }</td>
	                    <td >${zlkMap.LIMITEND }</td>
	                 	<td >${zlkMap.CERTIFYPERSON }</td>
	                    <td >${zlkMap.CERTIFYTIME }</td>
              		</tr>
	            </c:forEach>
            </table>
        </td>
    </tr> 
    
    <tr>
        <td style="text-align:center"><span class="img_add2 hand" title="点击展开"></span></td><td>注册信息</td>
    </tr>
  
    <tr>
        <td></td>
        <td>
            <table class="tableStyle" useHover="false" useClick="false">
                 <c:if test="${zcxxMap.userType=='1' }">
	                 <tr>
	                    <td style="width:10%" >姓名：</td>
	                    <td style="width:10%" >${zcxxMap.true_name }</td>
	                    <td style="width:10%" >性别：</td>
	                     <td style="width:10%" >${zcxxMap.sex }</td>
	                    <td style="width:10%" >年龄：</td>
	                     <td style="width:15%" >${ zcxxMap.age}</td>
	                     <td style="width:11%" >现居住地址：</td>
	                     <td style="width:24%" >${ zcxxMap.native_add}</td>
	                </tr>
	                <tr>
	                    <td>民族：</td>
	                    <td>${zcxxMap.nation }</td>
	                    <td>职业：</td>
	                    <td>${zcxxMap.metier }</td>
	                    <td>出生日期：</td>
	                    <td>${zcxxMap.birth_date }</td>
	                    <td>政治面貌：</td>
	                    <td>${zcxxMap.polity }</td>
	                </tr>
	                <tr>
	                    <td>婚姻状况：</td>
	                    <td>${zcxxMap.ifmarry }</td>
	                    <td>证件类型：</td>
	                    <td>
	                    	<c:if test="${zcxxMap.idcard_type=='1' }">身份证号</c:if>
	                    	<c:if test="${zcxxMap.idcard_type=='2' }">护照号码</c:if>
	                    	<c:if test="${zcxxMap.idcard_type=='3' }">组织机构代码</c:if>
	                    </td>
	                    <td>证件号码：</td>
	                    <td>${zcxxMap.idcard_code }</td>
	                    <td>毕业学校：</td>
	                    <td>${zcxxMap.grad_chool }</td>
	                </tr>
	                <tr>
	                    <td>毕业时间：</td>
	                    <td>${zcxxMap.grad_date }</td>
	                    <td>学历：</td>
	                    <td>${zcxxMap.schoolage }</td>
	                    <td>户口所在地：</td>
	                    <td>${zcxxMap.reg_add }</td>
	                    <td>籍贯：</td>
	                    <td>${zcxxMap.home_add }</td>
	                </tr>
	                <tr>
	                 	<td>手机号码：</td>
	                    <td>${zcxxMap.mobile }</td>
	                    <td>邮编：</td>
	                    <td>${zcxxMap.postcode }</td>
	                    <td>邮箱：</td>
	                    <td>${zcxxMap.email }</td>
	                    <td>工作单位：</td>
	                    <td>${zcxxMap.work_company }</td>
	                </tr>
	                <tr>
	                    <td>职称：</td>
	                    <td>${zcxxMap.work_title }</td>
	                    <td>工作地址：</td>
	                    <td colspan="5">${zcxxMap.work_add }</td>
	                </tr>
	              </c:if>
	              <c:if test="${zcxxMap.userType!='1' }">
	              	<tr>
	                    <td style="width:11%" >单位名称：</td>
	                    <td style="width:8%" >${zcxxMap.ename }</td>
	                    <td style="width:10%" >企业性质：</td>
	                     <td style="width:10%" >${zcxxMap.kind}
							<%-- <c:if test="${zcxxMap.kind=='1' }">身份证号</c:if>
	                    	<c:if test="${zcxxMap.kind=='2' }">护照号码</c:if>
	                    	<c:if test="${zcxxMap.kind=='3' }">组织机构代码</c:if>
							<c:if test="${zcxxMap.kind=='1' }">身份证号</c:if>
	                    	<c:if test="${zcxxMap.kind=='2' }">护照号码</c:if>
	                    	<c:if test="${zcxxMap.kind=='3' }">组织机构代码</c:if>
							<c:if test="${zcxxMap.kind=='1' }">身份证号</c:if>
	                    	<c:if test="${zcxxMap.kind=='2' }">护照号码</c:if>
	                    	<c:if test="${zcxxMap.kind=='3' }">组织机构代码</c:if> --%>
						 </td>
	                    <td style="width:10%" >组织机构代码：</td>
	                     <td style="width:14%" >${ zcxxMap.orgcode}</td>
	                     <td style="width:9%" >注册地址：</td>
	                     <td style="width:30%" >${ zcxxMap.address}</td>
	                </tr>
	                <tr>
	                    <td>注册号：</td>
	                    <td>${zcxxMap.regcode }</td>
	                    <td>法定代表人 ：</td>
	                    <td>${zcxxMap.lawperson }</td>
	                    <td>成立时间：</td>
	                    <td>${zcxxMap.regdate }</td>
	                    <td>注册资金：</td>
	                    <td>${zcxxMap.regmoney }</td>
	                </tr>
	                <tr>
	                    <td>经营范围：</td>
	                    <td>${zcxxMap.limit }</td>
	                    <td>联系人：</td>
	                    <td>${zcxxMap.truename }</td>
	                    <td>联系人身份证：</td>
	                    <td>${zcxxMap.personcardid }</td>
	                    <td>联系人电话：</td>
	                    <td>${zcxxMap.contactphone }</td>
	                </tr>
	                <tr>
	                    <td>联系人手机号码：</td>
	                    <td>${zcxxMap.contactmobile }</td>
						 <td>联系人邮箱：</td>
	                    <td>${zcxxMap.email }</td>						
	                    <td>传真：</td>
	                    <td>${zcxxMap.industry }</td>
	                    <td>邮编：</td>
	                    <td>${zcxxMap.postcode }</td>	                   
	                </tr>
	                <tr>
						<td>公司人数：</td>
	                    <td>${zcxxMap.totalpeop }</td>
	                 	<td>营业地址：</td>
	                    <td>${zcxxMap.openadd }</td>
	                    <td>公司简介：</td>
	                    <td colspan="3">${zcxxMap.companyabout }</td>                  
	                </tr>

	              </c:if>
            </table>
        </td>
    </tr>
</table>
</fieldset>
<fieldset>
<legend>相关文件</legend>
<div>
<risefilehtml:risefilebox
appname="relative" keepminimalversion="true" appinstguid="${SPinstanceId }" 
fileboxname="TANGER_OCX" subappname="fujian" height="200"
viewtype="1" savemode="true" isfilterextends="true"
majorversion="6" width="600" ishandleextends="true">
</risefilehtml:risefilebox>
<script language="javascript">isArchManager = "true";showVersion("TANGER_OCX");	</script>
</div>
</fieldset>

</body>
</html>