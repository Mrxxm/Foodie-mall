package com.kenrou.mapper;

import com.kenrou.pojo.vo.MyOrdersVO;
import com.kenrou.pojo.vo.center.CenterOrderTrendVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrdersMapperCustom {

    public List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String, Object> map);

    public Integer getMyOrderStatusCount(@Param("paramsMap") Map<String, Object> map);

    public List<CenterOrderTrendVO> queryMyOrdersTrend(@Param("paramsMap") Map<String, Object> map);
}
