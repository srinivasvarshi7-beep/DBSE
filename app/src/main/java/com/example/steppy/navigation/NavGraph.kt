package com.example.steppy.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.steppy.ui.screens.auth.LoginScreen
import com.example.steppy.ui.screens.auth.SignupScreen
import com.example.steppy.ui.screens.challenge.ChallengeListScreen
import com.example.steppy.ui.screens.challenge.CreateChallengeScreen
import com.example.steppy.ui.screens.home.HomeScreen
import com.example.steppy.ui.screens.profile.ProfileScreen
import com.example.steppy.ui.screens.social.FriendsScreen
import com.example.steppy.ui.screens.social.LeaderboardScreen
import com.example.steppy.viewmodel.auth.AuthViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    val startDestination = if (currentUser == null) Screen.Login.route else Screen.Home.route

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onNavigateToSignup = { navController.navigate(Screen.Signup.route) },
                onLoginSuccess = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Login.route) { inclusive = true }
                } }
            )
        }
        composable(Screen.Signup.route) {
            SignupScreen(
                onNavigateToLogin = { navController.popBackStack() },
                onSignupSuccess = { navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Signup.route) { inclusive = true }
                } }
            )
        }
        composable(Screen.Home.route) {
            HomeScreen()
        }
        composable(Screen.Challenges.route) {
            ChallengeListScreen(
                onNavigateToCreate = { navController.navigate(Screen.CreateChallenge.route) },
                onNavigateToDetail = { id -> /* navigate to detail later */ }
            )
        }
        composable(Screen.CreateChallenge.route) {
            CreateChallengeScreen(
                onChallengeCreated = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
        composable(Screen.Leaderboard.route) {
            LeaderboardScreen()
        }
        composable(Screen.Profile.route) {
            ProfileScreen()
        }
        composable(Screen.Friends.route) {
            FriendsScreen()
        }
    }
}
