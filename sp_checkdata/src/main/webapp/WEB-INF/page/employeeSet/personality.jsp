<%@ page language="java" contentType="text/html; charset=gbk" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<title>个性化设置</title>
<script type="text/javascript" src="/js/jquery-1.8.2.js"></script>
<link href="../../themes/div/css/div.css" rel="stylesheet" type="text/css" />
<script type="text/JavaScript">
	$(document).ready(function() {
		//蓝色
		$("#blue").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "blue"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//粉色
		$("#powder").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "powder"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//绿色
		$("#green").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "green"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//山水
		$("#montion").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "montion"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//麦田
		$("#catcher").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "catcher"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//荷花 scene
		$("#lotus").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "lotus"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//风光
		$("#scene").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "scene"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//浪漫都市
		$("#city").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "city"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//金色稻田
		$("#paddy").bind("click", function() {
			$.post("/commons/common.jsp", {
				skin : "paddy"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//字体大
		$("#big").bind("click", function() {
			$.post("/commons/common.jsp", {
				fontRe : "14"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//字体小
		$("#litter").bind("click", function() {
			$.post("/commons/common.jsp", {
				fontRe : "12"
			}, function(msg) {
				window.top.location.reload();
			});
		});
		//上一页
		$("#pgup").bind("click", function() {
			$("#pagetwo").hide();
			$("#pgup").hide();
			$("#pageone").show();
			$("#pgdn").show();
		});
		//下一页
		$("#pgdn").bind("click", function() {
			$("#pageone").hide();
			$("#pgdn").hide();
			$("#pagetwo").show();
			$("#pgup").show();
		});
		$("#changeskin").bind("click", function() {
			$("#divskin").center();
			$("#divskin").fadeIn();
			return false;
		});
		$("#resetskin").click(function() {
			$("#divskin").fadeOut(function() {
				$('#screen').hide();
			});
			return false;
		});
		$("#sure").bind("click", function() {
			$.post("saveOrUpdateSkin.jsp", function(msg) {
				alert("保存成功！")
			});
		});
	});
	jQuery.fn.center = function(loaded) {
		var obj = this;
		body_width = parseInt($(window).width());
		body_height = parseInt($(window).height());
		block_width = parseInt(obj.width());
		block_height = parseInt(obj.height());

		left_position = parseInt((body_width / 2) - (block_width / 2)
				+ $(window).scrollLeft());
		if (body_width < block_width) {
			left_position = 0 + $(window).scrollLeft();
		}
		;

		top_position = "150px";
		if (body_height < block_height) {
			top_position = 0 + $(window).scrollTop();
		}
		;

		if (!loaded) {

			obj.css({
				'position' : 'absolute'
			});
			obj.css({
				'top' : top_position,
				'left' : left_position
			});
			$(window).bind('resize', function() {
				obj.center(!loaded);
			});
			$(window).bind('scroll', function() {
				obj.center(!loaded);
			});

		} else {
			obj.stop();
			obj.css({
				'position' : 'absolute'
			});
			obj.animate({
				'top' : top_position
			}, 100, 'linear');
		}
	}
</script>
</head>
<body>
  <div class="top_left">
    &nbsp;<a href="#" id="changeskin">换肤</a>&nbsp;&nbsp;字体：<a href="#" id="big">大</a>&nbsp;<a href="#" id="litter">小</a>
  </div>
  <!-- div 开始 -->
  <div class="div1" id="divskin">
    <div class="div2" id="pageone">
      <ul>
        <li>
          <div class="div3">
            <a href="#" id="blue"><img src="/themes/div/images/blue.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">蓝色风情</div>
        </li>
        <li>
          <div class="div3">
            <a href="#" id="green"><img src="/themes/div/images/green.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">绿意盎然</div>
        </li>
        <li>
          <div class="div3">
            <a href="#" id="powder"><img src="/themes/div/images/powder.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">粉色回忆</div>
        </li>
        <li>
          <div class="div3">
            <a href="#" id="montion"><img src="/themes/div/images/montion.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">高山流水</div>
        </li>
        <li>
          <div class="div3">
            <a href="#" id="lotus"><img src="/themes/div/images/lotus.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">荷花</div>
        </li>
        <li>
          <div class="div3">
            <a href="#" id="catcher"><img src="/themes/div/images/catcher.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">麦田</div>
        </li>
      </ul>
    </div>
    <div class="div2" id="pagetwo" style="display: none;">
      <ul>
        <li>
          <div class="div3">
            <a href="#" id="scene"><img src="/themes/div/images/scene.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">风光</div>
        </li>
        <li>
          <div class="div3">
            <a href="#" id="city"><img src="/themes/div/images/city.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">浪漫都市</div>
        </li>
        <li>
          <div class="div3">
            <a href="#" id="paddy"><img src="/themes/div/images/paddy.jpg" width="136" height="90" border="0" /></a>
          </div>
          <div class="div4">金色稻田</div>
        </li>
      </ul>
    </div>
    <div class="div5">
      <input name="pgup" type="button" class="but1" id="pgup" value="上一页" style="display: none;" /> <input name="pgdn" type="button" class="but1"
        id="pgdn" value="下一页" /> <input name="Submit2" type="submit" class="but1" id="resetskin" value="关闭" />
    </div>
  </div>
</body>
</html>