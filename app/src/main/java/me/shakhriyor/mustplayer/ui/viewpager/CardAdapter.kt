package me.shakhriyor.mustplayer.ui.viewpager

import androidx.cardview.widget.CardView

interface CardAdapter {

    fun getBaseElevation(): Float

    fun getCount(): Int

    fun getCardViewAt(position: Int): CardView?


    companion object {
        const val MAX_ELEVATION_FACTOR = 6
    }
}