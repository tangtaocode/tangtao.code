package net.risesoft.util;

import java.util.List;
import java.util.TreeMap;

import net.risesoft.approve.entity.jpa.CodeMap;



public class Common {
	public static TreeMap<String,TreeMap<String,String>> tmKeyCodeMap; //codeMap 存储Map;
	public static TreeMap<String,List<CodeMap>> tmKeyCodeList; //codeMap 存储list;
}
