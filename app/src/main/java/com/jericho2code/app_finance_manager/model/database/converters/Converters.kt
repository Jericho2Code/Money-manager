package com.jericho2code.app_finance_manager.model.database.converters

import org.threeten.bp.LocalDate
import ru.kinoplan24.app.model.database.convertors.BaseConverter


class LocalDateConverter : BaseConverter<LocalDate>(LocalDate::class.java)
