/**
 * @author nicolas.peters
 * 
 * Contains all strings for the default language (en-us).
 * Version 1 - 08/29/08
 */
if(!ORYX) var ORYX = {};

if(!ORYX.I18N) ORYX.I18N = {};

ORYX.I18N.Language = "zh_cn"; //Pattern <ISO language code>_<ISO country code> in lower case!

if(!ORYX.I18N.Oryx) ORYX.I18N.Oryx = {};

ORYX.I18N.Oryx.title		= "流程编辑器";
ORYX.I18N.Oryx.noBackendDefined	= "注意! \n没有定义后端.\n 不能加载请求模型. 尝试加载一个拥有保存插件的配置.";
ORYX.I18N.Oryx.pleaseWait 	= "加载中,请稍等...";
ORYX.I18N.Oryx.notLoggedOn = "未登录";
ORYX.I18N.Oryx.editorOpenTimeout = "编辑器打不开,您是否禁用了弹出.";

if(!ORYX.I18N.AddDocker) ORYX.I18N.AddDocker = {};

ORYX.I18N.AddDocker.group = "节点";
ORYX.I18N.AddDocker.add = "添加节点";
ORYX.I18N.AddDocker.addDesc = "单击按钮和指定的边,在边上添加一个节点";
ORYX.I18N.AddDocker.del = "删除节点";
ORYX.I18N.AddDocker.delDesc = "删除节点";

if(!ORYX.I18N.Arrangement) ORYX.I18N.Arrangement = {};

ORYX.I18N.Arrangement.groupZ = "Z-Order";
ORYX.I18N.Arrangement.btf = "移到最前";
ORYX.I18N.Arrangement.btfDesc = "移到最前";
ORYX.I18N.Arrangement.btb = "移到最后";
ORYX.I18N.Arrangement.btbDesc = "移到最后";
ORYX.I18N.Arrangement.bf = "前移";
ORYX.I18N.Arrangement.bfDesc = "前移";
ORYX.I18N.Arrangement.bb = "后移";
ORYX.I18N.Arrangement.bbDesc = "后移";
ORYX.I18N.Arrangement.groupA = "排成直线";
ORYX.I18N.Arrangement.ab = "底对齐";
ORYX.I18N.Arrangement.abDesc = "底部";
ORYX.I18N.Arrangement.am = "水平对齐";
ORYX.I18N.Arrangement.amDesc = "水平对齐";
ORYX.I18N.Arrangement.at = "顶对齐";
ORYX.I18N.Arrangement.atDesc = "顶部";
ORYX.I18N.Arrangement.al = "左对齐";
ORYX.I18N.Arrangement.alDesc = "左边";
ORYX.I18N.Arrangement.ac = "垂直对齐";
ORYX.I18N.Arrangement.acDesc = "垂直对齐";
ORYX.I18N.Arrangement.ar = "右对齐";
ORYX.I18N.Arrangement.arDesc = "右边";
ORYX.I18N.Arrangement.as = "同等大小";
ORYX.I18N.Arrangement.asDesc = "同等大小";

if(!ORYX.I18N.Edit) ORYX.I18N.Edit = {};

ORYX.I18N.Edit.group = "编辑";
ORYX.I18N.Edit.cut = "剪切";
ORYX.I18N.Edit.cutDesc = "将选中对象剪切到粘贴板";
ORYX.I18N.Edit.copy = "复制";
ORYX.I18N.Edit.copyDesc = "将选中对象复制到粘贴板";
ORYX.I18N.Edit.paste = "粘贴";
ORYX.I18N.Edit.pasteDesc = "粘贴";
ORYX.I18N.Edit.del = "删除";
ORYX.I18N.Edit.delDesc = "删除选中对象";

if(!ORYX.I18N.EPCSupport) ORYX.I18N.EPCSupport = {};

ORYX.I18N.EPCSupport.group = "EPC";
ORYX.I18N.EPCSupport.exp = "导出为EPC";
ORYX.I18N.EPCSupport.expDesc = "导出图形为EPML";
ORYX.I18N.EPCSupport.imp = "导入EPC";
ORYX.I18N.EPCSupport.impDesc = "导入EPML文件";
ORYX.I18N.EPCSupport.progressExp = "导出模型";
ORYX.I18N.EPCSupport.selectFile = "选择一个EPML(.empl)文件导入.";
ORYX.I18N.EPCSupport.file = "文件";
ORYX.I18N.EPCSupport.impPanel = "导入EPML文件";
ORYX.I18N.EPCSupport.impBtn = "导入";
ORYX.I18N.EPCSupport.close = "关闭";
ORYX.I18N.EPCSupport.error = "错误";
ORYX.I18N.EPCSupport.progressImp = "导入...";

