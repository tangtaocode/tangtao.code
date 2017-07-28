var theInputObject      = null;
var theInputObject_code = null;
var isSetStatus = false;
var theMethod = 1;

//is form elements
function isFormObject(theObject)
{
    return theObject != null;
}

function getRadioCheckedValue(radio)
{
    if(radio != null)
    {
        if(radio.length==null)
        {
	    if(radio.checked) return radio.value;
        }
        else
        { 
	    for(var i=0; i<radio.length; i++)
            {
                if(radio[i].checked) return radio[i].value;
            }
        } 
    }
    return "";
}

function getSelectValue(selObj)
{
    return (selObj.selectedIndex == -1)?"":selObj.options[selObj.selectedIndex].value;
}

//for onkeypress
function getInputAssis(xmlId, obj, obj_code)
{     
    theMethod = 1;
    if(obj_code != null) obj_code.value = "";
    document.getElementById('textInputAssistance').style.display='';
    frames["textinput"].clearTable();
    frames["textinput"].writeTable(xmlId, obj.value);
    try{
    window.event.cancelBubble = true;}catch(e){}
}

function setInputAssis(xmlId, obj, obj_code)
{ 
    var e = obj;
    var o = document.getElementById("textInputAssistance").style;
    var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
    while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
    var cw = document.getElementById('textInputAssistance').clientWidth, ch = document.getElementById('textInputAssistance').clientHeight;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
    if (document.body.clientHeight + dt - t - h >= ch) o.top = ((p=="textarea")? t + h : t + h + 6) + "px";
    else o.top  = ((t - dt < ch) ? ((p=="textarea")? t + h : t + h + 6) : t - ch) + "px";
    if (dw + dl - l >= cw) o.left = l + "px"; else o.left = ((dw >= cw) ? dw - cw + dl : dl) + "px";
    o.display = "";
    frames["textinput"].clearTable();
    if(obj != theInputObject)
    {
        theInputObject = obj;    
        theInputObject_code = obj_code;
        frames["textinput"].clearNextXmlNode();
        frames["textinput"].writeTable(xmlId);
    }
    else
    {
        theMethod = 2;
        theInputObject = obj;
        theInputObject_code = obj_code;
        frames["textinput"].writeTable(xmlId,obj.value);
    }
    try{
    window.event.cancelBubble = true;}catch(e){}
}

function setValue(strTheCode, strTheValue, tempXmlNode)
{   
    var obj = __getEvent();
	  var isInputByHand = (obj != null && obj.tagName != null && (obj.tagName == "INPUT" || obj.tagName == "TEXTAREA"));

	  if(tempXmlNode == null)
    {
        theMethod = 1;
        if(theInputObject_code != null) theInputObject_code.value = strTheCode;
        if(isInputByHand)
        {
            theInputObject.value = strTheValue;
        }
        else
        {
            theInputObject.value = strTheValue;
        }
        try
        {
            _doSetValue(theInputObject, strTheValue, strTheCode);
        }
        catch(e){}
		    theInputObject.blur();
        theInputObject = null;
        theInputObject_code = null;        
        document.getElementById('textInputAssistance').style.display = "none";
        frames["textinput"].clearNextXmlNode();
    }
    else
    {
        if(theInputObject != null && theMethod == 1) theInputObject.value = strTheCode;
        frames["textinput"].clearTable();
        frames["textinput"].writeTable(tempXmlNode,strTheCode,1);
    }
}

//
function initSelElement(xmlID,objSelect,datasrc,datafld,isCodeItem)
{
    var optionTemp;
    var xmlTopNode;
    var xmlElementNode;
    var strCode = "";
    var strValue ="";

    //change to code item by the attribute of the select Object;
    if(objSelect.getAttribute("isCodeItem") == "1") isCodeItem = true;
    if(xmlID == null || xmlID.childNodes == null || xmlID.childNodes.length == null) return;
    if(xmlID.childNodes.length <2 )
    {
        xmlTopNode = __loadXML(xmlID.getAttribute("src"));
    }
    else
    {
    	  xmlTopNode = xmlID.childNodes(1);
    }

    optionTemp = document.createElement("OPTION")
    optionTemp.code = strCode;
    optionTemp.value = strValue;
    optionTemp.text = strValue;
    objSelect.add(optionTemp);
    var els = xmlTopNode.getElementsByTagName("Element");
    for(var i=0; i<els.length; i++)
    {
        xmlElementNode = els[i].firstChild; 
        strValue = xmlElementNode.nodeValue;
        if(isCodeItem)  //if the field is code item
        {
            strCode = ((strCode=els[i].getAttribute("code"))!=null)?strCode:"";
        }
        else  
        {
            strCode = strValue;
        }
        optionTemp = document.createElement("OPTION")
        optionTemp.value = strCode;
        optionTemp.text = strValue;
        objSelect.add(optionTemp);
    }
    objSelect.dataSrc = datasrc;
    objSelect.dataFld = datafld;
}


