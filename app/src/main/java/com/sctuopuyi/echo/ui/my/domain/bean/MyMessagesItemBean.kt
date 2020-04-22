package com.sctuopuyi.echo.ui.my.domain.bean

data class MyMessagesItemBean(
    val id: String
    , val title: String
    , val content: String
    , val date: String
    , var hasExpanded: Boolean
    , var hasReaded: Boolean
)