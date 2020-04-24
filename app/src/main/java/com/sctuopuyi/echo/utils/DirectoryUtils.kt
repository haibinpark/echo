package com.sctuopuyi.echo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Environment

@SuppressLint("StaticFieldLeak")
object DirectoryUtils {
    private val TAG = "DirectoryUtils"

    //:/system
    fun rootDir() = Environment.getRootDirectory().toString()

    //:/data 用户数据目录
    fun dataDir() = Environment.getDataDirectory().toString()

    //:/cache 下载缓存内容目录
    fun cacheDir() = Environment.getDownloadCacheDirectory().toString()

    //:/mnt/sdcard或者/storage/emulated/0或者/storage/sdcard0 主要的外部存储目录
    fun storageDir() = Environment.getExternalStorageDirectory().toString()

    //:/mnt/sdcard/Pictures或者/storage/emulated/0/Pictures或者/storage/sdcard0/Pictures
    fun publicDir() = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()

    //获取SD卡是否存在:mounted
    fun storageState() = Environment.getExternalStorageState().toLowerCase()

    //设备的外存是否是用内存模拟的，是则返回true。(API Level 11)
    fun isEmulated() = Environment.isExternalStorageEmulated()

    //设备的外存是否是可以拆卸的，比如SD卡，是则返回true。(API Level 9)
    fun isRemovable() = Environment.isExternalStorageRemovable()


    //获取当前程序路径 应用在内存上的目录 :/data/data/com.mufeng.toolproject/files
    fun filesDir(context: Context) = context.filesDir.toString()

    //应用的在内存上的缓存目录 :/data/data/com.mufeng.toolproject/cache
    fun appCacheDir(context: Context) = context.cacheDir.toString()

    //应用在外部存储上的目录 :/storage/emulated/0/Android/data/com.mufeng.toolproject/files/Movies
    fun externalFilesDir(context: Context) = context.getExternalFilesDir(Environment.DIRECTORY_MOVIES).toString()

    //应用的在外部存储上的缓存目录 :/storage/emulated/0/Android/data/com.mufeng.toolproject/cache
    fun externalCacheDir(context: Context) = context.externalCacheDir.toString()

    //获取该程序的安装包路径 :/data/app/com.mufeng.toolproject-3.apk
    fun packageResourcePath(context: Context) = context.packageResourcePath

    //获取程序默认数据库路径 :/data/data/com.mufeng.toolproject/databases/mufeng
    fun databasePat(context: Context) = context.getDatabasePath("mufeng").toString()

}