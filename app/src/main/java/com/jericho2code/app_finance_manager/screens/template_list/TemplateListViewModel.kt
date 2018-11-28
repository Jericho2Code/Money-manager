package com.jericho2code.app_finance_manager.screens.template_list

import android.arch.lifecycle.ViewModel
import com.jericho2code.app_finance_manager.model.repositories.TemplateRepository
import com.jericho2code.app_finance_manager.model.repositories.TransactionRepository
import javax.inject.Inject

class TemplateListViewModel : ViewModel() {

    @Inject
    lateinit var templateRepository: TemplateRepository

    fun templates() = templateRepository.templates()
}