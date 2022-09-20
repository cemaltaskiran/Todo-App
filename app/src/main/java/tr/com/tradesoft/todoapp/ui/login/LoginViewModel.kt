package tr.com.tradesoft.todoapp.ui.login

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import tr.com.tradesoft.todoapp.core.CommonViewModel
import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UiState
import tr.com.tradesoft.todoapp.domain.LoginUseCase

class LoginViewModel : CommonViewModel<LoginViewModel.UIState>() {

    private val loginUseCase: LoginUseCase by inject(LoginUseCase::class.java)

    override fun initState(): UIState {
        return UIState()
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _state.emit(state.value.copy(inProgress = true))
            when (val result = loginUseCase.execute(LoginUseCase.Input(email, password))) {
                is DataResult.Error -> {
                    println(result.exception.stackTraceToString())
                    _state.emit(
                        state.value.copy(
                            loginStatus = LoginStatus.Failed,
                            inProgress = false
                        )
                    )
                }
                is DataResult.Success -> {
                    println(result.data)
                    _state.emit(
                        state.value.copy(
                            loginStatus = LoginStatus.Success,
                            inProgress = false
                        )
                    )
                }
            }
        }
    }

    sealed class LoginStatus {
        object Idle : LoginStatus()
        object Success : LoginStatus()
        object Failed : LoginStatus()
    }

    data class UIState(
        val inProgress: Boolean = false,
        val loginStatus: LoginStatus = LoginStatus.Idle
    ) : UiState
}