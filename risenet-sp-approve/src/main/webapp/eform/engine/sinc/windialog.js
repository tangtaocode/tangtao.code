function banBackSpace(e)
{  
    var ev = e || window.event;if(!ev) return;
    var obj = ev.srcElement || ev.target;
	if(!obj) return true;

    var t = obj.type || obj.getAttribute('type');  
    var vReadOnly = obj.getAttribute('readonly');  
    var vEnabled = obj.getAttribute('enabled');  

    vReadOnly = (vReadOnly == null) ? false : vReadOnly;  
    vEnabled = (vEnabled == null) ? true : vEnabled;  
       
    var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea")&& (vReadOnly==true || vEnabled!=true))?true:false;
    var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea")?true:false;          

    if(flag2  || flag1) 
	{
	    e.cancelBubble = true;
	    if(e.preventDefault) e.preventDefault();
		return false;
	}

	return true;
}  


var __winPadding = [];
function __btnFocus(event, id)
{
	var doc = (this.contentWindow)?this.contentWindow.document:(frames[id])?frames[id].document:null;
	var id = (id)?id:this.id;

	if(!doc) return;
	var els = doc.getElementsByTagName("INPUT");

    if(__winPadding[id]) 
	{
		doc.body.style.margin = __winPadding[id] + "px";
	}
	for(var i=0; i<els.length; i++)
	{
		if(els[i].type == "button" || els[i].type == "submit" || els[i].type == "reset")
		{
			var _cName = els[i].className;
			if(_cName != "" && _cName.indexOf("_but") != -1)
			{
				els[i].setAttribute("_cName", _cName); 
				els[i].onmouseover = function(){this.className = this.getAttribute("_cName") + "_1"}
				els[i].onmouseout = function(){this.className = this.getAttribute("_cName")}
			}
		}
	}

    doc.onkeypress = banBackSpace;  
    doc.onkeydown = banBackSpace;  
}

