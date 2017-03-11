package com.semgt.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReqField {
	public String name() default "";
	public int length() default 0;
	public boolean option() default false;
	public String pattern() default "";
	public String validator() default "default";
}
