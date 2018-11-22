package ru.kinoplan24.app.model.database.convertors

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson

open class BaseConverter<T>(private val type: Class<T>) {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): T? = value?.let { gson.fromJson(value, type) }

    @TypeConverter
    fun fromClass(value: T?): String? = value?.let { gson.toJson(value) }

}
