package net.risesoft.approve.entity.senate;

public class WindowsSenateReportVO {
	private String windowName;//受理单位
	private String total;//业务总量
	private String pjl;//评价总量
	private String pjlv;//评价率
	private String mylv;//满意率
	private String bmylv;//不满意率
	private String bestnum;//满意
	private String goodnum;//一般
	private String badnum;//不满意
	private String tdbh;//态度不好
	private String sjtc;//时间太长
	private String ywbs;//业务不熟
	private String qt;//其他
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getBestnum() {
		return bestnum;
	}
	public void setBestnum(String bestnum) {
		this.bestnum = bestnum;
	}
	public String getGoodnum() {
		return goodnum;
	}
	public void setGoodnum(String goodnum) {
		this.goodnum = goodnum;
	}
	public String getBadnum() {
		return badnum;
	}
	public void setBadnum(String badnum) {
		this.badnum = badnum;
	}
	public String getPjl() {
		return pjl;
	}
	public void setPjl(String pjl) {
		this.pjl = pjl;
	}
	public String getPjlv() {
		return pjlv;
	}
	public void setPjlv(String pjlv) {
		this.pjlv = pjlv;
	}
	public String getMylv() {
		return mylv;
	}
	public void setMylv(String mylv) {
		this.mylv = mylv;
	}
	public String getBmylv() {
		return bmylv;
	}
	public void setBmylv(String bmylv) {
		this.bmylv = bmylv;
	}
	public String getTdbh() {
		return tdbh;
	}
	public void setTdbh(String tdbh) {
		this.tdbh = tdbh;
	}
	public String getSjtc() {
		return sjtc;
	}
	public void setSjtc(String sjtc) {
		this.sjtc = sjtc;
	}
	public String getYwbs() {
		return ywbs;
	}
	public void setYwbs(String ywbs) {
		this.ywbs = ywbs;
	}
	public String getQt() {
		return qt;
	}
	public void setQt(String qt) {
		this.qt = qt;
	}
	public String getWindowName() {
		return windowName;
	}
	public void setWindowName(String windowName) {
		this.windowName = windowName;
	}
	
}
