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
import com.example.financeapp.ui.add_record_page.AddRecordContent
import com.example.financeapp.ui.log_in_page.LogInScreen
import com.example.financeapp.ui.main_page.MainContent
import com.example.financeapp.ui.sign_in_page.SignInScreen
import com.example.financeapp.ui.password_recovery_page.PasswordRecoveryScreen
import com.example.financeapp.ui.theme.FinanceAppTheme
import com.example.financeapp.viewmodel.UserViewModel

enum class Routes {
    REGISTRATION, // check
    AUTHORIZATION, // check
    PASSWORD_RECOVERY, // I
    MAIN_PAGE,
    ACCOUNT, // check
    GROUP, // I
    ADD_RECORD,
    STATISTICS,
    COURSE,
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
                        passwordRecoveryScreen = { navController.navigate(route = Routes.PASSWORD_RECOVERY.name )},
                        userViewModel = userViewModel
                    )
                }
                composable(route = Routes.PASSWORD_RECOVERY.name) {
                    PasswordRecoveryScreen(
                        redirect = { navController.navigate(route = Routes.MAIN_PAGE.name )},
                        logInScreen = { navController.navigate(route = Routes.AUTHORIZATION.name) },
                        signInScreen = { navController.navigate(route = Routes.REGISTRATION.name )},
                        userViewModel = userViewModel
                    )
                }
                composable(route = Routes.MAIN_PAGE.name) {
                    val content = MainContent(userViewModel = userViewModel)
                    Drawer(content, navController)
                }
                composable(route = Routes.ACCOUNT.name) {
                    val content = AccountContent(
                        logout = { navController.navigate(route = Routes.AUTHORIZATION.name )},
                        deleted = { navController.navigate(route = Routes.REGISTRATION.name )},
                        userViewModel = userViewModel
                    )
                    Drawer(content, navController)
                }
                composable(route = Routes.ADD_RECORD.name) {
                    val content = AddRecordContent(userViewModel = userViewModel)
                    Drawer(content, navController)
                }
            }
        }
    }
}
