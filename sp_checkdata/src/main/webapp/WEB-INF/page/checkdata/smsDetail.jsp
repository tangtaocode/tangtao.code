<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>详细页面</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<!--数据表格end-->
<!--表单异步提交start-->
<script src="${ctx}/static/QUI/libs/js/form/form.js" type="text/javascript"></script>
<!--表单异步提交end-->
<!-- 表单验证start -->
<script src="${ctx}/static/QUI/libs/js/form/validationRule.js" type="text/javascript"></script>
<script src="${ctx}/static/QUI/libs/js/form/validation.js" type="text/javascript"></script>
<!-- 表单验证end -->
</head>
<body>
<div class="box1" panelWidth="600">
<form method="post" action="/Sms/updateSms" id="smsform">
    <table class="tableStyle" formMode="line">

        <tr><th colspan="2">详细信息</th></tr>

        <tr><td>姓名：</td><td><input type="text" name="name" id="name" value="${smsdetail.name}" class="validate[required,length[0,20]]"/>
        <input type="hidden" name="guid" id="guid" value="${smsdetail.guid}" />
        </td></tr>

        <tr><td>手机号码：</td><td><input type="text" name="mobile_phone" id="mobile_phone" value="${smsdetail.mobile_phone}" class="validate[required,custom[mobilephone]]"/></td></tr>    

        <tr><td>所在部门：</td><td><input type="text" name="deptname" id="deptname" value="${smsdetail.deptname}"/></td></tr>    

        <tr><td colspan="2"><input type="submit" value="提交"/>&nbsp;<input type="reset" value="重置"/></td></tr>

    </table>

</form>

</div>

<!--表单-->
<script type="text/javascript">
function initComplete(){
    //表单异步提交处理
   $('#smsform').submit(function(){ 
   //判断表单的客户端验证时候通过
       var valid = $('#smsform').validationEngine({returnIsValid: true});
       if(valid){
          $(this).ajaxSubmit({
               //表单提交成功后的回调
               success: function(responseText, statusText, xhr, $form){
            	   var json = eval("("+responseText+")");
            	   if(json.outcome=="1"){
            		   Dialog.alert("保存成功",function(){
            			   parent.searchHandler();
            			   parent.Dialog.close();
                         });
            		   
            	   }else{
            		   Dialog.alert("保存失败",function(){
                         }); 
            	   }
               }
           }); 
        }
       //阻止表单默认提交事件
       return false; 
   });
}

</script>
</body>
</html>