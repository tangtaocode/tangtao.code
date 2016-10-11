/*提交后刷新页面，要求定义的框架名称为：
 1、提交的页Frame名称为：frametop
 2、查询列表名称为：framebottom
*/
function setRefresh()
{
    var topUrl = new String(frames["frametop"].location);

    if(topUrl.indexOf("&edittype=1") == -1 || frames["frametop"].document.forms(0) != null && frames["frametop"].document.forms(0).action_Type.value == "2")
    {
        frames("frametop").location = "../procedure/blank.htm";
    }
    frames["framebottom"].location.replace(theFrameBottom);  
    //frames["framebottom"].location.reload();
}

//返回历史
var preHistosy = window.history.length;

function theHisCount()
{ 
  return preHistosy - window.history.length-1;
}