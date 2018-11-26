package com.jericho2code.app_finance_manager.screens.category_list

import android.arch.lifecycle.ViewModel
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import javax.inject.Inject

class CategoryListViewModel : ViewModel() {

    @Inject
    lateinit var categoryRepository: CategoryRepository

    fun categories() = categoryRepository.categories()
}