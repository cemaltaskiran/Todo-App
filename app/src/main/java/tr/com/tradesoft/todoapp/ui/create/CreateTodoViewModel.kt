package tr.com.tradesoft.todoapp.ui.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import tr.com.tradesoft.todoapp.domain.CreateTodoUseCase
import java.util.*

class CreateTodoViewModel : ViewModel() {

    private val createTodoUseCase: CreateTodoUseCase by inject(CreateTodoUseCase::class.java)

    fun createTodo(title: String, description: String? = null, dueDate: Date? = null) {
        viewModelScope.launch {
            createTodoUseCase.execute(CreateTodoUseCase.Input(title,description, dueDate))
        }
    }
}