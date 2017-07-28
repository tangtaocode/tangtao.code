<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>

<title>数据统计</title>
<!--框架必需start-->
<script type="text/javascript" src="${ctx}/static/QUI/libs/js/framework.js"></script>
<!--框架必需end-->

<script src="${ctx}/static/risesoft/js/echarts/echarts.common.min.js"></script>
<script type="text/javascript">
	function searchHandler(){
		$("#queryForm").submit();
	}
	function initComplete(){

	    var selData={"list":[{"value":"1","key":"第一季度"},{"value":"2","key":"第二季度"},{"value":"3","key":"第三季度"},{"value":"4","key":"第四季度"}]};

	    //赋给data

	    $("#quart").data("data",selData)

	    //刷新下拉框

	    $("#quart").render(); 

	}

</script>

</head>
<body>
	<div class="box2" id="searchPanel">
		<form action="${ctx}/chart/bar" id="queryForm" method="get" align="center">
		<table>
			<tr>
				<td>年度:</td>
				<td>
				<input id="year" name="year" type="text" class="Wdate" value="${year }" onfocus="WdatePicker({dateFmt:'yyyy'});"/></input></td>			
				<td>&nbsp;&nbsp;&nbsp;季度:</td>
				<td>
				<select id="quart" name="quart" prompt="" selectedValue="${quart }">
					
				</select>
				</td>
				<td>&nbsp;&nbsp;&nbsp;月份:</td>
				<td>
				<input id="month" name="month" type="text" class="Wdate" value="${month }" onfocus="WdatePicker({dateFmt:'MM'});"/></input></td>
				<td><button type="button" onclick="searchHandler()"><span  style="cursor:pointer;" class="icon_chart_bar">统计查询</span></button></td>
			</tr>
		</table>
		</form>
	</div>
 <!-- 为 ECharts 准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 1000px;height:500px;"></div>
	<script type="text/javascript">
		
        // 基于准备好的dom，初始化echarts图表
        var myChart = echarts.init(document.getElementById('main')); 
        var title='';
        var des = '';
        var x_ = eval('${categories}');
        var y_ = eval('${values}');
      	if('${quart}'=='1'){
      		des = '第一季度';
      	}
      	if('${quart}'=='2'){
      		des = '第二季度';
      	}
      	if('${quart}'=='3'){
      		des = '第三季度';
      	}
      	if('${quart}'=='4'){
      		des = '第四季度';
      	}
        if('${year}'==null || '${year}'==''){
        	title = '罗湖区'+'${quart}'+'各部门业务量统计图';
        }else{
        	title = '罗湖区'+'${year}'+'年'+des+'各部门业务量统计图';
        }
        
       
        var option = {
        	title:{
        		show:true,
        		text:title,
        		left:100,
        		textStyle:{
        			//color:'black',
        			//fontWeight:'bolder'
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
       	    	orient : 'vertical'
       	    },
        	//控制数据显示不全
        	grid: { // 控制图的大小，调整下面这些值就可以，
                x: 120,
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
                data:['业务量']
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
                    type : 'value'
                }
            ],
            series : [
                {
                    "name":"业务量",
                    "type":"bar",
                    "data":y_,
                    markPoint: {  
                    	data: [  
                    		{type: 'max', name: '最大值'},
                    		{type: 'average', name: '平均值'},
                    		{type: 'min', name: '最小值'}  
                    	]  
                    },  
                    itemStyle : { normal: {label : {show: true, position: 'top'}}},  //在柱状图上显示数字
                    on:'test'
                }  
            ]
        };
 
        // 为echarts对象加载数据 
        myChart.setOption(option,true); 
    </script>
	
</body>
</html>
