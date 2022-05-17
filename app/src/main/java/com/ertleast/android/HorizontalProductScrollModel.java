package com.ertleast.android;

public class HorizontalProductScrollModel {

    private String productImage;
    private String productTitle;
    private String productID;


    public HorizontalProductScrollModel(String productID, String productImage, String productTitle) {
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productID = productID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}




