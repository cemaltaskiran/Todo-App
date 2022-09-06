package tr.com.tradesoft.todoapp.ui.list

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.Navigator
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import tr.com.tradesoft.todoapp.ui.create.CreateTodoFragment

class TodoListFragment : Fragment() {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private lateinit var viewModel: TodoListViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var addNewButton: Button
    private var navigator: Navigator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity is Navigator) {
            navigator = activity as Navigator
        }

        recyclerView = view.findViewById(R.id.todoList)
        addNewButton = view.findViewById(R.id.addNewTodo)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = TodoListAdapter(
            listOf(
                Todo("Bir", "açıklama"),
                Todo("İki", "açıklama2"),
            )
        )
        addNewButton.setOnClickListener {
            navigator?.navigate(CreateTodoFragment.newInstance(), true)
        }
    }

}