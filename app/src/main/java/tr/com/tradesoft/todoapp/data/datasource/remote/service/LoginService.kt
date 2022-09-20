package tr.com.tradesoft.todoapp.data.datasource.remote.service

import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import tr.com.tradesoft.todoapp.data.datasource.remote.model.TokenResult

interface LoginService {

    @POST("login")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("client_id") client_id: String = "",
        @Field("client_secret") client_secret: String = "",
        @Field("grant_type") grant_type: String = "",
        @Field("type") type: String = "EMAIL",
    ): TokenResult

    companion object {
        private const val BASE_URL = "https://tradesoft.com.tr/"

        fun create(): LoginService {

            val client = OkHttpClient.Builder()
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LoginService::class.java)
        }
    }
}