/*�ύ��ˢ��ҳ�棬Ҫ����Ŀ������Ϊ��
 1���ύ��ҳFrame����Ϊ��frametop
 2����ѯ�б�����Ϊ��framebottom
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

//������ʷ
var preHistosy = window.history.length;

function theHisCount()
{ 
  return preHistosy - window.history.length-1;
}