package com.imooc.service;

import com.imooc.entity.ShoppingCart;

import java.util.List;

public interface ShippingAddressService {
    List<ShoppingCart> lookForTheAddress(String userName);
}
