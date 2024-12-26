package com.example.financeapp.ui.add_category_page

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.models.interfaces.Category
import com.example.financeapp.models.requests.PasswordRecoveryRequest
import com.example.financeapp.models.responses.MessageResponse
import com.example.financeapp.models.responses.PasswordRecoveryResponse
import com.example.financeapp.services.RetrofitClient
import com.example.financeapp.ui.theme.CustomTextField
import com.example.financeapp.ui.theme.CustomTextInknutAntiquaFont
import com.example.financeapp.viewmodel.UserViewModel
import org.json.JSONObject
import java.net.SocketTimeoutException


@Composable
fun AddCategoryContent(
    userViewModel: UserViewModel,
    addRecordPage: () -> Unit
): @Composable () -> Unit {
    val context = LocalContext.current
    val token by userViewModel.token.observeAsState()
    val apiService = RetrofitClient.apiService

    val title = remember { mutableStateOf("") }

    fun showMessageToUser(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun addCategory(){
        val request = Category(
            title = title.value
        )

        apiService.addCategory("Bearer $token", request).enqueue(object : retrofit2.Callback<MessageResponse>  {
            override fun onResponse(
                call: retrofit2.Call<MessageResponse>,
                response: retrofit2.Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    showMessageToUser("Category added successfully")
                    addRecordPage()
                } else {
                    val jsonObject = JSONObject(response.errorBody()?.string())
                    val errorMessage = jsonObject.optString("message", "An error occurred")
                    showMessageToUser(errorMessage)
                    Log.d("debug", "Category adding failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: retrofit2.Call<MessageResponse>, t: Throwable) {
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

    val content = @Composable {
        Box(modifier = Modifier
            .padding(top = 80.dp, start = 40.dp, end = 40.dp)
            .verticalScroll(rememberScrollState()))
        {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTextField(
                    "Назва категорії",
                    Modifier,
                    20.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                        .border(2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(25)),
                    border = ButtonDefaults.outlinedButtonBorder(false),
                    onClick = { addCategory() }
                ) {
                    CustomTextInknutAntiquaFont("Додати")
                }
            }
        }
    }

    return content
}