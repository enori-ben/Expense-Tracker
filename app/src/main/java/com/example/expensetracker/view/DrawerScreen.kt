package com.example.expensetracker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.repository.Routes
import kotlinx.coroutines.launch

@Composable
fun DrawerScreen(
    onClose: () -> Unit,
    navController: NavController
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(280.dp)
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Header Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onClose,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE0E0E0))
                ) {
                    Icon(
                        painter = painterResource(R.drawable.me),
                        contentDescription = "Close",
                        tint = Color(0xFF333333)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "بن عتوس نورالدين",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF333333)
                    )
                )
            }

            Divider(color = Color(0xFFBDBDBD), thickness = 1.dp)

            // Menu Items
            DrawerItem(
                text = "الإعدادات",
                iconRes = R.drawable.me,
                onClick = { navController.navigate(Routes.SETTINGS_SCREEN) }
            )
            DrawerItem(
                text = "المساعدة",
                iconRes = R.drawable.me,
                onClick = { navController.navigate(Routes.HELP_SCREEN) }
            )
            DrawerItem(
                text = "عن التطبيق",
                iconRes = R.drawable.me,
                onClick = { navController.navigate(Routes.ABOUT_SCREEN) }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer Section
            Text(
                text = "Version 1.0.0",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun DrawerItem(
    text: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = { Text(text) },
        icon = { Icon(painterResource(iconRes), contentDescription = null) },
        selected = false,
        onClick = {
            onClick()
        },
        modifier = Modifier.padding(vertical = 4.dp))
}

@Composable
fun MainScreenWithDrawer(navController: NavController) {
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
        // Main content of the screen
        Column(modifier = Modifier.fillMaxSize()) {
            Button(
                onClick = { scope.launch { drawerState.open() } },
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Open Drawer")
            }
            // ...existing content...
        }
    }
}