package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.application.di.Name.IO_SCHEDULER
import com.jericho2code.app_finance_manager.application.di.Name.UI_SCHEDULER
import com.jericho2code.app_finance_manager.application.di.scopes.AppScope
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
class SchedulerModule {

    @Provides
    @AppScope
    @Named(UI_SCHEDULER)
    fun provideMainThreadScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @AppScope
    @Named(IO_SCHEDULER)
    fun provideIoScheduler(): Scheduler = Schedulers.io()

}