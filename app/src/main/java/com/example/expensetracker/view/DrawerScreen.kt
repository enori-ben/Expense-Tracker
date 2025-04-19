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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.repository.Routes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun DrawerScreen(
    onClose: () -> Unit,
    navController: NavController
) {
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var userName by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        currentUser?.uid?.let { uid ->
            Firebase.firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userName = document.getString("name") ?: run {
                            error = context.getString(R.string.user_data_not_found)
                            ""
                        }
                    } else {
                        error = context.getString(R.string.user_data_not_found)
                    }
                    loading = false
                }
                .addOnFailureListener { e ->
                    error = context.getString(R.string.data_fetch_error, e.message)
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
        Column(
            modifier = Modifier
                .fillMaxSize()

        ) {
            // Header Section
            when {
                loading -> DrawerLoading()
                error != null -> DrawerError(error!!)
                else -> UserHeader(userName, onClose)
            }

            Divider(
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                thickness = 2.dp,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Menu Items
            DrawerItem(
                text = stringResource(R.string.settings),
                iconRes = R.drawable.me,
//                ic_settings
                onClick = { navController.navigate(Routes.SETTINGS_SCREEN) }
            )
            DrawerItem(
                text = stringResource(R.string.help),
                iconRes = R.drawable.me,
//                ic_help
                onClick = { navController.navigate(Routes.HELP_SCREEN) }
            )
            DrawerItem(
                text = stringResource(R.string.about),
                iconRes = R.drawable.me,
//                ic_info
                onClick = { navController.navigate(Routes.ABOUT_SCREEN) }
            )

            Spacer(modifier = Modifier.weight(1f))

            // Footer Section
            Text(
                text = stringResource(R.string.app_version),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun UserHeader(
    userName: String,
    onClose: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp, start = 23.dp, top = 23.dp
            ),
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
//                ic_close
                contentDescription = stringResource(R.string.close),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = userName,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )
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
        label = { Text(text) },
        icon = {
            Icon(
                painter = painterResource(id = iconRes),
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