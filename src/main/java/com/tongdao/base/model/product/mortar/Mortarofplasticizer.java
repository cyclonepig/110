package com.tongdao.base.model.product.mortar;


public class Mortarofplasticizer {

  private long id;
  private String productName;
  private String productFunction;
  private String concentration;
  private long productInstructions;
  private String targetCustomer;
  private String placeOforigin;
  private String remarks;


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


  public long getProductInstructions() {
    return productInstructions;
  }

  public void setProductInstructions(long productInstructions) {
    this.productInstructions = productInstructions;
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

}
