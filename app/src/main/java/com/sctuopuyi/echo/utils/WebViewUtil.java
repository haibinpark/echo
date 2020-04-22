package com.sctuopuyi.echo.utils;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.mylhyl.crlayout.SwipeRefreshWebView;
import com.sctuopuyi.echo.R;

/**
 * Created by ChenGY on 2018-04-23.
 */

public class WebViewUtil {

    public static void initWebView(WebView webView) {
        initWebSetting(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return initScheme(view, url);
            }
        });
    }

    public static void initFreshWebView(SwipeRefreshWebView swipeRefreshWebView, WebView webView) {
        swipeRefreshWebView.autoRefresh(R.color.base_color_button);
        initWebSetting(webView);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (swipeRefreshWebView != null) {
                    swipeRefreshWebView.autoRefresh();
                }
                return initScheme(view, url);
            }
        });
    }

    private static void initWebSetting(WebView webView) {
        webView.requestFocus();
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);// 启用js
        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setSaveFormData(false);
        webSettings.setSupportZoom(false);
        webSettings.setTextZoom(100);
        webSettings.setDomStorageEnabled(true);//最重要的方法，一定要设置，这就是出不来的主要原因
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webView.setLongClickable(false);// js中不能长按
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);//允许自动化测试
        }
    }

    private static boolean initScheme(WebView view, String url) {
        if (url == null) return false;
        try {
            if (url.startsWith("http")) {
                //处理http和https开头的url
                view.loadUrl(url);
                return true;
            } else {
                //其他自定义的scheme
                if (url.startsWith("weixin") //微信
                        || url.startsWith("alipays") //支付宝
                        || url.startsWith("mailto") //邮件
                        || url.startsWith("tel")//电话
                        || url.startsWith("dianping")//大众点评
                        || url.startsWith("baidu")//百度
                        ) {
                    //我不跳
                    //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    //startActivity(intent);
                    return true;
                } else {
                    return true;
                }
            }
        } catch (Exception e) { //防止crash (如果手机上没有安装处理某个scheme开头的url的APP, 会导致crash)
            return true;//没有安装该app时，返回true，表示拦截自定义链接，但不跳转，避免弹出上面的错误页面
        }
    }
}
