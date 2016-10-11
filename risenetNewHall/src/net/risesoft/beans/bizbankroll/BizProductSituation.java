package net.risesoft.beans.bizbankroll;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 
  * @ClassName: BizProductSituation
  * @Description: 上年度具有自主知识产权
  * @author Comsys-zhangkun
  * @date Apr 10, 2013 4:42:35 PM
  *
 */
@Entity
@Table(name="ZJFC_KJ_PRODUCTQK")
public class BizProductSituation implements Serializable{
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 8684549283869625037L;
	private String guid;  //
	private String appguid;  //申请扶持项目GUID
	private Float productionmarketing;  //产品销售收入
	private Float duty;  //纳税总额
	private Float profit;  //净利润
	private Float appreciation;  //其中增值税总额
	private String userguid;  //操作用户
	private Date createtime;  //提交时间
	@Id
	public String getGuid() {
		return guid;
	}
	public void setGuid(String guid) {
		this.guid = guid;
	}
	@Column
	public String getAppguid() {
		return appguid;
	}
	public void setAppguid(String appguid) {
		this.appguid = appguid;
	}
	@Column
	public Float getProductionmarketing() {
		return productionmarketing;
	}
	public void setProductionmarketing(Float productionmarketing) {
		this.productionmarketing = productionmarketing;
	}
	@Column
	public Float getDuty() {
		return duty;
	}
	public void setDuty(Float duty) {
		this.duty = duty;
	}
	@Column
	public Float getProfit() {
		return profit;
	}
	public void setProfit(Float profit) {
		this.profit = profit;
	}
	@Column
	public Float getAppreciation() {
		return appreciation;
	}
	public void setAppreciation(Float appreciation) {
		this.appreciation = appreciation;
	}
	@Column
	public String getUserguid() {
		return userguid;
	}
	public void setUserguid(String userguid) {
		this.userguid = userguid;
	}
	@Column
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

}
