package tr.com.tradesoft.todoapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tr.com.tradesoft.todoapp.di.appModule

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            //inject Android context
            androidContext(this@TodoApplication)
            // use modules
            modules(appModule)
        }
    }
}