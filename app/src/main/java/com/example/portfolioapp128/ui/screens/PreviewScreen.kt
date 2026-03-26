package com.example.portfolioapp128.ui.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun PreviewScreen() {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

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
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                text = data?.get("name").toString(),
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = data?.get("position").toString(),
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            Section("Skills", data?.get("skills").toString())

            SectionList("Education", data?.get("education") as? List<Map<String, Any>>)

            SectionList("Certificates", data?.get("certificates") as? List<Map<String, Any>>)

            SectionList("Internships", data?.get("internships") as? List<Map<String, Any>>)
        }
    }
}

@Composable
fun Section(title: String, content: String) {
    Text(title, style = MaterialTheme.typography.titleLarge)
    Text(content)
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun SectionList(title: String, list: List<Map<String, Any>>?) {

    Text(title, style = MaterialTheme.typography.titleLarge)

    list?.forEach {
        Card(
            modifier = Modifier.padding(vertical = 6.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(Modifier.padding(12.dp)) {
                it.forEach { (key, value) ->
                    Text("$key: $value")
                }
            }
        }
    }

    Spacer(modifier = Modifier.height(12.dp))
}