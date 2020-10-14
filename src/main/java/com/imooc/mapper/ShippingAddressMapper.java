package com.imooc.mapper;

import com.imooc.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressMapper {
    List<ShoppingCart> lookForTheAddress(@Param("userName")String userName);
}
