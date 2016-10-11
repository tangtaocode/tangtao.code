function Assistance(objName, fnName, width, height)
{
    this.objName = objName;	
	  this.fnName = fnName;
	  this.__signMenu = null;
	  this.width = width;
    this.height = height;

    this.iframeSrcReload = true;
    this.basePara = null;
    
    this.setBasePara = function(basePara)
    {
        this.basePara = basePara;
        this.iframeSrcReload = true;
    }

    this.createMenu = function(inputName, alias, contextPath)
    {
        if(this.width == null) this.width = 180;
        if(this.height == null) this.height = 220;
        this.inputName = inputName;
        this.alias = alias;
        this.contextPath = contextPath;
        this.iFrameObject = null;

        //random name
        if(this.__signMenu == null) 
        {
            this.__signMenu = "c_" + parseInt(Math.random() * 1000);
            var _div = document.createElement("DIV");
            _div.id = this.__signMenu;
            _div.style.width = this.width;
            _div.style.height = this.height;
            _div.style.display ="none";
            _div.style.position = "absolute";
            _div.style.border = "1px solid #303030";
            _div.style.fontSize = "13px";
            _div.style.cursor = "default";
            document.body.appendChild(_div);
            var _menu = document.createElement("IFRAME");
            this.iFrameObject = _menu;
            _menu.style.width = this.width;
            _menu.style.height = this.height;
			_menu.scrolling = "no";
            _menu.frameBorder = "0";
        }
        _div.appendChild(_menu);

        if(document.getElementById(this.inputName) != null)
        {
            document.getElementById(this.inputName).objName = this.objName;
            try{document.getElementById(this.inputName).onclick = function(){eval(this.objName + ".showInput(null)")}}catch(e){}
            try
            {
                if(window.__objName == null) 
                {
                    window.__objName = [];
                    if(document.attachEvent)
				    {
					    document.attachEvent("onclick", function(){for(var i=0;i<window.__objName.length;i++){if(window.event.srcElement==document.getElementById(eval(window.__objName[i] + ".inputName")))continue;document.getElementById(eval(window.__objName[i] + ".__signMenu")).style.display = "none";}})
					}
				    else
				    {
					    document.addEventListener("click", function(){for(var i=0;i<window.__objName.length;i++){if(__getEvent()==document.getElementById(eval(window.__objName[i] + ".inputName")))continue;document.getElementById(eval(window.__objName[i] + ".__signMenu")).style.display = "none";}}, false)
					}
                }                
                window.__objName[window.__objName.length] = this.objName;
            }catch(e){}
            eval(this.objName + ".showInput(true)")
        }
    }
	
    this.getSignMenu = function()
    {
        return this.__signMenu;
    }
    
    this.initFrameSrc = function(show)
    {//init the frame src
        if(show || !this.iframeSrcReload) return;
        if(this.contextPath == null) this.contextPath = "";
        if(this.contextPath.charAt(this.contextPath.length -1) != "/")
        {
            this.contextPath = this.contextPath + "/";
        }
        if(this.basePara != null)
        {
            this.iFrameObject.src = this.contextPath + "engine/listcodebyalias.jsp?" + this.basePara + "&obj=" + this.objName;
        }
        else if(this.alias != null)
        {
            this.iFrameObject.src = this.contextPath + "engine/listcodebyalias.jsp?alias=" + this.alias + "&obj=" + this.objName;
        }
        this.iframeSrcReload = false;
    }

    this.showInput = function(show)
    {		
        this.initFrameSrc(show);
        var e = document.getElementById(this.inputName);
        var o = document.getElementById(this.__signMenu).style;if(o.display!="none") return;
        var t = e.offsetTop,  h = e.clientHeight, l = e.offsetLeft, p = e.type;
        while (e = e.offsetParent){t += e.offsetTop; l += e.offsetLeft;}
        if(!show) o.display = "";
        var cw = this.width, ch = 220;
        var dw = document.body.clientWidth, dl = document.body.scrollLeft, dt = document.body.scrollTop;
        if (document.body.clientHeight + dt - t - h >= ch) o.top = (p=="textarea" || p=="input")? t + h : t + h + 6;
        else o.top  = (t - dt < ch) ? ((p=="textarea" || p=="input")? t + h : t + h + 6) : t - ch;
        if (dw + dl - l >= cw) o.left = l; else o.left = (dw >= cw) ? dw - cw + dl : dl;
    }

    this.setAssign = function setAssign(obj)
    {
        document.getElementById(this.__signMenu).style.display = "none";
        if(this.fnName != null)
        {
            try
            {
                eval(this.fnName + "(\"" + obj.innerHTML + "\",\"" + obj.getAttribute("code") + "\")");
            }
            catch(e){}
        }
    }

    this.clearValue = function()
    {
        document.getElementById(this.__signMenu).style.display = "none";
        if(this.fnName != null)
        {
            try
            {
                eval(this.fnName + "(\"\",\"\")");
            }
            catch(e){}
        }
    }
}