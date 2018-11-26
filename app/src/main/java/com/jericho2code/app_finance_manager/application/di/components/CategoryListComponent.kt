package com.jericho2code.app_finance_manager.application.di.components

import com.jericho2code.app_finance_manager.application.di.modules.CategoryModule
import com.jericho2code.app_finance_manager.application.di.scopes.ScreenScope
import com.jericho2code.app_finance_manager.screens.add_edit_transaction.SelectCategoryFragment
import com.jericho2code.app_finance_manager.screens.category_list.CategoryListFragment
import dagger.Subcomponent

@ScreenScope
@Subcomponent(
    modules = [
        CategoryModule::class
    ]
)
interface CategoryListComponent {
    fun inject(view: CategoryListFragment)
    fun inject(view: SelectCategoryFragment)

}