if(!ORYX.I18N.ERDFSupport) ORYX.I18N.ERDFSupport = {};

ORYX.I18N.ERDFSupport.exp = "导出为ERDF";
ORYX.I18N.ERDFSupport.expDesc = "导出为ERDF";
ORYX.I18N.ERDFSupport.imp = "从ERDF中导入";
ORYX.I18N.ERDFSupport.impDesc = "从ERDF中导入";
ORYX.I18N.ERDFSupport.impFailed = "导入ERDF失败.";
ORYX.I18N.ERDFSupport.impFailed2 = "导入失败! <br/>请检查错误信息: <br/><br/>";
ORYX.I18N.ERDFSupport.error = "错误";
ORYX.I18N.ERDFSupport.noCanvas = "xml文档不包括Oryx canvas node!";
ORYX.I18N.ERDFSupport.noSS = "Oryx canvas node 不包括模板集的定义!";
ORYX.I18N.ERDFSupport.wrongSS = "给定的模板集不适合当前编辑器!";
ORYX.I18N.ERDFSupport.selectFile = "选择一个ERDF(.xml)文件或输入其名称来导入!";
ORYX.I18N.ERDFSupport.file = "文件";
ORYX.I18N.ERDFSupport.impERDF = "导入ERDF";
ORYX.I18N.ERDFSupport.impBtn = "导入";
ORYX.I18N.ERDFSupport.impProgress = "导入...";
ORYX.I18N.ERDFSupport.close = "关闭";
ORYX.I18N.ERDFSupport.deprTitle = "确定要导出为eRDF?";
ORYX.I18N.ERDFSupport.deprText = "不推荐您导出为eRDF格式,因为这种格式在后续的版本中将不被支持,系统推荐导出为JSON格式.确定要导出吗?";

if(!ORYX.I18N.jPDLSupport) ORYX.I18N.jPDLSupport = {};

ORYX.I18N.jPDLSupport.group = "ExecBPMN";
ORYX.I18N.jPDLSupport.exp = "导出为jPDL";
ORYX.I18N.jPDLSupport.expDesc = "导出为jPDL";
ORYX.I18N.jPDLSupport.imp = "导入jPDL";
ORYX.I18N.jPDLSupport.impDesc = "导入jPDL";
ORYX.I18N.jPDLSupport.impFailedReq = "导入jPDL失败.";
ORYX.I18N.jPDLSupport.impFailedJson = "转成jPDL失败.";
ORYX.I18N.jPDLSupport.impFailedJsonAbort = "导入中止.";
ORYX.I18N.jPDLSupport.loadSseQuestionTitle = "JBPM工作流模板集的扩展需要加载"; 
ORYX.I18N.jPDLSupport.loadSseQuestionBody = "为了导入jPDL, 模板设置扩展必须加载。你想继续吗?";
ORYX.I18N.jPDLSupport.expFailedReq = "请求导出模型失败.";
ORYX.I18N.jPDLSupport.expFailedXml = "导出jPDL失败. 信息: ";
ORYX.I18N.jPDLSupport.error = "错误";
ORYX.I18N.jPDLSupport.selectFile = "请选择jPDL(.xml)文件以导入!";
ORYX.I18N.jPDLSupport.file = "文件";
ORYX.I18N.jPDLSupport.impJPDL = "导入jPDL";
ORYX.I18N.jPDLSupport.impBtn = "导入";
ORYX.I18N.jPDLSupport.impProgress = "导入...";
ORYX.I18N.jPDLSupport.close = "关闭";

if(!ORYX.I18N.Save) ORYX.I18N.Save = {};

ORYX.I18N.Save.group = "File";
ORYX.I18N.Save.save = "保存";
ORYX.I18N.Save.saveDesc = "保存";
ORYX.I18N.Save.saveAs = "另存为...";
ORYX.I18N.Save.saveAsDesc = "另存为...";
ORYX.I18N.Save.unsavedData = "存在未保存数据,请在退出前保存,以防信息丢失!";
ORYX.I18N.Save.newProcess = "新建流程";
ORYX.I18N.Save.saveAsTitle = "另存为...";
ORYX.I18N.Save.saveBtn = "保存";
ORYX.I18N.Save.close = "关闭";
ORYX.I18N.Save.savedAs = "另存为";
ORYX.I18N.Save.saved = "保存成功!";
ORYX.I18N.Save.failed = "保存失败.";
ORYX.I18N.Save.noRights = "无权限保存.";
ORYX.I18N.Save.saving = "保存中";
ORYX.I18N.Save.saveAsHint = "流程文件保存于:";

