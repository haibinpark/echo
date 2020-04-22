package com.sctuopuyi.echo.data.http


import io.reactivex.Observable

class HttpHelper(private var serviceApi: com.sctuopuyi.echo.data.http.MainServiceApi) {


    fun logout(): Observable<com.sctuopuyi.echo.data.http.response.BaseHttpResponse<Boolean>> {
        return serviceApi.logout()
    }

    //region mine

    //endregion

}