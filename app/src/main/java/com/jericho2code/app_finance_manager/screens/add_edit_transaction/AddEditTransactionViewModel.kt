package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.arch.lifecycle.MutableLiveData
import com.jericho2code.app_finance_manager.model.entity.*
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import com.jericho2code.app_finance_manager.model.repositories.TemplateRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import com.jericho2code.app_finance_manager.utils.BaseViewModel
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class AddEditTransactionViewModel : BaseViewModel() {

    @Inject
    lateinit var transactionRepository: TransactionRepository
    @Inject
    lateinit var categoryRepository: CategoryRepository
    @Inject
    lateinit var templateRepository: TemplateRepository

    var categoryLiveData = MutableLiveData<Category>()
    var transactionDateLiveData = MutableLiveData<LocalDateTime>()
    var templateDateLiveData = MutableLiveData<TemplateFullObject>()
    var saveAsTemplateVisibilityLiveData = MutableLiveData<Boolean>()
    var editTransactionLiveData = MutableLiveData<TransactionWithCategory>()
    var transactionTypeLiveData = MutableLiveData<TransactionType>()

    fun saveTransaction(transaction: Transaction) = transactionRepository.saveTransaction(transaction)

    fun updateTransaction(transaction: Transaction) = transactionRepository.updateTransaction(transaction)

    fun saveTemplate(template: Template) = templateRepository.saveTemplate(template)

    fun categories() = categoryRepository.categories()

    fun setCategory(category: Category) {
        categoryLiveData.postValue(category)
    }

    fun setTransactionDate(date: LocalDateTime) {
        transactionDateLiveData.postValue(date)
    }

    fun setTemplate(template: TemplateFullObject) {
        templateDateLiveData.postValue(template)
    }

    fun setAsTemplateVisibility(isVisible: Boolean) {
        saveAsTemplateVisibilityLiveData.postValue(isVisible)
    }

    fun setEditTransaction(transaction: TransactionWithCategory) {
        editTransactionLiveData.postValue(transaction)
    }

    fun setTransactionType(transactionType: TransactionType) {
        transactionTypeLiveData.postValue(transactionType)
    }
}