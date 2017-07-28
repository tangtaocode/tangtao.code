package net.risesoft.approve.service;

import java.util.Map;

public interface ExchangeDataService {
	//从织网工程获取用户数据
	public Map<String,Object> getDataByZWGC(String cardId);
}
