package com.jericho2code.app_finance_manager.model.repositories

import android.arch.lifecycle.LiveData
import com.jericho2code.app_finance_manager.model.database.dao.TemplateDao
import com.jericho2code.app_finance_manager.model.entity.Template
import com.jericho2code.app_finance_manager.model.entity.TemplateFullObject
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class TemplateRepository @Inject constructor(
    private val templateDao: TemplateDao,
    private val uiScheduler: Scheduler,
    private val ioScheduler: Scheduler
) {

    fun templates(): LiveData<List<TemplateFullObject>> = templateDao.templates()

    fun saveTemplate(template: Template): Single<Long> = Single.fromCallable { templateDao.insert(template) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)

    fun updateTemplate(template: Template): Single<Unit> = Single.fromCallable { templateDao.update(template) }
        .subscribeOn(ioScheduler)
        .observeOn(uiScheduler)
}