package com.suxiumaster.repair.businiss.center

import android.os.Bundle
import com.suxiumaster.repair.R
import com.suxiunet.repair.base.baseui.BasicActivity

/**
 * author : chenzhi
 * time   : 2018/01/31
 * desc   : 登录页面
 */
class LoginActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle("用户登录")
        startFragment(R.id.fl_basic_act,LoginFragment::class.java,LoginFragment::class.java.simpleName)
    }
}