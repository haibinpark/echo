package com.sctuopuyi.echo.data.http

import com.sctuopuyi.echo.data.local.SharedPreferenceHelper
import com.sctuopuyi.echo.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class TokenInterceptor : Interceptor {

    constructor(sharedPreferenceHelper: SharedPreferenceHelper, okHttpClient: OkHttpClient) {
        this.sharedPreferenceHelper = sharedPreferenceHelper
        mainServiceApi = Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL_MAIN)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(com.sctuopuyi.echo.data.http.MainServiceApi::class.java)
    }


    override fun intercept(chain: Interceptor.Chain?): Response? {
        val request = chain?.request()
        val response = chain?.proceed(request)
        //根据和服务端的约定判断token过期
        if (isTokenExpired(response)) {
            //同步请求方式，获取最新的Token
//            val newToken = StrNumUtil.getEmptyStr(getNewToken())
//            this.sharedPreferenceHelper?.saveTokenToLocal(newToken)
            //使用新的Token，创建新的请求
//            val newRequest = chain?.request()
//                    ?.newBuilder()
//                    ?.header("RefToken", "Refresh $newToken")
//                    ?.build()
            //重新请求
//            return chain?.proceed(newRequest)

        }
        return response
    }


    /**
     * 根据Response，判断Token是否失效
     *
     * @param response
     * @return
     */
    private fun isTokenExpired(response: Response?): Boolean {
        return response?.code() == 401
    }


    /**
     * 同步请求方式，获取最新的Token
     *
     * @return
     */
//    @Throws(IOException::class)
//    private fun getNewToken(): String? {
//        // 通过获取token的接口，同步请求接口
////        val response = mainServiceApi?.refreshToken()?.execute()
//        val response = mainServiceApi?.refreshToken()?.execute()
//        return if (response?.code() == 200) {
//            val entity = response.body()
//            if (entity?.code == "1") {
//                Throwable("刷新token失败: " + entity.msg)
//                return ""
//            } else {
//                response.body()?.result
//            }
//        } else {
//            ""
//        }
//    }


    private var sharedPreferenceHelper: SharedPreferenceHelper? = null
    private var mainServiceApi: com.sctuopuyi.echo.data.http.MainServiceApi? = null

}