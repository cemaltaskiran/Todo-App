package tr.com.tradesoft.todoapp.ui.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toolbar
import tr.com.tradesoft.todoapp.MainActivity
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.Navigator

class CreateTodoFragment : Fragment() {

    companion object {
        fun newInstance() = CreateTodoFragment()
    }

    private lateinit var viewModel: CreateTodoViewModel
    private lateinit var todoTitle: EditText
    private lateinit var todoDescription: EditText
    private lateinit var createTodoButton: Button
    private var navigator: Navigator? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity is Navigator) {
            navigator = activity as Navigator
        }

        viewModel = ViewModelProvider(this).get(CreateTodoViewModel::class.java)
        todoTitle = view.findViewById(R.id.todoTitle)
        todoDescription = view.findViewById(R.id.todoDescription)
        createTodoButton = view.findViewById(R.id.createTodoButton)

        createTodoButton.setOnClickListener {
            viewModel.createTodo(todoTitle.text.toString(), todoDescription.text.toString())
            navigator?.popBack()
        }
    }

}