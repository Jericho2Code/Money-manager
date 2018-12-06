package com.jericho2code.app_finance_manager.screens.add_edit_category

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.jericho2code.app_finance_manager.model.entity.Category
import com.jericho2code.app_finance_manager.model.repositories.CategoryRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class AddEditCategoryViewModel : ViewModel() {

    @Inject
    lateinit var categoryRepository: CategoryRepository

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)


    var backgroundColorLiveData = MutableLiveData<Int>()
    var iconIdLiveData = MutableLiveData<Int>()

    fun setBackgroundColor(color: Int?) {
        backgroundColorLiveData.postValue(color)
    }

    fun setIconId(id: Int?) {
        iconIdLiveData.postValue(id)
    }

    fun saveCategory(category: Category) {
        scope.launch {
            categoryRepository.saveCategory(category)
        }
    }

    override fun onCleared() {
        super.onCleared()
        parentJob.cancel()
    }
}