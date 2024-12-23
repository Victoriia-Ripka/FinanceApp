package com.example.financeapp.services

import com.example.financeapp.models.requests.LoginRequest
import com.example.financeapp.models.requests.RegisterRequest
import com.example.financeapp.models.responses.CurrentBalanceCategoriesResponse
import com.example.financeapp.models.responses.CurrentBalanceResponse
import com.example.financeapp.models.responses.LoginResponse
import com.example.financeapp.models.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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

    @GET("finance/balance/current/")
    fun getCurrentBalance(@Header("Authorization") token: String, ): Call<CurrentBalanceResponse>

    @GET("finance/balance/current/categories/")
    fun getCurrentBalanceCategories(@Header("Authorization") token: String, ): Call<CurrentBalanceCategoriesResponse>

    @GET("finance/balance/current/category/")
    fun getCurrentBalanceCategory(@Header("Authorization") token: String, @Query("categoryId") categoryId: String): Call<CurrentBalanceResponse>
}