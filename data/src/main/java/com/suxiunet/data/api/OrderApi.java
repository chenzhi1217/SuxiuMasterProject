package com.suxiunet.data.api;

import com.suxiunet.data.entity.base.ApiResponse;
import com.suxiunet.data.entity.order.OrderListEntity;

import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * author : chenzhi
 * time   : 2018/02/09
 * desc   :
 */
public interface OrderApi {
    /**
     * 查询订单列表
     *
     * @param status
     * @return
     */
    @FormUrlEncoded
    @POST("dnwx/app/order/queryOrderList")
    Observable<ApiResponse<OrderListEntity>> getOrderList(@Field("loginId") String masterMp, @Field("status") String status, @Field("loginType") String loginType);

    /**
     * 确认订单
     * @return
     */
    @FormUrlEncoded
    @POST("/dnwx/app/order/modifyMerOrderInfo")
    Observable<ApiResponse<Object>> confirmOrder(@FieldMap Map<String,String> map);
}
