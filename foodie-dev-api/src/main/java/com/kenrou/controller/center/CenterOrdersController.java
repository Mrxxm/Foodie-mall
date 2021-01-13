package com.kenrou.controller.center;

import com.kenrou.controller.BaseController;
import com.kenrou.pojo.Orders;
import com.kenrou.service.center.MyOrdersService;
import com.kenrou.utils.IMOOCJSONResult;
import com.kenrou.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "我的订单", tags = {"我的订单"})
@RestController
@RequestMapping("myorders")
public class CenterOrdersController extends BaseController {

    @Autowired
    private MyOrdersService myOrdersService;

    @ApiOperation(value = "查询我的订单", notes = "查询我的订单", httpMethod = "POST")
    @PostMapping("query")
    public IMOOCJSONResult query(@RequestParam String userId,
                                 @RequestParam Integer orderStatus,
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
        System.out.println("page=" + page);
        System.out.println("pageSize=" + pageSize);
        PagedGridResult result = myOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);

        return IMOOCJSONResult.ok(result);
    }

    // 商家发货，模拟
    @ApiOperation(value = "商家发货", notes = "商家发货", httpMethod = "GET")
    @GetMapping("deliver")
    public IMOOCJSONResult deliver(
            @RequestParam String orderId
    ) {
        if (StringUtils.isBlank(orderId)) {
            return IMOOCJSONResult.errorMsg("orderId不能为空");
        }

        myOrdersService.updateDeliverOrderStatus(orderId);
        return IMOOCJSONResult.ok();
    }

    // 确认收货
    @ApiOperation(value = "确认收货", notes = "确认收货", httpMethod = "POST")
    @PostMapping("confirmReceive")
    public IMOOCJSONResult confirmReceive(@RequestParam String userId,
                                          @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("orderId或userId不能为空");
        }

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if (imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }

        myOrdersService.updateReceiveOrderStatus(orderId);

        return IMOOCJSONResult.ok();
    }

    // 删除订单
    @ApiOperation(value = "删除订单", notes = "删除订单", httpMethod = "POST")
    @PostMapping("delete")
    public IMOOCJSONResult delete(@RequestParam String userId,
                                  @RequestParam String orderId) {
        if (StringUtils.isBlank(orderId) || StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("orderId或userId不能为空");
        }

        IMOOCJSONResult imoocjsonResult = checkUserOrder(userId, orderId);
        if (imoocjsonResult.getStatus() != HttpStatus.OK.value()) {
            return imoocjsonResult;
        }

        myOrdersService.deleteOrder(orderId);

        return IMOOCJSONResult.ok();
    }

}
