package com.tongdao.core.Dao.beans;

import com.tongdao.core.Dao.sql.where.C;

import java.io.Serializable;

public class Method {

	/**
	 * where条件
	 * @param pram
	 * @return
	 */
//	public static WherePrams where(String pram){
//		return new WherePrams(pram);
//	}
	/**
	 * where重写
	 * @param
	 * @return
	 */
	public static WherePrams where(String file, String where, Serializable value){
			return new WherePrams(file , where , value);
	}
	public static WherePrams where(String file, C c, Serializable value){
		String where = C.getSqlWhere(c);
		return where(file, where, value);
	}

	public static WherePrams orderByandLimt(String order,Integer limit){
		return new WherePrams(order,limit);
	}

	public static WherePrams createDefault(){
		return new WherePrams(null, null, null);
	}
	
}
