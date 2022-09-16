package tr.com.tradesoft.todoapp.data.datasource.local.impl

import tr.com.tradesoft.todoapp.data.datasource.local.base.TodoLocalDataSource
import tr.com.tradesoft.todoapp.database.AppDatabase
import tr.com.tradesoft.todoapp.database.Todos

class TodoLocalDataSourceImpl(private val appDatabase: AppDatabase) : TodoLocalDataSource {

    override suspend fun create(
        title: String,
        description: String?,
        dueDateTime: Long?,
        created: Long
    ): Long {
        appDatabase.todosQueries.insert(title, description, dueDateTime, created)
        return appDatabase.todosQueries.lastInsertRowId().executeAsOne()
    }

    override suspend fun update(
        id: Long,
        title: String,
        description: String?,
        dueDateTime: Long?,
        edited: Long?,
        done: Boolean?
    ) {
        appDatabase.todosQueries.updateById(title, description, dueDateTime, edited, done, id)
    }

    override suspend fun deleteById(id: Long) {
        appDatabase.todosQueries.deleteById(id)
    }

    override suspend fun getAll(): List<Todos> {
        return appDatabase.todosQueries.selectAll().executeAsList()
    }

    override suspend fun getById(id: Long): Todos {
        return appDatabase.todosQueries.selectById(id).executeAsOne()
    }
}