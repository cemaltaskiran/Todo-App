package tr.com.tradesoft.todoapp.data.repository.model

import java.util.*

data class Todo(
    val id: Long,
    val title: String,
    val description: String,
    val dueDate: Date?,
    val created: Date,
    val edited: Date?,
    val done: Boolean,
)