package com.kenrou.service;

import com.kenrou.pojo.Carousel;
import com.kenrou.pojo.Orders;
import com.kenrou.pojo.bo.SubmitOrderBO;
import com.kenrou.pojo.vo.OrderVo;

import java.util.List;

public interface OrderService {

    /**
     * 创建订单
     */
    public OrderVo createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 订单修改状态
     */
    public void updateOrderStatus(String orderId, Integer orderStatus);

    /**
     * 获取订单
     */
    public Orders queryOrderById(String orderId);
}
