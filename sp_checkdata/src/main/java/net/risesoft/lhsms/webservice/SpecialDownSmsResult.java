package net.risesoft.lhsms.webservice;

import java.util.ArrayList;
import java.util.List;

public class SpecialDownSmsResult {
	private String code;
	private String message;
	private List<Smsresult> smsresults = new ArrayList<Smsresult>();
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Smsresult> getSmsresults() {
		return smsresults;
	}
	public void setSmsresults(List<Smsresult> smsresults) {
		this.smsresults = smsresults;
	}
	
}
