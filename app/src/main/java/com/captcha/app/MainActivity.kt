package com.captcha.app

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var width: Int = 0
    private var height: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewTreeObserver = captchaParentView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                captchaParentView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                width = captchaParentView.measuredWidth
                height = captchaParentView.measuredHeight
                captchaParentView.addView(getCaptchaView())

            }
        })

        btnGenerateCaptcha.setOnClickListener {
            captchaParentView.removeAllViews()
            captchaParentView.addView(getCaptchaView())
        }

    }

    private fun getCaptchaView(): Captcha {
        return MathCaptchaBuilder(this@MainActivity).setParentViewWidth(width).setParentViewHeight(height)
            .setMinNumber(1).setMaxNumber(9).setCaptchaTextColor(Color.RED).build()
    }
}
