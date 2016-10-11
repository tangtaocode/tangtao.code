/**
 * @Project Name:LGOneHome
 * @File Name: OverTimeApproveSmsTimer.java
 * @Package Name: net.risesoft.timer
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date 2015年12月14日 上午9:25:59
 */

package net.risesoft.timer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.risesoft.common.util.ConstUtils;
import net.risesoft.common.util.DateX;
import net.risesoft.controller.checkdata.CheckDataController;
import net.risesoft.lhsms.webservice.SmsSenderUtil;
import net.risesoft.service.CheckDataService;
import net.risesoft.service.IOneHomeService;
import net.risesoft.service.ISmsService;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: OverTimeApproveSmsTimer.java
 * @Description: 系统自动自动获取即将超期件短信提醒待办用户
 *
 * @author tt
 * @date 2015年12月14日 上午9:25:59
 * @version 
 * @since JDK 1.6
 */
@Component
public class OverTimeApproveSmsTimer {
	private static Logger logger = Logger.getLogger(OverTimeApproveSmsTimer.class);
	@Resource
	private ISmsService iSmsService;
	@javax.annotation.Resource
	private IOneHomeService oneHomeService;
	@javax.annotation.Resource
	private CheckDataService checkDataService;
	
	
	/**
	 * 
	* @Title: autoCheckData 
	* @Description:定时自动执行数据检查
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@Scheduled(cron = "${autoCheckData}")
	public void autoCheckData(){
		oneHomeService.handworkCheckData();
		String date = DateX.getStandardDateTimeText(new Date());
		logger.info("========自动执行数据检查在"+date+"执行了一次===========");
	}
	/**
	 * 
	* @Title: autoSms 
	* @Description:定时自动执行发送短信
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	*  */
	@Scheduled(cron = "${autoSms}")
	public void autoSms(){
		List<Map<String,Object>> smslist = iSmsService.getSmsPersonList();
		String date = DateX.getStandardDateTimeText(new Date());
		logger.info("========自动执行发送短信"+date+"执行了一次===========");
		String overtimecounts = checkDataService.getHourCounts();
		String rulecounts = checkDataService.getJianChaCounts();
		String content = "";
		List<String> mobilelist = new ArrayList<String>();
		if(smslist.size()>0){
			for(Map temp:smslist){
				mobilelist.add((String)temp.get("MOBILE_PHONE"));
			}
//			if(!"0".equals(overtimecounts)){
//				content = ConstUtils.SMS_OVERTIME.replace("M", overtimecounts);
//				SmsSenderUtil.getInstance().sendSms(mobilelist, content);
//				System.out.println("=======数据超时短信内容"+content);
//			}
			if(!"0".equals(rulecounts)){
				content = ConstUtils.SMS_RULE.replace("M", rulecounts);
				SmsSenderUtil.getInstance().sendSms(mobilelist, content);
				System.out.println("=======数据规则短信内容"+content);
			}
		}
		
	}
}
