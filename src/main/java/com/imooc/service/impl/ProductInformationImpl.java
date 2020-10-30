package com.imooc.service.impl;

import com.imooc.entity.SalesManagement;
import com.imooc.mapper.ProductInformationMapper;
import com.imooc.service.ProductInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInformationImpl implements ProductInformationService {
    @Autowired
    ProductInformationMapper productInformationMapper;

    @Override
    public List<SalesManagement> findProduct(String allProduct) {
        return productInformationMapper.findProduct(allProduct);
    }
}
