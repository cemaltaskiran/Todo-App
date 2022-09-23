package tr.com.tradesoft.todoapp.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.NavigatorFragment
import tr.com.tradesoft.todoapp.databinding.FragmentTodoListComposeBinding
import tr.com.tradesoft.todoapp.ui.create.CreateTodoFragment
import tr.com.tradesoft.todoapp.ui.edit.EditTodoFragment

class TodoListFragment : NavigatorFragment() {

    companion object {
        fun newInstance() = TodoListFragment()
    }

    private lateinit var viewModel: TodoListViewModel
    private var _binding: FragmentTodoListComposeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTodoListComposeBinding.inflate(layoutInflater, container, false)

        binding.composeView.apply {
            setContent {
                val state by viewModel.state.collectAsState()
                LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
                    item {
                        Button(onClick = {
                            navigator?.navigate(
                                CreateTodoFragment.newInstance(),
                                true
                            )
                        }) {
                            Text(text = getString(R.string.new_todo))
                        }
                    }
                    items(state.list) {
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navigator?.navigate(EditTodoFragment.newInstance(it), true)
                            }) {
                            Text(text = it.title, color = Color.Black, fontSize = 20.sp, fontWeight = FontWeight.W500)
                            Text(text = it.description, color = Color.Gray)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }

        return binding.root
    }

    override val title: String get() = getString(R.string.list_todo_title)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[TodoListViewModel::class.java]
        viewModel.getTodos()
    }

}