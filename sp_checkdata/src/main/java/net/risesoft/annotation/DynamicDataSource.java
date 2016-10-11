/**
 * @ProjectName:zjjHome
 * @FileName: DynamicDataSource.java
 * @PackageName: net.risesoft.dao
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 8, 2015 6:00:18 PM
 */
package net.risesoft.annotation;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: DynamicDataSource.java
 * @Description: TODO
 *
 * @author kun
 * @date May 8, 2015 6:00:18 PM
 * @version 
 * @since JDK 1.6
 */
public class DynamicDataSource extends AbstractRoutingDataSource{
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
    public static void setCustomerType(String customerType) {
        contextHolder.set(customerType);
    }

	public static String getCustomerType() {
	        return contextHolder.get();
	}
	
	public static void clearCustomerType() {
	        contextHolder.remove();
	}
	
	@Override
	protected Object determineCurrentLookupKey() {
		return getCustomerType();
	}

}

