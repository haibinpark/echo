package com.sctuopuyi.echo.data.http.request.filter

/**
 * Created by ChenGY on 2018-08-29.
 */
class CommonGetListReq<T>(
        var filter: T? = null,
        var page: Int = 1,
        var rows: Int = 20,
        var sort: String? = "",
        var order: String? = ""
) {

    companion object {
        val ORDER_DESC = "desc"
        val ORDER_ASC = "asc"

        fun <T> getRecordRequest(page: Int): CommonGetListReq<T> {
            return getRecordRequest(page, 20)
        }

        fun <T> getRecordRequest(page: Int, pageSize: Int): CommonGetListReq<T> {
            val request = CommonGetListReq<T>()
            request.page = page
            request.rows = pageSize
            return request
        }
    }

}
