package com.kenrou.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public static final String FOODIE_SHOPCART = "shopcart";

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer SEARCH_PAGE_SIZE = 10;

    // 微信支付成功 -> 支付中心 -> 天天吃货平台
    String payReturnUrl = "http://localhost:8018/orders/notifyMerchantOrderPaid";
}
