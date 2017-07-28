(function(c) {
	var params = null; // 全局参数对象,这个对象在函数被调用时初始化,包含用户定义参数与默认参数
	var globalUploader = null; // 全局uploader对象
	var isLoadingExitFiles = false; // 正在处理已经存在的文件的标记
	var exsitUploadedFileCount = 0; // 初始的文件个数,用于限制上传的文件个数

	// 这个参数将来可能要参数化,因为可能需要管理员的联系方式,姓名等
	var errorContactAdmin = "上传附件出错! 请联系管理员! \n 错误详细信息为:\n"; // 出错时弹出的对话框中的文字,因为是给最终用户看,所以要尽量友好一些

	/**
	 * 日志函数,用于调试
	 * 
	 * @param message
	 *            要显示的信息内容
	 * @return void
	 */
	function log(message) {
		if ($.browser.msie) {
			alert(message);
		} else {
			console.log(message);
		}
	}

	/**
	 * 显示错误信息
	 * 
	 * @param message
	 *            要显示的信息内容
	 * @return void
	 */
	function showErrorMessage(message) {
		alert(message);
	}

	/**
	 * 通知长文件名获取一个短文件名,短文件名的长度最大长度为参数中的max_file_name_length
	 * 
	 * @param fileName
	 *            文件名
	 * @return 短文件名
	 */
	function getShortFileName(fileName) {
		if (fileName == null || fileName == ""
				|| fileName.length < params.max_file_name_length) {
			return fileName;
		}
		var returnFileName = null;
		var completeFileName = fileName;
		// 有"."
		if (completeFileName.indexOf(".") != -1) {
			var suffix = completeFileName.substring(completeFileName
					.lastIndexOf("."), completeFileName.length);
			returnFileName = completeFileName
					.substring(0, (params.max_file_name_length
							- params.file_name_ellipsis.length - suffix.length))
					+ params.file_name_ellipsis + suffix;
		} else {
			returnFileName = completeFileName.substring(0,
					params.max_file_name_length
							- params.file_name_ellipsis.length)
					+ params.file_name_ellipsis;
		}
		return returnFileName;
	}

	/**
	 * 通过文件名获取文件对应的图片
	 * 
	 * @param fileName
	 *            文件名
	 * @return 该文件对应的图片
	 */
	function getIconImage(fileName) {
		if (fileName == null || fileName == "") {
			return "";
		}

		if (fileName.indexOf(".") != -1) {
			var suffix = fileName.substring(fileName.lastIndexOf("."),
					fileName.length);
			if ("." == suffix) {
				return params.file_icons_folder + file_icons.default_;
			} else {
				suffix = suffix.substring(1, suffix.length); // 去掉.
				suffix = suffix.toLowerCase();
				var image = null;
				try {
					eval("if(params.file_icons." + suffix + ") {"
							+ "image = params.file_icons." + suffix + ";" + "}");
				} catch (err) {
					// 出异常说明没有配置此缀名的图片,则使用默认的图片
				} finally {
					if (image == null) {
						image = params.file_icons.default_;
					}
				}

				return params.file_icons_folder + image;
			}
		} else {
			return params.file_icons_folder + params.file_icons.default_;
		}
	}

	/**
	 * 通过文件数组 更新 设置用户的html元素的值,形式为 "id1,id2,id3,idN..."
	 * 
	 * @param fileArray
	 *            要处理的文件数组
	 * @param htmlElementId
	 *            存放文件ID的HTML元素ID
	 * @return void
	 */
	function updateByArray(fileArray, htmlElementId) {
		if (fileArray == null || htmlElementId == null || htmlElementId == "") {
			return;
		}

		try {
			var uploadAttachmentGUIDs = "";
			for ( var i = 0; i < fileArray.length; i++) {
				if (fileArray[i] == undefined || fileArray[i] == null) {
					continue;
				}
				uploadAttachmentGUIDs += fileArray[i].guid;
				if (i != fileArray.length - 1) {
					uploadAttachmentGUIDs += ",";
				}
			}
			$("#" + htmlElementId).val(uploadAttachmentGUIDs);
		} catch (ex) {
			var errorMessage = errorContactAdmin;
			errorMessage += "上传组件更新存放 附件ID 的HTML元素时出错\n参数[1]fileArray["
					+ fileArray + "]\n参数[2]htmlElementId:[" + htmlElementId
					+ "]\n异常信息为:[" + ex + "]";
			showErrorMessage(errorMessage);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param file
	 *            file对象
	 * @param callback
	 *            回调函数
	 * @return void
	 */
	function deleteFile(file, callback) {
		if (file == null) {
			return;
		}
		var ajaxParams = {
			fileName : file.name,
			fileGUID : file.guid,
			on_delete_service_name : params.on_delete_service_name,
			on_delete_businessPrimaryKey : params.on_delete_businessPrimaryKey
		};
		$.post(params.delete_url, ajaxParams,
				function(data, textStatus, jqXHR) {
					if (textStatus == "success") {
						if (data.err) {
							showErrorMessage(data.err);
							return;
						}
						callback();
					}
				}, "json");
	}

	/**
	 * 通过json数据填充file对象
	 * 
	 * @param file
	 *            file对象 ,此对象为plupload.full.js中定义的
	 * @param jsonData
	 *            json数据对象
	 * @return void
	 */
	function fillFileDataByJsonData(file, jsonData) {
		if (file == null || jsonData == null) {
			return;
		}

		try {
			// file.url = params.context + "/" + jsonData.attachmentUrl; //
			// 文件url
			// file.url = params.context +
			// "/ecfileUploadAction.do?method=download&id=" +
			// jsonData.attachmentGuid; // 文件url
			// file.guid = jsonData.attachmentGuid; // 文件guid
			// file.extName = jsonData.attachmentExtname; // 文件扩展名
			// file.uploaderName = jsonData.employeeName; // 上传者姓名
			// file.uploaderGuid = jsonData.employeeGuid; // 上传者guid
			// /* 服务器端返回的为毫秒 */
			// uploadDate = new Date();
			// uploadDate.setTime(jsonData.createDate);
			// file.uploadDate = uploadDate;
			file = $.extend(file, jsonData);
		} catch (ex) {
			var errorMessage = errorContactAdmin;
			errorMessage += "上传附件组件调用fillFileDataByJsonData时出错\n参数[1]file:["
					+ file + "]\n参数[2]jsonData:[" + jsonData + "]\n异常信息为:["
					+ ex + "]";
			showErrorMessage(errorMessage);
		}
	}

	/**
	 * 通过一个json数据获取一个文件对象,此对象plupload.full.js中的File对象.
	 * 
	 * @param jsonData
	 *            json数据对象
	 * @return 处理完成的 File 对象
	 */
	function getFileByJsonData(jsonData) {
		if (jsonData == null) {
			return;
		}

		try {
			var newFile = new plupload.File(plupload.guid(),
					jsonData.attachmentName, parseInt(jsonData.attachmentSize,
							10));
			fillFileDataByJsonData(newFile, jsonData);
			// newFile.percent = 100; // 上传完成百分比
			newFile.status = plupload.DONE; // 状态
			// newFile.url = params.context +
			// "/ecfileUploadAction.do?method=download&id=" +
			// jsonData.attachmentGuid; // 文件url
			// newFile.guid = jsonData.attachmentGuid; // 文件guid
			// newFile.extName = jsonData.attachmentExtname; // 文件扩展名
			// newFile.loaded = jsonData.attachmentSize; // 已经上传了的大小
			// newFile.uploaderName = jsonData.employeeName; // 上传者姓名
			// newFile.uploaderGuid = jsonData.employeeGuid; // 上传者guid
			//	
			// uploadDate = new Date();
			// // uploadDate = date2str(new Date(),"yyyy-MM-dd hh:mm:ss");
			// uploadDate.setTime(jsonData.createDate); // 服务器端返回的为毫秒
			// newFile.uploadDate = uploadDate; // 上传日期
			// newFile.isUploadError = false;

			return newFile;
		} catch (ex) {
			var errorMessage = errorContactAdmin;
			errorMessage += "上传附件组件调用getFileByJsonData时出错\n参数[1]jsonData:["
					+ jsonData + "]\n异常信息为:[" + ex + "]";
			showErrorMessage(errorMessage);
		}
	}
	/**
	 * 格式化显示日期时间
	 * 
	 * @param x
	 *            待显示的日期时间，例如new Date() ,如果此参数为代,则使用当前时间 [new Date()]
	 * @param y
	 *            需要显示的格式，例如"yyyy-MM-dd hh:mm:ss" ,如果此参数为空,则使用"yyyy-MM-dd
	 *            hh:mm:ss"
	 * @return 根据日期与格式字符串生成的字符串
	 */
	function date2str(x, y) {
		if (x == null) {
			x = new Date();
		}

		if (y == null || y == "") {
			y = "yyyy-MM-dd hh:mm:ss";
		}

		try {
			var z = {
				M : x.getMonth() + 1,
				d : x.getDate(),
				h : x.getHours(),
				m : x.getMinutes(),
				s : x.getSeconds()
			};
			y = y.replace(/(M+|d+|h+|m+|s+)/g, function(v) {
				return ((v.length > 1 ? "0" : "") + eval('z.' + v.slice(-1)))
						.slice(-2)
			});
			return y.replace(/(y+)/g, function(v) {
				return x.getFullYear().toString().slice(-v.length)
			});
		} catch (ex) {
			var errorMessage = errorContactAdmin;
			errorMessage += "上传附件组件调用date2str时出错\n参数[1]x:[" + x + "]\n参数[2]y:["
					+ y + "]\n异常信息为:[" + ex + "]";
			showErrorMessage(errorMessage);
		}
	}

	/**
	 * 通过文件数组初始化uploader,这个方法用来加载已经存在的文件
	 * 
	 * @param uploader
	 *            uploader对象
	 * @param fileArray
	 *            文件数组,这个数据必须是 new plupload.File 得来
	 * @return void
	 */
	function initUploaderByArray(uploader, fileArray) {
		if (uploader == null || fileArray == null || fileArray.length == 0) {
			return;
		}
		try {
			var totalSize = 0;

			for ( var i = 0; i < fileArray.length; i++) {
				totalSize += fileArray[i].size;
				uploader.files.push(fileArray[i]);
			}

			uploader.queued = 0;

			uploader.total.uploaded = fileArray.length;
			uploader.total.failed = 0;
			uploader.total.percent = 100;

			exsitUploadedFileCount = fileArray.length; // 已经存在的文件的个数

			uploader.total.loaded = totalSize;
			uploader.total.size = totalSize;
		} catch (ex) {
			var errorMessage = errorContactAdmin;
			errorMessage += "上传附件组件调用initUploaderByArray时出错\n参数[1]uploader:["
					+ uploader + "]\n参数[2]fileArray:[" + fileArray
					+ "]\n异常信息为:[" + ex + "]";
			showErrorMessage(errorMessage);
		}
	}

	/**
	 * 处理已经存在的附件,此函数会使用isLoadingExitFiles变量
	 * 
	 * @param exitstFiles
	 *            能够处理的格式有如下几种:
	 *            1.existFiles参数的类型为string,且形式为"[id,id,id,idn...]"或"id1,id2,id3,idn..."
	 *            2.existFiles参数类型为object,也就是数组[],其中的元素为string类型的id
	 *            3.existFiles参数类型为object,也是数组[],但其中的元素为file对象,
	 *            与第二种数组的区别是,这个数组中的对象都包含"guid"属性
	 * @param uploader
	 *            插件的uploader对象
	 * @param callback
	 *            回调函数
	 * @return void
	 */
	function processExistFiles(existFiles, existFilesMode, uploader, callback) {
		if (existFiles == null || existFiles == undefined || existFiles == ""
				|| uploader == null) {
			return;
		}

		try {
			isLoadingExitFiles = true; // 标记正在处理已经存在的附件

			var files = [];

			var typeOfExitsFiles = typeof (existFiles);

			if (typeOfExitsFiles == "string") {
				// [id1,id2,id3,id4,idN...]
				if (existFiles.charAt(0) == "["
						&& existFiles.charAt(existFiles.length - 1) == "]") {
					var existFilesStr = existFiles.substring(1,
							existFiles.length - 1);
					files = existFilesStr.split(",");
				} else {
					// id1,id2,id3,id4,idN..
					files = existFiles.split(",");
				}
			} else if (typeOfExitsFiles == "object") {
				if (existFilesMode == "files") {
					var loadedFileArray = new Array();
					var newFile = null;
					for ( var i = 0; i < existFiles.length; i++) {
						newFile = $.extend(new plupload.File(plupload.guid(),
								"", ""), existFiles[i]);
						loadedFileArray.push(newFile);
						newFile.status = plupload.DONE;
					}
					initUploaderByArray(uploader, loadedFileArray);

					updateByArray(uploader.files, params.html_element_id); // 更新附件隐藏表单域中的值
					setTimeout(function() {
						callback()
					}, 300); // 调用回调函数

					return;
				}

				files = existFiles;
			}

			if (files.length == 0) {
				return;
			}

			var filesStr = "";
			for ( var i = 0; i < files.length; i++) {
				filesStr += files[i];
				if (i < files.length - 1) {
					filesStr += ",";
				}
			}
			var ajaxParams = {
				method : "getAttachmentInfo",
				attachmentPermissionService : params.permission_service_name,
				attachmentGUIDs : filesStr,
				ssss : Math.random() * 200000
			};
			$.post(params.context + '/app/attachment/getAttachmentInfo', ajaxParams,
					function(data, textStatus, jqXHR) {
						if (textStatus == "success") {
							var newFile = {};
							var uploadDate = null;
							var loadedFileArray = new Array();
							// alert(data);
					if (data == null) {
						return;
					}
					for ( var i = 0; i < data.length; i++) {
						newFile = getFileByJsonData(data[i]);
						loadedFileArray.push(newFile);
					}

					initUploaderByArray(uploader, loadedFileArray);
				} else if (textStatus == "error") {
					alert("error");
				} else if (textStatus == "complete") {
					alert("complete");
				}
				updateByArray(uploader.files, params.html_element_id); // 更新附件隐藏表单域中的值
					isLoadingExitFiles = false;// 把处理已经存在的文件的标记设置为false
					setTimeout(function() {
						callback()
					}, 300); // 调用回调函数
				}, "json");
		} catch (ex) {
			var errorMessage = errorContactAdmin;
			errorMessage += "上传附件组件调用processExistFiles时出错\n参数[1]existFiles:["
					+ existFiles + "]\n参数[2]uploader:[" + uploader
					+ "]\n参数[3]callback:[" + callback + "]\n异常信息为:[" + ex
					+ "],描述为:[" + ex.message + "]";
			showErrorMessage(errorMessage);
		}
	}
	/**
	 * 设置提示信息
	 * 
	 * @param message
	 *            要提示的信息
	 * @return void
	 */
	function setTipMessage(message) {
		$(".plupload_header_title").html(message);
	}

	/**
	 * 显示提示信息
	 * 
	 * @return void
	 */
	function showTipMessage() {
		$(".plupload_header").slideDown();
	}

	/**
	 * 隐藏提示信息 return void
	 */
	function hideTipMessage() {
		if ($.browser.msie) {
			$(".plupload_header").fadeOut(); // IE的slideUp效果比较差,这里使用fadeOut效果
		} else {
			$(".plupload_header").slideUp();
		}
	}

	var d = {};

	function a(e) {
		return plupload.translate(e) || e
	}

	function b(f, e) {
		e.contents().each(function(g, h) {
			h = c(h);
			if (!h.is(".plupload")) {
				h.remove()
			}
		});
		var prependStr = '<div class="plupload_wrapper plupload_scroll"><div id="' + f + '_container" class="plupload_container"><div class="plupload">';

		// if(!params.read_only) {
		// prependStr += '<div class="plupload_header_text">'
		// + a(params.default_header_text)
		// + '</div>';
		// } else {
		// prependStr += '</div><div class="plupload_header_text">'
		// + a(params.read_only_header_text)
		// + '</div>';
		// }
		prependStr += '<div class="plupload_header"><div class="plupload_header_content">';
		prependStr += '<div class="plupload_header_title">';
		// if(params.read_only){
		// prependStr += a("EC上传组件[只读模式]");
		// }else{
		// prependStr += a("EC上传组件[编辑模式]");
		// }
		prependStr += '</div></div></div>';

		prependStr += '<div class="plupload_content"><div class="plupload_filelist_header">'
				+ '<div class="plupload_file_name">'
				+ a("文件名")
				+ '</div>'
				+ '<div class="plupload_file_action">&nbsp;</div>';

		if (!params.read_only) {
			prependStr += '<div class="plupload_file_status"><span>';
			prependStr += a("状态");
			prependStr += '</span></div>';
		}

		prependStr += '<div class="plupload_file_uploader_name">' + a("上传人")
				+ '</div>'

				+ '<div class="plupload_file_uploader_date">'
				+ a("&nbsp;&nbsp;&nbsp;上传时间&nbsp;&nbsp;&nbsp;") + '</div>'

				+ '<div  class="plupload_file_size" >' + a("文件大小") + '</div>'

				+ '<div class="plupload_clearer">&nbsp;</div></div><ul id="'
				+ f + '_filelist" class="plupload_filelist"></ul>';
		// if (!params.read_only) {
		if ($.browser.msie) {
			prependStr += '<div class="plupload_filelist_footer" style="height:35px;">';
		} else {
			prependStr += '<div class="plupload_filelist_footer">';
		}
		prependStr += '<div class="plupload_file_name">';
		prependStr += '<div class="plupload_buttons"><a href="#" class="plupload_button plupload_add"';
		if (params.read_only) {
			prependStr += ' style="display:none ;" ';
		}
		prependStr += '>' + a("添加文件") + '</a>';
		if (!params.auto_upload) {
			prependStr += '<a href="#" class="plupload_button plupload_start">';
			prependStr += a("开始上传");
			prependStr += '</a>';
		}
		prependStr += '</div>';
		prependStr += '</div><div style="float:left"><span class="plupload_upload_status"></div></span><div class="plupload_file_action" style="height:1px;overflow:height;width:1px;"></div>';
		if (!params.read_only) {
			prependStr += '<div class="plupload_file_status"><span class="plupload_total_status">0%</span></div>';
		}
		prependStr += '<div class="plupload_file_size"><span class="plupload_total_file_size">0 b</span></div><div class="plupload_progress"><div class="plupload_progress_container"><div class="plupload_progress_bar"></div></div></div></div></div></div></div><input type="hidden" id="'
				+ f + '_count" name="' + f + '_count" value="0" />';
		// }
		prependStr += '</div>';
		e.prepend(prependStr);
	}
	c.fn.pluploadQueue = function(e) {
		// var context = e.context; // context参数为必选参数
		/* 默认参数对象 */
		var defaultOpions = {
			/** 插件自带的参数begin */
			/* runtimes */
			runtimes : 'html5,flash,silverlight,gears,html4',

			// components/risefile/default/fileboxAction.jsp?action=save&appname=workflow&fileboxname=
			/* 上传url */
			url : context + '/app/attachment/upload',

			/* 用于上传的flash的地址 */
			flash_swf_url : context + '/static/common/js/plugins/plupload/js/plupload.flash.swf',

			/* 用于上传的silverlight.xap 的地址 */
			silverlight_xap_url : context + '/static/common/js/plugins/plupload/js/plupload.silverlight.xap',

			/* 上传大小限制 */
			max_file_size : '1gb', // 上传的文件大小限制

			/* 未知.. */
			unique_names : true,

			/* 选择文件的按钮上显示的文字 */
			browse_button : '选择上传文件',

			/** 插件自带的参数end * */

			/** 自定义功能的参数start */
			/* 上传文件个数限制,-1为不限制 */
			upload_count_limit : -1,

			/* 用户对象,会显示在上传界面 */
			user : {
				userName : userName,
				userGUID : userGUID
			},

			/* 上下文路径 */
			context : context,

			/* 是否自动上传 */
			auto_upload : true,

			/* 删除操作请求url */
			delete_url : context + '/app/attachment/delete',

			/* 下载文件的url的附加参数 */
			download_append_param_str : '',

			/**
			 * 已经存在了附件id,可以有三种形式,1:"[id1,id2,id3,idN...]",2:"id1,id2,i3,idN..."3,
			 */
			exist_files : null,
			exist_files_mode : "normal",

			/* 存放 附件ID 的HTML元素的ID,通常都为INPUT元素 */
			html_element_id : "uploadAttachmentGUIDs",

			/* 这个函数用来提示用户还未上传的文件的信息,未完成 */
			alert_user_unload_file : function() {
				var message = null;
				if (globalUploader.total.queued > 0) {
					message = "您还有" + globalUploader.total.queued
							+ "个附件未上传!请点击[开始上传]按钮上传!";
					setTipMessage(message);
					if (globalUploader.total.queued > 0) {
						showTipMessage();
					}
				} else {
					if (globalUploader.total.uploaded == 0) {
					} else {
						message = '<span style="color:green;">您的附件已经全部上传.</span>';
						setTipMessage(message);
					}
					hideTipMessage();
				}
			},

			// 此参数未使用
			default_header_text : "请选择您要上传的各个文件后，再点击“开始上传”按钮上传。",

			// 此参数未使用
			read_only_header_text : "只读模式,只能下载附件,不能上传及删除",

			/* 是否为只读模块 */
			read_only : false,

			/* 此参数指定处理权限的spring bean的id */
			permission_service_name : 'ecDefaultAttachmentPermissionService',

			/*
			 * 此参数为选择文件窗口的过滤设置 filters : [ {title : "Image files", extensions :
			 * "jpg,gif,png"}, {title : "Zip files", extensions : "zip,rar"} ],
			 */

			/* 删除时是否需要确认 */
			delete_need_confirm : true,// 删除附件是否需要用户确认

			/* 删除确认框中的文字 */
			delete_need_confirm_message : '真的要删除这个附件?', // 删除附件时提示的信息

			/* 删除时使用的spring bean 的id */
			on_delete_service_name :  '',
			
			/* 删除时附件时,触发on_delete方法，传到后台去的业务主键id */
			on_delete_businessPrimaryKey :  '',
			
			/* 用户执行删除操作前调用时执行的函数 */
			before_delete_file : function(file) {
			},

			/* 文件名显示的最大长度 */
			max_file_name_length : 30,

			/* 文件名过长时显示的省略字符串 */
			file_name_ellipsis : '···',

			/* 是否显示文件类型图标 */
			show_file_icon : true,

			/* 文件类型图标所在文件夹 */
			file_icons_folder : context + "/static/common/icon/fileType/",

			/* 文件类型图标定义 */
			file_icons : {
				default_ : 'default.icon.gif',
				ai : 'ai.gif',
				avi : 'avi.gif',
				bmp : 'bmp.gif',
				cs : 'cs.gif',
				dll : 'dll.gif',
				doc : 'doc.gif',
				docx : 'docx.gif',
				exe : 'exe.gif',
				fla : 'fla.gif',
				gif : 'gif.gif',
				jpg : 'gif.gif',
				htm : 'htm.gif',
				html : 'html.gif',
				jpg : 'jpg.gif',
				js : 'js.gif',
				mdb : 'mdb.gif',
				mp3 : 'mp3.gif',
				pdf : 'pdf.gif',
				ppt : 'ppt.gif',
				pptx : 'ppt.gif',
				rar : 'rar.gif',
				rdp : 'rdp.gif',
				swf : 'swf.gif',
				swt : 'swt.gif',
				txt : 'txt.gif',
				vsd : 'vsd.gif',
				xls : 'xls.gif',
				xml : 'xml.gif',
				zip : 'zip.gif'
			}
		};

		e = $.extend(defaultOpions, e);
		params = e;
		if (e) {
			this.each(function() {
				var j, i, k;
				i = c(this);
				k = i.attr("id");
				if (!k) {
					k = plupload.guid();
					i.attr("id", k)
				}
				j = new plupload.Uploader(c.extend( {
					dragdrop : true,
					container : k
				}, e));
				globalUploader = j;
				d[k] = j;
				function h(l) {
					var n;
					if (l.status == plupload.DONE) {
						n = "plupload_done"
					}
					if (l.status == plupload.FAILED) {
						n = "plupload_failed"
					}
					if (l.status == plupload.QUEUED) {
						n = "plupload_delete"
					}
					if (l.status == plupload.UPLOADING) {
						n = "plupload_uploading"
					}
					var m = c("#" + l.id).attr("class", n)
					$(m).find("a").css("display", "block");
					if (l.hint) {
						m.attr("title", l.hint)
					}
				}
				function f() {
					c("span.plupload_total_status", i).html(
							j.total.percent + "%");
					c("div.plupload_progress_bar", i).css("width",
							j.total.percent + "%");
					// c("span.plupload_upload_status", i).text(
					// a("已上传 %d/%d 个文件").replace(
					// /%d\/%d/,
					// j.total.uploaded + "/共"
					// + j.files.length))
					$("span.plupload_upload_status").html(
							"已上传" + j.total.uploaded + "个文件" + "/共"
									+ j.files.length + "个文件");
				}
				// 此函数的作用是刷新文件列表
				function g() {
					var m = c("ul.plupload_filelist", i).html(""), n = 0, l;
					// var m = c("ul.plupload_filelist", i),n = 0, l;
					c
							.each(
									j.files,
									function(p, o) {
										l = "";
										if (o.status == plupload.DONE) {
											if (o.target_name) {
												l += '<input type="hidden" name="'
														+ k
														+ "_"
														+ n
														+ '_tmpname" value="'
														+ plupload
																.xmlEncode(o.target_name)
														+ '" />'
											}
											l += '<input type="hidden" name="'
													+ k
													+ "_"
													+ n
													+ '_name" value="'
													+ plupload
															.xmlEncode(o.name)
													+ '" />';
											l += '<input type="hidden" name="'
													+ k
													+ "_"
													+ n
													+ '_status" value="'
													+ (o.status == plupload.DONE ? "done"
															: "failed")
													+ '" />';
											n++;
											c("#" + k + "_count").val(n)
										}

										if (o.uploaderGuid == null
												|| o.uploaderGuid == "") {
											o.uploaderGuid = params.user.userGUID;
										}
										if (o.uploaderName == null
												|| o.uploaderName == "") {
											o.uploaderName = params.user.userName;
										}

										var innerHTMLStr = "";
										innerHTMLStr += '<li id="';
										innerHTMLStr += o.id;
										innerHTMLStr += '"><div class="plupload_file_name"><span>'
										if (params.show_file_icon) {
											innerHTMLStr += '<img style="float: left ;" src="';
											innerHTMLStr += getIconImage(o.name);
											innerHTMLStr += '\" />';
										}
										if (o.url == "") {
											innerHTMLStr += getShortFileName(o.name);
										} else {
											innerHTMLStr += "<a style=\"float: left ; position: relative ;\" title='"
													+ o.name
													+ ",点击下载此附件' href='"
													+ o.url
													+ params.download_append_param_str
													+ "' target='_blank'>";
											innerHTMLStr += getShortFileName(o.name);
											innerHTMLStr += "</a>";
										}
										innerHTMLStr += '</span></div>';
										innerHTMLStr += '<div class="plupload_file_action">';

										if (!params.read_only
												&& o.permission.remove) {
											innerHTMLStr += '<a title="移除此附件" href="javascript:void(0);"></a>';
										}

										innerHTMLStr += '</div>';

										if (!params.read_only) {
											innerHTMLStr += '<div class="plupload_file_status">'
											if (o.isUploadError) {
												innerHTMLStr += "出错!";
											} else {
												if (o.percent == 100) {
													innerHTMLStr += "已上传";
												} else {
													innerHTMLStr += o.percent;
													innerHTMLStr += '%';
												}
											}
											innerHTMLStr += '</div>';
										}

										innerHTMLStr += '<div class="plupload_file_uploader_name">';
										if (o.uploaderName == "") {
											innerHTMLStr += userName;
										} else {
											innerHTMLStr += o.uploaderName;
										}
										innerHTMLStr += '</div>';

										innerHTMLStr += '<div class="plupload_file_uploader_date" title="';
										// innerHTMLStr += o.uploadDate ;
										innerHTMLStr += date2str(new Date(
												o.uploadDate),
												"yyyy-MM-dd hh:mm:ss");
										innerHTMLStr += '">';

										innerHTMLStr += date2str(new Date(
												o.uploadDate), "yyyy-MM-dd");
										innerHTMLStr += '</div>';

										innerHTMLStr += '<div class="plupload_file_size">';
										innerHTMLStr += plupload
												.formatSize(o.size);
										innerHTMLStr += '</div>';
										if (o.permission.remove) {
											innerHTMLStr += '<div class="plupload_clearer">&nbsp;</div>';
										} else {
											innerHTMLStr += '<div>&nbsp;</div>';
										}
										innerHTMLStr += l;
										innerHTMLStr += "</li>";
										m.append(innerHTMLStr);

										h(o);
										// 如果没有删除权限,则不注册动作
										if (o.permission.remove) {
											var deleteFunction = function(q) {
												if (!o.permission.remove) {
													// if (params.user.userGUID
													// != o.uploaderGuid) {
													// alert("这个附件不是您上传的,您不能删除!");
													alert("您没有删除权限!");
												} else {
													if (!params.delete_need_confirm
															|| (params.delete_need_confirm && confirm(params.delete_need_confirm_message))) {
														// 如果还未上传,不触发
														// before_delete_file事件
														if (o.guid != "") {
															params
																	.before_delete_file(o);
														}
														var callback = function() {
															c("#" + o.id)
																	.remove();
															j.removeFile(o);
														}
														deleteFile(o, callback);
													}
												}
												q.preventDefault();
											}

											c(
													"#"
															+ o.id
															+ ".plupload_delete .plupload_file_action a")
													.click(deleteFunction);
											c(
													"#"
															+ o.id
															+ ".plupload_done .plupload_file_action a")
													.click(deleteFunction);
										}
									});
					c("span.plupload_total_file_size", i).html(
							plupload.formatSize(j.total.size));
					if (j.total.queued === 0) {
						c("span.plupload_add_text", i).text(a("Add files."))
					} else {
						c("span.plupload_add_text", i).text(
								j.total.queued + " files queued.")
					}
					c("a.plupload_start", i)
							.toggleClass(
									"plupload_disabled",
									j.files.length == (j.total.uploaded + j.total.failed));
					m[0].scrollTop = m[0].scrollHeight;
					f();
					if (!j.files.length && j.features.dragdrop
							&& j.settings.dragdrop) {
						c("#" + k + "_filelist").append(
								'<li class="plupload_droptext">'
										+ a("您的浏览器支持HTML5，拖动文件到此即可上传！")
										+ "</li>")
					}
				}
				j.bind("UploadFile", function(l, m) {
					c("#" + m.id).addClass("plupload_current_file")
				});
				j
						.bind(
								"Init",
								function(l, m) {
									// 处理已经存在的文件,处理完成后刷新文件列表 (callbak)
									var callbak = function() {
										g();
									};
									if (e.exist_files) {
										processExistFiles(e.exist_files,
												e.exist_files_mode, l, callbak);
									}
									b(k, i);
									if (!e.unique_names && e.rename) {
										c(
												"#"
														+ k
														+ "_filelist div.plupload_file_name span",
												i)
												.live(
														"click",
														function(s) {
															var q = c(s.target), o, r, n, p = "";
															o = l
																	.getFile(q
																			.parents("li")[0].id);
															n = o.name;
															r = /^(.+)(\.[^.]+)$/
																	.exec(n);
															if (r) {
																n = r[1];
																p = r[2]
															}
															q
																	.hide()
																	.after(
																			'<input type="text" />');
															q
																	.next()
																	.val(n)
																	.focus()
																	.blur(
																			function() {
																				q
																						.show()
																						.next()
																						.remove()
																			})
																	.keydown(
																			function(
																					u) {
																				var t = c(this);
																				if (u.keyCode == 13) {
																					u
																							.preventDefault();
																					o.name = t
																							.val()
																							+ p;
																					q
																							.text(o.name);
																					t
																							.blur()
																				}
																			})
														})
									}
									c("a.plupload_add", i).attr("id",
											k + "_browse");
									l.settings.browse_button = k + "_browse";
									if (l.features.dragdrop
											&& l.settings.dragdrop) {
										l.settings.drop_element = k
												+ "_filelist";
										c("#" + k + "_filelist")
												.append(
														'<li class="plupload_droptext">'
																+ a("您的浏览器支持HTML5，拖动文件到此即可上传！")
																+ "</li>")
									}
									c("#" + k + "_container").attr("title",
											"Using runtime: " + m.runtime);
									c("a.plupload_start", i).click(
											function(n) {
												if (!c(this).hasClass(
														"plupload_disabled")) {
													j.start()
												}
												n.preventDefault()
											});
									c("a.plupload_stop", i).click(function(n) {
										n.preventDefault();
										j.stop()
									});
									c("a.plupload_start", i).addClass(
											"plupload_disabled")
								});
				j.init();
				j.bind("Error", function(l, o) {
					alert("上传出错!");
					alert(o.code);
					o.file.isUploadError = true;
					var m = o.file, n;
					if (m) {
						n = o.message;
						if (o.details) {
							n += " (" + o.details + ")"
						}
						if (o.code == plupload.FILE_SIZE_ERROR) {
							showErrorMessage(a("错误: 文件太大: ") + m.name + ".")
						}
						if (o.code == plupload.FILE_EXTENSION_ERROR) {
							showErrorMessage(a("错误: 非法的文件类型: ") + m.name)
						}
						if (o.code == plupload.IO_ERROR) {
							showErrorMessage(a("错误: IO异常: ") + m.name
									+ ".可能是您的网络断开造成的.错误详细信息:" + n)
						}
						m.hint = n;
						// c("#" + m.id).attr("class",
						// "plupload_failed").find("a")
						// .css("display", "block").attr(
						// "title", n)
					}
				});
				j
						.bind("StateChanged", function() {
							if (j.state === plupload.STARTED) {
								// c(
								// "li.plupload_delete a,div.plupload_buttons",
								// i).hide();
								c(
										"span.plupload_upload_status,div.plupload_progress,a.plupload_stop",
										i).css("display", "block");
								c("span.plupload_upload_status", i).text(
										"已上传 " + j.total.uploaded + "/共"
												+ j.files.length + " 个文件");
								if (e.multiple_queues) {
									c(
											"span.plupload_total_status,span.plupload_total_file_size",
											i).show()
								}
							} else {
								g();
								c("a.plupload_stop,div.plupload_progress", i)
										.hide();
								c("a.plupload_delete", i).css("display",
										"block")
							}
						});
				j
						.bind(
								"QueueChanged",
								function(uploader, files) {
									// 判断是否超过上传个数限制
									if (params.upload_count_limit != -1) {
										if ( params.upload_count_limit < uploader.files.length) {
											showErrorMessage("文件上传数限制为"
													+ params.upload_count_limit
													+ "个,已经达到最大限制,不能继续上传.");
											return;
										}
									}

									for ( var i = 0; i < uploader.files.length; i++) {
										for ( var j = 0; j < uploader.files.length; j++) {
											if (i == j)
												continue;
											if (uploader.files[i].name == uploader.files[j].name && uploader.files[i].size == uploader.files[j].size) {
												showErrorMessage("选择的文件["
														+ uploader.files[j].name
														+ "]已经选择了!不能上传相同文件名的附件!");
												c("#" + uploader.files[j].id)
														.remove();
												uploader
														.removeFile(uploader.files[j]);
												return;
											}
										}
									}

									if (params.auto_upload) {
										if (uploader.total.queued > 0) {
											uploader.start();
										}
									}

									g();
								});
				j.bind("FileUploaded", function(l, m, info) {
					h(m);

					var infoStr = info.response;
					try {
						var infoObj = eval("(" + infoStr + ")");

						if (infoObj.err) {
							m.isUploadError = true;
							m.percent = 0;
							alert("上传附件" + m.name + "时出错,请联系管理员,错误信息:"
									+ infoObj.err);
							return;
						}

						fillFileDataByJsonData(m, infoObj[0]);

						updateByArray(l.files, params.html_element_id);
					} catch (ex) {
						alert("上传附件[" + m.name + "]时出错,请联系管理员!错误信息:" + ex);
					}
				});
				j.bind("FilesRemoved", function(up, files) {
					updateByArray(up.files, e.html_element_id);
				});
				j
						.bind(
								"UploadProgress",
								function(l, m) {
									c("#" + m.id + " div.plupload_file_status",
											i).html(m.percent + "%");
									h(m);
									f();
									if (e.multiple_queues
											&& j.total.uploaded
													+ j.total.failed == j.files.length) {
										c(
												".plupload_buttons,.plupload_upload_status",
												i).css("display", "inline");
										c(".plupload_start", i).addClass(
												"plupload_disabled");
										c(
												"span.plupload_total_status,span.plupload_total_file_size",
												i).hide()
									}
								});
				if (e.setup) {
					e.setup(j)
				}
			});
			return this
		} else {
			return d[c(this[0]).attr("id")]
		}
	}
})(jQuery);