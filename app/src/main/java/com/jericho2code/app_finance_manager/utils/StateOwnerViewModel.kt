package com.jericho2code.app_finance_manager.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

open class StateOwnerViewModel : ViewModel() {
    var screenState = MutableLiveData<ScreenState>()

    fun setState(state: ScreenState) {
        screenState.postValue(state)
    }
}

abstract class StateFragment<VM : StateOwnerViewModel> : Fragment() {

    lateinit var viewModel: VM

    abstract fun provideViewModel(): VM
    abstract fun showLoading()
    abstract fun showContent()
    abstract fun showError()
    abstract fun showEmpty()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
        viewModel.screenState.observe(this, Observer {
            applyState(it ?: ScreenState.LOADING)
        })
        viewModel.setState(ScreenState.LOADING)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        applyState(viewModel.screenState.value ?: ScreenState.LOADING)
    }

    private fun applyState(screenState: ScreenState) {
        when (screenState) {
            ScreenState.LOADING -> showLoading()
            ScreenState.CONTENT -> showContent()
            ScreenState.ERROR -> showError()
            ScreenState.EMPTY -> showEmpty()
        }
    }

}

enum class ScreenState {
    LOADING, CONTENT, ERROR, EMPTY
}