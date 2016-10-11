package net.risesoft.actions.bizbankroll.home;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.bizbankroll.BizApplicationBean;
import net.risesoft.beans.bizbankroll.BizNoticeBean;
import net.risesoft.beans.bizbankroll.ConsultationPhone;
import net.risesoft.beans.bizbankroll.Statistical;
import net.risesoft.daos.base.ISimpleJdbcDao;
import net.risesoft.services.bizbankroll.dynamic.IBizDynamicService;
import net.risesoft.services.bizbankroll.notice.IBizNoticeService;
@Controller
public class BizHomeInfoAction extends BaseActionSupport{

	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = 5286660334835445300L;
	private List<BizNoticeBean> bizNoticeList;
	private List<BizNoticeBean> bizPolicyList;
	private List<BizApplicationBean> bizApplicationList;
	private List<ConsultationPhone> contactPhoneList;
	private Statistical stat;
	@Resource
	IBizNoticeService bizNoticeService;
	@Resource
	IBizDynamicService bizDynamicService;
	@Resource 
	ISimpleJdbcDao<ConsultationPhone> simpleJdbcDao;
	


	public List<BizNoticeBean> getBizPolicyList() {
		return bizPolicyList;
	}


	public void setBizPolicyList(List<BizNoticeBean> bizPolicyList) {
		this.bizPolicyList = bizPolicyList;
	}


	public List<BizNoticeBean> getBizNoticeList() {
		return bizNoticeList;
	}


	public void setBizNoticeList(List<BizNoticeBean> bizNoticeList) {
		this.bizNoticeList = bizNoticeList;
	}


	public List<BizApplicationBean> getBizApplicationList() {
		return bizApplicationList;
	}


	public void setBizApplicationList(List<BizApplicationBean> bizApplicationList) {
		this.bizApplicationList = bizApplicationList;
	}


	public List<ConsultationPhone> getContactPhoneList() {
		return contactPhoneList;
	}


	public void setContactPhoneList(List<ConsultationPhone> contactPhoneList) {
		this.contactPhoneList = contactPhoneList;
	}


	public Statistical getStat() {
		return stat;
	}


	public void setStat(Statistical stat) {
		this.stat = stat;
	}


	@Action(value = "/bizHomeInfo/homePage", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/home/index.jsp")})
	public String defaultQuery(){
		try {
			//政策
			setBizPolicyList(bizNoticeService.getBizPolicyList(11));
			//公告
			setBizNoticeList(bizNoticeService.getBizNoticeList(11));
			//业务动态
			setBizApplicationList(bizDynamicService.getHomeDynamicList(25));
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	@Action(value = "/bizHomeInfo/connectPhone", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/bizbankroll/home/connectPhone.jsp")})
	public String connectPhone(){
		try {
			//电话
			String sql = "select t.name as prijectName,t.contactp as phone from zjfc_xmlxinfo t where t.contactp is not null order by t.pguid desc, t.tab_index";
			setContactPhoneList(simpleJdbcDao.queryForRow(sql, ConsultationPhone.class));
			setStat(bizDynamicService.getStatistical());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
}
