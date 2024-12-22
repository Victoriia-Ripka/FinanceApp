package com.example.financeapp.ui.main_page

//import android.graphics.Color
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.getValue
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
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
) {

    fun dothing() {
        println("do thing")
    }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    TopAppBar(
        modifier = Modifier.clip(RoundedCornerShape(bottomStartPercent = 50, bottomEndPercent = 50)),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "Finance",
                modifier = Modifier.padding(start = 200.dp).offset(y = (-10).dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis

            )
        },
        navigationIcon = {
            IconButton(
                onClick = { /* do something */ },
                modifier = Modifier.padding(start = 20.dp).offset(y = (-10).dp))
            {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu Button"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )

    Column(modifier = Modifier.padding(40.dp, 100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Text(
            text = "FINANCE",
            modifier = Modifier.padding(0.dp, 20.dp)
        )
        ElevatedCard(
            elevation = CardDefaults.cardElevation(
                defaultElevation = 6.dp
            ),
            modifier = Modifier.size(width = 350.dp, height = 215.dp),
            colors = CardColors(
                containerColor = Color(0xFF00ADB5),
                contentColor = Color(0xFFFFFFFF),
                disabledContainerColor = Color(0xFF00ADB5),
                disabledContentColor = Color(0xFFFFFFFF)
            ), )
        {
            Text(
                text = "Balance",
                modifier = Modifier
                    .padding(16.dp),
                textAlign = TextAlign.Center,
            )
        }
        val categories = remember { listOf("Розваги", "Іжа", "Погашення боргів", "Краса", "Заробітня плата") }
        LazyColumn (
            contentPadding = PaddingValues(top = 30.dp)
        )
        {
            items(5) { index ->
                val item = categories[index]
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
                    ), )
                {
                    Text(
                        text = item,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 10.dp),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }

    BottomAppBar(
        modifier = Modifier.absoluteOffset(y=786.dp),
        actions = {},
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* do something */ },
                shape = CircleShape,
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Filled.Add, "Localized description")
            }
        }
    )

}