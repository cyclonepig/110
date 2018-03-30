package com.tongdao.base.model.productSeries;


public class Productseries {

  private Integer id;
  private String productSeriesName; //产品系列名称
  private String productSeriesEname; //产品系列英文名称
  private String placeOfOrigin; //产地
  private String remarks; //备注


  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }


  public String getProductSeriesName() {
    return productSeriesName;
  }

  public void setProductSeriesName(String productSeriesName) {
    this.productSeriesName = productSeriesName;
  }


  public String getProductSeriesEname() {
    return productSeriesEname;
  }

  public void setProductSeriesEname(String productSeriesEname) {
    this.productSeriesEname = productSeriesEname;
  }


  public String getPlaceOfOrigin() {
    return placeOfOrigin;
  }

  public void setPlaceOfOrigin(String placeOfOrigin) {
    this.placeOfOrigin = placeOfOrigin;
  }


  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

}
