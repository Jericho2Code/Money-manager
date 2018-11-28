package com.jericho2code.app_finance_manager.model.database.converters

import org.threeten.bp.LocalDateTime
import ru.kinoplan24.app.model.database.convertors.BaseConverter


class LocalDateTimeConverter : BaseConverter<LocalDateTime>(LocalDateTime::class.java)
