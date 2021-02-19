package com.kenrou.controller;

import com.kenrou.pojo.Orders;
import com.kenrou.pojo.Users;
import com.kenrou.service.center.MyOrdersService;
import com.kenrou.utils.IMOOCJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.File;

@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer SEARCH_PAGE_SIZE = 10;

    public static final String REDIS_USER_TOKEN = "redis_user_token";

    // 支付中心
    String paymentUrl = "https://pay.kenrou.cn/api/pay/unifiedOrder";

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
//    String payReturnUrl = "http://localhost:8018/orders/notifyMerchantOrderPaid";
    String payReturnUrl = "http://api.kenrou.cn/foodie-dev-api/orders/notifyMerchantOrderPaid"; // 线上接口
//    String payReturnUrl = "http://7kvnyd.natappfree.cc/orders/notifyMerchantOrderPaid"; // NatApp内网穿透地址

    // 用户上传地址
    public static final String IMAGE_USER_FACE_LOCATION =
            File.separator + "var" +
            File.separator + "www" +
            File.separator + "foodie-dev" +
            File.separator + "img";


    protected Users setNullProperty(Users user) {

        user.setPassword(null);
        user.setRealname(null);
        user.setMobile(null);
        user.setEmail(null);
        user.setBirthday(null);
        user.setCreatedTime(null);
        user.setUpdatedTime(null);

        return user;
    }

    @Autowired
    protected MyOrdersService myOrdersService;
    /**
     * 用于验证用户和订单关联
     */
    protected IMOOCJSONResult checkUserOrder(String userId, String orderId) {
        Orders order = myOrdersService.queryMyOrder(userId, orderId);

        if (order == null) {
            return IMOOCJSONResult.errorMsg("用户订单不对应或订单不存在");
        }
        return IMOOCJSONResult.ok(order);
    }
}
