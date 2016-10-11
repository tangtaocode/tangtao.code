package net.risesoft.services.system;

import net.risesoft.beans.system.MessageConfig;
import net.risesoft.daos.base.IBaseDao;

public interface ISendMessageService extends IBaseDao<MessageConfig>{
	/**
	 * 
	  * @Title: sendMessage
	  * @Description: 发送短信
	  * @param @param mobile 手机号码
	  * @param @param messageCode  短信模板标示
	  * @param @param messParams 短信模板需要替换的标示符参数
	  * @return String    验证码类的返回验证码。不是验证码的返回插入影响行数
	  * @throws
	 */
	public boolean sendMessage(String mobile,String messageCode,String[] messParams)throws Exception;
	
	String handlerSms(String mobile,String messageCode,String[] messParams) throws Exception;
}
