package com.odc.finalappodc.http

import com.odc.finalappodc.model.Users
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object Requettes {
    val baseUrl = "https://reqres.in/api/"

    fun getInstance() : Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


}
interface API {
    @GET("users")
    suspend fun getUsers(): Response<Users>?
}