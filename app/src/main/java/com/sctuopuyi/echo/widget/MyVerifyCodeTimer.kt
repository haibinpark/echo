package com.sctuopuyi.echo.widget

import android.os.CountDownTimer
import android.widget.TextView

/**
 * Created by ChenGY on 2018-08-28.
 */
class MyVerifyCodeTimer : CountDownTimer {

    private val tv: TextView

    constructor(tv: TextView) : this(tv, 60 * 1000L, 1000L)

    constructor(tv: TextView, millisInFuture: Long, countDownInterval: Long) : super(millisInFuture, countDownInterval) {
        this.tv = tv
    }

    override fun onFinish() {
        tv.text = "获取验证码"
        tv.isEnabled = true
    }

    override fun onTick(millisUntilFinished: Long) {
        tv.text = "${millisUntilFinished / 1000}s重新获取"
    }
}