var diag = new Dialog();
function openNewDialog(url,title,width,height){
		
	    diag.Title = title;
	    diag.ShowMaxButton=true;
	    diag.ShowMinButton=true;
	    diag.OKEvent = function(){
	    	saveDocument();
	    };
	    diag.Width=width;
	    diag.Height=height;
	    diag.CancelEvent = function(){
	       diag.close();
	    };
	    diag.URL = url;
	    diag.ShowButtonRow=true;
	    diag.show();
}