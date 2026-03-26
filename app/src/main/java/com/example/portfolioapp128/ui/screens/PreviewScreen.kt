package com.example.portfolioapp128.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PreviewScreen(navController: NavController) {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val auth = FirebaseAuth.getInstance()
    val userId = auth.currentUser?.uid

    var data by remember { mutableStateOf<Map<String, Any>?>(null) }

    LaunchedEffect(true) {
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener {
                    data = it.data
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error loading data", Toast.LENGTH_SHORT).show()
                }
        }
    }

    if (data == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(data?.get("name").toString(), style = MaterialTheme.typography.headlineLarge)
            Text(data?.get("position").toString())

            Spacer(modifier = Modifier.height(16.dp))

            Text("Skills: ${data?.get("skills")}")

            Spacer(modifier = Modifier.height(16.dp))

            Text("Education", style = MaterialTheme.typography.titleLarge)
            (data?.get("education") as? List<Map<String, Any>>)?.forEach {
                Text(it.toString())
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Certificates", style = MaterialTheme.typography.titleLarge)
            (data?.get("certificates") as? List<Map<String, Any>>)?.forEach {
                Text(it.toString())
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text("Internships", style = MaterialTheme.typography.titleLarge)
            (data?.get("internships") as? List<Map<String, Any>>)?.forEach {
                Text(it.toString())
            }

            Spacer(modifier = Modifier.height(20.dp))

            // 🔥 EDIT BUTTON
            Button(
                onClick = {
                    navController.navigate("home")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Edit Details ✏️")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔥 FEEDBACK BUTTON
            Button(
                onClick = {
                    navController.navigate("feedback")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Give Feedback 💬")
            }

            Spacer(modifier = Modifier.height(8.dp))

            // 🔥 LOGOUT BUTTON
            Button(
                onClick = {
                    auth.signOut()
                    Toast.makeText(context, "Logged out", Toast.LENGTH_SHORT).show()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("Logout 🚪")
            }
        }
    }
}