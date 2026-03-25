package com.example.portfolioapp128.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

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

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val genderOptions = listOf("Male", "Female", "Other")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Create Account 💖💙", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Name
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Gender Dropdown
        Box {
            Button(onClick = { expanded = true }) {
                Text(gender)
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                genderOptions.forEach {
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

        // Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            // VALIDATIONS
            if (name.isEmpty()) {
                Toast.makeText(context, "Enter Name", Toast.LENGTH_SHORT).show()
                return@Button
            }

            if (!email.contains("@") || !email.contains(".")) {
                Toast.makeText(context, "Invalid Email", Toast.LENGTH_SHORT).show()
                return@Button
            }

            if (gender == "Select Gender") {
                Toast.makeText(context, "Select Gender", Toast.LENGTH_SHORT).show()
                return@Button
            }

            if (password.length < 6) {
                Toast.makeText(context, "Password must be 6+ chars", Toast.LENGTH_SHORT).show()
                return@Button
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(context, "Account Created 🎉", Toast.LENGTH_SHORT).show()
                    onSignupSuccess()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

        }) {
            Text("Signup")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = onNavigateToLogin) {
            Text("Already registered? Login 💙")
        }
    }
}