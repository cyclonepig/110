package com.tongdao.core.Dao.util;

import com.tongdao.core.Dao.beans.FmtParm;
import com.tongdao.core.Dao.beans.Po;
import com.tongdao.core.Dao.beans.WherePrams;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL查询格式化工具类
 *
 */
public class FormatterSql implements Formatter {

	/**
	 * 格式化条件
	 */
	private List<FmtParm> fmtParms = new ArrayList<FmtParm>();
	
	
	@Override
	public  void addFmt(String fieldName, String select, Class<? extends Po> po, WherePrams where) {
		// TODO Auto-generated method stub
		fmtParms.add(new FmtParm(fieldName, select, po, where));
	}

	@Override
	public void addFmt(FmtParm parm) {
		// TODO Auto-generated method stub
		fmtParms.add(parm);
	}

	@Override
	public List<FmtParm> listFmtParm() {
		// TODO Auto-generated method stub
		return fmtParms;
	}

}
