package net.risesoft.actions.interaction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import net.risesoft.actions.base.BaseActionSupport;
import net.risesoft.beans.base.CodeMap;
import net.risesoft.beans.base.PageView;
import net.risesoft.beans.onlineservice.BusinessInfo;
import net.risesoft.beans.user.UserInfo;
import net.risesoft.common.Common;
import net.risesoft.services.interaction.IDynamicStateService;
import net.risesoft.utils.base.GenericsUtils;
import net.risesoft.utils.base.ICodeMapUtil;

/*******************************************************************************
 * @author 张坤
 * @用途 办事查询模块Action
 ******************************************************************************/
@Controller
@ParentPackage("default")
public class DynamicStateAction extends BaseActionSupport {
	/**
	 * @Fields serialVersionUID : TODO（用一句话描述这个变量表示什么）
	 */

	private static final long serialVersionUID = -1308438374692805664L;
	@Resource
	private ICodeMapUtil codeMapUtil;
	@Resource
	private IDynamicStateService dynamicStateService;
	private String yxtywlsh;// 业务流水号
	private String organizationCode;// 部门组织机构编码
	private String cardId;// 申请人证件编号
	private String personName;// 申请人姓名
	private String sqsj_S;// 申报开始时间
	private String sqsj_E;// 申报结束时间
	private String checkCode;// 查询验证码
	private String isCheck;// 是否需要验证验证码
	private String method;
	private String checkType;//查询类型（结果公示时使用到）
	private List<CodeMap> departList = new ArrayList<CodeMap>();

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCheckType() {
		return checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	public List<CodeMap> getDepartList() {
		return departList;
	}

	public void setDepartList(List<CodeMap> departList) {
		this.departList = departList;
	}

	public String getYxtywlsh() {
		return yxtywlsh;
	}

	public void setYxtywlsh(String yxtywlsh) {
		this.yxtywlsh = yxtywlsh;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getOrganizationCode() {
		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getSqsj_S() {
		return sqsj_S;
	}

	public void setSqsj_S(String sqsj_S) {
		this.sqsj_S = sqsj_S;
	}

	public String getSqsj_E() {
		return sqsj_E;
	}

	public void setSqsj_E(String sqsj_E) {
		this.sqsj_E = sqsj_E;
	}

	/***************************************************************************
	 * @author zhangkun
	 * @Description: 查询结果
	 **************************************************************************/
	@Action(value = "/interaction/queryResult", results = { 
			@Result(name = "success", location = "/WEB-INF/page/onlineservice/resultBusiness.jsp"),
			@Result(name = "input", location = "/WEB-INF/page/interaction/queryBusiness.jsp")})
	public String queryResult() {
		if (StringUtils.isBlank(getMethod())) {
			setDepartList(codeMapUtil.getListByType("罗湖区审批职能局机构代码"));
			return INPUT;
		} else {
			String code = (String) getSession().getAttribute(
					Common.sessionCheckCode);
			boolean flag = false;
			// 如果为true则检验验证码
			if ("true".equals(getIsCheck())) {
				if (StringUtils.isNotBlank(getCheckCode())
						&& getCheckCode().toLowerCase().equals(
								code.toLowerCase())) {
					flag = true;
				}
			} else {
				flag = true;
			}
			// 只有在检查验证码情况下才会出现false
			if (flag) {
				UserInfo user = (UserInfo) getSession().getAttribute(
						Common.sessionLoginUserID);
				String whereSql = "";
				int maxResult = 10;
				ArrayList<Object> param = new ArrayList<Object>();
				if (StringUtils.isNotBlank(getYxtywlsh())) {
					whereSql += " and (t.yxtywlsh like ? or t.sblsh like ?) ";
					param.add("%" + getYxtywlsh().trim() + "%");
					param.add("%" + getYxtywlsh().trim() + "%");
				}

				if (StringUtils.isNotBlank(getOrganizationCode())) {
					whereSql += " and t.sljgzzjgdm=? ";
					param.add(getOrganizationCode());
				}
				if (user != null) {
					whereSql += " and d.zhengjiandaima=? ";
					param.add(user.getCardid());
				} else if (StringUtils.isNotBlank(getCardId())) {
					whereSql += " and d.zhengjiandaima=? ";
					param.add(getCardId());
				}
				if (StringUtils.isNotBlank(getPersonName())) {
					whereSql += " and t.sqdwhsqrxm like ?";
					param.add("%" + getPersonName() + "%");
				}
				if (StringUtils.isNotBlank(getSqsj_S())) {
					whereSql += " and to_char(t.slsj,'yyyy-MM-dd')>=?";
					param.add(getSqsj_S());
				}
				if (StringUtils.isNotBlank(getSqsj_E())) {
					whereSql += " and to_char(t.slsj,'yyyy-MM-dd')<=?";
					param.add(getSqsj_E());
				}
				if("jggs".equals(checkType)){//特殊处理结果查询搜索
					whereSql += " and b.xkyf is not null ";
				}
				String sql = "select distinct t.yxtywlsh,t.sblsh,  t.sljgmc, t.spsxmc, "
						+ " t.sqdwhsqrxm, to_char(t.slsj, 'yyyy-MM-dd') slsj, " 
						+ "case when b.bjjg='6' then decode(b.xkyf,'0','办结','1','办结（不予许可）') else "
						+ " decode(b.bjjg,'0', '出证办结', '1',  '退回办结','2', '作废办结', '3', "
						+ "'删除办结',  '4',  '转报办结', '5', "
						+ " '补交不来办结',  null,  '办理中',  '办结') end blzt "
						+ " from (select *  from t_shouli s  where not exists (select 1 "
						+ " from t_shouli l where s.sjbbh < l.sjbbh "
						+ " and s.ywlsh = l.ywlsh)) t, office_spi_declareinfo d, "
						+ " T_BANJIE b  where d.declaresn(+) = t.yxtywlsh  and b.ywlsh(+) = t.ywlsh "
						+ " and t.yxtywlsh not in (select s.declaresn from spi_supervise_back s) "
						+ whereSql + "order by slsj desc";
				try {
					PageView<BusinessInfo> pageView = new PageView<BusinessInfo>(
							maxResult, getPage(), dynamicStateService
									.getDataRows(sql.toString(), GenericsUtils
											.listToArray(param)));
					param.add(pageView.getStartIndex());
					param.add(pageView.getEndIndex());
					pageView.setRecords(dynamicStateService.getMoreDynamic(sql
							.toString(), GenericsUtils.listToArray(param)));
					getRequest().setAttribute("pageView", pageView);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return SUCCESS;
			} else {
				outJson("{'message':'0'}", null);
				return null; // 验证码输入错误，可在这里进行返回提示处理
			}
		}

	}
	
	/***************************************************************************
	 * @author liuhua
	 * @Description: 我要查询（业务过程状态）
	 **************************************************************************/
	@Action(value = "/interaction/queryResultStus", results = { 
			@Result(name = "success", location = "/WEB-INF/page/onlineservice/resultBusiness.jsp"),
			@Result(name = "input", location = "/WEB-INF/page/interaction/queryBusiness.jsp")})
	public String queryResultStus() {
		if (StringUtils.isBlank(getMethod())) {
			setDepartList(codeMapUtil.getListByType("罗湖区审批职能局机构代码"));
			return INPUT;
		} else {
			String code = (String) getSession().getAttribute(
					Common.sessionCheckCode);
			boolean flag = false;
			// 如果为true则检验验证码
			if ("true".equals(getIsCheck())) {
				if (StringUtils.isNotBlank(getCheckCode())
						&& getCheckCode().toLowerCase().equals(
								code.toLowerCase())) {
					flag = true;
				}
			} else {
				flag = true;
			}
			// 只有在检查验证码情况下才会出现false
			if (flag) {
				UserInfo user = (UserInfo) getSession().getAttribute(
						Common.sessionLoginUserID);
				String whereSql = "";
				int maxResult = 10;
				ArrayList<Object> param = new ArrayList<Object>();
				if (StringUtils.isNotBlank(getYxtywlsh())) {
					whereSql += " and (t.yxtywlsh like ? or t.sblsh like ?) ";
					param.add("%" + getYxtywlsh().trim() + "%");
					param.add("%" + getYxtywlsh().trim() + "%");
				}

				if (StringUtils.isNotBlank(getOrganizationCode())) {
					whereSql += " and t.sljgzzjgdm=? ";
					param.add(getOrganizationCode());
				}
				if (user != null) {
					whereSql += " and d.zhengjiandaima=? ";
					param.add(user.getCardid());
				} else if (StringUtils.isNotBlank(getCardId())) {
					whereSql += " and d.zhengjiandaima=? ";
					param.add(getCardId());
				}
				if (StringUtils.isNotBlank(getPersonName())) {
					whereSql += " and t.sqdwhsqrxm like ?";
					param.add("%" + getPersonName() + "%");
				}
				if (StringUtils.isNotBlank(getSqsj_S())) {
					whereSql += " and to_char(t.slsj,'yyyy-MM-dd')>=?";
					param.add(getSqsj_S());
				}
				if (StringUtils.isNotBlank(getSqsj_E())) {
					whereSql += " and to_char(t.slsj,'yyyy-MM-dd')<=?";
					param.add(getSqsj_E());
				}
				if("jggs".equals(checkType)){//特殊处理结果查询搜索
					whereSql += " and b.xkyf is not null ";
				}
				String sql = "select distinct t.yxtywlsh,t.sblsh,  t.sljgmc, t.spsxmc, "
						+ " t.sqdwhsqrxm, to_char(t.slsj, 'yyyy-MM-dd') slsj, " 
						+ "case when b.bjjg='6' then decode(b.xkyf,'0','办结','1','办结（不予许可）') else "
						+ " decode(b.bjjg,'0', '出证办结', '1',  '退回办结','2', '作废办结', '3', "
						+ "'删除办结',  '4',  '转报办结', '5', "
						+ " '补交不来办结',  null,  '办理中',  '办结') end blzt "
						+ " from (select *  from t_shouli s  where not exists (select 1 "
						+ " from t_shouli l where s.sjbbh < l.sjbbh "
						+ " and s.ywlsh = l.ywlsh)) t, office_spi_declareinfo d, "
						+ " T_BANJIE b  where d.declaresn(+) = t.yxtywlsh  and b.ywlsh(+) = t.ywlsh "
						+ " and t.yxtywlsh not in (select s.declaresn from spi_supervise_back s) "
						+ whereSql + "order by slsj desc";
				try {
					PageView<BusinessInfo> pageView = new PageView<BusinessInfo>(
							maxResult, getPage(), dynamicStateService
									.getDataRows(sql.toString(), GenericsUtils
											.listToArray(param)));
					param.add(pageView.getStartIndex());
					param.add(pageView.getEndIndex());
					pageView.setRecords(dynamicStateService.getMoreDynamic(sql
							.toString(), GenericsUtils.listToArray(param)));
					getRequest().setAttribute("pageView", pageView);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return SUCCESS;
			} else {
				outJson("{'message':'0'}", null);
				return null; // 验证码输入错误，可在这里进行返回提示处理
			}
		}

	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}
}
