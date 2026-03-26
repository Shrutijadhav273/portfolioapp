package com.example.portfolioapp128.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun FeedbackScreen(navController: NavController) {

    var feedback by remember { mutableStateOf("") }
    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Your Feedback 💬", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = feedback,
            onValueChange = { feedback = it },
            label = { Text("Write your feedback") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {

                if (feedback.isEmpty()) {
                    Toast.makeText(context, "Write something first", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val data = mapOf(
                    "feedback" to feedback,
                    "time" to System.currentTimeMillis()
                )

                db.collection("feedback")
                    .add(data)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Thanks for feedback ❤️", Toast.LENGTH_SHORT).show()
                        navController.popBackStack()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Submit Feedback")
        }
    }
}