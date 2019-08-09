package com.captcha.captcha

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import com.captcha.app.Utility

class GradientBackgroundHelper {

    data class quard<T1, T2, T3, T4>(val first: T1, val second: T2, val third: T3, val fourth: T4)

    private val blueStart = "#2356AE"
    private val blueMiddle = "#0D141C"
    private val blueEnd = "#517BEC"
    private val blueTextColor = "#5B1046"

    private val purpleStart = "#3023AE"
    private val purpleMiddle = "#282461"
    private val purpleEnd = "#8B51EC"
    private val purpleTextColor = "#686868"

    private val greenStart = "#006D1F"
    private val greenMiddle = "#014012"
    private val greenEnd = "#021305"
    private val greenTextColor = "#4E7456"

    private val grayStart = "#B8B8B8"
    private val grayMiddle = "#625D5D"
    private val grayEnd = "#0A0000"
    private val grayTextColor = "#C7C7C7"

    private val brownStart = "#8F7D5E"
    private val brownMiddle = "#220808"
    private val brownEnd = "#45220E"
    private val brownTextColor = "#494A18"

    private val firstColorSet =
        quard(
            Color.parseColor(blueStart),
            Color.parseColor(blueMiddle),
            Color.parseColor(blueEnd),
            Color.parseColor(blueTextColor)
        )
    private val secondColorSet =
        quard(
            Color.parseColor(purpleStart),
            Color.parseColor(purpleMiddle),
            Color.parseColor(purpleEnd),
            Color.parseColor(purpleTextColor)
        )
    private val thirdColorSet =
        quard(
            Color.parseColor(greenStart),
            Color.parseColor(greenMiddle),
            Color.parseColor(greenEnd),
            Color.parseColor(greenTextColor)
        )
    private val forthColorSet =
        quard(
            Color.parseColor(grayStart),
            Color.parseColor(grayMiddle),
            Color.parseColor(grayEnd),
            Color.parseColor(grayTextColor)
        )
    private val fifthColorSet =
        quard(
            Color.parseColor(brownStart),
            Color.parseColor(brownMiddle),
            Color.parseColor(brownEnd),
            Color.parseColor(brownTextColor)
        )

    private var previousGradientTypeElement: Int = 0
    private var previousBackgroundColorElement: Int = 0
    private var previousGradientOrientationElement: Int = 0


    fun getGradientBackground(viewHeight: Int): Pair<Drawable, Int> {
        when (getRandomElementWithoutRepeat(1, 2, RandomElementType.GRADIENT_TYPE)) {
            1 -> return getLinearGradientBackground()
            2 -> return getRadialGradientBackground(viewHeight)
        }
        return getLinearGradientBackground()
    }

    private fun getLinearGradientBackground(): Pair<Drawable, Int> {
        val colorSet = getColorSet()
        val colors = intArrayOf(colorSet.first, colorSet.second, colorSet.third)
        val gd = GradientDrawable(
            getLinearGradientOrientation(), colors
        )
        gd.gradientType = GradientDrawable.LINEAR_GRADIENT
        gd.cornerRadius = 0f
        return Pair(gd, colorSet.fourth)
    }

    private fun getRadialGradientBackground(viewHeight: Int): Pair<Drawable, Int> {
        val colorSet = getColorSet()
        val gd = GradientDrawable()
        gd.colors = intArrayOf(colorSet.first, colorSet.second, colorSet.third)
        gd.gradientType = GradientDrawable.RADIAL_GRADIENT
        gd.gradientRadius = viewHeight.toFloat()
        return Pair(gd, colorSet.fourth)
    }

    private fun getLinearGradientOrientation(): GradientDrawable.Orientation {
        when (getRandomElementWithoutRepeat(1, 2, RandomElementType.GRADIENT_ORIENTATION)) {
            1 -> return GradientDrawable.Orientation.TOP_BOTTOM
            2 -> return GradientDrawable.Orientation.LEFT_RIGHT
        }
        return GradientDrawable.Orientation.TOP_BOTTOM
    }

    private fun getColorSet(): quard<Int, Int, Int, Int> {
        when (getRandomElementWithoutRepeat(1, 5, RandomElementType.BG_COLOR)) {
            1 -> return firstColorSet
            2 -> return secondColorSet
            3 -> return thirdColorSet
            4 -> return forthColorSet
            5 -> return fifthColorSet
        }
        return firstColorSet
    }

    private fun getRandomElementWithoutRepeat(origin: Int, bound: Int, previousElement: RandomElementType): Int {
        val value = Utility.getRandomNumber(origin, bound)
        when (previousElement) {
            RandomElementType.GRADIENT_TYPE -> {
                return if (previousGradientTypeElement == value) {
                    getRandomElementWithoutRepeat(origin, bound, previousElement)
                } else {
                    previousGradientTypeElement = value
                    value
                }
            }
            RandomElementType.BG_COLOR -> {
                return if (previousBackgroundColorElement == value) {
                    getRandomElementWithoutRepeat(origin, bound, previousElement)
                } else {
                    previousBackgroundColorElement = value
                    value
                }
            }
            RandomElementType.GRADIENT_ORIENTATION -> {
                return if (previousGradientOrientationElement == value) {
                    getRandomElementWithoutRepeat(origin, bound, previousElement)
                } else {
                    previousGradientOrientationElement = value
                    value
                }
            }
        }
    }

    enum class RandomElementType {
        GRADIENT_TYPE,
        BG_COLOR,
        GRADIENT_ORIENTATION
    }
}