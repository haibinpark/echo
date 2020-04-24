package com.sctuopuyi.echo.utils

import java.io.Closeable
import java.io.IOException

object CloseIOUtils {
    /**
     * 关闭IO
     * @param closeables closeables
     */
    fun closeIO(vararg closeables: Closeable) {
        if (closeables == null) return
        closeables
            .forEach {
                try {
                    it.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
    }

}