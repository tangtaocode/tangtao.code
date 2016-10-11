package net.risesoft.beans.onlineservice;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: SearchKeyWord
  * @Description: 搜索热词
  * @author Comsys-zhangkun
  * @date Jul 1, 2013 5:47:52 PM
  *
 */
@Entity
@Table(name="XZQL_XZSP_SEARCHKEY")
public class SearchKeyWord {
	private String keyword;  //关键词
	private Integer searchnum;  //搜索次数
	private String type;  //关键词类型，待用
	@Id
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Column
	public Integer getSearchnum() {
		return searchnum;
	}
	public void setSearchnum(Integer searchnum) {
		this.searchnum = searchnum;
	}
	@Column
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
