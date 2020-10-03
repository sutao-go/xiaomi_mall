package com.imooc.service;

import com.imooc.entity.OrderList;

public interface AdminUserShoppingCartService {
    OrderList settlement(String productName,Integer quantity,String price,Integer consumer);
}
