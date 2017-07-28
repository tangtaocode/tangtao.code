/**
 * @Project Name:risenet-sp-approve
 * @File Name: XzqlXzspListspoint.java
 * @Package Name: net.risesoft.approve.entity.jpa.together
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2016,RiseSoft  All Rights Reserved.
 * @date 2016年5月17日 上午11:17:38
 */
package net.risesoft.approve.entity.jpa.together;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @ClassName: XzqlXzspListspoint.java
 * @Description: TODO
 *
 * @author chenbingni
 * @date 2016年5月17日 上午11:17:38
 * @version 
 * @since JDK 1.7
 */
@Entity
@Table(name = "XZQL_XZSP_LISTSPOINT")
public class XzqlXzspListspoint  implements Serializable {

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 1L;
	
		private String listsguid;//VARCHAR2(38) not null,
		private String listsname;//VARCHAR2(3000),
		private String situation;//VARCHAR2(3000),
		private String reqirements;//VARCHAR2(3000),
		private String standards;//VARCHAR2(3000),
		private String clbh;//VARCHAR2(200),
		private String cjbm;//VARCHAR2(200),
		private String ybbh;//VARCHAR2(3000),
		private String kbbh;//VARCHAR2(3000),
		private String points;//VARCHAR2(3000)
		private List<XzqlXzspListsps> ps;//提交要点
		
		@Id
		@Column(name="LISTSGUID",length=38,nullable=false)
		public String getListsguid() {
			return listsguid;
		}
		public void setListsguid(String listsguid) {
			this.listsguid = listsguid;
		}
		@Column(name="LISTSNAME",length=3000)
		public String getListsname() {
			return listsname;
		}
		public void setListsname(String listsname) {
			this.listsname = listsname;
		}
		@Column(name="SITUATION",length=3000)
		public String getSituation() {
			return situation;
		}
		public void setSituation(String situation) {
			this.situation = situation;
		}
		@Column(name="REQIREMENTS",length=3000)
		public String getReqirements() {
			return reqirements;
		}
		public void setReqirements(String reqirements) {
			this.reqirements = reqirements;
		}
		@Column(name="STANDARDS",length=3000)
		public String getStandards() {
			return standards;
		}
		public void setStandards(String standards) {
			this.standards = standards;
		}
		@Column(name="CLBH",length=200)
		public String getClbh() {
			return clbh;
		}
		public void setClbh(String clbh) {
			this.clbh = clbh;
		}
		@Column(name="CJBM",length=200)
		public String getCjbm() {
			return cjbm;
		}
		public void setCjbm(String cjbm) {
			this.cjbm = cjbm;
		}
		@Column(name="YBBH",length=3000)
		public String getYbbh() {
			return ybbh;
		}
		public void setYbbh(String ybbh) {
			this.ybbh = ybbh;
		}
		@Column(name="KBBH",length=3000)
		public String getKbbh() {
			return kbbh;
		}
		public void setKbbh(String kbbh) {
			this.kbbh = kbbh;
		}
		@Column(name="POINTS",length=3000)
		public String getPoints() {
			return points;
		}
		public void setPoints(String points) {
			this.points = points;
		}
		@Transient
		public List<XzqlXzspListsps> getPs() {
			return ps;
		}
		public void setPs(List<XzqlXzspListsps> ps) {
			this.ps = ps;
		}
		
		

}

