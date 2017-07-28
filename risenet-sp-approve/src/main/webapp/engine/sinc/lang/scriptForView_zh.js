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
            alert("金額超過億位");
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
    
    if(isAlert && !confirm("確認刪除該記錄嗎？")) return;
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
        alert("請輸入頁碼");
        pageInput.focus();
        return;
    }
    if(!isInteger(pageStr) || pageStr.indexOf("-") != -1)
    {
        alert("頁碼輸入必需爲整數");
        pageInput.focus();
        return;
    }
    if(parseInt(pageStr, 10)>maxPage)
    {
        alert("頁碼範圍在[1-" + maxPage + "]之間");
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