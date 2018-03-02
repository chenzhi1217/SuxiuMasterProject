package com.suxiumaster.repair.businiss.order.order

import android.os.Bundle
import com.suxiumaster.repair.R
import com.suxiunet.data.entity.order.OrderInfoEntity
import com.suxiunet.repair.base.baseui.BasicActivity

/**
 * author : chenzhi
 * time   : 2018/02/10
 * desc   :
 */
class OrderDetailActivity : BasicActivity() {
    lateinit var mOrderInfo : OrderInfoEntity
    companion object {
        val ORDER_INFO_KEY = "order_info_key"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //拿到订单详情数据
        mOrderInfo = intent.getSerializableExtra(ORDER_INFO_KEY) as OrderInfoEntity
        setToolBarTitle("订单详情")
        startFragment(R.id.fl_basic_act,OrderDetailFragment::class.java,OrderDetailFragment::class.java.simpleName)
    }

    /**
     * 向外部提供订单详情数据
     */
    fun getOrderInfo(): OrderInfoEntity {
        return mOrderInfo
    }
}