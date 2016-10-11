package net.risesoft.common.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * 
 * @author Jackmen wei(韦杰民)2014年5月25日
 *
 */
public class MapUtil {
	
	public final static void toLowerCase(Map<String, Object> map){
		Map<String, Object> hm = new HashMap<String, Object>();
		if(map != null){
			hm.putAll(map);
			map.clear();
			Iterator<String> itr = hm.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				map.put(key.toLowerCase(), hm.get(key));
			}
		}		
	}
	
	public final static void toUpperCase(Map<String, Object> map){
		Map<String, Object> hm = new HashMap<String, Object>();
		if(map != null){
			hm.putAll(map);
			map.clear();
			Iterator<String> itr = hm.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				map.put(key.toUpperCase(), hm.get(key));
			}
		}
	}
	
	public final static void fillFromMap(Map<String, Object> toMap, Map<String, Object> fromMap){
		if(toMap != null && fromMap != null){
			Iterator<String> itr = fromMap.keySet().iterator();
			while(itr.hasNext()){
				String key = itr.next();
				if(fromMap.get(key) != null && (!toMap.containsKey(key)) || toMap.get(key) == null){
					toMap.put(key, fromMap.get(key));
				}
			}
		}
	}

}
