package com.kenrou.config;


import com.kenrou.service.OrderService;
import com.kenrou.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderJob {

    @Autowired
    private OrderService orderService;

    /**
     * 使用定时任务关闭超期未支付订单，存在的问题：
     * 1.会有时间差，程序不严谨
     * 2.不支持集群，会有多个定时任务
     *   解决方案：只使用一台计算机节点，单独来运行所有的定时任务
     * 3.会对数据库全表搜索,及其影响数据库性能
     *
     * 定时任务，仅仅只适用于小型轻量项目
     */

    // https://cron.qqe2.com/ cron表达式网址
    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder() {
//        System.out.println("Current time: " + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
        orderService.closeOrder();
    }
}
