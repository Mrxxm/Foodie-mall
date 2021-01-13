package com.kenrou.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.kenrou.enums.OrderStatusEnum;
import com.kenrou.enums.YesOrNo;
import com.kenrou.mapper.OrderStatusMapper;
import com.kenrou.mapper.OrdersMapper;
import com.kenrou.mapper.OrdersMapperCustom;
import com.kenrou.pojo.OrderStatus;
import com.kenrou.pojo.Orders;
import com.kenrou.pojo.vo.MyOrdersVO;
import com.kenrou.service.center.MyOrdersService;
import com.kenrou.service.impl.BaseService;
import com.kenrou.utils.PagedGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.annotation.Order;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service // 容器加载Service
public class MyOrdersServiceImpl extends BaseService implements MyOrdersService {

    @Autowired // 注入UsersMapper
    private OrdersMapperCustom ordersMapperCustom;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private OrdersMapper ordersMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        if (orderStatus != null) {
            map.put("orderStatus", orderStatus);
        }

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<MyOrdersVO> orderList = ordersMapperCustom.queryMyOrders(map);

        return setterPagedGrid(orderList, page);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateDeliverOrderStatus(String orderId) {
        OrderStatus os = new OrderStatus();
        os.setOrderStatus(OrderStatusEnum.WAIT_RECEIVE.type);
        os.setDeliverTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_DELIVER.type);

        orderStatusMapper.updateByExampleSelective(os, example);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Orders queryMyOrder(String userId, String orderId) {
        // 方式一
//        Example example = new Example(Orders.class);
//        Example.Criteria criteria = example.createCriteria();
//        criteria.andEqualTo("id", orderId);
//        criteria.andEqualTo("userId", userId);
//        criteria.andEqualTo("is_delete", YesOrNo.NO.type);
//
//        return ordersMapper.selectOneByExample(example);

        // 方式二
        Orders order = new Orders();
        order.setUserId(userId);
        order.setId(orderId);
        order.setIsDelete(YesOrNo.NO.type);

        return ordersMapper.selectOne(order);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateReceiveOrderStatus(String orderId) {

        OrderStatus os = new OrderStatus();
        os.setOrderStatus(OrderStatusEnum.SUCCESS.type);
        os.setSuccessTime(new Date());

        Example example = new Example(OrderStatus.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId", orderId);
        criteria.andEqualTo("orderStatus", OrderStatusEnum.WAIT_RECEIVE.type);

        orderStatusMapper.updateByExampleSelective(os, example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteOrder(String orderId) {
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsDelete(YesOrNo.YES.type);
        order.setUpdatedTime(new Date());

        ordersMapper.updateByPrimaryKeySelective(order);
    }
}
