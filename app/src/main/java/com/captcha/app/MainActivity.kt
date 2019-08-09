package com.captcha.app

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.captcha.captcha.Captcha
import com.captcha.captcha.MathCaptchaBuilder
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var width: Int = 0
    private var height: Int = 0
    private lateinit var captchaView: Captcha

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewTreeObserver = captchaParentView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                captchaParentView.viewTreeObserver.removeGlobalOnLayoutListener(this)
                width = captchaParentView.measuredWidth
                height = captchaParentView.measuredHeight
                captchaView = getCaptchaView()
                captchaParentView.addView(captchaView)

            }
        })

        btnGenerateCaptcha.setOnClickListener {
            captchaView.refreshCaptcha()
        }

        val handler = Handler()
        handler.postDelayed(object : Runnable {
            override fun run() {
                btnGenerateCaptcha.performClick()
                handler.postDelayed(this, 2000)
            }
        }, 1000)

    }

    private fun getCaptchaView(): Captcha {
        return MathCaptchaBuilder(this@MainActivity).setParentViewWidth(width).setParentViewHeight(height).build()
    }
}
