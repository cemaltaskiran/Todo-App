package tr.com.tradesoft.todoapp.core

sealed class DataResult<out T> {
    class Error(val exception: Exception) : DataResult<Nothing>()
    class Success<out T>(val data: T) : DataResult<T>()
}
