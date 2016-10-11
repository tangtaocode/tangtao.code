package net.risesoft.actions.interaction;



import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.interaction.Complain;
import net.risesoft.beans.interaction.Consult;
import net.risesoft.services.interaction.IComplainService;
import net.risesoft.services.interaction.IConsultService;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

/**
 * 
  * @ClassName: InteractionAction
  * @Description: 政民互动
  * @author Comsys-zhangkun
  * @date May 21, 2013 5:43:14 PM
  *
 */

@Controller
public class InteractionAction extends BaseActionSupport {
	/**
	  * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	  */
	
	private static final long serialVersionUID = -1817031889743912794L;
	private List<Complain> compList = new ArrayList<Complain>();
	private List<Consult> consList = new ArrayList<Consult>();
	@Resource
	private IComplainService complainService;//投诉
	@Resource
	private IConsultService consultService;//咨询
	
	public List<Complain> getCompList() {
		return compList;
	}
	public void setCompList(List<Complain> compList) {
		this.compList = compList;
	}
	public List<Consult> getConsList() {
		return consList;
	}
	public void setConsList(List<Consult> consList) {
		this.consList = consList;
	}
	/**
	 * 
	  * @Title: interaction
	  * @Description: 正民互动首页
	  * @param @return
	  * @param @throws Exception    设定文件
	  * @return String    返回类型
	  * @throws
	 */
	@Action(value = "/interaction/interactionAction", results = { @Result(name = "success", location = "/WEB-INF/page/interaction/index.jsp") })
	public String interaction() throws Exception {
		LinkedHashMap<String, String> orderMap = new LinkedHashMap<String, String>();
		orderMap.put("complaindate", "desc");
		setCompList(complainService.getScrollData(1, 20, orderMap).getResultList());
		orderMap.clear();
		orderMap.put("consultationdate", "desc");
		setConsList(consultService.getScrollData(1, 20, orderMap).getResultList());
		return SUCCESS;
	}
	@Action(value = "/interaction/appraiseAction", results = { @Result(name = "success", location = "/WEB-INF/page/interaction/appraise.jsp") })
	public String appraise() throws Exception {
		return SUCCESS;
	}
	
}
