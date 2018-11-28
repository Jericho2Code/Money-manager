package com.jericho2code.app_finance_manager.application.di.components

import com.jericho2code.app_finance_manager.application.di.modules.TemplateModule
import com.jericho2code.app_finance_manager.application.di.scopes.ScreenScope
import com.jericho2code.app_finance_manager.screens.template_list.TemplateListViewModel
import dagger.Subcomponent

@ScreenScope
@Subcomponent(
    modules = [
        TemplateModule::class
    ]
)
interface TemplateListComponent {
    fun inject(viewModel: TemplateListViewModel)
}