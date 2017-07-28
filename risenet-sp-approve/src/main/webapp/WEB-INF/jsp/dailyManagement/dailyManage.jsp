<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ include file="/static/common/util.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<script type="text/javascript" src="${ctx}/static/risesoft/js/myCore.js"></script>
</head>
<body>
	<div class="box2" panelTitle="查询" id="searchPanel">
		<form action="" id="queryForm" method="post" align="center">
		
 		<table>
			<tr>
				<!-- <td>日期:</td>
				<td><input id="in1" name="val1" type="text" class="date"></input>至<input id="in2" name="val2" type="text" class="date"></input></td> -->
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
		var grid;
        var gridArray=[];
        var g;
        var childGrid;
        
		function initComplete(){
		var name=$("#name").val();
		var department=$("#department").val();
		var sel=$("#sel").val();
		var enrollnumber=$("#enrollnumber").val();
			 g = $("#maingrid").quiGrid({
				 columns: [ 
							{ display: '主键', name: 'GUID', align: 'center',hide:true},
							{ display: '姓名', name: 'NAME', align: 'center',width:430},
							{ display: '部门', name: 'DEPARTMENT', align: 'center',width:450},
							{ display: '工号', name: 'ENROLLNUMBER', align: 'center',width:430}
							],
			    frozen:false,
			   
			    url:'${ctx}/dailymanagement/findUsers',
			    usePager:true,rownumbers:true,checkbox:true,
                detail: { onShowDetail: showEmployees, height: 'auto' },
			    pageSize:30,
		        height: '100%', width:"100%"
         	});
		}			
		
		//显示所选部门下的员工
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
							{ display: '奖惩记录', 
								render:function(rowdata){
									ret='<span class="icon_view"><a href="javascript:void(0);" onclick="findjc(\''+rowdata.SCOREGUID+'\')">查看</a></span>';
										return ret;
								}
							},
							{ display: '总计', name: 'HEJI', align: 'center'},
							{ display: '时间', name: 'TIME', align: 'center'},
							{display:'操作',
								render:function(rowdata){
									ret= '<a href="javascript:void(0);"  onclick="findbz(\''+ rowdata.REMARKS+'\' )">查看备注</a>';
									return ret;
								}	
							 } 
                         ],                                            
                         sortName: 'userId',width: '100%', columnWidth: 120,height:230,checkbox:true,
                         url: '${ctx}/dailymanagement/findRecord?userId=' + row.GUID,
                         pageSize: 5,
                         toolbar:{
				        	 items:[
				        		  {text: '新增', click: function(){onadd(row.NAME,row.GUID,row.ENROLLNUMBER)},    iconClass: 'icon_add'},
				        		  { line : true },
				        		  {text: '修改', click: function(){onReview()},iconClass: 'icon_edit'},
				        		  { line : true },
				        		  {text: '删除', click: function(){ondelete()}, iconClass: 'icon_delete'},
				        		  { line : true },
				        		  {text: '添加奖惩记录', click: function(){addjcjl(row.ENROLLNUMBER)}, iconClass: 'icon_add'},
				        		  { line : true }
				        		  
				        		]
				         	}

                    });  
                    var obj={};
                    obj.id=row.orgId;
                    obj.grid=childGrid;
                    gridArray.push(obj);
        }
		//添加奖惩记录
		function addjcjl(enrollnumber){
			var row = childGrid.getSelectedRows();
			var len = row.length;
		    if(len<1) { 
		    	Dialog.alert("至少选择一条记录！|提示",null);
				return;
			}else if(len>1){
				Dialog.alert("只能选择一条记录！|提示",null);
				return;
			}else if(len==1){
				var ScoreGuid = row[0].SCOREGUID;
				var name=row[0].NAME;
				
				Dialog.open({URL:"${ctx}/dailymanagement/addjcjl?ScoreGuid="+ScoreGuid+"&name="+name+"&enrollnumber="+enrollnumber,title:"月考评表",Width:500,Height:330});
			}
		    
			
		}
		//查看考勤记录
		function findjc(scoreGuid){
			
			Dialog.open({URL:"${ctx}/dailymanagement/findjc?scoreGuid="+scoreGuid,title:"月考评表",Width:500,Height:330});
		}
		//月考评分
		function monthpf(guid,name,enrollnumber){
			
			Dialog.open({URL:"${ctx}/dailymanagement/monthReview?monthguid="+guid+"&name="+name+"&enrollnumber="+enrollnumber,title:"月考评表",Width:500,Height:330});
		}
		 //删除
		function ondelete(){
			var row = childGrid.getSelectedRows();
			var len = row.length;
		    if(len<1) { 
		    	Dialog.alert("至少选择一条记录！|提示",null);
				return;
			}else if(len>1){
				Dialog.alert("只能选择一条记录！|提示",null);
				return;
			}else if(len==1){
				var ScoreGuid = row[0].SCOREGUID;
				
				$.ajax({
			         type: "POST",
			         dataType: "json",
			         data: {ScoreGuid:ScoreGuid },
			         url: "${ctx}/dailymanagement/delete",
			         success: function(data){
			        	alert(data.message);
			        	childGrid.loadData();
			         }
				});
			}
		} 
		//新增
		function onadd(name,userGuid,enrollnumber){
			//查看是否需要新增
			$.ajax({
		         type: "POST",
		         dataType: "json",
		         data:{userGuid:userGuid},
		         url: "${ctx}/dailymanagement/findis",
		         success: function(data){
		        	if(data.is){
		        		alert(data.message);
		        	}else{
		        	Dialog.open({URL:"${ctx}/dailymanagement/add?name="+name+"&userGuid="+userGuid+"&enrollnumber="+enrollnumber,title:"评分",width:600,height:200});
		        	}
		         }
			});
			
		}

		//修改
		function onReview(){
			var row = childGrid.getSelectedRows();
			var len = row.length;
			if(len<1) { 
			    	Dialog.alert("至少选择一条记录！|提示",null);
					return;
				}else if(len>1){
					Dialog.alert("只能选择一条记录！|提示",null);
					return;
				}else if(len==1){
				var id=	row[0].SCOREGUID;
				var name=row[0].NAME;
				var userGuid=row[0].USERGUID;
				}
			
			if(id=='undefined'){
				id=null;
			}
			Dialog.open({URL:"${ctx}/dailymanagement/updateScore?reviewid="+id+"&name="+name+"&userGuid="+userGuid,title:"评分",width:600,height:200});
		}
		
		//查看备注
		function findbz(remarks){
			if(remarks=='undefined'){
				remarks='';
			}
			Dialog.open({URL:"${ctx}/dailymanagement/findRemarks?remarks="+remarks,title:"查看备注",width:600,height:200});
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
		//导出处理
		function exportData(isPage){
			//istype=1 员工管理的导出
			var istype=1;
			var pageNo = g.options.page;
		    var pagerSize = g.options.pageSize;
			var name=$("#name").val();
			var department=$("#department").val();
			var enrollnumber=$("#enrollnumber").val();
			var sel=$('#sel option:selected').text();
	        var url = "${ctx}/dailymanagement/excel?isPage="+isPage+"&userName="+name+"&department="+department+"&enrollnumber="+enrollnumber+"&sel="+sel+"&istype="+istype;
	        url += "&pager.pageSize=" + pagerSize;
            url += "&pager.pageNo=" + pageNo;
	        window.location = url;


		}
    //刷新表格 表单提交的回调
    function afterFormSubmit(){
    	childGrid.loadData();
    }
 
</script>	
</body>
</html>