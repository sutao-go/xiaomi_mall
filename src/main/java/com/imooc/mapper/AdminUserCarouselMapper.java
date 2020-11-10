package com.imooc.mapper;

import com.imooc.entity.Carousel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserCarouselMapper {

    int updateCarousel(@Param("Path")String Path,@Param("id")String id);

    List<Carousel> findCarousel(@Param("allCarousel")String allCarousel);
}
