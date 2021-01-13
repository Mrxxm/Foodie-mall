package com.kenrou.service.center;

import com.kenrou.pojo.OrderItems;
import com.kenrou.pojo.Orders;
import com.kenrou.pojo.bo.center.CenterItemsCommentBO;
import com.kenrou.utils.PagedGridResult;

import java.util.List;

public interface MyCommentsService {

    /**
     * 查询订单item
     */
    public List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存评论列表
     */
    public void saveComments(String userId, String orderId, List<CenterItemsCommentBO> centerItemsCommentList);

    /**
     * 查询我的评论
     */
    public PagedGridResult queryMyComments(String userId, Integer page, Integer pageSize);
}
