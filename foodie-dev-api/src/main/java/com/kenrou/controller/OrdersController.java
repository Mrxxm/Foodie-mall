package com.kenrou.controller;

import com.kenrou.enums.OrderStatusEnum;
import com.kenrou.enums.PayMethod;
import com.kenrou.pojo.bo.SubmitOrderBO;
import com.kenrou.service.OrderService;
import com.kenrou.utils.CookieUtils;
import com.kenrou.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "订单", tags = {"订单相关接口"})
@RestController
@RequestMapping("orders")
public class OrdersController extends BaseController{

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(@RequestBody SubmitOrderBO submitOrderBO,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {

        if (submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type) {
            return IMOOCJSONResult.errorMsg("支付方式类型错误");
        }

        // 1.创建订单
        String orderId = orderService.createOrder(submitOrderBO);
        // 2.移除购物车中以提交的商品
        // TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端cookie
//        CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        // 3.向支付中心发起请求


        return IMOOCJSONResult.ok(orderId);
    }

    /**
     * 由支付中心通知
     */
    @PostMapping("notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId) {
        orderService.updateOrderStatus(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();
    }

}
