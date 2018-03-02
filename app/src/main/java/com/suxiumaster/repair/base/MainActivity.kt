package com.suxiumaster.repair.base

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.RadioGroup
import com.suxiumaster.repair.R
import com.suxiumaster.repair.businiss.center.CenterFragment
import com.suxiumaster.repair.businiss.order.home.view.HomeFragment
import com.suxiumaster.repair.databinding.ActMainBinding
import com.suxiunet.data.util.Utils
import java.util.ArrayList

/**
 * author : chenzhi
 * time   : 2018/01/27
 * desc   :
 */
class MainActivity : AppCompatActivity() {

    var mFragments: ArrayList<Fragment> = ArrayList()
    var mBinding: ActMainBinding? = null
    var mIndex: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView<ActMainBinding>(this, R.layout.act_main)
        initView()
        //初始化ToolBar
        initToolBar()
        initFragment()
    }

    private fun initFragment() {
        val homeFragment = HomeFragment()
        val centerFragment = CenterFragment()
        mFragments.add(homeFragment)
        mFragments.add(centerFragment)

        supportFragmentManager.beginTransaction().add(R.id.main_framlayout,homeFragment).show(homeFragment).commit()
        changeTitle("速修电脑")
    }

    private fun initView() {
        //初始化RadioGroup的选中监听事件
        mBinding?.mainRg?.setOnCheckedChangeListener(onCheckedChangeListener)

        //设置RadioButton图片的大小
        Utils.setRadioButtonSize(this, R.drawable.selector_lable_home, mBinding?.rbMainHome, 22)
        Utils.setRadioButtonSize(this, R.drawable.selector_lable_center, mBinding?.rbMainCenter, 22)
    }
    
     

    private fun initToolBar() {
        setSupportActionBar(mBinding?.tbMain)
        //取消返回键
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //隐藏原生标题
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private val onCheckedChangeListener = object : RadioGroup.OnCheckedChangeListener {
        override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
            when (checkedId) {
                R.id.rb_main_home -> {
                    setCheckedFragment(0)
                    changeTitle("速修电脑")
                }
                R.id.rb_main_center ->{
                    setCheckedFragment(1)
                    changeTitle("我的")
                }
            }
        }
    }


    private fun changeTitle(title: String) {
        mBinding?.tvMainTitle?.text = title
    }

    private fun setCheckedFragment(index: Int) {
        if (index == mIndex) {
            return
        }
        val curFragment = mFragments[index]
        //开启一个事物
        val transaction = supportFragmentManager.beginTransaction()
        //隐藏上一个Fragment
        transaction.hide(mFragments[mIndex])
        //显示当前Fragment
        if (!curFragment.isAdded) {
            transaction.add(R.id.main_framlayout, curFragment)
        } else {
            transaction.show(curFragment)
        }
        //提交事务
        transaction.commit()
        mIndex = index
    }
}
