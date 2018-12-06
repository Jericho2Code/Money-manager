package com.jericho2code.app_finance_manager.application.di.modules

import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
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

}