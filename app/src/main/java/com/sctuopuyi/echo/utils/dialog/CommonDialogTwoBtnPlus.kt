package com.sctuopuyi.echo.utils.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.databinding.DialogCommonTwoBtnPlusBinding
import com.sctuopuyi.echo.utils.dialog.viewmodel.DialogViewModel


class CommonDialogTwoBtnPlus : Dialog {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, cancelable: Boolean, cancelListener: DialogInterface.OnCancelListener) : super(context, cancelable, cancelListener) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}


    /**
     * 每次使用必须new一个builder，因为初始化界面在builder构造函数里
     */
    class Builder(private val context: Context) {
        private var mDialog: CommonDialogTwoBtnPlus? = null
        private var mView: View? = null//dialog视图
        private var mTextView_title: TextView? = null//标题
        private var mTextView_content: TextView? = null//内容
        private var mBottom: TextView? = null//按钮
        private var mBottom2: TextView? = null//按钮
        private var canBack = true//是否可以返回键取消，默认可以
        private var canTouch = true//是否可以点击外部取消，默认可以
        private var mBinding: DialogCommonTwoBtnPlusBinding? = null

        val isShowing: Boolean
            get() = mDialog != null && mDialog!!.isShowing

        init {
            initView()
        }

        /**
         * 初始化视图
         */
        private fun initView() {
            mBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(context),
                    R.layout.dialog_common_two_btn_plus,
                    null,
                    false)
        }

        public fun setViewModel(vm: DialogViewModel): Builder {
            mBinding?.vm = vm
            mBinding?.rvNewestCarSource?.apply {
                adapter = vm.adapter
            }
            return this
        }

        /**
         * 设置不可以返回键+out取消
         */
        fun noCancleAll(): Builder {
            canBack = false
            canTouch = false
            return this
        }

        /**
         * 设置不可以点击外部取消，可以点击返回按钮取消
         */
        fun noCancleTouchButCanBackPress(): Builder {
            canBack = true
            canTouch = false
            return this
        }

        /**
         * 关闭dialog
         */
        fun closeDialog() {
            if (mDialog != null) {
                mDialog!!.dismiss()
            }
        }

        fun create(): CommonDialogTwoBtnPlus? {
            mDialog = CommonDialogTwoBtnPlus(context, R.style.ComWinTransBackHalfDia)
            mDialog!!.setContentView(mBinding?.root)
            //设置dialog的大小
//            val lp: android.view.WindowManager.LayoutParams = mDialog!!.window!!.attributes
//            lp.width = LinearLayout.LayoutParams.MATCH_PARENT //dp
//            lp.height = LinearLayout.LayoutParams.WRAP_CONTENT
//            lp.horizontalMargin = 15f
//            mDialog!!.window!!.attributes = lp
            mDialog!!.setCancelable(canBack)
            mDialog!!.setCanceledOnTouchOutside(canTouch)
            return mDialog
        }
    }
}