if(!ORYX.I18N.File) ORYX.I18N.File = {};

ORYX.I18N.File.group = "File";
ORYX.I18N.File.print = "打印";
ORYX.I18N.File.printDesc = "打印当前模型";
ORYX.I18N.File.pdf = "导出为PDF";
ORYX.I18N.File.pdfDesc = "导出为PDF";
ORYX.I18N.File.info = "信息";
ORYX.I18N.File.infoDesc = "信息";
ORYX.I18N.File.genPDF = "生成PDF";
ORYX.I18N.File.genPDFFailed = "生成PDF失败.";
ORYX.I18N.File.printTitle = "打印";
ORYX.I18N.File.printMsg = "当前的打印存在问题,推荐您保存为pdf后再用pdf进行打印.是否继续打印?";

if(!ORYX.I18N.Grouping) ORYX.I18N.Grouping = {};

ORYX.I18N.Grouping.grouping = "分组";
ORYX.I18N.Grouping.group = "Group";
ORYX.I18N.Grouping.groupDesc = "分组选中图形";
ORYX.I18N.Grouping.ungroup = "取消分组";
ORYX.I18N.Grouping.ungroupDesc = "删除选中图形的分组";

if(!ORYX.I18N.Loading) ORYX.I18N.Loading = {};

ORYX.I18N.Loading.waiting ="请稍等...";

if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};

ORYX.I18N.PropertyWindow.name = "名称";
ORYX.I18N.PropertyWindow.value = "值";
ORYX.I18N.PropertyWindow.selected = "已选中";
ORYX.I18N.PropertyWindow.clickIcon = "单击图标";
ORYX.I18N.PropertyWindow.add = "添加";
ORYX.I18N.PropertyWindow.rem = "清除";
ORYX.I18N.PropertyWindow.complex = "复杂编辑器";
ORYX.I18N.PropertyWindow.text = "文本编辑器";
ORYX.I18N.PropertyWindow.ok = "确定";
ORYX.I18N.PropertyWindow.cancel = "取消";
ORYX.I18N.PropertyWindow.dateFormat = "m/d/y";

if(!ORYX.I18N.ShapeMenuPlugin) ORYX.I18N.ShapeMenuPlugin = {};

ORYX.I18N.ShapeMenuPlugin.drag = "拖动";
ORYX.I18N.ShapeMenuPlugin.clickDrag = "单击并拖动";
ORYX.I18N.ShapeMenuPlugin.morphMsg = "图形变换";

if(!ORYX.I18N.SyntaxChecker) ORYX.I18N.SyntaxChecker = {};

ORYX.I18N.SyntaxChecker.group = "Verification";
ORYX.I18N.SyntaxChecker.name = "流程检查";
ORYX.I18N.SyntaxChecker.desc = "检查流程";
ORYX.I18N.SyntaxChecker.noErrors = "没有流程错误.";
ORYX.I18N.SyntaxChecker.invalid = "存在流程错误.";
ORYX.I18N.SyntaxChecker.checkingMessage = "检查中 ...";
if(!ORYX.I18N.Deployer) ORYX.I18N.Deployer = {};

ORYX.I18N.Deployer.group = "部署";
ORYX.I18N.Deployer.name = "部署者";
ORYX.I18N.Deployer.desc = "部署到引擎";

if(!ORYX.I18N.Undo) ORYX.I18N.Undo = {};

ORYX.I18N.Undo.group = "撤销";
ORYX.I18N.Undo.undo = "撤消";
ORYX.I18N.Undo.undoDesc = "撤消最后操作";
ORYX.I18N.Undo.redo = "重做";
ORYX.I18N.Undo.redoDesc = "重做最后操作";

if(!ORYX.I18N.View) ORYX.I18N.View = {};

