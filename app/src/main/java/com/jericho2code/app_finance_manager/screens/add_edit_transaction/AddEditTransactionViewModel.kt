package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.arch.lifecycle.MutableLiveData
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.entity.Transaction
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import com.jericho2code.app_finance_manager.utils.BaseViewModel
import javax.inject.Inject

class AddEditTransactionViewModel : BaseViewModel() {

    @Inject
    lateinit var transactionRepository: TransactionRepository

    @Inject
    lateinit var categoryRepository: CategoryRepository

    var categoryLiveData = MutableLiveData<Category>()

    fun saveTransaction(transaction: Transaction) = transactionRepository.saveTransaction(transaction)

    fun categories() = categoryRepository.categories()

    fun setCategory(category: Category?) {
        categoryLiveData.postValue(category)
    }
}