(function()
{
	var TITLE_BACKGROUND = "#6596D8";
	var dialogWin = new Object();
	var isLowIE = navigator.appName.indexOf("Internet Explore") != -1;
	//var isIE6 = navigator.userAgent.indexOf("MSIE 6") != -1;

    var tempAlertDiv = null;
	var tempDocument = null;
	dialogWin.alert = function(win, message)
    {
		var mHeight = "100%";
		var padding = win.innerHeight;
		var mWidth = win.innetWidth;
		var doc = win.document;

		if(padding == null) padding = doc.body.clientHeight;
		if(mWidth == null) mWidth = doc.body.clientWidth;
		if(isLowIE)
		{
			var x = doc.body.scrollHeight;
			if(x < doc.body.clientHeight) x = doc.body.clientHeight;
			padding = x;
			mHeight = x + "px";
		}
		padding = padding/2 - 110;
		if(padding < 0) padding = 5;
	    var div = doc.createElement("div");
		div.style.position = (isLowIE)?"absolute":"fixed";		
		div.style.zIndex = 9999;		
		if(isLowIE)
		{
			div.style.width = "100%";
			div.style.top = padding + "px";
			div.style.textAlign = "center";
		}
		else
		{
			div.style.top = "0px";
			div.style.left = "0px";
			div.style.width = "100%";
			div.style.height = mHeight;
			div.style.background = "rgba(0, 0, 0, 0.03)";
			div.style.overflow = "hidden";
			div.style.paddingTop = padding + "px";
		}
		var _width = 200;
		if(_width > mWidth) _width = mWidth - 20;
		div.innerHTML = "<div style=\"text-align:center;letter-spacing:2px;color:#D00000;font-size:15px;padding:60px;border:1px solid #D0D0D0;border-radius:5px;background-color:#F3F3F3;width:" 
			            + _width + "px;margin:0px auto;white-space:nowrap;\">" + message + "</div>";
		div.style.margin = "0px auto";
		doc.body.appendChild(div);

		tempAlertDiv = div;
		tempDocument = doc;
	}
	
	dialogWin.dismissAlert = function()
	{
	    if(tempAlertDiv && tempDocument) {try{tempDocument.body.removeChild(tempAlertDiv);}catch(e){};tempAlertDiv=null;tempDocument=null;}
	}

	dialogWin.globalCover = new function()
	{
	    this.zIndex = null;
		this.cover = null;
		this.init = function()
		{//create the global cover
			var mHeight = "100%";
			if(isLowIE)
		    {
			    var x = document.body.scrollHeight;
				if(x < document.body.clientHeight) x = document.body.clientHeight;
				mHeight = x + "px";
			}
			var div = document.createElement("div");
			div.style.position = (isLowIE)?"absolute":"fixed";
			div.style.top = "0px";
			div.style.left = "0px";
			div.style.width = "100%";
			div.style.height = mHeight;
			div.style.overflow = "hidden";
			if(isLowIE)
			{
				div.style.backgroundColor = "#F9F9F9"; 
			    div.style.filter = "alpha(opacity=20)";
			}
			else
			{
			    div.style.background = "rgba(0, 0, 0, 0.02)";
			}
			div.style.display = "none";
			document.body.appendChild(div);
			this.cover = div;
		}

		this.show = function()
		{
		    this.cover.style.display = "";
			this.cover.style.zIndex = dialogWin.zIndex+1;
		}

		this.close = function()
		{
		    this.cover.style.display = "none";
		}
	}

	dialogWin.currentWin = null;
	dialogWin.openedWins = [];
	dialogWin.cacheWins = [];
	dialogWin.zIndex = 9000;
	dialogWin.frmDiv = null;
	dialogWin.frmIndex = 0;
	dialogWin._caller = null;
	
	dialogWin.post = function(title, winCall, frm, para)
	{
	    this.open(title, winCall, null, para);
		frm.target = this.openedWins[this.openedWins.length-1].frm.name;
		frm.submit();
	}

	dialogWin.open = function(title, winCall, url, para)
	{
		var _width = 400;
		var _height = 350;
		var _left = null;
		var _top = 200;

        if(url != null && url.substring(0, 15) == "gettemplate.jsp")
		{
		    url = "/engine/" + url;
		}

		if(para != null)
		{
		    if(para.top) _top = para.top;
			if(para.width) _width = para.width;
			if(para.height) _height = para.height;
		}

		var _sTop=0;
		try
		{
		    _sTop = document.documentElement.scrollTop || window.pageYOffset|| window.document.body.scrollTop;
		}catch(e){}

		var _left = document.body.clientWidth/2 - _width/2;
		if(para)
		{
		    if(para.left)
			{
			    _left = para.left;
			}
			else if(para.pleft)
			{
			    _left = _left + para.pleft;
			}
		}
		if(_left + _width > document.body.clientWidth)
		{
		    _left = document.body.clientWidth - _width - 10;
		}

		var tHeight = (document.documentElement.clientHeight)?document.documentElement.clientHeight:document.body.clientHeight;
		if(tHeight && (_top + _height > tHeight-10))
		{
		    _top = (tHeight - _height)/2 + _sTop - 10;
		}
		else
		{
		    _top = _top + _sTop;
		}
		if(_top < _sTop) _top = _sTop;
		dialogWin.zIndex = dialogWin.zIndex + 2;
		
		this.showCover();
		var winObj = null;		
		if(this.cacheWins.length > 0) winObj = this.cacheWins.pop();
		
		var div = (winObj)?winObj.div:null;
		if(div == null)
		{
		    div = document.createElement("div");
		    document.body.appendChild(div);
			div.style.position = "absolute";
			div.style.backgroundColor = "#FFFFFF";
			div.style.border = "1px solid #909090";
			div.style.borderBottomLeftRadius = "5px";
			div.style.borderBottomRightRadius = "5px";
			div.style.boxShadow = "0px 0px 20px #67686a";
			div.style.display = "none";
			div.style.filter = "progid:DXImageTransform.Microsoft.Shadow(color=#67686a,direction=120,strength=4)";
		}
		div.style.left = _left + "px";
		div.style.top = _top + "px";
		div.style.width = _width + "px";
		div.style.height = _height + "px";		
		div.style.zIndex = dialogWin.zIndex+2;		
        
        var divTitle = (winObj)?winObj.divTitle:null;
		if(divTitle == null)
		{
			divTitle = document.createElement("div");
			div.appendChild(divTitle);
			divTitle.style.paddingTop = "3px";
		    divTitle.style.paddingLeft = "5px";
		    divTitle.style.paddingRight = "3px";
			divTitle.style.backgroundColor = TITLE_BACKGROUND;
			divTitle.style.cursor = "move";
			divTitle.style.height = "21px";
		}
        divTitle.style.width = (_width-8) + "px";
        divTitle.innerHTML = "<div style=\"float:left;\"><table cellspacing=0 cellpadding=0><tr><td><img src=\"images/title_icon.gif\"></td>" +
			 "<td style=\"font-size:13px;color:#FFFFFF;font-weight:bold;padding-left:6px;letter-spacing:1px;-moz-user-select:none;\"" + 
			 " onselectstart=\"return false\">" + ((title)?title:"&nbsp;") + "</td></tr></table></div>" +
			 "<div style=\"margin-top:1px;margin-right:3px;width:14px;height:14px;float:right;cursor:default;background-image:url(images/close_x.gif);background-repeat:no-repeat;\" onclick=\"DIALOG_WIN.close()\"></div>";
        
		var frm = (winObj)?winObj.frm:null;
		if(frm == null)
		{
			var _id = "_dialog_" + this.frmIndex++;
			try
			{// for I.E.
				frm= document.createElement("<iframe onload=\"__btnFocus(event,'" + _id + "')\" name=\"" + _id + "\">");
			}catch(ex){
				frm = document.createElement('iframe');
				frm.name = _id;
			}
		
			frm.style.position = "absolute";
			frm.style.left = "0px";
			frm.style.top = "24px";
			frm.style.zIndex = 1;
			frm.scrolling = "auto";
			frm.frameBorder = "0";
			frm.id = _id;
		}
		__winPadding[frm.id] = para.padding;
		frm.style.width = (isLowIE)?(_width - 8) + "px":"100%";
		frm.style.height = (_height - 27) + "px";		
		if(url != null) 
		{
			if(url.indexOf("rnd=") == -1)
			{
				if(url.indexOf("?") != -1)
				{
					url = url + "&rnd=" + Math.floor(Math.random() * 1000000);
				}
				else
				{
					url = url + "?rnd=" + Math.floor(Math.random() * 1000000);
				}
			}
			frm.src = url;				
			frm.onload = __btnFocus;
		}
		if(!winObj) div.appendChild(frm);   //ie8

		var frmDiv = (winObj)?winObj.frmDiv:null;
		if(frmDiv == null)
		{
			frmDiv = document.createElement("div");
			div.appendChild(frmDiv);
			frmDiv.style.position = "absolute";
			frmDiv.style.left = "0px";
			frmDiv.style.top = "23px";
			frmDiv.style.zIndex = 2;
			frmDiv.onclick = function(){this.style.display = "none";};
		}
		frmDiv.style.width = "100%";
		frmDiv.style.height = (_height-30) + "px";
		frmDiv.style.display = "none";		

		this.frmDiv = frmDiv;
		if(winObj == null)
		{
			new this.move().init(div, this.zIndex);
			winObj = new Object();
			winObj.div = div;
			winObj.divTitle = divTitle;
			winObj.frm = frm;
			winObj.frmDiv = frmDiv;
		}
		winObj._caller = winCall; //  set the caller
		this._caller = winCall;

		this.openedWins[this.openedWins.length] = winObj;
		this.currentWin = div;

		div.style.display = "";
	}
	
	dialogWin.dealMove = function(status)
	{
	    this.frmDiv.style.display = (status==1)?"":"none";
	}

	var closeLocked = false;
    dialogWin.autoClose = function(win, message, msec)
	{
	    if(msec)
		{
			if(closeLocked) return;
			closeLocked = true;
			if(message) this.alert(win, message);
		    window.setTimeout(function(){DIALOG_WIN.dismissAlert();closeLocked = false;DIALOG_WIN.close();}, msec);
		}
		else
		{
		    this.close();
		}
	}

	dialogWin.close = function()
	{
		if(closeLocked) return;
        if(this.openedWins.length > 0)
		{
		    var _winObj = this.openedWins.pop();
			//to cache
			this.cacheWins[this.cacheWins.length] = _winObj;
			this.zIndex = dialogWin.zIndex-2;
			if(_winObj) _winObj.div.style.display = "none";

			//fire event
			if(this._caller && this._caller.callback) try{this._caller.callback(this._caller);}catch(e){}
			if(this.openedWins.length > 0) 
			{
				this.showCover();
				var newWinObj = this.openedWins[this.openedWins.length-1];
				this.currentWin = newWinObj.div;
				this.frmDiv = newWinObj.frmDiv;
				this._caller = newWinObj._caller;
			}
			else
			{
			    this.globalCover.close();
				this.currentWin = null;
			}
			if(_winObj && _winObj.frm) _winObj.frm.src = "loading.htm";
		}
		closeLocked = false;
	}
	
	dialogWin.showCover = function()
	{
	    if(dialogWin.globalCover.cover == null) dialogWin.globalCover.init();
		dialogWin.globalCover.show();
	}
	

	dialogWin.move = function()
	{
		this._move = false;
		this._x = null;
		this._y = null;
		
		this.getEvent = function()
		{
			if(window.event)
			{
				return window.event;
			}

			var _caller = this.getEvent.caller;
			while(_caller != null)
			{
				var _argument = _caller.arguments[0];
				if(_argument)
				{
					var _temp = _argument.constructor;
					if(_temp.toString().indexOf("Event") != -1)
					{
						return _argument;
					}
				}
				_caller = _caller.caller;
			}
			return null;
		}

		this.init = function(moveObj, index)
		{
			moveObj.setAttribute("mObject", "o" + index);
			var _mObject = null;
			try{_mObject = window._mObject}catch(e){};
			if(_mObject == null) _mObject = [];
			_mObject["o" + index] = this;
			window._mObject = _mObject;

			var _onmousedown = function()
			{
				if(DIALOG_WIN.currentWin == null) return;

				var obj = window._mObject[DIALOG_WIN.currentWin.getAttribute("mObject")];
				var event = obj.getEvent();

				var which = navigator.userAgent.indexOf('MSIE') > 1 ? event.button ==1?1:0 : event.which ==1?1:0;  
				if(which)
				{  
					obj._move = true; 
					DIALOG_WIN.dealMove(1);
					_x = event.clientX - parseInt(DIALOG_WIN.currentWin.style.left); 
					_y = event.clientY - parseInt(DIALOG_WIN.currentWin.style.top);  
				}   
			} 
			
			//init the events
			if(document.attachEvent)
			{
				moveObj.attachEvent("onmousedown", _onmousedown);
			}
			else if(document.addEventListener)
			{
				moveObj.addEventListener("mousedown", _onmousedown, false);
			}

			var _onmousemove = function()
			{
				if(DIALOG_WIN.currentWin == null) return;

				var obj = window._mObject[DIALOG_WIN.currentWin.getAttribute("mObject")];
				var event = obj.getEvent();

				if(obj._move)
				{
					var x = event.clientX - _x;
					var y = event.clientY - _y;
					DIALOG_WIN.currentWin.style.left = x + "px";
					DIALOG_WIN.currentWin.style.top = y + "px";
				}  
			}
			
			var _onmouseup = function()
			{
				if(DIALOG_WIN.currentWin == null) return;

			    var obj = window._mObject[DIALOG_WIN.currentWin.getAttribute("mObject")];
				obj._move = false;
				DIALOG_WIN.dealMove(0);
			}

			if(document.attachEvent)
			{
				document.attachEvent("onmousemove", _onmousemove);
			}
			else if(document.addEventListener)
			{
				document.addEventListener("mousemove", _onmousemove, false);
			}
			if(document.attachEvent)
			{
				document.attachEvent("onmouseup", _onmouseup);
			}
			else if(document.addEventListener)
			{
				 document.addEventListener("mouseup", _onmouseup, false);
			}
	   }
	}

	window.DIALOG_WIN = dialogWin;
})(); 