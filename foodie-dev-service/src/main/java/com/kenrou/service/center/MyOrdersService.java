package com.kenrou.service.center;

import com.kenrou.pojo.Users;
import com.kenrou.pojo.bo.center.CenterUserBO;
import com.kenrou.pojo.vo.MyOrdersVO;
import com.kenrou.utils.PagedGridResult;

import java.util.List;

public interface MyOrdersService {

    /**
     * 查询我的订单
     *
     */
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

}
