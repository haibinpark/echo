package com.sctuopuyi.echo.ui.web

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.KeyEvent
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.databinding.ActivityAuthenWebsBinding
import com.sctuopuyi.echo.ui.web.domain.AuthenWebsViewModel
import com.sctuopuyi.echo.ui.web.domain.AuthenWebsViewModelFactory
import com.sctuopuyi.echo.ui.base.BaseVmFactoryBean
import com.sctuopuyi.echo.ui.base.BaseActivity
import java.net.URISyntaxException

class AuthenWebsActivity : BaseActivity<ActivityAuthenWebsBinding, AuthenWebsViewModel>() {

    //region callback

    override fun initInject() = getActivityComponent().inject(this)

    override fun getVmFactoryBean() = BaseVmFactoryBean(
        R.layout.activity_authen_webs,
        AuthenWebsViewModel::class.java,
        AuthenWebsViewModelFactory(this, datamanagerHelper)
    )

    override fun initView() {
        mBinding.vm = vm
        authUrl = intent.getStringExtra(KEY_AUTH_URL)
        showResultType = intent.getStringExtra(KEY_SHOW_RESULT_TYPE)
        extraData = intent.getStringExtra(KEY_EXTRA_DATA)
        initWebView()
        initScoreWebView()
        vm.initData(extraData)
    }



    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            vm.showExitDialog()
            return false
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        mebview!!.webViewClient = null
        mebview!!.webChromeClient = null
        super.onDestroy()
    }

    private fun initWebView() {
        mebview = mBinding.wv
        mebview!!.requestFocus()
        val webSettings = mebview!!.settings
        webSettings.javaScriptEnabled = true// 启用js
        webSettings.defaultTextEncodingName = "utf-8"
        webSettings.saveFormData = false
        webSettings.setSupportZoom(true)
        webSettings.domStorageEnabled = true//最重要的方法，一定要设置，这就是出不来的主要原因
        mebview!!.isLongClickable = false// js中不能长按
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)//允许自动化测试
        }
    }

    private fun initScoreWebView() {
        //设置Schemes白名单
        mebview!!.loadUrl(authUrl) //加载授权地址
        if (showResultType != null && showResultType == "mobile") {
            mebview!!.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    //调用拨号、短信等程序
                    if (url.startsWith("mailto:") || url.startsWith("geo:") || url.startsWith("tel:") || url.startsWith(
                            "sms:"
                        )
                    ) {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        val componentName = intent.resolveActivity(packageManager)
                        if (componentName == null) {
                            com.sctuopuyi.echo.utils.ToastUtil.shortShow("未找到客户端")
                            return false
                        }
                        startActivity(intent)
                        return true
                    }
                    view.loadUrl(url)
                    return false
                }

                override fun onPageFinished(view: WebView, url: String) {
                    super.onPageFinished(view, url)
                    val js =
                        "javascript:function test() {window.onload=function(){var header = document.getElementsByTagName(\"h1\")[0];header.parentNode.parentNode.parentNode.removeChild(header.parentNode.parentNode); }}"
                    view.loadUrl(js)
                    view.loadUrl("javascript:test();")
                }
            }
        } else {
            val whiteList = arrayOf(
                "taobao://",
                "alipayqr://",
                "alipays://",
                "wechat://",
                "weixin://",
                "mqq://",
                "mqqwpa://",
                "openApp.jdMobile://"
            )
            mebview!!.webViewClient = object : WebViewClient() {

                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    for (s in whiteList) {
                        //判断url如果是在Schemes的白名单里，就启动对于的app，如果不是直接加载url
                        if (url.startsWith(s)) {
                            try {
                                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)
                                val componentName = intent.resolveActivity(packageManager)
                                if (componentName == null) {
                                    com.sctuopuyi.echo.utils.ToastUtil.shortShow("未找到客户端")
                                    return false
                                }
                                startActivity(intent)
                                return true
                            } catch (e: URISyntaxException) {
                                e.printStackTrace()
                            }

                        }
                    }
                    view.loadUrl(url)
                    return true
                }
            }


        }
        val chromeClient = object : WebChromeClient() {

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
            }
        }
        mebview!!.webChromeClient = chromeClient
    }

    override fun onResume() {
        super.onResume()
    }

    //endregion

    //region declare variable

    private var showResultType: String? = null
    var mebview: WebView? = null
    private var authUrl: String? = null
    private var extraData: String? = null

    companion object {
        private const val KEY_STR1 = "str1Key"

        fun start(context: Context, str1: String = "") {
            val starter = Intent(context, AuthenWebsActivity::class.java)
            starter.apply {
                putExtra(KEY_STR1, str1)
            }
            context.startActivity(starter)
        }


        fun start(context: Context, authUrl: String, extraData: String = "") {
            val intent = Intent(context, AuthenWebsActivity::class.java)
            intent.putExtra(AuthenWebsActivity.KEY_AUTH_URL, authUrl)
            intent.putExtra(AuthenWebsActivity.KEY_EXTRA_DATA, extraData)
            context.startActivity(intent)
        }

        private val KEY_AUTH_URL = "authUrlKey"
        private val KEY_EXTRA_DATA = "extraDataKey"
        private val KEY_SHOW_RESULT_TYPE = "showResultTypeKey"
    }

    //endregion
}
