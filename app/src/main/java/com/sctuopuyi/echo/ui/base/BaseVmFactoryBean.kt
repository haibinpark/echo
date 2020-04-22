package com.sctuopuyi.echo.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BaseVmFactoryBean<T : ViewModel>(val layoutId: Int, val vm: Class<T>, val factory: ViewModelProvider.Factory)