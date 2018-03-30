package com.tongdao.core.Dao.sql.exception;

/**
 * 内部异常类：表示实体类的ID字段类型与数据库类型不匹配
 * 解决方案：更正主键类型
 */
public class AiyiIdTypeException extends Exception{

	private static final Long serialVersionUID = 1L;
	
	public AiyiIdTypeException(String message){
		 super(message);
	}

}
