package ru.kinoplan24.app.model.database.convertors

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import java.lang.reflect.Type

open class BaseListConverter<T>(private val type: Type) {
    private val gson = Gson()

    @TypeConverter
    fun fromString(value: String?): List<T>? = value?.let { gson.fromJson(value, type) }

    @TypeConverter
    fun fromList(list: List<T>?): String? = list?.let { gson.toJson(list) }

}