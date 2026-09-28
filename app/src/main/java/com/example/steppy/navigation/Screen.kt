package com.example.steppy.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Signup : Screen("signup")
    object Home : Screen("home")
    object Challenges : Screen("challenges")
    object Leaderboard : Screen("leaderboard")
    object Friends : Screen("friends")
    object Profile : Screen("profile")
    object CreateChallenge : Screen("create_challenge")
}
