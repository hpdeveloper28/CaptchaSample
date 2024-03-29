package com.captcha.captcha

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import com.captcha.app.Utility

class MathCaptchaView(context: Context) : Captcha(context) {

    constructor(context: Context, width: Int, height: Int, minNum: Int, maxNum: Int) : this(
        context
    ) {
        parentWidth = width
        parentHeight = height
        minimumNumber = minNum
        maximumNumber = maxNum
        redrawCaptcha()
    }

    /**
     * This function draws the gradient background
     * @param captchaBitmap Captcha bitmap
     * @param drawable Gradient background
     */
    override fun drawCaptchaOnPreparedBackground(captchaBitmap: Bitmap, drawable: Drawable) {
        background = LayerDrawable(
            arrayOf(
                drawable,
                BitmapDrawable(resources, captchaBitmap)
            )
        )
    }

    /**
     * This function prepares the mathematical captcha based on the screen dimensions
     * @param width Parent view's width
     * @param height Parent view's height
     * @param minNum Min number is from range of 0 to 9
     * @param maxNum Parent view's height
     * @param captchaTextColor Parent view's height
     * @return Bitmap Captcha's bitmap
     */
    override fun prepareCaptchaBitmap(width: Int, height: Int, captchaTextColor: Int): Bitmap {
        val minX = 0
        val minY = 0
        val defaultTextSize = 75F

        var one = Utility.getRandomNumber(minimumNumber, maximumNumber)
        var two = Utility.getRandomNumber(minimumNumber, maximumNumber)
        val math = Utility.getRandomNumber(0, 1)
        if (one < two) {
            val temp = one
            one = two
            two = temp
        }
        prepareAnswer(one, two, operator(math))
        prepareTextToSpeechSentence(one, two, operator(math))
        textToBeDrawn =
            one.toString().toCharArray()[0].toString() + operator(math) + two.toString().toCharArray()[0].toString() + equalToPair.first
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

        finalX = Utility.getRandomNumber(rangeX.first.toInt(), rangeX.second.toInt()).toFloat()
        finalY = Utility.getRandomNumber(rangeY.first.toInt(), rangeY.second.toInt()).toFloat()

        return getCaptchaBitmap()
    }

    /**
     * This function prepares the answer of the captcha
     * @param firstDigit First number
     * @param secondDigit Second number
     * @param operator Whether + or -
     */
    override fun prepareAnswer(firstDigit: Int, secondDigit: Int, operator: String) {
        answer = when (operator) {
            plusPair.first -> firstDigit.plus(secondDigit)
            minusPair.first -> firstDigit.minus(secondDigit)
            else -> firstDigit.plus(secondDigit)
        }
    }

    /**
     * This function verifies the answer is correct or not
     * @param input Whatever user has given the answer
     * @return result true or false
     */
    override fun verifyAnswer(input: String): Boolean {
        return input.toInt() == answer
    }

    /**
     * This function prepares the sentence for text to speech
     * @param firstDigit First number
     * @param secondDigit Second number
     * @param operator Whether + or -
     * Note: For text to speech sdk requires plus or minus instead of + or -
     */
    override fun prepareTextToSpeechSentence(firstDigit: Int, secondDigit: Int, operator: String) {
        ttsSentence = when (operator) {
            plusPair.first -> firstDigit.toString() + " " + plusPair.second + " " + secondDigit.toString() + " " + equalToPair.second
            minusPair.first -> firstDigit.toString() + " " + minusPair.second + " " + secondDigit.toString() + " " + equalToPair.second
            else -> firstDigit.toString() + " " + plusPair.second + " " + secondDigit.toString() + " " + equalToPair.second
        }
    }

    /**
     * This function provides the sentence of captcha for Text to speech
     */
    override fun getTextToSpeechSentence(): String = ttsSentence

    /**
     * This function redraws the captcha
     */
    override fun redrawCaptcha() {
        val colorPair = gradientBackgroundHelper.getGradientBackground(parentHeight)
        val captchaBitmap = prepareCaptchaBitmap(parentWidth, parentHeight, colorPair.second)
        drawCaptchaOnPreparedBackground(captchaBitmap, colorPair.first)
    }

    private fun operator(math: Int): String {
        when (math) {
            0 -> return plusPair.first
            1 -> return minusPair.first
        }
        return plusPair.first
    }

    private fun getCaptchaBitmap(): Bitmap {
        val bitmap = Bitmap.createBitmap(parentWidth, parentHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.rotate(
            Utility.getRandomNumber(rotateLeftLimit, rotateRightLimit).toFloat(), finalX,
            finalY
        )
        val chars = textToBeDrawn.toCharArray()
        val tempX = desireBufferX / (textToBeDrawn.length - 1)
        for (i in chars.indices) {
            if (i != 0) {
                finalX += tempX
            }
            finalY = Utility.getRandomNumber(finalY.toInt() - maxMovementY, finalY.toInt() + maxMovementY)
                .toFloat()

            if (!(chars[i].toString().trim() == equalToPair.first || chars[i].toString().trim() == plusPair.first || chars[i].toString().trim() == minusPair.first)) {
                paint.textSkewX = random.nextFloat() - random.nextFloat()
            }
            canvas.drawText(chars[i].toString(), finalX, finalY, paint)
        }
        return bitmap
    }
}