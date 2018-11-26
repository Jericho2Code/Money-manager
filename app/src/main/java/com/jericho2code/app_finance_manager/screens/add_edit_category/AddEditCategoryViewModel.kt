package com.jericho2code.app_finance_manager.screens.add_edit_category

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import javax.inject.Inject

class AddEditCategoryViewModel : ViewModel() {

    @Inject
    lateinit var categoryRepository: CategoryRepository

    var backgroundColorLiveData = MutableLiveData<Int>()
    var iconColorLiveData = MutableLiveData<Int>()
    var iconIdLiveData = MutableLiveData<Int>()

    fun setBackgroundColor(color: Int?) {
        backgroundColorLiveData.postValue(color)
    }

    fun setIconColor(color: Int?) {
        iconColorLiveData.postValue(color)
    }

    fun setIconId(id: Int?) {
        iconIdLiveData.postValue(id)
    }


    fun saveCategory(category: Category) = categoryRepository.saveCategory(category)
}