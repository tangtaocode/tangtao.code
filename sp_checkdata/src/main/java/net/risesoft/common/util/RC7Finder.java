package net.risesoft.common.util;

import java.util.HashMap;
import java.util.Map;





import egov.appservice.asf.exception.ServiceClientException;
import egov.appservice.asf.service.ServiceClient;
import egov.appservice.asf.service.ServiceClientFactory;
/**
 * 
 * @author wjm
 *
 */
public abstract class RC7Finder {
	
	private static ServiceClient sc = null;
	
	@SuppressWarnings("rawtypes")
	private static Map services = new HashMap();
	
	@SuppressWarnings("unchecked")
	public static <T> T getServiceManagerClient(String serviceName){
		if(sc == null){
			sc = ServiceClientFactory.getServiceClient();
		}
		T service = null;
		if(services.containsKey(serviceName)){
			return (T)services.get(serviceName);
		}
		try{
			service = sc.getServiceByName(serviceName);
			services.put(serviceName, service);
			return service;
		}catch(ServiceClientException sce){
			sce.printStackTrace();
			return null;
		}		
	}					 
	


}
