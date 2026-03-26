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
import com.google.firebase.firestore.FirebaseFirestore

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

    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()

    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
        )
    )

    Box(
        modifier = Modifier.fillMaxSize().background(gradient)
    ) {

        Column(
            modifier = Modifier.fillMaxSize().padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.bitemoji),
                contentDescription = null,
                modifier = Modifier.size(120.dp).align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(shape = RoundedCornerShape(20.dp)) {
                Column(Modifier.padding(16.dp)) {

                    Text("Create Account 💖", style = MaterialTheme.typography.headlineSmall)

                    OutlinedTextField(name, { name = it }, label = { Text("Name") })

                    OutlinedTextField(
                        email,
                        {
                            email = it
                            emailError =
                                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches())
                                    "Invalid email"
                                else ""
                        },
                        label = { Text("Email") },
                        isError = emailError.isNotEmpty()
                    )

                    if (emailError.isNotEmpty()) {
                        Text(emailError, color = MaterialTheme.colorScheme.error)
                    }

                    Text("Gender")

                    Row {
                        listOf("Male", "Female", "Other").forEach {
                            FilterChip(
                                selected = gender == it,
                                onClick = { gender = it },
                                label = { Text(it) }
                            )
                        }
                    }

                    OutlinedTextField(
                        password,
                        {
                            password = it
                            passwordError = validatePassword(it)
                        },
                        label = { Text("Password") },
                        isError = passwordError.isNotEmpty(),
                        visualTransformation = if (passwordVisible)
                            VisualTransformation.None
                        else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = null
                                )
                            }
                        }
                    )

                    if (passwordError.isNotEmpty()) {
                        Text(passwordError, color = MaterialTheme.colorScheme.error)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(onClick = {

                        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                            Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        auth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener { result ->

                                val userId = result.user?.uid!!

                                val userData = mapOf(
                                    "name" to name,
                                    "email" to email,
                                    "gender" to gender
                                )

                                db.collection("users")
                                    .document(userId)
                                    .set(userData)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Saved to Firestore ✅", Toast.LENGTH_SHORT).show()
                                        onSignupSuccess()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Firestore Error: ${it.message}", Toast.LENGTH_LONG).show()
                                    }

                            }
                            .addOnFailureListener {
                                Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                            }

                    }) {
                        Text("Signup")
                    }

                    TextButton(onClick = onNavigateToLogin) {
                        Text("Already have account? Login")
                    }
                }
            }
        }
    }
}

fun validatePassword(password: String): String {
    return when {
        password.length < 8 -> "Min 8 chars"
        !password.any { it.isUpperCase() } -> "1 uppercase required"
        !password.any { it.isDigit() } -> "1 number required"
        else -> ""
    }
}