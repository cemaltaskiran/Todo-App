package tr.com.tradesoft.todoapp.ui.edit

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.Navigator
import tr.com.tradesoft.todoapp.core.NavigatorFragment
import tr.com.tradesoft.todoapp.data.repository.model.Todo

class EditTodoFragment(private val originalTodo: Todo) : NavigatorFragment() {

    companion object {
        fun newInstance(todo: Todo) = EditTodoFragment(todo)
    }

    private lateinit var viewModel: EditTodoViewModel
    private lateinit var todoTitle: EditText
    private lateinit var todoDescription: EditText
    private lateinit var createTodoButton: Button
    private lateinit var deleteTodoButton: Button
    private lateinit var doneCheckBox: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditTodoViewModel::class.java)

        todoTitle = view.findViewById(R.id.editTodoTitle)
        todoDescription = view.findViewById(R.id.editTodoDescription)
        createTodoButton = view.findViewById(R.id.editTodoButton)
        deleteTodoButton = view.findViewById(R.id.deleteTodoButton)
        doneCheckBox = view.findViewById(R.id.editTodoDoneCheckBox)

        todoTitle.setText(originalTodo.title)
        todoDescription.setText(originalTodo.description)
        doneCheckBox.isChecked = originalTodo.done

        createTodoButton.setOnClickListener {
            viewModel.edit(
                originalTodo.id,
                todoTitle.text.toString(),
                todoDescription.text.toString(),
                doneCheckBox.isChecked
            )
            navigator?.popBack()
        }
    }

}