package com.imooc.service.impl;

import com.imooc.entity.OrderList;
import com.imooc.mapper.AdminUserShoppingCartMapper;
import com.imooc.service.AdminUserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminUserShoppingCartImpl implements AdminUserShoppingCartService {
    @Autowired
    private AdminUserShoppingCartMapper adminUserShoppingCartMapper;
    @Override
    public OrderList settlement(String productName, Integer quantity, String price, Integer consumer) {
        return adminUserShoppingCartMapper.addToShoppingCart(productName,quantity,price,consumer);
    }
}
