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

        // 🔹 Basic Info
        Card(shape = RoundedCornerShape(16.dp)) {
            Column(Modifier.padding(16.dp)) {

                OutlinedTextField(
                    value = name,
                    onValueChange = { newValue -> name = newValue },
                    label = { Text("Full Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = position,
                    onValueChange = { newValue -> position = newValue },
                    label = { Text("Position") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = skills,
                    onValueChange = { newValue -> skills = newValue },
                    label = { Text("Skills") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 EDUCATION
        SectionTitle("Education")

        educationList.forEachIndexed { index, edu ->

            Card(modifier = Modifier.padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {

                    OutlinedTextField(
                        value = edu.className,
                        onValueChange = { newValue ->
                            val updatedList = educationList.toMutableList()
                            updatedList[index] = edu.copy(className = newValue)
                            educationList = updatedList
                        },
                        label = { Text("Class") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = edu.college,
                        onValueChange = { newValue ->
                            val updatedList = educationList.toMutableList()
                            updatedList[index] = edu.copy(college = newValue)
                            educationList = updatedList
                        },
                        label = { Text("College Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = edu.year,
                        onValueChange = { newValue ->
                            val updatedList = educationList.toMutableList()
                            updatedList[index] = edu.copy(year = newValue)
                            educationList = updatedList
                        },
                        label = { Text("Passing Year") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Button(onClick = {
            educationList = educationList + Education()
        }) {
            Text("Add Education")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 CERTIFICATES
        SectionTitle("Certificates")

        certificateList.forEachIndexed { index, cert ->

            Card(modifier = Modifier.padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {

                    OutlinedTextField(
                        value = cert.course,
                        onValueChange = { newValue ->
                            val updatedList = certificateList.toMutableList()
                            updatedList[index] = cert.copy(course = newValue)
                            certificateList = updatedList
                        },
                        label = { Text("Course Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = cert.date,
                        onValueChange = { newValue ->
                            val updatedList = certificateList.toMutableList()
                            updatedList[index] = cert.copy(date = newValue)
                            certificateList = updatedList
                        },
                        label = { Text("Date") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = cert.marks,
                        onValueChange = { newValue ->
                            val updatedList = certificateList.toMutableList()
                            updatedList[index] = cert.copy(marks = newValue)
                            certificateList = updatedList
                        },
                        label = { Text("Marks") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Button(onClick = {
            certificateList = certificateList + Certificate()
        }) {
            Text("Add Certificate")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 INTERNSHIPS
        SectionTitle("Internships")

        internshipList.forEachIndexed { index, intern ->

            Card(modifier = Modifier.padding(vertical = 6.dp)) {
                Column(Modifier.padding(12.dp)) {

                    OutlinedTextField(
                        value = intern.company,
                        onValueChange = { newValue ->
                            val updatedList = internshipList.toMutableList()
                            updatedList[index] = intern.copy(company = newValue)
                            internshipList = updatedList
                        },
                        label = { Text("Company Name") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = intern.position,
                        onValueChange = { newValue ->
                            val updatedList = internshipList.toMutableList()
                            updatedList[index] = intern.copy(position = newValue)
                            internshipList = updatedList
                        },
                        label = { Text("Position") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = intern.duration,
                        onValueChange = { newValue ->
                            val updatedList = internshipList.toMutableList()
                            updatedList[index] = intern.copy(duration = newValue)
                            internshipList = updatedList
                        },
                        label = { Text("Duration") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }

        Button(onClick = {
            internshipList = internshipList + Internship()
        }) {
            Text("Add Internship")
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 🔹 SAVE BUTTON
        Button(
            onClick = {
                Toast.makeText(context, "Portfolio Saved Successfully!", Toast.LENGTH_SHORT).show()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Portfolio")
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, style = MaterialTheme.typography.titleLarge)
}