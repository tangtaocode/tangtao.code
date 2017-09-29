package invengo.cn.pay.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mysql.fabric.xmlrpc.base.Member;

import invengo.cn.pay.Application;
import invengo.cn.pay.model.CacheObject;
import invengo.cn.pay.model.access.MemberAccess;
import invengo.cn.pay.service.IAccessPushService;

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PushOrderAccessTest {

	  @Autowired
	  private IAccessPushService accessPushService;
	  
	  *//**   
	 * @Title: pushOrderTest   
	 * @Description: TODO(pushOrder推送订单流水测试)   
	 * @param:       
	 * @return: void      
	 * @throws   
	 *//* 
	@Test
	public void pushOrderTest() {
		CacheObject pushobj = new CacheObject();
		pushobj.setOut_trade_no("20170927297994047033010655100483");
		pushobj.setDevice_info("e0:76:d0:5d:17:d3");
		pushobj.setUserId(1L);
		accessPushService.pushOrder(pushobj);
	}
	
	*//**   
	 * @Title: pushWxOrderTest   
	 * @Description: TODO(微信会员小票推送第三方接口测试)   
	 * @param:       
	 * @return: void      
	 * @throws   
	 *//* 
	@Test
	public void pushWxOrderTest() {
		//构造条件
		CacheObject pushobj = new CacheObject();
		pushobj.setOut_trade_no("20170927297994047033010655100483");
		pushobj.setDevice_info("e0:76:d0:5d:17:d3");
		pushobj.setUserId(1L);
		//构造会员
		MemberAccess member = new MemberAccess();
		member.setId("test");
		member.setMerchantId("20");
		member.setMobile("17512034328");
		
		pushobj.setMember(member);
		//调用pushWXOrder方法,进而调用第三方微信会员小票推送接口
		accessPushService.pushWxOrder(pushobj);
	}
	
}*/
