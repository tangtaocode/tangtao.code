/**
 * 门户相关js 
 **/

function homeTodoPage(page,pageSize,guids){
	showShade("todo");
	$("#gtasks").html("");
	$("#page").attr("value",page);
	var todoType = $("#todoType").val();
	var title = $("#title").val();
	var s_date = $("#s_date").val();
	var e_date = $("#e_date").val();
	$.ajax({
		url:"/oneHome/approveTodo",
		type:"get",
		cache:false,
		data:{"page":page,"pageSize":pageSize,"todoType":todoType,"title":title,"s_date":s_date,"e_date":e_date,"guids":guids,"randomId":getSessionRandom()},
		dataType:"json",
		success:function(data){
			if(data!=null&&data.pageList!=null&&data.pageList.length>0){
				$("#gtasks").html(forRows(data,pageSize*(page-1)));
				pageRow(data.pageCount,data.pageSize,guids);
			}else{
				$("#gtasks").html("<div align=\"center\" style='padding-top:100px;'>没有待办事项</div>");
				pageRow(data.pageCount,data.pageSize,guids);
			}
			hideShade("todo");
		},error:function(XMLHttpRequest,textStatus,errorThrown){
		       alert(textStatus);
			hideShade("todo");
			$("#gtasks").html("<div align=\"center\" style='padding-top:100px;'>获取待办事项失败，请稍后再试.</div>");
		}
	});
	
}

function forRows(data,rowIndex){
	var rs = "";
	var gtasks = data.pageList;

	for(var i=0;i<gtasks.length;i++){
		var content = gtasks[i].title;
		var gongchengmingcheng = gtasks[i].gongchengmingcheng;
		if(gongchengmingcheng==null||gongchengmingcheng=="undefine"||gongchengmingcheng=="null"||gongchengmingcheng==""){
			gongchengmingcheng = "";
		}else{
			gongchengmingcheng = gtasks[i].gongchengmingcheng;
		}
		var showContent = "<b>"+gongchengmingcheng+"</b>《"+content+"》";
		
		rs += "<li><span><img ";
		if(gtasks[i].status=="1"){
			rs+="src=\"/images/newimages/db03.gif\" width=\"30\" height=\"20\" /></span>";//平件
		}
		if(gtasks[i].status=="2"){
			rs+="src=\"/images/newimages/db03.gif\" width=\"30\" height=\"20\" /></span>";//只剩两天
		}
		if(gtasks[i].status=="3"){
			rs+="src=\"/images/newimages/db03.gif\" width=\"30\" height=\"20\" /></span>";//未受理
		}
		if(gtasks[i].status=="4"){
			rs+="src=\"/images/newimages/db02.gif\" width=\"30\" height=\"20\" /></span>";//只剩一天
		}
		if(gtasks[i].status=="5"){
			rs+="src=\"/images/newimages/db01.gif\" width=\"30\" height=\"20\" /></span>";//超期件
		}
		rs+="<a  href='javascript:void(0);' onclick=\"javascript:newWindow('"+gtasks[i].url+"');\"' title='"+content+"'>"+showContent+"</a> "+gtasks[i].todoDate+"</li>";
	}
	return rs;
}
function pageRow(pageCount,pageSize,guids){
	var currentPage = parseInt($("#page").val());
	var rs="<tr><td>共"+pageCount+"页  &nbsp;当前第"+currentPage+"页</td>";
	rs+="<td><div><a href=\"javascript:homeTodoPage(1,9,'"+guids+"')\">首页</a>";
	if(currentPage>1)
		rs+="<a href=\"javascript:homeTodoPage("+(currentPage-1)+","+pageSize+",'"+guids+"')\">上一页</a>";
	else 
		rs+="<a href='javascript:void(0);'>上一页</a>";
	
	if(currentPage<pageCount)
		rs +="<a href=\"javascript:homeTodoPage("+(currentPage+1)+","+pageSize+",'"+guids+"')\">下一页</a>";
	else
		rs+="<a href='javascript:void(0);'>下一页</a>";
	rs+="</div></td></tr>";
	$("#pageNum").html(rs);
}

/**
 * 业务跟踪列表
 */
