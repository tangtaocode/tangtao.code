/*
 * easyui扩展
*/

/**
 * 使用示例：$("#div_test").disable(); 
 */
(function($){ 
	$.fn.disable = function() { 
		return $(this).find("*").each(function() { 
			$(this).attr("disabled", "disabled"); 
		}); 
	} 
	$.fn.enable = function() { 
		return $(this).find("*").each(function() { 
			$(this).removeAttr("disabled"); 
		}); 
	} 
})(jQuery); 

(function ($) {
    /*
     * 面板组件
    */
    if($.fn.panel){
    	//加载提示
    	$.fn.panel.defaults.loadingMessage = '加载中...';
    	//销毁前回收内存
    	$.fn.panel.defaults.onBeforeDestroy = function () {
    	    var frame = $('iframe', this);
    	    try {
    	        if (frame.length > 0) {
    	            for (var i = 0; i < frame.length; i++) {
    	                frame[i].src = '';
    	                frame[i].contentWindow.document.write('');
    	                frame[i].contentWindow.close();
    	            }
    	            frame.remove();
    	            if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
    	                try {
    	                    CollectGarbage();
    	                } catch (e) {
    	                }
    	            }
    	        }
    	    } catch (e) {
    	    }
    	};
    }
    /*
     * 窗口组件
    */
    if($.fn.window){
    	$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
    }
    
    /*
     * 对话框组件
    */
    if($.fn.dialog){
    	$.fn.window.defaults.onMove = easyuiPanelOnMove;
    }
    /*
    * validatebox方法扩展
    * 包括如下：
    * 中文(CHS)、英文(english)、非法字符(unNormal)、用户名(userName)
    * 传真(faxNo)、邮政编码(ZIP)、QQ号码(QQ)、电话号码(phone)、手机号码(mobile)
    * 登录名(loginName)、安全密码(safepass)、比较验证(equalTo)、货币(currency)
    * 数字(number)、整数或小数(intOrFloat)、整数(integer)、年龄(age)
    * IP地址(ip)、身份证(idcard)
    */
    if ($.fn.validatebox) {
        $.extend($.fn.validatebox.defaults.rules, {
            CHS: {
                validator: function (value, param) {
                    return /^[\u0391-\uFFE5]+$/.test(value);
                },
                message: '请输入汉字'
            },
            english: {
                validator: function (value) {
                    return /^[A-Za-z]+$/i.test(value);
                },
                message: '请输入英文'
            },
            unNormal: {
                validator: function (value) {
                    return /.+/i.test(value);
                },
                message: '输入值不能为空和包含其他非法字符'
            },
            userName: {
                validator: function (value) {
                    return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
                },
                message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
            },
            faxNo: {
                validator: function (value) {
                    //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
                },
                message: '传真号码不正确'
            },
            ZIP: {
                validator: function (value, param) {
                    return /^[1-9]\d{5}$/.test(value);
                },
                message: '邮政编码不存在'
            },
            QQ: {
                validator: function (value, param) {
                    return /^[1-9]\d{4,10}$/.test(value);
                },
                message: 'QQ号码不正确'
            },
            phone: {
                validator: function (value) {
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
                },
                message: '电话号码不正确,请使用此格式:020-12345678'
            },
            mobile: {
                validator: function (value, param) {
                    return /^((\(\d{2,3}\))|(\d{3}\-))?(13|15|18)\d{9}$/.test(value);
                },
                message: '手机号码不正确'
            },
            loginName: {
                validator: function (value, param) {
                    return /^[\u0391-\uFFE5\w]+$/.test(value);
                },
                message: '登录名称只允许汉字、英文字母、数字及下划线。'
            },
            safepass: {
                validator: function (value, param) {
                    return safePassword(value);
                },
                message: '密码由字母和数字组成，至少6位'
            },
            equalTo: {
                validator: function (value, param) {
                    return value == $(param[0]).val();
                },
                message: '两次输入的字符不一致'
            },
            currency: {
                validator: function (value) {
                    return /^\d+(\.\d+)?$/i.test(value);
                },
                message: '货币格式不正确'
            },
            number: {
                validator: function (value, param) {
                    return /^\d+$/.test(value);
                },
                message: '请输入数字'
            },
            intOrFloat: {// 验证整数或小数
                validator: function (value) {
                    return /^\d+(\.\d+)?$/i.test(value);
                },
                message: '请输入数字，并确保格式正确'
            },
            integer: {// 验证整数 可正负数
                validator: function (value) {
                	return /^([-]?[0-9])+\d*$/i.test(value) ;
                },
                message: '请输入整数'
            },
            size: {// 验证范围
                validator: function (value,param) {
                    return param[0]<=value && param[1] >= value;
                },
                message: '范围必须在{0}到{1}之间'
            },
            age: {
                validator: function (value) {
                    return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
                },
                message: '年龄必须是0到120之间的整数'
            },
            ip: {
                validator: function (value) {
                    return /d+.d+.d+.d+/i.test(value);
                },
                message: 'IP地址格式不正确'
            },
            idcard: {
                validator: function (value, param) {
                    return idCard(value);
                },
                message: '请输入正确的身份证号码'
            }
        });

        /* 密码由字母和数字组成，至少6位 */
        var safePassword = function (value) {
            return !(/^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/.test(value));
        }

        var idCard = function (value) {
            if (value.length == 18 && 18 != value.length) return false;
            var number = value.toLowerCase();
            var d, sum = 0, v = '10x98765432', w = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2], a = '11,12,13,14,15,21,22,23,31,32,33,34,35,36,37,41,42,43,44,45,46,50,51,52,53,54,61,62,63,64,65,71,81,82,91';
            var re = number.match(/^(\d{2})\d{4}(((\d{2})(\d{2})(\d{2})(\d{3}))|((\d{4})(\d{2})(\d{2})(\d{3}[x\d])))$/);
            if (re == null || a.indexOf(re[1]) < 0) return false;
            if (re[2].length == 9) {
                number = number.substr(0, 6) + '19' + number.substr(6);
                d = ['19' + re[4], re[5], re[6]].join('-');
            } else d = [re[9], re[10], re[11]].join('-');
            if (!isDateTime.call(d, 'yyyy-MM-dd')) return false;
            for (var i = 0; i < 17; i++) sum += number.charAt(i) * w[i];
            return (re[2].length == 9 || number.charAt(17) == v.charAt(sum % 11));
        }

        var isDateTime = function (format, reObj) {
            format = format || 'yyyy-MM-dd';
            var input = this, o = {}, d = new Date();
            var f1 = format.split(/[^a-z]+/gi), f2 = input.split(/\D+/g), f3 = format.split(/[a-z]+/gi), f4 = input.split(/\d+/g);
            var len = f1.length, len1 = f3.length;
            if (len != f2.length || len1 != f4.length) return false;
            for (var i = 0; i < len1; i++) if (f3[i] != f4[i]) return false;
            for (var i = 0; i < len; i++) o[f1[i]] = f2[i];
            o.yyyy = s(o.yyyy, o.yy, d.getFullYear(), 9999, 4);
            o.MM = s(o.MM, o.M, d.getMonth() + 1, 12);
            o.dd = s(o.dd, o.d, d.getDate(), 31);
            o.hh = s(o.hh, o.h, d.getHours(), 24);
            o.mm = s(o.mm, o.m, d.getMinutes());
            o.ss = s(o.ss, o.s, d.getSeconds());
            o.ms = s(o.ms, o.ms, d.getMilliseconds(), 999, 3);
            if (o.yyyy + o.MM + o.dd + o.hh + o.mm + o.ss + o.ms < 0) return false;
            if (o.yyyy < 100) o.yyyy += (o.yyyy > 30 ? 1900 : 2000);
            d = new Date(o.yyyy, o.MM - 1, o.dd, o.hh, o.mm, o.ss, o.ms);
            var reVal = d.getFullYear() == o.yyyy && d.getMonth() + 1 == o.MM && d.getDate() == o.dd && d.getHours() == o.hh && d.getMinutes() == o.mm && d.getSeconds() == o.ss && d.getMilliseconds() == o.ms;
            return reVal && reObj ? d : reVal;
            function s(s1, s2, s3, s4, s5) {
                s4 = s4 || 60, s5 = s5 || 2;
                var reVal = s3;
                if (s1 != undefined && s1 != '' || !isNaN(s1)) reVal = s1 * 1;
                if (s2 != undefined && s2 != '' && !isNaN(s2)) reVal = s2 * 1;
                return (reVal == s1 && s1.length != s5 || reVal > s4) ? -10000 : reVal;
            }
        };
    };
    /*
     * 布局组件扩展
    */
    if ($.fn.layout) {
        $.extend($.fn.layout.methods, {
            /**
             * 设置某个region的宽度或者高度(不支持center)
             * @param jq jq对象
             * @param params 参数
             * 使用示例:
             * $('#layout').layout('setRegionSize',{region:'north',value:200});  
             */
            setRegionSize: function (jq, params) {
                return jq.each(function () {
                    if (params.region == "center")
                        return;
                    var panel = $(this).layout('panel', params.region);
                    var optsOfPanel = panel.panel('options');
                    if (params.region == "north" || params.region == "south") {
                        optsOfPanel.height = params.value;
                    } else {
                        optsOfPanel.width = params.value;
                    }
                    $(this).layout('resize');
                });
            },
            /**
             * 设置north south east west区域标题的图标
             * @param jq jq对象
             * @param params 参数
             * 使用示例:
             * $('#layout').layout('setHeaderIcon',{region:'north',iconCls:'icon-ok'}); 
             */
            setHeaderIcon: function (jq, params) {
                return jq.each(function () {
                    if (params.region == "center")
                        return;
                    var panel = $(this).layout('panel', params.region);
                    var title = panel.panel('header').find('>div.panel-title');
                    var icon = panel.panel('header').find('>div.panel-icon');
                    if (icon.length === 0) {
                        if (title.length && params.iconCls != null) {
                            $('<div class="panel-icon ' + params.iconCls + '"></div>').insertBefore(title);
                            title.addClass('panel-with-icon');
                        }
                    } else {
                        if (params.iconCls == null) {
                            icon.remove();
                            title.removeClass('panel-with-icon');
                        } else {
                            icon.attr('class', '').addClass('panel-icon ' + params.iconCls);
                        }
                    }
                });
            },
            /**
             * 设置north south east west的split是否可以拖动
             * @param jq jq对象
             * @param params 参数
             * 使用示例：
             * $('#layout').layout('setSplitActivateState',{region:'north',disabled:true});
             */
            setSplitActivateState: function (jq, params) {
                return jq.each(function () {
                    if (params.region == "center")
                        return;
                    $(this).layout('panel', params.region).panel('panel').resizable(params.disabled ? 'disable' : 'enable');
                });
            },
            /**
             * 设置north south east west的split是否显示
             * @param jq jq对象
             * @param params 参数 
             * 使用示例：
             * $('#layout').layout('setSplitVisiableState',{region:'north',visible:false});
             */
            setSplitVisiableState: function (jq, params) {
                return jq.each(function () {
                    if (params.region == "center")
                        return;
                    var panel = $(this).layout('panel', params.region);
                    panel.panel('options').split = params.visible;
                    if (params.visible) {
                        panel.panel('panel').addClass('layout-split-north');
                    } else {
                        panel.panel('panel').removeClass('layout-split-north');
                    }
                    //panel.panel('resize');      
                    $(this).layout('resize');
                });
            },
            /**  
            * 面板是否存在和可见
            * @param jq jq对象
            * @param params 参数 
            * 使用示例：
            * 
            */
            isVisible: function (jq, params) {
                var panels = $.data(jq[0], 'layout').panels;
                var pp = panels[params];
                if (!pp) {
                    return false;
                }
                if (pp.length) {
                    return pp.panel('panel').is(':visible');
                } else {
                    return false;
                }
            },
            /**  
             * 隐藏除某个region，center除外。  
             * @param jq jq对象
             * @param params 参数 
             * 使用示例：
             * $('#layout').layout('hidden','north');
             * $('#layout').layout('hidden','all');
             */
            hidden: function (jq, params) {
                return jq.each(function () {
                    var opts = $.data(this, 'layout').options;
                    var panels = $.data(this, 'layout').panels;
                    if (!opts.regionState) {
                        opts.regionState = {};
                    }
                    var region = params;
                    function hide(dom, region, doResize) {
                        var first = region.substring(0, 1);
                        var others = region.substring(1);
                        var expand = 'expand' + first.toUpperCase() + others;
                        if (panels[expand]) {
                            if ($(dom).layout('isVisible', expand)) {
                                opts.regionState[region] = 1;
                                panels[expand].panel('close');
                            } else if ($(dom).layout('isVisible', region)) {
                                opts.regionState[region] = 0;
                                panels[region].panel('close');
                            }
                        } else {
                            panels[region].panel('close');
                        }
                        //if (doResize) {
                        //    $(dom).layout('resize');
                        //}
                    };
                    if (region.toLowerCase() == 'all') {
                        hide(this, 'east', false);
                        hide(this, 'north', false);
                        hide(this, 'west', false);
                        hide(this, 'south', true);
                    } else {
                        hide(this, region, true);
                    }
                });
            },
            /**  
             * 显示某个region，center除外。  
             * @param jq jq对象
             * @param params 参数 
             * 使用示例：
             * $('#layout').layout('show','north');
             * $('#layout').layout('show','all'); 
             */
            show: function (jq, params) {
                return jq.each(function () {
                    var opts = $.data(this, 'layout').options;
                    var panels = $.data(this, 'layout').panels;
                    var region = params;

                    function show(dom, region, doResize) {
                        var first = region.substring(0, 1);
                        var others = region.substring(1);
                        var expand = 'expand' + first.toUpperCase() + others;
                        if (panels[expand]) {
                            if (!$(dom).layout('isVisible', expand)) {
                                if (!$(dom).layout('isVisible', region)) {
                                    if (opts.regionState[region] == 1) {
                                        panels[expand].panel('open');
                                    } else {
                                        panels[region].panel('open');
                                    }
                                }
                            }
                        } else {
                            panels[region].panel('open');
                        }
                        if (doResize) {
                            $(dom).layout('resize');
                        }
                    };
                    if (region.toLowerCase() == 'all') {
                        show(this, 'east', false);
                        show(this, 'north', false);
                        show(this, 'west', false);
                        show(this, 'south', true);
                    } else {
                        show(this, region, true);
                    }
                });
            }
        });
    }
    if ($.fn.datagrid) {
        /**
        * 详细见http://www.easyui.info/archives/1330.html
        */
        $.extend($.fn.datagrid.methods, {
            /**
             * 开启提示功能
             * @param jq jq对象
             * @param param 参数
             * 使用示例：
             * $('#datagrid').datagrid('doCellTip',{   
			 *      onlyShowInterrupt:false,   
			 *      position:'bottom',   
			 *      tipStyler:{'backgroundColor':'#fff000', borderColor:'#ff0000', maxWidth:'50px', boxShadow:'1px 1px 3px #292929'},   
			 *      contentStyler:{backgroundColor:'#333', paddingLeft:'5px'}   
			 * });  
             */
            doCellTip: function (jq, params) {
                function showTip(showParams, td, e, dg) {
                    if ($(td).text() == "") return;//无文本，不提示。   
                    params = params || {
                        onlyShowInterrupt: false,
                        position: 'bottom',
                        tipStyler: {
                            'backgroundColor': '#fff000',
                            borderColor: '#ff0000',
                            maxWidth: '50px',
                            boxShadow: '1px 1px 3px #292929'
                        },
                        contentStyler: {
                            backgroundColor: '#333',
                            paddingLeft: '5px'
                        }
                    };
                    var options = dg.data('datagrid');
                    showParams.content = '<div class="tipcontent">' + showParams.content + '</div>';
                    $(td).tooltip({
                        content: showParams.content,
                        trackMouse: true,
                        position: params.position,
                        onHide: function () {
                            $(this).tooltip('destroy');
                        },
                        onShow: function () {
                            var tip = $(this).tooltip('tip');
                            if (showParams.tipStyler) {
                                tip.css(showParams.tipStyler);
                            }
                            if (showParams.contentStyler) {
                                tip.find('div.tipcontent').css(showParams.contentStyler);
                            }
                        }
                    }).tooltip('show');
                };
                return jq.each(function () {
                    var grid = $(this);
                    var options = $(this).data('datagrid');
                    if (!options.tooltip) {
                        var panel = grid.datagrid('getPanel').panel('panel');
                        panel.find('.datagrid-body').each(function () {
                            var delegateEle = $(this).find('> div.datagrid-body-inner').length ? $(this).find('> div.datagrid-body-inner')[0] : this;
                            $(delegateEle).undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove').delegate('td[field]', {
                                'mouseover': function (e) {
                                    //if($(this).attr('field')===undefined) return;      
                                    var that = this;
                                    var setField = null;
                                    if (params.specialShowFields && params.specialShowFields.sort) {
                                        for (var i = 0; i < params.specialShowFields.length; i++) {
                                            if (params.specialShowFields[i].field == $(this).attr('field')) {
                                                setField = params.specialShowFields[i];
                                            }
                                        }
                                    }
                                    if (setField == null) {
                                        options.factContent = $(this).find('>div').clone().css({ 'margin-left': '-5000px', 'width': 'auto', 'display': 'inline', 'position': 'absolute' }).appendTo('body');
                                        var factContentWidth = options.factContent.width();
                                        params.content = $(this).text();
                                        if (params.onlyShowInterrupt) {
                                            if (factContentWidth > $(this).width()) {
                                                showTip(params, this, e, grid);
                                            }
                                        } else {
                                            showTip(params, this, e, grid);
                                        }
                                    } else {
                                        panel.find('.datagrid-body').each(function () {
                                            var trs = $(this).find('tr[datagrid-row-index="' + $(that).parent().attr('datagrid-row-index') + '"]');
                                            trs.each(function () {
                                                var td = $(this).find('> td[field="' + setField.showField + '"]');
                                                if (td.length) {
                                                    params.content = td.text();
                                                }
                                            });
                                        });
                                        showTip(params, this, e, grid);
                                    }
                                },
                                'mouseout': function (e) {
                                    if (options.factContent) {
                                        options.factContent.remove();
                                        options.factContent = null;
                                    }
                                }
                            });
                        });
                    }
                });
            },
            /**
             * 关闭消息提示功能    
             * @param jq jq对象
             * 使用示例：
             * $('#datagrid').datagrid('cancelCellTip');
             */
            cancelCellTip: function (jq) {
                return jq.each(function () {
                    var data = $(this).data('datagrid');
                    if (data.factContent) {
                        data.factContent.remove();
                        data.factContent = null;
                    }
                    var panel = $(this).datagrid('getPanel').panel('panel');
                    panel.find('.datagrid-body').undelegate('td', 'mouseover').undelegate('td', 'mouseout').undelegate('td', 'mousemove')
                });
            },

            /**
             * 动态添加编辑类型   
             * @param jq jq对象
             * @param param 参数
             * 使用示例：
             * $("#datagrid").datagrid('addEditor', {
		     *      field : 'itemid',
		     *      editor : {
		     *          type : 'validatebox',
		     *          options : {
		     *              required : true
		     *          }
		     *      }
		     * });
             */
            addEditor: function (jq, param) {
                return jq.each(function () {
                    if (param instanceof Array) {
                        $.each(param, function (index, item) {
                            var e = $(jq).datagrid('getColumnOption', item.field);
                            e.editor = item.editor;
                        });
                    } else {
                        var e = $(jq).datagrid('getColumnOption', param.field);
                        e.editor = param.editor;
                    }
                });

            },
            /**
             * 移除添加编辑类型
             * @param jq jq对象
             * @param param 参数
             * 使用示例：
             * $("#datagrid").datagrid('removeEditor', 'itemid');
             */
            removeEditor: function (jq, param) {
                return jq.each(function () {
                    if (param instanceof Array) {
                        $.each(param, function (index, item) {
                            var e = $(jq).datagrid('getColumnOption', item);
                            e.editor = {};
                        });
                    } else {
                        var e = $(jq).datagrid('getColumnOption', param);
                        e.editor = {};
                    }
                });
            }
        });
	    /*
	     * 重写datagrid 编辑器
	    */
        $.extend(jQuery.fn.datagrid.defaults.editors, {
        	/*
        	 * 修改返回只有第一个值的bug
        	*/
            combotree: {
                init: function (container, options) {
                    var editor = $('<input type="text">').appendTo(container);
                    editor.combotree(options);
                    return editor;
                },
                destroy: function (target) {
                    $(target).combotree('destroy');
                },
                getValue: function (target) {
                    var temp = $(target).combotree('getValues');
                    return temp.join(',');
                },
                setValue: function (target, value) {
                    var temp = value.split(',');
                    $(target).combotree('setValues', temp);
                },
                resize: function (target, width) {
                    $(target).combotree('resize', width);
                }
            },
            /*
             * 密码文本框
            */
            password: {
                init: function (container, options) {
                    var editor = $('<input type="password" style="width:100%">').appendTo(container);
                    editor.validatebox(options);
                    return editor;
                },
                destroy: function (target) {
                    $(target).validatebox('destroy');
                },
                getValue: function (target) {
                    return $(target).val();
                },
                setValue: function (target, value) {
                    $(target).val(value);
                },
                resize: function (target, width) {

                }
            },
            /*
             * 查找框
            */
            lookup: {
                init: function (container, options) {
                    var editor = $('<input type="text">').appendTo(container);
                    editor.lookup(options);
                    return editor;
                },
                destroy: function (target) {
                    $(target).lookup('destroy');
                },
                getValue: function (target) {
                    return $(target).lookup('getValue');
                },
                setValue: function (target, value) {
                    $(target).lookup('setValue', value);
                },
                resize: function (target, width) {
                    $(target).lookup('resize', width);
                }
            }
        });
    }
    })(jQuery)
    
    /*
     * 防止组件超出浏览器边界
    */
    var easyuiPanelOnMove = function (left, top) {
    var l = left;
    var t = top;
    if (l < 1) {
        l = 1;
    }
    if (t < 1) {
        t = 1;
    }
    var width = parseInt($(this).parent().css('width')) + 14;
    var height = parseInt($(this).parent().css('height')) + 14;
    var right = l + width;
    var buttom = t + height;
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
    if (right > browserWidth) {
        l = browserWidth - width;
    }
    if (buttom > browserHeight) {
        t = browserHeight - height;
    }
    $(this).parent().css({/* 修正面板位置 */
        left: l,
        top: t
    });
};