package tr.com.tradesoft.todoapp.data.datasource.remote.impl

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.data.datasource.remote.base.LoginRemoteDataSource
import tr.com.tradesoft.todoapp.data.datasource.remote.model.TokenResult
import tr.com.tradesoft.todoapp.data.datasource.remote.service.LoginService

class LoginRemoteDataSourceImpl(private val loginService: LoginService) : LoginRemoteDataSource {
    override suspend fun login(email: String, password: String): DataResult<TokenResult> {
        return try {
            val response = loginService.login(email, password)
            DataResult.Success(response)
        } catch (e: Exception) {
            DataResult.Error(Exception(e))
        }
    }
}