/*==业务跟踪开始===*/
function homeDoingPage(page,pageSize){
	showShade("doing");
	$("#followtasks").html("");
	$("#doing_page").attr("value",page);
	var todoType = $("#doingType").val();
	var title = $("#doingtitle").val();
	var s_date = $("#doings_date").val();
	var e_date = $("#doinge_date").val();
	$.ajax({
		url:"/oneHome/approveDoing",
		type:"get",
		cache:false,
		data:{"page":page,"pageSize":pageSize,"todoType":todoType,"title":title,"s_date":s_date,"e_date":e_date,"randomId":getSessionRandom()},
		dataType:"json",
		success:function(data){
			if(data!=null&&data.pageList!=null&&data.pageList.length>0){
				$("#followtasks").html(forRowsDoing(data,pageSize*(page-1)));
				pageRowDoing(data.pageCount,data.pageSize);
			}else{
				$("#followtasks").html("<div align=\"center\" style='padding-top:100px;'>没有跟踪事项</div>");
				pageRowDoing(data.pageCount,data.pageSize);
			}
			hideShade("doing");
		},error:function(XMLHttpRequest,textStatus,errorThrown){
			alert(textStatus);
			hideShade("doing");
			$("#followtasks").html("<div align=\"center\" style='padding-top:100px;'>获取跟踪事项失败，请稍后再试.</div>");
		}
	});
}

function forRowsDoing(data,rowIndex){
	var rs = "";
	var gtasks = data.pageList;

	for(var i=0;i<gtasks.length;i++){
		var content = gtasks[i].title;
		var gongchengmingcheng = gtasks[i].gongchengmingcheng;
		if(gongchengmingcheng==null||gongchengmingcheng=="undefine"||gongchengmingcheng=="null"||gongchengmingcheng==""){
			gongchengmingcheng = "";
		}else{
			gongchengmingcheng = gtasks[i].gongchengmingcheng;
		}
		var showContent = "<b>"+gongchengmingcheng+"</b>《"+content+"》【"+gtasks[i].statedescription+"】";
		rs += "<li><span><img ";
		rs+="src=\"/images/newimages/db04.gif\" width=\"30\" height=\"20\" /></span>";
		rs+="<a  href='javascript:void(0);' onclick=\"javascript:newWindow('"+gtasks[i].url+"');\"' title='"+content+"'>"+showContent+"</a> "+gtasks[i].todoDate+"</li>";
	}
	return rs;
}
function pageRowDoing(pageCount,pageSize){
	var currentPage = parseInt($("#doing_page").val());
	var rs="<tr><td>共"+pageCount+"页  &nbsp;当前第"+currentPage+"页</td>";
	rs+="<td  align='left'><div><a href=\"javascript:homeDoingPage(1,9)\">首页</a>";
	if(currentPage>1)
		rs+="<a href='javascript:homeDoingPage("+(currentPage-1)+","+pageSize+")'>上一页</a>";
	else 
		rs+="<a href='javascript:void(0);'>上一页</a>";
	if(currentPage<pageCount)
		rs +="<a href='javascript:homeDoingPage("+(currentPage+1)+","+pageSize+")'>下一页</a>";
	else
		rs+="<a href='javascript:void(0);'>下一页</a>";
	$("#doingpageNum").html(rs);
}
/*==业务跟踪结束===*/


function homeZljh(){
	$.ajax({
		url:"/oneHome/exchangeFile",
		type:"post",
		data:{"num": 8,"randomId":getSessionRandom()},
		dataType:"json",
		success:function(data){
			var todoHtml = "";
		      $.each(data, function(index, todo) {
		    	  var title = todo.TITLE;
		    	  if(title.length>50){
		    		  title = title.substring(0,50)+"...";
		    	  }
		          todoHtml += "<li><a href=\"JavaScript:openLayer('资料接收','"+todo.NEWOAURL+"/exchangedocnew/docShow.jsp?guid="
		        	  		+ todo.DOCGUID+"&rguid="+todo.RGUID+"',800,600,homeZljh);void(0);\" title='" 
		        	  		+ todo.TITLE + "'>" + title + "(" + todo.SENDTIME + ")</a></li>";
		      });
		      $("#cardTab2 > .index_db02").html("<ul>" + todoHtml + "</ul>");
		},error:function(XMLHttpRequest,textStatus,errorThrown){
			//alert(textStatus);
		}
	});
}


function openLayer(name,src,width,height,func){
	  $.layer({
		    type: 2,
		    shade : [0.6, '#CCC'],
		    fix: true,
		    title: name,
		    maxmin: true,
		    iframe: {src : src},
		    area: [width+'px' , height+'px'],
		    offset: [($(window).height() - 600)/2+'px', ''],
		    close:func
		});
}

