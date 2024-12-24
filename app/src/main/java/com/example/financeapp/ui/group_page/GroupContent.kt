package com.example.financeapp.ui.group_page


import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.models.requests.UpdateUserRequest
import com.example.financeapp.models.responses.GroupResponse
import com.example.financeapp.models.responses.UserDataResponse
import com.example.financeapp.services.RetrofitClient
import com.example.financeapp.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
fun GroupContent(
    userViewModel: UserViewModel
): @Composable () -> Unit {

    val infoRowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val content = @Composable {
        Box() {
            val context = LocalContext.current
            val token by userViewModel.token.observeAsState()
            val apiService = RetrofitClient.apiService

            val group = remember {
                mutableStateOf(
                    GroupResponse(
                        referalCode = "",
                        currency = "",
                        users = listOf(
                            GroupResponse.User(
                                _id = "",
                                name = "",
                                email = "",
                                role = ""
                            )
                        )
                    )
                )
            }

            fun showMessageToUser(message: String) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }

//            fun delete(){
//                val call = apiService.deleteAccount("Bearer $token")
//                call.enqueue(object : Callback<Void> {
//                    override fun onResponse(
//                        call: Call<Void>,
//                        response: Response<Void>
//                    ) {
//                        if (response.isSuccessful) {
//                            userViewModel.setToken(" ")
//                            showMessageToUser("Account deleted successfully!")
//                            deleted()
//                        } else {
//                            showMessageToUser("Error: ${response.message()}")
//                        }
//                    }
//
//                    override fun onFailure(call: Call<Void>, t: Throwable) {
//                        showMessageToUser("Error: ${t.localizedMessage}")
//                    }
//                })
//            }

            if( token != null ) {
                DisposableEffect(Unit) {
                    val call = apiService.getGroupUsers("Bearer $token")
                    call.enqueue(object : Callback<GroupResponse> {
                        override fun onResponse(
                            call: Call<GroupResponse>,
                            response: Response<GroupResponse>
                        ) {
                            if (response.isSuccessful) {
                                response.body()?.let { responseBody ->
                                    group.value = responseBody
                                    Log.d("debug", "Group init: ${group.value}")
                                }
                            } else {
                                showMessageToUser("Error: ${response.message()}")
                            }
                        }

                        override fun onFailure(call: Call<GroupResponse>, t: Throwable) {
                            showMessageToUser("Error: ${t.localizedMessage}")
                        }
                    })
                    onDispose {
                        call.cancel()
                    }
                }

                Column(
                    modifier = Modifier.padding(40.dp, 100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                )
                {
                    Text(
                        text = "Група ${group.value.referalCode}",
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        modifier = Modifier
                            .padding(0.dp, 20.dp)
                            .fillMaxWidth()
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(top = 30.dp)
                    )
                    {
                        items(group.value.users.size) { index ->
                            val item = group.value.users[index]
                            ElevatedCard(
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 6.dp
                                ),
                                modifier = Modifier
                                    .size(width = 350.dp, height = 57.dp)
                                    .padding(bottom = 10.dp)
                                    .clip(RoundedCornerShape(50)),
                                colors = CardColors(
                                    containerColor = Color(0xFF222831),
                                    contentColor = Color(0xFFFFFFFF),
                                    disabledContainerColor = Color(0xFF222831),
                                    disabledContentColor = Color(0xFFFFFFFF)
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxSize(),
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = item.name,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center,
                                    )
                                    Text(
                                        text = item.email,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center,
                                    )
                                    Text (
                                        text = item.role,
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    return content

}