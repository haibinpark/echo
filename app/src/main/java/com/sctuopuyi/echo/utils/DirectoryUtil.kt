package com.sctuopuyi.echo.utils

import android.os.Environment
import java.io.File
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL


object DirectoryUtil {


    fun getGalleryPath() = buildString {
        append(Environment.getExternalStorageDirectory())
        append(File.separator)
        append(Environment.DIRECTORY_DCIM)
        append(File.separator)
        append("Camera")
        append(File.separator)
    }

    fun returnBitMap(url: String?): Bitmap? {
        val myFileUrl: URL
        var bitmap: Bitmap? = null
        try {
            myFileUrl = URL(url)
            val conn: HttpURLConnection = myFileUrl.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            val `is` = conn.inputStream
            bitmap = BitmapFactory.decodeStream(`is`)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bitmap
    }
}