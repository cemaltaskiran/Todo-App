package tr.com.tradesoft.todoapp.data.datasource.remote.base

import tr.com.tradesoft.todoapp.core.DataResult
import tr.com.tradesoft.todoapp.data.datasource.remote.model.TokenResult

interface LoginRemoteDataSource {
    suspend fun login(email:String, password: String): DataResult<TokenResult>
}