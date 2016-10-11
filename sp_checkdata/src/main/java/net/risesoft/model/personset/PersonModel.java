/**
 * Person.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package net.risesoft.model.personset;

import java.util.Date;
import java.util.Map;

import egov.appservice.org.model.OrgUnit;
import egov.appservice.org.model.Person;


public class PersonModel implements Person  {
    private java.lang.String UID;

    private java.util.Date birthday;

    private java.lang.String caID;

    private java.lang.String city;

    private java.lang.String country;

    private java.util.Date createTime;

    private java.lang.String description;

    private java.lang.String dn;

    private java.lang.String duty;

    private int dutyLevel;

    private java.lang.String dutyLevelName;

    private java.lang.String education;

    private java.lang.String email;

    private java.lang.String homeAddress;

    private java.lang.String homePhone;

    private java.lang.String idNum;

    private java.lang.String idType;

    private java.lang.String loginName;

    private int maritalStatus;

    private java.lang.String mobile;

    private java.lang.String name;

    private java.lang.String officeAddress;

    private java.lang.String officeFax;

    private java.lang.String officePhone;

    private int official;

    private java.lang.String officialType;

    private java.lang.String password;

    private java.lang.String policitalStatus;

    private java.lang.String professional;

    private java.lang.String properties;

    private java.lang.String province;

    private int sex;

    private int tabIndex;

    private int version;
    
    private String orgType;

	public java.lang.String getUID() {
		return UID;
	}

	public void setUID(java.lang.String uID) {
		UID = uID;
	}

	public java.util.Date getBirthday() {
		return birthday;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public java.lang.String getCaID() {
		return caID;
	}

	public void setCaID(java.lang.String caID) {
		this.caID = caID;
	}

	public java.lang.String getCity() {
		return city;
	}

	public void setCity(java.lang.String city) {
		this.city = city;
	}

	public java.lang.String getCountry() {
		return country;
	}

	public void setCountry(java.lang.String country) {
		this.country = country;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public void setDescription(java.lang.String description) {
		this.description = description;
	}

	public java.lang.String getDn() {
		return dn;
	}

	public void setDn(java.lang.String dn) {
		this.dn = dn;
	}

	public java.lang.String getDuty() {
		return duty;
	}

	public void setDuty(java.lang.String duty) {
		this.duty = duty;
	}

	public int getDutyLevel() {
		return dutyLevel;
	}

	public void setDutyLevel(int dutyLevel) {
		this.dutyLevel = dutyLevel;
	}

	public java.lang.String getDutyLevelName() {
		return dutyLevelName;
	}

	public void setDutyLevelName(java.lang.String dutyLevelName) {
		this.dutyLevelName = dutyLevelName;
	}

	public java.lang.String getEducation() {
		return education;
	}

	public void setEducation(java.lang.String education) {
		this.education = education;
	}

	public java.lang.String getEmail() {
		return email;
	}

	public void setEmail(java.lang.String email) {
		this.email = email;
	}

	public java.lang.String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(java.lang.String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public java.lang.String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(java.lang.String homePhone) {
		this.homePhone = homePhone;
	}

	public java.lang.String getIdNum() {
		return idNum;
	}

	public void setIdNum(java.lang.String idNum) {
		this.idNum = idNum;
	}

	public java.lang.String getIdType() {
		return idType;
	}

	public void setIdType(java.lang.String idType) {
		this.idType = idType;
	}

	public java.lang.String getLoginName() {
		return loginName;
	}

	public void setLoginName(java.lang.String loginName) {
		this.loginName = loginName;
	}

	public int getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(int maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public java.lang.String getMobile() {
		return mobile;
	}

	public void setMobile(java.lang.String mobile) {
		this.mobile = mobile;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(java.lang.String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public java.lang.String getOfficeFax() {
		return officeFax;
	}

	public void setOfficeFax(java.lang.String officeFax) {
		this.officeFax = officeFax;
	}

	public java.lang.String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(java.lang.String officePhone) {
		this.officePhone = officePhone;
	}

	public int getOfficial() {
		return official;
	}

	public void setOfficial(int official) {
		this.official = official;
	}

	public java.lang.String getOfficialType() {
		return officialType;
	}

	public void setOfficialType(java.lang.String officialType) {
		this.officialType = officialType;
	}

	public java.lang.String getPassword() {
		return password;
	}

	public void setPassword(java.lang.String password) {
		this.password = password;
	}

	public java.lang.String getPolicitalStatus() {
		return policitalStatus;
	}

	public void setPolicitalStatus(java.lang.String policitalStatus) {
		this.policitalStatus = policitalStatus;
	}

	public java.lang.String getProfessional() {
		return professional;
	}

	public void setProfessional(java.lang.String professional) {
		this.professional = professional;
	}

	public java.lang.String getProperties() {
		return properties;
	}

	public void setProperties(java.lang.String properties) {
		this.properties = properties;
	}

	public java.lang.String getProvince() {
		return province;
	}

	public void setProvince(java.lang.String province) {
		this.province = province;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.OrgUnit#getShortDN()
	 */
	@Override
	public String getShortDN() {
		/**
		  * @MethodName: getShortDN
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.OrgUnit#getValues()
	 */
	@Override
	public Map<String, String> getValues() {
		/**
		  * @MethodName: getValues
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.OrgUnit#setShortDN(java.lang.String)
	 */
	@Override
	public void setShortDN(String arg0) {
		/**
		  * @MethodName: setShortDN
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.OrgUnit#setValues(java.util.Map)
	 */
	@Override
	public void setValues(Map<String, String> arg0) {
		/**
		  * @MethodName: setValues
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getAttributes()
	 */
	@Override
	public Map getAttributes() {
		/**
		  * @MethodName: getAttributes
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getCustomID()
	 */
	@Override
	public String getCustomID() {
		/**
		  * @MethodName: getCustomID
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getInnerRole()
	 */
	@Override
	public String getInnerRole() {
		/**
		  * @MethodName: getInnerRole
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getOrganization()
	 */
	@Override
	public OrgUnit getOrganization() {
		/**
		  * @MethodName: getOrganization
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getPhoto()
	 */
	@Override
	public byte[] getPhoto() {
		/**
		  * @MethodName: getPhoto
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getPlainText()
	 */
	@Override
	public String getPlainText() {
		/**
		  * @MethodName: getPlainText
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getSign()
	 */
	@Override
	public byte[] getSign() {
		/**
		  * @MethodName: getSign
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getTenantID()
	 */
	@Override
	public String getTenantID() {
		/**
		  * @MethodName: getTenantID
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#getWorkTime()
	 */
	@Override
	public Date getWorkTime() {
		/**
		  * @MethodName: getWorkTime
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return null;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#isDisabled()
	 */
	@Override
	public boolean isDisabled() {
		/**
		  * @MethodName: isDisabled
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
		return false;
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#setAttributes(java.util.Map)
	 */
	@Override
	public void setAttributes(Map arg0) {
		/**
		  * @MethodName: setAttributes
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#setCustomID(java.lang.String)
	 */
	@Override
	public void setCustomID(String arg0) {
		/**
		  * @MethodName: setCustomID
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#setDisabled(boolean)
	 */
	@Override
	public void setDisabled(boolean arg0) {
		/**
		  * @MethodName: setDisabled
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#setTenantID(java.lang.String)
	 */
	@Override
	public void setTenantID(String arg0) {
		/**
		  * @MethodName: setTenantID
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
	}

	/* (non-Javadoc)
	 * @see egov.appservice.org.model.Person#setWorkTime(java.util.Date)
	 */
	@Override
	public void setWorkTime(Date arg0) {
		/**
		  * @MethodName: setWorkTime
		  * @Description: TODO (这里用一句话描述这个方法的作用)
		  * @warning：TODO (这里描述这个方法的注意事项 – 可选)
		  * @param： (参数)
		  * @return     返回类型
		  * @throws
		  * 
		  * @Author Administrator
		  * @date 2015年11月12日  下午7:22:05
		  */
	}

}