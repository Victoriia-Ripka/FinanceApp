package com.example.financeapp.services

import com.example.financeapp.models.requests.LoginRequest
import com.example.financeapp.models.requests.RegisterRequest
import com.example.financeapp.models.requests.UpdateUserRequest
import com.example.financeapp.models.responses.CurrentBalanceCategoriesResponse
import com.example.financeapp.models.responses.CurrentBalanceResponse
import com.example.financeapp.models.responses.LoginResponse
import com.example.financeapp.models.responses.RegisterResponse
import com.example.financeapp.models.responses.UserDataResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ApiService {
    @POST("users/register/")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("users/login/")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("users/logout/")
    fun logoutUser(@Header("Authorization") token: String): Call<Void>

//    @POST("users/password_recovery/")
//    fun recoverPassword(@Body request: ExampleRequest): Call<ExampleResponse>

    @DELETE("users/")
    fun deleteAccount(@Header("Authorization") token: String): Call<Void>

    @GET("users/data/")
    fun getUserData(@Header("Authorization") token: String): Call<UserDataResponse>

    @PUT("users/data/")
    fun updateUserData(@Header("Authorization") token: String, @Body request: UpdateUserRequest): Call<UserDataResponse>

//    @GET("currency/")
//    fun getCurrencies(): Call<ExampleResponse>

//    @GET("currency/rates/")
//    fun getCurrenciesRates(): Call<ExampleResponse>

//    @GET("currency/rates/main/")
//    fun getMainCurrenciesRates(): Call<ExampleResponse>

//    @GET("group/users/")
//    fun getGroupUsers(): Call<ExampleResponse>

//    @DELETE("group/users/")
//    fun updateUserData(@Query("userId") userId: String ): Call<Void>

//    POST	/api/finance/categories/

//    GET	/api/finance/categories/all/

//    DELETE	/api/finance/categories/  @Query("id") id: String

//    POST	/api/finance/records/

//    GET	/api/finance/records/all/

//    DELETE	/api/finance/records/   @Query("id") id: String

    @GET("finance/balance/current/")
    fun getCurrentBalance(@Header("Authorization") token: String, ): Call<CurrentBalanceResponse>

    @GET("finance/balance/current/categories/")
    fun getCurrentBalanceCategories(@Header("Authorization") token: String, ): Call<CurrentBalanceCategoriesResponse>

//    @GET("finance/balance/current/category/")
//    fun getCurrentBalanceCategory(@Header("Authorization") token: String, @Query("categoryId") categoryId: String): Call<CurrentBalanceResponse>


}