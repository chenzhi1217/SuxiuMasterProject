package com.suxiumaster.repair.businiss.order.order

import android.content.Intent
import com.suxiumaster.repair.R
import com.suxiumaster.repair.base.MainActivity
import com.suxiumaster.repair.databinding.FragFirmOrderBinding
import com.suxiumaster.repair.util.ToastUtil
import com.suxiunet.data.api.OrderApiImpl
import com.suxiunet.data.entity.base.ApiResponse
import com.suxiunet.data.util.SpUtil
import com.suxiunet.repair.base.baseui.BasicFragment
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * author : chenzhi
 * time   : 2018/02/10
 * desc   :
 */
class ConfirmOrderFragment: BasicFragment<FragFirmOrderBinding>() {
    var map = HashMap<String,String>()
    override fun init() {
        mBinding.btConfirmOrder.setOnClickListener { 
            confirmOrder()
        }
    }

    override fun getContentLayoutId(): Int {
        return R.layout.frag_firm_order
    }

    /**
     * 确认订单的逻辑
     */
    private fun confirmOrder() {
        if (!checkData()) {
            return
        }
        OrderApiImpl(context).confirmOrder(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({confirmSuccess(it)},{confirmError(it)})
    }
    
    /**
     * 确认订单失败
     */
    private fun confirmError(it: Throwable?) {
        ToastUtil.showToast("请求失败")
    }
    
    /**
     * 确认订单成功
     */
    private fun confirmSuccess(it: ApiResponse<Any>?) {
        ToastUtil.showToast("确认成功")
        startActivity(Intent(context,MainActivity::class.java))
    }
    
    /**
     * 对数据进行判空
     */
    fun checkData(): Boolean {
        //维修金额
        val price = mBinding.etPrice.text.toString().trim()
        //维修方案
        val maintenancePlan = mBinding.etMaintenancePlan.text.toString().trim()
        //保修时间
        val warrantyTime = mBinding.etWarrantyTime.text.toString().trim()

        if (price.isNullOrEmpty()) {
            ToastUtil.showToast("请输入金额")
            return false
        }
        if (warrantyTime.isNullOrEmpty()) {
            ToastUtil.showToast("请输入保修期")
            return false
        }
        if (maintenancePlan.isNullOrEmpty()) {
            ToastUtil.showToast("请输入维修方案")
            return false
        }
        val loginId = SpUtil.getString(context, SpUtil.LOGIN_ID_KEY, "")
        val orderNo = (activity as ConfirmOrderActivity).returnOrderNo()
        map.put("loginId",loginId)
        map.put("orderNo",orderNo)
        map.put("maintenanceAmt",price)
        map.put("maintenancePlan", maintenancePlan)
        map.put("warrantyTime",warrantyTime)
        return true
    }
}
