package com.imooc.service.impl;

import com.imooc.entity.ShoppingCart;
import com.imooc.mapper.ShippingAddressMapper;
import com.imooc.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingAddressImpl implements ShippingAddressService {
    @Autowired
    ShippingAddressMapper shippingAddressMapper;

    @Override
    public List<ShoppingCart> lookForTheAddress(String userName) {
        return shippingAddressMapper.lookForTheAddress(userName);
    }

    @Override
    public String changeDeliveryInformation(String userName,String shippingAddress1, String recipient1, String phoneNumber1) {
        return shippingAddressMapper.changeDeliveryInformation(userName,shippingAddress1,recipient1,phoneNumber1);
    }

}
