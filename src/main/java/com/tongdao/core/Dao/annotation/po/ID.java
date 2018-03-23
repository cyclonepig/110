package com.tongdao.core.Dao.annotation.po;

import java.lang.annotation.*;

/**
 * 定义PO类的键类型
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ID {

	/**
	 * 主键数据类型，默认整数型
	 * @return
	 */
	int type() default com.tongdao.core.Dao.annotation.po.PUBVALUE.TABLE_ID_TYPE_INTEGER;
	
}
