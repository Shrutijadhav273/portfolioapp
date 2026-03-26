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
fun LoginScreen(
    onNavigateToSignup: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Welcome Back 💙", style = MaterialTheme.typography.headlineMedium)

        OutlinedTextField(email, { email = it }, label = { Text("Email") })
        OutlinedTextField(password, { password = it }, label = { Text("Password") })

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    Toast.makeText(context, "Login Success ✅", Toast.LENGTH_SHORT).show()
                    onLoginSuccess()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

        }) {
            Text("Login")
        }

        TextButton(onClick = onNavigateToSignup) {
            Text("Signup instead")
        }
    }
}