package com.kenrou.pojo.vo;

import com.kenrou.pojo.bo.ShopcartBO;

import java.util.List;

public class OrderVo {

    private MerchantOrderVO merchantOrderVO;
    private String orderId;
    // 删除redis中商品章节-添加
    private List<ShopcartBO> toBeRemovedShopCartList;

    public MerchantOrderVO getMerchantOrderVO() {
        return merchantOrderVO;
    }

    public void setMerchantOrderVO(MerchantOrderVO merchantOrderVO) {
        this.merchantOrderVO = merchantOrderVO;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<ShopcartBO> getToBeRemovedShopCartList() {
        return toBeRemovedShopCartList;
    }

    public void setToBeRemovedShopCartList(List<ShopcartBO> toBeRemovedShopCartList) {
        this.toBeRemovedShopCartList = toBeRemovedShopCartList;
    }
}
