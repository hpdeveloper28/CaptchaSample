package com.captcha.app

import android.content.Context
import android.graphics.Paint
import android.view.View
import java.util.Random

abstract class Captcha(context: Context) : View(context) {

    var finalX: Float = 0.0f
    var finalY: Float = 0.0f
    lateinit var paint: Paint
    var textToBeDrawn = ""
    val desireBufferX = 300
    val maxMovementY = 20
    val textSkewXBufferXY = 30
    val random: Random = Random()

    abstract fun prepareBackground()
    abstract fun prepareCaptcha(width: Int, height: Int, minNum: Int, maxNum: Int, captchaTextColor: Int)
}