package com.jericho2code.app_finance_manager.screens.template_list

import com.jericho2code.app_finance_manager.model.entity.Template
import com.jericho2code.app_finance_manager.model.repositories.TemplateRepository
import com.jericho2code.app_finance_manager.utils.StateOwnerViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class TemplateListViewModel : StateOwnerViewModel() {

    @Inject
    lateinit var templateRepository: TemplateRepository

    fun templates() = templateRepository.templates()

    fun incrementTemplateUsageCount(template: Template) {
        scope.launch {
            templateRepository.updateTemplate(template.apply { usageCount += 1 })
        }
    }
}