package com.imooc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.imooc.entity.OrderList;
import com.imooc.mapper.AdminUserShoppingCartMapper;
import com.imooc.service.AdminUserShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 购物车服务
 * <p>
 * 全部查询改用 MyBatis-Plus 的 LambdaQueryWrapper / LambdaUpdateWrapper / selectPage 实现，
 * 不再依赖 mapper XML；仅“有则累加、无则插入”的原子 upsert 因是 MySQL 特有语法仍保留在 XML。
 * <p>
 * 好处：字段名用方法引用表达（OrderList::getConsumer），改字段名时编译期即报错，
 * 不会出现 XML 里写错列名、运行到线上才发现的情况。
 */
@Service
public class AdminUserShoppingCartImpl implements AdminUserShoppingCartService {

    @Autowired
    private AdminUserShoppingCartMapper adminUserShoppingCartMapper;

    @Override
    @Transactional(readOnly = true)
    public List<OrderList> findimg(String userName) {
        return adminUserShoppingCartMapper.selectList(new LambdaQueryWrapper<OrderList>()
                .select(OrderList::getImgurl, OrderList::getProductName, OrderList::getPrice, OrderList::getQuantity)
                .eq(OrderList::getConsumer, userName));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int settlement(String consumer, String productName, String price, Integer quantity) {
        OrderList entity = new OrderList();
        entity.setConsumer(consumer);
        entity.setProductName(productName);
        entity.setPrice(price);
        entity.setQuantity(quantity);
        return adminUserShoppingCartMapper.insert(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean queryShoppingRecords(String userName, String productName) {
        return countByUserAndProduct(userName, productName) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addQuantity(Integer quantity2, String consumer, String productName) {
        return updateQuantity0(quantity2, consumer, productName);
    }

    @Override
    @Transactional(readOnly = true)
    public String checkProductQuantity(String consumer, String productName) {
        OrderList one = adminUserShoppingCartMapper.selectOne(new LambdaQueryWrapper<OrderList>()
                .select(OrderList::getQuantity)
                .eq(OrderList::getConsumer, consumer)
                .eq(OrderList::getProductName, productName)
                .last("limit 1"));
        return one == null || one.getQuantity() == null ? null : String.valueOf(one.getQuantity());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderList> queryProductInformation(String consumer) {
        return adminUserShoppingCartMapper.selectList(new LambdaQueryWrapper<OrderList>()
                .eq(OrderList::getConsumer, consumer));
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderList> totalAmount(String userName) {
        // 原 XML 里 totalAmount 与 queryProductInformation 是两条一模一样的 SQL，这里直接复用
        return queryProductInformation(userName);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateQuantity(String quantity, String userName, String productName1) {
        return updateQuantity0(Integer.valueOf(quantity), userName, productName1);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addToCart(String consumer, String productName, String price, Integer quantity) {
        return adminUserShoppingCartMapper.upsertShoppingCart(consumer, productName, price, quantity);
    }

    @Override
    @Transactional(readOnly = true)
    public int countCart(String userName) {
        Long count = adminUserShoppingCartMapper.selectCount(new LambdaQueryWrapper<OrderList>()
                .eq(OrderList::getConsumer, userName));
        return count == null ? 0 : count.intValue();
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderList> queryProductInformationPage(String userName, int offset, int pageSize) {
        int current = pageSize <= 0 ? 1 : (offset / pageSize) + 1;
        Page<OrderList> page = new Page<>(current, pageSize);
        LambdaQueryWrapper<OrderList> query = new LambdaQueryWrapper<OrderList>()
                .eq(OrderList::getConsumer, userName);
        return adminUserShoppingCartMapper.selectPage(page, query).getRecords();
    }

    private long countByUserAndProduct(String userName, String productName) {
        Long count = adminUserShoppingCartMapper.selectCount(new LambdaQueryWrapper<OrderList>()
                .eq(OrderList::getConsumer, userName)
                .eq(OrderList::getProductName, productName));
        return count == null ? 0L : count;
    }

    private int updateQuantity0(Integer quantity, String consumer, String productName) {
        OrderList entity = new OrderList();
        entity.setQuantity(quantity);
        return adminUserShoppingCartMapper.update(entity, new LambdaUpdateWrapper<OrderList>()
                .eq(OrderList::getConsumer, consumer)
                .eq(OrderList::getProductName, productName));
    }
}