function __loadXML(xmlUrl)
{
    var xmlDoc = null; 
    if(window.ActiveXObject)
    { 
        xmlDoc = new ActiveXObject('Msxml2.DOMDocument'); 
        xmlDoc.async = false; 
        xmlDoc.load(xmlUrl); 
    } 
    else if (document.implementation && document.implementation.createDocument)
    { 
        var xmlhttp = new window.XMLHttpRequest(); 
        xmlhttp.open("GET", xmlUrl, false); 
        xmlhttp.send(null); 
        xmlDoc = xmlhttp.responseXML.documentElement; 
    } 
    
    return xmlDoc; 
} 


function initSelElementByValue(xmlID, objSelect, value, isCodeItem)
{   
    var optionTemp;
    var xmlTopNode;
    var xmlElementNode;
    var strCode = "";
    var strValue ="";
    
    if(objSelect == null ){alert("元素不在form表单内"); return}
    if(objSelect.getAttribute("isCodeItem") == "1") isCodeItem = true;
    if(xmlID == null || xmlID.childNodes == null || xmlID.childNodes.length == null) return;

    if(xmlID.childNodes.length <2 )
    {
        xmlTopNode = __loadXML(xmlID.getAttribute("src"));
    }
    else
    {
    	  xmlTopNode = xmlID.childNodes[1];
    }

    optionTemp = document.createElement("OPTION")
    optionTemp.code = strCode;
    optionTemp.value = strValue;
    optionTemp.text = strValue;
    objSelect.add(optionTemp);

    var els = xmlTopNode.getElementsByTagName("Element");
    for(var i=0; i<els.length; i++)
    {
        xmlElementNode = els[i].firstChild; 
        strValue = xmlElementNode.nodeValue;
        if(isCodeItem)  //if the field is code item
        {
            strCode = ((strCode=els[i].getAttribute("code"))!=null)?strCode:"";
        }
        else  
        {
            strCode = strValue;
        }
        optionTemp = document.createElement("OPTION")
        optionTemp.value = strCode;
        optionTemp.text = strValue;
		if(value == strCode) optionTemp.selected = true;
        objSelect.add(optionTemp);
    }
}

function refreshSelElement(xmlID,objSelect,datasrc,datafld,isCodeItem,filter)
{
    var optionTemp;
    var xmlTopNode;
    var xmlElementNode;
    var strCode = "";
    var strValue ="";
    var strItemType = "";

    if(xmlID == null || xmlID.childNodes == null || xmlID.childNodes.length == null) return;
    if(xmlID.childNodes.length <2 )
    {
    	   xmlTopNode = __loadXML(xmlID.getAttribute("src"));
    }
    else
    {
    	   xmlTopNode = xmlID.childNodes(1);
    }
    objSelect.options.length = 0;
    optionTemp = document.createElement("OPTION")
    optionTemp.code = strCode;
    optionTemp.value = strValue;
    optionTemp.text = strValue;
    objSelect.add(optionTemp);

    var els = xmlTopNode.getElementsByTagName("Element");
    for(var i=0; i<els.length; i++)
    {
        xmlElementNode = els[i].firstChild; 
        strValue = xmlElementNode.nodeValue;
        if(isCodeItem)  //if the field is code item
        {
            strCode = ((strCode=els[i].getAttribute("code"))!=null)?strCode:"";
        }
        else  
        {
            strCode = strValue;
        }
        if(eval(filter)) continue;
        optionTemp = document.createElement("OPTION")
        optionTemp.value = strCode;
        optionTemp.text = strValue;
        objSelect.add(optionTemp);
    }
	
    objSelect.dataSrc = datasrc;
    objSelect.dataFld = datafld;
}

