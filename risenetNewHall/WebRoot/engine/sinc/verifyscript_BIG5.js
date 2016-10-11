function checkIdCard(strNumber) 
{
	var Ai;
    
    if(strNumber == "") return true;

	if(strNumber.length<15||strNumber.length==16||strNumber.length==17||strNumber.length>18)
	{
		alert("填寫的身份證號碼長度不正確，請重新填寫!\r\n");
		return false;
	}


	if(strNumber.length==18)
	{
		Ai = strNumber.substring(0,17);
	}
	else
	{
		Ai =strNumber.substring(0,6)+"19"+strNumber.substring(6,12);
	}

	if(!IsNumeric(Ai))
	{
		alert("身份證號碼數字字符串不正確，請重新填寫!\r\n");
		return false;
	}

	var strYear,strMonth,strDay,strBirthDay;
	strYear = Ai.substring(6,10); 
	strMonth = Ai.substring(10,12) ;
	strDay = Ai.substring(12,14); 

	if(IsValidDate(strYear,strMonth,strDay)==false)
	{
		alert("身份證號碼"+Ai+",日期格式年份"+strYear+",月份"+strMonth+",日"+strDay+"不正確，請重新填寫!\r\n");
		return false;
	}

	var arrVerifyCode = new Array("1","0","x","9","8","7","6","5","4","3","2");
	var Wi = new Array(7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2);
	var k,TotalmulAiWi=0;
	for (k=0; k<17;k++)
	{ 
		TotalmulAiWi = TotalmulAiWi + parseInt(Ai.substr(k,1)) * Wi[k]; 
	}

	var modValue =TotalmulAiWi % 11 ;
	var strVerifyCode = arrVerifyCode[modValue];
	Ai = Ai+strVerifyCode;

	if((strNumber.length==18)&&(strNumber!=Ai))
	{
		alert("身份證號碼"+strNumber+"與正確的號碼"+Ai+"不壹致，請重新填寫!\r\n");
		return false;
	}
		return true;

	}

	function IsNumeric(oNum) 
	{ 
	if(!oNum) return false; 
	var strP=/^\d+(\.\d+)?$/; 

	if(!strP.test(oNum)) return false; 
	try{ 
	if(parseFloat(oNum)!=oNum) return false; 
	} 
	catch(ex) 
	{ 
	 return false; 
	} 
	return true; 
	}

	function IsValidYear(psYear)
	{
	var sYear = new String(psYear);

	if(psYear==null)
	{
		alert("身份證號碼出生日期中年份爲Null，請重新填寫!\r\n");
		return false;
	}


	if(isNaN(psYear)==true)
	{
		alert("身份證號碼出生日期中年份必須爲數字，請重新填寫!\r\n");
		return false;
	}

	if(sYear == "")
	{
		alert("身份證號碼出生日期中年份爲空，請重新填寫!\r\n");
		return true;
	}

	if(sYear.match(/[^0-9]/g)!=null)
	{
		alert("身份證號碼出生日期中年份必須爲0-9之間的數字組成，請重新填寫!\r\n");
		return false;
	}

	var nYear = parseInt(sYear,10);

	if((nYear < 0) || (9999 < nYear))
	{
		alert(nYear +"身份證號碼出生日期中年份必須爲正常的正整數，請重新填寫!\r\n");
		return false;
	}

	return true;
	}


	function IsValidMonth(psMonth)
	{
	var sMonth = new String(psMonth);

	if(psMonth==null)
	{
		return false;
	}

	if(isNaN(psMonth)==true)
	{
		return false;
	}

	if(sMonth == "")
	{
		return true;
	}

	if(sMonth.match(/[^0-9]/g)!=null)
	{
		return false;
	}

	var nMonth = parseInt(sMonth,10);

	if((nMonth < 0) || (12 < nMonth))
	{
		return false;
	}

	return true;
}


function IsValidDay(psDay)
{
    var sDay  = new String(psDay);

    if(psDay==null)
    {
        return false;
    }

    if(isNaN(psDay)==true)
    {
        return false;
    }

    if(sDay == "")
    {
        return true;
    }

    if(sDay.match(/[^0-9]/g)!=null)
    {
        return false;
    }

    var nDay = parseInt(psDay, 10);

    if((nDay < 0) || (31 < nDay))
    {
    return false;
    }

    return true;
}


function IsValidDate(psYear, psMonth, psDay)
{
    if(psYear==null || psMonth==null || psDay==null)
    {
        return false;
    }

    var sYear  = new String(psYear);
    var sMonth = new String(psMonth);
    var sDay   = new String(psDay);

    if(IsValidYear(sYear)==false)
    {
        return false;
    }

    if(IsValidMonth(sMonth)==false)
    {
        return false;
    }

    if(IsValidDay(sDay)==false)
    {
        return false;
    }

    var nYear  = parseInt(sYear,  10);
    var nMonth = parseInt(sMonth, 10);
    var nDay   = parseInt(sDay,   10);

    if(sYear=="" &&  sMonth=="" && sDay=="")
    {
        return true;
    }

    if(sYear=="" || sMonth=="" || sDay=="")
    {
        return false;
    }
   
    if(nMonth < 1 || 12 < nMonth)
    {
        return false;
    }
    if(nDay < 1 || 31 < nDay)
    {
        return false;
    }

    if(nMonth == 2)
    {
        if((nYear % 400 == 0) || (nYear % 4 == 0) && (nYear % 100 != 0))
        {
            if((nDay < 1) || (nDay > 29))
            {
                return false;
            }
        }
        else
        {
            if((nDay < 1) || (nDay > 28))


    {
                return false;
            }
        }
    }
    else if((nMonth == 1)  ||
            (nMonth == 3)  ||
            (nMonth == 5)  ||
            (nMonth == 7)  ||
            (nMonth == 8)  ||
            (nMonth == 10) ||
            (nMonth == 12))
    {
        if((nDay < 1) || (31 < nDay))
        {
            return false;
        }
    }
    else
    {
        if((nDay < 1) || (30 < nDay))
        {
            return false;
        }
    }

    return true;
} 