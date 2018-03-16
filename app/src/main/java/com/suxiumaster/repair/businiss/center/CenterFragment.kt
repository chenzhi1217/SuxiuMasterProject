package com.suxiumaster.repair.businiss.center

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.suxiumaster.repair.R
import com.suxiumaster.repair.databinding.FragCenterBinding
import com.suxiumaster.repair.util.ToastUtil
import com.suxiunet.data.api.UserApiImpl
import com.suxiunet.data.entity.base.ApiResponse
import com.suxiunet.data.entity.user.UserInfoEntity
import com.suxiunet.data.util.CacheUtil
import com.suxiunet.data.util.SpUtil
import com.suxiunet.repair.base.baseui.BasicFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * author : chenzhi
 * time   : 2018/01/27
 * desc   : 个人中心页面
 */
class CenterFragment : BasicFragment<FragCenterBinding>() {

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUserInfo()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            setUserInfo()
        }
    }
    
    override fun getContentLayoutId(): Int {
        return R.layout.frag_center
    }

    override fun init() {
        //退出登录
        mBinding.rlQuitLogo.setOnClickListener { quitLogo() }
        //修改密码
        mBinding.rlModifyPsw.setOnClickListener { modfyPsw() }
        //修改性别
        mBinding.rlModifySex.setOnClickListener { modifySex() }
        //联系公司
        mBinding.rlContactCompany.setOnClickListener { contactCompany() }
        //点击头像
        mBinding.ivFragCenterUser.setOnClickListener {
            checkLogin()
        }
        //点击昵称
        mBinding.tvFragCenterNickName.setOnClickListener {
            checkLogin()
        }
    }

    /**
     * 跳转到登录界面
     */
    private fun checkLogin() {
        val token = SpUtil.getString(context, SpUtil.TOKEN_KEY, "")
        if (TextUtils.isEmpty(token)) {
            //跳转到登录界面
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    /**
     * 联系公司
     */
    private fun contactCompany() {
    }

    /**
     * 修改性别
     */
    private fun modifySex() {
        startActivity(Intent(context,ModifySexActivity::class.java))
    }

    /**
     * 跳转到修改密码页面
     */
    private fun modfyPsw() {
        startActivity(Intent(context,ModifyPswActivity::class.java))
    }

    /**
     * 退出登录的Dialog
     */
    private fun quitLogo() {
        AlertDialog.Builder(activity)
                .setTitle("确认退出？")
                .setNegativeButton("取消",DialogInterface.OnClickListener { dialog, which ->  })
                .setPositiveButton("确认",DialogInterface.OnClickListener { dialog, which -> 
                    loginOut()
                })
                .create().show()
    }

    /**
     * 退出登录
     */
    private fun loginOut() {
        val loginId = SpUtil.getString(context, SpUtil.LOGIN_ID_KEY, "")
        UserApiImpl(context).quitLogin(loginId,"B")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({loginOutSuccess(it)},{loginOutError(it)})
    }

    private fun loginOutError(it: Throwable?) {
        ToastUtil.showToast("退出失败")
    }

    /**
     * 退出成功
     */
    private fun loginOutSuccess(it: ApiResponse<Any>?) {
        ToastUtil.showToast("退出成功")
        mBinding.rlQuitLogo.visibility = View.GONE
        //清除token和用户信息
        try {
            SpUtil.putString(context, SpUtil.TOKEN_KEY, "")
            SpUtil.putString(context, SpUtil.LOGIN_ID_KEY, "")
            SpUtil.putString(context, SpUtil.LOGIN_MASTER_ID, "")
            CacheUtil.getInstance().saveCacheData(null, CacheUtil.USER_INFO)
        } catch (e: Exception) {
        }
        setUserInfo()
    }

    override fun onResume() {
        super.onResume()
        setUserInfo()
    }

    /**
     * 设置用户数据
     */
    fun setUserInfo() {
        val token = SpUtil.getString(context, SpUtil.TOKEN_KEY, "")
        val userInfo = CacheUtil.getInstance().getCacheData(CacheUtil.USER_INFO, UserInfoEntity::class.java)
        mBinding.tvFragCenterNickName.text = if (TextUtils.isEmpty(token)) "立即登录" else userInfo?.loginName?:""
        mBinding.rlQuitLogo.visibility = if (TextUtils.isEmpty(token)) View.GONE else View.VISIBLE
    }
}