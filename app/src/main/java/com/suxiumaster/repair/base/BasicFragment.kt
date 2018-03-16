package com.suxiunet.repair.base.baseui

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suxiumaster.repair.R
import com.suxiumaster.repair.base.MainActivity
import com.suxiumaster.repair.base.SuxiuMasterApplication
import com.suxiumaster.repair.businiss.center.LoginActivity
import com.suxiumaster.repair.databinding.FragBasicBinding
import com.suxiunet.data.exception.ApiException

/**
 * author : chenzhi
 * time   : 2018/01/03
 * desc   : 所有列表页面的基类
 */
abstract class BasicFragment<BIND : ViewDataBinding> : Fragment() {
    lateinit var mParentBinding: FragBasicBinding
    lateinit var mBinding: BIND
    //加载中的view
    lateinit var mLoadingView: View
    //加载失败的view
    lateinit var mErrorView: View
    //加载成功的View
    lateinit var mContentView: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //总的Binding
        mParentBinding = DataBindingUtil.inflate<FragBasicBinding>(inflater, R.layout.frag_basic, container, false)
        mErrorView = mParentBinding.includeCommonError.root
        mLoadingView = mParentBinding.includeCommonLoading.root
        //内容的Binding
        mBinding = DataBindingUtil.inflate<BIND>(inflater, getContentLayoutId(), container, false)
        mContentView = mBinding?.root
        mParentBinding.flBasicFrag.addView(mContentView)
        //设置失败页面
        mParentBinding?.includeCommonError?.ivNetError?.setImageResource(getErrorIconResid())
        mParentBinding?.includeCommonError?.root?.setOnClickListener { onLoadAgain() }
        //设置SwifeRefresh
        mParentBinding.sfBasicFrag.isEnabled = setSwipeRefreshEnable()
        mParentBinding.sfBasicFrag.setColorSchemeResources(R.color.theme_bg)
        mParentBinding.sfBasicFrag.setOnRefreshListener { onRefresh() }
        showContentView()
        init()
        return mParentBinding?.root
    }

    abstract fun init()

    /**
     * 下拉刷新的回掉
     */
    open protected fun onRefresh() {
    }

    /**
     * 失败重新加载
     */
    open protected fun onLoadAgain() {
    }

    /**
     * 下拉刷新控件默认是不可用的
     */
    open protected fun setSwipeRefreshEnable(): Boolean {
        return false
    }

    /**
     * 失败页面的icon
     */
    open protected fun getErrorIconResid(): Int {
        return R.mipmap.icon_net_error
    }

    /**
     * 让子类返回布局id
     */
    abstract fun getContentLayoutId(): Int

    /**
     * 显示加载成功的布局
     */
    open protected fun showContentView() {
        switchView(mContentView, mErrorView, mLoadingView)
    }

    /**
     * 显示加载失败的布局
     */
    open protected fun showErrorView() {
        switchView(mErrorView, mContentView, mLoadingView)
    }

    /**
     * 显示加载中的布局
     */
    protected fun showLoadingView() {
        switchView(mLoadingView, mContentView, mErrorView)
    }

    /**
     * 切换三种布局的显示预隐藏
     */
    private fun switchView(showView: View, vararg hideView: View) {
        if (hideView == null || hideView.size == 0) {
            return
        }
        showView.visibility = View.VISIBLE
        for (view in hideView) {
            view.visibility = View.GONE
        }
    }

    /**
     * 检测是否登录
     */
    fun checkUnLoad(e: Throwable?) {
        if (e is ApiException) {
            if (e.displayCode == 405) {
                //跳转到登录界面
                var intent = Intent(SuxiuMasterApplication.appContext, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                activity?.startActivity(intent)
                activity.finish()
            }
        }
    }
}