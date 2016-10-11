package net.risesoft.actions.civilbankroll.home;

import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.bizbankroll.Statistical;
import net.risesoft.beans.civilbankroll.CivilApplicationBean;
import net.risesoft.beans.civilbankroll.CivilNoticeBean;
import net.risesoft.beans.civilbankroll.CivilPolicyBean;
import net.risesoft.services.civilbankroll.dynamic.ICivilDynamicService;
import net.risesoft.services.civilbankroll.notice.ICivilNoticeService;
import net.risesoft.services.civilbankroll.policy.ICivilPolicyService;
//@Controller
public class CivilHomeInfoAction extends BaseActionSupport{

	
	private static final long serialVersionUID = -7816141107871483845L;
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private List<CivilPolicyBean> civilPolicyList;
	private List<CivilNoticeBean> civilNoticeList;
	private List<CivilApplicationBean> civilApplicationList;
	private Statistical stat;
	
	@Resource
	ICivilDynamicService civilDynamicService;
	@Resource
	ICivilNoticeService civilNoticeService;
	@Resource
	ICivilPolicyService civilPolicyService;


	public Statistical getStat() {
		return stat;
	}


	public void setStat(Statistical stat) {
		this.stat = stat;
	}


	public List<CivilPolicyBean> getCivilPolicyList() {
		return civilPolicyList;
	}


	public void setCivilPolicyList(List<CivilPolicyBean> civilPolicyList) {
		this.civilPolicyList = civilPolicyList;
	}


	public List<CivilNoticeBean> getCivilNoticeList() {
		return civilNoticeList;
	}


	public void setCivilNoticeList(List<CivilNoticeBean> civilNoticeList) {
		this.civilNoticeList = civilNoticeList;
	}


	public List<CivilApplicationBean> getCivilApplicationList() {
		return civilApplicationList;
	}


	public void setCivilApplicationList(
			List<CivilApplicationBean> civilApplicationList) {
		this.civilApplicationList = civilApplicationList;
	}


	@Action(value = "/civilHomeInfo/homePage", 
			results = {@Result(name = "success" ,location = "/WEB-INF/page/civilbankroll/home/default.jsp")})
	public String defaultQuery(){
		try {
			//政策
			setCivilPolicyList(civilPolicyService.civilPolicyList(7));
			//公告
			setCivilNoticeList(civilNoticeService.civilNoticeList(7));
			//业务动态
			setCivilApplicationList(civilDynamicService.homeDynamicList(25));
			setStat(civilDynamicService.getStatistical());
			return SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
}
