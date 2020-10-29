package com.imooc.service.impl;

import com.imooc.entity.Carousel;
import com.imooc.mapper.AdminUserCarouselMapper;
import com.imooc.service.AdminUserCarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class AdminUserCarouselImpl implements AdminUserCarouselService {
    @Autowired
    AdminUserCarouselMapper adminUserCarouselMapper;

    @Override
    public int updateCarousel(String Path, String id) {
        return adminUserCarouselMapper.updateCarousel(Path,id);
    }

    @Override
    public List<Carousel> findCarousel(String allCarousel) {
        return adminUserCarouselMapper.findCarousel(allCarousel);
    }
}
