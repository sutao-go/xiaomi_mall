package com.imooc.mapper;

import com.imooc.entity.SalesManagement;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProductInformationMapper {

    List<SalesManagement> findProduct(String allProduct);
}
