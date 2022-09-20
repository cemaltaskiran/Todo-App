package tr.com.tradesoft.todoapp.domain

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.core.UseCase
import tr.com.tradesoft.todoapp.data.datasource.remote.base.LoginRemoteDataSource
import tr.com.tradesoft.todoapp.data.datasource.remote.model.TokenResult

class LoginUseCase(private val loginRemoteDataSource: LoginRemoteDataSource) :
    UseCase<LoginUseCase.Input, TokenResult>() {

    data class Input(val email: String, val password: String)

    override suspend fun run(input: Input): DataResult<TokenResult> {
        return loginRemoteDataSource.login(input.email, input.password)
    }
}