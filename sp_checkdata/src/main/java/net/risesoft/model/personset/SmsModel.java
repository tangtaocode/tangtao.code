/**   
* @Title: SmsModel.java 
* @Package net.risesoft.model.personset 
* @Description: TODO(用一句话描述该文件做什么) 
* @author A18ccms A18ccms_gmail_com   
* @date 2016年9月23日 下午2:35:19 
* @version V1.0   
*/
package net.risesoft.model.personset;

import java.util.Date;

/** 
* @ClassName: SmsModel 
* @Description: 信息设置模型
* @author tangtao
* @date 2016年9月23日 下午2:35:19 
*  
*/
public class SmsModel {
	private String guid;
	private String mobile_phone;
	private String name;
	private String deptname;
	private Date createtime;
	private Date updatetime;
	/**
	 * @return the guid
	 */
	public String getGuid() {
		return guid;
	}
	/**
	 * @param guid the guid to set
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	/**
	 * @return the mobile_phone
	 */
	public String getMobile_phone() {
		return mobile_phone;
	}
	/**
	 * @param mobile_phone the mobile_phone to set
	 */
	public void setMobile_phone(String mobile_phone) {
		this.mobile_phone = mobile_phone;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the deptname
	 */
	public String getDeptname() {
		return deptname;
	}
	/**
	 * @param deptname the deptname to set
	 */
	public void setDeptname(String deptname) {
		this.deptname = deptname;
	}
	/**
	 * @return the createtime
	 */
	public Date getCreatetime() {
		return createtime;
	}
	/**
	 * @param createtime the createtime to set
	 */
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	/**
	 * @return the updatetime
	 */
	public Date getUpdatetime() {
		return updatetime;
	}
	/**
	 * @param updatetime the updatetime to set
	 */
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	}