package com.sctuopuyi.echo.data.http;


import com.sctuopuyi.echo.data.http.response.BaseHttpResponse;

import io.reactivex.Observable;

import retrofit2.http.*;

public interface MainServiceApi {



    /**
     * 退出登录
     *
     * @return
     */
    @POST("loginMgr/appOut")
    Observable<BaseHttpResponse<Boolean>> logout();

}
