package net.risesoft.webservice.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.ws.BindingProvider;






import net.risesoft.common.util.ConstUtils;


/**
 * OA系统待办事项客户端
 * @author HJL
 * @date 2014年9月16日  下午6:32:11
 */
public class OAGtasksClient {

	/**
	 * 通过用户guid获取OA系统待办事项
	 * @param userGUID 用户guid
	 * @return
	 */
	public static List<Map<String, Object>> getOAGtasks(String userGUID,String loginName) throws Exception{
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		PortalsServerPortType service = new PortalsServer().getPortalsServerHttpPort();
		
		/**********设置超时时间	开始**********/
		Map<String, Object> ctxt = ((BindingProvider) service).getRequestContext();
		//ctxt.put(BindingProviderProperties.REQUEST_TIMEOUT, 3000); // Timeout in millis   
		//ctxt.put(BindingProviderProperties.CONNECT_TIMEOUT, 1000); // Timeout in millis   
		/**********设置超时时间	结束**********/
		
		ArrayOfDbsx arr = service.dbsxView(loginName);
		List<Dbsx> dl = arr.getDbsx();
		Map<String, Object> map = null;
		for(int i=0;i<dl.size();i++){
			map = new HashMap<String, Object>();
			map.put("TYPE_GUID", dl.get(i).getType().getValue());
			//map.put("URL",dl.get(i).getUrl().getValue().replace("http://localhost:8080", ConstUtils.OLDOAURL) );
			map.put("TITLE", dl.get(i).getTitle().getValue());
			map.put("BEGINDATE", dl.get(i).getPreSubmitDate().getValue());
			map.put("EMPLOYEE_GUID", userGUID);
			map.put("STATUS", dl.get(i).getJjcd().getValue());
			list.add(map);
		}
		return list;
	}
	
}

