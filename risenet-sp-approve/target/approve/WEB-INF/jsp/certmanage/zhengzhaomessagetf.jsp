<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/util.jsp"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>基本表格模板</title>




<script type="text/javascript">

		var myCols = [ 
		    { display: '第一季度', name: 'beginworkDate', width: 300, align: 'left' ,
			    columns: [    { display: '一月', name: 'YIYUE', minWidth: 60,width:70 } ,
				              { display: '二月', name: 'ERYUE', minWidth: 60,width:70  } ,
				              { display: '三月', name: 'SANYUE', minWidth: 60,width:70  } ,
				              { display: '一季度合计', name: 'YIJIDU', minWidth: 60,width:90  } ,
			             ]
		    }, 
		    { display: '第二季度', name: 'beginworkDate', width: 300, align: 'left' ,
		    	columns: [    { display: '四月', name: 'SIYUE', minWidth: 60,width:70 } ,
				              { display: '五月', name: 'WUYUE', minWidth: 60,width:70  } ,
				              { display: '六月', name: 'LIUYUE', minWidth: 60,width:70  } ,
				              { display: '二季度合计', name: 'ERJIDU', minWidth: 60,width:90  } ,
			             ]
		    } ,
			{ display: '第三季度', name: 'beginworkDate', width: 300, align: 'left' ,
		    	columns: [    { display: '七月', name: 'QIYUE', minWidth: 60,width:70 } ,
				              { display: '八月', name: 'BAYUE', minWidth: 60,width:70  } ,
				              { display: '九月', name: 'JIUYUE', minWidth: 60,width:70  } ,
				              { display: '三季度合计', name: 'SANJIDU', minWidth: 60,width:90  } ,
			             ]
		    } ,
		    { display: '第四季度', name: 'beginworkDate', width: 300, align: 'left' ,
		    	columns: [    { display: '十月', name: 'SHIYUE', minWidth: 60,width:70 } ,
				              { display: '十一月', name: 'SHIYIYUE', minWidth: 60,width:70  } ,
				              { display: '十二月', name: 'SHIERYUE', minWidth: 60,width:70  } ,
				              { display: '四季度合计', name: 'SIJIDU', minWidth: 60,width:90 } ,
			             ]
		    } ,
		    { display: '年度合计', name: 'YEARTOTAL', width: 100, align: 'center' } ,
		    
		    ];
		 

	    //定义远程数据
	    
        var g;
	    var ret;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
		       columns: [ 
							/* { display: '主键', name: 'GID', align: 'center', width:60,hide:"true"}, */
			                { display: '发证单位',   align: 'center', minWidth: 150,width:"30%",
								render: function (rowdata, rowindex, value, column){
	                		    ret= '<a href="javascript:void(0);" onclick="onStatisticsInfo(\'' + rowdata.BUREAUGUID + '\',\'' + rowdata.PRODUCEORGAN + '\',\'' + rowdata.NIANFEN + '\');"> '+ rowdata.PRODUCEORGAN +'</a>';
	                		    return ret;
	                			}
							},
			                /* { display: '证照名称', name: 'DOCNAME', align: 'center', width:180}, */
			                /* { display: '证照类型', name: 'DOCTYPENAME',align: 'center', width:100}, */
			                { display: '年度采集量', columns: myCols ,align: 'center',width: "70%"},
			                /* { display: '总采集量', name: 'TOTALTOTAL',align: 'center', width: "10%"}, */
		         		], 
		        url:"${ctx}/scanninghistory/zzmessagetongjifx",rownumbers:true,pageSize:20,percentWidthMode:true,
		        height: '100%', width:'100%', 
		        toolbar:{
		            items:[
						 /* {text: '清空', click: clearWhere, iconClass: 'icon_delete'},
						 { line : true }, */
		                 {text: '导出为Excel', click: exportPageData, iconClass: 'icon_export'},
		                 { line : true },


		               ]
		           }
         	});
			 
		}
		
		//查看局下的项目统计
		function onStatisticsInfo(BUREAUGUID,PRODUCEORGAN,NIANFEN) {
			window.location.href = "${ctx}/scanninghistory/zzmessagetftwo?BUREAUGUID="+BUREAUGUID+"&PRONAME="+PRODUCEORGAN+"&NIANFEN="+NIANFEN;
		}
		
		//清空查询条件
		function clearWhere(){
			$("#queryForm")[0].reset();
		}
		//导出为Excel
		function exportPageData(){
			
			 //alert(document.getElementById("departmentGuid").value);
			var PRODUCEORGAN = document.getElementById("produceOrgan").value;
			var DOCTYPENAME = document.getElementById("docTypeName").value;
			var NIANFEN = document.getElementById("nianfen").value;
			/* $.ajax({ url: "${ctx}/scanninghistory/exportData?produceOrgan="+PRODUCEORGAN+"&docTypeName="+DOCTYPENAME+"&nianfen="+NIANFEN, context: document.body, success: function(){
		        $(this).addClass("done");
		      }}); */
		      window.location.href = "${ctx}/scanninghistory/exportData?produceOrgan="+PRODUCEORGAN+"&docTypeName="+DOCTYPENAME+"&nianfen="+NIANFEN ;   
			//alert("请到D盘下查收--证照信息统计分析.xls--文件");
			//out.clear();
			//out = pageContext.pushBody();
		}

		//查询
		function searchHandler(){

			//得到查询参数
			var query = $("#queryForm").formToArray(); 
			//将查询参数传给grid表格
			g.setOptions({ params: query});
			//页号重置为1
			g.setNewPage(1);
			
			//重新加载数据
			g.loadData();
		}
		

		//重置
		function resetHandler(){
		   //表单常规元素还原
		   $("#queryForm")[0].reset();
		   //下拉框还原
		   $("#queryForm").find("select").render();
		   //重新查询
		   searchHandler();
		}
		

	
	//删除
	function onDelete(rowid,rowidx){
		top.Dialog.confirm("确定要删除该记录吗？",function(){
		  	top.Dialog.alert("向后台发送ajax请求来删除。见JAVA版或.NET版演示。");
		});
	}
		
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
        g.loadData();
    }
    
    
    <%---------------------------------------------------------------------------------------------------------------------------------------------%> 
    
    //线性图
    function xianxingtu(){
    	$.ajax({
    		type: "POST",// 请求方式
    		url: "${ctx}/scanninghistory/xianxingtuList",// 请求url地址
    		data: {nianfen:document.getElementById("nianfen").value},
    		dataType: "json",// 数据返回类型
    		success: function(data){
    			// 请求发送成功后执行的函数,data是返回的数据。
    		
    	    	//折线图示例
        chart = new Highcharts.Chart({
            chart: {
                renderTo: 'maingrid',          //放置图表的容器
                plotBackgroundColor: null,
                plotBorderWidth: null,
                defaultSeriesType: 'line'   
            },
            title: {
                text: '证照信息统计分析'
            },
            subtitle: {
                text: ''
            },
            xAxis: {//X轴数据
                categories: ['一月份', '二月份', '三月份', '四月份', '五月份', '六月份', '七月份', '八月份', '九月份', '十月份', '十一月份', '十二月份'],
                labels: {
                    align: 'right',
                    style: { font: 'normal 13px 宋体' }
                }
            },
            yAxis: {//Y轴显示文字
                title: {
                    text: '发证量/个'
                }
            },
            tooltip: {
                enabled: true,
                formatter: function() {
                    return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + Highcharts.numberFormat(this.y, 1);
                }
            },
            plotOptions: {
                line: {
                    dataLabels: {
                        enabled: true
                    },
                    enableMouseTracking: true//是否显示title
                }
            },
            series:      data.tongjitu     		
        });
    			
    			
    			
    		},
    	})
    	

    }
    
    //柱状图
    function zhuzhuangtu(){
    	
    }
    
    //饼状图
    function bingzhuangtu(){
    	
    }
    
    
 
