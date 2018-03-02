package com.suxiumaster.repair.businiss.order.order

import android.os.Bundle
import com.suxiumaster.repair.R
import com.suxiunet.repair.base.baseui.BasicActivity

/**
 * author : chenzhi
 * time   : 2018/02/10
 * desc   :
 */
class ConfirmOrderActivity : BasicActivity() {
    var orderNo = ""
    companion object {
        val ORDER_NO = "order_no"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        orderNo = intent.getStringExtra(ORDER_NO)
        setToolBarTitle("确认订单")
        startFragment(R.id.fl_basic_act,ConfirmOrderFragment::class.java,ConfirmOrderFragment::class.java.simpleName)
    }

    fun returnOrderNo(): String {
        return orderNo
    }
}