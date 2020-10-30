package com.imooc.service;

import com.imooc.entity.SalesManagement;

import java.util.List;

public interface ProductInformationService {
    List<SalesManagement>findProduct(String allProduct);
}
