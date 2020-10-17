package com.imooc.service;

import com.imooc.entity.OrderList;

import java.util.List;

public interface AdminUserShoppingCartService {
    /**
     * 这个主要是用来查询数据库中的图片信息的，然后添加到网页中显示出来的
     * @param userName
     * @return
     */
    List<OrderList> findimg(String userName);

    /**
     * 这个是用来向后端的数据库中添加用户添加购物车的数据库的
     * @param consumer 用户名
     * @param productName 商品名称
     * @param price 商品价格
     * @param quantity 商品数量
     * @return
     */
    int settlement(String consumer, String productName,String price,Integer quantity );

    /**
     * 这个是用来查询数据库中有没有该用户对应的购物车信息的
     * @param userName 用户名
     * @return
     */
    Boolean queryShoppingRecords(String userName);

    /**
     * 这个是用来当用户在点击添加购物车时候购物车商品数量+1时使用的
     * @param quantity3 最终修改的商品数量
     * @return
     */
    int addQuantity(Integer quantity3,String consumer);

    /**
     * 这个用来查询这个用户对应的商品数量的
     * @param consumeer 用户名
     * @return
     */
    String checkProductQuantity(String consumeer);

    /**
     * 这个是用来取出数据库中有关用户购物信息的
     * @param consumer 用户名称
     * @return
     */
    List<OrderList> queryProductInformation(String consumer);

    String totalAmount(String userName);
}
