document.write("<div id=meizzCalendarLayer style='position: absolute; z-index: 9999; width:151px; height: 193; display: none'>");
document.write("<iframe name=meizzCalendarIframe id=meizzCalendarIframe scrolling=no frameborder=0 width=150 height=193 src=blank.htm></iframe></div>");

function writeIframe()
{
    var strIframe = "<html><head><meta http-equiv='Content-Type' content='text/html; charset=gb2312'><style>"+
    "*{font-size: 12px; font-family: ����}"+
    ".bg{  color: "+ WebCalendar.lightColor +"; cursor: default; background-color: "+ WebCalendar.darkColor +";}"+
    "table#tableMain{ width: 100%; height: 180;}"+
    "table#tableWeek td{ color: "+ WebCalendar.lightColor +";width:14%;}"+
    "table#tableDay  td{ font-weight: normal;padding-left:2px;padding-right:2px}"+
    "td#meizzYearHead, td#meizzYearMonth{color: "+ WebCalendar.wordColor +";padding-left:10px;}"+
    ".out {text-align: center; width:14%;border-top: 1px solid "+ WebCalendar.DarkBorder +"; border-left: 1px solid "+ WebCalendar.DarkBorder +";"+
    "border-right: 1px solid "+ WebCalendar.lightColor +"; border-bottom: 1px solid "+ WebCalendar.lightColor +";}"+
    ".over{text-align: center; width:14%;border-top: 1px solid #FFFFFF; border-left: 1px solid #FFFFFF;"+
    "border-bottom: 1px solid "+ WebCalendar.DarkBorder +"; border-right: 1px solid "+ WebCalendar.DarkBorder +"}"+
    "input{border: 1px solid "+ WebCalendar.darkColor +"; padding-top: 1px; height: 18; cursor: pointer;"+
    "       color:"+ WebCalendar.wordColor +"; background-color: "+ WebCalendar.btnBgColor +"}"+
    "</style></head><body onselectstart='return false' style='margin: 0px' oncontextmenu='return false'><form name=meizz>";

    strIframe += "<select name=tmpYearSelect  onblur='parent.hiddenSelect(this)' style='z-index:1;position:absolute;top:3px;left:23px;display:none'"+
    " onchange='parent.WebCalendar.thisYear =this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+
    "<select name=tmpMonthSelect onblur='parent.hiddenSelect(this)' style='z-index:1; position:absolute;top:3px;left:84px;display:none'"+
    " onchange='parent.WebCalendar.thisMonth=this.value; parent.hiddenSelect(this); parent.writeCalendar();'></select>"+

    "<table id=tableMain class='bg' border=0 cellspacing=1 cellpadding=0 width='100%'>"+
    "<tr><td width='100%' height=19 bgcolor='"+ WebCalendar.lightColor +"'>"+
    "    <table width='100%' id=tableHead border=0 cellspacing=1 cellpadding=0><tr align=center>"+
    "    <td width='12%' height=19 class=bg title='��ǰ�� 1 ��' style='cursor:pointer' onclick='parent.prevM()'><b>&lt;</b></td>"+
    "    <td width='40%' id=meizzYearHead  title='����˴�ѡ�����' onclick='parent.funYearSelect(parseInt(this.innerHTML, 10))'"+
    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>"+
    "    <td width='36%' id=meizzYearMonth title='����˴�ѡ���·�' onclick='parent.funMonthSelect(parseInt(this.innerHTML, 10))'"+
    "        onmouseover='this.bgColor=parent.WebCalendar.darkColor; this.style.color=parent.WebCalendar.lightColor'"+
    "        onmouseout='this.bgColor=parent.WebCalendar.lightColor; this.style.color=parent.WebCalendar.wordColor'></td>"+
    "    <td width='12%' class=bg title='��� 1 ��' onclick='parent.nextM()' style='cursor:pointer'><b>&gt;</b></td></tr></table>"+
    "</td></tr><tr><td height=20><table id=tableWeek border=0 width='100%' cellpadding=1 cellspacing=0 ";
    if(WebCalendar.drag){strIframe += "onmousedown='dragStart()' onmouseup='drag=false' onmouseout='drag=false'";}
    strIframe += " borderColorLight='"+ WebCalendar.darkColor +"' borderColorDark='"+ WebCalendar.lightColor +"'>"+
    "    <tr align=center><td height=20>��</td><td>һ</td><td>��</td><td>��</td><td>��</td><td>��</td><td>��</td></tr></table>"+
    "</td></tr><tr><td valign=top width='100%' bgcolor='"+ WebCalendar.lightColor +"'>"+
    "    <table id=tableDay height=110 width='100%' border=0 cellspacing=1 cellpadding=0>";
         for(var x=0; x<5; x++){ strIframe += "<tr>";
         for(var y=0; y<7; y++)  strIframe += "<td class=out id='meizzDay"+ (x*7+y) +"'></td>"; 
		     strIframe += "</tr>";}
         strIframe += "<tr>";
         for(var x=35; x<39; x++) strIframe += "<td class=out id='meizzDay"+ x +"'></td>";
         strIframe +="<td colspan=3 class=out nowrap><input style=' background-color: "+
         WebCalendar.btnBgColor +";cursor: pointer;padding-top:2px; width:28px; border:solid 1px #FFFFFF;padding-left:1px' onfocus='this.blur()'"+
         " type=button value='���' onclick='parent.__clearValue()'></span><span style='width:1px'></span><input style=' background-color: "+
         WebCalendar.btnBgColor +";cursor: pointer; padding-top:2px; width:28px; border:solid 1px #FFFFFF;padding-left:1px' onfocus='this.blur()'"+
         " type=button value='�ر�' onclick='parent.hiddenCalendar()'></td></tr></table>"+
    "</td></tr><tr><td height=20 width='100%' bgcolor='"+ WebCalendar.lightColor +"'>"+
    "    <table border=0 cellpadding=1 cellspacing=0 width=100%>"+
    "    <tr><td><input name=prevYear title='��ǰ�� 1 ��' onclick='parent.prevY()' type=button value='&lt;&lt;'"+
    "    onfocus='this.blur()' style='meizz:expression(this.disabled=parent.WebCalendar.thisYear==1000);width:20px'></td><td><input"+
    "    onfocus='this.blur()' name=prevMonth title='��ǰ�� 1 ��' onclick='parent.prevM()' style='width:20px' type=button value='&lt;'>"+
    "    </td><td align=center valign=top><input name=today type=button value='����' onfocus='this.blur()' style='width:40px' title='��ǰ����'"+
    "    onclick=\"parent.returnDate(new Date().getDate() +'/'+ (new Date().getMonth() +1) +'/'+ new Date().getFullYear())\">"+
    "</td><td align=right><input title='��� 1 ��' name=nextMonth onclick='parent.nextM()' type=button value='&gt;'"+
    "    onfocus='this.blur()' style='width:20px'></td><td align='right'><input name=nextYear title='��� 1 ��' onclick='parent.nextY()' type=button value='&gt;&gt;'"+
    "    onfocus='this.blur()' style='meizz:expralert(110)ession(this.disabled=parent.WebCalendar.thisYear==9999);width:20px'></td></tr></table>"+
    "</td></tr><table></form></body></html>"; 

	//document.getElementById("p").value = strIframe;
    with(frames["meizzCalendarIframe"])
    {
		document.writeln(strIframe);
		document.close();
        for(var i=0; i<39; i++)
        {
            WebCalendar.dayObj[i] = document.getElementById("meizzDay"+ i);
            WebCalendar.dayObj[i].onmouseover = dayMouseOver;
            WebCalendar.dayObj[i].onmouseout  = dayMouseOut;
            WebCalendar.dayObj[i].onclick     = returnDate;
        }
    }
}
function __WebCalendar() //��ʼ������������
{
    this.regInfo    = "WEB Calendar ver 3.0";
    this.daysMonth  = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
    this.day        = new Array(39);            //��������չʾ�õ�����
    this.dayObj     = new Array(39);            //��������չʾ�ؼ�����
    this.dateStyle  = null;                     //�����ʽ������������
    this.objExport  = null;                     //�����ش�����ʾ�ؼ�
    this.eventSrc   = null;                     //������ʾ�Ĵ����ؼ�
    this.inputDate  = null;                     //ת��������������(d/m/yyyy)
	this.callback   = null;                     //�ص�����
    this.thisYear   = new Date().getFullYear(); //������ı����ĳ�ʼֵ
    this.thisMonth  = new Date().getMonth()+ 1; //�����µı����ĳ�ʼֵ
    this.thisDay    = new Date().getDate();     //�����յı����ĳ�ʼֵ
    this.today      = this.thisDay +"/"+ this.thisMonth +"/"+ this.thisYear;   //����(d/m/yyyy)
    this.iframe     = frames["meizzCalendarIframe"]; //������ iframe ����
    this.calendar   = getObjectById("meizzCalendarLayer");  //�����Ĳ�
    this.dateReg    = "";           //������ʽ��֤������ʽ

    this.yearFall   = 50;           //����������������ֵ
    this.format     = "yyyy-mm-dd"; //�ش����ڵĸ�ʽ
	/**
       ����ʱ��
	   0  ������
	   1  ����
	   2  ����
	**/
    this.timeShow   = 0;            
    this.drag       = true;         //�Ƿ������϶�
    this.darkColor  = "#058DB0";    //�ؼ��İ�ɫ ����--#058DB0  ǳ��--#4FB0AC  ����--#31A66B
    this.lightColor = "#FDFDFD";    //�ؼ�����ɫ
    this.btnBgColor = "#E6E6FA";    //�ؼ��İ�ť����ɫ
    this.wordColor  = "#303030";    //�ؼ���������ɫ
    this.wordDark   = "#C0C0C0";    //�ؼ��İ�������ɫ
    this.dayBgColor = "#F5F5F5";    //�������ֱ���ɫ
    this.todayColor = "#E03030";    //�����������ϵı�ʾ����ɫ
    this.DarkBorder = "#F0F0F0";    //������ʾ��������ɫ
}

