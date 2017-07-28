function getSelectText(selectObject)
{
    if(selectObject.selectedIndex != -1)
    {
        return selectObject.options[selectObject.selectedIndex].text;
    }
    else
    {
        return ""
    }
} 

function refresh(formName)
{//refresh the query

    if(formName == null)
    {
        var oo = document.getElementsByTagName("FORM");
        for(var i=0; i<oo.length ;i++)
        {
            if(oo[i].name.indexOf("frmQuery_") != -1)
            {
                oo[i].submit();
                return;
            }
        }
        document.frmQuery.submit();
    }
    else
    {
        var frm = eval("document." + formName);
        if(frm != null) frm.submit();
    }
}

function isInteger(strNumber)
{
    var pattern = new RegExp("^(-)?\\d+$");
    
    return pattern.test(strNumber);
}

function RMB(value, maxBit, padding, empty)
{
    this.upper = new Array("零","壹","贰","叁","肆","伍","陆","柒","捌","玖");
    this.site = new Array("分","角","元","拾","佰","仟","万","拾","佰","仟","亿");
    this.value = value.replace(/[^0-9\.]*/g, "");
    this.leftLen = 0;
    this.maxBit = (maxBit != null && maxBit>0 && maxBit<10)?maxBit:9;
    this.padding = (padding != null)?padding:" ";
    this.empty = (empty != null)?empty:" ";

    this.toString = function()
    {
        var upperStr = "";
        var left = "",right = "";
        var s = new String(this.value);
        var pos = s.indexOf(".");
        
        if(pos != -1)
        {
            left = s.substring(0, pos);
            right = s.substring(pos+1);
        }
        else
        {
            left = s;
        }
        this.leftLen = left.length;
        if(this.leftLen == 1 && left == "0") this.leftLen = 0;

        var c;
        if(this.leftLen > this.maxBit)
        {
            alert("金额超过亿位");
            return "";
        }
        if(this.leftLen<this.maxBit)
        {
            for(var i=this.maxBit; i>this.leftLen; i--)
            {
                upperStr = upperStr + this.padding + this.empty + this.site[i+1];
            }
        }

        for(var i=0; i<this.leftLen; i++)
        {
            c = parseInt(left.substring(i,i+1));
            upperStr = upperStr + this.padding + this.upper[c] + this.site[this.leftLen-i+1];
        }
        for(var i=0; i<2; i++)
        {
            c = (right.length>i)?parseInt(right.substring(i,i+1)):0;
            upperStr = upperStr + this.padding + this.upper[c] + this.site[1-i];
        }
  
        return upperStr;
    }
}

function round(digit, how, thousand)  
{  
    var _digit = new String(Math.round(digit*Math.pow(10, how))/Math.pow(10, how));  
    var pos = _digit.lastIndexOf(".");

    if(pos !=-1)
    {
        if((_digit.length-pos-1)<how)
        {
            _digit = _digit + multiChar("0", how-(_digit.length-pos-1));
        }
    }
    else
    {
        _digit = _digit + "." + multiChar("0", how);
    }
    return (thousand)?_formatThousandNumber(_digit):_digit;  
}

function _formatThousandNumber(value)
{
    var isNegative = value.charAt(0) == '-';
    if(isNegative) value = value.substring(1);
    var bufValue = "";
    var pos = value.indexOf(".");
    var strValue = null;
    var tail = null;
    if(pos == -1)
    {
        strValue = value;
        tail = "";
    }
    else
    {
        strValue = value.substring(0, pos);
        tail = value.substring(pos);
    }
    var m = strValue.length;

    if(m > 3)
    {
        for(var i=m; i>0; i--)
        {
            if(i>=3 && i<m && (i%3) == 0) bufValue = bufValue + ",";
            bufValue = bufValue + strValue.charAt(m-i);
        }

        value = bufValue + tail;
    }

    return isNegative?"-"+value:value;
}

function multiChar(_char, num)
{
    var chars = "";
    for(var i=0; i<num; i++)
    {
        chars = chars + _char;
    }
    return chars;
}

function deleteLine(fieldIndex, formPrefix, values, isAlert)
{
    var frm = eval("document." + formPrefix);
    
    if(isAlert && !confirm("确认删除该记录吗？")) return;
    frm.ids.value = values;
    frm.fieldAlias.value = fieldIndex;
    frm.action = "deletelinedata.jsp";
    frm.submit();
}

function goPage(frmObj, page)
{
    var frm = eval("document." + frmObj);
    frm.currentPage.value = page;
    frm.submit();
}

function goPage1(frmObj)
{
    var frm = eval("document." + frmObj);
    var pageInput = eval("document.getElementById(\"" + frmObj +"_inputNum\")");
    var pageStr = pageInput.value;
    var maxPage = parseInt(frm.totalPage.value, 10);

    if(pageStr == "")
    {
        alert("请输入页码");
        pageInput.focus();
        return;
    }
    if(!isInteger(pageStr) || pageStr.indexOf("-") != -1)
    {
        alert("页码输入必需为整数");
        pageInput.focus();
        return;
    }
    if(parseInt(pageStr, 10)>maxPage)
    {
        alert("页码范围在[1-" + maxPage + "]之间");
        pageInput.focus();
        return;
    }
    frm.currentPage.value = pageStr;
    frm.submit();
}

