package net.risesoft.utils.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
/**
 * 
  * @ClassName: LoginRequired
  * @Description: 权限注解
  * @author Comsys-zhangkun
  * @date Jun 1, 2013 5:20:48 PM
  *
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME) 
@Target(ElementType.METHOD)
@Documented 
public @interface LoginRequired{
	String module();
}
