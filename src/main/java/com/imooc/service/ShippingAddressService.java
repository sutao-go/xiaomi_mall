package com.imooc.service;

import com.imooc.entity.OrderList;
import com.imooc.entity.ShoppingCart;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.util.List;

public interface ShippingAddressService {
    /**
     * 查询收货地址
     * @param userName
     * @return
     */
    List<ShoppingCart> lookForTheAddress(String userName);

    /**
     * 修改收货地址
     * @param shippingAddress1
     * @param phoneNumber1
     * @return
     */
    int changeDeliveryInformation(String userName, String shippingAddress1, String phoneNumber1,String nickName);
}
