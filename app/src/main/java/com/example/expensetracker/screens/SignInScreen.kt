package com.example.expensetracker.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expensetracker.R
import com.example.expensetracker.repository.Routes
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Composable
fun SignInScreen(navController: NavController) {
    val auth = Firebase.auth
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    fun signIn() {
        if (email.isEmpty() || password.isEmpty()) {
            error = "Please fill all fields"
            return
        }

        loading = true
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                loading = false
                if (task.isSuccessful) {
                    navController.navigate(Routes.MAIN_SCREEN) { launchSingleTop = true }
                } else {
                    error = task.exception?.message ?: "Unknown error occurred"
                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header Image
        Image(
            painter = painterResource(id = R.drawable.top_image),
            contentDescription = "Sign In Header",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )

        // Main Content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .weight(1f, fill = false)
        ) {
            // Email Field
            ResponsiveTextField(
                value = email,
                onValueChange = { email = it },
                label = "Enter Email",
                iconRes = R.drawable.ic_email,
                keyboardType = KeyboardType.Email,

                )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            ResponsiveTextField(
                value = password,
                onValueChange = { password = it },
                label = "Enter Password",
                iconRes = R.drawable.ic_password,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                passwordVisible = passwordVisible,
                onPasswordToggle = { passwordVisible = !passwordVisible }
            )

            // Forgot Password
            Text(
                text = "Forgot password?",
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp)
                    .clickable { /* Handle forgot password */ },
                color = Color(0xFF523804),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sign In Button
            ResponsiveButton(
                onClick = { signIn()
                    navController.navigate(Routes.MAIN_SCREEN)
                },
                isLoading = loading,
                text = "Sign In"
            )

            error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 14.sp
                )
            }

            // Sign Up Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color(0xB5513700),
                    fontSize = 14.sp
                )

                Text(
                    text = "Sign Up",
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.SIGN_UP_SCREEN) { launchSingleTop = true }
                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            // Social Login Section
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DividerWithText("Or continue with")

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    SocialSignInButton(R.drawable.ic_facebook)
                    SocialSignInButton(R.drawable.ic_apple)
                    SocialSignInButton(R.drawable.ic_google)
                }
            }
        }
    }
}

// Reusable Components
@Composable
fun ResponsiveTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    @DrawableRes iconRes: Int,
    keyboardType: KeyboardType,
    isPassword: Boolean = false,
    passwordVisible: Boolean = false,
    onPasswordToggle: (() -> Unit)? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(text = label, fontWeight = FontWeight.SemiBold) },
        leadingIcon = {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Black                )
        },
        trailingIcon = {
            if (isPassword && onPasswordToggle != null) {
                IconButton(onClick = onPasswordToggle) {
                    Icon(
                        painter = painterResource(
                            id = if (passwordVisible) R.drawable.eyeopened
                            else R.drawable.eyeclosed
                        ),
                        contentDescription = null,
                        tint = Color.Black,


                        )
                }
            }
        },
        visualTransformation = if (isPassword && !passwordVisible) PasswordVisualTransformation()
        else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color(0xFF040401),
            unfocusedTextColor = Color(0xFF040401),
            focusedBorderColor = Color(0xFF040401),
            unfocusedBorderColor = Color(0xFF040401),
            focusedLabelColor = Color(0xFF5E562A),
            unfocusedLabelColor = Color(0xFF5E562A),
        )
    )
}

@Composable
fun ResponsiveButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    text: String
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Black,
            contentColor = Color.White
        )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White
            )
        } else {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DividerWithText(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(
            modifier = Modifier.weight(1f),
            color = Color.Black,
            thickness = 1.dp
        )

        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 8.dp),
            color = Color.Gray,
            fontSize = 14.sp
        )

        Divider(
            modifier = Modifier.weight(1f),
            color = Color.Black,
            thickness = 1.dp
        )
    }
}

@Composable
fun SocialSignInButton(@DrawableRes iconResId: Int) {
    IconButton(
        onClick = { /* Handle social login */ },
        modifier = Modifier
            .size(56.dp)
            .border(1.dp, Color.Black, CircleShape)
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
    }
}
@Composable
private fun SocialSignInButton(
    @DrawableRes iconResId: Int,
    onClick: () -> Unit = {}
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(48.dp)

    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = "Social Sign In",
            modifier = Modifier
                .size(34.dp)
                .padding(4.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(navController = rememberNavController())
}