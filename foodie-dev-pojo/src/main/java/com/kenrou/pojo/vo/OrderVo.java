package com.kenrou.pojo.vo;

public class OrderVo {

    private MerchantOrderVO merchantOrderVO;
    private String orderId;

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
}
