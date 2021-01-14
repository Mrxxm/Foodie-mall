package com.kenrou.service.center;

import com.kenrou.pojo.Orders;
import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.center.CenterUserBO;
import com.kenrou.pojo.vo.MyOrdersVO;
import com.kenrou.pojo.vo.center.CenterOrderStatusCountVO;
import com.kenrou.utils.PagedGridResult;

import java.util.List;

public interface MyOrdersService {

    /**
     * 查询我的订单
     *
     */
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * 发货 更新订单状态
     */
    public void updateDeliverOrderStatus(String orderId);

    /**
     * 查询订单
     */
    public Orders queryMyOrder(String userId, String orderId);

    /**
     * 确认收货 更新订单状态
     */
    public void updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单
     */
    public void deleteOrder(String orderId);

    /**
     * 获取订单状态数
     */
    public CenterOrderStatusCountVO getMyOrderStatusCount(String userId);

    /**
     * 订单动向
     */
    public PagedGridResult queryMyOrdersTrend(String userId, Integer page, Integer pageSize);
}
