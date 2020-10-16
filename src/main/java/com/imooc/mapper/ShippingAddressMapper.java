package com.imooc.mapper;

import com.imooc.entity.ShoppingCart;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShippingAddressMapper {
    List<ShoppingCart> lookForTheAddress(@Param("userName")String userName);

    String changeDeliveryInformation(@Param("shippingAddress1")String shippingAddress1,
                                     @Param("recipient1")String recipient1,
                                     @Param("phoneNumber1")String phoneNumber1,
                                     @Param("userName") String userName
                                    );
}
