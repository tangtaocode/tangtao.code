<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<title>数据统计</title>
<style type="text/css">
label.legend{display:inline-block;font-weight: normal;margin-right: 5px;font-size: 12px;cursor: pointer;}
label.legend.disabled{color: #d3d3d3 !important;}
label.legend.disabled .label{background-color: #d3d3d3 !important;}
label.legend .label{width: 12px;height: 8px;margin-right: 5px;vertical-align: middle;margin-bottom: 2px;}
</style>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<!--框架必需end-->
<script src="${ctx}/static/risesoft/js/echarts/echarts.common.min.js"></script>
<script type="text/javascript">
	$(function(){
		initCharts('pie');
	})
</script>
<script type="text/javascript">
function initCharts(type){
	// 基于准备好的dom，初始化echarts图表
    var myChart = echarts.init(document.getElementById('main'));
    myChart.on('click', function (param) {
        alert(param.name);
		});

    var x_ = eval('${categories}');
    var y_ = eval('${values}');
      
    var option = {
    	title:{
    		show:true,
    		text:'季度业务量趋势图',
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
   	            restore : {show: true}
   	    	},
   	    	calculable : true, saveAsImage : {show: true},
   	    	left:'right',
   	    	top:'center',
   	    	orient : 'vertical',
   	    },
    	//控制数据显示不全
    	grid: { // 控制图的大小，调整下面这些值就可以，
            x: 100,
            x2: 100,
            y2: 150,// y2可以控制 X轴跟Zoom控件之间的间隔，避免以为倾斜后造成 label重叠到zoom上
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
            data:['业务量','业务量2']
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
                data : x_
            }
        ],
        yAxis : [
            {
                type : 'value',
            }
        ],
        series : [
            {
                "name":"业务量",
                "type":"line",
                "data":y_,
                markPoint: {  
                	data: [  
                		{type: 'max', name: '最大值'},
                		{type: 'average', name: '平均值'},
                		{type: 'min', name: '最小值'}  
                	]  
                },  
                itemStyle : { normal: {label : {show: true, position: 'top'}}}  //在柱状图上显示数字
            }
        ]
    };
    // 为echarts对象加载数据 
    myChart.setOption(option,true); 
}
</script>
</head>
<body>
<div class="basicTabProcess" style="width:800px">

    <div name="2014" style="height:100px;" iconClass="process_item1">

        <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1000px;height:500px;"></div>

    </div>

    <div name="2015" style="height:100px;" iconClass="process_item2">

       <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1000px;height:500px;"></div>

    </div>

    <div name="2016" style="height:100px;" iconClass="process_item3">

      <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1000px;height:500px;"></div>

    </div>

</div>
 
	
	
	
</body>

</html>
