package com.example.expensetracker.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.expensetracker.R
import com.example.expensetracker.repository.Routes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    val auth = Firebase.auth
    val currentUser = auth.currentUser
    var userName by remember { mutableStateOf("") }
    var userId by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        currentUser?.uid?.let { uid ->
            Firebase.firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        userName = document.getString("name") ?: ""
                        userId = document.getString("userId") ?: "N/A"
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

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when {
                loading -> FullScreenLoader()
                error != null -> ErrorSection(
                    errorMessage = error!!,
                    onRetry = { error = null; loading = true }
                )
                else -> ProfileContent(
                    userName = userName,
                    email = currentUser?.email ?: "",
                    userId = userId,
                    onEditProfile = { /* Navigate to edit screen */ },
                    onLogout = {
                        auth.signOut()
                        navController.navigate(Routes.SIGN_IN_SCREEN) {
                            popUpTo(Routes.MAIN_SCREEN) { inclusive = true }
                        }
                    },
                    onDeleteAccount = {
                        currentUser?.delete()?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                navController.navigate(Routes.SIGN_IN_SCREEN) {
                                    popUpTo(Routes.MAIN_SCREEN) { inclusive = true }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun FullScreenLoader() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ProfileContent(
    userName: String,
    email: String,
    userId: String,
    onEditProfile: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ProfileHeader(userName, email, userId)
        Spacer(modifier = Modifier.height(40.dp))
        ProfileActions(
            onEditProfile = onEditProfile,
            onLogout = onLogout,
            onDeleteAccount = onDeleteAccount
        )
    }
}

@Composable
private fun ProfileHeader(
    userName: String,
    email: String,
    userId: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .shadow(8.dp, CircleShape, ambientColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = userName.takeIf { it.isNotEmpty() }?.first()?.uppercase() ?: "?",
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = userName,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = email,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "ID: $userId",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun ProfileActions(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        FilledTonalButton(
            onClick = onEditProfile,
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Profile", modifier = Modifier.padding(4.dp))
        }

        OutlinedButton(
            onClick = { showLogoutDialog = true },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Logout")
        }

        TextButton(
            onClick = { showDeleteDialog = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Delete Account",
                color = MaterialTheme.colorScheme.error
            )
        }
    }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Exit Profile") },
            text = { Text("Are you sure you want to exit?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showLogoutDialog = false
                        onLogout()
                    }
                ) { Text("EXIT") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showLogoutDialog = false }
                ) { Text("CANCEL") }
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Profile") },
            text = {
                Text("Your profile and all data will be permanently deleted. This action cannot be undone.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDeleteAccount()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) { Text("DELETE") }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDeleteDialog = false }
                ) { Text("CANCEL") }
            }
        )
    }
}

@Composable
private fun ErrorSection(
    errorMessage: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Error",
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(48.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onRetry,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
        ) {
            Text("Try Again")
        }
    }
}