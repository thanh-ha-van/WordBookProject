package thanh.ha.ui.adapters

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import thanh.ha.R
import java.util.*


/**
 * @author Thanh
 * @date 3/1/2020
 */

fun getRandomBackgroundColor(context: Context): GradientDrawable {

    val randomStr = getRandomColorInt(context)
    val randomRight = getRandomColorInt(context)
    val gd = GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            intArrayOf(randomStr, randomRight)
    )
    gd.cornerRadius = 6f
    return gd
}

fun getRandomColorInt(context: Context): Int {
    val array = context.resources.getIntArray(R.array.ChipColor)
    return array[Random().nextInt(array.size)]
}

fun getRandomColor(context: Context): ColorDrawable {
    val array = context.resources.getIntArray(R.array.ChipColor)
    val randomCl = array[Random().nextInt(array.size)]
    return ColorDrawable(randomCl)
}

fun getLighterColor(inputColor: ColorDrawable): ColorDrawable {
    val toReturn = ColorDrawable(inputColor.color)
    toReturn.alpha = 90
    return toReturn
}