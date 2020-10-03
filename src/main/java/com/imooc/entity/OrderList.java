package com.imooc.entity;

public class OrderList {
    //商品名称
    public String productName;
    //单价
    public String price;
    //商品数量(小记)
    public Integer quantity;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getConsumer() {
        return consumer;
    }

    public void setConsumer(Integer consumer) {
        this.consumer = consumer;
    }

    //购买人
    public Integer consumer;

    @Override
    public String toString() {
        return "OrderList{" +
                "productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", consumer=" + consumer +
                '}';
    }
}