function createSelect(values, objSelect)
{
    var optionTemp;
    var arrTemp = values.split("\r\n");
    var pos = 0;

    objSelect.options.length = 0;
    optionTemp = document.createElement("OPTION")
    optionTemp.value = "";
    optionTemp.text = "";
    objSelect.add(optionTemp);
    if(arrTemp != null)
    {
        for(var i=0; i<arrTemp.length; i++)
        {
            pos = arrTemp[i].indexOf(",");
            if(pos == -1) continue;
            optionTemp = document.createElement("OPTION")
            optionTemp.value = arrTemp[i].substring(0, pos);
            optionTemp.text = arrTemp[i].substring(pos + 1);
            objSelect.add(optionTemp);
        }
    }
}

function callBack(http, objSelect, fn)
{
    if(http.readyState == 4 )
    {
        html = http.responseText ;
        if(http.status == 200 || http.status == 304 )
        {
            if(fn == null)
            {
                createSelect(html, objSelect);
            }
            else
            {
                eval(fn + "(html, objSelect)");
            }
        }
        else
        {
            alert( 'XML request error: ' + http.statusText + ' (' + http.status + ')');
        }
    }
}

function getDynamicOptions(url, objSelect, fn)
{
    var http = null; 

    if(window.XMLHttpRequest)		// Gecko
    {
        http = new XMLHttpRequest() ;
        http.onreadystatechange = function()
        {
            callBack(http, objSelect, fn);
        }
        http.open("GET", url, true); 
        http.send(null);
    }
    else if(window.ActiveXObject )	// IE
    {
        http = new ActiveXObject("MsXml2.XMLHTTP") ;//MsXml2.XMLHTTP
        http.onreadystatechange = function() 
        {
            callBack(http, objSelect, fn);
        } 
        http.open("GET", url, true); 
        http.send(); 
    }
    return true;  
}

function _initOtherValues(http, objArr)
{
    if(http.readyState == 4 )
    {
        if(http.status == 200)
        {
		    var xmlDoc = http.responseXML.documentElement;
			var xroot = xmlDoc.getElementsByTagName("value");
            //处理数据
            if(xroot != null && xroot.length >0)// && objArr != null)
			{
			    for(var i=0; i<xroot.length; i++)
				{
				    if(document.getElementById(objArr[i]) != null)
				    {
					    document.getElementById(objArr[i]).value = xroot[i].childNodes[0].nodeValue;
				    }
				}
			}
        }
        else
        {
            alert( 'XML request error: ' + http.statusText + ' (' + http.status + ')');
        }
    }
}

function _callInitValues(objArr, value)
{
    var http = null; 
    var url = "ajax.jsp?t=我的测试"; //指定地址

    if(window.XMLHttpRequest)		// Gecko
    {
        http = new XMLHttpRequest() ;
        http.onreadystatechange = function()
        {
            _initOtherValues(http, objArr);
        }
        http.open("GET", url, true); 
        http.send(null);
    }
    else if(window.ActiveXObject )	// IE
    {
        http = new ActiveXObject("MsXml2.XMLHTTP") ;//MsXml2.XMLHTTP
        http.onreadystatechange = function() 
        {
            _initOtherValues(http, objArr);
        } 
        http.open("GET", url, true); 
        http.send(); 
    }
    return true;  
}

function resize(obj)
{
    if(self == top || document.getElementById("menu") == null) return;
    if(top.document.getElementById("menu").style.display != "none")
    {
        top.document.getElementById("menu").style.display = "none";
        top.document.getElementById("menu").parentElement.width = "1";
        top.document.getElementById("menu").parentElement.width = "777";
        obj.title="还原";
    }
    else
    {
        top.document.getElementById("menu").style.display = "";
        top.document.getElementById("menu").parentElement.width = "174";
        top.document.getElementById("menu").parentElement.width = "603";
        obj.title="全屏";
    }
}

function _otherAction(_button)
{
    try
    {
        if(_button != null && !_button.getAttribute("ret")) _button.disabled = 1;
		__changeElementValues();
    }
    catch(e)
    {;}

    return true;
}

