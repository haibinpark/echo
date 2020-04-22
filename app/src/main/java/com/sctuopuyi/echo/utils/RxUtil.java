package com.sctuopuyi.echo.utils;


import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.sctuopuyi.echo.app.App;
import com.sctuopuyi.echo.data.http.exception.ApiException;
import com.sctuopuyi.echo.data.http.response.BaseHttpResponse;
import com.sctuopuyi.echo.data.local.SharedPreferenceHelper;
import com.sctuopuyi.echo.ui.main.MainActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 统一返回网络结果处理
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseHttpResponse<T>, T> handleMyResult() {
        if (true) {//lambda表达式的简写
            return upstream -> upstream.flatMap((Function<BaseHttpResponse<T>, ObservableSource<T>>)
                    tMyHttpResponse -> {
                        String code = String.valueOf(tMyHttpResponse.getCode());
                        if (code.equals("0")) {
                            return createData(tMyHttpResponse.getResult());
                        } else if (code.equals("CJ00006")) {
                            finishAllAndLogin();
                            return Observable.error(new ApiException("用户未登录!"));
                        } else {
                            switch (code) {
                                case "1":
                                    return reLogin(tMyHttpResponse);
                                case "CJ00002":
                                    return Observable.error(new ApiException("错误!接口配置不存在!"));
                                case "CJ00003":
                                    return Observable.error(new ApiException("请更新版本!"));
                                case "CJ00004":
                                    return Observable.error(new ApiException("你已被封号!"));
                                case "CJ00005":
                                    return Observable.error(new ApiException("接口开关已关闭!"));
                                case "CJ00007":
                                    return Observable.error(new ApiException("请求进行中!"));
                                case "CJ00008":
                                    return Observable.error(new ApiException("数字签名比对不一致!"));
                                case "CJ00009":
                                    return Observable.error(new ApiException("请求路径为空!"));
                                case "CJ00010":
                                    return Observable.error(new ApiException("请求路径参数不正确!"));
                                case "CJ00006":
                                    return reLogin(tMyHttpResponse);
                                case "CJ00013":
                                    return reLogin(tMyHttpResponse);
                                default:
                                    return Observable.error(new ApiException("错误码: "
                                            + tMyHttpResponse.getCode()
                                            + " 错误消息: "
                                            + tMyHttpResponse.getMsg()));
                            }
                        }
                    }
            );
        } else {
            return new ObservableTransformer<BaseHttpResponse<T>, T>() {
                @Override
                public ObservableSource<T> apply(Observable<BaseHttpResponse<T>> upstream) {
                    return upstream.flatMap(new Function<BaseHttpResponse<T>, ObservableSource<T>>() {
                        @Override
                        public ObservableSource<T> apply(BaseHttpResponse<T> tMyHttpResponse) throws Exception {
                            //...
                            return null;
                        }
                    });
                }
            };
        }
    }

    private static <T> ObservableSource<T> reLogin(BaseHttpResponse<T> tMyHttpResponse) {
        String msg = tMyHttpResponse.getMsg();
        if (!TextUtils.isEmpty(msg)) {
            if (msg.contains("用户登录信息不存在") || msg.contains("用户信息不存在")) {
                finishAllAndLogin();
            }
            return Observable.error(new ApiException(tMyHttpResponse.getMsg()));
        } else {
            return Observable.error(new ApiException("未知错误"));
        }
    }

    /**
     * 关闭所有Activity并回到登录页
     */
    private static void finishAllAndLogin() {
        App appContext = App.Companion.getInstance();
        SharedPreferenceHelper sharePreferenceHelper = new SharedPreferenceHelper(appContext);
        sharePreferenceHelper.clearUserInfo();
        sharePreferenceHelper.saveIsLogin(false);
        appContext.finishAllAct();
        Intent intent = new Intent(appContext, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        appContext.startActivity(intent);
    }

    /**
     * 生成Observable
     *
     * @param <T>
     * @return
     */
    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onComplete();
            } catch (Exception e) {
                subscriber.onError(e);
                Log.e("exctption", e.getMessage());
            }
        });
    }
}
