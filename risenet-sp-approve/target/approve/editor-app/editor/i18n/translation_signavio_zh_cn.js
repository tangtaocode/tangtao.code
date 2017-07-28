ORYX.I18N.PropertyWindow.dateFormat = "d/m/y";

ORYX.I18N.View.East = "属性";
ORYX.I18N.View.West = "模型元素";

ORYX.I18N.Oryx.title	= "流程编辑器";
ORYX.I18N.Oryx.pleaseWait = "编辑器加载中...";
ORYX.I18N.Edit.cutDesc = "将选中对象剪切到粘贴板";
ORYX.I18N.Edit.copyDesc = "将选中对象复制到粘贴板";
ORYX.I18N.Edit.pasteDesc = "粘贴";
ORYX.I18N.ERDFSupport.noCanvas = "xml文档不包括Oryx canvas node!";
ORYX.I18N.ERDFSupport.noSS = "流程编辑器Oryx canvas node不包括模板的定义!";
ORYX.I18N.ERDFSupport.deprText = "不推荐您导出为eRDF格式,因为这种格式在后续的版本中将不被支持,系统推荐导出为JSON格式.确定要导出吗?";
ORYX.I18N.Save.pleaseWait = "保存中<br/>请稍等...";

ORYX.I18N.Save.saveAs = "另存为";
ORYX.I18N.Save.saveAsDesc = "另存为";
ORYX.I18N.Save.saveAsTitle = "另存为";
ORYX.I18N.Save.savedAs = "另存为";
ORYX.I18N.Save.savedDescription = "流程保存于";
ORYX.I18N.Save.notAuthorized = "您未登录. 请点击链接<a href='/p/login' target='_blank'>登录</a>在新窗口登录以继续保存操作."
ORYX.I18N.Save.transAborted = "保存很慢,是否网络不好.";
ORYX.I18N.Save.noRights = "没有保存权限.";
ORYX.I18N.Save.comFailed = "连接服务器失败,请检查您的网络连接.";
ORYX.I18N.Save.failed = "保存失败,请重试.";
ORYX.I18N.Save.exception = "保存失败,出现异常,请重试.";
ORYX.I18N.Save.retrieveData = "数据获取中,请稍等.";

/** New Language Properties: 10.6.09*/
if(!ORYX.I18N.ShapeMenuPlugin) ORYX.I18N.ShapeMenuPlugin = {};
ORYX.I18N.ShapeMenuPlugin.morphMsg = "图形变换";
ORYX.I18N.ShapeMenuPlugin.morphWarningTitleMsg = "图形变换";
ORYX.I18N.ShapeMenuPlugin.morphWarningMsg = "子图形不包含在变形元素中.<br/>您想继续吗?";

if (!Signavio) { var Signavio = {}; }
if (!Signavio.I18N) { Signavio.I18N = {} }
if (!Signavio.I18N.Editor) { Signavio.I18N.Editor = {} }

if (!Signavio.I18N.Editor.Linking) { Signavio.I18N.Editor.Linking = {} }
Signavio.I18N.Editor.Linking.CreateDiagram = "新建";
Signavio.I18N.Editor.Linking.UseDiagram = "使用已存在的图形";
Signavio.I18N.Editor.Linking.UseLink = "使用网络连接";
Signavio.I18N.Editor.Linking.Close = "关闭";
Signavio.I18N.Editor.Linking.Cancel = "取消";
Signavio.I18N.Editor.Linking.UseName = "采用图形名称";
Signavio.I18N.Editor.Linking.UseNameHint = "用链接图的名称替换模型元素({type})的当前名称.";
Signavio.I18N.Editor.Linking.CreateTitle = "建立链接";
Signavio.I18N.Editor.Linking.AlertSelectModel = "请选择一个模型.";
Signavio.I18N.Editor.Linking.ButtonLink = "链接图形";
Signavio.I18N.Editor.Linking.LinkNoAccess = "您不能访问这个图形.";
Signavio.I18N.Editor.Linking.LinkUnavailable = "图形不可用.";
Signavio.I18N.Editor.Linking.RemoveLink = "删除链接";
Signavio.I18N.Editor.Linking.EditLink = "编辑链接";
Signavio.I18N.Editor.Linking.OpenLink = "打开";
Signavio.I18N.Editor.Linking.BrokenLink = "坏链接!";
Signavio.I18N.Editor.Linking.PreviewTitle = "预览";

if(!Signavio.I18N.Glossary_Support) { Signavio.I18N.Glossary_Support = {}; }
Signavio.I18N.Glossary_Support.renameEmpty = "不存在字典条目";
Signavio.I18N.Glossary_Support.renameLoading = "搜索中...";

/** New Language Properties: 08.09.2009*/
if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "主要属性";
ORYX.I18N.PropertyWindow.moreProps = "更多属性";

ORYX.I18N.PropertyWindow.btnOpen = "打开";
ORYX.I18N.PropertyWindow.btnRemove = "删除";
ORYX.I18N.PropertyWindow.btnEdit = "编辑";
ORYX.I18N.PropertyWindow.btnUp = "上移";
ORYX.I18N.PropertyWindow.btnDown = "下移";
ORYX.I18N.PropertyWindow.createNew = "新建";

if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "主要属性";
ORYX.I18N.PropertyWindow.moreProps = "更多属性";
ORYX.I18N.PropertyWindow.characteristicNr = "成本资源分析";
ORYX.I18N.PropertyWindow.meta = "自定义属性";

if(!ORYX.I18N.PropertyWindow.Category){ORYX.I18N.PropertyWindow.Category = {}}
ORYX.I18N.PropertyWindow.Category.popular = "主要属性";
ORYX.I18N.PropertyWindow.Category.characteristicnr = "成本资源分析";
ORYX.I18N.PropertyWindow.Category.others = "更多属性";
ORYX.I18N.PropertyWindow.Category.meta = "自定义属性";

if(!ORYX.I18N.PropertyWindow.ListView) ORYX.I18N.PropertyWindow.ListView = {};
ORYX.I18N.PropertyWindow.ListView.title = "编辑: ";
ORYX.I18N.PropertyWindow.ListView.dataViewLabel = "重复条目.";
ORYX.I18N.PropertyWindow.ListView.dataViewEmptyText = "没有列表项.";
ORYX.I18N.PropertyWindow.ListView.addEntryLabel = "添加新条目";
ORYX.I18N.PropertyWindow.ListView.buttonAdd = "添加";
ORYX.I18N.PropertyWindow.ListView.save = "保存";
ORYX.I18N.PropertyWindow.ListView.cancel = "取消";

if(!Signavio.I18N.Buttons) Signavio.I18N.Buttons = {};
Signavio.I18N.Buttons.save		= "保存";
Signavio.I18N.Buttons.cancel 	= "取消";
Signavio.I18N.Buttons.remove	= "删除";

if(!Signavio.I18N.btn) {Signavio.I18N.btn = {};}
Signavio.I18N.btn.btnEdit = "编辑";
Signavio.I18N.btn.btnRemove = "删除";
Signavio.I18N.btn.moveUp = "上移";
Signavio.I18N.btn.moveDown = "下移";

if(!Signavio.I18N.field) {Signavio.I18N.field = {};}
Signavio.I18N.field.Url = "URL";
Signavio.I18N.field.UrlLabel = "标签";
