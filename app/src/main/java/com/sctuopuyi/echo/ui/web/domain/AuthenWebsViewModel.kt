package com.sctuopuyi.echo.ui.web.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.databinding.ObservableField
import android.view.View
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.data.DatamanagerHelper
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.sctuopuyi.echo.ui.web.AuthenWebsActivity

class AuthenWebsViewModel(
    private val datamanagerHelper: DatamanagerHelper,
    private var mView: AuthenWebsActivity
) :
    ObserableViewModel() {

    //region onClick method

    override fun onSingleClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                showExitDialog()
            }
        }
    }

    fun showExitDialog() {
        if (mView.mebview?.canGoBack() == true) {
            mView.mebview?.goBack()
        } else {
            goback()
        }
    }

    //endregion

    //region other method

    fun getHttpDatas() {

    }

    fun getListDatas(page: Int) {

    }


    fun initData(extraData: String?) {
        if (extraData.isNullOrEmpty()) {

        } else {
            val datas = extraData.split("||")
            if (datas.isNotEmpty()) {
                pageType = datas[0]
                productId = datas[1]
            }
        }
    }

    fun goback() {
        val builder = com.sctuopuyi.echo.utils.dialog.CommonDialogTwoBtn.Builder(mView)
        builder.setTitleAndContent("提示消息", "退出该页面可能中断申请流程，是否确认退出？？")
            .setButton("确认退出") {
                builder.closeDialog()
                mView.onBackPressedSupport()
            }.setButton2("继续申请") {
                builder.closeDialog()
            }.noCancleTouchButCanBackPress().create().show()
    }

    //endregion

    //region all variable

    //region dataBind variable

    val xx = ObservableField<String>()
    private var pageType: String? = ""
    private var productId: String? = ""

    //endregion

    //region declare variable


    //endregion

    //endregion
}

class AuthenWebsViewModelFactory(
    private val mView: AuthenWebsActivity,
    private val datamanagerHelper: DatamanagerHelper
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthenWebsViewModel::class.java)) {
            return AuthenWebsViewModel(datamanagerHelper, mView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}