function initDocument() {
			var isIE=checkIE();
			if(!isIE){
				alert("当前正文控件只支持IE浏览器,请使用IE浏览器打开正文！");
				return false;
			}
			if(openWordOrPDF=="openPDF"){
				openPDF();
			}else{
				resize();
				//辅助选项
				AddMyMenuItems();
				//允许或禁止显示修订工具栏和工具菜单（保护修订,用户不能更改当前修订状态）
				//enableReviewBar(true);
				var templateurl;
				if(filePath!=""&&filePath!=null){
					alert("17");
					templateurl = filePath;
				}else{
					if(printType=="shouLiDan"){
						templateurl = slTemplate;
					}
					if(printType=="banJieDan"){
						alert(printType);
						templateurl = bjTemplate;
					}
				}
				alert(templateurl);
				var reg=/^[a-zA-Z]:\//;
				alert(reg.test(templateurl));
				if (!reg.test(templateurl)) {
					NTKO_OBJ.OpenFromURL(templateurl,false);
				} else {
					NTKO_OBJ.OpenLocalFile(templateurl);
				}
					
				NTKO_OBJ.IsShowToolMenu = true
				var vw = NTKO_OBJ.ActiveDocument.ActiveWindow.View;
				var doc = document.all.item("riseOffice").ActiveDocument;
				var app = doc.Application;
				var cmdbars = app.CommandBars;
				doc.TrackRevisions = true;
				cmdbars("Reviewing").Enabled = true;
				cmdbars("Reviewing").Visible = true;
				cmdbars(40).Enabled = true;
				cmdbars(40).Visible = true;
				vw.RevisionsView = 0;
				vw.ShowRevisionsAndComments = false;
				setFont();
				showToolbars(false);
			}
		}
		
		//判断是不是IE
		function checkIE(){
            if (!!window.ActiveXObject || "ActiveXObject" in window){
                return true;
            }else{
                return false;
            }
		}
		
		function resize() {
			var winWidth = $(window).width();
			var winHeight = $(window).height();
			$(NTKO_OBJ).css({
				"width" : (winWidth)-17 + "px",
				"height" : (winHeight) + "px"
			});
		}
		
		function AddMyMenuItems() {
			//return;
			try {
				NTKO_OBJ.AddCustomMenuItem('接受所有修订', false, false, 1);
				NTKO_OBJ.AddCustomMenuItem('拒绝所有修订', false, false, 2);
				NTKO_OBJ.AddCustomMenuItem('');
				NTKO_OBJ.AddCustomMenuItem('手写签名', false, false, 3);
				NTKO_OBJ.AddCustomMenuItem('电子印章', false, false, 5);
				NTKO_OBJ.AddCustomMenuItem('');
				NTKO_OBJ.AddCustomMenuItem('签章验证', false, false, 4);
			} catch (err) {
				alert("不能创建新对象：" + err.number + ":" + err.description);
			} finally {
			}
		}
		function showToolbars(da) {
			var s=da.value;
			if(s=='显示工具栏')	{
				document.all.item("riseOffice").Toolbars=true;
				da.value='隐藏工具栏';	
			}else{
			 	document.all.item("riseOffice").Toolbars=false;
			 	da.value='显示工具栏';	
			 }
		}