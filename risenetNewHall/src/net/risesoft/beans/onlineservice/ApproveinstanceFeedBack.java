package net.risesoft.beans.onlineservice;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;

public class ApproveinstanceFeedBack implements Serializable{
	private String guid;
	private String feeduser;
	private String content;
	private String feedbacktime;
	private String approveinstanceguid;
	
	
	
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	public String getFeeduser() {
		return feeduser;
	}
	public void setFeeduser(String feeduser) {
		this.feeduser = feeduser;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	public String getApproveinstanceguid() {
		return approveinstanceguid;
	}
	public void setApproveinstanceguid(String approveinstanceguid) {
		this.approveinstanceguid = approveinstanceguid;
	}
	public String getFeedbacktime() {
		return feedbacktime;
	}
	public void setFeedbacktime(String feedbacktime) {
		this.feedbacktime = feedbacktime;
	}
}
