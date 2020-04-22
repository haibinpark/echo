package com.sctuopuyi.echo.ui.base

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.sctuopuyi.echo.BuildConfig
import com.sctuopuyi.echo.R
import com.sctuopuyi.echo.app.App
import com.sctuopuyi.echo.data.DatamanagerHelper
import com.sctuopuyi.echo.di.component.DaggerActivityComponent
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.sctuopuyi.echo.ui.startup.StartupActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.wang.avi.AVLoadingIndicatorView
import me.yokeyword.fragmentation.SupportActivity
import javax.inject.Inject

abstract class BaseActivity<VB : ViewDataBinding, VM : ObserableViewModel> : SupportActivity(),
    BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initInject()
        mActivity = this
        val vmFactoryBean = getVmFactoryBean()
        mBinding = DataBindingUtil.setContentView(mActivity, vmFactoryBean.layoutId)
        vm = ViewModelProviders.of(mActivity, vmFactoryBean.factory).get(vmFactoryBean.vm)
        when (this) {
//            is StartupActivity, is RegisterLoginActivity, is RegisterActivity -> {
//                //启动页，白状态栏、黑字
//                com.sctuopuyi.echo.utils.StatusBarUtil.setStatusTransAndBlackFont(mActivity)
//            }
//            else -> {
//                when (BuildConfig.FLAVOR) {
//                    "hbdBeta", "hbdPd", "hqdBeta", "hqdPd", "hydBeta", "hydPd" -> {
//                        //启动页，白状态栏、黑字
//                        com.sctuopuyi.echo.utils.StatusBarUtil.setStatusTransAndBlackFont(mActivity)
//                    }
//                    else -> {
//                        com.sctuopuyi.echo.utils.StatusBarUtil.setStatusTrans(this)
//                    }
//                }
//            }
        }
        if (this is StartupActivity) {
//            StatusBarUtil.setStatusTrans(this)
        } else {

        }
        mPermission = com.sctuopuyi.echo.utils.PermissionUtils.getPersionInstance(this)
//        StatusBarUtil.setStatusTrans(this)
        initView()
//        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }


    override fun showError(msg: String) = com.sctuopuyi.echo.utils.ToastUtil.shortShow(msg)

    override fun showMsg(msg: String) = com.sctuopuyi.echo.utils.ToastUtil.shortShow(msg)

    override fun showProgress() = showProgress(null)

    override fun showProgress(msg: String?) {
        runOnUiThread {
            if (progressDialog == null) {
                val view =
                    layoutInflater.inflate(R.layout.layout_progress, LinearLayout(mActivity), false)
                indicatorView = view.findViewById(R.id.avi)
                progressMessage = view.findViewById(R.id.progress_message)
                progressDialog = Dialog(mActivity, R.style.LoadingDialog)
                progressDialog!!.setContentView(view)
                progressDialog!!.setCanceledOnTouchOutside(false)
                progressDialog!!.setCancelable(true)
            }
            if (TextUtils.isEmpty(msg)) {
                progressMessage!!.visibility = View.GONE
            } else {
                progressMessage!!.visibility = View.VISIBLE
                progressMessage!!.text = msg
            }
            if (!progressDialog!!.isShowing) {
                indicatorView!!.smoothToShow()
                progressDialog!!.show()
            }
        }
    }

    override fun dismissProgress() {
        runOnUiThread {
            if (progressDialog != null && progressDialog!!.isShowing) {
                indicatorView!!.smoothToHide()
                progressDialog!!.dismiss()
            }
        }
    }

    override fun showProgress1() = showProgress1(null)

    override fun showProgress1(msg: String?) {
        runOnUiThread {
            if (progressDialog1 == null) {
                val view =
                    layoutInflater.inflate(R.layout.layout_progress, LinearLayout(mActivity), false)
                indicatorView1 = view.findViewById(R.id.avi)
                progressMessage1 = view.findViewById(R.id.progress_message)
                progressDialog1 = Dialog(mActivity, R.style.LoadingDialog)
                progressDialog1!!.setContentView(view)
                progressDialog1!!.setCanceledOnTouchOutside(false)
                progressDialog1!!.setCancelable(true)
            }
            if (TextUtils.isEmpty(msg)) {
                progressMessage1!!.visibility = View.GONE
            } else {
                progressMessage1!!.visibility = View.VISIBLE
                progressMessage1!!.text = msg
            }
            if (!progressDialog1!!.isShowing) {
                indicatorView1!!.smoothToShow()
                progressDialog1!!.show()
            }
        }
    }

    override fun dismissProgress1() {
        runOnUiThread {
            if (progressDialog1 != null && progressDialog1!!.isShowing) {
                indicatorView1!!.smoothToHide()
                progressDialog1!!.dismiss()
            }
        }
    }

    protected fun getActivityComponent(): com.sctuopuyi.echo.di.component.ActivityComponent {
        return DaggerActivityComponent.builder()
            .appComponent(App.getAppComponents())
            .activityModule(com.sctuopuyi.echo.di.module.ActivityModule(this))
            .build()
    }

    abstract fun initView()

    abstract fun initInject()

    abstract fun getVmFactoryBean(): BaseVmFactoryBean<VM>

    //region declare variable

    @Inject
    lateinit var datamanagerHelper: DatamanagerHelper
    protected lateinit var mBinding: VB
    protected lateinit var vm: VM
    lateinit var mActivity: SupportActivity
    private var progressDialog: Dialog? = null
    private var progressDialog1: Dialog? = null
    private var indicatorView: AVLoadingIndicatorView? = null
    private var indicatorView1: AVLoadingIndicatorView? = null
    private var progressMessage: TextView? = null
    private var progressMessage1: TextView? = null
    protected lateinit var mPermission: RxPermissions
    protected var pressTime: Long = 0


    //endregion

}