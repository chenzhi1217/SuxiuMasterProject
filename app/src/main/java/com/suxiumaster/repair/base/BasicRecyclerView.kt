package com.suxiumaster.repair.base

import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.suxiumaster.repair.R
import com.suxiumaster.repair.databinding.FragRecyclerviewBasicBinding
import com.suxiumaster.repair.view.CustomRecyclerView
import com.suxiunet.repair.base.baseui.BasicFragment

/**
 * author : chenzhi
 * time   : 2018/02/03
 * desc   : 列表的基类
 */
abstract class BasicRecyclerViewFragment<ITEM > : BasicFragment<FragRecyclerviewBasicBinding>() {
    lateinit var mRecyclerView: CustomRecyclerView
    lateinit var mAdapter: BaseRecyclerViewAdapter<ITEM>
    lateinit var mRecyclerViewManager: LinearLayoutManager
    override fun init() {

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //设置空布局页面
        mBinding.includeBasicEmptyLayout.ivEmpty.setImageResource(setEmptyBgResId())
        mBinding.includeBasicEmptyLayout.tvEmpty.text = getString(setEmptyInfoResId())
        //设置RecyclerView
        mRecyclerView = mBinding.rvBasicRv
        mRecyclerView.setEmptyView(mBinding.includeBasicEmptyLayout.root)
        mAdapter = onCreateAdapter()
        mRecyclerView.layoutManager = onCreateLayoutManager()

        mAdapter.setViewTypeMapper(onCreateViewTypeMapper())
        mRecyclerView.adapter = mAdapter
    }

    /**
     * 创建RecyclerView的Manager
     */
    private fun onCreateLayoutManager(): RecyclerView.LayoutManager? {
        val recyclerColumn = getRecyclerColumn()
        if (recyclerColumn == 1) {
            mRecyclerViewManager = LinearLayoutManager(context)
        } else {
            val gm = GridLayoutManager(context, recyclerColumn)
            gm.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (mAdapter.isLoadMoreItem(position)) {
                        return recyclerColumn
                    } else {
                        return this@BasicRecyclerViewFragment.getItemColumnSize(mAdapter.getObject(position), position)
                    }
                }
            }
            mRecyclerViewManager = gm
        }
        return mRecyclerViewManager
    }

    /**
     * 子类可以重写此方法来决定当前条目所占的列数
     */
    open protected fun getItemColumnSize(item: ITEM, position: Int): Int {
        return getRecyclerColumn()
    }
    
    /**
     * 获取当前RecyclerView的列数，默认情况为1列
     */
    open protected fun getRecyclerColumn(): Int {
        return 1
    }

    abstract fun onCreateViewTypeMapper(): BaseRecyclerViewAdapter.ViewTypeMapper<ITEM>?
    
    /**
     * 创建RecycleView的Adapter
     */
    private fun onCreateAdapter(): BaseRecyclerViewAdapter<ITEM> {
        return object : BaseRecyclerViewAdapter<ITEM>(mRecyclerView) {
            override fun onCreateItemHolder(parent: ViewGroup?, viewType: Int): BasicHolder<out ITEM, out ViewDataBinding> {
                return this@BasicRecyclerViewFragment.onCreateViewHolder(parent,viewType)
            }
        }
    }

    /**
     * 让子类根据当前条目创建对应的Adapter
     */
    abstract fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BasicHolder<out ITEM, out ViewDataBinding>


    /**
     * 空布局时显示的icon
     */
    open protected fun setEmptyBgResId(): Int {
        return R.mipmap.icon_empty_common
    }

    /**
     * 空布局时显示的提示
     */
    private fun setEmptyInfoResId(): Int {
        return R.string.data_is_empty
    }

    /**
     * 下拉刷新的回调方法
     */
    open protected fun refreshData() {
    }

    override fun getContentLayoutId(): Int {
        return R.layout.frag_recyclerview_basic
    }
}