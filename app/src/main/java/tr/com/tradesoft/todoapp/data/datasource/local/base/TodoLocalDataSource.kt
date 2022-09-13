package tr.com.tradesoft.todoapp.data.datasource.local.base

import tr.com.tradesoft.todoapp.database.Todos

interface TodoLocalDataSource {
    suspend fun create(title: String, description: String?, dueDateTime: Long?, created: Long): Long
    suspend fun update(
        id: Long,
        title: String,
        description: String?,
        dueDateTime: Long?,
        edited: Long?,
        done: Boolean?
    )

    suspend fun deleteById(id: Long)
    suspend fun getAll(): List<Todos>
}