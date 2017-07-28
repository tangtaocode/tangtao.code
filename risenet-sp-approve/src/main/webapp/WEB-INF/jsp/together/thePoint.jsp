<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv=Content-Type content="text/html;charset=utf-8">
<meta http-equiv=X-UA-Compatible content="IE=edge">
<title>编辑材料收件要点</title>
	
<script tyoe="text/javascript">
function saveForm(){
	setCheckBox();
	$("#situation").val($("#situationdes").val());
	$("#reqirements").val($("#reqirementsdes").val());
	$("#standards").val($("#standardsdes").val());
	$("#clbh").val($("#clbhdes").val());
	$('#saveForm').ajaxSubmit({
		type : 'POST',
		dataType : 'json',
		url : '${ctx}/togetherWindow/savePoint',
		success : function(responseText, statusText, xhr, $form) {
			if(responseText.msg!=1){
				alert("保存失败");
				return;
			}else{
				alert("保存成功");
				parent.closeThePoint();
			}
		},
		error:function(){
		}
	});
}
function setCheckBox(){
	var str='';
	var sl='';
	$('input:checkbox[name="tjlx"]').each(function(e){  
		if($(this).attr("checked")){
			str += $(this).val()+":";
			sl += $('input:[name="des"]').eq(e).val()+":";
		}else{
			str += " :";
			sl += " :";
		}
	}); 
	
	$("#lxArr").val(str);
	$("#slArr").val(sl);
	var fs="";
	$('input:checkbox[name="tjfs"]').each(function(){  
		if($(this).attr("checked")){
			fs += $(this).val()+":";
		}else{
			fs += " :";
		}
	}); 
	$("#fsArr").val(fs);
}
</script>
</head>
<body>	
	<div  style="width:100%;height:100%;overflow-y:auto">
		<form action="" id="saveForm" method="post" align="center">
			<input type="hidden" name="listsguid" value="${point.listsguid }"/>
			<input type="hidden" name="clbh" id="clbh" value=""/>
			<input type="hidden" name="listsname" id="listsname" value="${point.listsname }" style="width:200px"></input>
			 <input type="hidden" name="situation" id="situation" value=""/>
			<input type="hidden" name="reqirements" id="reqirements" value=""/>
			<input type="hidden" name="standards" id="standards" value=""/>
			<input type="hidden" name="lxArr" id="lxArr" value=""/>
			<input type="hidden" name="fsArr" id="fsArr" value=""/>
			<input type="hidden" name="slArr" id="slArr" value=""/>
		</form>
		 <table class="tableStyle" formMode="line" style="width:100%;height:100%;">
			<tr>
				<td width="15%">
					材料名称：
				</td>
				<td width="85%">
					<input id="name" value="${point.listsname }" style="width:70%"></input>
				</td>
			</tr>
			<tr>
				<td>
					材料编号：
				</td>
				<td>
					<input name="clbhs" id="clbhdes" value="${point.clbh }" style="width:70%"></input>
				</td>
			</tr>
			<tr>
				<td>
					提交方式：
				</td>
				<td colspan="3">
					<table class="tableStyle" formMode="line" align="left">
						
						<tr>
							<td width="20%">
								<input type="checkbox" id="yj" name="tjlx" value="1"/>
								<label for="yj" class="hand">原件(正本)</label>
							</td>
							<td>
								<input type="checkbox" id="yj_hy" name="tjfs" value="1"/>
								<label for="yj_hy" class="hand">核验</label>&nbsp;&nbsp;
								<input type="checkbox" id="yj_sq" name="tjfs" value="2"/>
								<label for="yj_sq" class="hand">收取</label>&nbsp;&nbsp;
								<input type="text" name="des"/>
							</td>
						</tr>
						<tr>
							<td width="20%">
								<input type="checkbox" id="fb" name="tjlx" value="2"/>
								<label for="fb" class="hand">原件(副本)</label>
							</td>
							<td>
								<input type="checkbox" id="fb_hy" name="tjfs" value="1"/>
								<label for="fb_hy" class="hand">核验</label>&nbsp;&nbsp;
								<input type="checkbox" id="fb_sq" name="tjfs" value="2"/>
								<label for="fb_sq" class="hand">收取</label>&nbsp;&nbsp;
								<input type="text" name="des"/>
							</td>
						</tr>
						<tr>
							<td width="20%">
								<span style="margin-right:25px">
								<input type="checkbox" id="dzj" name="tjlx" value="3"/>
								<label for="dzj" class="hand">电子件</label>
								</span>
							</td>
							<td>
								<input type="checkbox" id="dzj_hy" name="tjfs" value="1"/>
								<label for="dzj_hy" class="hand">核验</label>&nbsp;&nbsp;
								<input type="checkbox" id="dzj_sq" name="tjfs" value="2"/>
								<label for="dzj_sq" class="hand">收取</label>&nbsp;&nbsp;
								<input type="text" name="des"/>
							</td>
						</tr>
						<tr>
							<td width="20%">
								<span style="margin-right:25px">
								<input type="checkbox" id="fyj" name="tjlx" value="4"/>
								<label for="fyj" class="hand">复印件</label>
								</span>
							</td>
							<td>
								<input type="checkbox" id="fyj_hy" name="tjfs" value="1"/>
								<label for="fyj_hy" class="hand">核验</label>&nbsp;&nbsp;
								<input type="checkbox" id="fyj_sq" name="tjfs" value="2"/>
								<label for="fyj_sq" class="hand">收取</label>&nbsp;&nbsp;
								<input type="text" name="des"/>
							</td>
						</tr>
						<tr>
							<td width="20%">
								<span style="margin-right:42px">
									<input type="checkbox" id="qt" name="tjlx" value="5"/>
									<label for="qt" class="hand">其它</label>
								</span>
							</td>
							<td>
								<input type="checkbox" id="qt_hy" name="tjfs" value="1"/>
								<label for="qt_hy" class="hand">核验</label>&nbsp;&nbsp;
								<input type="checkbox" id="qt_sq" name="tjfs" value="2"/>
								<label for="qt_sq" class="hand">收取</label>&nbsp;&nbsp;
								<input type="text" name="des"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr height="70px">
				<td>
					业务情形：
				</td>
				<td colspan="3">
					<textarea name="situationdes" id="situationdes" value="${point.situation }" keepDefaultStyle=true style="width:100%;height:70px;">${point.situation }</textarea>
				</td>
			</tr>
			<tr height="70px">
				<td>
					收件要求：
				</td>
				<td colspan="3">
					<textarea name="reqirementsdes" id="reqirementsdes" value="${point.reqirements }" keepDefaultStyle=true style="width:100%;height:70px;">${point.reqirements }</textarea>
				</td>
			</tr>
			<tr height="70px">
				<td>
					收件标准：
				</td>
				<td colspan="3">
					<textarea name="standardsdes" id="standardsdes" value="${point.standards }" style="width:100%;height:70px;" keepDefaultStyle=true
					>${point.standards }</textarea>
				</td>
			</tr>
			
			<c:if test="${method!='view' }">
			<tr><td colspan="4">
			<button  onclick="saveForm();" id="saveBtn1" style="cursor:pointer"><span class="icon_ok" >提交</span></button>&nbsp;
			<!-- 
			<button type="reset"><span class="icon_clear">重置</span></button>
			<input type="reset" class="hand" value="重置"/>
			 -->
			</td>
			</tr>
			
			</c:if>
		</table>
	</div>	

<script type="text/javascript">
	var tjlx = '${map.tjlx}';
	var tjfs = '${map.tjfs}';
	var tjsl = '${map.tjsl}';
	$(function(){
		if(tjlx!=null && tjlx!=''){
			var tjlxArr = tjlx.split(",");
			var tjfsArr = tjfs.split(",");
			var tjslArr = tjsl.split(",");
			for(var i=0;i<tjlxArr.length;i++){
				$('input:checkbox[name="tjlx"]').each(function(e){
					if(tjlxArr[i]==$(this).val()){
						var y = (e+1)*2;
						$(this).attr("checked","checked");
						//給提交數量賦值
						document.getElementsByName("des")[e].value=tjslArr[i]; 
						//給提交方式赋值
						var arr = tjfsArr[i].split(";");
						if(arr[0]==document.getElementsByName("tjfs")[y-2].value){
							document.getElementsByName("tjfs")[y-2].checked=true;
						}
						if(arr[1]==document.getElementsByName("tjfs")[y-1].value){
							document.getElementsByName("tjfs")[y-1].checked=true;
						}
					}
				}) 
			}
		}
	})
</script>	
</body>
</html>
