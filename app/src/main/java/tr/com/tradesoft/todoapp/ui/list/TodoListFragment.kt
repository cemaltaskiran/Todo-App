package tr.com.tradesoft.todoapp.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import tr.com.tradesoft.todoapp.MainActivity
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.Navigator
import tr.com.tradesoft.todoapp.core.NavigatorFragment
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import tr.com.tradesoft.todoapp.ui.create.CreateTodoFragment
import tr.com.tradesoft.todoapp.ui.edit.EditTodoFragment

class TodoListFragment : NavigatorFragment() {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private lateinit var viewModel: TodoListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var addNewButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override val title: String get() = getString(R.string.list_todo_title)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)

        recyclerView = view.findViewById(R.id.todoList)
        addNewButton = view.findViewById(R.id.addNewTodo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.viewModelScope.launch {
            viewModel.state.collect { state ->
                recyclerView.adapter = TodoListAdapter(state.list) {
                    navigator?.navigate(EditTodoFragment.newInstance(it), true)
                }
            }
        }

        viewModel.getTodos()

        addNewButton.setOnClickListener {
            navigator?.navigate(CreateTodoFragment.newInstance(), true)
        }
    }

}