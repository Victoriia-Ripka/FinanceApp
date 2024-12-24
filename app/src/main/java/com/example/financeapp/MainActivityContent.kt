package com.example.financeapp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financeapp.ui.Drawer
import com.example.financeapp.ui.account_page.AccountContent
import com.example.financeapp.ui.account_page.AccountScreen
import com.example.financeapp.ui.add_record_page.AddRecordContent
import com.example.financeapp.ui.log_in_page.LogInScreen
import com.example.financeapp.ui.main_page.MainContent
import com.example.financeapp.ui.main_page.MainScreen
import com.example.financeapp.ui.sign_in_page.SignInScreen
import com.example.financeapp.ui.theme.FinanceAppTheme
import com.example.financeapp.viewmodel.UserViewModel

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

@Composable
fun MainActivityContent() {
    val userViewModel: UserViewModel = viewModel()

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
                        userViewModel = userViewModel
                    )
                }
                composable(route = Routes.AUTHORIZATION.name) {
                    LogInScreen(
                        authorizate = { navController.navigate(route = Routes.MAIN_PAGE.name )},
                        signInScreen = { navController.navigate(route = Routes.REGISTRATION.name )},
                        userViewModel = userViewModel
                    )
                }
                composable(route = Routes.MAIN_PAGE.name) {
                    val content = MainContent(userViewModel = userViewModel)
                    Drawer(content, navController)
                }
                composable(route = Routes.ACCOUNT.name) {
//                    AccountScreen(
//                        logout = { navController.navigate(route = Routes.REGISTRATION.name )},
//                        userViewModel = userViewModel
//                    )
                    val content = AccountContent(userViewModel = userViewModel)
                    Drawer(content, navController)
                }
                composable(route = Routes.ADD_RECORD.name) {
                    val content = AddRecordContent()
                    Drawer(content, navController)
                }
            }
        }
    }
}
