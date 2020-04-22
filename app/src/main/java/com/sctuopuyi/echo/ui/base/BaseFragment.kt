package com.sctuopuyi.echo.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.sctuopuyi.echo.di.component.DaggerFragmentComponent
import com.sctuopuyi.echo.app.App
import com.sctuopuyi.echo.data.DatamanagerHelper
import com.sctuopuyi.echo.ui.base.domain.ObserableViewModel
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.components.support.RxFragment
import me.yokeyword.fragmentation.SupportActivity
import javax.inject.Inject

/**
 * Created by ChenGY on 2018-08-20.
 */
abstract class BaseFragment<VB : ViewDataBinding, VM : ObserableViewModel> : RxFragment(), BaseView {

    override fun showError(msg: String) = com.sctuopuyi.echo.utils.ToastUtil.shortShow(msg)

    override fun showMsg(msg: String) = com.sctuopuyi.echo.utils.ToastUtil.shortShow(msg)

    override fun showProgress() {
        try {
            val activity = mActivity as BaseView?
            activity?.showProgress()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showProgress(msg: String?) {
        try {
            val activity = mActivity as BaseView?
            activity?.showProgress(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismissProgress() {
        try {
            val activity = mActivity as BaseView?
            activity?.dismissProgress()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showProgress1() {
        try {
            val activity = mActivity as BaseView?
            activity?.showProgress1()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun showProgress1(msg: String?) {
        try {
            val activity = mActivity as BaseView?
            activity?.showProgress1(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dismissProgress1() {
        try {
            val activity = mActivity as BaseView?
            activity?.dismissProgress1()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initInject()
        val vmFactoryBean = getVmFactoryBean()
        vm = ViewModelProviders.of(this, vmFactoryBean.factory).get(vmFactoryBean.vm)
        if (mRootView == null) {
            mBinding = DataBindingUtil.inflate(inflater, vmFactoryBean.layoutId, null, false)
            mRootView = mBinding.root
        }
        mActivity = activity as SupportActivity
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!isHidden) {
            isInited = true
            mPermission = RxPermissions(this)
            initView()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isInited && !hidden) {
            isInited = true
            mPermission = RxPermissions(this)
            initView()
        }
    }

    protected fun getFragmentComponent(): com.sctuopuyi.echo.di.component.FragmentComponent {
        return DaggerFragmentComponent.builder()
                .appComponent(App.getAppComponents())
                .fragmentModule(com.sctuopuyi.echo.di.module.FragmentModule(this))
                .build()
    }

    abstract fun initInject()

    abstract fun initView()

    abstract fun getVmFactoryBean(): BaseVmFactoryBean<VM>

    //region declare variable

    @Inject
    lateinit var datamanagerHelper: DatamanagerHelper
    protected lateinit var mBinding: VB
    protected lateinit var vm: VM
    lateinit var mActivity: SupportActivity
    private var mRootView: View? = null
    private var isInited = false
    protected lateinit var mPermission: RxPermissions

    //endregion
}