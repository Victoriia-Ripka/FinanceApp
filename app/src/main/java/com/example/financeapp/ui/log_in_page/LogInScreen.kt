package com.example.financeapp.ui.log_in_page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.financeapp.models.requests.LoginRequest
import com.example.financeapp.models.responses.LoginResponse
import com.example.financeapp.services.RetrofitClient
import com.example.financeapp.viewmodel.UserViewModel
import java.net.SocketTimeoutException

@Composable
fun LogInScreen(
    authorizate: () -> Unit,
    signInScreen: () -> Unit,
    userViewModel: UserViewModel
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    fun showMessageToUser(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun loginUser() {
        val apiService = RetrofitClient.apiService

        val request = LoginRequest(
            email = email,
            password = password
        )

        apiService.loginUser(request).enqueue(object : retrofit2.Callback<LoginResponse>  {
            override fun onResponse(
                call: retrofit2.Call<LoginResponse>,
                response: retrofit2.Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    loginResponse?.token?.let {
                        userViewModel.setToken(it)
                        Log.d("debug", "Registration successful: $it")
                        authorizate()
                    }
                } else {
                    Log.d("debug", "Registration failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    Log.d("debug", "Timeout error: ${t.message}")
                    showMessageToUser("The server might be sleeping. Please try again (in 30s).")
                } else {
                    Log.d("debug", "Error: ${t.message}")
                    showMessageToUser("An error occurred: ${t.message}")
                }
            }
        })
    }

    Column(modifier = Modifier.padding(60.dp, 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "FINANCE",
            modifier = Modifier.padding(0.dp, 40.dp)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            maxLines = 1,
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            maxLines = 1,
        )
        Column(modifier = Modifier.padding(0.dp, 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Button(
                onClick = { loginUser() }
            ) {
                Text("Увійти")
            }
            Button(
                onClick = signInScreen
            ) {
                Text("Немає існуючого акаунту?")
            }
        }
    }
}