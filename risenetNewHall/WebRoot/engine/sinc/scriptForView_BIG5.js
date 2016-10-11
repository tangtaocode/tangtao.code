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
    var pattern = new RegExp("^\\d+$");
    
    return pattern.test(strNumber);
}

function deleteLine(fieldIndex, formPrefix, values, isAlert)
{
    var frm = eval("document." + formPrefix);
    
    if(isAlert && !confirm("�_�J�h��ԓӛ䛆᣿")) return;
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
    var pageInput = eval("document.all[\"" + frmObj +"_inputNum\"]");
    var pageStr = pageInput.value;
    var maxPage = parseInt(frm.totalPage.value, 10);

    if(pageStr == "")
    {
        alert("Ոݔ��퓴a");
        pageInput.focus();
        return;
    }
    if(!isInteger(pageStr))
    {
        alert("퓴aݔ����蠑����");
        pageInput.focus();
        return;
    }
    if(parseInt(pageStr, 10)>maxPage)
    {
        alert("퓴a������[1-" + maxPage + "]֮�g");
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