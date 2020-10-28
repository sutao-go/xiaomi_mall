package com.imooc.entity;

public class Carousel {
    //轮播图的名字
    public String carouselName;
    //轮播图地址
    public String carouselAddress;

    public String getCarouselName() {
        return carouselName;
    }

    public void setCarouselName(String carouselName) {
        this.carouselName = carouselName;
    }

    public String getCarouselAddress() {
        return carouselAddress;
    }

    public void setCarouselAddress(String carouselAddress) {
        this.carouselAddress = carouselAddress;
    }

    @Override
    public String toString() {
        return "Carousel{" +
                "carouselName='" + carouselName + '\'' +
                ", carouselAddress='" + carouselAddress + '\'' +
                '}';
    }
}
