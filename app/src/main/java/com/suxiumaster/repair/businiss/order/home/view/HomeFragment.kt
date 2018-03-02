package com.suxiumaster.repair.businiss.order.home.view
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.suxiumaster.repair.Constant
import com.suxiumaster.repair.R
import com.suxiumaster.repair.businiss.center.LoginActivity
import com.suxiumaster.repair.businiss.order.order.OrderListActivity
import com.suxiumaster.repair.databinding.FragHomeBinding
import com.suxiumaster.repair.util.ToastUtil
import com.suxiunet.data.util.SpUtil
import com.suxiunet.repair.base.baseui.BasicFragment

/**
 * author : chenzhi
 * time   : 2018/01/27
 * desc   :
 */
class HomeFragment : BasicFragment<FragHomeBinding>() {
    override fun init() {
        //初始化轮播图
        initLunbo()
        mBinding.fragment = this
    }

    override fun getContentLayoutId(): Int {
        return R.layout.frag_home
    }

    /**
     * 初始化显示轮播图
     */
    private fun initLunbo() {
        var images: ArrayList<String> = ArrayList()
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517892853316&di=3dde0fe1c4fd43bb22aa8e6e7288e986&imgtype=0&src=http%3A%2F%2Fpic90.huitu.com%2Fres%2F20161119%2F732704_20161119114417229020_1.jpg")
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1515572537680&di=12c10b2ac43a5194107a105ae2235cae&imgtype=0&src=http%3A%2F%2Fpic.90sjimg.com%2Fback_pic%2Fqk%2Fback_origin_pic%2F00%2F01%2F42%2F3c4a16697485ff3717379118de096f7e.jpg")
        mBinding.vphFragHome.setImageResource(images)
        mBinding.vphFragHome.show()
    }

    /**
     * 拨打公司电话
     */
    fun callToCompany() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Constant.companyPhone + ""))
        startActivity(intent)
    }

    /**
     * 调起QQ聊天
     */
    fun contactWithQQ() {
        try {
            val url = "mqqwpa://im/chat?chat_type=wpa&uin=" + Constant.companyQQ + ""
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
        } catch (e: ActivityNotFoundException) {
            ToastUtil.showToast("您还没有安装手机QQ")
        }
    }

    /**
     * 跳转到待处理订单页面
     */
    fun toWaitOrder() {
        if (isLogin()) {
            val intent = Intent(context, OrderListActivity::class.java)
            intent.putExtra("title", "待处理订单")
            intent.putExtra("type","B")
            startActivity(intent)
        } else {
            startActivity(Intent(context,LoginActivity::class.java))
        }
    }

    /**
     * 跳转到所有订单页面
     */
    fun toAllOrder() = if (isLogin()) {
        val intent = Intent(context, OrderListActivity::class.java)
        intent.putExtra("title", "所有订单")
        intent.putExtra("type","")
        startActivity(intent)
    } else {
        startActivity(Intent(context,LoginActivity::class.java))
    }

    fun isLogin(): Boolean {
        val token = SpUtil.getString(context, SpUtil.TOKEN_KEY, "")
        if (TextUtils.isEmpty(token)) {
            return false
        }
        return true
    }
}
