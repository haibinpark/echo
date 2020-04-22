package com.sctuopuyi.echo.widget

import com.sctuopuyi.echo.ui.base.BaseView
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.SocketException
import java.net.SocketTimeoutException
import javax.net.ssl.SSLException

/**
 * Created by ChenGY on 2018-09-04.
 */
class CommonSubscriber<T>(private val mView: BaseView, private val doNext: (T) -> Unit) : Observer<T> {

    override fun onNext(t: T) {
        doNext(t)
    }

    override fun onSubscribe(d: Disposable) {
        mView.showProgress()
    }

    override fun onComplete() {
        mView.dismissProgress()
    }

    override fun onError(e: Throwable) {
        mView.dismissProgress()
        when (e) {
            is com.sctuopuyi.echo.data.http.exception.ApiException -> mView.showError(e.message!!)
            is HttpException -> mView.showError("数据加载失败ヽ(≧Д≦)ノ" + e.message)
            is SocketTimeoutException, is SocketException, is SSLException -> mView.showError("网络连接失败，请检查网络ヽ(≧Д≦)ノ")
            is com.sctuopuyi.echo.app.AppException -> mView.showError(e.message!!)
            else -> mView.showError("未知错误ヽ(≧Д≦)ノ" + e.message)
        }
    }
}