function _fNode(obj, id)
{
    for(var i=0; i<obj.childNodes.length; i++)
    {
        if(obj.childNodes[i].id == id || obj.childNodes[i].name == id) 
        {
            return obj.childNodes[i];
        }
        else
        {
            var o = this.findChildNode(obj.childNodes[i], id);
            if(o != null) return o;
        }
    }

    return null;
}

function calendar() //��������
{
    var e = (window.event)?window.event.srcElement:arguments.callee.caller.arguments[0].target;
	writeIframe();
    var o = getObjectById("meizzCalendarLayer").style; 
	WebCalendar.eventSrc = e;
	if (arguments.length == 0) WebCalendar.objExport = e;
    else WebCalendar.objExport = eval(arguments[0]);
    if(arguments[1]) 
	{
		WebCalendar.timeShow = (arguments[1] == 2)?2:1; //���ӻ���ȫ��
	}
	if(arguments[2]) WebCalendar.callback = arguments[2];
    try{frames["meizzCalendarIframe"].tableWeek.style.cursor = WebCalendar.drag ? "move" : "default";}catch(e){}
	var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
	while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
    o.display = ""; 
	frames["meizzCalendarIframe"].document.body.focus();
    var cw = getObjectById("meizzCalendarLayer").clientWidth, ch = getObjectById("meizzCalendarLayer").clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;

    if (document.body.clientHeight + dt - t - h >= ch) o.top = ((p=="image")? t + h : t + h + 6) + "px";
    else o.top  = ((t - dt < ch) ? ((p=="image")? t + h : t + h + 6) : t - ch) + "px";
    if (dw + dl - l >= cw) o.left = l + "px"; else o.left = ((dw >= cw) ? dw - cw + dl : dl) + "px";

    if(WebCalendar.timeShow == 0) WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/;
    else WebCalendar.dateReg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2}) (\d{1,2}):(\d{1,2})(:(\d{1,2}))?$/;

    try{
        if (WebCalendar.objExport.value.trim() != ""){
            WebCalendar.dateStyle = WebCalendar.objExport.value.trim().match(WebCalendar.dateReg);
            if (WebCalendar.dateStyle == null)
            {
                WebCalendar.thisYear   = new Date().getFullYear();
                WebCalendar.thisMonth  = new Date().getMonth()+ 1;
                WebCalendar.thisDay    = new Date().getDate();
                //alert("ԭ�ı�����������д���\n�������㶨�����ʾʱ�����г�ͻ��");
                writeCalendar(); return false;
            }
            else
            {
                WebCalendar.thisYear   = parseInt(WebCalendar.dateStyle[1], 10);
                WebCalendar.thisMonth  = parseInt(WebCalendar.dateStyle[3], 10);
                WebCalendar.thisDay    = parseInt(WebCalendar.dateStyle[4], 10);
                WebCalendar.inputDate  = parseInt(WebCalendar.thisDay, 10) +"/"+ parseInt(WebCalendar.thisMonth, 10) +"/"+ 
                parseInt(WebCalendar.thisYear, 10); writeCalendar();
            }
        }  else writeCalendar();
    }  catch(e){writeCalendar();}
}
function funMonthSelect() //�·ݵ�������
{
    var m = isNaN(parseInt(WebCalendar.thisMonth, 10)) ? new Date().getMonth() + 1 : parseInt(WebCalendar.thisMonth);
    var e = frames["meizzCalendarIframe"].document.forms[0].tmpMonthSelect;
    for (var i=1; i<13; i++) e.options.add(new Option(i +"��", i));
    e.style.display = ""; e.value = m; e.focus(); 
}
function funYearSelect() //��ݵ�������
{
    var n = WebCalendar.yearFall;
    var e = frames["meizzCalendarIframe"].document.forms[0].tmpYearSelect;
    var y = isNaN(parseInt(WebCalendar.thisYear, 10)) ? new Date().getFullYear() : parseInt(WebCalendar.thisYear);
        y = (y <= 1000)? 1000 : ((y >= 9999)? 9999 : y);
    var min = (y - n >= 1000) ? y - n : 1000;
    var max = (y + n <= 9999) ? y + n : 9999;
        min = (max == 9999) ? max-n*2 : min;
        max = (min == 1000) ? min+n*2 : max;
    for (var i=min; i<=max; i++) e.options.add(new Option(i +"��", i));
    e.style.display = ""; e.value = y; e.focus();
}
function prevM()  //��ǰ���·�
{
    WebCalendar.thisDay = 1;
    if (WebCalendar.thisMonth==1)
    {
        WebCalendar.thisYear--;
        WebCalendar.thisMonth=13;
    }
    WebCalendar.thisMonth--; writeCalendar();
}
function nextM()  //�����·�
{
    WebCalendar.thisDay = 1;
    if (WebCalendar.thisMonth==12)
    {
        WebCalendar.thisYear++;
        WebCalendar.thisMonth=0;
    }
    WebCalendar.thisMonth++; writeCalendar();
}
function prevY(){WebCalendar.thisDay = 1; WebCalendar.thisYear--; writeCalendar();}//��ǰ�� Year
function nextY(){WebCalendar.thisDay = 1; WebCalendar.thisYear++; writeCalendar();}//���� Year
function hiddenSelect(e){for(var i=e.options.length; i>-1; i--)e.options.remove(i); e.style.display="none";}
function getObjectById(id){return document.getElementById(id);}
function hiddenCalendar(){getObjectById("meizzCalendarLayer").style.display = "none";}
function __clearValue(){WebCalendar.objExport.value = "";WebCalendar.inputDate = null;getObjectById("meizzCalendarLayer").style.display = "none";}
function appendZero(n){return(("00"+ n).substr(("00"+ n).length-2));}//�����Զ��������
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g,"");
}
function dayMouseOver()
{
    this.className = "over";
    this.style.backgroundColor = WebCalendar.darkColor;
    if(WebCalendar.day[this.id.substr(8)].split("/")[1] == WebCalendar.thisMonth)
    this.style.color = WebCalendar.lightColor;
}
function dayMouseOut()
{
    this.className = "out"; 
	  var d = WebCalendar.day[this.id.substr(8)], a = d.split("/");

	  this.style.backgroundColor = "";
    if(a[1] == WebCalendar.thisMonth && d != WebCalendar.today)
    {
        if(WebCalendar.dateStyle && a[0] == parseInt(WebCalendar.dateStyle[4], 10))
        this.style.color = WebCalendar.lightColor;
        this.style.color = WebCalendar.wordColor;
    }
}
function writeCalendar() //��������ʾ�����ݵĴ������
{
    var y = WebCalendar.thisYear;
    var m = WebCalendar.thisMonth; 
    var d = WebCalendar.thisDay;
    WebCalendar.daysMonth[1] = (0==y%4 && (y%100!=0 || y%400==0)) ? 29 : 28;
    if (!(y<=9999 && y >= 1000 && parseInt(m, 10)>0 && parseInt(m, 10)<13 && parseInt(d, 10)>0)){
        alert("�Բ����������˴�������ڣ�");
        WebCalendar.thisYear   = new Date().getFullYear();
        WebCalendar.thisMonth  = new Date().getMonth()+ 1;
        WebCalendar.thisDay    = new Date().getDate(); }
    y = WebCalendar.thisYear;
    m = WebCalendar.thisMonth;
    d = WebCalendar.thisDay;
    frames["meizzCalendarIframe"].document.getElementById("meizzYearHead").innerHTML  = y +" ��";
    frames["meizzCalendarIframe"].document.getElementById("meizzYearMonth").innerHTML = parseInt(m, 10) +" ��";
    WebCalendar.daysMonth[1] = (0==y%4 && (y%100!=0 || y%400==0)) ? 29 : 28; //�������Ϊ29��
    var w = new Date(y, m-1, 1).getDay();
    var prevDays = m==1  ? WebCalendar.daysMonth[11] : WebCalendar.daysMonth[m-2];
    for(var i=(w-1); i>=0; i--) //������ for ѭ��Ϊ����������Դ������ WebCalendar.day����ʽ�� d/m/yyyy
    {
        WebCalendar.day[i] = prevDays +"/"+ (parseInt(m, 10)-1) +"/"+ y;
        if(m==1) WebCalendar.day[i] = prevDays +"/"+ 12 +"/"+ (parseInt(y, 10)-1);
        prevDays--;
    }
    for(var i=1; i<=WebCalendar.daysMonth[m-1]; i++) WebCalendar.day[i+w-1] = i +"/"+ m +"/"+ y;
    for(var i=1; i<39-w-WebCalendar.daysMonth[m-1]+1; i++)
    {
        WebCalendar.day[WebCalendar.daysMonth[m-1]+w-1+i] = i +"/"+ (parseInt(m, 10)+1) +"/"+ y;
        if(m==12) WebCalendar.day[WebCalendar.daysMonth[m-1]+w-1+i] = i +"/"+ 1 +"/"+ (parseInt(y, 10)+1);
    }
    for(var i=0; i<39; i++)    //���ѭ���Ǹ���Դ����д����������ʾ
    {
        var a = WebCalendar.day[i].split("/");
        WebCalendar.dayObj[i].innerHTML    = a[0];
        WebCalendar.dayObj[i].title        = a[2] +"-"+ appendZero(a[1]) +"-"+ appendZero(a[0]);
        WebCalendar.dayObj[i].bgColor      = WebCalendar.dayBgColor;
        WebCalendar.dayObj[i].style.color  = WebCalendar.wordColor;
        if((i<10 && parseInt(WebCalendar.day[i], 10)>20) || (i>27 && parseInt(WebCalendar.day[i], 10)<12))
            WebCalendar.dayObj[i].style.color = WebCalendar.wordDark;
        if(WebCalendar.inputDate==WebCalendar.day[i])    //�����������������������ϵ���ɫ
        {WebCalendar.dayObj[i].bgColor = WebCalendar.darkColor; WebCalendar.dayObj[i].style.color = WebCalendar.lightColor;}
        if (WebCalendar.day[i] == WebCalendar.today)      //���ý����������Ϸ�Ӧ��������ɫ
        {WebCalendar.dayObj[i].bgColor = WebCalendar.todayColor; WebCalendar.dayObj[i].style.color = WebCalendar.lightColor;}
    }
}
function returnDate() //�������ڸ�ʽ�ȷ����û�ѡ��������
{
    if(WebCalendar.objExport)
    {
        var returnValue;
        var a = null;
		try{a = (arguments.length==0) ? WebCalendar.day[this.id.substr(8)].split("/") : arguments[0].split("/");}catch(e){}
        if(a == null) a = WebCalendar.day[this.id.substr(8)].split("/");
        var d = WebCalendar.format.match(/^(\w{4})(-|\/)(\w{1,2})\2(\w{1,2})$/);
        if(d==null){alert("���趨�����������ʽ���ԣ�\r\n\r\n�����¶��� WebCalendar.format ��"); return false;}
        var flag = d[3].length==2 || d[4].length==2; //�жϷ��ص����ڸ�ʽ�Ƿ�Ҫ����
        returnValue = flag ? a[2] +d[2]+ appendZero(a[1]) +d[2]+ appendZero(a[0]) : a[2] +d[2]+ a[1] +d[2]+ a[0];
        if(WebCalendar.timeShow > 0)
        {
            var h = new Date().getHours(), m = new Date().getMinutes(), s = new Date().getSeconds();
			if(WebCalendar.timeShow == 1)
		    {
                returnValue += flag ? " "+ appendZero(h) +":"+ appendZero(m) +":"+ appendZero(s) : " " +  h  + ":"+ m + ":" + s;
			}
			else if(WebCalendar.timeShow == 2)
		    {
                returnValue += flag ? " "+ appendZero(h) +":"+ appendZero(m) : " " +  h  + ":"+ m;
			}
        }
        WebCalendar.objExport.value = returnValue;
        hiddenCalendar();
		if(WebCalendar.callback != null){try{eval(WebCalendar.callback + "('" + returnValue + "')")}catch(e){};WebCalendar.callback = null;}
    }
}

