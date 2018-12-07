package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.jericho2code.app_finance_manager.model.repositories.AccountRepository
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import com.jericho2code.app_finance_manager.model.repositories.TemplateRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import javax.inject.Inject

class TransactionAddEditViewModelFactory @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val templateRepository: TemplateRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AddEditTransactionViewModel::class.java)) {
            AddEditTransactionViewModel(transactionRepository, templateRepository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }

}