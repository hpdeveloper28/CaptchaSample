package com.captcha.captcha

import android.content.Context
import android.graphics.Paint
import android.view.View
import java.util.Random

abstract class Captcha(context: Context) : View(context) {

    protected val gradientBackgroundHelper = GradientBackgroundHelper()
    protected var parentWidth: Int = 0
    protected var parentHeight: Int = 0
    protected var minimumNumber: Int = 0
    protected var maximumNumber: Int = 0
    protected var captchaTextColor: Int = 0
    protected var finalX: Float = 0.0f
    var finalY: Float = 0.0f
    lateinit var paint: Paint
    var textToBeDrawn = ""
    val desireBufferX = 300
    val maxMovementY = 20
    val textSkewXBufferXY = 30
    protected var answer: Int = 0
    var ttsSentence: String = ""
    val random: Random = Random()
    val plusPair = Pair("+", "Plus")
    val minusPair = Pair("-", "Minus")
    val equalToPair = Pair("=", "Equal to")

    abstract fun prepareBackground()
    abstract fun prepareCaptcha(width: Int, height: Int, minNum: Int, maxNum: Int, captchaTextColor: Int)
    abstract fun prepareAnswer(firstDigit: Int, secondDigit: Int, operator: String)
    abstract fun verifyAnswer(input: String): Boolean
    abstract fun prepareTextToSpeechSentence(firstDigit: Int, secondDigit: Int, operator: String)
    abstract fun getTextToSpeechSentence(): String
    abstract fun refreshCaptcha()
}