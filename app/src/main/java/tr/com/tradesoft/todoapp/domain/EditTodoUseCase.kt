package tr.com.tradesoft.todoapp.domain

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UseCase
import tr.com.tradesoft.todoapp.data.repository.base.TodoRepository
import java.util.*

class EditTodoUseCase(private val todoRepository: TodoRepository) :
    UseCase<EditTodoUseCase.Input, Unit>() {

    data class Input(
        val id: Long,
        val title: String,
        val description: String?,
        val dueDateTime: Date?,
        val done: Boolean = false
    )

    override suspend fun run(input: Input): DataResult<Unit> {
        val dueDateTime = input.dueDateTime?.time
        val edited = Date().time
        return DataResult.Success(
            todoRepository.update(
                input.id,
                input.title,
                input.description,
                dueDateTime,
                edited,
                input.done
            )
        )
    }
}