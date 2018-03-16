package com.suxiumaster.repair.businiss.center

import com.suxiumaster.repair.R
import com.suxiumaster.repair.databinding.FragLoginBinding
import com.suxiumaster.repair.util.ToastUtil
import com.suxiunet.data.api.UserApiImpl
import com.suxiunet.data.entity.base.ApiResponse
import com.suxiunet.data.entity.user.UserInfoEntity
import com.suxiunet.data.util.CacheUtil
import com.suxiunet.data.util.MD5Utils
import com.suxiunet.data.util.Md5Util
import com.suxiunet.data.util.SpUtil
import com.suxiunet.repair.base.baseui.BasicFragment
import kotlinx.android.synthetic.main.frag_login.*
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * author : chenzhi
 * time   : 2018/01/31
 * desc   : 登录界面
 */
class LoginFragment: BasicFragment<FragLoginBinding>() {
    override fun init() {
        mBinding.btLogin.setOnClickListener { login() }
    }

    override fun getContentLayoutId(): Int {
        return R.layout.frag_login
    }

    /**
     * 登录
     */
    private fun login() {
        //对用户名和密码进行校验
        val loginName = loginName.text.toString().trim()
        val psw = loginPsw.text.toString().trim()
        if (loginName.isNullOrEmpty()) {
            ToastUtil.showToast("请输入用户名")
            return
        }
        if (psw.isNullOrEmpty()) {
            ToastUtil.showToast("请输入密码")
            return
        }
        var md5Psw = MD5Utils.encode(psw)
        
        UserApiImpl(context).login(loginName,"B",md5Psw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({loginSuccess(it)},{loginError(it)})
    }

    private fun loginError(it: Throwable?) {
        ToastUtil.showToast(it?.message?:"登录失败")
    }

    /**
     * 登录成功
     */
    private fun loginSuccess(it: ApiResponse<UserInfoEntity>?) {
        ToastUtil.showToast("登录成功")
        var data = it?.retData
        //将用户信息保存到本地
        CacheUtil.getInstance().saveCacheData(data, CacheUtil.USER_INFO)
        //将token保存到本地
        SpUtil.putString(context, SpUtil.TOKEN_KEY,data?.token)
        SpUtil.putString(context, SpUtil.LOGIN_ID_KEY,data?.loginId)
        SpUtil.putString(context,SpUtil.LOGIN_MASTER_ID,data?.masterId)
        //销毁当前页面
        activity.finish()
    }
}