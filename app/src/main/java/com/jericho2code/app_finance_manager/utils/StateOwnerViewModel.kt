package com.jericho2code.app_finance_manager.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View

abstract class StateOwnerViewModel : CorutineViewModel() {
    var screenState = MutableLiveData<State>()

    fun setState(state: State) {
        screenState.postValue(state)
    }
}

abstract class StateFragment<VM : StateOwnerViewModel> : Fragment() {

    lateinit var viewModel: VM

    abstract fun provideViewModel(): VM
    abstract fun onStateChange(state: State)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel()
        viewModel.screenState.observe(this, Observer { state ->
            state?.let { onStateChange(state) }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setState(viewModel.screenState.value ?: LoadingState())
    }
}

abstract class State
class ContentState<T>(val value: T) : State()
class LoadingState : State()
class ErrorState : State()
class EmptyState : State()
