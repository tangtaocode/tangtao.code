package net.risesoft.fileflow.pojo;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import net.risesoft.common.util.ContextUtil;
import net.risesoft.common.util.GuidUtil;
import net.risesoft.common.util.SysVariables;
import net.risesoft.tenant.pojo.ThreadLocalHolder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class WebOffice {
	private static final Logger logger = LoggerFactory.getLogger(WebOffice.class);

	private byte[] mFileBody;
	private String mFileType;
	private String mRecordID;
	private String mProcessSerialNumber;
	private String mProcessInstanceId;
	private String mTaskId;
	private String mUserGuid;
	private String mTemplate;
	private String mOption;
	private String mCommand;
	private String mFilePath;
	private int mColumns;
	private int mCells;
	private String mTableContent;
	private String mSql;
	private int mIsTaoHong;
	private String mOperation;
	private String mTenantId;
	// 打印控制
	private String mOfficePrints;
	private int mCopies;
	private DBstep.iMsgServer2000 MsgObj = new DBstep.iMsgServer2000(); // 创建信息包对象

	private DataSource dataSource;

	private JdbcTemplate jdbcTemplate;

	public WebOffice() {
		logger.debug("WebOffice created.");
		this.dataSource= ContextUtil.getBean("defaultDataSource");
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
	}
	
	private String mDocumenttitle;

	// 保存文档，如果文档存在，则覆盖，不存在，则添加
	private boolean SaveFile() {
		long beginTime = System.currentTimeMillis();
		boolean mResult = false;
		int count = 0;
		try {
			/*
			 * (1).mIsTaoHong="2",分不清是保存原始word还是套红word
			 */
			if (mIsTaoHong == 2) {
				count = jdbcTemplate.queryForObject("select count(*) from ff_FileDocument t where t.recordId=?", Integer.class, mRecordID);
				if (count == 0) {
					String id = GuidUtil.genGuid();
					mSql = "insert into ff_FileDocument (id,title,fileType,content,userId,saveDate,processSerialNumber,processInstanceId,taskId,istaohong,recordId,tenantId,deleted) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
					jdbcTemplate.update(mSql, id, mDocumenttitle, mFileType, mFileBody, mUserGuid, new Date(), mProcessSerialNumber, mProcessInstanceId, mTaskId, 0, mRecordID, mTenantId,0);
					/*
					 * 保存至历史表
					 */
					mSql = "insert into ff_historyfiledocument (id,title,fileType,content,userId,saveDate,processSerialNumber,processInstanceId,taskId,istaohong,recordId,tenantId) values (?,?,?,?,?,?,?,?,?,?,?,?)";
					jdbcTemplate.update(mSql, id, mDocumenttitle, mFileType, mFileBody, mUserGuid, new Date(), mProcessSerialNumber, mProcessInstanceId, mTaskId, 0, mRecordID, mTenantId);

				} else if (count == 1) {
					mSql = "update ff_FileDocument set title=?,content=?,saveDate=? where recordId=? and istaohong=?";
					jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, 0);
					/*
					 * 更新历史表
					 */
					mSql = "update ff_historyfiledocument set title=?,content=?,saveDate=? where recordId=? and taskId=? and istaohong=?";
					jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, mTaskId, 0);

				} else if (count == 2) {
					mSql = "update ff_FileDocument set title=?,content=?,saveDate=? where recordId=? and istaohong=?";
					jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, 1);
					/*
					 * 保存至历史表
					 */
					mSql = "update ff_historyfiledocument set title=?,content=?,saveDate=? where recordId=? and taskId=? and istaohong=?";
					jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, mTaskId, 1);

				}
				/*
				 * (2).指定istaohong=0或者istaohong=1
				 */
			} else {
				count = jdbcTemplate.queryForObject("select count(*) from ff_FileDocument t where t.recordId=? and t.istaohong=?", Integer.class, mRecordID, mIsTaoHong);
				if (count == 0) {
					String id = GuidUtil.genGuid();
					mSql = "insert into ff_FileDocument (id,title,fileType,content,userId,saveDate,processSerialNumber,processInstanceId,taskId,istaohong,recordId,tenantId,deleted) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
					if (mIsTaoHong == 1) {
						jdbcTemplate.update(mSql, id, mDocumenttitle, mFileType, mFileBody, mUserGuid, new Date(), mProcessSerialNumber, mProcessInstanceId, mTaskId, 1, mRecordID, mTenantId,0);
						/*
						 * 保存至历史表
						 */
						count = jdbcTemplate.queryForObject("select count(*) from ff_historyfiledocument t where t.recordId=? and t.istaohong=?", Integer.class, mRecordID, 1);
						if (count == 0) {
							mSql = "insert into ff_historyfiledocument (id,title,fileType,content,userId,saveDate,processSerialNumber,processInstanceId,taskId,istaohong,recordId,tenantId,deleted) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
							jdbcTemplate.update(mSql, id, mDocumenttitle, mFileType, mFileBody, mUserGuid, new Date(), mProcessSerialNumber, mProcessInstanceId, mTaskId, 1, mRecordID, mTenantId,0);
						} else {
							mSql = "update ff_historyfiledocument set title=?,content=?,saveDate=? where recordId=? and taskId=? and istaohong=?";
							jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, mTaskId, 1);
						}
					} else if (mIsTaoHong == 0) {
						jdbcTemplate.update(mSql, id, mDocumenttitle, mFileType, mFileBody, mUserGuid, new Date(), mProcessSerialNumber, mProcessInstanceId, mTaskId, 0, mRecordID, mTenantId);
						/*
						 * 保存至历史表
						 */
						count = jdbcTemplate.queryForObject("select count(*) from ff_historyfiledocument t where t.recordId=? and t.istaohong=?", Integer.class, mRecordID, 0);
						if (count == 0) {
							mSql = "insert into ff_historyfiledocument (id,title,fileType,content,userId,saveDate,processSerialNumber,processInstanceId,taskId,istaohong,recordId,tenantId) values (?,?,?,?,?,?,?,?,?,?,?,?)";
							jdbcTemplate.update(mSql, id, mDocumenttitle, mFileType, mFileBody, mUserGuid, new Date(), mProcessSerialNumber, mProcessInstanceId, mTaskId, 1, mRecordID, mTenantId);
						} else {
							mSql = "update ff_historyfiledocument set title=?,content=?,saveDate=? where recordId=? and taskId=? and istaohong=?";
							jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, mTaskId, 0);
						}
					}
				} else if (count == 1) {
					mSql = "update ff_FileDocument set title=?,content=?,saveDate=? where recordId=? and istaohong=?";
					if (mIsTaoHong == 0) {
						jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, 0);
						/*
						 * 更新历史表的数据
						 */
						mSql = "update ff_historyfiledocument set title=?,content=?,saveDate=? where recordId=? and taskId=? and istaohong=?";
						jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, mTaskId, 0);
					} else if (mIsTaoHong == 1) {
						jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, 1);
						/*
						 * 更新历史表的数据
						 */
						mSql = "update ff_historyfiledocument set title=?,content=?,saveDate=? where recordId=? and taskId=? and istaohong=?";
						jdbcTemplate.update(mSql, mDocumenttitle, mFileBody, new Date(), mRecordID, mTaskId, 1);

					}
				}
			}
			mResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTestTime = System.currentTimeMillis();
		logger.debug("正文保存SaveFile时间差：=============================" + (endTestTime - beginTime));
		return (mResult);
	}

	// 调出正文文档，将文档内容保存在mFileBody里，以便进行打包
	private boolean LoadFile() {
		long beginTime = System.currentTimeMillis();
		boolean mResult = false;
		int istaohong = 0;
		try {
			/*
			 * mOperation="undotaohong",撤销套红，把套红正文删除
			 */
			if (mOperation.equalsIgnoreCase("undotaohong")) {
				mSql = "delete from ff_filedocument  where recordId=? and istaohong=?";
				jdbcTemplate.update(mSql, mRecordID, 1);
			}
			if (mOperation.equalsIgnoreCase("loadPDF")) {
				mFileBody = jdbcTemplate.queryForObject("select t.content from ff_filedocument t where t.recordId=? and t.fileType=?", byte[].class, mRecordID, ".pdf");
			} else {
				int count = jdbcTemplate.queryForObject("select count(*) from ff_FileDocument t where t.recordId=?", Integer.class, mRecordID);
				if (count == 2) {
					istaohong = 1;
				}
				List<byte[]> list = jdbcTemplate.queryForList("select t.content from ff_filedocument t where t.recordId=? and t.istaohong=?", byte[].class, mRecordID, istaohong);
				if (list.size() > 0) {
					mFileBody = list.get(0);
				}
			}
			mResult = true;
			mIsTaoHong=istaohong;
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		long endTestTime = System.currentTimeMillis();
		logger.debug("加载正文LoadFile时间差：=============================" + (endTestTime - beginTime));
		return (mResult);
	}

	private boolean savePDF() {
		long beginTime = System.currentTimeMillis();
		boolean mResult = false;
		try {
			int count=0;
			count = jdbcTemplate.queryForObject("select count(*) from ff_FileDocument t where t.recordId=? and t.fileType=?", Integer.class, mRecordID,".pdf");
			if(count==0){
				mSql = "insert into ff_FileDocument (id,title,fileType,content,userId,saveDate,processSerialNumber,istaohong,recordId,tenantId) values (?,?,?,?,?,?,?,?,?,?)";
				jdbcTemplate.update(mSql,GuidUtil.genGuid(), mDocumenttitle,".pdf", mFileBody, ThreadLocalHolder.getPerson().getID(), new Date(), mRecordID,2, mRecordID, ThreadLocalHolder.getTenantId());
				mResult = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTestTime = System.currentTimeMillis();
		logger.debug("LoadTemplate时间差：=============================" + (endTestTime - beginTime));
		return (mResult);
	}
	
	// 调出模板文档，将模板文档(套红)内容保存在mFileBody里，以便进行打包
	private boolean LoadTemplate() {
		long beginTime = System.currentTimeMillis();
		boolean mResult = false;
		try {
			mFileBody = jdbcTemplate.queryForObject("select t.template_content from ff_taohongtemplate t where t.template_guid=?", byte[].class, mTemplate);
			mResult = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTestTime = System.currentTimeMillis();
		logger.debug("LoadTemplate时间差：=============================" + (endTestTime - beginTime));
		return (mResult);
	}

	// 增加行并填充表格内容
	private boolean GetWordTable() {
		int i, n;
		boolean mResult;
		mColumns = 3;
		mCells = 8;
		MsgObj.MsgTextClear();
		MsgObj.SetMsgByName("COLUMNS", String.valueOf(mColumns)); // 设置表格行
		MsgObj.SetMsgByName("CELLS", String.valueOf(mCells)); // 设置表格列
		// 该部分内容可以从数据库中读取
		try {
			for (i = 1; i <= mColumns; i++) {
				String.valueOf(i);
				for (n = 1; n <= mCells; n++) {
					MsgObj.SetMsgByName(String.valueOf(i) + String.valueOf(n), "内容" + (java.sql.Date) new Date());
				}
			}
			mResult = true;
		} catch (Exception e) {
			logger.debug(e.toString());
			mResult = false;
		}
		return (mResult);
	}

	// 更新打印份数
	private boolean UpdataCopies(int mLeftCopies) {
		boolean mResult = true;
		// 该函数可以把打印减少的次数记录到数据库
		// 根据自己的系统进行扩展该功能
		return mResult;
	}

	public void ExecuteRun(HttpServletRequest request, HttpServletResponse response) {
		long beginTime = System.currentTimeMillis();
		mUserGuid = "";
		mOption = "";
		mRecordID = "";
		mProcessInstanceId = "";
		mTaskId = "";
		mTemplate = "";
		mFileBody = null;
		mFileType = "";
		mCommand = "";
		mTableContent = "";
		mOfficePrints = "0";
		mFilePath = request.getSession().getServletContext().getRealPath(""); // 取得服务器路径
		mDocumenttitle = "";
		mIsTaoHong = 0;
		mOperation = "";
		mTenantId = "";

		logger.debug("ReadPackage");

		try {
			if (request.getMethod().equalsIgnoreCase("POST")) {
				MsgObj.Load(request); // 8.1.0.2版后台类新增解析接口，可支持UTF-8编码自适应功能

				if (MsgObj.GetMsgByName("DBSTEP").equalsIgnoreCase("DBSTEP")) { // 判断是否是合法的信息包，或者数据包信息是否完整
					mOption = MsgObj.GetMsgByName("OPTION"); // 取得操作信息
					logger.debug("OPTION:" + mOption); // 打印出调试信息

					if (mOption.equalsIgnoreCase("LOADFILE")) { // 下面的代码为打开服务器数据库里的文件
						mRecordID = MsgObj.GetMsgByName("RECORDID");
						mOperation = MsgObj.GetMsgByName("operation");
						mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
						logger.debug("============ load ------mFileType2:     " + mFileType);
						MsgObj.MsgTextClear(); // 清除文本信息
						if (LoadFile()) { // 从数据库调入文档
							logger.debug("===LOADFILE==LoadFile()=======从数据库调入文档");
							if (mFileBody != null) {
								MsgObj.MsgFileBody(mFileBody); // 将文件信息打包
								MsgObj.SetMsgByName("STATUS", Integer.toString(mIsTaoHong));
								MsgObj.MsgError(""); // 清除错误信息
							} else {
								logger.debug("新建word正文，mFileBody为空,控件打开空白文档。");
							}
						} else {
							MsgObj.SetMsgByName("STATUS", "打开失败!");
						}
					}

					else if (mOption.equalsIgnoreCase("SAVEFILE")) { // 下面的代码为保存文件在服务器的数据库里===================================================
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						mDocumenttitle = MsgObj.GetMsgByName("documenttitle");
						mProcessSerialNumber = MsgObj.GetMsgByName("processSerialNumber");
						mProcessInstanceId = MsgObj.GetMsgByName("processInstanceId");
						mTaskId = MsgObj.GetMsgByName("taskId");
						mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
						mUserGuid = MsgObj.GetMsgByName("activitiUser").split(SysVariables.COLON)[0];
						mIsTaoHong = Integer.parseInt(MsgObj.GetMsgByName("istaohong"));
						mTenantId = MsgObj.GetMsgByName("tenantId");
						logger.debug("============ save ------FileType:     " + mFileType);
						mFileBody = MsgObj.MsgFileBody(); // 取得文档内容
						MsgObj.MsgTextClear(); // 清除文本信息
						if (SaveFile()) { // 保存文档内容到数据库中
							MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							logger.debug("保存失败!");
							MsgObj.MsgError("保存失败!"); // 设置错误信息
						}
						MsgObj.MsgFileClear(); // 清除文档内容
					}

					else if (mOption.equalsIgnoreCase("SAVEPDF")) { // 下面的代码为保存PDF文件
						System.out.println("===========SAVEPDF==========");
						System.out.println("mFilePath="+request.getSession().getServletContext().getRealPath(""));
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						mDocumenttitle = MsgObj.GetMsgByName("documenttitle");
						MsgObj.GetMsgByName("FILENAME");
						mFileBody = MsgObj.MsgFileBody(); // 取得文档内容
						MsgObj.MsgTextClear(); // 清除文本信息
						//if (MsgObj.MsgFileSave(mFilePath + "\\dynamicfile\\document\\pdf\\" + mRecordID + ".pdf")) { // 保存文档到文件夹中
						if(savePDF()){
							MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("保存失败!"); // 设置错误信息
						}
						MsgObj.MsgFileClear(); // 清除文档内容
					}

					else if (mOption.equalsIgnoreCase("LOADTEMPLATE")) { // 下面的代码为插入文件
						logger.debug("=====LOADTEMPLATE=======下面的代码为载入模版");
						mTemplate = MsgObj.GetMsgByName("template_guid");
						MsgObj.MsgTextClear();
						if (LoadTemplate()) { // 调入文档
							logger.debug("==LOADTEMPLATE===LOADTEMPLATE=======下面的代码为插入文件");
							MsgObj.MsgFileBody(mFileBody); // 将文件信息打包
							MsgObj.SetMsgByName("POSITION", "RiseOffice_body"); // 设置插入的位置[书签]
							MsgObj.SetMsgByName("STATUS", "插入文件成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("插入文件成功!"); // 设置错误信息
						}
					}
					/*
					 * 把获得的word正文插入套红的word中
					 */
					else if (mOption.equalsIgnoreCase("INSERTFILE")) {
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						MsgObj.MsgTextClear();
						if (LoadFile()) { // 调入文档
							if (mFileBody != null) {
								MsgObj.MsgFileBody(mFileBody); // 将文件信息打包
								MsgObj.SetMsgByName("POSITION", "RiseOffice_body"); // 设置插入书签的位置
								MsgObj.SetMsgByName("STATUS", "插入文件成功!"); // 设置状态信息
								MsgObj.MsgError(""); // 清除错误信息
							} else {
								logger.error("正文为空，请填写正文!");
							}
						} else {
							MsgObj.MsgError("插入文件失败!"); // 设置错误信息
						}
					}

					else if (mOption.equalsIgnoreCase("DATETIME")) { // 下面的代码为请求取得服务器时间
						MsgObj.MsgTextClear(); // 清除文本信息
						MsgObj.SetMsgByName("DATETIME", String.valueOf(new Date())); // 标准日期格式字串，如
																						// 2005-8-16
																						// 10:20:35
					}

					else if (mOption.equalsIgnoreCase("SENDMESSAGE")) { // 下面的代码为Web页面请求信息[扩展接口]
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						MsgObj.GetMsgByName("FILENAME");
						mFileType = MsgObj.GetMsgByName("FILETYPE"); // 取得文档类型
						mCommand = MsgObj.GetMsgByName("COMMAND"); // 取得自定义的操作类型
						MsgObj.GetMsgByName("CONTENT");
						mOfficePrints = MsgObj.GetMsgByName("OFFICEPRINTS"); // 取得Office文档的打印次数
						// 取得客户端传来的自定义信息
						mProcessInstanceId = MsgObj.GetMsgByName("processInstanceId");
						mTaskId = MsgObj.GetMsgByName("mTaskId");
						MsgObj.GetMsgByName("templateGUID");
						MsgObj.GetMsgByName("workflowGUID");
						MsgObj.GetMsgByName("templateName");
						MsgObj.GetMsgByName("docID");
						mFileBody = MsgObj.MsgFileBody(); // 取得文档内容
						MsgObj.MsgTextClear();
						MsgObj.MsgFileClear();
						logger.debug("COMMAND:" + mCommand);
						if (mCommand.equalsIgnoreCase("WORDTABLE")) { // 插入远程表格功能
							if (GetWordTable()) {
								MsgObj.SetMsgByName("COLUMNS", String.valueOf(mColumns)); // 列
								MsgObj.SetMsgByName("CELLS", String.valueOf(mCells)); // 行
								MsgObj.SetMsgByName("WORDCONTENT", mTableContent); // 表格内容
								MsgObj.SetMsgByName("STATUS", "增加和填充成功成功!"); // 设置状态信息
								MsgObj.MsgError(""); // 清除错误信息
							} else {
								MsgObj.MsgError("增加表格行失败!"); // 设置错误信息
							}
						} else if (mCommand.equalsIgnoreCase("COPIES")) { // 打印份数控制功能
							logger.debug("PRINTS:" + mOfficePrints);
							mCopies = Integer.parseInt(mOfficePrints); // 获得客户需要打印的份数
							if (mCopies <= 2) { // 比较打印份数，拟定该文档允许打印的总数为2份，注：可以在数据库中设置好文档允许打印的份数
								if (UpdataCopies(2 - mCopies)) { // 更新打印份数
									MsgObj.SetMsgByName("STATUS", "1"); // 设置状态信息，允许打印
									MsgObj.MsgError(""); // 清除错误信息
								}
							} else {
								MsgObj.SetMsgByName("STATUS", "0"); // 不允许打印
								MsgObj.MsgError("超过打印限度不允许打印!"); // 设置错误信息
							}
						} else if (mCommand.equalsIgnoreCase("SELFINFO")) {
							// 特殊处理保存文件
							if (SaveFile()) { // 保存文档内容到数据库中
								MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
								MsgObj.MsgError(""); // 清除错误信息
							} else {
								MsgObj.MsgError("保存失败!"); // 设置错误信息
							}
							MsgObj.MsgFileClear();
						} else {
							MsgObj.MsgError("客户端Web发送数据包命令没有合适的处理函数![" + mCommand + "]");
							MsgObj.MsgTextClear();
							MsgObj.MsgFileClear();
						}
					}

					else if (mOption.equalsIgnoreCase("SAVEPAGE")) { // 下面的代码为保存为全文批注格式文件
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						MsgObj.MsgTextClear(); // 清除文本信息
						mFilePath = mFilePath + "\\Document\\" + mRecordID + ".pgf"; // 全文批注文件的完整路径
						if (MsgObj.MsgFileSave(mFilePath)) { // 保存全文批注文件
							MsgObj.SetMsgByName("STATUS", "保存全文批注成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("保存全文批注失败!"); // 设置错误信息
						}
					}

					else if (mOption.equalsIgnoreCase("LOADPAGE")) { // 下面的代码为调入全文批注格式文件
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						MsgObj.MsgTextClear(); // 清除文本信息
						mFilePath = mFilePath + "\\Document\\" + mRecordID + ".pgf"; // 全文批注文件的完整路径
						if (MsgObj.MsgFileLoad(mFilePath)) { // 调入文档内容
							MsgObj.SetMsgByName("STATUS", "打开全文批注成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("打开全文批注失败!"); // 设置错误信息
						}
					}
				} else {
					MsgObj.MsgError("客户端发送数据包错误!");
					MsgObj.MsgTextClear();
					MsgObj.MsgFileClear();
				}
			} else {
				MsgObj.MsgError("请使用Post方法");
				MsgObj.MsgTextClear();
				MsgObj.MsgFileClear();
			}
			logger.debug("SendPackage");
			logger.debug("");
			MsgObj.Send(response); // 8.1.0.2新版后台类新增的功能接口，返回信息包数据
		} catch (Exception e) {
			e.printStackTrace();
		}
		long endTestTime = System.currentTimeMillis();
		logger.debug("正文加载正文总闸门运行时间差：=============================" + (endTestTime - beginTime));
	}
}