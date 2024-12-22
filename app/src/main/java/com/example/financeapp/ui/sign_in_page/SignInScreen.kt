package com.example.financeapp.ui.sign_in_page

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import com.example.pr4_calc.ui.dropdown.DropdownList




@Composable
fun SignInScreen(
    register: () -> Unit,
    logInScreen: () -> Unit
) {

    var Name by remember { mutableStateOf("") }
    var Email by remember { mutableStateOf("") }
    var Password by remember { mutableStateOf("") }
//    var Currency by remember { mutableStateOf("") }
    var ReferalCode by remember { mutableStateOf("") }

    var Currency: List<String> = listOf("Euro", "USD")
    var selectedIndexDrop by rememberSaveable { mutableStateOf(0) }
    val buttonModifier = Modifier.width(280.dp)

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
            value = Name,
            onValueChange = { Name = it },
            label = { Text("Ім'я") },
            maxLines = 1,
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
        DropdownList(Currency, selectedIndexDrop, buttonModifier, onItemClick = {
            selectedIndexDrop = it
            val choosed_currency = Currency[it]
        })
        TextField(
            value = ReferalCode,
            onValueChange = { ReferalCode = it },
            label = { Text("Реферальний код") },
            maxLines = 1,
        )
        Column(modifier = Modifier.padding(0.dp, 100.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Button(
                onClick = register
            ) {
                Text("Зареєструватися")
            }
            Button(
                onClick = logInScreen
            ) {
                Text("Є існуючий акаунт?")
            }
        }
    }
}