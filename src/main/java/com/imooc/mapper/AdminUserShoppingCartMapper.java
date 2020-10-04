package com.imooc.mapper;

import com.imooc.entity.OrderList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserShoppingCartMapper {
    OrderList addToShoppingCart(@Param("consumer")String consumer,
                                @Param("productName")String productName,
                                @Param("price")String price,
                                @Param("quantity")Integer quantity
                                );
}
