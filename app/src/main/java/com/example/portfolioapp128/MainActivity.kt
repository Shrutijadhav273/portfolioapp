package com.example.portfolioapp128

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// 🔴 Import your screens explicitly (important)
import com.example.portfolioapp128.ui.screens.HomeScreen
import com.example.portfolioapp128.ui.screens.LoginScreen
import com.example.portfolioapp128.ui.screens.PreviewScreen
import com.example.portfolioapp128.ui.screens.SignupScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = "signup"
            ) {

                // 🔹 Signup Screen
                composable("signup") {
                    SignupScreen(
                        onSignupSuccess = {
                            navController.navigate("home") {
                                popUpTo("signup") { inclusive = true }
                            }
                        },
                        onNavigateToLogin = {
                            navController.navigate("login")
                        }
                    )
                }

                // 🔹 Login Screen
                composable("login") {
                    LoginScreen(
                        onLoginSuccess = {
                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        },
                        onNavigateToSignup = {
                            navController.navigate("signup")
                        }
                    )
                }

                // 🔹 Home Screen
                composable("home") {
                    HomeScreen(navController)
                }
                composable("preview") {
                    PreviewScreen()
                }
            }
        }
    }
}