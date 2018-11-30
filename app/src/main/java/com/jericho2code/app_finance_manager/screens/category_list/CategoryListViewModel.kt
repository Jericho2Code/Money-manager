package com.jericho2code.app_finance_manager.screens.category_list

import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import com.jericho2code.app_finance_manager.utils.StateOwnerViewModel
import javax.inject.Inject

class CategoryListViewModel : StateOwnerViewModel() {

    @Inject
    lateinit var categoryRepository: CategoryRepository

    fun categories() = categoryRepository.categories()
}