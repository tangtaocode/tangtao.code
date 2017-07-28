function _PagePrint()
{
    //define the adjust objest id,type(1 width, 2 height, 3 fontsize, 4 visible, 5 display)
    var defineIds = [["_width","1"], ["_height","2"],["_fix","3"], ["_nonevisible","4"], ["_nonedisplay","5"]];
	  
	  var status = 0;
    this.initWidth = null;
    this.initHeight = null;
    this.initSize = null;
    this.windowObj = null;
    this.showPrint = function()
    {
        if(this.windowObj == null) this.windowObj = self;
        
        if(status == 0)
        {
            document.body.insertAdjacentHTML("beforeEnd", "<OBJECT  name=\"_WebBrowser\" id=\"_WebBrowser\" height=0 width=0 classid=\"CLSID:8856F961-340A-11D0-A96B-00C04FD705A2\"></OBJECT>");
            status = 1;
        }
        //show pinter windows
        var o = window.event.srcElement;
        o.style.display = "none";
        this.setDisplay("none");
        this.setVisible("hidden");
        window.showModalDialog("printsetting.htm", this, "dialogTop:100px;dialogWidth:320px;dialogHeight:260px;help:no;status:no;");
        this.setDisplay("");
        this.setVisible("");

        this.undo();
        o.style.display = "";
    }
	
    this.getWebBrowser = function()
    {
        return document.getElementById("_WebBrowser");
    }
	
    this.undo = function()
	  {
	      //fontsize
	      if(this.fontSize.length > 0)
		    {
            for(var i=0; i<this.fontSize.length; i++)
            {
                this.fontSize[i][0].style.fontSize = this.fontSize[i][1];
            }
		    }
		
		    //width
		    if(this.width.length > 0)
		    {
            for(var i=0; i<this.width.length; i++)
            {
                this.width[i][0].style.width = this.width[i][1];
            }
		    }
        //height
        if(this.height.length > 0)
		    {
            for(var i=0; i<this.height.length; i++)
            {
                this.height[i][0].style.height = this.height[i][1];
                this.height[i][0].style.lineHeight = "";
            }
		    }
        if(this.adjusts.length > 0)
		    {
            for(var i=0; i<this.adjusts.length; i++)
            {
                this.adjusts[i][0].width = this.adjusts[i][1];
            }
		    }
	  }	
	  
    this.adjusts = [];
    this.width = [];
    this.setArrayWidth = function(oo)
    {
        if(oo.length != null)
        {
            for(var i=0; i<oo.length; i++)
            {
                if(oo[i].getAttribute("fix") == null || oo[i].getAttribute("fix") == "") continue;
                this.width[this.width.length] = [oo[i], oo[i].style.width];
            }
        }
        else if(oo.getAttribute("fix") != null && oo.getAttribute("fix") != "")
        {
            this.width[this.width.length] = [oo, oo.style.width];
        }
    }

    this.fixTables = function()
    {
        var tabs = document.all.tags("TABLE");
        var obj = null;
        for(var x=0; x<tabs.length; x++)
        {
            obj = tabs[x];
            if(obj.getAttribute("fix") == null || obj.getAttribute("fix") == "") continue;
            if(obj.width != "")
            {
                this.adjusts[this.adjusts.length] = [obj, obj.width];
            }
            for(var i=0;i<obj.rows.length; i++)
            {
                for(var j=0; j<obj.rows[i].cells.length; j++)
                {
                    if(obj.rows[i].cells[j].width != "")
                    {
                        this.adjusts[this.adjusts.length] = [obj.rows[i].cells[j], obj.rows[i].cells[j].width];
                    }
                }
            }
        }
        if(this.adjusts.length > 0)
		    {
            for(var i=0; i<this.adjusts.length; i++)
            {
                if(this.adjusts[i][0].tagName == "TABLE" && this.adjusts[i][0].fix == "1")
                {
                     this.adjusts[i][0].width = "100%";
                }
                else
                {
                     this.adjusts[i][0].width = "";
                }
            }
		    }
    }

    this.setWidth =function(width)
    {
        if(width == "*")
        {
            this.fixTables();
            return;
        }
        var oo = document.all.tags("TABLE");
        if(oo == null) return;
        if(this.width.length == 0) this.setArrayWidth(oo);
        if(this.width.length > 0)
        {
            for(var i=0; i<this.width.length; i++)
            {
                var _width = null;
                if(width == "")
                {
                    _width = "";
                }
                else if(!isNaN(this.width[i][0].width))
                {
                    _width = width * this.width[i][0].width;
                }
                else
                {
                    _width = width * 100 + "%";
                }
                this.width[i][0].style.width = _width;
            }
        }
    }
	
    this.height = [];
    this.setArrayHeight = function(oo)
    {
        if(oo.length != null)
        {
            for(var i=0; i<oo.length; i++)
            {
                this.setChildHeight(oo[i]);
            }
        }
        else
        {
            this.setChildHeight(oo);
        }
    }
    this.setChildHeight = function(obj)
    {
        if(obj.tagName != "TABLE" && obj.childNodes.length > 0)
        {
            for(var i=0; i<obj.childNodes.length; i++)
            {
                this.setChildHeight(obj.childNodes[i]);
            }
        }
        else
        {
            this.setCellHeight(obj);
        }
    }
    this.setCellHeight = function(obj, isMin)
    {
        if(obj.tagName == "TABLE")
        {
            if(!isMin && obj.getAttribute("h") == "l") isMin = true;
            for(var i=0;i<obj.rows.length; i++)
            {
                for(var j=0; j<obj.rows[i].cells.length; j++)
                {
                    if(obj.rows[i].cells[j].getAttribute("skip") == "1") continue;
                    for(var x=0; x<obj.rows[i].cells[j].childNodes.length; x++)
                    {
                        if(obj.rows[i].cells[j].childNodes[x].tagName == "TABLE")
                        {
                            if(!isMin && obj.getAttribute("h") == "l") isMin = true;
                            this.setCellHeight(obj.rows[i].cells[j].childNodes[x], isMin);
                        }
                    }
                    if(obj.rows[i].cells[j].style != null && obj.rows[i].cells[j].style.height != null)
                    {
                        this.height[this.height.length] = [obj.rows[i].cells[j], obj.rows[i].cells[j].style.height, (isMin)?"1":"0"];
                    }
                    else
                    {
                        this.height[this.height.length] = [obj.rows[i].cells[j], "", (isMin)?"1":"0"];
                    }
                }
            }
        }
    }
    this.setHeight =function(height)
    {
        var oo = document.all[defineIds[1][0]];
        if(oo == null) return;
        if(this.height.length == 0) this.setArrayHeight(oo);
        if(this.height.length > 0)
        {
            for(var i=0; i<this.height.length; i++)
            {
                this.height[i][0].style.lineHeight = "15px";
                if(this.height[i][2] == "1")
                {
                    this.height[i][0].style.height = 10; 
                }
                else
                {
                    this.height[i][0].style.height = height;
                }
            }
        }
    }
    
    this.fontSize = [];
    this.setArrayFontsize = function(oo)
    {
        if(oo.length != null)
        {
            for(var i=0; i<oo.length; i++)
            {
                this.setChildFontsize(oo[i]);
            }
        }
        else
        {
            this.setChildFontsize(oo);
        }
    }
    
    this.setChildFontsize = function(oo)
    {
        var ss = oo.childNodes;
        if(ss.length == 0)
        {
            if(oo.tagName != null && oo.tagName != "BR") 
            {
                this.fontSize[this.fontSize.length] = [oo, oo.style.fontSize];
            }
            return;
        }
        else
        {
            this.fontSize[this.fontSize.length] = [oo, oo.style.fontSize];
            for(var i=0; i<oo.childNodes.length; i++)
            {
                this.setChildFontsize(oo.childNodes[i]);
            }
        }
    }

    this.setFontsize = function(size)
    {
        var oo = document.body;
        if(this.fontSize.length == 0) this.setArrayFontsize(oo);
        if(this.fontSize.length > 0)
        {
            for(var i=0; i<this.fontSize.length; i++)
            {
                this.fontSize[i][0].style.fontSize = size;
            }
        }
    }

    this.setVisible = function(visible)
    {
        var oo = document.all[defineIds[3][0]];
        if(oo == null) return;
        if(oo.length != null)
        {
            for(var i=0; i<oo.length; i++)
            {
                oo[i].style.visibility  = visible;
            }
        }
        else
        {
            oo.style.visibility = visible;
        }
    }

    this.setDisplay = function(display)
    {
        var oo = document.all[defineIds[4][0]];
        if(oo == null) return;
        if(oo.length != null)
        {
            for(var i=0; i<oo.length; i++)
            {
                oo[i].style.display = display;
            }
        }
        else
        {
            oo.style.display = display;
        }
    }

    this.preview = function()
    {
        document.getElementById("_WebBrowser").ExecWB(7,1);
    }
    
    this.pageSetting = function()
    {
        document.getElementById("_WebBrowser").ExecWB(8,1);
    }

    this.print = function()
    {
        this.windowObj.focus();
        window.print();
    }

    this.initParam = function(width, height, fontSize)
    {
        this.initWidth = width;
        this.initHeight = height;
        this.initSize = fontSize;
    }
}

var _fPrinter = new _PagePrint();

function _doPrint()
{
    _fPrinter.showPrint();

	  return false;
}

function _initPrint(width, height, fontSize)
{
    _fPrinter.initParam(width, height, fontSize);
}

function _pageLoad()
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
if(document.attachEvent)
{
    window.attachEvent("onload", _pageLoad);
}
else if(document.addEventListener)
{
    window.addEventListener("load", _pageLoad, false);
}