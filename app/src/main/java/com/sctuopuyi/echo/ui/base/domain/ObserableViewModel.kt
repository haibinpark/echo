package com.sctuopuyi.echo.ui.base.domain


import android.view.View
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.ViewModel

open class ObserableViewModel : ViewModel(), Observable {

    private val callbacks: PropertyChangeRegistry by lazy {
        PropertyChangeRegistry()
    }


    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.remove(callback)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        callbacks.add(callback)
    }

    /**
     * Notifies listeners that all properties of this instance have changed.
     */
    @Suppress("unused")
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }

    /**
     * Notifies listeners that a specific property has changed. The getter for the property
     * that changes should be marked with [Bindable] to generate a field in
     * `BR` to be used as `fieldId`.
     *
     * @param fieldId The generated BR id for the Bindable field.
     */
    fun notifyPropertyChanged(fieldId: Int) {
        callbacks.notifyCallbacks(this, fieldId, null)
    }

    /**
     * 点击事件
     */
    open fun onSingleClick(v: View) {
    }


    fun setSelectStatus(status: Boolean) {
        selectStatus.set(status)
    }

    fun setTitleName(titleName1: String) {
        titleName.set(titleName1)
    }

    //标题名
    open val titleName = ObservableField<String>()

    //右标题
    open val secondTitleName = ObservableField<String>()

    //标题栏右边名
    open val titleRightName = ObservableField<String>()

    open val rightImageUrl = ObservableField<String>()
    open val rightImageStatus = ObservableField<Boolean>(false)

    //选中状态
    open val selectStatus = ObservableField<Boolean>(true)
    open val leftBtnStatus = ObservableField<Boolean>(true)
    open val rightBtnStatus = ObservableField<Boolean>(true)
}