<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/static/common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>工作流中间件-调休设置</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/fullcalendar/fullcalendar/fullcalendar.print.css" media='print' />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery/fullcalendar/fullcalendar/fullcalendar.css" />
<link type="text/css" rel="stylesheet" href="${ctx}/static/jquery/jquery-ui/css/redmond/jquery-ui-1.10.3.custom.css" />

<script type="text/javascript" src="${ctx}/static/jquery/layer/layer.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/jquery-ui/js/jquery-ui-1.10.3.custom.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/fullcalendar/fullcalendar/fullcalendar.min.js"></script>
<script type="text/javascript" src="${ctx}/static/jquery/form/jquery.form.js"></script>

<script type="text/javascript">

$(document).ready(function() {

	/* initialize the calendar
	-----------------------------------------------------------------*/
	
	$('#calendar').fullCalendar({
		header: {
			left: 'prev,next today',
			center: 'title'
		},
		editable: true,
		droppable: true, // this allows things to be dropped onto the calendar !!!
		events:  function(start, end , callback){
			var holiday=$('#workingDayToHoliday').val();
			var workDay=$('#weekendToWorkingDay').val();
			//alert(holiday);
			var holidays=holiday.split(",");
			var workDays=workDay.split(",");
			var events = [];
			var now = new Date();
			for(var i=0;i<holidays.length;i++){
				var temp=holidays[i].split("-");
				//alert(holidays[i]);
				var evtstart = new Date(temp[0] , temp[1]-1, temp[2], now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());
				var evtend = new Date(temp[0] , temp[1]-1, temp[2], now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());							
			
				events.push({
					sid: i,
					uid: i,
					title: '公休',
					start: evtstart,
					end: evtend,
					fullname: 'terry li',
					confname: 'Meeting 1',
					confshortname: 'M1',
					confcolor: '#ff3f3f',
					confid: i,
					allDay: true,
					topic: 'Daily Scrum meeting',
					description : '公休',
					id: i
				});
			}						
			for(var i=0;i<workDays.length;i++){
				var temp=workDays[i].split("-");
				//alert(holidays[i]);
				var evtstart = new Date(temp[0] , temp[1]-1, temp[2], now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());
				var evtend = new Date(temp[0] , temp[1]-1, temp[2], now.getHours(), now.getMinutes(), now.getSeconds(), now.getMilliseconds());							
			
				events.push({
					sid: i+holidays.length,
					uid: i+holidays.length,
					title: '调休',
					start: evtstart,
					end: evtend,
					fullname: 'terry li',
					confname: 'Meeting 1',
					confshortname: 'M1',
					confcolor: '#ff3f3f',
					confid: i+holidays.length,
					allDay: true,
					topic: 'Daily Scrum meeting',
					description : '调休',
					id: i+holidays.length
				});
			}						
			
			callback(events);
		  },
		drop: function(date, allDay) { // this function is called when something is dropped
		
			// retrieve the dropped element's stored Event Object
			var originalEventObject = $(this).data('eventObject');
			
			// we need to copy it, so that multiple events don't have a reference to the same object
			var copiedEventObject = $.extend({}, originalEventObject);
			
			// assign it the date that was reported
			copiedEventObject.start = date;
			copiedEventObject.allDay = allDay;
			
			// render the event on the calendar
			// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
			$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
			
		},
		
		eventClick: function(event, jsEvent, view) {
			var tempStart = $.fullCalendar.formatDate(event.start, "yyyy-MM-dd");
            var tempEnd = $.fullCalendar.formatDate(event.end, "yyyy-MM-dd");
            var xiujiaCh = event.description;
            var holidayflag=1;
            if(xiujiaCh=="调休")
            {
            	holidayflag=2;
            }
			//alert(event._id);//Here _id is the unique ID fullCalendar generates
            $( "#dialog-confirm" ).dialog({
				resizable: false,
				height:200,
				modal: true,
				buttons: {
				"删除": function() {
					//$('#calendar').fullCalendar('removeEvents',event._id);//删除event._id对应的事项
					try{
					$("#calendarform").ajaxSubmit({
                            type : 'POST',
                            dataType : 'json',
                            data : {
                            	'selectdate':tempStart,
                            	'holidayflag':holidayflag,
                            	't':new Date()
                            }, 
                            url : '${ctx}/reminderconf/calendar/del',
                            success : function(responseText) {
                            	if (responseText.success == true) {
                            		alert(responseText.msg)
                                    window.location.reload();
                                } else {
                                    alert(responseText.msg)
                                }
                            }
                        });
					}catch(e)
					{
						alert(e);
					}
					 	//$( this ).dialog( "close" );
					 	//delDay(tempStart,event.description);
					 },
					 "关闭": function() {
					 	$( this ).dialog( "close" );
					 }
				 }
			});
            //下面是删除所有
            /*  $('#calendar').fullCalendar('removeEvents', function (event) {
				return true;
			}); */
		} ,
		dayClick: function(date,calEvent, jsEvent, view) { 
			var selectdate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");
			$( "#confcalendar" ).dialog({
                autoOpen: false,
                height: 200,
                width: 300,
                title: '调休假设置' ,// 此处声明title，会将reservebox中的title属性覆盖
                modal: true,
                position: "center",
                draggable: true,// 可拖拽
                buttons: {// 弹出窗右下角的按钮
                    "保 存": function() {
                    	var selectdate = $.fullCalendar.formatDate(date, "yyyy-MM-dd");
                    	var workingDayToHoliday=$('#workingDayToHoliday').val();
                    	try{
                        	  $("#calendarform").ajaxSubmit({
                                  //type : 'POST',
                                  //dataType : 'json',
                                  data : {
                                  	'selectdate':selectdate
                                  }, 
                                  //url : '${ctx}/reminderconf/calendar/save',
                                  success : function(responseText) {
                                  	if (responseText.success == true) {
                                        alert(responseText.msg)
                                        window.location.reload();
                                    } else {
                                        alert(responseText.msg)
                                    }
                                  }
                              });
                          }catch(e){
                        	  alert(e);
                          }
                    	
                    	$( this ).dialog( "close" );
                    	
                    },
                    "关 闭": function() {
                        $( this ).dialog( "close" );
                    }
                }
            });
			$( "#confcalendar" ).dialog( "open" );
		}
	});
});

