package com.sctuopuyi.echo.timer

import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import androidx.databinding.ObservableField

class MyTime(val millisInFuture: Long, val countDownInterval: Long, val btn: Button, val msgStatus: ObservableField<String>) : CountDownTimer(millisInFuture, countDownInterval) {

    override fun onFinish() {
        btn.isEnabled = true
        msgStatus.set("获取验证码")

    }

    override fun onTick(millisUntilFinished: Long) {
        Log.i("TTTTT=>>>>>", "计时器: ${millisUntilFinished / 1000}s")

        msgStatus.set("(${millisUntilFinished / 1000}s)")
    }


}