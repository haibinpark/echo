package com.sctuopuyi.echo.ui.web

import com.google.gson.annotations.SerializedName

data class SimpleWebJsBean(
        @SerializedName("secretkey") val secretkey: String,
        @SerializedName("ostype") val ostype: String,
        @SerializedName("devicetoken") val devicetoken: String,
        @SerializedName("usertoken") val usertoken: String,
        @SerializedName("accountid") val accountid: String,
        @SerializedName("datatoken") val datatoken: String
)