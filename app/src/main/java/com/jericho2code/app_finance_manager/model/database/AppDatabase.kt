package com.jericho2code.app_finance_manager.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.jericho2code.app_finance_manager.model.database.converters.LocalDateConverter
import com.jericho2code.app_finance_manager.model.database.converters.TransactionTypeConverter
import com.jericho2code.app_finance_manager.model.database.dao.TransactionDao
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.entity.Transaction

@Database(
    entities = [
        Transaction::class,
        Category::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateConverter::class, TransactionTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        const val DATABASE_NAME = "app-finance-manager-database.db"
    }
}