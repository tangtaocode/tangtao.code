     
	function initFileBox(fileboxObject){
		//重载控件对象
		var boxID=fileboxObject.FILEBOXNAME;
		var divID=fileboxObject.FILEBOXNAME+"DIV";
		var rsID=fileboxObject.FILEBOXNAME+"XML";
			document.all(divID).innerHTML ="";
			var fileOjectHTML=
				'<object classid="clsid:E8FD8E76-203A-48ed-9C39-481479080C34" id="'+boxID+'"'
				//'<object classid="clsid:E8FD8E76-203A-48ed-9C39-481479080C34" id="static"'
				+' width="100%" height="100%" CODEBASE="'+webContextPath+'/approve/static/js/ntko/ocx/ntkofman.cab#version=5,0,1,3">\n'
				+'<param name="ProductCaption" value="深圳市罗湖区信息中心">\n'
				+'<param name="ProductKey" value="1C13B141C06F43E7534EFFDB2BDB4C0BB9AA6B71">\n'
				//+'<param name="ProductCaption" value="risesoft">\n'
				//+'<param name="ProductKey" value="98E2DE4FC7794888A15741C8E91FABCC1BC8827E">\n'
				//+'<param name="ProductCaption" value="risesoft">\n'
				//+'<param name="MaxUploadSize" value="10000000000">\n'
				+'<param name="IsUseUTF8URL" value="-1">\n'
				+'<param name="IsUseUTF8Data" value="-1">\n'
				+'<param name="ViewType" value="3">\n'
				+'<param name="IsPermitAddDelFiles" value="-1">\n'
				+'<param name="DelFileField" value="DELATTNAME">\n'
				+'<param name="IsConfirmSaveModified" value="0">\n'
				+'<param name="ScannerPicFileName" value="NTKO扫描文件"><!--默认扫描仪添加文件的文件名前缀 --> '
				+'<param name="ScannerPicType" value="1">   <!--默认扫描仪添加文件的类型  0,TIFF, 1:JPEG,  2:GIF, 3:BMP --> '
				+'<param name="IsAllowSelScannerPicType" value="-1">  <!--是否允许选择文件类型 --> '
				+'<param name="IsCloseScannerUI" value="-1"> <!--扫描完毕之后是否关闭扫描仪--> '
				+'<param name="IsSelScannerIfOnlyOne" value="0"> <!--如果只有一个扫描仪，是否提示选择。--> '
				+'<SPAN STYLE="color:red">不能装载附件管理控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>\n'
			 +'</object>\n'
			 
			 document.all(divID).innerHTML =fileOjectHTML
			 filebox = document.all(fileboxObject.FILEBOXNAME);
			 //filebox = document.getElementById(boxID);
			 filebox.FILEBOXNAME=fileboxObject.FILEBOXNAME;
			 filebox.APPNAME=fileboxObject.APPNAME;
			 filebox.FILEROOT=fileboxObject.FILEROOT;
			 filebox.SAVEMODE=fileboxObject.SAVEMODE;
			 filebox.MAJORVERSION=fileboxObject.MAJORVERSION;
			 filebox.MAJORVERSIONNAME=fileboxObject.MAJORVERSIONNAME;
			 filebox.KEEPMINIMALVERSION=fileboxObject.KEEPMINIMALVERSION;
			 filebox.FILTERS=fileboxObject.FILTERS;
			 filebox.ISFILTEREXTENDS=fileboxObject.ISFILTEREXTENDS;
			 filebox.STREAMHANDLES=fileboxObject.STREAMHANDLES;
			 filebox.ISHANDLEEXTENDS=fileboxObject.ISHANDLEEXTENDS;
			 filebox.APPINSTGUID=fileboxObject.APPINSTGUID;
			 filebox.CONTEXT=fileboxObject.CONTEXT;
			//tag的配置 
			var config="appname="+filebox.APPNAME;//使用applicationname，没有用统一的appName,因为与控件有冲突。
			config+="&fileboxname="+filebox.FILEBOXNAME;
			config+="&fileroot="+filebox.FILEROOT;
			config+="&savemode="+filebox.SAVEMODE;
			config+="&majorversion="+filebox.MAJORVERSION;
			config+="&majorversionname="+filebox.MAJORVERSIONNAME;
			config+="&isminimalversion="+filebox.ISMINIMALVERSION;
			config+="&filehandles="+filebox.FILEHANDLES;
			config+="&streamhandles="+filebox.STREAMHANDLES;
			config+="&ishandleextends="+filebox.ISHANDLEEXTENDS;
			config+="&keepminimalversion="+filebox.KEEPMINIMALVERSION;
			config+="&appInstGUID="+filebox.APPINSTGUID;
			//app传入context的内容
			var context="context="+filebox.CONTEXT;
			
			filebox.config=config;
			//文件上传或修改的url
			
       
            filebox.url=webContextPath+"/static/js/ntko/fileboxAction.jsp?"+config+"&"+context;      


			//rs = document.all(rsID).recordset;
			//不再使用rs的方式，不支持数据的变化

			var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
			xmlDoc.loadXML(document.getElementById(rsID).innerHTML);
			
			var attachFile;
			var guid;
			var fileName;
			var fileExtName;
			var fileSize;
			var fileModifiedTimeStr;
			var isAllowEdit;
			var fileVersion;
			var fileCreator;
			var fileCreateDept;
			var fileCreatedTimeStr;
			var url;
			var item=xmlDoc.getElementsByTagName("row");
			//这种方式可以直接访问XX级的数据
			//guid = node.selectSingleNode("//row//guid").text;
	        for(var i=0;i<item.length;i++){
	        	guid=item[i].childNodes[0].text;
	        	fileName=item[i].childNodes[1].text;
	        	fileExtName=item[i].childNodes[2].text;
	        	fileSize=item[i].childNodes[3].text;
	        	fileModifiedTimeStr=item[i].childNodes[4].text;
	        	isAllowEdit=item[i].childNodes[5].text;
	        	fileVersion=item[i].childNodes[6].text;
	        	fileCreator=item[i].childNodes[7].text;
	        	fileCreateDept=item[i].childNodes[8].text;
	        	fileCreatedTimeStr=item[i].childNodes[9].text;
	        	//guid=item(i).getElementsByTagName("guid").item(0).text;
	        	//fileName= item(i).getElementsByTagName("fileName").item(0).text;
	        	//fileExtName= item(i).getElementsByTagName("fileExtName").item(0).text;
				//fileSize= item(i).getElementsByTagName("fileSize").item(0).text;
				//fileModifiedTimeStr= item(i).getElementsByTagName("fileModifiedTimeStr").item(0).text;
				//isAllowEdit= item(i).getElementsByTagName("isAllowEdit").item(0).text;
				//fileVersion= item(i).getElementsByTagName("fileVersion").item(0).text;
				//fileCreator= item(i).getElementsByTagName("fileCreator").item(0).text;
				//fileCreateDept= item(i).getElementsByTagName("fileCreateDept").item(0).text;
				//fileCreatedTimeStr= item(i).getElementsByTagName("fileCreatedTimeStr").item(0).text;
				//url=webContextPath+"/components/risefile/default/fileboxAction.jsp?action=download"
				//				+"&fileName="+fileName+"&fileGUID="+guid+"&"+config;
				url=webContextPath+"/risefileViewData1/view?fileName="+fileName+"&fileGUID="+guid+"&appname="+filebox.APPNAME;
				attachFile=filebox.AddServerFile(url+"&isArchManager="+isArchManager,fileName,parseInt(fileSize),fileCreatedTimeStr,isAllowEdit);
				
				attachFile.SetCustomData(0,fileVersion);
				attachFile.SetCustomData(1,fileCreator);
				attachFile.SetCustomData(2,fileCreateDept);
				attachFile.SetCustomData(3,fileCreatedTimeStr);
				attachFile.SetCustomData(4,fileExtName);
				filebox.SetColumnVisible(10,false);
	        }
	        //根据页面编码设置控件属性
			var useUTF8 = (document.charset == "utf-8");
			filebox.IsUseUTF8Data = useUTF8; 
			//filebox.ViewType = fileboxObject.VIEWTYPE;
			filebox.ViewType = 2;
			filebox.Statusbar=false;
			filebox.MaxUploadSize = fileboxObject.MAXUPLOADSIZE;//一次上传不能超过xxM
			filebox.IsConfirmSaveModified="false";
			//filebox1.DefaultAddFileTypes="*.doc;*.gif;";
			filebox.SetColumnVisible(1,false);  
			filebox.SetColumnVisible(2,true); 
			filebox.SetColumnVisible(3,false);  
			filebox.SetColumnVisible(4,false);  
			filebox.SetColumnVisible(5,false); 
		
			filebox.SetColumnVisible(6,true);
			filebox.SetColumnVisible(7,true);
			filebox.SetColumnVisible(8,true);
			filebox.SetColumnVisible(9,true);
			filebox.SetCustomColumnCaption(0,"版本");
			filebox.SetCustomColumnCaption(1,"上传人");
			filebox.SetCustomColumnCaption(2,"部门");
			filebox.SetCustomColumnCaption(3,"时间");
			filebox.IsPermitAddDelFiles=fileboxObject.ISPERMITADDDELFILES;
			if(fileboxObject.ISPERMITADDDELFILES==0){
				//filebox.Toolbar=false;
				filebox.IsReadOnlyMode = true;
			}
			hasInit = true;
	}
	
	function deleteRiseFile(filebox,attachfile){
		if(isArchManager == "true"){
		var str = attachfile.fileURL;
		str = str.substring(str.indexOf("fileGUID={")+9,str.indexOf("}&")+1);
		var url=filebox.url+"&action=delete"+"&fileName="+attachfile.FileName+"&fileGuid="+str+"&isArchManager="+isArchManager;
		//commitFileBox(filebox,url);
		var xmlhttp = new ActiveXObject('Microsoft.XMLHTTP'); 
		var post="";
        xmlhttp.open('POST',url,false);//使用POST方法打开服务器连接 
        xmlhttp.setrequestheader('content-length',post.length);
        xmlhttp.setrequestheader('content-type','application/x-www-form-urlencoded');
        xmlhttp.send(post);
        var res = xmlhttp.responseText;
        }else{
        	alert("您没有操作权限。");
        	return ;
        }
	}

	function addRiseFile(filebox)
	{
		if(isArchManager == "true"){
		var url=filebox.url+"&action=save";
		commitFileBox(filebox,url);
		}else{
        	alert("您没有操作权限。");
        	return;
        }	
	}
	function updateRiseFiles(filebox){
		if(!filebox.IsNeedSaveToServer){
			alert("您没有更改任何文件！");      
			return;
		}
		if(isArchManager == "true"){
			var url=filebox.url+"&action=update&meetSort="+meetSort+"&isArchManager="+isArchManager;
			commitFileBox(filebox,url);
		}else{
        	alert("您没有操作权限。");
        	return;
        }	
	}
	function isReadOnly(fileboxID){
			var filebox=document.all(fileboxID);
			filebox.IsReadOnlyMode = true;
	}
	function updateRiseFilesByID(fileboxID){
			var filebox=document.all(fileboxID);
			updateRiseFiles(filebox);
	}
	function fileboxSelectChange(filebox,count){
		filebox.SELECTCOUNT=count;
	}
	function doFileCommand(cmdType,IsServerFile,FilePathOrURL ,AttachFile,filebox){
		var commandstr = "";
		switch(cmdType)
		{
		case 0:
			commandstr = "ntkoCmdEdit";
			break;
		case 1:
			commandstr = "ntkoCmdOpen";
			break;
		case 2:
			commandstr = "ntkoCmdPrint";
			break;
		case 3:
			commandstr = "ntkoCmdDelete";
			deleteRiseFile(filebox,AttachFile);
			break;
		case 4:
			commandstr = "ntkoCmdSave";
			break;
		case 5:
			commandstr = "ntkoCmdSaveAll";
			break;
		case 6:
			commandstr = "ntkoCmdOpenFolder";
			break;
		case 7:
			commandstr = "ntkoCmdViewProp";
			break;		
		}
	}
	function afterCommitFileBox(retStr,ErrCode){
		if(ErrCode == 0)
		{			
			var fileboxID=retStr.substring(0,retStr.indexOf(";"));
			fileboxID=fileboxID.trim();
			var msg=retStr.substring(retStr.indexOf(";")+1,retStr.length);

			var fileboxObjectName=fileboxID+"Object";
			var rsID=fileboxID+"XML";
			
			if(msg.indexOf("ok:")==0){
				alert(msg);
			}else if(msg.indexOf("error:")==0){
				showError(fileboxID,msg);
			}else{
				document.all(rsID).innerHTML=msg;
				if(hasInit){
					eval("refreshFileBox("+fileboxObjectName+")");
				}else{
					eval("initFileBox("+fileboxObjectName+")");
					hasInit = true;
				}
			}
			//alert("保存成功.");				
			//window.location.reload(true);
		}
		else if(ErrCode == 10) //用户取消
		{
			alert(retStr);
		}
		else if(ErrCode == 11) //超时终止
		{
			alert(retStr);
		}
		else
		{
			newwin = window.open("","_blank","left=200,top=200,width=400,height=200,status=0,toolbar=0,menubar=0,location=0,scrollbars=0,resizable=0",false);
			newdoc = newwin.document;
			newdoc.open();
			newdoc.write("<center><hr>"+retStr+"<hr><input type=button VALUE='关闭窗口' onclick='window.close()'></center>");
			//newdoc.close();	
		}
	}
	
	function showVersion(fileboxID){
		var filebox=document.all(fileboxID);
		eval("var fileboxObject="+fileboxID+"Object");
		if(filebox.IsNeedSaveToServer){//如果需要更新，更新
			var url=filebox.url+"&action=update"+"&isArchManager="+isArchManager;
			if(fileboxObject.ALLVERSION){
			//alert("1-1");
				url+="&isAllVersion=false";
				fileboxObject.ALLVERSION=false;
				fileboxObject.ISPERMITADDDELFILES=-1;
			}else{
			//alert("1-2");
				url+="&isAllVersion=true";
				fileboxObject.ALLVERSION=true;
				fileboxObject.ISPERMITADDDELFILES=0;
				url+="&isAllVersion=false";
				fileboxObject.ALLVERSION=false;
				fileboxObject.ISPERMITADDDELFILES=-1;
			}
			commitFileBox(filebox,url);	
		}else{//不需要更新，直接显示版本
			var url=filebox.url+"&action=showVersion"+"&isArchManager="+isArchManager;
			
			if(fileboxObject.ALLVERSION){
				//url+="&isAllVersion=false";
				//fileboxObject.ALLVERSION=false;
				//fileboxObject.ISPERMITADDDELFILES=-1;
			}else{
				//url+="&isAllVersion=true";
				//fileboxObject.ALLVERSION=true;
				//fileboxObject.ISPERMITADDDELFILES=0;
			}
			
			var xmlhttp = new ActiveXObject('Microsoft.XMLHTTP'); 
			var post="";
	        xmlhttp.open('POST',url,false);//使用POST方法打开服务器连接 
	        xmlhttp.setrequestheader('content-length',post.length);
	        xmlhttp.setrequestheader('content-type','application/x-www-form-urlencoded');
	        xmlhttp.send(post);
	        var res = xmlhttp.responseText;
	        res=fileboxID+";"+res;
	        afterCommitFileBox(res,0);
		}
		
		
	}

	function commitFileBox(filebox,url){
			
			filebox.BeginSaveToURL(
				url,
				"RiseFileComponent",	//文件输入域名称,可任选,不与其他<input type=file name=..>的name部分重复即可
				"", //可选的其他自定义数据－值对，以&分隔。如：myname=tanger&hisname=tom,一般为空
				"", //控件的智能提交功能可以允许同时提交选定的表单的所有数据.此处可使用id或者序号
				0);
	}

	function showError(fileboxid,error){
		var errorHTML="<div align='center' style='font-size: 11px;color:red'><img src='" + webContextPath 
						+ "/static/js/ntko/image/error.gif'  border=0 alt='文件框错误！请与系统管理员联系！'><br>"
						+"文件框报错："+error+"<div>"
		document.all(fileboxid+"DIV").innerHTML =errorHTML;
	}
	
	function addFileCmd(fileboxID){
		var filebox=document.all(fileboxID);
		filebox.AddLocalFile("",false,false);
	}
	
	function refreshFileBox(fileboxObject){
		var rsID=fileboxObject.FILEBOXNAME+"XML";
		var xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.loadXML(document.getElementById(rsID).innerHTML);
		//alert(rsID);
		var filebox = document.all(fileboxObject.FILEBOXNAME);
		filebox.Reset();
		var attachFile;
		var guid;
		var fileName;
		var fileExtName;
		var fileSize;
		var fileModifiedTimeStr;
		var isAllowEdit;
		var fileVersion;
		var fileCreator;
		var fileCreateDept;
		var fileCreatedTimeStr;
		var url;
		var item=xmlDoc.getElementsByTagName("row");
		
		//这种方式可以直接访问XX级的数据
		//guid = node.selectSingleNode("//row//guid").text;
        for(var i=0;i<item.length;i++){
        	guid=item[i].childNodes[0].text;
        	//alert(guid);
        	fileName=item[i].childNodes[1].text;
        	fileExtName=item[i].childNodes[2].text;
        	fileSize=item[i].childNodes[3].text;
        	fileModifiedTimeStr=item[i].childNodes[4].text;
        	isAllowEdit=item[i].childNodes[5].text;
        	fileVersion=item[i].childNodes[6].text;
        	fileCreator=item[i].childNodes[7].text;
        	fileCreateDept=item[i].childNodes[8].text;
        	fileCreatedTimeStr=item[i].childNodes[9].text;

        	/*guid=item(i).getElementsByTagName("guid").item(0).text;
        	fileName= item(i).getElementsByTagName("fileName").item(0).text;
        	fileExtName= item(i).getElementsByTagName("fileExtName").item(0).text;
			fileSize= item(i).getElementsByTagName("fileSize").item(0).text;
			fileModifiedTimeStr= item(i).getElementsByTagName("fileModifiedTimeStr").item(0).text;
			isAllowEdit= item(i).getElementsByTagName("isAllowEdit").item(0).text;
			fileVersion= item(i).getElementsByTagName("fileVersion").item(0).text;
			fileCreator= item(i).getElementsByTagName("fileCreator").item(0).text;
			fileCreateDept= item(i).getElementsByTagName("fileCreateDept").item(0).text;
			fileCreatedTimeStr= item(i).getElementsByTagName("fileCreatedTimeStr").item(0).text;*/
			//url=webContextPath+"/components/risefile/default/fileboxAction.jsp?action=download"
			//				+"&fileName="+fileName+"&fileGUID="+guid+"&"+config;
			url=webContextPath+"/risefileViewData1/view?fileName="+fileName+"&fileGUID="+guid+"&appname="+filebox.APPNAME;
					
			attachFile=filebox.AddServerFile(url,fileName,parseInt(fileSize),fileCreatedTimeStr,isAllowEdit);
			attachFile.SetCustomData(0,fileVersion);
			attachFile.SetCustomData(1,fileCreator);
			attachFile.SetCustomData(2,fileCreateDept);
			attachFile.SetCustomData(3,fileCreatedTimeStr);
			attachFile.SetCustomData(4,fileExtName);
			filebox.SetColumnVisible(10,false);
        }
        $("#TANGER_OCXXML").hide();
       
	}