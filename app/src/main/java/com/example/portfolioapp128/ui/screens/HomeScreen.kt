package com.example.portfolioapp128.ui.screens

import android.app.DatePickerDialog
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar

// 🔹 Data Classes
data class Education(var className: String = "", var college: String = "", var year: String = "")
data class Certificate(var course: String = "", var date: String = "", var marks: String = "")
data class Internship(var company: String = "", var position: String = "", var from: String = "", var to: String = "")

@Composable
fun HomeScreen(navController: NavController) {

    val context = LocalContext.current
    val db = FirebaseFirestore.getInstance()
    val userId = FirebaseAuth.getInstance().currentUser?.uid

    var name by remember { mutableStateOf("") }
    var position by remember { mutableStateOf("") }
    var skills by remember { mutableStateOf("") }

    var educationList by remember { mutableStateOf(listOf(Education())) }
    var certificateList by remember { mutableStateOf(listOf(Certificate())) }
    var internshipList by remember { mutableStateOf(listOf(Internship())) }

    var isSaving by remember { mutableStateOf(false) }

    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF667eea), Color(0xFF764ba2))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            "Build Your Portfolio ✨",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 BASIC INFO
        Card(shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.padding(16.dp)) {

                InputField("Full Name", name) { name = it }
                InputField("Position", position) { position = it }
                InputField("Skills", skills) { skills = it }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 EDUCATION
        SectionTitle("Education 🎓")

        educationList.forEachIndexed { index, edu ->
            CardItem {
                InputField("Class", edu.className) {
                    updateList(educationList, index, edu.copy(className = it)) { educationList = it }
                }
                InputField("College", edu.college) {
                    updateList(educationList, index, edu.copy(college = it)) { educationList = it }
                }
                InputField("Passing Year", edu.year) {
                    updateList(educationList, index, edu.copy(year = it)) { educationList = it }
                }

                DeleteButton {
                    educationList = educationList.toMutableList().also { it.removeAt(index) }
                }
            }
        }

        AddButton("Add Education") {
            educationList = educationList + Education()
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 CERTIFICATES
        SectionTitle("Certificates 📜")

        certificateList.forEachIndexed { index, cert ->
            CardItem {
                InputField("Course", cert.course) {
                    updateList(certificateList, index, cert.copy(course = it)) { certificateList = it }
                }

                DateField("Date", cert.date) {
                    updateList(certificateList, index, cert.copy(date = it)) { certificateList = it }
                }

                InputField("Marks", cert.marks) {
                    updateList(certificateList, index, cert.copy(marks = it)) { certificateList = it }
                }

                DeleteButton {
                    certificateList = certificateList.toMutableList().also { it.removeAt(index) }
                }
            }
        }

        AddButton("Add Certificate") {
            certificateList = certificateList + Certificate()
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 INTERNSHIPS
        SectionTitle("Internships 💼")

        internshipList.forEachIndexed { index, intern ->
            CardItem {
                InputField("Company", intern.company) {
                    updateList(internshipList, index, intern.copy(company = it)) { internshipList = it }
                }
                InputField("Position", intern.position) {
                    updateList(internshipList, index, intern.copy(position = it)) { internshipList = it }
                }

                DateField("From", intern.from) {
                    updateList(internshipList, index, intern.copy(from = it)) { internshipList = it }
                }

                DateField("To", intern.to) {
                    updateList(internshipList, index, intern.copy(to = it)) { internshipList = it }
                }

                DeleteButton {
                    internshipList = internshipList.toMutableList().also { it.removeAt(index) }
                }
            }
        }

        AddButton("Add Internship") {
            internshipList = internshipList + Internship()
        }

        Spacer(modifier = Modifier.height(30.dp))

        // 🔹 SAVE BUTTON
        Button(
            onClick = {

                if (userId == null) {
                    Toast.makeText(context, "User not logged in ❌", Toast.LENGTH_SHORT).show()
                    return@Button
                }

                isSaving = true

                val data = mapOf(
                    "name" to name,
                    "position" to position,
                    "skills" to skills,
                    "education" to educationList,
                    "certificates" to certificateList,
                    "internships" to internshipList
                )

                db.collection("users")
                    .document(userId)
                    .set(data)
                    .addOnSuccessListener {
                        isSaving = false
                        Toast.makeText(context, "Saved Successfully ✅", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        isSaving = false
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }

            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isSaving) "Saving..." else "Save Details")
        }

        Spacer(modifier = Modifier.height(10.dp))

        // 🔥 PREVIEW BUTTON
        Button(
            onClick = {
                navController.navigate("preview")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Preview Resume 👀")
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(title, style = MaterialTheme.typography.titleLarge, color = Color.White)
}

@Composable
fun CardItem(content: @Composable ColumnScope.() -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.padding(vertical = 6.dp)
    ) {
        Column(Modifier.padding(12.dp), content = content)
    }
}

@Composable
fun InputField(label: String, value: String, onChange: (String) -> Unit) {
    OutlinedTextField(value, onChange, label = { Text(label) }, modifier = Modifier.fillMaxWidth())
}

@Composable
fun AddButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text)
    }
}

@Composable
fun DeleteButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
    ) {
        Text("Delete")
    }
}

@Composable
fun DateField(label: String, value: String, onDateSelected: (String) -> Unit) {

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val datePicker = DatePickerDialog(
        context,
        { _, year, month, day ->
            onDateSelected("$day/${month + 1}/$year")
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    OutlinedTextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { datePicker.show() }) {
                Text("📅")
            }
        }
    )
}

fun <T> updateList(list: List<T>, index: Int, newItem: T, update: (List<T>) -> Unit) {
    val newList = list.toMutableList()
    newList[index] = newItem
    update(newList)
}
