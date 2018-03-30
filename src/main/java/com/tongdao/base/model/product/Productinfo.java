package com.tongdao.base.model.product;

import com.tongdao.core.Dao.annotation.po.TableName;
import com.tongdao.core.Dao.beans.Po;

@TableName(name="productinfo")
public class Productinfo extends Po{

  private long id;
  private String productName;
  private String productFunction;
  private String concentration;
  private String instructions;
  private String targetCustomer;
  private String placeOforigin;
  private String remarks;
  private String createtime;
  private String prictureAddress;
  private String suggest;
  private int seriesid;
  private String productprice;
  private String unit;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }


  public String getProductFunction() {
    return productFunction;
  }

  public void setProductFunction(String productFunction) {
    this.productFunction = productFunction;
  }


  public String getConcentration() {
    return concentration;
  }

  public void setConcentration(String concentration) {
    this.concentration = concentration;
  }


  public String getInstructions() {
    return instructions;
  }

  public void setInstructions(String instructions) {
    this.instructions = instructions;
  }


  public String getTargetCustomer() {
    return targetCustomer;
  }

  public void setTargetCustomer(String targetCustomer) {
    this.targetCustomer = targetCustomer;
  }


  public String getPlaceOforigin() {
    return placeOforigin;
  }

  public void setPlaceOforigin(String placeOforigin) {
    this.placeOforigin = placeOforigin;
  }


  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }


  public String getCreatetime() {
    return createtime;
  }

  public void setCreatetime(String createtime) {
    this.createtime = createtime;
  }


  public String getPrictureAddress() {
    return prictureAddress;
  }

  public void setPrictureAddress(String prictureAddress) {
    this.prictureAddress = prictureAddress;
  }


  public String getSuggest() {
    return suggest;
  }

  public void setSuggest(String suggest) {
    this.suggest = suggest;
  }

  public int getSeriesid() {
    return seriesid;
  }

  public void setSeriesid(int seriesid) {
    this.seriesid = seriesid;
  }

  public String getProductprice() {
    return productprice;
  }

  public void setProductprice(String productprice) {
    this.productprice = productprice;
  }


  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

}
