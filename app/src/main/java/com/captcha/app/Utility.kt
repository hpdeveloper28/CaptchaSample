package com.captcha.app

import java.util.*

object Utility {

    private val random = Random()

    fun getRandomNumber(minNumber: Int, maxNumber: Int): Int {
        return random.nextInt(maxNumber - minNumber + 1) + minNumber
    }
}