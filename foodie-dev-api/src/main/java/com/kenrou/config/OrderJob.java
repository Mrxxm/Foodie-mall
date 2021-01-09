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

    // https://cron.qqe2.com/ cron表达式网址
    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrder() {
//        System.out.println("Current time: " + DateUtil.getCurrentDateString(DateUtil.DATETIME_PATTERN));
        orderService.closeOrder();
    }
}
