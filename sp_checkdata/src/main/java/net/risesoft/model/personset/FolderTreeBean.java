/**
 * @Project Name:risenetzj
 * @File Name: FloderTreeBean.java
 * @Package Name: net.risesoft.optpermission.bean
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date Sep 1, 2015 3:52:13 PM
 */

package net.risesoft.model.personset;

import java.io.Serializable;

/**
 * @ClassName: FloderTreeBean.java
 * @Description: 栏目bean
 *
 * @author tt
 * @date Sep 1, 2015 3:52:13 PM
 * @version 
 * @since JDK 1.6
 */
public class FolderTreeBean implements Serializable {
private String deedbox_guid;//文件箱主键
private String deedbox_name;//文件箱名称
private String superior_guid;//父文件夹主键
private String folder_guid;//文件夹主键
private String folder_name;//文件夹名称
private String folder_url;//文件夹url

public String getDeedbox_guid() {
	return deedbox_guid;
}
public void setDeedbox_guid(String deedbox_guid) {
	this.deedbox_guid = deedbox_guid;
}
public String getSuperior_guid() {
	return superior_guid;
}
public void setSuperior_guid(String superior_guid) {
	this.superior_guid = superior_guid;
}
public String getFolder_guid() {
	return folder_guid;
}
public void setFolder_guid(String folder_guid) {
	this.folder_guid = folder_guid;
}
public String getFolder_name() {
	return folder_name;
}
public void setFolder_name(String folder_name) {
	this.folder_name = folder_name;
}
public String getFolder_url() {
	return folder_url;
}
public void setFolder_url(String folder_url) {
	this.folder_url = folder_url;
}
public String getDeedbox_name() {
	return deedbox_name;
}
public void setDeedbox_name(String deedbox_name) {
	this.deedbox_name = deedbox_name;
}

}
