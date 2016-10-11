package net.risesoft.beans.bizbankroll;

import java.io.Serializable;

public class ConsultationPhone implements Serializable{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 138865140744648338L;
	private String prijectName;
	private String phone;
	public String getPrijectName() {
		return prijectName;
	}
	public void setPrijectName(String prijectName) {
		this.prijectName = prijectName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}
