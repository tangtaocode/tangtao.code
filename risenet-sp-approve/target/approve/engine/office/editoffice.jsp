<%@ page contentType="text/html;charset=utf-8" %><%@ page language="java" 
import="java.io.*,java.sql.*"%><%@ page import="net.sysmain.common.Operator"%><%@ page 
import="net.sysmain.common.I_UserConstant"%><%
Operator op = (Operator)request.getSession().getAttribute(I_UserConstant.USER_INFO);
if(op == null)
{
    request.setAttribute(I_UserConstant.MESSAGE_INFO, "系统可能超时，请重新登录访问");
    try
    {
        request.getRequestDispatcher("/engine/message.jsp").forward(request,response);
    }
    catch(Exception ex){}
    return;
}

String guid = request.getParameter("guid")==null?"":request.getParameter("guid").toString().trim();
String pguid = request.getParameter("pguid")==null?"":request.getParameter("pguid").toString().trim();
String currentUserName = "";
String tid = request.getParameter("tid");

if(op != null) currentUserName = op.getUserName();
String fileUrl = "showfile.jsp";
boolean isNewFile = true;
if(tid != null)
{
    tid = "&tid=" + tid;
}
else
{
    tid = "";
}

if(guid.equals(""))
{
    isNewFile = true;
}
else
{
    isNewFile = false;
}

if(isNewFile)
{
    fileUrl = "showfile.jsp?fileType="+request.getParameter("fileType");
}
else
{
    fileUrl = "showfile.jsp?guid="+guid;
}
//System.out.println(fileUrl);

boolean isPermitSign = net.jbpm.external.TaskNodeUtil.isPermitSign(request.getParameter("guid"), request);
boolean isShowLoadTemplate = !"".equals(tid) && !isPermitSign;

/**
  获取当前用户的电子印章信息
**/
java.util.ArrayList list = null;
list = new net.business.db.OfficeManager().getElecStampByOwner(op.getUserId());
String signDisabled = isPermitSign?"":" style=\"display:none\"";
%>
<html>
<head>
   <title>ms office文档编辑</title>
   <script language="Javascript">var currentUserName = "<%=currentUserName%>"</script>
   <meta content="IE=7" http-equiv="X-UA-Compatible" /> 
	  <!--设置缓存-->
	 <meta http-equiv="cache-control" content="no-cache,must-revalidate">
	 <meta http-equiv="pragram" content="no-cache">
	 <meta http-equiv="expires" content="0">
   <link href="<%=request.getContextPath()%>/innet/images/chysoft.css" rel="stylesheet" type="text/css" />
   <link href="<%=request.getContextPath()%>/innet/images/form.css" rel="stylesheet" type="text/css" />
   <script type="text/javascript" src="OfficeContorlFunctions.js"></script>
