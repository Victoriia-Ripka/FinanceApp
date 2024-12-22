package com.example.financeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.ui.sign_in_page.SignInScreen
import com.example.financeapp.ui.log_in_page.LogInScreen
import com.example.financeapp.ui.main_page.MainScreen
import com.example.financeapp.ui.theme.FinanceAppTheme

enum class Routes {
    REGISTRATION,
    AUTHORIZATION,
    MAIN_PAGE,
    ACCOUNT,
    ADD_RECORD,
    STATISTICS,
    COURSE,
    GROUP,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Routes.REGISTRATION.name,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(route = Routes.REGISTRATION.name) {
                            SignInScreen(
                                register = { navController.navigate(route = Routes.MAIN_PAGE.name) },
                                logInScreen = { navController.navigate(route = Routes.AUTHORIZATION.name) },
                            )
                        }
                        composable(route = Routes.AUTHORIZATION.name) {
                            LogInScreen(
                                authorizate = { navController.navigate(route = Routes.MAIN_PAGE.name )},
                                signInScreen = { navController.navigate(route = Routes.REGISTRATION.name )},
                            )
                        }
                        composable(route = Routes.MAIN_PAGE.name) {
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinanceAppTheme {
        Greeting("Android")
    }
}