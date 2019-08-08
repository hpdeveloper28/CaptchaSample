package com.captcha.app

import android.content.Context
import android.graphics.*
import java.util.concurrent.ThreadLocalRandom

class MathCaptchaView(context: Context) : Captcha(context) {

    constructor(context: Context, width: Int, height: Int, minNum: Int, maxNum: Int, captchaTextColor: Int) : this(
        context
    ) {
        prepareBackground()
        prepareCaptcha(width, height, minNum, maxNum, captchaTextColor)
    }

    override fun prepareBackground() {
        background = context.getDrawable(R.drawable.bg_gradient)
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
        textToBeDrawn =
            one.toString().toCharArray()[0].toString() + operator(math).toString() + two.toString().toCharArray()[0].toString() + "="
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

    private fun operator(math: Int): Char {
        when (math) {
            0 -> return '+'
            1 -> return '-'
        }
        return '+'
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

            if (!(chars[i].toString().trim() == "=" || chars[i].toString().trim() == "+" || chars[i].toString().trim() == "-")) {
                paint.textSkewX = random.nextFloat() - random.nextFloat()
            }
            canvas.drawText(chars[i].toString(), finalX, finalY, paint)
        }
    }
}