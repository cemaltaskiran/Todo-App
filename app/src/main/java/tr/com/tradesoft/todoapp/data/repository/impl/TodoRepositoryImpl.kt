package tr.com.tradesoft.todoapp.data.repository.impl

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import tr.com.tradesoft.todoapp.data.datasource.local.base.TodoLocalDataSource
import tr.com.tradesoft.todoapp.data.repository.base.TodoRepository
import tr.com.tradesoft.todoapp.data.repository.mapToDomain

class TodoRepositoryImpl(private val todoLocalDataSource: TodoLocalDataSource) : TodoRepository {
    override suspend fun create(
        title: String,
        description: String?,
        dueDateTime: Long?,
        created: Long
    ) {
        todoLocalDataSource.create(title, description, dueDateTime, created)
    }

    override suspend fun update(
        id: Long,
        title: String,
        description: String?,
        dueDateTime: Long?,
        edited: Long?,
        done: Boolean?
    ) {
        todoLocalDataSource.update(id, title, description, dueDateTime, edited, done)
    }

    override suspend fun deleteById(id: Long) {
        todoLocalDataSource.deleteById(id)
    }

    override suspend fun getAll(): DataResult<List<Todo>> {
        return try {
            val result = todoLocalDataSource.getAll().map { it.mapToDomain() }
            DataResult.Success(result)
        } catch (e: Exception) {
            DataResult.Error(e)
        }
    }
}