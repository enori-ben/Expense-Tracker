package com.example.expensetracker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.repository.Routes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@Composable
fun DrawerScreen(
    onClose: () -> Unit,
    navController: NavController,
    totalBalance: Double,
    drawerState: DrawerState, // إضافة المعلمة الجديدة
    scope: CoroutineScope // إضافة المعلمة الجديدة
) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var userName by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        currentUser?.uid?.let { uid ->
            try {
                val doc = withContext(Dispatchers.IO) {
                    Firebase.firestore.collection("users")
                        .document(uid)
                        .get()
                        .await()
                }

                if (doc.exists()) {
                    userName = doc.getString("name") ?: ""
                } else {
                    error = context.getString(R.string.user_data_not_found)
                }
            } catch (e: Exception) {
                error = context.getString(R.string.data_fetch_error, e.message)
            } finally {
                loading = false
            }
        } ?: run {
            error = context.getString(R.string.user_not_logged_in)
            loading = false
        }
    }

    ModalDrawerSheet(
        modifier = Modifier
            .width(320.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            when {
                loading -> DrawerLoading()
                error != null -> DrawerError(error!!)
                else -> UserHeader(
                    userName = userName,
                    userBalance = "%,.2f DZD".format(totalBalance),
                    onClose = onClose
                )
            }

            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Menu Items
            DrawerItem(
                text = stringResource(R.string.home),
                iconRes = R.drawable.home,
                onClick = {
                    navController.navigate(Routes.MAIN_SCREEN) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.startDestinationId)
                    }
                }
            )

            DrawerItem(
                text = stringResource(R.string.scan),
                iconRes = R.drawable.scanning,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        delay(200) // انتظر قليلاً لإغلاق الدراور قبل التنقل
                    }
                    navController.navigate(Routes.SCANNING_SCREEN) {
                        launchSingleTop = true
                        restoreState = true

                    }
                }
            )

            DrawerItem(
                text = stringResource(R.string.stats),
                iconRes = R.drawable.st,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        delay(200) // انتظر قليلاً لإغلاق الدراور قبل التنقل
                    }
                    navController.navigate(Routes.STATS_SCREEN) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            DrawerItem(
                text = stringResource(R.string.expense),
                iconRes = R.drawable.expense,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        delay(200) // انتظر قليلاً لإغلاق الدراور قبل التنقل
                    }
                    navController.navigate("add_transaction") {
                        launchSingleTop = true
                        restoreState = true

                    }
                }
            )


            DrawerItem(
                text = stringResource(R.string.income),
                iconRes = R.drawable.income,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        delay(200) // انتظر قليلاً لإغلاق الدراور قبل التنقل
                    }
                    navController.navigate("add_transaction") {
                        launchSingleTop = true
                        restoreState = true
                    }
                })

            DrawerItem(
                text = stringResource(R.string.about),
                iconRes = R.drawable.outline_info_24,
                onClick = {
                    scope.launch {
                        drawerState.close()
                        delay(200) // انتظر قليلاً لإغلاق الدراور قبل التنقل
                    }
                    navController.navigate(Routes.ABOUT_SCREEN) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer Section
            Text(
                text = stringResource(R.string.app_version),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(32.dp)
            )
        }
    }
}

@Composable
private fun UserHeader(
    userName: String,
    userBalance: String,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, start = 23.dp, top = 23.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = onClose,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Icon(
                painter = painterResource(R.drawable.profile),
                contentDescription = stringResource(R.string.close),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(modifier = Modifier.width(19.dp))
        Column {
            Text(
                text = userName,
                fontSize = 18.sp,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Balance: $userBalance",
                fontSize = 12.sp,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}

@Composable
private fun DrawerLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun DrawerError(errorMessage: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = stringResource(R.string.error),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun DrawerItem(
    text: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = text,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.inter_thin)),
                modifier = Modifier.padding(start = 8.dp)
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = iconRes),
                modifier = Modifier.size(30.dp),

                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        selected = false,
        onClick = onClick,
        modifier = Modifier.padding(vertical = 4.dp),
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.Transparent,
            unselectedTextColor = MaterialTheme.colorScheme.onSurface,
            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}
