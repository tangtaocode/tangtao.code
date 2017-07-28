package net.risesoft.approve.entity.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
/**
 * @codemap数据字段
 * 
 * @author 
 * @Date 2013-1-24 上午10:50:24
 */
@Entity
@Table(name="XZQL_CODEMAP")
public class CodeMap implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5429393397251908670L;
	private String id;  //主键ID
	private String type;  //类别名称：如公开形式
	private String key;  //键内容：如：001、002
	private String value;  //值内容:如：文件、公开栏
	private Integer orderno;  //排序：用于显示的顺序
	private String remark;  //备注说明
	private String status;  //是否有效：1、有效，0、无效
	private List<CodeMap> codeList;
	@Transient
	public List<CodeMap> getCodeList() {
		return codeList;
	}
	public void setCodeList(List<CodeMap> codeList) {
		this.codeList = codeList;
	}
	@Id
	@Column(name="ID", unique=true, nullable=false, length=38)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Column(name="TYPE")
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Column(name="CODE")
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	@Column(name="VALUE")
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Column(name="ORDERNO")
	public Integer getOrderno() {
		return orderno;
	}
	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}
	@Column(name="REMARK")
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Column(name="STATUS")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
