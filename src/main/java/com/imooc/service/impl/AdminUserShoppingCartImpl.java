package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.OrderList;
import com.imooc.mapper.AdminUserShoppingCartMapper;
import com.imooc.service.AdminUserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = Exception.class)
    public int updateQuantity(String quantity,String userName, String productName1) {
        return adminUserShoppingCartMapper.updateQuantity(quantity,userName,productName1);
    }

    /**
     * 加入购物车：一条原子 upsert SQL 完成“有则累加、无则插入”，
     * 不再需要“先查数量再更新”的两步操作（原实现并发下会丢更新）。
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addToCart(String consumer, String productName, String price, Integer quantity) {
        return adminUserShoppingCartMapper.upsertShoppingCart(consumer, productName, price, quantity);
    }

    @Override
    @Transactional(readOnly = true)
    public int countCart(String userName) {
        // 用 LambdaQueryWrapper 替代 XML 里的 select count(1)：字段写错会在编译期发现
        LambdaQueryWrapper<OrderList> query = new LambdaQueryWrapper<OrderList>()
                .eq(OrderList::getConsumer, userName);
        Long count = adminUserShoppingCartMapper.selectCount(query);
        return count == null ? 0 : count.intValue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderList> queryProductInformationPage(String userName, int offset, int pageSize) {
        // Page 的 current 从 1 开始，由 offset 反推；分页插件（MybatisPlusConfig）会真正追加 LIMIT
        int current = pageSize <= 0 ? 1 : (offset / pageSize) + 1;
        Page<OrderList> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<OrderList> query = new LambdaQueryWrapper<OrderList>()
                .eq(OrderList::getConsumer, userName);
        return adminUserShoppingCartMapper.selectPage(page, query).getRecords();
    }
}
