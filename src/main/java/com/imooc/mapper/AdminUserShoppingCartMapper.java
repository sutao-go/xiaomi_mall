package com.imooc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.imooc.entity.OrderList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 购物车 Mapper
 * <p>
 * 常规 CRUD 由继承的 {@link BaseMapper} 提供（selectList / selectOne / selectCount /
 * selectPage / insert / update 等），不再需要逐条手写 SQL。
 * 这里只保留 Plus 无法表达的原子 upsert。
 */
@Repository
public interface AdminUserShoppingCartMapper extends BaseMapper<OrderList> {

    /**
     * 加入购物车（原子 upsert）：已存在则数量累加，不存在则插入。
     * <p>
     * 用一条 SQL 完成“有则累加、无则插入”，替代原 Controller 里
     * “先查数量 → 再计算 → 再写回”的读-改-写（并发下会丢更新）。
     * 依赖 adminusershoppingcart(user_name, product_name) 唯一索引。
     */
    int upsertShoppingCart(@Param("consumer") String consumer,
                           @Param("productName") String productName,
                           @Param("price") String price,
                           @Param("quantity") Integer quantity);
}
