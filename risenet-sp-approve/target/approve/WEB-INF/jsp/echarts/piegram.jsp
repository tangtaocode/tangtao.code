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
		<form action="${ctx}/chart/pie" id="queryForm" method="get" align="center">
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
        	title = '罗湖区业务量'+'${quart}'+'评价满意率统计图';
        }else{
        	title = '罗湖区业务量'+'${year}'+'年'+des+'评价满意率统计图';
        }
        
        //myChart.showLoading({text: '正在努力的读取数据中...'});  
        var option = {
       		 title : {
       		        text: title,
       		        x:'center'
       		    },
       		 tooltip : {
       	        trigger: 'item',
       	        formatter: "{a} <br/>{b} : {c} ({d}%)"
       	    },
	       	 legend: {
	             orient: 'vertical',
	             left: 'right',
	             data: ['满意','一般','不满意']
	         },
	         series : [
	                   {
	                       name: '评价',
	                       type: 'pie',
	                       radius : '55%',
	                       center: ['50%', '60%'],
	                       data:y_,
	                       itemStyle: {
	                           emphasis: {
	                               shadowBlur: 10,
	                               shadowOffsetX: 0,
	                               shadowColor: 'rgba(0, 0, 0, 0.5)'
	                           }
	                       }
	                   }
	               ]
        };
        // 为echarts对象加载数据 
        myChart.setOption(option,true); 
    </script>
	
</body>
</html>
