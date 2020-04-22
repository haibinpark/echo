package com.sctuopuyi.echo.utils.dialog.viewmodel

import android.view.View
import androidx.databinding.ObservableArrayList
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.sctuopuyi.echo.utils.dialog.viewmodel.bean.DialogItemBean
import io.ditclear.bindingadapter.SingleTypeAdapter

class DialogViewModel(
        val id: String?,
        val datas: ObservableArrayList<DialogItemBean>,
        val content: String?,
        val title: String?,
        val adapter: SingleTypeAdapter<DialogItemBean>,
        val leftListener: View.OnClickListener,
        val rightListener: View.OnClickListener,
        val leftButtonName: String?,
        val rightButtonName: String?
) :
        ObserableViewModel() {

    override fun onSingleClick(v: View) {
        when (v.id) {
            R.id.tv_bottom_btn -> {
                //左边按钮
                leftListener.onClick(v)
            }
            R.id.tv_bottom_btn2 -> {
                //右边按钮
                rightListener.onClick(v)
            }
        }
    }
}