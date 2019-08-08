package com.captcha.app

import android.content.Context
import android.graphics.Color

class MathCaptchaBuilder(private val context: Context) {

    private var parentViewWidth: Int = 0
    private var parentViewHeight: Int = 0
    private var minNumber: Int = 1
    private var maxNumber: Int = 9
    private var captchaTextColor: Int = Color.WHITE

    fun setParentViewWidth(width: Int): MathCaptchaBuilder {
        parentViewWidth = width
        return this
    }

    fun setParentViewHeight(height: Int): MathCaptchaBuilder {
        parentViewHeight = height
        return this
    }

    fun setMinNumber(number: Int): MathCaptchaBuilder {
        minNumber = number
        return this
    }

    fun setMaxNumber(number: Int): MathCaptchaBuilder {
        maxNumber = number
        return this
    }

    fun setCaptchaTextColor(color: Int): MathCaptchaBuilder {
        captchaTextColor = color
        return this
    }

    fun build(): Captcha {
        return MathCaptchaView(context, parentViewWidth, parentViewHeight, minNumber, maxNumber, captchaTextColor)
    }
}