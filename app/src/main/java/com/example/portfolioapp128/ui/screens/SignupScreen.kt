package com.example.portfolioapp128.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.example.portfolioapp128.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

@Composable
fun SignupScreen(
    onSignupSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Error states
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    var genderError by remember { mutableStateOf("") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("users")

    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.bitemoji),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {

                    Text("Create Account 💖💙", style = MaterialTheme.typography.headlineSmall)

                    Spacer(modifier = Modifier.height(12.dp))

                    // NAME
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        label = { Text("Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // EMAIL
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches())
                                "Invalid email format"
                            else ""
                        },
                        label = { Text("Email") },
                        isError = emailError.isNotEmpty(),
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (emailError.isNotEmpty()) {
                        Text(emailError, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // 🔥 GENDER CHIPS (Better UI)
                    Text("Select Gender")

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("Male", "Female", "Other").forEach {
                            FilterChip(
                                selected = gender == it,
                                onClick = {
                                    gender = it
                                    genderError = ""
                                },
                                label = { Text(it) }
                            )
                        }
                    }

                    if (genderError.isNotEmpty()) {
                        Text(genderError, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // PASSWORD
                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = validatePassword(it)
                        },
                        label = { Text("Password") },
                        isError = passwordError.isNotEmpty(),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible)
                                        Icons.Default.Visibility
                                    else
                                        Icons.Default.VisibilityOff,
                                    contentDescription = ""
                                )
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (passwordError.isNotEmpty()) {
                        Text(passwordError, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {

                            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                                Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (emailError.isNotEmpty() || passwordError.isNotEmpty()) {
                                Toast.makeText(context, "Fix errors first", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (gender.isEmpty()) {
                                genderError = "Please select gender"
                                return@Button
                            }

                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener { result ->

                                    val userId = result.user?.uid

                                    val userMap = mapOf(
                                        "name" to name,
                                        "email" to email,
                                        "gender" to gender
                                    )

                                    if (userId != null) {
                                        reference.child(userId).setValue(userMap)
                                    }

                                    Toast.makeText(context, "Account Created 🎉", Toast.LENGTH_SHORT).show()
                                    onSignupSuccess()
                                }
                                .addOnFailureListener {
                                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                                }

                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Signup")
                    }

                    TextButton(onClick = onNavigateToLogin) {
                        Text("Already registered? Login 💙")
                    }
                }
            }
        }
    }
}

// 🔐 PASSWORD VALIDATION FUNCTION
fun validatePassword(password: String): String {
    return when {
        password.length < 8 -> "Minimum 8 characters required"
        !password.any { it.isUpperCase() } -> "Must contain 1 uppercase letter"
        !password.any { it.isDigit() } -> "Must contain 1 number"
        else -> ""
    }
}