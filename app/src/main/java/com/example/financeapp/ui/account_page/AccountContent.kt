package com.example.financeapp.ui.account_page


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.models.requests.UpdateUserRequest
import com.example.financeapp.models.responses.UserDataResponse
import com.example.financeapp.services.RetrofitClient
import com.example.financeapp.ui.theme.ChangeValueDialog
import com.example.financeapp.viewmodel.UserViewModel
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
fun AccountContent(
    userViewModel: UserViewModel,
    logout: () -> Unit,
    deleted: () -> Unit
): @Composable () -> Unit {

    val infoRowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)



    val content = @Composable{
        Box() {
            val context = LocalContext.current
            val token by userViewModel.token.observeAsState()
            val apiService = RetrofitClient.apiService

            val contentColor = MaterialTheme.colorScheme.onSecondary
            val contentColorConst = MaterialTheme.colorScheme.primary

            val user = remember {
                mutableStateOf(
                    UserDataResponse(
                        user = UserDataResponse.UpdatedUser(
                            name = "",
                            email = "",
                            password = "",
                            currency = "",
                            referalCode = "",
                            role = ""
                        )
                    )
                )
            }

            var name by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            var openEditNameDialog by remember { mutableStateOf(false) }
            var openEditPassDialog by remember { mutableStateOf(false) }


            fun showMessageToUser(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

            fun editName(newName: String) {
                val request = UpdateUserRequest(
                    name = newName,
                    password = user.value.user.password
                )

                val call = apiService.updateUserData("Bearer $token", request)
                call.enqueue(object : Callback<UserDataResponse> {
                    override fun onResponse(
                        call: Call<UserDataResponse>,
                        response: Response<UserDataResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { responseBody ->
                                user.value = responseBody
                                showMessageToUser("Name updated successfully!")
                            }
                        } else {
                            val jsonObject = JSONObject(response.errorBody()?.string())
                            val errorMessage = jsonObject.optString("message", "An error occurred")
                            showMessageToUser(errorMessage)
                            Log.d("debug", "Editing name failed: ${jsonObject}")
                        }
                    }

                    override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                        showMessageToUser("Error: ${t.localizedMessage}")
                    }
                })
            }

            fun editPassword(newPassword: String){
                val request = UpdateUserRequest(
                    name = user.value.user.name,
                    password = newPassword
                )

                val call = apiService.updateUserData("Bearer $token", request)
                call.enqueue(object : Callback<UserDataResponse> {
                    override fun onResponse(
                        call: Call<UserDataResponse>,
                        response: Response<UserDataResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { responseBody ->
                                user.value = responseBody
                                showMessageToUser("Password updated successfully!")
                            }
                        } else {
                            val jsonObject = JSONObject(response.errorBody()?.string())
                            val errorMessage = jsonObject.optString("message", "An error occurred")
                            showMessageToUser(errorMessage)
                            Log.d("debug", "Editing password failed: ${jsonObject}")
                        }
                    }

                    override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                        showMessageToUser("Error: ${t.localizedMessage}")
                    }
                })
            }

            fun getUserData() {
                val call = apiService.getUserData("Bearer $token")
                call.enqueue(object : Callback<UserDataResponse> {
                    override fun onResponse(
                        call: Call<UserDataResponse>,
                        response: Response<UserDataResponse>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let { responseBody ->
                                user.value = responseBody
                            }
                        } else {
                            val jsonObject = JSONObject(response.errorBody()?.string())
                            val errorMessage = jsonObject.optString("message", "An error occurred")
                            showMessageToUser(errorMessage)
                            Log.d("debug", "Fetching user data failed: ${jsonObject}")
                        }
                    }

                    override fun onFailure(call: Call<UserDataResponse>, t: Throwable) {
                        showMessageToUser("Error: ${t.localizedMessage}")
                    }
                })
            }

            fun logOut(){
                val call = apiService.logoutUser("Bearer $token")
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.isSuccessful) {
                            userViewModel.setToken(" ")
                            showMessageToUser("User logouted successfully!")
                            logout()
                        } else {
                            showMessageToUser("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        showMessageToUser("Error: ${t.localizedMessage}")
                    }
                })
            }

            fun delete(){
                val call = apiService.deleteAccount("Bearer $token")
                call.enqueue(object : Callback<Void> {
                    override fun onResponse(
                        call: Call<Void>,
                        response: Response<Void>
                    ) {
                        if (response.isSuccessful) {
                            userViewModel.setToken(" ")
                            showMessageToUser("Account deleted successfully!")
                            deleted()
                        } else {
                            showMessageToUser("Error: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        showMessageToUser("Error: ${t.localizedMessage}")
                    }
                })
            }

            @Composable
            fun editNameDialog(){
                when {
                    // ...
                    openEditNameDialog -> {
                        name = ChangeValueDialog(
                        onDismissRequest = { openEditNameDialog = false },
                        label = "Зміна імені користувача",
                        placeholder = "Нове ім'я"
                        )
                    }
                }
            }

            @Composable
            fun editPassDialog(){
                when {
                    // ...
                    openEditPassDialog -> {
                        password = ChangeValueDialog(
                            onDismissRequest = { openEditPassDialog = false },
                            label = "Зміна паролю користувача",
                            placeholder = "Новий пароль"
                        )
                    }
                }
            }

            if( token != null ) {
                DisposableEffect(Unit) {
                    getUserData()
                    onDispose {}
                }

                Column(
                    modifier = Modifier.padding(40.dp, 100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = "Акаунт",
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(0.dp, 20.dp)
                            .fillMaxWidth(),
                        color = contentColor
                    )
                    Column {
                        Row(
                            modifier = infoRowModifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Ім'я", color = contentColorConst)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = user.value.user.name, color = contentColor)
                                IconButton(onClick = { openEditNameDialog = true }) {
                                    Icon(
                                        Icons.Filled.Edit,
                                        "Edit name",
                                        tint = contentColor)
                                }
                            }
                        }
                        if(openEditNameDialog) {
                            editNameDialog()
                        } else {
                            if(name != "") {
                                Log.d("debug", name)
                                editName(name)
                            }
                        }

                        HorizontalDivider(color = Color(0xFF222831))

                        Row(
                            modifier = infoRowModifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Пошта", color = contentColorConst)
                            Text(text = user.value.user.email, color = contentColor)
                        }
                        HorizontalDivider(color = Color(0xFF222831))

                        Row(
                            modifier = infoRowModifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Пароль", color = contentColorConst)
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = "3gsif9slf3", color = contentColor)
                                IconButton(onClick = { openEditPassDialog = true }) {
                                    Icon(
                                        Icons.Filled.Edit,
                                        "Edit password",
                                        tint = contentColor)
                                }
                            }
                            if(openEditPassDialog) {
                                editPassDialog()
                            } else {
                                if(password != "") {
                                    Log.d("debug", password)
//                                    editPassword(password)
                                }
                            }
                        }
                        HorizontalDivider(color = Color(0xFF222831))

                        Row(
                            modifier = infoRowModifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Основна валюта", color = contentColorConst)
                            Text(text = user.value.user.currency, color = contentColor)
                        }
                        HorizontalDivider(color = Color(0xFF222831))

                        Row(
                            modifier = infoRowModifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Реферальний код", color = contentColorConst)
                            Text(text = user.value.user.referalCode, color = contentColor)
                        }
                        HorizontalDivider(color = Color(0xFF222831))

                        Row(
                            modifier = infoRowModifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "Роль у групі", color = contentColorConst)
                            Text(text = user.value.user.role, color = contentColor)
                        }
                        HorizontalDivider(color = Color(0xFF222831))
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .absoluteOffset(y = 320.dp)
                            .fillMaxWidth()
                            .border(
                                2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            ),
                        onClick = { logOut() },
                        border = ButtonDefaults.outlinedButtonBorder(false)
                    ) {
                        Text(text = "Вийти з облікового запису".uppercase())
                    }

                    OutlinedButton(
                        modifier = Modifier
                            .absoluteOffset(y = 330.dp)
                            .fillMaxWidth()
                            .border(
                                2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(50)
                            ),
                        onClick = { delete() },
                        border = ButtonDefaults.outlinedButtonBorder(false)
                    ) {
                        Text(text = "Видалити акаунт".uppercase())
                    }
                }
            }
        }
    }

    return content

}