ORYX.I18N.View.group = "缩放";
ORYX.I18N.View.zoomIn = "放大";
ORYX.I18N.View.zoomInDesc = "放大";
ORYX.I18N.View.zoomOut = "缩小";
ORYX.I18N.View.zoomOutDesc = "缩小";
ORYX.I18N.View.zoomStandard = "原始大小";
ORYX.I18N.View.zoomStandardDesc = "缩放至原始大小";
ORYX.I18N.View.zoomFitToModel = "最适合";
ORYX.I18N.View.zoomFitToModelDesc = "缩放至最适合模型大小";

/** New Language Properties: 08.12.2008 */

ORYX.I18N.PropertyWindow.title = "属性";

if(!ORYX.I18N.ShapeRepository) ORYX.I18N.ShapeRepository = {};
ORYX.I18N.ShapeRepository.title = "图形仓库";

ORYX.I18N.Save.dialogDesciption = "请输入名称,描述,注释.";
ORYX.I18N.Save.dialogLabelTitle = "标题";
ORYX.I18N.Save.dialogLabelDesc = "描述";
ORYX.I18N.Save.dialogLabelType = "类型";
ORYX.I18N.Save.dialogLabelComment = "修订注释";

Ext.MessageBox.buttonText.yes = "是";
Ext.MessageBox.buttonText.no = "否";
Ext.MessageBox.buttonText.cancel = "取消";
Ext.MessageBox.buttonText.ok = "确定";

if(!ORYX.I18N.Perspective) ORYX.I18N.Perspective = {};
ORYX.I18N.Perspective.no = "没有视图";
ORYX.I18N.Perspective.noTip = "卸载当前视图";

/** New Language Properties: 21.04.2009 */
ORYX.I18N.JSONSupport = {
    imp: {
    	name: "导入JSON",
        desc: "导入JSON",
        group: "导出",
        selectFile: "选择一个JSON(.json)文件并导入!",
        file: "文件",
        btnImp: "导入",
        btnClose: "关闭",
        progress: "导入中 ...",
        syntaxError: "语法错误"
    },
    exp: {
    	name: "导出为JSON",
        desc: "导出为JSON",
        group: "导出"
    }
};

/** New Language Properties: 09.05.2009 */
if(!ORYX.I18N.JSONImport) ORYX.I18N.JSONImport = {};
ORYX.I18N.JSONImport.title = "JSON导入";
ORYX.I18N.JSONImport.wrongSS = "输入文件的模板集（{ 0 }）与加载的模板集（{ 1 }）不匹配.";

/** New Language Properties: 14.05.2009 */
if(!ORYX.I18N.RDFExport) ORYX.I18N.RDFExport = {};
ORYX.I18N.RDFExport.group = "导出";
ORYX.I18N.RDFExport.rdfExport = "导出RDF";
ORYX.I18N.RDFExport.rdfExportDescription = "导出为xml";

/** New Language Properties: 15.05.2009*/
if(!ORYX.I18N.SyntaxChecker.BPMN) ORYX.I18N.SyntaxChecker.BPMN={};
ORYX.I18N.SyntaxChecker.BPMN_NO_SOURCE = "箭头必须要有起点.";
ORYX.I18N.SyntaxChecker.BPMN_NO_TARGET = "箭头必须要有终点.";
ORYX.I18N.SyntaxChecker.BPMN_DIFFERENT_PROCESS = "开始结点和结束结点必须要在同一流程中.";
ORYX.I18N.SyntaxChecker.BPMN_SAME_PROCESS = "开始结点和结束结点必须位于不同的池中.";
ORYX.I18N.SyntaxChecker.BPMN_FLOWOBJECT_NOT_CONTAINED_IN_PROCESS = "流程必须包含箭头.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITHOUT_INCOMING_CONTROL_FLOW = "结束节点必须存在流入箭头.";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "开始节点必须存在流出箭头.";
ORYX.I18N.SyntaxChecker.BPMN_STARTEVENT_WITH_INCOMING_CONTROL_FLOW = "开始节点不能有注入箭头.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITH_INCOMING_CONTROL_FLOW = "附加的中间事件必须是没有传入的序列流.";
ORYX.I18N.SyntaxChecker.BPMN_ATTACHEDINTERMEDIATEEVENT_WITHOUT_OUTGOING_CONTROL_FLOW = "附加的中间事件必须有一个完整的输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN_ENDEVENT_WITH_OUTGOING_CONTROL_FLOW = "结束节点不能有流出箭头.";
ORYX.I18N.SyntaxChecker.BPMN_EVENTBASEDGATEWAY_BADCONTINUATION = "基于事件的网关不能跟着网关或过程基于事件的网关不能跟在网关或子流程后面.";
ORYX.I18N.SyntaxChecker.BPMN_NODE_NOT_ALLOWED = "非法节点类型.";

