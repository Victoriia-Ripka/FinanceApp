package com.example.financeapp.ui.sign_in_page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.financeapp.models.requests.RegisterRequest
import com.example.financeapp.models.responses.RegisterResponse
import com.example.financeapp.services.RetrofitClient
import com.example.pr4_calc.ui.dropdown.DropdownList
import java.net.SocketTimeoutException

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignInScreen(
    register: () -> Unit,
    logInScreen: () -> Unit
) {
    val context = LocalContext.current
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var referalCode by remember { mutableStateOf("") }
    var currency: List<String> = listOf("Euro", "USD", "UAH")
    var selectedIndexDrop by rememberSaveable { mutableStateOf(0) }
    val buttonModifier = Modifier.width(280.dp)

    fun showMessageToUser(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun registerUser() {
        val apiService = RetrofitClient.apiService

        val request = RegisterRequest(
            name = name,
            email = email,
            password = password,
            referalCode = if (referalCode.isEmpty()) null else referalCode,
            currency = currency[selectedIndexDrop]
        )

        apiService.registerUser(request).enqueue(object : retrofit2.Callback<RegisterResponse> {
            override fun onResponse(
                call: retrofit2.Call<RegisterResponse>,
                response: retrofit2.Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val registerResponse = response.body()
                    Log.d("debug", "Registration successful: ${registerResponse?.token}")
                } else {
                    Log.d("debug", "Registration failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<RegisterResponse>, t: Throwable) {
                if (t is SocketTimeoutException) {
                    Log.d("debug", "Timeout error: ${t.message}")
                    showMessageToUser("The server might be sleeping. Please try again.")
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
            value = name,
            onValueChange = { name = it },
            label = { Text("Ім'я") },
            maxLines = 1,
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
        DropdownList(currency, selectedIndexDrop, buttonModifier, onItemClick = {
            selectedIndexDrop = it
            val choosed_currency = currency[it]
        })
        TextField(
            value = referalCode,
            onValueChange = { referalCode = it },
            label = { Text("Реферальний код") },
            maxLines = 1,
        )
        Column(modifier = Modifier.padding(0.dp, 100.dp).height(300.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Button(
                onClick = { registerUser() },
//                modifier = Modifier
//                    .width(250.dp)
//                    .height(60.dp),
//                shape = RoundedCornerShape(12.dp), // Rounded corners
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF6200EE), // Background color
//                    contentColor = Color.White
//                )
            ) {
                Text("Зареєструватися")
            }
            Button(
                onClick = logInScreen,
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(50.dp),
//                shape = RoundedCornerShape(12.dp), // Rounded corners
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color(0xFF6200EE), // Background color
//                    contentColor = Color.White
//                )
            ) {
                Text("Є існуючий акаунт?")
            }
        }
    }
}