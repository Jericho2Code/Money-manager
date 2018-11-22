package com.jericho2code.app_finance_manager.model.database.converters

import android.arch.persistence.room.TypeConverter
import com.jericho2code.app_finance_manager.model.entity.TransactionType

class TransactionTypeConverter {
    @TypeConverter
    fun fromString(value: String?): TransactionType? = value?.let { TransactionType.fromString(it) }

    @TypeConverter
    fun fromTransaction(transaction: TransactionType?): String? = transaction?.value
}