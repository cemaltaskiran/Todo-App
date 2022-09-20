package tr.com.tradesoft.todoapp.data.datasource.remote.model

import com.google.gson.annotations.SerializedName


data class TokenResult(
    @field:SerializedName("access_token") val accessToken: String? = null,
)
