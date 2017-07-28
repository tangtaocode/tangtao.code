package net.risesoft.approve.risefile.commons.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wangdong
 *	map--key list---object
 *				 ---object
 *				 ---object
 *		 key list
 **				 ---object
 *				 ---object
 *的数据结构
 */
public class ListInMap {

	private Map map=new HashMap();
	
	public List get(Object key){
		return (List)map.get(key);
	}
	
	public void put(Object key,Object value){
		List l=(List)map.get(key);
		if(l==null){
			l=new ArrayList();
			map.put(key,l);
		}
		l.add(value);
	}

}
