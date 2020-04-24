package com.sctuopuyi.echo.ui.main.domain

import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sctuopuyi.echo.bus.RxEvent
import com.sctuopuyi.echo.bus.RxEvent.Companion.EVENT_TYPE_SETTING_PASSWORD_OK
import com.sctuopuyi.echo.data.DatamanagerHelper
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.sctuopuyi.echo.ui.main.MainActivity
import com.sctuopuyi.echo.utils.MyTaskUtil
import com.sctuopuyi.echo.utils.DateUtil
import io.reactivex.disposables.Disposable

class MainViewModel(
    private var datamanagerHelper: DatamanagerHelper,
    private var mView: MainActivity
) :
    ObserableViewModel() {

    //region onSingleClick method

    override fun onSingleClick(v: View) {
        when (v.id) {

        }
    }

    fun requestLocation() {
        if (com.sctuopuyi.echo.utils.DateUtil.getCurrentStampLong() < (datamanagerHelper.getLocationInfoUpdateTimestamp() + 24 * 3600 * 1000)) return
//        BdLocationUtil.getInstance().requestLocation(object : BdLocationUtil.MyLocationListener {
//            override fun myLocation(location: BDLocation) {
//                if (datamanagerHelper.getIsLogin())
//                    pushLocation(location)
//            }
//        })
    }


//    fun checkUpdate() {
//        /**
//         * 检查是否有版本更新
//         */
//        val request = CheckUpdateRequest()
//        request.versionCode = BuildConfig.VERSION_CODE
//        request.versionType = PlatformUtil.getBuildTypeForRequestUpdate()
//        Updater.check(
//            mView,
//            "${BuildConfig.API_URL_MAIN}appVersionMgr/",
//            request,
//            BuildConfig.PACKAGE_TAG
//        )
//    }

    fun onListen(it: RxEvent.EventBean?) {
        when (it?.pageType) {
            EVENT_TYPE_SETTING_PASSWORD_OK -> {
//                SettingActivity.start(mView)
            }
        }
    }

    fun onDestroy() {
        if (disposable?.isDisposed == false) disposable?.dispose()
        if (xyDisposable?.isDisposed == false) xyDisposable?.dispose()
    }

    fun getLoginStatus(): Boolean {
        return datamanagerHelper.getIsLogin()
    }

    fun initData() {
        requestXyFlag = false
    }

    fun setIsToProductDetail(b: Boolean) {
        this.isToProductDetail = b
    }

    fun onResume() {
        localNavigateApproveTag = false
        datamanagerHelper.setMainOutListenTag(false)
        myTaskUtil.startTimer()
    }

    fun onPause() {
        myTaskUtil.stopTimer()
        if (!localNavigateApproveTag)
            datamanagerHelper.setMainOutListenTag(true)
    }

    fun setLocalApproveTag(status: Boolean) {
        localNavigateApproveTag = status
    }

    fun setHasMessageStatus(b: Boolean) {
        hasMessageTag.set(b)
    }


    private var disposable: Disposable? = null
    private var xyDisposable: Disposable? = null
    private var requestXyFlag: Boolean = false
    private var isToProductDetail = false
    private var localNavigateApproveTag = false
    var hasMessageTag = ObservableField<Boolean>(false)
    var timeInfo = ObservableField<String>("15:53")
    var dateInfo = ObservableField<String>("2020年01月01日 周三")
    var myTaskUtil = MyTaskUtil(object: MyTaskUtil.MyMaskUtilInterface {
        override fun doWork() {
            timeInfo.set(DateUtil.getCurrent_Hm())
            dateInfo.set("${DateUtil.getCurrent_yMd()} ${DateUtil.getCurrentWeek()}" )
        }
    })
    //endregion

    //region all variable

    //region dataBind variable

    //endregion

    //region declare variable

    //endregion

    //endregion
}

class MainViewModelFactory(
    private val mView: MainActivity,
    private val datamanagerHelper: DatamanagerHelper
) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(datamanagerHelper, mView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

