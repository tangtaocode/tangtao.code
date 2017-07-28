function showFileEditPage(Url, tWidth, tHeight)
{
    var left = (screen.width-tWidth)/2, top = (screen.height-tHeight)/2;
    if(left < 0) left = 0;
    if(top < 0) top = 0;
    var winFeatures = "left=" + left + ",top=" + top + ",toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no,"+"width="+tWidth+"px,height="+tHeight;
    window.open(Url, "", winFeatures);
}

//office编辑后自动刷新主窗体
function doRefresh()
{
    var url = new String(location);
    var pos = url.indexOf("?");
    var arr = [];
    var frm = null;

    para = url.substring(pos+1).split("&");
    for(var i=0; i<para.length; i++)
    {
        pos = para[i].indexOf("=");
        arr[para[i].substring(0, pos)] = para[i].substring(pos+1);
    }
    if(arr["temp_Id"] != null) frm = eval("document.frm_Edit_" + arr["temp_Id"]);
    //calculate the url
    if(arr["insid"] != null)
    {
        url = "gettemplate.jsp?temp_Id=" + arr["temp_Id"] + "&insid=" + arr["insid"];
    }
    else
    {
        url = "gettemplate.jsp?temp_Id=" + arr["temp_Id"] + "&edittype=1&querystring=" + _getMainAlias() + "='" + frm.guid.value + "'";
    }
    if(frm.scriptCode == null)
    {
        var el = document.createElement("INPUT");
        el.name = "scriptCode";
        el.type = "hidden";
        frm.appendChild(el);
    }
    frm.scriptCode.value = "location=\"" + url + "\"";

    frm.submit();
}
