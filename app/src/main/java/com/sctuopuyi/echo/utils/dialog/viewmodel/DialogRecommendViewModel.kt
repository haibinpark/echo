package com.sctuopuyi.echo.utils.dialog.viewmodel

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.sctuopuyi.echo.utils.dialog.viewmodel.bean.DialogRecommendItemBean
import io.ditclear.bindingadapter.SingleTypeAdapter

class DialogRecommendViewModel(
        val id: String?,
        val datas: ObservableArrayList<DialogRecommendItemBean>,
        val adapter: SingleTypeAdapter<DialogRecommendItemBean>,
        val submit: View.OnClickListener
) :
        ObserableViewModel() {

    override fun onSingleClick(v: View) {
        when (v.id) {
            R.id.rl_btn_approve -> {
                //左边按钮
                submit.onClick(v)
            }
        }
    }

    val btnUrl = ObservableField<String>(R.drawable.icon_yjsq.toString())
    val hasBtnLocal = ObservableField<Boolean>(true)
}