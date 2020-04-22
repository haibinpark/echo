package com.sctuopuyi.echo.data.http.response.list

/**
 * Created by ChenGY on 2018-08-29.
 */
class CommonGetListResp<T>(
        val total: Int,
        val rows: List<T>?
)