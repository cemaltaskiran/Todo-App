package tr.com.tradesoft.todoapp.data.repository.base

import tr.com.tradesoft.todoapp.data.repository.model.Todo

interface TodoRepository {
    suspend fun create(title: String, description: String?, dueDateTime: Long?, created: Long)
    suspend fun update(
        id: Long,
        title: String,
        description: String?,
        dueDateTime: Long?,
        edited: Long?,
        done: Boolean?
    )

    suspend fun deleteById(id: Long)
    suspend fun getAll(): List<Todo>
}