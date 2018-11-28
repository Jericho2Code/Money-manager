package com.jericho2code.app_finance_manager.application.di.components

import com.jericho2code.app_finance_manager.application.di.modules.CategoryModule
import com.jericho2code.app_finance_manager.application.di.modules.TemplateModule
import com.jericho2code.app_finance_manager.application.di.modules.TransactionModule
import com.jericho2code.app_finance_manager.application.di.scopes.ScreenScope
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.AddEditTransactionViewModel
import dagger.Subcomponent

@ScreenScope
@Subcomponent(
    modules = [
        TransactionModule::class,
        CategoryModule::class,
        TemplateModule::class
    ]
)
interface TransactionAddEditComponent {
    fun inject(viewModel: AddEditTransactionViewModel)
}