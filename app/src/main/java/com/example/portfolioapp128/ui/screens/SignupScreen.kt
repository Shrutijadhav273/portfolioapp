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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
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
    var gender by remember { mutableStateOf("Select Gender") }
    var expanded by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

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

            // Bitmoji Image (add in drawable)
            Image(
                painter = painterResource(id = R.drawable.bitmoji),
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

                    OutlinedTextField(name, { name = it }, label = { Text("Name") })

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(email, { email = it }, label = { Text("Email") })

                    Spacer(modifier = Modifier.height(8.dp))

                    // Gender Dropdown
                    Box {
                        Button(onClick = { expanded = true }) {
                            Text(gender)
                        }

                        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                            listOf("Male", "Female", "Other").forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        gender = it
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Password with toggle
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = ""
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {

                            // VALIDATION
                            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                                Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (!email.contains("@") || !email.contains(".")) {
                                Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (password.length < 6) {
                                Toast.makeText(context, "Password must be 6+ chars", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            if (gender == "Select Gender") {
                                Toast.makeText(context, "Select Gender", Toast.LENGTH_SHORT).show()
                                return@Button
                            }

                            // FIREBASE AUTH
                            auth.createUserWithEmailAndPassword(email, password)
                                .addOnSuccessListener { result ->

                                    val userId = result.user?.uid

                                    val userMap = hashMapOf(
                                        "name" to name,
                                        "email" to email,
                                        "gender" to gender
                                    )

                                    // SAVE TO FIRESTORE
                                    if (userId != null) {
                                        db.collection("users")
                                            .document(userId)
                                            .set(userMap)
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