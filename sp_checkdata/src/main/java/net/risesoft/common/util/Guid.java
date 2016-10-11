package net.risesoft.common.util;

import java.util.UUID;

public class Guid {

	/*
	 * 根据UUID生成guid
	 */
	public synchronized static String genGuid()
	{
		return "{"+UUID.randomUUID().toString().toUpperCase()+"}";
	}
}
