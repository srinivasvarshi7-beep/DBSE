package com.example.steppy.ui.screens.challenge

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.steppy.data.model.Challenge
import com.example.steppy.viewmodel.auth.AuthViewModel
import com.example.steppy.viewmodel.challenge.ChallengeViewModel
import java.util.Calendar

@Composable
fun CreateChallengeScreen(
    onChallengeCreated: () -> Unit,
    onBack: () -> Unit,
    viewModel: ChallengeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val currentUser by authViewModel.currentUser.collectAsState()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var stepGoal by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Create New Challenge", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Challenge Name") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = stepGoal,
            onValueChange = { stepGoal = it },
            label = { Text("Daily Step Goal") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                isLoading = true
                val calendar = Calendar.getInstance()
                val startDate = calendar.time
                calendar.add(Calendar.DAY_OF_YEAR, 7)
                val endDate = calendar.time
                
                val challenge = Challenge(
                    name = name,
                    description = description,
                    stepGoal = stepGoal.toLongOrNull() ?: 10000L,
                    creatorId = currentUser?.id ?: "",
                    startDate = startDate,
                    endDate = endDate,
                    participantIds = listOf(currentUser?.id ?: "")
                )
                
                viewModel.createChallenge(challenge) { result ->
                    isLoading = false
                    if (result.isSuccess) {
                        onChallengeCreated()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && name.isNotBlank()
        ) {
            Text("Create Challenge")
        }
        
        TextButton(onClick = onBack) {
            Text("Cancel")
        }
    }
}
