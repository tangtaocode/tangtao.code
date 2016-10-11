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
    document.all['textInputAssistance'].style.display='';
    frames("textinput").clearTable();
    frames("textinput").writeTable(xmlId, obj.value);
    window.event.cancelBubble = true;
}

function setInputAssis(xmlId, obj, obj_code)
{ 
    document.all['textInputAssistance'].style.left = event.x - window.event.offsetX +window.document.body.scrollLeft;
    document.all['textInputAssistance'].style.top = event.y - window.event.offsetY + obj.clientHeight + 1 + window.document.body.scrollTop;
    document.all['textInputAssistance'].style.display='';
    frames("textinput").clearTable();
    if(obj != theInputObject)
    {
        theInputObject = obj;    
        theInputObject_code = obj_code;
        frames("textinput").clearNextXmlNode();
        frames("textinput").writeTable(xmlId);
    }
    else
    {
        theMethod = 2;
        theInputObject = obj;
        theInputObject_code = obj_code;
        frames("textinput").writeTable(xmlId,obj.value);
    }
    window.event.cancelBubble = true;
}

function setValue(strTheCode, strTheValue, tempXmlNode)
{   
    if(tempXmlNode == null)
    {
        theMethod = 1;
        if(theInputObject_code != null) theInputObject_code.value = strTheCode;
        if(theInputObject != null) theInputObject.value = strTheValue;
        theInputObject.blur();
        theInputObject      = null;
        theInputObject_code = null;
        document.all['textInputAssistance'].style.display = "none";
        frames("textinput").clearNextXmlNode();
    }
    else
    {
        if(theInputObject != null && theMethod == 1) theInputObject.value = strTheCode;
        frames("textinput").clearTable();
        frames("textinput").writeTable(tempXmlNode,strTheCode,1);
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
    	return;
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
    for(var i=0; i<xmlTopNode.childNodes.length; i++)
    {
        xmlElementNode = xmlTopNode.childNodes(i);
        strValue = xmlElementNode.text;
        if(isCodeItem)  //if the field is code item
        {
            strCode = ((strCode=xmlElementNode.getAttribute("code"))!=null)?strCode:"";
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

function initSelElementByValue(xmlID, objSelect, value, isCodeItem)
{
    var optionTemp;
    var xmlTopNode;
    var xmlElementNode;
    var strCode = "";
    var strValue ="";

    if(objSelect.getAttribute("isCodeItem") == "1") isCodeItem = true;
    if(xmlID == null || xmlID.childNodes == null || xmlID.childNodes.length == null) return;
    if(xmlID.childNodes.length <2 )
    {
    	return;
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
    for(var i=0; i<xmlTopNode.childNodes.length; i++)
    {
        xmlElementNode = xmlTopNode.childNodes(i);
        strValue = xmlElementNode.text;
        if(isCodeItem)  //if the field is code item
        {
            strCode = ((strCode=xmlElementNode.getAttribute("code"))!=null)?strCode:"";
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
    	return;
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
    for(var i=0; i<xmlTopNode.childNodes.length; i++)
    {
        xmlElementNode = xmlTopNode.childNodes(i);
        strItemType = ((strItemType=xmlElementNode.getAttribute("itemType"))!=null)?strItemType:""; 
        if(eval(filter)) continue;
        strValue = xmlElementNode.text;
        if(isCodeItem)  //if the field is code item
        {
            strCode = ((strCode=xmlElementNode.getAttribute("code"))!=null)?strCode:"";
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
				    if(document.all[objArr[i]] != null)
				    {
					    document.all[objArr[i]].value = xroot[i].childNodes[0].nodeValue;
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
    if(self == top || top.document.all["menu"] == null) return;
    if(top.document.all["menu"].style.display != "none")
    {
        top.document.all["menu"].style.display = "none";
        top.document.all["menu"].parentElement.width = "1";
        top.document.all["main"].parentElement.width = "777";
        obj.title="还原";
    }
    else
    {
        top.document.all["menu"].style.display = "";
        top.document.all["menu"].parentElement.width = "174";
        top.document.all["main"].parentElement.width = "603";
        obj.title="全屏";
    }
}

function confirmSubmit(frmObj)
{
    var msg = null;

	  try
    {
        if(!validInput()) return;
    }
    catch(e)
    {
        ;//do nothing
    }
    try
    {
        if(_sMsg != null) msg = _sMsg;
    }
    catch(e)
    {;}
	  if(msg == null) msg = "確認提交嗎？"
    if(confirm(msg))
    {
        if(frmObj == null)
        {
            document.forms(0).submit();
        }
        else
        {
            frmObj.submit()
        }
    }
}

function confirmDelete(frmObj)
{
    if(frmObj == null) frmObj = document.forms(0);
    try
    {
        if(confirm("確認刪除嗎？"))
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
    document.forms(0).submit();
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
        alert("發生錯誤\r\n" + e);
        return false;
    }
    return true;
}

function round(digit, how)  
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
    return  _digit;  
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
	  if(obj == null) frmObj = document.forms(0);
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
    
    if(frmObj == null) frmObj = document.forms(0);
    try
    {
        frmObj.action_Type.value = 2;
        frmObj.submit();
    }
    catch(e)
    {
        alert("發生錯誤\r\n" + e);
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

function doClick()
{
    if(document.all["textInputAssistance"] != null)
    {
        if(!document.all["textInputAssistance"].contains(event.srcElement))
        {
            theInputObject = null;
            document.all["textInputAssistance"].style.display = "none";
        }
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
    if(typeof(hiddenCalendar) == 'function' && WebCalendar.eventSrc != window.event.srcElement) hiddenCalendar();
}

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
 
function document::onKeyDown() 
{
    var srcObject = window.event.srcElement;
    if(srcObject.tagName == "INPUT" && srcObject.getAttribute("type") == "text" || srcObject.tagName == "TEXTAREA")
    {
        var readOnly = srcObject.getAttribute("readonly"); 
        return (readOnly || srcObject.disabled == 1)?false:true;
    }
    if(event.keyCode == 8) return false
    return true;
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
    var pattern = new RegExp("^\\d+$");
    
    return pattern.test(strNumber);
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
        alert(name + "輸入小于" + min + "個字符");
        return false;
    }
        
    if(max>0 && value.length > max)
    {
        alert(name + "輸入大于" + max + "個字符");
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
	    alert(name + "數值輸入小于" + min) ;
        return false;
	}
	if(max != null && _value > max)
	{
	    alert(name + "數值輸入大于" + max) ;
        return false;
	}
    return true;       
}   

function compareValue(name1, o1, name2, ctrlName, type)
{
    var o2 = document.all[ctrlName];
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
			if(v1<=v2){alert(name1+ "的數值必須大于" + name2);return false;}else{break;}
		case ">=":
			if(v1<v2){alert(name1+ "的數值必須大于等于" + name2); return false;}else{break;}
		case "<":
			if(v1>=v2){alert(name1+ "的數值必須小于" + name2);return false;}else{break;}
		case "<=":
			if(v1>v2){alert(name1+ "的數值必須小于等于" + name2);return false;}else{break;}
		default:
			alert("不支持的數值比較方式，請聯系系統管理員");
			return false;
	}
	return true;
}

function goPage1(frmObj)
{
    var frm = eval("document." + frmObj);
    var pageInput = eval("document.all[\"" + frmObj +"_inputNum\"]");
    var pageStr = pageInput.value;
    var maxPage = parseInt(frm.totalPage.value, 10);

    if(pageStr == "")
    {
        alert("請輸入頁碼");
        pageInput.focus();
        return;
    }
    if(!isInteger(pageStr))
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
    if(frm.currentPage != null) frm.currentPage.value = 1;
    frm.otherSortField.value = fieldAlias + ((method!= null && method==1)?" desc":"");
    frm.submit();
}