package com.jericho2code.app_finance_manager.application.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.application.di.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
open class DatabaseModule {
    @Provides
    @AppScope
    open fun providesRoomDb(
            context: Context
    ): AppDatabase = Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
}