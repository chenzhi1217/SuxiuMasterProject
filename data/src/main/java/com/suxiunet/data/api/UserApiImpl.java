package com.suxiunet.data.api;

import android.content.Context;

import com.suxiunet.data.entity.base.ApiResponse;
import com.suxiunet.data.entity.user.UserInfoEntity;
import com.suxiunet.data.factory.RetrofitFactory;

import rx.Observable;

/**
 * author : chenzhi
 * time   : 2018/01/14
 * desc   : 用户模块
 */
public class UserApiImpl implements UserApi {

    private final UserApi mApi;

    public UserApiImpl(Context context) {
        mApi = RetrofitFactory.creat(UserApi.class, context);
    }

    /**
     * 用户登录
     * @param loginName 手机号码
     * @param loginType 要不过户类型
     * @param psw 短信验证码
     * @return
     */
    @Override
    public Observable<ApiResponse<UserInfoEntity>> login(String loginName, String loginType, String psw) {
        return mApi.login(loginName,loginType,psw);
    }

    /**
     * 退出登录
     * @return
     */
    @Override
    public Observable<ApiResponse<Object>> quitLogin(String loginId,String loginType) {
        return mApi.quitLogin(loginId,loginType);
    }
    
    /**
     * 修改昵称
     * @param name
     * @return
     */
    @Override
    public Observable<ApiResponse<Object>> modifyNickName(String loginId,String name) {
        return mApi.modifyNickName(loginId,name);
    }

    /**
     * 修改昵称
     * @param gender 1:男 2：女 3：保密
     * @return
     */
    @Override
    public Observable<ApiResponse<Object>> modifyGender(String loginId,String gender) {
        return mApi.modifyGender(loginId,gender);
    }

    /**
     * 修改师傅密码
     * @param loginId
     * @param oldpassWord
     * @param passWord
     * @return
     */
    @Override
    public Observable<ApiResponse<Object>> modifyPsw(String loginId, String oldpassWord, String passWord) {
        return mApi.modifyPsw(loginId,oldpassWord,passWord);
    }

}
