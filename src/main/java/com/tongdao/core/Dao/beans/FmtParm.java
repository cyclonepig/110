package com.tongdao.core.Dao.beans;

public class FmtParm {

	/**
	 * Ԥ��ʽ�ֶ���
	 */
	private String fieldName;
	
	/**
	 * ��ֵ�����ֶ���
	 */
	private String select;
	
	/**
	 * ��ֵ��
	 */
	private Class<? extends Po> po;
	
	/**
	 * ƥ������
	 */
	private WherePrams where;
	
	
	public FmtParm(){}
	
	public FmtParm(String fieldName, String select, Class<? extends Po> po, WherePrams where){
		this.fieldName = fieldName;
		this.select = select;
		this.po = po;
		this.where = where;
	}
	
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public Class<? extends Po> getPo() {
		return po;
	}
	public void setPo(Class<? extends Po> po) {
		this.po = po;
	}
	public WherePrams getWhere() {
		return where;
	}
	public void setWhere(WherePrams where) {
		this.where = where;
	}
	
	
}
