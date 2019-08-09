package com.captcha.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.speech.tts.TextToSpeech
import android.widget.Toast
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech.language = Locale.UK
            }
        })
        btnRefreshCaptcha.setOnClickListener {
            captchaParentViewCustom.refreshCaptcha()
        }

        btnSpeakCaptcha.setOnClickListener {
            textToSpeech.speak(captchaParentViewCustom.getTextToSpeechSentence(), TextToSpeech.QUEUE_FLUSH, null, null)
        }

        btnVerify.setOnClickListener {
            val captchaResult = captchaParentViewCustom.verifyAnswer(edtText.text.toString().trim())
            Toast.makeText(this@MainActivity, captchaResult.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    public override fun onPause() {
        textToSpeech.stop()
        textToSpeech.shutdown()
        super.onPause()
    }

}
