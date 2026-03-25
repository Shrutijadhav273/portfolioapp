package com.example.portfolioapp128

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.portfolioapp128.ui.screens.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var currentScreen by remember { mutableStateOf("login") }

            when (currentScreen) {
                "login" -> LoginScreen(
                    onNavigateToSignup = { currentScreen = "signup" },
                    onLoginSuccess = { currentScreen = "home" }
                )
                "signup" -> SignupScreen(
                    onSignupSuccess = { currentScreen = "home" },
                    onNavigateToLogin = { currentScreen = "login" }
                )
                "home" -> HomeScreen()
            }
        }
    }
}