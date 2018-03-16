package com.suxiumaster.repair.businiss.order.order

import android.content.Intent
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.ViewGroup
import com.suxiumaster.repair.R
import com.suxiumaster.repair.base.BaseRecyclerViewAdapter
import com.suxiumaster.repair.base.BasicHolder
import com.suxiumaster.repair.base.BasicRecyclerViewFragment
import com.suxiumaster.repair.businiss.order.holder.OrderListHolder
import com.suxiunet.data.api.OrderApiImpl
import com.suxiunet.data.entity.base.ApiResponse
import com.suxiunet.data.entity.order.OrderInfoEntity
import com.suxiunet.data.entity.order.OrderListEntity
import com.suxiunet.data.util.SpUtil
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * author : chenzhi
 * time   : 2018/02/09
 * desc   : 订单列表
 */
class OrderListFragment(): BasicRecyclerViewFragment<OrderInfoEntity>() {
    
    var mCurStatus = "A"
    
    companion object {
        val TYPE_ORDER_ITEM = R.layout.item_order_list
    }
    
    constructor(status: String):this() {
        this.mCurStatus = status
    }

    override fun onCreateViewTypeMapper(): BaseRecyclerViewAdapter.ViewTypeMapper<OrderInfoEntity>? {
        return BaseRecyclerViewAdapter.ViewTypeMapper<OrderInfoEntity> { _, _ -> TYPE_ORDER_ITEM }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BasicHolder<out OrderInfoEntity, out ViewDataBinding> {
        return OrderListHolder(parent!!,viewType,this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //开始请求网络获取数据
        getOrderData()
    }

    override fun setSwipeRefreshEnable(): Boolean {
        return true
    }

    override fun onRefresh() {
        super.onRefresh()
        getOrderData()
    }

    /**
     * 从服务端获取订单数据
     */
    private fun getOrderData() {
        val loginId = SpUtil.getString(context, SpUtil.LOGIN_ID_KEY, "")
        val masterId = SpUtil.getString(context, SpUtil.LOGIN_MASTER_ID, "")
        OrderApiImpl(context).getOrderList(loginId,mCurStatus,"B")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    loadSuccess(it)
                },{loadError(it)})
    }

    /**
     * 订单数据获取失败
     */
    private fun loadError(it: Throwable?) {
        mParentBinding.sfBasicFrag.isRefreshing = false
        showErrorView()
        checkUnLoad(it)
    }

    /**
     * 订单列表数据获取成功
     */
    private fun loadSuccess(data: ApiResponse<OrderListEntity>?) {
        mParentBinding.sfBasicFrag.isRefreshing = false
        showContentView()
        //给RecyclerView设置数据
        var list = ArrayList<OrderInfoEntity>()
        if (data != null && data.retData != null && data.retData.orderInfolist != null && data.retData.orderInfolist.size > 0) {
            list.addAll(data.retData.orderInfolist)
        }
        mAdapter.setItems(list)
        mAdapter.notifyDataSetChanged()
    }

    /**
     * 跳转到订单详情页面
     */
    fun goOrderDetail(data: OrderInfoEntity) {
        val intent = Intent(context, OrderDetailActivity::class.java)
        intent.putExtra(OrderDetailActivity.ORDER_INFO_KEY,data)
        startActivity(intent)
    }
    
}
