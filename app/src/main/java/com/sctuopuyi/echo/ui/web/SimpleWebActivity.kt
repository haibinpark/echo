package com.sctuopuyi.echo.ui.web

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.KeyEvent
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.bus.RxBus
import com.sctuopuyi.echo.bus.RxEvent
import com.sctuopuyi.echo.databinding.ActivitySimpleWebBinding
import com.sctuopuyi.echo.ui.base.BaseActivity
import com.sctuopuyi.echo.ui.base.BaseVmFactoryBean
import com.sctuopuyi.echo.ui.web.domain.AboutMeViewModel
import com.sctuopuyi.echo.ui.web.domain.AboutMeViewModelFactory
import io.reactivex.disposables.Disposable


class SimpleWebActivity : BaseActivity<ActivitySimpleWebBinding, AboutMeViewModel>() {

    //region callback

    override fun initInject() = getActivityComponent().inject(this)

    override fun getVmFactoryBean() = BaseVmFactoryBean(
            R.layout.activity_simple_web,
            AboutMeViewModel::class.java,
            AboutMeViewModelFactory(this, datamanagerHelper)
    )


    override fun initView() {
        mBinding.vm = vm
        val pageType = intent.getStringExtra(KEY_PAGE_TYPE)
        productUrl = intent.getStringExtra(KEY_URL)
        val t = intent.getStringExtra(KEY_TITLE)
        when (pageType) {
            TYPE_PAGE_H5_PRODUCT -> {
                val datas = t.split("||")
                if (datas.size == 2) {
                    productName = datas[0]
                    productId = datas[1]
                }
//                vm.showSuggestProducts()
                vm.titleName.set(productName)
                productUrl = productUrl?.trim()
                val intent = Intent()
                intent.action = "android.intent.action.VIEW"
                intent.data = Uri.parse(productUrl)
                startActivity(intent)
            }
            TYPE_PAGE_API_PRODUCT -> {
                val datas = t.split("||")
                if (datas.size == 2) {
                    productName = datas[0]
                    productId = datas[1]
                }
//                vm.showSuggestProducts()
                vm.titleName.set(productName)
            }
            else -> {
                vm.titleName.set(t)
            }
        }
        webView = mBinding.wv.scrollView
        initWebView()
        vm.initData(pageType, productId)
        initEvent()
    }

    private fun initEvent() {
        disposable = RxBus.listen(RxEvent.EventBean::class.java).subscribe {
        }
    }

    override fun onBackPressedSupport() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressedSupport()
        }
    }


    fun toLoanAmount(itemId: String?) {
//        ToastUtil.shortShow("贷款申请")
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (disposable?.isDisposed == false) disposable?.dispose()
    }

    //endregion

    //region other method

    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        com.sctuopuyi.echo.utils.WebViewUtil.initFreshWebView(mBinding.wv, webView)
        webView.addJavascriptInterface(SimpleWebInterface(), "simpleWebInterface")// js和安卓交互的接口，里面的方法能被js调用
        webView.loadUrl(productUrl)
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                view?.settings?.javaScriptEnabled = true
                super.onPageFinished(view, url)
            }


            override fun shouldOverrideKeyEvent(view: WebView?, event: KeyEvent?): Boolean {
                when(event?.action){
                    KeyEvent.ACTION_DOWN->{
                    }
                }
                return super.shouldOverrideKeyEvent(view, event)
            }
        }
    }

    inner class SimpleWebInterface {
        @JavascriptInterface
        fun doTitle(title: String) {
            runOnUiThread {
                try {
                    vm.titleName.set(title)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("exception", "doTitle")
                }
            }
        }

        @JavascriptInterface
        fun getHttpHeadInfo(): String {
            try {
//                val secretKey = datamanagerHelper.getSecretKey()
//                val deviceToken = datamanagerHelper.getDeviceToken()
//                val userToken = datamanagerHelper.getUserToken()
//                val accountId = datamanagerHelper.getAccountId()
//                val dataToken = datamanagerHelper.getDataTokenCgy()
//                val bean = SimpleWebJsBean(secretkey = secretKey,
//                        ostype = "Android",
//                        devicetoken = deviceToken,
//                        usertoken = userToken,
//                        accountid = accountId,
//                        datatoken = dataToken)
//                val result = GsonBuilder().disableHtmlEscaping().create().toJson(bean, SimpleWebJsBean::class.java)
//                Log.e("result", result)
                val result = ""
                return result
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("exception", "getUserInfo")
                return ""
            }
        }

        @JavascriptInterface
        fun approveProduct() {
        }

    }

    //endregion

    //region declare variable
    private lateinit var webView: WebView
    private var productName: String? = null
    private var productId: String? = null
    private var productUrl: String? = null
    private var disposable: Disposable? = null

    companion object {
        const val TYPE_PAGE_H5_PRODUCT = "0"
        const val TYPE_PAGE_API_PRODUCT = "1"
        private const val KEY_URL = "urlStrKey"
        private const val KEY_TITLE = "titleStrKey"
        private const val KEY_PAGE_TYPE = "pageTypeKey"

        fun start(context: Context, urlStr: String, title: String, pageType: String? = "") {
            //先清除缓存
            com.sctuopuyi.echo.utils.StorageAndCacheUtil.clearAllCache(context)
            val starter = Intent(context, SimpleWebActivity::class.java)
            starter.putExtra(KEY_URL, urlStr)
            starter.putExtra(KEY_TITLE, title)
            starter.putExtra(KEY_PAGE_TYPE, pageType)
            context.startActivity(starter)
        }
    }

    //endregion
}
