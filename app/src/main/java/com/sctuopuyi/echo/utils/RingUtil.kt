package com.sctuopuyi.echo.util

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.media.AudioManager
import android.media.RingtoneManager
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import java.io.File


class RingUtil {


    companion object {
        fun instance(): RingUtil {
            return RingUtil()
        }
    }


    /**
     * 是否是默认铃声
     */
    fun isDefaultRing(
        context: Context,
        path: String? = context.filesDir.path,
        fileName: String
    ): Boolean {
        val defaultRingToneUrl =
            RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE)
        val defaultNotificationUrl =
            RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION)
        val defaultAlarmUrl =
            RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM)
        if (defaultRingToneUrl == null || defaultNotificationUrl == null || defaultAlarmUrl == null) return false
        val fullPath = "$path/${fileName}.mp3"
        val file = File(fullPath)
        if (!file.exists()) return false
        val defaultRingToneFullPath = getRealFilePath(context, defaultRingToneUrl)
        val defaultNotificationFullPath = getRealFilePath(context, defaultRingToneUrl)
        val defaultAlarmFullPath = getRealFilePath(context, defaultRingToneUrl)
        return ((defaultRingToneFullPath == fullPath)
                && (defaultNotificationFullPath == fullPath)
                && (defaultAlarmFullPath == fullPath))
    }


    /**
     * 根据Uri获取真实文件路径
     */
    private fun getRealFilePath(context: Context, uri: Uri): String? {
        val schema = uri.scheme
        var data: String? = null
        if (schema == null) {
            data = uri.path
        } else if (ContentResolver.SCHEME_FILE == schema) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == schema) {
            val cursor = context.contentResolver.query(
                uri,
                arrayOf(
                    MediaStore.MediaColumns.DATA
                    , MediaStore.MediaColumns.SIZE
                    , MediaStore.MediaColumns.DISPLAY_NAME
                    , MediaStore.MediaColumns.TITLE
                    , MediaStore.MediaColumns.DATE_ADDED
                    , MediaStore.MediaColumns.DATE_MODIFIED
                    , MediaStore.MediaColumns.MIME_TYPE
                ),
                null,
                null,
                null
            )
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val dataIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
                    val sizeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.SIZE)
                    val displayIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME)
                    val titleIndex = cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)
                    val dataAddedIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED)
                    val dataModifiedIndex =
                        cursor.getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED)
                    val mineTyopeIndex = cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE)
                    if (dataIndex > -1) {
                        data = cursor.getString(dataIndex)
                        val size = cursor.getString(sizeIndex)
                        val display = cursor.getString(displayIndex)
                        val title = cursor.getString(titleIndex)
                        val dataAdded = cursor.getString(dataAddedIndex)
                        val dataModified = cursor.getString(dataModifiedIndex)
                        val mineTyope = cursor.getString(mineTyopeIndex)
                    }
                }
                cursor.close()
            }
        }
        return data
    }


    /**
     * 设置铃声
     *
     * @param type RingtoneManager.TYPE_RINGTONE 来电铃声
     *             RingtoneManager.TYPE_NOTIFICATION 通知铃声
     *             RingtoneManager.TYPE_ALARM 闹钟铃声
     *
     * @param path 下载下来的mp3全路径
     * @param fileName 铃声的名字
     */
    fun setRing(context: Context, type: Int, path: String, fileName: String) {
        val oldRingToneUrl =
            RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE)
        val oldNotificationUrl =
            RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION)
        val oldAlarmUrl =
            RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_ALARM)

        val sdFile = File("$path/${fileName}.mp3")
        if (!sdFile.exists()) return
        val values = ContentValues()
        values.put(MediaStore.MediaColumns.DATA, sdFile.absolutePath)
        values.put(MediaStore.MediaColumns.TITLE, fileName)
        values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3")
        values.put(MediaStore.Audio.Media.IS_RINGTONE, true)
        values.put(MediaStore.Audio.Media.IS_NOTIFICATION, true)
        values.put(MediaStore.Audio.Media.IS_ALARM, true)
        values.put(MediaStore.Audio.Media.IS_MUSIC, true)
        val uri: Uri = MediaStore.Audio.Media.getContentUriForPath(sdFile.absolutePath)
        var newUri: Uri? = null
        var deleteId = ""
        try {
            val cursor: Cursor? = context.contentResolver.query(
                uri,
                null,
                MediaStore.MediaColumns.DATA + "=?",
                arrayOf(sdFile.absolutePath),
                null
            )
            if (cursor?.moveToFirst() == true) {
                deleteId = cursor.getString(cursor.getColumnIndex("_id"))
            }
            context.contentResolver.delete(
                uri,
                MediaStore.MediaColumns.DATA + "=\"" + sdFile.absolutePath + "\"", null
            )
            newUri = context.contentResolver.insert(uri, values)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (newUri != null) {
            var ringStoneId = ""
            var notificationId = ""
            var alarmId = ""
            if (null != oldRingToneUrl) {
                ringStoneId = oldRingToneUrl.lastPathSegment!!
            }

            if (null != oldNotificationUrl) {
                notificationId = oldNotificationUrl.lastPathSegment!!
            }

            if (null != oldAlarmUrl) {
                alarmId = oldAlarmUrl.lastPathSegment!!
            }

            val setRingStoneUri =
                if (type == RingtoneManager.TYPE_RINGTONE || ringStoneId == deleteId) {
                    newUri
                } else {
                    oldRingToneUrl
                }

            val setNotificationUri =
                if (type == RingtoneManager.TYPE_NOTIFICATION || notificationId == deleteId) {
                    newUri
                } else {
                    oldNotificationUrl
                }

            val setAlarmUri = if (type == RingtoneManager.TYPE_ALARM || alarmId == deleteId) {
                newUri
            } else {
                oldAlarmUrl
            }

            RingtoneManager.setActualDefaultRingtoneUri(
                context,
                RingtoneManager.TYPE_RINGTONE,
                setRingStoneUri
            )
            RingtoneManager.setActualDefaultRingtoneUri(
                context,
                RingtoneManager.TYPE_NOTIFICATION,
                setNotificationUri
            )
            RingtoneManager.setActualDefaultRingtoneUri(
                context,
                RingtoneManager.TYPE_ALARM,
                setAlarmUri
            )
//            when (type) {
//                RingtoneManager.TYPE_RINGTONE -> Toast.makeText(
//                    context.applicationContext,
//                    "设置来电铃声成功！",
//                    Toast.LENGTH_SHORT
//                ).show()
//                RingtoneManager.TYPE_NOTIFICATION -> Toast.makeText(
//                    context.applicationContext,
//                    "设置通知铃声成功！",
//                    Toast.LENGTH_SHORT
//                ).show()
//                RingtoneManager.TYPE_ALARM -> Toast.makeText(
//                    context.applicationContext,
//                    "设置闹钟铃声成功！",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
        }


    }

    /**
     * 获取系统响铃的状态
     * @return {@link #RINGER_MODE_NORMAL},
     *         {@link #RINGER_MODE_SILENT}, or {@link #RINGER_MODE_VIBRATE}.
     *
     */
    fun getRingMode(context: Context): Int {
        return try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.ringerMode
        } catch (e: Exception) {
            e.printStackTrace()
            -1
        }
    }


    /**
     * 设置铃声模式
     * @param {@link #RINGER_MODE_NORMAL},
     *         {@link #RINGER_MODE_SILENT}, or {@link #RINGER_MODE_VIBRATE}.
     */
    fun setRingMode(context: Context, ringMode: Int) {
        try {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.ringerMode = ringMode
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}