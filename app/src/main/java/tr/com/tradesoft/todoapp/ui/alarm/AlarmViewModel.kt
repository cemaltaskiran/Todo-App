package tr.com.tradesoft.todoapp.ui.alarm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import tr.com.tradesoft.todoapp.core.CommonViewModel
import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UiState
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import tr.com.tradesoft.todoapp.domain.GetTodoUseCase

class AlarmViewModel : CommonViewModel<AlarmViewModel.UIState>() {

    private val getTodoUseCase: GetTodoUseCase by KoinJavaComponent.inject(GetTodoUseCase::class.java)


    fun getTodo(id: Long) {
        viewModelScope.launch {
            when (val result = getTodoUseCase.execute(GetTodoUseCase.Input(id))) {
                is DataResult.Error -> {
                    result.exception.printStackTrace()
                }
                is DataResult.Success -> {
                    _state.emit(state.value.copy(todo = result.data))
                }
            }
        }
    }

    data class UIState(
        val todo: Todo? = null
    ) : UiState

    override fun initState(): UIState {
        return UIState()
    }

}