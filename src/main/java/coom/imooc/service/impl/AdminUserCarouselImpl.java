package coom.imooc.service.impl;

import coom.imooc.entity.Carousel;
import coom.imooc.mapper.AdminUserCarouselMapper;
import coom.imooc.service.AdminUserCarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
