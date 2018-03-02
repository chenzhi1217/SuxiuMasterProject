package com.suxiumaster.repair.base

import android.app.Application
import com.suxiunet.data.DatasConstant

/**
 * author : chenzhi
 * time   : 2018/01/31
 * desc   :
 */
class SuxiuMasterApplication : Application() {
    
    companion object {
        lateinit var appContext: SuxiuMasterApplication
    }
    
    override fun onCreate() {
        super.onCreate()
        appContext = this
        DatasConstant.mApplication = this
    }
}