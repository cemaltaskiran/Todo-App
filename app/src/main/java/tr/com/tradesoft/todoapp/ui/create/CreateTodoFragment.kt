package tr.com.tradesoft.todoapp.ui.create

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import tr.com.tradesoft.todoapp.MainActivity
import tr.com.tradesoft.todoapp.R

class CreateTodoFragment : Fragment() {

    companion object {
        fun newInstance() = CreateTodoFragment()
    }

    private lateinit var viewModel: CreateTodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateTodoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}