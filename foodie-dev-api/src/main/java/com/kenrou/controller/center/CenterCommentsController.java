package com.kenrou.controller.center;

import com.kenrou.controller.BaseController;
import com.kenrou.enums.YesOrNo;
import com.kenrou.pojo.OrderItems;
import com.kenrou.pojo.Orders;
import com.kenrou.pojo.bo.center.CenterItemsCommentBO;
import com.kenrou.service.center.MyCommentsService;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "我的评论", tags = {"我的评论"})
@RestController
@RequestMapping("mycomments")
public class CenterCommentsController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询待评价订单item列表", notes = "查询待评价订单item列表", httpMethod = "POST")
    @PostMapping("pending")
    public IMOOCJSONResult pending(@RequestParam String userId, @RequestParam String orderId) {

        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("orderId或userId不能为空");
        }
        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if (imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }

        Orders myOrder = (Orders)imoocjsonResult.getData();
        if (myOrder.getIsComment() == YesOrNo.YES.type) {
            return IMOOCJSONResult.errorMsg("订单已评价");
        }

        List<OrderItems> result = myCommentsService.queryPendingComment(orderId);

        return IMOOCJSONResult.ok(result);
    }

    @ApiOperation(value = "保存评价列表", notes = "保存评价列表", httpMethod = "POST")
    @PostMapping("saveList")
    public IMOOCJSONResult saveList(@RequestParam String userId,
                                    @RequestParam String orderId,
                                    @RequestBody List<CenterItemsCommentBO> centerItemsCommentList) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("orderId或userId不能为空");
        }
        if (centerItemsCommentList == null || centerItemsCommentList.isEmpty()) {
            return IMOOCJSONResult.errorMsg("评论内容为空");
        }
        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if (imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }
        Orders myOrder = (Orders)imoocjsonResult.getData();
        if (myOrder.getIsComment() == YesOrNo.YES.type) {
            return IMOOCJSONResult.errorMsg("订单已评价");
        }

        myCommentsService.saveComments(userId, orderId, centerItemsCommentList);

        return IMOOCJSONResult.ok();
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("query")
    public IMOOCJSONResult saveList(@RequestParam String userId,
                                    @ApiParam(name = "page", value = "页码", required = false)
                                    @RequestParam Integer page,
                                    @ApiParam(name = "pageSize", value = "分页大小", required = false)
                                    @RequestParam Integer pageSize) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("用户不存在");
        }

        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult result = myCommentsService.queryMyComments(userId, page, pageSize);

        return IMOOCJSONResult.ok(result);
    }

}
