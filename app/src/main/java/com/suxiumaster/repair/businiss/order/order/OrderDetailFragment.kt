package com.suxiumaster.repair.businiss.order.order

import android.content.Intent
import android.text.TextUtils
import android.view.View
import com.suxiumaster.repair.R
import com.suxiumaster.repair.databinding.FragOrderDetailBinding
import com.suxiunet.data.entity.order.OrderInfoEntity
import com.suxiunet.repair.base.baseui.BasicFragment

/**
 * author : chenzhi
 * time   : 2018/02/10
 * desc   : 订单详情页面
 */
class OrderDetailFragment: BasicFragment<FragOrderDetailBinding>() {
    override fun init() {
        val orderInfo = (activity as OrderDetailActivity).getOrderInfo()
        val pageType = (activity as OrderDetailActivity).getPageType()
        if (TextUtils.isEmpty(pageType)) {
            //隐藏按钮
            mBinding?.btComplete.visibility = View.GONE
        }
        mBinding.data = orderInfo
        initData(orderInfo)
        mBinding.btComplete.setOnClickListener { 
            //点击完成维修
            val intent = Intent(context, ConfirmOrderActivity::class.java)
            intent.putExtra(ConfirmOrderActivity.ORDER_NO,orderInfo.orderNo)
            startActivity(intent)
        }
    }

    override fun getContentLayoutId(): Int {
        return R.layout.frag_order_detail
    }

    private fun initData(orderInfo: OrderInfoEntity?) {
        var equitLx = ""
        if (orderInfo != null) {
            when (orderInfo.machineMode) {
            //A:台式机 B：笔记本 C:服务器  D：其它
                "A" -> equitLx = getString(R.string.desktop)
                "B" -> equitLx = getString(R.string.notebook)
                "C" -> equitLx = getString(R.string.server)
                "D" -> equitLx = getString(R.string.other)
            }
            mBinding.includeOrderDetailInfo.tvEquitSblx.text = equitLx
        }
    }
}
