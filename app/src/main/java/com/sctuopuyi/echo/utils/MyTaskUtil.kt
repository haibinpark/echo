package com.sctuopuyi.echo.utils

import com.sctuopuyi.echo.app.Constants
import java.util.*


class MyTaskUtil {
    private var callback:MyMaskUtilInterface? = null

    constructor(callback: MyMaskUtilInterface?) {
        this.callback = callback
    }


    /**
     * 停止轮询
     */
    fun stopTimer() {
        if (mTimer != null) {
            mTimer?.cancel()
            mTimer = null
        }
        if (myTask != null) {
            myTask = null
        }
    }

    /**
     * 开始轮询
     */
    fun startTimer() {
        if (mTimer == null) {
            mTimer = Timer()
        }
        if (myTask == null) {
            myTask = MyTask()
        }
        mTimer?.schedule(myTask, Constants.LOOP_DELAY, Constants.LOOP_GET_DATA)
    }

    /**
     * 轮询逻辑
     */
    private inner class MyTask : TimerTask() {
        override fun run() {
            callback?.doWork()
        }

    }


    private var mTimer: Timer? = null
    private var myTask: MyTask? = null


    interface MyMaskUtilInterface {
        fun doWork()
    }
}