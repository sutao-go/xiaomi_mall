package com.imooc.mapper;

import com.imooc.entity.Carousel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;

@Repository
public interface AdminUserCarouselMapper {
    int updateCarousel(String showFilePath, String userName);

    List<Carousel> findCarousel(@Param("allCarousel")String allCarousel);
}
