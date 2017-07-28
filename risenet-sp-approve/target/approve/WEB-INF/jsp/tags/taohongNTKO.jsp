<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/static/common/head.jsp"%>
<title>套红模版选择</title>
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/layout/layout-default-latest.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jtable/themes/lightcolor/blue/jtable.css" />

<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/mstyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/listyle.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/form.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/risesoft/themes/blue/css/style.css" />
<link href="${ctx }/static/jquery/jui/extends/timepicker/jquery-ui-timepicker-addon.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layout/jquery.layout-latest.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layout/jquery.layout.slideOffscreen.min-1.1.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/databind/DataBind.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/tools/jquery.json-2.4.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/editable/jquery.editable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jtable/jquery.jtable.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/layer/extend/layer.ext.js"></script>
<script language="JavaScript">
	
	$(document).ready(function(){
		buildTaohongTemplate();
		//setValue();
		//var divtemp="<div align=\"center\"><label>没有找到可用的套红模板</label></div>";
		//$(".ui-layout-content").prepend(divtemp);
	})
	function buildTaohongTemplate(){
		$.getJSON("${ctx}/ntko/list", 
			{ 
			currentBureauGuid: "${currentBureauGuid}"
			}, 
			function(data){
			var divtemp="<div align=\"center\"><label>没有找到可用的套红模板</label></div>";
			var trtemp="";
				$.each(data,function(i,item){
						if(item.hasTaoHongTemplate==0){
							$(".ui-layout-content").prepend(divtemp);
						}else{
							trtemp=trtemp+"<tr><td class=\"li2_td_left\" style=\"width: 140px;\"><input type=\"radio\" name=\"taohong\" onclick=\"setValue(this)\" value=\""+item.template_guid+"|"+item.template_fileName+"|"+item.template_type+" \"> "+item.template_fileName+"  </td></tr>"
						}
				});
				$(".simple").prepend(trtemp);
		    }
		);
	}
	function setValue(obj){
		var o = new Object();
		var v = obj.value.split("|");
		o.guid = v[0];
		o.caption = v[1];
		o.type = v[2];
		window.returnValue = o;
		window.close();
	}

</script>
</head>
<body>
<div class="ui-layout-center">
    <div>
      <table width="100%" border="0" cellpadding="0" cellspacing="0" class="er_title">
        <tr>
          <td width="1%" style="padding: 0px;"><img src="${ctx}/static/risesoft/themes/blue/images/mimages/er_title_icon2.gif" width="2" height="27" /></td>
          <td class="er_title_font" id="title1">可选套红模板</td>
          <td></td>
        </tr>
      </table>
    </div>
    <div style="overflow: auto; width: 100%" class="ui-layout-content" id="ui-layout-content">
      <table class="simple" width="430px" border="0" align="center">
      </table>
    </div>
  </div>
</body>
</html>