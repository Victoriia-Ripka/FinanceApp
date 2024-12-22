package com.example.financeapp.services

import com.example.financeapp.models.requests.RegisterRequest
import com.example.financeapp.models.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @POST("users/register/")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

//    @POST("users/login/")
//    fun loginUser(@Body request: ExampleRequest): Call<ExampleResponse>
//
//    @POST("users/logout/")
//    fun logoutUser(@Query("param") param: String): Call<ExampleResponse>
//
//    @GET("users/data/")
//    fun getUserData(): Call<ExampleResponse>
//
//    @PUT("users/data/")
//    fun updateUserData(@Body request: ExampleRequest): Call<ExampleResponse>
//
//    @GET("currency/")
//    fun getCurrencies(): Call<ExampleResponse>
//
//    @GET("currency/rates/")
//    fun getCurrenciesRates(): Call<ExampleResponse>
//
//    @GET("currency/rates/main/")
//    fun getMainCurrenciesRates(): Call<ExampleResponse>
}