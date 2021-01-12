package com.kenrou.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.kenrou.mapper.OrdersMapperCustom;
import com.kenrou.pojo.vo.MyOrdersVO;
import com.kenrou.service.center.MyOrdersService;
import com.kenrou.service.impl.BaseService;
import com.kenrou.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service // 容器加载Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired // 注入UsersMapper
    private OrdersMapperCustom ordersMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)

    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        List<MyOrdersVO> orderList = ordersMapperCustom.queryMyOrders(map);

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);

        return setterPagedGrid(orderList, page);
    }

}
