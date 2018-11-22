package com.jericho2code.app_finance_manager.application.di.modules

import android.content.Context
import com.jericho2code.app_finance_manager.application.di.scopes.AppScope
import dagger.Module
import dagger.Provides

@Module
open class ContextModule(val context: Context) {
    @Provides
    @AppScope
    fun context(): Context = context.applicationContext
}
