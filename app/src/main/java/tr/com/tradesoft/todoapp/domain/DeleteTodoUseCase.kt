package tr.com.tradesoft.todoapp.domain

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UseCase
import tr.com.tradesoft.todoapp.data.repository.base.TodoRepository

class DeleteTodoUseCase(private val todoRepository: TodoRepository) :
    UseCase<DeleteTodoUseCase.Input, Unit>() {

    data class Input(
        val id: Long,
    )

    override suspend fun run(input: Input): DataResult<Unit> {
        return DataResult.Success(
            todoRepository.deleteById(
                input.id,
            )
        )
    }
}