package com.captcha.captcha

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import android.widget.RelativeLayout

class CaptchaParentView : RelativeLayout {

    private lateinit var captchaView: Captcha

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context)
    }

    private fun init(context: Context) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                captchaView = getCaptchaView(context)
                addView(captchaView)
            }
        })
    }

    private fun getCaptchaView(context: Context): Captcha {
        return MathCaptchaBuilder(context).setParentViewWidth(measuredWidth).setParentViewHeight(measuredHeight).build()
    }

    /**
     * This function refreshes the captcha
     */
    fun refreshCaptcha() {
        captchaView.redrawCaptcha()
    }

    fun verifyAnswer(input: String): Boolean {
        return captchaView.verifyAnswer(input)
    }

    fun getTextToSpeechSentence(): String {
        return captchaView.getTextToSpeechSentence()
    }
}