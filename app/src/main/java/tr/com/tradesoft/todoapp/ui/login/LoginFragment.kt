package tr.com.tradesoft.todoapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import tr.com.tradesoft.todoapp.R
import tr.com.tradesoft.todoapp.core.NavigatorFragment
import tr.com.tradesoft.todoapp.databinding.FragmentLoginBinding
import tr.com.tradesoft.todoapp.ui.list.TodoListFragment

class LoginFragment : NavigatorFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var listenJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override val title: String
        get() = getString(R.string.login)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        setOnClickListeners()
        listenState()
    }

    private fun setOnClickListeners() {

        binding.login.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            viewModel.login(email, password)
        }
    }

    private fun listenState() {
        listenJob?.cancel()
        listenJob = viewModel.viewModelScope.launch {
            viewModel.state.collect {
                binding.login.isClickable = !it.inProgress
                val loginStatus = when (it.loginStatus) {
                    LoginViewModel.LoginStatus.Failed -> getString(R.string.failed)
                    LoginViewModel.LoginStatus.Idle -> getString(R.string.idle)
                    LoginViewModel.LoginStatus.Success -> getString(R.string.success)
                }
                binding.loginStatusTextView.text = loginStatus
                if (it.loginStatus is LoginViewModel.LoginStatus.Success) {
                    navigator?.navigate(TodoListFragment.newInstance(), false)
                }
            }
        }
    }

}