if(!ORYX.I18N.SyntaxChecker.IBPMN) ORYX.I18N.SyntaxChecker.IBPMN={};
ORYX.I18N.SyntaxChecker.IBPMN_NO_ROLE_SET = "相互作用必须有一个发送器和接收器的作用集";
ORYX.I18N.SyntaxChecker.IBPMN_NO_INCOMING_SEQFLOW = "节点必须有流入箭头.";
ORYX.I18N.SyntaxChecker.IBPMN_NO_OUTGOING_SEQFLOW = "节点必须有流出箭头.";

if(!ORYX.I18N.SyntaxChecker.InteractionNet) ORYX.I18N.SyntaxChecker.InteractionNet={};
ORYX.I18N.SyntaxChecker.InteractionNet_SENDER_NOT_SET = "未设置发件人";
ORYX.I18N.SyntaxChecker.InteractionNet_RECEIVER_NOT_SET = "未设置收件人";
ORYX.I18N.SyntaxChecker.InteractionNet_MESSAGETYPE_NOT_SET = "未设置消息类型";
ORYX.I18N.SyntaxChecker.InteractionNet_ROLE_NOT_SET = "未设置角色";

if(!ORYX.I18N.SyntaxChecker.EPC) ORYX.I18N.SyntaxChecker.EPC={};
ORYX.I18N.SyntaxChecker.EPC_NO_SOURCE = "箭头必须有起点.";
ORYX.I18N.SyntaxChecker.EPC_NO_TARGET = "箭头必须有终点.";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED = "节点必须用箭头连接.";
ORYX.I18N.SyntaxChecker.EPC_NOT_CONNECTED_2 = "节点必须用更多的箭头连接.";
ORYX.I18N.SyntaxChecker.EPC_TOO_MANY_EDGES = "节点拥有太多的连接箭头.";
ORYX.I18N.SyntaxChecker.EPC_NO_CORRECT_CONNECTOR = "节点不是正确的连接器.";
ORYX.I18N.SyntaxChecker.EPC_MANY_STARTS = "只有有一个开始节点.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_OR = "或/异或后不能有功能.";
ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_OR = "或/异或后不能有过程界面.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_FUNCTION =  "函数不能接在函数后面。";
ORYX.I18N.SyntaxChecker.EPC_EVENT_AFTER_EVENT =  "事件不能接在事件后面.";
ORYX.I18N.SyntaxChecker.EPC_PI_AFTER_FUNCTION =  "函数后面不能有过程界面.";
ORYX.I18N.SyntaxChecker.EPC_FUNCTION_AFTER_PI =  "过程界面后面不能有函数.";
ORYX.I18N.SyntaxChecker.EPC_SOURCE_EQUALS_TARGET = "箭头必须连接两个节点.";

if(!ORYX.I18N.SyntaxChecker.PetriNet) ORYX.I18N.SyntaxChecker.PetriNet={};
ORYX.I18N.SyntaxChecker.PetriNet_NOT_BIPARTITE = "不是双向图";
ORYX.I18N.SyntaxChecker.PetriNet_NO_LABEL = "标签未设置过渡标记";
ORYX.I18N.SyntaxChecker.PetriNet_NO_ID = "存在没有id的节点";
ORYX.I18N.SyntaxChecker.PetriNet_SAME_SOURCE_AND_TARGET = "两个流的源和目标都相同";
ORYX.I18N.SyntaxChecker.PetriNet_NODE_NOT_SET = "某个节点没设置流关系";

/** New Language Properties: 02.06.2009*/
ORYX.I18N.Edge = "箭头";
ORYX.I18N.Node = "节点";

/** New Language Properties: 03.06.2009*/
ORYX.I18N.SyntaxChecker.notice = "将鼠标移到红色十字图标以查看错误信息.";

/** New Language Properties: 05.06.2009*/
if(!ORYX.I18N.RESIZE) ORYX.I18N.RESIZE = {};
ORYX.I18N.RESIZE.tipGrow = "增加画布尺寸:";
ORYX.I18N.RESIZE.tipShrink = "减小画布尺寸:";
ORYX.I18N.RESIZE.N = "上";
ORYX.I18N.RESIZE.W = "左";
ORYX.I18N.RESIZE.S ="下";
ORYX.I18N.RESIZE.E ="右";

