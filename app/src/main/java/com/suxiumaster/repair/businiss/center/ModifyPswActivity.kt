package com.suxiumaster.repair.businiss.center

import android.os.Bundle
import com.suxiumaster.repair.R
import com.suxiunet.repair.base.baseui.BasicActivity

/**
 * author : chenzhi
 * time   : 2018/01/29
 * desc   :
 */
class ModifyPswActivity : BasicActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setToolBarTitle("修改密码")
        startFragment(R.id.fl_basic_act, ModifyPswFragment::class.java, ModifyPswFragment::class.java.simpleName)
    }
}