function func_insert()
{
 	var select2 = document.getElementById("select2");
	for (i=0; i<select2.options.length; i++)
	{
	  if(select2.options(i).selected)
	  {
	    var option_text=select2.options(i).text;
	    var option_value=select2.options(i).value;
	    var option_style_color=select2.options(i).style.color;
	    var my_option = document.createElement("OPTION");
	    my_option.text=option_text;
	    my_option.value=option_value;
	    my_option.style.color=option_style_color;
	    var pos=select2.options.length;
	    document.getElementById("select1").add(my_option,pos);
	    select2.remove(i);
	    i--;
	  }
	}
	setSelectedLaws();
}
function func_delete()
{
	var select1 = document.getElementById("select1");
	for (var i=0;i<select1.options.length;i++)
	{
	  if(select1.options(i).selected)
	  {
	    var option_text=select1.options(i).text;
	    var option_value=select1.options(i).value;
	    var my_option = document.createElement("OPTION");
	    my_option.text=option_text;
	    my_option.value=option_value;
	    var select2 = document.getElementById("select2");
	    var pos=select2.options.length;
	    select2.add(my_option,pos);
	    select1.remove(i);
	    i--;
		}
	}
	setSelectedLaws();
}

//上移法律法规
function func_up(){
	var select2 = document.getElementById("select2");
	for (var i=0;i<select2.options.length;i++)
	{
	  if(i==0&&select2.options(i).selected)
	  {
	  	//alert("亲，已经是第一个啦！");
	  }
	  if(i>0&&select2.options(i).selected)
	  {
	    var option_text=select2.options(i-1).text;
	    var option_value=select2.options(i-1).value;
	    var my_option = document.createElement("OPTION");
	    my_option.text=option_text;
	    my_option.value=option_value;
	    var pos=i;
	    select2.remove(i-1);
	    select2.add(my_option,pos);
	  }
	}
	setSelectedLaws();
}

//下移法律法规
function func_down(){
	var select2 = document.getElementById("select2");
	for (var i=0;i<select2.options.length;i++)
	{
	  if(i>=select2.options.length-1&&select2.options(i).selected)
	  {
	  	//alert("亲，已经是第最后一个啦！");
	  }
	  if(i<select2.options.length-1&&select2.options(i).selected)
	  {
	    var option_text=select2.options(i+1).text;
	    var option_value=select2.options(i+1).value;
	    var my_option = document.createElement("OPTION");
	    my_option.text=option_text;
	    my_option.value=option_value;
	    var pos=i;
	    select2.remove(i+1);
	    select2.add(my_option,pos);
	  }
	}
	setSelectedLaws();
}

function setSelectedLaws()
{	
	var select2 = document.getElementById("select2");
  	var fld_str="";
  	for (i=0; i< select2.options.length; i++)
  	{
       var options_value=select2.options(i).value;
       fld_str+=options_value+",";
    }	
	fld_str = fld_str.substr(0,fld_str.length-1);
	document.getElementById("selectedLaws").value=fld_str;		
}

function getLawList(){
	var title = document.getElementById("flfgname").value;	
	var flfgtype = document.getElementById("flfgtype").value;
	var select2 = document.getElementById("select2");
  	var fld_str="";
	$.post(ctx+"/itemTree/lawList",{title:title,type:flfgtype,id:fld_str},function(data){
		setLawData(data);
	});
}

function setLawData(data){
	if(data!=null){	
		//DWRUtil.removeAllOptions("select1");	
		//DWRUtil.addOptions("select1",data,"id","title");
		var select1 = document.getElementById("select1");	
		select1.options.length=0;
		for(var i=0;i<data.length;i++){
			select1.options.add(new Option(data[i].title, data[i].id));	
		}
	}
}
function resetList(){
	document.getElementById("select2").options.length=0;
	getLawList();
}

function addLaw(){
	var val=window.showModalDialog("/xzql/law/add.jsp?method=approveitem","","dialogWidth=1024px;dialogHeight=768px");
	if(val!=null){				
		var select2 = document.getElementById("select2");
		var my_option = document.createElement("OPTION");
	    my_option.text=val[0];
	    my_option.value=val[1];	    
	    var pos=select2.options.length;
	    document.getElementById("select2").add(my_option,pos);
	    setSelectedLaws();
	}
}

