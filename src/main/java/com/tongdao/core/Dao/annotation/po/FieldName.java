package com.tongdao.core.Dao.annotation.po;

import java.lang.annotation.*;

/**
 * 实体类字段约束注解
 * 标有此注解的字段对应数据库中的字段名强制约束为该注解中的name值
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface FieldName {

	String name() default com.tongdao.core.Dao.annotation.po.PUBVALUE.FIELD_NAME_DEFAUL_VALUE;
}
