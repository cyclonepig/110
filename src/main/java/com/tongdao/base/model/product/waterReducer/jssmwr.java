package com.tongdao.base.model.product.waterReducer;

public class jssmwr {

    private Integer id; //产品id
    private String productName; //产品名称
    private String productFunction; //产品功能
    private String concentration; //浓度
    private Integer productInstructions; //是否具有产品说明书
    private String targetCustomer;//目标客户
    private String placeOforigin;//产地
    private String remarks;//备注

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Integer getProductInstructions() {
        return productInstructions;
    }

    public void setProductInstructions(Integer productInstructions) {
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

