var isSubmit = false;
var selectedRow = null;
var originalBgColor = null;
var originalClass = null;
var pForm = null;
var topWin = self;

String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

function isInteger(strNumber)
{
    var pattern = new RegExp("^\\d+$");
    
    return pattern.test(strNumber);
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
    var pageInput = eval("document.all[\"" + frmObj +"_inputNum\"]");
    var pageStr = pageInput.value;
    var maxPage = parseInt(frm.totalPage.value, 10);

    if(pageStr == "")
    {
        alert("请输入页码");
        pageInput.focus();
        return;
    }
    if(!isInteger(pageStr))
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

function calculateGoPage(page)
{
    document.frmQuery.currentPage.value = page;
    document.frmQuery.submit();
}

function calculatePrevPage(page)
{
    page = page-1
    document.frmQuery.currentPage.value = page;
    document.frmQuery.submit();
}	

function reSort(fieldAlias, method)
{
    if(isSubmit) return;
    isSubmit = true;
    if(document.frmQuery.currentPage != null)document.frmQuery.currentPage.value = 1;
    document.frmQuery.otherSortField.value = fieldAlias + ((method!= null && method==1)?" desc":"");
    document.frmQuery.submit();
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

function calculateNextPage(page)
{
    if(isSubmit) return;
    isSubmit = true;
    if(page>=parseInt(document.frmQuery.totalPage.value,10))
    {
        return false;
    }
    else
    {
        page = page+1
        document.frmQuery.currentPage.value = page;
    }
    document.frmQuery.submit();
}

function setSelectedRow(obj)
{
    if(selectedRow != null)
    {
        if(originalClass != null && originalClass != "")
        {
            selectedRow.className = originalClass;
        }
    }
    selectedRow = obj;
    if(selectedRow.className != null && selectedRow.className != "")
    {
        originalClass = selectedRow.className;
    }
    selectedRow.className = "selectedRow";
    try
    {
        openwin();
    }
    catch(e)
    {;}
}

function getCellInnerText(fieldName, rowIndex)
{
    var columnIndex = -1;
    var value;

    try
    {
        columnIndex = fieldPos[fieldName];
        if(columnIndex==null) return "";
        if(isInteger(columnIndex))
        {
            value = document.all["queryTab"].rows[rowIndex].cells[columnIndex].innerText.trim();
        }
        else
        {
            if(document.all[columnIndex] != null)
            {
                if(document.all[columnIndex].length == null)
                {//single element
                    value = document.all[columnIndex].value;
                }
                else
                {//multiple element
                    value = document.all[columnIndex][rowIndex-1].value;
                }
            }
            else
            {
                value = "";
            }
        }
	  }
    catch(e){;}
    return value;
}

function setEachFormElementValue(elName, fieldName, rowIndex)
{
    var value;
    var tagName;
    var _element = null;

    if(pForm == null) setParentForm();
    _element = eval("pForm." + elName); 
    if(pForm != null && _element != null)
    {
        tagName = _element.tagName;
		value = getCellInnerText(fieldName, rowIndex);
		if(tagName == "INPUT" || tagName == "TEXTAREA")
		{
		    _element.value = value;
		}
    }
}

function setParentFormValue(radboxObj, array)
{
    var rowObj = getSelectRowByRadio(document.all[radboxObj]);
    var rowIndex = null;

    if(rowObj != null) 
    {
        rowIndex = rowObj.rowIndex;
        for(var x=0; x<array.length; x++)
        {
	    try
	    {
                if(array[x][0] != "" && array[x][1] != "")setEachFormElementValue(array[x][0], array[x][1], rowIndex);
	    }
	    catch(e){;}
        }
	if(topWin != null)topWin.close();
    }
    else
    {
        alert("未能选取数据行!");
    }
    
}

function getSelectRowByRadio(checkbox)
{
    var obj = null;
    if(checkbox==null) return;

    var checkCount=checkbox.length;
    if(checkbox!=null && checkCount==null)
    {
        if(checkbox.checked) obj = checkbox;
    }
    else
    {
        for(var i=0; i<checkCount; i++) 
		{
        if(checkbox[i].checked) 
        {
            obj = checkbox[i];
            break;
        }
		}
    }
  
   while(obj != null && obj.tagName != "TD")
   {
       obj = obj.parentElement;
   }

   return (obj != null)?obj.parentElement:null;
}

function setParentForm()
{
    while(topWin != null && topWin.parent != topWin)
    {
        topWin = topWin.parent;
    }

    if(topWin != null)
    {
        pForm = topWin.dialogArguments;
        if(pForm == null) pForm = topWin.opener.document.forms(0);
    }
}