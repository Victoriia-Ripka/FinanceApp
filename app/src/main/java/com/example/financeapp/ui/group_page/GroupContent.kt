package com.example.financeapp.ui.group_page


import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.models.requests.UpdateUserRequest
import com.example.financeapp.models.responses.UserDataResponse
import com.example.financeapp.services.RetrofitClient
import com.example.financeapp.viewmodel.UserViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@OptIn(ExperimentalMaterial3Api::class)
fun GroupContent(
    userViewModel: UserViewModel,
): @Composable () -> Unit {

    val infoRowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)
        .padding(top = 10.dp)

    val groupName = "AHF-20576"
    val groupUsers = listOf(
        GroupUsers("Name 1", "Адміністартор", false),
        GroupUsers("Name 2", "Користувач", true),
        GroupUsers("Name 3", "Користувач", true),
    )


    val content = @Composable{
        Box() {

            val contentColor = MaterialTheme.colorScheme.onSecondary
            val contentColorConst = MaterialTheme.colorScheme.primary

            Column(
                modifier = Modifier.padding(top = 80.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            color = contentColor,
                            fontSize = 32.sp)
                        ) {
                            append("Група")
                        }

                        append(" ")

                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            color = contentColor,
                            fontSize = 32.sp)
                        ) {
                            append(groupName)
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp, 20.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.size(20.dp))

                Column {
                    groupUsers.forEach{ user ->
                        Row(
                            modifier = infoRowModifier,
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(horizontal = 20.dp),
                                text = user.name,
                                color = contentColorConst)
                            Row(
                                modifier = Modifier
                                    .weight(0.5f)
                                    .padding(horizontal = 20.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = user.role, color = contentColor)
                                if(user.deletable){
                                    IconButton(onClick = { /* delete user function */}) {
                                        Icon(
                                            Icons.Filled.Delete,
                                            "Delete user",
                                            tint = contentColor)
                                    }
                                }
                            }
                        }
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 6.dp),
                            color = Color(0xFF222831),
                            thickness = 2.dp)
                    }
//
//                    Row(
//                        modifier = infoRowModifier,
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                    ) {
//                        Text(text = "Name 1", color = contentColorConst)
//                        Text(text = "Адміністартор", color = contentColor)
//                    }
//                    HorizontalDivider(color = Color(0xFF222831))
//
//                    Row(
//                        modifier = infoRowModifier,
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                    ) {
//                        Text(text = "Name 2", color = contentColorConst)
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                        ) {
//                            Text(text = "Користувач", color = contentColor)
//                            IconButton(onClick = { /* delete user function */}) {
//                                Icon(
//                                    Icons.Filled.Delete,
//                                    "Delete user",
//                                    tint = contentColor)
//                            }
//                        }
//                    }
//                    HorizontalDivider(color = Color(0xFF222831))

                }
            }
        }
    }
    return content
}


data class GroupUsers(
    val name: String,
    val role: String,
    val deletable: Boolean
)