/** New Language Properties: 15.07.2009*/
if(!ORYX.I18N.Layouting) ORYX.I18N.Layouting ={};
ORYX.I18N.Layouting.doing = "布局...";

/** New Language Properties: 18.08.2009*/
ORYX.I18N.SyntaxChecker.MULT_ERRORS = "多个错误";

/** New Language Properties: 08.09.2009*/
if(!ORYX.I18N.PropertyWindow) ORYX.I18N.PropertyWindow = {};
ORYX.I18N.PropertyWindow.oftenUsed = "常用";
ORYX.I18N.PropertyWindow.moreProps = "更多属性";

/** New Language Properties 01.10.2009 */
if(!ORYX.I18N.SyntaxChecker.BPMN2) ORYX.I18N.SyntaxChecker.BPMN2 = {};

ORYX.I18N.SyntaxChecker.BPMN2_DATA_INPUT_WITH_INCOMING_DATA_ASSOCIATION = "数据输入必须没有任何输入数据关联.";
ORYX.I18N.SyntaxChecker.BPMN2_DATA_OUTPUT_WITH_OUTGOING_DATA_ASSOCIATION = "数据输出必须没有任何输出数据关联.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_TARGET_WITH_TOO_MANY_INCOMING_SEQUENCE_FLOWS = "基于事件的网关的目标可能只有一个传入的序列流。";

/** New Language Properties 02.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_OUTGOING_SEQUENCE_FLOWS = "基于事件的网关必须有2个或更多的输出序列流.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_EVENT_TARGET_CONTRADICTION = "如果在配置中使用消息中间事件, 然后接收任务不能使用，反之亦然.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_TRIGGER = "只有以下的中间事件触发器是有效的: 消息，信号，定时器，条件事件和多重事件.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WRONG_CONDITION_EXPRESSION = "事件网关的输出序列流不能有条件表达式.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_NOT_INSTANTIATING = "分支不存在初始流化程的约束.请为分支指定一个开始节点或实例属性";

/** New Language Properties 05.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_MIXED_FAILURE = "分支必须为多进多出.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_CONVERGING_FAILURE = "分支必须为多进单出.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAYDIRECTION_DIVERGING_FAILURE = "分支必须为单进多出.";
ORYX.I18N.SyntaxChecker.BPMN2_GATEWAY_WITH_NO_OUTGOING_SEQUENCE_FLOW = "分支必须至少含有一个流出箭头.";
ORYX.I18N.SyntaxChecker.BPMN2_RECEIVE_TASK_WITH_ATTACHED_EVENT = "在事件网关配置中使用的接收任务不能有任何附加的中间事件.";
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_SUBPROCESS_BAD_CONNECTION = "事件子流程不能有任何流入流出箭头.";

/** New Language Properties 13.10.2009 */
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_CONNECTED = "消息流的至少一个边必须连接.";

/** New Language Properties 24.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_MESSAGES = "一个编排活动可能只有一个起始信息.";
ORYX.I18N.SyntaxChecker.BPMN_MESSAGE_FLOW_NOT_ALLOWED = "这里不允许设置消息流.";

/** New Language Properties 27.11.2009 */
ORYX.I18N.SyntaxChecker.BPMN2_EVENT_BASED_WITH_TOO_LESS_INCOMING_SEQUENCE_FLOWS = "一个基于事件的网关没有实例化，它至少要被一个输入序列流实例化.";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_FEW_INITIATING_PARTICIPANTS = "编排活动必须有一个启动参与者（白色）.";
ORYX.I18N.SyntaxChecker.BPMN2_TOO_MANY_INITIATING_PARTICIPANTS = "一个编排活动不得有一个以上的发起人（白色）."

ORYX.I18N.SyntaxChecker.COMMUNICATION_AT_LEAST_TWO_PARTICIPANTS = "通信必须连接至少2个参与者.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_START_MUST_BE_PARTICIPANT = "消息流的源必须是参与者.";
ORYX.I18N.SyntaxChecker.MESSAGEFLOW_END_MUST_BE_PARTICIPANT = "消息流的目标必须是参与者.";
ORYX.I18N.SyntaxChecker.CONV_LINK_CANNOT_CONNECT_CONV_NODES = "会话连接必须连接一个与参与者的通信或子会话节点.";
