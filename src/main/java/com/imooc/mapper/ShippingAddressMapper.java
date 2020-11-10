package com.imooc.mapper;

import com.imooc.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressMapper {
    List<ShoppingCart> lookForTheAddress(@Param("userName")String userName);
    //顺序！！！顺序，很重要
    int changeDeliveryInformation(
            @Param("userName") String userName,
            @Param("shippingAddress1")String shippingAddress1,
            @Param("phoneNumber1") String phoneNumber1,
            @Param("nickName")String nickName
                                    );
}
