package com.kenrou.service;

import com.kenrou.pojo.Carousel;
import com.kenrou.pojo.bo.SubmitOrderBO;

import java.util.List;

public interface OrderService {

    /**
     * 创建订单
     */
    public String createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 订单修改状态
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);
}
