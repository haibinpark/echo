package com.sctuopuyi.echo.ui.startup.domain

import android.Manifest
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.data.DatamanagerHelper
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.sctuopuyi.echo.ui.startup.StartupActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.Disposable

class StartupViewModel(
    private val datamanagerHelper: DatamanagerHelper,
    private var mView: StartupActivity
) :
    ObserableViewModel() {

    //region onSingleClick method

    //endregion

    //region other method
    private lateinit var disposable: Disposable;

    fun getSecretKey() {
        //获取并存储公钥

    }

    fun initPermission() {
        disposable = RxPermissions(mView)
            .request(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )
            .subscribe { permission ->
                when {
                    permission -> {
                        // 用户已经同意该权限
                        //不管用户同不同意，都进行下一步
                        if (datamanagerHelper.getSecretKey().isNullOrBlank()) {
                            mView.secretKeyOk()
//                            getSecretKey()
                        } else {
                            if (datamanagerHelper.getIsLogin()) {
//                                    sendExtraInfo()
                                mView.secretKeyOk()
                            } else {
                                mView.secretKeyOk()
                            }
                        }
                    }
                    else -> {
                        // 用户拒绝了该权限，并且选中『不再询问』,读取唯一识别码,禁止也不用管
                        com.sctuopuyi.echo.utils.ToastUtil.shortShow("请设置相关权限为允许!")
                        android.os.Handler().postDelayed(Runnable {
                            mView.runOnUiThread {
                                mView.finish()
                            }
                        }, 800)
                    }
                }
            }
    }

    fun initData() {
        copyRight.set("Copyright © 2020 ${mView.resources.getString(R.string.app_name)} 版权所有")
    }

    //endregion

    //region all variable

    //region dataBind variable
    val copyRight = ObservableField<String>()

    //endregion

    //region declare variable


    //endregion

    //endregion
}

class StartupViewModelFactory(
    private val mView: StartupActivity,
    private val datamanagerHelper: DatamanagerHelper
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StartupViewModel::class.java)) {
            return StartupViewModel(datamanagerHelper, mView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}