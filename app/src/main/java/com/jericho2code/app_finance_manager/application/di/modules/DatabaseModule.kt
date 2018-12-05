package com.jericho2code.app_finance_manager.application.di.modules

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.ContentValues
import android.content.Context
import android.util.Log
import com.jericho2code.app_finance_manager.R
import com.jericho2code.app_finance_manager.application.di.scopes.AppScope
import com.jericho2code.app_finance_manager.application.extensions.str
import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.model.entity.Category
import dagger.Module
import dagger.Provides

@Module
open class DatabaseModule {
    @Provides
    @AppScope
    open fun providesRoomDb(
        context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                Log.e("test-db", "init")
                initCategoryBaseValue(db, context)
                initAccountBaseValue(db, context)
            }
        })
        .build()
}

fun initAccountBaseValue(db: SupportSQLiteDatabase, context: Context) {
    Log.e("test-db", "start create acc table")
    db.execSQL("CREATE TABLE IF NOT EXISTS `account_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `balance` REAL NOT NULL, `title` TEXT)")
    Log.e("test-db", "after create acc table")
    val baseAccount =  ContentValues().apply {
        put("balance", 0.0)
        put("title", context.str(R.string.base_account))
    }
    Log.e("test-db", "before insert acc table")
    db.insert("account_table", OnConflictStrategy.IGNORE, baseAccount)
    Log.e("test-db", "after insert acc table")
}

fun initCategoryBaseValue(db: SupportSQLiteDatabase, context: Context) {
    db.execSQL("CREATE TABLE IF NOT EXISTS `category_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT NOT NULL, `baseTransactionType` TEXT NOT NULL, `backgroundColor` INTEGER NOT NULL, `iconIdName` TEXT NOT NULL)")
    Category.baseCategories(context).forEach {
        val category = ContentValues().apply {
            put("title", it.title)
            put("baseTransactionType", it.baseTransactionType.value)
            put("backgroundColor", it.backgroundColor)
            put("iconIdName", it.iconIdName)
        }
        db.insert("category_table", OnConflictStrategy.IGNORE, category)
    }
}