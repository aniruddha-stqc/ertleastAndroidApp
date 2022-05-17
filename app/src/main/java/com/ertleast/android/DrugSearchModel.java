package com.ertleast.android;

public class DrugSearchModel {

    private String productType;
    private String productTitle;
    private String productID;


    public DrugSearchModel(String productID, String productType, String productTitle) {
        this.productType = productType;
        this.productTitle = productTitle;
        this.productID = productID;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }



    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}