//use for workflow
function __forbidReSubmit()
{
    try
    {
        _button = (window.event)?window.event.srcElement:arguments.callee.caller.arguments[0].target;
        if(_button != null && !_button.getAttribute("ret")) _button.disabled = 1;
    }
    catch(e)
    {
        ;//do nothing
    }
}

function confirmSubmit(frmObj)
{
    var _button = null; 
    var msg = null;

    try
    {
        _button = (window.event)?window.event.srcElement:arguments.callee.caller.arguments[0].target;
        frmObj._button = _button;
		if(frmObj._button && frmObj._button.tagName =="INPUT") 
        {
			var _actionName = frmObj._button.value.replace(/(\s)+/g, "");
			if(_actionName != "确认") msg = "确认" + _actionName + "吗？";
	    }
    }
    catch(e)
    {
        ;//do nothing
    }

    try
    {
        if(_button != null && _button.type=="submit" && _button.form != null)
        {
            if(!_button.form.onsubmit)
            {
                _button.form.onsubmit = function()
                {
                    if(!validInput()) return false;
                    try{if(_sMsg != null) msg = _sMsg;}catch(e){;}
                    if(msg == null) msg = "确认提交吗？";
                    return confirm(msg) && _otherAction(_button);
                }
            }
            return;
        }        
    }
    catch(e)
    {
        ;//do nothing
    }

    if(!validInput()) return false;
    try{if(_sMsg != null) msg = _sMsg;}catch(e){;}
    if(msg == null) msg = "确认提交吗？";
    if(confirm(msg))
    {
        _otherAction(_button);
		if(frmObj == null)
        {    
	        document.forms[0].submit();
        }
        else
        {
            frmObj.submit()
        }
    }
}

function confirmDelete(frmObj)
{
    if(frmObj == null) frmObj = document.forms[0];
    try
    {
        if(confirm("确认删除吗？"))
        {
            frmObj.target = "";
            frmObj.action_Type.value = 2;
            frmObj.submit();
        }
    }
    catch(e)
    {
        ;//do nothing
    }
}

function doQueryAction()
{
    try
    {
        if(!validInput()) return false;
    }
    catch(e)
    {
        ;//do nothing
    }
    return true;
}

function querySubmit(obj)
{
    try
    {
        if(!validInput()) return false;
    }
    catch(e)
    {
        ;//do nothing
    }
    //obj.disabled=1
    document.forms[0].submit();
}

