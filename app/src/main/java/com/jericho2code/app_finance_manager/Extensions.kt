package com.jericho2code.app_finance_manager

import android.content.Context
import android.content.res.Resources
import android.support.annotation.ArrayRes
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager

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