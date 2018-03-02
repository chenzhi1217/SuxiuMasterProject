package com.suxiumaster.repair.businiss.center

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import com.suxiumaster.repair.R
import com.suxiumaster.repair.databinding.FragModifySexBinding
import com.suxiumaster.repair.util.ToastUtil
import com.suxiunet.data.api.UserApiImpl
import com.suxiunet.data.entity.base.ApiResponse
import com.suxiunet.data.entity.user.UserInfoEntity
import com.suxiunet.data.util.CacheUtil
import com.suxiunet.data.util.SpUtil
import com.suxiunet.repair.base.baseui.BasicFragment
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * author : chenzhi
 * time   : 2018/02/10
 * desc   :
 */
class ModifySexFragment: BasicFragment<FragModifySexBinding>() {
    //记录当前选中的选项
    var checkIndex: Int = 0
    lateinit var images: ArrayList<ImageView>
    override fun init() {
        mBinding.fragment = this
        images = ArrayList()
        images.add(mBinding.ivModifySexMen)
        images.add(mBinding.ivModifySexWomen)
        images.add(mBinding.ivModifySexSecret)

        //初始化性别数据展示
        val userInfo = CacheUtil.getInstance().getCacheData(CacheUtil.USER_INFO, UserInfoEntity::class.java)
        when (userInfo.gender) {
            "0" -> checkIndex = 0
            "1" -> checkIndex = 1
            else -> checkIndex = 2
        }
        initCheckState()
    }

    override fun getContentLayoutId(): Int {
        return R.layout.frag_modify_sex
    }

    /**
     * 初始化选中状态
     */
    private fun initCheckState() {
        images[0].setImageResource(R.mipmap.icon_choose_nomal)
        images[1].setImageResource(R.mipmap.icon_choose_nomal)
        images[2].setImageResource(R.mipmap.icon_choose_nomal)
        images[checkIndex].setImageResource(R.mipmap.icon_choose_select)
    }

    fun switchCheck(index: Int) {
        if (index == checkIndex) {
            return
        }
        images[checkIndex].setImageResource(R.mipmap.icon_choose_nomal)
        images[index].setImageResource(R.mipmap.icon_choose_select)
        checkIndex = index
    }

    /**
     * 创建ToolBar菜单按钮
     */
    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.confirm_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.confirm_modify) {
            //拿到当前选中的性别
            val gendentState = getGendentState()
            modifyForService(gendentState)
        }
        return true
    }

    fun getGendentState(): String {
        when (checkIndex) {
            0 -> return "0"
            1 -> return "1"
            else -> return "2"
        }
    }

    /**
     * 向服务端发起修改请求
     */
    private fun modifyForService(gendentState: String) {
        val loginId = SpUtil.getString(context, SpUtil.LOGIN_ID_KEY, "")
        UserApiImpl(context).modifyGender(loginId,gendentState)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: Subscriber<ApiResponse<Any>>() {
                    override fun onCompleted() {
                        
                    }
                    //修改成功
                    override fun onNext(t: ApiResponse<Any>?) {
                        //更改本地的用户信息
                        val userInfo = CacheUtil.getInstance().getCacheData(CacheUtil.USER_INFO, UserInfoEntity::class.java)
                        userInfo.gender = gendentState
                        CacheUtil.getInstance().saveCacheData(userInfo, CacheUtil.USER_INFO)
                        activity.finish()
                    }
                    //修改失败
                    override fun onError(e: Throwable?) {
                        ToastUtil.showToast(e?.message?:"修改失败")
                    }

                })
    }
}
