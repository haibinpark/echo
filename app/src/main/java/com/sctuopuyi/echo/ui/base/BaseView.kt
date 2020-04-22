package com.sctuopuyi.echo.ui.base

interface BaseView {

    fun showError(msg: String)

    fun showMsg(msg: String)

    fun showProgress()

    fun showProgress(msg: String?)

    fun dismissProgress()

    fun showProgress1()

    fun showProgress1(msg: String?)

    fun dismissProgress1()
}