function insertNewRow(_obj)
{
    var aRow;
    var aCell;
    var curRowIndex;
    var eventObj = null
    var rowObj = getTableRow(_obj);
    var cellNum = null;
    var newName = name;
    var preIndex = null;
    var preRow = null;
    var tempStr = null;
    var tempStr1 = null;
    var tempHtml = null;
    var obj = null;
    var id = null;

    if(rowObj == null || rowObj.getAttribute("editRow") == null || rowObj.getAttribute("editRow") == "")
    {
        alert("增行发生错误");
        return;
    }
    obj = getParentNode(rowObj);
    cellNum = rowObj.cells.length;
    aRow = obj.insertRow(rowObj.rowIndex+1);
    aRow.setAttribute("editRow", rowObj.getAttribute("editRow"));
    cloneAttribute(rowObj, aRow);
    for(var i=0; i<cellNum; i++)
    {
        tempHtml = new String(rowObj.cells[i].innerHTML);
        aCell = aRow.insertCell(-1);
        aCell.innerHTML = tempHtml;
        cloneAttribute(rowObj.cells[i], aCell);
        clearValue(aCell);
    }
    
    var comId = rowObj.getAttribute("editRow");
    try
    {
        var _status = aRow.all.tags("SPAN"); 
        for(var i=_status.length-1; i>=0; i--)
        {
            if(_status[i].id == comId + "_status")
            {
                getParentNode(_status[i]).removeChild(_status[i]);
            }
        }
    }
    catch(e4){}
    var _sq = findChildNode(rowObj, comId + "_Sequence");
    if(_sq != null)
    {
        id = parseInt(_sq.innerHTML);
        resetSequence(obj, aRow.rowIndex, ++id);
    }

    var f0 = getComFunction(comId + "_OnInsertRow");
	var f1 = getComFunction(comId + "_OnInsertRow_1");
	try{
        if(typeof(f0) == 'function') eval(comId + "_OnInsertRow()");
		else if(typeof(f1) == 'function') eval(comId + "_OnInsertRow_1()");
    }catch(e){;};
    
    return aRow;
}

function getComFunction(comStr)
{
    var f = null;
	
	try
	{
        f = eval(comStr);
	}catch(e){}
	
	return f;
}

function getParentNode(obj)
{
    return (obj.parentElement)?obj.parentElement:obj.parentNode;
}

function cloneAttribute(s, t)
{
    for(var i=0; i<s.attributes.length; i++)
    {
        if(s.attributes[i].name == "class") 
        {
            t.className = s.className;
            continue;
        }
        tempHtml = s.getAttribute(s.attributes[i].name);
        if(tempHtml != null && tempHtml != "") t.setAttribute(s.attributes[i].name, tempHtml);
    }
}

function resetSequence(tableObj, rowIndex, id)
{
    var length = tableObj.rows.length;
    if(rowIndex >= length) return;
    var rowObj = tableObj.rows[rowIndex];
    var editRowId = rowObj.getAttribute("editRow");
    var _obj = null;

    for(var i=rowIndex ;i<length; i++)
    {
        _obj = tableObj.rows[i]
        if(_obj.getAttribute("editRow") != null && _obj.getAttribute("editRow") != "")
        {
             var _sq = findChildNode(_obj, editRowId + "_Sequence");
             if(_sq != null) _sq.innerHTML = id++;
        }
        else
        {
            break;
        }
    }
    
}

function clearValue(nodeObj)
{//clear value and focus
    var len = nodeObj.childNodes.length;
    var childObj = null;
    var tagName = null;
    var type = null;
         
    if(len == 0) return;
    for(var i=0; i<len; i++)
    {
        childObj = nodeObj.childNodes[i]
        tagName = childObj.tagName;
        switch(tagName)
        {
            case "INPUT":
                type=childObj.getAttribute("type");
                if(type != "button") 
                {
                    if(type == "radio" || type == "checkbox")
                    {
                        childObj.checked = false;
                    }
                    else
                    {
                        childObj.value = "";
                    }
                }
                break;
            case "TEXTAREA":
                 childObj.value = "";
                break;
            case "SELECT":
                 childObj.selectedIndex = -1;
                break;
            default:
                clearValue(childObj);
        }
    }
}

function getTableObject(obj)
{
    obj = getParentElement(obj, 9);
    return obj;
}
 
function getTableRow(_obj)
{
    var obj = _obj;
   
    while(obj != null)
    {
        if(obj.tagName == "TR" && obj.getAttribute("editRow") != null && obj.getAttribute("editRow") != "")
        {
            break;
        }
        obj = getParentNode(obj);
        if(obj == null) break;
    }

    return obj;
}

function findChildNode(obj, id)
{
    for(var i=0; i<obj.childNodes.length; i++)
    {
        if(obj.childNodes[i].id == id || obj.childNodes[i].name == id) 
        {
            return obj.childNodes[i];
        }
        else
        {
            var o = findChildNode(obj.childNodes[i], id);
            if(o != null) return o;
        }
    }

    return null;
}

