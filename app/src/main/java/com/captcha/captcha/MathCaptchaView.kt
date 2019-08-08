package com.captcha.captcha

import android.content.Context
import android.graphics.*
import com.captcha.app.R
import java.util.concurrent.ThreadLocalRandom

class MathCaptchaView(context: Context) : Captcha(context) {

    constructor(context: Context, width: Int, height: Int, minNum: Int, maxNum: Int, textColor: Int) : this(
        context
    ) {
        parentWidth = width
        parentHeight = height
        minimumNumber = minNum
        maximumNumber = maxNum
        captchaTextColor = textColor
        refreshCaptcha()
    }

    override fun prepareBackground() {
        background = gradientBackgroundHelper.getGradientBackground()
    }

    override fun prepareCaptcha(width: Int, height: Int, minNum: Int, maxNum: Int, captchaTextColor: Int) {
        val minX = 0
        val minY = 0
        val defaultTextSize = 75F

        var one = ThreadLocalRandom.current().nextInt(minNum, maxNum + 1)
        var two = ThreadLocalRandom.current().nextInt(minNum, maxNum + 1)
        val math = ThreadLocalRandom.current().nextInt(1, 3)
        if (one < two) {
            val temp = one
            one = two
            two = temp
        }
        prepareAnswer(one, two, operator(math))
        prepareTextToSpeechSentence(one, two, operator(math))
        textToBeDrawn =
            one.toString().toCharArray()[0].toString() + operator(math) + two.toString().toCharArray()[0].toString() + equalToPair.first
        paint = Paint()
        paint.color = captchaTextColor
        paint.textSize = defaultTextSize

        val rect = Rect()
        paint.getTextBounds(textToBeDrawn, 0, textToBeDrawn.length, rect)
        val maxX = width - (rect.left + rect.right)
        val maxY = height + (rect.top + rect.bottom)

        val rangeX =
            Pair(
                -rect.left + minX.toFloat() + textSkewXBufferXY,
                -rect.left + maxX.toFloat() - desireBufferX - textSkewXBufferXY
            )
        val rangeY = Pair(
            -rect.top + minY.toFloat() + maxMovementY + textSkewXBufferXY,
            -rect.top + maxY.toFloat() - maxMovementY - textSkewXBufferXY
        )

        finalX = ThreadLocalRandom.current().nextInt(rangeX.first.toInt(), rangeX.second.toInt()).toFloat()
        finalY = ThreadLocalRandom.current().nextInt(rangeY.first.toInt(), rangeY.second.toInt()).toFloat()
    }

    override fun prepareAnswer(firstDigit: Int, secondDigit: Int, operator: String) {
        answer = when (operator) {
            plusPair.first -> firstDigit.plus(secondDigit)
            minusPair.first -> firstDigit.minus(secondDigit)
            else -> firstDigit.plus(secondDigit)
        }
    }

    override fun verifyAnswer(input: String): Boolean {
        return input.toInt() == answer
    }

    override fun prepareTextToSpeechSentence(firstDigit: Int, secondDigit: Int, operator: String) {
        ttsSentence = when (operator) {
            plusPair.first -> firstDigit.toString() + " " + plusPair.second + " " + secondDigit.toString() + " " + equalToPair.second
            minusPair.first -> firstDigit.toString() + " " + minusPair.second + " " + secondDigit.toString() + " " + equalToPair.second
            else -> firstDigit.toString() + " " + plusPair.second + " " + secondDigit.toString() + " " + equalToPair.second
        }
    }

    override fun getTextToSpeechSentence(): String = ttsSentence

    override fun refreshCaptcha() {
        prepareBackground()
        prepareCaptcha(parentWidth, parentHeight, minimumNumber, maximumNumber, captchaTextColor)
    }

    private fun operator(math: Int): String {
        when (math) {
            0 -> return plusPair.first
            1 -> return minusPair.first
        }
        return plusPair.first
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.rotate(
            ThreadLocalRandom.current().nextInt(-5, 5).toFloat(), finalX,
            finalY
        )
        val chars = textToBeDrawn.toCharArray()
        val tempX = desireBufferX / (textToBeDrawn.length - 1)
        for (i in chars.indices) {
            if (i != 0) {
                finalX += tempX
            }
            finalY = ThreadLocalRandom.current().nextInt(finalY.toInt() - maxMovementY, finalY.toInt() + maxMovementY)
                .toFloat()

            if (!(chars[i].toString().trim() == equalToPair.first || chars[i].toString().trim() == "+" || chars[i].toString().trim() == "-")) {
                paint.textSkewX = random.nextFloat() - random.nextFloat()
            }
            canvas.drawText(chars[i].toString(), finalX, finalY, paint)
        }
    }
}