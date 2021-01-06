package com.kenrou.service;

import com.kenrou.pojo.Carousel;
import com.kenrou.pojo.bo.SubmitOrderBO;

import java.util.List;

public interface OrderService {

    /**
     * 创建订单
     */
    public void createOrder(SubmitOrderBO submitOrderBO);
}