function CalendarTimer()
{
	this.inputObj = null;
	this.initHour = "09";
	this.initMinute = "00";
	this.randomId = 0;
    this.iFrameObject = null;
	this.width = "145px";
	this.height = "100px";

    this.init = function()
	{		
		if(this.iFrameObject != null) return; 
		this.randomId = parseInt(Math.random() * 100000);
		this.divId = "div_" + this.randomId;
		var _div = document.createElement("DIV");
		_div.id = this.divId;
		_div.style.display ="none";
		_div.style.position = "absolute";
		_div.style.width = this.width;
		_div.style.height = this.height;
		_div.style.cursor = "default";
		_div.style.backgroundColor = "#FFFFFF";
		document.body.appendChild(_div);

		var s = "<table height=\"100\" style=\"border:solid 1px #000000;font-size:12px\">";
		s = s + "<tr><td>&nbsp;<td></tr>";
		s = s + "<tr><td align=\"center\" nowrap>";
		s = s + "<select id=\"_h" + this.randomId + "\">";
		for(var i=0; i<=23; i++) s = s + "<option" + ((this.initHour==i)?" selected":"")  + " value=\"" + ((i<10)?"0":"") + i + "\">" + ((i<10)?"0":"") +  i + "</option>";
		s = s+ "</select>Сʱ";

		s = s+ "<select id=\"_m" + this.randomId + "\">";
		for(var i=0; i<60; i++) s = s + "<option" + ((this.initMinute==i)?" selected":"")  + "  value=\"" + ((i<10)?"0":"") + i + "\">" + ((i<10)?"0":"") + i + "</option>";
		s = s+ "</select>����";

		s = s+ "</td></tr>";
		s = s+ "<tr><td align=\"center\" height=\"40\"><input type=\"button\" style=\"width:40px;border:solid 1px #D0D0D0; background-color:#FFFFFF\" onclick=\"__calendarTimer.setTime()\" value=\"ѡ��\">";
		s = s+ " <input type=\"button\" style=\"width:40px;border:solid 1px #D0D0D0; background-color:#FFFFFF\" onclick=\"__calendarTimer.clearTime()\" value=\"���\">";
		s = s+ " <input name=\"btc\" onclick='document.getElementById(\"" + __calendarTimer.divId + "\").style.display=\"none\";' type=\"button\" style=\"width:40px;border:solid 1px #D0D0D0; background-color:#FFFFFF\" value=\"�ر�\">";
		s = s+ "</td></tr>";
		s = s+ "</table>";

		_div.innerHTML = s;
	}

	this.setSelectedTime = function()
	{
		try
		{
			v = this.inputObj.value;
		}
		catch(e){}

		if(v == "") 
		{
			document.getElementById("_h" + this.randomId).value = this.initHour;
			document.getElementById("_m" + this.randomId).value = this.initMinute;
			return;
		}

		var t = v.split(":");

		document.getElementById("_h" + this.randomId).value = t[0];
		document.getElementById("_m" + this.randomId).value = t[1];
	}

	this.setTime = function()
	{
		var v = document.getElementById("_h" + this.randomId).options[document.getElementById("_h" + this.randomId).selectedIndex].value + ":" + 
									document.getElementById("_m" + this.randomId).options[document.getElementById("_m" + this.randomId).selectedIndex].value;
		this.inputObj.value = v;
		document.getElementById(this.divId).style.display = "none";
	}

	this.clearTime = function()
	{
		this.inputObj.value = "";
		document.getElementById(this.divId).style.display = "none";
	}
}

