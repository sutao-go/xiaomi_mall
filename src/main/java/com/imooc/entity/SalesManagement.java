package com.imooc.entity;

public class SalesManagement {
    //商品名称
    public String productName;
    //商品图片的地址
    public String pictureAddress;
    //商品销售状态
    public String salesStatus;
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPictureAddress() {
        return pictureAddress;
    }

    public void setPictureAddress(String pictureAddress) {
        pictureAddress = pictureAddress;
    }

    public String getSalesStatus() {
        return salesStatus;
    }

    public void setSalesStatus(String salesStatus) {
        salesStatus = salesStatus;
    }

    @Override
    public String toString() {
        return "SalesManagement{" +
                "productName='" + productName + '\'' +
                ", PictureAddress='" + pictureAddress + '\'' +
                ", SalesStatus='" + salesStatus + '\'' +
                '}';
    }
}
