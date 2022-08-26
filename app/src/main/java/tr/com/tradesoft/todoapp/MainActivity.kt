package tr.com.tradesoft.todoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tr.com.tradesoft.todoapp.ui.create.CreateTodoFragment
import tr.com.tradesoft.todoapp.ui.list.TodoListFragment
import tr.com.tradesoft.todoapp.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TodoListFragment.newInstance())
                .commitNow()
        }
    }
}