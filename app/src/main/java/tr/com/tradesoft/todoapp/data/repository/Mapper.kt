package tr.com.tradesoft.todoapp.data.repository

import tr.com.tradesoft.todoapp.data.repository.model.Todo
import tr.com.tradesoft.todoapp.database.Todos
import java.util.*

fun Todos.mapToDomain(): Todo {
    val dueDate = if (dueDateTime != null) {
        Date(dueDateTime)
    } else {
        null
    }
    val edited = if (this.edited != null) {
        Date(this.edited)
    } else {
        null
    }
    return Todo(
        id,
        title,
        description ?: "",
        dueDate,
        Date(created),
        edited,
        done ?: false
    )
}


