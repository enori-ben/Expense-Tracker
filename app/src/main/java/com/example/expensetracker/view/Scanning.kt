package com.example.expensetracker.view

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetracker.repository.Routes
import com.example.expensetracker.view.mainscreen.MainViewModel


@Composable
fun Scanning(navController: NavController) {
    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        hasPermission = isGranted
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    BackHandler {
        navController.popBackStack()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        if (hasPermission) {
            // عرض الكاميرا
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("كاميرا المسح الضوئي")
                Button(
                    onClick = {
                        // Handle confirmation
                        navController.navigate(Routes.MAIN_SCREEN)
                    }
                ) {
                    Text("تأكيد المسح")
                }
            }
        } else {
            Text("يجب منح إذن الكاميرا", modifier = Modifier.align(Alignment.Center))
        }
    }
}