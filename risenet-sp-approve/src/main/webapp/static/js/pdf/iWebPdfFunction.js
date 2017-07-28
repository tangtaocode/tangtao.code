$(function(){
/**公共方法**/	
    

	//文档阅读功能，文档控制功能，附加功能事例  收放
    var isNotLoad = true;
	$(".tableAll").click(function(){
        if(isNotLoad){
            isNotLoad = false;	 
			  var noneY = $(this).next().css("display");
			  $(".tableAll").next().css("display","none");
			  //$(".tableAll").find('td:eq(0)').css({'background-color':'#E6DBEC'});
			  $(".tableAll").find('span:eq(0)').html('+');

				  if( noneY== 'none'){
					  var s = $(this).find('td:eq(0)').html();                
					  $(this).find('td:eq(0)').html(s.replace("+", "-")) ;                              
					 // $(this).find('td:eq(0)').css({'background-color':'blue'});
		              $(this).next().slideToggle(function(){isNotLoad = true;});
				  }else{
					  isNotLoad = true;
				  }
            }
	});
	//功能列表收放
	var hide = false;
	$("#disPlayNone").click(function(){
		
		 if(hide){
			 $('#showTD').width('204px');
			 $(this).siblings().css("display", "")
			
			 hide = false;
		 }else{	
			 $('#showTD').width('25px');
			 $(this).siblings().css("display", "none")
			 hide = true;
		 }

	});	
    //状态信息
	function addState(value){
		$("#state").html(value);	
	}
    //打开文档
	function WebOpen()
	{   
		
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.WebUrl = url+"PDFServer.jsp";
		var tempFile = iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.CreateTempFileName();
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("DBSTEP","DBSTEP");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("OPTION","LOADFILE");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("FILETYPE","PDF");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("USERNAME","演示人");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("RECORDID",mRecordID);
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("FILENAME","1385716767003.pdf");
		if ( iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.PostDBPacket(false))
		{ 
			iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.MsgFileSave(tempFile);
			
			iWebPDF2015.Documents.Open(tempFile);
			iWebPDF2015.Documents.ActiveDocument.Views.ActiveView.Zoom = 100;
			addState("打开成功");
		}
		else
		{	alert("打开失败");
			addState("打开失败");
		}
	}
	
	if(mRecordID != 'null' && mIsExsitRId){
	WebOpen();
	}
	

	

/**文档保存**/	
		

$("#saveFile").click(function(){
	try{
		//在线保存文档
		alert("保存PDF");
		if ( 0 == iWebPDF2015.Documents.Count )
		{
			alert("没有要保存的文档");
			return;
		}
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.WebUrl = url+"PDFServer.jsp";
		var tempFile = iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.CreateTempFileName();
		iWebPDF2015.Documents.ActiveDocument.Save(tempFile);
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.MsgFileLoad(tempFile);
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("DBSTEP","DBSTEP");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("OPTION","SAVEFILE");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("FILETYPE","PDF");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("USERNAME","演示人");
		iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.SetMsgByName("RECORDID",mRecordID);

		if (iWebPDF2015.COMAddins("KingGrid.MsgServer2000").Object.PostDBPacket(false))
		{ 
			
			$('#Subject').val($('#txtSubject').val());
			$('#Author').val($('#txtAuthor').val());
			$('#iWebPDF').submit();
			addState("保存成功！文档编号是："+mRecordID);
		}
		else
		{
			addState("失败成功！");
		}
	}catch(e){
	  addState("打开失败"); 
	  alert(e.description);
	}								 
});		
	
	
	
	
/** 文档阅读功能**/	

  //打开本地文档 		   
  $("#openLocalFile").click(function(){
	 try{

		iWebPDF2015.Documents.Open();
		addState("打开成功");
	 }catch(e){
	    addState("打开失败"); 
	    alert(e.description);
	 }								 
  });		   
		   
  //保存本地文档
  $("#saveLocalFile").click(function(){
	 try{
		   if ( 0 == iWebPDF2015.Documents.Count ){
				alert("没有已打开文档");
				return;
		}
		iWebPDF2015.Documents.ActiveDocument.Save();
		addState("保存成功");
		alert('保存成功');
	 }catch(e){
	   addState("打开失败"); 	 
	   alert(e.description);
	 }								 
  });
		   
  //关闭当前的文档
   $("#closeActiveFile").click(function(){
	 try{
		if ( 0 == iWebPDF2015.Documents.Count ){
				alert("没有已打开文档");
				return;
		}
		iWebPDF2015.Documents.ActiveDocument.Close();
		addState("关闭成功");
	 }catch(e){
	   addState("关闭失败"); 	 
	   alert(e.description);
	 }								 
  });
   
  //关闭所有文档
  $("#closeAllFile").click(function(){
	 try{
		   if ( 0 == iWebPDF2015.Documents.Count ){
				alert("没有已打开文档");
				return;
		}
		iWebPDF2015.Documents.CloseAll();
		addState("关闭成功");
	 }catch(e){
	   addState("关闭失败"); 	 
	   alert(e.description);
	 }								 
  });
  //下一页
  
  
  $("#getVersion").click(function(){
		 try{
			 
			 alert(iWebPDF2015.Version);
			addState("获取成功。");
		 }catch(e){
		   addState("获取失败。"); 	 
		   alert(e.description);
		 }								 
   });
  
  $("#moveToNextPage").click(function(){
	 try{
		   if ( 0 == iWebPDF2015.Documents.Count ){
				alert("没有已打开文档");
				return;
		} 
		iWebPDF2015.Documents.CloseAll();
		addState("关闭成功");
	 }catch(e){
	   addState("关闭失败"); 	 
	   alert(e.description);
	 }								 
  });  


/** 文档控制功能**/	
  
  //添加页
  $("#addPage").click(function(){
	 try{
	  if ( 0 == iWebPDF2015.Documents.Count ){
			alert("没有已打开文档");
			return;
	  }
	  if(!iWebPDF2015.Documents.ActiveDocument.HasPermissions(1)){
		alert("您没有修改的权限，无法新增页。");  
	  }
	  if (iWebPDF2015.Documents.ActiveDocument.Pages.Add(592, 842, 0, 0)){
			//iWebPDF2015.Documents.ActiveDocument.Views.Item(0);
			addState("新增页成功！"); 
	  }else{
		addState("新增页失败！"); 
	  }
	 }catch(e){
	   addState("新增页失败"); 	 
	   alert(e.description);
	 }								 
  }); 
  
  //添加工具栏
  
  $("#addToolBar").click(function(){
	 try{
		   if ( 0 == iWebPDF2015.Documents.Count ){
				alert("没有已打开文档");
				return;
		}		 
        var custombar = iWebPDF2015.CommandBars.Add("Custom");
        var btn = custombar.Controls.Add(1);
        btn.Style = 2;
        btn.Caption = "自定义按钮(保存)";
        btn.FaceId = 2;
		btn.Enabled = 1;
		comdbar_ID_1 = btn.ID;
		/*
        btn = custombar.Controls.Add();
        btn.Style = 2;
        btn.Caption = "Custom1";
        btn.FaceId = 4;
		btn.Enabled = 0;
		*/
        custombar.Update();
		custombar.ShowPopup(0, 0);	
		addState("添加按钮成功。"); 
	  }catch(e){
	   addState("添加按钮失败。"); 	 
	   alert(e.description);
	  }		
  }); 
  

  //添加菜单
  
  $("#addMenu").click(function(){
	 try{
		 
		   if ( 0 == iWebPDF2015.Documents.Count ){
				alert("没有已打开文档");
				return;
		}		 
     	var menubar = iWebPDF2015.CommandBars.Item("MenuBar");
	    var popup = menubar.Controls.Add(3, 6);
	    popup.Caption = "TestCustomMenus";
	    var btn = popup.Controls.Add();
	    btn.Caption = "Menu1";
	    menuid_ID_1 = btn.ID;
		
		btn = popup.Controls.Add();
		btn.Caption = "Menu2";
		btn.Check = true;
		//iWebPDF2015.Images.Add("C:\\Program Files\\iSignature_V8\\plugins\\iWebPDF2015.Annots\\images\\IDB_BMP_ARROW_24.png", menuid);
		menubar.Update();
		addState("添加菜单成功"); 	 
	  }catch(e){
	   addState("添加菜单失败。"); 	 
	   alert(e.description);
	  }		
  }); 
  
  //添加水印
  
  $("#Watermark").click(function(){
		 try{
		   if ( 0 == iWebPDF2015.Documents.Count ){
					alert("没有已打开文档");
					return;
		   }
			 
			var Watermark = iWebPDF2015.Documents.ActiveDocument.Watermark;
			Watermark.FontColor= "001";
			Watermark.FontSize = 100;
			Watermark.Opacity = 10;
			Watermark.Rotation = 40;
			Watermark.Text = "Watermark";
			Watermark.Execute(0, 50, 400);
			addState("添加文档水印成功"); 	 
		  }catch(e){
		   addState("添加文档水印失败。"); 	 
		   alert(e.description);
		  }		
	  }); 
  //获取文档内容
  $("#getContext").click(function(){
		 try{
		   if ( 0 == iWebPDF2015.Documents.Count ){
					alert("没有已打开文档");
					return;
		   } 
		  alert(iWebPDF2015.Documents.ActiveDocument.Pages(0).Text);
		  addState("获取文档内容成功。"); 	 
		  }catch(e){
		   addState("获取文档内容失败。"); 	 
		   alert(e.description);
		  }		
	  }); 
  
/** 文档批注功能**/	

  //添加图片批注
  $("#addPictureAnnot").click(function(){
	 try{							  
		if ( 0 == iWebPDF2015.Documents.Count )
		{
			alert("没有已打开文档");
			return;
		}
		var annot = iWebPDF2015.Documents.ActiveDocument.Pages(0).Annots.Add(12);
		annot.FromDeviceRect(100,100,200,200);
		annot.BlendMode = "Multiply";
		annot.Title = "Admin";
		annot.Color = 255;
		annot.ImageAppearance("c:\\aa.jpg");
		iWebPDF2015.Documents.ActiveDocument.Views.ActiveView.Refresh();
		addState("添加图片批注成功"); 	 
	  }catch(e){
	   addState("添加图片批注失败。"); 	 
	   alert(e.description);
	  }		
  }); 


  //获取批注个数
  $("#getAnnotCount").click(function(){
	 try{							  
		if ( 0 == iWebPDF2015.Documents.Count ){
			alert("没有已打开文档");
			return;
		}
		var nAnnot = iWebPDF2015.Documents.ActiveDocument.Pages.Item(0).Annots.Count;
		alert("文档中共有批注"+nAnnot); 
	  }catch(e){
	   addState("获取失败。"); 	 
	   alert(e.description);
	  }		
  }); 

  //添加图片域
  $("#addPicSigfield").click(function(){
	 try{							  
		if ( 0 == iWebPDF2015.Documents.Count )
		{
			alert("没有已打开文档");
			return;
		}

		var fields = iWebPDF2015.Documents.ActiveDocument.Fields;
		var field = fields.Add(6);
		field.Name = "Signature100";
		var widget = field.AddToPage(0);;
		widget.FromDeviceRect(100, 100, 200, 200);
		widget.ImageAppearance("C:\\aa.jpg");//设置图片外观
		iWebPDF2015.Documents.ActiveDocument.Save();
		addState("添加图片成功。"); 	 
	  }catch(e){
	   addState("添加图片失败。"); 	 
	   alert(e.description);
	  }		
	 
  });   
  
  //跳转到指定域
  $("#gotoFields").click(function(){
	 try{							  
		if ( 0 == iWebPDF2015.Documents.Count ){
			alert("没有已打开文档");
			return;
		}
		var fields = iWebPDF2015.Documents.ActiveDocument.Fields;
		if(fields.Count != 0){
			fields(0).Goto();
		}else{
			alert("文档中不存在域");
		}	
		addState("添加图片批注失败。"); 	
	  }catch(e){
	   addState("添加图片批注失败。"); 	 
	   alert(e.description);
	  }		
  }); 
  
    //添加附件
  $("#addAttchments").click(function(){
	 try{
			if ( 0 == iWebPDF2015.Documents.Count )
			{
				alert("没有已打开文档");
				return;
			}		 
        iWebPDF2015.COMAddins("KingGrid.iWebPDF2015").Object.AddAttachments("C://test.pdf","test.pdf")
		addState("添加附件成功。"); 	
	  }catch(e){
	   addState("添加附件失败。"); 	 
	   alert(e.description);
	  }		
  }); 
  
  //图片签章
  
  $("#Signature").click(function(){
		 try{	
				if ( 0 == iWebPDF2015.Documents.Count )
				{
					alert("没有要签名的文档");
					return;
				}
				
				var addin = iWebPDF2015.COMAddins("KingGrid.iWebPDF2015").Object;
				addin.SignaturePages = "1-2";  
				//0 绝对坐标 1 相对坐标 2 文本定位 3 域定位 4未签名的域放置签名
				
				addin.SignaturePosMode = 1;
				addin.SignaturePos = "50*50";
				
				addin.SignatureWidth = 4;
				addin.SignatureHeight = 4;
				//指定了签章名称
				addin.SignatureImage = 1;
				//指定签章图片路径
				addin.SignatureImage = "C:/aa.jpg";
				//addin.SignatureImage ="http://www.kinggrid.com/images/logo.jpg";
				//addin.SignatureCSP = "EnterSafe ePass3003 CSP v1.0";
				//addin.SignaturePIN = "123456";
				//addin.SignatureCert = "徐根英_正常";
				addin.CreateSignature();
			    addState("签名成功。"); 	
		  }catch(e){
		       addState("签名失败  。"); 	 
		       alert(e.description);
		  }		
	  });
  
  //删除所有附件
  $("#delAttchments").click(function(){
		 try{	
				if ( 0 == iWebPDF2015.Documents.Count ){
					alert("没有要签名的文档");
					return;
				}
				iWebPDF2015.COMAddins("KingGrid.iWebPDF2015").Object.DelAttachments("");
			    addState("删除附件成功。"); 	
		  }catch(e){
		       addState("删除附件失败  。"); 	 
		       alert(e.description);
		  }		
	  }); 
  //删除所有的签名
  $("#delFields").click(function(){
		 try{	
				if ( 0 == iWebPDF2015.Documents.Count )
				{
					alert("没有已打开文档");
					return;
				}
				var fields = iWebPDF2015.Documents.ActiveDocument.Fields;
				var count = fields.SignatureCount;
				
				for(var i=0; i<count; i++)
				{
					var sigfield = fields.SignatureField(0);
					sigfield.ClearSignature();
					sigfield.Delete();
				}
				var nDle = count - fields.SignatureCount;
				iWebPDF2015.Documents.ActiveDocument.Save();
			    addState("删除成功。共删除域签名："+nDle+"个"); 	
		  }catch(e){
		       addState("删除失败  。"); 	 
		       alert(e.description);
		  }		
	  }); 	
  //获取文档域内容xml
  $("#gotoFieldvalue").click(function(){
		 try{	
				if ( 0 == iWebPDF2015.Documents.Count )
				{
					alert("没有已打开文档");
					return;
				}
				alert(iWebPDF2015.Documents(0).Fields.ExportData (0));
		  }catch(e){
		       addState("获取失败  。"); 	 
		       alert(e.description);
		  }		
	  }); 	
  //插入文档域内容xml
  $("#insertFieldvalue").click(function(){
		 try{	
				if ( 0 == iWebPDF2015.Documents.Count )
				{
					alert("没有已打开文档");
					return;
				}
				var xml='<?xml version="1.0" encoding="UTF-8"><fields xmlns:xfdf="http://ns.adobe.com/xfdf-transition/"><topmostSubform[0].Page1[0].tbdw[0]>填报单位wwww</topmostSubform[0].Page1[0].tbdw[0]></fields>';
				iWebPDF2015.Documents(0).Fields.ImportDate(0,"");
		  }catch(e){
		       addState("插入失败  。"); 	 
		       alert(e.description);
		  }		
	  }); 	
  
  
})

	
	
	