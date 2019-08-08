package com.captcha.captcha

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import java.util.concurrent.ThreadLocalRandom

class GradientBackgroundHelper {

    private val blueStart = "#2356AE"
    private val blueMiddle = "#0D141C"
    private val blueEnd = "#517BEC"

    private val purpleStart = "#3023AE"
    private val purpleMiddle = "#282461"
    private val purpleEnd = "#8B51EC"

    private val greenStart = "#006D1F"
    private val greenMiddle = "#014012"
    private val greenEnd = "#021305"

    private val grayStart = "#B8B8B8"
    private val grayMiddle = "#625D5D"
    private val grayEnd = "#0A0000"

    private val brownStart = "#8F7D5E"
    private val brownMiddle = "#220808"
    private val brownEnd = "#45220E"

    private val firstColorSet =
        Triple(Color.parseColor(blueStart), Color.parseColor(blueMiddle), Color.parseColor(blueEnd))
    private val secondColorSet =
        Triple(Color.parseColor(purpleStart), Color.parseColor(purpleMiddle), Color.parseColor(purpleEnd))
    private val thirdColorSet =
        Triple(Color.parseColor(greenStart), Color.parseColor(greenMiddle), Color.parseColor(greenEnd))
    private val forthColorSet =
        Triple(Color.parseColor(grayStart), Color.parseColor(grayMiddle), Color.parseColor(grayEnd))
    private val fifthColorSet =
        Triple(Color.parseColor(brownStart), Color.parseColor(brownMiddle), Color.parseColor(brownEnd))

    private var previousGradientTypeElement: Int = 0
    private var previousBackgroundColorElement: Int = 0
    private var previousGradientOrientationElement: Int = 0


    fun getGradientBackground(): Drawable {
        when (getRandomElementWithoutRepeat(1, 3, RandomElementType.GRADIENT_TYPE)) {
            1 -> return getLinearGradientBackground()
            2 -> return getRadialGradientBackground()
        }
        return getLinearGradientBackground()
    }

    private fun getLinearGradientBackground(): Drawable {
        val colorSet = getColorSet()
        val colors = intArrayOf(colorSet.first, colorSet.second, colorSet.third)
        val gd = GradientDrawable(
            getLinearGradientOrientation(), colors
        )
        gd.gradientType = GradientDrawable.LINEAR_GRADIENT
        gd.cornerRadius = 0f
        return gd
    }

    private fun getRadialGradientBackground(): Drawable {
        val colorSet = getColorSet()
        val gd = GradientDrawable()
        gd.colors = intArrayOf(colorSet.first, colorSet.second, colorSet.third)
        gd.gradientType = GradientDrawable.RADIAL_GRADIENT
        gd.gradientRadius = 200.0f
        return gd
    }

    private fun getLinearGradientOrientation(): GradientDrawable.Orientation {
        when (getRandomElementWithoutRepeat(1, 3, RandomElementType.GRADIENT_ORIENTATION)) {
            1 -> return GradientDrawable.Orientation.TOP_BOTTOM
            2 -> return GradientDrawable.Orientation.LEFT_RIGHT
        }
        return GradientDrawable.Orientation.TOP_BOTTOM
    }

    /*private fun getGradientType(): Int {
        when (ThreadLocalRandom.current().nextInt(1, 3)) {
            1 -> return GradientDrawable.LINEAR_GRADIENT
            2 -> return GradientDrawable.RADIAL_GRADIENT
        }
        return GradientDrawable.LINEAR_GRADIENT
    }*/

    private fun getColorSet(): Triple<Int, Int, Int> {
        when (getRandomElementWithoutRepeat(1, 6, RandomElementType.BG_COLOR)) {
            1 -> return firstColorSet
            2 -> return secondColorSet
            3 -> return thirdColorSet
            4 -> return forthColorSet
            5 -> return fifthColorSet
        }
        return firstColorSet
    }


    private fun getRandomElementWithoutRepeat(origin: Int, bound: Int, previousElement: RandomElementType): Int {
        val value = ThreadLocalRandom.current().nextInt(origin, bound)
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