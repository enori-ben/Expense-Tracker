package com.example.expensetracker.view.mainscreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.navigation.NavItem
import com.example.expensetracker.view.DrawerScreen
import com.example.expensetracker.view.HomeScreen
import com.example.expensetracker.view.Scanning
import com.example.expensetracker.view.StatsScreen
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(navController: NavController) {
    val viewModel: MainViewModel = viewModel()
    val navItems = listOf(
        NavItem("Home", painterResource(R.drawable.home)),
        NavItem("Scan", painterResource(R.drawable.scanning)),
        NavItem("Stats", painterResource(R.drawable.st))
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerScreen(
                onClose = { scope.launch { drawerState.close() } },
                navController = navController
            )
        }
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFFF5F5F5),
            bottomBar = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .drawBehind {
                            drawLine(
                                color = Color.Gray.copy(alpha = 0.6f),
                                start = Offset.Zero,
                                end = Offset(size.width, 0f),
                                strokeWidth = 10f
                            )
                        }
                ) {
                    NavigationBar(containerColor = Color.Black) {
                        navItems.forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = viewModel.selectedIndex == index,
                                onClick = { viewModel.updateIndex(index) },
                                icon = {
                                    Icon(
                                        painter = item.icon,
                                        contentDescription = item.label,
                                        modifier = Modifier.size(38.dp),
                                        tint = if (viewModel.selectedIndex == index)
                                        Color(0xFFFFBB0C) else Color.Gray
                                    )
                                }

                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            when (viewModel.selectedIndex) {
                0 -> HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    navController = navController,
                    viewModel = viewModel(),
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
                1 -> Scanning(navController)
                2 -> StatsScreen(navController)
            }
        }
    }
}



//        Scaffold(
//            bottomBar = {
//                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
//                    navItems.forEachIndexed { index, item ->
//                        NavigationBarItem(
//                            selected = viewModel.selectedIndex == index,
//                         onClick = { viewModel.updateIndex(index) },
//                            icon = {
//                                Icon(
//                                    painter = item.icon,
//                                    contentDescription = item.label,
//                                    tint = if (viewModel.selectedIndex == index)
//                                        Color(0xFFFFBB0C) else Color.Gray
//                                )
//                            }
//                        )
//                    }
//                }
//            }
//        ) { innerPadding ->
//            when (viewModel.selectedIndex) {
//                0 -> HomeScreen(
//                    modifier = Modifier.padding(innerPadding),
//                    navController = navController,
//                    onMenuClick = { scope.launch { drawerState.open() } }
//                )
//                1 -> Scanning(navController)
//                2 -> StatsScreen(navController)
//            }
//        }
//    }
//}