</script>	
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form id="queryForm" method="post" align="center">
		<table>
			<tr>
				<td>发证单位：</td>
				<td>
					<select name="produceOrgan" id="produceOrgan" prompt="选择发证单位" url="${ctx}/scanninghistory/wbjSelect" boxWidth="200" boxHeight="200" selWidth="180"
					childId="docTypeName" childDataPath="${ctx}/scanninghistory/zztypeSelect?produceOrgan="></select>
				</td>
				<td>证照类型：</td>
				<td>
					<select name="docTypeName" id="docTypeName" prompt="选择证照类型"  boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>
				<td>选择年份:</td>
				<td>
					<select name="nianfen" id="nianfen" prompt="选择年份" url="${ctx}/scanninghistory/nianfenSelect" boxWidth="200" boxHeight="200" selWidth="180"></select>
				</td>
				<td><button type="button" onclick="searchHandler()"><span class="icon_find">查询</span></button></td>
			    <td width="50"></td>
				<td>
					<button type="button" onclick="xianxingtu()"><span class="icon_find">线性图</span></button>				
				<!-- 	<button type="button" onclick="zhuzhuangtu()"><span class="icon_find">柱状图</span></button> 
					<button type="button" onclick="bingzhuangtu()"><span class="icon_find">饼状图</span></button> -->
				</td> 
					
			</tr>
		</table>
		</form>
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>


</body>
</html>
