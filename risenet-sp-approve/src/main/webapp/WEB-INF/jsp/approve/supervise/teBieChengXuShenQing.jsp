<%@ page language="java" pageEncoding="utf-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<%@ include file="/static/common/head.jsp"%>
<%@ include file="/static/common/common.jsp"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Content-Language" content="utf-8"/>
		<meta http-equiv="pragma" content="no-cache">   
   	 	<meta http-equiv="cache-control" content="no-cache">
    	<meta http-equiv="expires" content="0">
    	<script type="text/javascript" src="${ctx}/static/risesoft/js/tbcx/tbcxsq.js"></script>
		<title>特别程序申请</title>
		
		<script type="text/javascript">
		var apptype='${map.sxlx}';
		var tbcxzl = '${map.tbcxzl}';
		var tbcxsx = '${map.legaltimelimit}';
		var data,json;
		data = [];
		$(function(){
			//loadTbcx();
			if(apptype=="行政许可"||apptype=="非行政许可"){
					document.getElementById("tbcxsx").value=10;
					document.getElementById("tbcxsx").readOnly=true;
			}else if(apptype=="行政服务"){
					document.getElementById("tbcxsx").value=5;
					document.getElementById("tbcxsx").readOnly=true;
			}
			/*  $('#tbcxzl').combobox({
				 onSelect: function(n,o){
				 	checktype(n);
				 }
	         }); */
	     	
		})
		</script>
	</head>
		
	<body>
		<div class="easyui-layout" data-options="fit:true,border:false">
			<div data-options="region:'center',border:false">
				<form id="teBieChengXuShenQing" name="teBieChengXuShenQing" method="post"  action="${ctx}/sp/teBieChengXuShenQing/saveOrModify">
				<input type=hidden name=sjbbh value="${map.sjbbh}">
				<input type=hidden name="instanceId" value="${instanceId}">
				<input type=hidden name=eeguids id=eeguids>	
				<table border="0" cellpadding="0" cellspacing="1" class="table">
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">事项类型：</td>
							<td class="rigthtd" style="width: 75%" colspan="3" >
								<input id="sxlx" name="sxlx" type="text" value="${map.sxlx}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序开始日期：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<input id="tbcxk" name="tbcxk" type="text" value="${map.tbcxksrq}" readonly="readonly"/>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序启动理由或依据<font color=red>*</font>：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="tbcxqdlyhyj" name="tbcxqdlyhyj" style="width: 97%;height:110px" required="true"></textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序种类：</td>
							<td class="rigthtd" nowrap colspan="3">
								<select id="tbcxzl" name="tbcxzl" class="easyui-combobox" style="width: 20%"  
								data-options="required:'true',validType:'SelectValidate',panelHeight:'auto',
								onSelect:function(rec){checktype(rec.value)}">
									<option value=""></option>
									<option value="B" ${!(map.tbcxzl=='')?"selected":""}>B类除延长审批之外</option>
									<option value="A" ${!(map.tbcxzl=='')?"selected":""}>A类延长审批</option>
								</select><br>
								<font style="color:red">说明：A类延长审批（需要部门领导批示，延长不能超过10个工作日，多部门联合统一办理的，<br>需本级政府负责人批准，延长不能超过15个工作日）;
									<br>B类除延长审批之外（专家鉴定、专业评审、听证、招标、拍卖等法律法规规定的情况，<br>延长时间不得超过法定时间）。
								</font>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序种类名称<font color=red>*</font>：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<input id="P_TBCXZLMC" name="tbcxzlmc" style="width: 20%" class="easyui-combobox" data-options="required:'true',panelHeight:'200px',valueField:'id',textField:'text'" />
								<font style="color:red"><br>说明：填写上栏列举延长审批、专家鉴定等，不再上列范围时可自行扩展，自行扩展的都按照<br>B类处理。</font>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">申请内容<font color=red>*</font>：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="sqnr" name="sqnr"style="width: 97%;height:110px" required="true"></textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序时限<font color=red>*</font>：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<input id="tbcxsx" name="tbcxsx" type="text" value="" disabled/>
								<font style="color:red"><br>说明：只能为数字。</font>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">特别程序时限单位<font color=red>*</font>：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<input id="P_TBCXSXDW" name="tbcxsxdw" type="radio" required="true" value="G" checked>工作日（不包含法定节假日）<br>
								<input id="P_TBCXSXDW" name="tbcxsxdw" type="radio" required="true" value="Z">自然日
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">备注：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
								<textarea id="bz" name="bz" style="width: 97%;height:110px" ></textarea>
							</td>
						</tr>
						<tr>
						    <td class="lefttd lbl-must" style="width: 25%">选择审核人员：</td>
							<td class="rigthtd" style="width: 75%" colspan="3">
							<table border="0">   
				              <tr>   
				                <td>   
				                  <select width=70  id="personList" name="personList" size="10"  >   
					                 	 <c:forEach var="person" items="${person}">	
					                 		 <option value ="${person.ID}">${person.name}</option>
										</c:forEach>             
				                  </select>   
				                </td>   
				               <td width=50 align="center">     
					              <!--  <a href="#" title="添加所有人员"  onclick="insertAll();">>></a><br>  -->
					               <img src="${ctx}/static/risesoft/images/arrow_right.gif" width="20" height="20" border=0 alt="添加" onmouseover="this.style.cursor='hand';" onclick="insertPerson();"><br>
					               <img src="${ctx}/static/risesoft/images/arrow_left.gif" width="20" height="20" border=0 alt="移除" onmouseover="this.style.cursor='hand';" onclick="removeAll();">   <br>
					             <!--   <a href="#" title="移除所有人员"  onclick="removeAll();"><<</a>  --> 
				               </td>   
				                 <td>   
				                  <select id="lstSelected" name="auditPersonId" size="10" MULTIPLE >   
				                  </select>   
				                </td>   
				              </tr>    
				   </table>   
							</td>
						</tr>
						<tr >	
							<td colspan="4" align="center">
								<input type="submit" value="提交审核" />
								<input id="cancel" type="button" value="取消" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
	
	<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>
	<script type="text/javascript">
		var taskId="${taskId}";
		var instanceId="${instanceId}";
		
		$("form[name=teBieChengXuShenQing]").submit(function(){
			var tbcxsx = document.getElementById("tbcxsx").value;
			var auditPersonId = "";
			var auditPerson = "";
			var optionsObjects=document.getElementById("lstSelected"); 
			for(var o=0;o<optionsObjects.length;o++){   
			    var id=optionsObjects.options[o].value;   
			    var name=optionsObjects.options[o].text;
			    auditPersonId+=id;
			    auditPerson+=name;
			} 
			 if(auditPersonId == ""){
				alert("请选择审核人员！")
				return false;
			} 
			 if($(this).form('validate')){
				saveForm(tbcxsx,auditPersonId,auditPerson);
			} 
			return false;
		})
		
		function saveForm(tbcxsx,auditPersonId,auditPerson){
			$("#teBieChengXuShenQing").ajaxSubmit({
						async : false,  
						cache : false,
						url: "${ctx}/sp/teBieChengXuShenQing/saveOrModify",
						data: {tbcxsx:tbcxsx,auditPersonId:auditPersonId,auditPerson:auditPerson,taskId:taskId},
						dataType:"json",
						type:'POST', 
						error:function(data){
							alert("出现异常,此次操作可能失败");
						},
						success:function(data){
							if(data.success==true){
								alert("特别程序申请成功！");
								parent.window.parent.refreshMenu();
							}else{
								alert("特别程序申请失败！");
							}
						}
			});
			return false;
		}
		
		//添加所选人员
		function insertPerson(){
			 var optionsObjects=document.getElementById("personList");   
			 for(var o=0;o<optionsObjects.length;o++){   
				if(optionsObjects.options[o].selected==true){   
				    var id=optionsObjects.options[o].value;   
				    var name=optionsObjects.options[o].text;   
				    addoptions(name,id);  
				}   
			 }   
		}
		
	    //向目标   
		function addoptions(name,id){   
			var optionsSubObjects=document.getElementById("lstSelected");   
			var hasexist=0;   
			for(var o=0;o<optionsSubObjects.length;o++){   
				var id_sub=optionsSubObjects.options[o].value;   
				if(id_sub==id)   
				hasexist+=1;   
			}   
			if(hasexist==0&&optionsSubObjects.length>0)   {   
			 	//optionsSubObjects.add(new Option(name, id));  
				optionsSubObjects.options[0].value=id;
				optionsSubObjects.options[0].text=name;
			} else if(hasexist==0&&optionsSubObjects.length==0){
				optionsSubObjects.add(new Option(name, id));
			}  
		} 
	      
	    //添加所有人员
		function insertAll(){
			var optionsObjects=document.getElementById("personList");   
			 for(var o=0;o<optionsObjects.length;o++){   
			    var id=optionsObjects.options[o].value;   
			    var name=optionsObjects.options[o].text;   
			    addoptions(name,id);  
			 }   
	    }
	    
		//将对象中所选的项删除   
		function removePerson(){ 
			var optionsObjects=document.getElementById("lstSelected");   
			for(var o=0;o<optionsObjects.length;o++){   
				if(optionsObjects.options[o].selected==true){   
					//var id=optionsObjects.options[o].value;   
					//var name=optionsObjects.options[o].text;  
					optionsObjects.options.remove(o);   
				 }   
			}   
		} 
		
		//删除所有选项   
		function removeAll(){   
			var optionsSubObjects=document.getElementById("lstSelected");   
			for(var o=0;o<optionsSubObjects.length;o++){   
				optionsSubObjects.options.remove(o);    
			}   
		} 
		
		$("#cancel").click(function(){
			closeCurWindow(parent.frameID,'close');
		});
		
	</script>
</html>