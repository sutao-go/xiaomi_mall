package com.imooc.entity;

public class OrderList {
    public String consumer;
    //商品名称
    public String productName;
    //单价
    public String price;
    //商品数量(小记)
    public Integer quantity;
    //消费者


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

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "consumer='" + consumer + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}