package net.risesoft.approve.service;

import java.util.List;
import java.util.Map;

import net.risesoft.approve.entity.base.Pager;

import net.risesoft.approve.entity.jpa.Score;

public interface DailyManagementService {

		
	/**
	 * 查询窗口人员
	 */
	public Pager findWindowPerson(Pager pager,String userName,String department,String sel,String enrollnumber);
	/**
	 * 按日期查询窗口人员
	 */
	public Pager findDatePerson(Pager pager,String date,String department,String enrollnumber,String userName,String sel);
	/**
	 * 保存大厅人员评分
	 */
	public void saveScore(Score initscore,String scoreguid);
	/**
	 * 查询人员
	 */
	public Score find(String scoreGuid);
	/**
	 * 查询奖惩窗口人员
	 */
	public Pager showJcWindowPerson(Pager pager,String name);
	/**
	 * 查询考评记录
	 */
	public Pager findRecord(Pager pager,String date,String userId);
	/**
	 * 月考评表查找
	 */
	 public Score findMonth(Score score,String mounthguid);
	 /**
	  * 删除记录
	  */
	 public void delectScore(String ScoreGuid);
	 /**
	  * 查询个人信息
	  */
	 public int findperson(String mouthguid,String time);
	 /**
	  * 增加奖惩记录
	  */
	 public void addjcjl(String scoreGuid,int rewards,int punishment,int complaints,int praise);
	 /**
	  * 查找时间是否存在
	  */
	 public boolean findis(String time,String userGuid);
	 /**
	  * 按月份查找评分
	  */
	 public Pager findMonthRecord(Pager pager,String date,String userid);

}
