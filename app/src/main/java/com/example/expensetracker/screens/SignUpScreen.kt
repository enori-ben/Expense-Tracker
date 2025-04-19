package com.example.expensetracker.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun SignUpScreen(navController: NavController) {
    val auth = Firebase.auth
    val db = Firebase.firestore
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var loading by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var passwordVisible by remember { mutableStateOf(false) }

    fun signUp() {
        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            error = "Please fill all fields"
            return
        }

        loading = true
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "createdAt" to System.currentTimeMillis()
                    )

                    db.collection("users")
                        .document(auth.currentUser?.uid ?: "")
                        .set(user)
                        .addOnSuccessListener {
                            navController.navigate(Routes.MAIN_SCREEN) { launchSingleTop = true }
                            loading = false
                        }
                        .addOnFailureListener { e ->
                            error = "Failed to save user data: ${e.message}"
                            loading = false
                        }
                } else {
                    error = task.exception?.message ?: "Unknown error occurred"
                    loading = false
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
            painter = painterResource(id = R.drawable.up),
            contentDescription = "Sign Up Header",
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

            // Name Field
            ResponsiveTextField(
                value = name,
                onValueChange = { name = it },
                label = "Enter your name",
                iconRes = R.drawable.profile,
                keyboardType = KeyboardType.Text
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email Field
            ResponsiveTextField(
                value = email,
                onValueChange = { email = it },
                label = "Enter your email",
                iconRes = R.drawable.ic_email,
                keyboardType = KeyboardType.Email,


                )

            Spacer(modifier = Modifier.height(16.dp))

            // Password Field
            ResponsiveTextField(
                value = password,
                onValueChange = { password = it },
                label = "Enter your Password",
                iconRes = R.drawable.ic_password,
                keyboardType = KeyboardType.Password,
                isPassword = true,
                passwordVisible = passwordVisible,
                onPasswordToggle = { passwordVisible = !passwordVisible }
            )

            error?.let {
                Text(
                    text = it,
                    color = Color.Red,
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Button
            ResponsiveButton(
                onClick = { signUp() },
                isLoading = loading,
                text = "Sign Up"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Already have an account? ",
                    color = Color(0xB5513700),
                    fontSize = 14.sp
                )

                Text(
                    text = "Sign In",
                    modifier = Modifier.clickable {
                        navController.navigate(Routes.SIGN_IN_SCREEN) { launchSingleTop = true }
                    },
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

//            // Social Login Section
//            Column(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalAlignment = Alignment.CenterHorizontally
//            ) {
//                DividerWithText("Or continue with")
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(top = 16.dp),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    SocialSignInButton(R.drawable.ic_facebook)
//                    SocialSignInButton(R.drawable.ic_apple)
//                    SocialSignInButton(R.drawable.ic_google)
//                }
//            }
        }
    }


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
                    tint = Color.Black
                )
            },
            trailingIcon = {
                if (isPassword && onPasswordToggle != null) {
                    IconButton(onClick = onPasswordToggle) {
                        Icon(
                            painter = painterResource(
                                id = if (passwordVisible) R.drawable.eyeclosed
                                else R.drawable.eyeopened
                            ),
                            contentDescription = null,
                            tint = Color.Black
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

//    @Composable
//    fun DividerWithText(text: String) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Divider(
//                modifier = Modifier.weight(1f),
//                color = Color.Black,
//                thickness = 1.dp
//            )
//
//            Text(
//                text = text,
//                modifier = Modifier.padding(horizontal = 8.dp),
//                color = Color.Gray,
//                fontSize = 14.sp
//            )
//
//            Divider(
//                modifier = Modifier.weight(1f),
//                color = Color.Black,
//                thickness = 1.dp
//            )
//        }
//    }
//
//    @Composable
//    fun SocialSignInButton(@DrawableRes iconResId: Int) {
//        IconButton(
//            onClick = { /* Handle social login */ },
//            modifier = Modifier
//                .size(56.dp)
//        ) {
//            Image(
//                painter = painterResource(id = iconResId),
//                contentDescription = "Social Sign In",
//                modifier = Modifier.size(32.dp)
//            )
//        }
//    }
}


@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(navController = rememberNavController())
}