var __calendarTimer = null;
function calTimer(inputObj)
{
	if(inputObj == null) {alert("timer inputObject error!");return;}
	var e = (window.event)?window.event.srcElement:arguments.callee.caller.arguments[0].target;
	if(__calendarTimer == null)  
	{
	    __calendarTimer = new CalendarTimer();
		__calendarTimer.init();		
	}
	__calendarTimer.inputObj = inputObj;

	var o = document.getElementById(__calendarTimer.divId).style;
	
	var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
	while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
	o.display = "";
	var cw = 145, ch = 100;
	var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
	if (document.body.clientHeight + dt - t - h >= ch) o.top = ((p=="textarea")? t + h : t + h + 6) + "px";
	else o.top  = ((t - dt < ch) ? ((p=="textarea")? t + h : t + h + 6) : t - ch) + "px";
	if (dw + dl - l >= cw) o.left = l + "px"; else o.left = (((dw >= cw) ? dw - cw + dl : dl)-30) + "px";

	__calendarTimer.setSelectedTime();
}

function __doHidden(evt)
{
    var e = (window.event)?window.event.srcElement:((evt)?evt.target:null);
    if(e && !WebCalendar.eventSr && WebCalendar.eventSrc != e) hiddenCalendar();
}

if(document.attachEvent)
{
    document.attachEvent("onclick", __doHidden);
}
else if(document.addEventListener)
{
    document.addEventListener("click", __doHidden, false);
}

var WebCalendar = new __WebCalendar();