package net.risesoft.model.personset;



/**
 * 首页导航子屏
 * @author weigong1989
 *
 */
public class Icosubscrmodel {
	
	private String sub_guid;	//guid
	
	private String icon_guid;	//分屏guid
	
	private String ziyuan_guid;	//资源guid
	
	private Integer  sub_sort;		//子屏排序

	public String getSub_guid() {
		return sub_guid;
	}

	public void setSub_guid(String sub_guid) {
		this.sub_guid = sub_guid;
	}

	public String getIcon_guid() {
		return icon_guid;
	}

	public void setIcon_guid(String icon_guid) {
		this.icon_guid = icon_guid;
	}

	public String getZiyuan_guid() {
		return ziyuan_guid;
	}

	public void setZiyuan_guid(String ziyuan_guid) {
		this.ziyuan_guid = ziyuan_guid;
	}

	public Integer getSub_sort() {
		return sub_sort;
	}

	public void setSub_sort(Integer sub_sort) {
		this.sub_sort = sub_sort;
	}

	
}
