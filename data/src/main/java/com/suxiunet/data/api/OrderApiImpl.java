package com.suxiunet.data.api;

import android.content.Context;

import com.suxiunet.data.entity.base.ApiResponse;
import com.suxiunet.data.entity.order.OrderListEntity;
import com.suxiunet.data.factory.RetrofitFactory;

import java.util.Map;

import rx.Observable;

/**
 * author : chenzhi
 * time   : 2018/02/10
 * desc   :
 */
public class OrderApiImpl implements OrderApi{

    private final OrderApi mOrderApi;

    public OrderApiImpl(Context context) {
        mOrderApi = RetrofitFactory.creat(OrderApi.class, context);
    }

    /**
     * 查询订单列表
     * @param loginId
     * @param status
     * @param loginType
     * @return
     */
    @Override
    public Observable<ApiResponse<OrderListEntity>> getOrderList(String loginId, String status, String loginType) {
        return mOrderApi.getOrderList(loginId,status,loginType);
    }

    /**
     * 确认订单
     * @param map
     * @return
     */
    @Override
    public Observable<ApiResponse<Object>> confirmOrder(Map<String, String> map) {
        return mOrderApi.confirmOrder(map);
    }
} 