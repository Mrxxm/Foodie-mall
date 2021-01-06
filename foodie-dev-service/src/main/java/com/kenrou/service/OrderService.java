package com.kenrou.service;

import com.kenrou.pojo.Carousel;

import java.util.List;

public interface OrderService {

    /**
     * 查询所有轮播图列表
     * @param isShow
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);
}