function delDay(tempStart,description)
{
	var day;
	if(description=="公休")
	{
		day=$("#workingDayToHoliday").val();
	}
	if(description=="调休")
	{
		day=$("#weekendToWorkingDay").val();
	}
	if(tempStart.indexOf("/")>=0)
	{
		var reg=new RegExp("/","g");
		tempStart=tempStart.replace(reg,"-");
	}
	var days=day.split(",");
	var temp="";
	for(var i=0;i<days.length;i++)
	{
		if(!(days[i]==tempStart))
		{
			if(temp=="")
			{
				temp=days[i];
			}else
			{
				temp=temp+","+days[i];
			}
		}
	}
	day=temp;
	if(description=="公休")
	{
		$("#workingDayToHoliday").val(day);
	}
	if(description=="调休")
	{
		$("#weekendToWorkingDay").val(day);
	}
	
}
</script>
<style>

	body {
		margin-top: 20px;
		text-align: center;
		font-size: 14px;
		font-family: "Lucida Grande",Helvetica,Arial,Verdana,sans-serif;
		}
		
	/* #wrap {
		width: 650px;
		margin: 0 auto;
		}
		
	#calendar {
		float: center;
		width: 600px;
		} */
</style>
</head>
<body>
<div id='wrap'>
	<div id='calendar'></div>
	<div style='clear:both'></div>
</div>

<div id="confcalendar" title="配置" style="display: none;">
<form id="calendarform" action="${ctx}/reminderconf/calendar/save" method="post">
	<input type="radio" id="gongxiu" name="xiujia" value="1">公休
	<input type="radio" id="tiaoxiu" name="xiujia" value="2">调休
	<input type="hidden" id="workingDayToHoliday" name="workingDayToHoliday" value="${workingDayToHoliday }">
	<input type="hidden" id="weekendToWorkingDay" name="weekendToWorkingDay" value="${weekendToWorkingDay }">
</form>
</div>

<div id="dialog-confirm" title="确定删除" style="display: none;">
<p>确定删除当前事项?</p>
</div>
</body>
</html>
