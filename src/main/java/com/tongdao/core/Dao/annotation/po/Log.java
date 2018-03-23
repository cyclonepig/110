package com.tongdao.core.Dao.annotation.po;

import java.lang.annotation.*;

/**
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
	String action() default "null";
	
}
