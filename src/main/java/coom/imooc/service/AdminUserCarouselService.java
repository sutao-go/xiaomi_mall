package coom.imooc.service;

import coom.imooc.entity.Carousel;
import java.util.List;

public interface AdminUserCarouselService {
    //修改前端的轮播图
    int updateCarousel(String Path, String id);
    //将数据库中所有的轮播图展示在后台页面中去
    List<Carousel> findCarousel(String allCarousel);
}
