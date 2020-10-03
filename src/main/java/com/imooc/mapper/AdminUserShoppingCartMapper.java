package com.imooc.mapper;

import com.imooc.entity.OrderList;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserShoppingCartMapper {
    OrderList addToShoppingCart(@Param("productName")String productName,
                                @Param("quantity")Integer quantity,
                                @Param("price")String price,
                                @Param("consumer")Integer consumer);
}
