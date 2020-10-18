package com.imooc.service.impl;

import com.imooc.entity.OrderList;
import com.imooc.mapper.AdminUserShoppingCartMapper;
import com.imooc.service.AdminUserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminUserShoppingCartImpl implements AdminUserShoppingCartService {
    @Autowired
    private AdminUserShoppingCartMapper adminUserShoppingCartMapper;

    @Override
    public List<OrderList> findimg(String userName) {
        return adminUserShoppingCartMapper.findimg(userName);
    }

    /**
     * 首次点击时候添加商品数量用的
     * @param consumer 用户名
     * @param productName 商品名称
     * @param price 商品价格
     * @param quantity 商品数量
     * @return
     */
    @Override
    public int settlement(String consumer, String productName,String price,Integer quantity) {
        return adminUserShoppingCartMapper.addToShoppingCart(consumer,productName,price,quantity);
    }

    /**
     * 查询之前用户是否有添加商品
     * @param userName 用户名
     * @return
     */
    @Override
    public Boolean queryShoppingRecords(String userName) {
        return adminUserShoppingCartMapper.ShoppingRecords(userName);
    }

    @Override
    public int addQuantity(Integer quantity3,String consumer) {
        return adminUserShoppingCartMapper.addQuantity(quantity3,consumer);
    }

    @Override
    public String checkProductQuantity(String consumeer) {
        return adminUserShoppingCartMapper.checkProductQuantity(consumeer);
    }

    @Override
    public List<OrderList> queryProductInformation(String consumer) {
        return adminUserShoppingCartMapper.queryProductInformation(consumer);
    }

    @Override
    public List<OrderList> totalAmount(String userName) {
        return adminUserShoppingCartMapper.totalAmount(userName);
    }
}
