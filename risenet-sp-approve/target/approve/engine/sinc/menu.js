var isVisible;
var menu_h
var global = window.document
global.fo_currentMenu = null
global.fo_shadows = new Array

document.onmouseover= function(){HideMenu()};
function HideMenu() 
{
  var mX;
  var mY;
  var vDiv;
  var mDiv;

  if (isVisible == true)
  {
      vDiv = document.all("menuDiv");
      mX = window.event.clientX + document.body.scrollLeft;
      mY = window.event.clientY + document.body.scrollTop;
      if ((mX < parseInt(vDiv.style.left)) || (mX > parseInt(vDiv.style.left)+vDiv.offsetWidth) || 
      (mY < parseInt(vDiv.style.top)-menu_h) || (mY > parseInt(vDiv.style.top)+vDiv.offsetHeight))
      {
      	  vDiv.style.visibility = "hidden";
      	  isVisible = false;
      }
  }
}

function ShowMenu(vMnuCode,tWidth, bgColor) 
{
  var vSrc = window.event.srcElement;
  var w, l, t;
  var topMar = 1;
  var leftMar = -2;
  var space = 1;
  
	vMnuCode = "<table id='submenu' cellspacing=1 cellpadding=3 style='width:"+tWidth + 
	           ";background-color:#cccccc;border:1px' onmouseout='HideMenu()'>" +
	           "<tr height=23><td nowrap align=left bgColor='" + bgColor + "'>" + vMnuCode + "</td></tr></table>";
  menu_h = vSrc.offsetHeight;
  w = vSrc.offsetWidth;
  l = vSrc.offsetLeft + leftMar + 4;
  t = vSrc.offsetTop + topMar + menu_h + space-2;
  vParent = vSrc.offsetParent;
	
  while (vParent.tagName.toUpperCase() != "BODY")
  {
      l += vParent.offsetLeft;
      t += vParent.offsetTop;
      vParent = vParent.offsetParent;
  }
  menuDiv.innerHTML = vMnuCode;
  menuDiv.style.top = t;
  menuDiv.style.left = l;
  menuDiv.style.visibility = "visible";
  isVisible = true;
  makeRectangularDropShadow(submenu, 4)
}

function makeRectangularDropShadow(el, size)
{
  var i;
  for (i=size; i>0; i--)
  {
      var rect = document.createElement('div');
      var rs = rect.style
      rs.position = 'absolute';
      rs.left = (el.style.posLeft + i) + 'px';
      rs.top = (el.style.posTop + i) + 'px';
      rs.width = el.offsetWidth + 'px';
      rs.height = el.offsetHeight + 'px';
      rs.zIndex = el.style.zIndex - i;
      rs.backgroundColor = subMenuBgColor;
      var opacity = 1 - i / (i + 1);
      rs.filter = 'alpha(opacity=' + (100 * opacity) + ')';
      el.insertAdjacentElement('afterEnd', rect);
      global.fo_shadows[global.fo_shadows.length] = rect;
  }
}