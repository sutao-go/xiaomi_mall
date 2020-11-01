package coom.imooc.service.impl;

import coom.imooc.entity.SalesManagement;
import coom.imooc.mapper.ProductInformationMapper;
import coom.imooc.service.ProductInformationService;
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

    @Override
    public int changeSalesStatus(String id,String status) {
        return productInformationMapper.changeSalesStatus(id,status);
    }

    @Override
    public Object findStatus(String productName) {
        return productInformationMapper.findStatus(productName);
    }
}
