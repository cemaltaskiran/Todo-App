package tr.com.tradesoft.todoapp.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import tr.com.tradesoft.todoapp.domain.GetTodosUseCase

class TodoListViewModel : ViewModel() {
    private val getTodosUseCase: GetTodosUseCase by inject(GetTodosUseCase::class.java)

    private val _state = MutableStateFlow(UIState())
    val state: StateFlow<UIState> get() = _state

    fun getTodos() {
        viewModelScope.launch {
            when (val result = getTodosUseCase.execute(Unit)) {
                is DataResult.Error -> {

                }
                is DataResult.Success -> {
                    _state.emit(_state.value.copy(list = result.data))
                }
            }
        }
    }

    data class UIState(val list: List<Todo> = emptyList())
}