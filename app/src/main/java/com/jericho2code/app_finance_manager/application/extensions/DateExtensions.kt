package com.jericho2code.app_finance_manager.application.extensions

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.util.*

object DateFormatter {
    val defaultLocale: Locale = Locale.getDefault()
    val fullDateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", defaultLocale)
}

fun LocalDateTime.toFullDateString(): String = DateFormatter.fullDateFormat.format(this)

fun LocalDateTime.isToday() = dayOfYear == LocalDate.now().dayOfYear
