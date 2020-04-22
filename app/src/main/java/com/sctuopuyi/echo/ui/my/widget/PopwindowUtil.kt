package com.sctuopuyi.echo.ui.my.widget

import android.app.Activity
import android.graphics.Point
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sctuopuyi.echo.R
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.*
import android.widget.PopupWindow
import com.sctuopuyi.echo.ui.my.adapter.PopWindowAdapter
import android.widget.LinearLayout
import android.widget.TextView
import com.sctuopuyi.echo.ui.my.adapter.PopWindowMultiAdapter


object PopwindowUtil {

    fun popwindowInstance(bean: PopWindowBean, context: Activity) {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.popup_picker, null)
        val rcDataView: RecyclerView = view.findViewById(R.id.rc_data)
        val title: TextView = view.findViewById(R.id.tv_title)
        val btPick: TextView = view.findViewById(R.id.bt_pick)
        val mAdapter = PopWindowAdapter(
            bean.datas as ArrayList<PopWindowItemBean>,
            context
        )
        if (bean.title.isNullOrEmpty()) {

        } else {
            title.text = bean.title
        }
        if (bean.btnName.isNullOrEmpty()) {

        } else {
            title.text = bean.btnName
        }
        btPick.visibility = View.GONE
        val display = context.windowManager.defaultDisplay//得到当前屏幕的显示器对象
        val size = Point()//创建一个Point点对象用来接收屏幕尺寸信息
        display.getSize(size)//Point点对象接收当前设备屏幕尺寸信息
        val width = size.x//从Point点对象中获取屏幕的宽度(单位像素)
        val height = size.y//从Point点对象中获取屏幕的高度(单位像素)
        Log.v("zxy", "width=$width,height=$height")//width=480,height=854可知手机的像素是480x854的
        //创建一个PopupWindow对象，第二个参数是设置宽度的，用刚刚获取到的屏幕宽度乘以2/3，取该屏幕的2/3宽度，从而在任何设备中都可以适配，高度则包裹内容即可，最后一个参数是设置得到焦点
        val popWindow = PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popWindow.setBackgroundDrawable(BitmapDrawable())//设置PopupWindow的背景为一个空的Drawable对象，如果不设置这个，那么PopupWindow弹出后就无法退出了
        popWindow.isOutsideTouchable = true//设置是否点击PopupWindow外退出PopupWindow
        val params = context.window.attributes//创建当前界面的一个参数对象
        params.alpha =
            0.8f//设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面
        context.window.attributes = params
        popWindow.setOnDismissListener {
            val params = context.window.attributes
            params.alpha = 1.0f//设置为不透明，即恢复原来的界面
            context.window.attributes = params
        }

        view.findViewById<LinearLayout>(R.id.ll_close_rect).setOnClickListener {
            bean.callback.closeDialog(popWindow)
        }
        mAdapter.setCallback(object : PopWindowAdapter.PopWindowAdapterInterface {
            override fun onItemChildClick(
                adapter: PopWindowAdapter,
                view: View,
                position: String,
                v: PopWindowItemBean
            ) {
                bean.callback.onItemClick(v)
                popWindow.dismiss()
            }
        })
        rcDataView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
//        popWindow.showAtLocation(
//            layoutInflater.inflate(R.layout.activity_questing, null),
//            Gravity.BOTTOM,
//            0,
//            0
//        )

    }

    fun popwindowMultiInstance(bean: PopWindowBean, context: Activity) {
        val layoutInflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.popup_picker, null)
        val rcDataView: RecyclerView = view.findViewById(R.id.rc_data)
        val title: TextView = view.findViewById(R.id.tv_title)
        val btPick: TextView = view.findViewById(R.id.bt_pick)
        val beans = ArrayList<PopWindowItemBean>()
        beans.addAll(bean.datas)
        val mAdapter = PopWindowMultiAdapter(
            beans,
            context
        )
        if (bean.title.isNullOrEmpty()) {

        } else {
            title.text = bean.title
        }
        if (bean.btnName.isNullOrEmpty()) {

        } else {
            title.text = bean.btnName
        }
        btPick.visibility = View.VISIBLE
        val display = context.windowManager.defaultDisplay//得到当前屏幕的显示器对象
        val size = Point()//创建一个Point点对象用来接收屏幕尺寸信息
        display.getSize(size)//Point点对象接收当前设备屏幕尺寸信息
        val width = size.x//从Point点对象中获取屏幕的宽度(单位像素)
        val height = size.y//从Point点对象中获取屏幕的高度(单位像素)
        Log.v("zxy", "width=$width,height=$height")//width=480,height=854可知手机的像素是480x854的
        //创建一个PopupWindow对象，第二个参数是设置宽度的，用刚刚获取到的屏幕宽度乘以2/3，取该屏幕的2/3宽度，从而在任何设备中都可以适配，高度则包裹内容即可，最后一个参数是设置得到焦点
        val popWindow = PopupWindow(view, width, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        popWindow.setBackgroundDrawable(BitmapDrawable())//设置PopupWindow的背景为一个空的Drawable对象，如果不设置这个，那么PopupWindow弹出后就无法退出了
        popWindow.isOutsideTouchable = true//设置是否点击PopupWindow外退出PopupWindow
        val params = context.window.attributes//创建当前界面的一个参数对象
        params.alpha =
            0.8f//设置参数的透明度为0.8，透明度取值为0~1，1为完全不透明，0为完全透明，因为android中默认的屏幕颜色都是纯黑色的，所以如果设置为1，那么背景将都是黑色，设置为0，背景显示我们的当前界面
        context.window.attributes = params
        popWindow.setOnDismissListener {
            val params = context.window.attributes
            params.alpha = 1.0f//设置为不透明，即恢复原来的界面
            context.window.attributes = params
        }

        view.findViewById<LinearLayout>(R.id.ll_close_rect).setOnClickListener {
            bean.callback.closeDialog(popWindow)
        }
        mAdapter.setCallback(object : PopWindowMultiAdapter.PopWindowAdapterInterface {
            override fun onItemChildClick(
                adapter: PopWindowMultiAdapter,
                view: View,
                position: String,
                v: PopWindowItemBean
            ) {
                val it = beans.iterator()
                while (it.hasNext()) {
                    val bean = it.next()
                    if (bean.id == v.id) {
                        bean.isSelected = !v.isSelected
                    }
                }
                adapter.notifyDataSetChanged()
            }
        })
        rcDataView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
//        popWindow.showAtLocation(
//            layoutInflater.inflate(R.layout.activity_questing, null),
//            Gravity.BOTTOM,
//            0,
//            0
//        )
        btPick.setOnClickListener {
            bean.callback.onItemClick(beans.asSequence().filter {
                it.isSelected
            }.toList())
            popWindow.dismiss()
        }
    }
}

data class PopWindowBean(
    val title: String
    , val datas: List<PopWindowItemBean>
    , val callback: PopWindowInterface
    , val btnName: String? = ""
)

data class PopWindowItemBean(
    val id: String?
    , val productImageUrl: String?
    , val productName: String?
    , var isSelected: Boolean = false
    , var amountDesc: String? = ""
    , var isShowImage: Boolean? = true
)

interface PopWindowInterface {
    fun onItemClick(bean: PopWindowItemBean)
    fun onItemClick(beans: List<PopWindowItemBean>)
    fun closeDialog(popWindow: PopupWindow)
}