var diag = null;
var checkingID = null;
var loading = '<br><img src="/images/share/loading.gif"/><br><br>';

$(function(){

	/**
	 *@description 建造师材料共享 
	 */
	 $("input[id='WS_JZSZCZS']").blur(function(){
	 	var guid = $(this).attr("guid");
		var $this = $(this);
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var h = 250;
		var w = 600;
		var title = "“建造师注册证书”信息确认";
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“建造师注册证书”信息加载中...",loading);
//			$.post("/WebService/GetJZSZCZS.html",{"sfzhm":$this.val(),"category":"2"},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						if(data.indexOf("执业注册证书")>-1){//注册专业为空
//							showMessage(title,data,h,w,$this,imgID,false);
//						}else{
//							showMessage(title,data,h,w,$this,imgID,true);
//						}
//					}else{
//						h = 150;
//						showMessage(title,data.error,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"",h,w,$this,imgID,false);
		}
	 });
	 
	 /**
	 *@description 项目总监材料共享 
	 */
	 $("input[id='WS_XMZJZCZS']").blur(function(){
	 	var guid = $(this).attr("guid");
		var $this = $(this);
	 	var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var h = 250;
		var w = 600;
		var title = "“项目总监注册证书”信息确认";
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“项目总监注册证书”信息加载中...",loading);
//			$.post("/WebService/GetJZSZCZS.html",{"sfzhm":$this.val(),"category":"3"},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						if(data.indexOf("执业注册证书")>-1){//注册专业为空
//							showMessage(title,data,h,w,$this,imgID,false);
//						}else{
//							showMessage(title,data,h,w,$this,imgID,true);
//						}
//					}else{
//						h = 150;
//						showMessage(title,data.error,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"",h,w,$this,imgID,false);
		}
	 });
	
	/**
	 *	材料共享（施工工程的施工图设计文件审查合格意见书）
	 */
	$("input[id='WS_SGTSJWJSCHGYJS']").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var h = 250;
		var w = 600;
		var title = "“具备相应资质的施工图审查单位出具的施工图设计文件审查合格报告”信息确认";
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			//Dialog.alert("“具备相应资质的施工图审查单位出具的施工图设计文件审查合格报告编号”不能为空");
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“具备相应资质的施工图审查单位出具的施工图设计文件审查合格报告”信息加载中...",loading);
//			$.post("/onlineService/doInvestigateWebService.html",{"qualifiedbookid":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"",h,w,$this,imgID,false);
		}
	});
	
	
	/**
	 *	证照共享（深圳市民用建筑施工图设计文件抽查（备案）意见书）
	 */
	$("input[id='WS_MYJZSGT']").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var h = 250;
		var w = 600;
		var title = "“深圳市民用建筑施工图设计文件抽查（备案）意见书”信息确认";
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			//Dialog.alert("“深圳市民用建筑施工图设计文件抽查（备案）意见书编号”不能为空");
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“深圳市民用建筑施工图设计文件抽查（备案）意见书”信息加载中...",loading);
//			$.post("/onlineService/doLicenseWebService.html",{"zjbh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	
	/**
	 *	证照共享施工企业资质证书
	 */
	$("input[id='WS_SGQYZZZS']").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var h = 250;
		var w = 600;
		var title = "“施工企业资质证书”信息确认";
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			//Dialog.alert("“深圳市民用建筑施工图设计文件抽查（备案）意见书编号”不能为空");
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“施工企业资质证书”信息加载中...",loading);
//			$.post("/onlineService/checkSgqyzzzs.html",{"zjbh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	/**
	 *	证照共享监理单位资质证书
	 */
	$("input[id='WS_JLDWZZZS']").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var h = 250;
		var w = 600;
		var title = "“监理单位资质证书”信息确认";
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			//Dialog.alert("“深圳市民用建筑施工图设计文件抽查（备案）意见书编号”不能为空");
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“监理单位资质证书”信息加载中...",loading);
//			$.post("/onlineService/checkJldwzzzs.html",{"zjbh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	/**
	 * 材料共享（保函材料）
	 */
	$("input[id='WS_LYBH']").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var h = 250;
		var w = 600;
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var title = "“保函收讫证明”信息确认";
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			//Dialog.alert("“由市建设工程交易服务中心出具的履约保函收讫证明工程编号”不能为空");
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“保函收讫证明”信息加载中...",loading);
//			$.post("/onlineService/doBackletterWebService.html",{"strGcbh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	/**
	 *	材料共享（直接发包审批决定书文号）
	 */
	$("input[id='WS_ZJFBSPJDS']").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var h = 150;
		var w = 600;
		var title = "“直接发包审批决定书”信息确认";
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			//Dialog.alert("“直接发包审批决定书编号”不能为空");
			if(guid!=null&&guid!=""){//若该共享编号已保存，则删除
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“直接发包审批决定书”信息加载中...",loading);
//			$.post("/onlineService/doDecisionWebService.html",{"bwbh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){//查到正确的数据
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{//查不到记录或出错
//						if(guid!=null&&guid!=""){//若该共享编号已保存，则删除
//							deleteShare(guid);
//							$this.attr("guid","");
//						}
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	
	//建设用地规划许可证号码
	$("input[id='WS_JSYDGHXKZ']").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var title = "“建设用地规划许可证”信息确认";
		var h = 150;
		var w = 600;
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“建设用地规划许可证”信息加载中...",loading);
//			$.post("/onlineService/GetJSYDGHXKZDataById.html",{"jsydghxkzh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	// 建设工程规划许可证号码
	$("#WS_JSGCGHXKZ").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var title = "“建设工程规划许可证”信息确认";
		var h = 600;
		var w = 800;
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“建设工程规划许可证”信息加载中...",loading);
//			$.post("/onlineService/GetJSGCGHXKZDataById.html",{"jsgcghxkzh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	
	// 营业执照
	$("#WS_YYZZ").blur(function(){
		var guid = $(this).attr("guid");
		var $this = $(this);
		var fileGuid = $this.attr("fileGuid");
	 	var sFileGuid = fileGuid.substr(1,fileGuid.length-2);
	 	$("#\\{"+sFileGuid+"\\}").remove();//先清空标志正确与否的小图标
		var imgID = sFileGuid+$("input[id='"+$this.attr("id")+"']").index(this);
		var title = "“营业执照”信息确认";
		var h = 600;
		var w = 800;
		if($this.val()==null||$this.val()==""){
			$("#"+imgID).remove();
			if(guid!=null&&guid!=""){
				deleteShare(guid);
				$this.attr("guid","");
			}
		}else{
			checkingID = imgID;
			showLoading("“营业执照”信息加载中...",loading);
//			$.post("/onlineService/GetYYZZById.html",{"yyxkzh":$this.val()},function(data){
//				if(imgID==checkingID){
//					if(data!=null&&data.error==null){
//						showMessage(title,data,h,w,$this,imgID,true);
//					}else{
//						//$("#"+imgID).remove();
//						//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
//						//Dialog.alert(data.msg);
//						//closeDialog();
//						showMessage(title,data.msg,h,w,$this,imgID,false);
//					}
//				}
//			});
showMessage(title,"未找到信息",h,w,$this,imgID,false);
		}
	});
	
	/***************************************施工许可申请 start******************************************/
	
	/**
	 *	建造师回填
	 */
	$("#fwsq_jzssfzhm").blur(function(){
		var sfzhm = $(this).val();
		var category = "2";
		var xm_id = "fwsq_jzsmc";
		var zyzch_id = "fwsq_jzszch";
		var mobile="fwsq_jzsdh";
		var type = "sq";
		if(sfzhm!=""){
			doQuery(sfzhm,category,xm_id,zyzch_id,mobile,type);
		}
	});
	
	/**
	 *	项目总监回填
	 */
	$("#fwsq_xmzjsfzhm").blur(function(){
		var sfzhm = $(this).val();
		var category = "3";
		var xm_id = "fwsq_xmzj";
		var zyzch_id = "fwsq_zmzjzch";
		var mobile="fwsq_xmzjdh";
		var type = "sq";
		if(sfzhm!=""){
			doQuery(sfzhm,category,xm_id,zyzch_id,mobile,type);
		}
	});
	
	/***************************************施工许可申请 end******************************************/
	
	/***************************************施工许可更改 start******************************************/
	
	/**
	 *	建造师回填
	 */
	$("#skqz_bghxmjl_idno").blur(function(){
		var sfzhm = $(this).val();
		var category = "2";
		var xm_id = "skqz_bghdxmjl";
		var zyzch_id = "skqz_zyzch2";
		var mobile="skqz_jlyddh1";
		var type = "bg";
		if(sfzhm!=""){
			doQuery(sfzhm,category,xm_id,zyzch_id,mobile,type);
		}
	});
	
	/**
	 *	项目总监回填
	 */
	$("#skqz_bghxmzj_idno").blur(function(){
		var sfzhm = $(this).val();//身份证号码
		var category = "3";//人员标志：2：建造师，3：项目总监
		var xm_id = "skqz_bghdxmzj";//姓名id
		var zyzch_id = "skqz_zyzch4";//执业注册号id
		var mobile="skqz_yddh2";//电话号码id
		var type = "bg";//表单类型，sq：申请表单，bg：变更表单
		if(sfzhm!=""){
			doQuery(sfzhm,category,xm_id,zyzch_id,mobile,type);
		}
	});
	
	/***************************************施工许可更改 end******************************************/
	
	//法定代表人校验
	$("input[name=c_133_234_CORP_ID_NO]").blur(function(){
		var cardId="'"+$("input[name=c_133_234_CORP_ID_NO]").val()+"'";
		var qyguid=$("#SB_guid").val();
		var type="法定代表人";
		$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
			if(data.flag=="1"){
				$("#cardId").remove();
				$("input[name=c_133_234_CORP_ID_NO]").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='cardId'/>");
			}else{
				$("#cardId").remove();
				$("input[name=c_133_234_CORP_ID_NO]").parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='cardId'/>");
			}
		});
	});
	
	//企业负责人校验
	$("input[name=c_93_454_CORP_ID_NO]").blur(function(){
		var cardId="'"+$("input[name=c_93_454_CORP_ID_NO]").val()+"'";
		var qyguid=$("#SB_guid").val();
		var type="企业负责人";
		$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
			if(data.flag=="1"){
				$("#cardId").remove();
				$("input[name=c_93_454_CORP_ID_NO]").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='cardId'/>");
			}else{
				$("#cardId").remove();
				$("input[name=c_93_454_CORP_ID_NO]").parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='cardId'/>");
			}
		});
	});
	
	//技术负责人校验
	$("input[name=c_58_855_CORP_ID_NO]").blur(function(){
		var cardId="'"+$("input[name=c_58_855_CORP_ID_NO]").val()+"'";
		var qyguid=$("#SB_guid").val();
		var type="企业负责人";
		$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
			if(data.flag=="1"){
				$("#cardId").remove();
				$("input[name=c_58_855_CORP_ID_NO]").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='cardId'/>");
			}else{
				$("#cardId").remove();
				$("input[name=c_58_855_CORP_ID_NO]").parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='cardId'/>");
			}
		});
	});
	
	$("#CORP_ID_NO").blur(function(){
		var qyguid=$("#CORP_SB_GUID").val();
		var cardId="'"+$("#CORP_ID_NO").val()+"'";
		if(cardId==null||cardId==""||cardId.length<18){
			Dialog.alert("身份证号码不能小于18位");
			return;
		}
		var type=$("#CORP_EMPLOYEE_TYPE").val();
		$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
			if(data.flag=="1"){
				$("#cardId").remove();
				Dialog.alert(data.message);
				$("#CORP_ID_NO").parent().append("<img src='/engine/jquery/themes/icons/no.png' id='cardId'/>");
			}else{
				$("#cardId").remove();
				$("#CORP_ID_NO").parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='cardId'/>");
			}
		});
	});
	
	//专业人员校验
	$("#zyryyz").click(function(){
		var qyguid=$("#SB_guid").val();
		var type="7";
		var $test = $("input[name=c_251_244_CORP_ID_NO]");
		var cardId="";
		for(var i=0;i<$test.length;i++){
			if($test.eq(i).val()!=""){
					if(i==$test.length-1){
						cardId=cardId+"'"+$test.eq(i).val()+"'";
					}else{
						cardId=cardId+"'"+$test.eq(i).val()+"'"+",";
					}
				}else{
					Dialog.alert("身份证号码不能为空");
					return;
				}
			}
		$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
			if(data.flag=="1"){
				Dialog.alert(data.message);
			}else{
				Dialog.alert("验证通过");
			}
		});
	});
	
	//安装维修人员
	$("#azwxryyz").click(function(){
		var qyguid=$("#SB_guid").val();
		var type="10";
		var $test = $("input[name=c_477_409_CORP_CERTIFICATION_NO]");
		var cardId="";
		for(var i=0;i<$test.length;i++){
			if($test.eq(i).val()!=""){
					if(i==$test.length-1){
						cardId=cardId+"'"+$test.eq(i).val()+"'";
					}else{
						cardId=cardId+"'"+$test.eq(i).val()+"'"+",";
					}
				}else{
					Dialog.alert("身份证号码不能为空");
					return;
				}
			}
		$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
			if(data.flag=="1"){
				Dialog.alert(data.message);
			}else{
				Dialog.alert("验证通过");
			}
		});
	});
	
});



	//删除上传的共享材料
	function deleteShare(fileGuid,needDel){
		if(fileGuid!=undefined&&fileGuid.replace(" ","")!=""){
			$.post("/onlineService/deleteDocType.html",{'fileGuid':fileGuid},function(data){
				if(data.message=="1"){
					needDel.parent().parent().remove();
					return true;
				}else{
					return false;
				}
			});
		}
	}
	
	//保存共享材料信息
	function saveData($this,imgID){
		var params = {"fileGuid":$this.attr("fileGuid"),"appInstanceGuid":$this.attr("appInstanceGuid"),
					"webApplyFileDoctype.guid":$this.attr("guid"),"cardid":$this.val()};
		$.post("/onlineService/newSaveDocType.html",params,function(data){
				if(data.message=="1"){
					//$("#"+imgID).remove();
					//$this.parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='"+imgID+"'/>");
					addIcon($this,imgID,true);
					if(data.gxclguid!=null&&data.gxclguid!=""&&data.gxclguid!="null"){
						$this.attr("guid",data.gxclguid);
					}
					return true;
				}else if(data.message=="0"){
					//$("#"+imgID).remove();
					//$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='"+imgID+"'/>");
					addIcon($this,imgID,false);
					return false;
				}
		});
		
	}
	
	/**
	 *@desciption 执行建造师、项目总监查询并回填相关信息
	 *@param sfzhm 身份证号码
	 *@param category 人员类型标志，2：建造师，3：项目总监
	 *@param xm_id 姓名id
	 *@param zyzch_id 执业注册号id
	 *@param mobile 电话号码id
	 *@param type 表单类型，sq：申请表单，bg：变更表单
	 */
	function doQuery(sfzhm,category,xm_id,zyzch_id,mobile,type){
		var strGcbh;//工程编号
		var fwsq_xmbh;//项目编号
		var fwsq_szdxm;//是否大项目
		var gcbh_id;//工程编号id
		var xmbh_id;//项目编号id
		var szdxm_id;//是否大项目id
		if(type=="sq"){
			strGcbh = $("#gcbh").val();
			fwsq_xmbh = $("#fwsq_xmbh").val();
			fwsq_szdxm = $('input[name="fwsq_szdxm"]:checked').val();
			gcbh_id = "gcbh";
			xmbh_id = "fwsq_xmbh";
		}else{
			strGcbh = $("#skqz_gcbh").val();
			fwsq_xmbh = $("#skqz_PROJECTNO").val();
			fwsq_szdxm = $('input[name="skqz_sfzdxm"]:checked').val();
		}
		
		if(fwsq_szdxm!=undefined&&fwsq_szdxm=="是"){
			fwsq_szdxm = "1";
		}else if(fwsq_szdxm!=undefined&&fwsq_szdxm=="否"){ 
			fwsq_szdxm = "0";
		}
		var ti = "项目总监";
		
		if(category=="2"){
			ti = "建造师";
			if(strGcbh==""){
				Dialog.alert("工程编号 不能为空");
				$("#"+gcbh_id).focus();
				return false;
			}
			if(fwsq_xmbh==""){
				Dialog.alert("项目编号 不能为空");
				$("#"+xmbh_id).focus();
				return false;
			}
			
			if(fwsq_szdxm==undefined||fwsq_szdxm==null){
				Dialog.alert("请选择 是否是重大项目或政府投资项目");
				$('input[name="fwsq_szdxm"]').focus();
				return false;
			}
		}
		showLoading("信息查询中...",loading);
		$.post("/onlineService/backfill.html",{"sfzhm":sfzhm,"category":category,"strGcbh":strGcbh,"xmbh":fwsq_xmbh,"isBig":fwsq_szdxm},function(data){
		closeDialog();
			if(data!=null&&data.idCard!=null){//查询到正确数据
				$("#"+xm_id).val(data.name);
				$("#"+zyzch_id).val(data.certId);
				$("#"+mobile).val(data.mobile);
			}else if(data!=null&&data.error!=null){//查不到数据或出错
				Dialog.alert(data.error);
				$("#"+xm_id).val("");
				$("#"+zyzch_id).val("");
				$("#"+mobile).val("");
			}
		});
	}
	
	
	/**
	 * 施工许可 工程信息回填
	 */
	function selectProject(){
		var url = '';
	         if(isOnlineHall){
	             var gcbh = document.getElementById('gcbh').value;
	             url = '/onlineService/selectYwbwProjectInfo';//外网地址
	             if(gcbh!=null && gcbh!=''){
			url = url+'/projectid/'+gcbh+'/method/search/action';
		  }
		  url = url+'.html';
	         }else{
	             url = '/approve/selectYwbwProject.jsp';//内网地址url=url+'?method=search';
				 if(document.getElementById('gcbh').value!=null && document.getElementById('gcbh').value!=''){
					url = url+'&projectid='+document.getElementById('gcbh').value;
				 }
		         if(document.getElementById('fwsq_gcmc').value!=null && document.getElementById('fwsq_gcmc').value!=''){
		               url = url+'&projectname='+document.getElementById('fwsq_gcmc').value;
				 }
	         }
	
		var ret = window.showModalDialog(url,'','dialogWidth=900px;dialogHeight=768px');
		if(ret!=null && ret != undefined){//回填
		    document.getElementById('gcbh').value = ret.prj_id;
	        document.getElementById('fwsq_xmbh').value = ret.item_id;
			document.getElementById('fwsq_xmmc').value = ret.item_name;
			document.getElementById('fwsq_gcmc').value = ret.prj_name;
	        try{
				document.forms[0].c_253_360_sqdw_sqdw.value = ret.const_org;
	        }catch(e){}	
	
			document.getElementById('c_253_360_sqdw_fddbr').value=ret.auct_resp;
	        document.getElementById('fwsq_lxdh').value=ret.auct_mobile; 
			document.getElementById('fwsq_jldw').value=ret.sup_name;
	        document.getElementById('fwsq_sjdw').value=ret.design_name; 
			document.getElementById('fwsq_gcdz').value=ret.prj_location;
	        document.getElementById('fwsq_htzj').value=ret.fb_const_price; 
			document.getElementById('fwsq_htkgrq').value=ret.exp_start_date;
	        document.getElementById('fwsq_htjgrq').value=ret.exp_end_date; 
	        document.getElementById('fwsq_jzssfzhm').value=ret.mgr_card; 
	        document.getElementById('fwsq_jzsmc').value=ret.mgr_name;
	        document.getElementById('fwsq_jzsdh').value=ret.mgr_mobile;
	        document.getElementById('fwsq_jzszch').value=ret.mgr_reg_cert_no;	        
	        
	        try{
	        	document.getElementById('term').value=ret.term;
	        }catch(e){}
	        
	        if(ret.fund_gov!=undefined){
		        document.getElementById('fwsq_zfzj').value=ret.fund_gov;
	        }
	        if(ret.fund_nat!=undefined){
			    document.getElementById('fwsq_gyzj').value=ret.fund_nat;
	        }
	        if(ret.fund_comm!=undefined){
		        document.getElementById('fwsq_jtzj').value=ret.fund_comm;
	        }
	        if(ret.funn_priv!=undefined){
		        document.getElementById('fwsq_syzj').value=ret.funn_priv;
	        }
	        if(ret.fund_for!=undefined){
		        document.getElementById('fwsq_wzzj').value=ret.fund_for;
	        }
	        if(ret.fund_oth!=undefined){
		        document.getElementById('fwsq_qtzj').value=ret.fund_oth;
	        }
	        if(ret.prj_classlev!=undefined){
	        	if(ret.prj_classlev=='1')ret.prj_classlev='一级';
	        	if(ret.prj_classlev=='2')ret.prj_classlev='二级';
	        	if(ret.prj_classlev=='3')ret.prj_classlev='三级';
	        	document.getElementById('fwsq_gcjbyj').value=ret.prj_classlev;
	        }
	        if(ret.bldg_up_type!=undefined){
		        document.getElementById('fwsq_jc').value=ret.bldg_up_type;
	        }
	        if(ret.bldg_base_type!=undefined){
		        document.getElementById('fwsq_zt').value=ret.bldg_base_type;
	        }
	        
	        //var oos = document.getElementsByName("c_253_360_sqdw_sqdw");
	        //oos[0].value = ret.const_org;
		}
	}
	
	
	/**********************************弹框 start***************************************/
	
	//加载中
	function showLoading(title,data){
		closeDialog();
		diag = new Dialog();
		diag.Width = 600;
		diag.Title = title;
		diag.InnerHtml = data;
		diag.CancelEvent = function(){
			closeDialog();
			checkingID = null;
		}
		diag.show();
	}
	
	//关闭加载中窗口
	function closeDialog(){
		if(diag!=null){
			diag.close();
			diag = null;
		}
		return true;
	}
	
	//弹出确认信息框
	function showMessage(title,data,h,w,$this,imgID,isSuccess){
		if(closeDialog()){
			if(imgID==checkingID){
				diag = new Dialog();
				diag.Width = w;
				diag.Height = h;
				diag.Title = title;
				diag.InnerHtml = data;
				if(isSuccess==false){
					diag.OkButtonText = "上传材料";
					diag.CancelButtonText = "重新输入";
				}
				diag.OKEvent = function(){//点击确定按钮
					if(isSuccess){
						if(!saveData($this,imgID)){
							var $del = $("a[del_insID='"+$this.attr("uploadID")+"']");
							for(var i=0;i<$del.size();i++){
								$del[i].click();
							}
						}
					}else{
						deleteShare($this.attr("guid"));
						$this.attr("guid","");
						$("#"+imgID+"upload").remove();	
						addIcon($this,imgID,false);
						//添加上传材料小图标
						//$this.parent().append("<img id=\""+imgID+"upload\" onclick=\"showFile('"+$this.attr("uploadID")+"');\" src=\"/images/lineservice/upLine.png\" class=\"imagefile\">");
						
						closeDialog();//关闭已打开的弹框
						showFile($this.attr("uploadID"));//打开上传材料弹框
					}
					closeDialog();
				};
				diag.CancelEvent = function(){//点击取消按钮
					$("#"+imgID+"upload").remove();	
					addIcon($this,imgID,false);
					closeDialog();
				}
				diag.show();
			}
		}
	}
	
	/**
	 *@description 添加标志图标
	 *@param addImg_id 需添加标志图标的元素id
	 *@param isSuccess 图标类型 true:成功图标，false：失败图标
	 */
	function addIcon($this,addImg_id,isSuccess) {
		if (isSuccess) {
			$("#" + addImg_id ).remove();
			$this.parent().append("<img src='/engine/jquery/themes/icons/checked.gif' id='" + addImg_id + "' width='16' height='16'/>");
		} else {
			$("#" + addImg_id ).remove();
			$this.parent().append("<img src='/engine/jquery/themes/icons/no.png' id='" + addImg_id + "' width='16' height='16'/>");
		}
	}

	/**********************************弹框 end***************************************/
	
	
	$(function(){
		var empType="";
		/**
		 *	燃气企业设立经营许可在线申报 ------法定代表人查询
		 */
		$("input[name='c_452_678_CORP_ID_NO']").blur(function(){
			var guid = $("#SB_guid").val();
			var idNo = $(this).val();		
			empType="法定代表人";
			//alert($(this).index());

			var idNo = $(this).val();
			if(idNo!=""){
				$.post("/onlineService/doEmpCheck.html",{"idno":idNo,"sbguid":guid,"empType":empType},function(data){
					if(data!=null&&data.qyname!=null){
						alert("该人员已在《"+data.qyname+"》企业!");
						$("input[name='c_452_678_CORP_ID_NO']").val("");
						
					}		
				});
			}
		});
		
		/**
		 *	董事长查询
		 */
		$("input[name='c_164_17_CORP_ID_NO']").blur(function(){
			var guid = $("#SB_guid").val();
			var idNo = $(this).val();
			empType="董事长";
			if(idNo!=""){
				$.post("/onlineService/doEmpCheck.html",{"idno":idNo,"sbguid":guid,"empType":empType},function(data){
					if(data!=null&&data.qyname!=null){
						alert("该人员已在《"+data.qyname+"》企业任职!");
						$("input[name='c_164_17_CORP_ID_NO']").val("");
						
					}		
				});
			}
		});
		/**
		 *	主要负责人查询
		 */
		$("input[name='c_878_231_CORP_ID_NO']").blur(function(){
			var guid = $("#SB_guid").val();
			var idNo = $(this).val();
			empType="主要负责人";
			if(idNo!=""){
				$.post("/onlineService/doEmpCheck.html",{"idno":idNo,"sbguid":guid,"empType":empType},function(data){
					if(data!=null&&data.qyname!=null){
						alert("该人员已在《"+data.qyname+"》企业任职!");
						$("input[name='c_878_231_CORP_ID_NO']").val("");
						
					}		
				});
			}
		});
		
		/**
		 *	安全负责人查询
		 */
		$("input[name='c_357_62_CORP_ID_NO']").blur(function(){
			var guid = $("#SB_guid").val();
			var idNo = $(this).val();
			empType="安全负责人";
			if(idNo!=""){
				$.post("/onlineService/doEmpCheck.html",{"idno":idNo,"sbguid":guid,"empType":empType},function(data){
					if(data!=null&&data.qyname!=null){
						alert("该人员已在《"+data.qyname+"》企业任职!");
						$("input[name='c_357_62_CORP_ID_NO']").val("");
						
					}		
				});
			}
		});
		/**
		 *	技术负责人查询
		 */
		$("input[name='c_555_674_CORP_ID_NO']").blur(function(){
			var guid = $("#SB_guid").val();
			var idNo = $(this).val();
			empType="技术负责人";
			if(idNo!=""){
				$.post("/onlineService/doEmpCheck.html",{"idno":idNo,"sbguid":guid,"empType":empType},function(data){
					if(data!=null&&data.qyname!=null){
						alert("该人员已在《"+data.qyname+"》企业任职!");
						$("input[name='c_555_674_CORP_ID_NO']").val("");				
					}		
				});
			}
		});
		/**
		 *	专业技术人员查询
		 */
		$("#zyjsry").click(function(){
			var qyguid=$("#SB_guid").val();
			var type="专业技术人员";
			var $test = $("input[name=c_265_978_CORP_ID_NO]");
			var cardId="";
			for(var i=0;i<$test.length;i++){
				if($test.eq(i).val()!=""){
						if(i==$test.length-1){
							cardId=cardId+"'"+$test.eq(i).val()+"'";
						}else{
							cardId=cardId+"'"+$test.eq(i).val()+"'"+",";
						}
					}else{
						alert("身份证号码不能为空");
						return;
					}
				}
			$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
			//$.post("/onlineService/doZyjsEmpCheck.html",{'zyIdno':cardId,'sbguid':qyguid,'empType':type},function(data){
				if(data.flag=="1"){
					alert(data.message);
				}else{
					alert("验证通过");
				}
			});
		});
		/**
		 *	安全生产管理人员查询
		 */
		$("#aqscglry").click(function(){
			var qyguid=$("#SB_guid").val();
			var type="安全生产管理人员";
			var $test = $("input[name=c_595_879_CORP_ID_NO]");
			var cardId="";
			for(var i=0;i<$test.length;i++){
				if($test.eq(i).val()!=""){
						if(i==$test.length-1){
							cardId=cardId+"'"+$test.eq(i).val()+"'";
						}else{
							cardId=cardId+"'"+$test.eq(i).val()+"'"+",";
						}
					}else{
						alert("身份证号码不能为空");
						return;
					}
				}
			$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
				if(data.flag=="1"){
					alert(data.message);
				}else{
					alert("验证通过");
				}
			});
		});
		/**
		 *	操作及抢修人员查询
		 */
		$("#czjqxry").click(function(){
			var qyguid=$("#SB_guid").val();
			var type="操作及抢修人员";
			var $test = $("input[name=c_998_43_CORP_ID_NO]");
			var cardId="";
			for(var i=0;i<$test.length;i++){
				if($test.eq(i).val()!=""){
						if(i==$test.length-1){
							cardId=cardId+"'"+$test.eq(i).val()+"'";
						}else{
							cardId=cardId+"'"+$test.eq(i).val()+"'"+",";
						}
					}else{
						alert("身份证号码不能为空");
						return;
					}
				}
			$.post("/onlineService/getfddbr.html",{'cardId':cardId,'qyguid':qyguid,'type':type},function(data){
				if(data.flag=="1"){
					alert(data.message);
				}else{
					alert("验证通过");
				}
			});
		});
	});
	
//	//任职人员信息查询
//	$(document).ready(function(){
//		  $("input[name='c_265_978_CORP_ID_NO']").focus(function(){
//		    $("input[name='c_265_978_CORP_ID_NO']").css("background-color","#FFFFCC");
//		  });
//		  $("input[name='c_265_978_CORP_ID_NO']").blur(function(){
//		    $("input[name='c_265_978_CORP_ID_NO']").css("background-color","#D6D6FF");
//		  });
//		});
	

	