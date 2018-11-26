package com.jericho2code.app_finance_manager.utils

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel : ViewModel() {
    var disposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        unsubscribe()
    }

    protected fun unsubscribe() {
        disposable.dispose()
    }
}