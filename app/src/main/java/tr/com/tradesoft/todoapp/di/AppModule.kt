package tr.com.tradesoft.todoapp.di

import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module
import tr.com.tradesoft.todoapp.data.datasource.local.base.TodoLocalDataSource
import tr.com.tradesoft.todoapp.data.datasource.local.impl.TodoLocalDataSourceImpl
import tr.com.tradesoft.todoapp.database.AppDatabase

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

    // TODO: TodoRepository ekle
}