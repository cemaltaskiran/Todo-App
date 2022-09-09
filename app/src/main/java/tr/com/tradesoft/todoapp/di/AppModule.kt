package tr.com.tradesoft.todoapp.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import tr.com.tradesoft.todoapp.data.datasource.local.base.TodoLocalDataSource
import tr.com.tradesoft.todoapp.data.datasource.local.impl.TodoLocalDataSourceImpl
import tr.com.tradesoft.todoapp.data.repository.base.TodoRepository
import tr.com.tradesoft.todoapp.data.repository.impl.TodoRepositoryImpl
import tr.com.tradesoft.todoapp.database.AppDatabase
import tr.com.tradesoft.todoapp.domain.CreateTodoUseCase
import tr.com.tradesoft.todoapp.domain.EditTodoUseCase
import tr.com.tradesoft.todoapp.domain.GetTodosUseCase

val appModule = module {

    factory {
        AndroidSqliteDriver(AppDatabase.Schema, androidContext(), "todos_app.db")
    } bind SqlDriver::class

    single {
        AppDatabase(get())
    } bind AppDatabase::class

    single {
        TodoLocalDataSourceImpl(get())
    } bind TodoLocalDataSource::class

    single {
        TodoRepositoryImpl(get())
    } bind TodoRepository::class

    factory {
        CreateTodoUseCase(get())
    }

    factory {
        GetTodosUseCase(get())
    }

    factory {
        EditTodoUseCase(get())
    }
}