package tr.com.tradesoft.todoapp.ui.edit

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.NavigatorFragment
import tr.com.tradesoft.todoapp.data.repository.model.Todo
import tr.com.tradesoft.todoapp.databinding.FragmentEditTodoComposeBinding
import tr.com.tradesoft.todoapp.ui.edit.components.TodoTextField

class EditTodoFragment(private val originalTodo: Todo) : NavigatorFragment() {

    companion object {
        fun newInstance(todo: Todo) = EditTodoFragment(todo)
    }

    private lateinit var viewModel: EditTodoViewModel
    private var _binding: FragmentEditTodoComposeBinding? = null
    private val binding get() = _binding!!

    override val title: String get() = getString(R.string.edit_todo_title)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditTodoComposeBinding.inflate(layoutInflater, container, false)

        binding.composeView.apply {
            setContent {
                var todoName by remember { mutableStateOf(originalTodo.title) }
                var todoDescription by remember { mutableStateOf(originalTodo.description) }
                var done by remember { mutableStateOf(originalTodo.done) }
                MaterialTheme {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        TodoTextField(
                            value = todoName,
                            onValueChange = {
                                todoName = it
                            },
                            label = getString(R.string.title)
                        )
                        TodoTextField(
                            value = todoDescription,
                            onValueChange = {
                                todoDescription = it
                            },
                            label = getString(R.string.description)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = getString(R.string.done))
                            Checkbox(
                                checked = done,
                                onCheckedChange = {
                                    done = it
                                }
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = {
                                    DeleteTodoDialogFragment(onPositive = {
                                        viewModel.delete(originalTodo.id)
                                        navigator?.popBack()
                                    }).show(childFragmentManager, DeleteTodoDialogFragment.TAG)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.Red,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(text = getString(R.string.delete))
                            }
                            Button(onClick = {
                                viewModel.edit(
                                    originalTodo.id,
                                    todoName,
                                    todoDescription,
                                    done
                                )
                                navigator?.popBack()
                            }) {
                                Text(text = getString(R.string.save))
                            }
                        }
                    }
                }
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[EditTodoViewModel::class.java]
    }

    class DeleteTodoDialogFragment(
        val onPositive: () -> Unit
    ) : DialogFragment() {
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.are_you_sure_to_delete_todo))
                .setPositiveButton(getString(R.string.yes)) { _, _ ->
                    onPositive()
                }
                .setNegativeButton(getString(R.string.no)) { _, _ -> }
                .create()

        companion object {
            const val TAG = "DeleteTodoDialogFragment"
        }
    }

}