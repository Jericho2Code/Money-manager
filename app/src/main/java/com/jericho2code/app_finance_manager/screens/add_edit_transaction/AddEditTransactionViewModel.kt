package com.jericho2code.app_finance_manager.screens.add_edit_transaction

import android.arch.lifecycle.MutableLiveData
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.entity.Template
import com.jericho2code.app_finance_manager.model.entity.TemplateFullObject
import com.jericho2code.app_finance_manager.model.entity.Transaction
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

    fun saveTransaction(transaction: Transaction) = transactionRepository.saveTransaction(transaction)

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
}