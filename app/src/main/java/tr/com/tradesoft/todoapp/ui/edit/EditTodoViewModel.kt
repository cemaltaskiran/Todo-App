package tr.com.tradesoft.todoapp.ui.edit

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import tr.com.tradesoft.todoapp.core.CommonViewModel
import tr.com.tradesoft.todoapp.core.UiState
import tr.com.tradesoft.todoapp.domain.DeleteTodoUseCase
import tr.com.tradesoft.todoapp.domain.EditTodoUseCase

class EditTodoViewModel : CommonViewModel<EditTodoViewModel.UIState>() {

    private val editTodoUseCase: EditTodoUseCase by inject(EditTodoUseCase::class.java)
    private val deleteTodoUseCase: DeleteTodoUseCase by inject(DeleteTodoUseCase::class.java)

    override fun initState(): UIState {
        return UIState()
    }

    fun edit(id: Long, title: String, description: String, done: Boolean) {
        viewModelScope.launch {
            editTodoUseCase.execute(EditTodoUseCase.Input(id, title, description, null, done))
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            deleteTodoUseCase.execute(DeleteTodoUseCase.Input(id))
        }
    }

    data class UIState(
        val title: String = "",
        val description: String = "",
    ) : UiState
}