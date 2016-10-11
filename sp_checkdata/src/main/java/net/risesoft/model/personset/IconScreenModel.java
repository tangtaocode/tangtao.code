package net.risesoft.model.personset;

import java.io.Serializable;




/**
 * ��ҳ��������
 * @author weigong1989
 *
 */
public class IconScreenModel implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String icon_guid;	//guid
	
	private String employee_guid;	//Ա��guid
	
	private String icon_name;	//��������������
	
	private Integer  icon_sort;		//��������
	
	public IconScreenModel(){}

	public String getIcon_guid() {
		return icon_guid;
	}

	public void setIcon_guid(String icon_guid) {
		this.icon_guid = icon_guid;
	}

	public String getEmployee_guid() {
		return employee_guid;
	}

	public void setEmployee_guid(String employee_guid) {
		this.employee_guid = employee_guid;
	}

	public String getIcon_name() {
		return icon_name;
	}

	public void setIcon_name(String icon_name) {
		this.icon_name = icon_name;
	}

	public Integer getIcon_sort() {
		return icon_sort;
	}

	public void setIcon_sort(Integer icon_sort) {
		this.icon_sort = icon_sort;
	}

}
