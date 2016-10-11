/**
 * @ProjectName:zjjHome
 * @FileName: DataSource.java
 * @PackageName: net.risesoft.annotation
 * @Company:北京有生博大软件有限公司（深圳分公司）
 * @Copyright (c) 2015,RiseSoft  All Rights Reserved.
 * @date May 9, 2015 5:59:07 PM
 */
package net.risesoft.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @ClassName: DataSource.java
 * @Description: 数据源切换注解
 *
 * @author kun
 * @date May 9, 2015 5:59:07 PM
 * @version 
 * @since JDK 1.6
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
@Documented 
public @interface DataSource {
	String value();
}

