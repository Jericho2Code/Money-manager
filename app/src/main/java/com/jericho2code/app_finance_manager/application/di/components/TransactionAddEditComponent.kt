package com.jericho2code.app_finance_manager.application.di.components

import com.jericho2code.app_finance_manager.application.di.modules.TransactionModule
import com.jericho2code.app_finance_manager.application.di.scopes.ScreenScope
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.AddEditTransactionFragment
import com.jericho2code.app_finance_manager.screens.transaction_list.TransactionListFragment
import dagger.Subcomponent

@ScreenScope
@Subcomponent(
    modules = [
        TransactionModule::class
    ]
)
interface TransactionAddEditComponent {
    fun inject(view: AddEditTransactionFragment)
}