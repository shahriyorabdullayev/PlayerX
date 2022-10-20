package me.shakhriyor.mustplayer.ui

import android.media.MediaPlayer

object MyMediaPlayer {
    private var instance: MediaPlayer? = null

    fun getInstance(): MediaPlayer? {
        if (instance == null) {
            instance = MediaPlayer()
        }
        return instance
    }

    var currentIndex = -1
}