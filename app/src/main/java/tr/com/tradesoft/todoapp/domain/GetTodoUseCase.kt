package tr.com.tradesoft.todoapp.domain

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UseCase
import tr.com.tradesoft.todoapp.data.repository.base.TodoRepository
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import java.util.*

class GetTodoUseCase(private val todoRepository: TodoRepository) :
    UseCase<GetTodoUseCase.Input, Todo>() {

    override suspend fun run(input: Input): DataResult<Todo> {
        return todoRepository.getById(input.id)
    }

    data class Input(val id: Long)

}