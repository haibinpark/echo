package com.sctuopuyi.echo.aop

import android.util.Log
import android.view.View
import com.sctuopuyi.echo.BuildConfig
import com.sctuopuyi.echo.R
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import java.util.*

@Aspect
class SingleClickAspect {

    @Pointcut(POINT_CUT_REG) //方法切入点
    fun methodAnnotated() {
    }

    @Around("methodAnnotated()")//在连接点进行方法替换
    @Throws(Throwable::class)
    fun aroundJoinPoint(joinPoint: ProceedingJoinPoint) {
        var view: View? = null
        for (arg in joinPoint.args) {
            if (arg is View) view = arg
        }
        view?.let {
            //获取当前view的tag中保存的上一次点击时间戳
            val tag = view.getTag(com.sctuopuyi.echo.aop.SingleClickAspect.Companion.TIME_TAG)
            val lastClickTime = if (tag != null) tag as Long else 0
            if (BuildConfig.DEBUG) Log.d(com.sctuopuyi.echo.aop.SingleClickAspect.Companion.TAG, "lastClickTime:$lastClickTime")
            //获取当前点击时间戳
            val currentTime = Calendar.getInstance().timeInMillis
            //比较两次点击的间隔时间，大于600毫秒才处理
            if (currentTime - lastClickTime > com.sctuopuyi.echo.aop.SingleClickAspect.Companion.MIN_CLICK_DELAY_TIME) {
                //把当前时间保存进view的tag中
                view.setTag(com.sctuopuyi.echo.aop.SingleClickAspect.Companion.TIME_TAG, currentTime)
                if (BuildConfig.DEBUG) Log.d(com.sctuopuyi.echo.aop.SingleClickAspect.Companion.TAG, "currentTime:$currentTime")
                //执行原方法
                joinPoint.proceed()
            }
        }
    }

    companion object {
        const val TAG = "singleClick"
        const val MIN_CLICK_DELAY_TIME = 600
        const val TIME_TAG = R.id.click_time
        const val packageName = BuildConfig.APPLICATION_ID
        const val POINT_CUT_ITEMCLICK =
            "execution(* ${com.sctuopuyi.echo.aop.SingleClickAspect.Companion.packageName}..onItemClick(android.view.View, ..))"
        const val POINT_CUT_SINGLECLICK =
            "execution(* ${com.sctuopuyi.echo.aop.SingleClickAspect.Companion.packageName}..onSingleClick(android.view.View))"
        const val POINT_CUT_REG = "${com.sctuopuyi.echo.aop.SingleClickAspect.Companion.POINT_CUT_ITEMCLICK} || ${com.sctuopuyi.echo.aop.SingleClickAspect.Companion.POINT_CUT_SINGLECLICK}"
        val POINT_CUT_REG2 = "execution(@${com.sctuopuyi.echo.aop.SingleClickAspect.Companion.packageName}.aop.SingleClick * *(..))"
    }
}

@Retention(AnnotationRetention.BINARY)
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
annotation class SingleClick