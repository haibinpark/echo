package com.sctuopuyi.echo.ui.main

import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.bus.RxBus
import com.sctuopuyi.echo.bus.RxEvent
import com.sctuopuyi.echo.ui.base.BaseActivity
import com.sctuopuyi.echo.ui.base.BaseVmFactoryBean
import com.sctuopuyi.echo.ui.main.domain.MainViewModel
import com.sctuopuyi.echo.ui.main.domain.MainViewModelFactory
import com.sctuopuyi.echo.databinding.ActivityMainBinding
import com.sctuopuyi.echo.utils.ScreentUtil
import io.reactivex.disposables.Disposable


class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    private var disposable: Disposable? = null

    override fun initView() {
        mBinding.vm = vm
        fromType = intent.getStringExtra(KEY_TYPE)
        initDefaultView()
        initEvent()
        vm.initData()
        val display = Build.DISPLAY
        val width = ScreentUtil.getScreenPxWidth(this)
        val height = ScreentUtil.getScreenPxHeight(this)
        val dpi = ScreentUtil.getDpi(this)
        Log.d("设备信息", "设置的宽x高xdpi:${width}x${height}x${dpi}")
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            val secondTime = System.currentTimeMillis()
            if (secondTime - pressTime > 1500) {
                com.sctuopuyi.echo.utils.ToastUtil.shortShow("在点击一次，退出应用")
                pressTime = secondTime
                return true
            }
        } else {
            finish()
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun initDefaultView() {

    }

    override fun onResume() {
        super.onResume()
        registerListener()
        vm.onResume()
    }

    override fun onPause() {
        super.onPause()
        unregisterListener()
        vm.onPause()
    }

    private fun unregisterListener() {
//        unregisterReceiver(mSdcardReceiver)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable?.isDisposed == false) disposable?.dispose()
        if (disposableListen?.isDisposed == false) disposableListen?.dispose()
        vm.onDestroy()
    }


    override fun initInject() = getActivityComponent().inject(this)

    override fun getVmFactoryBean(): BaseVmFactoryBean<MainViewModel> = BaseVmFactoryBean(
        R.layout.activity_main,
        MainViewModel::class.java,
        MainViewModelFactory(this, datamanagerHelper)
    )

    private fun initEvent() {
        disposableListen = RxBus.listen(RxEvent.EventBean::class.java).subscribe {
            vm.onListen(it)
        }
    }

    private fun registerListener() {
//        val filter = IntentFilter()
//        filter.addAction(Intent.ACTION_MEDIA_MOUNTED)
//        filter.addAction(Intent.ACTION_MEDIA_REMOVED)
//        filter.addDataScheme("file");
//        registerReceiver(mSdcardReceiver, filter, "android.permission.READ_EXTERNAL_STORAGE", null)
    }

    private var disposableListen: Disposable? = null

//    val mSdcardReceiver: BroadcastReceiver = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            when (intent?.action) {
//                Intent.ACTION_MEDIA_MOUNTED -> {
//                    //磁盘已经插入
//                    sdcardPath = intent?.data?.path
//                }
//                Intent.ACTION_MEDIA_REMOVED -> {
//
//                }
//            }
//        }
//    }


    var fromType: String? = null
    var jkglFragment: Fragment? = null
    var sdcardPath: String? = null

    companion object {
        val KEY_TYPE = "typeKey"
        val TYPE_OUT_ACTIVITY = "outActivityType"
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }

        fun startForJkgl(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            starter.putExtra(KEY_TYPE, TYPE_OUT_ACTIVITY)
            context.startActivity(starter)
        }
    }
}
