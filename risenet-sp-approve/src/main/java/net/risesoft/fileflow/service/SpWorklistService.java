package net.risesoft.fileflow.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.risesoft.approve.entity.base.Pager;

import org.springframework.ui.Model;

public interface SpWorklistService {
	
	/**
	 * 暂停件列表
	 * @param page
	 * @param rows
	 * @param sord
	 * @return
	 */
	Map<String, Object> pauseList(String pauseDocumentTitle,String taskSender,String startTime,String endTime,Pager pager);
	
	/**
	 * 特别程序审核列表
	 * @param page
	 * @param rows
	 * @param sord
	 * @return
	 */
	Map<String, Object> teBieChengXuList(String documentTitle,
			String taskSender, String startTime, String endTime,Pager pager);
	/**
	 * 待办列表
	 * @param page
	 * @param rows
	 * @param sord
	 * @return
	 */
	//Map<String, Object> todoList(String todoDocumentTitle,String taskName,String taskSender,String startTime,String endTime,int page,int rows,String sord);

	Map<String, Object> todoList(String todoDocumentTitle, String taskName,
			String taskSender, String startTime, String endTime, Pager pager);
	
	/**
	 * 在办列表
	 * @param page
	 * @param rows
	 * @param sord
	 * @return
	 */
	Map<String, Object> doingList(String doingDocumentTitle,String taskName,String taskSender,String taskAssignee,String startTime,String endTime, Pager pager);
	
	/**
	 * 办结件
	 * @param page
	 * @param rows
	 * @param sord
	 * @return
	 */
	Map<String, Object> doneList(String doneDocumentTitle,String startTime,String endTime,Pager pager);
	
	/**
	 * 所有件
	 * @param page
	 * @param rows
	 * @param sord
	 * @return
	 */
	Map<String, Object> allDocumentList(Pager pager);
	Map<String, Object> allDocumentList2(Pager pager);

	/**
	 * 特别程序审核件计数
	 * @return
	 */
	long getTeBieChengXuCount();
	
	
	/**
	 * 暂停件计数
	 * @return
	 */
	long getPauseCount();
	
	/**
	 * 待办件计数
	 * @return
	 */
	long getTodoCount();
	
	/**
	 * 在办件计数
	 * @return
	 */
	long getDoingCount();
	
	/**
	 * 办结件计数
	 * @return
	 */
	long getDoneCount();

	/**
	 * 待办件查找
	 * @return
	 */
	Map<String, Object> findTodoList(String todocumentTitle, String taskName,
			String uploadtimeStart, String uploadtimeStart2, int page,
			int rows, String sord);

	public Model getBureauName(String processInstanceId,String SPinstanceId,Model model);
	
	/**
	 * 历程
	 * @param processInstanceId
	 * @return
	 */
	Map<String,Object> history(String processInstanceId);

	/**
	 * 导出待办excel
	 * @param response
	 * @param request
	 * @param isPage
	 * @param pager
	 * @param todoDocumentTitle
	 * @param taskName
	 * @param taskSender
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public String todoExcel(HttpServletResponse response,HttpServletRequest request,String isPage,Pager pager,
			  	String todoDocumentTitle,String taskName,String taskSender,String startTime,String endTime);

	public String doingExcel(HttpServletResponse response,HttpServletRequest request,String isPage,Pager pager,
		  	String doingDocumentTitle,String taskAssignee,String taskName,String taskSender,String startTime,String endTime);

}
