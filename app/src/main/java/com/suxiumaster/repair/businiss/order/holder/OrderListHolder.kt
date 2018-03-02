package com.suxiumaster.repair.businiss.order.holder

import android.view.ViewGroup
import com.suxiumaster.repair.base.BasicHolder
import com.suxiumaster.repair.businiss.order.order.OrderListFragment
import com.suxiumaster.repair.databinding.ItemOrderListBinding
import com.suxiunet.data.entity.order.OrderInfoEntity

/**
 * author : chenzhi
 * time   : 2018/02/10
 * desc   :
 */
class OrderListHolder(parent: ViewGroup, resId: Int) : BasicHolder<OrderInfoEntity, ItemOrderListBinding>(parent, resId) {
    private lateinit var mFragment: OrderListFragment

    constructor(parent: ViewGroup, resId: Int, fragment: OrderListFragment) : this(parent, resId) {
        this.mFragment = fragment
        mBinding.fragment = mFragment
    }

    override fun bindData(data: OrderInfoEntity?) {
        //A:台式机 B：笔记本 C:服务器  D：其它
        mBinding.data = data
        if (data != null) {
            var machineMode = ""
            when (mBinding.data.machineMode) {
                "A" -> machineMode = "台式机"
                "B" -> machineMode = "笔记本"
                "C" -> machineMode = "服务器"
                "D" -> machineMode = "其它"
            }
            mBinding.tvItemOrderEquitModel.text = machineMode
        }
    }
}