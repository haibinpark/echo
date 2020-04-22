package com.sctuopuyi.echo.ui.my.domain.bean

data class MyBankCardsItemBean(
    val id: String
    , var hasExpanded: Boolean = false
    , val bindBandCardDesc: String = ""
    , var btnName: String = ""
    , val bankCardNum: String = ""
    , val bankName: String = ""
    , val bindCardNumDesc: String = ""
    , val bankCardBackGroundUrl: String = ""
    , val bindCardIconUrl: String = ""
)