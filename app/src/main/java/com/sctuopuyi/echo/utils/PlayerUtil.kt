package com.sctuopuyi.echo.util

import android.annotation.SuppressLint
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Handler
import android.os.Message
import android.util.Log
import android.widget.SeekBar
import java.util.*

class PlayerUtil : MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener {

    private var mediaUrl: String? = null
    private var seekBar: SeekBar? = null
    private var mediaPlayer: MediaPlayer? = null
    private var timer = Timer()
    private var timeTask = object : TimerTask() {
        override fun run() {
            if (mediaPlayer == null) return
            if (seekBar == null) return
            if ((mediaPlayer?.isPlaying == true) && (seekBar?.isPressed == false)) {
                handler.sendEmptyMessage(0)
            }
        }
    }
    private var handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message?) {
            val position = mediaPlayer?.currentPosition ?: 0
            val duration = mediaPlayer?.duration ?: 0
            if (duration > 0) {
                val pos: Long = (((seekBar?.max ?: 0) * position) / duration).toLong()
                seekBar?.progress = pos.toInt()
            }
        }
    }
    private var mediaCallback: MediaCallback? = null

    constructor(callback: MediaCallback?) {
        try {
            this.mediaCallback = callback
            mediaPlayer = MediaPlayer()
            mediaPlayer?.setAudioStreamType(AudioManager.STREAM_MUSIC)
            mediaPlayer?.setOnBufferingUpdateListener(this)
            mediaPlayer?.setOnPreparedListener(this)
            timer.schedule(timeTask, 0, 1000)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBufferingUpdate(mp: MediaPlayer?, percent: Int) {
        seekBar?.secondaryProgress = percent
        val currentProgress = (seekBar?.progress ?: 0) * (mediaPlayer?.currentPosition ?: 0)
        Log.e("$currentProgress % play", "$percent buffer")
    }

    override fun onCompletion(mp: MediaPlayer?) {
        this.mediaCallback?.onCompletion()
    }

    override fun onPrepared(mp: MediaPlayer?) {
//        mp?.start()
    }


    /**
     * 设置播放地址
     */
    fun setPlayUrl(mediaUrl: String?) {
        mediaPlayer?.reset()
        mediaPlayer?.setDataSource(mediaUrl)
        mediaPlayer?.prepare()
    }

    /**
     * 设置拖动条
     */
    fun setSeekBar(seekBar: SeekBar?) {
        this.seekBar = seekBar
    }


    fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        mediaCallback?.onStop()
    }

    fun pause() {
        mediaPlayer?.pause()
        mediaCallback?.onPause()
    }

    fun play() {
        mediaPlayer?.start()
        mediaCallback?.onStart()
    }


    companion object {

        @JvmStatic
        fun instance(callback: MediaCallback?): PlayerUtil {
            return PlayerUtil(callback)
        }
    }


    interface MediaCallback {
        fun onStart()
        fun onStop()
        fun onPause()
        fun onCompletion()
    }

}