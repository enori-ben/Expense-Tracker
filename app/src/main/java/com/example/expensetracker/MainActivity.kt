package com.example.expensetracker

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.repository.Routes
import com.example.expensetracker.repository.Routes.HOME_SCREEN
import com.example.expensetracker.screens.SignInScreen
import com.example.expensetracker.screens.SignUpScreen
import com.example.expensetracker.screens.Splashscreen
import com.example.expensetracker.ui.theme.ExpenseTrackerTheme
import com.example.expensetracker.view.HomeScreen
import com.example.expensetracker.view.transaction.AddTran
import com.example.expensetracker.view.Scanning
import com.example.expensetracker.view.mainscreen.MainScreen
import com.example.expensetracker.view.ProfileScreen
import com.example.expensetracker.view.StatsScreen
import com.example.expensetracker.view.transaction.CreateCategoryScreen

class MainActivity : ComponentActivity() {
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
                    composable(Routes.MAIN_SCREEN) { MainScreen(navController) }
                    composable(Routes.PROFILE_SCREEN) { ProfileScreen(navController) }
                    composable(Routes.ADD_TRANSACTION) { AddTran(navController) }
                    composable(Routes.HOME_SCREEN) { HomeScreen(navController) }
                    composable(Routes.SCANNING_SCREEN) { Scanning(navController) }
                    composable(Routes.STATS_SCREEN) { StatsScreen(navController) }
//                    composable(Routes.SETTINGS_SCREEN) { SettingsScreen(navController) }
//                    composable(Routes.HELP_SCREEN) { HelpScreen(navController) }
//                    composable(Routes.ABOUT_SCREEN) { AboutScreen(navController) }
                    composable(Routes.CREATE_CATEGORY) { CreateCategoryScreen(navController) }

                    // ... داخل NavHost الثاني



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