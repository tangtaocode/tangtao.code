/**
 * @ProjectName:zjjHome
 * @FileName: DataSourceAspect.java
 * @PackageName: DynamicDataSource
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 9, 2015 6:02:51 PM
 */
package net.risesoft.annotation;

import java.lang.reflect.Method;

import net.risesoft.annotation.DataSource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

/**
 * @ClassName: DataSourceAspect.java
 * @Description: TODO
 *
 * @author kun
 * @date May 9, 2015 6:02:51 PM
 * @version 
 * @since JDK 1.6
 */
public class DataSourceAspect {
	public void before(JoinPoint point){
        Object target = point.getTarget();
        String method = point.getSignature().getName();
        Class<?>[] classz = target.getClass().getInterfaces();
        Class<?>[] parameterTypes = ((MethodSignature) point.getSignature())
                .getMethod().getParameterTypes();
        try {
            Method m = classz[0].getMethod(method, parameterTypes);
            if (m != null && m.isAnnotationPresent(DataSource.class)) {
                DataSource data = m
                        .getAnnotation(DataSource.class);
                DynamicDataSource.setCustomerType(data.value());
                System.out.println("======="+data.value());
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
}

