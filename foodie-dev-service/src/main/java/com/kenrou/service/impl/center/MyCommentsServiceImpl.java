package com.kenrou.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.kenrou.enums.OrderStatusEnum;
import com.kenrou.enums.YesOrNo;
import com.kenrou.mapper.*;
import com.kenrou.pojo.ItemsComments;
import com.kenrou.pojo.OrderItems;
import com.kenrou.pojo.OrderStatus;
import com.kenrou.pojo.Orders;
import com.kenrou.pojo.bo.center.CenterItemsCommentBO;
import com.kenrou.pojo.vo.MyOrdersVO;
import com.kenrou.pojo.vo.center.CenterItemCommentVO;
import com.kenrou.service.center.MyCommentsService;
import com.kenrou.service.center.MyOrdersService;
import com.kenrou.service.impl.BaseService;
import com.kenrou.utils.PagedGridResult;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service // 容器加载Service
public class MyCommentsServiceImpl extends BaseService implements MyCommentsService {

    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;
    @Autowired
    private ItemsCommentsMapperCustom itemsCommentsMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems orderItems = new OrderItems();
        orderItems.setOrderId(orderId);

        return orderItemsMapper.select(orderItems);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComments(String userId, String orderId, List<CenterItemsCommentBO> centerItemsCommentList) {
        // 1.保存评价列表 items_comment
        for (CenterItemsCommentBO commentBO: centerItemsCommentList) {
            commentBO.setCommentId(sid.nextShort());
            ItemsComments itemsComment = new ItemsComments();
            itemsComment.setId(commentBO.getCommentId());
            itemsComment.setUserId(userId);
            itemsComment.setItemId(commentBO.getItemId());
            itemsComment.setItemName(commentBO.getItemName());
            itemsComment.setItemSpecId(commentBO.getItemSpecId());
            itemsComment.setSepcName(commentBO.getItemSpecName());
            itemsComment.setContent(commentBO.getContent());
            itemsComment.setCommentLevel(commentBO.getCommentLevel());
            itemsComment.setCreatedTime(new Date());
            itemsComment.setUpdatedTime(new Date());

            itemsCommentsMapper.insert(itemsComment);
        }

        // 2.修改订单状态是否评价状态 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(YesOrNo.YES.type);
        order.setUpdatedTime(new Date());

        ordersMapper.updateByPrimaryKeySelective(order);

        // 3.修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());

        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        /**
         * page: 第几页
         * pageSize: 每页显示条数
         */
        PageHelper.startPage(page, pageSize);
        List<CenterItemCommentVO> orderList = itemsCommentsMapperCustom.queryMyComments(map);

        return setterPagedGrid(orderList, page);
    }
}
