package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.application.di.Name
import com.jericho2code.app_finance_manager.model.database.AppDatabase
import com.jericho2code.app_finance_manager.model.database.dao.AccountDao
import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import javax.inject.Named

@Module
class AccountModule {

    @Provides
    fun provideRepository(
        dao: AccountDao,
        @Named(Name.UI_SCHEDULER)
        uiScheduler: Scheduler,
        @Named(Name.IO_SCHEDULER)
        ioScheduler: Scheduler
    ): AccountRepository = AccountRepository(dao, uiScheduler, ioScheduler)

    @Provides
    fun provideDao(db: AppDatabase): AccountDao = db.accountDao()
}