package com.suxiumaster.repair.util

import android.widget.Toast
import com.suxiumaster.repair.base.SuxiuMasterApplication

/**
 * author : chenzhi
 * time   : 2018/01/31
 * desc   :
 */
/**
 * Created by 月光和我 on 2017/2/6.
 * 静态吐司工具类
 */
object ToastUtil {

    private var mToast: Toast? = null

    fun showToast(text: String) {
        if (null == mToast) {
            mToast = Toast.makeText(SuxiuMasterApplication.appContext, text, Toast.LENGTH_SHORT)
        }
        mToast!!.setText(text)
        mToast!!.show()
    }


    fun showToast(resId: Int) {
        if (null == mToast) {
            mToast = Toast.makeText(SuxiuMasterApplication.appContext, resId, Toast.LENGTH_SHORT)
        }
        mToast!!.setText(resId)
        mToast!!.show()
    }
}