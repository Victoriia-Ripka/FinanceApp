package com.example.financeapp.ui.account_page


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.financeapp.ui.Drawer
import kotlinx.coroutines.launch
import kotlin.random.Random


fun AccountContent(): @Composable () -> Unit {

    val infoRowModifier = Modifier
        .fillMaxWidth()
        .height(40.dp)

    val content = @Composable{
        Box() {
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
                        .fillMaxWidth()
                )
                Column {
                    Row (
                        modifier = infoRowModifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "Ім'я")
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = "Some name")
                                IconButton(onClick = { edit_name() }) {
                                    Icon(Icons.Filled.Edit, "Edit name")
                            }
                        }
                    }
                    HorizontalDivider(color = Color(0xFF222831))

                    Row (
                        modifier = infoRowModifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "Пошта")
                        Text(text = "example@gmail.com")
                    }
                    HorizontalDivider(color = Color(0xFF222831))

                    Row (
                        modifier = infoRowModifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "Основна валюта")
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(text = "UAH")
                            IconButton(onClick = { edit_currency() }) {
                                Icon(Icons.Filled.Edit, "Edit currency")
                            }
                        }
                    }
                    HorizontalDivider(color = Color(0xFF222831))

                    Row (
                        modifier = infoRowModifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "Реферальний код")
                        Text(text = "AHF-20576")
                    }
                    HorizontalDivider(color = Color(0xFF222831))

                    Row (
                        modifier = infoRowModifier,
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "Роль у групі")
                        Text(text = "Адміністартор")
                    }
                    HorizontalDivider(color = Color(0xFF222831))
                }

                OutlinedButton(
                    modifier = Modifier
                        .absoluteOffset(y=390.dp)
                        .fillMaxWidth()
                        .border(2.dp, color = MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(50)),
                    onClick = { log_out() },
                    border = ButtonDefaults.outlinedButtonBorder(false)
                ) {
                    Text(text = "Вийти з акаунта")
                }
            }

        }
    }

    return content

}

fun edit_name(){
    println("Edit name btn clicked!")
}

fun edit_currency(){
    println("Edit currency btn clicked!")
}

fun log_out(){
    println("User Logs Out!")
}