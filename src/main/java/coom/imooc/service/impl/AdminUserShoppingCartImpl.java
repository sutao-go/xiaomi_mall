package coom.imooc.service.impl;

import coom.imooc.entity.OrderList;
import coom.imooc.mapper.AdminUserShoppingCartMapper;
import coom.imooc.service.AdminUserShoppingCartService;
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
    public Boolean queryShoppingRecords(String userName, String productName) {
        return adminUserShoppingCartMapper.ShoppingRecords(userName,productName);
    }

    @Override
    public int addQuantity(Integer quantity2,String consumer,String productName) {
        return adminUserShoppingCartMapper.addQuantity(quantity2,consumer,productName);
    }

    @Override
    public String checkProductQuantity(String consumeer, String productName) {
        return adminUserShoppingCartMapper.checkProductQuantity(consumeer,productName);
    }



    @Override
    public List<OrderList> queryProductInformation(String consumer) {
        return adminUserShoppingCartMapper.queryProductInformation(consumer);
    }

    @Override
    public List<OrderList> totalAmount(String userName) {
        return adminUserShoppingCartMapper.totalAmount(userName);
    }

    @Override
    public int updateQuantity(String quantity,String userName, String productName1) {
        return adminUserShoppingCartMapper.updateQuantity(quantity,userName,productName1);
    }
}
