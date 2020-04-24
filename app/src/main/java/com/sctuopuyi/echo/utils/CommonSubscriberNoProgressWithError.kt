package com.sctuopuyi.echo.utils

import com.sctuopuyi.echo.data.http.exception.ApiException
import com.sctuopuyi.echo.ui.base.BaseView
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException

/**
 * Created by ChenGY on 2018-09-04.
 */
open class CommonSubscriberNoProgressWithError<T>(private val mView: BaseView, private val doNext: (T) -> Unit, private val error: (Throwable) -> Unit) : CommonSubscriber<T>(mView, doNext) {

    override fun onNext(t: T) {
        doNext(t)
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable) {
        mView.dismissProgress()
        when (e) {
            is ApiException -> mView.showError(e.message!!)
            is HttpException -> mView.showError("数据加载失败ヽ(≧Д≦)ノ" + e.message)
            is SocketTimeoutException, is SocketException, is SSLException -> mView.showError("网络连接失败，请检查网络ヽ(≧Д≦)ノ")
            is AppException -> mView.showError(e.message!!)
            else -> mView.showError("未知错误ヽ(≧Д≦)ノ" + e.message)
        }
        error(e)
    }
}