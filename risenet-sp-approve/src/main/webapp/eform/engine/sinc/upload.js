//移除列表
function removeFile(selObj, frmObj, componentName)
{
    var strFileName = null;
    var arrPara = null;
	  var pos = -1;
    var prePos = -1;

    if(selObj.selectedIndex == -1) 
    {
        alert("请选择移除文件");
        return;
    }
    strFileName = selObj.options[selObj.selectedIndex].value;
    if(strFileName.substring(0,1) == "$")
    {
        frmObj.fileName.value = strFileName.substring(1);
		    frmObj.id.value = "";
        frmObj.tableId.value = "";
		    frmObj.seqIds.value = "";
		    frmObj.submit();
    }
	  else
	  {   
        //id
        pos = strFileName.indexOf(",");
        frmObj.id.value = strFileName.substring(0, pos);
        //tableId
            prePos = pos;
        pos = strFileName.indexOf(",", pos+1);
            frmObj.tableId.value = strFileName.substring(prePos+1, pos);
        //seqId
        prePos = pos;
        pos = strFileName.indexOf(",", pos+1);
        frmObj.seqIds.value = strFileName.substring(prePos+1, pos);
        //name
            frmObj.fileName.value = strFileName.substring(pos+1);
        //alert(frmObj.id.value + "," + frmObj.tableId.value + "," + frmObj.seqIds.value + "," + frmObj.fileName.value);
        removeFromList(componentName);
    }
}

function getSaveFileName(strUrl)
{
    var strFileName = "";
    var pos = -1;

    pos = strUrl.indexOf(",");
    if(pos != -1)
    {
        strFileName = strUrl.substring(0, pos);
        pos = strFileName.lastIndexOf("/");
        if(pos != -1)strFileName = strFileName.substring(pos+1);
    }
    
    return strFileName;
}

/*
value          系统返回的数据
comName        部件名称
*/
function setListFilesValue(value, componentName)
{
    var fileList = null;
    var fileName = null;
    var attachmentList = eval("document.all['" + componentName + "_attachmentList" + "']");
    var textValue =  eval("document.all['txt_" + componentName + "']");
    var frmUpload = eval("frm_Attachment_" + componentName)
    var realFileName = null;
    var pos = -1;
     
    fileList = value.split("\r\n\r\n");
    for(var i=0; i<fileList.length; i++)
    {
        var aOption = null;
    	  pos = fileList[i].indexOf("\r\n");
    	  if(pos == -1) continue;
        fileName = fileList[i].substring(0, pos); 
        realFileName = getSaveFileName(fileList[i].substring(pos+2)); //after CRLF
        if(realFileName == "") continue;
    	  aOption = document.createElement("OPTION");
        aOption.text = fileName;
        aOption.value = "$" + realFileName;
    	  attachmentList.add(aOption);
    }
    if(textValue.value == "")
    {
        textValue.value = value;
    }
    else
    {
        textValue.value = textValue.value + "\r\n\r\n" + value;
    }
    frmUpload.reset();
}

function removeFromList(componentName)
{
    var attachmentList = eval("document.all['" + componentName + "_attachmentList" + "']");
    var textValue = eval("document.all['txt_" + componentName + "']");
    var arrFileList = null;
    var strTemp = "";
    var removeFileName = null;

    if(attachmentList.selectedIndex == -1) 
    {
        return;
    }

    removeFileName = attachmentList.options[attachmentList.selectedIndex].value; 
	  if(removeFileName.substring(0,1)== "$") removeFileName = removeFileName.substring(1);
	  //update the submit value
    arrFileList = textValue.value.split("\r\n\r\n");
    for(var i=0; i<arrFileList.length; i++)
    {
        if(arrFileList[i].indexOf(removeFileName) != -1) continue;
        if(strTemp != "") strTemp = strTemp + "\r\n\r\n";
        strTemp = strTemp + arrFileList[i];
    }
    textValue.value = strTemp;
    //remove from the fileList
    attachmentList.options.remove(attachmentList.selectedIndex);
}

function setDeleteSeqIds(existIdsName, selObjName, existIds)
{
	  var ids = "";
	  var arrTemp = null;
    var arrExistIds = null;
	  var isFind = false;
	  document.all[existIdsName].value = existIds;
    if(document.all[existIdsName].value == "") return;
    arrExistIds = document.all[existIdsName].value.split(",");

	  for(var x=0; x<arrExistIds.length; x++)
    {
        isFind = false;
        for(var i=0; i<document.all[selObjName].options.length; i++)
        {
            arrTemp = document.all[selObjName].options[i].value.split(",");
            if(arrTemp[2] == arrExistIds[x]) 
            {
                isFind = true;
                break;
            }
        }
        if(!isFind)
        {
            if(ids != "") ids = ids + ",";
            ids = ids + arrExistIds[x];
        }
	  }
    document.all[existIdsName].value = ids;
}