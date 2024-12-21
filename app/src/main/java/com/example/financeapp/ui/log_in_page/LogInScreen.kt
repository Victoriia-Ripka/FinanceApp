package com.example.financeapp.ui.log_in_page


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun LogInScreen(
    authorizate: () -> Unit,
    signInScreen: () -> Unit
) {

    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }

    fun dothing() {
        println("do thing")
    }

    Column(modifier = Modifier.padding(60.dp, 60.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "FINANCE",
            modifier = Modifier.padding(0.dp, 40.dp)
        )
        TextField(
            value = Email,
            onValueChange = { Email = it },
            label = { Text("Email") },
            maxLines = 1,
        )
        TextField(
            value = Password,
            onValueChange = { Password = it },
            label = { Text("Password") },
            maxLines = 1,
        )
        Column(modifier = Modifier.padding(0.dp, 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Button(
                onClick = authorizate
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