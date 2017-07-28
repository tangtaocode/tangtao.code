<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>月考评表</title>

</head>
<body>
<div class="box2" panelTitle="查询">	
<form action="${ctx}/dailymanagement/monthReview" id="queryForm" method="post" align="center">
		<table>
		<tr>
			<td>日期</td>
			<td><input type="text" name="date" id="time" dateFmt="yyyy-MM"  class="date"/></td>
			
			<td>&nbsp;&nbsp;&nbsp;大厅:</td>
				<td>
						<c:if test="${departmentname==null}">	
		  					<select  id="sel" selWidth="200" name="sel">						 
								    <option  value="罗湖区行政服务大厅">罗湖区行政服务大厅</option>
									<option value="罗湖区民政局婚姻登记大厅">罗湖区民政局婚姻登记大厅</option>
									<option value="罗湖区财政局专业服务大厅">罗湖区财政局专业服务大厅</option>
									<option value="罗湖区人力资源局专业服务大厅">罗湖区人力资源局专业服务大厅</option>
									<option value="罗湖区卫生监督所专业服务大厅">罗湖区卫生监督所专业服务大厅</option>
							</select>	
						 </c:if>	
						<c:if test="${departmentname!=null}">
									<span>${departmentname}</span>	
						</c:if>	
						<input type="hidden" name="x" value="${departmentname}"/>
				</td>	
				<td>&nbsp;&nbsp;&nbsp;姓名：<input type="text" id="name" name="userName"/></td>
				<td>&nbsp;&nbsp;&nbsp;部门：<input type="text" id="department" name="department"/></td>
				<td>&nbsp;&nbsp;&nbsp;工号：<input type="text" id="enrollnumber" name="enrollnumber"/></td>
				<td><button type="button"  onclick="search()">查询</button></td>
		<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="exportPageData()"><span class="icon_export">导出当前页</span></button></td>
				<td>&nbsp;&nbsp;&nbsp;<button type="button" onclick="exportTotalData()"><span class="icon_export">导出所有页</span></button></td>	
		</tr>
		
		</table>
			
</form>
	</div>

	<div class="padding_right5">
		<div id="maingrid"></div>
    </div>

<script type="text/javascript">
        var gridArray=[];
        var g;
        var childGrid;
		function initComplete(){
			 g = $("#maingrid").quiGrid({
				 columns: [ 
							{ display: '主键', name: 'GUID', align: 'center',hide:true},
							{ display: '姓名', name: 'NAME', align: 'center',width:260},
							{ display: '部门', name: 'DEPARTMENT', align: 'center',width:270},
							{ display: '工号', name: 'ENROLLNUMBER', align: 'center',width:270},
							{ display: '月份 ', name: 'DATED', align: 'center',width:250},
							{ display: '月度评分', name: 'YDPF', align: 'center',width:260}
							],
			    frozen:false,
			   
			    url:'${ctx}/dailymanagement/findMonthReview',usePager:true,rownumbers:true,
                detail: { onShowDetail: showEmployees, height: 'auto' },
			    pageSize:20,
		        height: '100%', width:"100%"
         	});
		}
		function showEmployees(row, detailPanel,callback){
            childGrid = document.createElement('div'); 
               $(detailPanel).append(childGrid);
               childGrid=$(childGrid).css('margin',10).quiGrid({
                    columns: [
						
                       { display: '评分主键', name: 'SCOREGUID', align: 'center',hide:true},
                       { display: '用户主键', name: 'USERGUID', align: 'center',hide:true},
						{ display: '姓名', name: 'NAME',value:11, align: 'center',hide:true},
						{ display: '内务整洁情况', name: 'NWZJQK',value:11, align: 'center'},
						{ display: '游戏、聊天、看手机', name: 'YXLTKSJ', align: 'center'},
						{ display: '迟到、早退', name: 'CDZT', align: 'center'},
						{ display: '没带工牌', name: 'MDGP', align: 'center'},
						{ display: '着装、头发、鞋', name: 'ZZTFX', align: 'center'},
						{ display: '评议器', name: 'PYQ', align: 'center'},
						{ display: '备注', name: 'REMARKS', align: 'center',hide:true},
						{ display: '礼貌文明用语', name: 'LMWMYY', align: 'center'},
						{ display: '总计', name: 'HEJI', align: 'center'},
						{ display: '时间', name: 'TIME', align: 'center'}
						
                    ],                                            
                    sortName: 'userId',width: '100%', columnWidth: 120,height:230,checkbox:true,
                    url: '${ctx}/dailymanagement/findMonthRecord?userId=' + row.GUID+'&date='+row.DATED,
                    pageSize: 5,
                    toolbar:{
			        	 items:[
			        		  {text: '导出当前页', click: function(){exportPageData1(row.GUID,row.DATED)},iconClass: 'icon_add'},
			        		  { line : true },
			        		  {text: '导出所有页', click: function(){exportTotalData1(row.GUID,row.DATED)},iconClass: 'icon_edit'},
			        		  { line : true }		        		  
			        		]
			         	}

               });  
               var obj={};
               obj.id=row.orgId;
               obj.grid=childGrid;
               gridArray.push(obj);
		}

		//查询
		function search(){
			//得到查询参数
			var query = $("#queryForm").formToArray(); 
			//将查询参数传给grid表格
			g.setOptions({ params: query});
			//页号重置为1
			g.setNewPage(1);
			//重新加载数据
			g.loadData();
			
		}
		//导出本页
		function exportPageData(){
			exportData(true);
		}
		//导出所有页
		function exportTotalData(){
			exportData(false);
		}
		//子类导出本页
		function exportPageData1(guid,date){
			exportData1(true,guid,date);
		}
		//子类导出所有页
		function exportTotalData1(guid,date){
			exportData1(false,guid,date);
		}
		//导出处理
		function exportData(isPage){
			//istype=2 月度评分的导出
			var istype=2;
			var pageNo = g.options.page;
		    var pagerSize = g.options.pageSize;
			var name=$("#name").val();
			var date=$("#time").val();
			var department=$("#department").val();
			var enrollnumber=$("#enrollnumber").val();
			var sel=$('#sel option:selected').text();
	        var url = "${ctx}/dailymanagement/excel?isPage="+isPage+"&userName="+name+"&department="+department+"&enrollnumber="+enrollnumber+"&sel="+sel+"&istype="+istype;
	        url +="&date=" +date;
	        url += "&pager.pageSize=" + pagerSize;
            url += "&pager.pageNo=" + pageNo;
	        window.location = url;


		}
		//子类导出处理
		function exportData1(isPage,guid,date){
			var pageNo = childGrid.options.page;
		    var pagerSize = childGrid.options.pageSize;
		    var url = "${ctx}/dailymanagement/excel1?isPage="+isPage+"&userid="+guid;
		    url +="&date=" +date;
		    url += "&pager.pageSize=" + pagerSize;
            url += "&pager.pageNo=" + pageNo;
            window.location = url;
		}
        function afterFormSubmit(){
             childGrid.loadData();
          }  
   
</script>
</body>
</html>