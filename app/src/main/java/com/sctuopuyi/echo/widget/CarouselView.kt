package com.sctuopuyi.echo.widget

import android.content.Context
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ViewSwitcher
import com.sctuopuyi.echo.R
import java.lang.ref.WeakReference
import android.widget.TextView


class CarouselView : ViewSwitcher {

    private var mcontext: Context? = null
    private var currentItem: Int = 0
    private var loopTime: Int? = null
    private var datas: ArrayList<CarouselItemBean>? = null
    private var mHandler: MyHandler? = null

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        this.mcontext = context
        initAnimation()
        initData()
    }

    private class MyHandler : Handler {

        private var mRef: WeakReference<CarouselView>? = null

        constructor(view: CarouselView) {
            this.mRef = WeakReference(view)
        }


        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val vm = this.mRef?.get()
            vm?.showNextView()
            vm?.startLooping()
        }
    }

    private fun initAnimation() {
        setInAnimation(this.mcontext, com.sctuopuyi.echo.R.anim.translate_in)
        setOutAnimation(this.mcontext, com.sctuopuyi.echo.R.anim.translate_out)
    }


    fun addView(layoutId: Int): CarouselView {
        setFactory {
            LayoutInflater.from(context).inflate(layoutId, null)
        }
        showNext()
        return this
    }


    private fun initData() {
        this.datas = ArrayList()
        this.mHandler = MyHandler(this)
    }


    /**
     * 设置数据源并展示view，外部调用
     * @param mList
     * @param time
     */
    fun updateListAndView(mList: ArrayList<CarouselItemBean>?, time: Int) {
        currentItem = 0
        loopTime = time
        if (null == mList) {
            return
        }
        datas?.clear()
        datas?.addAll(mList)
        updataView(mList[0], currentView, currentItem ?: 0)
    }

    /**
     * 展示下一条广告
     */
    private fun showNextView() {
        if (null == datas || datas?.size ?: 0 < 2) {
            return
        }
        var itemIndex = 0
        if (currentItem == ((datas?.size ?: 0) - 1)) {
            currentItem = 0
            itemIndex = 0
        } else {
            currentItem += 1
            itemIndex = currentItem
        }
        updataView(datas?.get(itemIndex), nextView, currentItem ?: 0)
        showNext()
    }

    /**
     * 启动轮播
     */
    fun startLooping() {
        if (null == datas || datas?.size ?: 0 < 2) {
            return
        }
        mHandler?.removeMessages(0)
        mHandler?.sendEmptyMessageDelayed(0, loopTime?.toLong() ?: 0L)
    }

    /**
     * 停止轮播
     */
    fun stopLooping() {
        mHandler?.removeMessages(0)
    }

    /**
     * 在当前view上设置数据
     * @param text
     * @param view
     */
    private fun updataView(itemBean: CarouselItemBean?, view: View, mCurrentItem: Int) {
        val textView = view.findViewById<TextView>(R.id.tv_carouse_text1)
        val textView2 = view.findViewById<TextView>(R.id.tv_carouse_text2)
        val textView1 = view.findViewById<TextView>(R.id.tv_carouse_text3)
        textView.text = itemBean?.name
        textView1.text = itemBean?.loanAmount
        textView2.text = "在${resources.getString(R.string.app_name)}成功下款"

    }
}

data class CarouselItemBean(
    val id: String?,
    val name: String?,
    val loanAmount: String,
    val icon: String?
)
