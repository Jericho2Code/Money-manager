package com.jericho2code.app_finance_manager.application.di.modules

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.ContentValues
import android.content.Context
import com.jericho2code.app_finance_manager.application.di.scopes.AppScope
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
                db.execSQL("CREATE TABLE IF NOT EXISTS `category_table` (`id` INTEGER PRIMARY KEY AUTOINCREMENT, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `baseTransactionType` TEXT NOT NULL, `color` INTEGER NOT NULL, `iconId` INTEGER NOT NULL)")
                Category.baseCategories(context).forEach {
                    val category = ContentValues().apply {
                        put("title", it.title)
                        put("description", it.description)
                        put("baseTransactionType", it.baseTransactionType.value)
                        put("color", it.color)
                        put("iconId", it.iconId)
                    }
                    db.insert("category_table", OnConflictStrategy.IGNORE, category)
                }
            }
        })
        .build()
}