//check
function validSave()
{
    try
    {
        if(!validInput()) return false;
    }
    catch(e)
    {
        alert("发生错误\r\n" + e);
        return false;
    }
    return true;
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

    this.toString = function(l, r)
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
		if(l)
		{
			if(this.leftLen<this.maxBit)
			{
				for(var i=this.maxBit; i>this.leftLen; i--)
				{
					upperStr = upperStr + this.padding + this.empty + this.site[i+1];
				}
			}
		}

        for(var i=0; i<this.leftLen; i++)
        {
            c = parseInt(left.substring(i,i+1));
            upperStr = upperStr + this.padding + this.upper[c] + this.site[this.leftLen-i+1];
        }

		if(r)
		{
			for(var i=0; i<2; i++)
			{
				c = (right.length>i)?parseInt(right.substring(i,i+1)):0;
				upperStr = upperStr + this.padding + this.upper[c] + this.site[1-i];
			}
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

function saveAction(frmObj)
{    
	  if(obj == null) frmObj = document.forms[0];
	  frmObj.submit();

    return true;
}

function validDelete()
{
    if(self.location.href.indexOf("&edittype=1") == -1 || self.location.href.indexOf("&querystring") == -1) return false;
    
    return true;
}

function deleteAction(frmObj)
{
    if(self.location.href.indexOf("&edittype=1") == -1 || self.location.href.indexOf("&querystring") == -1) return;
    
    if(frmObj == null) frmObj = document.forms[0];
    try
    {
        frmObj.action_Type.value = 2;
        frmObj.submit();
    }
    catch(e)
    {
        alert("发生错误\r\n" + e);
        return false;
    }
    
    return true;
}

function hideSubmitButton()
{
    if(document.all["button"] == null) return;
    if(self.parent != top)
    {
        if(document.all["button"].length != null)
        {
            for(var k=0; k<document.all["button"].length; k++)
            {
                document.all["button"][k].style.display = "none";
            }
        }
        else
        {
            document.all["button"].style.display = "none";
        }
    }
}

function __doClick()
{
    if(document.getElementById("textInputAssistance") != null)
    {
		theInputObject = null;
		document.getElementById("textInputAssistance").style.display = "none";
    }
    
    try
    {
        if(!isSetStatus)
        { 
            setStatus();
            try
            {
               pageInit();  //the function with you want to init the value first
            }
            catch(e)
            {
                ;//do nothing	
            }
            isSetStatus = true;
        }
    }
    catch(e){;}
}

function getSelectText(selectObject)
{
    if(selectObject.selectedIndex != -1)
    {
        return selectObject.options[selectObject.selectedIndex].text;
    }
    else
    {
        return "";
    }
} 


function getSelectValue(selectObject)
{
    if(selectObject.selectedIndex != -1)
    {
        return selectObject.options[selectObject.selectedIndex].value;
    }
    else
    {
        return "";
    }
} 

function enabledButton(temp_Id)
{
    if(document.all["button_query_" + temp_Id] != null)
    {
        if(document.all["button_query_" + temp_Id].length == null)
        {
            document.all["button_query_" + temp_Id].disabled = 0;
        }
        else 
        {
            for(var i=0; i<document.all["button_query_" + temp_Id].length; i++)
            {
                document.all["button_query_" + temp_Id][i].disabled = 0;
            }
        }
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
        if(frm != null)frm.submit();
    }
}

//query
function isInteger(strNumber)
{
    var pattern = new RegExp("^(-)?\\d+$");
    
    return pattern.test(strNumber);
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

function isValidEmail(strMail)
{
    var pattern = new RegExp("^([a-zA-Z0-9_-])+(\\.[a-zA-Z0-9_-]+)*@([a-zA-Z0-9_-])+(\\.[a-zA-Z0-9_-]+)*$");
	if(strMail == "") return true;

    return pattern.test(strMail);
}

function validCharCount(name, value, min, max)
{
    if(min>0 && value.length < min)
    {
        alert(name + "输入小于" + min + "个字符");
        return false;
    }
        
    if(max>0 && value.length > max)
    {
        alert(name + "输入大于" + max + "个字符");
        return false;
    }
        
    return true;
}

function validNumericRange(name, value, min, max)
{
    if(value == "") return true;
    var _value  = parseFloat(value);

    if(min != null && _value < min)
    {
        alert(name + "数值输入小于" + min) ;
        return false;
    }
    if(max != null && _value > max)
    {
        alert(name + "数值输入大于" + max) ;
        return false;
    }
    return true;       
}   

function validNumeric(name, v1, type, v2)
{
    if(v1 == "") return true;
    switch(type)
    {
        case ">":
          if(v1<=v2){alert(name+ "的数值必须大于" + v2);return false;}else{break;}
        case ">=":
          if(v1<v2){alert(name+ "的数值必须大于等于" + v2); return false;}else{break;}
        case "<":
          if(v1>=v2){alert(name+ "的数值必须小于" + v2);return false;}else{break;}
        case "<=":
          if(v1>v2){alert(name+ "的数值必须小于等于" + v2);return false;}else{break;}
        default:
          alert("不支持的数值比较方式，请联系系统管理员");
          return false;
	}
    return true;
}

function compareValue(name1, o1, name2, ctrlName, type)
{
    var o2 = document.getElementById(ctrlName);
	if(o1 == null)
    {
	    alert("元素[" + name1 + "]不存在"); return false;
	}
	if(o2 == null)
    {
	    alert("元素[" + name2 + "]不存在"); return false;
	}
    if(o1.value == "" && o2.value == "" || isNaN(o1.value) || isNaN(o2.value)) return true;
    var v1 = parseFloat(o1.value);
    var v2 = parseFloat(o2.value);
    switch(type)
    {
        case ">":
          if(v1<=v2){alert(name1+ "的数值必须大于" + name2);return false;}else{break;}
        case ">=":
          if(v1<v2){alert(name1+ "的数值必须大于等于" + name2); return false;}else{break;}
        case "<":
          if(v1>=v2){alert(name1+ "的数值必须小于" + name2);return false;}else{break;}
        case "<=":
          if(v1>v2){alert(name1+ "的数值必须小于等于" + name2);return false;}else{break;}
        default:
          alert("不支持的数值比较方式，请联系系统管理员");
          return false;
	}
    return true;
}

function compareDate(name1, o1, name2, ctrlName, type)
{
    var o2 = document.getElementById(ctrlName);
    if(o1 == null)
      {
        alert("元素[" + name1 + "]不存在"); return false;
    }
    if(o2 == null)
      {
        alert("元素[" + name2 + "]不存在"); return false;
    }
    if(o1.value == "" && o2.value == "") return true;
	
	var s1 = new String(o1.value);
    var s2 = new String(o2.value);
	s1 = s1.replace(/-/g,"/");
    s2 = s2.replace(/-/g,"/");

	var v1 = new Date(s1);
	var v2 = new Date(s2);
	switch(type)
    {
        case ">":
          if(v1<=v2){alert(name1+ "必须大于" + name2);return false;}else{break;}
        case ">=":
          if(v1<v2){alert(name1+ "必须大于等于" + name2); return false;}else{break;}
        case "<":
          if(v1>=v2){alert(name1+ "必须小于" + name2);return false;}else{break;}
        case "<=":
          if(v1>v2){alert(name1+ "必须小于等于" + name2);return false;}else{break;}
        default:
          alert("不支持的时间比较方式，请联系系统管理员");
          return false;
	  }
	  return true;
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
    if(frm.currentPage != null) frm.currentPage.value = 1;
    frm.otherSortField.value = fieldAlias + ((method!= null && method==1)?" desc":"");
    frm.submit();
}

function uniquecheck(o, tid, fid, name)
{
    if(window.XMLHttpRequest)
    {
        ajax = new XMLHttpRequest();
    }
    else if(window.ActiveXObject)
    {
        try 
        {
            ajax = new ActiveXObject("Msxml2.XMLHTTP");
        } 
        catch(e) 
        {
            try 
            {
                ajax = new ActiveXObject("Microsoft.XMLHTTP");
            }catch(e){}
        }
    }
    if(ajax)
    {	
        ajax.onreadystatechange = function() 
        {
            if(ajax.readyState == 4 )
            {
                var text = ajax.responseText ;
                if(ajax.status == 200 || ajax.status == 304 )
                {
                    if(text != "")
                    {
                        alert(name + text);
                        if(o.readOnly != 1 && o.disabled != 1) o.focus();
                        if(o.readOnly != 1 && o.disabled != 1 && o.tagName != "SELECT") o.select();
                    }
                }
                else
                {
                    alert( 'XML request error: ' + ajax.statusText + ' (' + ajax.status + ')');
                }
            }
        } 
        var url= "uniquerecord.jsp?tid=" + tid + "&fid=" + fid + "&v=" + o.value;
		    ajax.open("POST", url, true);  
        //定义传输的文件HTTP头信息 
        ajax.setRequestHeader("Content-Type","application/x-www-form-urlencoded"); 
        ajax.send(null);
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

function docKeyDown(evt) 
{
    var srcObject = __getEvent();
    if(srcObject.tagName == "INPUT" && (srcObject.getAttribute("type") == "text" || srcObject.getAttribute("type") == null || 
		srcObject.getAttribute("type") == "" || srcObject.getAttribute("type") == "password") || srcObject.tagName == "TEXTAREA")
    {
        var readOnly = srcObject.getAttribute("readonly"); 
        return (readOnly || srcObject.disabled == 1)?false:true;
    }
    var key = window.event?window.event.keyCode:evt.keyCode;
    if(key == 8) return false;

    return true;
}

function doClick()
{
    __doClick();
}

if(document.attachEvent)
{
    document.attachEvent("onclick", __doClick);
    document.attachEvent("onkeydown", docKeyDown);
}
else if(document.addEventListener)
{
    document.addEventListener("click", __doClick, false);
    document.addEventListener("keydown", function(evt){docKeyDown(evt)}, false);
}