package com.sctuopuyi.echo.ui.startup

import android.os.Handler
import com.sctuopuyi.echo.ui.startup.domain.StartupViewModel
import com.sctuopuyi.echo.ui.startup.domain.StartupViewModelFactory
import com.sctuopuyi.echo.ui.main.MainActivity
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.ui.base.BaseActivity
import com.sctuopuyi.echo.ui.base.BaseVmFactoryBean
import com.sctuopuyi.echo.databinding.ActivityStartupBinding


class StartupActivity : BaseActivity<ActivityStartupBinding, StartupViewModel>() {

    //region callback

    override fun initInject() = getActivityComponent().inject(this)

    override fun getVmFactoryBean() = BaseVmFactoryBean(R.layout.activity_startup, StartupViewModel::class.java, StartupViewModelFactory(this, datamanagerHelper))

    override fun initView() {
        mBinding.vm = vm
        //获取读写权限和uuid权限（为了获取DeviceToken），然后获取SecretKey（请求发起之前会生成DeviceToken）
        vm.initData()
        vm.initPermission()
    }

    fun secretKeyOk() {

        Handler().postDelayed({
            MainActivity.start(mActivity)
            mActivity.onBackPressedSupport()
        }, 500)
    }

    //endregion

}
