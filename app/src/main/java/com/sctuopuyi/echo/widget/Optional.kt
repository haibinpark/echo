package com.sctuopuyi.echo.widget

import androidx.annotation.Nullable


class Optional<M>(// 接收到的返回结果
        @Nullable private val optional: M?) {

    // 判断返回结果是否为null
    fun isEmpty(): Boolean {
        return this.optional == null
    }

    // 获取不能为null的返回结果，如果为null，直接抛异常，经过二次封装之后，这个异常最终可以在走向RxJava的onError()
    fun get(): M {
        if (optional == null) {
            throw NoSuchElementException("No value present")
        }
        return optional
    }

    // 获取可以为null的返回结果
    fun getIncludeNull(): M? {
        return optional
    }
}