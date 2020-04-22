package com.sctuopuyi.echo.ui.web.domain

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.bus.RxBus
import com.sctuopuyi.echo.bus.RxEvent
import com.sctuopuyi.echo.data.DatamanagerHelper
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.sctuopuyi.echo.ui.web.SimpleWebActivity

class AboutMeViewModel(
    private val datamanagerHelper: DatamanagerHelper,
    private var mView: SimpleWebActivity
) :
    ObserableViewModel() {

    //region onSingleClick method

    override fun onSingleClick(v: View) {
        when (v.id) {
            R.id.iv_back -> {
                when (pageType) {
                    SimpleWebActivity.TYPE_PAGE_H5_PRODUCT, SimpleWebActivity.TYPE_PAGE_API_PRODUCT -> {
                        RxBus.publish(RxEvent.EventBean(RxEvent.EVENT_TYPE_UNLOCK_LISTEN, null))
                    }
                    else -> {
                        mView.onBackPressedSupport()
                    }
                }
                mView.onBackPressedSupport()
            }
        }
    }


    fun initData(pageType: String?, productId: String?) {
        this.pageType = pageType
        this.productId = productId
        when (pageType) {
            SimpleWebActivity.TYPE_PAGE_API_PRODUCT -> {
                showBtnStatus.set(true)
            }
            SimpleWebActivity.TYPE_PAGE_H5_PRODUCT -> {
                showBtnStatus.set(false)
            }
            else -> {
                showBtnStatus.set(false)
            }
        }

    }




    private fun showDialogTip() {
//        val builder =
//            CommonDialogOneBtnNoTitle.Builder(mView.mActivity)
//        builder
//            .setContent("已为您完成申请！请耐心等待审核！")
//            .setButton("确认") {
//                builder.closeDialog()
//                mView.finish()
//            }
//            .noCancleTouchButCanBackPress()
//            .create()
//            .show()

        val builder = com.sctuopuyi.echo.utils.dialog.CommonDialogEmpty.Builder(mView)
        builder.setContainer(
            com.sctuopuyi.echo.utils.dialog.CommonDialogEmpty.getFinishApproveView(
                mView
            ) { s ->
                builder.closeDialog()
                mView.finish()
            }
        ).noCancleTouchButCanBackPress().create().show()
    }


    //endregion

    //region other method

    //endregion

    //region all variable

    //region dataBind variable

    val xx = ObservableField<String>()

    //endregion

    //region declare variable
    private var pageType: String? = null
    private var productId: String? = null
    val sendPrivateLock = ObservableField<Boolean>(false)
    val showBtnStatus = ObservableField<Boolean>(false)


    //endregion

    //endregion
}

class AboutMeViewModelFactory(
    private val mView: SimpleWebActivity,
    private val datamanagerHelper: DatamanagerHelper
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AboutMeViewModel::class.java)) {
            return AboutMeViewModel(datamanagerHelper, mView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}