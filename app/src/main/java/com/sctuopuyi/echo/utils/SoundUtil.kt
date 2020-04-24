package com.sctuopuyi.echo.utils

import android.content.Context
import android.media.AudioManager

class SoundUtil {


    private var context: Context? = null
    private var audioManager: AudioManager? = null

    constructor(context: Context?) {
        this.context = context
        val obj = context?.getSystemService(Context.AUDIO_SERVICE)
        if (obj != null) {
            audioManager = obj as AudioManager
        }
    }


    /**
     * 获取媒体的最大音量
     */
    fun getStreamMaxVolume(): Int {
        return audioManager?.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ?: 0
    }


    /**
     * 获取系统最大音量
     */
    fun getSystemMaxVolume(): Int {
        return audioManager?.getStreamMaxVolume(AudioManager.STREAM_SYSTEM) ?: 0
    }


    /**
     * 设置系统音量
     */
    fun setSystemVolume(volume: Int) {
        audioManager?.setStreamVolume(AudioManager.STREAM_SYSTEM, volume, AudioManager.FLAG_SHOW_UI)
    }


    /**
     * 设置通话音量
     */
    fun setVoiceVolume(volume: Int) {
        audioManager?.setStreamVolume(AudioManager.STREAM_VOICE_CALL, volume, AudioManager.FLAG_SHOW_UI)
    }


    /**
     * 设置铃声音量
     */
    fun setRingVolume(volume: Int) {
        audioManager?.setStreamVolume(AudioManager.STREAM_RING, volume, AudioManager.FLAG_SHOW_UI)
    }


    /**
     * 设置音乐音量
     */
    fun setMusicVolume(volume: Int) {
        audioManager?.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)
    }


    /**
     * 设置警告音量
     */
    fun setAlarmVolume(volume: Int) {
        audioManager?.setStreamVolume(AudioManager.STREAM_ALARM, volume, AudioManager.FLAG_SHOW_UI)
    }


    /**
     * 设置警告音量
     */
    fun setNotificationVolume(volume: Int) {
        audioManager?.setStreamVolume(AudioManager.STREAM_NOTIFICATION, volume, AudioManager.FLAG_SHOW_UI)
    }


    /**
     * 获取当前系统音量
     */
    fun getCurrentSystemVolume(): Int {
        return audioManager?.getStreamVolume(AudioManager.STREAM_SYSTEM) ?: 0
    }


    companion

    object {
        fun instance(context: Context): SoundUtil {
            return SoundUtil(context)
        }
    }
}