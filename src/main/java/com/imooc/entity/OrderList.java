package com.imooc.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;


/** 对应数据库表 adminusershoppingcart */
@TableName("adminusershoppingcart")
public class OrderList {
    @TableField("user_name")
    public String consumer;
    //商品名称
    public String productName;
    //单价
    public String price;
    //商品数量(小记)
    @TableField("numbers_of_products")
    public Integer quantity;
    //图片的路径
    @TableField("img")
    public String imgurl;




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

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Override
    public String toString() {
        return "OrderList{" +
                "consumer='" + consumer + '\'' +
                ", productName='" + productName + '\'' +
                ", price='" + price + '\'' +
                ", quantity=" + quantity +
                ", imgurl='" + imgurl + '\'' +
                '}';
    }
}