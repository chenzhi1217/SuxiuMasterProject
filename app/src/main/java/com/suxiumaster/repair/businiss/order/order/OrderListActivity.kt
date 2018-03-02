package com.suxiumaster.repair.businiss.order.order

import android.os.Bundle
import com.suxiumaster.repair.R
import com.suxiunet.repair.base.baseui.BasicActivity

/**
 * author : chenzhi
 * time   : 2018/02/09
 * desc   : 订单列表页
 */
class OrderListActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val title = intent.getStringExtra("title")
        val type = intent.getStringExtra("type")
        setToolBarTitle(title)
        startFragment(R.id.fl_basic_act,OrderListFragment(type),OrderListFragment::class.java.simpleName)
    }
}