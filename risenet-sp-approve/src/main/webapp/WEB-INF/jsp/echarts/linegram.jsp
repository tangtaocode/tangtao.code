<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>数据统计</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<!--框架必需end-->
<script src="${ctx}/static/risesoft/js/echarts/echarts.min.js"></script>


  
</head>
<body>
<div class="box2" id="searchPanel">
		<form action="${ctx}/chart/line" id="queryForm" method="get" align="center">
		<table>
			<tr>
				<td>数据来源:</td>
				<td>
					<select name="dataSource" id="dataSource"  prompt="" url="${ctx}/codeMap/dataSource" boxWidth="200" boxHeight="200" selWidth="180" selectedValue="${dataSource }"></select>
				</td>
				<td>年度:</td>
				<td>
				<input type="hidden" name="type" value="${type }">
				<input id="year" name="year" type="text" class="Wdate" value="${year }" onfocus="WdatePicker({dateFmt:'yyyy'});"/></input></td>			
				
				<td><button type="button" onclick="searchHandler()"><span  style="cursor:pointer;" class="icon_chart_line">查询统计</span></button></td>
			</tr>
		</table>
		</form>
	</div>
 <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1000px;height:500px;"></div>
	
	
</body>

<script type="text/javascript">


		function initCharts(data){
			if('${type}'=='online'){
				var title = '罗湖区'+'${year}'+"年各季度网上办理率曲线图";
				var legend = ['网上办理率'];
				var text='网上办理率';
				var yName = '百分比(%)';
			}else{
				var title = '罗湖区'+'${year}'+"年各季度业务量曲线图";
				var legend = ['业务量'];
				var text='业务量';
				var yName='业务量';
			}
			
			//var bureau = $("#bureauGUID").attr("selectedValue");
			//var text = $("#bureauGUID").attr("relText");
			//alert(text);
			//if(bureau!=''&& text!=undefined){
			//	title= text+title;
			//}else{
				//title='罗湖区'+title;
			//}
			// 基于准备好的dom，初始化echarts图表
	        var myChart = echarts.init(document.getElementById('main'));
			var year = $("#year").val();
	        myChart.on('click', function (param) {
	        	if('${type}'!='online'){
	        		window.top.openNewTab("季度统计","${ctx}/chart/byQuart?quart="+param.name+"&year="+year,"");
	        	}
     		});

	        var x_ = ['第一季度','第二季度','第三季度','第四季度'];
	        var value = data;
	       
	          
	        var option = {
	        	title:{
	        		show:true,
	        		text:title,
	        		left:100,
	        		textStyle:{
	        			color:'black',
	        			fontWeight:'bolder'
	        		}
	        	},
	        	//设置工具lan
	       	 	toolbox: {
	       	        show : true,
	       	        feature : {
	       	            mark : {show: true},
	       	            dataView : {show: true, readOnly: false},
	       	            magicType : {show: true, type: ['line', 'bar']},
	       	            restore : {show: true},
	       	         	color:['#1e90ff','#22bb22','#4b0082','#d2691e']
	       	    	},
	       	    	calculable : true, saveAsImage : {show: true},
	       	    	left:'right',
	       	    	top:'center',
	       	    	orient : 'vertical'
	       	    },
	        	//控制数据显示不全
	        	grid: { // 控制图的大小，调整下面这些值就可以，
	                x: 100,
	                x2: 100,
	                y2: 150// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
	            },

	            tooltip: {
	                show: true,
	                backgroundColor:"green",
	                trigger:'axis',
	                showDelay : 0, // 显示延迟，添加显示延迟可以避免频繁切换，单位ms
	                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	                    type : 'line'        // 默认为直线，可选为：‘line‘ | ‘shadow‘
	                }

	            },
	            legend: {
	            	show:true,
	            	left:700,
	                data:legend
	            },
	            xAxis : [
	                {
	                    type : 'category',
	                    axisLabel: {
	                        show: true,
	                        interval: '0', 
	                       	textStyle: {
	                            color: 'green',
	                            fontFamily: 'verdana',
	                            fontSize: 12,
	                            fontStyle: 'normal',
	                            fontWeight: 'bold'
	                        },
	                       //rotate: 60 , //文字倾斜
	                       //文字竖直显示
	                        formatter:function(val){
	                            return val.split("").join("\n");
	                        }
	                    },
	                    nameGap:10,
	                    boundaryGap:true,
	                    data : ['第一季度','第二季度','第三季度','第四季度']
	                }
	            ],
	            yAxis : [
	                {
	                    type : 'value',
	                    name:yName
	                }
	            ],
	            series : [
	                {
	                    "name":text,
	                    "type":"line",
	                    "data":value,
	                   	
	                    itemStyle : { normal: {label : {show: true, position: 'top'}}}  //在柱状图上显示数字
	                }
	               
	            ]
	        };
	        // 为echarts对象加载数据 
	        myChart.setOption(option,true); 
		}

    window.onload = function(){
    	var data=eval('${values}');
    	var dataSource = eval('${list }');
    	var json = eval(dataSource);
    	$("#dataSource").attr("data",dataSource);
    	if(data==null || data==""){
    		alert("暂无业务量");
    		data=[0,0,0,0];
    	}
    	initCharts(data);

    }
    	function searchHandler(){
    		$("#queryForm").submit();
    	}

    </script>
</html>
