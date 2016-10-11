<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="../../share/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>深圳市龙岗区住房与建设局社会建设和民生创新专项资金网上申报系统</title>
		<meta name="keywords" content="深圳市龙岗区住房与建设局社会建设民生创新专项资金申报系统，社会建设，民生创新,深圳市龙岗区住房与建设局" />
		<meta name="description" content="深圳市龙岗区住房与建设局社会建设民生创新专项资金申报系统，社会建设，民生创新" />
		<link href="/css/mscx.css" rel="stylesheet" type="text/css" />
		<link href="/css/index.css" rel="stylesheet" type="text/css" />
		<script src="/js/Scripts/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="/js/zDialog/risenetDialog.js"></script>
		<script type="text/javascript" src="/js/login.js"></script>
		<script type="text/javascript" src="/js/main.js"></script>
		<script type="text/javascript" src="/js/civilHome.js"></script>

	</head>
	<body
		onload="MM_preloadImages('/images/menu/zxsb.png','/images/menu/zxsb_m.gif','/images/menu/gsyy.png','/images/menu/gsyy_m.gif','/images/menu/pgzj.png','/images/menu/pgzj_m.gif','/images/menu/pgjg.png','/images/menu/pgjg_m.gif')">
		<div id="containerzjfc">
			<jsp:include page="/WEB-INF/page/home/menu.jsp"></jsp:include>

			<div class="mainzjfc">
				<table border="0" align="center" cellpadding="2" cellspacing="0">
					<tr>
						<td width="218px">
							<!-- 登陆-->
							<div class="loginzjfc" id="longinUserInfo">
								<s:if test="#session.loginUser==null">
									<jsp:include page="/WEB-INF/page/login/info.jsp"></jsp:include>
								</s:if>
								<s:else>
									<jsp:include page="/WEB-INF/page/login/logInfo.jsp"></jsp:include>
								</s:else>
							</div>
						</td>
						<!-- 通知公告 -->
						<td>
							<div class="windowszjfc">
								<div class="noticezjfc">
									<h4 style="font-weight: bold;">
										通知公告&nbsp;&nbsp;
										<a href="/civilNotice/queryPage.YS"
											style="font-size: 12px; color: #FFFFFF; position: relative; right: -400px;"
											target="_blank">更多&gt;&gt;</a>
									</h4>
									<ul>
										<s:iterator value="civilNoticeList" status="laws">
											<li>
												<a
													href="/civilNotice/findNotice.YS?guid=<s:property value="id"/>"
													target="_blank" title="<s:property value="title"/>"><img
														src="/images/bizbankroll/right_dian.jpg" />&nbsp;&nbsp;<s:property
														value="title" /> </a>
											</li>
										</s:iterator>
									</ul>
								</div>
							</div>
						</td>
						<!-- 政策法规-->
						<td>
							<div class="main_gtzjfc">
								<div class="noticezjfc1">
									<h4 style="font-weight: bold;">
										政策法规&nbsp;&nbsp;
										<a href="/civilPolicy/queryPage.YS"
											style="font-size: 12px; color: #FFFFFF; position: relative; right: -70px;"
											target="_blank">更多&gt;&gt;</a>
									</h4>
									<ul>
										<s:iterator value="civilPolicyList" status="laws">
											<li>
												<a
													href="/civilPolicy/findPolicy.YS?guid=<s:property value="id"/>"
													target="_blank" title="<s:property value="title"/>"><img
														src="/images/bizbankroll/right_dian.jpg" />&nbsp;&nbsp;
														<s:if test="%{title!=null&&title.length()>13}">
																	<s:property value="%{title.substring(0,13)}" />...
                  												</s:if>
																<s:else>
																	<s:property value="title" />
																</s:else>
														</a>
											</li>
										</s:iterator>
									</ul>
								</div>

							</div>
						</td>
					</tr>
					</tale>
					<tr>
						<td colspan="3">
							<a href="#" onclick="doApply();"><img src="/images/civilbankroll/zxsb.png" border="0"
									width="247" height="64"
									onmouseover="chageImg(this,'0','zxsb');"
									onmouseout="chageImg(this,'1','zxsb');" />
							</a>
							<a href="#" onclick="assOrganization()"><img src="/images/civilbankroll/pgjg.png" border="0"
									width="247" height="64"
									onmouseover="chageImg(this,'0','pgjg');"
									onmouseout="chageImg(this,'1','pgjg');" />
							</a>
							<a href="#" onclick="assSpecialist()"><img src="/images/civilbankroll/pgzj.png" border="0"
									width="247" height="64"
									onmouseover="chageImg(this,'0','pgzj');"
									onmouseout="chageImg(this,'1','pgzj');" /> </a>
							<a href="/civilPublicity/queryPage.YS" target="_blank"><img src="/images/civilbankroll/gsyy.png" border="0"
									width="247" height="64"
									onmouseover="chageImg(this,'0','gsyy');"
									onmouseout="chageImg(this,'1','gsyy');" /> </a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div class="main_ftzjfc1">
						<!-- 开始 -->
						<div class="winzjfc">
									<h3>
										<table width="100%">
											<tr>
												<td style="text-align: left">
													&nbsp;&nbsp;实施中项目
												</td>
												<td style="text-align: right">
													总件数：
													<s:property value="stat.countReceive" />
													&nbsp;&nbsp;今日件：
													<s:property value="stat.todayReceive" />
													&nbsp;&nbsp; 本月件：
													<s:property value="stat.monthReceive" />
													&nbsp;&nbsp;在办件：
													<s:property value="stat.transactCount" />
													&nbsp;&nbsp; 办结件：
													<s:property value="stat.finishCount" />
													&nbsp;&nbsp;&nbsp;&nbsp;<a href="/civilDynamic/queryPage.YS" target="_blank">更多</a>
												</td>
											</tr>
										</table>
									</h3>
									<div class="tb">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tbody>
												<tr>
													<td class="t_head" width="15%">
														业务编号
													</td>
													<td class="t_head" width="15%">
														项目类别
													</td>
													<td class="t_head" width="20%">
														项目名称
													</td>
													<td class="t_head" width="20%">
														申报主体
													</td>
													<td class="t_head" width="20%">
														实施主体
													</td>
													<td class="t_head" width="10%">
														发布时间
													</td>
												</tr>
											</tbody>
										</table>
										<div id="maq" style="overflow: hidden; height: 210px;">
											<div id="mtext">
												<table width="100%" border="0" cellspacing="0"
													cellpadding="0">
													<s:iterator value="civilApplicationList" status="busin">
														<tr>
															<td class="t_dttd" width="15%">
																<s:property value="slbh" />
															</td>
															<td class="t_dttd" width="15%">
																<s:property value="xmlxname" />
															</td>
															<td class="t_dttd" width="20%">
																<s:if test="%{xmname!=null&&xmname.length()>11}">
																	<s:property value="%{xmname.substring(0,11)}" />...
                  												</s:if>
																<s:else>
																	<s:property value="xmname" />
																</s:else>
															</td>
															<td class="t_dttd" width="20%">
																<s:if test="%{sbztid!=null&&sbztid.length()>11}">
																	<s:property value="%{sbztid.substring(0,11)}" />...
                  												</s:if>
																<s:else>
																	<s:property value="sbztid" />
																</s:else>
															</td>
															<td class="t_dttd" width="20%">
																<s:if test="%{sszt!=null&&sszt.length()>11}">
																	<s:property value="%{sszt.substring(0,11)}" />...
                  												</s:if>
																<s:else>
																	<s:property value="sszt" />
																</s:else>
															</td>
															<td class="t_dttd" width="10%">
																<s:property value="createtimeStr" />
															</td>
														</tr>
													</s:iterator>
												</table>
											</div>
											<div id="m0"></div>
										</div>
									</div>

								</div>
								<!-- 结束 -->
							</div>
						</td>
						<td>
							<div class="main_zxzjfc">
								<!-- 咨询电话 -->
								<div class="noticezjfc2">
									<h4>
										咨询电话
									</h4>
									<ul>
										<li style="line-height: 19px; margin-left: 8px;">
											&nbsp;技术支持联系人（张工）：&nbsp;25666484
										</li>
										<li style="line-height: 19px; margin-left: 8px;">
											&nbsp;领导小组办公室：&nbsp;25666905
										</li>
									</ul>
								</div>
							</div>
						</td>
					</tr>

				</table>
			</div>
			<!-- 版权 -->
			<jsp:include page="/WEB-INF/page/home/bottom.html" />
		</div>
	</body>
</html>
<script type="text/javascript">
var speed=40 //调整滚动速度
m0.innerHTML=mtext.innerHTML;
function Marquee(){
	if(m0.offsetTop-maq.scrollTop<=0){
		maq.scrollTop-=mtext.offsetHeight;
	}else{
		maq.scrollTop++;
	}
}
var MyMar=setInterval(Marquee,speed);
maq.onmouseover=function() {
	clearInterval(MyMar);
}
maq.onmouseout=function() {
	MyMar=setInterval(Marquee,speed);
}
changeMenuBg(13,13);
</script>