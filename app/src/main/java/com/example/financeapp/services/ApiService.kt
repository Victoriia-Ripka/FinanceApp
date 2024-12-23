package com.example.financeapp.services

import com.example.financeapp.models.requests.LoginRequest
import com.example.financeapp.models.requests.RegisterRequest
import com.example.financeapp.models.responses.LoginResponse
import com.example.financeapp.models.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("users/register/")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("users/login/")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

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