function deleteRow(_obj)
{
    var rowObj = getTableRow(_obj)
    var obj = null;
    var index = null;
    var id = null;

    if(rowObj == null)
    {
        alert("删行发生错误");
        return
    }
    index = rowObj.rowIndex;
    var _sq = findChildNode(rowObj, rowObj.getAttribute("editRow") + "_Sequence");

    if(_sq != null)
    {
        id = parseInt(_sq.innerHTML);
    }
    obj = getParentNode(rowObj);

    if(rowObj != null && obj != null)
    {
        if(getEditRowNum(obj) <= 1)
        {
            if(!confirm("不进行任何数据录入吗")) return;
        }
        //record sequence Field for delete
        var comId = rowObj.getAttribute("editRow");
        var _SeqField = findChildNode(rowObj, comId + "_SeqField");
        var seqId = _SeqField.value;

        if(seqId != "")
        {
            var _DeleteRow = document.getElementsByName(comId + "_DeleteRow")[0];
            if(_DeleteRow.value != "")
            {
                _DeleteRow.value += "\r\n"
            }
            _DeleteRow.value += seqId;
        }
        obj.deleteRow(rowObj.rowIndex);

		var f0 = getComFunction(comId + "_OnInsertRow");
	    var f1 = getComFunction(comId + "_OnInsertRow_1");
        try{
            if(typeof(f0) == 'function') eval(comId + "_OnDeleteRow()")
			else if(typeof(f1) == 'function') eval(comId + "_OnDeleteRow_1()")
        }catch(e){;};
    }
    if(id != null) resetSequence(obj, index, id);
}
   
function getEditRowNum(tab)
{
     var num = 0;
     var temp = null;

     for(var i=0; i<tab.rows.length; i++)
     {
         if((temp=tab.rows[i].getAttribute("editRow"))!=null && temp != "")
         {
             num++;
             if(num>1) break;
         }
     }

     return num;
 }

function getParentElement(_obj, type)
{
    var obj = _obj;
   
    while(obj != null)
    {
       if(type == 0 && obj.tagName == "TD")
       {
           if(getParentNode(obj).getAttribute("editRow") != null && getParentNode(obj).getAttribute("editRow") != "")
           {
               break;
           }
       }
       else if(obj.tagName == "TR")
       {
           if(obj.getAttribute("editRow") != null && obj.getAttribute("editRow") != "")
           {
               if(type == 1)
               {
                   break;
               }
               else if(type == 9)
               {
                   obj = getParentNode(obj);
                   break;
               }
           }
       }
       obj = getParentNode(obj);
       if(obj == null) break;
    }

    return obj;
}

/*
do with CR LF
*/
function doWithReturn()
{
    var currentRow = null;
    var currentCell = null;
    var curRowIndex;
    var curCellIndex;
    var cellNum = null;
    var tempName = null;
    var sElelemt = event.srcElement;
    var tabObj = null;
    var rowObj = null;
    var keyCode = null;
    
    if(sElelemt.tagName != "INPUT" || sElelemt.type != "text") return;     
    keyCode = event.keyCode;
    
    switch(keyCode)
    {
        case 39:
            if(sElelemt.value.length >0) break;
        case 13:  //CR LF
            var souceIndex = sElelemt.sourceIndex; 
            var _obj = null;
            for(var i=souceIndex+1; ; i++)
            {
               _obj = document.all.item(i);
               if(_obj == null) break;
               if(_obj.tagName == "INPUT" && _obj.type != "hidden" || _obj.tagName == "SELECT" || _obj.tagName == "TEXTAREA")
               {
                   try{_obj.focus()}catch(e){;}
                   break;
               } 
            }
            break;
        case 37:  //LEFT
            if(sElelemt.value.length >0) break;
            var souceIndex = sElelemt.sourceIndex; 
            var _obj = null;
            for(var i=souceIndex-1; i>0 ; i--)
            {
               _obj = document.all.item(i);
               if(_obj == null) break;
               if(_obj.tagName == "INPUT" && _obj.type != "hidden" || _obj.tagName == "SELECT" || _obj.tagName == "TEXTAREA")
               {
                   try{_obj.focus()}catch(e){;}
                   break;
               } 
            }
            break;
        case 38:  //TOP
            rowObj = getTableRow(sElelemt, 1);
            tabObj = rowObj.parentElement;
            var rowIndex = rowObj.rowIndex;
            if(rowIndex - 1>=0)
            {
                rowObj = tabObj.rows(rowIndex-1); 
                if(rowObj.editRow == null || rowObj.editRow == "")break;
                rowObj.all[sElelemt.name].focus();
            }
            break;
        case 40:  //DOWN
            rowObj = getTableRow(sElelemt, 1);
            tabObj = rowObj.parentElement;
            var rowIndex = rowObj.rowIndex;
            if(rowIndex+1<tabObj.rows.length)
            {
                rowObj = tabObj.rows(rowIndex+1); 
                if(rowObj.editRow == null || rowObj.editRow == "")break;
                rowObj.all[sElelemt.name].focus();
            }
            break;    
    }
}

function initUnfixTable()
{
    var tables = document.getElementsByTagName("TABLE");
    var temp = null;
    
    for(var i=0; i<tables.length; i++)
    {
        for(var j=0; j<tables[i].rows.length; j++)
        {
            temp = tables[i].rows[j].getAttribute("editRow");
            if(temp != null && temp != "")
            {
                tables[i].onkeyup = doWithReturn;
                break;
            }
        }
    }
   
}

function getUnfixAssis(xmlId, obj)
{     
    var codeId = obj.name + "_code";
    var xmlObj = document.getElementById("Assias_" + xmlId.replace('.', '_'));
    
    getInputAssis(xmlObj, obj, obj.parentElement.all[codeId]);
}

function setUnfixAssis(xmlId, obj)
{
    var codeId = obj.name + "_code";
    var xmlObj =  document.getElementById("Assias_" + xmlId.replace('.', '_'));

    setInputAssis(xmlObj, obj, obj.parentElement.all[codeId]);
}
initUnfixTable();