package tr.com.tradesoft.todoapp.core

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class CommonViewModel<State : UiState> : ViewModel() {

    protected val _state: MutableStateFlow<State> by lazy { MutableStateFlow(initState()) }
    val state: StateFlow<State> get() = _state

    abstract fun initState(): State
}