</head>
<body onload='intializePage("<%=fileUrl%>")' onbeforeunload ="onPageClose()">
   <div id="menuSetting" style="position:absolute;display:none;"><iframe name="frmSetting" src="setting.htm" frameborder="0" style="width:130px;height:150px;" scrolling="no"></iframe></div>
   <form id="form1" action="upLoadOfficeFile.jsp?<%=guid.equals("")?"":"guid="+guid%><%=guid.equals("")?"":"&"%>pguid=<%=pguid%><%=tid%>" enctype="multipart/form-data" style="padding:0px;margin:0px;"><br>
    <div id="editmain" class="editmain">
        <div id="editmain_top" class="editmain_top">
           <div id="edit_button_div" class="edit_button_div">
           &nbsp;&nbsp;&nbsp;&nbsp;<span style="font-size:13px;">文档名称：<input class="style_box" style="position:relative; top:-3px" name="fileName" size="35">&nbsp;&nbsp;<input type="button" id="saveId" class="style_but_t4" value="保存文档" onclick="saveFileToUrl();"> <input type="button" id="handWriter" value="手写批注" onclick="addHandSecSign(currentUserName, printMode);" class="style_but_t4"> <span id="spLock"><input type="button" id="setting" name="setting" onclick="showSetting(this)" class="style_but_t2" value="设 置"></span>
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select id="SignFileUrl"<%=signDisabled%>>
             <option value="" selected>---请选择---</option><%for(int i=0; i<list.size(); i++){String[] temp = (String[])list.get(i);%>
             <option value="<%=request.getContextPath()%>/innet/admin/ntko/showfile.jsp?guid=<%=temp[0]%>"><%=temp[1]%></option> 
            <%}%></select>&nbsp;<input class="style_but_t2" type="button" value="签 章" onclick="var signUrl=document.all('SignFileUrl').options[document.all('SignFileUrl').selectedIndex].value;if(signUrl==''){alert('请选择印章')}else{addServerSign(signUrl, printMode);}"<%=signDisabled%>>&nbsp;<input type="button" onclick="showMenu(this)" class="style_but_t4" value="模板文件"><div id="menuTemp" style="position:absolute;display:none;"><iframe name="frm1" src="listtempfiles.jsp" frameborder="0" style="width:430px;height:350px;" scrolling="yes"></iframe></div>
           </div>
        </div>
        <div id="editmain_middle" class="editmain_middle">
            <div id="editmain_right" class="editmain_right">
                <div id="officecontrol">
                <script type="text/javascript" src="officecontrol/ntkoofficecontrol.js"></script>
                <div id=statusBar style="height:20px;width:100%;background-color:#c0c0c0;font-size:12px;"></div>
								<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
									setFileOpenedOrClosed(false);
								</script>
								<script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
									
									OFFICE_CONTROL_OBJ.activeDocument.saved=true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
									//获取文档控件中打开的文档的文档类型
									switch (OFFICE_CONTROL_OBJ.doctype)
									{
										case 1:
											fileType = "Word.Document";
											fileTypeSimple = "wrod";
											break;
										case 2:
											fileType = "Excel.Sheet";
											fileTypeSimple="excel";
											break;
										case 3:
											fileType = "PowerPoint.Show";
											fileTypeSimple = "ppt";
											break;
										case 4:
											fileType = "Visio.Drawing";
											break;
										case 5:
											fileType = "MSProject.Project";
											break;
										case 6:
											fileType = "WPS Doc";
											fileTypeSimple="wps";
											break;
										case 7:
											fileType = "Kingsoft Sheet";
											fileTypeSimple="et";
											break;
										default :
											fileType = "unkownfiletype";
											fileTypeSimple="unkownfiletype";
									}
									setFileOpenedOrClosed(true);
								</script>
									<script language="JScript" for=TANGER_OCX event="BeforeOriginalMenuCommand(TANGER_OCX_str,TANGER_OCX_obj)">
									alert("BeforeOriginalMenuCommand事件被触发");
								</script>
								<script language="JScript" for=TANGER_OCX event="OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj)">
									if(TANGER_OCX_str == 3) 
									{
										alert("不能保存！");
										CancelLastCommand = true;
									}
								</script>
								<script language="JScript" for=TANGER_OCX event="AfterPublishAsPDFToURL(result,code)">
									result=trim(result);
									alert(result);
									document.all("statusBar").innerHTML="服务器返回信息:"+result;
									if(result=="文档保存成功。") {window.close();}
								</script>
								<script language="JScript" for=TANGER_OCX event="OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID)">
								alert("第" + menuPos +","+ submenuPos +","+ subsubmenuPos +"个菜单项,menuID="+menuID+",菜单标题为\""+menuCaption+"\"的命令被执行.");
								</script>
                </div>
            </div>
        </div>
       <div id="edit_bottom" class="edit_bottom">
           <div id="conmpanyinfo" class="conmpanyinfo">         
            </div>
        </div>
    </div>
    </form>
</body><%if(isShowLoadTemplate){%>
<script language="JScript" for="TANGER_OCX" event="AfterOpenFromURL(doc)">
if(isLoadFile)
{
    try
    {
        if(opener != null) opener.doOfficeAction(document);
        isLoadFile = false;
    }
    catch(e){}
}
</script>
<%}%>
<script language="Javascript">
<!--
var signUser = "<%=op.getUserName()%>";
var printMode = 2;
var isLoadFile = false;

