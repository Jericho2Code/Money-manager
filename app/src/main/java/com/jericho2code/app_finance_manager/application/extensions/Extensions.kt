package com.jericho2code.app_finance_manager.application.extensions

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.support.annotation.ArrayRes
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.content.res.AppCompatResources
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import com.jericho2code.app_finance_manager.R

/**
 * Принудительно показывает клавиатуру
 */
fun Context.showKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.run {
        toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
        view.requestFocus()
    }
}

/**
 * Принудительно скрывает клавиатуру
 */
fun Context.hideKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.run {
        view.clearFocus()
        hideSoftInputFromWindow(view.windowToken, 0)
    }
}

/**
 * Функция возвращает список цветов по его id
 *
 * @param colorArrayId id списка цветов
 */
fun Context.getColorsFromTypedArray(@ArrayRes colorArrayId: Int): List<Int> {
    var result: List<Int> = emptyList()
    this.resources?.obtainTypedArray(colorArrayId)?.apply {
        result = (0..length()).map { getColor(it, 0) }
        recycle()
    }
    return result
}

/**
 * Переводит значение из dp в пиксели
 */
fun Int.dp2px(): Int = Math.round(this * Resources.getSystem().displayMetrics.density)

/**
 * Переводит значение из sp в пиксели
 */
fun Int.sp2px(): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), Resources.getSystem().displayMetrics).toInt()

/**
 * Создает и показывает [Snackbar] с переданным текстом
 * и длительностью (по умолчанию [Snackbar.LENGTH_SHORT])
 */
fun View.makeSnackbar(
    @StringRes
    text: Int,
    duration: Int = Snackbar.LENGTH_SHORT
) {
    Snackbar.make(this, text, duration).show()
}

/**
 * Создает и показывает [Toast] с переданным текстом
 * и длительностью (по умолчанию [Toast.LENGTH_SHORT])
 */
fun Context.showToast(
    @StringRes
    text: Int,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, text, duration).show()
}

/**
 * Устанавливает для textview текст или делает его GONE
 */
fun TextView.setTextOrHideIfEmpty(value: String?) {
    if (value?.isNotEmpty() == true) {
        text = value
        visible()
    } else {
        gone()
    }
}

/**
 * Устанавливает visibility в значение VISIBLE
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Устанавливает visibility в значение GONE
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Устанавливает visibility в значение INVISIBLE
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Функция возвращает цвет по его id
 *
 * @param colorId id цвета
 */
fun Context.color(@ColorRes colorId: Int) = ContextCompat.getColor(this, colorId)

/**
 * Функция возвращает объект [Drawable] по его id
 *
 * @param drawableId id изображения
 * @param tintColor цвет заливки (для векторов) по умолчанию равен Null,
 * если равен null то не применяется
 */
fun Context.drawable(@DrawableRes drawableId: Int, tintColor: Int? = null): Drawable {
    val drawable = AppCompatResources.getDrawable(this, drawableId)!!
    tintColor?.let {
        drawable.setColorFilter(tintColor, android.graphics.PorterDuff.Mode.SRC_IN)
    }
    return drawable
}

/**
 *  Возвращает строку по ей Id
 */
fun Context.str(
    @StringRes
    resId: Int,
    vararg formatArgs: Any
): String = if (formatArgs.isNotEmpty()) getString(resId, *formatArgs) else getString(resId)

fun Context.drawableIdByName(name: String) = resources.getIdentifier(name, "drawable", packageName)
fun Context.nameForId(@DrawableRes resId: Int): String = resources.getResourceEntryName(resId)

/**
 * Функция возвращает список цветов по его id
 *
 * @param colorArrayId id списка цветов
 */
fun Context.getIdsFromTypedArray(@ArrayRes colorArrayId: Int): List<Int> {
    var result: List<Int> = emptyList()
    this.resources?.obtainTypedArray(colorArrayId)?.apply {
        result = (0 until length()).map { getResourceId(it, R.drawable.ic_money) }
        recycle()
    }
    return result
}

/**
 * Устанавливает для textview видмость в зависимости от  isVisible,
 * в случае false делает view GONE
 */
fun View.visibleOrGone(isVisible: Boolean?) {
    if (isVisible == true) visible() else gone()
}