package com.example.financeapp.ui.course_page


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.financeapp.models.responses.UserDataResponse
import com.example.financeapp.services.RetrofitClient
import com.example.financeapp.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun CourseInfoContent(
    userViewModel: UserViewModel
): @Composable () -> Unit {

    val infoRowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(top = 10.dp)

    val courseInfo = listOf(
                            CourseInfo("USD", "41.0500", "41.4852"),
                            CourseInfo("EUR", "44.4200", "45.0349"),
                            CourseInfo("EUR/USD", "1.0760", "1.0870")
                            )

    val content = @Composable{
        Box() {

            val contentColor = MaterialTheme.colorScheme.onSecondary
            val contentColorConst = MaterialTheme.colorScheme.secondary

            Column(
                modifier = Modifier.padding(top = 60.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Основні курси валют",
                    textAlign = TextAlign.Center,
                    fontSize = 32.sp,
                    modifier = Modifier
                        .padding(0.dp, 20.dp)
                        .fillMaxWidth(),
                    color = contentColor
                )
                Column() {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clip(shape = RoundedCornerShape(50))
                            .background(color = MaterialTheme.colorScheme.primaryContainer),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                            text = "Валюта",
                            color = contentColorConst)
                        Row(
                            modifier = Modifier
                                .weight(0.5f)
                                .padding(horizontal = 20.dp, vertical = 20.dp),
                        ) {
                            Text(
                                modifier = Modifier.padding(end = 14.dp),
                                text = "Купівля",
                                color = contentColorConst
                            )
                            Text(text = "Продаж", color = contentColorConst)
                        }
                    }

                    Spacer(Modifier.size(20.dp))

                    Column {
                        courseInfo.forEach{ currency ->
                            Row(
                                modifier = infoRowModifier,
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(horizontal = 20.dp),
                                    text = currency.title,
                                    color = contentColor,
                                    fontSize = 26.sp)
                                Row(
                                    modifier = Modifier
                                        .weight(0.5f)
                                        .padding(horizontal = 20.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .weight(0.5f)
                                            .padding(end = 14.dp),
                                        text = currency.buy,
                                        color = contentColor)
                                    Text(
//                                    modifier = Modifier.padding(start = 5.dp, end = 10.dp),
                                        text = currency.sell,
                                        color = contentColor)
                                }
                            }
                            HorizontalDivider(
                                modifier = Modifier.padding(top = 14.dp),
                                color = Color(0xFF222831),
                                thickness = 2.dp)
                        }
                    }
                }
            }
        }
    }

    return content

}

data class CourseInfo(
    val title: String,
    val buy: String,
    val sell: String
)