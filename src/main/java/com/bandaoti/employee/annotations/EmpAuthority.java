package com.bandaoti.employee.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EmpAuthority {
	/**
	 * 允许访问的角色
	 * @return
	 */
	String[] value() default "";
	
	/**
	 * 拒绝访问的角色
	 * @return
	 */
	String[] reject() default "";
}
