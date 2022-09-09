package tr.com.tradesoft.todoapp.domain

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UseCase
import tr.com.tradesoft.todoapp.data.repository.base.TodoRepository
import java.util.*

class CreateTodoUseCase(private val todoRepository: TodoRepository) :
    UseCase<CreateTodoUseCase.Input, Unit>() {

    data class Input(
        val title: String,
        val description: String?,
        val dueDateTime: Date?,
    )

    override suspend fun run(input: Input): DataResult<Unit> {
        val dueDateTime = input.dueDateTime?.time
        val created = Date().time
        return DataResult.Success(
            todoRepository.create(
                input.title,
                input.description,
                dueDateTime,
                created
            )
        )
    }
}