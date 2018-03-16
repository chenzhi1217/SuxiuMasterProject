package com.suxiumaster.repair.businiss.center

import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.suxiumaster.repair.R
import com.suxiumaster.repair.databinding.FragLoginBinding
import com.suxiumaster.repair.databinding.FragModifyPswBinding
import com.suxiumaster.repair.util.ToastUtil
import com.suxiunet.data.api.UserApiImpl
import com.suxiunet.data.entity.base.ApiResponse
import com.suxiunet.data.util.MD5Utils
import com.suxiunet.data.util.SpUtil
import com.suxiunet.repair.base.baseui.BasicFragment
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * author : chenzhi
 * time   : 2018/01/29
 * desc   :
 */
class ModifyPswFragment: BasicFragment<FragModifyPswBinding>() {
    override fun init() {
        mBinding.tvQueryModify.setOnClickListener { 
            //确认修改密码
            queryModify()
        }
    }

    private fun queryModify() {
        //拿到新密码和老密码
        val oriPsw = mBinding?.etOriPsw.text.toString().trim()
        val newPsw = mBinding?.etNewPsw.text.toString().trim()
        if (oriPsw.isNullOrEmpty()) {
            ToastUtil.showToast("请输入原始密码")
            return
        }
        if (newPsw.isNullOrEmpty()) {
            ToastUtil.showToast("请输入新密码密码")
            return
        }
        //开始修改
        val loginId = SpUtil.getString(context, SpUtil.LOGIN_ID_KEY, "")
        var md5OriPsw = MD5Utils.encode(oriPsw)
        val md5NewPsw = MD5Utils.encode(newPsw)
        UserApiImpl(context).modifyPsw(loginId,md5OriPsw,md5NewPsw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({modifySuccess(it)},{modifyError(it)})
    }

    /**
     * 修改失败
     */
    private fun modifyError(it: Throwable?) {
        ToastUtil.showToast(it?.message?:"修改失败")
    }

    /**
     * 修改成功
     */
    private fun modifySuccess(it: ApiResponse<Any>?) {
        ToastUtil.showToast("修改成功")
        activity?.finish()
    }

    override fun getContentLayoutId(): Int {
        return R.layout.frag_modify_psw
    }

}
