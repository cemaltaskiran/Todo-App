package tr.com.tradesoft.todoapp.domain

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UseCase
import tr.com.tradesoft.todoapp.data.repository.base.TodoRepository
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import java.util.*

class GetTodosUseCase(private val todoRepository: TodoRepository) :
    UseCase<Unit, List<Todo>>() {

    override suspend fun run(input: Unit): DataResult<List<Todo>> {
        return todoRepository.getAll()
    }
}