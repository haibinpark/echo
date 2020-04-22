package com.sctuopuyi.echo.di.module;


import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import com.sctuopuyi.echo.BuildConfig;
import com.sctuopuyi.echo.app.App;
import com.sctuopuyi.echo.app.Constants;
import com.sctuopuyi.echo.data.http.MainServiceApi;
import com.sctuopuyi.echo.data.local.SharedPreferenceHelper;
import com.sctuopuyi.echo.di.qualifier.MessageUrl;
import com.sctuopuyi.echo.di.qualifier.ServiceUrl;
import com.sctuopuyi.echo.utils.security.SecurityUtil;
import com.sctuopuyi.echo.utils.DateUtil;
import com.sctuopuyi.echo.utils.LogUtil;
import com.sctuopuyi.echo.utils.OkHttpDns;
import com.sctuopuyi.echo.utils.StrNumUtil;
import com.sctuopuyi.echo.utils.SystemUtil;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created on 25/07/2017 16:41.
 */
@Module
public class HttpModule {

    public HttpModule(App app) {
        this.sharedPreferenceHelper = new SharedPreferenceHelper(app);
    }

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    @ServiceUrl
    Retrofit provideWkllmeRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, BuildConfig.API_URL_MAIN);
    }

    @Singleton
    @Provides
    @MessageUrl
    Retrofit provideWkllmeMessageRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, BuildConfig.API_URL_MESSAGE);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        //设置缓存
        Interceptor cacheInterceptor = chain -> {
            Request request = chain.request();
            if (!SystemUtil.isNetworkConnected()) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (SystemUtil.isNetworkConnected()) {
                int maxAge = 0;
                // 有网络时, 不缓存, 最大保存时长为0
                response.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .removeHeader("Pragma")
                        .build();
            } else {
                //没有网络也不走缓存
                int maxStale = 0;
                response.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .removeHeader("Pragma")
                        .build();
            }
            return response;
        };
        OkHttpDns dns = OkHttpDns.getInstance(App.Companion.getInstance().getBaseContext());
        builder.dns(dns);
        builder.addNetworkInterceptor(cacheInterceptor);
        builder.addInterceptor(cacheInterceptor);
        File cacheFile = new File(Constants.PATH_CACHE);
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        builder.cache(cache);
        //设置统一的请求头部参数
        Interceptor apikey = chain -> {
            Request request = chain.request();
            String sign = "";
            if (request.url().toString().contains("file/upload")) {
                sign = getUploadSign();
            } else {
                sign = getSign(request);
            }
            String deviceToken = sharedPreferenceHelper.getDeviceToken();
            String userToken = sharedPreferenceHelper.getUserToken();
            String accountId = sharedPreferenceHelper.getAccountId();
            String dataToken = sharedPreferenceHelper.getDataTokenCgy();

            request = request.newBuilder()
                    .addHeader("sign", sign)
                    .addHeader("ostype", "Android")
                    .addHeader("devicetoken", deviceToken)
                    .addHeader("usertoken", userToken)
                    .addHeader("accountid", accountId)
                    .addHeader("datatoken", StrNumUtil.getEmptyStr(dataToken))
                    .addHeader("timestamp", DateUtil.getCurrentStampStr())
                    .addHeader("packageCode", BuildConfig.PACKAGE_TAG)
                    .build();
            return chain.proceed(request);
        };
        builder.addInterceptor(apikey);
        //设置打印头信息
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            builder.addInterceptor(loggingInterceptor);
        }
        //设置打印json信息
        if (BuildConfig.DEBUG && Constants.isNeedHttpJson) {
            Interceptor loggerInterceptor = chain -> {
                //请求参数
                Request request = chain.request();
                try {
                    RequestBody requestBody = request.body();
                    Buffer buffer = new Buffer();
                    requestBody.writeTo(buffer);
                    Charset charset = UTF8;
                    MediaType contentType = requestBody.contentType();
                    if (contentType != null) {
                        charset = contentType.charset(UTF8);
                    }
                    String str = buffer.clone().readString(charset);
                    if (BuildConfig.DEBUG && Constants.isNeedHttpJson) {
                        String requstUrl = request.url().toString();
                        LogUtil.i("request url: " + requstUrl + " request body: " + str);
                        LogUtil.prettyJson(str);
                    }
                } catch (Exception ignored) {
                }

                //返回参数
                Response response = chain.proceed(request);
                try {
                    ResponseBody responseBody = response.body();
                    BufferedSource source = responseBody.source();
                    if (source != null) {
                        source.request(Long.MAX_VALUE);
                        Buffer buffer = source.buffer();
                        Charset charset = UTF8;
                        MediaType contentType = responseBody.contentType();
                        if (contentType != null) {
                            charset = contentType.charset(UTF8);
                        }
                        String str = buffer.clone().readString(charset);
                        if (BuildConfig.DEBUG && Constants.isNeedHttpJson) {
                            String requestUrl = response.request().url().toString();
                            LogUtil.i("request url: " + requestUrl + " response body: " + str);
                            LogUtil.prettyJson(str);
                        }
                    }
                    //重新生成DataToken
                    sharedPreferenceHelper.newDataTokenCgy();
                } catch (Exception ignored) {
                }
                return response;
            };
            builder.addInterceptor(loggerInterceptor);
        }
        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //设置错误重连
        builder.retryOnConnectionFailure(true);
        //创建builder
        return builder.build();
    }

    /**
     * 生成Sign
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String getSign(Request request) {
        String sign = "";
        try {
            RequestBody requestBody = request.body();
            if (requestBody != null) {
                Buffer buffer = new Buffer();
                requestBody.writeTo(buffer);
                Charset charset = UTF8;
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    charset = contentType.charset(UTF8);
                }
                String params = buffer.readString(charset);
                String secretKey = sharedPreferenceHelper.getSecretKey();
                String deviceToken = sharedPreferenceHelper.getDeviceToken();
                sign = SecurityUtil.signData(secretKey + params + deviceToken);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sign;
    }

    private String getUploadSign() {
        String sign = "";
        String secretKey = sharedPreferenceHelper.getSecretKey();
        String deviceToken = sharedPreferenceHelper.getDeviceToken();
        sign = SecurityUtil.signData(secretKey + "" + deviceToken);
        return sign;
    }

    @Singleton
    @Provides
    MainServiceApi provideWkllmeService(@ServiceUrl Retrofit retrofit) {
        return retrofit.create(MainServiceApi.class);
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private SharedPreferenceHelper sharedPreferenceHelper;

}
