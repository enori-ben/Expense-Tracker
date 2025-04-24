package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.expensetracker.repository.Routes
import com.example.expensetracker.screens.SignInScreen
import com.example.expensetracker.screens.SignUpScreen
import com.example.expensetracker.screens.Splashscreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.view.AboutScreen
import com.example.expensetracker.view.HelpScreen
<<<<<<< HEAD
import com.example.expensetracker.view.scan.Scanning
=======
>>>>>>> 242b99d42569f9ea70fc42011200e9663bb41699
import com.example.expensetracker.view.mainscreen.MainScreen
import com.example.expensetracker.view.ProfileScreen
import com.example.expensetracker.view.SettingScreen
import com.example.expensetracker.view.stats.StatsScreen
import com.example.expensetracker.view.transaction.AddTran
import com.example.expensetracker.view.transaction.TransactionViewModel
import com.example.policeplus.views.Scanning

class MainActivity : ComponentActivity() {
    private val transactionViewModel: TransactionViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ExpenseTrackerTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Routes.SPLASH_SCREEN
                ) {
                    composable(Routes.SPLASH_SCREEN) { Splashscreen(navController) }
                    composable(Routes.SIGN_IN_SCREEN) { SignInScreen(navController) }
                    composable(Routes.SIGN_UP_SCREEN) { SignUpScreen(navController) }
                    composable(Routes.MAIN_SCREEN) { MainScreen(navController, transactionViewModel) }
                    composable(Routes.PROFILE_SCREEN) { ProfileScreen(navController) }
                    composable(Routes.ADD_TRANSACTION) { AddTran(navController, transactionViewModel) }
                    composable(Routes.SCANNING_SCREEN) { Scanning(
<<<<<<< HEAD
                        onClose = { navController.popBackStack() },
                        onConfirm = { /* Handle confirmation */ },
                    )  }
=======
                            onClose = { navController.popBackStack() },
                        onConfirm = { /* Handle confirmation */ },
                        )  }
>>>>>>> 242b99d42569f9ea70fc42011200e9663bb41699
                    composable(Routes.STATS_SCREEN) { StatsScreen(navController,transactionViewModel) }
                    composable(Routes.SETTINGS_SCREEN) { SettingScreen(navController) }
                    composable(Routes.HELP_SCREEN) { HelpScreen(navController) }
                    composable(Routes.ABOUT_SCREEN) { AboutScreen(navController) }

                }
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun MainPreview() {
        ExpenseTrackerTheme {
            val navController = rememberNavController()
            Splashscreen(navController)
        }
    }
}
