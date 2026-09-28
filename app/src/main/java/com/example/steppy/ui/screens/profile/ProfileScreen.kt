package com.example.steppy.ui.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.steppy.viewmodel.auth.AuthViewModel

@Composable
fun ProfileScreen(
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val user by authViewModel.currentUser.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Profile", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        
        user?.let {
            Text("Username: ${it.username}")
            Text("Email: ${it.email}")
            Text("XP: ${it.xp} (Level ${it.level})")
            Text("Total Steps: ${it.totalSteps}")
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { authViewModel.signOut() }) {
            Text("Logout")
        }
    }
}
