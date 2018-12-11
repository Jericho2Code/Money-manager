package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.arch.lifecycle.MutableLiveData
import com.jericho2code.app_finance_manager.model.entity.*
import com.jericho2code.app_finance_manager.model.repositories.TemplateRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import com.jericho2code.app_finance_manager.utils.LoadingState
import com.jericho2code.app_finance_manager.utils.StateOwnerViewModel
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDateTime

class AddEditTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val templateRepository: TemplateRepository
) : StateOwnerViewModel() {

    var categoryLiveData = MutableLiveData<Category>()
    var transactionDateLiveData = MutableLiveData<LocalDateTime>()
    var templateDateLiveData = MutableLiveData<TemplateFullObject>()
    var saveAsTemplateVisibilityLiveData = MutableLiveData<Boolean>()
    var editTransactionLiveData = MutableLiveData<TransactionWithCategory>()
    var transactionTypeLiveData = MutableLiveData<TransactionType>()

    fun saveTransaction(transaction: Transaction, useAsTemplate: Boolean) {
        scope.launch {
            transactionRepository.saveTransaction(transaction)
            if (useAsTemplate) {
                saveTemplate(transaction)
            }
            setState(AddEditTransactionFragment.SavedState())
        }
    }

    fun updateTransaction(transaction: Transaction, useAsTemplate: Boolean) {
        scope.launch {
            setState(LoadingState())
            transactionRepository.updateTransaction(transaction)
            if (useAsTemplate) {
                saveTemplate(transaction)
            }
            setState(AddEditTransactionFragment.SavedState())
        }
    }

    private suspend fun saveTemplate(transaction: Transaction) {
        val templateTransactionId = transactionRepository.saveTransaction(
            transaction.apply {
                id = null
                isTemplate = true
            }
        )
        val template = Template(
            usageCount = 1,
            transactionId = templateTransactionId,
            categoryId = categoryLiveData.value?.id ?: 0
        )
        templateRepository.saveTemplate(template)
    }

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