function loadFile(fileName)
{
    document.getElementById("menuTemp").style.display = "none";
    <%if(isShowLoadTemplate){%>intializePage("loadfile.jsp?f=" + fileName);<%}else{%>insertRedHeadFromUrl("loadfile.jsp?f=" + fileName);<%}%>
    isLoadFile = true;
}

function closeMenu()
{
    document.getElementById("menuTemp").style.display = "none";
}

function showMenu(e)
{
    var o = document.getElementById("menuTemp").style;if(o.display!="none") return;
    document.getElementById("menuTemp").style.display = "";
    var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
    while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
    var cw = 430, ch = 350;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
    if (document.body.clientHeight + dt - t - h >= ch) o.top = ((p=="textarea")? t + h : t + h + 6) + "px";
    else o.top  = ((t - dt < ch) ? ((p=="textarea")? t + h : t + h + 6) : t - ch) + "px";
    if (dw + dl - l >= cw) o.left = (l-cw) + "px"; else o.left = (((dw >= cw) ? dw - cw + dl : dl)-cw) + "px";
}

function showSetting(e)
{
    var o = document.getElementById("menuSetting").style;if(o.display!="none") return;
    document.getElementById("menuSetting").style.display = "";
    var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
    while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
    var cw = 130, ch = 150;
    var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
    if (document.body.clientHeight + dt - t - h >= ch) o.top = ((p=="textarea")? t + h : t + h + 6) + "px";
    else o.top  = ((t - dt < ch) ? ((p=="textarea")? t + h : t + h + 6) : t - ch) + "px";
    if (dw + dl - l >= cw) o.left = l + "px"; else o.left = (((dw >= cw) ? dw - cw + dl : dl)) + "px";
}

function doProtected(obj)
{
     if(obj.checked) 
     {
         document.getElementById("TANGER_OCX").SetReadOnly(true);
     }
     else
     {
         OFFICE_CONTROL_OBJ.SetReadOnly(false);
     }
}

function doTrace(obj)
{
    OFFICE_CONTROL_OBJ.ActiveDocument.TrackRevisions = true;
}

function doEnablePrint(obj)
{
    printMode = (obj.checked)?2:0;
}

if(opener == null) 
{
    document.getElementById("saveId").disabled = 1;
    document.getElementById("saveId").style.display = "none";
    document.forms[0].fileName.disabled = 1;
    self.close();
}
var right = (opener!= null)?opener._getOfficePermit():0;
var _print = false;

try
{
    _print = opener._getPrintRight();
}
catch(ex){}

if(right != 2)
{
    document.getElementById("saveId").disabled = 1;
    document.getElementById("saveId").style.display = "none";
    document.getElementById("handWriter").style.display = "none";
    document.getElementById("spLock").style.display = "none"; 
    document.forms[0].fileName.disabled = 1;
}

function attachResizeEvent()
{
    try
    {
        var els = document.getElementsByTagName("INPUT");
        for(var i=0; i<els.length; i++)
        {
            if(els[i].type == "button" || els[i].type == "submit" || els[i].type == "reset")
            {
                var _cName = els[i].className;
                if(_cName != "")
                {
                    els[i].setAttribute("_cName", _cName); 
                    els[i].onmouseover = function(){this.className = this.getAttribute("_cName") + "_1"}
                    els[i].onmouseout = function(){this.className = this.getAttribute("_cName")}
                }
            }
        }
    }catch(e){}
}

attachResizeEvent();
if(!_print)
{
    document.getElementById("TANGER_OCX").FilePrint = false;
    document.getElementById("TANGER_OCX").FilePrintPreview = false;
    document.getElementById("TANGER_OCX").FileSaveAs = false;
    document.getElementById("TANGER_OCX").IsNoCopy = true;
    document.getElementById("TANGER_OCX").FileNew = false;
    document.getElementById("TANGER_OCX").FileOpen = false;
}
//document.getElementById("TANGER_OCX").FileSave = false;
document.getElementById("TANGER_OCX").FileSaveAs = true;
document.getElementById("TANGER_OCX").FileSave = false;
//-->
</script>
</html>