function reSort(frmObj, fieldAlias, method)
{
    var frm = eval("document." + frmObj);
    if(frm.currentPage != null)frm.currentPage.value = 1;
    frm.otherSortField.value = fieldAlias + ((method!= null && method==1)?" desc":"");
    frm.submit();
}

function getSelectValue(selectObject)
{
    if(selectObject.selectedIndex != -1)
    {
        return selectObject.options[selectObject.selectedIndex].value;
    }
    else
    {
        return ""
    }
}

function chysoftXMLHttp(url, fun)
{
    this.xmlhttp = null;;    
    this.url = url;

	if(window.XMLHttpRequest)
    {
        this.xmlhttp = new XMLHttpRequest();
    }
    else if(window.ActiveXObject)
    {
        try 
        {
            this.xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
        } 
        catch(e) 
        {
            try{this.xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");}catch(e){}
        }
    }
	var _xmlhttp = this.xmlhttp;
	this.xmlhttp.onreadystatechange = function()
    {
	    if(4==_xmlhttp.readyState && _xmlhttp.status==200)
	    {
		    if(typeof(fun) != "function"){alert("非法的回调函数");return;}
			fun(_xmlhttp);
	    }
    }
    this.submit =function()
    {
        this.xmlhttp.open("post", this.url, true);
        this.xmlhttp.setRequestHeader('Conten-type','application/x-www-form-urlencode');
        this.xmlhttp.send(null);
    }
}

function __getEvent()
{
    if(window.event)
    {
        return window.event.srcElement;
    }

    var _caller = __getEvent.caller;
    while(_caller != null)
    {
        var _argument = _caller.arguments[0];
        if(_argument)
        {
            var _temp = _argument.constructor;
            if(_temp.toString().indexOf("Event") != -1)
            {
                return _argument.target;
            }
        }
        _caller = _caller.caller;
    }
    return null;
}

function closeEditDiv(index)
{
    document.getElementById("__divEdit" + ((index)?index:"")).style.display = "none";
}

function posEditDiv(left, top, index)
{
    if(left != null)
    {
        document.getElementById("__divEdit" + ((index)?index:"")).style.left = left + "px";
    }
    if(top != null)
    {
        document.getElementById("__divEdit" + ((index)?index:"")).style.top = top + "px";
    }
}

function iframeEdit(url, width, height, isNotAutoClose, paddingTop, paddingLeft, index)
{
	var selectInput = __getEvent();
	var _width = width;
	var _height = height;
	window.__divEdit = selectInput;
	
	var divName = "__divEdit" + ((index)?index:"");
	var frmName = "__frmEdit" + ((index)?index:"");
	var frm = document.getElementById(frmName);
	
	if(_width == null) _width = "400";
	if(_height == null) _height = "300";
	var e = selectInput;
	
	if(document.getElementById(divName) == null)
	{ 
		var div = document.createElement("DIV");
		div.name = divName;
		div.id = divName;
		div.style.position = "absolute";
		div.style.border = "1px solid #000000";
		document.body.appendChild(div);
        
        var divTitle = document.createElement("DIV");
        divTitle.style.display = "none";
        divTitle.style.cursor = "move";
		divTitle.id = divName + "_title";
        divTitle.style.width = _width + "px";
		divTitle.style.height = "20px";
        divTitle.innerHTML = "&nbsp;";
        divTitle.style.backgroundColor = "#509050";
		div.appendChild(divTitle);

		frm = document.createElement("IFRAME");			
		frm.name = frmName;
		frm.id = frmName;
		frm.scrolling = "auto";
		frm.frameBorder = "0";
		div.appendChild(frm);
		
		if(!isNotAutoClose)
		{
			if(document.attachEvent)
			{
				document.attachEvent("onclick", function(){try{if(__getEvent() != window.__divEdit) document.getElementById(divName).style.display = "none";}catch(e){}});
			}
			else if(document.addEventListener)
			{
				document.addEventListener("click", function(){try{if(__getEvent() != window.__divEdit) document.getElementById(divName).style.display = "none";}catch(e){}}, false);
			}
		}
	}

	frm.style.width = _width + "px";
	frm.style.height = _height + "px";
	if(window.__frmEditUrl != url) frames[frmName].location = url;
	window.__frmEditUrl = url;

	var o = document.getElementById(divName).style;
	var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
	while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
	var cw = _width, ch = _height;
	if(cw >= 400) cw = cw + 30;
	if(!paddingTop) paddingTop = 0;
	if(!paddingLeft) paddingLeft = 0;
	var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
	if (document.body.clientHeight + dt - t - h >= ch) o.top = (((p=="textarea")? t + h : t + h + 6)+paddingTop) + "px";
	else o.top  = (((t - dt < ch) ? ((p=="textarea")? t + h : t + h + 6) : t - ch)+paddingTop) + "px";
	if (dw + dl - l >= cw) o.left = (l+paddingLeft) + "px"; else o.left = (((dw >= cw) ? dw - cw + dl : dl)+paddingLeft) + "px";
	o.display="";
}