<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
Calendar cal=Calendar.getInstance();//使用日历类
int year=cal.get(Calendar.YEAR);//得到年
int month=cal.get(Calendar.MONTH)+1;//得到月，因为从0开始的，所以要加1
%>
<div align="center">
	<div id="div_F">
		<div align="left" style="display: none">
			<span id="Xdas"></span>
			<h6 id="Xh6"></h6>
		</div>
		<br />
		<div>
			<input id="nian" type="hidden" readonly="readonly"
				style="width: 40px;" value="<%=year%>" /> <input id="yue" type="hidden"
				readonly="readonly" style="width: 20px;" value="1" /> <img
				src="/images/rc_left.jpg" title=" 前一月" onclick="rili(2)" />&nbsp; <span
				id="Xday"></span>&nbsp; <img src="/images/rc_rit.jpg"  title="下一月"
				onclick="rili(1)" />
		</div>
		<table id="trh">
			<tr>
				<td>日</td>
				<td>一</td>
				<td>二</td>
				<td>三</td>
				<td>四</td>
				<td>五</td>
				<td>六</td>
			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="1">
			<tbody id="tbody">

			</tbody>
		</table>
	</div>
</div>


<script>
	// JavaScript Document\
	var date = new Date();//第一次登陆加载
	function rili(id) {
		//先清除，在添加
		qingchu();
		//获取页面上的数据
		var nian = Number(document.getElementById("nian").value);
		var yue = Number(document.getElementById("yue").value);
		//判断页面数据传输
		if (id == 3) {
			nian = date.getFullYear();
			yue = date.getMonth();
		}
		if (yue == 12 && id == 1) {//下一个月,而且之前的月份已经是十二月，所以年份必须加1
			yue = 0;
			nian = nian + 1;
		}
		if (yue == 1 && id == 2) {//上一个月，而之前的月份是一月，年份减1，月份回到十二
			yue = 11;
			nian = nian - 1;
		} else if (id == 2) {
			yue = yue - 2;
		}
		var op = yue + 1;
		if (id == 7) {
			nian = nian - 1;
			op = yue;
			yue = yue - 1;
			//yue=date.getMonth();
		}
		if (id == 8) {
			nian = nian + 1;
			//yue=date.getMonth();	
			op = yue;
			yue = yue - 1;
		}
		var ol = op < 10 ? "0" + op : op;
		document.getElementById("Xday").innerHTML = nian + "年" + (ol) + "月";
		document.getElementById("Xdas").innerHTML = date.getDate();
		document.getElementById("Xh6").innerHTML = nian + "年" + (ol) + "月"
				+ date.getDate() + "日";
		document.getElementById("nian").value = nian;
		document.getElementById("yue").value = op;
		var yy = 2012;// 固定不变量2012年
		var nextyy = nian// 下个年份.可以用传参来调用
		var nextYFday = 0;// 以2012年的第一个月的第一天为标准
		var months = [ 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
		if (nextyy % 4 == 0) {
			months[1] = 29;
		} else {
			months[1] = 28;
		}
		// 当年之前的年份计算
		if (nextyy < yy) {
			for ( var i = 0; i < yy - nextyy; i++) {
				if (nextYFday == 0) {
					nextYFday = 7;
				}
				if ((i + 1) % 4 == 0) {
					nextYFday = nextYFday == -1 ? 6 : nextYFday - 2;
				} else {
					nextYFday--;
				}
				nextYFday = Math.abs(nextYFday);
			}
			// 当年之后的计算
		} else {
			var odatef = new Date();        
			odatef.setFullYear(nextyy);
			odatef.setMonth(0)
			odatef.setDate(1);
			
		var	nextYFday = odatef.getDay();
		
		}
		var Fdays = new Array(12);
		Fdays[0] = Math.abs(nextYFday);// 第一个月的 由上面方法推算出来
		for ( var p = 0; p < 11; p++) {// 获取每个月第一天是星期几
			Fdays[p + 1] = (months[p] + Fdays[p]) % 7;
			
		}
           
		var is = 1;//要打印出的每个月的日期号
		//每个月要打印的行数
		var list = ((months[yue] + Fdays[yue]) / 7) <= 5 ? 5 : 6;
		for ( var i = 1; i <= list; i++) {
			var newRow = document.getElementById("tbody").insertRow();//自动生成表格
			if (i == 1 && Fdays[yue] != 0) {// 打上一个月的,Y可以用参数来代替，就是月份
				for ( var p = 0; p < 7; p++) {
					var u = "col" + p;
					
					if (p >= Fdays[yue]) {
						u = newRow.insertCell(p).innerHTML = "<div id='"
								+ lp(is)
								+ "'onmouseover='ov(this)' onclick='as(this)'  onmouseout='out(this)'><a >"
								+ is + "</a></div>";
						is++;
					} else {
						if (yue == 0 && p < Fdays[0]) {
							u = newRow.insertCell(p).innerHTML = "<div onmouseover='ov(this)' onclick='as(this)' onmouseout='out(this)'id='day-"
									+ (months[yue] - Fdays[yue] + (p + 1))
									+ "'><a style='color:#999999;'>"
									+ ""
									+ (months[yue] - Fdays[yue] + (p + 1))
									+ "</a></div>";
						} else {
							u = newRow.insertCell(p).innerHTML = "<div onmouseover='ov(this)' onclick='as(this)' onmouseout='out(this)' id='day-"
									+ (months[yue - 1] - Fdays[yue] + (p + 1))
									+ "'><a style='color:#999999;'>"
									+ ""
									+ (months[yue - 1] - Fdays[yue] + (p + 1))
									+ "</a></div>";
						}
					}
				}
			} else {
				if (is % 7 == 0) {
					newRow = document.getElementById("tbody").insertRow(i);
					var o = 1;
					for ( var p = 0; p < 7; p++) {
						var u = "col" + p;
						if (is > months[yue]) {
							if (yue == 11) {
								u = newRow.insertCell(p).innerHTML = "<div onmouseover='ov(this)' onclick='as(this)'   onmouseout='out(this)' id='day-"
										+ (o)
										+ "' "
										+ "><a style='color:#999999;'>"
										+ (o++)
										+ "</a></div>";
							} else {
								u = newRow.insertCell(p).innerHTML = "<div onmouseover='ov(this)' onclick='as(this)'  "
										+ "onmouseout='out(this)' id='day-"
										+ (o)
										+ "'><a style='color:#999999;'>"
										+ (o++) + "</a></div>";
							}
						} else {
							u = newRow.insertCell(p).innerHTML = "<div onmouseover='ov(this)' onclick='as(this)'  "
									+ "onmouseout='out(this)' id='"
									+ lp(is)
									+ "'><a >" + is + "</a></div>";
							is++;
						}
					}
				} else {
					var o = 1;
					for ( var p = 0; p < 7; p++) {
						var u = "col" + p;
						if (is > months[yue]) {
							if (yue == 11) {
								u = newRow.insertCell(p).innerHTML = "<div  onmouseover='ov(this)' onclick='as(this)'  "
										+ "onmouseout='out(this)' id='day-"
										+ (o)
										+ "'><a style='color:#999999;'>"
										+ (o++) + "</a></div>";
							} else {
								u = newRow.insertCell(p).innerHTML = "<div  onmouseover='ov(this)' onclick='as(this)' "
										+ "onmouseout='out(this)' id='day-"
										+ (o)
										+ "'><a style='color:#999999;'>"
										+ (o++) + "</a></div>";
							}
						} else {
							u = newRow.insertCell(p).innerHTML = " <div onmouseover='ov(this)' onclick='as(this)' onmouseout='out(this)' id='"
									+ lp(is) + "' ><a>" + is + "</a></div>";
							is++;
						}
					}
				}
			}
		}
		$.post("/oneHome/FindscheduleDatecount", {
			date : nian + "-" +op+ "-01"
		}, function(result) {
			for ( var i = 0; i < result.length; i++) {
				$("#" + result[i].TODAY).children().css("color", "red");
				//$("#" + result[i].TODAY).text(result[i].TODAY);
			}
		},"json");
		var yy = date.getDate();
		if (id == 3) {
			document.getElementById(lp(yy)).parentNode.style.backgroundColor = '#9DC3DA';
		}
		if (nian == date.getFullYear() && yue == date.getMonth()) {
			document.getElementById(lp(yy)).parentNode.style.backgroundColor = '#9DC3DA';
		}
		if (id != 3) {
			oobj = null;
		}
	}
	function lp(num){
		if(num>9){
			return num+"";
		}else{
			return "0"+num;
		}
	}
	var oobj;//作为一个中间量，作用是改变上一次点击的背景颜色
	function ov(obj) {//鼠标悬浮的颜色
		obj.parentNode.style.backgroundColor = rgbTo16Color(obj.parentNode.style.backgroundColor) == '#0000FF' ? '#0000FF'
				: '#FFFF99';
	}
	function out(obj) {//鼠标离开的颜色
		var nian = Number(document.getElementById("nian").value);
		var yue = Number(document.getElementById("yue").value) - 1;
		var cc = rgbTo16Color(obj.parentNode.style.backgroundColor)== '#0000FF' ? '#0000FF': '';
		obj.parentNode.style.backgroundColor = cc;
		//obj.parentNode.style.backgroundColor = '#F3FDFF';
		var yy = date.getDate();
		if (nian == date.getFullYear() && yue == date.getMonth()) {
		//	var bg = rgbTo16Color(document.getElementById(yy).parentNode.style.backgroundColor);
			//document.getElementById(yy).parentNode.style.backgroundColor = bg == '#0000FF' ? '#0000FF': '#9DC3DA';
			document.getElementById(lp(yy)).parentNode.style.backgroundColor = '#9DC3DA';
		}
	}
	function qingchu() {//先清除之前的在加载新的
		var rows = document.getElementById("tbody").rows;
		if (rows.length > 0) {
			for ( var r = 0; r <= rows.length; r++) {
				document.getElementById("tbody").deleteRow(r);
				rows = document.getElementById("tbody").rows;
				r--;
				if (rows.length == 0) {
					r = 2;
				}
			}
		}
	}
	function weekd() {
		var week = new Array("一", "二", "三", "四", "五", "六", "七");
		for ( var i = 1; i <= 7; i++) {
			if (date.getDay() == i) {
				return week[i - 1];
			} else if (date.getDay() == 0) {
				return week[6];
			}
		}
	}
	function as(obj) {
		obj.parentNode.style.backgroundColor = '#0000FF';
		if (oobj != null && oobj != this) {//为了改变颜色做的判断
			oobj.parentNode.style.backgroundColor = '';
			oobj = null;
		}
		if (oobj == null) {
			oobj = document
					.getElementById(obj.id);
		}
		var nn = Number(document
				.getElementById("Xday").innerHTML
				.substr(0, 4));//时间年份
		var yy = Number(document
				.getElementById("Xday").innerHTML
				.substr(5, 2));//时间月份
		var dd;//时间日期
		dd = obj.id.length == 5 ? obj.id
				.substr(4, 1) : obj.id;//判断日期是否属于本月
		dd = obj.id.length == 6
				&& obj.id.length != 5 ? obj.id
				.substr(4, 2)
				: dd;
		dd = obj.id.length == 1
				&& obj.id.length != 6
				&& obj.id.length != 5 ? obj.id
				: dd;
		yy = obj.id.length == 5 ? (yy + 1)
				: yy;//判断如果不是本月份 就给出相应的改变月份
		yy = obj.id.length == 6
				&& obj.id.length != 5 ? (yy - 1)
				: yy;
		nn = yy == 0 ? (nn - 1) : nn;
		nn = yy > 12 ? (nn + 1) : nn;
		yy = yy == 0 ? 12 : yy;//判断是否是上一年的最后一个月
		yy = yy > 12 ? "0" + 1 : yy;//判断是否是下一年的一月
		yy = yy.toString().length < 2 ? "0"
				+ yy : yy;
		document.getElementById("Xdas").innerHTML = obj.id.length == 1 ? dd
				: dd;
		dd = dd.length < 2 ? "0" + dd : dd;
		document.getElementById("Xh6").innerHTML = nn
				+ "年" + yy + "月d" + dd + "日";
		$.post("/oneHome/personSchedule",
						{
							"id" : nn + "-"
									+ yy
									+ "-"
									+ dd,
							"tab" : 'now',
							"length" : 3
						},
						function(list) {
							var ul = document
									.getElementById("jsul");
							if (list.schedule == "")
								ul.innerHTML = "<hr>暂无安排";
							else
								ul.innerHTML = "<hr>"+list.schedule;
						},"json");
	}
	
	function rgbTo16Color(rgb){
		if(rgb==null||rgb=="undefined"||rgb=="")
			return "";
	  else if(rgb.indexOf("\#")>-1)
		   return rgb;//如果是一个hex值则直接返回
	   else{
	      rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
	      function hex(x) {return ("0" + parseInt(x).toString(16)).slice(-2);}
	      rgb= "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
	      }
	   return rgb.toUpperCase();
	}
	
	$(document).ready(
					function() {
						var date = new Date();//刷新当天数据
						var nn = date.getFullYear();
						var yy = date.getMonth() < 10 ? "0"
								+ (date.getMonth() + 1) : (date.getMonth() + 1);//时间月份
						var dd = date.getDate() < 10 ? "0" + date.getDate()
								: date.getDate();
						$.post("/oneHome/personSchedule", {
							"id" : nn + "-" + yy,
							"tab" : "all",
							"length":3
						}, function(list) {
							var ul = document.getElementById("jsul");
							if (list.schedule == "")
								ul.innerHTML = "<hr>暂无安排";
							else
								ul.innerHTML = "<hr>"+list.schedule;
						},"json");
					});
</script>
<style>
.bt {
	width: 300px;
	background-color: #F8FBFC;
	height: 100px;
	border: 1px solid #EAF2F7;
	padding: 5px;
}

#trh {
	height: 10px;
	font-size: 14px;
	color: #999999;
	width: 100%;
	font-weight: bold;
	margin-top: 5px;
	text-align: center;
}

#Xday {
	font-size: 20px;
	font-weight: bold;
	color: #999999;
}

#tbody td {
	width: 20px;
	height: 20px;
	text-align: center;
	padding: 0px;
}

#tbody td div {
	width: 100%;
	text-align: center;
}

.Xbody {
	background-color: #F7F7F7;
	border: 1px solid #CCC;
}

#tbody a {
	text-align: center;
	text-decoration: none;
	background: transparent;
	font-size: 12px;
	width: 50%;
	color: black;
	padding-left: 10px;
	padding-right: 5px;
	outline: none;
	outline: none;
	blr: expression(this.onFocus =     this.blur ());
	cursor: hand;
}

#tbody span {
	font-size: 20px;
	font-weight: bold;
}

#sch {
	margin: 0px;
	overflow-x: hidden;
}

#Xdas {
	margin-left: 50px;
	font-size: 100px;
}

#Xh6 {
	margin-top: -15px;
	font-size: 16px;
	margin-left: 40px;
}
</style>




