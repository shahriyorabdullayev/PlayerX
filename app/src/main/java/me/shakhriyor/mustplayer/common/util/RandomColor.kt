package me.shakhriyor.mustplayer.common.util

import me.shakhriyor.mustplayer.R
import java.util.*


object RandomColor {

    fun randomColor(): Int {
        val random = Random()
        val colorList = ArrayList<Int>()

        colorList.add(R.color.random1)
        colorList.add(R.color.random2)
        colorList.add(R.color.random3)
        colorList.add(R.color.random4)

        return colorList[random.nextInt(colorList.size)]
    }
}