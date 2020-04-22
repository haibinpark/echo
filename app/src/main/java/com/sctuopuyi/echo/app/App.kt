package com.sctuopuyi.echo.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.text.TextUtils
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lzy.imagepicker.ImagePicker
import com.lzy.imagepicker.loader.ImageLoader
import com.lzy.imagepicker.view.CropImageView
import com.sctuopuyi.echo.BuildConfig
import com.sctuopuyi.echo.di.component.DaggerAppComponent
import com.sctuopuyi.echo.utils.ToastUtil
import com.tencent.bugly.crashreport.CrashReport
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initVariable()
        initSDK()
        racb()
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        Multidex.install(this)
    }

    fun setIsAuthFirst(b: Boolean) {
        this.isAuthFirst = b
    }

    fun getIsAuthFirst(): Boolean {
        return this.isAuthFirst
    }

    //region activity collect
    var tts: TextToSpeech? = null
    private lateinit var allActivities: MutableSet<Activity>
    private var singleThreadExecutor = Executors.newSingleThreadExecutor()
    private var fixedThreadPool = Executors.newFixedThreadPool(4)
    private var isAuthFirst: Boolean = false
//    private var client: AMapLocationClient? = null

    private val FLYTEK_PACKAGE_NAME = "com.iflytek.speechcloud"

    fun addActivity(act: Activity) {
        allActivities.add(act)
    }

    fun removeActivity(act: Activity) {
        allActivities.remove(act)
    }

    fun finishAllAct() {
        for (act in allActivities) {
            act.finish()
        }
    }

    private fun racb() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityPaused(activity: Activity?) {}
            override fun onActivityResumed(activity: Activity?) {}
            override fun onActivityStarted(activity: Activity?) {}
            override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
            override fun onActivityStopped(activity: Activity?) {}
            override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                activity?.let { addActivity(activity) }
            }

            override fun onActivityDestroyed(activity: Activity?) {
                activity?.let { removeActivity(activity) }
            }
        })
    }

    private fun uploadCrashReport() {
//        val context = applicationContext
//        // 获取当前包名
//        val packageName = context.packageName
//        // 获取当前进程名
//        val processName = getProcessName(android.os.Process.myPid())
//        // 设置是否为上报进程
//        val strategy = CrashReport.UserStrategy(context)
//        strategy.isUploadProcess = processName == null || processName == packageName
        // 初始化Bugly
        CrashReport.initCrashReport(this, BuildConfig.BUGLY_APP_ID, true)
    }

    private fun getProcessName(pid: Int): String? {
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(FileReader("/proc/$pid/cmdline"));
            var processName = reader.readLine()
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim()
            }
            return processName;
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        } finally {
            try {
                if (reader != null) {
                    reader.close()
                }
            } catch (exception: IOException) {
                exception.printStackTrace()
            }
        }
        return null
    }


    //endregion

    //region init method

    private fun initVariable() {
        appInstance = this
        allActivities = HashSet()
        tts = TextToSpeech(this, TextToSpeech.OnInitListener {
            if (it == TextToSpeech.SUCCESS) {
                try {
                    val result = tts?.setLanguage(Locale.CHINA)
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        ToastUtil.shortShow("初始化引擎失败")
                    }else{
                        //不支持中文就将语言设置为英文
//                        tts?.language = Locale.US
//                        ToastUtil.shortShow("初始化引擎成功")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }else{
                ToastUtil.shortShow("初始化引擎失败")
            }
        },FLYTEK_PACKAGE_NAME)
    }

    private fun initSDK() {
        initImagePicker()
        uploadCrashReport()
    }

    private fun initBugly() {
        CrashReport.initCrashReport(applicationContext)
    }


    private fun initImagePicker() {
        val h =
            (com.sctuopuyi.echo.utils.ScreentUtil.getScreenPxHeight(appInstance) * com.sctuopuyi.echo.app.Constants.Camera.HeightPer).toInt()
        val w = (h * com.sctuopuyi.echo.app.Constants.Camera.WIDTH_DIVIDE_HEIGHT).toInt()
        imagePicker = ImagePicker.getInstance()
        imagePicker.initDefaultImagePicker(imagePicker, w, h, object : ImageLoader {
            override fun displayImagePreview(
                activity: Activity?,
                path: String?,
                imageView: ImageView?,
                width: Int,
                height: Int
            ) {
                Glide.with(activity!!).load(path).into(imageView!!)
            }

            override fun clearMemoryCache() {
            }

            override fun displayImage(
                activity: Activity?,
                path: String?,
                imageView: ImageView?,
                width: Int,
                height: Int
            ) {
                Glide.with(activity!!).load(path).into(imageView!!)
            }
        })
    }

    fun getFixedThreadPool(): ExecutorService {
        return this.fixedThreadPool
    }

    fun startLocation() {
        App.getInstance().getFixedThreadPool().run {
            //启动定位
//            client = App.getInstance().getClient()
//            client?.setLocationOption(LocationUtils.getLocationOption())
//            client?.setLocationListener { aMapLocation ->
//                Log.d("stacrtLocation", "stacrtLocation: " + aMapLocation.toString())
//                RxBus.publish(RxEvent.EventBean(RxEvent.EVENT_PAGE_TYPE_LOCATION,aMapLocation))
//                client?.stopLocation()
//            }
//            client?.startLocation()
        }
    }


    //endregion

    //region declare variable

    companion object {
        @Volatile
        private lateinit var appInstance: App
        @Volatile
        private var appComponent: com.sctuopuyi.echo.di.component.AppComponent? = null
        private lateinit var imagePicker: ImagePicker

        fun getInstance(): App {
            return appInstance
        }

        fun getAppComponents(): com.sctuopuyi.echo.di.component.AppComponent? {
            if (appComponent == null) {
                appComponent = DaggerAppComponent
                    .builder()
                    .appModule(com.sctuopuyi.echo.di.module.AppModule(appInstance))
                    .httpModule(com.sctuopuyi.echo.di.module.HttpModule(appInstance))
                    .build()
            }
            return appComponent
        }

        fun getImagePicker(): ImagePicker {
            return imagePicker
        }
    }

    //endregion

}


fun ImagePicker.initDefaultImagePicker(
    imagePicker: ImagePicker,
    w: Int,
    h: Int,
    imageLoader: ImageLoader
): ImagePicker {
    imagePicker.imageLoader = imageLoader
    imagePicker.isShowCamera = true //显示拍照按钮
    imagePicker.isCrop = true //允许裁剪（单选才有效）
    imagePicker.isSaveRectangle = true //是否按矩形区域保存
    imagePicker.selectLimit = 10 //选中数量限制
    imagePicker.style = CropImageView.Style.RECTANGLE //裁剪框的形状
    imagePicker.focusWidth = w //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
    imagePicker.focusHeight = h //裁剪框的高度。单位像素（圆形自动取宽高最小值）
    imagePicker.outPutX = w //保存文件的宽度。单位像素
    imagePicker.outPutY = h //保存文件的高度。单位像素
    return imagePicker
}

