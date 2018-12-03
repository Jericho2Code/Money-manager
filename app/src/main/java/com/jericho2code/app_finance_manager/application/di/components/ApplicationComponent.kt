package com.jericho2code.app_finance_manager.application.di.components

import com.jericho2code.app_finance_manager.application.di.modules.ContextModule
import com.jericho2code.app_finance_manager.application.di.modules.DatabaseModule
import com.jericho2code.app_finance_manager.application.di.modules.SchedulerModule
import com.jericho2code.app_finance_manager.application.di.scopes.AppScope
import dagger.Component

@AppScope
@Component(
    modules = [
        ContextModule::class,
        DatabaseModule::class,
        SchedulerModule::class
    ]
)
interface ApplicationComponent {
    fun plusTransactionListComponent(): TransactionListComponent
    fun plusCategoryListComponent(): CategoryListComponent
    fun plusCategoryAddEditComponent(): CategoryAddEditComponent
    fun plusTransactionAddEditComponent(): TransactionAddEditComponent
}

