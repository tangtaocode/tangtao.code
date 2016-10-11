document.write("<script src='/js/showMessage.js'></script>");

/*************************施工许可更改 start******************************/

/**
 * @description 表单信息回填
 */
function fillBackPermit(checkid){
	checkingID = checkid;//记录查询id，作为判断是否为当前查询的依据
	var skqz_gcbh;
	var skqz_ysgxkzxh;
	var title = "查询施工许可申请信息";
	if(checkid == "xkzxh"){
		skqz_ysgxkzxh = $("#skqz_ysgxkzxh").val();
		if(skqz_ysgxkzxh==""){
			Dialog.alert("“原施工许可证序号”不能为空！");
			return ;
		}
	}else{
		skqz_gcbh = $("#skqz_gcbh").val();
		if(skqz_gcbh==""){
			Dialog.alert("“工程编号”不能为空！");
			return ;
		}
	}
	
	showLoading(title,loading);
	$.post("/onlineService/doQueryPermitForm.html",{"skqz_gcbh":skqz_gcbh,"skqz_ysgxkzxh":skqz_ysgxkzxh,"queryId":checkingID},function(data){
		if(data!=null&&data.queryId!=null&&data.queryId==checkingID){//判断是否为当前查询
			if(data.error!=null&&data.error!=""){//查不到数据或出错
				Dialog.alert(data.error);
			}else{//查到正确数据
				if(checkingID == "xkzxh"){
					$("#skqz_gcbh").val(data.gcbh);
				}else{
					$("#skqz_ysgxkzxh").val(data.zjbh);//原施工许可证序号
				}
				$("#skqz_gcmc").val(data.gcmc);//工程名称
				
				var dwmc_all = "";//变更前的建设单位名称
				var len = data.applyUnit.length;//查询到的申请单位行数
				for(var i=0;i<len;i++){
					if(i>0)$("img[title='增行']").eq(i-1).click();//增加一行申请单位
					dwmc_all += data.applyUnit[i].sqdw+"、";//拼接 变更前的建设单位名称
					$("input[name='c_296_807_qhdt_sqdw']").eq(i).val(data.applyUnit[i].sqdw);//申请单位
					$("input[name='c_296_807_qhdt_fddbr']").eq(i).val(data.applyUnit[i].fddbr);//法定代表人
					$("input[name='c_296_807_qhdt_sfzhm']").eq(i).val(data.applyUnit[i].sfzh);//身份证号码
				}
				dwmc_all = dwmc_all.substring(1,dwmc_all.length-1);//去掉最后一个“、”
				
				var row = $("input[name='c_296_807_qhdt_sqdw']").length;//已存在的申请单位行数
				if(row>len){//已存在的行数大于查询得到的行数
					for(var i=row;i>len;i--){
						$("img[title='删除']").eq(i-1).click();//删除多余的申请单位行
					}
				}
				
				$("#skqz_PROJECTNO").val(data.xmbh);//项目编号
				$("#skqz_PROJECTNAME").val(data.xmmc);//项目名称
				if(data.szdxm=="是"){
					$('input[name="skqz_sfzdxm"]').eq(0).attr("checked","checked");
				}else if(data.szdxm=="否"){
					$('input[name="skqz_sfzdxm"]').eq(1).attr("checked","checked");
				}
				
				$("#skqz_dwmc1").val(dwmc_all);//变更前的建设单位名称
				$("#skqz_dwmc3").val(data.sgdw);//变更前的施工单位名称
				$("#skqz_dwmc5").val(data.jldw);//变更前的监理单位名称
				
				$("#skqz_bgqdxmjl").val(data.jzsmc);//变更前的项目经理
				$("#skqz_zyzch1").val(data.jzszch);//变更前的执业注册号
				$("#skqz_bgqdxmzj").val(data.xmzj);//变更前的项目总监
				$("#skqz_zyzch3").val(data.zmzjzch);//变更前的执业注册号
				
				$("#skqz_bgqdjsdwzlzr").val(data.jsdwxm);//变更前的建设单位质量主任
				$("#skqz_sfzh1").val(data.jsdwsfz);//变更前的身份证号
				$("#skqz_yddh3").val(data.jsdwyddh);//变更前的移动电话
				
				$("#skqz_bgqdjsdwaqzr").val(data.jszqzrxm);//变更前的建设单位安全主任
				$("#skqz_sfzh3").val(data.jsaqzrsfzh);//变更前的身份证号
				$("#skqz_yddh5").val(data.jsaqzryddh);//变更前的移动电话
				
				$("#skqz_bgqdsgdwzlzr").val(data.sgzlxm);//变更前的施工单位质量主任
				$("#skqz_sfzh5").val(data.sgzlsfz);//变更前的身份证号
				$("#skqz_yddh7").val(data.sgzlyddh);//变更前的移动电话
				
				$("#skqz_bgqdsgdwaqzr").val(data.sgaqxm);//变更前的施工单位安全主任
				$("#skqz_sfzh7").val(data.sgaqsfz);//变更前的身份证号
				$("#skqz_yddh9").val(data.szaqyddh);//变更前的移动电话
				
				$("#skqz_bgqdgcmc").val(data.gcmc);//变更前的工程名称
				
				$("#skqz_sgxkhtkg").val(data.htkgrq);//变更前的原施工许可证登记合同开工日期
				$("#skqz_ysgxkhtjgrq").val(data.htjgrq);//变更前的竣工日期
			}
		}
		closeDialog();
	});
}

/*************************施工许可更改 end******************************/