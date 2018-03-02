package com.suxiunet.data.api;

import com.suxiunet.data.entity.base.ApiResponse;
import com.suxiunet.data.entity.user.UserInfoEntity;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * author : chenzhi
 * time   : 2018/01/14
 * desc   : 用户模块接口
 */
public interface UserApi {
    
    /**
     * 登录接口
     *
     * @param loginName 手机号码
     * @param loginType 要不过户类型
     * @param psw 短信验证码
     * @return
     */
    @FormUrlEncoded
    @POST("dnwx/app/user/login")
    Observable<ApiResponse<UserInfoEntity>> login(@Field("loginId") String loginName, @Field("loginType") String loginType, @Field("password") String psw);
    
    /**
     * 退出登录
     * @return
     */
    @FormUrlEncoded
    @POST("dnwx/app/user/loginout")
    Observable<ApiResponse<Object>> quitLogin(@Field("loginId") String loginId,@Field("loginType") String loginType);

    /**
     * 修改昵称
     * @param name
     * @return
     */
    @FormUrlEncoded
    @POST("dnwx/app/user/modifyUserInfo")
    Observable<ApiResponse<Object>> modifyNickName(@Field("loginId") String loginId, @Field("loginName") String name);

    /**
     * 修改性别
     * @param gender 1:男 2：女 3：保密
     * @return 119.28.52.119/dnwx/app/user/modifyMasInfo
     */
    @FormUrlEncoded
    @POST("dnwx/app/user/modifyMasInfo")
    Observable<ApiResponse<Object>> modifyGender(@Field("loginId") String loginId, @Field("gender") String gender);
    
}