function closeLayer(){
	layer.closeAll();
}
/**二级菜单显示与关闭*/
function openMenu(name){
	var clasName=$("#"+name).attr('class');
	$('#leftTree a').each(function(){
		if($(this).attr('id')==name){//选中的
			if(clasName.indexOf("index_nav")>=0){
				$("[name="+name+"]").each(function(){
					$(this).css('display','block'); 
				});
			}
		}else{
			$('#leftTree div').each(function(){
				if($(this).attr('name')!=name){
					$(this).css('display','none'); 
				}
			});

		}
	});
}


function changeClass(thisa,className){
	$(thisa).removeClass();
	$(thisa).addClass(className);
}

//去掉遮罩层
function hideShade(type){
	if(type=="todo"){
		$("#gtasks_loading").hide();
	}else{
		$("#followtasks_loading").hide();
	}
	//$("#shade").css("display","none");
}
//添加遮盖层
function showShade(type){
	//var bH=$("body").height();
    //var bW=$("body").width()+16;
	if(type=="todo"){
		 $("#gtasks_loading").show();
	}else{
		 $("#followtasks_loading").show();
	}
    //$("#shade").css({width:bW,height:bH,display:"block"});
}

function newWindow(url){
	 window.open(url);
	return;
}
/**弹出层对象*/
function poptip(){
	$.ajax({
		url:"/checkData/getOverCounts",
		type:"get",
		cache:false,
		dataType:"json",
		success:function(data){
		var tiphtml="<ul>";
		tiphtml+="<li><img src='/images/newimages/tx01.gif' width='30' height='15'><a href='#'>您有<strong>"+data.jianChaCounts+"</strong>条问题数据必须今天处理！</a></li>";
		//tiphtml+="<li><img src='/images/newimages/tx02.gif' width='30' height='15'><a href='#'>您有<strong>"+data.hourCounts+"</strong>条数据已超时报送！</a></li>";	
		tiphtml+="</ul>";
		$("#pop").find("ul").remove();
		$("#pop").append(tiphtml);
		new Pop();
			
		},error:function(XMLHttpRequest,textStatus,errorThrown){
		       //alert("错提示"+textStatus);
		}
	});
}
/**打开查询提示超期数据列表*/
function opentiplist(){
	var url="/oneHome/moreTodo?guids="+$("#guids").val();
	window.open(url);
}
/**隐藏左侧栏目*/
function hidenleft(){
	var display = $("#leftTree").css("display");
	if(display=='block'){
		$("#leftTree").css('display','none');
		$(".index_right").css('margin-left','0px');
		$(".index_close").css('left','0px');
	}else{
		$("#leftTree").css('display','block');
		$(".index_right").css('margin-left','200px');
		$(".index_close").css('left','200px');
	}
}
/**返回首页**/
function rebackhome(){
	var screenW = screen.width;
	var maxsize = 14;//最高分辨率容纳应用最大数
	var curentsize = maxsize;//当前能容纳应用数
	if(screenW<1440&&screenW>=1366){
		curentsize = maxsize-2;
		}
	if(screenW<1366&&screenW>=1280){
		curentsize = maxsize-4;
	}
	window.location.href='/oneHome/index?curentsize='+curentsize;
}
/**关闭窗口*/
function closehome(){
	window.close();
}
/**切换窗口内容*/
function changetable(obj){
	if(obj=="checkhour"){
		$("#tablecontent").attr("src","/checkData/hourHtml");	
	}
	if(obj=="checkdata"){
		$("#tablecontent").attr("src","/checkData/jianchaHtml");	
	}
	if(obj=="sms"){
		$("#tablecontent").attr("src","/Sms/toSmsHtml");	
	}
}

/**手工校验审批过程数据*/
function handwordCheck(){
	$.ajax({
		url:"/oneHome/handworkcheck",
		type:"post",
		data:{},
		dataType:"json",
		beforeSend:function (xml){
			layer.load(100,2,false);
		},
		success:function(data){
			if(data.outcome=='1'){
				layer.closeAll('loading');
				layer.alert('手工校验数据成功！',{icon: 0})
				changetable("checkdata");
			
			}else{
				layer.alert("手工校验数据失败！");
			}
		}
	});
}

/**打开问题详细列表*/
function openProblemListWin(sblsh){
	Dialog.open({URL:"/checkData/problemDataHtml?sblsh=" + sblsh,Title:"问题详细描述列表",Width:600,Height:500});
}