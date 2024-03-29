package com.captcha.captcha

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.view.View
import java.util.Random

abstract class Captcha(context: Context) : View(context) {

    protected val gradientBackgroundHelper = GradientBackgroundHelper()
    protected var parentWidth: Int = 0
    protected var parentHeight: Int = 0
    protected var minimumNumber: Int = 0
    protected var maximumNumber: Int = 0
    protected val rotateLeftLimit = -5
    protected val rotateRightLimit = 5
    protected var finalX: Float = 0.0f
    protected var finalY: Float = 0.0f
    protected val paint: Paint = Paint()
    protected var textToBeDrawn = ""
    protected val desireBufferX = 300
    protected val maxMovementY = 20
    protected val textSkewXBufferXY = 30
    protected var answer: Int = 0
    protected var ttsSentence: String = ""
    protected val random: Random = Random()
    protected val plusPair = Pair("+", "Plus")
    protected val minusPair = Pair("-", "Minus")
    protected val equalToPair = Pair("=", "Equal to")

    abstract fun drawCaptchaOnPreparedBackground(captchaBitmap: Bitmap, drawable: Drawable)
    abstract fun prepareCaptchaBitmap(width: Int, height: Int, captchaTextColor: Int): Bitmap
    abstract fun prepareAnswer(firstDigit: Int, secondDigit: Int, operator: String)
    abstract fun verifyAnswer(input: String): Boolean
    abstract fun prepareTextToSpeechSentence(firstDigit: Int, secondDigit: Int, operator: String)
    abstract fun getTextToSpeechSentence(): String
    abstract fun redrawCaptcha()
}