package com.example.portfolioapp128.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// 🔹 Data Classes
data class Education(
    var className: String = "",
    var college: String = "",
    var year: String = ""
)

data class Certificate(
    var course: String = "",
    var date: String = "",
    var marks: String = ""
)

data class Internship(
    var company: String = "",
    var position: String = "",
    var duration: String = ""
)

@Composable
fun HomeScreen() {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }

    var educationList by remember { mutableStateOf(listOf(Education())) }
    var certificateList by remember { mutableStateOf(listOf(Certificate())) }
    var internshipList by remember { mutableStateOf(listOf(Internship())) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA))
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        Text("Build Your Portfolio", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 BASIC INFO
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = position,
                    onValueChange = { position = it },
                    label = { Text("Position") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it },
                    label = { Text("Skills") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 EDUCATION
        Text("Education", style = MaterialTheme.typography.titleLarge)

        educationList.forEachIndexed { index, edu ->
            Card(modifier = Modifier.padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {

                    OutlinedTextField(
                        value = edu.className,
                        onValueChange = { value ->
                            val list = educationList.toMutableList()
                            list[index] = edu.copy(className = value)
                            educationList = list
                        },
                        label = { Text("Class") }
                    )

                    OutlinedTextField(
                        value = edu.college,
                        onValueChange = { value ->
                            val list = educationList.toMutableList()
                            list[index] = edu.copy(college = value)
                            educationList = list
                        },
                        label = { Text("College Name") }
                    )

                    OutlinedTextField(
                        value = edu.year,
                        onValueChange = { value ->
                            val list = educationList.toMutableList()
                            list[index] = edu.copy(year = value)
                            educationList = list
                        },
                        label = { Text("Passing Year") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val list = educationList.toMutableList()
                            list.removeAt(index)
                            educationList = list
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }

        Button(onClick = { educationList = educationList + Education() }) {
            Text("Add Education")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 CERTIFICATES
        Text("Certificates", style = MaterialTheme.typography.titleLarge)

        certificateList.forEachIndexed { index, cert ->
            Card(modifier = Modifier.padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {

                    OutlinedTextField(
                        value = cert.course,
                        onValueChange = { value ->
                            val list = certificateList.toMutableList()
                            list[index] = cert.copy(course = value)
                            certificateList = list
                        },
                        label = { Text("Course Name") }
                    )

                    OutlinedTextField(
                        value = cert.date,
                        onValueChange = { value ->
                            val list = certificateList.toMutableList()
                            list[index] = cert.copy(date = value)
                            certificateList = list
                        },
                        label = { Text("Date") }
                    )

                    OutlinedTextField(
                        value = cert.marks,
                        onValueChange = { value ->
                            val list = certificateList.toMutableList()
                            list[index] = cert.copy(marks = value)
                            certificateList = list
                        },
                        label = { Text("Marks") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val list = certificateList.toMutableList()
                            list.removeAt(index)
                            certificateList = list
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }

        Button(onClick = { certificateList = certificateList + Certificate() }) {
            Text("Add Certificate")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 INTERNSHIPS
        Text("Internships", style = MaterialTheme.typography.titleLarge)

        internshipList.forEachIndexed { index, intern ->
            Card(modifier = Modifier.padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {

                    OutlinedTextField(
                        value = intern.company,
                        onValueChange = { value ->
                            val list = internshipList.toMutableList()
                            list[index] = intern.copy(company = value)
                            internshipList = list
                        },
                        label = { Text("Company Name") }
                    )

                    OutlinedTextField(
                        value = intern.position,
                        onValueChange = { value ->
                            val list = internshipList.toMutableList()
                            list[index] = intern.copy(position = value)
                            internshipList = list
                        },
                        label = { Text("Position") }
                    )

                    OutlinedTextField(
                        value = intern.duration,
                        onValueChange = { value ->
                            val list = internshipList.toMutableList()
                            list[index] = intern.copy(duration = value)
                            internshipList = list
                        },
                        label = { Text("Duration") }
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            val list = internshipList.toMutableList()
                            list.removeAt(index)
                            internshipList = list
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text("Delete")
                    }
                }
            }
        }

        Button(onClick = { internshipList = internshipList + Internship() }) {
            Text("Add Internship")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 🔹 SAVE TO FIREBASE
        Button(
            onClick = {

                val userId = FirebaseAuth.getInstance().currentUser?.uid

                if (userId == null) {
                    Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                val database = FirebaseDatabase.getInstance().reference

                val data = mapOf(
                    "name" to name,
                    "position" to position,
                    "skills" to skills,
                    "education" to educationList,
                    "certificates" to certificateList,
                    "internships" to internshipList
                )

                database.child("users").child(userId).setValue(data)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Saved to Firebase!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Portfolio")
        }
    }
}