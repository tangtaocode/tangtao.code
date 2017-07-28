function getTheLength(strValue)
{
    if(strValue == null) return 0;
    return strValue.replace(/[^\x00-\xff]/g,"aa").length;
}

//for(checkbox oe radio
function isChecked(theName, theObject)
{
    var blState;
    var i;
    
    if(theObject == null)
	{
        alert("[" + theName + "]對應的表單元素不存在");
	    return false;
    }

    blState = false;
    if(theObject.length == null)
	{
        if(theObject.checked) blState = true;  
    }
	else
	{
        for(i=0; i<theObject.length; i++)
	    {
            if(theObject[i].checked) blState = true;
        }
    }
    if(!blState)
	{ 
		alert("請選擇輸入" + theName);
		if(theObject.length == null)
        {
			theObject.focus();
		}
		else
	    {
	        theObject[0].focus();
		}
    }

    return blState;
}    

function limitStringLenth(theName, theRealObject, theObject, theLength)
{
    var strValue;
    var strCode;
  
    if(theObject == null)
    {
        alert("[" + theName + "]對應的表單元素不存在");
        return false;
    }

    if(theObject.tagName == "SELECT")
    {
        if(theObject.selectedIndex != -1)
        {
           strValue = theObject.options[theObject.selectedIndex].value;
        }
        else
        {
              strValue = "";
        }
        strCode = strValue;
     }
     else
     {
          strCode = theRealObject.value;
          strValue = theObject.value;
     }
  
     if(theObject.name != theRealObject.name)
	   {
         if(strCode == "" && strValue != "")
         {
             alert(theName + "爲代碼項，請選擇輸入！");
             try{theObject.focus();}catch(e){}
             return false;
         }
	       strValue = strCode;
    }

    if(getTheLength(strValue) > theLength)
	{
        alert(theName + "輸入長度大于" + theLength);
		try{theObject.focus();}catch(e){}
        return false;
    }

    return true;
} 

function isNumber(theName, theRealObject, theObject, theType, theLength)
{
    var strValue;
  
    if(theObject == null)
	{
        alert("[" + theName + "]對應的表單元素不存在");
	      return false;
    }

    if(theObject.tagName == "SELECT")
    {
        if(theObject.selectedIndex != -1)
        {
            strValue = theObject.options[theObject.selectedIndex].value;
        }
        else
        {
            strValue = "";
        }
    }
    else
    {
        strValue = theRealObject.value;
    }

    if(strValue.replace(/(^\s*)|(\s*$)/g, "") == "")
    {
        return true;
    }
	  strValue = strValue.replace(/,/g, "");
    //for(float type
    if(theType == 0 && !isInteger(strValue))
    {
        alert(theName + "輸入必須爲整數！");
        return false;
    }

    //for(integer type
    if(!isNumeric(strValue))
    {
        alert(theName + "輸入必須爲數值！");
        try{theObject.focus();}catch(e){}
        return false;
    }

    if(parseFloat(strValue) >= Math.pow(10, theLength))
	  {
        alert(theName + "數值輸入大于" + multiChar("9", theLength) + "！");
        try{theObject.focus();}catch(e){}
        return false;
    }

    return true;
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

function isInteger(strNumber)
{
    var pattern = new RegExp("^-?\\d+$");
    
    return pattern.test(strNumber);
}

function isNumeric(strNumber)
{   
    if(strNumber.replace(/(^\s*)|(\s*$)/g, "") != "")
    {
        return !isNaN(strNumber);
    }
    else
    {
        return true;
    }
}

function isDateFormat(theName, theRealObject, theObject)
{
    var strValue;
  
    if(theObject == null)
	{
        alert("[" + theName + "]對應的表單元素不存在");
	    return false;
    }
  
    strValue = theObject.value;
    if(strValue == "")
    {
          return true;
    }

    if(!isDate(strValue))
    {
          alert(theName + "輸入必須爲日期");
          try{theObject.focus();}catch(e){}
          return false;
    }

    return true;
}

function validDateFormat(sDate)
{
    var pattern1 = new RegExp("^\\d{4}-\\d{1,2}-\\d{1,2}(\\s\\d{1,2}(:\\d{2})?(:\\d{2})?)?$");
    var pattern2 = new RegExp("^\\d{4}/\\d{1,2}/\\d{1,2}(\\s\\d{1,2}(:\\d{2})?(:\\d{2})?)?$");
    
    return pattern1.test(sDate) | pattern2.test(sDate);
}

function isDate(sDate1)
{
    var _sDate = sDate1.replace(/(^\s*)|(\s*$)/g, "");
    if(!validDateFormat(_sDate)) return false;

    var sDate = _sDate.split(" ")[0];
    if(sDate.indexOf("-")>0)
    {
        aDate = sDate.split("-");
    }
    if(sDate.indexOf("/")>0)
    {
        aDate = sDate.split("/");
    }
    sYear = aDate[0];
    sMonth = aDate[1];
    sDay = aDate[2];
    if(_sDate.indexOf(" ") != -1)
    {
        var sTime = _sDate.substring(_sDate.indexOf(" ")+1).split(":");
        if(parseInt(sTime[0]) > 23) return false;
        if(sTime.length > 1 && parseInt(sTime[1]) > 59) return false;
        if(sTime.length > 2 && parseInt(sTime[2]) > 59) return false;
    }
    switch(parseFloat(sMonth))
    {
        case 1:
        case 3:
        case 5:
        case 7:
        case 8:
        case 10:
        case 12:
    	    if(eval(sDay)<=31)
    	    {
    	        return true;
    	    }
    	    break;
    	case 4:
    	case 6:
    	case 9:
    	case 11:
    	    if(eval(sDay)<=30)
    	    {
    	    	return true;
    	    }
    	    break;
      	case 2:
            lastDay=(((sYear%4)==0) &&( ( (sYear%100)!=0 )|| ((sYear%400)==0 )))? 29:28  //判断润年的二月份天数
            if(eval(sDay)<=lastDay)
            {
                return true;
            }
            break;	
    }
    return false;	
}

function isNotBlank(theName, theRealObject, theObject)
{
    var strValue = "";
    var strCode = null;
    var errMsg = null;
  
    if(theObject == null)
    {
        alert("[" + theName + "]對應的表單元素不存在");
	      return false;
	  } 
  
    
    if(theObject.tagName == "SELECT")
    {
        if(theObject.selectedIndex != -1)
        {
            strValue = theObject.options[theObject.selectedIndex].value;
            strCode = theObject.options[theObject.selectedIndex].value;
        }
        else
        {
            strCode = "";
        }
    }
    else
    {
        strValue = theObject.value
        strCode = theRealObject.value;
    }

    if(theObject.name != theRealObject.name)
    {
        if(strCode == "")
        {
            if(strValue == "")
            { 
                errMsg = "輸入不能爲空！";
            }
            else
            {
                errMsg =  "爲代碼項，請選擇輸入！";
            }
        }
        else
        {
            if(strValue == "") errMsg = "輸入不能爲空！";
        }
    }
    else
    {
         if(strValue == "") errMsg = "輸入不能爲空！";
    }
  
    if(errMsg != null)
    {
        alert(theName + errMsg);
        try{theObject.focus();}catch(e){}
        return false;
    }

    return true;  
}

//convert String to float
function toFloat(strValue)
{
    if(strValue != "" && !isNaN(strValue))
    {
        return parseFloat(strValue)
	}
	else
    {
        return 0;
	}
} 