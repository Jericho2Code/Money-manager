package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import com.jericho2code.app_finance_manager.model.repositories.TemplateRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.TransactionAddEditViewModelFactory
import com.jericho2code.app_finance_manager.screens.transaction_list.TransactionListViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelFactoryModule {

    @Provides
    fun provideTransactionListViewModelFactory(
        transactionRepository: TransactionRepository,
        accountRepository: AccountRepository
    ): TransactionListViewModelFactory = TransactionListViewModelFactory(transactionRepository, accountRepository)

    @Provides
    fun provideTransactionAddEditViewModelFactory(
        transactionRepository: TransactionRepository,
        templateRepository: TemplateRepository
    ): TransactionAddEditViewModelFactory = TransactionAddEditViewModelFactory(transactionRepository, templateRepository)

}